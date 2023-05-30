package com.kodlamaio.invoiceservice.business.kafka.consumer;

import com.kodlamaio.commonpackage.events.Invoice.CreateInvoiceEvent;
import com.kodlamaio.commonpackage.utils.mappers.ModelMapperService;
import com.kodlamaio.invoiceservice.business.abstracts.InvoiceService;
import com.kodlamaio.invoiceservice.entities.Invoice;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RentalConsumer {
    private final InvoiceService service;
    private final ModelMapperService mapper;
    @KafkaListener(
            topics = "create-invoice",
            groupId = "1"
    )
    public void consume(CreateInvoiceEvent event) {
        var invoice = mapper.forRequest().map(event, Invoice.class);
        service.add(invoice);
    }
}
