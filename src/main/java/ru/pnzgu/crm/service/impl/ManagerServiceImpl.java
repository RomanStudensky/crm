package ru.pnzgu.crm.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.mapper.Mapper;
import org.springframework.stereotype.Service;
import ru.pnzgu.crm.dto.ManagerDto;
import ru.pnzgu.crm.exception.NotFoundException;
import ru.pnzgu.crm.service.ManagerService;
import ru.pnzgu.crm.store.entity.Activity;
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
                .map(Mappers.MANAGER_MAPPER::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ManagerDto read(Long id) {
        return Mappers
                .MANAGER_MAPPER
                .mapEntityToDto(managerRepository
                        .findById(id)
                        .orElseThrow(() -> new NotFoundException(String.format(ExMes.MANAGER_MESSAGE, id))));
    }

    @Override
    public ManagerDto create(ManagerDto managerDto) {
        return Mappers
                .MANAGER_MAPPER
                .mapEntityToDto(
                        managerRepository.save(Mappers.MANAGER_MAPPER.mapDtoToEntity(managerDto))
                );
    }

    @Override
    public ManagerDto update(Long id, ManagerDto managerDto) {
        managerRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ExMes.MANAGER_MESSAGE, id)));

        Manager manager = Mappers.MANAGER_MAPPER.mapDtoToEntity(managerDto);
        manager.setId(id);

        return Mappers
                .MANAGER_MAPPER
                .mapEntityToDto(
                        managerRepository.save(manager)
                );
    }
}
