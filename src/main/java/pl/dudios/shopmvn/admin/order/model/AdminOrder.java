package pl.dudios.shopmvn.admin.order.model;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Table(name = "orders")
public class AdminOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime placeDate;
    @Enumerated(EnumType.STRING)
    private AdminOrderStatus orderStatus;
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
}