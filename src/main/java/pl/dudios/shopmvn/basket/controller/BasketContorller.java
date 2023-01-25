package pl.dudios.shopmvn.basket.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.dudios.shopmvn.basket.controller.dto.BasketSummaryDto;
import pl.dudios.shopmvn.basket.controller.mapper.BasketMapper;
import pl.dudios.shopmvn.basket.model.Basket;
import pl.dudios.shopmvn.basket.model.dto.BasketProductDto;
import pl.dudios.shopmvn.basket.service.BasketService;

@RestController
@AllArgsConstructor
@RequestMapping("/basket")
public class BasketContorller {

    private final BasketService basketService;

    @GetMapping("/{id}")
    public BasketSummaryDto getBasket(@PathVariable Long id){
        return BasketMapper.mapToBasketSummaryDto(basketService.getBasket(id));
    }

    @PutMapping("/{id}")
    public BasketSummaryDto addProductToBasket(@PathVariable Long id, @RequestBody BasketProductDto basketProductDto){
        return BasketMapper.mapToBasketSummaryDto(basketService.addProductToBasket(id,basketProductDto));
    }
}
