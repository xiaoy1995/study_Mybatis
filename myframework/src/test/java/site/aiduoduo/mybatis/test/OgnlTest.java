package site.aiduoduo.mybatis.test;

import org.junit.Test;
import site.aiduoduo.mybatis.pojo.User;
import site.aiduoduo.mybatis.util.OgnlUtils;

import java.util.HashMap;

/**
 * @Author yangtianhao
 * @Date 2020/2/14 2:38 下午
 * @Version 1.0
 */
public class OgnlTest {
    @Test
    public void test(){
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        User user = new User();
        user.setPhone("18112412444");
        stringObjectHashMap.put("_parameter",user);
        boolean b = OgnlUtils.evaluateBoolean("phone != null", stringObjectHashMap.get("_parameter"));
        System.out.println(b);
    }
}
