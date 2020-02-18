package site.aiduoduo.mybatis.mapping;

import site.aiduoduo.mybatis.executor.BatchExecutor;
import site.aiduoduo.mybatis.executor.CatchingExecutor;
import site.aiduoduo.mybatis.executor.Executor;
import site.aiduoduo.mybatis.executor.SimpleExecutor;
import site.aiduoduo.mybatis.executor.statment.PreparedStatementHandler;
import site.aiduoduo.mybatis.executor.statment.StatmentHandler;
import site.aiduoduo.mybatis.session.ExecutorType;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Author yangtianhao
 * @Date 2020/2/5 5:28 下午
 * @Version 1.0
 */
public class Configuration {
    protected ExecutorType defaultExecutorType = ExecutorType.SIMPLE;
    protected boolean cacheEnabled = true;
    private Environment environment;
    private Properties properties;

    private Map<String, MappedStatement> mappedStatmentMap = new HashMap<>();

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public void addMappedStatment(MappedStatement mappedStatement) {
        mappedStatmentMap.put(mappedStatement.getId(), mappedStatement);
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public MappedStatement getMappedStatment(String id) {
        return mappedStatmentMap.get(id);
    }

    public Executor newExecutor(ExecutorType executorType, Connection connection) {
        Executor executor;
        if (ExecutorType.BATCH == executorType) {
            executor = new BatchExecutor(this, connection);
        } else {
            executor = new SimpleExecutor(this, connection);
        }
        if (cacheEnabled) {
            executor = new CatchingExecutor(executor);
        }
        return executor;

    }

    public ExecutorType getDefaultExecutorType() {
        return defaultExecutorType;
    }

    public StatmentHandler newStatementHandler(MappedStatement ms, Object parameter, BoundSql boundSql) {
        String statementType = ms.getStatementType();
        StatmentHandler statmentHandler = null;
        switch (statementType) {
            case "prepared":
                statmentHandler = new PreparedStatementHandler(boundSql, parameter, ms);
                break;
            default:

        }
        return statmentHandler;
    }
}
