package ru.pnzgu.crm.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pnzgu.crm.store.entity.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
}