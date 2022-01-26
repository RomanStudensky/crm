package ru.pnzgu.crm.controller.director;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import ru.pnzgu.crm.util.excel.SpisDto;

@Controller
@RequestMapping("/director/document")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @GetMapping("/orders")
    public String getPostavDocumentView(Model model) {
        model.addAttribute("dateWrap", new DateWrapperDto());

        return "/director/document/documentOrders";
    }

    @GetMapping("/manager")
    public String getSpisBetweenDateExelDocumentView(Model model) {
        model.addAttribute("managerDto", new ManagerDto());

        return "/director/document/documentManager";
    }

    @PostMapping(value = "/orders", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<?> getPostavDocument(@ModelAttribute(name = "dateWrap") DateWrapperDto dateWrapperDto) throws DocumentExportException {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Отчёт_по_заявкам.xlsx\"")
                .body(documentService.getOrdersDocument(dateWrapperDto));
    }

    @PostMapping(value = "/spis", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<?> getSpisBetweenDateExelDocument(@ModelAttribute(name = "spisDto") SpisDto spisDto) throws DocumentExportException {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"spis.xlsx\"")
                .body(activityService.getSpisCurrentDateExelDocument(spisDto.getBeginDate(), spisDto.getEndDate()));
    }
}
