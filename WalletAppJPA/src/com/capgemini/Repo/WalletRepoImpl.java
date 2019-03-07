package com.capgemini.Repo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.capgemini.Beans.Customer;
import com.capgemini.Exception.MobileNoAlreadyRegisteredException;
import com.capgemini.Exception.MobileNoNotFoundException;
import com.capgemini.Jpa.JpaLoad;

public class WalletRepoImpl implements WalletRepo {

	EntityManagerFactory emfactory = JpaLoad.loader();

	@Override
	public boolean save(Customer customer) throws MobileNoAlreadyRegisteredException, MobileNoNotFoundException {
		

			EntityManager entitymanager = emfactory.createEntityManager();
			entitymanager.getTransaction().begin();
			if(entitymanager.find(Customer.class, customer.getMobileno())==null) {
				entitymanager.persist(customer);
				entitymanager.getTransaction().commit();
				entitymanager.close();
			emfactory.close();
			return true;
			}
		throw new MobileNoAlreadyRegisteredException();

	}

	@Override
	public Customer findOne(String mobileno) throws MobileNoNotFoundException {

		EntityManager entitymanager = emfactory.createEntityManager();
		Customer c = entitymanager.find(Customer.class, mobileno);
		entitymanager.close();
		emfactory.close();
		if (c == null) {
			throw new MobileNoNotFoundException();
		}
		return c;
	}

	@Override
	public Customer updateBal(Customer customer) throws MobileNoNotFoundException {

		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		Customer c = entitymanager.find(Customer.class, customer.getMobileno());
		c.setWallet(customer.getWallet());
		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();

		return c;

	}

}
