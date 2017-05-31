package com.wavelabs.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wavelabs.dao.CommunicationDao;
import com.wavelabs.model.Status;

@Service
public class CommunicationService {

	@Autowired
	private CommunicationDao commdao;
	
	public CommunicationService(){
		
	}

	/*public CommunicationService(CommunicationDao commdao) {
		this.commdao = commdao;
	}*/

	Logger logger = Logger.getLogger(CommunicationService.class);

	public void sendData(String rName, String phno, String email, String pName,
			String times, String status) {
		String message = null;
		if (status.equals(Status.booked.toString())) {
			message = getSuccessMessage(rName, pName, times);
			System.out.println(message);
		}
		if (status.equals(Status.available.toString())) {
			message = getFailureMessage(rName, pName, times);
		}
		if (status.equals(Status.process.toString())) {
			message = getProcessMessage(rName, pName, times);
		}
		if (status.equals(Status.cancel.toString())) {
			message = getFailureMessage(rName, pName, times);
		}
		if (status.equals(Status.decline.toString())) {
			message = getDeclineMessage(rName, pName, times);
		}
		String phonenumber = "+91" + phno;
		try {
			commdao.sendSMS(phonenumber, message);
			commdao.sendMail(email, message);
			logger.info("SMS and Mail sending done");
		} catch (Exception e) {
			logger.info("something went wrong while sending messages & mails");
		}
	}

	public void send(List<String> msglist) {
		String rName = "";
		String phno = "";
		String email = "";
		String pName = "";
		String time = "";
		String status = "";
		for (String msgdata : msglist) {
			String[] msgMetaData = msgdata.split(",");
			rName = msgMetaData[0];
			phno = msgMetaData[1];
			email = msgMetaData[2];
			pName = msgMetaData[3];
			time = msgMetaData[5];
			status = msgMetaData[4];
			logger.info(rName);
			logger.info(phno);
			logger.info(email);
			logger.info(pName);
			logger.info(time);
			logger.info(status);
			sendData(rName, phno, email, pName, time, status);
		}
	}

	public String getSuccessMessage(String rName, String pName, String times) {
		String message = "Congratulations\nHi " + rName.toUpperCase()
				+ "\nBooking confirmed with the provider \nMr." + pName
				+ "\nTimings: " + times + "\nPlease be on time";
		System.out.println("message generated");
		logger.info("Done with success message creation");
		return message;

	}

	public String getFailureMessage(String rName, String pName, String times) {
		String message = "Sorry for this..\nHi " + rName.toUpperCase()
				+ "\nBooking request is cancelled with the provider \nMr."
				+ pName + "\nTimings: " + times
				+ "\nPlease kindly book an another timeslots";
		logger.info("done with failure message creation");
		return message;
	}

	public String getProcessMessage(String rName, String pName, String times) {
		String message = "Congratulations\nHi "
				+ rName.toUpperCase()
				+ "\nBooking request is under process.. with the provider \nMr."
				+ pName + "\nTimings: " + times
				+ "\nwill get back to you with update...";
		logger.info("done with process message creation");
		return message;
	}

	public String getDeclineMessage(String rName, String pName, String times) {
		String message = "Sorry for this..\nHi " + rName.toUpperCase()
				+ "\nBooking request is decline with the provider \nMr."
				+ pName + "\nTimings: " + times
				+ "\nPlease kindly book an another timeslots";
		logger.info("done with failure message creation");
		return message;
	}

}
