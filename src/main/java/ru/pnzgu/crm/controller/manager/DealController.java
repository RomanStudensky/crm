package ru.pnzgu.crm.controller.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pnzgu.crm.dto.DealDto;
import ru.pnzgu.crm.service.DealService;
import ru.pnzgu.crm.service.ProductService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/manager/deal")
@RequiredArgsConstructor
public class DealController {
    private final String COMMON_VIEW = "/manager/deal/commonDealView";
    private final String CREATE_VIEW = "/manager/deal/action/create";
    private final String UPDATE_VIEW = "/manager/deal/action/update";
    private final String REDIRECT_URL = "redirect:/manager/deal/%s";

    private final DealService dealService;

    @GetMapping("")
    public String getCommonView(Model model) {
        model.addAttribute("dealList", dealService.readAll());

        List<DealDto> DealDtoList = new ArrayList<>();
        model.addAttribute("dealList", DealDtoList);

        return COMMON_VIEW;
    }

    @GetMapping("/{dealId}")
    public String getCommonView(@PathVariable Long dealId, Model model) {
        model.addAttribute("dealList", dealService.readAll());
        model.addAttribute("productList", dealService.readAllProductByDealId(dealId));

        model.addAttribute("dealId", dealId);

        return COMMON_VIEW;
    }

    @GetMapping("/create/view")
    public String getDealCreateView(Model model) {
        model.addAttribute("deal", new DealDto());

        return CREATE_VIEW;
    }

    @GetMapping("/update/view/{id}")
    public String getDealUpdateView(@PathVariable Long id, Model model) {
        DealDto DealDto = dealService.read(id);
        model.addAttribute("deal", DealDto);

        return UPDATE_VIEW;
    }

    // ------------- REST ------------

    @PostMapping("/create")
    public String createDeal(@ModelAttribute DealDto dealDto, Long idDeal, Long idProduct) {
        dealDto = dealService.create(dealDto, idDeal, idProduct);

        return String.format(REDIRECT_URL, dealDto.getId());
    }

    @PostMapping("/update/{id}")
    public String updateDeal(@PathVariable Long id, @ModelAttribute DealDto DealDto) {
        dealService.update(id, DealDto);

        return String.format(REDIRECT_URL, id);
    }
}
