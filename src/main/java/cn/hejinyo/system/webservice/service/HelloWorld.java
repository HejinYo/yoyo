package cn.hejinyo.system.webservice.service;

import javax.jws.WebService;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/6/3 20:41
 * @Description :
 */
@WebService
public interface HelloWorld {
    String sayHi(String text);

    String sum(int x, int y);

    String login(String username, String password);
}
