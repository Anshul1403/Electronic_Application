package com.Electronic.Store.Electronic.Store.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();


}
