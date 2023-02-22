package pl.dudios.shopmvn.order.service.payment.p24;

import lombok.Getter;

@Getter
public class TransactionVerifyResponseDto {

    private Data data;

    record Data(String status) {
    }
}
