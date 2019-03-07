package com.capgemini.Jpa;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaLoad {
	
	
	public static EntityManagerFactory loader()
	{
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Customer_Details");
		return emfactory;
	}

}
