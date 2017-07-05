<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default"/>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context}/js/enter_select.js"></script>
<style type="text/css">
</style>
<script type="text/javascript">
	$(document).ready(function(){
		// 初始化门店下拉框
		loan.getstorelsit("txtStore","hdStore");
	});
	
	function page(n,s) {
		if (n) $("#pageNo").val(n);
		if (s) $("#pageSize").val(s);
		$("#accountHandleForm").attr("action", "${ctx}/car/carBankInfo/carCustomerBankInfo/accountHandleList");
		$("#accountHandleForm").submit();
		return false;
	}
	
	//点击清除按钮调用的的方法
	function resets(){
		// 清除text	
		$(":text").val('');
		// 清除门店隐藏域
		$("#hdStore").val('');
		// 清除select	
		$("select").val("");
		$("#accountHandleForm").submit();
	}

	function maintainHistory(loanCode){
		var url = "${ctx}/car/carBankInfo/carCustomerBankInfo/showMaintainHistory?loanCode=" + loanCode;
		art.dialog.open(url, {  
	        id: 'his',
	        title: '维护历史',
	        lock:true,
	        width:850,
	     	height:450
	    }, false); 
	}
	
	function lookMessage(id){
		var url = "${ctx}/car/carBankInfo/carCustomerBankInfo/showMessage?id=" + id;
		art.dialog.open(url, {  
	        id: 'look',
	        title: '查看',
	        lock:true,
	        width:1000,
	     	height:400
	    }, false); 
	}
</script>
</head>
<body>
	<div class="control-group">
		<form id="accountHandleForm" action="${ctx}/car/carBankInfo/carCustomerBankInfo/accountHandleList" method="post">
			<input id="pageNo" name="pageNo" type="hidden"	value="${page.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" />
			
			<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td>
						<label class="lab">客户姓名：</label>
						<input type="text" class="input-medium" name="customerName" value="${bankInfo.customerName}"/>
					</td>
					<td>
						<label class="lab">合同编号：</label>
						<input type="text" class="input-medium" name="contractCode" style="width: 270px;" value="${bankInfo.contractCode}"/>
					</td>
					<td>
						<label class="lab">还款日：</label>
						<select class="select180" id="repayDay" name="repayDay">
						<option value="">全部</option>
						<c:forEach items="${billDayList}" var="version">
								<option value="${version.billday}"
									<c:if test="${version.billday == bankInfo.repayDay}">
										selected = true
									</c:if>>
									${version.billday}
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<label class="lab">门店：</label> 
						<input type="text" id="txtStore" name="storeName" maxlength="20" class="txt date input_text178" 
								value="${bankInfo.storeName}" readonly/> 
						<i id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
						<input type="hidden" id="hdStore" name="orgCode" value="${bankInfo.orgCode}"/>
					</td>
					<td>
						<label class="lab">维护状态：</label> 
						<select class="select180" name="dictMaintainStatus" id="dictMaintainStatus">
							<option value="">全部</option>
							<c:forEach items="${fns:getDictList('jk_maintain_status')}" var="item">
								 <c:if test="${item.value ne '3' && item.value ne '4'}">
								<option value="${item.value}" <c:if test="${item.value eq bankInfo.dictMaintainStatus}">selected</c:if>>${item.label}</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
					<td>
						<label class="lab">身份证号码：</label> 
						<input type="text" class="input-medium" name="customerCertNum" value="${bankInfo.customerCertNum}"/>
					</td>
				</tr>
			</table>
		</form>
		
		<div class="tright pr30 pb5">
			<input type="button" class="btn btn-primary" onclick="document.forms[0].submit();" value="搜索"> 
			<input type="button" class="btn btn-primary" onclick="resets()" value="清除">
		</div>
	</div>
	<div>
		<table id="tableList" class="table table-condensed table-striped">
			<thead>
				<tr>
					<th>合同编号</th>
					<th>客户姓名</th>
					<th>合同到期日</th>
					<th>门店名称</th>
					<th>操作时间</th>
					<th>借款状态</th>
					<th>维护类型</th>
					<th>还款日</th>
					<th>维护状态</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
			
				<c:if test="${page!=null && fn:length(page.list)>0}">
         		<c:set var="index" value="0"/>
					<c:forEach items="${page.list}" var="item">
         			<c:set var="index" value="${index+1}" />
					<tr>
						<td>${item.contractCode}</td>
						<td>${item.customerName}</td>
						<td><fmt:formatDate value="${item.contractEndDay}" type="date" pattern="yyyy-MM-dd"/></td>
						<td>${item.storeName}</td>
						<td>
							<c:if test="${item.dictMaintainType eq '0'}">
								<fmt:formatDate value="${item.createTime}" type="date" pattern="yyyy-MM-dd"/>
							</c:if>
							<c:if test="${item.dictMaintainType ne '0'}">
								<fmt:formatDate value="${item.modifyTime}" type="date" pattern="yyyy-MM-dd"/>
							</c:if>
						</td>
						<td>${item.loanStatus}</td>
						<td>${item.dictMaintainTypeName}</td> 
						<td>${item.repayDay}</td>
						<td>${item.dictMaintainStatusName}</td>
						<td>
							<button class="btn_edit" onclick="lookMessage('${item.id}')">查看</button>
							<button class="btn_edit" onclick="maintainHistory('${item.loanCode}')">维护历史</button>
						</td>
					</tr>
					</c:forEach>
				</c:if>
				<c:if
					test="${ page==null || fn:length(page.list)==0}">
					<tr>
						<td colspan="19" style="text-align: center;">没有待办数据</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>
	<div class="pagination">${page}</div>
</body>
</html>