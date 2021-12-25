package ru.pnzgu.crm.store.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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

    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "deal")
    private List<DealProduct> sostav = new ArrayList<>();

    @ManyToOne(optional = false, cascade = { CascadeType.ALL })
    @JoinColumn(name = "fk_id_dogovor", nullable = false)
    private Dogovor dogovor;

    @ManyToOne
    @JoinColumn(name = "fk_id_sostav_lead")
    private SostavLead sostavLead;
}