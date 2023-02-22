package pl.dudios.shopmvn.order.service.payment.p24;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pl.dudios.shopmvn.order.model.Order;
import pl.dudios.shopmvn.order.service.payment.p24.dto.NotificationReceiveDto;
import reactor.core.publisher.Mono;

import static pl.dudios.shopmvn.order.service.payment.p24.RequestUtil.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentMethodP24 {

    private final PaymentMethodP24Config config;
    private final WebClient webClient;

    public String initPayment(Order newOrder) {
        log.info("Initiating payment for order: {}", newOrder.getId());
        ResponseEntity<TransactionRegisterResponse> result = webClient.post().uri("/transaction/register")
                .bodyValue(createTransactionRegisterRequest(config, newOrder))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        clientResponse -> {
                            log.error("Error during payment initiation: {}", clientResponse.statusCode());
                            throw new RuntimeException("Error during payment initiation: " + clientResponse.statusCode());
                        })
                .toEntity(TransactionRegisterResponse.class)
                .block();
        if (result != null && result.getBody() != null && result.getBody().getData() != null)
            return (config.isTestMode() ? config.getTestUrl() : config.getUrl()) + "/trnRequest/" + result.getBody().getData().token();

        return null;
    }

    public String receiveNotification(Order order, NotificationReceiveDto receiveDto, String remoteAddr) {
        log.info("NotificationReceiveDto: {}", receiveDto);
        validateIpAddress(remoteAddr, config);
        validateNotification(order, receiveDto, config);

        return verifyPayment(order, receiveDto);
    }

    private String verifyPayment(Order order, NotificationReceiveDto receiveDto) {
        ResponseEntity<TransactionVerifyResponseDto> response = webClient.put().uri("/transaction/verify")
                .bodyValue(createTransactionVerifyRequest(config, order, receiveDto))
                .retrieve()
                .toEntity(TransactionVerifyResponseDto.class)
                .block();

        log.info("=Status: {}", response.getBody().getData().status());

        return response.getBody().getData().status();
    }

}
