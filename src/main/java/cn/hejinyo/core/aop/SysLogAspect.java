package cn.hejinyo.core.aop;

import cn.hejinyo.core.utils.IPUtils;
import cn.hejinyo.core.utils.JsonUtils;
import cn.hejinyo.core.utils.ShiroUtils;
import cn.hejinyo.core.utils.WebUtils;
import cn.hejinyo.system.model.po.SysLog;
import cn.hejinyo.system.service.SysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;


/**
 * 系统日志，切面处理类
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/6/12 22:19
 * @Description :
 */
@Aspect
@Component
public class SysLogAspect {

    @Resource
    private SysLogService sysLogService;

    @Pointcut("@annotation(cn.hejinyo.core.annotation.SysLog)")
    public void logPointCut() {

    }

    @Before("logPointCut()")
    public void saveSysLog(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        SysLog sysLog = new SysLog();
        cn.hejinyo.core.annotation.SysLog syslog = method.getAnnotation(cn.hejinyo.core.annotation.SysLog.class);
        if (syslog != null) {
            //注解上的描述
            sysLog.setOperation(syslog.value());
        }

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");

        //请求的参数
        Object[] args = joinPoint.getArgs();
        String params = JsonUtils.toJSONString(args[0]);//第一个参数
        sysLog.setParams(params);

        //获取request
        HttpServletRequest request = WebUtils.getHttpServletRequest();
        //设置IP地址
        sysLog.setIp(IPUtils.getIpAddr(request));

        //用户名
        //Optional<String> username = Optional.ofNullable(ShiroUtils.getCurrentUser().getUserName());
        //sysLog.setUserName(username.orElse("outline"));
        if(ShiroUtils.getSubject().isAuthenticated()){
            sysLog.setUserName(ShiroUtils.getCurrentUser().getUserName());
        }else{
            sysLog.setUserName("visitor");
        }
        sysLog.setCreateTime(new Date());
        //保存系统日志
        sysLogService.save(sysLog);
    }

}
