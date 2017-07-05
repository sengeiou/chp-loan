<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default"/>
<script type="text/javascript" src="${context}/js/serve/contractSendEmailCommon.js"></script>
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
		$("#sendStatus").val('');
		$("#model").trigger("change");
		$("#loanFlag").trigger("change");
		$("#sendStatus").trigger("change");
	}
	
	function page(n,s) {
		if (n) $("#pageNo").val(n);
		if (s) $("#pageSize").val(s);
		$("#alreadyHandleEmailForm").attr("action", "${ctx }/borrow/serve/customerServe/alreadyHandleEmailList");
		$("#alreadyHandleEmailForm").submit();
		return false;
	}
	
	function remarks(applyId, loanCode){
		$("#applyId").val(applyId);
		$("#loanCode").val(loanCode);
		$("#remarks").val("");
		$('#deleteItem').modal('toggle').css({
			width : '60%',   
			"margin-left" : (($(document).width() -  $("#inputExpressNumber").css("width").replace("px", "")) / 2),
			"margin-top" : (($(document).height() -  $("#inputExpressNumber").css("height").replace("px", "")) / 2)
		});
	}
	
	function saveRemarks(){
		var remarks = $("#remarks").val();
		if (remarks == null || remarks == ""){
			art.dialog.alert("请输入备注信息");
			return;
		}
		var applyId = $("#applyId").val();
		var loanCode = $("#loanCode").val();
		var fileType = $("#fileType").val();
		$.ajax({
			cache : false,
			type : "POST",
			url : "${ctx}/borrow/serve/customerServe/remarksItem",
			dataType : "json",
			data : {'applyId':applyId,'loanCode':loanCode,'remarks':remarks,'fileType':fileType},
			async : false,
			success : function(data) {
				if (data == "1"){
					art.dialog.alert("操作成功");
				} else {
					art.dialog.alert("操作失败");
				}
			}
		});
	}
	function settleProveLook(id){
		var url = "${ctx} /apply/contractAudit/writeTo?docId="+id;
	/* 	var url = ctx + "/borrow/serve/customerServe/settleProveLook?id=" + id; */
		art.dialog.open(url, {  
	        id: 'his',
	        title: '结清证明查看',
	        lock:true,
	        width:'100%',
	     	height:'100%'
	    }, false); 
	}
	function exports(){
		var idVal = "";
		if ($(":input[name='checkItem']:checked").length > 0) {
			$(":input[name='checkItem']:checked").each(
					function() {
						if (idVal != "") {
							idVal += ","
									+ $(this).attr(
											"value");
						} else {
							idVal = $(this)
									.attr("value");
						}
					});
		}
		$("#ids").val(idVal);
		document.getElementById("alreadyHandleEmailForm").action="${ctx}/borrow/serve/customerServe/exports";
		document.getElementById("alreadyHandleEmailForm").submit();
		document.getElementById("alreadyHandleEmailForm").action="${ctx}/borrow/serve/customerServe/alreadyHandleEmailList";
	}
</script>
</head>
<body>
	<div class = "control-group">
		<form id="alreadyHandleEmailForm" action="${ctx }/borrow/serve/customerServe/alreadyHandleEmailList" method="post">
			<input id="pageNo" name="pageNo" type="hidden"	value="${page.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" />
			<input id="ids" name="ids" type="hidden" />
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
						<label class="lab">发送状态：</label>
						<select class="select180" id="sendStatus" name="sendStatus">
							<option value="" <c:if test="${contractFileSendEmailView.sendStatus==null }">selected</c:if>>请选择</option>
							<option value="3" <c:if test="${contractFileSendEmailView.sendStatus==3 }">selected</c:if>>已发送</option>
							<option value="6" <c:if test="${contractFileSendEmailView.sendStatus==6 }">selected</c:if>>退回申请</option>
						</select>
					</td>
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
	<p class="mb5">
			<input type="button" onclick="javaScript:exports();" class="btn btn-small" value="导出Excel"></input>
	</p>
	<div>
		<table id="tableList" class="table  table-bordered table-condensed table-hover ">
			<thead>
				<tr>
					<th><input type="checkbox" id="checkAll" onclick="checkAll(this)"/>序号</th>
					<th>客户姓名</th>
					<th>合同编号</th>
					<th>门店名称</th>
					<th>门店申请日期</th>
					<th>借款产品</th>
					<th>借款期限</th>
					<th>合同金额</th>
					<th>合同到期日期</th>
					<th>结清日期</th>
					<th>借款状态</th>
					
					<th>邮箱</th>
					<th>发送状态</th>
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
							<td><input type="checkbox" name="checkItem" value="${item.id}">${index}</td>
							<td>${item.customerName}</td>
							<td>${item.contractCode}</td>
							<td>${item.storeName}</td>
							<td><fmt:formatDate value="${item.applyTime}" type="date" pattern="yyyy-MM-dd"/></td>
							<td>${item.productType}</td>
							<td>${item.loanMonths}</td>
							<td><fmt:formatNumber value="${item.contractAmount}" pattern="#,##0.00"/> </td>
							<td><fmt:formatDate value="${item.contractEndDay}" type="date" pattern="yyyy-MM-dd"/></td>
							<td><fmt:formatDate value="${item.settleDay}" type="date" pattern="yyyy-MM-dd"/></td>
							<td>${item.creditStatusName}</td>
							
							<td>${item.receiverEmail}</td>
							<td><c:if test="${item.sendStatus == '4'}">已发送</c:if><c:if test="${item.sendStatus == '6'}">退回申请</c:if></td>
							<td>${item.loanFlag}</td>
							<td>${item.model}</td>
							<td>
								<button class="btn_edit" onclick="settleProveLook('${item.pdfId}')">查看</button>
								<button class="btn_edit" onclick="remarks('${item.applyId}','${item.loanCode}')">备注</button>&nbsp;
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
	
	<div class="modal fade" id="deleteItem" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title">备注信息</h4>
				</div>
				<div class="box1 mb10">
					<input type="hidden" id="applyId" name="applyId"/>
					<input type="hidden" id="loanCode" name="loanCode"/>
					<table class="f14 table4" cellpadding="0" cellspacing="0" border="0" width="100%">
						<tr>
							<td>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<font color="red">备注后，可以在历史中查看</font><br />
								<label class="lab">备注:</label> 
								<textarea class="textarea_refuse" id="remarks" name="remarks" rows="3" 
											maxlength="200" style="width: 292px; height: 75px;"></textarea>
							</td>
						</tr>
					</table>
				</div>
				<div class="modal-footer">
					<a type="button" class="btn btn-primary" data-dismiss="modal" onclick="saveRemarks()">保存</a>
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