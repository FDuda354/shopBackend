package pl.dudios.shopmvn.admin.order.model;

import lombok.Getter;
import pl.dudios.shopmvn.admin.product.model.AdminProduct;
import pl.dudios.shopmvn.order.model.Shipment;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_rows")
@Getter
public class AdminOrderRow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;
    @OneToOne
    @JoinColumn(name = "productId")
    private AdminProduct product;
    private int quantity;
    private BigDecimal price;
    @OneToOne
    @JoinColumn(name = "shipmentId")
    private Shipment shipment;
}
