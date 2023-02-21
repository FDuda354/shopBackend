package pl.dudios.shopmvn.order.service.payment.p24;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;
import pl.dudios.shopmvn.order.model.Order;
import pl.dudios.shopmvn.order.service.payment.p24.dto.NotificationReceiveDto;
import pl.dudios.shopmvn.order.service.payment.p24.dto.TransactionVerifyRequestDto;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentMethodP24 {

    private final PaymentMethodP24Config config;

    public String initPayment(Order newOrder) {
        log.info("Initiating payment for order: {}", newOrder.getId());

        WebClient webClient = WebClient.builder()
                .filter(ExchangeFilterFunctions.basicAuthentication(config.getPosId().toString(),
                        config.isTestMode() ? config.getTestSecretKey() : config.getSecretKey()))
                .baseUrl(config.isTestMode() ? config.getTestApiUrl() : config.getApiUrl())
                .build();


        ResponseEntity<TransactionRegisterResponse> result = webClient.post().uri("/transaction/register")
                .bodyValue(TransactionRegisterRequest.builder()
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
                        .urlReturn(getReturnUrl(newOrder.getOrderHash()))
                        .urlStatus(getStatusUrl(newOrder.getOrderHash()))
                        .sign(createSign(newOrder))
                        .encoding("UTF-8")
                        .build())
                .retrieve()
                .onStatus(status -> {
                     log.info("registering transaction: {}", status);
                   return status.is4xxClientError();
                }, clientResponse -> Mono.empty())
                .toEntity(TransactionRegisterResponse.class)
                .block();
        if(result != null && result.getBody() != null && result.getBody().getData() != null)
            return (config.isTestMode()? config.getTestUrl(): config.getUrl()) + "/trnRequest/"+ result.getBody().getData().token();

        return null;
    }

    private String getStatusUrl(String orderHash) {
        String baseUrl = config.isTestMode() ? config.getTestUrlStatus() : config.getUrlStatus();
        return baseUrl + "/order/notification/" + orderHash;
    }

    private String getReturnUrl(String orderHash) {
        String baseUrl = config.isTestMode() ? config.getTestUrlReturn() : config.getUrlReturn();
        return baseUrl + "/order/notification/" + orderHash;
    }

    private String createSessionId(Order newOrder) {
        return "order_id_" + newOrder.getId();
    }

    public String receiveNotification(Order order, NotificationReceiveDto receiveDto, String remoteAddr) {
        log.info("NotificationReceiveDto: {}", receiveDto);
        validateIpAddress(remoteAddr);
        validateNotification(order, receiveDto);

       return verifyPayment(order, receiveDto);

    }

    private void validateIpAddress(String remoteAddr) {
        log.info("===+++===+++====remoteAddr: " + remoteAddr);
        //TODO: validate IP address. now is show remoteAddr is 172.17.01 why?
        //validateField(config.getServers().contains(remoteAddr), "IP address not allowed" + remoteAddr);
    }


    private void validateNotification(Order order, NotificationReceiveDto receiveDto) {
        validateField(receiveDto.merchantId().equals(config.getMerchantId()), "Merchant ID does not match");
        validateField(receiveDto.posId().equals(config.getPosId()), "POS ID does not match");
        validateField(receiveDto.sessionId().equals(createSessionId(order)), "Session ID does not match");
        validateField(order.getGrossValue().compareTo(BigDecimal.valueOf(receiveDto.amount()).movePointLeft(2)) == 0, "Amount does not match");
        validateField(order.getGrossValue().compareTo(BigDecimal.valueOf(receiveDto.originAmount()).movePointLeft(2)) == 0, "Amount does not match");
        validateField(receiveDto.currency().equals("PLN"), "Currency does not match");
        validateField(createReceivedSign(receiveDto, order).equals(receiveDto.sign()), "Sign does not match");
    }

    private String verifyPayment(Order order, NotificationReceiveDto receiveDto) {
        WebClient webClient = WebClient.builder()
                .filter(ExchangeFilterFunctions.basicAuthentication(config.getPosId().toString(),
                        config.isTestMode() ? config.getTestSecretKey() : config.getSecretKey()))
                .baseUrl(config.isTestMode() ? config.getTestApiUrl() : config.getApiUrl())
                .build();

        ResponseEntity<TransactionVerifyResponseDto> response = webClient.put().uri("/transaction/verify")
                .bodyValue(TransactionVerifyRequestDto.builder()
                        .merchantId(config.getMerchantId())
                        .posId(config.getPosId())
                        .sessionId(createSessionId(order))
                        .amount(order.getGrossValue().movePointRight(2).intValue())
                        .currency("PLN")
                        .orderId(receiveDto.orderId())
                        .sign(createVerifySign(receiveDto, order))
                        .build())
                .retrieve()
                .toEntity(TransactionVerifyResponseDto.class)
                .block();

        log.info("=Status: {}", response.getBody().getData().status());

        return response.getBody().getData().status();
    }

    private String createVerifySign(NotificationReceiveDto receiveDto, Order order) {
        String json = "{\"sessionId\":\"" + createSessionId(order) +
                "\",\"orderId\":" + receiveDto.orderId() +
                ",\"amount\":" + order.getGrossValue().movePointRight(2).intValue() +
                ",\"currency\":\"PLN\",\"crc\":\"" + (config.isTestMode() ? config.getTestCrc() : config.getCrc()) + "\"}";
        return DigestUtils.sha384Hex(json);
    }

    private String createSign(Order newOrder) {
        String json = "{\"sessionId\":\"" + createSessionId(newOrder) +
                "\",\"merchantId\":" + config.getMerchantId() +
                ",\"amount\":" + newOrder.getGrossValue().movePointRight(2).intValue() +
                ",\"currency\":\"PLN\",\"crc\":\"" + (config.isTestMode() ? config.getTestCrc() : config.getCrc()) + "\"}";

        return DigestUtils.sha384Hex(json);
    }
    private String createReceivedSign(NotificationReceiveDto receiveDto, Order order) {
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

    private void validateField(boolean condition, String message) {
        if (!condition)
            throw new RuntimeException(message);
    }


}
