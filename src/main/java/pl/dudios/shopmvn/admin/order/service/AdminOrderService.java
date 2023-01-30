package pl.dudios.shopmvn.admin.order.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.dudios.shopmvn.admin.order.model.AdminOrder;
import pl.dudios.shopmvn.admin.order.repositor.AdminOrderRepo;


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
}
