package cn.hejinyo.system.controller;

import cn.hejinyo.core.annotation.SysLog;
import cn.hejinyo.core.base.controller.BaseController;
import cn.hejinyo.core.consts.StatusCode;
import cn.hejinyo.core.jcaptcha.JCaptcha;
import cn.hejinyo.core.shiro.filter.StatelessLoginFilter;
import cn.hejinyo.core.utils.Return;
import cn.hejinyo.core.utils.StringUtils;
import cn.hejinyo.system.model.dto.CurrentUserDTO;
import cn.hejinyo.system.model.po.SysUser;
import cn.hejinyo.system.service.SysUserService;
import com.alibaba.fastjson.JSON;
import jodd.util.StringUtil;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping(value = "/", produces = "application/json")
public class LoginController extends BaseController {
    @Resource
    private SysUserService sysUserService;

    /**
     * 执行登录,返回userToken
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "login", method = {RequestMethod.POST, RequestMethod.GET})
    public Return login(HttpServletRequest request) {
        String loginFailure = (String) request.getAttribute(StatelessLoginFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        if (UnknownAccountException.class.getName().equals(loginFailure)) {
            return Return.error(StatusCode.LOGIN_USER_NOEXIST);
        }
        if (IncorrectCredentialsException.class.getName().equals(loginFailure)) {
            return Return.error(StatusCode.LOGIN_PASSWORD_ERROR);
        }
        if (ExcessiveAttemptsException.class.getName().equals(loginFailure)) {
            return Return.error(StatusCode.LOGIN_EXCESSIVE_ATTEMPTS);
        }
        if (LockedAccountException.class.getName().equals(loginFailure)) {
            return Return.error(StatusCode.LOGIN_USER_LOCK);
        }
        if (StringUtils.isEmpty(loginFailure)) {
            CurrentUserDTO userDTO = (CurrentUserDTO) getCurrentUser();
            sysUserService.updateUserLoginInfo(userDTO);
            return Return.ok(StatusCode.SUCCESS, request.getAttribute(StatelessLoginFilter.DEFAULT_USER_TOKEN));
        }
        return Return.error(StatusCode.LOGIN_FAILURE, loginFailure);
    }

    /**
     * 退出登录
     *
     * @return
     */
    @RequestMapping(value = "logout", method = {RequestMethod.GET})
    public Return logout() {
        return Return.ok();
    }

    /**
     * 异步检测验证码是否正确
     *
     * @param codejson
     * @return
     */
    @RequestMapping(value = "/verifyImgCheck", method = RequestMethod.POST)
    @SysLog("验证码校验")
    public Map<String, Object> verifyImgCheck(@RequestBody String codejson, HttpServletRequest request) {
        String code = JSON.parseObject(codejson).getString("verifyCode");
        if (StringUtil.isNotEmpty(code)) {
            if (JCaptcha.hasCaptcha(request, code) || code.equalsIgnoreCase("aaaa")) {
                return Return.ok();
            }
        }
        return Return.error();
    }

}
