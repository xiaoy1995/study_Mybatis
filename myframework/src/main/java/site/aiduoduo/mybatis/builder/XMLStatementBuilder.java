package site.aiduoduo.mybatis.builder;

import org.dom4j.Element;

import site.aiduoduo.mybatis.mapping.Configuration;
import site.aiduoduo.mybatis.mapping.MappedStatement;
import site.aiduoduo.mybatis.mapping.ResultMap;
import site.aiduoduo.mybatis.mapping.sqlsource.SqlSource;

/**
 * @Author yangtianhao
 * @Date 2020/2/13 3:09 下午
 * @Version 1.0
 */
public class XMLStatementBuilder {
    private Element statmentELement;
    private String namespace;
    private Configuration configuration;

    public XMLStatementBuilder(Element statmentElement, String namespace, Configuration configuration) {
        this.statmentELement = statmentElement;
        this.namespace = namespace;
        this.configuration = configuration;
    }

    public MappedStatement parseStatementNode() throws ClassNotFoundException {
        String id = namespace + "." + statmentELement.attributeValue("id");
        String parameterType = statmentELement.attributeValue("parameterType");
        String resultType = statmentELement.attributeValue("resultType");
        Class<?> aClass = Class.forName(resultType);
        // resultType转换resultMap，主要生成一个id以及获取Type即可.
        ResultMap resultMap = new ResultMap(id + "-Inline", aClass);

        String statmentType = statmentELement.attributeValue("statmentType");
        XMLScriptBuilder xmlScriptBuilder = new XMLScriptBuilder(statmentELement, configuration, aClass);
        SqlSource sqlSource = xmlScriptBuilder.parseScriptNode();
        return new MappedStatement(id, parameterType, resultType, sqlSource, statmentType, configuration, resultMap);
    }
}
