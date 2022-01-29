package com.migros.courier.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourierStoreModel {
    private CourierRequestModel courierRequestModel;
    private String storeName;
}
