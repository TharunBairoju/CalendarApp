package com.wavelabs.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wavelabs.dao.ReceiverDao;
import com.wavelabs.model.Receiver;

@Service
public class ReceiverService {

	@Autowired
	private ReceiverDao receiverdao;
	
	public ReceiverService(){
		
	}

	Logger logger = Logger.getLogger(Receiver.class);

	public Receiver createReceiver(Receiver receiver) {
		logger.info("persisting receiver is going on");
		return receiverdao.persistReceiver(receiver);
	}

	public Receiver getReceiverById(int id) {
		logger.info("getting receiver by id is going on");
		return receiverdao.getReceiverById(id);
	}
}
