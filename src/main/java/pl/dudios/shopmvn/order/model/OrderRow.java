package pl.dudios.shopmvn.order.model;


import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "order_rows")
public class OrderRow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;
    private Long productId;
    private Long quantity;
    private BigDecimal price;
    private Long shipmentId;
}
