package ru.pnzgu.crm.service;

import ru.pnzgu.crm.dto.ActivityDto;

import java.util.List;

public interface ActivityService {

    List<ActivityDto> readAll();

    ActivityDto read(Long id);

    ActivityDto create(ActivityDto activityDto, Long managerId, Long leadId);

    ActivityDto update(Long id, ActivityDto activityDto, Long managerId);

    List<ActivityDto> readAllSostavByLeadId(Long leadId);
}
