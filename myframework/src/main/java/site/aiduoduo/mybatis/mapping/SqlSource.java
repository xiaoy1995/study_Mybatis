package site.aiduoduo.mybatis.mapping;

/**
 * @Author yangtianhao
 * @Date 2020/2/13 3:16 下午
 * @Version 1.0
 */
public interface SqlSource {

    BoundSql getBoundSql(Object param);

}
