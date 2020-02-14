package site.aiduoduo.mybatis.mapping.sqlsource;

import site.aiduoduo.mybatis.mapping.BoundSql;
import site.aiduoduo.mybatis.mapping.DynamicContext;
import site.aiduoduo.mybatis.mapping.ParameterMapping;
import site.aiduoduo.mybatis.mapping.SqlNode;
import site.aiduoduo.mybatis.mapping.SqlSource;
import site.aiduoduo.mybatis.parsing.GenericTokenParser;
import site.aiduoduo.mybatis.parsing.tokenhandler.ParameterMappingTokenHandler;

import java.util.List;

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

        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        String parseSql = genericTokenParser.parse(sql);
        List<ParameterMapping> parameterMappingList = parameterMappingTokenHandler.getParameterMappingList();
        delegate = new StaticSqlSource(parseSql, parameterMappingList);
    }

    @Override
    public BoundSql getBoundSql(Object param) {
        return delegate.getBoundSql(param);
    }
}
