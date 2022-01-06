package ru.pnzgu.crm.store.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.pnzgu.crm.store.states.ActivityState;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;


@Entity
@Table(name = "activity")
@NoArgsConstructor
@Getter
@Setter
public class Activity extends ParentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "time", nullable = false)
    private LocalTime time;

    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    private ActivityState state;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_id_manager", nullable = false)
    private Manager manager;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_id_lead", nullable = false)
    private Lead lead;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_id_deal", referencedColumnName = "id")
    private Deal deal;
}