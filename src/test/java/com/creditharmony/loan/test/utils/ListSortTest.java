/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.test.utilsListSortTest.java
 * @Create By 王彬彬
 * @Create In 2015年12月23日 下午8:04:06
 */
package com.creditharmony.loan.test.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.creditharmony.loan.borrow.payback.entity.PaybackTransferInfo;
import com.creditharmony.loan.borrow.payback.service.DealPaybackService;
import com.creditharmony.loan.common.consts.SortConst;
import com.creditharmony.loan.common.utils.ListSortUtil;
import com.creditharmony.loan.test.base.AbstractTestCase;

/**
 * @Class Name ListSortTest
 * @author 王彬彬
 * @Create In 2015年12月23日
 */
public class ListSortTest extends AbstractTestCase {

	@Autowired
	private DealPaybackService dealPaybackService;

	public void sortTest() {
		List<TestBean> targetList = new ArrayList<TestBean>();
		targetList.add(new TestBean(1, "title1", 31, new Date()));

		targetList.add(new TestBean(2, "title2", 12, new Date()));

		targetList.add(new TestBean(1, "title3", 12, new Date()));

		System.out.println("排序前: " + targetList.get(0).getHits() + ":::"
				+ targetList.get(0).getId());

		ListSortUtil<TestBean> sortList = new ListSortUtil<TestBean>();
		sortList.sort(targetList, SortConst.ASC, new String[] { "hits", "id" });
		System.out.println("排序后：" + targetList.get(0).getHits() + ":::"
				+ targetList.get(0).getId());

	}

	@Test
	public void test() {
		PaybackTransferInfo paybackTransferInfo = new PaybackTransferInfo();

		List<PaybackTransferInfo> paybackTransferInfoList = new ArrayList<PaybackTransferInfo>();
		// 获取门店申请的汇款数据
		paybackTransferInfoList = dealPaybackService
				.findTransfer(paybackTransferInfo);

		for (PaybackTransferInfo p : paybackTransferInfoList) {
			System.out.println("排序前：" + p.getReallyAmount() + ":::");
		}

		ListSortUtil<PaybackTransferInfo> sortList = new ListSortUtil<PaybackTransferInfo>();

		sortList.sort(paybackTransferInfoList, SortConst.DESC,
				new String[] { "ReallyAmount" });

		for (PaybackTransferInfo p : paybackTransferInfoList) {
			System.out.println("排序后：" + p.getReallyAmount() + ":::");
		}
	}
}
