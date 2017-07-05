<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="decorator" content="default" />
<title>金账户业务列表</title>
<script src="${context}/js/common.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		loan.getstorelsit("storesName", "storeOrgId");
		$("#clearBtn").click(
				function() {
					$(':input', '#disCardForm').not(':submit,:button, :reset')
							.val('').removeAttr('checked').removeAttr(
									'selected');
				});
		// 列表导出
		/* 2016-05-12 需求要求删除
		$("#dao").click(
				function() {
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
					}
					window.location.href = ctx
							+ "/borrow/trusteeship/business/exportBusiness?"
							+ "cid=" + cid + "&referralsDate="
							+ $("#referralsDate").val() + "&customerName="
							+ $("#customerName").val() + "&storeOrgId="
							+ $("#storeOrgId").val() + "&loanStatus="
							+ $("#loanStatus").val() + "&grantDate="
							+ $("#grantDate").val();
				});
		*/
		// 伸缩
		$("#showMore").click(function(){
			if(document.getElementById("T1").style.display=='none'){
				document.getElementById("showMore").src='../../../../static/images/down.png';
				document.getElementById("T1").style.display='';
				document.getElementById("T2").style.display='';
			}else{
				document.getElementById("showMore").src='../../../../static/images/up.png';
				document.getElementById("T1").style.display='none';
				document.getElementById("T2").style.display='none';
			}
		});
		//全选checkAll
		$("#checkAll").click(function (){
			var $checkBox = $(":input[name='checkBoxItem']"),count = 0,contract = 0.00,grant = 0.00, audit = 0.00;
			$checkBox.prop("checked",this.checked);
			if ($(this).is(":checked")) {
				$checkBox.each(function(){
					count += 1;
					contract += parseFloat($.isBlank($(this).attr("contractAmount")),10);
					grant += parseFloat($.isBlank($(this).attr("grantAmount")),10);
					audit += parseFloat($.isBlank($(this).attr("loanAmount")),10);
				});
			}
			setValue(count,contract,grant,audit);
		});
		$(":input[name='checkBoxItem']").click(function (){
			var $checkBox = $(":input[name='checkBoxItem']:checked"),$box = $(":input[name='checkBoxItem']");
			var contract = parseFloat($("#hiddenContract").val(),10),
			audit = parseFloat($("#hiddenAudit").val(),10),
			grant = parseFloat($("#deductGrant").val(),10),
			count = parseInt($("#hiddenCount").val(),10);
			
			$("#checkAll").prop("checked", $checkBox.length == $box.length ? true : false);
			if ($(this).is(":checked")) {
				count += 1;
				grant += parseFloat($.isBlank($(this).attr("grantAmount")),10);
				contract += parseFloat($.isBlank($(this).attr("contractAmount")),10);
				audit += parseFloat($.isBlank($(this).attr("loanAmount")),10);
			} else {
				if ($checkBox.length == 0) {
					count = 0,
					grant = contract = audit = 0.00;
				} else {
					count -= 1;
					grant -= parseFloat($.isBlank($(this).attr("grantAmount")),10);
					contract -= parseFloat($.isBlank($(this).attr("contractAmount")),10);
					audit -= parseFloat($.isBlank($(this).attr("loanAmount")),10);
				}
			}
			setValue(count,contract,grant,audit);
		});
		function setValue(count,contract,grant,audit) {
			$("#count").text(count);
			$("#contract").text(fmoney(contract,2));
			$("#grant").text(fmoney(grant,2));
			$("#audit").text(fmoney(audit,2));
			
			$("#hiddenCount").val(count);
			$("#hiddenContract").val(contract);
			$("#deductGrant").val(grant);
			$("#hiddenAudit").val(audit);
		}		
	});
	//页码跳转
	function page(n, s) {
		if (n)
			$("#pageNo").val(n);
		if (s)
			$("#pageSize").val(s);
		$("#disCardForm").attr("action", "${ctx}/borrow/trusteeship/business/init");
		$("#disCardForm").submit();
		return false;
	};
	//格式化，保留两个小数点
	function fmoney(s, n) {  
	    n = n > 0 && n <= 20 ? n : 2;  
	    s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";  
	    var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];  
	    t = "";  
	    for (i = 0; i < l.length; i++) {  
	        t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");  
	    }  
	    return t.split("").reverse().join("") + "." + r;  
	}
	;(function($){
		  $.isBlank = function(obj){
		    return(!obj || $.trim(obj) === "") ? 0 : obj;
		  };
	})(jQuery);	
</script>
</head>
<body>
	<div class="control-group">
		<form:form id="disCardForm"
			action="${ctx }/borrow/trusteeship/business/init"
			modelAttribute="params" method="post">
			<input type="hidden" id="userCode">
			<input type="hidden" id="pageNo" name="pageNo" value="${creditorBack.pageNo}" />
	   		<input type="hidden" id="pageSize" name="pageSize" value="${creditorBack.pageSize}" />
			<table class="table1" cellpadding="0" cellspacing="0" border="0"
				width="100%">
				<tr>
					<td><label class="lab">推介日期：</label><input type="text"
						class="Wdate input_text178" name="referralsDate"
						value="<fmt:formatDate value='${params.referralsDate}' pattern='yyyy-MM-dd'/>"
						onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" /></td>
					<td><label class="lab">客户姓名：</label><form:input type="text" class="input_text178" path="customerName"></form:input></td>
					<td><label class="lab">门店：</label> <input type="text"
						class="input_text178" name="storesName" id="storesName"
						readonly="readonly" value="${params.storesName }" /> <i
						id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
						<input type="hidden" name="storeOrgId" value="${params.storeOrgId}"
						id="storeOrgId" /></td>
				</tr>
				<tr>
					<td><label class="lab">借款状态：</label><form:select class="select180" path="loanStatus">
							<form:option value="">请选择</form:option>
							<c:forEach items="${fns:getDictList('jk_loan_apply_status')}"
								var="loan_type">
								<form:option value="${loan_type.value}">${loan_type.label}</form:option>
							</c:forEach>
						</form:select></td>
					<td><label class="lab">放款日期：</label><input type="text"
						class="Wdate input_text178" name="grantDate"
						value="<fmt:formatDate value='${params.grantDate}' pattern='yyyy-MM-dd'/>"
						onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" /></td>
					<td>
					<label class="lab">是否无纸化：</label>
					<form:select class="select180" path="yesNO">
						<form:option value="">请选择</form:option>
						<c:forEach items="${fns:getDictList('yes_no')}"
							var="yes_no">
							<form:option value="${yes_no.value}">${yes_no.label}</form:option>
						</c:forEach>
					</form:select></td>
				</tr>
			  <tr id="T1" style="display:none">
			  		<td><label class="lab">是否有保证人：</label><form:select class="select180" path="warrantor">
						<form:option value="">请选择</form:option>
						<c:forEach items="${fns:getDictList('yes_no')}"
							var="yes_no">
							<form:option value="${yes_no.value}">${yes_no.label}</form:option>
						</c:forEach>
					</form:select></td>
					<td><label class="lab">合同版本号：</label><form:select class="select180" path="contractVersion">
						<form:option value="">请选择</form:option>
						<c:forEach items="${fns:getDictList('jk_contract_ver')}"
							var="contract_vesion">
							<form:option value="${contract_vesion.value}">${contract_vesion.label}</form:option>
						</c:forEach>
					</form:select></td>
					<td>
					  <label class="lab">渠道：</label>
					    <form:select path="channel" class="select180">
						  <option value=''>全部</option>
						  <c:forEach items="${fns:getDictList('jk_channel_flag')}" var="mark">
							<c:if test="${mark.label!='金信' && mark.label!='TG' && mark.label!='XT01' }">
							  <option value="${mark.value}"
							  <c:if test="${params.channel == mark.value}">
								     selected=true
							  </c:if>>${mark.label}
							  </option>
							</c:if>
						  </c:forEach>
						</form:select>
					</td>
			  </tr>				
			</table>
			<div class="tright pr30 pb5">
				<input class="btn btn-primary" type="submit" value="搜索"
					id="searchBtn" />
				<button class="btn btn-primary" id="clearBtn">清除</button>
				<div style="float: left; margin-left: 45%; padding-top: 10px">
			      <img src="${ctxStatic }/images/up.png" id="showMore"></img>
			    </div>
			</div>
		</form:form>
	</div>
	<p class="mb5">
	
		<%-- <button class="btn btn-small" id="dao">导出Excel</button>--%>

		&nbsp;&nbsp;<label class="lab1"><span class="red">放款总笔数：</span></label><label id = "count"
			class="red">0</label><label class="red">笔</label> <input type="hidden" id="hiddenCount" value="0" />
			
		&nbsp;&nbsp;<label class="lab1"><span class="red">放款总金额：</span></label><label id = "grant"
		class="red"> 0.00</label> <label class="red">元</label> <input type="hidden" id="deductGrant" value="0">
		&nbsp;&nbsp;<label class="lab1"><span class="red">批借总金额：</span></label><label id = "audit"
			class="red">0.00</label><label class="red">元</label> <input
			type="hidden" id="hiddenAudit" value="0.00" />
		&nbsp;&nbsp;<label class="lab1"><span class="red">合同总金额：</span></label><label id = "contract"
			class="red">0.00</label><label class="red">元</label> <input
			type="hidden" id="hiddenContract" value="0.00" />
	</p>
	 <div>
   	 <sys:columnCtrl pageToken="gAccountBusinessList"></sys:columnCtrl>
   	</div>
	
		<table id="tableList"
			class="table  table-bordered table-condensed table-hover ">
			<thead>
				<tr>
					<th><input type = "checkbox" id="checkAll"/></th>
					<th>序号</th>
					<th>门店</th>
					<th>推介日期</th>
					<th>客户姓名</th>
					<th>身份证号</th>
					<th>开户行</th>
					<th>银行帐号</th>
					<th>批借金额</th>
					<th>合同金额</th>
					<th>外访费</th>
					<th>加急费</th>
					<th>放款金额</th>
					<th>合同版本号</th>
					<th>期限</th>
					<th>模式</th>
					<th>渠道</th>
					<th>状态</th>
					<th>放款日期</th>
					<th>是否无纸化</th>
					<th>是否有保证人</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:if
					test="${ creditorBack!=null && fn:length(creditorBack.list)>0}">
					<c:set var="index" value="0" />
					<c:forEach items="${creditorBack.list}" var="item" varStatus="xh">
						<c:set var="index" value="${index+1}" />
						<tr>
							<td>
								<input type="checkbox" name="checkBoxItem" contractAmount = '${item.contractAmount}'
                                       grantAmount='${item.grantAmount}' loanAmount ='${item.loanAuditAmount}'
                                       value='${item.applyId}'/>
							</td>
							<td>${xh.count+(creditorBack.pageSize*(creditorBack.pageNo-1))}</td>
							<td>${item.storesName}</td>
							<td><fmt:formatDate value="${item.referralsDate}"
									pattern='yyyy-MM-dd' /></td>
							<td>${item.customerName}</td>
							<td>${item.customerNum}</td>
							<td>${item.bankName}</td>
							<td>${item.bankAccount}</td>
							<td><fmt:formatNumber value='${item.loanAuditAmount}'
									pattern="#,#00.00" /></td>
							<td><fmt:formatNumber value='${item.contractAmount}'
									pattern="#,##0.00" /></td>		
							<td><fmt:formatNumber value='${item.feePetition}'
									pattern="#,#00.00" /></td>
							<td><fmt:formatNumber value='${item.feeExpedited}'
									pattern="#,#00.00" /></td>
							<td><fmt:formatNumber value='${item.grantAmount}'
									pattern="#,#00.00" /></td>
							<td>${item.contractVersionLabel}</td>
							<td>${item.loanMonths}</td>
							<td>${item.modelLabel}</td>
							<td>${item.channelLabel}
							</td>
							<td>${item.loanStatus }
							</td>
							<td><fmt:formatDate value="${item.grantDate}"
									pattern='yyyy-MM-dd' /></td>
							<td>
							   <c:if test="${item.yesNO == '1'}">是</c:if>
							   <c:if test="${item.yesNO != '1'}">否</c:if>
							</td>
							<td>
							  <c:if test="${item.warrantor == '1'}">是</c:if>
							  <c:if test="${item.warrantor != '1'}">否</c:if>
							</td>
							<td><button class="btn_edit" name="dealBtn"
									value='${item.applyId}'
									onclick="showAllHisByLoanCode('${item.loanCode}')">历史</button></td>
						</tr>

					</c:forEach>

				</c:if>

				<c:if
					test="${ creditorBack==null || fn:length(creditorBack.list)==0}">
					<tr>
						<td colspan="18" style="text-align: center;">没有待办数据</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>
	</div>
	<div class="pagination">${creditorBack}</div>
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