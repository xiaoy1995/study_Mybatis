package site.aiduoduo.mybatis.mapping.sqlsource;

import site.aiduoduo.mybatis.mapping.BoundSql;
import site.aiduoduo.mybatis.mapping.ParameterMapping;

import java.util.List;

/**
 * @Author yangtianhao
 * @Date 2020/2/14 2:19 下午
 * @Version 1.0
 */
public class StaticSqlSource implements SqlSource {
    private String sql;
    private List<ParameterMapping> parameterMappings;

    public StaticSqlSource(String sql, List<ParameterMapping> parameterMappings) {
        this.sql = sql;
        this.parameterMappings = parameterMappings;
    }

    @Override
    public BoundSql getBoundSql(Object param) {
        return new BoundSql(sql, parameterMappings);
    }
}
