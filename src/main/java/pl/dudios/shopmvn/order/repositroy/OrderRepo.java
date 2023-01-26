package pl.dudios.shopmvn.order.repositroy;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dudios.shopmvn.order.model.Order;

public interface OrderRepo extends JpaRepository<Order, Long> {
}
