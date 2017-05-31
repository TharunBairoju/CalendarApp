package com.wavelabs.resources;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wavelabs.model.Bookings;
import com.wavelabs.model.Ids;
import com.wavelabs.model.Message;
import com.wavelabs.service.BookingService;
import com.wavelabs.service.TimeSlotService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * This is a resource class for bookings
 * 
 * @author tharunkumarb
 *
 */
@RestController
public class BookTimeSlot {

	@Autowired
	private TimeSlotService timeslotService;

	@Autowired
	private BookingService bookingService;

	/*public BookTimeSlot(BookingService bookingService,
			TimeSlotService timeslotService) {
		this.timeslotService = timeslotService;
		this.bookingService = bookingService;
	}*/

	public BookTimeSlot() {

	}

	Logger logger = Logger.getLogger(BookTimeSlot.class);

	/**
	 * This method for booking a timeslots for a receiver
	 * 
	 * @param receiverid
	 * @param ids
	 * @return Response
	 */

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/booking/timeslots/{id}", method = RequestMethod.PUT, produces = {
			MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_JSON_VALUE })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The timeslot successfully booked"),
			@ApiResponse(code = 404, message = "the timeslot is not booked"),
			@ApiResponse(code = 500, message = "There is problem in server") })
	@ApiOperation(value = "booking will done by the receiver", notes = "This resource will book the timeslot for particular receiver based on the receiver_id and timeslot_id")
	public ResponseEntity bookTimeSlot(
			@ApiParam(name = "receiverid", value = "The id of the receiver") @RequestParam("receiverid") int receiverid,
			@ApiParam(name = "id", value = "The id of the timeslot") @PathVariable("id") String ids) {
		logger.info("receiverid id  " + receiverid);
		logger.info("timeslot id  " + ids);
		logger.info("-----------------BOOKING A TIMESLOT is launched-----------------");
		String status = null;
		Message message = null;
		try {
			status = timeslotService.bookTimeSlot(receiverid, ids);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		if (status == null) {
			message = new Message();
			message.setStatus(500);
			message.setMessage("Something went wrong with booking");
			return ResponseEntity.status(500).body(message);
		} else {
			return ResponseEntity.status(200).body(status);
		}
	}

	/**
	 * This method is for confirming the bookings for particular receivers
	 * 
	 * @param ids
	 * @return
	 */

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/booking/confirm", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_HTML_VALUE })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The bookings confirmed"),
			@ApiResponse(code = 404, message = "the bookings not confirmed"),
			@ApiResponse(code = 500, message = "There is problem in server") })
	@ApiOperation(value = "booking confirmation will done by the provider", notes = "This resource confirms the bookings")
	public ResponseEntity confirmTimeSlot(@RequestBody Ids[] ids) {
		logger.info("timeslot id  " + ids);
		logger.info("-----------------------CONFIRM BOOKING launched-----------------");
		String status = null;
		Message message = null;
		try {
			status = timeslotService.confirmBooking(ids);
		} catch (Exception e) {

		}
		if (status == null) {
			message = new Message();
			message.setStatus(500);
			message.setMessage("Something went wrong with confirm bookings");
			return ResponseEntity.status(500).body(message);
		} else {
			return ResponseEntity.status(200).body(status);
		}
	}

	/**
	 * This method is cancel the timeslots for particular receivers
	 * 
	 * @param ids
	 * @return
	 */

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/booking/cancel", produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_HTML_VALUE }, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The bookings cancellation success"),
			@ApiResponse(code = 404, message = "the bookings cancelltion fail"),
			@ApiResponse(code = 500, message = "There is problem in server") })
	@ApiOperation(value = "booking cancellation will done by the provider", notes = "This resource will cancel the booking of a particuler timeslot_id")
	public ResponseEntity cancelTimeSlot(@RequestBody Ids[] ids) {
		String status = null;
		Message message = null;
		try {
			logger.info("--------------------CANCELLING BOOKING launched-----------------------");
			status = timeslotService.cancelBooking(ids);
		} catch (Exception e) {
			logger.info("message" + e.getMessage());
		}
		if (status == null) {
			message = new Message();
			message.setStatus(404);
			message.setMessage("Something went wrong with cancelling bookings");
			return ResponseEntity.status(500).body(message);
		} else {
			return ResponseEntity.status(200).body(status);
		}
	}

	/**
	 * This method is for modify the bookings
	 * 
	 * @param ids
	 * @return
	 */

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/booking/modify/{ids}", produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_HTML_VALUE }, method = RequestMethod.PUT)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The timeslots modified"),
			@ApiResponse(code = 404, message = "the timeslot is not modified"),
			@ApiResponse(code = 500, message = "There is problem in server") })
	@ApiOperation(value = "booking modifications will done by the provider", notes = "This resource will modifies the bookings")
	public ResponseEntity modifyTimeSlot(@PathVariable("ids") String ids) {
		String status = null;
		Message message = null;
		try {
			logger.info("--------------------MODIFYING BOOKING launched-----------------------");
			status = timeslotService.modifyBooking(ids);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		if (status == null) {
			message = new Message();
			message.setStatus(404);
			message.setMessage("Something went wrong with modifying bookings");
			return ResponseEntity.status(500).body(message);
		} else {
			return ResponseEntity.status(200).body(status);
		}
	}

	/**
	 * This method is for declining the bookings based on the IDs
	 * 
	 * @param ids
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/booking/decline/{ids}", produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_HTML_VALUE }, method = RequestMethod.PUT)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The decline process done"),
			@ApiResponse(code = 404, message = "the decline process fail"),
			@ApiResponse(code = 500, message = "There is problem in server") })
	@ApiOperation(value = "booking decline will done by the provider", notes = "This resource will bookings")
	public ResponseEntity declineTimeSlot(@PathVariable("ids") String ids) {
		String status = null;
		Message message = null;
		try {
			logger.info("--------------------DECLINE BOOKING launched-----------------------");
			status = timeslotService.declineBooking(ids);
		} catch (Exception e) {

		}
		if (status == null) {
			message = new Message();
			message.setStatus(404);
			message.setMessage("Something went wrong with decline booking");
			return ResponseEntity.status(500).body(message);
		} else {
			return ResponseEntity.status(200).body(status);
		}
	}

	/**
	 * This method gets the bookings need to confirm of a provider
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/booking/process/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The get bookings process done"),
			@ApiResponse(code = 404, message = "the get bookings process fail"),
			@ApiResponse(code = 500, message = "There is problem in server") })
	@ApiOperation(value = "provider will gets all the bookings need to accept", notes = "Getting all bookings need to acept")
	public ResponseEntity getBookings(@PathVariable("id") int id) {
		List<Bookings> processBookings = null;
		;
		Message message = null;
		try {
			logger.info("--------------------GETTING BOOKINGS launched-----------------------");
			processBookings = bookingService.getBookingsById(id);
		} catch (Exception e) {

		}
		if (processBookings == null) {
			message = new Message();
			message.setStatus(404);
			message.setMessage("Something went wrong with getting bookings");
			return ResponseEntity.status(500).body(message);
		} else {
			return ResponseEntity.status(200).body(processBookings);
		}
	}
}
