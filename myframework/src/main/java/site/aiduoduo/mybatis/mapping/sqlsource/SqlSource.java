package site.aiduoduo.mybatis.mapping.sqlsource;

import site.aiduoduo.mybatis.mapping.BoundSql;

/**
 * @Author yangtianhao
 * @Date 2020/2/13 3:16 下午
 * @Version 1.0
 */
public interface SqlSource {

    BoundSql getBoundSql(Object param);

}
