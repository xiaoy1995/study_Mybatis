package site.aiduoduo.mybatis.session;

import site.aiduoduo.mybatis.builder.XmlConfigurationBuilder;
import site.aiduoduo.mybatis.mapping.Configuration;

import java.io.InputStream;

/**
 * @Author yangtianhao
 * @Date 2020/2/18 11:43 上午
 * @Version 1.0
 */
public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(InputStream inputStream) throws Exception {
        XmlConfigurationBuilder xmlConfigurationBuilder = new XmlConfigurationBuilder(inputStream);
        Configuration configuration = xmlConfigurationBuilder.parse();
        return build(configuration);
    }

    public SqlSessionFactory build(Configuration config) {
        return new DefaultSqlSessionFactory(config);
    }
}
