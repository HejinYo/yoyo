package cn.hejinyo.core.exception.handler;

import cn.hejinyo.core.consts.Const;
import cn.hejinyo.core.utils.Return;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExceptionController {

    @RequestMapping(value = "/404", produces = "application/json")
    public Return notFound() {
        return Return.error(Const.HttpStatus.NOT_FOUND.getReasonPhrase());
    }

    @RequestMapping(value = "/500", produces = "application/json")
    public Return serverError() {
        return Return.error(Const.HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }
}
