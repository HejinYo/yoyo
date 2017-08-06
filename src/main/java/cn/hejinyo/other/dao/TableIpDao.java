package cn.hejinyo.other.dao;

import cn.hejinyo.core.base.dao.BaseDao;
import cn.hejinyo.other.model.TableIp;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface TableIpDao extends BaseDao {

	public int getRowCount();

	public List<TableIp> selectByParams(Map<String, Object> params);

	public List<TableIp> fetchByParams(Map<String, Object> params);

	public int addTable_Ip(TableIp tableIp);

}
