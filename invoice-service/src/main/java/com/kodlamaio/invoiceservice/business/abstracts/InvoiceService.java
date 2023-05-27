package com.kodlamaio.invoiceservice.business.abstracts;

import com.kodlamaio.invoiceservice.business.dto.GetAllInvoicesResponse;
import com.kodlamaio.invoiceservice.business.dto.GetInvoiceResponse;
import com.kodlamaio.invoiceservice.entities.Invoice;

import java.util.List;
import java.util.UUID;

public interface InvoiceService {
    List<GetAllInvoicesResponse> getAll();

    GetInvoiceResponse getById(UUID id);
    void add(Invoice invoice);
    void delete(UUID id);
}
