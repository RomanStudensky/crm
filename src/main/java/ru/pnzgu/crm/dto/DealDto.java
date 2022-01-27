package ru.pnzgu.crm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.pnzgu.crm.store.states.DealState;

import java.io.Serializable;
import java.time.LocalDate;
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
    private DealState state;
    private LocalDate date;
    private List<DealSostavDto> sostav = new ArrayList<>();
}
