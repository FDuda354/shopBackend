package pl.dudios.shopmvn.order.controller;

import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.dudios.shopmvn.common.model.OrderStatus;
import pl.dudios.shopmvn.order.model.dto.InitOrder;
import pl.dudios.shopmvn.order.model.dto.OrderDto;
import pl.dudios.shopmvn.order.model.dto.OrderDtoForUser;
import pl.dudios.shopmvn.order.model.dto.OrderSummary;
import pl.dudios.shopmvn.order.service.OrderService;
import pl.dudios.shopmvn.order.service.PaymentService;
import pl.dudios.shopmvn.order.service.ShipmentService;
import pl.dudios.shopmvn.order.service.payment.p24.dto.NotificationDto;
import pl.dudios.shopmvn.order.service.payment.p24.dto.NotificationReceiveDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@AllArgsConstructor
@Validated
public class OrderController {
    private final OrderService orderService;
    private final ShipmentService shipmentService;
    private final PaymentService paymentService;

    @PostMapping("/order")
    public OrderSummary createOrder(@RequestBody OrderDto orderDto, @AuthenticationPrincipal Long userId) {
        return orderService.createOrder(orderDto, userId);
    }

    @GetMapping("/order/initOrder")
    public InitOrder initOrder() {
        return InitOrder.builder()
                .shipments(shipmentService.getShipments())
                .payments(paymentService.getPayments())
                .build();
    }

    @GetMapping("/orders")
    public List<OrderDtoForUser> getOrders(@AuthenticationPrincipal Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("NULL user in getOrders");
        }
        return orderService.getOrdersFromUser(userId);
    }

    @GetMapping("/order/notification/{orderHash}")
    public NotificationDto P24NotificationShow(@PathVariable @Length(max = 12) String orderHash) {
        return new NotificationDto(orderService.getOrderByOrderHash(orderHash).getOrderStatus().equals(OrderStatus.PAID));
    }

    @PostMapping("/order/notification/{orderHash}")
    public String getP24Notification(@PathVariable @Length(max = 12) String orderHash,
                                     @RequestBody NotificationReceiveDto receiveDto,
                                     HttpServletRequest request) {
        orderService.receiveNotification(orderHash, receiveDto, request.getRemoteAddr());
        return orderHash;
    }

}
