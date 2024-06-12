package org.burgerapp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.burgerapp.entity.enums.CookType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "tblproduct")
public class Product extends BaseEntity {
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

    //String id; String ad; Double price; Map<String, Object> ozellikler; List<UrunSecenekler> secenekler;

}
