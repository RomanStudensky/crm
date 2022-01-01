package ru.pnzgu.crm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pnzgu.crm.ActivityState;
import ru.pnzgu.crm.dto.DealDto;
import ru.pnzgu.crm.dto.ProductDto;
import ru.pnzgu.crm.exception.NotFoundException;
import ru.pnzgu.crm.exception.util.MessageConst;
import ru.pnzgu.crm.service.DealService;
import ru.pnzgu.crm.store.entity.*;
import ru.pnzgu.crm.store.repository.*;
import ru.pnzgu.crm.util.mapping.Mappers;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {

    private final DealRepository dealRepository;
    private final SostavLeadRepository sostavLeadRepository;
    private final DogovorRepository dogovorRepository;
    private final LeadRepository leadRepository;
    private final ActivityRepository activityRepository;

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


    /**
     * Сохраняет новую сделку и прикрепляет к последнему составу лида, если к нему не прикреплена сделка, иначе создаёт новый состав и активность и связывает их со сделкой
     * <br/>
     *
     * @param dealDto сделка
     * @param leadId лид
     * @param dogovorId идентификатор договора
     * @return сохраненная сделка
     */
    @Override
    @Transactional
    public DealDto create(DealDto dealDto, Long leadId, Long dogovorId) {

        Dogovor dogovor = dogovorRepository
                        .findById(dogovorId)
                        .orElseThrow(() -> new NotFoundException(String.format(MessageConst.DOGOVOR, dogovorId)));

        // создаём и сохраняем сделку
        Deal deal = Mappers.DEAL.mapDtoToEntity(dealDto);
        deal.setDogovor(dogovor);
        deal.setApproved(false);
        dealRepository.saveAndFlush(deal);
        //

        // считываем состав лида
        SostavLead sostavLead = sostavLeadRepository
                .findLastByLeadId(leadId)
                .orElseThrow(() -> new NotFoundException(String.format(MessageConst.SOSTAV_LEAD, dogovorId)));

        Activity activity;

        if (Objects.nonNull(sostavLead.getDeal())) {
            // создаём и сохраняем активность
            activity = new Activity();
            activity.setManager(sostavLead.getActivity().getManager());
            activity.setTitle("Заключение сделки");
            activity.setState(ActivityState.END.getLabel());
            activity = activityRepository.saveAndFlush(activity);

            Lead leadNow = sostavLead.getLead();


            sostavLead = new SostavLead();
            sostavLead.setLead(leadNow);
        } else {
            activity = sostavLead.getActivity();
        }

        sostavLead.setActivity(activity);
        sostavLead.setDeal(deal);

        dealDto = Mappers.DEAL.mapEntityToDto(sostavLeadRepository.save(sostavLead).getDeal());

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
