package ru.pnzgu.crm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public String getFullName() {
        return String.format("%s %s %s", surname, name, lastname);
    }
}
