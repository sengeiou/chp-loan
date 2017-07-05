package com.creditharmony.loan.app.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.lend.type.LendConstants;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.app.dao.AppDao;
import com.creditharmony.loan.borrow.outvisit.enity.LoanOutsideTaskInfo;
import com.creditharmony.loan.borrow.outvisit.enity.LoanOutsideTaskList;
import com.creditharmony.loan.common.entity.OutsideCheckInfo;

/**
 * APP业务处理service
 * @Class Name AppService
 * @author zhangfeng
 * @Create In 2016年2月1日
 */
@Service("AppService")
@Transactional(readOnly = true,value = "loanTransactionManager")
public class AppService extends CoreManager<AppDao, OutsideCheckInfo> {

    /**
     * 查询外访信息
     * 2016年2月1日
     * By zhangfeng
     * @param mapCustomer
     * @return list
     */
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public List<OutsideCheckInfo> getOutsideTaskList(Map<String, String> map) {
		return dao.getOutsideTaskList(map);
	}

	/**
	 * 更新外访信息
	 * 2016年2月2日
	 * By zhangfeng
	 * @param out
	 * @return none
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void updateOutSideTask(LoanOutsideTaskList out) {
		dao.updateOutSideTask(out);
	}

	/**
	 * 获取共借人主借人信息
	 * 2016年3月11日
	 * By zhangfeng
	 * @param outInfo
	 * @return list
	 */
	public List<LoanOutsideTaskInfo> getOutsideTaskListInfo(LoanOutsideTaskInfo outInfo) {
		return dao.getOutsideTaskListInfo(outInfo);
	}

	/**
	 * 判断是否重复上传
	 * 2016年3月21日
	 * By zhangfeng
	 * @param mapCustomer
	 * @return list
	 */
	public List<LoanOutsideTaskInfo> getOutListForDistance(Map<String, String> mapCustomer) {
		return dao.getOutListForDistance(mapCustomer);
	}

	/**
	 * 获取主借共借人拍摄要求
	 * 2016年3月14日
	 * By zhangfeng
	 * @param checkJson
	 * @return String
	 */
	public String getOutCheck(String checkJson) {
		Dict dict = new Dict();
		Dict sdict = new Dict();
		String msgJson = null;
		String[] checkIds = checkJson.split(",");
		for (int i = 0; i < checkIds.length; i++) {
			dict.setType(LendConstants.OUT_CHK);
			dict.setValue(checkIds[i]);
			sdict = dao.getOutCheck(dict);
			if(!ObjectHelper.isEmpty(sdict)){
				if(StringUtils.isNotEmpty(msgJson)){
					msgJson =msgJson + sdict.getLabel();
				}else{

					msgJson = sdict.getLabel();
				}
			}
		}
		return msgJson;
	}
	
    /**
     * 删除目录（文件夹）以及目录下的文件
     * @param   sPath 被删除目录的文件路径
     * @return  目录删除成功返回true，否则返回false
     */
	/**
	 * 删除文件、文件夹
	 */
	public void deleteFile(String path) {
		File file = new File(path);
		if (file.isDirectory()) {
			File[] ff = file.listFiles();
			if(ff!=null){
				for (int i = 0; i < ff.length; i++) {
					deleteFile(ff[i].getPath());
				}
			}
		}
		file.delete();
	}
}
