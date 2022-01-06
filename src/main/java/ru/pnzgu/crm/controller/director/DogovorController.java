package ru.pnzgu.crm.controller.director;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pnzgu.crm.dto.DogovorDto;
import ru.pnzgu.crm.service.DogovorService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/director/dogovor")
@RequiredArgsConstructor
public class DogovorController {

    private final String COMMON_VIEW = "/director/dogovor/commonDogovorView";
    private final String CREATE_VIEW = "/director/dogovor/action/create";
    private final String UPDATE_VIEW = "/director/dogovor/action/update";
    private final String REDIRECT_URL = "redirect:/director/dogovor/%s";

    private final DogovorService dogovorService;

    @GetMapping("")
    public String getCommonView(Model model) {
        model.addAttribute("dogovorList", dogovorService.readAll());

        List<DogovorDto> dogovorDtoList = new ArrayList<>();
        model.addAttribute("dogovorList", dogovorDtoList);

        return COMMON_VIEW;
    }

    @GetMapping("/{dogovorId}")
    public String getCommonView(@PathVariable Long dogovorId, Model model) {
        model.addAttribute("dogovorList", dogovorService.readAll());

        List<DogovorDto> dogovorDtoList = new ArrayList<>();
        model.addAttribute("dogovorList", dogovorDtoList);
        model.addAttribute("dogovorId", dogovorId);

        return COMMON_VIEW;
    }

    @GetMapping("/create/view")
    public String getDogovorCreateView(Model model) {
        model.addAttribute("dogovor", new DogovorDto());

        return CREATE_VIEW;
    }

    @GetMapping("/update/view/{id}")
    public String getDogovorUpdateView(@PathVariable Long id, Model model) {
        DogovorDto dogovorDto = dogovorService.read(id);
        model.addAttribute("dogovor", dogovorDto);

        return UPDATE_VIEW;
    }

    // ------------- REST ------------

    @PostMapping("/create")
    public String createDogovor(@ModelAttribute DogovorDto dogovorDto) {
        dogovorDto = dogovorService.create(dogovorDto);

        return String.format(REDIRECT_URL, dogovorDto.getId());
    }

    @PostMapping("/update/{id}")
    public String updateDogovor(@PathVariable Long id, @ModelAttribute DogovorDto dogovorDto) {
        dogovorService.update(id, dogovorDto);

        return String.format(REDIRECT_URL, id);
    }
    
}
