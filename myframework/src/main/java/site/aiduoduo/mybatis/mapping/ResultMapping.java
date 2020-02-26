package site.aiduoduo.mybatis.mapping;

import site.aiduoduo.mybatis.type.TypeHandler;

/**
 * @Author yangtianhao
 * @Date 2020/2/26 10:56 下午
 * @Version 1.0
 */
public class ResultMapping {
    private String column;
    private String property;
    private TypeHandler<?> typeHandler;

    public String getProperty() {
        return property;
    }

    public TypeHandler<?> getTypeHandler() {
        return typeHandler;
    }

    public String getColumn() {
        return column;
    }
}
