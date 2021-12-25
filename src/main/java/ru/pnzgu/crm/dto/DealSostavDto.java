package ru.pnzgu.crm.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DealSostavDto extends ParentDto{
    private ProductDto product;
    private DealDto deal;
    private Integer count;
}
