package site.aiduoduo.mybatis.parsing;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Author yangtianhao
 * @Date 2020/2/8 3:27 下午
 * @Version 1.0
 * 简单解析，不弄那么复杂了
 */
public class XpathParser {
    private Document document;

    public XpathParser(InputStream inputStream) {
        SAXReader saxReader = new SAXReader();
        try {
            document = saxReader.read(inputStream);
            //因为我用的是XML Schemal，所以需要处理名称空间
            Map<String, String> namespaceMap = new HashMap();
            for (Object o : document.getRootElement().content()) {
                if (o instanceof Namespace) {
                    Namespace ns = (Namespace)o;
                    String namespacePrefix = StringUtils.isBlank(ns.getPrefix())?"src":ns.getPrefix();
                    String namespaceURI = ns.getURI();
                    namespaceMap.put(namespacePrefix, namespaceURI);
                }
            }

            saxReader.getDocumentFactory().setXPathNamespaceURIs(namespaceMap);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public List<Node> selectNodes(String s) {
        return document.selectNodes(s);
    }

    public Node selectSingleNode(String s) {
        return document.selectSingleNode(s);
    }

    public Element selectSingleElement(String s) {
        List<Element> elements = selectElements(s);
        if(CollectionUtils.isNotEmpty(elements)){
            return  elements.get(0);
        }
        return null;
    }

    public List<Element> selectElements(String s) {
        List list = document.selectNodes(s);
        Iterator iterator = list.iterator();
        while(iterator.hasNext()){
            Node node = (Node) iterator.next();
            if(node.getNodeType() != Node.ELEMENT_NODE){
                iterator.remove();
            }
        }
        return list;
    }

}
