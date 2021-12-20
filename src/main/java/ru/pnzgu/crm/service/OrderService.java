package ru.pnzgu.crm.service;
import ru.pnzgu.crm.dto.ContactDto;
import ru.pnzgu.crm.dto.LeadDto;
import ru.pnzgu.crm.dto.OrderDto;

import java.util.List;

public interface OrderService {

    List<OrderDto> readAll();

    OrderDto read(Long id);

    List<OrderDto> readByContactId(Long id);

    OrderDto createOrderAndLead(OrderDto orderDto, Long contactId);

    OrderDto update(Long id, OrderDto orderDto);
}
