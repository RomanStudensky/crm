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
public class DealDto extends ParentDto implements Serializable {
    private String title;
    private List<ProductDto> products = new ArrayList<>();
    private DogovorDto dogovor;
    private List<DealSostavDto> sostav = new ArrayList<>();
}
