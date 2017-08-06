package cn.hejinyo.other.model;

import org.springframework.stereotype.Component;

/**
 * 账户实体类
 */
@Component
public class Account {
    private String custid;//客户编号
    private String n_custid;
    private String account;//账户
    private String n_account;
    private double balance;//账户余额
    private double owefee;//欠费金额
    private double addfee;//累计金额
    private String updateStr;//拼装update字符串
    private String wherInfo;//存储sql查询语句

    @Override
    public String toString() {
        return "账户[" +
                "客户编号='" + custid + '\'' +
                ", 账户='" + account + '\'' +
                ", 账户余额=" + balance +
                ", 欠费金额=" + owefee +
                ", 累计金额=" + addfee +
                ']';
    }

    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid;
    }

    public String getN_custid() {
        return n_custid;
    }

    public void setN_custid(String n_custid) {
        this.n_custid = n_custid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getN_account() {
        return n_account;
    }

    public void setN_account(String n_account) {
        this.n_account = n_account;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getOwefee() {
        return owefee;
    }

    public void setOwefee(double owefee) {
        this.owefee = owefee;
    }

    public double getAddfee() {
        return addfee;
    }

    public void setAddfee(double addfee) {
        this.addfee = addfee;
    }

    public String getUpdateStr() {
        return updateStr;
    }

    public void setUpdateStr(String updateStr) {
        this.updateStr = updateStr;
    }

    public String getWherInfo() {
        return wherInfo;
    }

    public void setWherInfo(String wherInfo) {
        this.wherInfo = wherInfo;
    }
}
