package site.aiduoduo.mybatis.mapping.sqlnode;

import site.aiduoduo.mybatis.mapping.DynamicContext;
import site.aiduoduo.mybatis.mapping.SqlNode;
import site.aiduoduo.mybatis.util.OgnlUtils;

/**
 * @Author yangtianhao
 * @Date 2020/2/13 4:09 下午
 * @Version 1.0
 */
public class IfSqlNode implements SqlNode {
    private SqlNode sqlNode;
    private String text;

    public IfSqlNode(SqlNode sqlNode, String text) {
        this.sqlNode = sqlNode;
        this.text = text;
    }

    @Override
    public void apply(DynamicContext context) {
        if (OgnlUtils.evaluateBoolean(text, context.getParams().get("_parameter"))) {
            sqlNode.apply(context);
        }
    }
}
