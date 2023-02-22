package pl.dudios.shopmvn.common.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "baskets")
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime created;
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "basketId")
    private List<BasketItem> items;

    //TODO: Upgrade this 1

    public Basket addProduct(BasketItem item) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.stream()
                .filter(basketItem -> Objects.equals(basketItem.getProduct().getId(), item.getProduct().getId()))
                .findFirst()
                .ifPresentOrElse(basketItem ->
                                basketItem.setQuantity(basketItem.getQuantity() + 1),
                        () -> items.add(item));

        return this;
    }
}
