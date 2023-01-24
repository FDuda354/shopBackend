package pl.dudios.shopmvn.basket.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import pl.dudios.shopmvn.basket.service.BasketService;

@RestController
@AllArgsConstructor
public class BasketContorller {

    private final BasketService basketService;
}
