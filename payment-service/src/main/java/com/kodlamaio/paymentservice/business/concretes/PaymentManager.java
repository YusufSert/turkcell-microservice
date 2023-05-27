package com.kodlamaio.paymentservice.business.concretes;


import com.kodlamaio.commonpackage.utils.dto.requests.PaymentRequest;
import com.kodlamaio.commonpackage.utils.dto.responses.ClientResponse;
import com.kodlamaio.commonpackage.utils.exceptions.BusinessException;
import com.kodlamaio.commonpackage.utils.mappers.ModelMapperService;
import com.kodlamaio.paymentservice.business.abstracts.PaymentService;
import com.kodlamaio.paymentservice.business.dto.request.create.CreatePaymentRequest;
import com.kodlamaio.paymentservice.business.dto.request.update.UpdatePaymentRequest;
import com.kodlamaio.paymentservice.business.dto.response.create.CreatePaymentResponse;
import com.kodlamaio.paymentservice.business.dto.response.get.GetAllPaymentResponse;
import com.kodlamaio.paymentservice.business.dto.response.get.GetPaymentResponse;
import com.kodlamaio.paymentservice.business.dto.response.update.UpdatePaymentResponse;
import com.kodlamaio.paymentservice.business.rules.PaymentBusinessRules;
import com.kodlamaio.paymentservice.entities.Payment;
import com.kodlamaio.paymentservice.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PaymentManager implements PaymentService {
    private final PaymentRepository repository;
    private final ModelMapperService mapper;
    private final PaymentBusinessRules rules;
    @Override
    public List<GetAllPaymentResponse> getAll() {
        return repository.findAll()
            .stream()
            .map(payment -> mapper.forResponse()
                .map(payment, GetAllPaymentResponse.class))
            .toList();
    }

    @Override
    public GetPaymentResponse getById(UUID id) {
        rules.checkIfPaymentExist(id);
        var payment = repository.findById(id).orElseThrow();
        return mapper.forResponse().map(payment, GetPaymentResponse.class);
    }

    @Override
    public CreatePaymentResponse add(CreatePaymentRequest request) {
        Payment payment = mapper.forRequest().map(request, Payment.class);
        payment.setId(UUID.randomUUID());
        repository.save(payment);
        return mapper.forResponse().map(payment, CreatePaymentResponse.class);
    }
    @Override
    public UpdatePaymentResponse update(UUID id, UpdatePaymentRequest request) {
        Payment payment = mapper.forRequest().map(request, Payment.class);
        payment.setId(id);
        repository.save(payment);
        return mapper.forResponse().map(payment, UpdatePaymentResponse.class);
    }


    @Override
    public void delete(UUID id) {
        rules.checkIfPaymentExist(id);
        repository.deleteById(id);
    }


    @Override
    public <T extends PaymentRequest> ClientResponse makePayment(T request) {
        var clientResponse = new ClientResponse();
        try{
            rules.makePayment(request);
            clientResponse.setSuccess(true);
            clientResponse.setMessage("Ödeding");
        }catch (Exception e) {
            clientResponse.setSuccess(false);
            clientResponse.setMessage(e.getMessage());
        }
        return clientResponse;
    }







}
