/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.reconsider.eventReconsiderApplyInit.java
 * @Create By 张灏
 * @Create In 2015年12月24日 下午2:49:07
 */
package com.creditharmony.loan.borrow.reconsider.event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.bpm.frame.face.InitViewData;
import com.creditharmony.bpm.frame.face.base.BaseService;
import com.creditharmony.bpm.frame.view.BaseBusinessView;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.dao.LoanCoborrowerDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCoborrower;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.reconsider.view.ReconsiderBusinessView;
import com.creditharmony.loan.common.dao.LoanCustomerDao;
import com.creditharmony.loan.common.entity.LoanCustomer;
import com.creditharmony.loan.common.entity.ex.FlowStep;
import com.creditharmony.loan.common.service.ImageService;

/**
 * @Class Name ReconsiderApplyInit
 * @author 张灏
 * @Create In 2015年12月24日
 */
@Service("init_hj_reconsiderApply")
public class ReconsiderApplyInit  extends BaseService implements InitViewData {

    @Autowired
    private LoanCustomerDao loanCustomerDao;
    
    @Autowired
    private ApplyLoanInfoDao loanInfoDao;
    
    @Autowired
    private LoanCoborrowerDao loanCoborrowerDao;
    
    @Autowired
    private ImageService imageService;
    
    @SuppressWarnings("rawtypes")
	@Override
    public BaseBusinessView initViewData(Map parameterMap) {
        ReconsiderBusinessView businessView = new ReconsiderBusinessView();
        String[] applyIds = (String[]) parameterMap.get("applyId");
        String loanCode = null;
        List<LoanCoborrower> coborrowers = null;
        LoanCustomer loanCustomer = null;
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("applyId", applyIds[0]);
        loanCustomer = loanCustomerDao.selectByApplyId(applyIds[0]);
        if(ObjectHelper.isEmpty(loanCustomer)){
           
            loanCustomer = new LoanCustomer();
        }
        LoanInfo loanInfo = loanInfoDao.selectByApplyId(param);
        
        if(!ObjectHelper.isEmpty(loanInfo)){
           loanCode = loanInfo.getLoanCode();
           coborrowers = loanCoborrowerDao.selectByLoanCode(loanCode);
        }else{
           loanInfo = new LoanInfo();
        }
        String imageUrl = imageService.getImageUrl(FlowStep.LANUCH_RE.getName(), loanCode);
        businessView.setImageUrl(imageUrl);
        DictCache dict = DictCache.getInstance();
        loanInfo.setDictLoanUserLabel(dict.getDictLabel("jk_loan_use", loanInfo.getDictLoanUse()));
        businessView.setApplyId(applyIds[0]);
        businessView.setLoanCustomer(loanCustomer);
        businessView.setLoanInfo(loanInfo);
        businessView.setCoborrowers(coborrowers);
     
        return businessView;
    }

}
