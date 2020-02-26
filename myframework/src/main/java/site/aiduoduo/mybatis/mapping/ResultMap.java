package site.aiduoduo.mybatis.mapping;

import java.util.List;

/**
 * @Author yangtianhao
 * @Date 2020/2/26 10:56 下午
 * @Version 1.0
 */
public class ResultMap {
    private String id;
    private Class<?> type;
    private List<ResultMapping> resultMappings;

    public ResultMap(String id, Class<?> aClass) {
        this.id = id;
        this.type = aClass;
    }

    public String getId() {
        return id;
    }

    public Class<?> getType() {
        return type;
    }

    public List<ResultMapping> getResultMappings() {
        return resultMappings;
    }
}
