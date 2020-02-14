package site.aiduoduo.mybatis.mapping.sqlnode;

import site.aiduoduo.mybatis.mapping.DynamicContext;
import site.aiduoduo.mybatis.mapping.SqlNode;

/**
 * @Author yangtianhao
 * @Date 2020/2/13 3:41 下午
 * @Version 1.0 静态sql片段
 */
public class StaticTextSqlNode implements SqlNode {

    private String text;

    public StaticTextSqlNode(String text) {
        this.text = text;
    }

    @Override
    public void apply(DynamicContext context) {
        context.getSql().append(text);
    }
}
