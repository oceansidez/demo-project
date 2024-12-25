package ${controllerPackage};
  
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ${package}.base.BaseController;
import ${package}.bean.Pager;
import ${entityPackage}.${entityName};
import ${servicePackage}.${entityName}Service;

@Controller
@RequestMapping(value = "${methodName}")
public class ${entityName}Controller extends BaseController {

    private final String PAGE_PRE = "/${controllerPrefix}/${methodName}_";

    @Autowired
    ${entityName}Service ${methodName}Service;

    // 列表
    @GetMapping("list")
    public String list(Pager pager, ModelMap model) {
        pager = ${methodName}Service.findPager(pager);
        return PAGE_PRE + "list";
    }

    // 查看
    @GetMapping("view/{id}")
    public String view(@PathVariable(value="id") String id, ModelMap model) {
        ${entityName} ${methodName} = ${methodName}Service.get(id);
        model.put("${methodName}", ${methodName});
        return PAGE_PRE + "view";
    }
    
    // 添加
    @GetMapping("add")
    public String add(ModelMap model) {
        model.put("isAdd", true);
        return PAGE_PRE + "input";
    }
    
    // 编辑
    @GetMapping("edit/{id}")
    public String edit(@PathVariable(value="id") String id, ModelMap model) {
		${entityName} ${methodName} = ${methodName}Service.get(id);
		model.put("${methodName}", ${methodName});
        model.put("isAdd", false);
        return PAGE_PRE + "input";
    }
}
