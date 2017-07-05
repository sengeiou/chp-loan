<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript" type="text/javascript"
	src='${context}/js/common.js'></script>
<script src="${context}/js/channel/jyj/jyjgrantAudit.js" type="text/javascript"></script>
<script type="text/javascript"
	src="${context}/js/payback/ajaxfileupload.js"></script>
<script type="text/javascript"
	src="${context}/js/pageinit/areaselect.js"></script>
<meta name="decorator" content="default" />
<style type="text/css">
  .trRed {
color:red;
}
</style>
<script type="text/javascript">
	$(document).ready(
			function() {
				loan.initCity("addrProvice", "addrCity", null);
				areaselect.initCity("addrProvice", "addrCity", null, $(
						"#addrCity").val(), null);
				loan.getstorelsit("storeName", "storeOrgId");
				loan.getOpenBank('中间人开户行','cautionerDepositBank',null);
			});
			var msg = "${message}";
			if (msg != "" && msg != null) {
				art.dialog.alert(msg);
			};	
			
			// 
			function checkAllBtn(){
				var curMoney = 0.0;
				var curNum = 0;
				if($('#checkAll').attr('checked')=='checked'|| $('#checkAll').attr('checked'))
				{
					$(":input[name='checkBoxItem']").each(function() {
					  $(this).attr("checked",'true');
					  if($(this).attr("lendingMoney")!=''){
						  curMoney = parseFloat($(this).attr("lendingMoney"))+curMoney;
					  }
					  curNum=curNum+1;
					});
					$('#totalItem').text(curNum);
					$('#numHid').val(curNum);
					$('#grantMoneyText').text(fmoney(curMoney, 2));
					$('#grantMoneyHid').val(curMoney);
				}else{
					$(":input[name='checkBoxItem']").each(function() {
					  $(this).removeAttr("checked");
					});
					$('#grantMoneyHid').val(0.00);
					$('#numHid').val(0);
					$('#totalItem').text($("#hiddenNum").val());
					$('#grantMoneyText').text(fmoney($("#hiddenTotalGrant").val(), 2));
				}
		   }
			
			function page(n, s) {
					if (n)
						$("#pageNo").val(n);
					if (s)
						$("#pageSize").val(s);
					$("#grantAuditForm").attr("action", "${ctx}/channel/jyj/grantAudit/grantAuditItem");
					$("#grantAuditForm").submit();
					return false;
			}
			
			function setContractCode(contractCode){
				$("#contractCode").val(contractCode);
				$("#auditSureModal").modal('show');
			}
</script>
<title>简易借首次放款审核</title>
</head>
<body>
	<div class="control-group">
		<form:form
			action="${ctx }/channel/jyj/grantAudit/grantAuditItem"
			modelAttribute="LoanFlowQueryParam" id="grantAuditForm">
			<table class="table1" cellpadding="0" cellspacing="0" border="0"
				width="100%">
				<input id="pageNo" name="pageNo" type="hidden"　value="${workItems.pageNo}" />
				<input id="pageSize" name="pageSize" type="hidden"　value="${workItems.pageSize}" />
				<tr>
					<td><label class="lab">客户姓名：</label> <form:input type="text"
							class="input_text178" path="customerName"></form:input></td>
					<td><label class="lab">合同编号：</label> <form:input type="text"
							class="input_text178" path="contractCode"></form:input></td>
					<td><label class="lab">管辖城市：</label> <form:select
					class="select180" style="width:110px" path="provinceCode"
					id="addrProvice">
					<form:option value="">请选择</form:option>
					<c:forEach var="item" items="${provinceList}" varStatus="status">
						<form:option value="${item.areaCode }">${item.areaName}</form:option>
					</c:forEach>
				</form:select>-<form:select class="select180" style="width:110px"
					path="cityCode" id="addrCity">
					<form:option value="">请选择</form:option>
				</form:select></td>
				</tr>
				<tr>
					<td><label class="lab">门店：</label> <form:input type="text"
							id="storeName" class="input_text178" path="storeName"
							readonly="true"></form:input> <i id="selectStoresBtn"
						class="icon-search" style="cursor: pointer;"></i> <form:hidden
							path="storeOrgIds" id="storeOrgId" /></td>
					<td><label class="lab">是否追加借：</label> <form:select
					class="select180" path="additionalFlag">
					<form:option value="">请选择</form:option>
					<c:forEach items="${fns:getDictList('jk_add_flag')}"
						var="card_type">
						<form:option value="${card_type.value}">${card_type.label}</form:option>
					</c:forEach>
					</form:select></td>
					<td><label class="lab">放款时间：</label> <input
						id="lendingTimeStart" name="lendingTimeStart" type="text"
						class="Wdate input_text70" size="10"
						value="<fmt:formatDate value='${LoanFlowQueryParam.lendingTimeStart}' pattern='yyyy-MM-dd'/>"
						onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'lendingTimeEnd\')}'})"
						style="cursor: pointer"></input>-<input id="lendingTimeEnd"
						name="lendingTimeEnd" type="text" class="Wdate input_text70"
						size="10"
						value="<fmt:formatDate value='${LoanFlowQueryParam.lendingTimeEnd}' pattern='yyyy-MM-dd'/>"
						onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'lendingTimeStart\')}'})"
						style="cursor: pointer"></input></td>
				</tr>
				<tr id="T1" style="display: none">
					<td><label class="lab">放款批次：</label> <form:select
							class="select180" path="grantBatchCode">
							<form:option value="">请选择</form:option>
							<c:forEach items="${grantPchList}" var="card_type">
								<form:option value="${card_type.grantBatch}">${card_type.grantBatch}</form:option>
							</c:forEach>
						</form:select></td>
						<td>
						<label class="lab">放款银行：</label> <form:input type="text" id="cautionerDepositBank" readonly="true" class="input_text178" path="cautionerDepositBanks"></form:input>
				    	<i id="chooseOpenBankBtn"
						class="icon-search" style="cursor: pointer;"></i></td>
					<td><label class="lab">是否电销：</label> <form:select
							class="select180" path="telesalesFlag">
							<form:option value="">请选择</form:option>
							<c:forEach items="${fns:getDictList('jk_telemarketing')}"
								var="card_type">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
							</c:forEach>
						</form:select></td>
				</tr>
				<tr id="T2" style="display: none">
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
					<td><label class="lab">费用收取状态：</label> <form:select
							class="select180" path="receiptResult">
							<form:option value="">请选择</form:option>
							<c:forEach items="${fns:getDictList('jk_urge_counteroffer_result')}"
								var="card_type">
								<c:if test="${card_type.label=='划扣成功' or card_type.label=='划扣失败' or card_type.label=='查账成功' or card_type.label=='查账失败'}">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
								</c:if>
							</c:forEach>
						</form:select></td>
				</tr>
				<tr id="T3" style="display: none">
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
				<input class="btn btn-primary" type="submit" value="搜索"
					id="searchBtn"></input>
				<button class="btn btn-primary" id="clearBtn">清除</button>
				<div style="float: left; margin-left: 45%; padding-top: 10px">
					<img src="../../../../static/images/up.png" id="showMore"></img>
				</div>
			</div>
		</form:form>
	</div>
	<p class="mb5">
		<input type="button" class="btn btn-small" id="resultSure" value="结果确认" data-toggle="modal" data-target="#auditSureModal"/>
		<button class="btn btn-small" id="daoBtn">导出excel</button>
		<input type="hidden" id="hiddenTotalGrant" value="${totalGrantMoney}">
		<input type="hidden" id="hiddenNum" value="${totalItem}">
		&nbsp;&nbsp;
		 <label  style="color:red;">放款总金额：
		    <span id="grantMoneyText">
		  	 <fmt:formatNumber value='${totalGrantMoney}' pattern="#,##0.00" />
		  	</span>
		  	
		  	 <input type="hidden" id="numHid" value="0"/>
		  	 <input type="hidden" id="grantMoneyHid" value="0.00"/>
		  	 
		 </label>
		 <label class="lab" style="color:red;" >放款总笔数：
		   <span id="totalItem">
		     ${totalItem}
		     </span>
		   &nbsp;笔
		 </label>
	</p>
	<sys:columnCtrl pageToken="gauditList"></sys:columnCtrl>
	<div class="box5" style="overflow: auto; width: 100%;">
		<table id="tableList"
			class="table  table-bordered table-condensed table-hover ">
			<thead>
				<tr>
					<th><input type="checkbox" id="checkAll" onclick="checkAllBtn()"/></th>
					<th>合同编号</th>
					<th>客户姓名</th>
					<th>共借人</th>
					<th>门店名称</th>
					<th>账户名字</th>
					<th>借款产品</th>
					<th>合同金额</th>
					<th>首次放款金额</th>
					<th>划扣金额</th>
					<th>批复期限(月)</th>
					<th>放款账号</th>
					<th>开户行</th>
					<th>账户姓名</th>
					<th>放款途径</th>
					<th>费用收取状态</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${ workItems!=null && fn:length(workItems.list)>0}">
					<c:set var="index" value="0" />
					<c:forEach items="${workItems.list}" var="item">
						<c:set var="index" value="${index+1}" />
						<tr <c:if test="${item.receiptResult=='划扣失败'||item.receiptResult=='查账失败'}">
                    	class='trRed'
               			</c:if>>
							<td><input type="checkbox" name="checkBoxItem" 
								contractCode="${item.contractCode }" value='${item.applyId}' lendingMoney='${item.lendingMoney}'/>
							</td>
							<td>${item.contractCode}</td>
							<td>${item.customerName}</td>
							<td><c:if
									test="${item.coborrowerName!=null && fn:contains(item.coborrowerName,'null')==false}">
                    			  ${item.coborrowerName}
            				</c:if></td>
							<td>${item.storeName}</td>
							<td>${item.custBankAccountName}</td>
							<td>${item.replyProductName}</td>
							<td><fmt:formatNumber value='${item.contractMoney}'
									pattern="#,#00.00" /></td>
							<td><fmt:formatNumber value='${item.lendingMoney}'
									pattern="#,#00.00" /></td>
							<td><fmt:formatNumber value='${item.urgeServiceFee}'
									pattern="#,#00.00" /></td>
							<td>${item.replyMonth}</td>
							<td>${item.lendingAccount}</td>
							<td>${item.depositBank}</td>
							<td>${item.custBankAccountName}</td>
							<td>金信</td>
							<td>${item.receiptResult}</td>

							<td><button class="btn_edit" 
									 onclick="setContractCode('${item.contractCode}');">办理</button>
								<button class="btn_edit"
									onclick="showHisByLoanCode('${item.loanCode}')">历史</button>
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
	<input type="hidden" id="contractCode"></input>
	<!-- 手动确认放款审核 -->
	<div class="modal fade" id="auditSureModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title">结果确认</h4>
				</div>
				<form id="auditSureForm" action="${ctx}/channel/jyj/grantAudit/grantAuditSure" method="post">
					<input type="hidden" id="blackLoanCode" name="contract.loanCode">
					<div class="modal-body">
						<table id="customerTab">
							<tr>
								<td>
									<span class="red">*</span><label class="lab"> 结果确认：</label> 
									<input type="radio" style="margin-left:5px;margin-top:4px" name="grantAuditResult" value="1" label="放款成功"/>
									放款成功
									<input type="radio" style="margin-left:5px;margin-top:4px" name="grantAuditResult" value="0" label="放款失败"/>
									放款失败
								</td>
							</tr>
							<tr>
								<td style="display:none" id="backReason">
								<span>退回原因：</span>
								<select class="select180" id="backResult" name="emergencyRemark"><option value="">请选择</option>
										<c:forEach items="${fns:getDictList('jk_chk_back_reason')}" var="card_type">
											<option value="${card_type.value}">${card_type.label}</option>
									</c:forEach>
								</select>
								</td>
							</tr>
						</table>
					</div>
					<div class="modal-footer">
						<a type="button" class="btn btn-primary" data-dismiss="modal" id="auditSureBtn">确定</a>
						<a type="button" class="btn btn-primary" data-dismiss="modal">关闭</a>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- 手动确认放款审核end -->
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