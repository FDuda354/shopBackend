package pl.dudios.shopmvn.order.repositroy;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dudios.shopmvn.order.model.OrderLog;

public interface OrderLogsRepo extends JpaRepository<OrderLog, Long> {
}
