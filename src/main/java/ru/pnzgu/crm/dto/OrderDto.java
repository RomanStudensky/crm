package ru.pnzgu.crm.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto extends ParentDto implements Serializable {
    private String origin;
    private LocalDate date;
    private ContactDto fkIdContact;
    private LeadDto lead;
}
