package com.wavelabs.dao;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wavelabs.model.Provider;
import com.wavelabs.model.SlotInfo;
import com.wavelabs.service.SlotInfoService;
import com.wavelabs.service.TimeSlotService;
import com.wavelabs.util.SessionUtil;

/**
 * This class will perform actions of slotinfo
 * 
 * @author tharunkumarb
 *
 */

@Component
public class SlotInfoDao {
	@Autowired
	private TimeSlotService timeslotService;
	@Autowired
	private SlotInfoService slotinfoService;

	public SlotInfoDao() {

	}

	
	private Logger logger = Logger.getLogger(SlotInfoDao.class);

	/**
	 * this will persist the single slot with particular doctor
	 * 
	 * @param date
	 * @param fromTime
	 * @param toTime
	 * @param noOfTimeSlots
	 * @param noOfBooked
	 * @param status
	 * @param provider
	 * @return
	 */
	public SlotInfo addSlot(Date date, Time fromTime, Time toTime,
			int noOfTimeSlots, int noOfBooked, Provider provider) {
		Session session = SessionUtil.getSession();
		SlotInfo slotinfo = new SlotInfo();
		slotinfo.setFromTime(fromTime);
		slotinfo.setToTime(toTime);
		slotinfo.setProvider(provider);
		slotinfo.setDate(date);
		session.save(slotinfo);
		session.beginTransaction().commit();
		session.close();
		return slotinfo;
	}

	/**
	 * this will persist the Slots
	 * 
	 * @param slotinfo
	 * @return
	 */
	public SlotInfo persistSlotInfo(SlotInfo slotinfo) {
		Session session = SessionUtil.getSession();
		try {
			session.save(slotinfo);
			session.beginTransaction().commit();
			logger.info("Done with persisting the slotinfo");
		} catch (Exception e) {
			logger.warn("Something went wrong in persisting the slotinfo");
			logger.info("Reason:" + e.getMessage());
		}finally{
			session.close();
		}

		return slotinfo;
	}

	/**
	 * This method will fetch the all the slots data in the form of list
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SlotInfo> getAllSlotDetails() {
		Session session = SessionUtil.getSession();
		try {
			Criteria criteria = session.createCriteria(SlotInfo.class);
			List<SlotInfo> slotList = criteria.list();
			logger.info("Done with getting slotinfo list");
			return slotList;
		} catch (Exception e) {
			List<SlotInfo> slotlist = new ArrayList<>();
			logger.warn("Something went wrong while getting slotinfo with criteria");
			logger.info("Reason " + e.getMessage());
			return slotlist;
		}finally{
			session.close();
		}
	}

	/**
	 * This method will persist the all the slots with particular doctor
	 * 
	 * @param name
	 * @param phno
	 * @param date1
	 * @param date2
	 * @param slotinfo
	 * @return
	 */
	public List<SlotInfo> persistSlots(Date date1, Date date2,
			SlotInfo slotinfo, Time startTime, Time endTime, int slotDuration) {
		Session session = SessionUtil.getSession();
		List<SlotInfo> listOfSlots = new ArrayList<SlotInfo>();
		try {
			List<Time> timingslist = timeslotService.getTimings(startTime,
					endTime, slotDuration);
			List<Date> datesList = slotinfoService.getListOfDates(date1, date2);
			for (int i = 0; i < datesList.size(); i++) {
				slotinfo.setDate(datesList.get(i));
				session.save(slotinfo);
				session.flush();
				listOfSlots.add(slotinfo);
				timeslotService.createTimeSlots(slotinfo, timingslist);
				session.clear();
			}
			session.beginTransaction().commit();
		} catch (Exception e) {
			logger.warn("Something went wrong in the slot creation");
			logger.info("Reason :" + e.getMessage());
			List<SlotInfo> slotlist = new ArrayList<SlotInfo>();
			return slotlist;
		}finally{
			session.close();
		}
		return listOfSlots;
	}
}
