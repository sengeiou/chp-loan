package com.creditharmony.loan.webservice.infodisclosure.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.adapter.constant.ReturnConstant;
import com.creditharmony.adapter.service.disclosure.InfoDisclosureBaseService;
import com.creditharmony.adapter.service.disclosure.bean.InfoDisclosureInBean;
import com.creditharmony.adapter.service.disclosure.bean.InfoDisclosureOutBean;
import com.creditharmony.adapter.service.disclosure.bean.InfoDisclosureOutDetailBean;
import com.creditharmony.common.util.IdGen;
import com.creditharmony.loan.webservice.infodisclosure.dao.InfoDisclosureDao;
import com.creditharmony.loan.webservice.infodisclosure.entity.InfoDisclosure;
import com.google.common.collect.Maps;

@Service
public class InfoDisclosureService extends InfoDisclosureBaseService {
	protected Logger logger = LoggerFactory
			.getLogger(InfoDisclosureService.class);

	@Autowired
	private InfoDisclosureDao infoDisclosureDao;

	private static final Random r = new Random();

	@Override
	public InfoDisclosureOutBean doExec(InfoDisclosureInBean inParam) {
		logger.info("-----------信息披露接口调用开始-------------");
		logger.info("-----------信息披露接口调用参数：-------------" + inParam.getParam());
		Set<String> contCodes = new HashSet<String>();
		InfoDisclosureOutBean outParam = new InfoDisclosureOutBean();
		Map<String, Object> param = Maps.newHashMap();
		try {
			List<InfoDisclosure> infoDisclosureList = null;
			infoDisclosureList = infoDisclosureDao.getBatchDetailInfo(inParam
					.getContractNumList());
			List<InfoDisclosureOutDetailBean> detailList = null;
			if (infoDisclosureList != null && infoDisclosureList.size() > 0) {
				InfoDisclosureOutDetailBean target;
				detailList = new ArrayList<InfoDisclosureOutDetailBean>();
				for (int i = 0; i < infoDisclosureList.size(); i++) {
					target = new InfoDisclosureOutDetailBean();
					InfoDisclosure source = infoDisclosureList.get(i);
					String contCode = source.getContractCode();
					if (contCodes.contains(contCode)) {// 去重
						continue;
					} else {
						contCodes.add(contCode);
					}
					String loanCode = source.getLoanCode();
					if (!loanCode.contains("JK")) {// 2.0数据
						source.setFeeAllRaio(source.getFeeAllRaio().multiply(
								new BigDecimal("100")));// 修改利率字段
						if("".equals(source.getRiskLevel())){//未初始化
							int score = this.getScore();
							source.setRiskLevel(String.valueOf(score));// 获取随机风险等级
							param.put("id", IdGen.uuid());
							param.put("loanCode", loanCode);
							param.put("score", score);
							infoDisclosureDao.saveRiskLevel(param);
						}						
					}
					source.setRiskLevel(getRiskLevel(Integer
							.valueOf(source.getRiskLevel())));
					BeanUtils.copyProperties(target, source);
					detailList.add(target);
				}
				outParam.setDetailList(detailList);
				outParam.setRetCode(ReturnConstant.SUCCESS);
				logger.info("-----------信息披露接口返回参数：-------------"
						+ outParam.getParam());
			} else {
				outParam.setRetCode(ReturnConstant.FAIL);
				outParam.setRetMsg("未找到对应的合同信息.");
				logger.error("-----------未找到对应的合同信息.-------------");
			}
		} catch (Exception e) {
			outParam.setRetCode(ReturnConstant.ERROR);
			outParam.setRetMsg("数据查询出现异常.");
			logger.error("-----------数据查询出现异常：-------------" + e);
		}
		return outParam;
	}

	/**
	 * 获取随机的风险评分
	 * 
	 * @return
	 */
	private int getScore() {
		return 631+r.nextInt(70);
	}

	/**
	 * 通过评分获得评分等级
	 * 
	 * @param score
	 * @return
	 */
	private String getRiskLevel(int score) {
		String riskLevel = "";

		if (score > 750) {
			riskLevel = "A1";
		} else if (score > 720) {
			riskLevel = "A2";
		} else if (score > 700) {
			riskLevel = "A3";
		} else if (score > 690) {
			riskLevel = "B1";
		} else if (score > 680) {
			riskLevel = "B2";
		} else if (score > 670) {
			riskLevel = "B3";
		} else if (score > 660) {
			riskLevel = "C1";
		} else if (score > 650) {
			riskLevel = "C2";
		} else if (score > 640) {
			riskLevel = "C3";
		} else if (score > 630) {
			riskLevel = "C4";
		} else if (score > 620) {
			riskLevel = "D1";
		} else if (score > 610) {
			riskLevel = "D2";
		} else if (score > 600) {
			riskLevel = "D3";
		} else if (score > 590) {
			riskLevel = "D4";
		} else if (score > 580) {
			riskLevel = "E1";
		} else if (score > 570) {
			riskLevel = "E2";
		} else if (score > 560) {
			riskLevel = "E3";
		} else {
			riskLevel = "F";
		}

		return riskLevel;
	}

}
