package com.migros.courier.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.migros.courier.model.Store;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class StoreRepository {

    private static final String storesJson = "[\n" +
            "{\n" +
            "\"name\": \"Ataşehir MMM Migros\",\n" +
            "\"lat\": 40.9923307,\n" +
            "\"lng\": 29.1244229\n" +
            "},\n" +
            "{\n" +
            "\"name\": \"Novada MMM Migros\",\n" +
            "\"lat\": 40.986106,\n" +
            "\"lng\": 29.1161293\n" +
            "},\n" +
            "{\n" +
            "\"name\": \"Beylikdüzü 5M Migros\",\n" +
            "\"lat\": 41.0066851,\n" +
            "\"lng\": 28.6552262\n" +
            "},\n" +
            "{\n" +
            "\"name\": \"Ortaköy MMM Migros\",\n" +
            "\"lat\": 41.055783,\n" +
            "\"lng\": 29.0210292\n" +
            "},\n" +
            "{\n" +
            "\"name\": \"Caddebostan MMM Migros\",\n" +
            "\"lat\": 40.9632463,\n" +
            "\"lng\": 29.0630908\n" +
            "}\n" +
            "]";


    public List<Store> getStores() {
        ObjectMapper mapper = new ObjectMapper();
        List<Store> stores = new ArrayList<>();
        try {
            stores = mapper.readValue(storesJson, mapper.getTypeFactory().constructCollectionType(List.class, Store.class));
        }catch (JsonProcessingException e){
            log.error("Store parse exception {}", e.getMessage());
        }

        return stores;
    }
}