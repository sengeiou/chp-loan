<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
   <meta name="decorator" content="default"/>
	<script type="text/javascript" src="${context }/js/consult/consultOcrList.js"></script>
	<script type="text/javascript" src="${context}/js/common.js"></script>
    <script type="text/javascript" src="${ctxStatic}/tableCommon.js"></script>
    <script src="${context}/static/ckfinder/plugins/gallery/colorbox/jquery.colorbox-min.js"></script>
    <link media="screen" rel="stylesheet" href="${context}/static/ckfinder/plugins/gallery/colorbox/colorbox.css" />
    <link href="${ctxStatic}/bootstrap/3.3.5/awesome/daterangepicker-bs3.css" type="text/css" rel="stylesheet" />
</head>
<body>
 <div class="control-group">
	<form id="searchForm" action="${ctx}/apply/consultOcr/list" method="post" >
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td><label class="lab">客户姓名：</label> <input type="text"
							name="customerName" class="input_text178"
							value="${customerInfo.customerName }" /></td>
						<td><label class="lab">客户身份证号：</label> <input type="text"
							name="certNum" class="input_text178"
							value="${customerInfo.certNum }" /></td>
						<td><label class="lab">客户经理：</label> <input id="txtStore"
							name="financingName" type="text" maxlength="20"
							class="input_text178" value="${customerInfo.financingName }" />
						</td>
					</tr>
		  </table>
	</form>
	<div class="tright pr30 pb5">
			<input type="button" class="btn btn-primary "
				onclick="document.forms[0].submit();" value="搜索"> <input
				type="button" class="btn btn-primary" id="clearBtn" value="清除" />
	</div>
	</div>
    <table id="tableList" class="table  table-bordered table-condensed table-hover ">
				<thead>
					<tr height="37px">
						<th>序号</th>
						<th>客户姓名</th>
						<th>身份证号</th>
						<th>App咨询创建时间</th>
						<th>计划贷款金额</th>
						<th>客户经理</th>
						<th>团队经理</th>
						<th>操作</th>
					</tr>
				</thead>
				<c:forEach items="${waitPage.list}" var="item" varStatus="status">
					<tr>
					  <td>${status.count }</td>
					  <td>${item.customerName }</td>
					  <td>${item.certNum }</td>
					  <td>
					       <fmt:formatDate value="${item.firstCreateTime }" type="date" />
					  </td>
					  <td>
					        <fmt:formatNumber value='${item.loanPosition }' pattern="#,##0.00" /> 
					  </td>
					  <td>${item.financingId }</td>
					  <td>${item.teamEmpcode }</td>
					  <td><input type="button" class="btn_edit" value="办理"
						  onclick="JavaScript:window.location='${ctx}/apply/consultOcr/getCustomerInfoById?id=${item.id }'"/>
					  </td>
					</tr>
				</c:forEach>
		</table>
	<div class="pagination">${waitPage}</div>
	</div>
</body>
</html>