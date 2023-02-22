package pl.dudios.shopmvn.security.user.model;

import lombok.*;
import pl.dudios.shopmvn.common.model.Review;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "users")
public class AppUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private boolean enabled;
    @ElementCollection
    @CollectionTable(name = "authorities", joinColumns = @JoinColumn(name = "username", referencedColumnName = "username"))
    @Column(name = "authority")
    @Enumerated(EnumType.STRING)
    private List<Role> authorities;
    private String image;
    private String hash;
    private LocalDateTime HashDate;
    @OneToMany
    @JoinColumn(name = "userId")
    private List<Review> reviews;

}
