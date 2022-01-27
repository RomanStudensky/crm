package ru.pnzgu.crm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pnzgu.crm.dto.*;
import ru.pnzgu.crm.exception.DocumentExportException;
import ru.pnzgu.crm.exception.NotFoundException;
import ru.pnzgu.crm.exception.util.MessageConst;
import ru.pnzgu.crm.service.DocumentService;
import ru.pnzgu.crm.store.repository.DealRepository;
import ru.pnzgu.crm.store.repository.ManagerRepository;
import ru.pnzgu.crm.store.repository.OrderRepository;
import ru.pnzgu.crm.util.excel.ExcelExportUtil;
import ru.pnzgu.crm.util.mapping.Mappers;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DocumentServiceImpl implements DocumentService {

    private final OrderRepository orderRepository;
    private final ManagerRepository managerRepository;
    private final DealRepository dealRepository;

    public byte[] getOrdersDocument(@NonNull DateWrapperDto dateWrapperDto) throws DocumentExportException {

        List<OrderDto> orderList = orderRepository
                .findByDateBetween(dateWrapperDto.getBeginDate(), dateWrapperDto.getEndDate())
                .stream()
                .map(Mappers.ORDER::mapEntityToDto)
                .collect(Collectors.toList());

        try {
            return ExcelExportUtil.createAndGetOrdersExelDocument(orderList, dateWrapperDto).toByteArray();
        } catch (IOException e) {
            throw new DocumentExportException("Не удалось сформировать отчёт по заявкам, повторите попытку");
        }
    }

    public byte[] getManagerDocument(@NonNull ManagerDto managerDto) throws DocumentExportException {

        Long id = managerDto.getId();

        managerDto = Mappers.MANAGER
                .mapEntityToDto(managerRepository
                        .findById(managerDto.getId())
                        .orElseThrow(() -> new NotFoundException(String.format(MessageConst.MANAGER, id)))
                );

        List<ActivityDto> activityList = managerRepository
                .findActivitiesById(id)
                .stream()
                .map(Mappers.ACTIVITY::mapEntityToDto)
                .collect(Collectors.toList());

        try {
            return ExcelExportUtil.createAndGetManagerExelDocument(managerDto, activityList).toByteArray();
        } catch (IOException e) {
            throw new DocumentExportException("Не удалось сформировать отчёт по менеджеру, повторите попытку");
        }
    }

    @Override
    public byte[] getDealDocument(DateWrapperDto dateWrapperDto) throws DocumentExportException {
        List<DealDto> dealList = dealRepository
                .findByDateBetween(dateWrapperDto.getBeginDate(), dateWrapperDto.getEndDate())
                .stream()
                .map(Mappers.DEAL::mapEntityToDto)
                .collect(Collectors.toList());

        try {
            return ExcelExportUtil.createAndGetDealExelDocument(dealList, dateWrapperDto).toByteArray();
        } catch (IOException e) {
            throw new DocumentExportException("Не удалось сформировать отчёт по сделкам, повторите попытку");
        }
    }
}
