package site.aiduoduo.mybatis.builder;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;
import org.dom4j.Node;
import site.aiduoduo.mybatis.mapping.SqlNode;
import site.aiduoduo.mybatis.mapping.SqlSource;
import site.aiduoduo.mybatis.mapping.sqlnode.IfSqlNode;
import site.aiduoduo.mybatis.mapping.sqlnode.MixedSqlNode;
import site.aiduoduo.mybatis.mapping.sqlnode.StaticTextSqlNode;
import site.aiduoduo.mybatis.mapping.sqlnode.TextSqlNode;
import site.aiduoduo.mybatis.mapping.sqlnode.WhereSqlNode;
import site.aiduoduo.mybatis.mapping.sqlsource.DynamicSqlSource;
import site.aiduoduo.mybatis.mapping.sqlsource.RawSqlSource;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author yangtianhao
 * @Date 2020/2/13 3:17 下午
 * @Version 1.0 解析select/insert/update/delete标签
 */
public class XMLScriptBuilder {
    private Element statmentELement;
    /**
     * 如果包含${}或者有动态标签，就是动态的SqlSource
     */
    private boolean isDynamic;

    public XMLScriptBuilder(Element statmentELement) {
        this.statmentELement = statmentELement;
    }

    public SqlSource parse() {
        List<SqlNode> sqlNodes = parseSqlNodes(statmentELement);
        SqlSource sqlSource = null;
        if (isDynamic) {
            sqlSource = new DynamicSqlSource(new MixedSqlNode(sqlNodes));
        } else {
            sqlSource = new RawSqlSource(new MixedSqlNode(sqlNodes));
        }
        return sqlSource;
    }

    private List<SqlNode> parseSqlNodes(Node rootNode) {
        List<Node> list = rootNode.selectNodes("node()");
        List<SqlNode> sqlNodeList = new ArrayList<SqlNode>();
        if (CollectionUtils.isNotEmpty(list)) {
            for (Node node : list) {
                if (node.getNodeType() == Node.TEXT_NODE) {
                    String data = node.getText();
                    data = parseText(data);
                    if (StringUtils.isBlank(data)) {
                        continue;
                    }
                    TextSqlNode textSqlNode = new TextSqlNode(data);
                    if (textSqlNode.isDynamic()) {
                        isDynamic = true;
                        sqlNodeList.add(textSqlNode);
                    } else {
                        sqlNodeList.add(new StaticTextSqlNode(data));
                    }
                } else if (node.getNodeType() == Node.ELEMENT_NODE) {
                    isDynamic = true;
                    Element element = (Element)node;
                    String name = node.getName();
                    NodeHandle handle = getHandle(name);
                    handle.handle(element, sqlNodeList);
                }
            }
        }
        return sqlNodeList;
    }

    private String parseText(String data) {
        return data.replaceAll("\n", "").trim();
    }

    private NodeHandle getHandle(String name) {
        switch (name) {
            case "where":
                return new WhereNodeHandle();
            case "if":
                return new IfNodeHandle();
            default:
                return new NullNodeHandle();
        }
    }

    private interface NodeHandle {
        void handle(Element node, List<SqlNode> list);
    }

    private class WhereNodeHandle implements NodeHandle {

        public void handle(Element node, List<SqlNode> list) {
            List<SqlNode> sqlNodeList = parseSqlNodes(node);
            list.add(new WhereSqlNode(new MixedSqlNode(sqlNodeList)));
        }
    }

    private class IfNodeHandle implements NodeHandle {

        public void handle(Element node, List<SqlNode> list) {
            String test = node.attributeValue("test");
            List<SqlNode> sqlNodeList = parseSqlNodes(node);
            list.add(new IfSqlNode(new MixedSqlNode(sqlNodeList), test));
        }
    }

    private class NullNodeHandle implements NodeHandle {

        public void handle(Element node, List<SqlNode> list) {

        }
    }
}
