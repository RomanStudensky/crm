package ru.pnzgu.crm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pnzgu.crm.dto.DealDto;
import ru.pnzgu.crm.dto.ProductDto;
import ru.pnzgu.crm.exception.NotFoundException;
import ru.pnzgu.crm.exception.util.MessageConst;
import ru.pnzgu.crm.service.DealService;
import ru.pnzgu.crm.store.entity.Deal;
import ru.pnzgu.crm.store.entity.DealProduct;
import ru.pnzgu.crm.store.entity.Dogovor;
import ru.pnzgu.crm.store.entity.SostavLead;
import ru.pnzgu.crm.store.repository.DealRepository;
import ru.pnzgu.crm.store.repository.DogovorRepository;
import ru.pnzgu.crm.store.repository.LeadRepository;
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
    private final LeadRepository leadRepository;

    @Override
    public List<DealDto> readAll() {
        return dealRepository
                .findAll()
                .stream()
                .map(Mappers.DEAL::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DealDto read(Long id) {
        return Mappers
                .DEAL
                .mapEntityToDto(dealRepository
                        .findById(id)
                        .orElseThrow(() -> new NotFoundException(String.format(MessageConst.DEAL, id))));
    }

    @Override
    public List<ProductDto> readAllProductByDealId(Long id) {
        return dealRepository
                .findAll()
                .stream()
                .filter(deal -> deal.getId().equals(id))
                .map(Deal::getSostav)
                .flatMap(Collection::stream)
                .map(DealProduct::getProduct)
                .map(Mappers.PRODUCT::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DealDto create(DealDto dealDto, Long leadId, Long dogovorId) {
        Dogovor dogovor = dogovorRepository
                        .findById(dogovorId)
                        .orElseThrow(() -> new NotFoundException(String.format(MessageConst.DOGOVOR, dogovorId)));

        Deal deal = Mappers.DEAL.mapDtoToEntity(dealDto);
        deal.setDogovor(dogovor);
        dealDto = Mappers.DEAL.mapEntityToDto(dealRepository.save(deal));

        SostavLead sostavLead = sostavLeadRepository
                .findByLead_Id(leadId)
                .orElseThrow(() -> new NotFoundException(String.format(MessageConst.SOSTAV_LEAD, dogovorId)));
        sostavLead.setDeal(deal);

        sostavLeadRepository.save(sostavLead);

        return dealDto;
    }

    @Override
    public DealDto update(Long id, DealDto dealDto) {
        Deal deal =
                dealRepository
                        .findById(id)
                        .orElseThrow(() -> new NotFoundException(String.format(MessageConst.DEAL, id)));

        deal.setTitle(dealDto.getTitle());

        return Mappers.DEAL.mapEntityToDto(dealRepository.save(deal));
    }

    @Override
    public List<DealDto> readAllByLeadId(Long leadId) {
        return dealRepository
                .findAllByLeadId(leadId)
                .stream()
                .map(Mappers.DEAL::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Long readLeadIdByDealId(Long dealId) {
        return dealRepository
                .findAllByDealId(dealId)
                .stream().findFirst().orElseThrow(() -> new NotFoundException(String.format(MessageConst.DEAL, dealId)));
    }
}
