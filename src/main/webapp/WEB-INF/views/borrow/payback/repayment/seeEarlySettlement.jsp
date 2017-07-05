<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>提前结清已办查看页面</title> 
</head>
<body>

   
       
    <table class="table table-hover table-bordered table-condensed" style="margin:0">
    <thead>
    <tr>
        <th>合同编号</th>
        <th>客户姓名</th>
        <th>门店名称</th>
        <th>合同金额</th>
		<th>已收催收服务费金额</th>
        <th>放款金额</th>
        <th>借款状态</th>
        <th>期供金额</th>
        <th>已还期供金额</th>
		<th>最长逾期天数</th>
        <th>未还违约金(滞纳金)及罚息金额</th>
        <th>已还违约金(滞纳金)及罚息金额</th>
    </tr>
    </thead>
    <tr>
        <td>${earlySettlement.contractCode}</td>
        <td>${earlySettlement.customerName}</td>
        <td>${earlySettlement.orgName}</td>
        <td><fmt:formatNumber value='${earlySettlement.contractMoney}' pattern="#,##0.00" /></td>
        <td><fmt:formatNumber value='${earlySettlement.urgeMoeny}' pattern="#,##0.00" /></td>
        <td><fmt:formatNumber value='${earlySettlement.grantAmount}' pattern="#,##0.00" /></td>
        <td>${earlySettlement.dictLoanStatusLabel}</td>
        <td><fmt:formatNumber value='${earlySettlement.paybackMonthMoney}' pattern="#,##0.00" /></td>
        <td><fmt:formatNumber value='${earlySettlement.hisOverpaybackMonthMoney}' pattern="#,##0.00" /></td>
        <td>${earlySettlement.paybackMaxOverduedays}</td>
        <td><fmt:formatNumber value='${earlySettlement.notYetLiquidatedPenalty}' pattern="#,##0.00" /></td>
        <td><fmt:formatNumber value='${earlySettlement.hisOverLiquidatedPenalty}' pattern="#,##0.00" /></td>
    </tr>
</table> 
         <div class="control-group">
          <table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
               <tr>
                  <td><label class="lab">减免提前结清金额：</label><input type="text" value="<fmt:formatNumber value='${earlySettlement.monthModifyMoney}' pattern="#,##0.00" />" readOnly="true" class="input_text140 "></td>
                  <td><label class="lab" style="width:150px">累计减免违约金(滞纳金)罚息金额：</label><input type="text" value="<fmt:formatNumber value='${earlySettlement.monthPenaltyPunishReductionSum}' pattern="#,##0.00" />" readOnly="true" class="input_text140 "></td>
                  <td><label >提前结清应还款总金额：</label><input type="text" value="<fmt:formatNumber value='${earlySettlement.modifyYingpaybackBackMoney}' pattern="#,##0.00" />" readOnly="true" class="input_text140 "></td>
               </tr>
               <tr>
                  <td><label class="lab">减免人：</label><input type="text" readOnly="true" value="${earlySettlement.reductionBy}" class="input_text140 mr10"></td>
                  <td><label class="lab" style="width:150px">蓝补金额：</label><input type="text" value="<fmt:formatNumber value='${earlySettlement.paybackBuleAmont}' pattern="#,##0.00" />" readOnly="true" class="input_text140 mr10"></td>
                  <td><label >一次性提前结清应还款金额：</label><input type="text"  value="<fmt:formatNumber value='${earlySettlement.monthBeforefinishMoney}' pattern="#,##0.00" />" readOnly="true" class="input_text140 mr10"></td>
               </tr>
               <tr>
                <td ><label class="lab" style="vertical-align:middle">是否确认结清：</label>
					<c:if test="${earlySettlement.chargeStatus eq 3}"> 
						是
					</c:if>
					<c:if test="${earlySettlement.chargeStatus ne 3}"> 
						否
					</c:if>	
				</td> 
				<td ><label style="vertical-align:middle">是否返款：</label>
					<c:if test="${(earlySettlement.chargeStatus eq 3) && (earlySettlement.urgeMoeny le 0) && (earlySettlement.paybackMaxOverduedays gt 30)}">
						是
					</c:if>
					<c:if test="${(earlySettlement.chargeStatus ne 3) || (earlySettlement.paybackMaxOverduedays le 30) || (earlySettlement.urgeMoeny gt 0)}"> 
						否
					</c:if>
			   </td>
            </tr>
            <c:if test="${earlySettlement.dictPayStatusSF != null}">
               <tr>
			    <td>
				  <label class="lab">返款金额：</label>
				  <input type="text" value="<fmt:formatNumber value='${earlySettlement.urgeMoeny}' pattern="0.00"/>" readOnly="true" class="input_text140 mr10">
				</td>
			</tr>
            </c:if>
			
			<tr>
				<td colspan="2"><label class="lab" >审核意见：</label><textarea rows="" readOnly="true" cols="" class="textarea_refuse" >${earlySettlement.remark}</textarea></td>
            </tr>
        </table>
        </div>
</body>

</html>