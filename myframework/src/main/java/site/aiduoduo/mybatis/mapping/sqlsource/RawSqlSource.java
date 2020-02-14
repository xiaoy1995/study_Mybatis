package site.aiduoduo.mybatis.mapping.sqlsource;

import site.aiduoduo.mybatis.mapping.BoundSql;
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

    public RawSqlSource(SqlNode contents) {
        this.contents = contents;

        DynamicContext dynamicContext = new DynamicContext(null);
        contents.apply(dynamicContext);
        String sql = dynamicContext.getSql().toString();

        SqlSourceParser sqlSourceParser = new SqlSourceParser();
        SqlSource parse = sqlSourceParser.parse(sql);

        delegate = parse;
    }

    @Override
    public BoundSql getBoundSql(Object param) {
        return delegate.getBoundSql(param);
    }
}
