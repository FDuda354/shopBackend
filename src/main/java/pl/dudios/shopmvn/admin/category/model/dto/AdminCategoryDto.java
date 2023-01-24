package pl.dudios.shopmvn.admin.category.model.dto;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
public class AdminCategoryDto {

    @NotBlank
    @Length(min = 3, max = 255)
    private String name;
    private String description;
    @NotBlank
    @Length(min = 3, max = 255)
    private String slug;

}
