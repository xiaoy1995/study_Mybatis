package site.aiduoduo.mybatis.mapping.sqlnode;

import site.aiduoduo.mybatis.mapping.DynamicContext;
import site.aiduoduo.mybatis.mapping.SqlNode;

/**
 * @Author yangtianhao
 * @Date 2020/2/13 4:09 下午
 * @Version 1.0
 */
public class WhereSqlNode implements SqlNode {
    private SqlNode contents;

    public WhereSqlNode(SqlNode contents) {
        this.contents = contents;
    }

    @Override
    public void apply(DynamicContext context) {
        DynamicContext dynamicContext = new DynamicContext(context.getParams().get("_parameter"));
        contents.apply(dynamicContext);
        String sql = dynamicContext.getSql().toString();
        if (sql.length() > 0) {
            if (sql.startsWith("and") || sql.startsWith("AND")) {
                sql = sql.substring(3);
            }
            context.getSql().append(" where").append(" " + sql);
        }

    }
}
