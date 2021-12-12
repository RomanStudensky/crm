package ru.pnzgu.crm.dto;

import lombok.*;
import ru.pnzgu.crm.dto.DogovorDto;
import ru.pnzgu.crm.dto.ProductDto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DealDto extends ParentDto implements Serializable {
    private String title;
    private List<ProductDto> products = new ArrayList<>();
    private DogovorDto dogovor;
}
