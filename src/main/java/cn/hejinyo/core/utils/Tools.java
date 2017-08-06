package cn.hejinyo.core.utils;

import cn.hejinyo.core.consts.Const;
import cn.hejinyo.system.model.dto.CurrentUserDTO;
import com.alibaba.fastjson.JSONObject;
import jodd.util.Base64;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 字符串，字符，日期 工具类
 *
 * @author HejinYo
 * @version 1.0
 * @email hejinyo@gmail.com
 * @since 1.0
 */
public class Tools {
    /**
     * BASE64编码
     *
     * @param str String
     */
    public static String base64Encode(String str) {
        return Base64.encodeToString(str);
    }

    /**
     * BASE64编码
     *
     * @param arr byte[]
     */
    public static String base64Encode(byte[] arr) {
        return Base64.encodeToString(arr);
    }

    /**
     * BASE64解码
     */
    public static String base64Decoder(String str) {
        return Base64.decodeToString(str);
    }

    /**
     * 数据库密码加密
     *
     * @param password
     * @return
     * @throws IOException
     */
    public static String[] encryptDBPassword(String password) {
        String path = "D:/java/jdk/";
        String druid = "druid-1.0.16.jar com.alibaba.druid.filter.config.ConfigTools ";
        String fileInfo = "java -cp " + path + druid + password + " ;exit;";
        String pw[] = new String[3];
        try {
            Process proc = Runtime.getRuntime().exec(fileInfo);
            InputStreamReader is = new InputStreamReader(proc.getInputStream());
            BufferedReader in = new BufferedReader(is);
            pw[1] = in.readLine();//privateKey
            pw[2] = in.readLine();//publicKey
            pw[0] = in.readLine();//encryptPassword
            in.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pw;
    }

    /**
     * 生成用户密码
     *
     * @return
     */
    public static String userPassword(String userPwd, String salt) {
        String algorithmName = "md5";
        int hashIterations = 2;
        SimpleHash hash = new SimpleHash(algorithmName, userPwd, salt, hashIterations);
        return hash.toHex();
    }

    /**
     * HmacSHA256 加密，返回 base64编码
     *
     * @param key     密钥
     * @param content 内容
     * @return
     */
    public static String hmacSHA256Digest(String key, String content) {
        try {
            //创建加密对象
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            //处理密钥Key
            SecretKey secret_key = new SecretKeySpec(key.getBytes("utf-8"), "HmacSHA256");
            //初始化加密
            sha256_HMAC.init(secret_key);
            //加密内容
            byte[] doFinal = sha256_HMAC.doFinal(content.getBytes("utf-8"));
            //16进制内容
            // byte[] hexB = new Hex().encode(doFinal);
            //base64编码内容
            return base64Encode(doFinal);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * byte 转 字符串
     *
     * @param b
     * @return
     */
    public static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString();
    }

    /**
     * 创建token
     *
     * @param userDTO
     * @return
     */
    public static String createToken(CurrentUserDTO userDTO, String tokenType) {
        JSONObject object = new JSONObject();
        //token签发者
        object.fluentPut(Const.UserToken.ISSUER.getValue(), "yoyo");
        //设置当前登录ip
        object.fluentPut(Const.UserToken.IPADDRESS.getValue(), userDTO.getLoginIp());
        //生成token类型，用户验证时选择对应的realm
        object.fluentPut(Const.UserToken.TYPE.getValue(), tokenType);
        //时间戳
        object.fluentPut(Const.UserToken.CREATETIME.getValue(), userDTO.getLoginTime());
        //用户id
        object.fluentPut(Const.UserToken.USERID.getValue(), userDTO.getUserId());
        //接受签证的用户
        object.fluentPut(Const.UserToken.USERNAME.getValue(), userDTO.getUserName());
        String payload = base64Encode(object.toJSONString());
        String signature = hmacSHA256Digest(userDTO.getUserPwd(), payload);
        return payload + "." + signature;
    }

    /**************************** 测试 *********************************/
    public static void main(String agrs[]) {
        //String password = generatePassword("admin","123456");
        //System.out.println(base64Decoder("4AvVhmFLUs0KTA3Kprsdag=="));
        //System.out.println(getPropsValue("properties/application.properties", "cxf.username"));
        //System.out.println(WebServiceClient.sendClient("sum", new Object[]{1, 2}));
        String header = "{\"typ\":\"JWT\",\"alg\":\"HS256\"}";
        String payload = "{\"iss\":\"skye\",\"sub\":\"web\",\"iat\":\"1482656248798\",\"username\":\"admin\"}";
        //String payload = "{\"cid\":\"OA0001\",\"iat\":1482657284221}";
        String headerBase64 = base64Encode(header);
        String payloadBase64 = base64Encode(payload);
        String base64Token = headerBase64 + "." + payloadBase64;
        String signature = hmacSHA256Digest("123456", base64Token);
        // System.out.println(signature);
        //String signatureBase = base64Encode(signature);


        String JWT = base64Token + "." + signature;

        System.out.println(JWT);

        String salt = new SecureRandomNumberGenerator().nextBytes().toHex();
        System.out.println(salt);
        System.out.println(Tools.userPassword("123456", salt));
        CurrentUserDTO userDTO = new CurrentUserDTO();
        userDTO.setUserId(1);
        userDTO.setUserName("admin");
        userDTO.setUserPwd("123456");
        System.out.println(createToken(userDTO, Const.ShiroToken.WEB_TOKEN.getKey()));

        // System.out.println(userPassword("123456","salt"));
    }
}
