package com.telecom.manage.controller.other;

import com.telecom.base.BaseController;
import com.telecom.config.WebConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "elasticSearch")
public class ElasticSearchController extends BaseController {

	private final String PAGE_PRE = "es_";

//    @Autowired
//    ElasticSearchConfig elasticSearchConfig;

    @Autowired
    WebConfig webConfig;

    @GetMapping("index")
    public String index(ModelMap modelMap) {
        return "/other/" + PAGE_PRE + "index";
    }

}
