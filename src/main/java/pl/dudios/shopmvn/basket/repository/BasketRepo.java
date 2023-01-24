package pl.dudios.shopmvn.basket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dudios.shopmvn.basket.model.Basket;

@Repository
public interface BasketRepo extends JpaRepository<Basket, Long> {

}
