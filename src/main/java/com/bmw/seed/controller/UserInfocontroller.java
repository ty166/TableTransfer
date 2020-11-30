package com.bmw.seed.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bmw.seed.service.UserInfoService;
import com.bmw.seed.util.bean.BaseResponse;
import com.bmw.seed.util.bean.CommonQueryBean;
import com.bmw.seed.util.bean.PageReq;
import com.bmw.seed.util.bean.PageResp;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/user")
@Slf4j
public class UserInfocontroller {

	@Autowired
	UserInfoService userInfoService;

	/**
	 * 迁移需求
	 */
	@RequestMapping(value = "/transfer")
	public BaseResponse<Boolean> transfer() {
		return BaseResponse.ok(userInfoService.transferExpand());
	}


}
