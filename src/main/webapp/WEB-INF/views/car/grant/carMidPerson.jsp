<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>放款确认</title>

<script src="${context}/static/ckfinder/plugins/gallery/colorbox/jquery.min.js"></script>
<script src="${context}/static/ckfinder/plugins/gallery/colorbox/jquery.colorbox-min.js"></script>
<link media="screen" rel="stylesheet" href="${context}/static/ckfinder/plugins/gallery/colorbox/colorbox.css" />
<script src="${context}/js/car/grant/carDisCardDeal.js" type="text/javascript"></script>
<meta name="decorator" content="default" />
<script language="javascript">
	$(document).ready(function() {
		$("#sel").change(function() {
			SelectChange();
		});
	})
	function SelectChange() {
		var selectText = $("#sel").find("option:selected").text();
		if (selectText != "其他") {
			$("#other").hide();
		} else {
			$("#other").show();
		}
	}

</script>
</head>

<base target="_self">
<body >
	<!--分配外访弹出框表-->
	<div class="dialogbox">
		<div class="diabox_cell">
			<div class="diabox">
				<div class="mb10">
				
				<form id="searchForm">
				
					<label  class="lab">开户行：</label><input type="text" class="input_text140 mr10"
						id="bankName" name="midBankName" value="${midPerson.midBankName }"><label  class="lab">中间人：</label><input type="text"
						class="input_text140 mr10" name="middleName" id="middleName" value="${midPerson.middleName }">
					<button class="btn btn-small">查询</button>
				</form>
				</div>
				<table class="table  table-bordered table-condensed table-hover ">
					<tr>
						<th>中间人</th>
						<th>开户行</th>
						<th>银行账号</th>
						<th>操作</th>
					</tr>
					<c:if test="${ middlePage!=null && fn:length(middlePage)>0}">
						<c:forEach items="${middlePage}" var="item">
							<tr>
								<td>${item.middleName }</td>
								<td>${item.midBankName}</td>
								<td>${item.bankCardNo }</td>
								<td><input type="radio" name="SelectBank"
									middleId="${item.id }"></td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${ middlePage==null || fn:length(middlePage)==0}">
						<tr>
							<td colspan="4" style="text-align: center;">没有查找到符合条件的数据</td>
						</tr>
					</c:if>
				</table>
				
			</div>
		</div>
	</div>

</body>
</html>