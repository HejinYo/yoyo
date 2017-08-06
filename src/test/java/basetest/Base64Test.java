package basetest;

import jodd.util.Base64;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;
import org.springframework.util.Base64Utils;

import java.nio.charset.Charset;

public class Base64Test {
    /**
     * Shiro 记住密码采用的是AES加密，AES key length 需要是16位，该方法生成16位的key
     */
    public static void main(String[] args) {
        String keyStr = "如梦技术";
        byte[] keys = keyStr.getBytes(Charset.forName("utf-8"));
//Arrays.copyOf(keys, 16)
        String encode = Base64Utils.encodeToString(keys);
        System.out.println(encode);


    }

    @Test
    public void testBase64() {
        String keyStr = "hejinyo";
        String encode = Base64.encodeToString(keyStr.getBytes(Charset.forName("utf-8")));
        System.out.println(encode);

        String decode = Base64.decodeToString(encode);
        System.out.println(decode);
        System.out.println(Base64.decodeToString(""));
        System.out.println("xxxx");
    }

    @Test
    public void testMd5(){
        String keyStr = "hejinyo";//AxFhcAUojzpPXvxkfadibQ==
        String md5 = new Md5Hash(keyStr).toBase64();//还可以转换为 toBase64()/toHex()
        System.out.println(md5);
    }

    @Test
    public void testGeneratePassword() {
        String algorithmName = "md5";
        String username = "admin";
        String password = "123456";
        String salt1 = username;
        String salt2 = new SecureRandomNumberGenerator().nextBytes().toHex();//随机数
        int hashIterations = 2;

        SimpleHash hash = new SimpleHash(algorithmName, password, salt1 + salt2, hashIterations);
        String encodedPassword = hash.toHex();
        System.out.println(salt2);
        System.out.println(encodedPassword);
    }
}
