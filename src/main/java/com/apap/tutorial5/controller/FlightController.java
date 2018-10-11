package com.apap.tutorial5.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
		ArrayList<FlightModel> pilotFlight = new ArrayList<>();
		pilotFlight.add(flight);
		pilot.setPilotFlight(pilotFlight);
		
		model.addAttribute("flight", flight);
		model.addAttribute("pilot", pilot);
		return "addFlight";
	}
	@RequestMapping(value = "/flight/add/{licenseNumber}", params = {"addRow"}, method = RequestMethod.POST)
	private String addRow(@ModelAttribute PilotModel pilot, BindingResult bindingResult, Model model) {
		if(pilot.getPilotFlight()==null) {
			pilot.setPilotFlight(new ArrayList<FlightModel>());
		}
		
		pilot.getPilotFlight().add(new FlightModel());
		model.addAttribute("pilot", pilot);
		return "addFlight";
	}
	
	@RequestMapping(value = "/flight/add/{licenseNumber}", params= {"submit"}, method = RequestMethod.POST)
	private String addFlightSubmit(@ModelAttribute PilotModel pilot) {
		PilotModel pilot2 = pilotService.getPilotDetailByLicenseNumber(pilot.getLicenseNumber());
		for (FlightModel flight : pilot.getPilotFlight()) {
			flight.setPilot(pilot2);
			flightService.addFlight(flight);
		}
		return "add";
	}
	
	@RequestMapping("/flight/view")
	public String view(@RequestParam("flightNumber") String flightNumber, Model model) {
		FlightModel flight = flightService.getByFlightNumber(flightNumber);
		model.addAttribute("flight", flight);
		model.addAttribute("pilot", flight.getPilot());
		return "view-flight";
	}
	@RequestMapping(value = "/flight/delete", method = RequestMethod.POST)
	private String deleteFlight(@ModelAttribute PilotModel pilot, Model model) {
		for(FlightModel flight : pilot.getPilotFlight()) {
			flightService.deleteFlightById(flight.getFlightNumber());
		}
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
