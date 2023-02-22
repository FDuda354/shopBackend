package pl.dudios.shopmvn.order.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dudios.shopmvn.common.mail.EmailClientService;
import pl.dudios.shopmvn.common.model.Basket;
import pl.dudios.shopmvn.common.model.OrderStatus;
import pl.dudios.shopmvn.common.repository.BasketItemRepo;
import pl.dudios.shopmvn.common.repository.BasketRepo;
import pl.dudios.shopmvn.order.model.*;
import pl.dudios.shopmvn.order.model.dto.OrderDto;
import pl.dudios.shopmvn.order.model.dto.OrderDtoForUser;
import pl.dudios.shopmvn.order.model.dto.OrderSummary;
import pl.dudios.shopmvn.order.repositroy.*;
import pl.dudios.shopmvn.order.service.payment.p24.PaymentMethodP24;
import pl.dudios.shopmvn.order.service.payment.p24.dto.NotificationReceiveDto;

import java.time.LocalDateTime;
import java.util.List;

import static pl.dudios.shopmvn.order.service.mapper.OrderEmailMessageMapper.createEmailMessage;
import static pl.dudios.shopmvn.order.service.mapper.OrderMapper.*;

@Service
@Slf4j
@AllArgsConstructor
public class OrderService {
    private final OrderRepo orderRepo;
    private final OrderLogsRepo orderLogsRepo;
    private final OrderRowRepo orderRowRepo;
    private final BasketRepo basketRepo;
    private final BasketItemRepo basketItemRepo;
    private final ShipmentRepo shipmentRepo;
    private final PaymentRepo paymentRepo;
    private final EmailClientService emailClientService;
    private final PaymentMethodP24 paymentMethodP24;

    @Transactional
    public OrderSummary createOrder(OrderDto orderDto, Long userId) {
        Basket basket = basketRepo.findById(orderDto.getBasketId()).orElseThrow();
        Shipment shipment = shipmentRepo.findById(orderDto.getShipmentId()).orElseThrow();
        Payment payment = paymentRepo.findById(orderDto.getPaymentId()).orElseThrow();
        Order newOrder = orderRepo.save(createNewOrder(orderDto, basket, shipment, payment, userId));

        saveOrderRows(basket, newOrder.getId(), shipment);
        clearBasket(orderDto);

        sendConfirmEmail(newOrder);

        String redirectUrl = initPaymentIfNeeded(newOrder);

        if (redirectUrl == null)
            newOrder.setOrderStatus(OrderStatus.NEW);

        return createOrderSummary(newOrder, redirectUrl);
    }

    private String initPaymentIfNeeded(Order newOrder) {
        if (newOrder.getPayment().getType().equals(PaymentType.P24_ONLINE)) {
            return paymentMethodP24.initPayment(newOrder);
        }
        return null;
    }

    private void sendConfirmEmail(Order newOrder) {
        try {
            emailClientService.getSender()
                    .sendEmail(newOrder.getEmail(), "Order confirmation", createEmailMessage(newOrder));
        } catch (Exception e) {
            log.error("Error while sending email", e);
        }

    }

    private void clearBasket(OrderDto orderDto) {
        basketItemRepo.deleteByBasketId(orderDto.getBasketId());
        basketRepo.deleteBasketById(orderDto.getBasketId());
    }


    private void saveOrderRows(Basket basket, Long orderId, Shipment shipment) {
        saveProductRows(basket, orderId);
        saveShipmentRow(orderId, shipment);
    }

    private void saveShipmentRow(Long orderId, Shipment shipment) {
        orderRowRepo.save(mapToOrderRow(orderId, shipment));
    }

    private void saveProductRows(Basket basket, Long orderId) {
        List<OrderRow> orderRows = basket.getItems().stream()
                .map(item -> mapToOrderRowWithQuantity(orderId, item))
                .peek(orderRowRepo::save)
                .toList();
    }

    public List<OrderDtoForUser> getOrdersFromUser(Long userId) {
        return createOrderListDtoForUser(orderRepo.findAllByUserId(userId));
    }

    public Order getOrderByOrderHash(String orderHash) {
        return orderRepo.findByOrderHash(orderHash).orElseThrow();
    }

    @Transactional
    public void receiveNotification(String orderHash, NotificationReceiveDto receiveDto, String remoteAddr) {
        Order order = getOrderByOrderHash(orderHash);
        String status = paymentMethodP24.receiveNotification(order, receiveDto, remoteAddr);
        if (status.equals("success")) {
            order.setOrderStatus(OrderStatus.PAID);
            orderLogsRepo.save(OrderLog.builder()
                    .created(LocalDateTime.now())
                    .orderId(order.getId())
                    .note("Order paid by P24 order id: " + receiveDto.statement())
                    .build());
        }
    }
}
