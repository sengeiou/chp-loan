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
		/* $("#examineMessage").validate({
			rules : {
				"maintainStatus" : {
					required : true
				}
			},
			messages : {
				"maintainStatus" : {
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
		}); */
	});
	
	function page(n,s) {
		if (n) $("#pageNo").val(n);
		if (s) $("#pageSize").val(s);
		$("#accountManageForm").attr("action", "${ctx}/borrow/account/repayaccount/emailManageList");
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
		url = "${ctx}/borrow/account/repayaccount/examineMessage?id=" + id;
		$.ajax({
			type : "POST",
			url : url,
			success : function(data){
				if (data != null){
					$("#id").val(data.id);
					$("#AfileId").val(data.fileId);
					$("#AfileName").val(data.fileName);
					$("#contractCode").val(data.contractCode);
					$("#maintainType").val(data.maintainType);
					$("#updatecontent").val(data.updatecontent);
					$("#customerEmail").val(data.customerEmail);
					$("#customerName").val(data.customerName);
					$("#customerCard").val(data.customerCard);
					$("#accountFile").text(data.fileName);
					$("#accountFile").attr("href", "${ctx}/borrow/account/repayaccount/downloadFile?id=" + data.fileId + "&name=" + data.fileName);
					$("#phoneFile").text(data.phoneFileName);
					$("#phoneFile").attr("href", "${ctx}/borrow/account/repayaccount/downloadFile?id=" + data.phoneFileId + "&name=" + data.phoneFileName);
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
		    height:350,
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
				
				this.DOM.buttons[0].childNodes[0].disabled = true;
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
		var url = "${ctx}/borrow/account/repayaccount/showMessage?id=" + id;
		art.dialog.open(url, {  
	        id: 'look',
	        title: '查看',
	        lock:true,
	        width:880,
	     	height:270
	    }, false); 
	}
	
</script>
</head>
<body>
	<div class="control-group">
		<form id="accountManageForm" action="${ctx}/borrow/account/repayaccount/emailManageList" method="post">
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
					<td>
						<label class="lab">还款日：</label>
						<select class="select180" id="repayDay" name="repayDay">
						<option value="">全部</option>
						<c:forEach items="${billDayList}" var="version">
								<option value="${version.billday}"
									<c:if test="${version.billday == repayAccountApplyView.repayDay}">
										selected = true
									</c:if>>
									${version.billday}
								</option>
							</c:forEach>
						</select>
<%-- 						<input type="text" class="input-medium" id="repayDay" name="repayDay" value="${repayAccountApplyView.repayDay}"/> --%>
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
							   		<c:if test="${repayAccountApplyView.maintainStatus==item.value}">
							     		 selected=true 
							   		</c:if> 
							 	 >${item.label}
							   </option>
							   </c:if>
						     </c:forEach>
						</select>
					</td>
					<td>
						<label class="lab">合同版本号：</label> 
		                 <select class="select180" name="version">
									<option value="">全部</option>
		                     <c:forEach items="${fns:getDictList('jk_contract_ver')}" var="item">
							   <option value="${item.value}"
							   		<c:if test="${repayAccountApplyView.version==item.value}">
							     		 selected=true 
							   		</c:if> 
							 	 >${item.label}
							   </option>
						     </c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td><label class="lab">模式：</label> 
						<select name="model" class="select180">
							<option value=''>请选择</option>
							<c:forEach items="${fns:getDictList('jk_loan_model')}" var="loanmodel">
	                                  <option value="${loanmodel.value }" <c:if test="${payback.model == loanmodel.value }">selected</c:if>>
	                                  <c:if test="${loanmodel.value=='0'}">
	                                  	非TG
	                                  </c:if>
	                                  <c:if test="${loanmodel.value!='0'}">${loanmodel.label}</c:if>
	                                  </option>
	                           </c:forEach>
	                      </select>
	             	</td>
	             	<td><label class="lab">渠道：</label> 
						<select class="select180" name="flag">
                			<option value="">请选择</option>
                              <c:forEach items="${fns:getDictList('jk_channel_flag')}" var="loanMark">
                                   <option value="${loanMark.value }" <c:if test="${repayAccountApplyView.flag == loanMark.value }">selected</c:if>>${loanMark.label }</option>
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
					<th>模式</th>
					<th>维护状态</th>
					<th>合同版本号</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${rasList}" var="item">
					<tr>
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
                          ${item.maintainTypeName}
						</td>
						<td>${item.repayDay}</td>
						<td>${item.flag}</td>
						<td>${item.model}</td>
						<td>${item.maintainStatusName}</td>
						<td>${item.versionLabel}</td>
						<td>
							<button class="btn_edit" onclick="lookMessage('${item.id}')">查看</button>
							<c:if test="${item.maintainStatusName == '待审核'}">
							  <button class="btn_edit" onclick="examine('${item.id}')">审核</button>
						    </c:if>
							<button class="btn_edit" onclick="maintainHistory('${item.contractCode}')">维护历史</button>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
	<c:if test="${rasList != null && fn:length(rasList)>0}">
		<div class="pagination">${page }</div>
	</c:if>
	
	<!-- 审核div -->
	<div id="examineDiv" class=" pb5 pt10 pr30 title ohide" style="display:none">
		<form id="examineMessage" class="validate" action="${ctx}/borrow/account/repayaccount/saveEmailExamineResult" method="post" enctype="multipart/form-data">
			<input type="hidden" id="id" name="id"/>
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
							<label class="lab">邮箱地址(新)：</label>
							<input type="text" class="input-medium" id="updatecontent" name="updatecontent"  readonly="readonly"/>
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
							<label class="lab">邮箱地址(旧)：</label>
							<input type="text" class="input-medium" id="customerEmail" name="customerEmail" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td>
							<label class="lab">上传附件：</label>
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
							<textarea class="input-xlarge" id="remarks" name="remarks" rows="3" 
									maxlength="200" style="width: 320px; height: 75px;"></textarea>
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