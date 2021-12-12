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
@Table(name = "orders")
public class Order extends ParentEntity {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "origin")
    private String origin;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_id_contact", nullable = false)
    private Contact fkIdContact;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_id_lead")
    private Lead lead;
}