package com.bmw.seed.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bmw.seed.dao.UserInfoDao;
import com.bmw.seed.service.UserInfoService;
import com.bmw.seed.util.redis.RedisUtil;

@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService{

	 private static final Logger log = LoggerFactory.getLogger(UserInfoServiceImpl.class);
	
	@Autowired
	UserInfoDao userInfoDao;
	
	@Autowired
	RedisUtil redisUtil;

	@Override
	public Boolean transferExpand() {
		log.info("===[开始调用幂等性校验的表迁移接口]===");
		//生成唯一token
		//先采用固定值
		String token = "transfer";
		//--------------------------------------------------------------------------
		if (!redisUtil.set(token,1)) {
			log.error("===[重复执行API,直接返回失败]===");
			return false;
		}else {
			try {
				return transfer();
			} catch (Exception e) {
				log.error("==[表迁移异常！！]===");
				return false;
			}finally {
				redisUtil.del(token);
			}
		}
	}

	@Override
	public Boolean transfer() {
		int bak = userInfoDao.countBak();
		log.info("===[迁移前，user_info_bak表中数据{}条]===",bak);
		log.info("===[迁移前，user_info表中数据有0条]===");
		log.info("===[开始transfer()方法]===");
		userInfoDao.transfer();
		int count = userInfoDao.countTotal();
		log.info("===[迁移完成。迁移后user_info表中的数据为{}条]==",count);
		if (count>0) {
			return true;
		}else {
			return false;
		}
	}


	
}
