package site.aiduoduo.mybatis.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @Author yangtianhao
 * @Date 2020/2/26 11:41 下午
 * @Version 1.0
 */
public class DateTypeHandler extends BaseTypeHandler<java.util.Date> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, java.util.Date parameter, JdbcType jdbcType)
        throws SQLException {
        ps.setTimestamp(i, new Timestamp((parameter).getTime()));
    }

    @Override
    public java.util.Date getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Timestamp sqlTimestamp = rs.getTimestamp(columnName);
        if (sqlTimestamp != null) {
            return new Date(sqlTimestamp.getTime());
        }
        return null;
    }
}
