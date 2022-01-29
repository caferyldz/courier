package com.migros.courier.service;

import com.migros.courier.model.CourierRequestModel;
import com.migros.courier.model.TravelDistanceResponseModel;

public interface CourierService {
    TravelDistanceResponseModel getTotalTravelDistance(Long id);
    void courierLocationInput(CourierRequestModel courierRequestModel);
}
