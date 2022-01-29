package com.migros.courier.service;

import com.migros.courier.model.CourierRequestModel;
import com.migros.courier.model.CourierStoreModel;
import com.migros.courier.model.Store;
import com.migros.courier.model.TravelDistanceResponseModel;
import com.migros.courier.repository.StoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.migros.courier.util.DateUtils.lessThanOneMinute;

@Service
@Slf4j
public class CourierServiceImpl implements CourierService{

    private final StoreRepository storeRepository;
    private DistanceCalculatorStrategy distanceCalculatorStrategy;

    private final Map<Long, List<CourierStoreModel>> locationMap;

    public CourierServiceImpl(StoreRepository storeRepository, DistanceCalculatorStrategy distanceCalculatorStrategy){
        this.storeRepository = storeRepository;
        this.distanceCalculatorStrategy = distanceCalculatorStrategy;
        locationMap = new HashMap<>();
    }

    @Override
    public TravelDistanceResponseModel getTotalTravelDistance(Long id) {
        List<CourierStoreModel> courierRoutes = locationMap.get(id);
        TravelDistanceResponseModel travelDistanceResponseModel = new TravelDistanceResponseModel();
        double totalDistance = calculateTotalDistance(courierRoutes);
        travelDistanceResponseModel.setTotalDistance(totalDistance);
        return travelDistanceResponseModel;
    }

    private double calculateTotalDistance(List<CourierStoreModel> courierRoutes){
        double totalDistance = 0D;
        if (courierRoutes == null){
            return totalDistance;
        }

        ListIterator<CourierStoreModel> iterator = courierRoutes.listIterator();
        CourierRequestModel previous = iterator.next().getCourierRequestModel();
        while (iterator.hasNext()){
            CourierRequestModel current = iterator.next().getCourierRequestModel();
            totalDistance += distanceCalculatorStrategy.calculate(previous.getLat(), current.getLat(), previous.getLng(), current.getLng());
            previous = current;
        }
        return totalDistance;
    }

    @Override
    public void courierLocationInput(CourierRequestModel courierRequestModel) {
        Optional<Store> optionalStore = findLoggableStore(courierRequestModel);
        if (optionalStore.isEmpty() || isLoggedInOneMinute(optionalStore.get(), courierRequestModel)) return;

        List<CourierStoreModel> courierRoutes = locationMap.computeIfAbsent(courierRequestModel.getCourierId(), k -> new ArrayList<>());

        CourierStoreModel courierStoreModel = CourierStoreModel
                .builder()
                .courierRequestModel(courierRequestModel)
                .storeName(optionalStore.get().getName()).build();
        courierRoutes.add(courierStoreModel);
        log.info("Mağazaya giriş yapıldı : {}", courierRequestModel);
    }

    private Optional<Store> findLoggableStore(CourierRequestModel requestModel){
        return storeRepository.getStores()
                .stream()
                .filter(it->distanceCalculatorStrategy.calculate(it.getLat(), requestModel.getLat(), it.getLng(), requestModel.getLng()) < 100)
                .findFirst();
    }

    private boolean isLoggedInOneMinute(Store store, CourierRequestModel courierRequestModel) {
        List<CourierStoreModel> courierRoutes = locationMap.get(courierRequestModel.getCourierId());
        if (courierRoutes == null){
            return false;
        }

        List<CourierStoreModel> filteredList = courierRoutes.stream()
                .filter(courierStoreModel -> courierStoreModel.getStoreName().equals(store.getName())
                        && lessThanOneMinute(courierStoreModel.getCourierRequestModel().getTime(), courierRequestModel.getTime()))
                .collect(Collectors.toList());

        return !filteredList.isEmpty();
    }

    public Map<Long, List<CourierStoreModel>>  getLocationMap(){
        return locationMap;
    }
}
