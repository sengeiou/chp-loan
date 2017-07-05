<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default"/>
<title>电子协议已办列表</title>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context}/js/enter_select.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		// 初始化门店下拉框
		loan.getstorelsit("txtStore","hdStore");
		
		 $('#tableList').bootstrapTable('destroy');
			$('#tableList').bootstrapTable({
				method: 'get',
				cache: false,
				height: $(window).height()-220,
				pageSize: 20,
				pageNumber:1
			});
	});
	
	function page(n,s) {
		if (n) $("#pageNo").val(n);
		if (s) $("#pageSize").val(s);
		$("#eleAgreeForm").attr("action", "${ctx}/borrow/account/repayaccount/accountHandleList");
		$("#eleAgreeForm").submit();
		return false;
	}
	
	//点击清除按钮调用的的方法
	function resets(){
		$("#maintainStatus").val('');
		$("#maintainStatus").trigger("change");
		// 清除text	
		$(":text").val('');
		// 清除门店隐藏域
		$("#hdStore").val('');
		// 清除select	
		$("select").val("");
		$("#eleAgreeForm").submit();
	}
	
	//维护历史
	function agrHistory(contractCode){
		var url = "${ctx}/common/carHistory/showContractAgrHistory?contractCode=" + contractCode;
		art.dialog.open(url, {  
	        id: 'his',
	        title: '维护历史',
	        lock:true,
	        width:850,
	     	height:450
	    }, false); 
	}
</script>
</head>
<body>
	<div class="control-group">
		<form id="eleAgreeForm" action="${ctx}/common/carHistory/applyAgreementHandleList" method="post">
			<input id="pageNo" name="pageNo" type="hidden"	value="${page.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" />
			
			<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td>
						<label class="lab">客户姓名：</label>
						<input type="text" class="input-medium" name="loanCustomerName" value="${ex.loanCustomerName}"/>
					</td>
					<td>
						<label class="lab">门店：</label> 
						<input type="text" id="txtStore" name="storeName" maxlength="20" class="txt date input_text178" 
								value="${ex.storeName}" readonly/> 
						<i id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
						<input type="hidden" id="hdStore" name="storeId" value="${ex.storeId}"/>
					</td>
					<td>
						<label class="lab">合同编号：</label>
						<input type="text" class="input-medium" name="contractCode" value="${ex.contractCode}"/>
					</td>
				</tr>
				<tr>
					<td>
						<label class="lab">紧急程度：</label> 
						<select id="urgentDegree" name="urgentDegree" style="width:100px">
							<option value="">请选择</option>
							<option value="1" <c:if test="${ex.urgentDegree == '1'}">
										selected = true
									</c:if>>紧急</option>
							<option value="2" <c:if test="${ex.urgentDegree == '2'}">
										selected = true
									</c:if>>正常</option>
						</select>
					</td>
					<td>
						<label class="lab">身份证号码：</label> 
						<input type="text" class="input-medium" name="customerCertNum" value="${ex.customerCertNum}"/>
					</td>
				</tr>
			</table>
		</form>
		<div class="tright pr30 pb5">
			<input type="button" class="btn btn-primary" onclick="document.forms[0].submit();" value="搜索"> 
			<input type="button" class="btn btn-primary" onclick="resets()" value="清除">
		</div>
	</div>
	<div class="box5" style="position:relative;width:100%;overflow:hidden;height:60%;margin-bottom: 20px">
		<sys:columnCtrl pageToken="creditorw"></sys:columnCtrl>
		<table id="tableList" class="table table-condensed table-striped">
			<thead>
				<tr>
					<th><input type="checkbox" onclick="checkAll();" id="checkAll"/></th>
					<th>序号</th>
					<th>客户姓名</th>
					<th>合同编号</th>
					<th>门店名称</th>
					<th>借款产品</th>
					<th>借款期限</th>
					<th>合同金额</th>
					<th>合同到期日期</th>
					<th>结清日期</th>
					<th>借款状态</th>
					<th>紧急程度</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${eleList!=null && fn:length(eleList.list)>0}">
         		<c:set var="index" value="0"/>
					<c:forEach items="${eleList.list}" var="item">
         			<c:set var="index" value="${index+1}" />
						<tr>
							<td>
								<input type="checkbox" name="checkBoxItem" 
								cid='${item.id }' 
								deductAmount='${item.customerEmail}'/>
							</td>
							<td>${index}</td>
							<td>${item.loanCustomerName}</td>
							<td>${item.contractCode}</td>
							<td>${item.storeName}</td>
							<td>${fncjk:getPrdLabelByTypeAndCode('products_type_car_credit',item.productTypeContract)}</td>
							<td>${item.contractMonths}</td>
             				<td><fmt:formatNumber value="${item.contractAmount}" type="number" pattern="0.00"/></td>
							<td><fmt:formatDate value="${item.contractEndDay}" type="date" pattern="yyyy-MM-dd"/></td>
							<td><fmt:formatDate value="${item.settledDate}" type="date" pattern="yyyy-MM-dd"/></td>
							<td>${item.dictLoanStatus}</td>
							<td>
								<c:if test="${item.urgentDegree eq '1'}">紧急</c:if>
								<c:if test="${item.urgentDegree eq '2'}">正常</c:if>
							</td>
							<td>
								<button class="btn_edit" onclick="agrHistory('${item.contractCode}')">维护历史</button>
							</td>
						</tr>
				</c:forEach>
				</c:if>
				<c:if
					test="${ eleList==null || fn:length(eleList.list)==0}">
					<tr>
						<td colspan="19" style="text-align: center;">没有待办数据</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>
	<div class="pagination">${eleList}</div>
</body>
</html>