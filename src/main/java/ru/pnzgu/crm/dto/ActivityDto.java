package ru.pnzgu.crm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import ru.pnzgu.crm.store.states.ActivityState;
import ru.pnzgu.crm.util.mapping.DateOptions;
import ru.pnzgu.crm.util.mapping.TimeOptions;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActivityDto extends ParentDto implements Serializable {

    private String title;
    private String description;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateOptions.PATTERN)
    private LocalDate date;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TimeOptions.PATTERN)
    private LocalTime time;
    private ActivityState state;
    private LeadDto lead;
    private ManagerDto manager;
    private DealDto deal;
}
