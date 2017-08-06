package cn.hejinyo.core.shiro.realm;

import cn.hejinyo.core.shiro.token.StatelessLoginToken;
import cn.hejinyo.system.model.dto.CurrentUserDTO;
import cn.hejinyo.system.service.SysPermissionService;
import cn.hejinyo.system.service.SysResourceService;
import cn.hejinyo.system.service.SysRoleService;
import cn.hejinyo.system.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * 自定义用户登录Realm
 *
 * @author HejinYo
 * @version 1.0
 * @email hejinyo@gmail.com
 * @since 1.0
 */
public class UserRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(UserRealm.class);

    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysResourceService sysResourceService;
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysPermissionService sysPermissionService;

    /**
     * 支持什么类型的token
     *
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    /**
     * 认证
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String loginName = (String) token.getPrincipal();//从Token中获取身份信息
        CurrentUserDTO currentUserDTO = sysUserService.getCurrentUser(loginName);//根据登录名查询用户信息
        /*if (null == currentUserDTO || -1 == currentUserDTO.getState()) {// 如果无相关用户或已删除则返回null
            logger.debug("登录名：[" + loginName + "],尝试登录，无此用户！");
            return null;
        } else if (1 == currentUserDTO.getState()) {//是否锁定
            logger.debug("登录名：[" + loginName + "],尝试登录，状态为锁定！");
            throw new LockedAccountException(); //抛出帐号锁定异常
        }*/
        currentUserDTO.setUserMenu(sysResourceService.getUserMenuList(currentUserDTO.getUserId()));//获得用户菜单，只在登录的时候查询一次
        String password = currentUserDTO.getUserPwd();//获取用户数据库中密码
        String salt = currentUserDTO.getUserSalt();//获取用户盐
        // 返回认证信息由父类AuthenticatingRealm进行认证
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(currentUserDTO, password, ByteSource.Util.bytes(salt), getName());
        return simpleAuthenticationInfo;
    }

    /**
     * 授权
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        CurrentUserDTO CurrentUserDTO = (CurrentUserDTO) principals.getPrimaryPrincipal();
        int userId = CurrentUserDTO.getUserId();
        //获取用户权限
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addStringPermissions(sysPermissionService.getUserPermisSet(userId));//获得权限信息
        authorizationInfo.addRoles(sysRoleService.getUserRoleSet(userId));//获得角色信息
        return authorizationInfo;

    }


    /**
     * 清除当前用户所有缓存，不用传入参数，自动获取当前主体
     */
    public void clearCached() {
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        super.clearCache(principals);
    }

    /**
     * 清除指定主体的授权缓存
     *
     * @param principals
     */
    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    /**
     * 清除指定主体的认证缓存
     *
     * @param principals
     */
    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    /**
     * 清除指定主体的所有缓存
     *
     * @param principals
     */
    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    /**
     * 清除服务器中所有授权缓存
     */
    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    /**
     * 清除服务器中所有认证缓存
     */
    public void clearAllCachedAuthenticationInfo() {
        //getAuthenticationCache().clear();
    }

    /**
     * 清除服务器中所有认证和授权缓存
     */
    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }
}
