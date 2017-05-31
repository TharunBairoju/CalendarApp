package com.wavelabs.dao;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wavelabs.model.Bookings;
import com.wavelabs.model.Ids;
import com.wavelabs.model.Provider;
import com.wavelabs.model.Receiver;
import com.wavelabs.model.SlotInfo;
import com.wavelabs.model.Status;
import com.wavelabs.model.TimeSlots;
import com.wavelabs.service.BookingService;
import com.wavelabs.service.ReceiverService;
import com.wavelabs.service.TimeSlotService;
import com.wavelabs.util.SessionUtil;

/**
 * In this class persistind of timeslots and getting slots and updating the
 * timeslots will done based on the parameters
 * 
 * @author tharunkumarb
 *
 */

@Component
public class TimeSlotDao {
	@Autowired
	private ReceiverService receiverService;

	@Autowired
	private TimeSlotService timeslotService;

	@Autowired
	private BookingService bookingService;

	public TimeSlotDao() {

	}

	Logger logger = Logger.getLogger(TimeSlotDao.class);

	/**
	 * this will persist the timeslot based on the parameter and returns the
	 * TimeSlot
	 * 
	 * @param fromTime
	 * @param toTime
	 * @param slot
	 * @param consult
	 * @return
	 */

	public TimeSlots addTimeSlot(Time fromTime, Time toTime, SlotInfo slot) {
		Session session = SessionUtil.getSession();
		TimeSlots timeslot = new TimeSlots();
		timeslot.setFromTime(fromTime);
		timeslot.setStatus(Status.available);
		timeslot.setSlot(slot);
		session.save(timeslot);
		session.beginTransaction().commit();
		session.close();
		return timeslot;
	}

	/**
	 * This method will persist the all the timeslots with its particular
	 * slotinfo
	 * 
	 * @param slotinfo
	 * @param timings
	 */

	public void persistTimeslots(SlotInfo slotinfo, List<Time> timings) {
		Session session = SessionUtil.getSession();
		for (int i = 0; i < timings.size(); i++) {
			TimeSlots timeslot = new TimeSlots();
			System.out.println(timings.get(i));
			timeslot.setFromTime(timings.get(i));
			timeslot.setStatus(Status.empty);
			timeslot.setSlot(slotinfo);
			session.save(timeslot);
			session.flush();
		}
		session.close();
		logger.info("Done with persisting the timeslots");
	}

	/**
	 * This will gets the all the time slots from the database and returns the
	 * list of timeslots
	 * 
	 * @return
	 */
	public List<TimeSlots> getAllTimeSlots() {
		Session session = SessionUtil.getSession();
		@SuppressWarnings("unchecked")
		List<TimeSlots> timeslotlist = session.createCriteria(TimeSlots.class)
				.list();
		session.close();
		return timeslotlist;
	}

	/**
	 * This method will gets the all the available timeslots
	 * 
	 * @return
	 */
	public List<TimeSlots> getAllAvailableTimeSlots() {
		Session session = SessionUtil.getSession();
		Criteria criteria = session.createCriteria(TimeSlots.class);
		criteria.add(Restrictions.eq("status", Status.available));
		@SuppressWarnings("unchecked")
		List<TimeSlots> availableTimeSlots = criteria.list();
		session.close();
		return availableTimeSlots;
	}

	/**
	 * This method will gets the all the booked slots
	 * 
	 * @return
	 */
	public List<TimeSlots> getAllBookedTimeSlots() {
		Session session = SessionUtil.getSession();
		Criteria criteria = session.createCriteria(TimeSlots.class);
		criteria.add(Restrictions.eq("status", Status.booked));
		@SuppressWarnings("unchecked")
		List<TimeSlots> bookedTimeSlots = criteria.list();
		session.close();
		return bookedTimeSlots;
	}

	/**
	 * This method will gets the available timeslot for specified Slotinfo
	 * 
	 * @param slotid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TimeSlots> getAvaialbleTimeSlotsPerSlot(int slotid) {
		Session session = SessionUtil.getSession();
		SlotInfo slot = (SlotInfo) session.get(SlotInfo.class, slotid);
		Criteria criteria = SessionUtil.getSession().createCriteria(
				TimeSlots.class);
		criteria.add(Restrictions.eq("slot.id", slot.getId()));
		criteria.add(Restrictions.eq("status", Status.available));
		List<TimeSlots> timelotList = criteria.list();
		logger.info("Done with getting all available timeslots of a slot");
		session.close();
		return timelotList;

	}

	/**
	 * this method will gets the all the booked slots for specified slot
	 * 
	 * @param slotid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TimeSlots> getBookedTimeSlotsPerSlot(int slotid) {
		Session session = SessionUtil.getSession();
		SlotInfo slot = (SlotInfo) session.get(SlotInfo.class, slotid);
		Criteria criteria = SessionUtil.getSession().createCriteria(
				TimeSlots.class);
		criteria.add(Restrictions.eq("slot.id", slot.getId()));
		criteria.add(Restrictions.eq("status", Status.booked));
		List<TimeSlots> timelotList = criteria.list();
		logger.info("Done with getting all booked timeslots a slot");
		session.close();
		return timelotList;
	}

	/**
	 * This method will gets the Timeslot based on the id
	 * 
	 * @param id
	 * @return
	 */
	public TimeSlots getTimeslotById(int id) {
		Session session = SessionUtil.getSession();
		logger.info("Geting timeslot by id is going on");
		TimeSlots timeslot=(TimeSlots) session.get(TimeSlots.class, id);
		session.close();
		return timeslot;
	}

	public String confirmBooking(Ids[] ids) {
		Session session = SessionUtil.getSession();
		List<String> msgData = new ArrayList<String>();
		List<TimeSlots> timeslotlist = new ArrayList<TimeSlots>();
		try {
			for (int i = 0; i < ids.length; i++) {
				Receiver receiver = receiverService.getReceiverById(ids[i]
						.getReceiverId());
				TimeSlots timeslot = timeslotService.getTimeSlotById(ids[i]
						.getTimeslotId());
				Provider provider = timeslot.getSlot().getProvider();
				String status = Status.booked.toString();
				timeslot.setStatus(Status.booked);
				timeslot.setReceiver(receiver);
				session.update(timeslot);
				session.flush();
				msgData.add(receiver.getName() + ","
						+ receiver.getPhoneNumber() + "," + receiver.getEmail()
						+ "," + provider.getName() + "," + status + ","
						+ timeslot.getFromTime().toString());

				Bookings booking = bookingService.getBooking(receiver.getId(),
						timeslot.getId());
				booking.setStatus(Status.booked);
				session.update(booking);
				session.flush();
				session.beginTransaction().commit();
				timeslotlist.add(timeslot);
				logger.info("Done with confirm booking process");
			}
			msgData.addAll(bookingService
					.cancelForRemainingReceivers(timeslotlist));
			CommunicationThread cthread = new CommunicationThread(msgData);
			Thread thread = new Thread(cthread);
			thread.start();

			return " booking confirmation success";
		} catch (Exception e) {
			return "booking confirmation fail";
		}finally{
			session.close();
		}
	}

	/**
	 * This method will cancels the bookings based on the ids
	 * 
	 * @param ids
	 * @return String
	 */
	public String cancelBooking(Ids[] ids) {
		Session session = SessionUtil.getSession();
		List<String> msgData = new ArrayList<String>();
		try {
			for (int i = 0; i < ids.length; i++) {
				Receiver receiver = receiverService.getReceiverById(ids[i]
						.getReceiverId());
				TimeSlots timeslot = timeslotService.getTimeSlotById(ids[i]
						.getTimeslotId());
				Provider provider = timeslot.getSlot().getProvider();
				String status = Status.cancel.toString();
				Bookings booking = bookingService.getBooking(receiver.getId(),
						timeslot.getId());
				booking.setStatus(Status.cancel);
				session.update(booking);
				session.flush();
				List<Bookings> bookingslist = bookingService
						.getBookingsByTimeslot(timeslot);
				if (bookingslist.size() == 0) {
					timeslot.setStatus(Status.available);
					session.update(timeslot);
					session.flush();
					session.beginTransaction().commit();
				}
				msgData.add(receiver.getName() + ","
						+ receiver.getPhoneNumber() + "," + receiver.getEmail()
						+ "," + provider.getName() + "," + status + ","
						+ timeslot.getFromTime().toString());
				logger.info("Done with cancel booking process");
			}
			logger.info(msgData);
			CommunicationThread cthread = new CommunicationThread(msgData);
			Thread thread = new Thread(cthread);
			thread.start();
			return " booking cancellation success";
		} catch (Exception e) {
			e.printStackTrace();
			return "booking cancellation fail";
		}
	}

	/**
	 * This methods will gets the list of timeslots
	 * 
	 * @param ids
	 * @return
	 */
	public List<TimeSlots> getTimeslots(int[] ids) {
		Session session = SessionUtil.getSession();
		List<Integer> listOfIds = new ArrayList<Integer>();
		for (int i = 0; i < ids.length; i++) {
			listOfIds.add(ids[i]);
		}
		Criteria criteria = session.createCriteria(TimeSlots.class);
		criteria.add(Restrictions.in("id", listOfIds));
		@SuppressWarnings("unchecked")
		List<TimeSlots> timeslots = criteria.list();
		return timeslots;
	}

	/**
	 * this method will creates the availability means it makes the timeslots as
	 * available
	 * 
	 * @param intids
	 * @return
	 */
	public String createAvailability(int[] intids) {
		Session session = SessionUtil.getSession();
		List<TimeSlots> timeslots = getTimeslots(intids);
		try {
			for (TimeSlots timeSlot : timeslots) {
				timeSlot.setStatus(Status.available);
				session.update(timeSlot);
				session.flush();
				session.clear();
			}
			session.beginTransaction().commit();
			return "success";
		} catch (HibernateException e) {
			return "fail";
		}

	}

	/**
	 * This method will book the timeslot for particular consultant and also
	 * updates the no of booked column in slotinfo table
	 * 
	 * @param consultantid
	 * @param timeslotid
	 * @return
	 * @throws URISyntaxException
	 */
	public String updateTimeslot(Receiver receiver, int[] ids) {
		Session session = SessionUtil.getSession();
		List<String> msgData = new ArrayList<String>();
		String status = "";
		List<TimeSlots> timeslots = timeslotService.getTimeSlotList(ids);
		String times = "";
		try {
			for (TimeSlots timeslot : timeslots) {
				try {
					if (isAvailable(timeslot)) {
						logger.info("timeslot availablle");
						if (checkBookings(timeslot.getSlot().getDate(),
								timeslot.getFromTime(), receiver)) {
							logger.info("booking allowed");
							times = times + timeslot.getFromTime() + " ";
							timeslot.setStatus(Status.process);
							Bookings bookings = new Bookings();
							bookings.setReceiver(receiver);
							bookings.setTimeslot(timeslot);
							bookings.setStatus(Status.process);
							session.save(bookings);
							session.flush();
							session.update(timeslot);
							session.flush();
							status = Status.process.toString();
							logger.info("booking process done");
							msgData.add(receiver.getName()
									+ ","
									+ receiver.getPhoneNumber()
									+ ","
									+ receiver.getEmail()
									+ ","
									+ timeslot.getSlot().getProvider()
											.getName() + "," + status + ","
									+ timeslot.getFromTime().toString());
						} else {
							logger.info("booking not allowed");
							status = "You already booked this time and date with someother bookie"
									+ timeslot.getId() + "\t";
						}
					} else {
						status = "Empty timeslot booking is not allowed"
								+ timeslot.getId();
					}
				} catch (HibernateException e) {
					logger.info("Booking process fail");
				}
				session.beginTransaction().commit();
			}

			CommunicationThread cthread = new CommunicationThread(msgData);
			cthread.start();
			return status + "booking sucess";
		} catch (Exception e) {
			return "booking fail";
		}
	}

	/**
	 * This method gets the timeslots of a provider
	 * 
	 * @param provider
	 * @return
	 */
	public List<TimeSlots> getTimeslotsList(Provider provider) {
		Session session = SessionUtil.getSession();
		Criteria criteria = session.createCriteria(TimeSlots.class);
		criteria.createAlias("slot", "slot");
		criteria.createAlias("slot.provider", "provider");
		criteria.add(Restrictions.eq("provider.id", provider.getId()));
		@SuppressWarnings("unchecked")
		List<TimeSlots> timeslots = criteria.list();
		return timeslots;
	}

	/**
	 * this method gets the lis of timeslots need to accept of a provider
	 * 
	 * @param provider
	 * @return
	 */
	public List<TimeSlots> getTimeslotsBookingInfo(Provider provider) {
		Session session = SessionUtil.getSession();
		Criteria criteria = session.createCriteria(TimeSlots.class);
		criteria.createAlias("slot", "slot");
		criteria.createAlias("slot.provider", "provider");
		criteria.add(Restrictions.eq("provider.id", provider.getId()));
		criteria.add(Restrictions.eq("status", Status.process));
		@SuppressWarnings("unchecked")
		List<TimeSlots> timeslots = criteria.list();
		return timeslots;
	}

	/**
	 * This method will modifys the bookings in the calendar
	 * 
	 * @param ids
	 * @return
	 */
	public String modifyBookings(String ids) {
		List<TimeSlots> timeslotList = timeslotService
				.getTimeSlotList(timeslotService.getIds(ids));
		List<TimeSlots> processTimeslots = timeslotList.stream()
				.filter(timeslot -> timeslot.getStatus() == Status.process)
				.collect(Collectors.toList());
		List<TimeSlots> availableList = timeslotList.stream()
				.filter(timeslot -> timeslot.getStatus() == Status.available)
				.collect(Collectors.toList());
		List<TimeSlots> emptyTimeslots = timeslotList.stream()
				.filter(timeslot -> timeslot.getStatus() == Status.empty)
				.collect(Collectors.toList());
		try {
			try {
				makeEmptyOfProcessTimeslots(processTimeslots);
				logger.info("done with process modifications");
			} catch (Exception e) {
				logger.info("there is process timeslots");
			}
			try {
				makeEmptyOfAvailableTimeslots(emptyTimeslots);
				logger.info("done with empty slot modifications");
			} catch (Exception e) {
				logger.info("there is no empty timeslots");
			}
			try {
				makeAvailableOfEmptyTimeslots(availableList);
				logger.info("Done with available slot modifications");
			} catch (Exception e) {
				logger.info("there is no available timeslots");
			}
			return "Done with Modifications";
		} catch (Exception e) {
			return "Something happen in modifications";
		}
	}

	/**
	 * this method will make the available timeslots to empty
	 * 
	 * @param availableList
	 */
	private void makeAvailableOfEmptyTimeslots(List<TimeSlots> availableList) {
		Session session = SessionUtil.getSession();
		for (TimeSlots timeSlots : availableList) {
			timeSlots.setStatus(Status.empty);
			session.update(timeSlots);
			session.flush();
			session.clear();
		}
		session.beginTransaction().commit();

	}

	/**
	 * this method make the empty timeslots as available
	 * 
	 * @param emptyTimeslots
	 */

	private void makeEmptyOfAvailableTimeslots(List<TimeSlots> emptyTimeslots) {
		Session session = SessionUtil.getSession();
		for (TimeSlots timeSlots : emptyTimeslots) {
			timeSlots.setStatus(Status.available);
			session.update(timeSlots);
			session.flush();
			session.clear();
		}
		session.beginTransaction().commit();

	}

	/**
	 * This method makes the proess timeslots as empty
	 * 
	 * @param processTimeslots
	 */

	private void makeEmptyOfProcessTimeslots(List<TimeSlots> processTimeslots) {
		Session session = SessionUtil.getSession();
		List<String> msgData = new ArrayList<String>();
		for (TimeSlots timeSlots : processTimeslots) {
			Provider provider = timeSlots.getSlot().getProvider();
			List<Bookings> bookingList = bookingService
					.getBookingsByTimeslot(timeSlots);
			for (Bookings bookings : bookingList) {
				Receiver receiver = bookings.getReceiver();
				String status = Status.decline.toString();
				bookings.setStatus(Status.decline);
				session.update(bookings);
				session.flush();
				session.clear();
				msgData.add(receiver.getName() + ","
						+ receiver.getPhoneNumber() + "," + receiver.getEmail()
						+ "," + provider.getName() + "," + status + ","
						+ timeSlots.getFromTime().toString());
				logger.info("Done with cancel booking process");

			}
			timeSlots.setStatus(Status.empty);
			session.update(timeSlots);
			session.flush();
			session.beginTransaction().commit();
			CommunicationThread cthread = new CommunicationThread(msgData);
			Thread thread = new Thread(cthread);
			thread.start();

		}
	}

	/**
	 * this method will decline bookings process
	 * 
	 * @param ids
	 * @return
	 */
	public String declineBookings(String ids) {
		Session session = SessionUtil.getSession();
		List<String> msgData = new ArrayList<String>();
		List<TimeSlots> timeslotList = timeslotService
				.getTimeSlotList(timeslotService.getIds(ids));
		String message = "";
		for (TimeSlots timeSlots : timeslotList) {
			Date date1 = timeSlots.getSlot().getDate();
			System.out.println(date1);
			String stringdate = LocalDate.now().toString();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date2 = null;
			try {
				date2 = sdf.parse(stringdate);
			} catch (ParseException e) {
				logger.info("wrong in date parsing");
			}
			if (date1.compareTo(date2) > 0) {
				logger.info("decline allowed");
				Bookings booking = bookingService.getBookedBookings(timeSlots);
				Provider provider = timeSlots.getSlot().getProvider();
				Receiver receiver = booking.getReceiver();
				String status = Status.decline.toString();
				booking.setStatus(Status.decline);
				session.update(booking);
				session.flush();
				msgData.add(receiver.getName() + ","
						+ receiver.getPhoneNumber() + "," + receiver.getEmail()
						+ "," + provider.getName() + "," + status + ","
						+ timeSlots.getFromTime().toString());
				logger.info("Done with decline booking process");
				timeSlots.setStatus(Status.empty);
				timeSlots.setReceiver(null);
				session.update(timeSlots);
				session.flush();
				session.beginTransaction().commit();
			} else {
				message = "Decline not allowed for this timeslot\t";
			}
		}
		CommunicationThread cthread = new CommunicationThread(msgData);
		Thread thread = new Thread(cthread);
		thread.start();
		return message + "Done with decline process";
	}

	/**
	 * This metho will checks the bookings
	 * 
	 * @param date
	 * @param fromTime
	 * @param receiver
	 * @return boolean
	 */
	private boolean checkBookings(Date date, Time fromTime, Receiver receiver) {
		Bookings bookings = null;
		try {
			Criteria criteria = SessionUtil.getSession().createCriteria(
					Bookings.class);
			criteria.createAlias("timeslot", "timeslot");
			criteria.add(Restrictions.eq("timeslot.from_time", fromTime));
			criteria.createAlias("timeslot.slot", "slot");
			criteria.add(Restrictions.eq("slot.date", date));
			criteria.add(Restrictions.eq("receiver.id", receiver.getId()));
			criteria.add(Restrictions.or(
					Restrictions.eq("status", Status.process),
					Restrictions.eq("status", Status.booked)));
			bookings = (Bookings) criteria.uniqueResult();
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		if (bookings == null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * this method checks the timeslot is available or not
	 * 
	 * @param timeslot
	 * @return boolean
	 */
	private static boolean isAvailable(TimeSlots timeslot) {
		if (timeslot.getStatus() == Status.available
				|| timeslot.getStatus() == Status.process) {
			return true;
		} else {
			return false;
		}
	}
}
