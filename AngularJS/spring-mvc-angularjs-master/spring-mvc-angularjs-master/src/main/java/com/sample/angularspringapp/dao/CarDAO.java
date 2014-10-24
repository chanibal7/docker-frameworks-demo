package com.sample.angularspringapp.dao;

import java.util.List;

import com.sample.angularspringapp.beans.Car;

public interface CarDAO {

	List<Car> getAllCars();

	void add(String car);

	void deleteCar(int car);

	void clear();

}
