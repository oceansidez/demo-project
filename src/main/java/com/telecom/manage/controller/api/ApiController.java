package com.telecom.manage.controller.api;

import com.mongodb.BasicDBObject;
import com.telecom.mongo.entity.Merchant;
import com.telecom.mongo.service.MerchantService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "api")
public class ApiController {

	@Autowired
	MerchantService merchantService;

	@GetMapping("/add")
	public String add() {
		int a = 1;
		int b = 2;
		int sum = a + b;
		System.out.println(a + b);
		return "result is: 1 + 2 = " + sum;
	}

	@GetMapping("/sub")
	public String sub() {
		int a = 10;
		int b = 9;
		int sub = a - b;
		System.out.println(a - b);
		return "result is: 10 - 9 = " + sub;
	}

	@GetMapping(value = "/test", produces = "application/json; charset=utf-8")
	public String test(
			@RequestParam(value="lng") Double lng,
			@RequestParam(value="lat") Double lat,
			@RequestParam(value="distance") Integer distance) {
		
//		Merchant mer = new Merchant();
//		mer.setName("龙泉山");
//		mer.setAddress("很遥远的地址");
//		Location location = new Location();
//		location.setLng(114.511775);
//		location.setLat(30.413075);
//		mer.setLocation(location);
//		merchantService.save(mer);
		
		BasicDBObject condition = new BasicDBObject();
		BasicDBObject regexCondition = new BasicDBObject();
		regexCondition.append("$regex", "1");
		condition.append("address", regexCondition);
		System.out.println(condition);
		List<Merchant> list = merchantService.getNearMerchantListWithDis(condition, lng, lat, distance);
		return JSONArray.fromObject(list).toString();
	}
	
}
