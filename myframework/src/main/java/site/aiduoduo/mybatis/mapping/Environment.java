package site.aiduoduo.mybatis.mapping;

import javax.sql.DataSource;

/**
 * @Author yangtianhao
 * @Date 2020/2/5 5:29 下午
 * @Version 1.0
 */

public class Environment {
    private String id;
    private DataSource dataSource;

    public Environment(String id, DataSource dataSource) {
        this.id = id;
        this.dataSource = dataSource;
    }

    public String getId() {
        return id;
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}