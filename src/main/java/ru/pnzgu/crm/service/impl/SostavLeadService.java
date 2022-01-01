package ru.pnzgu.crm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pnzgu.crm.dto.SostavLeadDto;
import ru.pnzgu.crm.store.repository.SostavLeadRepository;
import ru.pnzgu.crm.util.mapping.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SostavLeadService {

    private final SostavLeadRepository sostavLeadRepository;

    public List<SostavLeadDto> readAllSostavByLeadId(Long id) {
        return sostavLeadRepository
                .findByLeadId(id)
                .stream()
                .map(Mappers.SOSTAV_LEAD::mapEntityToDto)
                .collect(Collectors.toList());
    }

}
