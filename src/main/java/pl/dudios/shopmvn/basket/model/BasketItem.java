package pl.dudios.shopmvn.basket.model;

import lombok.*;
import pl.dudios.shopmvn.common.model.Product;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "basket_items")
public class BasketItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long quantity;
    @OneToOne
    private Product product;
    private Long basketId;

}
