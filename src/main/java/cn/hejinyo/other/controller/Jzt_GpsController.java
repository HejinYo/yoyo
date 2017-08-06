package cn.hejinyo.other.controller;

import cn.hejinyo.other.model.Jzt_Gps;
import cn.hejinyo.other.service.Jzt_GpsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class Jzt_GpsController {
    @Resource(name = "jzt_gpsService")
    private Jzt_GpsService jzt_gpsService;

    @RequestMapping(value = "/jzt/index", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> index() {
        List<Jzt_Gps> jzt_gpss = jzt_gpsService.getJzt_GpsList();
       return null;
    }

    //url参数
    @RequestMapping(value = "/jzt/url_add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> url_add(@RequestParam("devid") String devid,
                                       @RequestParam("longitude") double longitude,
                                       @RequestParam("latitude") double latitude) {
        Jzt_Gps jzt_gps = new Jzt_Gps();
        jzt_gps.setDevid(devid);
        jzt_gps.setLongitude(longitude);
        jzt_gps.setLatitude(latitude);
        if (!"".equals(jzt_gps.getDevid()) && null != jzt_gps.getDevid()) {
            int count = jzt_gpsService.addJzt_Gps(jzt_gps);
            if (count > 0) {
               /* jsonRetrun.setStatus(1);
                jsonRetrun.setMessage("增加成功");*/
            }
        } else {
            /*jsonRetrun.setMessage("devid不能为空");*/
        }
        /*return jsonRetrun.result();*/
        return null;
    }

    //路径参数
    @RequestMapping(value = "/jzt/path_add/{devid}/{longitude}/{latitude}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> path_add(@PathVariable("devid") String devid, @PathVariable("longitude") double longitude, @PathVariable("latitude") double latitude) {
        Jzt_Gps jzt_gps = new Jzt_Gps();
        jzt_gps.setDevid(devid);
        jzt_gps.setLongitude(longitude);
        jzt_gps.setLatitude(latitude);
        int count = jzt_gpsService.addJzt_Gps(jzt_gps);
        if (count > 0) {
           /* jsonRetrun.setStatus(1);
            jsonRetrun.setMessage("增加成功");*/
        } else {
            /*jsonRetrun.setMessage("删除失败");*/
        }
        /*return jsonRetrun.result();*/
        return null;
    }

    //restful_add
    @RequestMapping(value = "/jzt/restful", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> restful_add(@RequestBody Jzt_Gps jzt_gpss) {
        if (!"".equals(jzt_gpss.getDevid())) {
            int count = jzt_gpsService.addJzt_Gps(jzt_gpss);
            if (count > 0) {
                /*jsonRetrun.setStatus(1);
                jsonRetrun.setMessage("增加成功");*/
            }
        } else {
            /*jsonRetrun.setMessage("devid不能为空");*/
        }
       /* return jsonRetrun.result();*/

        return null;
    }

}
