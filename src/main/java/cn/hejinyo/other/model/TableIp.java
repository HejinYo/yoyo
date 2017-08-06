package cn.hejinyo.other.model;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class TableIp {
	private int id;

    @Size(min = 6,message = "{tableip.ip.size}")
	private String ip;
    @Email
    @Size(min = 5,message = "密码长度只能在6-20之间")
	private String port;
	private String country;
	private String province;
	private String city;
    @NotEmpty
	private String isp;
	private Date findTime;
}
