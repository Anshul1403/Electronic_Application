package com.Electronic.Store.Electronic.Store.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="category")
public class Category {

    @Id
    @Column(name="id")
    private String categoryId;
    @Column(name="category_id" ,length=60,nullable = false)
    private String title;
    @Column(name="category_desc" ,length=50)
    private String description;
    private String coverImage;
}
