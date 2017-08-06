package cn.hejinyo.system.service.impl;

import cn.hejinyo.core.annotation.SysLog;
import cn.hejinyo.core.utils.*;
import cn.hejinyo.system.dao.SysUserDao;
import cn.hejinyo.system.model.dto.CurrentUserDTO;
import cn.hejinyo.system.model.po.SysUser;
import cn.hejinyo.system.model.vo.SysUserVO;
import cn.hejinyo.system.service.SysUserService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserDao sysUserDao;

    @Override
    public CurrentUserDTO getCurrentUser(String userName) {
        return sysUserDao.getCurrentUser(userName);
    }

    @Override
    public SysUserVO get(int userID) {
        SysUser sysUser = sysUserDao.get(userID);
        return PojoConvertUtil.convert(sysUser, SysUserVO.class);
    }

    @Override
    public List<SysUser> listPage(PageQuery pageQuery) {
        return sysUserDao.listPage(pageQuery);
    }

    @Override
    @SysLog("增加用户")
    public int save(SysUserVO userVO) {
        //创建新的 PO
        SysUser sysUser = new SysUser();
        //用户名小写
        sysUser.setUserName(StringUtils.toLowerCase(userVO.getUserName()));
        //用户盐,随机数
        String salt = new SecureRandomNumberGenerator().nextBytes().toHex();
        sysUser.setUserSalt(salt);
        //加密密码
        sysUser.setUserPwd(Tools.userPassword(userVO.getUserPwd(), salt));
        //邮箱
        sysUser.setEmail(userVO.getEmail());
        //手机
        sysUser.setPhone(userVO.getPhone());
        //创建人员
        sysUser.setCreateId(ShiroUtils.getUserId());
        //创建时间
        sysUser.setCreateTime(new Date());
        //默认状态：正常
        sysUser.setState(0);
        return sysUserDao.save(sysUser);
    }

    @Override
    public int isExistUserName(String userName) {
        //查询用户名是否存在
        return sysUserDao.isExistUserName(StringUtils.toLowerCase(userName));
    }

    @Override
    @SysLog("更新用户")
    public int update(SysUserVO userVO) {
        //用户原来信息
        SysUser sysUserOld = sysUserDao.get(userVO.getUserId());
        if (null == sysUserOld) {
            return -1;
        }
        //修改标志
        boolean flag = Boolean.FALSE;
        //新的PO
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userVO.getUserId());

        String userStr = userVO.getUserName();
        String pwdStr = userVO.getUserPwd();
        String email = userVO.getEmail();
        String phone = userVO.getPhone();
        String loginIp = userVO.getLoginIp();
        Date loginTime = userVO.getLoginTime();
        Integer state = userVO.getState();

        //校验用户名是否修改
        if (StringUtils.isNotNull(userStr)) {
            String userName = StringUtils.toLowerCase(userStr);
            if (!userName.equals(sysUserOld.getUserName())) {
                if (sysUserDao.isExistUserName(userName) > 0) {
                    //新的用户名已经存在
                    return -2;
                }
                sysUser.setUserName(userName);
                flag = true;
            }
        }
        //密码是否修改
        if (StringUtils.isNotNull(pwdStr)) {
            //加密新密码
            String userPwd = Tools.userPassword(userVO.getUserPwd(), sysUserOld.getUserSalt());
            if (!userPwd.equals(sysUserOld.getUserPwd())) {
                //用户盐,随机数
                String salt = new SecureRandomNumberGenerator().nextBytes().toHex();
                sysUser.setUserSalt(salt);
                //加密密码
                sysUser.setUserPwd(Tools.userPassword(userVO.getUserPwd(), salt));
                flag = true;
            }
        }
        //邮箱是否修改
        if (StringUtils.isNotNull(email)) {
            if (!email.equals(sysUserOld.getEmail())) {
                sysUser.setEmail(email);
                flag = true;
            }
        }
        //手机是否修改
        if (StringUtils.isNotNull(phone)) {
            if (!phone.equals(sysUserOld.getPhone())) {
                sysUser.setPhone(phone);
                flag = true;
            }
        }
        //登录IP是否修改
        if (StringUtils.isNotNull(loginIp)) {
            if (!loginIp.equals(sysUserOld.getLoginIp())) {
                sysUser.setLoginIp(loginIp);
                flag = true;
            }
        }
        //登录时间是否修改
        if (null != loginTime) {
            if (!loginTime.equals(sysUserOld.getLoginTime())) {
                sysUser.setLoginTime(loginTime);
                flag = true;
            }
        }
        //状态是否修改
        if (null != state) {
            if (!state.equals(sysUserOld.getState())) {
                sysUser.setState(state);
                flag = true;
            }
        }
        if (flag) {
            return sysUserDao.update(sysUser);
        }
        return -3;
    }

    @Override
    @SysLog("删除用户")
    public int delete(SysUserVO userVO) {
        return sysUserDao.delete(userVO.getUserId());
    }

    @Override
    @SysLog("用户登录")
    public int updateUserLoginInfo(CurrentUserDTO userDTO) {
        SysUser sysUser = new SysUser();
        sysUser.setLoginTime(userDTO.getLoginTime());
        sysUser.setUserId(userDTO.getUserId());
        sysUser.setLoginIp(userDTO.getLoginIp());
        return sysUserDao.update(sysUser);
    }
}
