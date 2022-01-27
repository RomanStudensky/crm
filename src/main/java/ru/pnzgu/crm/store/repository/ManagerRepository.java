package ru.pnzgu.crm.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.pnzgu.crm.store.entity.Activity;
import ru.pnzgu.crm.store.entity.Manager;

import java.util.List;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
    @Query("select m.activities from Manager m where m.id = ?1")
    List<Activity> findActivitiesById(Long id);
}