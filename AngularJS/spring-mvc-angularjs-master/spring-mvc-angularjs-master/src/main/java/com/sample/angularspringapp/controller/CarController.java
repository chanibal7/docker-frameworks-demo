package com.sample.angularspringapp.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sample.angularspringapp.beans.Car;
import com.sample.angularspringapp.service.CarService;


@Controller
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @RequestMapping("/carlist.json")
    public @ResponseBody List<Car> getCarList() {
    	
        return carService.getAllCars();
    }

    @RequestMapping(value = "/addCar/{car}", method = RequestMethod.POST)
    public @ResponseBody void addCarPost(@PathVariable("car") String carname) {
    	
        carService.addCar(carname);
        
    }
    @RequestMapping(value = "/addCar/{car}", method = RequestMethod.GET)
    public @ResponseBody void addCarGet(@PathVariable("car") String carname) {
    	
        carService.addCar(carname);
        
    }

    @RequestMapping(value = "/removeCar/{carid}", method = RequestMethod.DELETE)
    public @ResponseBody void removeCar(@PathVariable("carid") int carid) {
    	
        carService.deleteCar(carid);
    }

    @RequestMapping(value = "/removeAllCars", method = RequestMethod.DELETE)
    public @ResponseBody void removeAllCars() {
        carService.deleteAll();
    }

    @RequestMapping("/layout")
    public String getCarPartialPage() {
        return "cars/layout";
    }
    
}
