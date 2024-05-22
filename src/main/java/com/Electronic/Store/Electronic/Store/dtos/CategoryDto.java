package com.Electronic.Store.Electronic.Store.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CategoryDto {



    private String categoryId;

@NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description required")
private String description;
    @NotBlank(message = "coverImage is required")
    private String coverImage;
}
