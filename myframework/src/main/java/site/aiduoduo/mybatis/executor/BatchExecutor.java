package site.aiduoduo.mybatis.executor;

import site.aiduoduo.mybatis.mapping.BoundSql;
import site.aiduoduo.mybatis.mapping.Configuration;
import site.aiduoduo.mybatis.mapping.MappedStatement;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @Author yangtianhao
 * @Date 2020/2/18 2:01 下午
 * @Version 1.0
 */
public class BatchExecutor implements Executor {
    private Configuration configuration;

    public BatchExecutor(Configuration configuration, Connection connection) {
        this.configuration = configuration;
    }

    @Override
    public int update(MappedStatement ms, Object parameter) throws SQLException {
        return 0;
    }

    @Override
    public <E> List<E> query(MappedStatement ms, Object parameter) throws SQLException {
        return null;
    }

    @Override
    public <E> List<E> query(MappedStatement ms, Object parameter, BoundSql boundSql) throws SQLException {
        return null;
    }
}
