package com.sample.angularspringapp.service;

import com.sample.angularspringapp.beans.Train;

import java.util.List;


public interface TrainService {
    public List<Train> getAllTrains();

    public Train getTrainById(int id);

    public void addTrain(Train train);

    public void deleteTrainById(int id);

    public void deleteAll();

    public void updateTrain(Train train);
}
