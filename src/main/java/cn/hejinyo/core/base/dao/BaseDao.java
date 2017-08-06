package cn.hejinyo.core.base.dao;

import java.util.List;
import java.util.Map;

/**
 * 继承 BaseDao 才作为映射器加载
 */
public interface BaseDao<T> {

    int save(T t);

    int save(Map<String, Object> parameter);

    int saveBatch(List<T> list);

    int delete(Object id);

    int delete(Map<String, Object> parameter);

    int deleteBatch(Object[] id);

    int update(T t);

    int update(Map<String, Object> parameter);

    T get(Object id);

    List<T> list(Object id);

    List<T> list(Map<String, Object> parameter);

    List<T> listPage(Object id);

    List<T> listPage(Map<String, Object> parameter);

    int count();

    int count(Map<String, Object> parameter);

}
