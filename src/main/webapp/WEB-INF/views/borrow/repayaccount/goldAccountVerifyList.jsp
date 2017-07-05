<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default"/>
<script type="text/javascript" src="${context}/js/common.js"></script>

<script type="text/javascript">
	$(document).ready(function(){
		// 初始化门店下拉框
		loan.getstorelsit("txtStore","hdStore");
		$('#exportExcel').bind('click',function(){
			download();
		});
	
      // 点击全选
		$("#checkAll").click(function() {
			if ($('#checkAll').attr('checked') == 'checked' || $('#checkAll').attr('checked')) {
				$(":input[name='checkBoxItem']").each(function() {
					$(this).attr("checked", 'true');
				});
			} else {
				$(":input[name='checkBoxItem']").each(function() {
					$(this).removeAttr("checked");
				});
			}
		});

		//确认成功
		$("#confirmSuccess").click(function() {
			var flag = confirm("提交确认成功?");
			if (flag) {
				var ids = "";
				$(":input[name='checkBoxItem']:checked").each(function() {
					if (ids != "") {
						ids += "," + $(this).val();
					} else {
						ids = $(this).val();
					}
				});
				if(ids == ""){
					art.dialog.alert("请选择数据!")
					return false;
				}
				$("#accountManageForm").attr("action", "${ctx}/borrow/account/repayaccount/goldConfirmSuccess?bankIds="+ids);
				$("#accountManageForm").submit();
			}
		});

		//失败/退回
		$("#failReturn").click(function() {
			var ids = "";
			$(":input[name='checkBoxItem']:checked").each(function() {
				if (ids != "") {
					ids += "," + $(this).val();
				} else {
					ids = $(this).val();
				}
			});
			if(ids == ""){
				art.dialog.alert("请选择数据!")
				return false;
			} 
			art.dialog({
			    content: document.getElementById("failReturnDiv"),
			    title:'失败/退回',
			    fixed: true,
			    lock:true,
			    width:300,
			    height:150,
			    id: 'confirm',
			    okVal: '确认',
			    ok: function () {
					if($("#msg").val() == ''){
						$("#showMsgA").html("请输入退回意见");
						return false;
					}
					this.DOM.buttons[0].childNodes[0].disabled = true
					$("#accountManageForm").attr("action", "${ctx}/borrow/account/repayaccount/goldFailReturn?bankIds="+ids+"&msg="+$("#msg").val());
					$("#accountManageForm").submit();
					
					return false;
			    },
			    cancel: true
			});
			
		});
	});
	
	function page(n,s) {
		if (n) $("#pageNo").val(n);
		if (s) $("#pageSize").val(s);
		$("#accountManageForm").attr("action", "${ctx}/borrow/account/repayaccount/goldAccountCheckList");
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
	
	function examine(id){
		url = "${ctx}/borrow/account/repayaccount/examineGoldAccount?maintainStatus=3&id=" + id;
		$.ajax({
			type : "POST",
			url : url,
			success : function(data){
				if (data != null){
					$("#id").val(data.id);
					$("#provinceName").val(data.provinceName);
					$("#cityName").val(data.cityName);
					$("#loanCode").val(data.loanCode);
					$("#deductPlat").val(data.deductPlat);
					$("#bankName").val(data.bankName);
					$("#oldAccountId").val(data.oldAccountId);
					$("#fileId").val(data.fileId);
					$("#fileName").val(data.fileName);
					$("#AfileId").val(data.fileId);
					$("#AfileName").val(data.fileName);
					$("#customerId").val(data.customerId);
					$("#contractCode").val(data.contractCode);
					$("#maintainType").val(data.maintainType);
					if (data.uptedaType == "0"){
						$("#phone").val("0");
						$("#newCustomerPhone").val(data.newCustomerPhone);
						$("#oldAccount").val(data.account);
						$("#newAccount").val("");
						$("#hiscustomerPhone").val(data.newCustomerPhone)
					} else {
						$("#phone").val("1");
						$("#newCustomerPhone").val("");
						$("#oldAccount").val(data.oldAccount);
						$("#newAccount").val(data.account);
						$("#hiscustomerPhone").val(data.customerPhone);
					}
					$("#hisAccount").val(data.account);
					$("#customerName").val(data.customerName);
					$("#customerCard").val(data.customerCard);
					$("#customerPhone").val(data.customerPhone);
					$("#accountName").val(data.accountName);
					$("#hisAccount").val(data.account);
					$("#deductPlatName").val(data.deductPlatName);
					$("#openBankName").val(data.openBankName);
					$("#bankBranch").val(data.bankBranch);
					$("#cityId").val(data.cityId);
					$("#hdloanBankbrId").val(data.hdloanBankbrId);
					$("#provinceId").val(data.provinceId);
					if (data.fileId != null && data.fileName != null){
						$("#accountFile").text(data.fileName);
						$("#accountFile").attr("href", "${ctx}/borrow/account/repayaccount/downloadFile?id=" + data.fileId + "&name=" + data.fileName);
					} else if(data.phoneFileId != null && data.phoneFileName != null){
						$("#accountFile").text(data.phoneFileName);
						$("#accountFile").attr("href", "${ctx}/borrow/account/repayaccount/downloadFile?id=" + data.phoneFileId + "&name=" + data.phoneFileName);
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
		    title:'审核',
		    fixed: true,
		    lock:true,
		    width:600,
		    height:400,
		    id: 'confirm',
		    okVal: '确认',
		    ok: function () {
		    	if($('input[name="maintainStatus"]:checked ').val()== undefined ){
					$("#showRadioMsg").html("请选择审核意见");
					return false;
				}
		    	if($('input[name="maintainStatus"]:checked ').val()=='1'){
					if($("#remarks").val() == ''){
						$("#showMsg").html("请输入审核意见");
						return false;
					}
				}
		    	this.DOM.buttons[0].childNodes[0].disabled = true
		    	var srcFormParam = $('#examineMessage');
				srcFormParam.submit();
				return false;
		    },
		    cancel: true
		});
	}
	
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
	
	function lookMessage(id){
		var url = "${ctx}/borrow/account/repayaccount/goldShowCheckMessage?maintainStatus=3&id=" + id;
		art.dialog.open(url, {  
	        id: 'look',
	        title: '查看',
	        lock:true,
	        width:900,
	     	height:400
	    }, false); 
	}
	function download(){
		var checkBoxItem = $("input[name='checkBoxItem']:checked");
		if(checkBoxItem.length==0){
			art.dialog.alert("请选择需要下载的数据");
			return false;
		}
		var i;
		var id = "";
		for(i=0;i<checkBoxItem.length;i++){
			if(id==""){
				id = checkBoxItem[i].value;
			}else{
				id+=","+checkBoxItem[i].value;
			}
		}
		var url="${ctx}/borrow/account/repayaccount/goldDownloadFile?id=" + id;
		window.location.href=url;
	}
</script>
</head>
<body>
	<div class="control-group">
		<form id="accountManageForm" action="${ctx}/borrow/account/repayaccount/goldAccountVerifyList" method="post">
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
				</tr>
				<tr>
					<td>
						<label class="lab">门店：</label> 
						<input type="text" id="txtStore" name="storeName" maxlength="20" class="txt date input_text178" 
								value="${repayAccountApplyView.storeName}"/> 
						<i id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
						<input type="hidden" id="hdStore" name="ids" value="${repayAccountApplyView.ids}"/>
					</td>
					<td>
						<label class="lab">维护状态：</label> 
						<select class="select180" name="maintainStatus">
							<option value="">全部</option>
							<c:forEach items="${fns:getDictList('jk_maintain_status')}" var="item">
							    <c:if test="${item.value !='3'}">
							   <option value="${item.value}"
							   		<%-- <c:if test="${repayAccountApplyView.maintainStatus==item.value}">
							     		 selected=true 
							   		</c:if> --%> 
							 	 >${item.label}
							   </option>
							   </c:if>
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
	</div>
		<p class="mb5">
		<input class="btn btn-small" id="exportExcel" name="exportExcel" type="button" value="下载"/>
		<input class="btn btn-small" id="confirmSuccess" name="confirmSuccess" type="button" value="确认成功"/>
		<input class="btn btn-small" id="failReturn" name="failReturn" type="button" value="失败/退回"/>
		</p>
	<div class="box5">
		<table id="tableList">
			<thead>
				<tr>
				   <th><input type="checkbox" id="checkAll"/></th>
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
					<th>渠道</th>
					<th>维护状态</th>
					<th>合同版本号</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${rasList}" var="item">
					<tr>
					  <td><input type="checkbox" name="checkBoxItem" id="checkBoxItem" value="${item.id}"/> </td>			            
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
						<%-- <td>${item.maintainTypeName}</td> --%>
						 <td>
						   <c:if test="${item.uptedaType eq '1'}">
								账户变更
							</c:if>
							<c:if test="${item.uptedaType eq '0'}">
								手机变更
							</c:if>
						 </td> 
						<td>${item.repayDay}</td>
						<td>${item.flagName}</td>
						<td>${item.maintainStatusName}</td>
						<td>${item.versionLabel}</td>
						<td>
							<button class="btn_edit" onclick="lookMessage('${item.id}')">查看</button>
							<button class="btn_edit" onclick="examine('${item.id}')">审核</button>
							<button class="btn_edit" onclick="maintainHistory('${item.contractCode}')">维护历史</button>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
		<div class="pagination">${page }</div>
	<!-- 审核div -->
	<div id="examineDiv" class=" pb5 pt10 pr30 title ohide" style="display:none">
		<form id="examineMessage" class="validate" action="${ctx}/borrow/account/repayaccount/saveGoldFirstCheck" method="post" enctype="multipart/form-data">
			<input type="hidden" id="id" name="id"/>
			<input type="hidden" id="loanCode" name="loanCode"/>
			<input type="hidden" id="deductPlat" name="deductPlat"/>
			<input type="hidden" id="bankName" name="bankName"/>
			<input type="hidden" id="phone" name="phone"/>
			<input type="hidden" id="oldAccountId" name="oldAccountId"/>
			<input type="hidden" id="AfileId" name="fileId"/>
			<input type="hidden" id="customerId" name="customerId"/>
			<input type="hidden" id="AfileName" name="fileName"/>
			<input type="hidden" id="cityId" name="cityId"/>
			<input type="hidden" id="provinceId" name="provinceId"/>
			<input type="hidden" id="hdloanBankbrId" name="hdloanBankbrId"/>
			<input type="hidden" id="hisAccount"  name="account" />
			<input type="hidden" id="hiscustomerPhone"  name="customerPhone" />
			
			<table class="table table-bordered table-condensed table-hover ">
				<tbody>
					<tr>
						<td>
							<label class="lab">合同编号：</label>
							<input type="text" class="input-medium" id="contractCode" name="contractCode" readonly="readonly"/>
						</td>
						<td>
							<label class="lab">维护类型：</label>
							<input type="text" class="input-medium" id="maintainType" readonly="readonly"/>
						</td>
						<td>
							<label class="lab">客户身份证号：</label>
							<input type="text" class="input-medium" id="customerCard" name="customerCard" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td>
							<label class="lab">客户姓名：</label>
							<input type="text" class="input-medium" id="customerName" name="customerName" readonly="readonly"/>
						</td>
						<td>
							<label class="lab">新手机号：</label>
							<input type="text" class="input-medium" id="newCustomerPhone" readonly="readonly"/>
						</td>
						<td>
							<label class="lab">原手机号：</label>
							<input type="text" class="input-medium" id="customerPhone" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td>
							<label class="lab">账号姓名：</label>
							<input type="text" class="input-medium" id="accountName" name="accountName" readonly="readonly"/>
						</td>
						
						<td>
							<label class="lab">新金账户账号：</label>
							<input type="text" class="input-medium" id="newAccount" name="newAccount" readonly="readonly"/>
						</td>
						<td>
							<label class="lab">原划扣账号：</label>
							<input type="text" class="input-medium" id="oldAccount" readonly="readonly"/>
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
					</tr>
					<tr>
						<td>
							<label class="lab">划扣平台：</label>
							<input type="text" class="input-medium" id="deductPlatName" readonly="readonly"/>
						</td>
						<td>
							<label class="lab">省份：</label>
							<input type="text" class="input-medium" id="provinceName" name="provinceName" readonly="readonly"/>
						</td>
						<td>
							<label class="lab">城市：</label>
							<input type="text" class="input-medium" id="cityName" name="cityName" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td>
							<label class="lab">附件：</label>
							<a class="input-medium" id="accountFile"></a>
						</td>
					</tr>
					<tr>
						<td colspan="3"><h6>审核结果</h6></td>
					</tr>
					<tr>
						<td colspan="3" align="left">
							<label class="lab">审核结果：</label>
							<input type="radio" name="maintainStatus" value="2"/>通过
							<input type="radio" name="maintainStatus" value="1"/>拒绝
							<font id="showRadioMsg" color="red"></font>
						</td>
					</tr>
					<tr>
						<td colspan="3" align="left">
							<label class="lab">审核意见：</label>
							<textarea class="input-xlarge" name="remarks" rows="3" id="remarks" 
									maxlength="200" style="width: 320px; height: 75px;"></textarea>
									<font id="showMsg" color="red"></font>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	
	
	<!-- 失败/退回div -->
	<div id="failReturnDiv" class=" pb5 pt10 pr30 title ohide" style="display:none">
		<table class="table table-bordered table-condensed table-hover ">
			<tbody>
				<tr>
					<td colspan="3" align="left">
						<label class="lab">审核意见：</label>
						<textarea class="input-xlarge" name="msg" rows="3" id="msg" 
								maxlength="200" style="width: 320px; height: 75px;"></textarea>
								<font id="showMsgA" color="red"></font>
					</td>
				</tr>
			</tbody>
		</table>
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