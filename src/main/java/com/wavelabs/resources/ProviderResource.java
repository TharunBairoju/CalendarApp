package com.wavelabs.resources;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wavelabs.model.Message;
import com.wavelabs.model.Provider;
import com.wavelabs.model.SlotInfo;
import com.wavelabs.service.ProviderService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * This resource class is for Provider actions
 * 
 * @author tharunkumarb
 *
 */
@RestController
public class ProviderResource {

	@Autowired
	private ProviderService providerService;
	
	public ProviderResource() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * This method is for persisiting the provider
	 * 
	 * @param provider
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/provider", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "persist the provider", notes = "This method takes the input as json provider object and gives the persisted provider object")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "The bookie successfully created"),
			@ApiResponse(code = 404, message = "The provider not created"),
			@ApiResponse(code = 500, message = "there is some problem in server") })
	public ResponseEntity persistProvider(@RequestBody Provider provider) {
		Provider persistedProvider = providerService.persistProvider(provider);
		if (persistedProvider == null) {
			Message message = new Message();
			message.setStatus(500);
			message.setMessage("Something went wrong with persisting receiver");
			return ResponseEntity.status(500).body(message);
		} else {
			return ResponseEntity.status(200).body(persistedProvider);
		}
	}

	/**
	 * This method is for creating the calendar for particular provider
	 * 
	 * @param id
	 * @param fromdate
	 * @param todate
	 * @param fromtime
	 * @param totime
	 * @param slotDuration
	 * @return
	 * @throws ParseException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/generateCalendar/{id}")
	@ApiOperation(value = "Generates a calendar", notes = "This will creates the slot accoding to input provided by the provider")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "calendar created successfully"),
			@ApiResponse(code = 404, message = "there is some problem in slot creation"),
			@ApiResponse(code = 500, message = "Problem in server") })
	public ResponseEntity createSlots(@ApiParam(value = "Id of the provider to create slot") @PathVariable("id") int id,
			@ApiParam(value = "starting date of slot creation") @RequestParam("fromdate") String fromdate,
			@ApiParam(value = "Ending date of slot creation") @RequestParam("todate") String todate,
			@ApiParam(value = "starting time of provider availability") @RequestParam("fromtime") String fromtime,
			@ApiParam(value = "End time of provider availability") @RequestParam("totime") String totime,
			@ApiParam(value = "slot duration in minutes") @RequestParam("slotDuration") int slotDuration) {
		List<SlotInfo> createslotlist = null;
		Message message = new Message();
		try {
			DateFormat parser = new SimpleDateFormat("dd/MM/yyyy");
			Date fromDate = parser.parse(fromdate);
			Date toDate = parser.parse(todate);
			createslotlist = providerService.providerSlots(id, fromDate, toDate, Time.valueOf(fromtime),
					Time.valueOf(totime), slotDuration);
		} catch (Exception e) {
		}
		if (createslotlist == null) {
			message.setStatus(500);
			message.setMessage("Something went wrong with calendar generation");
			return ResponseEntity.status(500).body(message);
		} else {
			return ResponseEntity.status(200).body(createslotlist);
		}
	}

	/**
	 * This method will creates the availability
	 * 
	 * @param id
	 * @return
	 */

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/createAvailability/{id}", produces = MediaType.TEXT_HTML_VALUE, method = RequestMethod.PUT)
	@ApiOperation(value = "create availability", notes = "This will creates the availability accoding to input provided by the provider")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "slot availability creation done"),
			@ApiResponse(code = 404, message = "there is some problem in slot creation"),
			@ApiResponse(code = 500, message = "Problem in server") })
	public ResponseEntity createavailability(
			@ApiParam(name = "id", value = "ids of the timeslots") @PathVariable("id") String id) {
		String status = null;
		Message message = null;
		try {
			status = providerService.createAvailability(id);
		} catch (Exception e) {
			
		}
		if (status == null) {
			message = new Message();
			message.setStatus(500);
			message.setMessage("Something went wrong in creation of availability");
			return ResponseEntity.status(500).body(message);
		} else {
			return ResponseEntity.status(200).body(status);
		}
	}

	/**
	 * This method will gets the list providers
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/provider/all", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Gets all the provider", notes = "This will gets the all the providers available in the DB")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "providers successfully got from the DB"),
			@ApiResponse(code = 404, message = "there is some problem in getting providers"),
			@ApiResponse(code = 500, message = "Problem in server") })
	public ResponseEntity getProvidersList() {
		List<Provider> providerList = null;
		Message message = null;
		try {
			providerList = providerService.getAllProviders();
		} catch (Exception e) {
			
		}
		if (providerList == null) {
			message = new Message();
			message.setStatus(404);
			message.setMessage("Something went wrong in getting all bookie");
			return ResponseEntity.status(500).body(message);
		} else {
			return ResponseEntity.status(200).body(providerList);
		}

	}

	/**
	 * This method will gets the booked timeslot data of provider
	 * 
	 * @param id
	 * @return
	 */

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/provider/slotdata", produces = MediaType.TEXT_PLAIN_VALUE)
	@ApiOperation(value = "slotnfo data of a provider", notes = "This will gets the particular provider's slotinfodata based on the provider_id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The slotinfo data retrieved based on provider_id successfully"),
			@ApiResponse(code = 404, message = "there is some problem in getting slotinfo"),
			@ApiResponse(code = 500, message = "Problem in server") })
	public ResponseEntity getBookedSlotData(@RequestParam("id") int id) {
		String data = null;
		Message message = null;
		try {
			data = providerService.getBookedInformation(id);
		} catch (Exception e) {
			
		}
		if (data == null) {
			message = new Message();
			message.setStatus(404);
			message.setMessage("Something went wrong with getting booked slot data");
			return ResponseEntity.status(500).body(message);
		} else {
			return ResponseEntity.status(200).body(data);
		}
	}

}
