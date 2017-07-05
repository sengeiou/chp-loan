package com.creditharmony.loan.common.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.creditharmony.common.util.IdGen;
import com.creditharmony.core.users.util.UserUtils;
/**
 * 导出功能历史记录
 * @author huowenlong
 *
 */
public class ExportHistory implements Serializable{

	private String id;
	
	private String exportDesc; //导出功能描述
	
	private String exportUsername;//导出人
	
	private Date exportDate;//导出时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getExportDesc() {
		return exportDesc;
	}

	public void setExportDesc(String exportDesc) {
		this.exportDesc = exportDesc;
	}

	public String getExportUsername() {
		return exportUsername;
	}

	public void setExportUsername(String exportUsername) {
		this.exportUsername = exportUsername;
	}

	public Date getExportDate() {
		return exportDate;
	}

	public void setExportDate(Date exportDate) {
		this.exportDate = exportDate;
	}
	
	/**
	 * 更新之前执行方法，需要手动调用
	 */
	public void preUpdate() {
		String userId = UserUtils.getUserId();
		if (StringUtils.isNotBlank(userId)){
			setExportUsername(userId);
		}
		setExportDate(new Date());
	}
	
	/**
	 * 插入之前执行方法，需要手动调用
	 */
	public void preInsert() {
		setId(IdGen.uuid());
		String userId = UserUtils.getUserId();
		if (StringUtils.isNotBlank(userId)){
			setExportUsername(userId);
		}
		setExportDate(new Date());
	}
	
	
}
