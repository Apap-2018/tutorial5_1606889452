package com.apap.tutorial5.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apap.tutorial5.model.FlightModel;
/**
 * 
 * FlightDB
 *
 */
public interface FlightDB extends JpaRepository<FlightModel, Long>{
	FlightModel findByFlightNumber(String flightNumber );
}
