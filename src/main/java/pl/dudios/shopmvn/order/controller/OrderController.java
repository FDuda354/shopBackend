package pl.dudios.shopmvn.order.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.dudios.shopmvn.order.model.dto.InitOrder;
import pl.dudios.shopmvn.order.model.dto.OrderDto;
import pl.dudios.shopmvn.order.model.dto.OrderSummary;
import pl.dudios.shopmvn.order.service.OrderService;
import pl.dudios.shopmvn.order.service.ShipmentService;

@RestController
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final ShipmentService shipmentService;

    @PostMapping("/order")
    public OrderSummary createOrder(@RequestBody OrderDto orderDto) {
        return orderService.createOrder(orderDto);
    }

    @GetMapping("/order/initOrder")
    public InitOrder initOrder() {
        return InitOrder.builder()
                .shipments(shipmentService.getShipments())
                .build();
    }
}
