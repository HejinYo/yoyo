package cn.hejinyo.system.controller;

import cn.hejinyo.core.base.controller.BaseController;
import cn.hejinyo.core.utils.*;
import cn.hejinyo.core.validator.group.SaveGroup;
import cn.hejinyo.core.validator.group.UpdateGroup;
import cn.hejinyo.system.model.po.SysUser;
import cn.hejinyo.system.model.vo.SysUserVO;
import cn.hejinyo.system.service.SysUserService;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/6/17 22:29
 * @Description :
 */
@RestController
@RequestMapping("/system/user")
public class SysUserController extends BaseController {
    @Resource
    private SysUserService sysUserService;

    /**
     * 获得一个用户信息
     *
     * @return
     */
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public Return get(@PathVariable(value = "userId") int userId) {
        SysUserVO userVO = sysUserService.get(userId);
        if (userVO == null) {
            return Return.error("用户不存在");
        }
        return Return.ok(userVO);
    }

    /**
     * 分页查询用户信息
     *
     * @return
     */
    @RequestMapping(value = "/listPage", method = RequestMethod.GET)
    public Return list(PageQuery pageQuery, SysUserVO userVO, @RequestParam(required = false) int startIndex, @RequestParam(required = false) int pageSize, @RequestParam(required = false, value = "search[value]") String search) {
        pageQuery.setQuery(userVO);
        SysUser s1 = new SysUser();
        if (StringUtils.isNotEmpty(pageQuery.getSidx())) {
            String sidx = pageQuery.getSidx();
            String json = "{'" + sidx + "':'" + 0 + "'}";
            s1 = JsonUtils.toObject(json, SysUser.class);
        }
        System.out.println(s1);
        pageQuery.setOrd(s1);
        System.out.println(pageQuery);
        //PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize());
        PageHelper.offsetPage(startIndex, pageSize);
        List<SysUser> sysUserList = sysUserService.listPage(pageQuery);
        PageInfo<SysUser> userPageInfo = new PageInfo<>(sysUserList, 3);
        return Return.ok(userPageInfo);
    }

    /**
     * 分页查询用户信息
     *
     * @return
     */
    @RequestMapping(value = "/listPage/jqgrid", method = RequestMethod.GET)
    public Return jqgrid(PageQuery pageQuery, SysUserVO userVO, @RequestParam int page, @RequestParam int limit) {
        pageQuery.setQuery(userVO);
        SysUser s1 = new SysUser();
        if (StringUtils.isNotEmpty(pageQuery.getSidx())) {
            String sidx = pageQuery.getSidx();
            String json = "{'" + sidx + "':'" + 0 + "'}";
            s1 = JsonUtils.toObject(json, SysUser.class);
        }
        System.out.println(s1);
        pageQuery.setOrd(s1);
        System.out.println(pageQuery);
        PageHelper.startPage(page, limit);
        List<SysUser> sysUserList = sysUserService.listPage(pageQuery);
        PageInfo<SysUser> userPageInfo = new PageInfo<>(sysUserList, 3);
        ;
        HashMap<String, Object> map = new HashMap<>();
        map.put("rows", sysUserList);
        map.put("total", userPageInfo.getTotal());
        map.put("records", 1000);
        map.put("page", page);
        return Return.ok(userPageInfo);
    }

    /**
     * 增加一个用户
     *
     * @param userVO
     * @return
     */
    @RequiresPermissions("user:create")
    @RequestMapping(method = RequestMethod.POST)
    public Return save(@RequestBody SysUserVO userVO) {
        String validate = ValidatorUtils.validate(userVO, SaveGroup.class);
        if (StringUtils.isNotEmpty(validate)) {
            return Return.error(validate);
        }
        if (sysUserService.isExistUserName(userVO.getUserName()) > 0) {
            return Return.error("用户名已经存在");
        }
        int result = sysUserService.save(userVO);
        if (result == 0) {
            return Return.error();
        }
        return Return.ok();
    }

    /**
     * 用户名是否已经存在
     *
     * @param userName
     * @return
     */
    @RequiresPermissions("user:create,update")
    @RequestMapping(value = "/isExistUserName/{userName}", method = RequestMethod.GET)
    public Return isExistUserName(@PathVariable("userName") String userName) {
        if (sysUserService.isExistUserName(userName) > 0) {
            return Return.error("用户名已经存在");
        }
        return Return.ok();
    }

    /**
     * 更新一个用户
     *
     * @param userVO
     * @return
     */
    @RequiresPermissions("user:update")
    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
    public Return update(@RequestBody SysUserVO userVO, @PathVariable("userId") int userId) {
        String validate = ValidatorUtils.validate(userVO, UpdateGroup.class);
        if (StringUtils.isNotEmpty(validate)) {
            return Return.error(validate);
        }
        userVO.setUserId(userId);
        int result = sysUserService.update(userVO);

        if (result > 0) {
            return Return.ok();
        }
        if (result == -1) {
            return Return.error("用户不存在");
        }
        if (result == -2) {
            return Return.error("用户名已经存在");
        }
        if (result == -3) {
            return Return.error("未作任何修改");
        }
        return Return.error();
    }

    /**
     * 删除一个用户
     *
     * @param userId
     * @return
     */
    @RequiresPermissions("user:delete")
    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public Return delete(@PathVariable("userId") int userId) {
        SysUserVO userVO = sysUserService.get(userId);
        if (userVO == null) {
            return Return.error("用户不存在");
        }
        int result = sysUserService.delete(userVO);
        if (result > 0) {
            return Return.ok("删除成功");
        }
        return Return.error("删除失败");
    }
}
