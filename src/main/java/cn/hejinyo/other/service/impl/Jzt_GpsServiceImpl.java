package cn.hejinyo.other.service.impl;

import cn.hejinyo.other.service.Jzt_GpsService;
import cn.hejinyo.other.dao.Jzt_GpsDao;
import cn.hejinyo.other.model.Jzt_Gps;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("jzt_gpsService")
public class Jzt_GpsServiceImpl implements Jzt_GpsService {
    @Resource(name = "jzt_GpsMapper")
    private Jzt_GpsDao jzt_gpsMapper;

    @Override
    public List<Jzt_Gps> getJzt_GpsList() {
        return jzt_gpsMapper.getJzt_GpsList();
    }

    @Override
    public int addJzt_Gps(Jzt_Gps jzt_gps) {
        return jzt_gpsMapper.addJzt_Gps(jzt_gps);
    }

    @Override
    public int delJzt_Gps(Jzt_Gps jzt_gps) {
        return jzt_gpsMapper.delJzt_Gps(jzt_gps);
    }
}
