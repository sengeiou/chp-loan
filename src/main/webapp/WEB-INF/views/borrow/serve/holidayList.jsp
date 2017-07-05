<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default"/>
<script type="text/javascript" src="${context}/js/serve/holidayList.js"></script>
<script type="text/javascript" src="${context}/js/common.js"></script>
</head>
<body>
<input id="pageCount" type="hidden" value="${page.count}"/>
	<div class = "control-group">
		<form id="waitSendForm" action="${ctx }/borrow/serve/emailServe/getHolidayList" method="post">
			<input id="pageNo" name="pageNo" type="hidden"	value="${page.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" />
			<input id="ids" type="hidden" name="ids"/>
			<input id="exportType" type="hidden" name="exportType" value="4"/>
			
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">客户姓名：</label>
					<input type="text" id="customerName" class="input-medium" name="customerName" value="${sendEmail.customerName }"/></td>
					<td><label class="lab">合同编号：</label>
					    <input type="text" id="contractCode" class="input-medium" name="contractCode" value="${sendEmail.contractCode }"/></td>
					<td>
						<label class="lab">门店：</label> 
						<input readOnly type="text" id="txtStore" name="storeName" maxlength="20" class="txt date input_text178" value="${sendEmail.storeName}"/> 
						<i id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
						<input type="hidden" id="hdStore" name="storeId" value="${sendEmail.storeId}"/>
					</td>
				</tr>
				<tr>
					<td>
						<label class="lab">借款状态：</label>
						<select class="select180" name="loanStatus" id="loanStatus">
							<option value="">请选择</option>
							<c:forEach items="${fns:getDictList('jk_loan_status')}" var="item">
								<c:if test="${item.value!=89 && item.value!=90 }">
									<option value="${item.value}" <c:if test="${item.value==sendEmail.loanStatus }">selected</c:if>>${item.label}</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
					<td>
						<label class="lab">邮箱：</label>
						<input type="text" id="customerEmail" class="input-medium" name="customerEmail" value="${sendEmail.customerEmail }"/></td>
					</td>
					<td>
						<label class="lab">是否无纸化：</label>
						<select class="select180" id="paperlessFlag" name="paperlessFlag" >
							<option value="">全部</option>
							<option value="1" <c:if test="${sendEmail.paperlessFlag==1 }">selected</c:if>>是</option>
							<option value="0" <c:if test="${sendEmail.paperlessFlag==0 }">selected</c:if>>否</option>
						</select>
					</td>
				</tr>
				
			</table>
		</form>
		<div class="tright pr30 pb5">
			<input type="button" class="btn btn-primary" id="btn-submit" value="搜索"> 
			<input type="button" class="btn btn-primary" id="clearBtn" value="清除">
		</div>
	</div>
	
	<p class="mb5">
			<button class="btn btn-small" onclick="exportExcel()">导出excel</button>&nbsp;
			<button class="btn btn-small" onclick="send(1,0,'',3)">发送</button>
	</p>
	<div>
		<table id="tableList" class="table table-condensed table-striped">
			<thead>
				<tr>
					<th><input type="checkbox" id="checkAllItem" onclick="selectAll()"/></th>
					<th>序号</th>
					<th>客户姓名</th>
					<th>合同编号</th>
					<th>门店名称</th>
					<th>借款状态</th>
					<th>放款金额</th>
					<th>月还金额</th>
					<th>合同金额</th>
					<th>还款日</th>
					<th>是否无纸化</th>
					<th>邮箱</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${page.list != null && fn:length(page.list)>0}">
					<c:set var="index" value="0"/>
					<c:forEach items="${page.list}" var="item">
						<c:set var="index" value="${index+1}"/>
						<tr>
							<td><input type="checkbox" name="checkItem" value="${item.contractCode}"></td>
							<td>${index}</td>
							<td>${item.customerName}</td>
							<td>${item.contractCode}</td>
							<td>${item.storeName}</td>
							<td>${item.loanStatusLabel }</td>
							<td><fmt:formatNumber value="${item.auditAmount}" pattern="#,##0.00" /></td>
							<td><fmt:formatNumber value="${item.contractMonthRepayAmount}" pattern="#,##0.00" /></td>
							<td><fmt:formatNumber value="${item.contractAmount}" pattern="#,##0.00" /></td>
							<td>${item.paybackDay}</td>
							<td><c:if test="${item.paperlessFlag==0}">否</c:if>
								<c:if test="${item.paperlessFlag==1}">是</c:if>
							</td>
							<td>${item.customerEmail}</td>
							<td>
							    <button class="btn_edit" onclick="send(1,1,'${item.contractCode}',3)">发送</button>
								<button class="btn_edit" onclick="showEmailOpeByContractCode('${item.contractCode}')">历史</button>
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${page.list==null || fn:length(page.list)==0}">
					<tr>
						<td colspan="13" style="text-align: center;">没有数据</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>
	
	<c:if test="${page.list != null && fn:length(page.list)>0}">
		<div class="pagination">${page }</div>
	</c:if>
	
	<!-- 选择邮件模板弹出框 -->
	<div id="emailDialog" class=" pb5 pt10 pr30 title ohide" style="display:none">
		<form id="holidayDialog" action="${ctx }/borrow/serve/emailServe/updateSendEmailStatus">
		<input type="hidden" name="ids" id="sendIds"/> 
		<input type="hidden" name="sendEmailStatus" id="emailStatus"/>
		<input type="hidden" name="idType" id="idType"/>
		<input type="hidden" name="customerName" id="customerNameDialog"/>
		<input type="hidden" name="contractCode" id="contractCodeDialog"/>
		<input type="hidden" name="loanStatus" id="loanStatusDialog"/>
		<input type="hidden" name="storeId" id="storeIdDialog"/>
		<input type="hidden" name="customerEmail" id="customerEmailDialog"/>
		<input type="hidden" name="paperlessFlag" id="paperlessFlagDialog" />
		<table class="table table-bordered table-condensed table-hover ">
			<tbody>
				<tr>
					<td>
						<label class="lab">邮件模板：</label>
						<select class="select180" id="emailType" name="emailType">
							<option value="">请选择</option>
							<c:forEach items="${fns:getDictList('jk_email_type')}" var="item">
								<c:if test="${item.value==2 || item.value==3 }">
									<option value="${item.value}">${item.label}</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>
					<label class="lab">放假日期：</label>
					<input id="startTime" name="startTime" type="text" readonly="readonly" maxlength="40"
								class="input-mini Wdate" 
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" />
						至
					<input id="endTime" name="endTime" type="text" readonly="readonly" maxlength="40"
								class="input-mini Wdate" 
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" />
				</td>
				</tr>
			</tbody>
		</table>
		</form>
	</div>
	
	 <script type="text/javascript">

	  $("#tableList").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
		$('#tableList').bootstrapTable({
			method: 'get',
			cache: false,
			height: $(window).height()-230,
			pageSize: 20,
			pageNumber:1
		});
		$(window).resize(function () {
			$('#tableList').bootstrapTable('resetView');
		});
	</script>
</body>
</html>