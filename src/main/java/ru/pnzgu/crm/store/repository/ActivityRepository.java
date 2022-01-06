package ru.pnzgu.crm.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.pnzgu.crm.store.entity.Activity;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    List<Activity> findActivitiesByLeadId(Long leadId);

    @Query(nativeQuery = true,
            value = "select a.* " +
                    "from activity as a " +
                    "where a.id = ( " +
                    "   select max(a.id) " +
                    "   from activity as a " +
                    "   where a.fk_id_lead = ?1 " +
                    ")")
    List<Activity> findLastByLeadId(Long leadId);
}