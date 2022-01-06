package ru.pnzgu.crm.controller.director;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pnzgu.crm.dto.ManagerDto;
import ru.pnzgu.crm.service.ManagerService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/director/manager")
@RequiredArgsConstructor
public class ManagerController {
    private final String COMMON_VIEW = "/director/manager/commonManagerView";
    private final String CREATE_VIEW = "/director/manager/action/create";
    private final String UPDATE_VIEW = "/director/manager/action/update";
    private final String REDIRECT_URL = "redirect:/director/manager/%s";

    private final ManagerService managerService;

    @GetMapping("")
    public String getCommonView(Model model) {
        model.addAttribute("managerList", managerService.readAll());

        List<ManagerDto> managerList = new ArrayList<>();
        model.addAttribute("managerList", managerList);

        return COMMON_VIEW;
    }

    @GetMapping("/{managerId}")
    public String getCommonView(@PathVariable Long managerId, Model model) {
        model.addAttribute("managerList", managerService.readAll());

        List<ManagerDto> ManagerDtoList = new ArrayList<>();
        model.addAttribute("managerList", ManagerDtoList);

        return COMMON_VIEW;
    }

    @GetMapping("/create/view")
    public String getManagerCreateView(Model model) {
        model.addAttribute("manager", new ManagerDto());

        return CREATE_VIEW;
    }

    @GetMapping("/update/view/{id}")
    public String getManagerUpdateView(@PathVariable Long id, Model model) {
        ManagerDto ManagerDto = managerService.read(id);
        model.addAttribute("manager", ManagerDto);

        return UPDATE_VIEW;
    }

    // ------------- REST ------------

    @PostMapping("/create")
    public String createManager(@ModelAttribute ManagerDto ManagerDto) {
        ManagerDto = managerService.create(ManagerDto);

        return String.format(REDIRECT_URL, ManagerDto.getId());
    }

    @PostMapping("/update/{id}")
    public String updateManager(@PathVariable Long id, @ModelAttribute ManagerDto ManagerDto) {
        managerService.update(id, ManagerDto);

        return String.format(REDIRECT_URL, id);
    }

}
