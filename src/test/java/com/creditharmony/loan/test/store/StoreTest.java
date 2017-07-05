/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.test.StoreStoreTest.java
 * @Create By 王彬彬
 * @Create In 2015年12月7日 下午6:16:52
 */
package com.creditharmony.loan.test.store;

import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.creditharmony.core.deduct.bean.out.LoanDeductEntity;
import com.creditharmony.core.deduct.bean.out.PaybackSplitEntityEx;
import com.creditharmony.loan.common.service.DeductUpdateService;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.service.OrgGlService;
import com.creditharmony.loan.test.base.AbstractTestCase;
import com.creditharmony.loan.users.service.UserInfoService;

/**
 * @Class Name StoreTest
 * @author 王彬彬
 * @Create In 2015年12月7日
 */
public class StoreTest extends AbstractTestCase {

	@Autowired
	private OrgGlService orgService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private DeductUpdateService deductUpdateService;
	
	public void testStore() {
//		OrgGl org = new OrgGl();
//		// org.setIsNewRecord(false);
//		// org.preInsert();
//		org.setId(IdGen.uuid());
//		org.setAddId(\"1\");
//		org.setAreaId(\"2\");
//		org.setCityId(\"3\");
//		org.setSort(1);
//		org.setDistrictId(\"4\");
//		org.setName(\"石家庄裕华路门店\");
//		org.setStoresCode(\"001000\");
//		org.setOrgCode(String.valueOf(System.currentTimeMillis()));
//		org.setOrgContacts(\"汇金\");
//		org.setOrgDescription(\"石家庄裕华路3\");
//		org.setOrgEmail(\"email@123.com\");
//		org.setOrgName(org.getName());
//		org.setOrgStatus(\"0\");
//		org.setOrgTel(\"15888888888\");
//		org.setOrgType(\"4\");
//
//		org.setCreateBy(\"123\");
//		org.setCreateTime(new Date());
//		org.setModifyBy(\"223\");
//		org.setModifyTime(new Date());
//		orgService.insert(org);
	}

	@Test
	public void testGetUser() {
		String jsonMsg = "[{\"applyAmount\":\"8350.55000\",\"batId\":\"batid2\",\"createBy\":\"Batch\",\"createTime\":1456285605059,\"deductFailMoney\":\"0\",\"deductSucceedMoney\":\"8350.55000\",\"deductSysIdType\":\"HJ_01\",\"id\":\"81d3ccdb18074a1eb61199875b524f9d\",\"modifyBy\":\"Batch\",\"modifyTime\":1456285605059,\"refId\":\"refId2\",\"requestId\":\"TZ4016088552\",\"splitData\":[{\"batchFlag\":\"0\",\"createBy\":\"Batch\",\"createTime\":1456285605041,\"dealType\":\"0\",\"deductType\":\"201\",\"id\":\"4a478fddab744054bf7f637f3132403d\",\"modifyBy\":\"Batch\",\"modifyTime\":1456285605041,\"paybackApplyId\":\"TZ4016088552\",\"splitAmount\":350.55,\"splitBackResult\":\"0000\",\"splitFailResult\":\"\",\"splitPch\":\"batid2\",\"splitbackDate\":1456285548171},{\"batchFlag\":\"0\",\"createBy\":\"Batch\",\"createTime\":1456285605044,\"dealType\":\"0\",\"deductType\":\"201\",\"id\":\"e5a28e0ba60547ecb9faa2b4ca3c2682\",\"modifyBy\":\"Batch\",\"modifyTime\":1456285605044,\"paybackApplyId\":\"TZ4016088552\",\"splitAmount\":500,\"splitBackResult\":\"0000\",\"splitFailResult\":\"\",\"splitPch\":\"batid2\",\"splitbackDate\":1456285545446},{\"batchFlag\":\"0\",\"createBy\":\"Batch\",\"createTime\":1456285605045,\"dealType\":\"0\",\"deductType\":\"201\",\"id\":\"c39039bfcf6a42e99b2e30ef427f44fd\",\"modifyBy\":\"Batch\",\"modifyTime\":1456285605045,\"paybackApplyId\":\"TZ4016088552\",\"splitAmount\":500,\"splitBackResult\":\"0000\",\"splitFailResult\":\"\",\"splitPch\":\"batid2\",\"splitbackDate\":1456285542340},{\"batchFlag\":\"0\",\"createBy\":\"Batch\",\"createTime\":1456285605045,\"dealType\":\"0\",\"deductType\":\"201\",\"id\":\"d5b6d8b7a4954724a0fff808dc2e9edb\",\"modifyBy\":\"Batch\",\"modifyTime\":1456285605045,\"paybackApplyId\":\"TZ4016088552\",\"splitAmount\":500,\"splitBackResult\":\"0000\",\"splitFailResult\":\"\",\"splitPch\":\"batid2\",\"splitbackDate\":1456285539341},{\"batchFlag\":\"0\",\"createBy\":\"Batch\",\"createTime\":1456285605046,\"dealType\":\"0\",\"deductType\":\"201\",\"id\":\"b9bb1ff4c8d84ff18f8e43f6ce564792\",\"modifyBy\":\"Batch\",\"modifyTime\":1456285605046,\"paybackApplyId\":\"TZ4016088552\",\"splitAmount\":500,\"splitBackResult\":\"0000\",\"splitFailResult\":\"\",\"splitPch\":\"batid2\",\"splitbackDate\":1456285536817},{\"batchFlag\":\"0\",\"createBy\":\"Batch\",\"createTime\":1456285605046,\"dealType\":\"0\",\"deductType\":\"201\",\"id\":\"e36b0d7b3bf5431b97ca079c9e89c708\",\"modifyBy\":\"Batch\",\"modifyTime\":1456285605046,\"paybackApplyId\":\"TZ4016088552\",\"splitAmount\":500,\"splitBackResult\":\"0000\",\"splitFailResult\":\"\",\"splitPch\":\"batid2\",\"splitbackDate\":1456285534284},{\"batchFlag\":\"0\",\"createBy\":\"Batch\",\"createTime\":1456285605047,\"dealType\":\"0\",\"deductType\":\"201\",\"id\":\"803dbd6de4b0434ea13401457a994f0c\",\"modifyBy\":\"Batch\",\"modifyTime\":1456285605047,\"paybackApplyId\":\"TZ4016088552\",\"splitAmount\":500,\"splitBackResult\":\"0000\",\"splitFailResult\":\"\",\"splitPch\":\"batid2\",\"splitbackDate\":1456285531764},{\"batchFlag\":\"0\",\"createBy\":\"Batch\",\"createTime\":1456285605047,\"dealType\":\"0\",\"deductType\":\"201\",\"id\":\"ae28aea7ac8d43cd802ab3a125edf4dd\",\"modifyBy\":\"Batch\",\"modifyTime\":1456285605047,\"paybackApplyId\":\"TZ4016088552\",\"splitAmount\":500,\"splitBackResult\":\"0000\",\"splitFailResult\":\"\",\"splitPch\":\"batid2\",\"splitbackDate\":1456285529281},{\"batchFlag\":\"0\",\"createBy\":\"Batch\",\"createTime\":1456285605048,\"dealType\":\"0\",\"deductType\":\"201\",\"id\":\"435bc2965ea949efb60811af8bfdf039\",\"modifyBy\":\"Batch\",\"modifyTime\":1456285605048,\"paybackApplyId\":\"TZ4016088552\",\"splitAmount\":500,\"splitBackResult\":\"0000\",\"splitFailResult\":\"\",\"splitPch\":\"batid2\",\"splitbackDate\":1456285526664},{\"batchFlag\":\"0\",\"createBy\":\"Batch\",\"createTime\":1456285605048,\"dealType\":\"0\",\"deductType\":\"201\",\"id\":\"11293ed5b33447b8976e19263d75743c\",\"modifyBy\":\"Batch\",\"modifyTime\":1456285605048,\"paybackApplyId\":\"TZ4016088552\",\"splitAmount\":500,\"splitBackResult\":\"0000\",\"splitFailResult\":\"\",\"splitPch\":\"batid2\",\"splitbackDate\":1456285524273},{\"batchFlag\":\"0\",\"createBy\":\"Batch\",\"createTime\":1456285605049,\"dealType\":\"0\",\"deductType\":\"201\",\"id\":\"fb4dd748eab545f2b00f3858b4147d47\",\"modifyBy\":\"Batch\",\"modifyTime\":1456285605049,\"paybackApplyId\":\"TZ4016088552\",\"splitAmount\":500,\"splitBackResult\":\"0000\",\"splitFailResult\":\"\",\"splitPch\":\"batid2\",\"splitbackDate\":1456285521917},{\"batchFlag\":\"0\",\"createBy\":\"Batch\",\"createTime\":1456285605049,\"dealType\":\"0\",\"deductType\":\"201\",\"id\":\"6317e88c1d204acb8847c04ccf94d175\",\"modifyBy\":\"Batch\",\"modifyTime\":1456285605049,\"paybackApplyId\":\"TZ4016088552\",\"splitAmount\":500,\"splitBackResult\":\"0000\",\"splitFailResult\":\"\",\"splitPch\":\"batid2\",\"splitbackDate\":1456285519365},{\"batchFlag\":\"0\",\"createBy\":\"Batch\",\"createTime\":1456285605050,\"dealType\":\"0\",\"deductType\":\"201\",\"id\":\"7320c2074af94d06b8a192f46c965204\",\"modifyBy\":\"Batch\",\"modifyTime\":1456285605050,\"paybackApplyId\":\"TZ4016088552\",\"splitAmount\":500,\"splitBackResult\":\"0000\",\"splitFailResult\":\"\",\"splitPch\":\"batid2\",\"splitbackDate\":1456285516742},{\"batchFlag\":\"0\",\"createBy\":\"Batch\",\"createTime\":1456285605050,\"dealType\":\"0\",\"deductType\":\"201\",\"id\":\"91af5525fb084a5783d6e9cba4d7a0aa\",\"modifyBy\":\"Batch\",\"modifyTime\":1456285605050,\"paybackApplyId\":\"TZ4016088552\",\"splitAmount\":500,\"splitBackResult\":\"0000\",\"splitFailResult\":\"\",\"splitPch\":\"batid2\",\"splitbackDate\":1456285513948},{\"batchFlag\":\"0\",\"createBy\":\"Batch\",\"createTime\":1456285605051,\"dealType\":\"0\",\"deductType\":\"201\",\"id\":\"380b42b5a4674d3e97f25e69066e338f\",\"modifyBy\":\"Batch\",\"modifyTime\":1456285605051,\"paybackApplyId\":\"TZ4016088552\",\"splitAmount\":500,\"splitBackResult\":\"0000\",\"splitFailResult\":\"\",\"splitPch\":\"batid2\",\"splitbackDate\":1456285511342},{\"batchFlag\":\"0\",\"createBy\":\"Batch\",\"createTime\":1456285605052,\"dealType\":\"0\",\"deductType\":\"201\",\"id\":\"1e1c9ef999454426a92729cd4e2bb325\",\"modifyBy\":\"Batch\",\"modifyTime\":1456285605052,\"paybackApplyId\":\"TZ4016088552\",\"splitAmount\":500,\"splitBackResult\":\"0000\",\"splitFailResult\":\"\",\"splitPch\":\"batid2\",\"splitbackDate\":1456285508715},{\"batchFlag\":\"0\",\"createBy\":\"Batch\",\"createTime\":1456285605053,\"dealType\":\"0\",\"deductType\":\"201\",\"id\":\"74ba51377b844b0fa7a9a86957426c50\",\"modifyBy\":\"Batch\",\"modifyTime\":1456285605053,\"paybackApplyId\":\"TZ4016088552\",\"splitAmount\":500,\"splitBackResult\":\"0000\",\"splitFailResult\":\"\",\"splitPch\":\"batid2\",\"splitbackDate\":1456285506138}]}]";
		List<LoanDeductEntity> retList = JSONArray.parseArray(jsonMsg,
				LoanDeductEntity.class);
		System.out.println(retList.size());
		List<PaybackSplitEntityEx> splitData = retList.get(0).getSplitData();
		for(PaybackSplitEntityEx ex : splitData)
		{
			System.out.println(JSONArray.toJSON(ex));
			System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(ex.getSplitbackDate()));
		}
		
//		// 更新划扣回盘信息（拆分划扣详细更新）
//					deductUpdateService.updateSplit(retList);
	}
	

}
