package com.apap.tutorial5.controller;

import java.util.List;

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
public class PilotController {
	
	@Autowired
	private PilotService pilotService;
	
	@Autowired
	private FlightService flightService;
	
	@RequestMapping("/")
	private String home(Model model) {
		model.addAttribute("title", "APAP");
		return "home";
	}
	@RequestMapping(value = "/pilot/add", method = RequestMethod.GET)
	private String add (Model model) {
		model.addAttribute("pilot", new PilotModel());
		return "addPilot";
	}
	@RequestMapping(value = "/pilot/add", method = RequestMethod.POST)
	private String addPilotSubmit(@ModelAttribute PilotModel pilot) {
		pilotService.addPilot(pilot);
		return "add";
	}
	@RequestMapping(value="/pilot/view", method = RequestMethod.GET)
	private String view (@RequestParam String licenseNumber, Model model) {
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		List<FlightModel> listFlight = pilot.getPilotFlight();
		model.addAttribute("pilot", pilot);
		model.addAttribute("listFlight", listFlight);
		return "view-pilot";
	}
	
	@RequestMapping(value="/pilot/delete/{licenseNumber}", method = RequestMethod.GET)
	private String deletePilot (@PathVariable(value = "licenseNumber") String licenseNumber) {
		pilotService.deletePilot(pilotService.getPilotDetailByLicenseNumber(licenseNumber));
		return "delete";
	}
	@RequestMapping(value = "/pilot/update/{licenseNumber}", method = RequestMethod.GET)
	private String update (@PathVariable(value = "licenseNumber") String licenseNumber, Model model) {
		model.addAttribute("old-pilot", pilotService.getPilotDetailByLicenseNumber(licenseNumber));
		PilotModel pilot = new PilotModel();
		pilot.setLicenseNumber(licenseNumber);
		model.addAttribute("pilot", pilot);
		return "update-pilot";
	}
	@RequestMapping(value = "/pilot/update/{licenseNumber}", method = RequestMethod.POST)
	private String updatePilot(@ModelAttribute PilotModel newPilot, 
			@PathVariable(value = "licenseNumber") String licenseNumber) {
		pilotService.updatePilot(newPilot, licenseNumber);
		return "update";
	}
}
