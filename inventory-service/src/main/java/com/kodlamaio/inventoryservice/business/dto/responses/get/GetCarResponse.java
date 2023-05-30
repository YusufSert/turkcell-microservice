package com.kodlamaio.inventoryservice.business.dto.responses.get;

import com.kodlamaio.commonpackage.utils.dto.CarResponse;
import com.kodlamaio.inventoryservice.entities.enums.State;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
public class GetCarResponse extends CarResponse {



    /*
    private UUID id;
    private UUID modelId;
    private int modelYear;
    private String plate;
    private State state;
    private double dailyPrice;
    private String modelName;
    private String modeLBrandName;

     */
}
