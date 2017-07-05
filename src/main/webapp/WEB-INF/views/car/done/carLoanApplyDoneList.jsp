<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>车借申请已办列表</title>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript">
  $(document).ready(function(){
	 $('#searchBtn').bind('click',function(){
		 page(1,${page.pageSize});
	 });
	 $('#clearBtn').bind('click',function(){
		 $(':input','#searchTable')
		  .not(':button, :reset')
		  .val('')
		  .removeAttr('checked')
		  .removeAttr('selected');
		 $("#hdStore").val('');
		 $("select").trigger("change");
	 });
	 $('#showMore').bind('click',function(){
		show();
	 });
	 //选择门店
	 loan.getstorelsit("txtStore","hdStore");
	 
	//敲回车执行input条件查询
	 $(function(){
	 document.onkeydown = function(e){ 
	 var ev = document.all ? window.event : e;
	 		if(ev.keyCode== 13) {
	 			page(1,${page.pageSize});
	 		}
	 	}
	 });

 });
  function page(n, s) {
		if(n){
			$("#pageNo").val(n);
		}
		if(s){
			$("#pageSize").val(s);
		}
		$("#inputForm").attr("action", "${ctx}/common/carHistory/carLoanApplyDoneList");
		$("#inputForm").submit();
		return false;
	}
</script>
</head>
<body>
	<div class="control-group">
		<form:form id="inputForm" modelAttribute="carLoanStatusHisEx" enctype="multipart/form-data"
			method="post" class="form-horizontal">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<table id = "searchTable" class="table1 " cellpadding="0" cellspacing="0" border="0"
				width="100%">
				<tr>
					<td><label class="lab">客户姓名：</label> <form:input
							id="customerName" path="loanCustomerName" class="input_text178" />
					</td>
					<td><label class="lab">产品类型：</label> <form:select
							id="productType" path="productType" class="select180">
							<option value="">全部</option>
							<c:forEach items="${fncjk:getPrd('products_type_car_credit')}"
								var="product_type">
								<form:option value="${product_type.productCode}">${product_type.productName}</form:option>
							</c:forEach>
						</form:select></td>
					<td><label class="lab">身份证号：</label> <form:input
							path="certNum" class="input_text178" /></td>
				</tr>
				<tr>
					<td><label class="lab">团队经理：</label> <form:input
							path="teamManagerName" class="input_text178" /></td>
					<td><label class="lab">客户经理：</label> <form:input
							path="costumerManagerName" class="input_text178" /></td>
					<td><label class="lab">门店：</label> <form:input id="txtStore"
						path="storeName" maxlength="20"
						class="txt date input_text178" value="${secret.store }" readonly="true"/> <i
						id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
				</tr>
				<tr id="T1" style="display: none">
					<td><label class="lab">借款状态：</label> <form:select
							id="loanMonths" path="loanStatusCode" class="select180">
							<option value="">全部</option>
							<form:options items="${fns:getDictList('jk_car_loan_status')}"
								itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select></td>
					<td colspan="2"><label class="lab">是否电销：</label> <form:radiobuttons
							path="telesalesFlag"
							items="${fns:getDictList('jk_telemarketing')}" itemLabel="label"
							itemValue="value" htmlEscape="false" /></td>

				</tr>
			</table>
			<div class="tright pr30 pb5">
				<form:input type="hidden" id="hdStore" path="storeCode"/></td>
				<input type="button" class="btn btn-primary" id="searchBtn"
					value="搜索"></input> <input type="button" class="btn btn-primary"
					id="clearBtn" value="清除"></input>
				<div class="xiala" style="text-align: center;">
					<img src="${context}/static/images/up.png" id="showMore"></img>
				</div>
			</div>
	</div>
	</form:form>
	<sys:columnCtrl pageToken="carLoanApplyDoneList"></sys:columnCtrl>
	<div class="box5" style="position:relative;width:100%;overflow:hidden;height:60%;margin-bottom: 20px">
		<table id="tableList" class="table table-bordered table-condensed table-hover"
			style="margin-bottom: 200px;">
			<thead>
				<tr>
					<th>序号</th>
					<th>合同编号</th>
					<th>客户姓名</th>
					<th>申请金额（元）</th>
					<th>借款期限(天)</th>
					<th>产品类型</th>
					<th>评估金额</th>
					<th>批借金额</th>
					<th>申请日期</th>
					<th>所属省份</th>
					<th>门店名称</th>
					<th>团队经理</th>
					<th>客户经理</th>
					<th>面审人员</th>
					<th>借款状态</th>
					<th>合同到期提醒</th>
					<th>车牌号码</th>
					<th>是否电销</th>
					<th>操作</th>
				</tr>
			</thead>
			<c:if test="${ page.list != null && fn:length(page.list)>0}">
				<c:forEach items="${page.list}" var="item" varStatus="status">
					<tr>
						<td>${status.count}</td>
						<td>${item.contractCode}</td>
						<td>${item.loanCustomerName}</td>
						<td><fmt:formatNumber value="${item.loanApplyAmount == null ? 0 : item.loanApplyAmount }" pattern="#,##0.00" /></td>
						<c:choose>
							<c:when test="${item.finalAuditMonths != null }">
								<td>${item.finalAuditMonths}</td>
							</c:when>
							<c:when test="${item.secondAuditMonths != null }">
								<td>${item.secondAuditMonths}</td>
							</c:when>
							<c:otherwise>
								<td>${item.loanMonths}</td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${item.finalProductType != null }">
								<td>${item.finalProductType}</td>
							</c:when>
							<c:when test="${item.secondProductType != null }">
								<td>${item.secondProductType}</td>
							</c:when>
							<c:otherwise>
								<td>${item.firstProductType}</td>
							</c:otherwise>
						</c:choose>
						<td><fmt:formatNumber value="${item.storeAssessAmount == null ? 0 : item.storeAssessAmount }" pattern="#,##0.00" /></td>
						<c:choose>
							<c:when test="${item.finalAuditAmount != null }">
								<td><fmt:formatNumber value="${item.finalAuditAmount == null ? 0 : item.finalAuditAmount }" pattern="#,##0.00" /></td>
							</c:when>
							<c:when test="${item.secondAuditAmount != null }">
								<td><fmt:formatNumber value="${item.secondAuditAmount == null ? 0 : item.secondAuditAmount }" pattern="#,##0.00" /></td>
							</c:when>
							<c:otherwise>
								<td><fmt:formatNumber value="${item.firstAuditAmount == null ? 0 : item.firstAuditAmount }" pattern="#,##0.00" /></td>
							</c:otherwise>
						</c:choose>
						<td><fmt:formatDate value="${item.applyTime}"
								pattern="yyyy-MM-dd" /></td>
						<td>${item.provinceName}</td>
						<td>${item.storeName}</td>
						<td>${item.teamManagerName}</td>
						<td>${item.costumerManagerName}</td>
						<td>${item.reviewMeetName}</td>
						<td>${item.loanStatusCode}</td>
						<td><fmt:formatDate value="${item.rateCheckDate}"
								pattern="yyyy-MM-dd" /></td>
						<td>${item.plateNumbers}</td>
						<td>${item.telesalesFlag}</td>
						<td>
						<c:choose>
						   <c:when test="${item.loanStatusCode == '101'}"><input type="button" value="查看" class="btn_edit" id="butss"
						 onclick="appraiserEntryDone('${item.loanCode}')"></input>
						   </c:when>  
						   <c:otherwise><input type="button" value="查看" class="btn_edit" id="butss"
						 onclick="showCarLoanInfo('${item.loanCode}')"></input>
						   </c:otherwise> 
						</c:choose>
						</td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${ page.list==null || fn:length(page.list)==0}">
				<tr>
					<td colspan="18" style="text-align: center;">没有已办数据</td>
				</tr>
			</c:if>
		</table>
	</div>
	<div class="pagination">${page}</div>
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