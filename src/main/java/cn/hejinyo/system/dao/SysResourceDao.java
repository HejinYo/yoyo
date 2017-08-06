package cn.hejinyo.system.dao;

import cn.hejinyo.core.base.dao.BaseDao;
import cn.hejinyo.system.model.dto.UserMenuDTO;
import cn.hejinyo.system.model.po.SysResource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/4/22 14:08
 * @Description :
 */
@Repository("sysResourceMapper")
public interface SysResourceDao extends BaseDao<SysResource> {

    /**
     * 查询用户编号可用菜单
     *
     * @param userId
     * @return
     */
    List<UserMenuDTO> getUserMenuList(int userId);

}
