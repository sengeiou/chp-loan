<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default"/>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context}/js/enter_select.js"></script>

<script type="text/javascript">
	$(document).ready(function(){
		// 初始化门店下拉框
		loan.getstorelsit("txtStore","hdStore");
		
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
		
		//验证审核表单
		$("#examineMessage").validate({
			rules : {
				"bankCheckResult" : {
					required : true
				}
			},
			messages : {
				"bankCheckResult" : {
					required : "<font color='red'>请选择审核结果</font>"
				}
			},
			errorPlacement : function(error, element) {
				if (element.is(":radio"))
					error.appendTo(element.parent());
				else if (element.is(":checkbox"))
					error.appendTo(element.parent().parent());
				else
					error.appendTo(element.parent());
			} 
		});
	});
	
	function page(n,s) {
		if (n) $("#pageNo").val(n);
		if (s) $("#pageSize").val(s);
		$("#accountManageForm").attr("action", "${ctx}/car/carBankInfo/carCustomerBankInfo/accountManageList");
// 		$("#accountManageForm").attr("action", "${ctx}/borrow/account/repayaccount/accountManageList");
		$("#accountManageForm").submit();
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
		$("#accountManageForm").submit();
	}
	
	function examine(id,coboFlag){
		$("#cobo").hide();
		url = "${ctx}/car/carBankInfo/carCustomerBankInfo/examineMessage?id=" + id;
		$.ajax({
			type : "POST",
			url : url,
			success : function(data){
				if (data != null){
					$("#id").val(data.id);
					$("#checkLoanCode").val(data.loanCode);
					$("#customerId").val(data.customerId);
					$("#oldBankAccountId").val(data.oldBankAccountId);
					$("#maintainType").val(data.dictMaintainTypeName);
					$("#openBankName").val(data.cardBankName);
					$("#contractCode").val(data.contractCode);
					$("#contractVersion").val(data.contractVersion);
					$("#updateType").val(data.updateType);
					$("#updateTypeName").val(data.updateTypeName);
					$("#customerEmail").val(data.customerEmail);
					$("#newEmail").val(data.newEmail);
					$("#newCustomerPhone").val(data.newCustomerPhone);
					$("#customerName").val(data.customerName);
					$("#customerCard").val(data.customerCertNum);
					$("#customerPhone").val(data.customerPhoneFirst);
					$("#accountName").val(data.bankAccountName);
					$("#account").val(data.bankCardNo);
					$("#deductPlatName").val(data.bankSigningPlatformName);
					$("#bankBranch").val(data.applyBankName);
					$("#provinceName").val(data.provinceName);
					$("#cityName").val(data.cityName);
					$("#accountFile").text(data.fileName);
					$("#emailAddress").hide();
					$("#phone").hide();
					$("#accountFile").attr("href", "${ctx}/car/carBankInfo/carCustomerBankInfo/downloadFile?id=" + data.fileId + "&name=" + data.fileName);
					if(data.israre == '1'){
						$("#israre").attr("checked",true);
					}else{
						$("#israre").attr("checked",false);
					}
					if(data.dictMaintainType == '0'){
						$("#accountName").css("color","red");
						$("#account").css("color","red");
						$("#openBankName").css("color","red");
						$("#bankBranch").css("color","red");
						$("#deductPlatName").css("color","red");
						$("#provinceName").css("color","red");
						$("#cityName").css("color","red");
						if(coboFlag == '1'){
							$("#cobo").show();
							$("#cEmail").hide();
							$("#cPhone").hide();
							$("#coboName").val(data.coboName);
							$("#coboCertNum").val(data.coboCertNum);
						}
					}else if(data.dictMaintainType == '1'){
						$("#accountName").removeAttr("style");
						$("#account").removeAttr("style");
						$("#openBankName").removeAttr("style");
						$("#bankBranch").removeAttr("style");
						$("#deductPlatName").removeAttr("style");
						$("#newCustomerPhone").removeAttr("style");
						$("#customerPhone").removeAttr("style");
						$("#newEmail").removeAttr("style");
						$("#customerEmail").removeAttr("style");
						$("#provinceName").removeAttr("style");
						$("#cityName").removeAttr("style");
						if(data.updateType == '1'){
							if(data.customerPhoneFirst != data.newCustomerPhone){
								$("#newCustomerPhone").css("color","red");
								$("#customerPhone").css("color","red");
							}
							$("#phone").show();
							if(coboFlag == '1'){
								$("#cobo").show();
								$("#cEmail").hide();
								$("#cPhone").show();
								$("#coboName").val(data.coboName);
								$("#coboCertNum").val(data.coboCertNum);
								$("#coboMobile").val(data.coboMobile);
								if(data.coboMobile != data.oldCoboMobile){
									$("#coboMobile").css("color","red");
								}
							}
						}
						if(data.updateType == '2'){
							if(data.bankCardNo != data.oldBankCardNo){
								$("#account").css("color","red");
							}
							if(data.applyBankName != data.oldApplyBankName){
								$("#bankBranch").css("color","red");
							}
							if(data.bankSigningPlatform != data.oldBankSigningPlatform){
								$("#deductPlatName").css("color","red");
							}

							if(coboFlag == '1'){
								$("#cobo").show();
								$("#cEmail").hide();
								$("#cPhone").hide();
								$("#coboName").val(data.coboName);
								$("#coboCertNum").val(data.coboCertNum);
							}
						}
						if(data.updateType == '3'){
							if(data.customerEmail != data.newEmail){
								$("#newEmail").css("color","red");
								$("#customerEmail").css("color","red");
							}
							$("#emailAddress").show();
							$("#phone").show();
							if(coboFlag == '1'){
								$("#cobo").show();
								$("#cEmail").show();
								$("#cPhone").hide();
								$("#coboName").val(data.coboName);
								$("#coboCertNum").val(data.coboCertNum);
								$("#coboEmail").val(data.coboEmail);
								if(data.coboEmail != data.oldCoboEmail){
									$("#coboEmail").css("color","red");
								}
							}
						}
					}
					showExamine();
				} else {
					art.dialog.alert("操作有误，没有查到数据！");
				}
			}
		});
	}
	
	function showExamine(){
		art.dialog({
		    content: document.getElementById("examineDiv"),
		    title:'还款账号审核',
		    fixed: true,
		    lock:true,
		    width:600,
		    height:400,
		    id: 'confirm',
		    okVal: '确认',
		    ok: function () {
				if($('input[name="bankCheckResult"]:checked ').val()=='1'){
					if($("#bankCheckDesc").val() == ''){
						$("#showMsg").html("请输入审核意见");
						return false;
					}
				}
				this.DOM.buttons[0].childNodes[0].disabled = true;
		    	var srcFormParam = $('#examineMessage');
				srcFormParam.submit();
				return false;
		    },
		    cancel: true
		});
	}
	
	function maintainHistory(loanCode){
		var url = "${ctx}/car/carBankInfo/carCustomerBankInfo/showMaintainHistory?loanCode=" + loanCode;
		art.dialog.open(url, {  
	        id: 'his',
	        title: '维护历史',
	        lock:true,
	        width:850,
	     	height:450
	    }, false); 
	}
	
	function lookMessage(id){
		var url = "${ctx}/car/carBankInfo/carCustomerBankInfo/showMessage?id=" + id;
		art.dialog.open(url, {  
	        id: 'look',
	        title: '查看',
	        lock:true,
	        width:1000,
	     	height:400
	    }, false); 
	}
	
</script>
</head>
<body>
	<div class="control-group">
		<form id="accountManageForm" action="${ctx}/car/carBankInfo/carCustomerBankInfo/accountManageList" method="post">
			<input id="pageNo" name="pageNo" type="hidden"	value="${page.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" />
			
			<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td>
						<label class="lab">客户姓名：</label>
						<input type="text" class="input-medium" name="customerName" value="${bankInfo.customerName}"/>
					</td>
					<td>
						<label class="lab">合同编号：</label>
						<input type="text" class="input-medium" name="contractCode"  style="width: 270px;" value="${bankInfo.contractCode}"/>
					</td>
					<td>
						<label class="lab">还款日：</label>
						<select class="select180" id="repayDay" name="repayDay">
						<option value="">全部</option>
						<c:forEach items="${billDayList}" var="version">
								<option value="${version.billday}"
									<c:if test="${version.billday == bankInfo.repayDay}">
										selected = true
									</c:if>>
									${version.billday}
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<label class="lab">门店：</label> 
						<input type="text" id="txtStore" name="storeName" maxlength="20" class="txt date input_text178" 
								value="${bankInfo.storeName}" readonly/> 
						<i id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
						<input type="hidden" id="hdStore" name="orgCode" value="${bankInfo.orgCode}"/>
					</td>
					<td>
						<label class="lab">维护状态：</label> 
						<select class="select180" name="dictMaintainStatus" id="dictMaintainStatus">
							<option value="">全部</option>
							<c:forEach items="${fns:getDictList('jk_maintain_status')}" var="item">
								 <c:if test="${item.value ne '3' && item.value ne '4'}">
								<option value="${item.value}" <c:if test="${item.value eq bankInfo.dictMaintainStatus}">selected</c:if>>${item.label}</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
					<td>
						<label class="lab">身份证号码：</label> 
						<input type="text" class="input-medium" name="customerCertNum" value="${bankInfo.customerCertNum}"/>
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
					<th>合同到期日</th>
					<th>门店名称</th>
					<th>操作时间</th>
					<th>借款状态</th>
					<th>维护类型</th>
					<th>还款日</th>
					<th>维护状态</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${page!=null && fn:length(page.list)>0}">
         		<c:set var="index" value="0"/>
					<c:forEach items="${page.list}" var="item">
         			<c:set var="index" value="${index+1}" />
					<tr>
						<td>${item.contractCode}</td>
						<td>${item.customerName}</td>
						<td><fmt:formatDate value="${item.contractEndDay}" type="date" pattern="yyyy-MM-dd"/></td>
						<td>${item.storeName}</td>
						<td>
							<c:if test="${item.modifyTime eq null}">
								<fmt:formatDate value="${item.createTime}" type="date" pattern="yyyy-MM-dd"/>
							</c:if>
							<c:if test="${item.modifyTime ne null}">
								<fmt:formatDate value="${item.modifyTime}" type="date" pattern="yyyy-MM-dd"/>
							</c:if>
						</td>
						<td>${item.loanStatus}</td>
						<td>${item.dictMaintainTypeName}</td> 
						<td>${item.repayDay}</td>
						<td>${item.dictMaintainStatusName}</td>
							<td>
								<button class="btn_edit" onclick="lookMessage('${item.id}')">查看</button>
								<c:if test="${item.dictMaintainStatus eq '0'}">
								  <button class="btn_edit" onclick="examine('${item.id}','${item.coboFlag}')">审核</button>
								</c:if>
								<button class="btn_edit" onclick="maintainHistory('${item.loanCode}')">维护历史</button>
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if
					test="${ page==null || fn:length(page.list)==0}">
					<tr>
						<td colspan="19" style="text-align: center;">没有待办数据</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>
	<c:if test="${page != null && fn:length(page.list)>0}">
		<div class="pagination">${page }</div>
	</c:if>
	<!-- 审核div -->
	<div id="examineDiv" class=" pb5 pt10 pr30 title ohide" style="display:none;margin-top: -33px;margin-bottom: -21px;margin-left: -2px;margin-right: -33px;" >
		<form id="examineMessage" class="validate" action="${ctx}/car/carBankInfo/carCustomerBankInfo/saveExamineResult" method="post" enctype="multipart/form-data">
			<input type="hidden" id="id" name="id"/>
			<input type="hidden" id="checkLoanCode" name="loanCode"/>
			<input type="hidden" id="oldBankAccountId" name="oldBankAccountId"/>
			<input type="hidden" id="updateType" name="updateType"/>
			<input type="hidden" id="customerId" name="customerId"/>
			<table class="table table-bordered table-condensed table-hover ">
				<tbody>
					<tr>
						<td>
							<label class="lab">合同编号：</label>
							<input type="text" class="input-medium" id="contractCode"  readonly="readonly" style="width: 270px;"/>
						</td>
						<td>
							<label class="lab">维护类型：</label>
							<input type="text" class="input-medium" id="maintainType" readonly="readonly"/>
						</td>
						<td  id="phone" style="display:none;">
							<label class="lab">新手机号：</label>
							<input type="text" class="input-medium" id="newCustomerPhone" name="newCustomerPhone" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td>
							<label class="lab">客户姓名：</label>
							<input type="text" class="input-medium" id="customerName" name="customerName" readonly="readonly"/>
						</td>
						<td>
							<label class="lab">客户身份证号：</label>
							<input type="text" class="input-medium" id="customerCard" name="customerCard" readonly="readonly"/>
						</td>
						<td>
							<label class="lab">客户手机号：</label>
							<input type="text" class="input-medium" id="customerPhone" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td>
							<label class="lab">账号姓名：</label>
							<input type="text" class="input-medium" id="accountName" name="accountName" readonly="readonly"/>
						</td>
						<td>
							<input type="checkbox" id="israre" disabled="disabled">
							是否生僻字</input>
						</td>
						<td>
							<label class="lab">划扣账号：</label>
							<input type="text" class="input-medium" id="account" name="account" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td>
							<label class="lab">开户行：</label>
							<input type="text" class="input-medium" id="openBankName" readonly="readonly"/>
						</td>
						<td>
							<label class="lab">开户行支行：</label>
							<input type="text" class="input-medium" id="bankBranch" name="bankBranch" readonly="readonly"/>
						</td>
						<td>
							<label class="lab">划扣平台：</label>
							<input type="text" class="input-medium" id="deductPlatName" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td>
							<label class="lab">省份：</label>
							<input type="text" class="input-medium" id="provinceName" readonly="readonly"/>
						</td>
						<td>
							<label class="lab">市：</label>
							<input type="text" class="input-medium" id="cityName" readonly="readonly"/>
						</td>
						<td>
							<label class="lab">合同版本号：</label>
							<input type="text" class="input-medium" id="contractVersion" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td>
							<label class="lab">邮箱地址：</label>
							<input type="text" class="input-medium" id="customerEmail" readonly="readonly"/>
						</td>
						<td id="emailAddress" style="display:none;">
							<label class="lab">新邮箱地址：</label>
							<input type="text" class="input-medium" id="newEmail" name="newEmail" readonly="readonly"/>
						</td>
						<td>
							<label class="lab">修改类型：</label>
							<input type="text" class="input-medium" id="updateTypeName" readonly="readonly"/>
						</td>
					</tr>
					<tr id="cobo">
						<td>
							<label class="lab">共借人姓名：</label>
							<input type="text" class="input-medium" id="coboName" readonly="readonly"/>
						</td>
						<td>
							<label class="lab">共借人身份证号：</label>
							<input type="text" class="input-medium" id="coboCertNum" readonly="readonly"/>
						</td>
						<td id="cEmail">
							<label class="lab">共借人邮箱：</label>
							<input type="text" class="input-medium" id="coboEmail" readonly="readonly"/>
						</td>
						<td id="cPhone">
							<label class="lab">共借人手机号码：</label>
							<input type="text" class="input-medium" id="coboMobile" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td>
							<label class="lab">上传附件：</label>
							<a class="input-medium" id="accountFile"></a>
						</td>
					</tr>
					<tr>
						<td colspan="3" align="left">
							<label class="lab">审核结果：</label>
							<label>
								<input type="radio" class="input-medium" name="bankCheckResult" value="2"/>通过
								<input type="radio" class="input-medium" name="bankCheckResult" value="1"/>拒绝
							</label>
						</td>
					</tr>
					<tr>
						<td colspan="3" align="left">
							<label class="lab">审核意见：</label>
							<textarea class="input-xlarge" id="bankCheckDesc" name="bankCheckDesc" rows="3" 
									maxlength="200" style="width: 706px; height: 30px;"></textarea>
							<font id="showMsg" color="red"></font>
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