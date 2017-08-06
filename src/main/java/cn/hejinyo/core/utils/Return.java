package cn.hejinyo.core.utils;

import cn.hejinyo.core.consts.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * 返回JSON结果 工具类
 *
 * @author ：HejinYo hejinyo@gmail.com
 * @version 1.0
 * @apiNote code：   结果标识：0：成功  1：失败
 * <p>
 * message：  结果信息：失败时为失败原因，成功就填写详细信息
 * <p>
 * result：     数据内容：可以发送map、list等其他所有的对象
 */
public class Return extends HashMap<String, Object> {
    private static final int SUCCESS = StatusCode.SUCCESS.getKey();
    private static final int ERROR = StatusCode.FAILURE.getKey();
    private static final int INITIAL = 4;
    private static final String MESSAGE = "message";
    private static final String CODE = "code";
    private static final String RESUTLT = "result";

    public Return() {
        super();
    }

    public Return(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * @return Map： {"code":0}
     */
    public static Return ok() {
        //initialCapacity = (需要存储的元素个数 / 负载因子) + 1。注意负载因子（即loader factor）默认为 0.75，如果暂时无法确定初始值大小，请设置为 16
        Return jsonMap = new Return(INITIAL);
        jsonMap.put(CODE, SUCCESS);
        return jsonMap;
    }

    /**
     * @param message
     * @return Map：{"message":"成功","code":0}
     */
    public static Return ok(String message) {
        Return jsonMap = ok();
        jsonMap.put(MESSAGE, message);
        return jsonMap;
    }

    /**
     * @param result
     * @return Map：{"result":["1","2"],"code":0}
     */
    public static Return ok(Object result) {
        Return jsonMap = ok();
        jsonMap.put(RESUTLT, result);
        return jsonMap;
    }

    public static Return ok(StatusCode statusCode) {
        return error(statusCode.getKey(), statusCode.getValue());
    }

    public static Return ok(StatusCode statusCode, Object result) {
        Return jsonMap = error(statusCode);
        jsonMap.put(RESUTLT, result);
        return jsonMap;
    }

    /**
     * @param message
     * @param result
     * @return Map：{"result":["1","2"],"message":"成功","code":0}
     */
    public static Return ok(String message, Object result) {
        Return jsonMap = ok(message);
        jsonMap.put(RESUTLT, result);
        return jsonMap;
    }

    /**
     * @return Map： {"code":1}
     */
    public static Return error() {
        Return jsonMap = new Return(INITIAL);//存放信息的对象
        jsonMap.put(CODE, ERROR);
        return jsonMap;
    }

    /**
     * @return Map： {"code":1000}
     */
    public static Return error(int code) {
        Return jsonMap = new Return(INITIAL);//存放信息的对象
        jsonMap.put(CODE, code);
        return jsonMap;
    }

    /**
     * @param message
     * @return Map：{"message":"失败","code":1}
     */
    public static Return error(String message) {
        Return jsonMap = error();
        jsonMap.put(MESSAGE, message);
        return jsonMap;
    }

    /**
     * @param message
     * @return Map：{"message":"失败","code":1000}
     */
    public static Return error(int code, String message) {
        Return jsonMap = error(code);
        jsonMap.put(MESSAGE, message);
        return jsonMap;
    }

    public static Return error(StatusCode statusCode) {
        return error(statusCode.getKey(), statusCode.getValue());
    }

    public static Return error(StatusCode statusCode, Object result) {
        Return jsonMap = error(statusCode);
        jsonMap.put(RESUTLT, result);
        return jsonMap;
    }

    /**
     * @param result
     * @return Map：{result":["1","2"],"code":1}
     */
    public static Return error(Object result) {
        Return jsonMap = error();
        jsonMap.put(RESUTLT, result);
        return jsonMap;
    }

    /**
     * @param result
     * @return Map：{result":["1","2"],"code":1}
     */

    public static Return error(int code, Object result) {
        Return jsonMap = error(code);
        jsonMap.put(RESUTLT, result);
        return jsonMap;
    }

    /**
     * @param message
     * @param result
     * @return Map：{result":["1","2"],"message":"失败","code":1}
     */
    public static Return error(String message, Object result) {
        Return jsonMap = error(message);
        jsonMap.put(RESUTLT, result);
        return jsonMap;
    }


    /**
     * @param message
     * @param result
     * @return Map：{result":["1","2"],"message":"失败","code":1}
     */
    public static Return error(int code, String message, Object result) {
        Return jsonMap = error(code, message);
        jsonMap.put(RESUTLT, result);
        return jsonMap;
    }

    public Return put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    //response 返回json格式Return数据
    public static void response(HttpServletResponse httpResponse, int statusCode, Return returns) {
        httpResponse.setStatus(statusCode);
        httpResponse.setCharacterEncoding("UTF-8"); //设置编码格式
        httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);//设置ContentType，返回内容的MIME类型
        httpResponse.setHeader("Cache-Control", "no-cache");//告诉所有的缓存机制是否可以缓存及哪种类型
        String json = JsonUtils.toJSONString(returns);
        try {
            httpResponse.getWriter().write(json);
            httpResponse.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void response(ServletResponse response, int statusCode, Return returns) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        response(httpResponse, statusCode, returns);
    }

    public static void response(ServletResponse response, Return returns) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        response(httpResponse, HttpStatus.OK.value(), returns);
    }
}
