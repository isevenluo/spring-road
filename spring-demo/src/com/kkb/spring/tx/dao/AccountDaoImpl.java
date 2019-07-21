package com.kkb.spring.tx.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * JdbcDaoSupport内部封装了JdbcTemplate
 * @author 怡吾宇
 */
@Repository
public class AccountDaoImpl extends JdbcDaoSupport implements AccountDao {

	// 完成数据源的自动装配
	@Autowired
	public AccountDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Override
	public void update(String name, double money) {
		Object[] args = { money, name };
		this.getJdbcTemplate().update("UPDATE account SET money = ? WHERE name = ? ", args);
	}

	@Override
	public double queryMoney(String name) {

		Double money = this.getJdbcTemplate().queryForObject("SELECT money FROM account WHERE name = ?",
				new DoubleMapper(), name);
		return money;
	}

}

// 结果映射器
class DoubleMapper implements RowMapper<Double> {

	@Override
	public Double mapRow(ResultSet rs, int rowNum) throws SQLException {
		return rs.getDouble("money");
	}

}
