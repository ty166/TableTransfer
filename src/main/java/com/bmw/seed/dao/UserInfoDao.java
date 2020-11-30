package com.bmw.seed.dao;


import org.springframework.stereotype.Repository;


@Repository
public interface UserInfoDao {

	void transfer();

	int countTotal();

	int countBak();

}
