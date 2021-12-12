package ru.pnzgu.crm.store.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "contact")
public class Contact extends ParentEntity {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Column(name = "phone", nullable = false, length = 12)
    private String phone;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @Column(name = "company")
    private String company;

    @Column(name = "post")
    private String post;

}