<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default"/>
<script type="text/javascript" src="${context}/js/common.js"></script>
<style type="text/css">
</style>
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
		
		//还款日验证
		$("#repayDay").blur(function(){
			var da = $("#repayDay").val();
			if (da != null && "" != da) {
				var dar = eval(da);
				if (dar>31 || dar<1 ) {
					artDialog.alert('请输入1~31之间的数字!');
					$("#repayDay").focus();
					return;
				}
			}
		});
	});
	
	function page(n,s) {
		if (n) $("#pageNo").val(n);
		if (s) $("#pageSize").val(s);
		$("#accountHandleForm").attr("action", "${ctx}/borrow/account/repayaccount/accountHandlePhoneSaleList");
		$("#accountHandleForm").submit();
		return false;
	}
	
	//点击清除按钮调用的的方法
	function resets(){
		$("#repayDay").val('');
		$("#repayDay").trigger("change");
		$("#maintainStatus").val('');
		$("#maintainStatus").trigger("change");
		// 清除text	
		$(":text").val('');
		// 清除门店隐藏域
		$("#hdStore").val('');
		// 清除select	
		$("select").val("");
		$("#accountHandleForm").submit();
	}
	
	//维护历史
	function maintainHistory(contractCode){
		var url = "${ctx}/borrow/account/repayaccount/showMaintainHistory?contractCode=" + contractCode;
		art.dialog.open(url, {  
	        id: 'his',
	        title: '维护历史',
	        lock:true,
	        width:850,
	     	height:450
	    }, false); 
	}
	
	//查看
	function lookMessage(id){
		var url = "${ctx}/borrow/account/repayaccount/showMessage?id=" + id;
		art.dialog.open(url, {  
	        id: 'look',
	        title: '查看',
	        lock:true,
	        width:900,
	     	height:400
	    }, false); 
	}
</script>
</head>
<body>
	<div class="control-group">
		<form id="accountHandleForm" action="${ctx}/borrow/account/repayaccount/accountHandlePhoneSaleList" method="post">
			<input id="pageNo" name="pageNo" type="hidden"	value="${page.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" />
			
			<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td>
						<label class="lab">客户姓名：</label>
						<input type="text" class="input-medium" name="customerName" value="${repayAccountApplyView.customerName}"/>
					</td>
					<td>
						<label class="lab">合同编号：</label>
						<input type="text" class="input-medium" name="contractCode" value="${repayAccountApplyView.contractCode}"/>
					</td>
					<td>
						<label class="lab">还款日：</label>
						<select class="select180" id="repayDay" name="repayDay">
						<option value="">全部</option>
						<c:forEach items="${billDayList}" var="version">
								<option value="${version.billday}"
									<c:if test="${version.billday == repayAccountApplyView.repayDay}">
										selected = true
									</c:if>>
									${version.billday}
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
				
				  <c:if test="${isManager == false}">	  
					<td>
						<label class="lab">门店：</label> 
						<input type="text" id="txtStore" name="storeName" maxlength="20" class="txt date input_text178" 
								value="${repayAccountApplyView.storeName}" readonly/> 
						<input type="hidden" id="hdStore" name="ids" value="${repayAccountApplyView.ids}"/>
					</td>
				 </c:if>
				
				 <c:if test="${isManager == true}">	
					<td>
						<label class="lab">门店：</label> 
						<input type="text" id="txtStore" name="storeName" maxlength="20" class="txt date input_text178" 
								value="${repayAccountApplyView.storeName}" readonly/> 
						<i id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
						<input type="hidden" id="hdStore" name="ids" value="${repayAccountApplyView.ids}"/>
					</td>
					 </c:if>
					
					
					
					<td>
						<label class="lab">维护状态：</label> 
						<select class="select180" name="maintainStatus" id="maintainStatus">
							<option value="">全部</option>
							<c:forEach items="${fns:getDictList('jk_maintain_status')}" var="item">
								 <c:if test="${item.value !='3'}">
								<option value="${item.value}" <c:if test="${item.value eq cpcn.banknum}">selected</c:if>>${item.label}</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
					<td>
						<label class="lab">合同版本号：</label> 
		                 <select id="version" class="version" name="version">
									<option value="">全部</option>
		                     <c:forEach items="${fns:getDictList('jk_contract_ver')}" var="item">
							   <option value="${item.value}"
							   		<c:if test="${repayAccountApplyView.version==item.value}">
							     		 selected=true 
							   		</c:if> 
							 	 >${item.label}
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
	<div>
		<table id="tableList" class="table table-condensed table-striped">
			<thead>
				<tr>
					<th>合同编号</th>
					<th>客户姓名</th>
					<th>账号名称</th>
					<th>合同到期日</th>
					<th>首期还款日</th>
					<th>门店名称</th>
					<th>操作时间</th>
					<th>借款状态</th>
					<th>维护类型</th>
					<th>还款日</th>
					<th>模式</th>
					<th>渠道</th>
					<th>维护状态</th>
					<th>合同版本号</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${rasList}" var="item">
					<tr>
						<td>${item.contractCode}</td>
						<td>${item.customerName}</td>
						<td>${item.accountName}</td>
						<td><fmt:formatDate value="${item.contractEndDay}" type="date" pattern="yyyy-MM-dd"/></td>
						<td><fmt:formatDate value="${item.firstRepayDay}" type="date" pattern="yyyy-MM-dd"/></td>
						<td>${item.storeName}</td>
						<td>
							<c:if test="${item.maintainStatusName != '已维护'}">
								<fmt:formatDate value="${item.applyTime}" type="date" pattern="yyyy-MM-dd"/>
							</c:if>
							<c:if test="${item.maintainStatusName == '已维护'}">
								<fmt:formatDate value="${item.maintainTime}" type="date" pattern="yyyy-MM-dd"/>
							</c:if>
						</td>
						<td>${item.loanStatusName}</td>
						<td>${item.maintainTypeName}</td> 
						<td>${item.repayDay}</td>
						<td>
						<c:if test="${item.modelName=='CHP'}"></c:if>
						<c:if test="${item.modelName!='CHP'}">${item.modelName}</c:if></td>
						<td>${item.flagName}</td>
						<td>${item.maintainStatusName}</td>
						<td>${item.versionLabel}</td>
						<td>
							<button class="btn_edit" onclick="lookMessage('${item.id}')">查看</button>
							<button class="btn_edit" onclick="maintainHistory('${item.contractCode}')">维护历史</button>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="pagination">${page}</div>
</body>
</html>