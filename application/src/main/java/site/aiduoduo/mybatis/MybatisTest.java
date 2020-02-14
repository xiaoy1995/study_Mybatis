package site.aiduoduo.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;

/**
 * @Author yangtianhao
 * @Date 2020/2/5 4:47 下午
 * @Version 1.0
 */
public class MybatisTest {

    @Test
    public void test() {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("/mybatis-config.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(resourceAsStream);

    }
}
