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
                .mapEntityToDto(getActivityById(id));
    }

    @Override
    public ActivityDto create(ActivityDto activityDto, Long managerId, Long leadId) {

        Manager manager = getManagerById(managerId);

        Lead lead = getLeadById(leadId);

        Activity activity = Mappers.ACTIVITY.mapDtoToEntity(activityDto);
        activity.setId(null);
        activity.setManager(manager);
        activity.setLead(lead);

        activityDto = Mappers
                .ACTIVITY
                .mapEntityToDto(
                        activityRepository.saveAndFlush(activity)
                );

        return activityDto;
    }

    private Lead getLeadById(Long leadId) {
        return leadRepository
                .findById(leadId)
                .orElseThrow(() -> new NotFoundException(MessageConst.LEAD));
    }

    @Override
    public ActivityDto update(Long id, ActivityDto activityDto, Long managerId) {

        Lead lead = getActivityById(id).getLead();

        Manager manager = getManagerById(managerId);

        Activity activity = Mappers.ACTIVITY.mapDtoToEntity(activityDto);
        activity.setId(id);
        activity.setManager(manager);
        activity.setLead(lead);

        return Mappers
                .ACTIVITY
                .mapEntityToDto(
                        activityRepository.save(activity)
                );
    }

    @Override
    public List<ActivityDto> readAllSostavByLeadId(Long leadId) {
        return activityRepository
                .findActivitiesByLeadId(leadId)
                .stream()
                .map(Mappers.ACTIVITY::mapEntityToDto)
                .collect(Collectors.toList());
    }

    private Activity getActivityById(Long id) {
        return activityRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(MessageConst.ACTIVITY, id)));
    }

    private Manager getManagerById(Long managerId) {
        return managerRepository
                .findById(managerId)
                .orElseThrow(() -> new NotFoundException(String.format(MessageConst.MANAGER, managerId)));
    }
}
