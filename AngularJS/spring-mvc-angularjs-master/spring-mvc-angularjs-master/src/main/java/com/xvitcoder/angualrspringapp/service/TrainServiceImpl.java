package com.xvitcoder.angualrspringapp.service;

import com.xvitcoder.angualrspringapp.beans.Train;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xvitcoder
 * Date: 12/21/12
 * Time: 12:21 AM
 */
@Service("trainService")
public class TrainServiceImpl implements TrainService {
    private static List<Train> trainList = new ArrayList<Train>();
    private static Long id = 0L;

     
    public List<Train> getAllTrains() {
        return trainList;
    }

     
    public Train getTrainById(Long id) {
        return findTrainById(id);
    }

     
    public void addTrain(Train train) {
        train.setId(++id);
        trainList.add(train);
    }

    
    public void deleteTrainById(Long id) {
        Train foundTrain = findTrainById(id);
        if (foundTrain != null) {
            trainList.remove(foundTrain);
            id--;
        }
    }

     
    public void deleteAll() {
        trainList.clear();
        id = 0L;
    }

     
    public void updateTrain(Train train) {
        Train foundTrain = findTrainById(train.getId());
        if (foundTrain != null) {
            trainList.remove(foundTrain);
            trainList.add(train);
        }
    }

    private Train findTrainById(Long id) {
        for (Train train : trainList) {
            if (train.getId() == id) {
                return train;
            }
        }

        return null;
    }
}
