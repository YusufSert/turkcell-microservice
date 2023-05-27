package com.kodlamaio.paymentservice.api.controllers;


import com.kodlamaio.commonpackage.utils.dto.requests.PaymentRequest;
import com.kodlamaio.commonpackage.utils.dto.responses.ClientResponse;
import com.kodlamaio.paymentservice.business.abstracts.PaymentService;
import com.kodlamaio.paymentservice.business.dto.request.create.CreatePaymentRequest;
import com.kodlamaio.paymentservice.business.dto.request.update.UpdatePaymentRequest;
import com.kodlamaio.paymentservice.business.dto.response.create.CreatePaymentResponse;
import com.kodlamaio.paymentservice.business.dto.response.get.GetAllPaymentResponse;
import com.kodlamaio.paymentservice.business.dto.response.get.GetPaymentResponse;
import com.kodlamaio.paymentservice.business.dto.response.update.UpdatePaymentResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService service;
    @GetMapping
    public List<GetAllPaymentResponse> getAll(){
        return service.getAll();
    }
    @GetMapping("/{id}")
    public GetPaymentResponse getById(@PathVariable UUID id){
        return service.getById(id);
    }
    @PutMapping("/{id}")

    public UpdatePaymentResponse update(@PathVariable UUID id, @RequestBody UpdatePaymentRequest request){
        return service.update(id,request);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreatePaymentResponse add(@RequestBody CreatePaymentRequest request){
        return service.add(request);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id){
        service.delete(id);
    }

    @PostMapping("/make-payment")
    public <T extends PaymentRequest> ClientResponse makePayment(@RequestBody T request) {
        return service.makePayment(request);
    }

}
