package com.creditharmony.loan.test;

import java.util.List;

import javax.annotation.Resource;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.creditharmony.bpm.filenet.FileNetContextHelper;
import com.creditharmony.bpm.filenet.FileNetRequestContext;
import com.creditharmony.bpm.filenet.utils.FileNetContextHolder;
import com.creditharmony.bpm.frame.service.FlowService;
import com.creditharmony.bpm.frame.view.BaseTaskItemView;
import com.creditharmony.bpm.utils.SpringUtil;
import com.query.ProcessQueryBuilder;

import filenet.vw.api.VWSession;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/spring-context*.xml", 
		"classpath:applicationContext-ruleManage.xml" })
public class QueueQueryTest extends TestCase {
	
	@Resource(name = "appFrame_flowServiceImpl")
	private FlowService flowService;
	@Autowired
	protected ApplicationContext ctx;
	
	@Test
	public void testQuery(){
		try{
			SpringUtil.setCtx(ctx);
			FileNetRequestContext context = FileNetContextHelper.login("60000383", "123456");
			FileNetContextHolder.setContext(context);
			
			List<BaseTaskItemView> baseViewList = (List<BaseTaskItemView>) flowService
					.fetchTaskItems("HJ_CAR_DEDUCTION_COMMISSIONER", null,
							BaseTaskItemView.class).getItemList();
			
			if(baseViewList != null && baseViewList.size() > 0){
				for(BaseTaskItemView v:baseViewList){
					System.out.println(v.getApplyId());
				}
			}else{
				System.out.println("====");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
