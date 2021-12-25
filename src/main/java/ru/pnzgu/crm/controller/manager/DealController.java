package ru.pnzgu.crm.controller.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pnzgu.crm.dto.DealDto;
import ru.pnzgu.crm.dto.DealSostavDto;
import ru.pnzgu.crm.dto.DogovorDto;
import ru.pnzgu.crm.dto.ProductDto;
import ru.pnzgu.crm.service.DealService;
import ru.pnzgu.crm.service.DealSostavService;
import ru.pnzgu.crm.service.LeadService;
import ru.pnzgu.crm.service.ProductService;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Контроллер CRUD интерфейса "Сделка - Лид"
 */
@Controller
@RequestMapping("/manager/deal")
@RequiredArgsConstructor
public class DealController {
    private final String COMMON_VIEW = "/manager/deal/commonDealView";
    private final String CREATE_VIEW = "/manager/deal/action/deal/create";
    private final String UPDATE_VIEW = "/manager/deal/action/deal/update";
    private final String CREATE_SOSTAV_VIEW = "/manager/deal/action/sostav/create";
    private final String UPDATE_SOSTAV_VIEW = "/manager/deal/action/sostav/update";
    private final String REDIRECT_URL = "redirect:/manager/deal/%s/%s";
    private final String REDIRECT_LEAD_URL = "redirect:/manager/deal/%s/%s";

    private final DealService dealService;
    private final LeadService leadService;
    private final ProductService productService;
    private final DealSostavService dealSostavService;

    @GetMapping("")
    public String getCommonView(Model model) {
        model.addAttribute("dealList", dealService.readAll());

        List<DealDto> DealDtoList = new ArrayList<>();
        model.addAttribute("dealList", DealDtoList);

        return COMMON_VIEW;
    }

    @GetMapping("/{leadId}")
    public String getCommonView(@PathVariable Long leadId, Model model) {
        model.addAttribute("leadList", leadService.readAll());
        model.addAttribute("leadId", leadId);
        model.addAttribute("dealList", dealService.readAllByLeadId(leadId));
        model.addAttribute("sostavList", new ArrayList<DealSostavDto>());

        return COMMON_VIEW;
    }

    @GetMapping("/{leadId}/{dealId}")
    public String getCommonView(@PathVariable Long leadId, @PathVariable Long dealId, Model model) {
        model.addAttribute("leadList", leadService.readAll());
        model.addAttribute("leadId", leadId);
        model.addAttribute("dealList", dealService.readAllByLeadId(leadId));
        model.addAttribute("sostavList", dealSostavService.readAllByDealId(dealId));

        model.addAttribute("dealId", dealId);

        return COMMON_VIEW;
    }

    @GetMapping("/create/view/{leadId}")
    public String getDealCreateView(@PathVariable Long leadId, Model model) {
        model.addAttribute("deal", new DealDto());
        model.addAttribute("leadId", leadId);
        return CREATE_VIEW;
    }

    @GetMapping("/update/view/{id}")
    public String getDealUpdateView(@PathVariable Long id, Model model) {
        DealDto dealDto = dealService.read(id);
        model.addAttribute("deal", dealDto);

        return UPDATE_VIEW;
    }

    @GetMapping("/create/sostav/view/{dealId}")
    public String getDealSostavCreateView(@PathVariable Long dealId, Model model) {
        model.addAttribute("product", new ProductDto());
        model.addAttribute("productList", productService.readAll());
        model.addAttribute("sostav", new DealSostavDto());
        model.addAttribute("dealId", dealId);
        return CREATE_VIEW;
    }

    @GetMapping("/update/sostav/view/{id}")
    public String getDealSosatavUpdateView(@PathVariable Long id, Model model) {
        DealSostavDto sostavDto = dealSostavService.read(id);
        model.addAttribute("product", sostavDto.getProduct());
        model.addAttribute("productList", productService.readAll());
        model.addAttribute("sostav", sostavDto);

        return UPDATE_VIEW;
    }

    // ------------- REST ------------

    @PostMapping("/create/{leadId}")
    public String createDeal(@PathVariable Long leadId, @ModelAttribute DealDto dealDto, @ModelAttribute DogovorDto dogovor) {
        requireNonNull(dogovor, "Договор - Null");

        dealDto = dealService.create(dealDto, leadId, dogovor.getId());

        return String.format(REDIRECT_LEAD_URL, leadId, dealDto.getId());
    }

    @PostMapping("/update/{id}")
    public String updateDeal(@PathVariable Long id, @ModelAttribute DealDto DealDto) {
        dealService.update(id, DealDto);
        Long leadId = dealService.readLeadIdByDealId(id);

        return String.format(REDIRECT_LEAD_URL, leadId, id);
    }

    @PostMapping("/create/sostav/{dealId}")
    public String createDealSostav(@PathVariable Long dealId, @ModelAttribute DealSostavDto sostav, @ModelAttribute ProductDto product) {
        requireNonNull(sostav, "Состав сделки - Null");
        requireNonNull(product, "Продукт - Null");

        dealSostavService.create(sostav, dealId, product.getId());
        Long leadId = dealService.readLeadIdByDealId(dealId);

        return String.format(REDIRECT_LEAD_URL, leadId, dealId);
    }

    @PostMapping("/update/sostav/{id}")
    public String updateDealSostav(@PathVariable Long id, @ModelAttribute DealSostavDto dealSostav) {
        dealSostavService.update(id, dealSostav);
        Long dealId = dealSostavService.findDealIdBySostavId(id);
        Long leadId = dealService.readLeadIdByDealId(dealId);

        return String.format(REDIRECT_LEAD_URL, leadId, dealId);
    }
}
