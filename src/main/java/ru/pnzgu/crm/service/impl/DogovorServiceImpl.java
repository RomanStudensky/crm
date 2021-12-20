package ru.pnzgu.crm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pnzgu.crm.dto.DogovorDto;
import ru.pnzgu.crm.exception.util.ExMes;
import ru.pnzgu.crm.exception.NotFoundException;
import ru.pnzgu.crm.service.DogovorService;
import ru.pnzgu.crm.store.entity.Dogovor;
import ru.pnzgu.crm.store.repository.DogovorRepository;
import ru.pnzgu.crm.util.mapping.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DogovorServiceImpl implements DogovorService {

    private final DogovorRepository dogovorRepository;

    @Override
    public List<DogovorDto> readAll() {
        return dogovorRepository
                .findAll()
                .stream()
                .map(Mappers.DOGOVOR_MAPPER::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DogovorDto read(Long id) {
        return Mappers
                .DOGOVOR_MAPPER
                .mapEntityToDto(
                        dogovorRepository
                                .findById(id)
                                .orElseThrow(() -> new NotFoundException(String.format(ExMes.DOGOVOR_MESSAGE, id))));
    }

    @Override
    public DogovorDto create(DogovorDto dogovorDto) {
        return Mappers
                .DOGOVOR_MAPPER
                .mapEntityToDto(
                        dogovorRepository
                                .save(Mappers
                                        .DOGOVOR_MAPPER
                                        .mapDtoToEntity(dogovorDto))
                );
    }

    @Override
    public DogovorDto update(Long id, DogovorDto dogovorDto) {
        dogovorRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ExMes.DOGOVOR_MESSAGE, id)));

        Dogovor dogovor = Mappers.DOGOVOR_MAPPER.mapDtoToEntity(dogovorDto);
        dogovor.setId(id);

        return Mappers
                .DOGOVOR_MAPPER
                .mapEntityToDto(
                        dogovorRepository.save(dogovor)
                );
    }
}
