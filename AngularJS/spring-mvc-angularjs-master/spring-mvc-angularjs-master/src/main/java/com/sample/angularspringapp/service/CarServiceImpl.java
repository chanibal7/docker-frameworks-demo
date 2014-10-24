package com.sample.angularspringapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sample.angularspringapp.beans.Car;
import com.sample.angularspringapp.dao.CarDAO;

@Service("carService")
@Transactional
public class CarServiceImpl implements CarService {

	@Autowired
	private CarDAO carDAO;

	public List<Car> getAllCars() {

		return carDAO.getAllCars();
	}

	public void addCar(String car) {

		carDAO.add(car);

	}

	public void deleteCar(int car) {

		carDAO.deleteCar(car);

	}

	public void deleteAll() {
		carDAO.clear();

	}

}
