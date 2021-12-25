package ru.pnzgu.crm.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.pnzgu.crm.store.entity.Deal;

import java.util.List;
import java.util.Optional;

public interface DealRepository extends JpaRepository<Deal, Long> {
    @Query(nativeQuery = true,
            value = "select * from deal " +
                    "join sostav_lead sl " +
                    "on deal.id = sl.fk_id_deal and sl.fk_id_lead = ?1")
    List<Deal> findAllByLeadId(Long leadId);

    @Query(nativeQuery = true,
            value = "select id from lead " +
                    "join sostav_lead sl " +
                    "on sl.fk_id_deal = ?1")
    Optional<Long> findAllByDealId(Long dealId);
}