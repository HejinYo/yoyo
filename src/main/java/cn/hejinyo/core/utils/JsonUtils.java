package cn.hejinyo.core.utils;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class JsonUtils {
    private JsonUtils() {
        throw new Error("工具类不能实例化！");
    }

    public static String toJSONString(Object entity) {
        return JSON.toJSONString(entity);
    }

    public static Map<String, Object> toMap(String json) {
        return (Map) JSON.parse(json);
    }

    public static <T> T toObject(String json, Class<T> c) {
        return JSON.parseObject(json, c);
    }

    /**
     * @return [{"data":"123","message"},{"hejinyo","status":0}]
     */
    public static String toListJson(String json) {
        String jsonStr = json;
        if (!StringUtils.isBlank(json) && !json.startsWith("[")) {
            jsonStr = "[" + json + "]";
        }
        return jsonStr;
    }

    public static <T> List<T> toList(String json, Class<T> c) {
        String jsonStr = json;
        if (!StringUtils.isBlank(json)) {
            if (!json.startsWith("[")) {
                jsonStr = "[" + json + "]";
            }
            return JSON.parseArray(jsonStr, c);
        } else {
            return new ArrayList();
        }
    }

}