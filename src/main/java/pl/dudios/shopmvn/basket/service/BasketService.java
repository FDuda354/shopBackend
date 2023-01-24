package pl.dudios.shopmvn.basket.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.dudios.shopmvn.basket.repository.BasketRepo;

@Service
@AllArgsConstructor
public class BasketService {

    private final BasketRepo basketRepo;
}
