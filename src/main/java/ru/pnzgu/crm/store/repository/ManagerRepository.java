package ru.pnzgu.crm.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pnzgu.crm.store.entity.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
}