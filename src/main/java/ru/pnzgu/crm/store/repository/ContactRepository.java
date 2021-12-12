package ru.pnzgu.crm.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pnzgu.crm.store.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}