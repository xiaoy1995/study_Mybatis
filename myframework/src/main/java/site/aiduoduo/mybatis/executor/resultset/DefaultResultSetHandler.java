package site.aiduoduo.mybatis.executor.resultset;

import org.apache.commons.collections4.CollectionUtils;
import site.aiduoduo.mybatis.mapping.Configuration;
import site.aiduoduo.mybatis.mapping.MappedStatement;
import site.aiduoduo.mybatis.mapping.ResultMap;
import site.aiduoduo.mybatis.mapping.ResultMapping;
import site.aiduoduo.mybatis.type.TypeHandler;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author yangtianhao
 * @Date 2020/2/18 6:32 下午
 * @Version 1.0
 */
public class DefaultResultSetHandler implements ResultSetHandler {

    private MappedStatement mappedStatement;
    private Configuration configuration;

    public DefaultResultSetHandler(MappedStatement mappedStatement) {
        this.mappedStatement = mappedStatement;
        this.configuration = mappedStatement.getConfiguration();
    }

    @Override
    public List<Object> handleResultSets(Statement stmt) throws SQLException {
        List<Object> result = new ArrayList<>();
        // 获取对应的ResultMap
        ResultMap resultMap = mappedStatement.getResultMap();
        ResultSet resultSet = stmt.getResultSet();

        while (resultSet.next()) {
            try {
                Object resultObject = resultMap.getType().newInstance();
                // 处理没有映射关系的，自动映射,基于属性名自动映射列到 JavaBean 的属性上
                applyAutomaticMappings(resultObject, resultSet);

                if (CollectionUtils.isNotEmpty(resultMap.getResultMappings())) {
                    // 处理有映射关系的
                    for (ResultMapping propertyMapping : resultMap.getResultMappings()) {
                        String property = propertyMapping.getProperty();
                        TypeHandler typeHandler = propertyMapping.getTypeHandler();
                        String column = propertyMapping.getColumn();
                        Object value = typeHandler.getResult(resultSet, column);

                        Field declaredField = resultObject.getClass().getDeclaredField(property);
                        declaredField.setAccessible(true);
                        declaredField.set(resultObject, value);
                    }
                }

                result.add(resultObject);
            } catch (Exception e) {

            }
        }

        return result;
    }

    private void applyAutomaticMappings(Object resultObject, ResultSet resultSet) throws SQLException {
        ResultSetMetaData rsm = resultSet.getMetaData(); // 获得列集
        int col = rsm.getColumnCount(); // 获得列的个数
        String colName[] = new String[col];
        // 取结果集中的表头名称, 放在colName数组中
        for (int i = 0; i < col; i++) {
            colName[i] = rsm.getColumnName(i + 1);
        }
        for (String s : colName) {
            try {
                Field declaredField = resultObject.getClass().getDeclaredField(s);
                declaredField.setAccessible(true);
                Class<?> type = resultObject.getClass().getDeclaredField(s).getType();
                TypeHandler<?> typeHandler = configuration.getTypeHandlerRegistry().getTypeHandler(type);
                Object value = typeHandler.getResult(resultSet, s);
                declaredField.set(resultObject, value);
            } catch (NoSuchFieldException e) {
                // e.printStackTrace();
            } catch (IllegalAccessException e) {
                // e.printStackTrace();
            }

        }

    }
}
