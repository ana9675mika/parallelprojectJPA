package com.capgemini.Service;

import java.math.BigDecimal;

import com.capgemini.Beans.Customer;
import com.capgemini.Beans.Wallet;
import com.capgemini.Exception.InsufficientBalanceException;
import com.capgemini.Exception.MobileNoAlreadyRegisteredException;
import com.capgemini.Exception.MobileNoNotFoundException;
import com.capgemini.Repo.WalletRepo;

public class WalletServiceImpl implements WalletService {

	private WalletRepo walletrepo;

	public WalletServiceImpl(WalletRepo walletrepo) {
		super();
		this.walletrepo = walletrepo;
	}

	@Override
	public Customer createAccount(String name, String mobileno, BigDecimal amount)
			throws MobileNoAlreadyRegisteredException, MobileNoNotFoundException {

		Customer customer = new Customer();
		Wallet wallet = new Wallet();
		customer.setMobileno(mobileno);
		customer.setName(name);
		wallet.setBalance(amount);
		customer.setWallet(wallet);
		if (walletrepo.save(customer)) {
			return customer;
		}
		return null;
	}

	@Override
	public Customer showBalance(String mobileno) throws MobileNoNotFoundException {

		return walletrepo.findOne(mobileno);

	}

	@Override
	public Customer depositAmount(String mobileNo, BigDecimal amount) throws MobileNoNotFoundException {

		Customer customer = walletrepo.findOne(mobileNo);
		Wallet wallet = customer.getWallet();
		wallet.setBalance(wallet.getBalance().add(amount));
		customer.setWallet(wallet);
		return walletrepo.updateBal(customer);
	}

	@Override
	public Customer withdrawAmount(String mobileno, BigDecimal amount)
			throws InsufficientBalanceException, MobileNoNotFoundException {

		Customer customer = walletrepo.findOne(mobileno);
		if (customer.getWallet().getBalance().compareTo(amount) < 0) {
			throw new InsufficientBalanceException();

		} else {
			Wallet wallet = customer.getWallet();
			wallet.setBalance(wallet.getBalance().subtract(amount));
			customer.setWallet(wallet);
			return walletrepo.updateBal(customer);
		}
	}

	@Override
	public Customer[] fundTransfer(String sourceMobileno, String targetMobileno, BigDecimal amount)
			throws InsufficientBalanceException, MobileNoNotFoundException {
		Customer c[] = new Customer[2];
		c[0] = walletrepo.findOne(sourceMobileno);
		c[1] = walletrepo.findOne(targetMobileno);
		if (c[0].getWallet().getBalance().compareTo(amount) < 0) {
			throw new InsufficientBalanceException();
		}

		if (c[0] != null && c[1] != null) {

			Wallet wallet = c[0].getWallet();
			wallet.setBalance(wallet.getBalance().subtract(amount));
			c[0].setWallet(wallet);
			walletrepo.updateBal(c[0]);

			c[1] = walletrepo.findOne(targetMobileno);
			Wallet wallet1 = c[1].getWallet();
			wallet1.setBalance(wallet1.getBalance().add(amount));
			c[1].setWallet(wallet1);
			walletrepo.updateBal(c[1]);

		}

		return c;
	}
}
