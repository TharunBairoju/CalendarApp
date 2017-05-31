package com.wavelabs.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.wavelabs.model.Receiver;
import com.wavelabs.util.SessionUtil;

/**
 * In this dao class all the operation related to Consultant will be done based
 * on the input parameters
 * 
 * @author tharunkumarb
 *
 */

@Component
public class ReceiverDao {
	
	Logger logger = Logger.getLogger(ReceiverDao.class);

	/**
	 * This method will persist the consultant based on the input parameter
	 * 
	 * @param name
	 * @param email
	 * @param phoneNumber
	 * @param address
	 * @param password
	 * @return
	 */
	public Receiver addReceiver(String name, String email, long phoneNumber, String address, String password) {
		Session session = SessionUtil.getSession();
		Receiver consultant = new Receiver();
		consultant.setName(name);
		consultant.setEmail(email);
		consultant.setPhoneNumber(phoneNumber);
		consultant.setAddress(address);
		consultant.setPassword(password);
		session.save(consultant);
		session.beginTransaction().commit();
		session.close();
		return consultant;
	}

	/**
	 * This method will gets the all the consultants in the form of list
	 * 
	 * @return
	 */
	public List<Receiver> getAllReceiver() {
		Session session = SessionUtil.getSession();
		@SuppressWarnings("unchecked")
		List<Receiver> list = session.createCriteria(Receiver.class).list();
		logger.info("Done with getting receviers list");
		session.close();
		return list;
	}

	/**
	 * This will persist the consultant
	 * 
	 * @param receiver
	 * @return
	 */
	public Receiver persistReceiver(Receiver receiver) {
		Session session = SessionUtil.getSession();
		session.save(receiver);
		session.beginTransaction().commit();
		logger.info("Done with persisting the receiver");
		session.close();
		return receiver;
	}

	/**
	 * This will get the Consultant by its id
	 * 
	 * @param id
	 * @return
	 */
	public Receiver getReceiverById(int id) {
		Session session = SessionUtil.getSession();
		Receiver consult = (Receiver) session.get(Receiver.class, id);
		logger.info("Done with getting receiver by id");
		session.close();
		return consult;
	}
}
