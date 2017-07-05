<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default"/>
<title>电子协议申请列表</title>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context}/js/enter_select.js"></script>
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
	});
	
	function page(n,s) {
		if (n) $("#pageNo").val(n);
		if (s) $("#pageSize").val(s);
		$("#eleAgreeForm").attr("action", "${ctx}/borrow/account/repayaccount/accountHandleList");
		$("#eleAgreeForm").submit();
		return false;
	}
	
	//点击清除按钮调用的的方法
	function resets(){
		$("#maintainStatus").val('');
		$("#maintainStatus").trigger("change");
		// 清除text	
		$(":text").val('');
		// 清除门店隐藏域
		$("#hdStore").val('');
		// 清除select	
		$("select").val("");
		$("#eleAgreeForm").submit();
	}
	
	//维护历史
	function agrHistory(contractCode){
		var url = "${ctx}/common/carHistory/showContractAgrHistory?contractCode=" + contractCode;
		art.dialog.open(url, {  
	        id: 'his',
	        title: '维护历史',
	        lock:true,
	        width:850,
	     	height:450
	    }, false); 
	}
	
	function checkAll(){
		if($('#checkAll').is(':checked')) { 
			$('input[name="checkBoxItem"]').attr("checked", true);
		}else{
			$('input[name="checkBoxItem"]').attr("checked", false);
		}
	}
	
	function batchSend(){
		var cid = "";
		if ($(":input[name='checkBoxItem']:checked").length > 0) {
			$(":input[name='checkBoxItem']:checked").each(
					function() {
						if (cid != "") {
							cid += "," + $(this).attr("cid");
						} else {
							cid = $(this).attr("cid");
						}
					});
		} else {
			alert("请选择要进行发送的数据！！！");
			return false;
		}
		art.dialog.confirm("确定要发送选中的数据吗",function (){
			window.location.href = ctx
					+ "/common/carHistory/updateSendOrReturn?"
					+ "ids=" + cid;
		});
	}
	
	//发送电子协议
	function send(id){
		var url = "${ctx}/common/carHistory/showCutomerMsg";
		$.ajax({
			type : "POST",
			url : url,
			data:{"id":id},
			success : function(data){
				$("#actId").val(data.id);
    			$("#applyLoanCustomerName").val(data.loanCustomerName);
    			$("#applyContractCode").val(data.contractCode);
    			$("#applyCustomerCertNum").val(data.customerCertNum);
    			$("#applyCustomerEmail").val(data.customerEmail);
    			$("#customerEmail").val(data.customerEmail);
    			if(data.urgentDegree == '1')
   				{
        			$("#applyUrgentDegree").val("紧急");
   				}else if(data.urgentDegree == '2'){
        			$("#applyUrgentDegree").val("正常");
   				}
    			$("#applyReason").val(data.applyReason);
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
	
	//预览
	function previewMsg(id){
		var url = "${ctx}/common/carHistory/showCutomerMsg?id=" + id;
		$.ajax({
			type : "POST",
			url : url,
			success : function(data){
				$("#mailAddress").html(data.customerEmail);
				$("#mailSubject").html(data.loanCustomerName+"的借款协议");
				 var msg = "尊敬的"+data.loanCustomerName+"客户您好：<br />"+
						  "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您的借款经信和汇金信息咨询（北京）有限公司、"+
						  "信和汇诚信用管理（北京）有限公司、信和惠民投资管理（北京）有限公司、"+
						  "信和财富投资管理（北京）有限公司撮合成功，"+
						  "协议编号为:"+data.contractCode+"的一系列电子协议已经以附件形式发送，请您查收。"+
						  "如有疑问可致电我司全国客服热线：400-090-1199。"+
						"<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;祝您生活愉快！";
				$("#mailContent").html(msg);
				$("#mailAddress").removeAttr("style");  
				$("#mailSubject").removeAttr("style");  
				$("#mailContent").removeAttr("style");  
    			art.dialog({
    			    content: document.getElementById("previewMessage"),
    			    title:'预览',
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

	function applyReturn(id,contractCode){
			$("#id").val(id);
			$("#contractCode").val(contractCode);
 			art.dialog({
 			    content: document.getElementById("returnMsg"),
 			    title:'退回',
 			    okVal: '确认',
 			    ok: function () {
 			    	var srcFormParam = $('#returnMsgMessage');
 					srcFormParam.submit();
 					return false;
 			    },
 			    cancel: true
 			});
	}

	 /**
	  * 弹出车借合同协议预览和下载 loanCode flag
	  */
	  function signDocShow(applyId,loanCode,contractCode){
			 var url =ctx+"/car/carContract/checkRate/initPreviewSignContract?contractCode="+contractCode;
			 art.dialog.open(url, {
				   id: 'protcl',
				   title: '协议查看',
				   lock:false,
				   width:1500,
				   height:600
				},false); 
		}
</script>
</head>
<body>
	<div class="control-group">
		<form id="eleAgreeForm" action="${ctx}/common/carHistory/applyAgreementList" method="post">
			<input id="pageNo" name="pageNo" type="hidden"	value="${page.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" />
			
			<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td>
						<label class="lab">客户姓名：</label>
						<input type="text" class="input-medium" name="loanCustomerName" value="${ex.loanCustomerName}"/>
					</td>
					<td>
						<label class="lab">门店：</label> 
						<input type="text" id="txtStore" name="storeName" maxlength="20" class="txt date input_text178" 
								value="${ex.storeName}" readonly/> 
						<i id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
						<input type="hidden" id="hdStore" name="storeId" value="${ex.storeId}"/>
					</td>
					<td>
						<label class="lab">合同编号：</label>
						<input type="text" class="input-medium" name="contractCode" value="${ex.contractCode}"/>
					</td>
				</tr>
				<tr>
					<td>
						<label class="lab">紧急程度：</label> 
						<select id="urgentDegree" name="urgentDegree" style="width:100px">
							<option value="">请选择</option>
							<option value="1" <c:if test="${ex.urgentDegree == '1'}">
										selected = true
									</c:if>>紧急</option>
							<option value="2" <c:if test="${ex.urgentDegree == '2'}">
										selected = true
									</c:if>>正常</option>
						</select>
					</td>
					<td>
						<label class="lab">身份证号码：</label> 
						<input type="text" class="input-medium" name="customerCertNum" value="${ex.customerCertNum}"/>
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
	</p>
	<div class="box5" style="position:relative;width:100%;overflow:hidden;height:60%;margin-bottom: 20px">
		<sys:columnCtrl pageToken="creditorw"></sys:columnCtrl>
		<table id="tableList" class="table table-condensed table-striped">
			<thead>
				<tr>
					<th><input type="checkbox" onclick="checkAll();" id="checkAll"/></th>
					<th>序号</th>
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
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${eleList!=null && fn:length(eleList.list)>0}">
         		<c:set var="index" value="0"/>
					<c:forEach items="${eleList.list}" var="item">
         			<c:set var="index" value="${index+1}" />
						<tr>
							<td>
								<input type="checkbox" name="checkBoxItem" 
								cid="${item.id }"  
								/>
							</td>
							<td>${index}</td>
							<td>${item.loanCustomerName}</td>
							<td>${item.contractCode}</td>
							<td>${item.storeName}</td>
							<td>${fncjk:getPrdLabelByTypeAndCode('products_type_car_credit',item.productTypeContract)}</td>
							<td>${item.contractMonths}</td>
             				<td><fmt:formatNumber value="${item.contractAmount}" type="number" pattern="0.00"/></td>
							<td><fmt:formatDate value="${item.contractEndDay}" type="date" pattern="yyyy-MM-dd"/></td>
							<td><fmt:formatDate value="${item.settledDate}" type="date" pattern="yyyy-MM-dd"/></td>
							<td>${item.dictLoanStatus}</td>
							<td>
								<c:if test="${item.urgentDegree eq '1'}">紧急</c:if>
								<c:if test="${item.urgentDegree eq '2'}">正常</c:if>
							</td>
							<td>
								<button class="btn_edit" onclick="send('${item.id }')">发送电子协议</button>
								<button class="btn_edit" onclick="applyReturn('${item.id}','${item.contractCode}')">退回</button>
								<button class="btn_edit" onclick="previewMsg('${item.id }')">预览</button>
								<button class="btn_edit" onclick="signDocShow('','${item.loanCode}','${item.contractCode}')">协议查看</button>
								<button class="btn_edit" onclick="agrHistory('${item.contractCode}')">维护历史</button>
							</td>
						</tr>
				</c:forEach>
				</c:if>
				<c:if
					test="${ eleList==null || fn:length(eleList.list)==0}">
					<tr>
						<td colspan="19" style="text-align: center;">没有待办数据</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>
	<div class="pagination">${eleList}</div>
	
	<!-- 发送电子协议 -->
	<div id="applyAgreement" class=" pb5 pt10 pr30 title ohide" style="display:none">
		<form id="applyAgreementMessage" class="validate" action="${ctx}/common/carHistory/updateSendOrReturn" method="post" enctype="multipart/form-data">
			<input type="hidden" id="actId" name="id"/>
			<input type="hidden" id="agreementType" name="agreementType" value="2"/>
			<input type="hidden" id="customerEmail" name="customerEmail"/>
			<table class="table table-bordered table-condensed table-hover ">
				<tbody>
					<tr>
						<td><label class="lab">客户姓名：</label></td>
						<td><input type="text" class="input-medium" id="applyLoanCustomerName" name="loanCustomerName" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label class="lab">合同编号：</label></td>
						<td><input type="text" class="input-medium" id="applyContractCode" name="contractCode" style="width: 270px;" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label class="lab">身份证号码：</label></td>
						<td>
							<input type="text" class="input-medium isEmailModify" id="applyCustomerCertNum" readonly="readonly"/></td>
						</td>
					</tr>
					<tr>
						<td><label class="lab">邮箱地址：</label></td>
						<td>
							<input type="text" class="input-medium isEmailModify" id="applyCustomerEmail" readonly="readonly"/></td>
						</td>
					</tr>
					<tr>
						<td><label class="lab">紧急程度：</label></td>
						<td>
							<input type="text" class="input-medium isEmailModify" id="applyUrgentDegree" readonly="readonly"/></td>
						</td>
					</tr>
					<tr>
						<td><label class="lab">申请理由：</label></td>
						<td>
							<textarea rows="20" cols="10"  id="applyReason" style="margin: 3px 0px; width: 305px; height: 68px;" disabled="disabled"></textarea>
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
						<td>
							<label id="mailContent"></label>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	
	<!-- 退回 -->
	<div id="returnMsg" class=" pb5 pt10 pr30 title ohide" style="display:none">
		<form id="returnMsgMessage" class="validate" action="${ctx}/common/carHistory/updateSendOrReturn" method="post">
			<input type="hidden" id="agreementType" name="agreementType" value="0"/>
			<input type="hidden" id="id" name="id"/>
			<input type="hidden" id="contractCode" name="contractCode"/>
			<table class="table table-bordered table-condensed table-hover ">
				<tbody>
					<tr>
						<td><label class="lab">退回理由：</label></td>
						<td>
							<textarea rows="20" cols="10"  id="dictDealReason" name="dictDealReason" style="margin: 3px 0px; width: 305px; height: 68px;""></textarea>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
</body>
</html>