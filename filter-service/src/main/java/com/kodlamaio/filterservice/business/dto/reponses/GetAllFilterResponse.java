package com.kodlamaio.filterservice.business.dto.reponses;

import java.util.UUID;

public class GetAllFilterResponse {
    private String id;
    private UUID carId;
    private UUID modelId;
    private UUID brandId;
    private String modelName;
    private String brandName;
    private String plate;
    private int modelYear;
    private double dailyPrice;
    private String state;
}
