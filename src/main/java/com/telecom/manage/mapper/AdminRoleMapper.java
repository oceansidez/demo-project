package com.telecom.manage.mapper;

import com.telecom.base.BaseMapper;
import com.telecom.manage.entity.Admin;
import com.telecom.manage.entity.AdminRole;
import com.telecom.manage.entity.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminRoleMapper extends BaseMapper<AdminRole> {

	@Select("select role.* from t_admin_role as adminRole "
			+ " left join t_role as role "
			+ " on adminRole.role_id = role.id "
			+ " where adminRole.admin_id = #{id} ")
    public List<Role> getRoleList(String adminId);
	
	@Select("select admin.* from t_admin_role as adminRole "
			+ " left join t_admin as admin "
			+ " on adminRole.admin_id = admin.id "
			+ " where adminRole.role_id = #{id} ")
    public List<Admin> getAdminList(String roleId);
	
	@Delete("delete from t_admin_role where admin_id = #{id}")
	public Integer deleteAdminRoleByAdmin(String adminId);
	
	@Insert( "<script>"+
             "insert into t_admin_role(admin_id, role_id) "
           + "values "
           + "<foreach collection =\"roleIds\" item=\"role\" index= \"index\" separator =\",\"> "
           + "(#{adminId}, #{role}) "
           + "</foreach > "
           + "</script>" )
	public Integer batchInsertAdminRole(@Param("adminId") String adminId,
			@Param("roleIds") List<String> roleIds);
	
}
