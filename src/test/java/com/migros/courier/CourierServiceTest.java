package com.migros.courier;

import com.migros.courier.model.CourierRequestModel;
import com.migros.courier.model.CourierStoreModel;
import com.migros.courier.model.TravelDistanceResponseModel;
import com.migros.courier.repository.StoreRepository;
import com.migros.courier.service.CourierServiceImpl;
import com.migros.courier.service.DistanceCalculatorStrategy;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class CourierServiceTest {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    DistanceCalculatorStrategy distanceCalculatorStrategy;

    private InOrder inOrder;

    @Autowired
    private CourierServiceImpl courierService;

    @Before
    public void setUp() {
        courierService = new CourierServiceImpl(storeRepository, distanceCalculatorStrategy);
    }

    @Test
    public void shouldCourierLocationInput() {

        CourierRequestModel courierRequestModel = new CourierRequestModel();
        courierRequestModel.setCourierId(1L);
        courierRequestModel.setLat(41.055783);
        courierRequestModel.setLng(29.0210292);
        courierRequestModel.setTime(LocalDateTime.now());

        CourierRequestModel courierRequestModel2 = new CourierRequestModel();
        courierRequestModel2.setCourierId(1L);
        courierRequestModel2.setLat(41.055783);
        courierRequestModel2.setLng(29.0210292);
        courierRequestModel2.setTime(LocalDateTime.now().plusSeconds(30));

        CourierRequestModel courierRequestModel3 = new CourierRequestModel();
        courierRequestModel3.setCourierId(1L);
        courierRequestModel3.setLat(41.055783);
        courierRequestModel3.setLng(29.0210292);
        courierRequestModel3.setTime(LocalDateTime.now().plusMinutes(5));

        courierService.courierLocationInput(courierRequestModel);
        courierService.courierLocationInput(courierRequestModel2);
        courierService.courierLocationInput(courierRequestModel3);

        List<CourierStoreModel> foundedLocations = courierService.getLocationMap().get(courierRequestModel.getCourierId());
        assertNotNull(foundedLocations);
        assertEquals(foundedLocations.size(), 2);
    }

    @Test
    public void shouldFinByFollower() {
        CourierRequestModel courierRequestModel = new CourierRequestModel();
        courierRequestModel.setCourierId(1L);
        courierRequestModel.setLat(41.055783);
        courierRequestModel.setLng(29.0210292);
        courierRequestModel.setTime(LocalDateTime.now());

        TravelDistanceResponseModel travelDistanceResponseModel = new TravelDistanceResponseModel();
        travelDistanceResponseModel.setTotalDistance(20D);

        when(courierService.getTotalTravelDistance(courierRequestModel.getCourierId())).thenReturn(travelDistanceResponseModel);

        final TravelDistanceResponseModel foundTravelDistanceResponseModel = courierService.getTotalTravelDistance(courierRequestModel.getCourierId());

        assertNotNull(foundTravelDistanceResponseModel);
        assertNotNull(foundTravelDistanceResponseModel.getTotalDistance());
        assertEquals(travelDistanceResponseModel.getTotalDistance(), foundTravelDistanceResponseModel.getTotalDistance());
    }
}
