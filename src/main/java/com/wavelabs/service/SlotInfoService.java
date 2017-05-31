package com.wavelabs.service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wavelabs.dao.SlotInfoDao;
import com.wavelabs.model.Provider;
import com.wavelabs.model.SlotInfo;

@Service
public class SlotInfoService {

	@Autowired
	SlotInfoDao slotinfodao;


	public SlotInfoService() {

	}

	static Logger logger = Logger.getLogger(SlotInfoService.class);

	public List<SlotInfo> createSlots(Provider provider, Date fromdate, Date todate, Time fromtime, Time totime,
			int slotDuration) {
		logger.info("creation of availability is going on");
		SlotInfo slotinfo = new SlotInfo();
		slotinfo.setFromTime(fromtime);
		slotinfo.setToTime(totime);
		slotinfo.setProvider(provider);
		return slotinfodao.persistSlots(fromdate, todate, slotinfo, fromtime, totime, slotDuration);
	}

	/**
	 * this method will give you the all the list of dates from between two
	 * dates in the form of list
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Date> getListOfDates(Date startDate, Date endDate) {
		logger.info("getting list of dates from between two dates is going on");
		List<Date> dates = new ArrayList<Date>();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(startDate);
		while (calendar.getTime().before(endDate)) {
			Date result = calendar.getTime();
			dates.add(result);
			calendar.add(Calendar.DATE, 1);
		}
		dates.add(endDate);
		logger.info("done with getting list of dates");
		return dates;
	}

	/**
	 * This method gives you the all the slot data
	 * 
	 * @return
	 */
	public List<SlotInfo> getAllSlotData() {
		logger.info("getting slotdata is going on");
		return slotinfodao.getAllSlotDetails();
	}

}
