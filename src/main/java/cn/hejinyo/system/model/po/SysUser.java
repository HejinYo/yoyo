package cn.hejinyo.system.model.po;

import cn.hejinyo.system.model.dto.UserMenuDTO;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.beans.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/4/9 14:48
 * @Description : 用户实体类
 */
@Data
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer userId;//用户编号
    private String userName;//用户名称
    private String userPwd;//密码
    private String userSalt;//盐
    private String email;//邮箱
    private String phone;//手机号
    private String loginIp;//最后登录IP
    private Date loginTime;//最后登录时间
    private Integer state;//用户状态 0：正常；1：锁定；-1：禁用(删除)
    private Date createTime; //创建时间
    private Integer createId;//创建人员ID
}
