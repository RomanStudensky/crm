package ru.pnzgu.crm.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ManagerDto extends ParentDto implements Serializable {
    private String name;
    private String lastname;
    private String surname;
    private String phone;
}
