package site.aiduoduo.mybatis.mapping.sqlsource;

import site.aiduoduo.mybatis.mapping.BoundSql;
import site.aiduoduo.mybatis.mapping.Configuration;
import site.aiduoduo.mybatis.mapping.DynamicContext;
import site.aiduoduo.mybatis.mapping.sqlnode.SqlNode;
import site.aiduoduo.mybatis.parsing.SqlSourceParser;

/**
 * @Author yangtianhao
 * @Date 2020/2/13 4:30 下午
 * @Version 1.0
 */
public class RawSqlSource implements SqlSource {
    private SqlNode contents;
    private SqlSource delegate;
    private Configuration configuration;

    public RawSqlSource(Configuration configuration, SqlNode contents, Class<?> parameterType) {
        this.contents = contents;
        this.configuration = configuration;
        DynamicContext dynamicContext = new DynamicContext(null);
        contents.apply(dynamicContext);
        String sql = dynamicContext.getSql().toString();

        Class<?> clazz = parameterType == null ? Object.class : parameterType;
        SqlSourceParser sqlSourceParser = new SqlSourceParser(configuration);
        SqlSource parse = sqlSourceParser.parse(sql, clazz);

        delegate = parse;
    }

    @Override
    public BoundSql getBoundSql(Object param) {
        return delegate.getBoundSql(param);
    }
}
