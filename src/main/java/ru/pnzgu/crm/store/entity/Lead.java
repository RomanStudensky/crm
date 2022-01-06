package ru.pnzgu.crm.store.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.pnzgu.crm.store.states.LeadState;

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
    @Enumerated(EnumType.STRING)
    private LeadState state;

    @Column(name = "title", nullable = false)
    private String title;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_id_orders")
    private Order orders;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_lead")
    List<Activity> activities = new ArrayList<>();
}