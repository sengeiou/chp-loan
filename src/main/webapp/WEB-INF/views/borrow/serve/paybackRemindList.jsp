<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default"/>
<script type="text/javascript" src="${context}/js/serve/paybackRemindList.js"></script>
<script type="text/javascript" src="${context}/js/common.js"></script>
</head>
<body>
<input id="pageCount" type="hidden" value="${page.count }"/>
	<div class = "control-group">
		<form id="waitSendForm" action="${ctx }/borrow/serve/emailServe/paybackRemindList" method="post">
			<input id="pageNo" name="pageNo" type="hidden"	value="${page.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" />
			<input id="ids" type="hidden" name="ids"/>
			<input id="exportType" type="hidden" name="exportType" value="1"/>
			<input id="sendEmailStatus" type="hidden" name="sendEmailStatus"/>
			<input id="idType" type="hidden" name="idType"/>
			<input id="emailType" type="hidden" name="emailType" value="1"/>
			
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">客户姓名：</label>
					<input type="text" class="input-medium" name="customerName" value="${sendEmail.customerName }"/></td>
					<td><label class="lab">合同编号：</label>
					    <input type="text" class="input-medium" name="contractCode" value="${sendEmail.contractCode }"/></td>
					<td>
						<label class="lab">门店：</label> 
						<input readOnly type="text" id="txtStore" name="storeName" maxlength="20" class="txt date input_text178" value="${sendEmail.storeName}"/> 
						<i id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
						<input type="hidden" id="hdStore" name="storeId" value="${sendEmail.storeId}"/>
					</td>
					
				</tr>
				<tr>
					<%-- <td>
						<label class="lab">发送状态：</label>
						<select class="select180" name="sendEmailStatus" >
							<option value="">全部</option>
							<option value="0" <c:if test="${sendEmail.sendEmailStatus==0 }">selected</c:if>>未发送</option>
							<option value="3" <c:if test="${sendEmail.sendEmailStatus==3 }">selected</c:if>>发送失败</option>
						</select>
					</td> --%>
					<td>
						<label class="lab">邮箱：</label>
						<input type="text" class="input-medium" name="customerEmail" value="${sendEmail.customerEmail }"/></td>
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
			<button class="btn btn-small" onclick="send(1,0,'',1)">发送</button>
			<button class="btn btn-small" onclick="send(2,0,'',1)">作废</button>
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
					<th>邮箱</th>
					<th>发送状态</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${page.list != null && fn:length(page.list)>0}">
					<c:set var="index" value="0"/>
					<c:forEach items="${page.list}" var="item">
						<c:set var="index" value="${index+1}"/>
						<tr>
							<td><input type="checkbox" name="checkItem" value="${item.paybackMonthId}"></td>
							<td>${index}</td>
							<td>${item.customerName}</td>
							<td>${item.contractCode}</td>
							<td>${item.storeName}</td>
							<td>${item.loanStatusLabel }</td>
							<td><fmt:formatNumber value="${item.auditAmount}" pattern="#,##0.00" /></td>
							<td><fmt:formatNumber value="${item.contractMonthRepayAmount}" pattern="#,##0.00" /></td>
							<td><fmt:formatNumber value="${item.contractAmount}" pattern="#,##0.00" /></td>
							<td>${item.paybackDay}</td>
							<td>${item.customerEmail}</td>
							<td>${item.sendEmailStatusLabel}</td>
							<td>
							    <button class="btn_edit" onclick="send(1,1,'${item.paybackMonthId}',1)">发送</button>
								<button class="btn_edit" onclick="send(2,1,'${item.paybackMonthId}',1)">作废</button>
								<button class="btn_edit" onclick="showEmailOpe('${item.id}')">历史</button>
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