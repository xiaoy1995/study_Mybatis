package site.aiduoduo.mybatis.executor.statment;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @Author yangtianhao
 * @Date 2020/2/18 4:45 下午
 * @Version 1.0
 */
public interface StatmentHandler {
    // 准备语句
    Statement prepare(Connection connection) throws SQLException;

    // 参数化
    void parameterize(Statement statement) throws SQLException;

    // update
    int update(Statement statement) throws SQLException;

    // select
    <E> List<E> query(Statement statement) throws SQLException;
}
