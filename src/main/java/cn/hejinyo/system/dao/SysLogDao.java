package cn.hejinyo.system.dao;

import cn.hejinyo.core.base.dao.BaseDao;
import cn.hejinyo.system.model.po.SysLog;
import org.springframework.stereotype.Repository;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/6/17 16:54
 * @Description :
 */
@Repository("sysLogDao")
public interface SysLogDao extends BaseDao<SysLog> {

}
