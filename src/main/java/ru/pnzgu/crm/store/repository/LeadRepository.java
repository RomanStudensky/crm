package ru.pnzgu.crm.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pnzgu.crm.store.entity.Lead;

public interface LeadRepository extends JpaRepository<Lead, Long> {
}