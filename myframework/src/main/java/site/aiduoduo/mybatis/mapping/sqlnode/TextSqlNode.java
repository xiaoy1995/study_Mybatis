package site.aiduoduo.mybatis.mapping.sqlnode;

import site.aiduoduo.mybatis.mapping.DynamicContext;
import site.aiduoduo.mybatis.parsing.GenericTokenParser;
import site.aiduoduo.mybatis.parsing.TokenHandler;
import site.aiduoduo.mybatis.util.OgnlUtils;
import site.aiduoduo.mybatis.util.SimpleTypeRegistry;

/**
 * @Author yangtianhao
 * @Date 2020/2/13 3:42 下午
 * @Version 1.0 包含${}的Sql片段
 */
public class TextSqlNode implements SqlNode {

    private String text;

    public TextSqlNode(String text) {
        this.text = text;
    }

    public boolean isDynamic() {
        if (text.indexOf("${") > 0) {
            return true;
        }
        return false;
    }

    @Override
    public void apply(DynamicContext context) {
        BindingTokenHandler bindingTokenHandler = new BindingTokenHandler(context);
        GenericTokenParser genericTokenParser = new GenericTokenParser("${", "}", bindingTokenHandler);
        String parse = genericTokenParser.parse(text);
        context.getSql().append(parse);
    }

    private static class BindingTokenHandler implements TokenHandler {
        private DynamicContext context;

        public BindingTokenHandler(DynamicContext context) {
            this.context = context;
        }

        @Override
        public String handleToken(String content) {
            Object parameter = context.getParams().get("_parameter");
            if (parameter != null) {
                if (SimpleTypeRegistry.isSimpleType(parameter.getClass())) {
                    context.getParams().put("value", String.valueOf(parameter));
                }
            } else {
                context.getParams().put("value", "");
            }
            Object value = OgnlUtils.getValue(content, context.getParams());
            return value == null ? "" : String.valueOf(value);
        }
    }
}
