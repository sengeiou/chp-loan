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

import com.creditharmony.loan.common.validate.validator.InDictValidator;
import com.creditharmony.loan.common.validate.validator.IntegerInDictValidator;

/**
 * 字典值校验
 * @author 任志远
 * @date 2017年1月10日
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {IntegerInDictValidator.class, InDictValidator.class})
public @interface InDict {

	/**
	 * 字典类型
	 * By 任志远 2017年1月10日
	 * @return
	 */
	String type();
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
    
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
	@Retention(RUNTIME)
	@Documented
	@interface List {
    	InDict[] value();
	}
}
