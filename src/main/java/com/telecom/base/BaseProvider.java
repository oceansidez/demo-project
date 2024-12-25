package com.telecom.base;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.StaticTextSqlNode;
import org.apache.ibatis.scripting.xmltags.WhereSqlNode;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;

import java.util.ArrayList;
import java.util.List;

public class BaseProvider extends MapperTemplate {
    // 继承父类的方法
    public BaseProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }
    
    public SqlNode selectForDefault(MappedStatement ms) {
    	final Class<?> entityClass = getEntityClass(ms);
        // 修改返回值类型为实体类型
        setResultType(ms, entityClass);

        List<SqlNode> sqlNodes = new ArrayList<SqlNode>();
        // 静态的sql部分:select * from table
        sqlNodes.add(new StaticTextSqlNode("SELECT "
                + " * "
                + " FROM "
                + tableName(entityClass)));
        
        List<SqlNode> whereNodes = new ArrayList<SqlNode>();
        whereNodes.add(new StaticTextSqlNode(" id = #{key} "));
        sqlNodes.add(new WhereSqlNode(ms.getConfiguration(), new MixedSqlNode(whereNodes)));
        return new MixedSqlNode(sqlNodes);
    }
}
