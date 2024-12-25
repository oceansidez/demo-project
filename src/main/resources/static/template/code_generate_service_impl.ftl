package ${serviceImplPackage};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ${package}.base.BaseMapper;
import ${package}.base.BaseServiceImpl;
import ${entityPackage}.${entityName};
import ${mapperPackage}.${entityName}Mapper;
import ${servicePackage}.${entityName}Service;

@Service
public class ${entityName}ServiceImpl extends BaseServiceImpl<${entityName}> implements ${entityName}Service{

    @Autowired
    ${entityName}Mapper ${methodName}Mapper;
    
    @Override
    public BaseMapper<${entityName}> getBaseMapper() {
    	return ${methodName}Mapper;
    }

}

