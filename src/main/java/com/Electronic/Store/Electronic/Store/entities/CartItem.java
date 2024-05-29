package com.Electronic.Store.Electronic.Store.entities;

import com.Electronic.Store.Electronic.Store.services.ProductService;
import jakarta.persistence.*;
import lombok.*;
import org.modelmapper.internal.bytebuddy.dynamic.loading.InjectionClassLoader;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.data.domain.Pageable;

import java.lang.annotation.Target;
@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartItemId;

   @OneToOne
   @JoinColumn(name ="product_id" )
    private Product product;
   private int quantity;
   private int totalprice;

   //mapping cart
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;
}

