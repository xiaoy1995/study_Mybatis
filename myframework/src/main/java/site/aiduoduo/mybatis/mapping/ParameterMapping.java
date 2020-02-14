package site.aiduoduo.mybatis.mapping;

/**
 * @Author yangtianhao
 * @Date 2020/2/14 2:14 下午
 * @Version 1.0
 */
public class ParameterMapping {
    private String name;
    private Class type;

    public ParameterMapping(String name, Class type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }
}
