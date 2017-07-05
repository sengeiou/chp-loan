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
		
		var msg = "${message}";
		if (msg != "" && msg != null) {
			top.$.jBox.tip(msg);
		};
		
		$("#confirmInput").click(function(){
			var expressNumber = $("#expressNumber").val();
			var sendCompany = $("#sendCompany").val();
			if (expressNumber == null || expressNumber == ""){
				art.dialog.alert("请输入快递单号");
				return;
			}
			if (sendCompany == null || sendCompany == ""){
				art.dialog.alert("请选择快递公司");
				return;
			}
			$.ajax({
				cache : false,
				type : "POST",
				url : "${ctx }/borrow/serve/customerServe/inputExpressNumber",
				dataType : "json",
				data : $("#expressForm").serialize(),
				async : false,
				success : function(data) {
	 				if (data == "1"){
		 				art.dialog.alert("录入成功");
						$("#waitSendForm").submit();
	 				} else {
	 					art.dialog.alert("录入失败");
	 				}
				}
			});
		});
	});
	
	function page(n,s) {
		if (n) $("#pageNo").val(n);
		if (s) $("#pageSize").val(s);
		$("#waitSendForm").attr("action", "${ctx}/borrow/serve/customerServe/waitSendList");
		$("#waitSendForm").submit();
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
		$("#waitSendForm").submit();
	}
	
	function checkAll(obj){
		if(obj.checked){
			$("input[name='checkItem']").attr('checked', true)
	    }else{
			$("input[name='checkItem']").attr('checked', false)
		}
	}
	
	//批量发送
	function batchSend(){
		var id = "";
		if ($(":input[name='checkItem']:checked").length > 0) {
			$(":input[name='checkItem']:checked").each(
					function() {
						if (id != "") {
							id += "," + $(this).attr("value");
						} else {
							id = $(this).attr("value");
						}
					});
		} else {
			alert("请选择要进行发送的数据！！！");
			return false;
		}
		 art.dialog.confirm("确定要发送选中的数据吗",function (){
			 $.ajax({
				 	cache : false,
					type : "POST",
					async : false,
					url : ctx + "/borrow/serve/customerServe/jqUpdateSendOrReturns?" + "ids=" + id + "&sendStatus=4&emailTemplateType=1&fileType=1",
					success : function(data){
						art.dialog.alert(data,function(){
							$("#waitSendForm").submit();
						});
					},
					error : function(msg){
						art.dialog.alert(msg);
					}
				});
			/* window.location.href = ctx
					+ "/borrow/serve/customerServe/jqUpdateSendOrReturn?"
					+ "ids=" + id + "&sendStatus=4&emailTemplateType=1&fileType=1"; */
		}); 
	}
	
    //批量退回
	function batchReturn(){
		var id = "";
		if ($(":input[name='checkItem']:checked").length > 0) {
			$(":input[name='checkItem']:checked").each(
					function() {
						if (id != "") {
							id += "," + $(this).attr("value");
						} else {
							id = $(this).attr("value");
						}
					});
		} else {
			alert("请选择要进行退回的数据！！！");
			return false;
		}
		art.dialog.confirm("确定要发送退回的数据吗",function (){
			$.ajax({
			 	cache : false,
				type : "POST",
				async : false,
				url : ctx + "/borrow/serve/customerServe/jqUpdateSendOrReturns?" + "ids=" + id + "&sendStatus=3&fileType=1",
				success : function(data){
					art.dialog.alert(data,function(){
						$("#waitSendForm").submit();
					});
				},
				error : function(msg){
					art.dialog.alert(msg);
				}
			});
			/* window.location.href = ctx
					+ "/borrow/serve/customerServe/jqUpdateSendOrReturn?"
					+ "ids=" + id + "&sendStatus=3"; */
		});
	}
	//发送电子协议
	function send(id){
		var url = "${ctx}/borrow/serve/customerServe/showCustomerMsg?ids="+id;
		$.ajax({
			type : "POST",
			url : url,
			success : function(data){
				$("#eid").val(data.id);
    			$("#customerName1").val(data.customerName);
    			$("#contractCode1").val(data.contractCode);
    			$("#applyReceiverEmail").val(data.receiverEmail);
    			$("#receiverEmail").val(data.receiverEmail);
    			$("#loanFlag1").val(data.loanFlag);
    			$("#fileType1").val(data.fileType);
    			$("#applyId").val(data.applyId);
    			$("#loanCode").val(data.loanCode);
    			$("#pdfId").val(data.pdfId);
    			art.dialog({
    			    content: document.getElementById("applyAgreement"),
    			    title:'申请电子协议',
    			    fixed: true,
    			    lock:true,
    			    width:450,
    			    height:200,
    			    id: 'confirm',
    			    okVal: '确认',
    			    ok: function () {
    			    	var srcFormParam = $('#applyAgreementMessage');
    					srcFormParam.submit();
    					return false;
    			    },
    			    cancel: true
    			});
			}
		});
	}
	//邮件预览
	function previewMsg(id){
		var url = "${ctx }/borrow/serve/customerServe/showCustomerMsg?ids=" + id + "&emailTemplateType=1";
		$.ajax({
			type : "POST",
			url : url,
			dataType : "json",
			success : function(data){
				$("#mailAddress").html(data.receiverEmail);
				//$("#mailSubject").html(data.customerName+"的借款协议");
				$("#mailSubject").html(data.subject);
				 /* var msg = "尊敬的"+data.customerName+"客户您好：<br />"+
						  "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您的借款经信和汇金信息咨询（北京）有限公司、"+
						  "信和汇诚信用管理（北京）有限公司、信和惠民投资管理（北京）有限公司、"+
						  "信和财富投资管理（北京）有限公司撮合成功，"+
						  "协议编号为:"+data.contractCode+"的一系列电子协议已经以附件形式发送，请您查收。"+
						  "如有疑问可致电我司全国客服热线：400-090-1199。"+
						"<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;祝您生活愉快！"; */
				$("#mailContent").html(data.emailContent);
				$("#mailAddress").removeAttr("style");  
				$("#mailSubject").removeAttr("style");  
				$("#mailContent").removeAttr("style");  
    			art.dialog({
    			    content: document.getElementById("previewMessage"),
    			    title:'邮件预览',
    			    fixed: true,
    			    lock:true,
    			    width:700,
    			    height:200,
    			    id: 'confirm',
    			    cancel: true
    			});
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
</script>
</head>
<body>
	<div class = "control-group">
		<form id="waitSendForm" action="${ctx }/borrow/serve/customerServe/waitSendEmailList" method="post">
			<input id="pageNo" name="pageNo" type="hidden"	value="${page.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" />
			<input id="ids" type="hidden" name="ids"/>
			<input type="hidden" name="excelFlag" value="2">
			
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
						<label class="lab">发送状态：</label>
						<select class="select180" name="sendStatus">
							<option value="" <c:if test="${contractFileSendEmailView.sendStatus == null}">
										selected = true
									</c:if>>请选择</option>
							<option value="2" <c:if test="${contractFileSendEmailView.sendStatus == '2'}">
										selected = true
									</c:if>>待发送</option>
							<option value="9" <c:if test="${contractFileSendEmailView.sendStatus == '9'}">
										selected = true
									</c:if>>发送失败</option>
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
	
	<p class="mb5">
			<input type="button" onclick="batchSend();" class="btn btn-small" value="批量发送"></input>&nbsp;
			<input type="button" onclick="batchReturn();" class="btn btn-small" value="批量退回"></input>
			<form id="importExcel" style="display:none;" action="${ctx }/borrow/serve/customerServe/importExcel" method="post" enctype="multipart/form-data">
				<input type="file" id="file" name="file" onchange="fileChange()" style="position:relative; left:-75px; width:30px; 
						overflow:hidden; filter:alpha(opacity=0); opacity:0;cursor:hand;" size="1" />
						<input type="hidden" name="fileType" value="${contractFileSendView.fileType }">
			</form>
	</p>
	<sys:columnCtrl pageToken="outside"></sys:columnCtrl>
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
					<th>合同签订日期</th>
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
							<td><fmt:formatDate value="${item.contractFactDay}" type="date" pattern="yyyy-MM-dd"/></td>
							<td><fmt:formatDate value="${item.settleDay}" type="date" pattern="yyyy-MM-dd"/></td>
							<td>${item.creditStatusName}</td>
							<td>${item.receiverEmail}</td>
							<td><c:if test="${item.sendStatus == '2'}">待发送</c:if><c:if test="${item.sendStatus == '9'}">发送失败</c:if></td>
							<td>${item.loanFlag}</td>
							<td>${item.model}</td>
							<td>
								<button class="btn_edit" onclick="send('${item.id }')">发送</button>&nbsp;
                                <c:if test="${item.fileTypeName == '结清证明'}">
									<a class="btn_edit" href="${ctx}/borrow/serve/customerServe/downloadProof?pdfId=${item.pdfId}&name=${item.customerName}">下载</a>&nbsp;
									<button class="btn_edit" onclick="previewMsg('${item.id }')">邮件预览</button>&nbsp;
								</c:if>
								<button class="btn_edit" onclick="settleProveLook('${item.pdfId}')">查看</button>
							    <button class="btn_edit" onclick="doOpencheck('${item.id}','${item.applyId}','${item.loanCode}','3','1','waitSendForm')">退回</button>
								<button class="btn_edit" onclick="doOpenhis('${item.loanCode}','${contractFileSendView.fileType }')">历史</button>
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
	
	<!-- 预览 -->
	<div id="preview" class=" pb5 pt10 pr30 title ohide" style="display:none">
			<form id="previewMessage" class="validate" action="${ctx}/common/carHistory/updateSendOrReturn" method="post" enctype="multipart/form-data">
				<table class="table-bordered table-condensed table-hover ">
					<tbody>
						<tr>
							<td><label class="lab">邮件地址：</label></td>
							<td>
								<label id="mailAddress"></label>
							</td>
						</tr>
						<tr>
							<td><label class="lab">邮件主题：</label></td>
							<td>
								<label id="mailSubject"></label>
							</td>
						</tr>
						<tr>
							<td><label class="lab">邮件内容：</label></td>
							<td id="mailContent">
								
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		
	<!-- 发送电子协议 -->
	<div id="applyAgreement" class=" pb5 pt10 pr30 title ohide" style="display:none">
		<form id="applyAgreementMessage" class="validate" action="${ctx}/borrow/serve/customerServe/jqUpdateSendOrReturn" method="post" enctype="multipart/form-data">
			<input type="hidden" id="eid" name="id"/>
			<input type="hidden" id="sendStatus" name="sendStatus" value="4"/>
			<input type="hidden" id="receiverEmail" name="receiverEmail"/>
			<input type="hidden" id="loanFlag1" name="loanFlag"/>
			<input type="hidden" id="applyId" name="applyId"/>
			<input type="hidden" id="loanCode" name="loanCode"/>
			<input type="hidden" id="emailTemplateType" name="emailTemplateType" value="1"/>
			<input type="hidden" id="fileType1" name="fileType"/>
			<input type="hidden" id="pdfId" name="pdfId"/>
			<table class="table table-bordered table-condensed table-hover ">
				<tbody>
					<tr>
						<td><label class="lab">客户姓名：</label></td>
						<td><input type="text" class="input-medium" id="customerName1" name="customerName" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label class="lab">合同编号：</label></td>
						<td><input type="text" class="input-medium" id="contractCode1" name="contractCode" style="width: 270px;" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label class="lab">邮箱地址：</label></td>
						<td>
							<input type="text" class="input-medium isEmailModify" id="applyReceiverEmail" readonly="readonly"/></td>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	 <script type="text/javascript">

	  $("#tableList").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
		$('#tableList').bootstrapTable({
			method: 'get',
			cache: false,
			height: $(window).height()-230,
			pageSize: 20,
			pageNumber:1
		});
		$(window).resize(function () {
			$('#tableList').bootstrapTable('resetView');
		});
	</script>
</body>
</html>