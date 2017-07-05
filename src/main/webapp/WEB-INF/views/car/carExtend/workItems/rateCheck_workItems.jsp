<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>车借展期利率审核待办列表</title>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context}/js/enter_select.js"></script>
<script type="text/javascript">
  $(document).ready(function(){
	 $('#searchBtn').bind('click',function(){
		// 终审日期验证触发事件,点击搜索进行验证
		var startDate = $("#finalCheckTimeStart").val();
		var endDate = $("#finalCheckTimeEnd").val();
		if(endDate!=""&& endDate!=null && startDate!="" &&startDate!=null){
			var arrStart = startDate.split("-");
			var arrEnd = endDate.split("-");
			var sd=new Date(arrStart[0],arrStart[1],arrStart[2]);  
			var ed=new Date(arrEnd[0],arrEnd[1],arrEnd[2]);  
			if(sd.getTime()>ed.getTime()){   
				art.dialog.alert("终审开始日期不能大于终审结束日期!",function(){
					$("#finalCheckTimeStart").val("");
					$("#finalCheckTimeEnd").val("");
				});
				return false;     
			}else{
				$('#inputForm').submit(); 
			}  
		}else{
			$('#inputForm').submit(); 
		}
	 });
	 $('#showMore').bind('click',function(){
		show(); 
	 });
	 $('#clearBtn').bind('click',function(){
		 $(':input','#searchTable')
		  .not(':button, :reset')
		  .val('')
		  .removeAttr('checked')
		  .removeAttr('selected');  
		 $("select").trigger("change");
	 });
	 
	 //选择门店
	 loan.getstorelsit("txtStore","hdStore");
	 var contractNo='<c:forEach items="${fns:getDictList('jk_car_contract_version')}" var="dict" varStatus="status"><c:if test="${status.last}">${dict.label }</c:if></c:forEach>';
	  $(".contractNo").html(contractNo);
 });
 function generalContractCode(applyId, wobNum, token) {
	$.ajax({
		type : "POST",
		data : {
			applyId : applyId
		},
		url : ctx + "/car/carExtendContract/generalExtendContractCode",
		success : function(data) {
			if (data == "true") {
				windowLocationHref(ctx + '/bpm/flowController/openForm?applyId=' + applyId + '&wobNum=' + wobNum + '&dealType=0&token=' + token);
			} else if (data == "false") {
				alert("生成合同编号或插入合同表失败，请联系管理员！");
				return false;
			}
		}
	});
}
 function page(n, s) {
		if (n)
			$("#pageNo").val(n);
		if (s)
			$("#pageSize").val(s);
		$("#inputForm").attr("action", "${ctx}/car/carExtendWorkItems/fetchTaskItems/extendContractCommissionerRateCheck");
		$("#inputForm").submit();
		return false;
} 
</script>
</head>
<body>
	<div class="control-group">
		<form:form id="inputForm" modelAttribute="carExtendFlowQueryView"
			action="${ctx}/car/carExtendWorkItems/fetchTaskItems/extendContractCommissionerRateCheck"
			method="post" class="form-horizontal">
			 <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
			 <input name="menuId" type="hidden" value="${param.menuId}" />
		     <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
		     <table id="searchTable" class="table1 " cellpadding="0" cellspacing="0" border="0"
				width="100%">
				<tr>
					<td><label class="lab">客户姓名：</label> <form:input
							id="customerName" path="customerName" class="input_text178" /></td>
					<td><label class="lab">门店：</label> <input id="txtStore"
						name="storeId" type="text" maxlength="20"
						class="txt date input_text178" value="${secret.store }" readonly="true"/> <i
						id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
					<td><label class="lab">产品类型：</label> <form:select
							id="productType" path="auditBorrowProductCode" class="select180">
							<option value="">请选择</option>
							<c:forEach items="${fncjk:getPrd('products_type_car_credit')}"
								var="product_type">
								<form:option value="${product_type.productCode}">${product_type.productName}</form:option>
							</c:forEach>
						</form:select></td>
				</tr>
				<tr>
					<td><label class="lab">合同编号：</label> <form:input
							path="contractCode" class="input_text178" /></td>
					<td><label class="lab">终审日期：</label> <input
						id="finalCheckTimeStart" name="finalCheckTimeStart"
						class="input_text70 Wdate" type="text"
						onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
						style="cursor: pointer"
						value="<fmt:formatDate value='${carExtendFlowQueryView.finalCheckTimeStart}' type='date' pattern="yyyy-MM-dd" />" />-<input id="finalCheckTimeEnd" name="finalCheckTimeEnd"
						class="input_text70 Wdate" type="text"
						onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
						style="cursor: pointer"
						value="<fmt:formatDate value='${carExtendFlowQueryView.finalCheckTimeEnd}' type='date' pattern="yyyy-MM-dd" />" />
					</td>
					<td><label class="lab">借款期限：</label> <form:select
							id="loanMonths" path="auditLoanMonths" class="select180">
							<option value="">全部</option>
							<c:forEach items="${fncjk:getPrdMonthsByType('products_type_car_credit')}" var="product_type">
								<form:option value="${product_type.key}">${product_type.value}</form:option>
							</c:forEach>
						</form:select></td>
				</tr>
				<tr id="T1" style="display: none">
					<td><label class="lab">是否电销：</label> <form:radiobuttons
							path="loanIsPhone" items="${fns:getDictList('jk_telemarketing')}"
							itemLabel="label" itemValue="value" htmlEscape="false" /></td>
							<td><label class="lab">合同版本号：</label> <form:input
							id="contractVersion" path="contractVersion" class="input_text178" /></td>
					<td><label class="lab">渠道：</label><form:select id="cardType" path="loanFlag" class="select180">
                    <option value="">请选择</option>
                    <c:forEach items="${fns:getDictList('jk_car_throuth_flag')}" var="loan_Flag">
			                   		<c:if test = "${loan_Flag.value!=1}">
			                   			<form:option value="${loan_Flag.value}">${loan_Flag.label}</form:option>
			                   		</c:if>	
			                   </c:forEach>
				</form:select>
					</td>
				</tr>
			</table>
			<div class="tright pr30 pb5">
			 <span style="position: relative;left: 100px;  top: 43px;color:red;font-size:14px;" >当前合同版本号：<span class="contractNo"></span></span>
				<input type="hidden" id="hdStore"></td>
				<input type="button" class="btn btn-primary" id="searchBtn" value="搜索"></input>
            	<input type="button" class="btn btn-primary" id="clearBtn" value="清除"></input>
				<div class="xiala" style="text-align: center;">
					<img src="${context}/static/images/up.png" id="showMore"></img>
				</div>
			</div>
		</form:form>
	</div>
	<sys:columnCtrl pageToken="carRateCheckWorList"></sys:columnCtrl>
	<div class="box5" style="position:relative;width:100%;overflow:hidden;height:60%;margin-bottom: 20px">
	
		<table id="tableList" class="table  table-bordered table-condensed table-hover "
			style="margin-bottom: 200px;">
			<thead>
				<tr>
					<th>序号</th>
					<th>展期合同编号</th>
					<th>客户姓名</th>
					<th>共借人姓名</th>
					<th>门店名称</th>
					<th>管辖省份</th>
					<th>审批金额</th>
					<th>借款期限</th>
					<th>产品类型</th>
					<th>终审日期</th>
					<th>已展期次数</th>
					<th>申请状态</th>
					<th>是否电销</th>
					<th>合同版本号</th>
					<th>渠道</th> 
					<!-- 
					<th>合同版本号</th> -->
					<th>操作</th>
				</tr>
			</thead>
			<c:if test="${ itemList!=null && fn:length(itemList)>0}">
				<c:forEach items="${itemList}" var="item" varStatus="status">
					 <tr <c:if test="${fn:contains(item.orderField,'0,') }">class='trRed'</c:if>>
						<td>${status.count}</td>
						<td>${item.contractCode}</td>
						<td>${item.customerName}</td>
						<td>${item.coborrowerName}</td>
						<td>${item.storeName}</td>
						<td>${item.addrProvince}</td>
						<td>${item.auditAmount}</td>
						<td>${item.auditLoanMonths}</td>
						<td>${item.auditBorrowProductName}</td>
						<td><fmt:formatDate value='${item.finalCheckTime}'
								pattern="yyyy-MM-dd" /></td>
						<td>${item.extendNumber}</td>
						<td>${item.applyStatusCode}</td>
						<td>${item.loanIsPhone}</td>
						<td>${item.contractVersion}</td>
						<td>${item.loanFlag}</td> 
						<td><input type="button" value="办理" class="btn_edit jkcj_rateCheck_workItems_deal"
							onclick="generalContractCode('${item.applyId}','${item.wobNum}','${item.token}')"></input>&nbsp;
							<button class="btn_edit jkcj_rateCheck_workItems_history" value="${item.applyId}"  onclick="javascript:showCarLoanHisByApplyId(this.value)">历史</button>
						</td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${ itemList==null || fn:length(itemList)==0}">
				<tr>
					<td colspan="14" style="text-align: center;">没有待办数据</td>
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