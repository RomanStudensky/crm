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
@Table(name = "lead")
public class Lead extends ParentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "title", nullable = false)
    private String title;

    @OneToOne(mappedBy="lead")
    private Order orders;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_lead")
    List<SostavLead> sostavLead = new ArrayList<>();


}