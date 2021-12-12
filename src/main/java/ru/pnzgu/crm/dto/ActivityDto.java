package ru.pnzgu.crm.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActivityDto extends ParentDto implements Serializable {
    private String title;
    private String description;
    private LocalDate date;
    private String state;
    private ManagerDto manager;
}
