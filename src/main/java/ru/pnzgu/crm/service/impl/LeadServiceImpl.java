package ru.pnzgu.crm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pnzgu.crm.dto.LeadDto;
import ru.pnzgu.crm.exception.NotFoundException;
import ru.pnzgu.crm.service.LeadService;
import ru.pnzgu.crm.store.entity.Activity;
import ru.pnzgu.crm.store.entity.Lead;
import ru.pnzgu.crm.store.repository.LeadRepository;
import ru.pnzgu.crm.util.mapping.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeadServiceImpl implements LeadService {
    private final LeadRepository leadRepository;

    @Override
    public List<LeadDto> readAll() {
        return leadRepository
                .findAll()
                .stream()
                .map(Mappers.LEAD_MAPPER::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public LeadDto read(Long id) {
        return Mappers
                .LEAD_MAPPER
                .mapEntityToDto(leadRepository
                        .findById(id)
                        .orElseThrow(() -> new NotFoundException(String.format(ExMes.LEAD_MESSAGE, id))));
    }

    @Override
    public LeadDto update(Long id, LeadDto leadDto) {
        leadRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ExMes.ACTIVITY_MESSAGE, id)));

        Lead lead = Mappers.LEAD_MAPPER.mapDtoToEntity(leadDto);
        lead.setId(id);

        return Mappers
                .LEAD_MAPPER
                .mapEntityToDto(
                        leadRepository.save(lead)
                );
    }
}
