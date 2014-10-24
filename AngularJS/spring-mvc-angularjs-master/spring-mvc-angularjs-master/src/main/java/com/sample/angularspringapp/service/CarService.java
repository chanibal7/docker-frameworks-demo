package com.sample.angularspringapp.service;

import java.util.List;

import com.sample.angularspringapp.beans.Car;


public interface CarService {
    public List<Car> getAllCars();

    public void addCar(String car);

    public void deleteCar(int car);

    public void deleteAll();
}
