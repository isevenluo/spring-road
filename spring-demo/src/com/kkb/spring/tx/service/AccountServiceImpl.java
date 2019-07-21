package com.kkb.spring.tx.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kkb.spring.tx.dao.AccountDao;

//@Transactional：标记该类的所有方法都已经被事务进行管理了，至于管理属性，不设置的话，都采取默认值
@Transactional
@Service
public class AccountServiceImpl implements AccountService {

	@Resource
	private AccountDao accountDao;

	@Override
	public void transfer(String from, String to, double money) {
		// 先查询from账户的钱
		double fromMoney = accountDao.queryMoney(from);
		// 对from账户进行扣钱操作
		accountDao.update(from, fromMoney - money);

		// 手动制造异常
		// System.out.println(1/0);
		// 先查询from账户的钱
		double toMoney = accountDao.queryMoney(to);
		// 对to账户进行加钱操作
		accountDao.update(to, toMoney + money);
	}

}
