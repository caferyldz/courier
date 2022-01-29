package com.migros.courier.service;

public interface DistanceCalculatorStrategy {
    double calculate(double lat1, double lat2, double lng1, double lng2);
}
