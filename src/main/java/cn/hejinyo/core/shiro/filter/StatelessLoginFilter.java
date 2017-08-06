package cn.hejinyo.core.shiro.filter;

import cn.hejinyo.core.consts.Const;
import cn.hejinyo.core.consts.StatusCode;
import cn.hejinyo.core.exception.customException.InfoException;
import cn.hejinyo.core.shiro.token.StatelessLoginToken;
import cn.hejinyo.core.utils.Return;
import cn.hejinyo.core.utils.ShiroUtils;
import cn.hejinyo.core.utils.Tools;
import cn.hejinyo.system.model.dto.CurrentUserDTO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/7/29 19:50
 * @Description :
 */
public class StatelessLoginFilter extends AccessControlFilter {

    private static final Logger logger = LoggerFactory.getLogger(StatelessLoginFilter.class);

    public static final String DEFAULT_USERNAME_PARAM = "username";
    public static final String DEFAULT_PASSWORD_PARAM = "password";
    public static final String DEFAULT_ERROR_KEY_ATTRIBUTE_NAME = "shiroLoginFailure";
    public static final String DEFAULT_TOKEN_CACHENAME = "tokenCache";
    public static final String DEFAULT_USER_TOKEN = "userToken";

    @Resource
    private CacheManager cacheManager;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        try {
            //获取request body中的数据
            getRequestBodyData(WebUtils.toHttp(request));
        } catch (Exception e) {
            Return.response(response, Return.error(StatusCode.PARAMETER_ERROR));
            return false;
        }
        // HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        String username = (String) request.getAttribute(DEFAULT_USERNAME_PARAM);
        String password = (String) request.getAttribute(DEFAULT_PASSWORD_PARAM);
        String tokenType = Const.ShiroToken.WEB_LOGIN.getKey();
        //创建shiro token
        StatelessLoginToken loginToken = createToken(tokenType, username, password, request);
        Cache cache = cacheManager.getCache(DEFAULT_TOKEN_CACHENAME);
        try {
            //委托给Realm进行登录
            getSubject(request, response).login(loginToken);
        } catch (Exception e) {
            //登录失败
            if (logger.isDebugEnabled()) {
                logger.error("[" + username + "] 登录失败：", e);
            }
            setFailureAttribute(request, e);
            return true;
        }
        CurrentUserDTO userDTO = (CurrentUserDTO) ShiroUtils.getSubject().getPrincipal();
        userDTO.setLoginIp(loginToken.getHost());
        userDTO.setLoginTime(new Date());
        //生成用户token
        String userToken = Tools.createToken(userDTO, Const.ShiroToken.WEB_TOKEN.getKey());
        userDTO.setUserToken(userToken);
        //认证成功，用户信息放入缓存
        cache.put(userDTO.getUserName(), userDTO);
        //放入request中
        request.setAttribute(DEFAULT_USER_TOKEN, userToken);
        return true;
    }

    /**
     * 获取request Body 中的json数据，并存放在request域中
     *
     * @param request
     */
    @SneakyThrows //抛异常,捕获异常并在catch中用Lombok.sneakyThrow(e)把异常抛出
    public void getRequestBodyData(HttpServletRequest request) {
        InputStreamReader inputStreamReader = new InputStreamReader(request.getInputStream(), "utf-8");
        @Cleanup BufferedReader br = new BufferedReader(inputStreamReader); //@Cleanup 保证此变量代表的资源会被自动关闭,调用资源的close()方法
        StringBuffer sb = new StringBuffer("");
        String temp;
        while ((temp = br.readLine()) != null) {
            sb.append(temp);
        }
        String str = sb.toString();//获取request body中的json字符串
        JSONObject json = JSON.parseObject(str);
        String username = json.getString(DEFAULT_USERNAME_PARAM);//从json对像中获取用户名
        String password = json.getString(DEFAULT_PASSWORD_PARAM);//获取密码
        request.setAttribute(DEFAULT_PASSWORD_PARAM, password);
        request.setAttribute(DEFAULT_USERNAME_PARAM, username);
    }

    protected StatelessLoginToken createToken(String tokenType, String username, String password, ServletRequest request) {
        String host = getHost(request);
        return new StatelessLoginToken(tokenType, username, password, host);
    }

    protected String getHost(ServletRequest request) {
        return request.getRemoteHost();
    }

    protected void setFailureAttribute(ServletRequest request, Exception ae) {
        String className = ae.getClass().getName();
        request.setAttribute(DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, className);
    }

}
