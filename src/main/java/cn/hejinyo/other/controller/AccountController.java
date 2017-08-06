package cn.hejinyo.other.controller;

import cn.hejinyo.other.model.Account;
import cn.hejinyo.other.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;

/**
 * Account Controller
 */

@Controller
@RequestMapping("/")
public class AccountController {
    @Resource
    private AccountService accountsService;

    @Resource
    private Account account;

    @ResponseBody
    @RequestMapping(value = "/getAllAccounts", method = RequestMethod.GET)
    public List<Account> getAllAccounts() {
        //account.setCustid("custid");
        List<Account> list = accountsService.getAllAccounts(account);
        List<Account> list1 = accountsService.getTest("custid");
       /* for(Account a :list1){
            System.out.println(a.toString());
        }*/
        Iterator<Account> it = list1.iterator();
        while (it.hasNext()) {
            if ("C003".equals(it.next().getCustid())) {
                it.remove();
                System.out.println("success");
            }
        }
        return list1;
    }
}
