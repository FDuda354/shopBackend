package pl.dudios.shopmvn.admin.order.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dudios.shopmvn.admin.order.model.AdminOrder;
import pl.dudios.shopmvn.admin.order.model.AdminOrderStatus;
import pl.dudios.shopmvn.admin.order.repositor.AdminOrderRepo;

import java.util.Map;


@Service
@AllArgsConstructor
public class AdminOrderService {

    private final AdminOrderRepo adminOrderRepo;

    public Page<AdminOrder> getOrders(Pageable pageable) {
        return adminOrderRepo.findAll(
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        Sort.by("id").descending())
        );
    }

    public AdminOrder getOrder(Long id) {
        return adminOrderRepo.findById(id).orElseThrow();
    }

    @Transactional
    public void updateOrderStatus(Long id, Map<String, String> values) {
        AdminOrder order = adminOrderRepo.findById(id).orElseThrow();
        patchValues(order, values);

    }

    private void patchValues(AdminOrder order, Map<String, String> values) {
        if (values.containsKey("orderStatus")) {
            order.setOrderStatus(AdminOrderStatus.valueOf(values.get("orderStatus")));
        }

    }
}
