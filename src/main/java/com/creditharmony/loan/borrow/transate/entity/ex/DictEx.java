package com.creditharmony.loan.borrow.transate.entity.ex;
import com.creditharmony.core.persistence.DataEntity;
/**
 * 封装字典表状态
 * @Class Name DictEx
 * @author lirui
 * @Create In 2015年12月21日
 */
@SuppressWarnings("serial")
public class DictEx extends DataEntity<DictEx> {
	// 数据值
	private String value;
	// 标签名
	private String label;
	// 描述
	private String description;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
