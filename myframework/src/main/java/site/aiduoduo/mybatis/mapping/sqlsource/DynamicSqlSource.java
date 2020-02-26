package site.aiduoduo.mybatis.mapping.sqlsource;

import site.aiduoduo.mybatis.mapping.BoundSql;
import site.aiduoduo.mybatis.mapping.Configuration;
import site.aiduoduo.mybatis.mapping.DynamicContext;
import site.aiduoduo.mybatis.mapping.sqlnode.SqlNode;
import site.aiduoduo.mybatis.parsing.SqlSourceParser;

/**
 * @Author yangtianhao
 * @Date 2020/2/13 4:31 下午
 * @Version 1.0
 */
public class DynamicSqlSource implements SqlSource {
    private SqlNode contents;
    private Configuration configuration;

    public DynamicSqlSource(Configuration configuration, SqlNode contents) {
        this.contents = contents;
        this.configuration = configuration;
    }

    @Override
    public BoundSql getBoundSql(Object param) {
        DynamicContext dynamicContext = new DynamicContext(param);
        contents.apply(dynamicContext);
        String sql = dynamicContext.getSql().toString();

        Class<?> parameterType = param == null ? Object.class : param.getClass();
        SqlSourceParser sqlSourceParser = new SqlSourceParser(configuration);
        SqlSource parse = sqlSourceParser.parse(sql, parameterType);
        return parse.getBoundSql(param);
    }
}
