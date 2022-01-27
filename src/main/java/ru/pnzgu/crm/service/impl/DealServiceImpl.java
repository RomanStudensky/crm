package ru.pnzgu.crm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pnzgu.crm.dto.DealDto;
import ru.pnzgu.crm.dto.ProductDto;
import ru.pnzgu.crm.exception.NotFoundException;
import ru.pnzgu.crm.exception.util.MessageConst;
import ru.pnzgu.crm.service.DealService;
import ru.pnzgu.crm.store.entity.Activity;
import ru.pnzgu.crm.store.entity.Deal;
import ru.pnzgu.crm.store.entity.DealProduct;
import ru.pnzgu.crm.store.entity.Dogovor;
import ru.pnzgu.crm.store.repository.ActivityRepository;
import ru.pnzgu.crm.store.repository.DealRepository;
import ru.pnzgu.crm.store.repository.DogovorRepository;
import ru.pnzgu.crm.store.states.ActivityState;
import ru.pnzgu.crm.store.states.DealState;
import ru.pnzgu.crm.util.mapping.Mappers;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {

    private final DealRepository dealRepository;
    private final DogovorRepository dogovorRepository;
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
    @Transactional
    @Override
    public DealDto create(DealDto dealDto, Long leadId, Long dogovorId) {
        Dogovor dogovor = dogovorRepository
                        .findById(dogovorId)
                        .orElseThrow(() -> new NotFoundException(String.format(MessageConst.DOGOVOR, dogovorId)));

        // создаём и сохраняем сделку
        Deal deal = Mappers.DEAL.mapDtoToEntity(dealDto);
        deal.setDogovor(dogovor);
        deal.setState(DealState.OPEN);
        dealDto = Mappers.DEAL.mapEntityToDto(dealRepository.saveAndFlush(deal));

        // находим последнюю активность у лида с идентификатором - leadId, иначе создаём новую активность
        Activity activity = activityRepository
                .findLastByLeadId(leadId)
                .stream()
                .findFirst()
                .orElse(null);

        if (activity == null || Objects.nonNull(activity.getDeal())) {
            // создаём и сохраняем активность
            activity = new Activity();
            activity.setTitle("Заключение сделки");
            activity.setState(ActivityState.END);
        }

        activity.setDeal(deal);
        activityRepository.saveAndFlush(activity);

        return dealDto;
    }

    @Override
    public DealDto update(Long id, DealDto dealDto) {
        Deal deal = getDealById(id);

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
                .findLeadByDealId(dealId)
                .stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException(String.format("Не удалось найти лид по сделке с идентификатором - %s ", dealId)));
    }

    @Transactional
    @Override
    public void approveDealById(Long id) {
        Deal deal = getDealById(id);

        deal.setState(DealState.APPROVE);
        dealRepository.save(deal);
    }

    @Transactional
    @Override
    public void closeDealById(Long id) {
        Deal deal = getDealById(id);

        deal.setState(DealState.CLOSE);
        dealRepository.save(deal);
    }

    private Deal getDealById(Long id) {
        return dealRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(MessageConst.DEAL, id)));
    }

}
