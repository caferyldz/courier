package com.migros.courier.controller;

import com.migros.courier.model.CourierRequestModel;
import com.migros.courier.model.TravelDistanceResponseModel;
import com.migros.courier.service.CourierService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "Courier endpoints")
public class CourierController {
    private final CourierService courierService;

    public CourierController(final CourierService courierService){this.courierService = courierService;}

    @GetMapping("/travel/distance/{id}")
    @ApiOperation("The application must provide a way for querying total distances, over which any courier travels.")
    public TravelDistanceResponseModel getTotalTravelDistance(@PathVariable("id") Long id){
        return courierService.getTotalTravelDistance(id);
    }

    @PostMapping("/location")
    @ApiOperation("Log courier and store when any courier enters radius of 100 meters from Migros " +
            "stores. Reentries to the same store's circumference over 1 minute should not count\n" +
            "as entrance. Store locations are given as stores.json file.")
    public void locationInput(@RequestBody CourierRequestModel courierRequestModel){
        courierService.courierLocationInput(courierRequestModel);
    }
}
