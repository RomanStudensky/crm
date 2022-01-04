package ru.pnzgu.crm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pnzgu.crm.dto.ActivityDto;
import ru.pnzgu.crm.exception.NotFoundException;
import ru.pnzgu.crm.exception.util.MessageConst;
import ru.pnzgu.crm.service.ActivityService;
import ru.pnzgu.crm.store.entity.Activity;
import ru.pnzgu.crm.store.entity.Lead;
import ru.pnzgu.crm.store.entity.Manager;
import ru.pnzgu.crm.store.repository.ActivityRepository;
import ru.pnzgu.crm.store.repository.LeadRepository;
import ru.pnzgu.crm.store.repository.ManagerRepository;
import ru.pnzgu.crm.util.mapping.Mappers;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;
    private final ManagerRepository managerRepository;
    private final LeadRepository leadRepository;

    @Override
    public List<ActivityDto> readAll() {
        return activityRepository
                .findAll()
                .stream()
                .map(Mappers.ACTIVITY::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ActivityDto read(Long id) {
        return Mappers
                .ACTIVITY
                .mapEntityToDto(activityRepository
                                .findById(id)
                                .orElseThrow(() -> new NotFoundException(String.format(MessageConst.ACTIVITY, id))));
    }

    @Override
    public ActivityDto create(ActivityDto activityDto, Long managerId, Long leadId) {
        requireNonNull(activityDto, "activityDto is null!");
        requireNonNull(activityDto, "managerId is null!");
        requireNonNull(activityDto, "leadId is null!");

        Manager manager =
                managerRepository
                        .findById(managerId)
                        .orElseThrow(() -> new NotFoundException(String.format(MessageConst.MANAGER, managerId)));
        Lead lead =
                leadRepository
                        .findById(leadId)
                        .orElseThrow(() -> new NotFoundException(String.format(MessageConst.LEAD, leadId)));

        Activity activity = Mappers.ACTIVITY.mapDtoToEntity(activityDto);

        activity.setManager(manager);
        activity.setLead(lead);

        activityDto = Mappers
                .ACTIVITY
                .mapEntityToDto(
                        activityRepository.save(activity)
                );

        return activityDto;
    }

    @Override
    public ActivityDto update(Long id, ActivityDto activityDto) {

        activityRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(MessageConst.ACTIVITY, id)));

        Activity activity = Mappers.ACTIVITY.mapDtoToEntity(activityDto);
        activity.setId(id);

        return Mappers
                .ACTIVITY
                .mapEntityToDto(
                        activityRepository.save(activity)
                );
    }

    @Override
    public List<ActivityDto> readAllSostavByLeadId(Long leadId) {
        return activityRepository
                .findByLeadId(leadId)
                .stream()
                .map(Mappers.ACTIVITY::mapEntityToDto)
                .collect(Collectors.toList());
    }
}
