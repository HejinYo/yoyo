package cn.hejinyo.system.webservice.service;

import cn.hejinyo.core.utils.PropertiesUtils;
import org.apache.wss4j.common.ext.WSPasswordCallback;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import java.io.IOException;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/6/5 21:41
 * @Description : webservice ws-security 回调函数，用于访问安全验证
 */
public class ServerPasswordCallbackHandler implements CallbackHandler {
    private static final String WEBSERVICE_USERNAME = PropertiesUtils.getStringByKey("cxf.username");
    private static final String WEBSERVICE_PASSWORD = PropertiesUtils.getStringByKey("cxf.password");

    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {

        WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
        if (pc.getIdentifier().equals(WEBSERVICE_USERNAME)) {//验证用户名
            //这个密码和客户端发送的密码进行比较
            //如果和客户端不同将抛出org.apache.ws.security.WSSecurityException
            pc.setPassword(WEBSERVICE_PASSWORD);//设置密码
        }
    }

}
