package site.aiduoduo.mybatis.builder;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;
import site.aiduoduo.mybatis.io.Resources;
import site.aiduoduo.mybatis.mapping.Configuration;
import site.aiduoduo.mybatis.mapping.Environment;
import site.aiduoduo.mybatis.parsing.GenericTokenParser;
import site.aiduoduo.mybatis.parsing.XpathParser;
import site.aiduoduo.mybatis.parsing.tokenhandler.PropertyHandler;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @Author yangtianhao
 * @Date 2020/2/7 8:39 下午
 * @Version 1.0
 */
public class XmlConfigurationBuilder {

    private Configuration configuration;
    private XpathParser xpathParser;

    public XmlConfigurationBuilder(InputStream inputStream) {
        xpathParser = new XpathParser(inputStream);
        configuration = new Configuration();
    }

    public Configuration parse() throws Exception {
        parseProperties(xpathParser.selectSingleElement("src:configuration/src:properties"));
        parseEnviroments(xpathParser.selectSingleElement("src:configuration/src:enviroments"));
        parseMappers(xpathParser.selectSingleElement("src:configuration/src:mappers"));
        return configuration;
    }

    private void parseMappers(Element mappersElement) {
        List<Element> mapperList = mappersElement.elements("mapper");
        if (CollectionUtils.isNotEmpty(mapperList)) {
            for (Element element : mapperList) {
                String resource = element.attributeValue("resource");
                InputStream resourceAsStream = Resources.getResourceAsStream(resource);
                XmlMapperBuilder xmlMapperBuilder = new XmlMapperBuilder(configuration, resourceAsStream);
                xmlMapperBuilder.parse();
            }
        }
    }

    private void parseEnviroments(Element enviromentsElement) throws Exception {
        String defaultEnviromentId = enviromentsElement.attributeValue("default");
        List<Element> enviromentList = enviromentsElement.elements("enviroment");
        for (Element element : enviromentList) {
            String id = element.attributeValue("id");
            if (StringUtils.equals(defaultEnviromentId, id)) {
                Element datasource = element.element("datasource");
                String type = datasource.attributeValue("type");
                if ("druid".equals(type)) {
                    GenericTokenParser genericTokenParser = new GenericTokenParser("${", "}", new PropertyHandler());
                    Map<String, String> dataSourceProperty = new HashMap();
                    List<Element> propertyList = datasource.elements("property");
                    for (Element property : propertyList) {
                        String name = property.attributeValue("name");
                        String value = property.attributeValue("value");
                        String parse = genericTokenParser.parse(value);
                        dataSourceProperty.put(name, configuration.getProperties().getProperty(parse));
                    }
                    DataSource dataSource = DruidDataSourceFactory.createDataSource(dataSourceProperty);
                    configuration.setEnvironment(new Environment(defaultEnviromentId, dataSource));
                }
                break;
            }

        }
    }

    private void parseProperties(Element propertiesElement) {

        String resource = propertiesElement.attributeValue("resource");

        if (StringUtils.isNotBlank(resource)) {
            InputStream resourceAsStream = Resources.getResourceAsStream(resource);
            Properties properties = new Properties();
            try {
                properties.load(resourceAsStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            configuration.setProperties(properties);
        }

    }

}
