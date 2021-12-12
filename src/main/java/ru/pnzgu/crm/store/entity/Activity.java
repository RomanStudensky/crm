package ru.pnzgu.crm.store.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "activity")
public class Activity extends ParentEntity {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "state", nullable = false)
    private String state;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_id_manager", nullable = false)
    private Manager manager;

    @ManyToOne
    @JoinColumn(name = "fk_id_sostav_lead")
    private SostavLead sostavLead;

}