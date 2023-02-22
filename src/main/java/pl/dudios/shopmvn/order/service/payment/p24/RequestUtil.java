package pl.dudios.shopmvn.order.service.payment.p24;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import pl.dudios.shopmvn.order.model.Order;
import pl.dudios.shopmvn.order.service.payment.p24.dto.NotificationReceiveDto;
import pl.dudios.shopmvn.order.service.payment.p24.dto.TransactionVerifyRequestDto;

import java.math.BigDecimal;

@Slf4j
public class RequestUtil {

    public static TransactionRegisterRequest createTransactionRegisterRequest(PaymentMethodP24Config config, Order newOrder) {
        return TransactionRegisterRequest.builder()
                .merchantId(config.getMerchantId())
                .posId(config.getPosId())
                .sessionId(createSessionId(newOrder))
                .amount(newOrder.getGrossValue().movePointRight(2).intValue())
                .currency("PLN")
                .description("Order ID: " + newOrder.getId())
                .email(newOrder.getEmail())
                .client(newOrder.getFirstName() + " " + newOrder.getLastName())
                .country("PL")
                .language("pl")
                .urlReturn(getReturnUrl(newOrder.getOrderHash(), config))
                .urlStatus(getStatusUrl(newOrder.getOrderHash(), config))
                .sign(createSign(newOrder, config))
                .encoding("UTF-8")
                .build();
    }

    public static TransactionVerifyRequestDto createTransactionVerifyRequest(PaymentMethodP24Config config, Order order, NotificationReceiveDto receiveDto) {
        return TransactionVerifyRequestDto.builder()
                .merchantId(config.getMerchantId())
                .posId(config.getPosId())
                .sessionId(createSessionId(order))
                .amount(order.getGrossValue().movePointRight(2).intValue())
                .currency("PLN")
                .orderId(receiveDto.orderId())
                .sign(createVerifySign(receiveDto, order, config))
                .build();
    }

    private static String createSessionId(Order newOrder) {
        return "order_id_" + newOrder.getId();
    }

    private static String getReturnUrl(String orderHash, PaymentMethodP24Config config) {
        String baseUrl = config.isTestMode() ? config.getTestUrlReturn() : config.getUrlReturn();
        return baseUrl + "/order/notification/" + orderHash;
    }

    private static String getStatusUrl(String orderHash, PaymentMethodP24Config config) {
        String baseUrl = config.isTestMode() ? config.getTestUrlStatus() : config.getUrlStatus();
        return baseUrl + "/order/notification/" + orderHash;
    }

    private static String createVerifySign(NotificationReceiveDto receiveDto, Order order, PaymentMethodP24Config config) {
        String json = "{\"sessionId\":\"" + createSessionId(order) +
                "\",\"orderId\":" + receiveDto.orderId() +
                ",\"amount\":" + order.getGrossValue().movePointRight(2).intValue() +
                ",\"currency\":\"PLN\",\"crc\":\"" + (config.isTestMode() ? config.getTestCrc() : config.getCrc()) + "\"}";
        return DigestUtils.sha384Hex(json);
    }

    private static String createSign(Order newOrder, PaymentMethodP24Config config) {
        String json = "{\"sessionId\":\"" + createSessionId(newOrder) +
                "\",\"merchantId\":" + config.getMerchantId() +
                ",\"amount\":" + newOrder.getGrossValue().movePointRight(2).intValue() +
                ",\"currency\":\"PLN\",\"crc\":\"" + (config.isTestMode() ? config.getTestCrc() : config.getCrc()) + "\"}";

        return DigestUtils.sha384Hex(json);
    }

    private static String createReceivedSign(NotificationReceiveDto receiveDto, Order order, PaymentMethodP24Config config) {
        String json = "{\"merchantId\":" + config.getMerchantId() +
                ",\"posId\":" + config.getPosId() +
                ",\"sessionId\":\"" + createSessionId(order) +
                "\",\"amount\":" + order.getGrossValue().movePointRight(2).intValue() +
                ",\"originAmount\":" + order.getGrossValue().movePointRight(2).intValue() +
                ",\"currency\":\"PLN\"" +
                ",\"orderId\":" + receiveDto.orderId() +
                ",\"methodId\":" + receiveDto.methodId() +
                ",\"statement\":\"" + receiveDto.statement() +
                "\",\"crc\":\"" + (config.isTestMode() ? config.getTestCrc() : config.getCrc()) + "\"}";
        return DigestUtils.sha384Hex(json);
    }

    public static void validateIpAddress(String remoteAddr, PaymentMethodP24Config config) {
        log.info("===+++===+++====remoteAddr: " + remoteAddr);
        //TODO: validate IP address. now is show remoteAddr is 172.17.01 why?
        //validateField(config.getServers().contains(remoteAddr), "IP address not allowed" + remoteAddr);
    }

    public static void validateNotification(Order order, NotificationReceiveDto receiveDto, PaymentMethodP24Config config) {
        validateField(receiveDto.merchantId().equals(config.getMerchantId()), "Merchant ID does not match");
        validateField(receiveDto.posId().equals(config.getPosId()), "POS ID does not match");
        validateField(receiveDto.sessionId().equals(createSessionId(order)), "Session ID does not match");
        validateField(order.getGrossValue().compareTo(BigDecimal.valueOf(receiveDto.amount()).movePointLeft(2)) == 0, "Amount does not match");
        validateField(order.getGrossValue().compareTo(BigDecimal.valueOf(receiveDto.originAmount()).movePointLeft(2)) == 0, "Amount does not match");
        validateField(receiveDto.currency().equals("PLN"), "Currency does not match");
        validateField(createReceivedSign(receiveDto, order, config).equals(receiveDto.sign()), "Sign does not match");
    }

    private static void validateField(boolean condition, String message) {
        if (!condition)
            throw new RuntimeException(message);
    }

}
