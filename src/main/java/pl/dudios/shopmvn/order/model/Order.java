package pl.dudios.shopmvn.order.model;

import lombok.*;
import pl.dudios.shopmvn.common.model.OrderStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime placeDate;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @OneToMany
    @JoinColumn(name = "orderId")
    private List<OrderRow> orderRows;
    private BigDecimal grossValue;
    private String firstName;
    private String lastName;
    private String street;
    private String city;
    private String zipCode;
    private String email;
    private String phone;
    @OneToOne
    private Payment payment;
    private Long userId;
    private String orderHash;
}
