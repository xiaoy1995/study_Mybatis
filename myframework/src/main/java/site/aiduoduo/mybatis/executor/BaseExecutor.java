package site.aiduoduo.mybatis.executor;

import site.aiduoduo.mybatis.executor.statment.StatmentHandler;
import site.aiduoduo.mybatis.mapping.BoundSql;
import site.aiduoduo.mybatis.mapping.Configuration;
import site.aiduoduo.mybatis.mapping.MappedStatement;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @Author yangtianhao
 * @Date 2020/2/18 6:01 下午
 * @Version 1.0
 */
public abstract class BaseExecutor implements Executor {
    protected Configuration configuration;
    protected Connection connection;

    public BaseExecutor(Configuration configuration, Connection connection) {
        this.configuration = configuration;
        this.connection = connection;
    }

    @Override
    public int update(MappedStatement ms, Object parameter) throws SQLException {
        BoundSql boundSql = ms.getBoundSql(parameter);
        StatmentHandler statmentHandler = configuration.newStatementHandler(ms, parameter, boundSql);
        Statement statement = statmentHandler.prepare(connection);
        statmentHandler.parameterize(statement);
        return statmentHandler.update(statement);
    }

    @Override
    public <E> List<E> query(MappedStatement ms, Object parameter) throws SQLException {
        BoundSql boundSql = ms.getBoundSql(parameter);
        // 一级缓存处理 TODO

        return queryFromDatabase(ms, boundSql, parameter);
    }

    private <E> List<E> queryFromDatabase(MappedStatement ms, BoundSql boundSql, Object parameter) {
        return doQuery(ms, boundSql, parameter);
    }

    protected abstract <E> List<E> doQuery(MappedStatement ms, BoundSql boundSql, Object parameter);
}
