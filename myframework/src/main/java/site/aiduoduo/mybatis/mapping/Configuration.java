package site.aiduoduo.mybatis.mapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Author yangtianhao
 * @Date 2020/2/5 5:28 下午
 * @Version 1.0
 */
public class Configuration {

    private Environment environment;
    private Map<String, MappedStatment> mappedStatmentMap = new HashMap<>();
    private Properties properties;

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public void addMappedStatment(MappedStatment mappedStatment) {
        mappedStatmentMap.put(mappedStatment.getId(), mappedStatment);
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public MappedStatment getMappedStatment(String id) {
        return mappedStatmentMap.get(id);
    }
}
