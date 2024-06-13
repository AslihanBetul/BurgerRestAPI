package org.burgerapp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Document
public class Adress {

    private String id;
    private String userId;
    private String name;
    private String no;
    private String street;
    private String city;


}
