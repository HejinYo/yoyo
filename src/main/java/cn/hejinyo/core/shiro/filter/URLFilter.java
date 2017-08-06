package cn.hejinyo.core.shiro.filter;

import cn.hejinyo.core.consts.Const;
import cn.hejinyo.core.utils.JsonUtils;
import cn.hejinyo.core.utils.Return;
import cn.hejinyo.core.utils.ShiroUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class URLFilter extends AccessControlFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String uri = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();
        //System.out.println("请求的URL："+uri);
        return Boolean.TRUE;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (ShiroUtils.isAjax(request)) {
            String json = JsonUtils.toJSONString(Return.error("无访问权限！"));
            ShiroUtils.writeJson(response, json);
        } else {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
        return Boolean.FALSE;
    }

}
