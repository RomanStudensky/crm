package ru.pnzgu.crm.service;
import ru.pnzgu.crm.dto.ContactDto;

import java.util.List;

public interface ContactService {

    List<ContactDto> readAll();

    ContactDto read(Long id);

    ContactDto create(ContactDto contactDto);

    ContactDto update(Long id, ContactDto contactDto);
}
