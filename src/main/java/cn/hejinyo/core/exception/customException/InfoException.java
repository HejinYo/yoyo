package cn.hejinyo.core.exception.customException;

import cn.hejinyo.core.consts.StatusCode;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/8/5 18:46
 * @Description : 自定义，返回消息的异常
 */
public class InfoException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private static final int DEFAULT_FAILURE_CODE = StatusCode.FAILURE.getKey();
    private static final String DEFAULT_FAILURE_MESSAGE = StatusCode.FAILURE.getValue();

    private int code = DEFAULT_FAILURE_CODE;
    private String message = DEFAULT_FAILURE_MESSAGE;

    public InfoException(Throwable cause) {
        super(cause);
    }

    public InfoException(int code) {
        super(DEFAULT_FAILURE_MESSAGE);
        this.code = code;
    }

    public InfoException(String message) {
        super(message);
        this.message = message;
    }

    public InfoException(int code, String message) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public InfoException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public InfoException(int code, String message, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
