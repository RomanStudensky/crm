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
public class LeadDto extends ParentDto implements Serializable {
    private String state;
    private String title;
    private OrderDto orders;
    private List<ActivityDto> sostavLead = new ArrayList<>();
}
