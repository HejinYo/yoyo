package cn.hejinyo.other.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/Restful")
public class RestfulController {

    @RequestMapping(value = "/test1/{parameter1}")
    @ResponseBody
    public Map<String,Object> Restful1(@PathVariable String parameter1){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("status",parameter1);
        return map;
    }
}
