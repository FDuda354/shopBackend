package pl.dudios.shopmvn.order.repositroy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dudios.shopmvn.order.model.Order;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
    List<Order> findAllByUserId(Long userId);

    Optional<Order> findByOrderHash(String orderHash);
}
