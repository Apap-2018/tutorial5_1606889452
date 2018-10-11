package com.apap.tutorial5.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apap.tutorial5.model.PilotModel;

/**
 * 
 * PilotDB
 *
 */
public interface PilotDB extends JpaRepository<PilotModel, Long>{
	PilotModel findByLicenseNumber(String licenseNumber);
	

}
