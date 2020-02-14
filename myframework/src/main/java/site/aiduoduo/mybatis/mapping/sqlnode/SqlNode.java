package site.aiduoduo.mybatis.mapping.sqlnode;

import site.aiduoduo.mybatis.mapping.DynamicContext;

/**
 * @Author yangtianhao
 * @Date 2020/2/13 3:24 下午
 * @Version 1.0
 */
public interface SqlNode {
    void apply(DynamicContext context);
}
