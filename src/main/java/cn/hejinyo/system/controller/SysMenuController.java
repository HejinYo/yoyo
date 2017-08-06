package cn.hejinyo.system.controller;

import cn.hejinyo.core.base.controller.BaseController;
import cn.hejinyo.core.utils.Return;
import cn.hejinyo.system.model.dto.UserMenuDTO;
import cn.hejinyo.system.service.SysResourceService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/manage/sysMenu")
public class SysMenuController extends BaseController {

    @Resource
    private SysResourceService sysResourceService;

    /**
     * 获得用户菜单
     *
     * @return
     */
    @RequestMapping(value = "/userMenu", method = RequestMethod.GET, produces = "application/json")
    public Map<String, Object> mutilMenu() {
        return Return.ok("获取成功", sysResourceService.getUserMenuList(getUserId()));
    }


    /**
     * 获取菜单树
     *
     * @return
     */
    @RequestMapping(value = "menuTree", method = RequestMethod.GET, produces = "application/json")
    public Object menuTree() {
        JSONArray jsonArray = new JSONArray();
        //所有菜单
        List<UserMenuDTO> userMenuDTOS = sysResourceService.getUserMenuList(getUserId());
        for (int i = 0; i < userMenuDTOS.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", String.valueOf(userMenuDTOS.get(i).getMid()));
            if (0 == (userMenuDTOS.get(i).getPid())) {
                jsonObject.put("parent", "#");
            } else {
                jsonObject.put("parent", String.valueOf(userMenuDTOS.get(i).getPid()));
            }
            jsonObject.put("text", userMenuDTOS.get(i).getMname());
            int menuLevel = userMenuDTOS.get(i).getMlevel();
            JSONObject state = new JSONObject();
            if (menuLevel == 1) {
                state.put("opened", true);
                jsonObject.put("state", state);
            } else if (menuLevel == 2) {
                jsonObject.put("state", state);
                jsonObject.put("icon", "fa fa-folder text-primary");
            } else if (menuLevel == 3) {
                jsonObject.put("icon", "fa fa-file text-primary");
            }
            jsonArray.add(jsonObject);
        }
       /* String json = "[{\"id\":\"1\",\"parent\":\"#\",\"text\":\"Parent Node\"}," +
                "{\"id\":\"2\",\"parent\":\"1\",\"text\":\"Initially selected\",\"state\": {\"selected\": true}}," +
                "{\"id\":\"3\",\"parent\":\"1\",\"text\":\"Custom Icon\",\"icon\": \"fa fa-warning text-primary\"}," +
                "{\"id\":\"4\",\"parent\":\"1\",\"text\":\"Initially open\",\"icon\": \"fa fa-folder text-primary\", \"state\": {\"opened\": true}}," +
                "{\"id\":\"5\",\"parent\":\"4\",\"text\":\"Another node\",\"icon\": \"fa fa-file text-primary\"}]";*/
        System.out.println(jsonArray.toString());
        return jsonArray;
    }

}
