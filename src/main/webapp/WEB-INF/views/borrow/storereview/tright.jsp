<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/storereview/loanFlowStoreReview_new.js?version=1"></script>	
<div class="tright" style="margin-right: 10px;">
	<input type="button" class="btn btn-primary" id="storeViewSubmitFlow" value="提交" style="padding: 3px 6px !important;font-size: 12px;"/>
	<input type="hidden" id="imageUrl" value="${workItem.bv.imageUrl}">		
	<input type="button" class="btn btn-small" id="storereviews" value="客户影像资料">
	<button class="btn btn-small" id="${workItem.bv.applyId}"  onclick="showLoanHis(this.id)">历史</button>
 	<button class="btn btn-small" id="refuse">门店拒绝</button>
	<button class="btn btn-small" id="giveUp">客户放弃</button>
    <c:if test="${workItem.bv.preResponse == 'STORE_TO_CHECK' || workItem.bv.preResponse == 'STORE_BACK_CHECK' }">
        <button class="btn btn-small" loanCode="${workItem.bv.loanCode}" id="backDetailBtn">汇诚退回明细</button>
    </c:if>
	<button class="btn btn-small" id="backBtn">返回</button> 
</div>
<!-- 门店拒绝弹出框 -->
<div id="refuseMod" style="display:none">
	<form method="post" action="">
		<table class="table1" cellpadding="0" cellspacing="0" border="0"
			width="100%">
			<tr valign="top">
				<td><label >拒绝理由：</label>
					<textarea id="rejectReason" maxlength="450" cols="55" class="textarea_refuse" ></textarea>
				</td>
			</tr>
		</table>		
	</form>
</div>
<form id="storeViewSubmitForm" method="post">
	<input type="hidden" name="loanCode" value="${workItem.bv.loanCode}" id="loanCode">
	<input type="hidden" name="flowId" value="${workItem.flowId}"/>
	<input type="hidden" name="flowName" value="${workItem.flowName }">
	<input type="hidden" name="stepName" value="${workItem.stepName }">
	<input type="hidden" name="token" value="${workItem.token}">
	<input type="hidden" name="wobNum" value="${workItem.wobNum }">
	<input type="hidden" name="response" value="${workItem.bv.preResponse }" id="response">
	<input type="hidden" name="applyId" value="${workItem.bv.applyId}">
	<input type="hidden" name="lastLoanStatus" value="${workItem.bv.lastLoanStatus}"/>
	<input type="hidden" name="loanCustomer.loanCode" value="${workItem.bv.loanCustomer.loanCode}"/>
	<input type="hidden" name="queue" value="HJ_CUSTOMER_AGENT"/>
    <input type="hidden" name="viewName" value="loanflow_borrowlist_workItems"/>
	
	<input type="hidden" name="rejectReason" id="remark"/>
	<input type="hidden" name="teleFlag" value="${param.teleFlag}" id="teleFlag"/>
</form>
<script type="text/javascript">
	var teleFlag='${param.teleFlag}';
	$("#backBtn").click(function(){
		if(teleFlag=='0'){
			window.location=ctx+"/borrow/borrowlist/fetchTaskItems";
		}else if(teleFlag=='1'){
			window.location=ctx+"/borrow/borrowlist/fetchTaskTelesales";
		}
	});	
	//门店复核提交
	$('#storeViewSubmitFlow').bind('click',function(){
		launch.storeViewSubmitFlow('storeViewSubmitForm','storeViewSubmitFlow');	
	});
</script>
