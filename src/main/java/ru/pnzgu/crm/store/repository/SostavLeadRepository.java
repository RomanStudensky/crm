package ru.pnzgu.crm.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pnzgu.crm.store.entity.SostavLead;

public interface SostavLeadRepository extends JpaRepository<SostavLead, Long> {
}