package ru.pnzgu.crm.store.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.pnzgu.crm.store.states.DealState;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "deal")
public class Deal extends ParentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    private DealState state;

    @Column(name = "date_execute", nullable = false)
    private LocalDate date;

    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, mappedBy = "deal")
    private List<DealProduct> sostav = new ArrayList<>();

    @ManyToOne(optional = false, cascade = { CascadeType.ALL })
    @JoinColumn(name = "fk_id_dogovor", nullable = false)
    private Dogovor dogovor;

    @OneToOne(mappedBy = "deal")
    private Activity activity;
}