package ${entityPackage};

import java.util.Date;
import java.math.BigDecimal;
import javax.persistence.Table;
import ${package}.base.BaseEntity;

@Table(name = "t_${tableName}")
public class ${entityName} extends BaseEntity{

    private static final long serialVersionUID = -1L;
    
    <#list fieldList as field>
    private ${field.dataType} ${field.field};// ${field.description}
    </#list>

    <#list fieldList as field>
    public ${field.dataType} get${field.fieldU}() {
        return ${field.field};
    }

    public void set${field.fieldU}(${field.dataType} ${field.field}){
        this.${field.field}=${field.field};
    }

    </#list>

}
