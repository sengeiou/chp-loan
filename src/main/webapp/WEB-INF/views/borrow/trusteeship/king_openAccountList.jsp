<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script src="${context}/js/common.js" type="text/javascript"></script>
<script src="${context}/js/payback/ajaxfileupload.js" type="text/javascript" ></script>
<script src="${context}/js/trusteeship/kingOpenAccount.js?version=20161115" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		loan.getstorelsit("storeName", "storeOrgId");
		var msg = "${message}";
		if (msg != "" && msg != null) {
			art.dialog.alert(msg);
		}
	});
	
	function page(n, s) {
		if (n)
			$("#pageNo").val(n);
		if (s)
			$("#pageSize").val(s);
		$("#accountForm").attr("action", "${ctx }/borrow/king/openAccount/getTaskItems");
		$("#accountForm").submit();
		return false;
	}
	
	function validForm() {
		var form = document.forms[0];
		var flag = true;
		$(".number").each(function() {
			var tel = /^-?(?:\d+|\d{1,3}(?:,\d{3})+)?(?:\.\d+)?$/;
			if (!tel.test($(this).val())) {
				art.dialog.alert("请输入数字！");
				flag = false;
			}
		});
		if (flag == true) {
			form.submit();
		}
	}
</script>
<meta name="decorator" content="default" />
<title>金账户开户列表</title>
</head>
<body>
	<div class="control-group">
		<form:form id="accountForm"
			action="${ctx }/borrow/king/openAccount/getTaskItems"
			modelAttribute="loanQueryParam" method="post">
			<table class=" table1" cellpadding="0" cellspacing="0" border="0"
				width="100%">
				<input type="hidden" id="pageNo" name="pageNo" value="${workItems.pageNo}" />
	   			<input type="hidden" id="pageSize" name="pageSize" value="${workItems.pageSize}" />
				<tr>
					<td><label class="lab">客户姓名：</label>
					<form:input type="text" class="input_text178" path="customerName"></form:input></td>
					<td><label class="lab">合同编号：</label>
					<form:input type="text" class="input_text178" path="contractCode"></form:input></td>
					<td><label class="lab">证件号码：</label>
					<form:input type="text" class="input_text178" path="identityCode"></form:input></td>
				</tr>
				<tr>
					<td><label class="lab">借款类型：</label>
					<form:select class="select180" path="loanType">
							<form:option value="">请选择</form:option>
							<c:forEach items="${fns:getDictList('jk_loan_type')}"
								var="loan_type">
								<form:option value="${loan_type.value}">${loan_type.label}</form:option>
							</c:forEach>
						</form:select></td>
					<td><label class="lab">是否追加借：</label>
					<form:select class="select180" path="additionalFlag">
							<form:option value="">请选择</form:option>
							<c:forEach items="${fns:getDictList('yes_no')}" var="item">
								<form:option value="${item.value}">${item.label}</form:option>
							</c:forEach>
						</form:select></td>                
                <td><label class="lab">门店：</label> 
                	<form:input type="text" id="storeName" class="input_text178" 
                			path="storeName" readonly="true"></form:input> 
					<i id="selectStoresBtn"	class="icon-search" style="cursor: pointer;"></i> 
					<form:hidden path="storeOrgIds" id="storeOrgId" /></td>               
					
				</tr>
				<tr id="T1" style="display: none">		
					<td><label class="lab">开户状态：</label>
					<form:select class="select180" path="">
					    <form:option value="">请选择</form:option>
					    <form:option value="1">开户成功</form:option>
					    <form:option value="2">开户中</form:option>
					    <form:option value="3">未开户</form:option>
						</form:select></td>			
					<td colspan="2"><label class="lab">放款金额：</label> <form:input
							type="text" class="input_text70 number" path="lendingMoneyStart"></form:input>-<form:input
							type="text" class="input_text70 number" path="lendingMoneyEnd"></form:input></td>
				</tr>
			</table>
			<div class="tright pr30 pb5">
				<input type="button" class="btn btn-primary" onclick="validForm()"
					value="搜索">
				<button class="btn btn-primary" id="clearBtn">清除</button>
				<div style="float: left; margin-left: 45%; padding-top: 10px">
					<img src="${ctxStatic }/images/up.png" id="showMore"></img>
				</div>
			</div>
		</form:form>
	</div>
	<p class="mb5">
		<button class="btn btn-small" id="tryOpenAccount">自动开户</button>
		<button class="btn btn-small" id="exportAccount">导出excel</button>
		<button class="btn btn-small" id="exportProtocol">导出协议库</button>
		<button class="btn btn-small" id="refreshProtocol">更新协议库</button>
		<button class="btn btn-small" data-target="#backBatch_div"
			data-toggle="modal" id="batchBackBtn">批量退回</button>
		<button id="offLineShang" role="button" class="btn btn-small"
			data-target="#uploadAuditedModal" data-toggle="modal">上传回执结果</button>
	</p>
	<sys:columnCtrl pageToken="kingrr"></sys:columnCtrl>
	<div class="box5">
		<table id="tableList" class="table  table-bordered table-condensed table-hover ">
			<thead>
				<tr>
					<th><input type="checkbox" id="checkAll">全选 </input></th>
					<th>借款编号</th>
					<th>合同编号</th>
					<th>放款金额</th>
					<th>客户姓名</th>
					<th>身份证号</th>
					<th>手机号</th>
					<th>金账户用户名</th>
					<th>邮箱地址</th>
					<th>开户省市</th>
					<th>开户区县</th>
					<th>开户行行别</th>
					<th>支行名称</th>
					<th>户名</th>
					<th>帐号</th>
					<th>金账户开户状态</th>
					<th>失败原因</th>
					<th>协议库返回</th>
					<th>状态</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${ workItems!=null && fn:length(workItems.list)>0}">
					<c:set var="index" value="0" />
					<c:forEach items="${workItems.list}" var="item">
						<c:set var="index" value="${index+1}" />
						<tr>
							<td><input type="checkbox" name="checkBoxItem"
								loanCode="${item.loanCode}"  contractCode="${item.contractCode}" 
								value='${item.applyId}' index='${index}'  issplit="${item.issplit}"
								paramStr="${item.loanCode},${item.applyId}" />
							</td>
							<td>${item.loanCode}</td>
							<td>${item.contractCode}</td>
							<td>${item.lendingMoney}</td>
							<td>${item.customerName}</td>
							<td>${item.identityCode}</td>
							<td>${item.mobelPhone}</td>
							<td>${item.mobelPhone}</td>
							<td>${item.email}</td>
							<td>${item.kingBankProvinceName}</td>
							<td>${item.kingBankCityName}</td>
							<td>${item.depositBank}</td>
							<td>${item.bankBranchName}</td>
							<td>${item.custBankAccountName}</td>
							<td>${item.bankAccountNumber}</td>
							<td>${item.kingStatusLabel}</td>
							<td>${item.kingOpenRespReason}</td>
							<td>${item.kingProctolRespReason}</td>
							<td>${item.loanStatusName}</td>
							<td>		
								<c:if test="${item.issplit=='0'}">					
									<button class="btn_edit" data-target="#" data-toggle="modal"
										name="back" applyId='${item.applyId}'
										dealType='0'
										contractCode='${item.contractCode}'>退回</button>
								</c:if>	
								<button class="btn_edit" data-target="#" data-toggle="modal"
									name="openSuccess" applyId='${item.applyId}'
									dealType='0'
									contractCode='${item.contractCode}'>开户成功</button>
								<button class="btn_edit" data-target="#" data-toggle="modal"
									name="openFail" applyId='${item.applyId}' dealType='0' 
									contractCode='${item.contractCode}'>开户失败</button>
								<button class="btn_edit"
									onclick="showLoanHis('${item.applyId}')">历史</button>
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${ workItems==null || fn:length(workItems.list)==0}">
					<tr>
						<td colspan="18" style="text-align: center;">没有待办数据</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>
	<div id="fail_div" class="modal fade" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabe1l" aria-hidden="true" data-url="data">
		<div class="modal-dialog role="document"">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4>金账户开户失败</h4>
				</div>
				<div class="modal-body">
					<lable class="lab" style="vertical-align:top">失败原因：</lable>
					<textarea id="failReason" name="batchBackReason"
						class="textarea_big "></textarea>
					<input type="hidden" id="check">
				</div>
				<div class="modal-footer" style="text-align: right">
					<button id="openFailSure" class="btn btn-primary"
						data-dismiss="modal" aria-hidden="true">确定</button>
					<button class="btn btn-primary" data-dismiss="modal"
						aria-hidden="true">取消</button>
				</div>
			</div>
		</div>
	</div>
	
	<div id="backBatch_div" class="modal fade" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabe1l" aria-hidden="true" data-url="data">
		<div class="modal-dialog role="document"">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4>金账户开户退回</h4>
				</div>
				<div class="modal-body">
					<div>
						<lable class="lab">退回流程节点：</lable>
						 <select id="flowFlag">
							<!-- 
				 <c:forEach items="${fns:getDictList('jk_chk_back_reason')}" var="card_type">
							<option value="${card_type.value}">${card_type.label}</option>
				 </c:forEach>
				  -->
							<option value="CTR_AUDIT">合同审核</option>
						</select> <input type="hidden" id="check">
					</div>
					<div>
						<lable class="lab" style="vertical-align:top">原因：</lable>
						<textarea id="batchBackReason" name="batchBackReason"
							class="textarea_big "></textarea>
					</div>
				</div>
				<div class="modal-footer" style="text-align: right">
					<button id="backBatchSure" class="btn btn-primary"
						data-dismiss="modal" aria-hidden="true">确定</button>
					<button class="btn btn-primary" data-dismiss="modal"
						aria-hidden="true">取消</button>
				</div>
			</div>
		</div>
	</div>
	<div id="back_reason" class="modal fade" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabe1l" aria-hidden="true" data-url="data">
		<label class="lab">退回原因：</label><label id="reason">${backReason }</label>
		<div style="text-align: right">
			<button id="backSure" class="btn" data-dismiss="modal"
				aria-hidden="true">确定</button>
		</div>
	</div>
	<div class="modal fade" id="uploadAuditedModal" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<form id="uploadAuditForm" role="form" enctype="multipart/form-data"
					method="post" action="${ctx}/borrow/king/openAccount/importExl">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">上传回执结果</h4>
					</div>
					<div class="modal-body">
						<input type='file' name="file" id="fileid">
					</div>
				</form>
				<div class="modal-footer">
					<button class="btn btn-primary" class="close" data-dismiss="modal"
						id="sureBtn">确认</button>
					<button class="btn btn-primary" class="close" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>
	<div class="pagination">${workItems}</div>
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