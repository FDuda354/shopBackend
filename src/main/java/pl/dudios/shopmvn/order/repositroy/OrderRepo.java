package pl.dudios.shopmvn.order.repositroy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dudios.shopmvn.order.model.Order;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
}
