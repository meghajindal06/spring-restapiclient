package com.afkl.cases.df.airport;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.afkl.cases.df.airport.resource.Location;
import com.afkl.cases.df.airport.resource.SelectionDetail;
import com.afkl.cases.df.airport.service.AirportService;
import com.afkl.cases.df.airport.service.SelectionDetailService;

@RestController
public class AirportController {

	@Autowired
	AirportService airportService;
	
	@Autowired
	SelectionDetailService detailService;
	
	
	
	@RequestMapping("locations")
	@GetMapping
    public HttpEntity<List<Location>> getAirports(@RequestParam(value = "lang", defaultValue = "en") String lang) {
     List<Location> locations = airportService.getAirportList();
		
     
		return new ResponseEntity<>(locations, HttpStatus.OK);
    }

	@RequestMapping("selectionDetail")
	@GetMapping
    public HttpEntity<SelectionDetail> getJourneyDetails(@RequestParam(value = "origin") String origin , @RequestParam(value = "dest") String dest) throws InterruptedException, ExecutionException {
    SelectionDetail detail = detailService.getJourneyDetails(origin, dest);
		
     
		return new ResponseEntity<>(detail, HttpStatus.OK);
    }

	
	
}
