package pl.dudios.shopmvn.basket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.dudios.shopmvn.basket.model.Basket;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BasketRepo extends JpaRepository<Basket, Long> {

    List<Basket> findByCreatedLessThan(LocalDateTime minusDays);

    @Query("DELETE FROM Basket b WHERE b.id in (:expiredBasketsIds)")
    @Modifying
    void deleteAllByIdIn(List<Long> expiredBasketsIds);
}
