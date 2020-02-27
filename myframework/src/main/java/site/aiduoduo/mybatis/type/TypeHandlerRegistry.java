package site.aiduoduo.mybatis.type;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author yangtianhao
 * @Date 2020/2/27 12:03 上午
 * @Version 1.0
 */
public final class TypeHandlerRegistry {

    // 枚举型map
    private final Map<JdbcType, TypeHandler<?>> JDBC_TYPE_HANDLER_MAP =
        new EnumMap<JdbcType, TypeHandler<?>>(JdbcType.class);
    private final Map<Type, Map<JdbcType, TypeHandler<?>>> TYPE_HANDLER_MAP =
        new HashMap<Type, Map<JdbcType, TypeHandler<?>>>();
    private final Map<Class<?>, TypeHandler<?>> ALL_TYPE_HANDLERS_MAP = new HashMap<Class<?>, TypeHandler<?>>();

    public TypeHandlerRegistry() {
        // 构造函数里注册系统内置的类型处理器
        // 以下是为多个类型注册到同一个handler

        register(Integer.class, new IntegerTypeHandler());
        register(int.class, new IntegerTypeHandler());
        register(Integer.class, JdbcType.INTEGER, new IntegerTypeHandler());
        register(int.class, JdbcType.INTEGER, new IntegerTypeHandler());

        // 以下是为同一个类型的多种变种注册到多个不同的handler
        register(String.class, new StringTypeHandler());
        register(String.class, JdbcType.CHAR, new StringTypeHandler());
        register(String.class, JdbcType.VARCHAR, new StringTypeHandler());
        register(JdbcType.CHAR, new StringTypeHandler());
        register(JdbcType.VARCHAR, new StringTypeHandler());

        register(Date.class, new DateTypeHandler());
        register(JdbcType.TIMESTAMP, new DateTypeHandler());

        register(Object.class, new ObjectTypeHandler());
        register(Object.class, JdbcType.OTHER, new ObjectTypeHandler());

    }

    public <T> TypeHandler<T> getTypeHandler(Class<T> type) {
        return getTypeHandler((Type)type, null);
    }

    public <T> TypeHandler<T> getTypeHandler(Class<T> type, JdbcType jdbcType) {
        return getTypeHandler((Type)type, jdbcType);
    }

    @SuppressWarnings("unchecked")
    private <T> TypeHandler<T> getTypeHandler(Type type, JdbcType jdbcType) {
        Map<JdbcType, TypeHandler<?>> jdbcHandlerMap = TYPE_HANDLER_MAP.get(type);
        TypeHandler<?> handler = null;
        if (jdbcHandlerMap != null) {
            handler = jdbcHandlerMap.get(jdbcType);
            if (handler == null) {
                handler = jdbcHandlerMap.get(null);
            }
        }
        return (TypeHandler<T>)handler;
    }

    public void register(JdbcType jdbcType, TypeHandler<?> handler) {
        JDBC_TYPE_HANDLER_MAP.put(jdbcType, handler);
    }

    // java type + handler

    public <T> void register(Class<T> javaType, TypeHandler<? extends T> typeHandler) {
        register((Type)javaType, typeHandler);
    }

    private <T> void register(Type javaType, TypeHandler<? extends T> typeHandler) {

        register(javaType, null, typeHandler);

    }

    // java type + jdbc type + handler

    public <T> void register(Class<T> type, JdbcType jdbcType, TypeHandler<? extends T> handler) {
        register((Type)type, jdbcType, handler);
    }

    private void register(Type javaType, JdbcType jdbcType, TypeHandler<?> handler) {
        if (javaType != null) {
            Map<JdbcType, TypeHandler<?>> map = TYPE_HANDLER_MAP.get(javaType);
            if (map == null) {
                map = new HashMap<JdbcType, TypeHandler<?>>();
                TYPE_HANDLER_MAP.put(javaType, map);
            }
            map.put(jdbcType, handler);
        }
        ALL_TYPE_HANDLERS_MAP.put(handler.getClass(), handler);
    }

}
