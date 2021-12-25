package ru.pnzgu.crm.service;

import ru.pnzgu.crm.dto.DealSostavDto;

import java.util.List;

public interface DealSostavService{
    List<DealSostavDto> readAll();

    DealSostavDto read(Long id);

    List<DealSostavDto> readAllByDealId(Long dealId);

    DealSostavDto create(DealSostavDto dealProductDto, Long dealId, Long productId);

    DealSostavDto update(Long id, DealSostavDto dealProductDto);

    void delete(Long id);

    Long findDealIdBySostavId(Long id);
}
