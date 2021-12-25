package ru.pnzgu.crm.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pnzgu.crm.store.entity.Dogovor;

public interface DogovorRepository extends JpaRepository<Dogovor, Long> {
}