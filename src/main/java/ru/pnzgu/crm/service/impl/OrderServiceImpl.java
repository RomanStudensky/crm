package ru.pnzgu.crm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pnzgu.crm.dto.OrderDto;
import ru.pnzgu.crm.exception.NotFoundException;
import ru.pnzgu.crm.exception.util.MessageConst;
import ru.pnzgu.crm.service.OrderService;
import ru.pnzgu.crm.store.entity.Contact;
import ru.pnzgu.crm.store.entity.Lead;
import ru.pnzgu.crm.store.entity.Order;
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
                .map(Mappers.ORDER::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto read(Long id) {
        return Mappers
                .ORDER
                .mapEntityToDto(orderRepository
                        .findById(id)
                        .orElseThrow(() -> new NotFoundException(String.format(MessageConst.ORDER, id))));
    }

    @Override
    public List<OrderDto> readByContactId(Long id) {
        return orderRepository
                .findOrdersByContact_Id(id)
                .stream()
                .map(Mappers.ORDER::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderDto createOrderAndLead(OrderDto orderDto, Long contactId) {

        Contact contact = contactRepository
                .findById(contactId)
                .orElseThrow(() -> new NotFoundException(String.format(MessageConst.CONTACT, contactId)));

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

        Order order = Mappers.ORDER.mapDtoToEntity(orderDto);
        order.setLead(lead);
        order.setContact(contact);

        return Mappers
                .ORDER
                .mapEntityToDto(
                        orderRepository.save(order)
                );
    }

    @Override
    public OrderDto update(Long id, OrderDto orderDto) {
        read(id);

        Order order = Mappers.ORDER.mapDtoToEntity(orderDto);
        order.setId(id);

        orderDto = Mappers
                .ORDER
                .mapEntityToDto(
                        orderRepository.save(order)
                );

        orderDto.setContact(Mappers.CONTACT.mapEntityToDto(order.getContact()));

        return orderDto;
    }
}
