package com.apap.tutorial5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial5.model.FlightModel;
import com.apap.tutorial5.model.PilotModel;
import com.apap.tutorial5.service.FlightService;
import com.apap.tutorial5.service.PilotService;

@Controller
public class FlightController {
	
	@Autowired
	private FlightService flightService;
	
	@Autowired
	private PilotService pilotService;
	
	
	@RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.GET)
	private String add (@PathVariable(value = "licenseNumber") String licenseNumber, Model model) {
		FlightModel flight = new FlightModel();
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		flight.setPilot(pilot);
		
		model.addAttribute("flight",  flight);
		return "addFlight";
	}
	
	@RequestMapping(value = "/flight/add", method = RequestMethod.POST)
	private String addFlightSubmit(@ModelAttribute FlightModel flight) {
		flightService.addFlight(flight);
		return "add";
	}
	
	@RequestMapping("/flight/view")
	public String view(@RequestParam("flightNumber") String flightNumber, Model model) {
		FlightModel flight = flightService.getByFlightNumber(flightNumber);
		model.addAttribute("flight", flight);
		model.addAttribute("pilot", flight.getPilot());
		return "view-flight";
	}
	@RequestMapping(value="/flight/delete/{flightNumber}", method = RequestMethod.GET)
	private String deletePilot (@PathVariable(value = "flightNumber") String flightNumber) {
		flightService.deleteFlight(flightService.getByFlightNumber(flightNumber));
		return "delete";

	}
	
	@RequestMapping(value = "/flight/update/{flightNumber}", method = RequestMethod.GET)
    private String updateFlight(@PathVariable(value = "flightNumber") String flightNumber, Model model) {
        FlightModel flight = flightService.getByFlightNumber(flightNumber);
        model.addAttribute("flight", flight);
        return "update-flight";
    }
	
	@RequestMapping(value = "/flight/update/{flightNumber}", method = RequestMethod.POST)
	private String updateFlightSubmit(@PathVariable(value = "flightNumber") String flightNumber, @ModelAttribute FlightModel flight) {
		flightService.updateFlight(flight,  flightNumber);
		return "update";
	}
}
