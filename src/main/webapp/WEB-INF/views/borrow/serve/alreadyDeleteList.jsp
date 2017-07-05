<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default"/>
<script type="text/javascript" src="${context}/js/serve/contractSendCommon.js"></script>
<script type="text/javascript" src="${context}/js/common.js"></script>

<script type="text/javascript">
	$(document).ready(function(){
		// 初始化门店下拉框
		loan.getstorelsit("txtStore","hdStore");
	});

	function page(n,s) {
		if (n) $("#pageNo").val(n);
		if (s) $("#pageSize").val(s);
		$("#alreadyDeleteForm").attr("action", "${ctx }/borrow/serve/customerServe/alreadyDeleteList");
		$("#alreadyDeleteForm").submit();
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
		$("#alreadyDeleteForm").submit();
	}
	
	function restore(id, applyId, loanCode, customerName){
		if (window.confirm("将还原客户 : " + customerName)){
			$.ajax({
				type : "post",
				url : "${ctx }/borrow/serve/customerServe/restoreItem",
				dataType : "json",
				data : {'id':id,'applyId':applyId,'loanCode':loanCode},
				success : function(msg){
					if (msg == "1"){
						art.dialog.alert("操作成功");
						$("#alreadyDeleteForm").submit();
					} else {
						art.dialog.alert("操作失败");
					}
				},
				error : function(msg){
					art.dialog.alert(msg);
				}
			});
		}
	}
	
	function permanentDelete(id, applyId, loanCode, customerName){
		if (window.confirm("确定永久删除'"+ customerName +"'吗？")){
			$.ajax({
				type : "post",
				url : "${ctx }/borrow/serve/customerServe/permanentDelete",
				dataType : "json",
				data : {'id':id,'applyId':applyId, 'loanCode':loanCode},
				success : function(msg){
					if (msg == "1"){
						art.dialog.alert("操作成功");
						$("#alreadyDeleteForm").submit();
					} else {
						art.dialog.alert("操作失败");
					}
				},
				error : function(msg){
					art.dialog.alert(msg);
				}
			});
		}
	}
</script>
</head>
<body>
	<div class = "control-group">
		<form id="alreadyDeleteForm" action="${ctx }/borrow/serve/customerServe/alreadyDeleteList" method="post">
			<input id="pageNo" name="pageNo" type="hidden"	value="${page.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" />
		
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">客户姓名：</label>
					<input type="text" class="input-medium" name="customerName" value="${contractFileSendView.customerName }"/></td>
					<td>
						<label class="lab">门店：</label> 
						<input type="text" id="txtStore" name="storeName" maxlength="20" class="txt date input_text178" value="${contractFileSendView.storeName}"/> 
						<i id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
						<input type="hidden" id="hdStore" name="orgCode" value="${contractFileSendView.orgCode}"/>
					</td>
					<td><label class="lab">合同编号：</label><input type="text" class="input-medium" name="contractCode" value="${contractFileSendView.contractCode }"/></td>
				</tr>
				<tr>
					<td>
						<label class="lab">邮寄类别：</label>
						<select class="select180" name="fileType">
							<option value="">全部</option>
							<c:forEach items="${fns:getDictList('jk_cm_admin_file_type')}" var="fType">
								<option value="${fType.value}"
									<c:if test="${contractFileSendView.fileType == fType.value}">
										selected = true
									</c:if>>
									${fType.label}
								</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<label class="lab">紧急程度：</label>
						<select class="select180" name="emergentLevel">
							<option value="">全部</option>
							<c:forEach items="${fns:getDictList('jk_cm_admin_urgent_flag')}" var="urgent">
								<option value="${urgent.value}"
									<c:if test="${contractFileSendView.emergentLevel == urgent.value}">
										selected = true
									</c:if>>
									${urgent.label}
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
					<th>客户姓名</th>
					<th>合同编号</th>
					<th>门店名称</th>
					<th>门店申请日期</th>
					<th>邮寄类别</th>
					<th>收件人姓名</th>
					<th>收件人电话</th>
					<th>收件人地址</th>
					<th>紧急程度</th>
					<th>合同状态</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${contractFileSendViewlist != null && fn:length(contractFileSendViewlist)>0}">
					<c:forEach items="${contractFileSendViewlist}" var="item">
						<tr>
							<td>${item.customerName}</td>
							<td>${item.contractCode}</td>
							<td>${item.storeName}</td>
							<td><fmt:formatDate value="${item.applyTime}" type="date" pattern="yyyy-MM-dd"/> </td>
							<td>${item.fileTypeName}</td>
							<td>${item.receiverName}</td>
							<td>${item.receiverPhone}</td>
							<td>${item.receiverAddress}</td>
							<td>${item.emergentLevelName}</td>
							<td>${item.sendStatusName}</td>
							<td>
								<button class="btn_edit" onclick="restore('${item.id}','${item.applyId}','${item.loanCode}','${item.customerName}')">还原</button>&nbsp;
								<button class="btn_edit" onclick="permanentDelete('${item.id}','${item.applyId}','${item.loanCode}','${item.customerName}')">删除</button>&nbsp;
								<button class="btn_edit" onclick="doOpenhis('${item.applyId}','${contractFileSendView.fileType }')">历史</button>
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${contractFileSendViewlist == null || fn:length(contractFileSendViewlist)==0}">
					<tr>
						<td colspan="11" style="text-align: center;">没有待办数据</td>
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