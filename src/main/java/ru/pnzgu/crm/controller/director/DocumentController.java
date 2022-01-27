package ru.pnzgu.crm.controller.director;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pnzgu.crm.dto.DateWrapperDto;
import ru.pnzgu.crm.dto.ManagerDto;
import ru.pnzgu.crm.exception.DocumentExportException;
import ru.pnzgu.crm.service.DocumentService;
import ru.pnzgu.crm.service.ManagerService;

import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/director/document")
public class DocumentController {

    private final DocumentService documentService;
    private final ManagerService managerService;

    @GetMapping("/orders")
    public String getPostavDocumentView(Model model) {
        model.addAttribute("dateWrap", new DateWrapperDto());

        return "/director/document/documentOrders";
    }

    @GetMapping("/deal")
    public String getDealDocumentView(Model model) {
        model.addAttribute("dateWrap", new DateWrapperDto());

        return "/director/document/documentDeal";
    }

    @GetMapping("/manager")
    public String getSpisBetweenDateExelDocumentView(Model model) {
        model.addAttribute("managerList", managerService.readAll());
        model.addAttribute("manager", new ManagerDto());

        return "/director/document/documentManager";
    }

    @PostMapping(value = "/orders", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<?> getOrdersDocument(@ModelAttribute(name = "dateWrap") @NonNull DateWrapperDto dateWrapperDto) throws DocumentExportException {
        Objects.requireNonNull(dateWrapperDto.getBeginDate(), "Дата не может быть пустой");
        Objects.requireNonNull(dateWrapperDto.getEndDate(), "Дата не может быть пустой");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"orders.xlsx\"")
                .body(documentService.getOrdersDocument(dateWrapperDto));
    }

    @PostMapping(value = "/deal", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<?> getDealDocument(@ModelAttribute(name = "dateWrap") @NonNull DateWrapperDto dateWrapperDto) throws DocumentExportException {
        Objects.requireNonNull(dateWrapperDto.getBeginDate(), "Дата не может быть пустой");
        Objects.requireNonNull(dateWrapperDto.getEndDate(), "Дата не может быть пустой");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"deal.xlsx\"")
                .body(documentService.getDealDocument(dateWrapperDto));
    }

    @PostMapping(value = "/manager", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<?> getManagerBetweenDateExelDocument(@ModelAttribute(name = "manager") @NonNull ManagerDto manager) throws DocumentExportException {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"manager_#%s.xlsx\"", manager.getId()))
                .body(documentService.getManagerDocument(manager));
    }
}
