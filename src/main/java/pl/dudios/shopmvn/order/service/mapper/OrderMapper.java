package pl.dudios.shopmvn.order.service.mapper;

import pl.dudios.shopmvn.common.model.Basket;
import pl.dudios.shopmvn.common.model.BasketItem;
import pl.dudios.shopmvn.order.model.Order;
import pl.dudios.shopmvn.order.model.OrderRow;
import pl.dudios.shopmvn.order.model.OrderStatus;
import pl.dudios.shopmvn.order.model.Payment;
import pl.dudios.shopmvn.order.model.Shipment;
import pl.dudios.shopmvn.order.model.dto.OrderDto;
import pl.dudios.shopmvn.order.model.dto.OrderSummary;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderMapper {

    public static Order createNewOrder(OrderDto orderDto, Basket basket, Shipment shipment, Payment payment) {
        return Order.builder()
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
                .payment(payment)
                .build();
    }

    private static BigDecimal calcGrossValue(List<BasketItem> items, Shipment shipment) {
        return items.stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .add(shipment.getPrice());
    }

    public static OrderSummary createOrderSummary(Order newOrder) {
        return OrderSummary.builder()
                .id(newOrder.getId())
                .placeDate(newOrder.getPlaceDate())
                .status(newOrder.getOrderStatus())
                .grossValue(newOrder.getGrossValue())
                .payment(newOrder.getPayment())
                .build();
    }

    public static OrderRow mapToOrderRow(Long orderId, Shipment shipment) {
        return OrderRow.builder()
                .orderId(orderId)
                .price(shipment.getPrice())
                .quantity(1L)
                .shipmentId(shipment.getId())
                .build();
    }

    public static OrderRow mapToOrderRowWithQuantity(Long orderId, BasketItem item) {
        return OrderRow.builder()
                .orderId(orderId)
                .productId(item.getProduct().getId())
                .price(item.getProduct().getPrice())
                .quantity(item.getQuantity())
                .build();
    }
}
