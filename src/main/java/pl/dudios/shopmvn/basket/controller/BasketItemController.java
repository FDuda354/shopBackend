package pl.dudios.shopmvn.basket.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.dudios.shopmvn.basket.service.BasketItemService;

@RestController
@AllArgsConstructor
@RequestMapping("/basketItems")
public class BasketItemController {

    private final BasketItemService basketItemService;

    @DeleteMapping("/{id}")
    public void deleteItemFromBasket(@PathVariable Long id) {
        basketItemService.deleteItemFromBasket(id);
    }

    @GetMapping("/counter/{basketId}")
    public Long countItemInBasket(@PathVariable Long basketId) {
        return basketItemService.countItemInBasket(basketId);
    }
}
