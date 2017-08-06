package cn.hejinyo.core.consts;

import cn.hejinyo.core.shiro.realm.StatelessAuthcRealm;
import cn.hejinyo.core.shiro.realm.StatelessLoginRealm;

/**
 * 系统常量，用户常量
 */
public class Const {
    public enum Status {
        NORMAL(0, "正常"),
        DISABLE(1, "禁用");

        private int key;
        private String value;

        private Status(int key, String value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    public enum HttpStatus {
        BAD_REQUEST(400, "请求参数错误"),
        UNAUTHORIZED(401, "无访问权限"),
        NOT_FOUND(404, "无此资源"),
        METHOD_NOT_ALLOWED(405, "请求方法被禁止"),
        UNSUPPORTED_MEDIA_TYPE(415, "不支持的媒体格式"),
        INTERNAL_SERVER_ERROR(500, "服务器发生异常");

        private final int value;
        private final String reasonPhrase;

        private HttpStatus(int value, String reasonPhrase) {
            this.value = value;
            this.reasonPhrase = reasonPhrase;
        }

        public int getValue() {
            return this.value;
        }

        public String getReasonPhrase() {
            return this.reasonPhrase;
        }

    }

    /**
     * user token
     */
    public enum UserToken {
        ISSUER("iss"),//签发者
        USERNAME("aud"),//用户名,username
        USERID("uid"),//用户id,userid
        TYPE("sub"),//token类型，用于选择realm进行校验
        IPADDRESS("ipa"),//登录ip
        CREATETIME("iat");//时间戳
        private final String value;

        private UserToken(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    /**
     * shiro token
     */
    public enum ShiroToken {
        WEB_LOGIN("WEB_LOGIN", StatelessLoginRealm.class.getName()),
        APP_LOGIN("APP_LOGIN", StatelessLoginRealm.class.getName()),
        WEB_TOKEN("WEB_TOKEN", StatelessAuthcRealm.class.getName()),
        APP_TOKEN("APP_TOKEN", StatelessAuthcRealm.class.getName());

        private final String key;
        private final String value;

        private ShiroToken(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return getKey();
        }

    }

}

