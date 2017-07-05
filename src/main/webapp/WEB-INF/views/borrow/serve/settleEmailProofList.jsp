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
		$("#settleProofForm").attr("action", "${ctx }/borrow/serve/customerServe/settleEmailProofList");
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
	function doOpenss(id, applyId, loanCode,contractCode,customerName,creditStatus,loanFlag) {
		var url = ctx + '/borrow/serve/customerServe/findServeConsult?id='
				+ id + "&applyId=" + applyId + "&loanCode="
				+ loanCode + "&contractCode="+ contractCode + "&creditStatus="+ creditStatus + "&loanFlag=" + loanFlag + "&type=email";
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
		<form id="settleProofForm" action="${ctx }/borrow/serve/customerServe/settleEmailProofList" method="post">
			<input id="pageNo" name="pageNo" type="hidden"	value="${page.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" />
		
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">客户姓名：</label>
					<input type="text" class="input-medium" name="customerName" value="${contractFileSendEmailView.customerName }"/></td>
					<td>
						<label class="lab">门店：</label> 
						<input type="text" id="txtStore" name="storeName" maxlength="20" class="txt date input_text178" value="${contractFileSendEmailView.storeName}" readonly/> 
						<i id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
						<input type="hidden" id="hdStore" name="orgCode" value="${contractFileSendEmailView.orgCode}"/>
					</td>
					<td><label class="lab">合同编号：</label>
					<input type="text" class="input-medium" name="contractCode" value="${contractFileSendEmailView.contractCode }"/></td>
				</tr>
				<tr>
					<td>
						<label class="lab">制作状态：</label>
						<select class="select180" name="sendStatus">
							<option value="" <c:if test="${contractFileSendEmailView.sendStatus == null}">
										selected = true
									</c:if>>请选择</option>
					        <option value="4" <c:if test="${contractFileSendEmailView.sendStatus == 4}">
										selected = true
									</c:if>>制作中</option>
					        <option value="5" <c:if test="${contractFileSendEmailView.sendStatus == 5}">
										selected = true
									</c:if>>制作失败</option>
						</select>
					</td>
					<td>
						<label class="lab">渠道：</label>
						<select class="select180" name="loanFlag">
							<option value="">全部</option>
							<c:forEach items="${fns:getDictList('jk_channel_flag')}" var="loanFlag">
								<option value="${loanFlag.value}"
								<c:if test="${contractFileSendEmailView.loanFlag == loanFlag.value}">
										selected = true
									</c:if>
								>
									${loanFlag.label}
								</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<label class="lab">模式：</label>
						<select class="select180" name="model">
							<option value="">全部</option>
							<c:forEach items="${fns:getDictList('jk_loan_model')}" var="model">
								<option value="${model.value}"
									<c:if test="${contractFileSendEmailView.model == model.value}">
										selected = true
									</c:if>>
									${model.label}
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
					<th>制作状态</th>
					<th>渠道</th>
					<th>模式</th>
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
							<td><c:if test="${item.sendStatus == 4}">制作中</c:if><c:if test="${item.sendStatus == '0'}">待制作</c:if><c:if test="${item.sendStatus == '5'}">制作失败</c:if></td>
							<td>${item.loanFlagName}</td>
							<td>${item.model}</td>
							<td>
								<c:if test="${item.sendStatus != '4'}">
								<c:if test="${item.docId == ''||item.docId == null}">
									<button class="btn_edit" onclick="doOpenss('${item.id}','${item.applyId}','${item.loanCode}','${item.contractCode}','${item.customerName}','${item.creditStatus}','${item.loanFlag}')">制作电子结清证明</button>&nbsp;
								</c:if>
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