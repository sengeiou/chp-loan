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

	//点击清除按钮调用的的方法
	function resets(){
		// 清除text	
		$(":text").val('');
		// 清除门店隐藏域
		$("#hdStore").val('');
		// 清除select	
		$("select").val("");
		$("#alreadyHandleForm").submit();
	}
	
	function page(n,s) {
		if (n) $("#pageNo").val(n);
		if (s) $("#pageSize").val(s);
		$("#alreadyHandleForm").attr("action", "${ctx }/borrow/serve/customerServe/alreadyHandleList");
		$("#alreadyHandleForm").submit();
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
	
	$(function(){
		document.onkeydown = function(e){
			var ev = document.all ? window.event : e;
			 if(ev.keyCode==13) {
		           $('#alreadyHandleForm').submit();//处理事件
		     }
		}
		}); 
</script>
</head>
<body>
	<div class = "control-group">
		<form id="alreadyHandleForm" action="${ctx }/borrow/serve/customerServe/alreadyHandleList" method="post">
			<input id="pageNo" name="pageNo" type="hidden"	value="${page.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" />
		<input type="hidden" class="input-medium" id="fileType" name="fileType" value="${contractFileSendView.fileType }"/></td>
		
				
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">客户姓名：</label>
					<input type="text" class="input-medium" name="customerName" value="${contractFileSendView.customerName }"/></td>
					<td>
						<label class="lab">门店：</label> 
						<input type="text" id="txtStore" name="storeName" readonly="readonly" class="txt date input_text178" value="${contractFileSendView.storeName}"/> 
						<i id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
						<input type="hidden" id="hdStore" name="orgCode" value="${contractFileSendView.orgCode}"/>
					</td>
					<td><label class="lab">合同编号：</label>
					<input type="text" class="input-medium" name="contractCode" value="${contractFileSendView.contractCode }"/></td>
				</tr>
				<tr>
					<td>
						<label class="lab">邮寄类别：</label>
						<select class="select180" name="fileType" disabled>
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
					<td><label class="lab">快递单号：</label>
					<input type="text" class="input-medium" name="expressNumber" value="${contractFileSendView.expressNumber}"/></td>
				</tr>
				<tr>
				     <td >
							<label class="lab">渠道：</label> 
							<select name="loanFlag"   class="select180"> 
                                    <option value="">请选择</option>
                                    <c:forEach items="${fns:getDictList('jk_channel_flag')}" var="str">
                                    	<option value="${str.value }" 
                                    	 <c:if test="${contractFileSendView.loanFlag==str.value }">selected</c:if>>${str.label }</option>
                                    </c:forEach>									
							</select>
						</td>
						<td><label class="lab">模式：</label> 
							<select name="model" class="select180">
								<option value=''>请选择</option>
								<c:forEach items="${fns:getDictList('jk_loan_model')}" var="loanmodel">
	                                   <option value="${loanmodel.value }" <c:if test="${contractFileSendView.model == loanmodel.value }">selected</c:if>>
	                                   <c:if test="${loanmodel.value=='0'}">
	                                   	非TG
	                                   </c:if>
	                                   <c:if test="${loanmodel.value!='0'}">${loanmodel.label}</c:if>
	                                   </option>
	                            </c:forEach>
	                     </select></td>
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
					<th>快递单号</th>
					<th>快递公司</th>
					<th>邮寄类别</th>
					<th>收件人姓名</th>
					<th>收件人电话</th>
					<th>收件人地址</th>
					<th>紧急程度</th>
					<th>
					<c:if test="${contractFileSendView.fileType != '0'}">
						邮寄状态
					</c:if>
					<c:if test="${contractFileSendView.fileType == '0'}">
						合同状态
					</c:if>
					</th>
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
							<td><fmt:formatDate value="${item.applyTime}" type="date" pattern="yyyy-MM-dd"/> </td>
							<td>${item.expressNumber}</td>
							<td>${item.sendCompanyLoabe}</td>
							<td>${item.fileTypeName}</td>
							<td>${item.receiverName}</td>
							<td>${item.receiverPhone}</td>
							<td>${item.receiverAddress}</td>
							<td>${item.emergentLevelName}</td>
							<td>${item.sendStatusName}</td>
							<td>${item.loanFlag}</td>
							<td>${item.model}</td>
							<td>
								<c:if test="${item.fileTypeName == '结清证明'}">
									<%-- <a class="btn_edit" href="${ctx}/borrow/serve/customerServe/downloadProof?pdfId=${item.docId}&name=${item.customerName}">下载</a>&nbsp; --%>
									<button class="btn_edit" onclick="settleProveLook('${item.docId}')">查看</button>&nbsp;
								</c:if>
								<button class="btn_edit" onclick="remarks('${item.applyId}','${item.loanCode}')">备注</button>&nbsp;
								<button class="btn_edit" onclick="doOpenhis('${item.loanCode}','${contractFileSendView.fileType }')">历史</button>
								<c:if test="${contractFileSendView.fileType == '0'}">
									<button class="btn_edit" onclick="doOpencheck('${item.id}','6','alreadyHandleForm')">退回</button>
								</c:if>
							</td>
						</tr>
					</c:forEach>
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