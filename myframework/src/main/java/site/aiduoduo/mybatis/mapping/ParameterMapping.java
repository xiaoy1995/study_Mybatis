package site.aiduoduo.mybatis.mapping;

import site.aiduoduo.mybatis.type.JdbcType;
import site.aiduoduo.mybatis.type.TypeHandler;

/**
 * @Author yangtianhao
 * @Date 2020/2/14 2:14 下午
 * @Version 1.0
 */
public class ParameterMapping {
    private String property;
    private Class type;
    private JdbcType jdbcType;
    private TypeHandler<?> typeHandler;

    public ParameterMapping(String property, Class type, JdbcType jdbcType, TypeHandler typeHandler) {
        this.property = property;
        this.type = type;
        this.jdbcType = jdbcType;
        this.typeHandler = typeHandler;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public TypeHandler<?> getTypeHandler() {
        return typeHandler;
    }

    public JdbcType getJdbcType() {
        return jdbcType;
    }
}
