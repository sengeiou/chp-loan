package com.creditharmony.loan.car.carGrant.ex;

import java.lang.reflect.Field;

import com.creditharmony.core.excel.annotation.ExcelField;

public class SortableField {

	public SortableField() {
	}

	private ExcelField eF;
	private Field field;
	private String name;
	private Class<?> type;

	public SortableField(ExcelField eF, Field field) {
		super();
		this.eF = eF;
		this.field = field;
		this.name = field.getName();
		this.type = field.getType();
	}

	public SortableField(ExcelField eF, String name, Class<?> type) {
		super();
		this.eF = eF;
		this.name = name;
		this.type = type;
	}

	/**
	 * 
	 * 注解
	 * By 申诗阔
	 * @return
	 */
	public ExcelField geteF() {
		return eF;
	}

	public void seteF(ExcelField eF) {
		this.eF = eF;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	/**
	 * 字段名称
	 * 2016年5月16日
	 * By 申诗阔
	 * @return
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 字段类型
	 * 2016年5月16日
	 * By 申诗阔
	 * @return
	 */
	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

}
