package pl.dudios.shopmvn.order.model.dto;

import pl.dudios.shopmvn.common.model.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderDtoForUser(Long id, LocalDateTime placeDate, String status, BigDecimal grossValue,
                              String paymentName) {

}