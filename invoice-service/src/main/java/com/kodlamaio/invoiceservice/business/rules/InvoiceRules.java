package com.kodlamaio.invoiceservice.business.rules;

import com.kodlamaio.commonpackage.utils.exceptions.BusinessException;
import com.kodlamaio.invoiceservice.repository.InvoiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class InvoiceRules {
    private final InvoiceRepository repository;

    public void checkIfExist(UUID id) {
        if(!repository.existsById(id)) {
            throw new BusinessException("Not found invoice!");
        }
    }
}
