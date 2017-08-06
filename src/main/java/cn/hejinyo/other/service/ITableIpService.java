package cn.hejinyo.other.service;


import cn.hejinyo.core.utils.PageParam;
import cn.hejinyo.other.model.TableIp;

public interface ITableIpService {

	public int getRowCount();
	public PageParam getIpListByPage(PageParam pageParam);
	public String printIp(String number, String country, String isp);

    public int addTable_Ip(TableIp tableIp);
}
