<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="decorator" content="default" />
<title>金信业务列表</title>
<script src="${context}/js/common.js" type="text/javascript"></script>
<script type="text/javascript" src="${context}/js/channel/goldcredit/popuplayer.js"></script>
<script type="text/javascript">
function page(n, s) {
	if (n)
		$("#pageNo").val(n);
	if (s)
		$("#pageSize").val(s);
	$("#disCardForm").attr("action","${ctx}/channel/goldcredit/business/init");
	$("#disCardForm").submit();
	return false;
}
	$(function() {
		//获取table数据集
		var tlen = $("#tableList tbody>tr");
		//如果只有一条记录,则需要判断
		if(tlen.length==1){
			//获取tr的第二列
			var $td = $("#tableList tbody>tr").find("td:eq(1)");
			//第二列的文本内容
			var tdtext = $td.text();
			//如果=='-',则表示没有数据,则需要做以下操作,此行的第一个列增加colspan属性,删除之后的数据
			if(tdtext=='-'){
				//获取th的列数
				var theadtr = $("#tableList thead>tr").find("th");
				//
				var len = theadtr.length;
				$("#tableList tbody>tr").find("td:eq(0)").attr("colspan",len).css("text-align","center");
				tlen.children("td:gt(0)").remove();
			}
		}
		loan.getstorelsit("storesName", "storeOrgId");
		$.popuplayer(".edit");
		$("#clearBtn").click(
				function() {
					$(':input', '#disCardForm').not(':submit,:button, :reset')
							.val('').removeAttr('checked').removeAttr(
									'selected');
				});
		// 列表导出
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
							+ "/channel/goldcredit/business/exportBusiness?"
							+$("#disCardForm").serialize()+"&cid=" + cid;
				});
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
			action="${ctx}/channel/goldcredit/business/init"
			modelAttribute="params" method="post">
			<input type="hidden" id="userCode">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
			<table class="table1" cellpadding="0" cellspacing="0" border="0"
				width="100%">
				<tr>
					<td><label class="lab">推介日期：</label><input type="text"
						class="Wdate input_text178" name="referralsDate" id= "referralsDate"
						value="<fmt:formatDate value='${params.referralsDate}' pattern='yyyy-MM-dd'/>"
						onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" /></td>
					<td><label class="lab">客户姓名：</label><form:input type="text" class="input_text178" path="customerName"></form:input></td>
					<td><label class="lab">门店：</label><input type="text"
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
					<td><label class="lab">是否无纸化：</label><form:select class="select180" path="yesNO">
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
					 <td><label class="lab">合同版本号：</label>
	                   <form:select class="select180" path="contractVersion">
							<form:option value="">请选择</form:option>
							     <c:forEach items="${fns:getDictList('jk_contract_ver')}"
										var="contract_vesion">
										<form:option value="${contract_vesion.value}">${contract_vesion.label}</form:option>
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
	<p><label>全选</label>
		<input type = "checkbox" id="checkAll"/>
	</p>
	 <div>
   	 <sys:columnCtrl pageToken="gCreditBusinessList"></sys:columnCtrl>
   	</div>
	<div class="box5" style="position:relative;width:100%;overflow:hidden;height:60%;">
		<table id="tableList"
			class="table  table-bordered table-condensed table-hover ">
			<thead>
				<tr>
					<th></th>
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
					<th>状态</th>
					<th>放款日期</th>
					<th>渠道</th>
					<th>模式</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:if
					test="${ creditorBack!=null && fn:length(creditorBack.list)>0}">
					<c:set var="index" value="0" />
					<c:forEach items="${creditorBack.list}" var="item" varStatus="status">
						<c:set var="index" value="${index+1}" />
						<tr>
							<td>
								<input type="checkbox" name="checkBoxItem" contractAmount = '${item.contractAmount}'
               grantAmount='${item.grantAmount}' loanAmount ='${item.loanAuditAmount}'
              value='${item.applyId}'/> 
              					${status.count}
							</td>
							<td>${item.storesName}</td>
							<td><fmt:formatDate value="${item.referralsDate}"
									pattern='yyyy-MM-dd' /></td>
							<td>${item.customerName}</td>
							<td>${item.customerNum}</td>
							<td>${item.bankName}</td>
							<td>${item.bankAccount}</td>
							<td><fmt:formatNumber value='${item.loanAuditAmount}'
									pattern="#,##0.00" /></td>
							<td><fmt:formatNumber value='${item.contractAmount}'
									pattern="#,##0.00" /></td>
							<td><fmt:formatNumber value='${item.feePetition}'
									pattern="#,##0.00" /></td>
							<td><fmt:formatNumber value='${item.feeExpedited}'
									pattern="#,##0.00" /></td>
							<td><fmt:formatNumber value='${item.grantAmount}'
									pattern="#,##0.00" /></td>
							<td>${item.contractVersion}</td>
							<td>${item.loanMonths}</td>
							<td>${item.loanStatus }
							</td>
							<td><fmt:formatDate value="${item.grantDate}"
									pattern='yyyy-MM-dd' /></td>
							<td>${item.channel }</td>
							<td></td>
							<td style="position:relative">
								<input type="button" class="btn btn_edit edit" value="操作"/>
								 <div class="alertset" style="display:none">
								 	<button class="btn_edit" name="dealBtn" value='${item.applyId}'
									onclick="showAllHisByLoanCode('${item.loanCodes}')">历史</button>
								 </div>
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if
					test="${ creditorBack==null || fn:length(creditorBack.list)==0}">
					<tr>
						<td colspan="17" style="text-align: center;">没有待办数据</td>
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