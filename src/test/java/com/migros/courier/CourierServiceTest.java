package com.migros.courier;

import com.migros.courier.model.CourierRequestModel;
import com.migros.courier.model.CourierStoreModel;
import com.migros.courier.model.TravelDistanceResponseModel;
import com.migros.courier.repository.StoreRepository;
import com.migros.courier.service.CourierServiceImpl;
import com.migros.courier.service.DistanceCalculatorStrategy;
import com.migros.courier.service.LocationCalculatorStrategy;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class CourierServiceTest {

    DistanceCalculatorStrategy distanceCalculatorStrategy;

    private CourierServiceImpl courierService;

    @Before
    public void setUp() {
        distanceCalculatorStrategy = new LocationCalculatorStrategy();
        courierService = new CourierServiceImpl(new StoreRepository(), distanceCalculatorStrategy);
    }

    @Test
    public void shouldCourierLocationInput() {

        CourierRequestModel courierRequestModel = new CourierRequestModel();
        courierRequestModel.setCourierId(1L);
        courierRequestModel.setLat(41.055783);
        courierRequestModel.setLng(29.0210292);
        courierRequestModel.setTime(LocalDateTime.now());
        courierService.courierLocationInput(courierRequestModel);

        CourierRequestModel courierRequestModel2 = new CourierRequestModel();
        courierRequestModel2.setCourierId(1L);
        courierRequestModel2.setLat(41.055783);
        courierRequestModel2.setLng(29.0210292);
        courierRequestModel2.setTime(LocalDateTime.now().plusSeconds(30));
        courierService.courierLocationInput(courierRequestModel2);

        CourierRequestModel courierRequestModel3 = new CourierRequestModel();
        courierRequestModel3.setCourierId(1L);
        courierRequestModel3.setLat(41.055783);
        courierRequestModel3.setLng(29.0210292);
        courierRequestModel3.setTime(LocalDateTime.now().plusMinutes(5));
        courierService.courierLocationInput(courierRequestModel3);


        List<CourierStoreModel> foundedLocations = courierService.getLocationMap().get(courierRequestModel.getCourierId());
        assertNotNull(foundedLocations);
        assertEquals(foundedLocations.size(), 2);
    }

    @Test
    public void shouldGetTotalTravelDistance() {
        CourierRequestModel courierRequestModel = new CourierRequestModel();
        courierRequestModel.setCourierId(1L);
        courierRequestModel.setLat(41.055783);
        courierRequestModel.setLng(29.0210292);
        courierRequestModel.setTime(LocalDateTime.now());

        CourierRequestModel courierRequestModel2 = new CourierRequestModel();
        courierRequestModel2.setCourierId(1L);
        courierRequestModel2.setLat(40.9632463);
        courierRequestModel2.setLng(29.0630908);
        courierRequestModel2.setTime(LocalDateTime.now().plusSeconds(5));

        CourierRequestModel courierRequestModel3 = new CourierRequestModel();
        courierRequestModel3.setCourierId(1L);
        courierRequestModel3.setLat(41.055783);
        courierRequestModel3.setLng(29.0210292);
        courierRequestModel3.setTime(LocalDateTime.now().plusMinutes(15));

        courierService.courierLocationInput(courierRequestModel);
        courierService.courierLocationInput(courierRequestModel2);
        courierService.courierLocationInput(courierRequestModel3);

        final TravelDistanceResponseModel foundTravelDistanceResponseModel = courierService.getTotalTravelDistance(1L);

        assertNotNull(foundTravelDistanceResponseModel);
        assertNotNull(foundTravelDistanceResponseModel.getTotalDistance());
        Double totalDistance = 21756.104252904202;
        assertEquals(foundTravelDistanceResponseModel.getTotalDistance(), totalDistance);
    }
}
