package com.wavelabs.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.wavelabs.model.Provider;
import com.wavelabs.util.SessionUtil;

/**
 * this is an provider DAO class which is for persist the data
 * 
 * @author tharunkumarb
 *
 */

@Component
public class ProviderDao {
	Logger logger = Logger.getLogger(ProviderDao.class);

	/**
	 * This method will persist the doctor based on the input data
	 * 
	 * @param name
	 * @param phoneNumber
	 * @param email
	 * @param password
	 * @return
	 */
	public Provider addProvider(String name, String address, long phoneNumber,
			String email, String password) {
		Session session = SessionUtil.getSession();
		Provider provider = new Provider();
		provider.setName(name);
		provider.setAddress(address);
		provider.setPhoneNumber(phoneNumber);
		provider.setEmail(email);
		provider.setPassword(password);
		session.save(provider);
		session.beginTransaction().commit();
		session.close();
		return provider;

	}

	/**
	 * This method gets the doctors based on id of the doctor
	 * 
	 * @param id
	 * @return Doctor
	 */
	public Provider getProvider(int id) {
		Session session = SessionUtil.getSession();
		logger.info("Getting provider based on the id");
		Provider provider = (Provider) session.get(Provider.class, id);
		session.close();
		return provider;
	}

	/**
	 * this method will gets the all the doctors in the form of list
	 * 
	 * @return
	 */
	public List<Provider> getProvidersList() {
		Session session = SessionUtil.getSession();
		Criteria criteria = session.createCriteria(Provider.class);
		@SuppressWarnings("unchecked")
		List<Provider> providerList = criteria.list();
		logger.info("Done with getting provider list");
		session.close();
		return providerList;
	}

	/**
	 * this method will persist the provider
	 * 
	 * @param provider
	 * @return
	 */
	public Provider persistProvider(Provider provider) {
		Session session = SessionUtil.getSession();
		session.save(provider);
		session.flush();
		session.beginTransaction().commit();
		logger.info("Done with persisting the provider");
		session.close();
		return provider;
	}

}
