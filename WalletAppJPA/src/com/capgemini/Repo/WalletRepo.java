package com.capgemini.Repo;


import com.capgemini.Beans.Customer;
import com.capgemini.Exception.MobileNoAlreadyRegisteredException;
import com.capgemini.Exception.MobileNoNotFoundException;

public interface WalletRepo {

	public boolean save(Customer customer) throws MobileNoAlreadyRegisteredException, MobileNoNotFoundException;

	public Customer findOne(String mobileno) throws MobileNoNotFoundException;

	public Customer updateBal(Customer customer) throws MobileNoNotFoundException;

}
