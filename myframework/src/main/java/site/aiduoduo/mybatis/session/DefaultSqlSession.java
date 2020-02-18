package site.aiduoduo.mybatis.session;

import org.apache.commons.collections4.CollectionUtils;
import site.aiduoduo.mybatis.executor.Executor;
import site.aiduoduo.mybatis.mapping.Configuration;
import site.aiduoduo.mybatis.mapping.MappedStatement;

import java.sql.SQLException;
import java.util.List;

/**
 * @Author yangtianhao
 * @Date 2020/2/18 11:37 上午
 * @Version 1.0
 */
public class DefaultSqlSession implements SqlSession {
    private Configuration configuration;
    private Executor executor;

    public DefaultSqlSession(Configuration configuration, Executor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) {
        List<T> objects = this.selectList(statement, parameter);
        if (CollectionUtils.isNotEmpty(objects)) {
            return objects.get(0);
        }
        return null;
    }

    @Override
    public <E> List<E> selectList(String statement, Object parameter) {
        MappedStatement ms = configuration.getMappedStatment(statement);
        if (ms == null) {
            return null;
        }
        try {
            return executor.query(ms, parameter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int insert(String statement, Object parameter) {
        MappedStatement ms = configuration.getMappedStatment(statement);
        if (ms == null) {
            return 0;
        }
        try {
            return executor.update(ms, parameter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
