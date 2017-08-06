package cn.hejinyo.system.webservice.client;

import cn.hejinyo.core.exception.handler.ExceptionAdvice;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/6/3 21:58
 * @Description :
 */
public class WebServiceClient {
    private static Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class.getName());
    private static final String WEBSERVICE_URL = "http://localhost/webservice/HelloWorld?wsdl";
    private static final String WEBSERVICE_USERNAME = "webservice";
    private static final String WEBSERVICE_PASSWORD = "123456";

    public static String sendClient(String method, Object[] result) {
        JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
        Client client = factory.createClient(WEBSERVICE_URL);
        //WS-Security输出拦截器
        Map<String, Object> outProps = new HashMap<String, Object>();
        outProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
        outProps.put(WSHandlerConstants.USER, WEBSERVICE_USERNAME);//添加用户名
        outProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);
        outProps.put(WSHandlerConstants.PW_CALLBACK_CLASS, ClientPasswordCallbackHandler.class.getName());

        String jsonResult = null;
        try {
            client.getOutInterceptors().add(new WSS4JOutInterceptor(outProps));
            // Object[] resultObj = client.invoke("sum", new Object[]{1, 2});
            Object[] resultObj = client.invoke(method, result);
            if (resultObj.length > 0) {
                logger.debug("WEBSERVICE : " + method + " , RESULT : " + resultObj[0] + "");
                jsonResult = resultObj[0].toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonResult;
    }

    public static void main(String[] args){
        sendClient("sum",new Object[]{1,2});
    }
}
