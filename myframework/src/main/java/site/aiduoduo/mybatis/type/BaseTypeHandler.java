package site.aiduoduo.mybatis.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author yangtianhao
 * @Date 2020/2/26 11:32 下午
 * @Version 1.0
 */
public abstract class BaseTypeHandler<T> implements TypeHandler<T> {

    @Override
    public void setParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        // 特殊情况，设置NULL
        if (parameter == null) {
            if (jdbcType == null) {
                // 如果没设置jdbcType，报错啦
                throw new RuntimeException(
                    "JDBC requires that the JdbcType must be specified for all nullable parameters.");
            }
            try {
                // 设成NULL
                ps.setNull(i, jdbcType.TYPE_CODE);
            } catch (SQLException e) {
                throw new RuntimeException("Error setting null for parameter #" + i + " with JdbcType " + jdbcType
                    + " . "
                    + "Try setting a different JdbcType for this parameter or a different jdbcTypeForNull configuration property. "
                    + "Cause: " + e, e);
            }
        } else {
            // 非NULL情况
            setNonNullParameter(ps, i, parameter, jdbcType);
        }
    }

    @Override
    public T getResult(ResultSet rs, String columnName) throws SQLException {
        T result = getNullableResult(rs, columnName);
        // 通过ResultSet.wasNull判断是否为NULL
        if (rs.wasNull()) {
            return null;
        } else {
            return result;
        }
    }

    // 不为null就设置参数
    protected abstract void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType)
        throws SQLException;

    // 取得可能为null的结果，具体交给子类完成
    public abstract T getNullableResult(ResultSet rs, String columnName) throws SQLException;
}
