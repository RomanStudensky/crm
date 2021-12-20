package ru.pnzgu.crm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pnzgu.crm.dto.DealDto;
import ru.pnzgu.crm.dto.ProductDto;
import ru.pnzgu.crm.exception.util.ExMes;
import ru.pnzgu.crm.exception.NotFoundException;
import ru.pnzgu.crm.service.DealService;
import ru.pnzgu.crm.store.entity.*;
import ru.pnzgu.crm.store.repository.DealRepository;
import ru.pnzgu.crm.store.repository.DogovorRepository;
import ru.pnzgu.crm.store.repository.SostavLeadRepository;
import ru.pnzgu.crm.util.mapping.Mappers;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {

    private final DealRepository dealRepository;
    private final SostavLeadRepository sostavLeadRepository;
    private final DogovorRepository dogovorRepository;

    @Override
    public List<DealDto> readAll() {
        return dealRepository
                .findAll()
                .stream()
                .map(Mappers.DEAL_MAPPER::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DealDto read(Long id) {
        return Mappers
                .DEAL_MAPPER
                .mapEntityToDto(dealRepository
                        .findById(id)
                        .orElseThrow(() -> new NotFoundException(String.format(ExMes.DEAL_MESSAGE, id))));
    }

    @Override
    public List<ProductDto> readAllProductByDealId(Long id) {
        return dealRepository
                .findAll()
                .stream()
                .filter(deal -> deal.getId().equals(id))
                .map(Deal::getProducts)
                .flatMap(Collection::stream)
                .map(Mappers.PRODUCT_MAPPER::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DealDto create(DealDto dealDto, Long idSostavLead, Long idDogovor) {
        SostavLead lead =
                sostavLeadRepository
                        .findById(idSostavLead)
                        .orElseThrow(() -> new NotFoundException(String.format(ExMes.SOSTAV_LEAD_MESSAGE, idSostavLead)));

        Dogovor dogovor =
                dogovorRepository
                        .findById(idSostavLead)
                        .orElseThrow(() -> new NotFoundException(String.format(ExMes.DOGOVOR_MESSAGE, idDogovor)));

        Deal deal = Mappers.DEAL_MAPPER.mapDtoToEntity(dealDto);
        deal.setDogovor(dogovor);

        lead.setDeal(deal);

        sostavLeadRepository.save(lead);

        return Mappers
                .DEAL_MAPPER
                .mapEntityToDto(
                        dealRepository.save(deal)
                );
    }

    @Override
    public DealDto update(Long id, DealDto dealDto) {
        Deal deal =
                dealRepository
                        .findById(id)
                        .orElseThrow(() -> new NotFoundException(String.format(ExMes.DEAL_MESSAGE, id)));

        deal.setTitle(dealDto.getTitle());

        return Mappers.DEAL_MAPPER.mapEntityToDto(dealRepository.save(deal));
    }
}
