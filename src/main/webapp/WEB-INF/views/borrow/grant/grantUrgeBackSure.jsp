<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>催收服务费退款确认</title>
<script src="${context}/js/grant/grantUrgeBack.js" type="text/javascript"></script>
<meta name="decorator" content="default" />
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
					<button class="btn btn-small">检索</button>
				</form>
				</div>
				<table class="table  table-bordered table-condensed table-hover ">
					<tr>
						<th>中间人</th>
						<th>开户行</th>
						<th>银行账号</th>
						<th>操作</th>
					</tr>
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
			</div>
		</div>
	
	<!-- 确认日期 -->
	<div style="display: inline" id="sort_div">
			<label class="lab">确认退款日期：</label><input id="backDate" name="returnTime" value="<fmt:formatDate value='${returnTime}' pattern="yyyy-MM-dd"/>" type="text" class="Wdate input_text178" size="10"  
                                       onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" />
	</div>
	<div class="tright mt20 pr30">
					<button class="btn btn-primary" id="dateSure">确认</button>
	</div>
</div>
</body>
</html>