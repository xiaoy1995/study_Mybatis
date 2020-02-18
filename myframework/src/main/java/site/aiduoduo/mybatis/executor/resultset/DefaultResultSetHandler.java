package site.aiduoduo.mybatis.executor.resultset;

import site.aiduoduo.mybatis.mapping.MappedStatement;

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

    public DefaultResultSetHandler(MappedStatement mappedStatement) {
        this.mappedStatement = mappedStatement;
    }

    @Override
    public <E> List<E> handleResultSets(Statement stmt) throws SQLException {
        List<E> result = new ArrayList<>();

        String resultType = mappedStatement.getResultType();
        ResultSet resultSet = stmt.getResultSet();

        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        String[] colNames = new String[columnCount];
        for (int i = 1; i <= columnCount; i++) {
            String columnName = metaData.getColumnName(i);
            colNames[i - 1] = columnName;
        }

        while (resultSet.next()) {
            try {
                E instance = (E)Class.forName(resultType).newInstance();
                for (int i = 0; i < colNames.length; i++) {
                    try {
                        String colName = colNames[i];
                        Object value = resultSet.getObject(colName);

                        Field declaredField = instance.getClass().getDeclaredField(colName);
                        declaredField.setAccessible(true);
                        declaredField.set(instance, value);
                    } catch (Exception e) {

                    }
                }
                result.add(instance);
            } catch (Exception e) {

            }
        }

        return result;
    }
}
