package site.aiduoduo.mybatis.executor.statment;

import site.aiduoduo.mybatis.executor.parameter.ParameterHandle;
import site.aiduoduo.mybatis.executor.resultset.ResultSetHandler;
import site.aiduoduo.mybatis.mapping.BoundSql;
import site.aiduoduo.mybatis.mapping.Configuration;
import site.aiduoduo.mybatis.mapping.MappedStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @Author yangtianhao
 * @Date 2020/2/18 6:17 下午
 * @Version 1.0
 */
public class PreparedStatementHandler implements StatmentHandler {

    private BoundSql boundSql;
    private ParameterHandle parameterHandle;
    private ResultSetHandler resultSetHandler;
    private Configuration configuration;

    public PreparedStatementHandler(BoundSql boundSql, Object parameterObject, MappedStatement mappedStatement) {
        this.boundSql = boundSql;
        this.configuration = mappedStatement.getConfiguration();
        this.parameterHandle = configuration.newParameterHandler(mappedStatement, parameterObject, boundSql);
        this.resultSetHandler = configuration.newResultSetHandler(mappedStatement);
    }

    @Override
    public Statement prepare(Connection connection) throws SQLException {
        String sql = boundSql.getSql();
        return connection.prepareStatement(sql);
    }

    @Override
    public void parameterize(Statement statement) throws SQLException {
        parameterHandle.setParameters((PreparedStatement)statement);
    }

    @Override
    public int update(Statement statement) throws SQLException {
        PreparedStatement preparedStatement = (PreparedStatement)statement;
        return preparedStatement.executeUpdate();
    }

    @Override
    public <E> List<E> query(Statement statement) throws SQLException {
        PreparedStatement preparedStatement = (PreparedStatement)statement;
        preparedStatement.executeQuery();
        return resultSetHandler.handleResultSets(preparedStatement);
    }
}
