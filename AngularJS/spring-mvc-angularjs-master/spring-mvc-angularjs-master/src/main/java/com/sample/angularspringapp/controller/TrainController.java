package com.sample.angularspringapp.controller;

import com.sample.angularspringapp.beans.Train;
import com.sample.angularspringapp.service.TrainService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


@Controller
@RequestMapping("/trains")
public class TrainController {

    @Autowired
    private TrainService trainService;

    @RequestMapping("trainslist.json")
    public @ResponseBody List<Train> getTrainList() {
        return trainService.getAllTrains();
    }

    @RequestMapping(value = "/addTrain", method = RequestMethod.POST)
    public @ResponseBody void addTrain(@RequestBody Train train) {
        trainService.addTrain(train);
        test("http://localhost:8081/EmployerInfoService/EmployerServlet");
    }

    @RequestMapping(value = "/updateTrain", method = RequestMethod.PUT)
    public @ResponseBody void updateTrain(@RequestBody Train train) {
        trainService.updateTrain(train);
    }

    @RequestMapping(value = "/removeTrain/{id}", method = RequestMethod.DELETE)
    public @ResponseBody void removeTrain(@PathVariable("id") int id) {
        trainService.deleteTrainById(id);
    }

    @RequestMapping(value = "/removeAllTrains", method = RequestMethod.DELETE)
    public @ResponseBody void removeAllTrains() {
        trainService.deleteAll();
    }

    @RequestMapping("/layout")
    public String getTrainPartialPage(ModelMap modelMap) {
        return "trains/layout";
    }
    
    public static void test(String address) {
        try{
     System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                URL serverAddress = new URL(address);
                HttpURLConnection connection = null;
                connection = (HttpURLConnection) serverAddress.openConnection();
                connection.setRequestMethod("GET");
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
