package ru.pnzgu.crm.service;
import ru.pnzgu.crm.dto.LeadDto;
import ru.pnzgu.crm.dto.OrderDto;

import java.util.List;

public interface LeadService {

    List<LeadDto> readAll();

    LeadDto read(Long id);

    LeadDto update(Long id, LeadDto orderDto);
}
