package ru.pnzgu.crm.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pnzgu.crm.store.entity.Deal;
import ru.pnzgu.crm.store.entity.Product;

import java.util.List;

public interface DealRepository extends JpaRepository<Deal, Long> {
}