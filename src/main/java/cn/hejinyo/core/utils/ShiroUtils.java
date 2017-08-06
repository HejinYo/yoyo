package cn.hejinyo.core.utils;

import cn.hejinyo.core.shiro.realm.UserRealm;
import cn.hejinyo.system.model.dto.CurrentUserDTO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * @author wjggwm
 * @ClassName ShiroFilterUtils
 * @Description Shiro Filter
 * @data 2016年12月12日 下午5:14:09
 */
public class ShiroUtils {
    @Resource
    private UserRealm userRealm;

    private static final Logger logger = LoggerFactory.getLogger(ShiroUtils.class);

    /**
     * 是否是Ajax请求
     *
     * @param request
     * @return
     */
    public static boolean isAjax(ServletRequest request) {
        return "XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"));
    }

    /**
     * response 输出JSON
     *
     * @param response
     * @param json
     * @throws IOException
     */
    public static void writeJson(ServletResponse response, String json) {
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            out = response.getWriter();
            out.print(json);
        } catch (Exception e) {
            logger.error("输出JSON异常:", e);
        } finally {
            if (null != out) {
                out.flush();
                out.close();
            }
        }
    }

    /**
     * 获取用户Session
     *
     * @return
     */
    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    /**
     * 获取用户Subject
     *
     * @return
     */
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 获取用户对象
     *
     * @return
     */
    public static CurrentUserDTO getCurrentUser() {
        return (CurrentUserDTO) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 获得用户id
     *
     * @return
     */
    public static int getUserId() {
        return getCurrentUser().getUserId();
    }

    /**
     * 设置用户 Attribute 参数
     *
     * @param key
     * @param value
     */
    public static void setSessionAttribute(Object key, Object value) {
        getSession().setAttribute(key, value);
    }

    /**
     * 获得用户 Attribute 参数
     *
     * @param key
     * @return
     */
    public static Object getSessionAttribute(Object key) {
        return getSession().getAttribute(key);
    }

    /**
     * 判断用户是否登录
     *
     * @return
     */
    public static boolean isLogin() {
        return SecurityUtils.getSubject().getPrincipal() != null;
    }

    /**
     * 用户注销
     */
    public static void logout() {
        SecurityUtils.getSubject().logout();
    }

}
