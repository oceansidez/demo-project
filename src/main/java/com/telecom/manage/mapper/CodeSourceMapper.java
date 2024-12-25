package com.telecom.manage.mapper;

import com.telecom.base.BaseMapper;
import com.telecom.manage.entity.CodeSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CodeSourceMapper extends BaseMapper<CodeSource> {

	@Select("select * from t_code_source where file_name = #{fileName}")
	public CodeSource getByFileName(String fileName);
}
