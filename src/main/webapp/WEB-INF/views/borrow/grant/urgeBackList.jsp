<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script src="${context}/js/common.js" type="text/javascript"></script>
<script src="${context}/js/grant/urgeBack.js" type="text/javascript"></script>
<meta name="decorator" content="default" />
<script language="javascript">
$(document).ready(function(){
	loan.getstorelsit("storesName","storesCode");
});
	function page(n, s) {
		if (n) $("#pageNo").val(n);
		if (s) $("#pageSize").val(s);
		$("#backForm").attr("action", "${ctx}/borrow/grant/urgeBackList/urgeBackListInfo");
		$("#backForm").submit();
		return false;
	}

	function backReason() {
		$('#diabox01').modal('toggle').css({
			width : '90%',
			'margin-left' : function() {
				return -($(this).width() / 2);
			}
		});
	}
</script>
</head>
<body>

	<div id="messageBox" class="alert alert-success ${empty message ? 'hide' : ''}">
		<button data-dismiss="alert" class="close">×</button>
		<label id="loginSuccess" class="success">${message}</label>
	</div>

	<div class="control-group">
		   <form:form  action="${ctx }/borrow/grant/urgeBackList/urgeBackListInfo" method="post" id="backForm" modelAttribute="urgeBackMoneyEx">
   			<input id="pageNo" name="pageNo" type="hidden" value="${urgeListPage.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" value="${urgeListPage.pageSize}" />	
			<input id="returnIds" name="returnIds" type="hidden" />
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
			    <td><label class="lab">门店：</label>
                <form:input type="text" id="storesName" class="input_text178" path="storesName" readonly="true"></form:input>
                	  <i id="selectStoresBtn"
						class="icon-search" style="cursor: pointer;"></i>
				   <form:hidden path="storesCode" id="storesCode"/></td>
                
                <td><label class="lab">合同编号：</label><form:input type="text" class="input_text178" path="contractCode"></form:input></td>
                <td>
				 <sys:multipleBank bankClick="selectBankBtn"
							bankName="search_applyBankName" bankId="bankNameCode"></sys:multipleBank>
				 <label class="lab">开户行名称：</label>
						<form:input type="text"
							id="search_applyBankName" name="search_applyBankName"
							class="input_text178" path="bankName" readonly="true"></form:input> 
						<i id="selectBankBtn" class="icon-search" style="cursor: pointer;"></i>
						<form:hidden path="bankNameCode" id="bankNameCode"/>
				</td>
            </tr>
            <tr>
            
                 <td><label class="lab" >申请日期：</label><input id="grantDate" name="backApplyPayTime" 
                 value="<fmt:formatDate value='${urgeBackMoneyEx.backApplyPayTime }' pattern='yyyy-MM-dd'/>"
                     type="text" class="Wdate input_text178" size="10"  
                                        onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer"></input>
                                        
                                        </td>
                <td ><label class="lab">返款状态：</label><form:select class="select180" path="dictPayStatus">
						<form:option value="">请选择</form:option>
						<c:forEach items="${fns:getDictList('jk_urge_repay_status')}" var="card_type">
						<c:if test="${card_type.label=='待返款'||card_type.label=='已返款'}">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
						</c:if>
				    </c:forEach>
				
						</form:select></td>
				 <td><label class="lab">返款结果：</label><form:select class="select180" path="dictPayResult">
						<form:option value="">请选择</form:option>
							<c:forEach items="${fns:getDictList('jk_payback_fee_result')}" var="card_type">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
				   			 </c:forEach>
				</form:select></td>
            </tr>
           
        </table>
		<div class="tright pr30 pb5">
			<input type="submit" class="btn btn-primary" value="搜索"></input>
			<button class="btn btn-primary" id="clearBtn">清除</button>
		</div>
		</form:form>
		
	</div>
	
	<p class="mb5">
		<button class="btn btn-small" id="daoBtnList">导出excel</button>
		<button class="btn btn-small" id="daoBtnrs" role="button" data-target="#uploadAuditedModal" data-toggle="modal">导入返款结果</button>
		<button class="btn btn-small" id="exitBt" data-toggle="modal" role="button">退回</button>
	</p>
	<div class="box5">
	<table id="tableList" class="table  table-bordered table-condensed table-hover">
		<thead>
			<tr>
				<th><input type="checkbox" id="checkAll"/></th>
				<th>门店</th>
				<th>客户姓名</th>
				<th>合同编号</th>
				<th>开户行</th>
				<th>银行账号</th>
				<th>申请返款金额</th>
				<th>返款状态</th>
				<th>返款结果</th>
				<th>原因</th>
				<th>申请日期</th>
				<th>返款日期</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${urgeListPage.list!=null && fn:length(urgeListPage.list)>0}">
				<c:set var="index" value="0" />
				<c:forEach items="${urgeListPage.list}" var="item">
					<c:set var="index" value="${index+1}" />
					<tr>
						<td><input type="checkbox" name="checkBoxItem"
							returnId='${item.id }' /></td>
						<td>${item.storesName}</td>
						<td>${item.customerName}</td>
						<td>${item.contractCode}</td>
						<td>${item.bankName}</td>
						<td>${item.bankAccount}</td>
						<td>${item.paybackBackAmount}</td>
						<td>${item.dictPayStatusLabel} </td>
						<td>${item.dictPayResultLabel}</td>
						<td>${item.remark}</td>
						<td><fmt:formatDate value="${item.backApplyPayTime}"
								type="date" /></td>
						<td><fmt:formatDate value="${item.backTime}" type="date" /></td>
						<td><button class="btn_edit" onclick="backHistory('${item.id }');" sid="${item.id }">历史</button></td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${ urgeListPage.list==null || fn:length(urgeListPage.list)==0}">
				<tr>
					<td colspan="18" style="text-align: center;">没有待办数据</td>
				</tr>
			</c:if>
		</tbody>
	</table>
    </div>
	<div class="pagination">${urgeListPage }</div>


	<!-- 导入返款结果弹框 -->
	<div class="modal fade" id="uploadAuditedModal" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"> 
			<div class="modal-dialog" role="document">
				    <div class="modal-content">
				    <form id="uploadAuditForm" role="form" enctype= "multipart/form-data" method="post" action="${ctx}/borrow/grant/urgeBackList/importBackList">
					<div class="modal-header">
					    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title" id="myModalLabel">上传回执结果</h4>
					</div>
					<div class="modal-body">
                      <input type='file' name="file" id="fileid">
                    </div>
					</form>
					<div class="modal-footer">
       					 <button class="btn btn-primary"  class="close" data-dismiss="modal" id="sureBtn">确认</button>
      					 <button class="btn btn-primary"  class="close" data-dismiss="modal" onclick = "closeModal('uploadAuditedModal')">取消</button>
   					</div>
			</div>
			</div>
	</div>

<!--退回弹框  -->
<div class="modal fade" id="backDiv" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
       <div class="modal-content">
       <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h4 class="modal-title" id="online">返款退回</h4>
	   </div>
       <div class="modal-body">
       <table class=" table4" cellpadding="0" cellspacing="0" border="0" width="100%">
             <tr>
				<td><span style="color: red;">*</span>退回原因：
					<p>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<textarea rows="" cols="" class="textarea_refuse"
									id="backReason"></textarea>
					</p>
				</td>
			</tr>
        </table>
     </div>
    <div class="modal-footer">
    <button class="btn btn-primary"  class="close" data-dismiss="modal" id="backSure">确认</button>
    <button class="btn btn-primary"  class="close" data-dismiss="modal" onclick="closeModal('backDiv')">取消</button>
    </div>
    </div>
	</div>
</div>
	<script type="text/javascript">

	  $("#tableList").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
		$('#tableList').bootstrapTable({
			method: 'get',
			cache: false,
			height: $(window).height()-260,
			
			pageSize: 20,
			pageNumber:1
		});
		$(window).resize(function () {
			$('#tableList').bootstrapTable('resetView');
		});
	</script>
</body>
</html>