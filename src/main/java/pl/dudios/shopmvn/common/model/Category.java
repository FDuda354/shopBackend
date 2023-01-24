package pl.dudios.shopmvn.common.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String slug;
//    @OneToMany
//    @JoinColumn(name = "categoryId")
//    private List<Product> products;
}
