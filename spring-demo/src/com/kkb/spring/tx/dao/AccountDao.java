package com.kkb.spring.tx.dao;

public interface AccountDao {

	void update(String name,double money);
	
	double queryMoney(String name);
}
