package com.capgemini.Service;

import java.math.BigDecimal;
import java.sql.SQLException;

import com.capgemini.Beans.Customer;
import com.capgemini.Exception.InsufficientBalanceException;
import com.capgemini.Exception.MobileNoAlreadyRegisteredException;
import com.capgemini.Exception.MobileNoNotFoundException;

public interface WalletService {

	public Customer createAccount(String name, String mobileno, BigDecimal amount)
			throws MobileNoAlreadyRegisteredException, MobileNoNotFoundException;

	public Customer showBalance(String mobileno) throws MobileNoNotFoundException, SQLException;

	public Customer depositAmount(String mobileNo, BigDecimal amount) throws MobileNoNotFoundException;

	public Customer withdrawAmount(String mobileno, BigDecimal amount)
			throws InsufficientBalanceException, MobileNoNotFoundException;

	Customer[] fundTransfer(String sourceMobileno, String targetMobileno, BigDecimal amount)
			throws InsufficientBalanceException, MobileNoNotFoundException;

}
