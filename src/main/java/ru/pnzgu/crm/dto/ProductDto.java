package ru.pnzgu.crm.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto extends ParentDto implements Serializable {
    private String name;
    private Double price;
    private String description;
}
