package com.sample.angularspringapp.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sample.angularspringapp.beans.RailwayStation;
import com.sample.angularspringapp.service.RailwayStationServiceImpl;

@Controller
@RequestMapping("/railwaystations")
public class RailwayStationController {

    @Autowired
    private RailwayStationServiceImpl railwayStationsService;

    @RequestMapping("railwaystationlist.json")
    public @ResponseBody List<RailwayStation> getRailwayStationList() {
    	test("http://localhost:8081/EmployerInfoService/EmployerServlet");
        return railwayStationsService.getAllRailwayStations();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody void addRailwayStation(@RequestBody RailwayStation railwayStation) {
    	test("http://localhost:8081/EmployerInfoService/EmployerServlet");
        railwayStationsService.addRailwayStation(railwayStation);
        
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public @ResponseBody void updateRailwayStation(@RequestBody RailwayStation railwayStation) {
        railwayStationsService.updateRailwayStation(railwayStation);
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.DELETE)
    public @ResponseBody void removeRailwayStation(@PathVariable("id") int id) {
        railwayStationsService.deleteRailwayStationById(id);
    }

    @RequestMapping(value = "/removeAll", method = RequestMethod.DELETE)
    public @ResponseBody void removeAllRailwayStations() {
        railwayStationsService.deleteAll();
    }

    @RequestMapping("/layout")
    public String getRailwayStationPartialPage(ModelMap modelMap) {
        return "railwaystations/layout";
    }
    
    public static void test(String address) {
        try{
     System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                URL serverAddress = new URL(address);
                HttpURLConnection connection = null;
                connection = (HttpURLConnection) serverAddress.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setReadTimeout(10000);
                connection.connect();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                connection.disconnect();
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
