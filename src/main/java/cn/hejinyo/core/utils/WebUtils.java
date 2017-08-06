package cn.hejinyo.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WebUtils extends org.springframework.web.util.WebUtils {
    private static final Logger logger = LoggerFactory.getLogger(WebUtils.class);

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    public static ServletContext getServletContext() {
        return ContextLoader.getCurrentWebApplicationContext().getServletContext();
    }

    /**
     * 判断是否ajax请求
     */
    public static boolean isAjax(HttpServletRequest request) {
        String accept = request.getHeader("accept");//（客户端）希望接受的数据类型
        //String content = request.getHeader("content-type");//发送端（客户端|服务器）发送的实体数据的数据类型
        String getHeaderX = request.getHeader("X-Requested-With");// AJAX特有的一个参数
        if ((accept != null && accept.equalsIgnoreCase("application/json")) || (getHeaderX != null && getHeaderX.equalsIgnoreCase("XMLHttpRequest"))) {
            return true;
        }
        return false;
    }


    /**
     * 读取cookie
     *
     * @param request
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String name) {
        Cookie cookie = getCookie(request, name);
        return cookie != null ? cookie.getValue() : null;
    }

    /**
     * 清除 某个指定的cookie
     *
     * @param response
     * @param key
     */
    public static void removeCookie(HttpServletResponse response, String key) {
        setCookie(response, key, null, 0);
    }

    /**
     * 设置cookie
     *
     * @param response
     * @param name
     * @param value
     * @param maxAgeInSeconds
     */
    public static void setCookie(HttpServletResponse response, String name, String value, int maxAgeInSeconds) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAgeInSeconds);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    /**
     * 设置cookie
     *
     * @param response
     * @param name     cookie名字
     * @param value    cookie值
     * @param maxAge   cookie生命周期 以秒为单位
     */
    public static void addCookie(HttpServletResponse response, String name,
                                 String value, int maxAge) {
        try {
            Cookie cookie = new Cookie(name, value);
            if (maxAge > 0)
                cookie.setMaxAge(maxAge);
            cookie.setPath("/");
            response.addCookie(cookie);
        } catch (Exception ex) {
            logger.error("创建Cookies发生异常！", ex);
        }
    }

    /**
     * 清空Cookie操作 clearCookie
     *
     * @param request
     * @param response
     * @return boolean
     * @author JIANG FEI Jun 19, 2014 10:12:17 AM
     */
    public static boolean clearCookie(HttpServletRequest request,
                                      HttpServletResponse response, String name) {
        boolean bool = false;
        Cookie[] cookies = request.getCookies();
        if (null == cookies || cookies.length == 0) return bool;
        try {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = new Cookie(name, null);
                cookie.setMaxAge(0);
                cookie.setPath("/");// 根据你创建cookie的路径进行填写
                response.addCookie(cookie);
                bool = true;
            }
        } catch (Exception ex) {
            logger.error("清空Cookies发生异常！", ex);
        }
        return bool;
    }

    /**
     * 清空Cookie操作 clearCookie
     *
     * @param request
     * @param response
     * @return boolean
     * @author JIANG FEI Jun 19, 2014 10:12:17 AM
     */
    public static boolean clearCookie(HttpServletRequest request,
                                      HttpServletResponse response, String name, String domain) {
        boolean bool = false;
        Cookie[] cookies = request.getCookies();
        if (null == cookies || cookies.length == 0) return bool;
        try {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = new Cookie(name, null);
                cookie.setMaxAge(0);
                cookie.setPath("/");// 根据你创建cookie的路径进行填写
                cookie.setDomain(domain);
                response.addCookie(cookie);
                bool = true;
            }
        } catch (Exception ex) {
            logger.error("清空Cookies发生异常！", ex);
        }
        return bool;
    }

    /**
     * 获取指定cookies的值 findCookieByName
     *
     * @param request
     * @param name
     * @return String
     */
    public static String findCookieByName(HttpServletRequest request,
                                          String name) {
        Cookie[] cookies = request.getCookies();
        if (null == cookies || cookies.length == 0) return null;
        String string = null;
        try {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                String cname = cookie.getName();
                if (!StringUtils.isBlank(cname) && cname.equals(name)) {
                    string = cookie.getValue();
                }

            }
        } catch (Exception ex) {
            logger.error("获取Cookies发生异常！", ex);
        }
        return string;
    }
}
