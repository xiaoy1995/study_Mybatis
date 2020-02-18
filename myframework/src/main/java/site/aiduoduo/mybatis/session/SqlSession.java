package site.aiduoduo.mybatis.session;

import java.util.List;

/**
 * @Author yangtianhao
 * @Date 2020/2/18 11:34 上午
 * @Version 1.0
 */
public interface SqlSession {
    <T> T selectOne(String statement, Object parameter);

    <E> List<E> selectList(String statement, Object parameter);

    int insert(String statement, Object parameter);
}
