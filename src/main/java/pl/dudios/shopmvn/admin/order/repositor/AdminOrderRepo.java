package pl.dudios.shopmvn.admin.order.repositor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dudios.shopmvn.admin.order.model.AdminOrder;


@Repository
public interface AdminOrderRepo extends JpaRepository<AdminOrder, Long> {
}
