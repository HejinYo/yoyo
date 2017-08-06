package basetest;


import cn.hejinyo.core.utils.Return;

public class EasyTest {
    public static void main(String args[]) {
        /*Map<String, Object> jsonMap = new HashMap();
        jsonMap.put("status", "success");
        jsonMap.put("msg", "200");
        System.out.println(JsonUtil.toJSONString(jsonMap));
        ;String json = "{\"msg\":\"200\",\"status\":\"success\"}";


        Map<String, Object> jsonMap1 = JsonUtil.json2Map(json);
        System.out.println(jsonMap1.get("msg"));
        //System.out.println(JsonUtil.toJSONString(new Retrun(400,"not found page")));
        System.out.println(new Retrun(400,"not found page",new String[]{"1","2"}).result());*/

        System.out.println(Return.ok());
        System.out.println(Return.ok("成功"));
        System.out.println(Return.error("失败"));
        System.out.println(Return.ok(new String[]{"1","2"}));
        System.out.println(Return.ok("成功",new String[]{"1","2"}));

    }

}
