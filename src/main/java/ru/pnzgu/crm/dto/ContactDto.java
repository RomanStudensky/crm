package ru.pnzgu.crm.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactDto extends ParentDto implements Serializable {
    private String name;
    private String surname;
    private String lastname;
    private String phone;
    private String address;
    private LocalDate birthday;
    private String company;
    private String post;
}
