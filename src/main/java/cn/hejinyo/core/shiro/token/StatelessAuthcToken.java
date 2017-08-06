package cn.hejinyo.core.shiro.token;

import cn.hejinyo.system.model.dto.CurrentUserDTO;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/7/29 18:07
 * @Description :
 */
public class StatelessAuthcToken extends BaseToken {

    private String username;
    private String userToken;
    private String host;
    private CurrentUserDTO userDTO;

    public StatelessAuthcToken(String tokenType, String username, String userToken, String host, CurrentUserDTO userDTO) {
        super.tokenType = tokenType;
        this.username = username;
        this.userToken = userToken;
        this.host = host;
        this.userDTO = userDTO;
    }

    public String getUserName() {
        return username;
    }

    public String getUserToken() {
        return userToken;
    }

    public String getHost() {
        return host;
    }

    public CurrentUserDTO getUserDTO() {
        return userDTO;
    }

    public Object getPrincipal() {
        return getUserName();
    }

    public Object getCredentials() {
        return getUserToken();
    }
}
