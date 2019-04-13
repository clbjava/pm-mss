package com.pm.mss.comm.util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class JsonUtil {
    // 定义ObjectMapper对象，用于数据转换
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * getJSON:(对象转换成JSON). <br/>
     *
     * @param object
     * @return String
     */
    public static String getJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * getBean:(JSON字符串转对象). <br/>
     *
     * @param json
     * @param t
     * @return T
     */
    public static <T> T getBean(String json, Class<T> t) {
        try {
            return mapper.readValue(json, t);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * jsonToObject:(JSON字符串转对象). <br/>
     *
     * @param json
     * @param beanType
     * @return T
     */
    public static <T> List<T> getBeanList(String json, Class<T> beanType) {
        try {
            //方案一：
            //            JavaType javaType = mapper.getTypeFactory().constructCollectionType(List.class, beanType);
            //方案二：
            JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, beanType);
            return mapper.readValue(json, javaType);
            //方案三
            //            TypeReference<T> typeReference = new TypeReference<T>() {};
            //            return mapper.readValue(json, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
