package ${mapperPackage};

import org.apache.ibatis.annotations.Mapper;

import ${package}.base.BaseMapper;
import ${entityPackage}.${entityName};

@Mapper
public interface ${entityName}Mapper extends BaseMapper<${entityName}> {

}

