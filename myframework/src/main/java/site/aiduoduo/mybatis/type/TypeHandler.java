package site.aiduoduo.mybatis.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author yangtianhao
 * @Date 2020/2/26 9:38 下午
 * @Version 1.0
 */
public interface TypeHandler<T> {
    // 设置参数
    void setParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException;

    // 取得结果,供普通select用
    T getResult(ResultSet rs, String columnName) throws SQLException;

}
