package com.wavelabs.service.test;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.wavelabs.dao.BookingsDao;
import com.wavelabs.model.Bookings;
import com.wavelabs.model.Provider;
import com.wavelabs.model.TimeSlots;					
import com.wavelabs.service.BookingService;
import com.wavelabs.service.ProviderService;

@RunWith(MockitoJUnitRunner.class)
public class BookingServiceTest {

	@Mock
	BookingsDao bookingsdao;
	
	@Mock
	ProviderService providerService;
	
	@InjectMocks
	private BookingService bookingService;

	@Test
	public void testGetBooking() {
		Bookings bookings = DataBuilder.getBookings();
		when(bookingsdao.getBooking(anyInt(), anyInt())).thenReturn(bookings);
		Bookings booking = bookingService.getBooking(1, 1);
		Assert.assertEquals(bookings.getId(), booking.getId());
	}

	@Test
	public void testGetBookingByStatus() {
		Bookings bookings = DataBuilder.getBookings();
		when(bookingsdao.getBookingByStatus(anyInt(), anyInt())).thenReturn(bookings);
		Bookings booking = bookingService.getBookingByStatus(1, 1);
		Assert.assertEquals(bookings, booking);
	}

	@Test
	public void testCancelForRemainingReceivers() {
		List<String> stringlist = new ArrayList<String>();
		stringlist.add("soccess");
		List<TimeSlots> timeslotList = new ArrayList<TimeSlots>();
		timeslotList.add(DataBuilder.getTimeslot());
		when(bookingsdao.cancelBookingForRemaining(timeslotList)).thenReturn(stringlist);
		List<String> resultList = bookingService.cancelForRemainingReceivers(timeslotList);
		Assert.assertEquals(stringlist, resultList);
	}

	@Test
	public void testGetBookingsByTimeslot() {
		List<Bookings> bookingsList = new ArrayList<Bookings>();
		bookingsList.add(DataBuilder.getBookings());
		TimeSlots timeslots = DataBuilder.getTimeslot();
		when(bookingsdao.getBookingByTimeslot(timeslots)).thenReturn(bookingsList);
		List<Bookings> bookingslist = bookingService.getBookingsByTimeslot(timeslots);
		Assert.assertEquals(bookingslist, bookingsList);
	}

	@Test
	public void testGetBookedBookings() {
		Bookings bookings = DataBuilder.getBookings();
		TimeSlots timeslot = DataBuilder.getTimeslot();
		when(bookingsdao.getBookedBookings(timeslot)).thenReturn(bookings);
		Bookings booking = bookingService.getBookedBookings(timeslot);
		Assert.assertEquals(bookings, booking);
	}
	@Test
	public void testGetBookingsById(){
		List<Bookings>bookingsList=new ArrayList<Bookings>();
		bookingsList.add(DataBuilder.getBookings());
		Provider provider=DataBuilder.getProvider();
		when(providerService.getProviderById(anyInt())).thenReturn(provider);
		when(bookingsdao.getBookings(provider)).thenReturn(bookingsList);
		List<Bookings>bookings=bookingService.getBookingsById(1);
		Assert.assertEquals(bookings, bookingsList);
	}
}
