package ru.pnzgu.crm.service;

import ru.pnzgu.crm.dto.DealDto;

import java.util.List;

public interface DealService {
    List<DealDto> readAll();

    DealDto read(Long id);

    DealDto create(DealDto dealDto, Long idSostavLead, Long idDogovor);

    DealDto update(Long id, DealDto dealDto);
}
