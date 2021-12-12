package ru.pnzgu.crm.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pnzgu.crm.store.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}