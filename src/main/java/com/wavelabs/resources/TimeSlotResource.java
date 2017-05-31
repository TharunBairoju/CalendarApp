package com.wavelabs.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wavelabs.model.Message;
import com.wavelabs.model.TimeSlots;
import com.wavelabs.service.TimeSlotService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * This resource have the actions of a timeslots
 * 
 * @author tharunkumarb
 *
 */
@RestController
public class TimeSlotResource {

	@Autowired
	private TimeSlotService timeslotService;

	public TimeSlotResource() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * This method gets the all the timeslots of a provider
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/timeslot/all/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	@ApiOperation(value = "Get all timeslots", notes = "This resources gives you the all the timeslots in the database")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully gets the all the timeslots"),
			@ApiResponse(code = 404, message = "The timeslots not return properly"),
			@ApiResponse(code = 500, message = "There is problem in server side") })
	public ResponseEntity getAllTimeSlots(
			@ApiParam(name = "id", value = "provider id") @PathVariable("id") int id) {
		List<TimeSlots> timeslots = null;
		Message message = null;

		try {
			timeslots = new ArrayList<TimeSlots>();
			timeslots = timeslotService.getTimeSlotsByProvider(id);
		} catch (Exception e) {
			message = new Message();
			message.setStatus(500);
			message.setMessage("Something went wrong with getting all timeslots of bookie");
		}
		if (timeslots == null) {
			return ResponseEntity.status(500).body(message);
		} else {
			return ResponseEntity.status(200).body(timeslots);
		}
	}
}
