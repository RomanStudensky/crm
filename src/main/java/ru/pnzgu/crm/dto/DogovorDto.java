package ru.pnzgu.crm.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DogovorDto extends ParentDto implements Serializable {
    private String title;
}
