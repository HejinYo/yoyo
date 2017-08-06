package cn.hejinyo.system.webservice.client;

import org.apache.wss4j.common.ext.WSPasswordCallback;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import java.io.IOException;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/6/5 21:44
 * @Description : webservice client ws.security 回调函数，用于安全验证访问
 */
public class ClientPasswordCallbackHandler implements CallbackHandler {
    private static final String WEBSERVICE_PASSWORD = "123456";

    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
        pc.setPassword(WEBSERVICE_PASSWORD);//设置密码
    }

}
