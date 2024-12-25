package com.telecom.manage.entity;

import com.telecom.base.BaseEntity;
import com.telecom.util.JsonUtil;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Table(name = "t_resource")
public class Resource extends BaseEntity {

	private static final long serialVersionUID = -1L;

	// 拦截类型（放行，拦截，忽略）
	// 放行：security permit允许通行
	// 拦截：security auth进入验证
	// 忽略：完全绕开security
	public enum AllowType {
		permit, intercept, ignore
	}

	private String name;// 资源名称
	private String path;// 资源路径
	private String allowType;// 拦截类型（放行，拦截，忽略）
	private String authorityListStore;// 不拦截的用户权限

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getAllowType() {
		return allowType;
	}

	public void setAllowType(String allowType) {
		this.allowType = allowType;
	}

	public String getAuthorityListStore() {
		return authorityListStore;
	}

	public void setAuthorityListStore(String authorityListStore) {
		this.authorityListStore = authorityListStore;
	}

	// 获取权限集合
	@SuppressWarnings("unchecked")
	public List<String> getAuthorityList() {
		if (StringUtils.isEmpty(authorityListStore)) {
			return null;
		}
		return JsonUtil.toObject(authorityListStore, ArrayList.class);
	}

	// 设置权限集合
	public void setAuthorityList(List<String> roleList) {
		if (roleList == null || roleList.size() == 0) {
			authorityListStore = null;
			return;
		}
		authorityListStore = JsonUtil.toJson(roleList);
	}
}
