package site.aiduoduo.mybatis.util;

import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;

/**
 * @Author yangtianhao
 * @Date 2020/2/14 12:06 下午
 * @Version 1.0
 */
public class OgnlUtils {
    public static Object getValue(String expression, Object paramObject) {

        OgnlContext ognlContext = new OgnlContext();
        ognlContext.setRoot(paramObject);
        try {
            Object parseExpression = Ognl.parseExpression(expression);
            Object value = Ognl.getValue(parseExpression, ognlContext, ognlContext.getRoot());
            return value;
        } catch (OgnlException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean evaluateBoolean(String expression, Object paramObject) {
        Object value = getValue(expression, paramObject);
        if (value instanceof Boolean) {
            return (Boolean)value;
        }
        return value != null;
    }
}
