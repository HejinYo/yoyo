package cn.hejinyo.system.dao;

import cn.hejinyo.core.base.dao.BaseDao;
import cn.hejinyo.system.model.dto.CurrentUserDTO;
import cn.hejinyo.system.model.po.SysUser;
import org.springframework.stereotype.Repository;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/4/9 14:59
 * @Description : 用户实体类
 */
@Repository("sysUserDao")
public interface SysUserDao extends BaseDao<SysUser> {

    /**
     * 执行登录，查询用户登录信息
     *
     * @param userName
     * @return
     */
    CurrentUserDTO getCurrentUser(String userName);

    /**
     * 用户名是否存在
     *
     * @param userName
     * @return
     */
    int isExistUserName(String userName);

}
