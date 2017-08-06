package cn.hejinyo.core.base.controller;

import cn.hejinyo.core.utils.ShiroUtils;
import cn.hejinyo.system.model.dto.CurrentUserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获得当前用户
     *
     * @return
     */
    protected CurrentUserDTO getCurrentUser() {
        return ShiroUtils.getCurrentUser();
    }

    /**
     * 获得当前用户编号
     *
     * @return
     */
    protected int getUserId() {
        return ShiroUtils.getUserId();
    }
}