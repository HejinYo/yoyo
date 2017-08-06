package cn.hejinyo.other.service.impl;

import cn.hejinyo.other.dao.AccountDao;
import cn.hejinyo.other.model.Account;
import cn.hejinyo.other.service.AccountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Account Service 实现类
 */

@Service(value = "accountsService")
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountDao accountMapper;

    @Override
    public List<Account> getAllAccounts(Account account) {
        System.out.println(System.getProperty("user.dir").replace("bin", "webapps"));
        System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));
        System.out.println(this.getClass().getResource("/"));
        String s = this.getClass().getResource("/").toString();

        System.out.println("------------------------=========");
        System.out.println(s);
        return accountMapper.getAllAccounts(account);
    }

    @Override
    public List<Account> getTest(String account) {
        return accountMapper.getTest(account);
    }
}
