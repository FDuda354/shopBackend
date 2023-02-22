package pl.dudios.shopmvn.order.service.payment.p24.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TransactionVerifyRequestDto {
    private Integer merchantId;
    private Integer posId;
    private String sessionId;
    private Integer amount;
    private String currency;
    private Integer orderId;
    private String sign;
}
