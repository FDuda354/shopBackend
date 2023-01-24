package pl.dudios.shopmvn.basket.model;

import lombok.Getter;
import lombok.Setter;
import pl.dudios.shopmvn.common.model.Product;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "basket_items")
public class BasketItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long quantity;

    private Product product;

}
