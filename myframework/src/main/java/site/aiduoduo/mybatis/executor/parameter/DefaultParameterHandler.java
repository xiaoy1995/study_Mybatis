package site.aiduoduo.mybatis.executor.parameter;

import org.apache.commons.collections4.CollectionUtils;
import site.aiduoduo.mybatis.mapping.BoundSql;
import site.aiduoduo.mybatis.mapping.MappedStatement;
import site.aiduoduo.mybatis.mapping.ParameterMapping;
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

    public DefaultParameterHandler(BoundSql boundSql, Object parameterObject, MappedStatement mappedStatement) {
        this.boundSql = boundSql;
        this.parameterObject = parameterObject;
        this.mappedStatement = mappedStatement;
    }

    @Override
    public void setParameters(PreparedStatement ps) throws SQLException {

        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (CollectionUtils.isNotEmpty(parameterMappings)) {
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                String name = parameterMapping.getName();
                Object value = null;
                if (parameterObject == null) {
                    value = null;
                } else if (SimpleTypeRegistry.isSimpleType(parameterObject.getClass())) {
                    value = parameterObject;
                } else {
                    try {
                        Field field = parameterObject.getClass().getDeclaredField(name);
                        field.setAccessible(true);
                        value = field.get(parameterObject);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                ps.setObject(i+1, value);
            }
        }
    }
}
