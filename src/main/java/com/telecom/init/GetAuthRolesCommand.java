package com.telecom.init;

import com.telecom.bean.RoleBean;
import com.telecom.bean.RolesBean;
import com.telecom.config.GlobalValue;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 初始化Role权限集合
 *
 */
@Component
@Order(value = 0)
public class GetAuthRolesCommand implements CommandLineRunner {

	// Role权限集合文件名
	private final static String ROLE_FILE = "authorize-role-config.yml";
	// 顶级节点名
	private final static String ROOT_NODE = "roles";
	// Role节点属性名
	private final static String NAME_NODE = "name";
	private final static String LIST_NODE = "lists";

	@SuppressWarnings("unchecked")
	public void run(String... args) throws Exception {

		try {
			Yaml yaml = new Yaml();
			String rootPath = null;
			try {
				rootPath = ResourceUtils.getURL("classpath:").getPath();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			File ymlFile = new File(rootPath + "/" + ROLE_FILE);

			// 加载流,获取yaml文件中的配置数据，然后转换为Map
			Object obj = yaml.loadAs(new FileInputStream(ymlFile), Object.class);

			// 解析yml对象
			JSONObject rolesObj = JSONObject.fromObject(obj);
			JSONArray array = rolesObj.getJSONArray(ROOT_NODE);

			// 定义list
			List<RolesBean> authRoles = GlobalValue.authRoles;
			array.stream().forEach(jsonObj -> {
				JSONObject role = JSONObject.fromObject(jsonObj);
				String name = role.getString(NAME_NODE);
				JSONArray list = role.getJSONArray(LIST_NODE);

				// 定义bean对象
				RolesBean rolesBean = new RolesBean();
				List<RoleBean> roleBeanList = new ArrayList<RoleBean>();

				list.stream().forEach(roleObj -> {
					JSONObject roleNode = JSONObject.fromObject(roleObj);
					Iterator<String> it = roleNode.keys();
					while (it.hasNext()) {
						RoleBean roleBean = new RoleBean();
						String key = it.next();
						String value = roleNode.getString(key);
						roleBean.setCode(key);
						roleBean.setName(value);
						roleBeanList.add(roleBean);
					}
				});

				rolesBean.setName(name);
				rolesBean.setRoles(roleBeanList);
				authRoles.add(rolesBean);
			});

			// 初始化
			GlobalValue.setAuthRoles(authRoles);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}