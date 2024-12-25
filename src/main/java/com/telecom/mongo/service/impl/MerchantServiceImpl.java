package com.telecom.mongo.service.impl;

import com.mongodb.BasicDBObject;
import com.telecom.mongo.base.BaseDaoForMongo;
import com.telecom.mongo.base.BaseServiceImplForMongo;
import com.telecom.mongo.dao.MerchantDao;
import com.telecom.mongo.dao.repository.MerchantRepository;
import com.telecom.mongo.entity.Merchant;
import com.telecom.mongo.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MerchantServiceImpl extends BaseServiceImplForMongo<Merchant> implements MerchantService {

	@Autowired
	MerchantDao merchantDao;

	@Autowired
	MerchantRepository merchantRepository;

	@Override
	public BaseDaoForMongo<Merchant> getBaseDao() {
		return merchantDao;
	}

	@Override
	public List<Merchant> getNearMerchantList(Double lng, Double lat, Integer distance) {
		return merchantRepository.getNearMerchantList(lng, lat, distance);
	}

	@Override
	public List<Merchant> getNearMerchantListWithDis(BasicDBObject condition, Double lng, Double lat,
			Integer distance) {
		return merchantRepository.getNearMerchantListWithDis(condition, lng, lat, distance);
	}

}
