package com.wavelabs.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.wavelabs.model.Bookings;
import com.wavelabs.model.Provider;
import com.wavelabs.model.Status;
import com.wavelabs.model.TimeSlots;
import com.wavelabs.util.SessionUtil;

/**
 * This class will interact with data base
 * 
 * @author tharunkumarb
 *
 */

@Component
public class BookingsDao {

	public BookingsDao() {

	}

	Logger logger = Logger.getLogger(BookingsDao.class);

	/**
	 * This method will gets the booking using receiverid and the timeslotid
	 * 
	 * @param receiverid
	 * @param timeslotid
	 * @return
	 */
	public Bookings getBooking(int receiverid, int timeslotid) {
		Session session = SessionUtil.getSession();
		Bookings booking = null;
		try {
			Criteria criteria = session.createCriteria(Bookings.class);
			criteria.add(Restrictions.eq("receiver.id", receiverid));
			criteria.add(Restrictions.eq("timeslot.id", timeslotid));
			criteria.add(Restrictions.eq("status", Status.process));
			booking = (Bookings) criteria.uniqueResult();
			session.close();
		} catch (Exception e) {
			logger.info("something went wrong getting bookings");
			logger.info("cause " + e.getCause());
		}
		return booking;
	}

	/**
	 * This method will gets the bookings by timeslots
	 * 
	 * @param timeslot
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Bookings> getBookingByTimeslot(TimeSlots timeslot) {
		Session session = SessionUtil.getSession();
		List<Bookings> bookingslist = new ArrayList<Bookings>();
		try {
			Criteria criteria = session.createCriteria(Bookings.class);
			criteria.add(Restrictions.eq("timeslot.id", timeslot.getId()));
			criteria.add(Restrictions.eq("status", Status.process));
			bookingslist = criteria.list();
		} catch (Exception e) {
			logger.info("something went wrong getting bokings");
			logger.info("cause " + e.getCause());
		} finally {
			session.close();
		}

		return bookingslist;
	}

	/**
	 * This method will cancel the bookings
	 * 
	 * @param timeslotlist
	 * @return List<String>
	 */
	public List<String> cancelBookingForRemaining(List<TimeSlots> timeslotlist) {
		Session session = SessionUtil.getSession();
		List<String> msgData = new ArrayList<String>();
		for (TimeSlots timeslot : timeslotlist) {
			List<Bookings> bookinglist = getBookingByTimeslot(timeslot);
			for (Bookings bookings : bookinglist) {
				bookings.setStatus(Status.cancel);
				session.update(bookings);
				session.flush();
				msgData.add(bookings.getReceiver().getName() + ","
						+ bookings.getReceiver().getPhoneNumber() + ","
						+ bookings.getReceiver().getEmail() + ","
						+ timeslot.getSlot().getProvider().getName() + ","
						+ Status.cancel.toString() + ","
						+ timeslot.getFromTime().toString());
			}
			session.beginTransaction().commit();
		}
		session.close();
		return msgData;
	}

	/**
	 * This method gets the method the by status
	 * 
	 * @param receiverid
	 * @param timeslotid
	 * @return Bookings
	 */
	public Bookings getBookingByStatus(int receiverid, int timeslotid) {
		Session session = SessionUtil.getSession();
		Bookings booking = null;
		try {
			Criteria criteria = session.createCriteria(Bookings.class);
			criteria.add(Restrictions.eq("receiver.id", receiverid));
			criteria.add(Restrictions.eq("timeslot.id", timeslotid));
			criteria.add(Restrictions.or(
					Restrictions.eq("status", Status.process),
					Restrictions.eq("status", Status.booked)));
			booking = (Bookings) criteria.uniqueResult();
		} catch (Exception e) {
			logger.info("something went wrong getting bokings");
			logger.info("cause " + e.getCause());
		}finally{
			session.close();
		}
		return booking;

	}

	/**
	 * This method gets the bookings by timeslots
	 * 
	 * @param timeSlots
	 * @return Bookings
	 */
	public Bookings getBookedBookings(TimeSlots timeSlots) {
		Session session = SessionUtil.getSession();
		Bookings booking = null;
		try {
			Criteria criteria = session.createCriteria(Bookings.class);
			criteria.add(Restrictions.eq("timeslot.id", timeSlots.getId()));
			criteria.add(Restrictions.eq("status", Status.booked));
			booking = (Bookings) criteria.uniqueResult();
		} catch (Exception e) {
			logger.info("something went wrong getting bokings");
			logger.info("cause " + e.getCause());
		}finally{
			session.close();
		}
		return booking;
	}

	/**
	 * This method gets the list of bookings based on the provider
	 * 
	 * @param provider
	 * @return List<Bookings>
	 */
	public List<Bookings> getBookings(Provider provider) {
		Session session = SessionUtil.getSession();
		logger.info("getting bookings");
		Criteria criteria = session.createCriteria(Bookings.class);
		criteria.createAlias("timeslot", "timeslot");
		criteria.createAlias("timeslot.slot", "slot");
		criteria.createAlias("slot.provider", "provider");
		criteria.add(Restrictions.eq("provider.id", provider.getId()));
		criteria.add(Restrictions.eq("status", Status.process));
		@SuppressWarnings("unchecked")
		List<Bookings> bookings = criteria.list();
		logger.info(bookings);
		session.close();
		return bookings;
	}
}
