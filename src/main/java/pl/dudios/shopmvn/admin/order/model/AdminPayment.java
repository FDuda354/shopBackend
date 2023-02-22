package pl.dudios.shopmvn.admin.order.model;

import lombok.Getter;
import pl.dudios.shopmvn.order.model.PaymentType;

import javax.persistence.*;

@Entity
@Table(name = "payments")
@Getter
public class AdminPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private PaymentType type;
    private boolean defaultPayment;
    private String note;
}
