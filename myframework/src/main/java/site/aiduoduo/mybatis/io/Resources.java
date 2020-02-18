package site.aiduoduo.mybatis.io;

import java.io.InputStream;

/**
 * @Author yangtianhao
 * @Date 2020/2/17 4:01 下午
 * @Version 1.0
 */
public class Resources {

    public static InputStream getResourceAsStream(String resource) {
        return Resources.class.getClassLoader().getResourceAsStream(resource);
    }
}
