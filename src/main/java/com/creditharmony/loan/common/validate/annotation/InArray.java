package com.creditharmony.loan.common.validate.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.creditharmony.loan.common.validate.validator.InIntegerArrayValidator;
import com.creditharmony.loan.common.validate.validator.InStringArrayValidator;
/**
 * 校验数据在制定的数组中
 * @author 任志远
 * @date 2017年1月12日
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {InIntegerArrayValidator.class, InStringArrayValidator.class})
public @interface InArray {

	/**
	 * 数组
	 * By 任志远 2017年1月10日
	 * @return
	 */
	String[] array();
	/**
	 * 校验失败后的错误信息
	 * By 任志远 2017年1月10日
	 * @return
	 */
	String message();
	/**
	 * 校验组
	 * By 任志远 2017年1月10日
	 * @return
	 */
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
