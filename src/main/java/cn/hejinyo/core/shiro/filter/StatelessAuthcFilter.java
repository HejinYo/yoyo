package cn.hejinyo.core.shiro.filter;

import cn.hejinyo.core.consts.Const;
import cn.hejinyo.core.consts.StatusCode;
import cn.hejinyo.core.shiro.token.StatelessAuthcToken;
import cn.hejinyo.core.utils.JsonUtils;
import cn.hejinyo.core.utils.Return;
import cn.hejinyo.core.utils.Tools;
import cn.hejinyo.system.model.dto.CurrentUserDTO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/7/29 18:05
 * @Description :
 */
public class StatelessAuthcFilter extends AccessControlFilter {
    private static final Logger logger = LoggerFactory.getLogger(StatelessAuthcFilter.class);

    public static final String DEFAULT_USERNAME_PARAM = Const.UserToken.USERNAME.getValue();
    public static final String DEFAULT_AUTHOR_PARAM = "Authorization";

    @Resource
    private CacheManager cacheManager;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //header中获得用户token
        String userToken = ((HttpServletRequest) request).getHeader(DEFAULT_AUTHOR_PARAM);
        try {
            //分离参数
            String[] parts = userToken.split("\\.");
            JSONObject jsonObject = JSON.parseObject(Tools.base64Decoder(parts[0]));
            //token类型
            String tokenType = jsonObject.getString(Const.UserToken.TYPE.getValue());
            //身份信息，用户名
            String username = jsonObject.getString(DEFAULT_USERNAME_PARAM);
            //当前ip
            String loginIp = jsonObject.getString(Const.UserToken.IPADDRESS.getValue());
            //验证登录ip是否变化
            if (loginIp.equals(getHost(request))) {
                Cache cache = cacheManager.getCache(StatelessLoginFilter.DEFAULT_TOKEN_CACHENAME);
                CurrentUserDTO userDTO = (CurrentUserDTO) cache.get(username);
                if (null != userDTO) {//缓存中是否有此用户
                    StatelessAuthcToken token = createToken(tokenType, username, userToken, request, userDTO);
                    try {
                        //委托给Realm进行登录
                        getSubject(request, response).login(token);
                    } catch (Exception e) {
                        //userToken验证失败
                        if (logger.isDebugEnabled()) {
                            logger.debug("[ username:" + username + "] userToken 验证失败：" + userToken);
                        }
                        cache.remove(username);
                        Return.response(response, Return.error(StatusCode.TOKEN_OVERDUE));
                        return false;
                    }
                } else {
                    // /token失效
                    Return.response(response, Return.error(StatusCode.TOKEN_OVERDUE));
                    return false;
                }
            } else {
                //token异常
                Return.response(response, Return.error(StatusCode.TOKEN_NO_SAFE));
                return false;
            }
        } catch (Exception e) {
            //认证信息不符合规范，抛出异常
            if (logger.isDebugEnabled()) {
                logger.debug("token验证参数异常：" + e.toString());
            }
            Return.response(response, Return.error(StatusCode.USERTOKEN_PARAMETER_ERROR));
            return false;
        }
        return true;
    }

    protected StatelessAuthcToken createToken(String tokenType, String username, String userToken, ServletRequest request, CurrentUserDTO userDTO) {
        String host = getHost(request);
        return new StatelessAuthcToken(tokenType, username, userToken, host, userDTO);
    }

    protected String getHost(ServletRequest request) {
        return request.getRemoteHost();
    }

}

