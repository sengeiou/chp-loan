<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script src="${context}/js/common.js" type="text/javascript"></script>
<script type="text/javascript"
	src="${context}/js/payback/ajaxfileupload.js"></script>
<script src="${context}/js/channel/jyj/jyjgrant.js" type="text/javascript"></script>
<meta name="decorator" content="default" />
<title>简易借首次放款列表</title>
<style type="text/css">
  .trRed {
color:red;
}
</style>
<script type="text/javascript">
$(document).ready(
		function() {
			loan.getstorelsit("storeName", "storeOrgId");
		});
	
	var msg = "${message}";
	if (msg != "" && msg != null) {
		art.dialog.alert(msg);
	};
	
	function page(n, s) {
		if (n)
			$("#pageNo").val(n);
		if (s)
			$("#pageSize").val(s);
		$("#grantForm").attr("action", "${ctx}/channel/jyj/grantItem");
		$("#grantForm").submit();
		return false;
	}
</script>
</head>
<body>
	<div class="control-group">
		<form:form id="grantForm"
			action="${ctx}/channel/jyj/grantItem"
			modelAttribute="LoanFlowQueryParam" method="post">
			<table class=" table1" cellpadding="0" cellspacing="0" border="0"
				width="100%">
				<input type="hidden" id="pageNo" name="pageNo" value="${workItems.pageNo}" />
	   			<input type="hidden" id="pageSize" name="pageSize" value="${workItems.pageSize}" />
				<tr>
					<td><label class="lab">客户姓名：</label> <form:input type="text"
							class="input_text178" path="customerName"></form:input></td>
					<td><label class="lab">合同编号：</label> <form:input type="text"
							class="input_text178" path="contractCode"></form:input></td>
					<td><label class="lab">证件号码：</label> <form:input type="text"
							class="input_text178" path="identityCode"></form:input></td>
				</tr>
				<tr>
					<td><label class="lab">门店：</label> <form:input type="text"
							id="storeName" class="input_text178" path="storeName"
							readonly="true"></form:input> <i id="selectStoresBtn"
						class="icon-search" style="cursor: pointer;"></i> <form:hidden
							path="storeOrgIds" id="storeOrgId" /></td>
					<td><label class="lab">是否电销：</label> <form:select
					class="select180" path="telesalesFlag">
					<form:option value="">请选择</form:option>
					<c:forEach items="${fns:getDictList('jk_telemarketing')}"
						var="card_type">
						<form:option value="${card_type.value}">${card_type.label}</form:option>
					</c:forEach>
				</form:select></td>
				<td><label class="lab">是否追加借：</label> <form:select
					class="select180" path="additionalFlag">
					<form:option value="">请选择</form:option>
					<c:forEach items="${fns:getDictList('jk_add_flag')}"
						var="card_type">
						<form:option value="${card_type.value}">${card_type.label}</form:option>
					</c:forEach>
				</form:select></td>
				</tr>
				<tr id="T1" style="display: none">
					<td><label class="lab">提交时间：</label> 
					<input  name="submissionDateStart"  id="submissionDateStart"  
                  value="<fmt:formatDate value='${LoanFlowQueryParam.submissionDateStart}' pattern='yyyy-MM-dd'/>"
                     type="text" class="Wdate input_text70" size="10"  
                                        onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'submissionDateEnd\')}'})" style="cursor: pointer" ></input>-<input  name="submissionDateEnd" 
                  value="<fmt:formatDate value='${LoanFlowQueryParam.submissionDateEnd}' pattern='yyyy-MM-dd'/>"
                     type="text" class="Wdate input_text70" size="10" id="submissionDateEnd"  
                                        onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'submissionDateStart\')}'})" style="cursor: pointer" ></input></td>
					<td><label class="lab">提交批次：</label> <form:input type="text"
							class="input_text178" path="submissionBatch"></form:input></td>
						<td><label class="lab">是否加急：</label> <form:select
						class="select180" path="urgentFlag">
						<form:option value="">请选择</form:option>
						<c:forEach items="${fns:getDictList('jk_urgent_flag')}"
							var="card_type">
							<form:option value="${card_type.value}">${card_type.label}</form:option>
						</c:forEach>
					</form:select></td>
				</tr>
			</table>
			<div class="tright pr30 pb5">
				<input type="button" class="btn btn-primary"
					onclick="document.forms[0].submit();" value="搜索">
				<button class="btn btn-primary" id="clearBtn">清除</button>
				<div style="float: left; margin-left: 45%; padding-top: 10px">
					<img src="${ctxStatic }/images/up.png" id="showMore"></img>
				</div>
			</div>
	</div>
	</form:form>
	<p class="mb5">
		<button class="btn btn-small" id="offLineDao" role="button" data-toggle="modal">导出Excel</button>
		<button id="offLineShang" role="button" class="btn btn-small"
			data-target="#uploadAuditedModal" data-toggle="modal">上传银行回执</button>
		<button id="handSureGrant" class="btn btn-small" data-target="#hand_sure_grant" data-toggle="modal">手动确认放款</button>
		<button id="batchBackBtn" role="button" class="btn btn-small"
			data-toggle="modal">批量退回</button>
		<button class="btn btn-small" id="offLineBF">导出宝付</button>
		<input type="hidden" id="hiddenTotalGrant" value="${totalGrantMoney}">
		<input type="hidden" id="hiddenNum" value="${totalItem}">
		<input type="hidden" id="hiddenTotalFail" value="${totalGrantFailAmount}">
		 <label class="lab" style="color:red;" >放款总笔数：
		   <span id="totalItem">
		     ${totalItem}
		     </span>
		   &nbsp;
		 </label>
		 <label  style="color:red;">放款总金额：
		    <span id="grantMoneyText">
		  	 <fmt:formatNumber value='${totalGrantMoney}' pattern="#,##0.00" />
		  	</span>
		  	
		  	 <input type="hidden" id="numHid" value="0"/>
		  	 <input type="hidden" id="grantMoneyHid" value="0.00"/>
		  	 <input type="hidden" id="grantFaileMoneyHid" value="0.00"/>
		  	 
		 </label>
	</p>
	<sys:columnCtrl pageToken="grantList"></sys:columnCtrl>
	<div class="box5" style="overflow: auto; width: 100%;">
		<table id="tableList"
			class="table  table-bordered table-condensed table-hover ">
			<thead>
				<tr>
					<th><input type="checkbox" id="checkAll" onclick = "checkAllClick()"/></th>
					<th>合同编号</th>
					<th>客户姓名</th>
					<th>证件号码</th>
					<th>门店名称</th>
					<th>账户名字</th>
					<th>借款产品</th>
					<th>合同金额</th>
					<th>首次放款金额</th>
					<th>划扣金额</th>
					<th>批借期限(月)</th>
					<th>放款途径</th>
					<th>开户行</th>
					<th>是否电销</th>
					<th>状态</th>
					<th>渠道</th>
					<th>加急标识</th>
					<th>提交批次</th>
					<th>提交日期</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${ workItems!=null && fn:length(workItems.list)>0}">
					<c:set var="index" value="0" />
					<c:forEach items="${workItems.list}" var="item">
						<c:set var="index" value="${index+1}" />
						<tr <c:if test="${item.frozenFlag=='1'}">
                    		class='trRed'
               				</c:if>>
							<td><input type="checkbox" name="checkBoxItem"
								contractCode='${item.contractCode }' value='${item.applyId}'
								index='${index}'  issplit="${item.issplit}" 
								lendingMoney='${item.lendingMoney}' /></td>
							<td>${item.contractCode}</td>
							<td>${item.customerName}<input type="hidden"
								value='${item.applyId}' name="sendapplyid"></td>
							<td>${item.identityCode}</td>
							<td>${item.storeName}</td>
							<td>${item.custBankAccountName}</td>
							<td>${item.replyProductName}</td>
							<td><fmt:formatNumber value='${item.contractMoney}'
									pattern="#,##0.00" /></td>
							<td><fmt:formatNumber value='${item.lendingMoney}'
									pattern="#,##0.00" /></td>
							<td><fmt:formatNumber value='${item.urgeServiceFee}'
							pattern="#,##0.00" /></td>
							<td>${item.replyMonth}</td>
							<td>金信</td>
							<td>${item.cautionerDepositBank}</td>
							<td><c:if test="${item.telesalesFlag=='1'}">
									<span>是</span>
								</c:if></td>
							<td>${item.loanStatusName}</td>
							<td>金信</td>
							<td><c:if test="${item.urgentFlag=='1'}">
									<span style="color: red">加急</span>
								</c:if></td>
							<td>${item.submissionBatch}</td>
							<td>${item.submissionDate}</td>
							<td><button class="btn_edit"
									onclick="showLoanHis('${item.applyId}')">历史</button>
								<c:if test="${item.issplit=='0'}">
									<button class="btn_edit" data-target="#backBatch_div"
										data-toggle="modal" name="back"
										dealType='0' applyId='${item.applyId}'
										contractCode='${item.contractCode}'>退回</button>
								</c:if>		
								</td>
										
						</tr>

					</c:forEach>
				</c:if>
				<c:if test="${ workItems==null || fn:length(workItems.list)==0}">
					<tr>
						<td colspan="20" style="text-align: center;">没有待办数据</td>
					</tr>
				</c:if>
			</tbody>
		</table>
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
				</div>
				<div class="modal-body">
					 <table><tr><td>
					 <label class="lab">退回原因：</label>
						<select id="backBatchReason" class="select180"  onchange="javascript:showRemark('backBatchReason','backBatchReasonTr');">
							<option value="">请选择</option>
							<c:forEach items="${fns:getDictList('jk_chk_back_reason')}"
								var="card_type">
								<option value="${card_type.value}">${card_type.label}</option>
							</c:forEach>
						</select>
					</td>
					</tr>
				    <tr id="backBatchReasonTr"  style="display:none">
				         <td> 
				            <label class="lab">备注</label>
                    	    <span class="red">*</span>
                    	    <textarea id="backBatchRemark" rows="20" cols="30" style='font-family:"Microsoft YaHei";' ></textarea>
				         </td>
				    </tr>
				</table>	
				</div>
				<div class="modal-footer" style="text-align: right">
					<button id="backBatchSure" class="btn btn-primary"
						data-dismiss="modal" aria-hidden="true" onclick = "backSure()">确定</button>
					<button class="btn btn-primary" data-dismiss="modal"
						aria-hidden="true">取消</button>
				</div>
			</div>
		</div>
	</div>
	
	<input type="hidden" name="contractCodeHidden" id="contractCodeHidden" />

	<div id="back_reason" class="modal fade" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabe1l" aria-hidden="true" data-url="data">
		<label class="lab">退回原因：</label><label id="reason">${backReason }</label>
		<div style="text-align: right">
			<button id="backSure" class="btn" data-dismiss="modal"
				aria-hidden="true">确定</button>
		</div>
	</div>
	
	
	<div class="modal fade" id="uploadRecModal" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<form id="uploadAuditForm" role="form" enctype="multipart/form-data"
					method="post" action="${ctx}/borrow/grant/grantDeal/uploadRec">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">上传回执结果</h4>
					</div>
					<div class="modal-body">
						<input type='file' name="fl" id="fl">
					</div>
				</form>
				<div class="modal-footer">
					<button class="btn btn-primary" class="close" data-dismiss="modal"
						id="sureRecBtn">确认</button>
					<button class="btn btn-primary" class="close" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>

	</div>
		<!-- 手动确认放款Start -->
		<div  id="hand_sure_grant" class="modal fade" tabindex="-1" role="dialog" 
		  aria-labelledby="myModalLabe1l" aria-hidden="true">
		  <div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title">放款批次</h4>
				</div>
				<div class="modal-body">
				<label class="lab">放款批次：</label>
				<input type="text" class="input_text178" id="handSureCon"></input>
				</div>
				<div class="modal-footer" style="text-align:right">
					<button id="sureGrant" class="btn btn-primary" data-dismiss="modal" aria-hidden="true">确定</button>
					<button class="btn btn-primary" data-dismiss="modal" aria-hidden="true">取消</button>
				</div>
			</div>
			</div>
		</div>
		<!-- 手动确认放款End -->

	<div class="modal fade" id="uploadAuditedModal" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<form id="uploadAuditForm" role="form" enctype="multipart/form-data"
					method="post" action="${ctx}/channel/jyj/importResult">
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
	
	<div class="modal fade" id="isBreakUp" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">放款金额是否拆分</h4>
					</div>
					<div class="modal-body">
						<table class=" table1" cellpadding="0" cellspacing="0" border="0"
							width="100%">
							<tr>
								<td><label class="lab"><span id="templateTypeId"
										style="color: red">*</span>放款金额是否拆分：</label> <span> <input
										type="radio" name="isBreak" label="按五万元拆分" value="0" />按五万元拆分 <input
										type="radio" name="isBreak" label="不拆分" value="1" checked="checked"/>不拆分
										<input type="hidden" id="loanGrant"/>
										<input type="hidden" id="idVal"/>
								   </span>
							   </td>
							</tr>
						</table>
					</div>
					<div class="modal-footer">
						<button class="btn btn-primary" class="close"
							 id="isBreakUpBtn">确认</button>
						<button class="btn btn-primary" class="close" data-dismiss="modal" onclick="closeModal('isBreakUp')">取消</button>
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