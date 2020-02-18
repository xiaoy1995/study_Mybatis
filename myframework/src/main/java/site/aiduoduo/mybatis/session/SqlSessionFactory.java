package site.aiduoduo.mybatis.session;

/**
 * @Author yangtianhao
 * @Date 2020/2/18 11:41 上午
 * @Version 1.0
 */
public interface SqlSessionFactory {
    SqlSession openSession();
}
