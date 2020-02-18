package site.aiduoduo.mybatis.builder;

import org.dom4j.Element;

import site.aiduoduo.mybatis.mapping.MappedStatement;
import site.aiduoduo.mybatis.mapping.sqlsource.SqlSource;

/**
 * @Author yangtianhao
 * @Date 2020/2/13 3:09 下午
 * @Version 1.0
 */
public class XMLStatementBuilder {
    private Element statmentELement;
    private String namespace;
    public XMLStatementBuilder(Element statmentElement, String namespace) {
        this.statmentELement = statmentElement;
        this.namespace = namespace;
    }

    public MappedStatement parseStatementNode() {
        String id = namespace+"."+statmentELement.attributeValue("id");
        String parameterType = statmentELement.attributeValue("parameterType");
        String resultType = statmentELement.attributeValue("resultType");
        String statmentType = statmentELement.attributeValue("statmentType");
        XMLScriptBuilder xmlScriptBuilder = new XMLScriptBuilder(statmentELement);
        SqlSource sqlSource = xmlScriptBuilder.parseScriptNode();
        return new MappedStatement(id, parameterType, resultType, sqlSource, statmentType);
    }
}
