package cn.hejinyo.core.utils;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.spi.resourceloading.ResourceBundleLocator;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.MessageSourceResourceBundleLocator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/6/18 23:07
 * @Description :
 */
public class ValidatorUtils {
    private static Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }


    /**
     * 校验对象
     *
     * @param object 待校验对象
     */
    public static String validate(Object object) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);
        if (constraintViolations.isEmpty()) {
            return "";
        } else {
            ConstraintViolation<Object> constraint = constraintViolations.iterator().next();
            return constraint.getMessage();
        }
    }

    /**
     * 校验对象
     *
     * @param object 待校验对象
     * @param groups 待校验的组
     */
    public static String validate(Object object, Class<?>... groups) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (constraintViolations.isEmpty()) {
            return "";
        } else {
            ConstraintViolation<Object> constraint = constraintViolations.iterator().next();
            return constraint.getMessage();
        }
    }


}
