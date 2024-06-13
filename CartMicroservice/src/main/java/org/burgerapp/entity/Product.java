package org.burgerapp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Document
public class Product {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Object cookType;


    private List<Object> removableItems = new ArrayList<>();

    private List<Object> availableItems = new ArrayList<>(); //Eklenebilecek malzemeler

    private List<Object> drinks = new ArrayList<>();

    private Object category;


    private Object baseEntity;


}
