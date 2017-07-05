<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>蓝补退款终审</title>

<script type="text/javascript" >

function updateRefund(formId) {
	if($('#zcRemark').val()==''){
		art.dialog.alert("请添加审批意见！");
		return;
	}
	$.ajax({
		url : ctx + '/borrow/refund/longRefund/refundExamine',
		type : 'post',
		data : $('#' + formId).serialize(),
		dataType : 'text',
		success : function(data) {
			if (data) {
				// 如果父页面重载或者关闭其子对话框全部会关闭
				var win = art.dialog.open.origin;//来源页面
				art.dialog.close();
				art.dialog.alert("审核成功！");
			} else {
				art.dialog.alert("审核失败,请重试！");
			}
		},
		error : function() {
			art.dialog.alert("服务器异常,请重试！");
		},
		async : false

	});
}

</script>
<meta name="decorator" content="default"/>

</head>
<body>
 <div class="box2 mb10">
  <form id="inputForm" >
        <table class=" table1" cellpadding="0" cellspacing="0" border="0" width="95%">
            <tr><input type="hidden" id="id" name="id" value="${refund.id }" />
            <input type="hidden" id="mt" name="mt" value="${modifyTime }" />
            <input type="hidden" id="type" name="type" value="end" />
            <input type="hidden" id="appType" name="appType" value="${refund.appType }" />
            <input type="hidden" id="startTime" name="startTime" value="<fmt:formatDate value="${refund.createTimes}" pattern="yyyy-MM-dd" />"  />
                <td><label class="lab">客户姓名：</label>
               <input type="text" class="input_text178" id="customerName" name="customerName" value="${payback.customername}" readonly/>
                </td>
                <td><label class="lab">证件号码：</label>
                   <input type="text" class="input_text178" id="customerCertNum" name="customerCertNum" value="${payback.customercertnum}" readonly/>
                </td>
             </tr>
			  <tr>
                <td><label class="lab">合同编号：</label>
                <input type="text" class="input_text178" name="contractCode" value="${payback.contractcode}" readonly="readonly"/>
                </td>
                <td><label class="lab">借款状态：</label>
                   <input type="text" class="input_text178" id="dictLoanStatus" name="loanInfo.dictLoanStatus" value="${payback.dictloanstatus }" readonly/>
                </td>
             </tr>
			   <tr>
                <td><label class="lab">蓝补金额：</label>
               <input type="text" class="input_text178" id = "paybackBuleAmount1" name="paybackBuleAmount1" value="<fmt:formatNumber value="${payback.paybackbuleamount }" pattern='0.00'/>" readOnly/>
                </td>
                <td><label class="lab">期供余额：</label>
                  <input type="text" class="input_text178" name="paybackMonthAmount" value="<fmt:formatNumber value="${payback.paybackmonthamount }" pattern='0.00'/>" readOnly/>
                </td>
             </tr>
       
            <tr>
                <td><label class="lab">退款金额：</label>
               <input type="text" class="input_text178" id = "paybackBuleAmount" name="paybackBuleAmount" value="<fmt:formatNumber value="${refund.refundMoney }" pattern='0.00'/>" readOnly/>
                <input type="hidden" name="refundMoney" value="${refund.refundMoney }" />
                </td>
                <td>
                	<label class="lab">未还违约金及罚息总额：</label>
                  	<input type="text" class="input_text178" value="<fmt:formatNumber value="${payback.notpenaltypunishshouldsum }" pattern='0.00'/>" readOnly/>
                </td>
             </tr>
             <tr>
                <td><label class="lab">退款后蓝补余额：</label>
               <input type="text" class="input_text178" id = "paybackBuleAmount" name="paybackBuleAmount" value="<fmt:formatNumber value="${refund.money }" pattern='0.00'/>" readOnly/>
                </td>
                <td>
                  
                </td>
             </tr>
             <tr>
                <td><label class="lab">收款账户：</label>
               <input type="text" class="input_text178 required"  value="${refund.incomeAccount }" readonly/> 
                </td>
                <td><label class="lab">收款户名：</label>
               <input type="text" class="input_text178 required"  value="${refund.incomeName }" readonly/>
                </td>
             </tr>
             <tr>
                <td><label class="lab">收款银行：</label>
                <input type="text" class="input_text178 required"  value="${refund.incomeBank }" readonly/>
                </td>
                <td><label class="lab">收款支行：</label>
               <input type="text" class="input_text178 required" value="${refund.incomeBranch }" readonly/>
                </td>
             </tr>
             <tr>
                <td><label class="lab">收款省直辖市：</label>
                <input type="text" class="input_text178 required" value="${refund.incomeCityName }" readonly/>
                </td>
                <td><label class="lab">收款市县：</label>
                <input type="text" class="input_text178 required" value="${refund.incomeCountyName }" readonly/>
                </td>
             </tr>
             <tr>
                <td cols='2'><label class="lab">退款原因：</label>
            <textarea rows="5" cols="6" maxlength="150" style="width:200px;height: 80px;" class="required" name="paybackBuleReson" readOnly>${refund.remark }</textarea>
                </td>
             </tr> 
             <tr><td colspan="2"><h1></h1></td></tr>
             <tr>
                <td><label class="lab">初审结果：</label>
               <input type="radio" class="input_text18" disabled <c:if test="${refund.fkResult=='1' }">checked</c:if> />通过
               &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
               <input type="radio" class="input_text18" disabled <c:if test="${refund.fkResult=='0' }">checked</c:if> />退回
               &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                </td>
                <td><label class="lab">终审结果：</label>
                   <input type="radio" class="input_text18" id="zcResult" name="zcResult" value="1" checked/>通过
               <input type="radio" class="input_text18" id="zcResult" name="zcResult" value="0" />退回
                </td>
             </tr>
             <tr>
                <td><label class="lab">退款原因：</label>
               <input type="radio" class="input_text18" disabled <c:if test="${refund.fkReason=='1' }">checked</c:if> />系统原因
               <input type="radio" class="input_text18" disabled <c:if test="${refund.fkReason=='2' }">checked</c:if> />门店操作错误
               <input type="radio" class="input_text18" disabled <c:if test="${refund.fkReason=='3' }">checked</c:if> />客户自身原因
                </td>
                <td></td>
             </tr>
			  <tr>
                <td><label class="lab">审批意见：</label>
            <textarea rows="5" cols="6" maxlength="150" style="width:200px;height: 80px;" class="required" readonly>${refund.fkRemark }</textarea>
                </td>
                <td><label class="lab">审批意见：</label>
            <textarea rows="5" cols="6" maxlength="150" style="width:200px;height: 80px;" class="required" id="zcRemark" name="zcRemark" ></textarea>
                </td>
             </tr> 
             <tr>
                <td cols='2'><label class="lab">申请状态：</label>
            		${refund.appStatusName}<c:if test="${refund.appStatus==null }">待退款</c:if>
                </td>
               
             </tr>
        </table>
        
        <div class="tright pr30">
       
        <input type="button" id="submitBtn" value="提交"  class="btn btn-primary" onclick="updateRefund('inputForm');"/> 
    
        </div>
 </form>  
</div>
</body>
</html>
