package ru.pnzgu.crm.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pnzgu.crm.store.entity.DealProduct;

import java.util.List;
import java.util.Optional;

public interface DealProductRepository extends JpaRepository<DealProduct, Long> {
    List<DealProduct> findDealProductByDeal_Id(Long deal_id);

    Optional<DealProduct> findDealProductById(Long id);
}