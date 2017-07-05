<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>放款确认</title>

<script src="${context}/js/grant/disCardDeal.js" type="text/javascript"></script>
<meta name="decorator" content="default" />
<script language="javascript">
	$(document).ready(function() {
		$("#sel").change(function() {
			SelectChange();
		});
		var win = window.parent.art.dialog.open.origin;
		$("input[name='SelectBank'][middleId='"+$("#middleId",win.document).val()+"']").prop("checked",true);
	})
	function SelectChange() {
		var selectText = $("#sel").find("option:selected").text();
		if (selectText != "其他") {
			$("#other").hide();
		} else {
			$("#other").show();
		}
	}
	function page(n, s) {
		var sureFlag = $("#sureFlag").val();
		if (n)
			$("#pageNo").val(n);
		if (s)
			$("#pageSize").val(s);
		if (sureFlag == "disCard") {
			$("#searchForm").attr("action",
					"${ctx}/borrow/grant/disCard/showMiddlePerson");
		}
		if (sureFlag == "grantAudit") {
			$("#searchForm").attr("action",
					"${ctx}/borrow/grant/grantAudit/selectMiddle");
		}
		$("#searchForm").submit();
		return false;
	}
</script>
</head>
<base target="_self">
<body>
	<!--分配外访弹出框表-->
	<div class="dialogbox">
		<div class="diabox_cell">
			<div class="diabox">
				<div class="mb10 control-group control-group">
					<form id="searchForm" method="post"
						action="${ctx}/borrow/grant/disCard/showMiddlePerson">
						<input type="hidden" id="sureFlag" value="${sureFlag}"> <input
							id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
						<input id="pageSize" name="pageSize" type="hidden"
							value="${page.pageSize}" /> <label class="lab">中间人：</label><input
							type="text" class="input_text140 mr10" name="middleName"
							id="middleName" value="${midPerson.middleName }"><label
							class="lab">开户行：</label><input type="text"
							class="input_text140 mr10" id="bankName" name="midBankName"
							value="${midPerson.midBankName }">
						<input type="hidden" id="wayFlag" name="wayFlag" value="${midPerson.wayFlag }">
						<button class="btn btn-small">检索</button>
					</form>
				</div>
				<table class="table  table-bordered table-condensed table-hover ">
					<thead>
						<tr>
							<th>中间人</th>
							<th>开户行</th>
							<th>银行账号</th>
							<th>操作</th>
						</tr>
					</thead>
					<c:if test="${ middlePage!=null && fn:length(middlePage.list)>0}">
						<c:forEach items="${middlePage.list}" var="item">
							<tr>
								<td>${item.middleName }</td>
								<td>${item.midBankName}</td>
								<td>${item.bankCardNo }</td>
								<td><input type="radio" name="SelectBank"
									middleId="${item.id }"></td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${ middlePage==null || fn:length(middlePage.list)==0}">
						<tr>
							<td colspan="4" style="text-align: center;">没有查找到符合条件的数据</td>
						</tr>
					</c:if>
				</table>
				<br>
				<br>
			</div>
		</div>
	</div>
	<div class="pagination">${middlePage}</div>

	<c:if test="${sureFlag=='grantAudit'}">
		<br>
		<br>
		<br>
		<div class="tright mt20 pr30">
			<button class="btn btn-primary" id="middleSure">确认</button>
		</div>
	</c:if>
</body>
</html>