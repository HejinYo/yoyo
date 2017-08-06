package cn.hejinyo.other.controller;

import cn.hejinyo.core.annotation.SysLog;
import cn.hejinyo.core.utils.Return;
import cn.hejinyo.core.utils.ShiroUtils;
import cn.hejinyo.system.model.dto.UserMenuDTO;
import cn.hejinyo.system.service.SysResourceService;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cn.hejinyo.core.utils.PropertiesUtils;
import cn.hejinyo.core.shiro.realm.UserRealm;
import cn.hejinyo.system.model.po.SysUser;
import cn.hejinyo.other.model.Account;
import cn.hejinyo.other.model.Sys_Dto;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Account Controller
 */

@Controller
@RequestMapping(value = "/test")
public class TestController {

  /*  @Resource
    private UserRealm userRealm;*/

    @Resource
    private CacheManager cacheManager;

    @Resource
    private Account account;
    @Resource
    private SysResourceService sysResourceService;

    /**
     * 获得用户菜单
     *
     * @return
     */
    @RequestMapping(value = "/userMenu", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map<String, Object> mutilMenu() {
        List<UserMenuDTO> userMenuDTOS = sysResourceService.getUserMenuList(1);
        return Return.ok("获取成功", userMenuDTOS);
    }


    @RequestMapping(value = "/test")
    public String test() {
        return "/test";
    }


    @RequestMapping(value = "/getjson")
    @ResponseBody
    public Object getjson() {
        JSONObject data = new JSONObject();
        JSONObject data1 = new JSONObject();
        JSONArray json = new JSONArray();
        // String tmp[] = {"{'text':'已使用','value':30}","{'text':'未使用','value':70}"};
        // JSONArray jsonArray = JSONArray.fromObject(tmp);
        JSONObject data2 = new JSONObject();
        JSONObject data3 = new JSONObject();
        //data3.put("json",jsonArray);

        JSONObject type1 = new JSONObject();
        JSONObject type2 = new JSONObject();
        JSONObject jsondata = new JSONObject();
        type1.put("used", "30");
        type1.put("unused", "30");
        type2.put("used", "40");
        type2.put("unused", "60");

        data2.put("hejinyo", "hejinyo1");
        data2.put("data", type1);
        data3.put("hejinyo", "hejinyo2");
        data3.put("data", type2);

        jsondata.put("1", data2);
        jsondata.put("2", data3);
        System.out.print(jsondata.getJSONObject("1").getString("hejinyo"));
        data.put("text", "已使用");
        data.put("value", 30);
        data1.put("text", "未使用");
        data1.put("value", 70);

        json.add(data);
        json.add(data1);

        return jsondata;


    }

    @RequestMapping(value = "/getdate")
    @ResponseBody
    public Object getdate() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(new Date());

        List<String> listData = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            rightNow.add(Calendar.DAY_OF_YEAR, -1);//日期加10天
            String date = sdf.format(rightNow.getTime());
            String tmp = "{test:'" + date + "',value:'" + i + "'}";
            listData.add(tmp);
        }
        JSONObject date = new JSONObject();
        date.put("ticket", listData);
        // JSONArray array = JSONArray.fromObject(json);
        return date;
    }

    @RequestMapping("/testJson")
    @ResponseBody
    public Map<String, Object> testJson() {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Account account = new Account();
        account.setCustid("s0000001");
        account.setAccount("banace");
        List list = new ArrayList();
        list.add("wo");
        list.add("shi");
        list.add(123);
        jsonMap.put("name", "hss");
        jsonMap.put("number", 20122281);
        jsonMap.put("list", list);
        jsonMap.put("account", account);
        /*jsonRetrun.setData(jsonMap);
        jsonRetrun.setStatus(1);
        jsonRetrun.setMessage("登录成功");
        return jsonRetrun.result();*/
        return null;
    }

    @RequestMapping(value = "/testJson2", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object testJson2() {
        Map<String, String> map1 = new HashMap<String, String>();
        Map<String, String> map2 = new HashMap<String, String>();
        Map<String, String> map3 = new HashMap<String, String>();
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        map1.put("name", "小明");
        map1.put("age", "23");
        map1.put("sex", "男");
        list.add(map1);

        map2.put("name", "小王");
        map2.put("age", "24");
        map2.put("sex", "女");
        list.add(map2);

        map3.put("name", "小张");
        map3.put("age", "22");
        map3.put("sex", "男");
        list.add(map3);
        String jsonStr = JSONArray.fromObject(list).toString();
        System.out.println(jsonStr);
        JSONArray ja = JSONArray.fromObject(jsonStr);

        System.out.println(ja.toString());
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        int msg = 1;
        String status = "SUCCESS";

        jsonMap.put("msg", msg);
        jsonMap.put("status", status);
        jsonMap.put("data", map3);
        String s = jsonMap.toString();
        System.out.println(s);
        return Return.ok(jsonMap);
    }

    @RequestMapping(value = "/testJson3")
    @ResponseBody
    public String gson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        int msg = 1;
        String status = "SUCCESS";

        jsonMap.put("msg", msg);
        jsonMap.put("status", status);
        jsonMap.put("data", 123);
        System.out.println(gson.toJson(jsonMap));
        return gson.toJson(jsonMap);
    }

    @RequestMapping(value = "/testExcrption")
    @ResponseBody
    public String testExcrption(HttpServletRequest request) {
        String s = "";
        Enumeration enu = request.getHeaderNames();//取得全部头信息
        while (enu.hasMoreElements()) {//以此取出头信息
            String headerName = (String) enu.nextElement();
            String headerValue = request.getHeader(headerName);//取出头信息内容
            s += headerName + ":" + headerValue + "<br>";
            //System.out.println(headerName + ":" + headerValue);
        }
        String s1 = null;
        //s1.length();
        int x = 1 / 0;
        //throw new RuntimeException("xxx");
        return s;
    }

    @RequestMapping(value = "/excptionView")
    public String excptionView() {
        return "test/exception";
    }

    @RequestMapping(value = "/html1")
    public String html1() {
        return "test/Modal";
    }

    @RequestMapping(value = "/html2")
    @ResponseBody
    public Object html() {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        int msg = 1;
        String status = "SUCCESS";

        jsonMap.put("msg", msg);
        jsonMap.put("status", status);
        jsonMap.put("data", 123);
        return jsonMap;
    }

    @RequestMapping(value = "/html3")
    @ResponseBody
    public Object html3() {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        int msg = 1;
        String status = "SUCCESS1";

        jsonMap.put("msg", msg);
        jsonMap.put("status", status);
        jsonMap.put("data", 123);
        return jsonMap;
    }


    @RequestMapping(value = "/testresponse")
    public void testresponse(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setHeader("Cache-Control", "no-cache");
        response.setStatus(HttpStatus.OK.value());
        try {
            response.getWriter().print("test");
            response.getWriter().print("test2");
            response.getWriter().print("test3");
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "/needquery", produces = "application/json")
    @ResponseBody
    public Map<String, Object> needquery() {
        return Return.ok(ShiroUtils.getCurrentUser());
    }

    @RequestMapping(value = "/needview", produces = "application/json")
    @ResponseBody
    @RequiresPermissions("user:view")
    public Map<String, Object> needview() {
        return Return.ok("需要view权限访问！");
    }

    @RequestMapping(value = "/needcreate", produces = "application/json")
    @ResponseBody
    @RequiresPermissions("user:create12")
    public Map<String, Object> needcreate() {
        return Return.ok("需要create权限访问！");
    }

    //解除登录错误锁定
    @RequestMapping(value = "/clearLock", produces = "application/json")
    @ResponseBody
    public Map<String, Object> clearLook(@RequestParam String username) {
       /* Ehcache passwordRetryCache;
        CacheManager cacheManager = CacheManager.newInstance(CacheManager.class.getClassLoader().getResource("ehcache/shiro-ehcache.xml"));
        passwordRetryCache = cacheManager.getCache("loginRecordCache");
        Element element = passwordRetryCache.get(username);
        int count = 0;
        if (element == null) {
            element = new Element(username, new AtomicInteger(0));
            passwordRetryCache.put(element);
        }
        AtomicInteger retryCount = (AtomicInteger) element.getObjectValue();
        count = retryCount.get();
        if (count > 5) {
            passwordRetryCache.remove(username);
            return Return.ok(username + "清除缓存成功！");
        }*/
        return Return.ok(username + "当前错误次数！");
    }

    /**
     * 清空缓存
     *
     * @return
     */
    @RequestMapping(value = "/clearCached", produces = "application/json")
    @ResponseBody
    public Map<String, Object> clearCached() {
    /*    userRealm.clearAllCache();*/
        return Return.ok("清除缓存成功！");
    }


    @RequestMapping("/testDto")
    @ResponseBody
    public Sys_Dto testDto(Sys_Dto sys_dto) {
        //System.out.println(sys_dto.getSys_menu().getMname());
        System.out.println(sys_dto.getTest());
        return sys_dto;
    }

    @RequestMapping("/testDto3")
    @ResponseBody
    @SysLog("验证码测试")
    public SysUser testDto3(SysUser sys_dto) {
        //System.out.println(sys_dto.getSys_menu().getMname());
        return sys_dto;
    }

    @RequestMapping("/testDto2")
    @ResponseBody
    public SysUser testDto2(SysUser sys_user) {
        //System.out.println(sys_dto.getSys_menu().getMname());
        System.out.println(sys_user.toString());
        return sys_user;
    }

    @RequestMapping("/testDto1")
    @ResponseBody
    public Sys_Dto testDto1(Sys_Dto sys_user) {
        System.out.println("==========" + JSON.toJSONString(sys_user));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        System.out.println("===========" + gson.toJson(sys_user));
        return sys_user;
        //return Retrun.ok(gson.toJson(sys_user));
    }

    @RequestMapping("/testDto4")
    @ResponseBody
    public Sys_Dto testDto4(@RequestBody Sys_Dto sys_dto) {
        System.out.println("==========" + JSON.toJSONString(sys_dto));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        System.out.println("-----------" + gson.toJson(sys_dto));
        return sys_dto;
        //return Retrun.ok(gson.toJson(sys_user));
    }

    @RequestMapping("/testPath")
    @ResponseBody
    public String testPath() {
        return PropertiesUtils.getStringByKey("cxf.username");
    }

    @Cacheable(value = "cacheTest", key = "#param")
    public String getTimestamp(String param) {
        Long timestamp = System.currentTimeMillis();
        return timestamp.toString();
    }

    @RequestMapping("/testchache")
    @ResponseBody
    public String getTimestampTest() throws InterruptedException {

        /*System.out.println("第一次调用：" + getTimestamp("param"));
        Thread.sleep(4000);
        System.out.println("2秒之后调用：" + getTimestamp("param"));
        Thread.sleep(11000);
        System.out.println("再过11秒之后调用：" + getTimestamp("param"));*/
        Cache userCache = cacheManager.getCache("testCache");
        Element element = new Element("time", System.currentTimeMillis());
        userCache.put("time", System.currentTimeMillis());
        System.out.println("第一次调用：" + userCache.get("time"));
        System.out.println("第一次调用：" + userCache.get("time"));
        System.out.println("第一次调用：" + userCache.get("time"));
        System.out.println("第一次调用：" + userCache.get("time"));
        Thread.sleep(4000);
        System.out.println("4秒之后调用：" + userCache.get("time"));

        Thread.sleep(11000);
        System.out.println("15秒之后调用：" + userCache.get("time"));
        return "123";
    }

}
