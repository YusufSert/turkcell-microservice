package com.kodlamaio.paymentservice2.business.concretes;


import com.kodlamaio.commonpackage.kafka.producer.KafkaProducer;

import com.kodlamaio.commonpackage.utils.dto.request.PaymentRentalRequest;
import com.kodlamaio.commonpackage.utils.dto.response.ClientResponse;
import com.kodlamaio.commonpackage.utils.exceptions.BusinessException;
import com.kodlamaio.commonpackage.utils.mappers.ModelMapperService;
import com.kodlamaio.paymentservice2.business.abstracts.PaymentService;
import com.kodlamaio.paymentservice2.business.dto.request.create.CreatePaymentRequest;
import com.kodlamaio.paymentservice2.business.dto.request.update.UpdatePaymentRequest;
import com.kodlamaio.paymentservice2.business.dto.response.create.CreatePaymentResponse;
import com.kodlamaio.paymentservice2.business.dto.response.get.GetAllPaymentResponse;
import com.kodlamaio.paymentservice2.business.dto.response.get.GetPaymentResponse;
import com.kodlamaio.paymentservice2.business.dto.response.update.UpdatePaymentResponse;
import com.kodlamaio.paymentservice2.business.rules.PaymentBusinessRules;
import com.kodlamaio.paymentservice2.entities.Payment;
import com.kodlamaio.paymentservice2.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PaymentManager implements PaymentService {
    private final PaymentRepository repository;
    private final PaymentBusinessRules rules;
    private final ModelMapperService mapper;
    private final KafkaProducer producer;
    @Override
    public List<GetAllPaymentResponse> getAll() {
        var payments=repository.findAll();
        var response=payments.stream()
                .map(payment -> mapper.forResponse().map(payment,GetAllPaymentResponse.class)).toList();
        return response;
    }

    @Override
    public GetPaymentResponse getById(UUID id) {
      var payment=repository.findById(id).orElseThrow();
      var response=mapper.forResponse().map(payment, GetPaymentResponse.class);
      return response;
    }

    @Override
    public CreatePaymentResponse add(CreatePaymentRequest request) {
      var payment=mapper.forRequest().map(request, com.kodlamaio.paymentservice2.entities.Payment.class);
      payment.setId(UUID.randomUUID());
      repository.save(payment);
      return mapper.forResponse().map(payment, CreatePaymentResponse.class);
    }

    @Override
    public UpdatePaymentResponse update(UUID id, UpdatePaymentRequest request) {
        var payment=mapper.forRequest().map(request, com.kodlamaio.paymentservice2.entities.Payment.class);
        payment.setId(id);
        repository.save(payment);
        return mapper.forResponse().map(payment, UpdatePaymentResponse.class);
    }

    @Override
    public void delete(UUID id) {
      repository.deleteById(id);

    }

    @Override
    public ClientResponse makePayment(PaymentRentalRequest request) {
        // p.cardNumber =?1  and p.cvv=?2 and p.year=?3 and p.month=?4 and p.cardHolderName=?
        ClientResponse clientResponse=new ClientResponse();
        Payment payment=mapper.forRequest().map(request,Payment.class);
        try{
            ensureCheckvalidateIfTrueCard(payment);
            var validatePayment=repository.findPaymentByCardNumber(request.getCardNumber());
            moneyTransaction(request.getPrice(), validatePayment);
            clientResponse.setSuccess(true);

        }
        catch (Exception e){
            clientResponse.setSuccess(false);
            clientResponse.setMessage(e.getMessage());
        }
        return clientResponse;
    }
// payment
private void ensureCheckvalidateIfTrueCard(Payment request){
    var result=repository.validateCard(request.getCardNumber(),request.getCvv(),request.getYear(),request.getMonth(), request.getCardHolderName());
    if(result == null){
        throw new BusinessException("card  not validate");
    }
}
private void moneyTransaction(double price,Payment payment){
        if(payment.getBalance()<price){
            throw new BusinessException("money not enought");
        }
        payment.setBalance(payment.getBalance() - price);
        repository.save(payment);}
}

