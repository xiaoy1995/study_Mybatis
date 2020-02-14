package site.aiduoduo.mybatis.mapping.sqlnode;

import site.aiduoduo.mybatis.mapping.DynamicContext;

import java.util.List;

/**
 * @Author yangtianhao
 * @Date 2020/2/13 4:09 下午
 * @Version 1.0
 */
public class MixedSqlNode implements SqlNode {

    List<SqlNode> sqlNodeList;

    public MixedSqlNode(List<SqlNode> sqlNodeList) {
        this.sqlNodeList = sqlNodeList;
    }

    @Override
    public void apply(DynamicContext context) {
        for (SqlNode sqlNode : sqlNodeList) {
            sqlNode.apply(context);
        }
    }
}
