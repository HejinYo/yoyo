package cn.hejinyo.other.dao;

import cn.hejinyo.core.base.dao.BaseDao;
import cn.hejinyo.other.model.Account;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountDao extends BaseDao {
    /**
     * 返回Account 所有的记录
     *
     * @return List<Accounts>
     */
    public List<Account> getAllAccounts(Account account);

    public List<Account> getTest(String account);
}
