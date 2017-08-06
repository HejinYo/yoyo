package cn.hejinyo.system.service.impl;

import cn.hejinyo.core.utils.JsonUtils;
import cn.hejinyo.system.dao.SysLogDao;
import cn.hejinyo.system.model.po.SysLog;
import cn.hejinyo.system.service.SysLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/6/12 22:31
 * @Description :
 */
@Service("sysLogService")
public class SysLogServiceImpl implements SysLogService {

    @Resource
    private SysLogDao sysLogDao;

    @Override
    public void save(SysLog sysLog) {
        System.out.println(JsonUtils.toJSONString(sysLog));
        sysLogDao.save(sysLog);
    }
}
