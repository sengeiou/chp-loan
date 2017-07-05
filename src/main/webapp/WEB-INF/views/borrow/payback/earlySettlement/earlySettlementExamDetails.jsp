<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>提前结清审核详情页面</title>
<script type="text/javascript">
$(document).ready(function() {
	var msg = "${message}";
	if (msg != "" && msg != null) {
		art.dialog.alert(msg);
	}
});
</script>
<script type="text/javascript" src="${context}/js/payback/earlySettlementExam.js"></script>
<script type="text/javascript" src="${context}/js/common.js"></script>
</head>
<body>
        <div ><h2 class="f14 pl10">基本信息</h2></div>

    <div class="box2 ">
        <table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td><label class="lab" style="width:150px">客户姓名：&nbsp;</label>${paybackCharge.loanCustomer.customerName }</td>
                <td><label class="lab" style="width:150px">证件号码：&nbsp;</label>${paybackCharge.loanCustomer.customerCertNum }</td>
                <td><label class="lab" style="width:150px">合同编号：&nbsp;</label>${paybackCharge.contractCode }</td>
            </tr>
            <tr>
                <td>
                    <label class="lab" style="width:150px">产品类型：&nbsp;</label>${paybackCharge.loanInfo.productType }&nbsp;
                </td>
                <td><label class="lab" style="width:150px">合同金额（元）：&nbsp;</label>
                <fmt:formatNumber value='${paybackCharge.contract.contractAmount }' pattern="#,##0.00" />
                </td>
                <td><label class="lab"  style="width:150px">期供金额（元）：&nbsp;</label>
                 <fmt:formatNumber value='${paybackCharge.payback.paybackMonthAmount }' pattern="#,##0.00" />
                </td>
            </tr>
            <tr>
                <td><label class="lab" style="width:150px">期数 ：&nbsp;</label>${paybackCharge.contract.contractMonths}</td>
                <td><label class="lab" style="width:150px">借款状态：&nbsp;</label>${paybackCharge.loanInfo.dictLoanStatusLabel}</td>
                <td><label class="lab" style="width:150px">合同版本号：&nbsp;</label>${paybackCharge.contract.contractVersionLabel}</td>
            </tr>
        </table>	
     
    </div>

   
        <div><h2 class="f14 pl10 mt20">还款信息</h2></div>

    <div class="box2 ">
        <table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td><label class="lab" style="width:150px">违约金(滞纳金)及罚息总金额：&nbsp;</label>
                    <fmt:formatNumber value='${paybackCharge.penaltyTotalAmount }' pattern="#,##0.00" />
                </td>
                <td><label class="lab" style="width:150px">提前结清金额：&nbsp;</label>
                    <fmt:formatNumber value='${paybackCharge.paybackMonth.monthBeforeFinishAmount }' pattern="#,##0.00" />
               </td>
                <td><label class="lab" style="width:150px">蓝补余额：&nbsp;</label>
                    <fmt:formatNumber value='${paybackCharge.payback.paybackBuleAmount }' pattern="#,##0.00" />
                </td>
            </tr>
            <tr>
                <td><label class="lab" style="width:150px">提前结清应还款总金额：&nbsp;</label>
                     <fmt:formatNumber value='${paybackCharge.settleTotalAmount }' pattern="#,##0.00" />
                </td>
                <td><label class="lab" style="width:150px">减免金额：&nbsp;</label>
                     <fmt:formatNumber value='${paybackCharge.paybackMonth.creditAmount }' pattern="#,##0.00" />
                </td>
                <td><label class="lab" style="width:150px">提前结清申请资料：</label>
					<input class="btn_edit" id="downZip"
					value="${paybackCharge.uploadFilename }"
					docId=${paybackCharge.uploadPath }
					fileName="${paybackCharge.uploadFilename }" />
					<input type="hidden" id="detailId" value="${paybackCharge.id  }">
					</td>
            </tr>
             <tr>
                <td><label class="lab" style="width:150px">最长逾期天数：&nbsp;</label>
                     ${paybackCharge.payback.paybackMaxOverduedays }
                </td>
                <td><label class="lab" style="width:150px">催收服务费金额：&nbsp;</label>
                     <fmt:formatNumber value='${paybackCharge.urgeServicesMoney.urgeMoeny}' pattern="#,##0.00" />
                </td>
            </tr>
        </table>
    </div>

 
       <h2 class="f14 pl10 mt20">审核结果</h2>

    <div class="box2 ">
    <form id="earlyForm">
        <table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td>
                    <input type="hidden" value="${paybackCharge.id }" name="chargeId"/>
                    <input type="hidden" value="${paybackCharge.payback.id}" name="id"/>
	                <label class="lab"><span style="color:red">*</span>审核结果：</label>
	                <input type="radio" name="one" value="0" required>&nbsp;通过&nbsp;
	                <input type="radio" name="one" value="1" required>&nbsp;退回&nbsp;
                </td>
            </tr>
            <tr>
                <td>
                  <label class="lab"><span style="color:red">*</span>审核意见：</label>
                  <textarea class="textarea_big " id="returnReason" maxlength="50"></textarea>
                  <span style="color:red">(50字以内)</span>
                </td>
            </tr>
        </table>
        </div>
            <div class="tright mt10 pr30"><button class="btn btn-primary" type="button" id="submitBtn">提交</button>&nbsp;&nbsp;
            <button class="btn btn-primary" type="button"
             onclick="JavaScript:window.location='${ctx}/borrow/payback/earlySettlement/list'" >返回</button>
             </div>
      </form>
      

  
</body>
</html>