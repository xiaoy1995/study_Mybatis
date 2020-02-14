package site.aiduoduo.mybatis.mapping;

import java.util.List;

/**
 * @Author yangtianhao
 * @Date 2020/2/13 4:37 下午
 * @Version 1.0
 */
public class BoundSql {
    private String sql;
    private List<ParameterMapping> parameterMappings;

    public BoundSql(String sql, List<ParameterMapping> parameterMappings) {
        this.sql = sql;
        this.parameterMappings = parameterMappings;
    }

    public String getSql() {
        return sql;
    }

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }
}
