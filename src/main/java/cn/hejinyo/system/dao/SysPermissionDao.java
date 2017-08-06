package cn.hejinyo.system.dao;

import cn.hejinyo.core.base.dao.BaseDao;
import cn.hejinyo.system.model.po.SysPermission;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/6/17 16:11
 * @Description :
 */
@Repository("sysPermissionDao")
public interface SysPermissionDao extends BaseDao<SysPermission> {

    /**
     * 查找用户编号对应的权限编码字符串
     *
     * @param userId
     * @return
     */
    Set<String> getUserPermisSet(int userId);
}
