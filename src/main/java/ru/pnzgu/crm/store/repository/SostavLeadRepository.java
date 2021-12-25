package ru.pnzgu.crm.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.pnzgu.crm.store.entity.SostavLead;

import java.util.Optional;

public interface SostavLeadRepository extends JpaRepository<SostavLead, Long> {
    @Query(nativeQuery = true,
            value = "select l.* " +
                    "from sostav_lead as s, lead as l, activity as a " +
                    "where l.id = ?1 and s.fk_id_lead = l.id " +
                    "group by a.id " +
                    "having max()")
    Optional<SostavLead> findByLead_Id(Long leadId);
}