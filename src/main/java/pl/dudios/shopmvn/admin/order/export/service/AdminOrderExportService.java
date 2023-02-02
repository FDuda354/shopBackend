package pl.dudios.shopmvn.admin.order.export.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.dudios.shopmvn.admin.order.model.AdminOrder;
import pl.dudios.shopmvn.admin.order.model.AdminOrderStatus;
import pl.dudios.shopmvn.admin.order.repository.AdminOrderRepo;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class AdminOrderExportService {

    private final AdminOrderRepo adminOrderRepo;

    public List<AdminOrder> exportOrders(LocalDateTime from, LocalDateTime to, AdminOrderStatus orderStatus) {
        return adminOrderRepo.findAllByPlaceDateIsBetweenAndOrderStatus(from, to, orderStatus);
    }
}
