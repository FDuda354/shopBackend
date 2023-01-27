package pl.dudios.shopmvn.order.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dudios.shopmvn.common.model.Basket;
import pl.dudios.shopmvn.common.model.BasketItem;
import pl.dudios.shopmvn.common.repository.BasketItemRepo;
import pl.dudios.shopmvn.common.repository.BasketRepo;
import pl.dudios.shopmvn.order.model.Order;
import pl.dudios.shopmvn.order.model.OrderRow;
import pl.dudios.shopmvn.order.model.OrderStatus;
import pl.dudios.shopmvn.order.model.Shipment;
import pl.dudios.shopmvn.order.model.dto.OrderDto;
import pl.dudios.shopmvn.order.model.dto.OrderSummary;
import pl.dudios.shopmvn.order.repositroy.OrderRepo;
import pl.dudios.shopmvn.order.repositroy.OrderRowRepo;
import pl.dudios.shopmvn.order.repositroy.ShipmentRepo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepo orderRepo;
    private final OrderRowRepo orderRowRepo;
    private final BasketRepo basketRepo;
    private final BasketItemRepo basketItemRepo;

    private final ShipmentRepo shipmentRepo;

    @Transactional
    public OrderSummary createOrder(OrderDto orderDto) {
        Basket basket = basketRepo.findById(orderDto.getBasketId()).orElseThrow();
        Shipment shipment = shipmentRepo.findById(orderDto.getShipmentId()).orElseThrow();

        Order order = Order.builder()
                .firstName(orderDto.getFirstName())
                .lastName(orderDto.getLastName())
                .street(orderDto.getStreet())
                .zipCode(orderDto.getZipCode())
                .city(orderDto.getCity())
                .email(orderDto.getEmail())
                .phone(orderDto.getPhone())
                .placeDate(LocalDateTime.now())
                .orderStatus(OrderStatus.NEW)
                .grossValue(calcGrossValue(basket.getItems(), shipment))
                .build();

        Order newOrder = orderRepo.save(order);
        saveOrderRows(basket, newOrder.getId(), shipment);
        basketItemRepo.deleteByBasketId(orderDto.getBasketId());
        basketRepo.deleteBasketById(orderDto.getBasketId());

        return OrderSummary.builder()
                .id(newOrder.getId())
                .placeDate(newOrder.getPlaceDate())
                .status(newOrder.getOrderStatus())
                .grossValue(newOrder.getGrossValue())
                .build();
    }

    private BigDecimal calcGrossValue(List<BasketItem> items, Shipment shipment) {
        return items.stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .add(shipment.getPrice());
    }


    private void saveOrderRows(Basket basket, Long orderId,Shipment shipment) {
        saveProductRows(basket, orderId);
        saveShipmentRow(orderId, shipment);
    }

    private void saveShipmentRow(Long orderId, Shipment shipment) {
        orderRowRepo.save(OrderRow.builder()
                .orderId(orderId)
                .price(shipment.getPrice())
                .quantity(1L)
                .shipmentId(shipment.getId())
                .build());
    }

    private void saveProductRows(Basket basket, Long orderId) {
        List<OrderRow> orderRows = basket.getItems().stream()
                .map(item -> OrderRow.builder()
                        .orderId(orderId)
                        .productId(item.getProduct().getId())
                        .price(item.getProduct().getPrice())
                        .quantity(item.getQuantity())
                        .build())
                .peek(orderRowRepo::save)
                .toList();
    }
}
