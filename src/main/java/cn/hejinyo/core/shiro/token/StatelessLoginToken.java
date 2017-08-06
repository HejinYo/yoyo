package cn.hejinyo.core.shiro.token;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/7/29 18:07
 * @Description :
 */
public class StatelessLoginToken extends BaseToken {

    private String username;
    private char[] password;
    private String host;

    public StatelessLoginToken(final String tokenType, final String username, final char[] password, final String host) {
        super.tokenType = tokenType;
        this.username = username;
        this.password = password;
        this.host = host;
    }

    public StatelessLoginToken(final String tokenType, final String username, final String password, final String host) {
        this(tokenType, username, password != null ? password.toCharArray() : null, host);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public Object getPrincipal() {
        return getUsername();
    }

    public Object getCredentials() {
        return getPassword();
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName());
        sb.append(" - ");
        sb.append(super.tokenType);
        sb.append(username);
        if (host != null) {
            sb.append(" (").append(host).append(")");
        }
        return sb.toString();
    }

}
