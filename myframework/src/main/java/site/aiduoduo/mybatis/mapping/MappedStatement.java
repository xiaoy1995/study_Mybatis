package site.aiduoduo.mybatis.mapping;

import site.aiduoduo.mybatis.mapping.sqlsource.SqlSource;

/**
 * @Author yangtianhao
 * @Date 2020/2/7 8:16 下午
 * @Version 1.0
 */
public class MappedStatement {
    private final String id;
    private final String parameterType;
    private final String resultType;
    private final String statementType;
    private final SqlSource sqlSource;

    public MappedStatement(String id, String parameterType, String resultType, SqlSource sqlSource,
        String statementType) {
        this.id = id;
        this.parameterType = parameterType;
        this.resultType = resultType;
        this.sqlSource = sqlSource;
        this.statementType = statementType;
    }

    public String getId() {
        return id;
    }

    public String getParameterType() {
        return parameterType;
    }

    public String getResultType() {
        return resultType;
    }

    public SqlSource getSqlSource() {
        return sqlSource;
    }

    public String getStatementType() {
        return statementType;
    }

    public BoundSql getBoundSql(Object param) {
        return this.sqlSource.getBoundSql(param);
    }
}
