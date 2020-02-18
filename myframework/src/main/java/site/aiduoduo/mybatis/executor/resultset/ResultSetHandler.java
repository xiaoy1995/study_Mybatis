package site.aiduoduo.mybatis.executor.resultset;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @Author yangtianhao
 * @Date 2020/2/18 6:28 下午
 * @Version 1.0
 */
public interface ResultSetHandler {
    // 处理结果集
    <E> List<E> handleResultSets(Statement stmt) throws SQLException;
}
