package com.creditharmony.loan.car.common.mqservice;

import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.creditharmony.core.deduct.bean.out.LoanDeductEntity;
import com.creditharmony.loan.car.common.service.CarFinanceUpdateService;
import com.creditharmony.loan.car.common.service.CarBatchBackInsertService;

/**
 * @Class Name DeductResultMQService
 * @author 施大勇
 * @Create In 2016年02月23日
 */
@Service
public class CarDeductResultMQService implements MessageListener {
	/** 日志. */
	private static final Logger logger = LoggerFactory.getLogger(CarDeductResultMQService.class);

	@Autowired
	private CarFinanceUpdateService financeUpdateService;
	@Autowired
	private CarBatchBackInsertService batchBackInsertService;
	/**
	 * 取得MQ信息
	 */
	@Override
	public void onMessage(Message msgParam) {
		logger.info("【汇金车借系统】划扣结果返回：业务更新开始");

		final TextMessage mapMessage = (TextMessage) msgParam;
		try {
			long sendTime = mapMessage.getJMSTimestamp();

			// 发送方ID
			String fromSys = mapMessage.getStringProperty("FromSys");
			// 发送消息ID
			String msgTypeID = mapMessage.getStringProperty("MsgTypeID1");
			// 发送消息名
			String msgTypeName = mapMessage.getStringProperty("MsgTypeName1");
			// 消息主体
			String jsonMsg = mapMessage.getText();
			logger.debug("【汇金车借系统】划扣结果返回：" + sendTime);
			logger.debug("FromSys : " + fromSys);
			logger.debug("MsgTypeID1 : " + msgTypeID);
			logger.debug("MsgTypeName1 : " + msgTypeName);
			logger.debug("JsonMsg : " + jsonMsg);

			List<LoanDeductEntity> retList = JSONArray.parseArray(jsonMsg,
					LoanDeductEntity.class);
			logger.info("回盘结果件数 : " + retList.size());
			try {
				logger.info("插入批处理返回数据开始 ");
				batchBackInsertService.batchInsert(retList);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("插入批处理返回数据报错！");
			}
			logger.info("插入批处理返回数据结束 ");
			// 更新划扣回盘信息（拆分划扣详细更新）,集中、非集中划扣使用
			financeUpdateService.updateSplit(retList);
		} catch (JMSException e) {
			logger.error("JMS:划扣结果更新失败,请重新更新划扣结果！");
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("更新服务:划扣结果更新失败,请重新更新划扣结果！");
			e.printStackTrace();
		}
		logger.info("【汇金系统】划扣结果返回：业务更新结束");
	}
}
