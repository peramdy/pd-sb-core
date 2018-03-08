package com.pd.spring.utils;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.springframework.objenesis.Objenesis;
import org.springframework.objenesis.ObjenesisStd;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author peramdy on 2018/2/7.
 */
public class SerializeUtils {

    /**
     * map 缓存
     */
    private static Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<Class<?>, Schema<?>>();

    /**
     * 不使用构造方法创建Java对象
     * new instance
     */
    private static Objenesis objenesis = new ObjenesisStd(true);


    /**
     * get schema
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> Schema<T> getSchema(Class<T> clazz) {
        Schema<T> schema = (Schema<T>) cachedSchema.get(clazz);
        if (schema == null) {
            schema = RuntimeSchema.getSchema(clazz);
            if (schema != null) {
                cachedSchema.put(clazz, schema);
            }
        }
        return schema;
    }


    /**
     * serialize
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> byte[] serializeObject(T t) {
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        Class<T> clazz = (Class<T>) t.getClass();
        Schema<T> schema = getSchema(clazz);
        byte[] protostuff = ProtobufIOUtil.toByteArray(t, schema, buffer);
        return protostuff;
    }


    /**
     * deserialize
     *
     * @param data
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T deserialize(byte[] data, Class<T> clazz) {
        T t = objenesis.newInstance(clazz);
        Schema<T> schema = getSchema(clazz);
        ProtobufIOUtil.mergeFrom(data, t, schema);
        return t;
    }


}
