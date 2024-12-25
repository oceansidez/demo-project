package com.telecom.manage.mapper;

import com.telecom.base.BaseMapper;
import com.telecom.bean.FindPagerMapperBean;
import com.telecom.manage.entity.Job;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface JobMapper extends BaseMapper<Job>{

	@Select("select * from t_job")
	public Job getJob();
	
	@Select("select * from t_job where name=#{name}")
	public Job getJobByName(String name);
	
	@Select("select * from t_job where state=#{state}")
	public List<Job> getJobByState(String state);
	
	@Select("select * from t_job where job_group=#{group}")
	public Job getJobByGroup(String group);

	@Delete("delete t_job where name=#{jobName}")
	public int deleteByJobName(String jobName);

	@Update("update  t_job set state=#{0}  where name=#{1}")
	public boolean updateState(String state,String name);
	
	@Select( "<script>"
		    + "select * from t_job where date_key=#{dateKey} "
			+ "<if test = \"state != null\"  >"
			+ "and state = #{state} "
			+ "</if>"
			+ "<if test = \"fpmbSearchList.size != 0\"  >"
			+ "<foreach collection =\"fpmbSearchList\" item=\"fpmbSearch\" index= \"index\" separator =\"\"> "
			+ "and ${fpmbSearch.key} like '%${fpmbSearch.value}%' "
			+ "</foreach >"
			+ "</if>"
            + "</script>" )
	public Job getJobByDateKey(@Param("dateKey") String dateKey,@Param("state") String state,@Param("fpmbSearchList") List<FindPagerMapperBean> fpmbSearchList);
	
}
