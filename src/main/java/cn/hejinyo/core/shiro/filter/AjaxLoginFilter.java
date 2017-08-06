package cn.hejinyo.core.shiro.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import cn.hejinyo.core.jcaptcha.JCaptcha;
import cn.hejinyo.core.utils.Tools;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/4/15 9:44
 * @Description : Ajax登录拦截器
 */
public class AjaxLoginFilter extends AuthenticatingFilter {
    private static final Logger logger = LoggerFactory.getLogger(FormAuthenticationFilter.class);

    public static final String DEFAULT_USERNAME_PARAM = "username";
    public static final String DEFAULT_PASSWORD_PARAM = "password";
    public static final String DEFAULT_REMEMBER_ME_PARAM = "rememberMe";
    public static final String DEFAULT_VERIFY_CODE = "verifyCode";
    public static final String DEFAULT_SUCCESS_KEY_ATTRIBUTE_NAME = "shiroLoginSuccess";
    public static final String DEFAULT_ERROR_KEY_ATTRIBUTE_NAME = "shiroLoginFailure";
    public static final String DEFAULT_REDIRECT_SUCCESS_URL = "";//默认不进行登录后跳转successUrl

    private String usernameParam = DEFAULT_USERNAME_PARAM;
    private String passwordParam = DEFAULT_PASSWORD_PARAM;
    private String rememberMeParam = DEFAULT_REMEMBER_ME_PARAM;
    private String verifyCodeMeParam = DEFAULT_VERIFY_CODE;
    private String successKeyAttribute = DEFAULT_SUCCESS_KEY_ATTRIBUTE_NAME;
    private String failureKeyAttribute = DEFAULT_ERROR_KEY_ATTRIBUTE_NAME;
    private String redirectSuccessUrl = DEFAULT_REDIRECT_SUCCESS_URL;

    public AjaxLoginFilter() {
        setLoginUrl(DEFAULT_LOGIN_URL);
    }

    /**
     * 设置登录成功后的url
     *
     * @param successUrl
     */
    public void setSuccessUrl(String successUrl) {
        super.setSuccessUrl(successUrl);
    }

    /**
     * 设置特定原本访问的路径重定向到successUrl，默认所有路径都不重定向
     *
     * @param redirectPath
     */
    public void setRedirectSuccessUrl(String redirectPath) {
        this.redirectSuccessUrl = redirectPath;
    }

    public String getRedirectSuccessUrl() {
        return redirectSuccessUrl;
    }

    /**
     * 设置获取用户名时要查找的请求参数名称。
     */
    public void setUsernameParam(String usernameParam) {
        this.usernameParam = usernameParam;
    }

    public String getUsernameParam() {
        return usernameParam;
    }

    /**
     * 设置获取密码时要查找的请求参数名称。
     */
    public void setPasswordParam(String passwordParam) {
        this.passwordParam = passwordParam;
    }

    public String getPasswordParam() {
        return passwordParam;
    }

    /**
     * 设置获取记住我时要查找的请求参数名称。
     */
    public void setRememberMeParam(String rememberMeParam) {
        this.rememberMeParam = rememberMeParam;
    }

    public String getRememberMeParam() {
        return rememberMeParam;
    }

    /**
     * 获取是否记住我选择
     */
    protected boolean isRememberMe(ServletRequest request) {
        return (boolean) request.getAttribute(getRememberMeParam());
    }

    /**
     * 获取登录提交的验证码参数
     *
     * @return
     */
    public String getVerifyCodeMeParam() {
        return verifyCodeMeParam;
    }

    public void setVerifyCodeMeParam(String verifyCodeMeParam) {
        this.verifyCodeMeParam = verifyCodeMeParam;
    }

    /**
     * 设置登录出现异常时，异常存放变量的名称
     */
    public String getFailureKeyAttribute() {
        return failureKeyAttribute;
    }

    public void setFailureKeyAttribute(String failureKeyAttribute) {
        this.failureKeyAttribute = failureKeyAttribute;
    }

    protected void setFailureAttribute(ServletRequest request, AuthenticationException ae) {
        String className = ae.getClass().getName();
        request.setAttribute(getFailureKeyAttribute(), className);
    }

    /**
     * 设置登录成功，成功信息存放变量的名称
     */
    public String getSuccessKeyAttribute() {
        return successKeyAttribute;
    }

    public void setSuccessKeyAttribute(String successKeyAttribute) {
        this.successKeyAttribute = successKeyAttribute;
    }


    protected void setSuccessAttribute(ServletRequest request, AuthenticationException ae) {
        String className = ae.getClass().getName();
        request.setAttribute(getSuccessKeyAttribute(), className);
    }

    /**
     * 设置登录时的连接，会覆盖shiro配置文件中shiroFilter配置的url
     *
     * @param loginUrl
     */
    @Override
    public void setLoginUrl(String loginUrl) {
        String previous = getLoginUrl();
        if (previous != null) {
            //移除<property name="loginUrl" value="${shiro.loginUrl}"/>配置的登录地址
            this.appliedPaths.remove(previous);
        }
        super.setLoginUrl(loginUrl);//设置表单拦截中的登录地址
        if (logger.isTraceEnabled()) {
            logger.trace("Adding login url to applied paths.");
        }
        this.appliedPaths.put(getLoginUrl(), null);
    }

    /**
     * 确定当前主体是否应该被允许使当前的请求。
     *
     * @return true 如果请求允许访问
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = getSubject(request, response);
        if (subject.isAuthenticated() && isLoginRequest(request, response) && isLoginSubmission(request, response)) {//已经登录用户再次执行登陆
            return false;
        }
        return subject.isAuthenticated();//是否已经登录，已经登录继续拦截器链，未登录的执行onAccessDenied
    }

    /**
     * 表示当访问拒绝时是否已经处理了；如果返回true表示需要继续处理；
     * 如果返回false表示该拦截器实例已经处理了，将直接返回，不再继续后面的操作。
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {//检测是否是登录请求
            if (isLoginSubmission(request, response)) {//如果是POST方式请求
                if (logger.isTraceEnabled()) {
                    logger.trace("Login submission detected.  Attempting to execute login.");
                    logger.trace("检测到登录提交,尝试执行登录..");
                }
                getRequestBodyData(WebUtils.toHttp(request));//获取request body中的数据。

                HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
                String VerifyCode = (String) httpServletRequest.getAttribute(getVerifyCodeMeParam());

                if (JCaptcha.validateResponse(httpServletRequest, VerifyCode)) {//TO DO 检测验证码,并删除
                    executeLogin(request, response);//执行登录;
                } else {
                    request.setAttribute(getFailureKeyAttribute(), "verifyCodeError");//验证码错误
                }
                return true;
            } else {
                if (logger.isTraceEnabled()) {
                    logger.trace("Login page view.");
                }
                return true;//如果是非POST请求，将展示登录页面
            }
        } else {//不是登录请求，将被重定向到登录页面
            if (logger.isTraceEnabled()) {
                logger.trace("Attempting to access a path which requires authentication.  Forwarding to the " +
                        "Authentication url [" + getLoginUrl() + "]");
            }
            //保存当前地址并重定向到登录界面
            saveRequestAndRedirectToLogin(request, response);
            return false;//处理完成，不再继续拦截器链
        }
    }

    /**
     * 登录成功后执行的操作
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        //登录成功后重定向到原来访问的地址
        issueSuccessRedirect(request, response);
        //return false;//我们直接处理成功重定向，阻止拦截器链继续进行：
        return true;//Ajax登录，不管是否登录成功，都将继续执行
    }

    /**
     * 登录成功后重定向到原来访问的地址
     */
    @Override
    protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
        String successUrl = null;
        //boolean contextRelative = true;
        SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(request);
        if (savedRequest != null) {
            String requestURI = savedRequest.getRequestUrl();
            if (pathsMatch(getRedirectSuccessUrl(), requestURI)) {//登陆前访问路径满足则跳转，默认不跳转，"/*"表示所有登录都跳转，"/"表示登陆前访问根目录跳转
                successUrl = getSuccessUrl();
            } else if (savedRequest.getMethod().equalsIgnoreCase(AccessControlFilter.GET_METHOD)) {
                successUrl = requestURI;
                //contextRelative = false;
            }
        }
        if (successUrl == null) {
            successUrl = getSuccessUrl();
        }
        if (successUrl == null) {
            throw new IllegalStateException("Success URL not available via saved request or via the " +
                    "successUrlFallback method parameter. One of these must be non-null for " +
                    "issueSuccessRedirect() to work.");
        }
        request.setAttribute(getSuccessKeyAttribute(), successUrl);//登录成功将跳转的url放进request域中
        //WebUtils.issueRedirect(request, response, successUrl, null, contextRelative);
    }

    /**
     * 登录失败执行的操作
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        if (logger.isDebugEnabled()) {
            logger.debug("Authentication exception", e);
        }
        setFailureAttribute(request, e);
        //登录失败，让请求继续返回登录页面：
        return true;
    }

    /**
     * 如果请求是POST,则此默认实现仅返回true，否则返回false
     */
    @SuppressWarnings({"UnusedDeclaration"})
    protected boolean isLoginSubmission(ServletRequest request, ServletResponse response) {
        return (request instanceof HttpServletRequest) && WebUtils.toHttp(request).getMethod().equalsIgnoreCase(POST_METHOD);
    }

    /**
     * 需要登录重定向时子类使用的方法。
     */
    @Override
    protected void saveRequestAndRedirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        saveRequest(request);//保存当前的请求
        redirectToLogin(request, response);//重定向到登录界面
    }

    /**
     * 根据request中的用户名和密码，创建一个token
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String username = (String) request.getAttribute(getUsernameParam());
        String password = Tools.base64Decoder((String) request.getAttribute(getPasswordParam()));
        return createToken(username, password, request, response);
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
        logger.debug("Ajax登录信息：" + str);
        JSONObject json = JSON.parseObject(str);
        String username = json.getString(getUsernameParam());//从json对像中获取用户名
        String password = json.getString(getPasswordParam());//获取密码
        String verifycode = json.getString(getVerifyCodeMeParam());//获取验证码
        boolean rememberMe = false;//是否记住我
        if (null != json.getBoolean(getRememberMeParam())) {
            rememberMe = json.getBoolean(getRememberMeParam());
        }
        request.setAttribute(getPasswordParam(), password);
        request.setAttribute(getUsernameParam(), username);
        request.setAttribute(getVerifyCodeMeParam(), verifycode);
        request.setAttribute(getRememberMeParam(), rememberMe);
    }


}
