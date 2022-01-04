package ru.pnzgu.crm.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.pnzgu.crm.store.entity.Activity;

import java.util.List;
import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    @Query(nativeQuery = true,
            value = "select a.* " +
                    "from activity as a, lead as l " +
                    "where a.fk_id_lead = ?1 ")
    List<Activity> findByLeadId(Long leadId);

    @Query(nativeQuery = true,
            value = "select a.* " +
                    "from activity as a, lead as l " +
                    "where a.fk_id_lead = ?1 " +
                    "group by a.id " +
                    "having max()")
    Optional<Activity> findLastByLeadId(Long leadId);
}