package ru.pnzgu.crm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pnzgu.crm.dto.ContactDto;
import ru.pnzgu.crm.exception.NotFoundException;
import ru.pnzgu.crm.exception.util.MessageConst;
import ru.pnzgu.crm.service.ContactService;
import ru.pnzgu.crm.store.entity.Contact;
import ru.pnzgu.crm.store.repository.ContactRepository;
import ru.pnzgu.crm.util.mapping.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    @Override
    public List<ContactDto> readAll() {
        return contactRepository
                .findAll()
                .stream()
                .map(Mappers.CONTACT::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ContactDto read(Long id) {
        return Mappers
                .CONTACT
                .mapEntityToDto(
                        contactRepository
                        .findById(id)
                        .orElseThrow(() -> new NotFoundException(String.format(MessageConst.CONTACT, id))));
    }

    @Override
    public ContactDto create(ContactDto contactDto) {
        return Mappers
                .CONTACT
                .mapEntityToDto(
                        contactRepository
                                .save(Mappers
                                        .CONTACT
                                        .mapDtoToEntity(contactDto))
                );
    }

    @Override
    public ContactDto update(Long id, ContactDto contactDto) {
        contactRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(MessageConst.CONTACT, id)));

        Contact contact = Mappers.CONTACT.mapDtoToEntity(contactDto);
        contact.setId(id);

        return Mappers
                .CONTACT
                .mapEntityToDto(
                        contactRepository.save(contact)
                );
    }
}
