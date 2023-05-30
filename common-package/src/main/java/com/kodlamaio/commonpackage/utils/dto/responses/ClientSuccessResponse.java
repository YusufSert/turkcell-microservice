package com.kodlamaio.commonpackage.utils.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClientSuccessResponse {
    private boolean isSuccess;
    private String message;
}
