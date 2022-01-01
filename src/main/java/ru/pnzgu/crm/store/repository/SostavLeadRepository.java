package ru.pnzgu.crm.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.pnzgu.crm.store.entity.SostavLead;

import java.util.List;
import java.util.Optional;

public interface SostavLeadRepository extends JpaRepository<SostavLead, Long> {
    @Query(nativeQuery = true,
            value = "select s.* " +
                    "from sostav_lead as s, lead as l " +
                    "where s.fk_id_lead = ?1 ")
    List<SostavLead> findByLeadId(Long leadId);

    @Query(nativeQuery = true,
            value = "select s.* " +
                    "from sostav_lead as s, lead as l " +
                    "where s.fk_id_lead = ?1 " +
                    "group by s.id " +
                    "having max()")
    Optional<SostavLead> findLastByLeadId(Long leadId);
}