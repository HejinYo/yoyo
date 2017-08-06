package cn.hejinyo.other.model;

import cn.hejinyo.system.model.po.SysUser;
import lombok.Data;

import java.io.Serializable;

@Data
public class Sys_Dto implements Serializable {
    private SysUser sys_user;
    private String test;
    private int mid;
    private int xid;
    private String name;
}
