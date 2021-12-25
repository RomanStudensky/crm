package ru.pnzgu.crm.service;

import ru.pnzgu.crm.dto.DogovorDto;

import java.util.List;

public interface DogovorService {

    List<DogovorDto> readAll();

    DogovorDto read(Long id);

    DogovorDto create(DogovorDto dogovorDto);

    DogovorDto update(Long id, DogovorDto dogovorDto);
}
