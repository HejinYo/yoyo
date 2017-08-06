package cn.hejinyo.core.aop;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/6/14 22:31
 * @Description :
 */

import cn.hejinyo.system.model.dto.CurrentUserDTO;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 刷新缓存数据AOP
 *
 * @desc 用户个人资料发生改变后，对数据进行刷新缓存
 */
@Aspect
@Component
public class SysUserCacheAspect {

  /*  @Resource
    private CacheManager cacheManager;*/

    @Pointcut(value = "execution(public * cn.hejinyo.system.service.impl.SysUserServiceImpl.update(..))")
    private void update() {
    }

    @AfterReturning("update()")
    private void cleanCache(JoinPoint jp) {
        //Object[] obj = jp.getArgs();
        //CurrentUserDTO currentUserDTO = (CurrentUserDTO) obj[0];
        //clear(currentUserDTO.getUserName());
    }

  /*  private void clear(String key) {
        //身份缓存
        Cache<String, Object> userCache = cacheManager.getCache("sys-userCache");
        userCache.remove(key);
        //权限缓存
        Cache<String, Object> authCache = cacheManager.getCache("sys-authCache");
        authCache.remove(key);
    }*/
}
