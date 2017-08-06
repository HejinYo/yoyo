package basetest;

import cn.hejinyo.core.consts.Const;
import cn.hejinyo.core.shiro.realm.StatelessLoginRealm;
import cn.hejinyo.core.utils.PojoConvertUtil;
import cn.hejinyo.core.utils.Tools;
import cn.hejinyo.other.model.Sys_Dto;
import cn.hejinyo.system.dao.SysUserDao;
import cn.hejinyo.system.model.dto.CurrentUserDTO;
import cn.hejinyo.system.model.po.SysUser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.sf.cglib.beans.BeanCopier;
import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.util.AntPathMatcher;
import org.apache.shiro.util.PatternMatcher;
import org.junit.Test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.Optional;

public class BaseTest {

    private SysUserDao sysUserMapper;

    @Test
    public void test1() {
        String path = Tools.class.getClass().getResource("/").getPath();
        System.out.println(path);
        System.out.println(StatelessLoginRealm.class.getName());
        System.out.println(Const.ShiroToken.valueOf("APP_LOGIN").getValue());
    }

    @Test
    public void testBaseb4() {
        /*String str = "hello";
        String base64Encoded = Base64.encodeToString(str.getBytes());
        String str2 = Base64.decodeToString("");
        System.out.println(base64Encoded);
        System.out.println(str2);
        System.out.println(base64Encoded);*/
/*
        String str = "hello";
        String salt = "123";
        String md5 = new Md5Hash(str, salt).toString();
        System.out.println(md5);
        SecureRandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
        randomNumberGenerator.setSeed("123".getBytes());
        String hex = randomNumberGenerator.nextBytes().toHex();
        System.out.println(new SecureRandomNumberGenerator().nextBytes().toString());*/
        String str = "admin";
        String salt = "123";
        //md5(salt+str)盐在前面
        ;
        String sjson = "{\"mid\":23,\"sys_menu\":{\"creater\":0,\"mid\":0,\"mlevel\":0,\"mname\":\"菜单\",\"pid\":0,\"sorder\":0,\"state\":88},\"test\":\"测试\",\"xid\":0}";
        Sys_Dto sys_dto = JSON.parseObject(sjson, Sys_Dto.class);

        Date date = new Date();
        String dates = JSON.toJSONStringWithDateFormat(date, "yyyy-MM-dd HH:mm:ss.SSS");
        JSON.toJSONString(date, SerializerFeature.WriteClassName);
        System.out.println(dates);
    }

    @Test
    public void TestpathsMatch() {
        PatternMatcher pathMatcher = new AntPathMatcher();
        System.out.println(pathMatcher.matches("/test/*", "/test/excptionView"));
        System.out.println(pathMatcher.matches("/test/*", "/"));
    }

    @Test
    public void TestFaseJson() {
        String str = "{\"username\":\"asdfasd\",\"loginpw\":\"WVhOa1ptRnpaQT09NWJjYWY3ZmFmOWY4ZWI3NzQwOWJkZjEzZjlmY2M1ZDg=\",\"verifi\":\"aaaa\"}";
        JSONObject json = JSON.parseObject(str);
        String ss = json.getString("usernames");
        System.out.println(ss);
        boolean s = json.getBoolean("userndames");
        System.out.println(s);
    }

    @Test
    public void testSet() {
        Integer value1 = null;
        Integer value2 = new Integer(10);

        // Optional.ofNullable - 允许传递为 null 参数
        Optional<Integer> a = Optional.ofNullable(value2);
        System.out.println(a.orElse(123));
        System.out.println(a.isPresent());
    }

    @Test
    public void testCglib() {
        long startTime = System.currentTimeMillis(); //执行方法
        for (int i = 0; i < 10000; i++) {
            SysUser sysUser = new SysUser();
            sysUser.setUserName("test");
            sysUser.setLoginTime(new Date());
            final BeanCopier copier = BeanCopier.create(SysUser.class, CurrentUserDTO.class, false);
            CurrentUserDTO dto = new CurrentUserDTO();
            copier.copy(sysUser, dto, null);
            System.out.println(dto.getLoginTime());
            // Assert.assertEquals("test", dto.getUserName());
        }

        long endTime = System.currentTimeMillis();
        float excTime = (float) (endTime - startTime);
        System.out.println("执行时间：" + excTime + "ms\"");
    }

    @Test
    public void testconvert() {
        long startTime = System.currentTimeMillis(); //执行方法
        for (int i = 0; i < 1000000; i++) {
            SysUser sysUser = new SysUser();
            sysUser.setUserName("test");
            sysUser.setLoginTime(new Date());
            CurrentUserDTO dto = PojoConvertUtil.convert(sysUser, CurrentUserDTO.class);
            System.out.println(dto.getLoginTime());
        }

        long endTime = System.currentTimeMillis();
        float excTime = (float) (endTime - startTime);
        System.out.println("执行时间：" + excTime + "ms\"");

    }

    @Test
    public void testconvert2() {
        long startTime = System.currentTimeMillis(); //执行方法
        for (int i = 0; i < 10000; i++) {
            SysUser sysUser = new SysUser();
            sysUser.setUserName("test");
            sysUser.setLoginTime(new Date());
            CurrentUserDTO dto = new CurrentUserDTO();
            dto.setUserName(sysUser.getUserName());
            dto.setLoginTime(sysUser.getLoginTime());
            System.out.println(dto.getLoginTime());
        }

        long endTime = System.currentTimeMillis();
        float excTime = (float) (endTime - startTime);
        System.out.println("执行时间：" + excTime + "ms\"");

    }

    @Test
    public void test10() {
        try {
            String secret = "secret";
            String message = "Message";

            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);

            String hash = Base64.encodeBase64String(sha256_HMAC.doFinal(message.getBytes()));
            System.out.println(hash);
        } catch (Exception e) {
            System.out.println("Error");
        }
    }


}
