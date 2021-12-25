package ru.pnzgu.crm.service;

import ru.pnzgu.crm.dto.ActivityDto;

import java.util.List;

public interface ActivityService {

    List<ActivityDto> readAll();

    ActivityDto read(Long id);

    ActivityDto create(ActivityDto activityDto, Long managerId);

    ActivityDto update(Long id, ActivityDto activityDto);
}
