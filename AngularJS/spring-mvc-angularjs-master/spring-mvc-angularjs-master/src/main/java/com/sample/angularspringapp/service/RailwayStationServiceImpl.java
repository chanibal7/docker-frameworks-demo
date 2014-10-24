package com.sample.angularspringapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sample.angularspringapp.beans.RailwayStation;


@Service("RailwayStationService")
public class RailwayStationServiceImpl implements RailwayStationService {
    
    private static List<RailwayStation> rsList = new ArrayList<RailwayStation>();
    private static int id = 0;

    public RailwayStation getRailwayStationById(int id) {
    	
    	return findRailwayStationById(id);
    }

    private RailwayStation findRailwayStationById(int id) {
        for (RailwayStation rs : rsList) {
            if (rs.getId() == id) {
                return rs;
            }
        }

        return null;
    }

	public List<RailwayStation> getAllRailwayStations() {		
		return rsList;
	}


	public void addRailwayStation(RailwayStation railwayStation) {
		railwayStation.setId(++ id);
		rsList.add(railwayStation);		
		
	}

	public void deleteRailwayStationById(int id) {
        RailwayStation found = findRailwayStationById(id);
        if (found != null) {
            rsList.remove(found);
            id--;
        }
		
	}

	public void updateRailwayStation(RailwayStation railwayStation) {
        RailwayStation found = findRailwayStationById(railwayStation.getId());
        if (found != null) {
            rsList.remove(found);
            rsList.add(railwayStation);
        }
	}
	
    
    public void deleteAll() {
        rsList.clear();
        id = 0;
    }
}
