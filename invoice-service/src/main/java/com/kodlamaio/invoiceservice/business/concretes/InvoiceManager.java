package com.kodlamaio.invoiceservice.business.concretes;

import com.kodlamaio.commonpackage.utils.exceptions.BusinessException;
import com.kodlamaio.commonpackage.utils.mappers.ModelMapperService;
import com.kodlamaio.invoiceservice.business.abstracts.InvoiceService;
import com.kodlamaio.invoiceservice.business.dto.GetAllInvoicesResponse;
import com.kodlamaio.invoiceservice.business.dto.GetInvoiceResponse;
import com.kodlamaio.invoiceservice.entities.Invoice;
import com.kodlamaio.invoiceservice.repository.InvoiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
@AllArgsConstructor
public class InvoiceManager implements InvoiceService {
    private final InvoiceRepository repository;
    private final ModelMapperService mapper;
    @Override
    public List<GetAllInvoicesResponse> getAll() {
        return repository.findAll().stream().map(invoice -> mapper.forResponse().map(invoice, GetAllInvoicesResponse.class)).toList();
    }

    @Override
    public GetInvoiceResponse getById(UUID id) {
        checkIfExist(id);
        var invoice = repository.findById(id).orElseThrow();
        return mapper.forResponse().map(invoice, GetInvoiceResponse.class);
    }

    @Override
    public void add(Invoice invoice) {
        invoice.setId(UUID.randomUUID());
        repository.save(invoice);
    }

    @Override
    public void delete(UUID id) {
        checkIfExist(id);
        repository.deleteById(id);
    }

    public void checkIfExist(UUID id) {
        if(!repository.existsById(id)) {
            throw new BusinessException("Not found invoice!");
        }
    }
}
