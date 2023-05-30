package com.kodlamaio.commonpackage.utils.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class CarResponse {
    private UUID id;
    private UUID modelId;
    private int modelYear;
    private String plate;
    private String state;
    private double dailyPrice;
    private String modelName;
    private String modelBrandName;
}
