package com.telecom.mongo.service;

import com.mongodb.BasicDBObject;
import com.telecom.mongo.base.BaseServiceForMongo;
import com.telecom.mongo.entity.Merchant;

import java.util.List;

public interface MerchantService extends BaseServiceForMongo<Merchant> {

	/**
	 * 查询距离当前坐标指定距离的商户
	 * @param lng
	 * @param lat
	 * @param distance
	 * @return
	 */
	public List<Merchant> getNearMerchantList(Double lng, Double lat, Integer distance);
	
	/**
	 * 查询距离当前坐标指定距离的商户（并展示距离）
	 * @param lng
	 * @param lat
	 * @param distance
	 * @return
	 */
	public List<Merchant> getNearMerchantListWithDis(BasicDBObject condition, Double lng, Double lat, Integer distance);
}