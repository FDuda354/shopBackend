package pl.dudios.shopmvn.order.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.dudios.shopmvn.order.model.dto.OrderDto;
import pl.dudios.shopmvn.order.model.dto.OrderSummary;
import pl.dudios.shopmvn.order.service.OrderService;

@RestController
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/order")
    public OrderSummary createOrder(@RequestBody OrderDto orderDto) {
        return orderService.createOrder(orderDto);
    }
}
