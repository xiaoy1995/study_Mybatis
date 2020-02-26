package site.aiduoduo.mybatis.executor;

import site.aiduoduo.mybatis.mapping.BoundSql;
import site.aiduoduo.mybatis.mapping.MappedStatement;

import java.sql.SQLException;
import java.util.List;

/**
 * @Author yangtianhao
 * @Date 2020/2/18 11:46 上午
 * @Version 1.0
 */
public interface Executor {
    int update(MappedStatement ms, Object parameter) throws SQLException;

    <E> List<E> query(MappedStatement ms, Object parameter) throws SQLException;

    <E> List<E> query(MappedStatement ms, Object parameter, BoundSql boundSql) throws SQLException;
}
