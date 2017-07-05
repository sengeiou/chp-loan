<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<title>暗访列表</title>
<head>
<meta name="decorator" content="default" />
<script src='${context}/js/bootstrap.autocomplete.js'
	type="text/javascript"></script>
<script src='${context}/js/secret/secret.js' type="text/javascript"></script>
<script src='${context}/js/common.js' type="text/javascript"></script>
<script language="javascript">
	function page(n, s) {
		if (n)
			$("#pageNo").val(n);
		if (s)
			$("#pageSize").val(s);
		$("#searchForm").attr("action", "${ctx}/borrow/secret/secretlist");
		$("#searchForm").submit();
		return false;
	}

	function clearForm() {
		$(':input', '#searchForm').not(':button, :submit, :reset').val('')
				.removeAttr('checked').removeAttr('selected');

		document.forms[0].submit();
	}
	$(document).ready(function() {
		secret.automatch("teamManager");
		secret.automatch("manager");
		loan.getstorelsit("txtStore","hdStore");
	});
</script>
</head>
<body>

	<div class="control-group">
		<form id="searchForm" action="${ctx}/borrow/secret/secretlist"
			method="post">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
			<input id="pageSize" name="pageSize" type="hidden"
				value="${page.pageSize}" />
			<table class="table1 " cellpadding="0" cellspacing="0" border="0"
				width="100%">
				<tr>
					<td><label class="lab">客户姓名：</label><input type="text"
						name="customerName" value="${secret.customerName }"
						class="input_text178"></td>

					<td><label class="lab">身份证号：</label><input id="bd"
						name="certiNo" type="text" maxlength="20"
						class="txt date input_text178" value="${secret.certiNo }" /></td>

					<td><label class="lab">门店：</label> <input id="txtStore" name="store"
						type="text" maxlength="20" class="txt date input_text178"
						value="${secret.store }" /> <i id="selectStoresBtn"
						class="icon-search" style="cursor: pointer;"></i>
						<input type="" id="hdStore">
						</td>
				</tr>
				<tr>
					<td><label class="lab">团队经理：</label><input type="text"
						id="teamManager" name="teamManager" value="${secret.teamManager }"
						class="input_text178"> <input type="hidden"
						name="teamManagerCode" id="teamManagerCode"
						value="${secret.teamManagerCode }" /></td>
					<td><label class="lab">客户经理：</label><input type="text"
						id="manager" name="manager" value="${secret.manager }"
						class="input_text178"> <input name="managerCode"
						id="managerCode" type="hidden" name="managerCode"
						value="${secret.managerCode }" /></td>
					<td><label class="lab">产品：</label> <input id="bd"
						name="productName" type="text" maxlength="20"
						class="txt date input_text178" value="${secret.productName }" /></td>
				</tr>
			</table>

			<div class="tright pr30 pb5">
				<input type="button" class="btn btn-primary"
					onclick="document.forms[0].submit();" value="搜索">
				<button class="btn btn-primary" id="clearBtn" onclick="clearForm();">清除</button>
			</div>
	</div>
	</form>
	<sys:message content="${message}" />
     <sys:columnCtrl pageToken="secrtile"></sys:columnCtrl>
	<div class="box5">
		<table id="tableList" class="table  table-bordered table-condensed table-hover ">
			<thead>
				<tr>
					<th>序号</th>
					<th>合同编号</th>
					<th>客户姓名</th>
					<th>门店</th>
					<th>产品</th>
					<th>状态</th>
					<th>批复金额</th>
					<th>批复分期</th>
					<th>加急标识</th>
					<th>是否电销</th>
					<th>团队经理</th>
					<th>销售人员</th>
					<th>进件时间</th>
					<th>暗访状态</th>
					<th>渠道</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody id="prepareListBody">
				<c:forEach items="${secretPage.list}" var="secret"
					varStatus="status">
					<tr>
						<td>${status.index+1 }</td>
						<td>${secret.contractCode }</td>
						<td>${secret.customerName }</td>
						<td>${secret.store.name }</td>
						<td>${secret.productinfo.productName }</td>
						<td>
							${secret.loanStatus}
						</td>
						<td align="right"><fmt:formatNumber
								value="${secret.auditAmount }" pattern="#,#00.00" /></td>
						<td>${secret.contractMonths }</td>
						<td>
							${secret.loanUrgentFlag}</td>
						<td>${secret.consTelesalesFlag}</td>
						<td>${secret.loanTeamManagerName }</td>
						<td>${secret.loanManagerName }</td>
						<td><fmt:formatDate value="${secret.customerInfoTime }"
								pattern="yyyy-MM-dd" /></td>
						<td>${secret.visitFlagLabel}</td>
						<td>${secret.loanFlag}
						</td>
						<td><c:if test="${secret.visitFlag != '1' }">
								<button class="btn_edit" id="${secret.contractCode }"
									onclick="secretTra(this) ">办理</button>
							</c:if> <c:if test="${secret.visitFlag == '1' }">
								<button class="btn_edit" id="${secret.contractCode }"
									onclick="secretTra(this) ">查看</button>
							</c:if></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="pagination">${secretPage}</div>
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
