package pl.dudios.shopmvn.basket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dudios.shopmvn.basket.model.BasketItem;

@Repository
public interface BasketItemRepo extends JpaRepository<BasketItem, Long> {
}
