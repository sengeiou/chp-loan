package com.creditharmony.loan.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import junit.framework.TestCase;

import org.apache.tools.ant.taskdefs.Sleep;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import cn.daihuade.tool.thread.Frame.TaskPump;
import cn.daihuade.tool.thread.bean.ThreadContext;
import cn.daihuade.tool.thread.face.BusinessExceptionCallBack;
import cn.daihuade.tool.thread.face.DataChannel;
import cn.daihuade.tool.thread.face.EventCallBack;

import com.creditharmony.bpm.frame.exception.WorkflowException;
import com.creditharmony.bpm.frame.task.bean.BaseBusinessBean;
import com.creditharmony.bpm.utils.SpringUtil;
import com.creditharmony.loan.car.LoopQueueView;
import com.creditharmony.loan.car.carApply.service.CarApplicationInterviewInfoService;
import com.creditharmony.loan.car.common.entity.CarApplicationInterviewInfo;
import com.strongit.filenet.pe.core.VWWorkObjectHelper;

/**
 * 
 * @author chenwd
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/spring-context*.xml", 
		"classpath:applicationContext-ruleManage.xml" })
public class TransationTest extends TestCase {
	
	@Autowired
	protected ApplicationContext ctx;
	@Autowired
	CarApplicationInterviewInfoService service;
	
	@Test
	public void test(){
		try{
			SpringUtil.setCtx(ctx);
			
			DataSourceTransactionManager txManager = (DataSourceTransactionManager)SpringUtil.getBean("loanTransactionManager");
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			TransactionStatus status = txManager.getTransaction(def);
			try{
				CarApplicationInterviewInfo entity = new CarApplicationInterviewInfo();
				entity.setLoanCode("test_chenwd_1");
				service.save(entity);
				
				txManager.commit(status);
			} catch (Throwable e){
				e.printStackTrace();
				txManager.rollback(status);
				throw new WorkflowException(e.getMessage(),e);
			}
			System.out.println("====over1===");
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void test0(){
		while(true){
			System.out.println(Math.round(Math.random()*3));
		}
	}
	
	@Test
	public void test2(){
		try{
			SpringUtil.setCtx(ctx);
			
			DataSourceTransactionManager txManager = (DataSourceTransactionManager)SpringUtil.getBean("loanTransactionManager");
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			TransactionStatus status = txManager.getTransaction(def);
			try{
				CarApplicationInterviewInfo entity = new CarApplicationInterviewInfo();
				entity.setLoanCode("test_chenwd_2");
				service.save(entity);
				
				txManager.commit(status);
			} catch (Throwable e){
				e.printStackTrace();
				txManager.rollback(status);
				throw new WorkflowException(e.getMessage(),e);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("====over2===");
	}
	
	@Test
	public void test3(){
		try{
			SpringUtil.setCtx(ctx);

			TaskPump<BaseBusinessBean> tp = new TaskPump<BaseBusinessBean>();
			ThreadContext tc = new ThreadContext();
			tc.setThreadCount(1);
			tp.setTc(tc);
			
			//数据加载
			tp.setChannel(new DataChannel<BaseBusinessBean>() {
				public List<BaseBusinessBean> getTasks() {
					List<BaseBusinessBean> tList = new ArrayList<BaseBusinessBean>();
					LoopQueueView bean = null;
					
					for(int i=0;i<10000;i++){
						
						bean = new LoopQueueView();
						bean.setLoanCode("test_chenwd_" + (new Random()).nextInt(1000000000));
						tList.add(bean);
					}
					
					System.out.println( "################" + new Date() + "  请求到的数据：" + tList.size());
					return tList;
				}
			});
			
			//业务处理
			tp.setTaskCall(new EventCallBack<BaseBusinessBean>() {
				
				@Override
				public void invoke(BaseBusinessBean bbb) {
					try {
						Thread.sleep(Math.round(Math.random()*3));
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					
					DataSourceTransactionManager txManager = (DataSourceTransactionManager)SpringUtil.getBean("loanTransactionManager");
					DefaultTransactionDefinition def = new DefaultTransactionDefinition();
					TransactionStatus status = txManager.getTransaction(def);
					try{
						LoopQueueView lqv = (LoopQueueView)bbb;
						CarApplicationInterviewInfoService carService = (CarApplicationInterviewInfoService)SpringUtil.getBean("carApplicationInterviewInfoService");
						CarApplicationInterviewInfo entity = new CarApplicationInterviewInfo();
						entity.setLoanCode(lqv.getLoanCode());
						carService.save(entity);
						System.out.println("=========saved==========thread Name:" + 
								Thread.currentThread().getName() + "; thread Id:" +
								Thread.currentThread().getId());
						Thread.sleep(Math.round(Math.random()*3));
						txManager.commit(status);
						//VWWorkObjectHelper.doSave(null, null);
					} catch (Throwable e){
						e.printStackTrace();
						txManager.rollback(status);
						throw new WorkflowException(e.getMessage(),e);
					}
				}
			});
			
			
			//异常处理
			tp.setTaskException(new BusinessExceptionCallBack<BaseBusinessBean>() {
				public void invoke(BaseBusinessBean arg, Throwable e) {
					String msg = "  applyId：" + arg.getApplyId();
					System.err.println(msg);
					e.printStackTrace();
				}
			});
			
			tp.setPushException(tp.getTaskException());
			
			tp.Run();
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
