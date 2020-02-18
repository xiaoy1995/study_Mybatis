package site.aiduoduo.mybatis.executor.parameter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Author yangtianhao
 * @Date 2020/2/18 6:25 下午
 * @Version 1.0
 */
public interface ParameterHandle {
    // 设置参数
    void setParameters(PreparedStatement ps) throws SQLException;
}
