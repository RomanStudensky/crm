package ru.pnzgu.crm.controller.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pnzgu.crm.dto.ActivityDto;
import ru.pnzgu.crm.dto.LeadDto;
import ru.pnzgu.crm.service.ActivityService;
import ru.pnzgu.crm.service.LeadService;

import java.util.ArrayList;
import java.util.List;

/**
 * Контроллер CRUD интерфейса "Лид-Активность"
 */
@Controller
@RequestMapping("/manager")
@RequiredArgsConstructor
public class LeadController {

    private final String COMMON_VIEW = "/templates/manager/lead/commonLeadView";
    
    private final String ACTIVITY_CREATE_VIEW = "/templates/manager/lead/action/activity/create";
    private final String ACTIVITY_UPDATE_VIEW = "/templates/manager/lead/action/activity/update";

    private final String LEAD_UPDATE_VIEW = "/templates/manager/lead/action/lead/update";
    
    private final String REDIRECT_URL = "redirect:/manager/lead/%s";
    
    private final LeadService leadService;
    private final ActivityService activityService;

    @GetMapping("/lead")
    public String getCommonView(Model model) {
        model.addAttribute("leadList", leadService.readAll());

        List<LeadDto> leadList = new ArrayList<>();
        model.addAttribute("leadList", leadList);

        return COMMON_VIEW;
    }

    @GetMapping("/lead/{leadId}")
    public String getCommonView(@PathVariable Long leadId, Model model) {
        model.addAttribute("leadList", leadService.readAll());

        List<LeadService> leadList = new ArrayList<>();
        model.addAttribute("leadList", leadList);
        model.addAttribute("leadId", leadId);

        return COMMON_VIEW;
    }

    @GetMapping("/lead/update/view/{id}")
    public String getLeadUpdateView(@PathVariable Long id, Model model) {
        LeadDto leadDto = leadService.read(id);
        model.addAttribute("lead", leadDto);

        return LEAD_UPDATE_VIEW;
    }

    @GetMapping("/activity/create/view")
    public String getLeadCreateView(Model model) {
        model.addAttribute("activity", new ActivityDto());

        return ACTIVITY_CREATE_VIEW;
    }

    @GetMapping("/activity/update/view/{id}")
    public String getActivityUpdateView(@PathVariable Long id, Model model) {
        ActivityDto activityDto = activityService.read(id);
        model.addAttribute("activity", activityDto);

        return ACTIVITY_UPDATE_VIEW;
    }

    // ------------- REST ------------

    @PostMapping("/activity/create")
    public String createLead(@ModelAttribute ActivityDto activityDto, Long idManager) {
        activityDto = activityService.create(activityDto, idManager);

        return String.format(REDIRECT_URL, activityDto.getId());
    }

    @PostMapping("/activity/update/{id}")
    public String updateActivity(@PathVariable Long id, @ModelAttribute ActivityDto activityDto) {
        activityService.update(id, activityDto);

        return String.format(REDIRECT_URL, id);
    }

    @PostMapping("/lead/update/{id}")
    public String updateLead(@PathVariable Long id, @ModelAttribute LeadDto LeadDto) {
        leadService.update(id, LeadDto);

        return String.format(REDIRECT_URL, id);
    }
}
