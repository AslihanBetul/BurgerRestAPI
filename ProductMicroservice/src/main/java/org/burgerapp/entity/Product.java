package org.burgerapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany
    private List<Item> removableItems = new ArrayList<>();
    @ManyToMany
    private List<Item> availableItems = new ArrayList<>(); //Eklenebilecek malzemeler
    @ManyToMany
    private List<Item> drinks = new ArrayList<>();
    @OneToOne
    private Category category;


}
