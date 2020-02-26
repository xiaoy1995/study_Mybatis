package site.aiduoduo.mybatis.executor.parameter;

import org.apache.commons.collections4.CollectionUtils;
import site.aiduoduo.mybatis.mapping.BoundSql;
import site.aiduoduo.mybatis.mapping.Configuration;
import site.aiduoduo.mybatis.mapping.MappedStatement;
import site.aiduoduo.mybatis.mapping.ParameterMapping;
import site.aiduoduo.mybatis.type.JdbcType;
import site.aiduoduo.mybatis.type.TypeHandler;
import site.aiduoduo.mybatis.util.SimpleTypeRegistry;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @Author yangtianhao
 * @Date 2020/2/18 6:26 下午
 * @Version 1.0
 */
public class DefaultParameterHandler implements ParameterHandle {
    private BoundSql boundSql;
    private Object parameterObject;
    private MappedStatement mappedStatement;
    private Configuration configuration;

    public DefaultParameterHandler(BoundSql boundSql, Object parameterObject, MappedStatement mappedStatement) {
        this.boundSql = boundSql;
        this.parameterObject = parameterObject;
        this.mappedStatement = mappedStatement;
        this.configuration = mappedStatement.getConfiguration();
    }

    @Override
    public void setParameters(PreparedStatement ps) throws SQLException {

        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (CollectionUtils.isNotEmpty(parameterMappings)) {
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                String propertyName = parameterMapping.getProperty();
                Object value = null;
                if (parameterObject == null) {
                    value = null;
                } else if (SimpleTypeRegistry.isSimpleType(parameterObject.getClass())) {
                    value = parameterObject;
                } else {
                    try {
                        // 写的比较简单，直接用反射取值，只支持一级
                        Field field = parameterObject.getClass().getDeclaredField(propertyName);
                        field.setAccessible(true);
                        value = field.get(parameterObject);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                TypeHandler typeHandler = parameterMapping.getTypeHandler();
                JdbcType jdbcType = parameterMapping.getJdbcType();
                if (value == null && jdbcType == null) {
                    // 不同类型的set方法不同，所以委派给子类的setParameter方法
                    jdbcType = configuration.getJdbcTypeForNull();
                }
                typeHandler.setParameter(ps, i + 1, value, jdbcType);
            }
        }
    }
}
