package com.telecom.manage.mapper;

import com.telecom.base.BaseMapper;
import com.telecom.manage.entity.WechatReply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface WechatReplyMapper extends BaseMapper<WechatReply> {

	@Select("<script>SELECT * FROM t_wechat_reply r WHERE r.is_open=true"
			+ "<if test='type!=null'>"
		    + "	AND r.type = #{type}"
		    + "</if>"
		    + "<if test='keyword!=null and keyword!=\"\" and type!= null and type == \"click\"'>"
		    + "	AND (r.keyword = #{keyword})"
		    + "</if>"
		    + "<if test='keyword!=null and keyword!=\"\" and type!= null and type == \"keyword\"'>"
		    + "	AND (r.keyword = #{keyword} or r.keyword like concat('%${keyword}%'))"
		    + "</if>"
		    + "<if test='startDate!=null'>"
		    + "	AND r.create_date &gt;= #{startDate} "
		    + "</if>"
		    + "<if test='endDate!=null'>"
		    + "	AND r.create_date &lt;= #{endDate} "
		    + "</if>"
		    + " ORDER BY r.create_date desc "
		    + "<if test='pageNumber!=null'>"
			    + "<if test='pageSize!=null'>"
			    	+ " limit #{pageNumber}, #{pageSize}"
			    + "</if>"
		    + "</if>"
			+ " </script>")
	List<WechatReply> getListByConditions(Map<String, Object> map);
	
	@Select("<script>SELECT count(*) FROM t_wechat_reply r WHERE r.is_open=true"
			+ "<if test='type!=null'>"
		    + "	AND r.type = #{type}"
		    + "</if>"
		    + "<if test='keyword!=null and keyword!=\"\" and type!= null and type == \"click\"'>"
		    + "	AND (r.keyword = #{keyword})"
		    + "</if>"
		    + "<if test='keyword!=null and keyword!=\"\" and type!= null and type == \"keyword\"'>"
		    + "	AND (r.keyword = #{keyword} or r.keyword like concat('%${keyword}%'))"
		    + "</if>"
		    + "<if test='startDate!=null'>"
		    + "	AND r.create_date &gt;= #{startDate} "
		    + "</if>"
		    + "<if test='endDate!=null'>"
		    + "	AND r.create_date &lt;= #{endDate} "
		    + "</if>"
			+ " </script>")
	int getCountByConditions(Map<String, Object> map);
	
}
