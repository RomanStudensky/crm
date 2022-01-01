package ru.pnzgu.crm.store.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "sostav_lead")
public class SostavLead extends ParentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_id_lead", nullable = false)
    private Lead lead;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_id_activity", nullable = false)
    private Activity activity;

    @ManyToOne
    @JoinColumn(name = "fk_id_deal")
    private Deal deal;
}