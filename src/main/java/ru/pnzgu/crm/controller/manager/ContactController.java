package ru.pnzgu.crm.controller.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pnzgu.crm.dto.ContactDto;
import ru.pnzgu.crm.dto.OrderDto;
import ru.pnzgu.crm.service.ContactService;
import ru.pnzgu.crm.service.OrderService;

import java.util.List;

/**
 * Контроллер CRUD интерфейса "Контакт-Заявка"
 * Создаёт лид совместно с заявкой
 */
@Controller
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ContactController {
    private final String COMMON_VIEW = "/manager/contact/commonContactView";
    private final String CONTACT_CREATE_VIEW = "/manager/contact/action/create";
    private final String CONTACT_UPDATE_VIEW = "/manager/contact/action/update";

    private final String ORDER_CREATE_VIEW = "/manager/contact/order/action/create";
    private final String ORDER_UPDATE_VIEW = "/manager/contact/order/action/update";

    private final String REDIRECT_URL = "redirect:/manager/contact/%s";

    private final ContactService contactService;
    private final OrderService orderService;

    @GetMapping("/contact")
    public String getCommonView(Model model) {
        model.addAttribute("contactList", contactService.readAll());

        List<ContactDto> contactList = contactService.readAll();
        model.addAttribute("contactList", contactList);

        return COMMON_VIEW;
    }

    @GetMapping("/contact/{contactId}")
    public String getCommonView(@PathVariable Long contactId, Model model) {
        model.addAttribute("contactList", contactService.readAll());

        List<ContactDto> contactDtoList = contactService.readAll();
        List<OrderDto> orderDtoList = orderService.readByContactId(contactId);

        model.addAttribute("contactList", contactDtoList);
        model.addAttribute("orderList", orderDtoList);
        model.addAttribute("contactId", contactId);

        return COMMON_VIEW;
    }

    @GetMapping("/contact/create/view")
    public String getContactCreateView(Model model) {
        model.addAttribute("contact", new ContactDto());
        return CONTACT_CREATE_VIEW;
    }

    @GetMapping("/contact/update/view/{id}")
    public String getContactUpdateView(@PathVariable Long id, Model model) {
        ContactDto contact = contactService.read(id);
        model.addAttribute("contact", contact);

        return CONTACT_UPDATE_VIEW;
    }

    @GetMapping("/order/create/view/{id}")
    public String getOrderCreateView(@PathVariable Long id, Model model) {
        model.addAttribute("order", new OrderDto());
        model.addAttribute("contactId", id);

        return ORDER_CREATE_VIEW;
    }

    @GetMapping("/order/update/view/{id}")
    public String getOrderUpdateView(@PathVariable Long id, Model model) {
        OrderDto orderDto = orderService.read(id);
        model.addAttribute("order", orderDto);

        return ORDER_UPDATE_VIEW;
    }

    // ------------- REST ------------

    @PostMapping("/contact/create")
    public String createContact(@ModelAttribute ContactDto contactDto) {
        contactDto = contactService.create(contactDto);

        return String.format(REDIRECT_URL, contactDto.getId());
    }

    @PostMapping("/contact/update/{id}")
    public String updateContact(@PathVariable Long id, @ModelAttribute ContactDto ContactDto) {
        contactService.update(id, ContactDto);

        return String.format(REDIRECT_URL, id);
    }

    @PostMapping("/order/create/{contactId}")
    public String createOrder(@ModelAttribute OrderDto orderDto, @PathVariable Long contactId) {
        orderService.createOrderAndLead(orderDto, contactId);

        return String.format(REDIRECT_URL, contactId);
    }

    @PostMapping("/order/update/{id}")
    public String updateOrder(@PathVariable Long id, @ModelAttribute OrderDto orderDto) {
        orderDto = orderService.update(id, orderDto);

        return String.format(REDIRECT_URL, orderDto.getContact().getId());
    }
}
