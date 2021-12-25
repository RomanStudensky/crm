package ru.pnzgu.crm.util.mapping;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import ru.pnzgu.crm.dto.ParentDto;
import ru.pnzgu.crm.store.entity.ParentEntity;

;

@RequiredArgsConstructor
public class SimpleMapper<Dto extends ParentDto, Entity extends ParentEntity> {

    private final Dto dto;
    private final Entity entity;

    private final ModelMapper modelMapper = new ModelMapper();

    public Dto mapEntityToDto(Entity e) {
        return (Dto) modelMapper.map(e, dto.getClass());
    }

    public Entity mapDtoToEntity(Dto d) {
        return (Entity) modelMapper.map(d, entity.getClass());
    }
}
