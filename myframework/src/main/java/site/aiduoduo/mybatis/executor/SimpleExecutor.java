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
 * @Date 2020/2/18 1:49 下午
 * @Version 1.0
 */
public class SimpleExecutor extends BaseExecutor {

    public SimpleExecutor(Configuration configuration, Connection connection) {
        super(configuration, connection);
    }

    @Override
    protected <E> List<E> doQuery(MappedStatement ms, BoundSql boundSql, Object parameter) {
        StatmentHandler statmentHandler = configuration.newStatementHandler(ms, parameter, boundSql);
        try {
            Statement statement = statmentHandler.prepare(connection);
            statmentHandler.parameterize(statement);
            return statmentHandler.query(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected int doUpdate(MappedStatement ms, Object parameter) throws SQLException {
        BoundSql boundSql = ms.getBoundSql(parameter);
        StatmentHandler statmentHandler = configuration.newStatementHandler(ms, parameter, boundSql);
        Statement statement = statmentHandler.prepare(connection);
        statmentHandler.parameterize(statement);
        return statmentHandler.update(statement);
    }
}
