<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default"/>
<script type="text/javascript" src="${context}/js/serve/contractSendEmailCommon.js"></script>
<script type="text/javascript" src="${context}/js/common.js"></script>
<style>
li {list-style-type:none;float:left;margin-left: 20px;}
.a_class{
	text-decoration: none;    
	text-align: center;
    line-height: 30px;}
</style>
<script type="text/javascript">
	$(document).eady(function(){
		// 初始化门店下拉框
		loan.getstorelsit("txtStore","hdStore");
		var msg = "${message}";
		if (msg != "" && msg != null) {
			top.$.jBox.tip(msg);
		};
	});

	function page(n,s) {
		if (n) $("#pageNo").val(n);
		if (s) $("#pageSize").val(s);
		$("#contractSendEmailForm").attr("action", "${ctx }/borrow/serve/customerServe/contractSendEmailList");
		$("#contractSendEmailForm").submit();
		return false;
	}
	
	//点击清除按钮调用的的方法
	function resets(){
		// 清除text	
		$("#customerName").val('');
		$("#hdStore").val('');
		$("#txtStore").val('');
		$("#contractCode").val('');
		$("#sendStatus").val('');
		$("#model").val('');
		$("#loanFlag").val('');
		$("#sendStatus").trigger("change");
		$("#model").trigger("change");
		$("#loanFlag").trigger("change");
		//$("#contractSendEmailForm").submit();
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
					url : ctx + "/borrow/serve/customerServe/updateSendOrReturns?" + "ids=" + id + "&sendStatus=6&emailTemplateType=2&fileType=0",
					success : function(data){
						art.dialog.alert(data,function(){
							$("#contractSendEmailForm").submit();
						});
					},
					error : function(msg){
						art.dialog.alert(msg);
					}
				});
			/* window.location.href = ctx
					+ "/borrow/serve/customerServe/updateSendOrReturn?"
					+ "ids=" + id + "&sendStatus=6&emailTemplateType=2&fileType=0"; */
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
				url : ctx + "/borrow/serve/customerServe/updateSendOrReturns?" + "ids=" + id + "&sendStatus=7",
				success : function(data){
					art.dialog.alert(data,function(){
						$("#contractSendEmailForm").submit();
					});
				},
				error : function(msg){
					art.dialog.alert(msg);
				}
			});
			/* window.location.href = ctx
					+ "/borrow/serve/customerServe/updateSendOrReturn?"
					+ "ids=" + id + "&sendStatus=7"; */
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
	/* function send(ids){
		if (window.confirm("确认发送吗？")){
			$.ajax({
				type : "post",
				url : "${ctx }/borrow/serve/customerServe/updateSendOrReturn?fileType=" + $("#fileType").val(),
				dataType : "json",
				data : {'ids':ids},
				async : false,
				success : function(msg){
					art.dialog.alert("发送成功");
					$("#contractSendEmailForm").submit();
				},
				error : function(msg){
					art.dialog.alert("发送失败");
				}
			});
		}
	} */
	//邮件预览
	function previewMsg(id){
		var url = "${ctx }/borrow/serve/customerServe/showCustomerMsg?ids=" + id + "&emailTemplateType=2";
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
				$("#ul").html("");
				if(null!=data.fileNameOne){
					$.each(data.fileNameOne,function(i,n){
				    	$("#ul").append('<li><a href="javaScript:void(0);" class="a_class" onclick=settleProveLook("'+n.docId+'","'+ n.contractFileName +'")>'+n.fileName+'</a></li>');
					})
				}
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
	function settleProveLook(id,title){
		var url = "${ctx}/apply/contractAudit/contractFilePreviewShow?docId="+id;
		art.dialog.open(url, {  
	        id: 'his',
	        title: title,
	        lock:true,
	        width:'100%',
	     	height:'100%'
	    }, false); 
	}	
</script>
</head>
<body>
	<div class = "control-group">
		<form id="contractSendEmailForm" action="${ctx }/borrow/serve/customerServe/contractSendEmailList" method="post">
			<input id="pageNo" name="pageNo" type="hidden"	value="${page.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" />
			<input id="ids" type="hidden" name="ids"/>
			<input type="hidden" name="excelFlag" value="1">
		
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">客户姓名：</label>
					<input type="text" class="input-medium" id="customerName" name="customerName" value="${contractFileSendEmailView.customerName }"/></td>
					<td>
						<label class="lab">门店：</label> 
						<input type="text" id="txtStore" name="storeName" readonly="readonly"  class="txt date input_text178" value="${contractFileSendEmailView.storeName}"/> 
						<i id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
						<input type="hidden" id="hdStore" name="orgCode" value="${contractFileSendEmailView.orgCode}"/>
					</td>
					<td><label class="lab">合同编号：</label>
					<input type="text" class="input-medium" id="contractCode" name="contractCode" value="${contractFileSendEmailView.contractCode }"/></td>
				</tr>
				<tr>
					<td>
						<label class="lab">合同状态：</label>
						<select class="select180" id="sendStatus" name="sendStatus">
							<option value="">全部</option>
								<option value="4"
									<c:if test="${contractFileSendEmailView.sendStatus =='4'}">
										selected = true
									</c:if>>
									电子协议待审核
								</option>
								<option value="5"
									<c:if test="${contractFileSendEmailView.sendStatus =='5'}">
										selected = true
									</c:if>>
									电子协议发送失败
								</option>
						</select>
					</td>
					<td >
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
		<input type="button" onclick="batchSend();" class="btn btn-small" value="批量发送"></input>
		<input type="button" onclick="batchReturn();" class="btn btn-small" value="批量退回"></input>
	</p>
	<sys:columnCtrl pageToken="outside"></sys:columnCtrl>
	<div class="box5">
		<table id="tableList" class="table  table-bordered table-condensed table-hover ">
			<thead>
				<tr>
					<th><input type="checkbox" id="checkAll" onclick="checkAll(this)"/>序号</th>
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
							<td><input type="checkbox" name="checkItem" value="${item.id}">${index}</td>
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
							<td>
								<c:if test="${item.sendStatus =='4'}">
										电子协议待审核
								</c:if>
								<c:if test="${item.sendStatus =='5'}">
										电子协议发送失败
								</c:if>
							</td>
							<td>${item.loanFlagName}</td>
							<td>${item.model}</td>
							<td>
								<c:if test="${item.loanFlag == '0' || item.loanFlag == '5'}">
								<c:if test="${item.pdfId != '' && item.pdfId != null}">
									<button class="btn_edit" onclick="send('${item.id }')">发送</button>
								</c:if>
								<c:if test="${item.pdfId == '' || item.pdfId == null}">
									<button class="btn_edit" style="color: #C8C8C8">发送</button>
								</c:if>
								</c:if>
								<c:if test="${item.loanFlag != '0' && item.loanFlag != '5'}">
									<button class="btn_edit" onclick="send('${item.id }')">发送</button>
								</c:if>
								<button class="btn_edit" onclick="previewMsg('${item.id }')">邮件预览</button>
								<button class="btn_edit" onclick="doOpencheck2('${item.id}','${item.applyId}','${item.loanCode}','7','${item.fileType}','contractSendEmailForm')">退回</button>
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
	
	<%-- <div class="modal fade" id="deleteItem" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
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
									<font color="red"></font><br />
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
	</div> --%>
	
	<!-- 发送电子协议 -->
	<div id="applyAgreement" class=" pb5 pt10 pr30 title ohide" style="display:none">
		<form id="applyAgreementMessage" class="validate" action="${ctx}/borrow/serve/customerServe/updateSendOrReturn" method="post" enctype="multipart/form-data">
			<input type="hidden" id="eid" name="id"/>
			<input type="hidden" id="sendStatus" name="sendStatus" value="6"/>
			<input type="hidden" id="receiverEmail" name="receiverEmail"/>
			<input type="hidden" id="loanFlag1" name="loanFlag"/>
			<input type="hidden" id="applyId" name="applyId"/>
			<input type="hidden" id="loanCode" name="loanCode"/>
			<input type="hidden" id="emailTemplateType" name="emailTemplateType" value="2"/>
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
						<tr>
							<td><label class="lab">邮件附件：</label></td>
							<td id="ul">
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