package cn.hejinyo.system.dao;

import cn.hejinyo.core.base.dao.BaseDao;
import cn.hejinyo.system.model.po.SysRole;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/4/22 12:44
 * @Description :
 */
@Repository("sysRoleDao")
public interface SysRoleDao extends BaseDao<SysRole> {

    /**
     * 查找用户编号对应的角色编码字符串
     *
     * @param userId
     * @return
     */
    Set<String> getUserRoleSet(int userId);

}
