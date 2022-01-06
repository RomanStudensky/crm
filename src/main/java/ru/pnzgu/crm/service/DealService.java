package ru.pnzgu.crm.service;

import ru.pnzgu.crm.dto.DealDto;
import ru.pnzgu.crm.dto.ProductDto;

import java.util.List;

public interface DealService {
    List<DealDto> readAll();

    DealDto read(Long id);

    List<ProductDto> readAllProductByDealId(Long id);

    DealDto create(DealDto dealDto, Long leadId, Long dogovorId);

    DealDto update(Long id, DealDto dealDto);

    List<DealDto> readAllByLeadId(Long leadId);

    Long readLeadIdByDealId(Long dealdId);

    void approveDealById(Long id);

    void closeDealById(Long id);
}
