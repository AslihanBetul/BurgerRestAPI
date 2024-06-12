package org.burgerapp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.burgerapp.entity.enums.CookType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "tblproduct")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private CookType cookType;

    @ManyToMany
    private List<Item> removableItems = new ArrayList<>();
    @ManyToMany
    private List<Item> availableItems = new ArrayList<>(); //Eklenebilecek malzemeler
    @ManyToMany
    private List<Item> drinks = new ArrayList<>();
    @OneToOne
    private Category category;

    @Embedded
    private BaseEntity baseEntity;
    //String id; String ad; Double price; Map<String, Object> ozellikler; List<UrunSecenekler> secenekler;

}
