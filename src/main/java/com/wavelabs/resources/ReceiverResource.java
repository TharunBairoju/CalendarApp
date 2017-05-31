package com.wavelabs.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wavelabs.model.Message;
import com.wavelabs.model.Receiver;
import com.wavelabs.service.ReceiverService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * This class actions of the receiver
 * 
 * @author tharunkumarb
 *
 */
@RestController
public class ReceiverResource {

	@Autowired
	private ReceiverService receiverService;

	public ReceiverResource() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * This method is for persisting the receiver
	 * 
	 * @param receiver
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/receiver", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Persist the booker", notes = "This API will persist the recevier which takes the input in the form of JSON")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The receiver persisted successfully"),
			@ApiResponse(code = 404, message = "The receiver not persisted"),
			@ApiResponse(code = 500, message = "There is some problem in server") })
	public ResponseEntity persistReceiver(@RequestBody Receiver receiver) {
		Receiver persistedReceiver = receiverService.createReceiver(receiver);
		if (persistedReceiver == null) {
			Message message = new Message();
			message.setStatus(500);
			message.setMessage("Something went wrong with persisting receiver");
			return ResponseEntity.status(500).body(message);
		} else {
			return ResponseEntity.status(200).body(persistedReceiver);
		}
	}
}
