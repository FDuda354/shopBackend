package pl.dudios.shopmvn.admin.product.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import pl.dudios.shopmvn.admin.product.model.AdminProductCurrency;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class AdminProductDto {
    @NotBlank
    @Length(min = 3, max = 255)
    private String name;
    @NotBlank
    @Length(min = 3, max = 100)
    private String description;

    private String fullDescription;
    @NotNull
    private Long categoryId;
    @NotNull
    @Min(0)
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private AdminProductCurrency currency;
    private String image;
    @NotBlank
    @Length(min = 3, max = 255)
    private String slug;
}
