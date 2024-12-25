package com.telecom.mongo.dao.impl;

import com.telecom.mongo.base.BaseDaoImplForMongo;
import com.telecom.mongo.dao.MerchantDao;
import com.telecom.mongo.entity.Merchant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository("merchantDao")
public class MerchantDaoImpl extends BaseDaoImplForMongo<Merchant> implements MerchantDao {

	@Autowired
	MongoTemplate mongoTemplate;

}
