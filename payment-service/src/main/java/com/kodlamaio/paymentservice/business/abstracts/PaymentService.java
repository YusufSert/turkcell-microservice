package com.kodlamaio.paymentservice2.business.abstracts;



import com.kodlamaio.commonpackage.utils.dto.request.Payment;
import com.kodlamaio.commonpackage.utils.dto.request.PaymentRentalRequest;
import com.kodlamaio.commonpackage.utils.dto.response.ClientResponse;
import com.kodlamaio.paymentservice2.business.dto.request.create.CreatePaymentRequest;
import com.kodlamaio.paymentservice2.business.dto.request.update.UpdatePaymentRequest;
import com.kodlamaio.paymentservice2.business.dto.response.create.CreatePaymentResponse;
import com.kodlamaio.paymentservice2.business.dto.response.get.GetAllPaymentResponse;
import com.kodlamaio.paymentservice2.business.dto.response.get.GetPaymentResponse;
import com.kodlamaio.paymentservice2.business.dto.response.update.UpdatePaymentResponse;

import java.util.List;
import java.util.UUID;

public interface PaymentService {
    List<GetAllPaymentResponse> getAll();
    GetPaymentResponse getById(UUID id);
    CreatePaymentResponse add(CreatePaymentRequest request);
    UpdatePaymentResponse update(UUID id, UpdatePaymentRequest request);
    void delete(UUID id);
    ClientResponse makePayment(PaymentRentalRequest request);

}
