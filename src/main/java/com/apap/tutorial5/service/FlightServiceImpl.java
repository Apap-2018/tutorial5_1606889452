package com.apap.tutorial5.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tutorial5.model.FlightModel;
import com.apap.tutorial5.model.PilotModel;
import com.apap.tutorial5.repository.FlightDB;

@Service
@Transactional
public class FlightServiceImpl implements FlightService {
	@Autowired
	private FlightDB flightDb;
	
	@Override
	public void addFlight(FlightModel flight) {
		flightDb.save(flight);
	}

	@Override
	public void deleteFlight(FlightModel flight) {
		flightDb.delete(flight);
		
	}

	@Override
	public void updateFlight(FlightModel flight, String flightNumber) {
		FlightModel target = flightDb.findByFlightNumber(flightNumber);
		target.setOrigin(flight.getOrigin());
		target.setTime(flight.getTime());
		target.setFlightNumber(flight.getFlightNumber());
		target.setDestination(flight.getDestination());
		flightDb.save(target);		
	}

	@Override
	public FlightModel getByFlightNumber(String flightNumber) {
		return flightDb.findByFlightNumber(flightNumber);
	}
	 @Override
	    public FlightModel getFlightById(Long id) {
	        return flightDb.getOne(id);
	    }

}
