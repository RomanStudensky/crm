package ru.pnzgu.crm.service;

import ru.pnzgu.crm.dto.ManagerDto;

import java.util.List;

public interface ManagerService {

    List<ManagerDto> readAll();

    ManagerDto read(Long id);

    ManagerDto create(ManagerDto managerDto);

    ManagerDto update(Long id, ManagerDto managerDto);
}
