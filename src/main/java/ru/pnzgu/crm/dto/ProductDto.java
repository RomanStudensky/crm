package ru.pnzgu.crm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto extends ParentDto implements Serializable {
    private String name;
    private Double price;
    private String description;
    private List<DealSostavDto> sostav = new ArrayList<>();
}
