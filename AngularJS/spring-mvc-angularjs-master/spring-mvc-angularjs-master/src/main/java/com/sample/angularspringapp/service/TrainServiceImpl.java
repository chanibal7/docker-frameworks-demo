package com.sample.angularspringapp.service;

import com.sample.angularspringapp.beans.Train;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service("trainService")
public class TrainServiceImpl implements TrainService {
    private static List<Train> trainList = new ArrayList<Train>();
    private static int id = 0;

   
    public List<Train> getAllTrains() {
        return trainList;
    }

    
    public Train getTrainById(int id) {
        return findTrainById(id);
    }

    
    public void addTrain(Train train) {
        train.setId(++id);
        trainList.add(train);
    }

   
    public void deleteTrainById(int id) {
        Train foundTrain = findTrainById(id);
        if (foundTrain != null) {
            trainList.remove(foundTrain);
            id--;
        }
    }

   
    public void deleteAll() {
        trainList.clear();
        id = 0;
    }

   
    public void updateTrain(Train train) {
        Train foundTrain = findTrainById(train.getId());
        if (foundTrain != null) {
            trainList.remove(foundTrain);
            trainList.add(train);
        }
    }

    private Train findTrainById(int id) {
        for (Train train : trainList) {
            if (train.getId() == id) {
                return train;
            }
        }

        return null;
    }
}
