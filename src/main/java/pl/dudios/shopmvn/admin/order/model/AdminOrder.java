package pl.dudios.shopmvn.admin.order.model;

import lombok.Getter;
import lombok.Setter;
import pl.dudios.shopmvn.admin.order.model.log.AdminOrderLog;
import pl.dudios.shopmvn.common.model.OrderStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter

@Entity
@Table(name = "orders")
public class AdminOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime placeDate;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @OneToMany
    @JoinColumn(name = "orderId")
    private List<AdminOrderRow> orderRows;
    private BigDecimal grossValue;
    private String firstName;
    private String lastName;
    private String street;
    private String zipCode;
    private String city;
    private String email;
    private String phone;
    @OneToOne
    private AdminPayment payment;
    @OneToMany
    @JoinColumn(name = "orderId")
    private List<AdminOrderLog> logs;
}
