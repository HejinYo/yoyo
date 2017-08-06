package cn.hejinyo.other.service;

import cn.hejinyo.other.model.Jzt_Gps;

import java.util.List;

public interface Jzt_GpsService {
    //查询列表
    List<Jzt_Gps> getJzt_GpsList();

    //增加数据
    int addJzt_Gps(Jzt_Gps jzt_gps);

    //删除数据
    int delJzt_Gps(Jzt_Gps jzt_gps);
}
