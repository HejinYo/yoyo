package cn.hejinyo.other.service.impl;

import cn.hejinyo.core.utils.PageParam;
import cn.hejinyo.other.dao.TableIpDao;
import cn.hejinyo.other.model.TableIp;
import cn.hejinyo.other.service.ITableIpService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("tableIpServiceImpl")
public class TableIpServiceImpl implements ITableIpService {

	@Resource
    TableIpDao dao;

	public int getRowCount() {
		return dao.getRowCount();
	}

	@Override
	public PageParam getIpListByPage(PageParam pageParam) {
		// limit offset,size
		int currPage = pageParam.getCurrPage();

		int offset = (currPage - 1) * PageParam.pageSize;
		int size = PageParam.pageSize;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("offset", offset);
		params.put("size", size);

		List<TableIp> ipList = dao.selectByParams(params);
		pageParam.setData(ipList);
		return pageParam;
	}

	@Override
	public String printIp(String number, String country, String isp) {
		int size = 100;
		try {
			size = Integer.parseInt(number);
		} catch (Exception e) {
		}
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("size", size);
		params.put("country", country);
		params.put("isp", isp);
		
		List<TableIp> ipList = dao.fetchByParams(params);
		StringBuilder sBuilder = new StringBuilder();
		for(TableIp tableIp:ipList){
			sBuilder.append(tableIp.getIp()).append(":").append(tableIp.getPort()).append(",").append(tableIp.getCountry()).append(",").append(tableIp.getIsp());
			sBuilder.append("\r\n");
		}
		
		return sBuilder.toString();
	}

	@Override
	public int addTable_Ip (TableIp tableIp){
        return dao.addTable_Ip(tableIp);
    }

}
