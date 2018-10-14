package com.cjf.java.api.vo;

import java.math.BigDecimal;

import com.cjf.java.entity.UserEntity;

import lombok.Data;

@Data
public class UserAccountInfoVo extends UserEntity {

	private Integer id;
	private Integer userId;
	private BigDecimal rechargeAmountTotal;
	private int goldCoinoin;
	private Long inputTime;
	private Long updateTime;
}
