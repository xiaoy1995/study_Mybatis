package site.aiduoduo.mybatis.parsing;

import site.aiduoduo.mybatis.mapping.ParameterMapping;
import site.aiduoduo.mybatis.mapping.sqlsource.SqlSource;
import site.aiduoduo.mybatis.mapping.sqlsource.StaticSqlSource;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author yangtianhao
 * @Date 2020/2/14 4:26 下午
 * @Version 1.0
 */
public class SqlSourceParser {

    public SqlSource parse(String sql) {
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        String parseSql = genericTokenParser.parse(sql);
        List<ParameterMapping> parameterMappingList = parameterMappingTokenHandler.getParameterMappingList();
        return new StaticSqlSource(parseSql, parameterMappingList);
    }

    public class ParameterMappingTokenHandler implements TokenHandler {
        List<ParameterMapping> parameterMappingList = new ArrayList<>();

        @Override
        public String handleToken(String content) {
            String name = content;
            Class type = null;
            parameterMappingList.add(new ParameterMapping(name, type));
            return "?";
        }

        public List<ParameterMapping> getParameterMappingList() {
            return parameterMappingList;
        }
    }
}
