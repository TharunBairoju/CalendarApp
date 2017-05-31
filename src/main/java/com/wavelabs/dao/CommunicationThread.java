package com.wavelabs.dao;

import java.util.ArrayList;
import java.util.List;

import com.wavelabs.service.CommunicationService;

public class CommunicationThread extends Thread {
	public List<String> msglist=new ArrayList<>();

	private CommunicationService communicationService=new CommunicationService();

	public CommunicationThread(List<String> commlist) {
		this.msglist = commlist;
	}

	@Override
	public void run() {
		communicationService.send(msglist);
	}
}
