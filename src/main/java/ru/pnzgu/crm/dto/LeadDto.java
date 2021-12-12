package ru.pnzgu.crm.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LeadDto extends ParentDto implements Serializable {
    private String state;
    private String title;
    private OrderDto order;
}
