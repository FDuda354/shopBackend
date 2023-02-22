package pl.dudios.shopmvn.order.model;

import lombok.Getter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter

@Entity
@Table(name = "shipments")
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private ShipmentType type;
    private boolean defaultShipment;

}
