package ru.pnzgu.crm.dto;

import lombok.*;
import ru.pnzgu.crm.dto.ActivityDto;
import ru.pnzgu.crm.dto.DealDto;
import ru.pnzgu.crm.dto.LeadDto;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SostavLeadDto extends ParentDto implements Serializable {
    private LeadDto lead;
    private ActivityDto activity;
    private DealDto deal;
}
