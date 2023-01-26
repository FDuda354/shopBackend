package pl.dudios.shopmvn.order.repositroy;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dudios.shopmvn.order.model.OrderRow;

public interface OrderRowRepo extends JpaRepository<OrderRow, Long> {
}
