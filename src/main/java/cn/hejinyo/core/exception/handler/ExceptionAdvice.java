package cn.hejinyo.core.exception.handler;

import cn.hejinyo.core.consts.Const;
import cn.hejinyo.core.exception.customException.InfoException;
import cn.hejinyo.core.utils.Return;
import cn.hejinyo.core.utils.WebUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

@RestControllerAdvice
public class ExceptionAdvice {// ResponseEntityExceptionHandler
    private static Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class.getName());

    /**
     * infoException
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(InfoException.class)
    public void infoExceptionException(InfoException ex, HttpServletResponse response) {
        exceptionReturn(ex, response, Return.error(ex.getCode(), ex.getMessage()));
    }

    /**
     * 401 UNAUTHORIZED
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({UnauthorizedException.class})
    public void shiroException(UnauthorizedException ex, HttpServletResponse response) {
        Return re = Return.error(Const.HttpStatus.UNAUTHORIZED.getValue(), Const.HttpStatus.UNAUTHORIZED.getReasonPhrase());
        exceptionReturn(ex, response, re);
    }

    /**
     * 500 Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {Exception.class})
    public void exception(Exception ex, HttpServletResponse response) {
        Const.HttpStatus con = Const.HttpStatus.INTERNAL_SERVER_ERROR;
        if (ex instanceof HttpMessageNotReadableException) {
            con = Const.HttpStatus.BAD_REQUEST;
        } else if (ex instanceof HttpRequestMethodNotSupportedException) {
            con = Const.HttpStatus.METHOD_NOT_ALLOWED;
        } else if (ex instanceof HttpMediaTypeNotSupportedException) {
            con = Const.HttpStatus.UNSUPPORTED_MEDIA_TYPE;
        }
        int httpStatus = con.getValue();
        Return re = Return.error(con.getValue(), con.getReasonPhrase());
        exceptionReturn(ex, response, httpStatus, re);
    }

    /**
     * 返回json格式的异常信息
     *
     * @param ex
     * @param returns
     * @return HttpStatus.UNAUTHORIZED.value()
     */
    private void exceptionReturn(Exception ex, HttpServletResponse response, int httpStatus, Return returns) {
        logger.debug(ex.toString(), ex);//日志记录，ModelAndView 不用设置。
        Return.response(response, httpStatus, returns);
    }

    private void exceptionReturn(Exception ex, HttpServletResponse response, Return returns) {
        logger.debug(ex.toString(), ex);//日志记录，ModelAndView 不用设置。
        Return.response(response, returns);
    }

    /**
     * 根据不同的请求特点 ，返回视图 或者 json 错误提示
     *
     * @param ex
     * @param request
     * @param message
     * @return
     */
    private Object exceptionReturn(Exception ex, HttpServletRequest request, String message) {
        if (WebUtils.isAjax(request)) {
            logger.debug(ex.toString(), ex);//日志记录，ModelAndView 不用设置。
            return Return.error(message);
        } else {
            String page = "common/500";
            ModelAndView mv = new ModelAndView(page);
            mv.addObject("status", 1);
            mv.addObject("message", message);
            return mv;
        }
    }


    /**
     * 获取异常的详细情况String(包括异常类和位置等debug信息，可以存放到数据库)
     *
     * @param ex
     * @return
     */
    private String exceptionInfo(Exception ex) {
        PrintWriter pw = null;
        StringWriter sw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            pw.flush();
            sw.flush();
        } finally {
            if (sw != null) {
                try {
                    sw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (pw != null) {
                pw.close();
            }
        }
        return sw.toString();
    }
}
