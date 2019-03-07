package com.capgemini.Testcase;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import com.capgemini.Beans.Customer;
import com.capgemini.Beans.Wallet;
import com.capgemini.Exception.InsufficientBalanceException;
import com.capgemini.Exception.MobileNoAlreadyRegisteredException;
import com.capgemini.Exception.MobileNoNotFoundException;
import com.capgemini.Repo.WalletRepoImpl;
import com.capgemini.Service.WalletService;
import com.capgemini.Service.WalletServiceImpl;

public class TestCaseWallet {
	WalletService wsr;
	
	@Before
	public void setUp()
	{
		wsr = new WalletServiceImpl(new WalletRepoImpl());
	}

	/*
	 * Add Customer 1. When all the information is valid 2. When Mobile Number
	 * Already registered Exception
	 */

	@Test
	public void whenInfoIsValid()
			throws MobileNoAlreadyRegisteredException, ClassNotFoundException, SQLException, MobileNoNotFoundException {
		Customer c = new Customer();
		Wallet w = new Wallet();
		BigDecimal bd = new BigDecimal(400);
		w.setBalance(bd);
		c.setMobileno("7989098989");
		c.setName("Robin");
		c.setWallet(w);
		assertEquals(c, wsr.createAccount("Robin", "7989098989", bd));
	}

	@Test(expected = MobileNoAlreadyRegisteredException.class)
	public void whenMobileNumberIsAlreadyRegistered()
			throws MobileNoAlreadyRegisteredException, ClassNotFoundException, SQLException, MobileNoNotFoundException {
		wsr.createAccount("Robin", "8989898989", new BigDecimal(4000));
		wsr.createAccount("Robin", "8989898989", new BigDecimal(4000));
	}

	/*
	 * Deposit 1. When All Info Is Valid 2. When mobile Number Not Found Exception
	 * Occur
	 */
	@Test
	public void whenAllInfoIsValid()
			throws MobileNoNotFoundException, MobileNoAlreadyRegisteredException, SQLException, ClassNotFoundException {
		Customer c = new Customer();
		Wallet w = new Wallet();
		BigDecimal bd = new BigDecimal(3500);
		BigDecimal b1 = new BigDecimal(500);
		w.setBalance(bd);
		c.setMobileno("8969898980");
		c.setName("Robin");
		c.setWallet(w);
		wsr.createAccount("Robin", "8969898980", bd);
		assertEquals(c.getWallet().getBalance().add(b1), wsr.depositAmount("8969898980", b1).getWallet().getBalance());
	}

	@Test(expected = MobileNoNotFoundException.class)
	public void whenMobileNumberNotFoundException() throws MobileNoNotFoundException, SQLException {
		wsr.depositAmount("7878787878", new BigDecimal(600));
	}

	/*
	 * Withdraw 1. When all the information is Valid 2. When mobile is not found
	 * exception 3. when insufficient balance exception
	 */

	@Test
	public void whenAllInfoIsValidForWithdraw() throws MobileNoAlreadyRegisteredException, InsufficientBalanceException,
			MobileNoNotFoundException, SQLException, ClassNotFoundException {
		Customer c = new Customer();
		Wallet w = new Wallet();
		BigDecimal bd = new BigDecimal(3500);
		BigDecimal b1 = new BigDecimal(500);
		w.setBalance(bd);
		c.setMobileno("7989898987");
		c.setName("Robin");
		c.setWallet(w);
		wsr.createAccount("Robin", "7989898987", bd);
		assertEquals(c.getWallet().getBalance().subtract(b1),
				wsr.withdrawAmount("7989898987", b1).getWallet().getBalance());
	}

	@Test(expected = MobileNoNotFoundException.class)
	public void whenMobileNumberIsNotFoundExcepion()
			throws InsufficientBalanceException, MobileNoNotFoundException, SQLException {
		wsr.withdrawAmount("9999991122", new BigDecimal(400));
	}

	@Test(expected = InsufficientBalanceException.class)
	public void whenInsufficientBalanceOccurInWithdraw() throws MobileNoAlreadyRegisteredException,
			InsufficientBalanceException, MobileNoNotFoundException, SQLException, ClassNotFoundException {
		BigDecimal bd = new BigDecimal(3500);
		BigDecimal b1 = new BigDecimal(5000);
		wsr.createAccount("Robin", "7889898959", bd);
		wsr.withdrawAmount("7889898959", b1);
	}

	/*
	 * Show Details 1. When information is valid 2. When Mobile Number Not Found
	 * Exception
	 */

	@Test
	public void whenInfoIsValidInShowDetails()
			throws MobileNoAlreadyRegisteredException, MobileNoNotFoundException, SQLException, ClassNotFoundException {
		BigDecimal b1 = new BigDecimal(5000);
		Customer c = wsr.createAccount("Robin", "8959898979", b1);
		assertEquals(c, wsr.showBalance("8959898979"));
	}

	@Test(expected = MobileNoNotFoundException.class)
	public void whenMobileNumberIsNotFoundException() throws MobileNoNotFoundException, SQLException {
		wsr.showBalance("7878787876");
	}
	
	/*
	 * FundTransfer
	 *  1. When the info is valid
	 *  2. When Mobile Number Not Found Exception
	 *  3. When there is Insufficient balance in Sender's Account
	 */


	@Test
	public void whenAllInfoIsvalid() throws InsufficientBalanceException, MobileNoNotFoundException, SQLException
	{
		wsr.fundTransfer("7889898959","8959898979", new BigDecimal(7));
	}
	@Test(expected = MobileNoNotFoundException.class)
	public void whenMoileNumberNotFoundExceptionInFundTransfer()
			throws InsufficientBalanceException, MobileNoNotFoundException, SQLException {
		wsr.fundTransfer("9090909011", "8989898989", new BigDecimal(80));
	}

	@Test(expected = InsufficientBalanceException.class)
	public void whenThereIsInsufficienBalanceException()
			throws MobileNoAlreadyRegisteredException, InsufficientBalanceException, MobileNoNotFoundException, ClassNotFoundException, SQLException {
		wsr.createAccount("Raj", "8979898007", new BigDecimal(80));
		wsr.createAccount("Rahul", "7978787001", new BigDecimal(20));
		wsr.fundTransfer("7978787001", "8979898007", new BigDecimal(30));
	}


}
