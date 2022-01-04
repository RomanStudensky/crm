package ru.pnzgu.crm.store.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.pnzgu.crm.ActivityState;

import javax.persistence.*;
import java.time.LocalDate;


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
    private LocalDate time;

    @Column(name = "state", nullable = false)
    @Enumerated
    private ActivityState state;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_id_manager", nullable = false)
    private Manager manager;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_id_lead", nullable = false)
    private Lead lead;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_id_deal", referencedColumnName = "id")
    private Deal deal;
}