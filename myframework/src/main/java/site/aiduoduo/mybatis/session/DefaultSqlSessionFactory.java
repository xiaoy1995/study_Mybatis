package site.aiduoduo.mybatis.session;

import site.aiduoduo.mybatis.executor.Executor;
import site.aiduoduo.mybatis.mapping.Configuration;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @Author yangtianhao
 * @Date 2020/2/18 1:47 下午
 * @Version 1.0
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {
    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return openSessionFromDataSource(configuration.getDefaultExecutorType());
    }

    private SqlSession openSessionFromDataSource(ExecutorType executorType) {
        Connection connection = null;
        try {
            connection = configuration.getEnvironment().getDataSource().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Executor executor = configuration.newExecutor(executorType,connection);
        return new DefaultSqlSession(configuration, executor);

    }
}
