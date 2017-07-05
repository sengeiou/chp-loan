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

import com.creditharmony.loan.common.validate.validator.AddressValidator;

/**
 * 地址校验
 * @author 任志远
 * @date 2017年1月10日
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = AddressValidator.class)
public @interface Address {
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
