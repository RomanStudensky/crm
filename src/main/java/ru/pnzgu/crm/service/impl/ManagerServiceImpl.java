package ru.pnzgu.crm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pnzgu.crm.dto.ManagerDto;
import ru.pnzgu.crm.exception.NotFoundException;
import ru.pnzgu.crm.exception.util.MessageConst;
import ru.pnzgu.crm.service.ManagerService;
import ru.pnzgu.crm.store.entity.Manager;
import ru.pnzgu.crm.store.repository.ManagerRepository;
import ru.pnzgu.crm.util.mapping.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {

    private final ManagerRepository managerRepository;

    @Override
    public List<ManagerDto> readAll() {
        return managerRepository
                .findAll()
                .stream()
                .map(Mappers.MANAGER::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ManagerDto read(Long id) {
        return Mappers
                .MANAGER
                .mapEntityToDto(managerRepository
                        .findById(id)
                        .orElseThrow(() -> new NotFoundException(String.format(MessageConst.MANAGER, id))));
    }

    @Override
    public ManagerDto create(ManagerDto managerDto) {
        return Mappers
                .MANAGER
                .mapEntityToDto(
                        managerRepository.save(Mappers.MANAGER.mapDtoToEntity(managerDto))
                );
    }

    @Override
    public ManagerDto update(Long id, ManagerDto managerDto) {
        managerRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(MessageConst.MANAGER, id)));

        Manager manager = Mappers.MANAGER.mapDtoToEntity(managerDto);
        manager.setId(id);

        return Mappers
                .MANAGER
                .mapEntityToDto(
                        managerRepository.save(manager)
                );
    }
}
