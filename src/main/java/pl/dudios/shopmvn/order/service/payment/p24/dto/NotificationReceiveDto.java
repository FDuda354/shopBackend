package pl.dudios.shopmvn.order.service.payment.p24.dto;

public record NotificationReceiveDto(
        Integer merchantId,
        Integer posId,
        String sessionId,
        Integer amount,
        Integer originAmount,
        String currency,
        Integer orderId,
        Integer methodId,
        String statement,
        String sign
) {
}
