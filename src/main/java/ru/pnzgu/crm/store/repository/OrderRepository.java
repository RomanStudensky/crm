package ru.pnzgu.crm.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pnzgu.crm.store.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}