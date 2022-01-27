package ru.pnzgu.crm.service;

import org.springframework.lang.NonNull;
import ru.pnzgu.crm.dto.DateWrapperDto;
import ru.pnzgu.crm.dto.ManagerDto;
import ru.pnzgu.crm.exception.DocumentExportException;

public interface DocumentService {
    byte[] getOrdersDocument(@NonNull DateWrapperDto dateWrapperDto) throws DocumentExportException;

    byte[] getManagerDocument(@NonNull ManagerDto managerDto) throws DocumentExportException;

    byte[] getDealDocument(@NonNull DateWrapperDto dateWrapperDto) throws DocumentExportException;
}
