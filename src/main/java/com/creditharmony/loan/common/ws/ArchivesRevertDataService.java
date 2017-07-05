package com.creditharmony.loan.common.ws;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.adapter.service.archives.ArchivesRevertDataBaseService;
import com.creditharmony.adapter.service.archives.bean.ArchiveInDetailBean;
import com.creditharmony.adapter.service.archives.bean.ArchivesRevertDataInBean;
import com.creditharmony.adapter.service.archives.bean.ArchivesRevertDataOutBean;
import com.creditharmony.loan.common.dao.ChangeDao;

/**
 * 
 * @Class Name ArchivesRevertDataService
 * @author WJJ
 * @Create In 2016年3月21日
 */
@Service
@Transactional(value = "loanTransactionManager", readOnly = false)
public class ArchivesRevertDataService extends ArchivesRevertDataBaseService{

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ChangeDao changeDao;

	@Override
	public ArchivesRevertDataOutBean doExec(ArchivesRevertDataInBean paramBean) {
		ArchivesRevertDataOutBean adob = new ArchivesRevertDataOutBean();
		adob.setRetMsg("没有数据");
		adob.setRetCode("0000");
		try{
			List<ArchiveInDetailBean> list = paramBean.getRetList();
			for(int i=0;i<list.size();i++){
				ArchiveInDetailBean aidb = list.get(i);
				if(aidb.getFileType()!=null){
					changeDao.updateArchivesStatus(aidb.getLoanNo(), aidb.getFileType());
					logger.info("影像合同文件推送至档案下载成功，借款编号："+aidb.getLoanNo());
				}else{
					changeDao.updateAccountChange(aidb.getDocId(), "2");
					logger.info("变更文件推送至档案下载成功，DOCID："+aidb.getDocId());
				}
			}
			adob.setRetCode("0000");
			adob.setRetMsg("成功");
		}catch(Exception e){
			adob.setRetCode("9999");
			adob.setRetMsg("失败");
		}
		return adob;
	}
	
}
