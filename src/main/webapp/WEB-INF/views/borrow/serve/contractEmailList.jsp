<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default"/>
<script type="text/javascript" src="${context}/js/common.js"></script>

<script type="text/javascript">
	$(document).ready(function(){
		// 初始化门店下拉框
		loan.getstorelsit("txtStore","hdStore");
	});

	//点击清除按钮调用的的方法
	function resets(){
		// 清除text	
		$("#customerName").val('');
		$("#hdStore").val('');
		$("#txtStore").val('');
		$("#contractCode").val('');
		$("#model").val('');
		$("#loanFlag").val('');
		$("#model").trigger("change");
		$("#loanFlag").trigger("change");
	}
	
	/**
	 * 查看历史操作
	 * @param applyId
	 */
	function doOpenhis(loanCode,fileType) {
		var url = ctx + "/borrow/serve/customerServe/historyEmailList?loanCode=" + loanCode + "&fileType=" + fileType;
		art.dialog.open(url, {  
	        id: 'his',
	        title: '历史列表',
	        lock:true,
	        width:700,
	     	height:350
	    }, false);  
	}
	
	function page(n,s) {
		if (n) $("#pageNo").val(n);
		if (s) $("#pageSize").val(s);
		$("#alreadyHandleEmailForm").attr("action", "${ctx }/borrow/serve/customerServe/contractEmailList");
		$("#alreadyHandleEmailForm").submit();
		return false;
	}
</script>
</head>
<body>
	<div class = "control-group">
		<form id="alreadyHandleEmailForm" action="${ctx }/borrow/serve/customerServe/contractEmailList" method="post">
			<input id="pageNo" name="pageNo" type="hidden"	value="${page.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" />
		<input type="hidden" class="input-medium" id="fileType" name="fileType" value="${contractFileSendEmailView.fileType }"/></td>
		
				
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">客户姓名：</label>
					<input type="text" class="input-medium" id="customerName" name="customerName" value="${contractFileSendEmailView.customerName }"/></td>
					<td>
						<label class="lab">门店：</label> 
						<input type="text" id="txtStore" name="storeName" readonly="readonly" class="txt date input_text178" value="${contractFileSendEmailView.storeName}"/> 
						<i id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
						<input type="hidden" id="hdStore" name="orgCode" value="${contractFileSendEmailView.orgCode}"/>
					</td>
					<td><label class="lab">合同编号：</label>
					<input type="text" class="input-medium" id="contractCode" name="contractCode" value="${contractFileSendEmailView.contractCode }"/></td>
				</tr>
				<tr>
				    <td>
							<label class="lab">渠道：</label> 
							<select id="loanFlag" name="loanFlag"   class="select180"> 
                                    <option value="">请选择</option>
                                    <c:forEach items="${fns:getDictList('jk_channel_flag')}" var="str">
                                    	<option value="${str.value }" 
                                    	 <c:if test="${contractFileSendEmailView.loanFlag==str.value }">selected</c:if>>${str.label }</option>
                                    </c:forEach>									
							</select>
					</td>
					<td><label class="lab">模式：</label> 
							<select id="model" name="model" class="select180">
								<option value=''>请选择</option>
								<c:forEach items="${fns:getDictList('jk_loan_model')}" var="loanmodel">
	                                   <option value="${loanmodel.value }" <c:if test="${contractFileSendEmailView.model == loanmodel.value }">selected</c:if>>
	                                   <c:if test="${loanmodel.value=='0'}">
	                                   	非TG
	                                   </c:if>
	                                   <c:if test="${loanmodel.value!='0'}">${loanmodel.label}</c:if>
	                                   </option>
	                            </c:forEach>
	                       </select>
	                  </td>
				</tr>
			</table>
		</form>
		<div class="tright pr30 pb5">
			<input type="button" class="btn btn-primary" onclick="document.forms[0].submit();" value="搜索"> 
			<input type="button" class="btn btn-primary" onclick="resets()" value="清除">
		</div>
	</div>
	<sys:columnCtrl pageToken="outside"></sys:columnCtrl>
	<div class="box5">
		<table id="tableList" class="table  table-bordered table-condensed table-hover ">
			<thead>
				<tr>
					<th>客户姓名</th>
					<th>合同编号</th>
					<th>门店名称</th>
					<th>借款产品</th>
					<th>借款期限</th>
					<th>合同金额</th>
					<th>合同到期日期</th>
					<th>合同签订日期</th>
					<th>提前结清日期</th>
					<th>借款状态</th>
					<th>门店申请日期</th>
					<th>邮箱</th>
					<th>合同状态</th>
					<th>渠道</th>
					<th>模式</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${page.list != null && fn:length(page.list)>0}">
					<c:set var="index" value="0"/>
					<c:forEach items="${page.list}" var="item">
						<c:set var="index" value="${index+1}"/>
						<tr>
							<td>${item.customerName}</td>
							<td>${item.contractCode}</td>
							<td>${item.storeName}</td>
							<td>${item.productType}</td>
							<td>${item.loanMonths}</td>
							<td><fmt:formatNumber value="${item.contractAmount}" pattern="#,##0.00"/> </td>
							<td><fmt:formatDate value="${item.contractEndDay}" type="date" pattern="yyyy-MM-dd"/></td>
							<td><fmt:formatDate value="${item.contractFactDay}" type="date" pattern="yyyy-MM-dd"/></td>
							<td><fmt:formatDate value="${item.settleDay}" type="date" pattern="yyyy-MM-dd"/></td>
							<td>${item.creditStatusName}</td>
							<td><fmt:formatDate value="${item.applyTime}" type="date" pattern="yyyy-MM-dd"/></td>
							<td>${item.receiverEmail}</td>
							<td>${item.sendStatusName}</td>
							<td>${item.loanFlag}</td>
							<td>${item.model}</td>
							<td>
								<button class="btn_edit" onclick="doOpenhis('${item.loanCode}','${contractFileSendEmailView.fileType }')">历史</button>
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${page.list == null || fn:length(page.list)==0}">
					<tr>
						<td colspan="18" style="text-align: center;">没有待办数据</td>
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
			height: $(window).height()-200,
			pageSize: 20,
			pageNumber:1
		});
		$(window).resize(function () {
			$('#tableList').bootstrapTable('resetView');
		});
	</script>
</body>
</html>