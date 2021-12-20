package ru.pnzgu.crm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pnzgu.crm.dto.OrderDto;
import ru.pnzgu.crm.exception.util.ExMes;
import ru.pnzgu.crm.exception.NotFoundException;
import ru.pnzgu.crm.service.OrderService;
import ru.pnzgu.crm.store.entity.*;
import ru.pnzgu.crm.store.repository.ContactRepository;
import ru.pnzgu.crm.store.repository.LeadRepository;
import ru.pnzgu.crm.store.repository.OrderRepository;
import ru.pnzgu.crm.util.mapping.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ContactRepository contactRepository;
    private final LeadRepository leadRepository;

    @Override
    public List<OrderDto> readAll() {
        return orderRepository
                .findAll()
                .stream()
                .map(Mappers.ORDER_MAPPER::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto read(Long id) {
        return Mappers
                .ORDER_MAPPER
                .mapEntityToDto(orderRepository
                        .findById(id)
                        .orElseThrow(() -> new NotFoundException(String.format(ExMes.ORDER_MESSAGE, id))));
    }

    @Override
    public List<OrderDto> readByContactId(Long id) {
        return orderRepository
                .findOrdersByContact_Id(id)
                .stream()
                .map(Mappers.ORDER_MAPPER::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderDto createOrderAndLead(OrderDto orderDto, Long contactId) {

        Contact contact = contactRepository
                .findById(contactId)
                .orElseThrow(() -> new NotFoundException(String.format(ExMes.CONTACT_MESSAGE, contactId)));

        Lead lead = new Lead();
        lead.setTitle(
                String.format("%s %s %s. %s",
                        contact.getSurname(),
                        contact.getName(),
                        contact.getLastname(),
                        orderDto.getOrigin())
        );

        lead.setState("Создан");
        lead = leadRepository.save(lead);

        Order order = Mappers.ORDER_MAPPER.mapDtoToEntity(orderDto);
        order.setLead(lead);
        order.setContact(contact);

        return Mappers
                .ORDER_MAPPER
                .mapEntityToDto(
                        orderRepository.save(order)
                );
    }

    @Override
    public OrderDto update(Long id, OrderDto orderDto) {
        read(id);

        Order order = Mappers.ORDER_MAPPER.mapDtoToEntity(orderDto);
        order.setId(id);

        orderDto = Mappers
                .ORDER_MAPPER
                .mapEntityToDto(
                        orderRepository.save(order)
                );

        orderDto.setContact(Mappers.CONTACT_MAPPER.mapEntityToDto(order.getContact()));

        return orderDto;
    }
}
