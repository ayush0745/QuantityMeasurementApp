package com.apps.quantitymeasurement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuantityHistoryDTO {
    private String operation;
    private String result;
    private String createdAt;
}
