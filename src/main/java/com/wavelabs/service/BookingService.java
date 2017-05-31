package com.wavelabs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wavelabs.dao.BookingsDao;
import com.wavelabs.model.Bookings;
import com.wavelabs.model.Provider;
import com.wavelabs.model.TimeSlots;

/**
 * This class contains the all the services of the bookings
 * 
 * @author tharunkumarb
 *
 */
@Service
public class BookingService {

	@Autowired
	private BookingsDao bookingsdao;

	@Autowired
	private ProviderService providerService;

	public BookingService() {

	}

	public Bookings getBooking(int receiverid, int timeslotid) {
		Bookings booking = bookingsdao.getBooking(receiverid, timeslotid);
		return booking;
	}

	public Bookings getBookingByStatus(int receiverid, int timeslotid) {
		return bookingsdao.getBookingByStatus(receiverid, timeslotid);
	}

	public List<String> cancelForRemainingReceivers(List<TimeSlots> timeslotlist) {
		return bookingsdao.cancelBookingForRemaining(timeslotlist);
	}

	public List<Bookings> getBookingsByTimeslot(TimeSlots timeslot) {
		return bookingsdao.getBookingByTimeslot(timeslot);
	}

	public Bookings getBookedBookings(TimeSlots timeSlots) {
		return bookingsdao.getBookedBookings(timeSlots);
	}

	public List<Bookings> getBookingsById(int id) {
		Provider provider = providerService.getProviderById(id);
		return bookingsdao.getBookings(provider);
	}
}
