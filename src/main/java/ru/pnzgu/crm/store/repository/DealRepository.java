package ru.pnzgu.crm.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.pnzgu.crm.store.entity.Deal;

import java.util.List;
import java.util.Optional;

public interface DealRepository extends JpaRepository<Deal, Long> {
    @Query(nativeQuery = true,
            value = "select * from deal " +
                    "join activity a " +
                    "on deal.id = a.fk_id_deal and a.fk_id_lead = ?1")
    List<Deal> findAllByLeadId(Long leadId);

    @Query(nativeQuery = true,
            value = "select l.id " +
                    "from lead as l " +
                    "join activity a " +
                    "on a.fk_id_deal = ?1 and l.id = a.fk_id_lead")
    Optional<Long> findLeadByDealId(Long dealId);
}