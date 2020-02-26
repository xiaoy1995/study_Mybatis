package site.aiduoduo.mybatis.builder;

import org.apache.commons.collections4.CollectionUtils;
import org.dom4j.Element;
import site.aiduoduo.mybatis.mapping.Configuration;
import site.aiduoduo.mybatis.parsing.XpathParser;

import java.io.InputStream;
import java.util.List;

/**
 * @Author yangtianhao
 * @Date 2020/2/13 2:47 下午
 * @Version 1.0 解析Mapper映射文件
 */
public class XmlMapperBuilder {
    private Configuration configuration;
    private XpathParser xpathParser;
    private String namespace;

    public XmlMapperBuilder(Configuration configuration, InputStream inputStream) {
        this.configuration = configuration;
        xpathParser = new XpathParser(inputStream);
    }

    public void parse() {
        parseNamespace(xpathParser.selectSingleElement("src:mapper"));
        parseMappedStatment(xpathParser.selectElements("src:mapper/src:select|src:mapper/src:insert"));
    }

    private void parseMappedStatment(List<Element> mappedStatmentElementList) {
        if (CollectionUtils.isNotEmpty(mappedStatmentElementList)) {
            for (Element element : mappedStatmentElementList) {
                XMLStatementBuilder XMLStatementBuilder = new XMLStatementBuilder(element, namespace, configuration);
                try {
                    configuration.addMappedStatment(XMLStatementBuilder.parseStatementNode());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void parseNamespace(Element mapperElement) {
        namespace = mapperElement.attributeValue("namespace");
    }
}
