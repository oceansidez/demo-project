package com.telecom.mongo.dao.repository;

import com.mongodb.BasicDBObject;
import com.telecom.mongo.entity.Merchant;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MerchantRepository extends MongoRepository<Merchant, String> {

	@Query( "{'location': {" + 
			"     $near: {" + 
			"       $geometry: {" + 
			"          type: 'Point' ," + 
			"          coordinates: [ ?0 , ?1 ]" + 
			"       }," + 
			"       $maxDistance: ?2" + 
			"     }" + 
			"	}" +
			"}")
	public List<Merchant> getNearMerchantList(Double lng, Double lat, Integer distance);
	
	@Aggregation(pipeline = {
			"	{" + 
			"		\"$geoNear\" : {" + 
			"			\"near\" : {" + 
			"				\"type\" : \"Point\"," + 
			"				\"coordinates\" : [ ?1, ?2 ]" + 
			"			}," + 
			"			\"distanceField\" : \"distance\"," + 
			"			\"maxdistance\" : ?3," + 
			"			\"spherical\" : true" + 
			"		}" + 
			"	}",
			"	{" + 
			"		\"$match\" : ?0 "+ 
			"	}"
	})
	public List<Merchant> getNearMerchantListWithDis(BasicDBObject condition, Double lng, Double lat, Integer distance);

}
