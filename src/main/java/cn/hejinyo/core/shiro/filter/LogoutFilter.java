package cn.hejinyo.core.shiro.filter;

import cn.hejinyo.core.consts.Const;
import cn.hejinyo.core.utils.Return;
import cn.hejinyo.core.utils.Tools;
import cn.hejinyo.system.model.dto.CurrentUserDTO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.web.servlet.AdviceFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/8/6 15:07
 * @Description :
 */
public class LogoutFilter extends AdviceFilter {
    private static final Logger logger = LoggerFactory.getLogger(StatelessAuthcFilter.class);

    public static final String DEFAULT_USERNAME_PARAM = Const.UserToken.USERNAME.getValue();
    public static final String DEFAULT_AUTHOR_PARAM = "Authorization";

    @Resource
    private CacheManager cacheManager;

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        String userToken = ((HttpServletRequest) request).getHeader(DEFAULT_AUTHOR_PARAM);
        try {
            //分离参数
            String[] parts = userToken.split("\\.");
            JSONObject jsonObject = JSON.parseObject(Tools.base64Decoder(parts[0]));
            //身份信息，用户名
            String username = jsonObject.getString(DEFAULT_USERNAME_PARAM);
            //当前ip
            String loginIp = jsonObject.getString(Const.UserToken.IPADDRESS.getValue());
            //验证登录ip是否变化
            if (loginIp.equals(getHost(request))) {
                Cache cache = cacheManager.getCache(StatelessLoginFilter.DEFAULT_TOKEN_CACHENAME);
                CurrentUserDTO userDTO = (CurrentUserDTO) cache.get(username);
                if (null != userDTO && userDTO.getUserToken().equals(userToken)) {
                    cache.remove(userDTO.getUserName());
                    if (logger.isDebugEnabled()) {
                        logger.debug("[username:" + userDTO.getUserName() + "] 退出登录");
                    }
                }
            } else {
                Return.response(response, Return.error());
                return false;
            }
        } catch (Exception e) {
            Return.response(response, Return.error());
            return false;
        }
        return true;
    }

    protected String getHost(ServletRequest request) {
        return request.getRemoteHost();
    }

}
