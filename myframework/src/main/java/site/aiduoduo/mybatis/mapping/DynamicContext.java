package site.aiduoduo.mybatis.mapping;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author yangtianhao
 * @Date 2020/2/13 9:04 下午
 * @Version 1.0
 */
public class DynamicContext {
    private StringBuilder sql = new StringBuilder();
    private Map<String, Object> params = new HashMap<>();

    public DynamicContext(Object param) {
        params.put("_parameter", param);

    }

    public StringBuilder getSql() {
        return sql;
    }

    public void setSql(StringBuilder sql) {
        this.sql = sql;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
