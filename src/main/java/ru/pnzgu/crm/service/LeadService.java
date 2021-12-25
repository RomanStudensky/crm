package ru.pnzgu.crm.service;

import ru.pnzgu.crm.dto.LeadDto;

import java.util.List;

public interface LeadService {

    List<LeadDto> readAll();

    LeadDto read(Long id);

    LeadDto update(Long id, LeadDto leadDto);
}
