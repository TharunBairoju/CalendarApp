package com.wavelabs.model;

import org.springframework.stereotype.Component;

/**
 * This is enum for status
 * 
 * @author tharunkumarb
 *
 */
@Component
public enum Status {
	booked, available, process, empty, cancel, decline;
}
