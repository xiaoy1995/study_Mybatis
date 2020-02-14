package site.aiduoduo.mybatis.mapping;

/**
 * @Author yangtianhao
 * @Date 2020/2/13 3:24 下午
 * @Version 1.0
 */
public interface SqlNode {
    void apply(DynamicContext context);
}
