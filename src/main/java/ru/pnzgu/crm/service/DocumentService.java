package ru.pnzgu.crm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pnzgu.crm.dto.DateWrapperDto;
import ru.pnzgu.crm.dto.OrderDto;
import ru.pnzgu.crm.exception.DocumentExportException;
import ru.pnzgu.crm.store.repository.OrderRepository;
import ru.pnzgu.crm.util.excel.ExcelExportUtil;
import ru.pnzgu.crm.util.mapping.Mappers;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final OrderRepository orderRepository;

    public byte[] getOrdersDocument(DateWrapperDto dateWrapperDto) throws DocumentExportException {

        List<OrderDto> orderList = orderRepository
                .findByDateBetween(dateWrapperDto.getBeginDate(), dateWrapperDto.getEndDate())
                .stream()
                .map(Mappers.ORDER::mapEntityToDto)
                .collect(Collectors.toList());

        try {
            return ExcelExportUtil.createPostavExelDocument(orderList).toByteArray();
        } catch (IOException e) {
            throw new DocumentExportException("Не удалось сформировать отчёт по поставщику, повторите попытку");
        }
    }
}
