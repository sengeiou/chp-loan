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
		$("#settleProofForm").attr("action", "${ctx }/borrow/serve/customerServe/settleProofList");
		$("#settleProofForm").submit();
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
		$("#settleProofForm").submit();
	}
	
	   //打开制作结清证明装口
	function doOpenss(id, applyId, loanCode,contractCode,customerName,creditStatus) {
		var url = ctx + '/borrow/serve/customerServe/findServeConsult?id='
				+ id + "&applyId=" + applyId + "&loanCode="
				+ loanCode + "&contractCode="+ contractCode + "&creditStatus="+ creditStatus;
		art.dialog.open(url, {
			id:"id",
			title : '制作结清通知书 ：'+ customerName,
			width : 800,
			lock:true,
			height : 250,
			close:function()
			{
				$("form:first").submit();
				}
			});
	}
	
	
	function makeSettleProve(id, applyId, loanCode,contractCode){
		if(window.confirm("确定制作结清证明吗？")){
			$.ajax({
				cache : false,
				type : "POST",
				url : "${ctx }/borrow/serve/customerServe/makeSettleProve",
				dataType : "json",
				data : {'id':id, 'applyId':applyId, 'loanCode':loanCode},
				async : false,
				success : function(data){
					if (data == "1"){
						art.dialog.alert("操作成功");
						$("#settleProofForm").submit();
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
		<form id="settleProofForm" action="${ctx }/borrow/serve/customerServe/settleProofList" method="post">
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
					<td><label class="lab">合同编号：</label>
					<input type="text" class="input-medium" name="contractCode" value="${contractFileSendView.contractCode }"/></td>
				</tr>
				<tr>
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
					<th>借款产品</th>
					<th>借款期限</th>
					<th>合同金额</th>
					<th>合同到期日期</th>
					<th>结清日期</th>
					<th>借款状态</th>
					<th>紧急程度</th>
					<th>制作状态</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
			<c:if test="${page.list != null && fn:length(page.list)>0}">
			          <c:forEach items="${page.list}" var="item">
						<tr>
							<td>${item.customerName}</td>
							<td>${item.contractCode}</td>
							<td>${item.storeName}</td>
							<td>${item.productType}</td>
							<td>${item.loanMonths}</td>
							<td><fmt:formatNumber value='${item.contractAmount}' pattern="#,##0.00"/></td>
							<td><fmt:formatDate value="${item.contractEndDay}" type="date" pattern="yyyy-MM-dd"/></td>
							<td><fmt:formatDate value="${item.settleDay}" type="date" pattern="yyyy-MM-dd"/></td>
							<td>${item.creditStatusName}</td>
							<td>${item.emergentLevelName}</td>
							<td>${item.sendStatusName}</td>
							<td>
								<c:if test="${item.sendStatus != '4'}">
									<button class="btn_edit" onclick="doOpenss('${item.id}','${item.applyId}','${item.loanCode}','${item.contractCode}','${item.customerName}','${item.creditStatus}')">制作结清证明</button>&nbsp;
								</c:if>
								<button class="btn_edit" onclick="doOpenhis('${item.loanCode}','1')">历史</button>
							</td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
	</div>
	<c:if test="${page.list != null && fn:length(page.list) > 0}">
		<div class="pagination">${page }</div>
	</c:if>
	
	<div class="modal fade" id="deleteItem" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title">删除数据</h4>
				</div>
				<div class="box1 mb10">
					<form id="delForm" action="${ctx }/borrow/serve/customerServe/deleteItem" method="post">
						<input type="hidden" id="id" name="id"/>
						<input type="hidden" id="applyId" name="applyId"/>
						<input type="hidden" id="loanCode" name="loanCode"/>
						<input type="hidden" id="operateStep" name="operateStep"/>
						<input type="hidden" id="formId">
						<table class="f14 table4" cellpadding="0" cellspacing="0" border="0" width="100%">
							<tr>
								<td>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<font color="red">删除后，可以在已删除合同列表查询</font><br />
									<label class="lab">删除原因:</label> 
									<textarea class="input-xlarge" id="delReason" name="remarks" rows="3" 
												maxlength="200" style="width: 292px; height: 75px;vertical-align:top"></textarea>
								</td>
							</tr>
						</table>
					</form>
				</div>
				<div class="modal-footer">
					<a type="button" class="btn btn-primary" data-dismiss="modal">取消</a>
					<a type="button" class="btn btn-primary" data-dismiss="modal" onclick="confirmDel()">确定删除</a>
				</div>
			</div>
		</div>
	</div>
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