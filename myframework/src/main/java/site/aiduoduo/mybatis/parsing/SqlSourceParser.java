package site.aiduoduo.mybatis.parsing;

import site.aiduoduo.mybatis.mapping.Configuration;
import site.aiduoduo.mybatis.mapping.ParameterMapping;
import site.aiduoduo.mybatis.mapping.sqlsource.SqlSource;
import site.aiduoduo.mybatis.mapping.sqlsource.StaticSqlSource;
import site.aiduoduo.mybatis.type.JdbcType;
import site.aiduoduo.mybatis.type.TypeHandler;
import site.aiduoduo.mybatis.type.TypeHandlerRegistry;
import site.aiduoduo.mybatis.util.SimpleTypeRegistry;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author yangtianhao
 * @Date 2020/2/14 4:26 下午
 * @Version 1.0
 */
public class SqlSourceParser {

    private Configuration configuration;

    public SqlSourceParser(Configuration configuration) {
        this.configuration = configuration;
    }

    public SqlSource parse(String sql, Class<?> parameterType) {
        ParameterMappingTokenHandler parameterMappingTokenHandler =
            new ParameterMappingTokenHandler(configuration, parameterType);
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        String parseSql = genericTokenParser.parse(sql);
        List<ParameterMapping> parameterMappingList = parameterMappingTokenHandler.getParameterMappingList();
        return new StaticSqlSource(parseSql, parameterMappingList);
    }

    public class ParameterMappingTokenHandler implements TokenHandler {
        private List<ParameterMapping> parameterMappingList = new ArrayList<>();
        private Class<?> parameterType;
        private Configuration configuration;

        public ParameterMappingTokenHandler(Configuration configuration, Class<?> parameterType) {
            this.configuration = configuration;
            this.parameterType = parameterType;
        }

        @Override
        public String handleToken(String content) {
            String property = content;
            // 如果#{}里面配置了jdbcType，那么可以获取，并赋值，我们就不处理了
            JdbcType jdbcType = null;
            Class<?> propertyType;
            if (parameterType == null) {
                propertyType = Object.class;
            } else if (SimpleTypeRegistry.isSimpleType(parameterType)) {
                propertyType = parameterType;
            } else {
                try {
                    // 简单处理，只支持一级
                    Field declaredField = parameterType.getClass().getDeclaredField(content);
                    propertyType = declaredField.getType();
                } catch (NoSuchFieldException e) {
                    // e.printStackTrace();
                    propertyType = Object.class;
                }
            }
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            TypeHandler typeHandler = typeHandlerRegistry.getTypeHandler(propertyType, jdbcType);
            parameterMappingList.add(new ParameterMapping(property, parameterType, jdbcType, typeHandler));
            return "?";
        }

        public List<ParameterMapping> getParameterMappingList() {
            return parameterMappingList;
        }
    }
}
