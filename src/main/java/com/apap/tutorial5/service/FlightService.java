package com.apap.tutorial5.service;

import com.apap.tutorial5.model.FlightModel;

public interface FlightService {
	void addFlight(FlightModel flight);
	void deleteFlight(FlightModel flight);
	FlightModel getByFlightNumber(String flightNumber);
	void updateFlight(FlightModel flight, String flightNumber);
	FlightModel getFlightById(Long id);
	
}
