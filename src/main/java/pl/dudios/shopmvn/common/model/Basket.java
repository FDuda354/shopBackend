package pl.dudios.shopmvn.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
