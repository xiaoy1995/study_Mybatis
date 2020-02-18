package site.aiduoduo.mybatis.executor;

import site.aiduoduo.mybatis.mapping.MappedStatement;

import java.sql.SQLException;
import java.util.List;

/**
 * @Author yangtianhao
 * @Date 2020/2/18 2:02 下午
 * @Version 1.0
 */
public class CatchingExecutor implements Executor {
    private Executor delegate;

    public CatchingExecutor(Executor delegate) {
        this.delegate = delegate;
    }

    @Override
    public int update(MappedStatement ms, Object parameter) throws SQLException {
        return delegate.update(ms, parameter);
    }

    @Override
    public <E> List<E> query(MappedStatement ms, Object parameter) throws SQLException {
        return delegate.query(ms, parameter);
    }
}
