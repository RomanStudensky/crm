package ru.pnzgu.crm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import ru.pnzgu.crm.util.mapping.DateOptions;

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
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateOptions.PATTERN)
    private LocalDate birthday;
    private String company;
    private String post;

    public String getFullName() {
        return String.format("%s %s %s", surname, name, (lastname == null ? "" : lastname));
    }
}
