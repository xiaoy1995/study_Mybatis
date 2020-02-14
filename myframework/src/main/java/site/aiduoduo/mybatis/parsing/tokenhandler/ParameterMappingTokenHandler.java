package site.aiduoduo.mybatis.parsing.tokenhandler;

import site.aiduoduo.mybatis.mapping.ParameterMapping;
import site.aiduoduo.mybatis.parsing.TokenHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author yangtianhao
 * @Date 2020/2/14 2:13 下午
 * @Version 1.0
 */
public class ParameterMappingTokenHandler implements TokenHandler {
    List<ParameterMapping> parameterMappingList = new ArrayList<>();

    @Override
    public String handleToken(String content) {
        String name = content;
        Class type = null;
        parameterMappingList.add(new ParameterMapping(name, type));
        return "?";
    }

    public List<ParameterMapping> getParameterMappingList() {
        return parameterMappingList;
    }
}
