package pl.dudios.shopmvn.basket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.dudios.shopmvn.basket.model.BasketItem;

import java.util.List;

@Repository
public interface BasketItemRepo extends JpaRepository<BasketItem, Long> {
    Long countByBasketId(Long basketId);


    @Query("DELETE FROM BasketItem bi WHERE bi.basketId in (:expiredBasketsIds)")
    @Modifying
    void deleteAllByBasketId(List<Long> expiredBasketsIds);
}
