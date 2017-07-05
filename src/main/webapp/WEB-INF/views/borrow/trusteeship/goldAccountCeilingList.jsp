<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="decorator" content="default" />
<title>金账户上限列表</title>
<script src="${context}/js/common.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		$("#clearBtn").click(
				function() {
					$(':input', '#disCardForm').not(':submit,:button, :reset').val('')
							.removeAttr('checked').removeAttr('selected');
				});
		// 伸缩
		$("#showMore").click(
			function() {
				if (document.getElementById("T1").style.display == 'none') {
					document.getElementById("showMore").src = ctxStatic
							+ '/images/down.png';
					document.getElementById("T1").style.display = '';
					document.getElementById("T2").style.display = '';
				} else {
					document.getElementById("showMore").src = ctxStatic
							+ '/images/up.png';
					document.getElementById("T1").style.display = 'none';
					document.getElementById("T2").style.display = 'none';
				}
		});
		loan.getstorelsit("storesName", "storeOrgId");
		// 点击全选
		$("#checkAll").click(function() {
			var deductAmount = $("#deduct").val();
			$('input[name="checkBoxItem"]').attr("checked", this.checked);
			if ($(this).is(":checked")){
				$("#hiddenDeduct").val(deductAmount);
			}else {
				$("#hiddenDeduct").val(0.00);
			}
			$("#amount").text(fmoney(deductAmount, 2));
			 
		});
		// 计算金额,
		var $subBox = $(":input[name='checkBoxItem']");
		$subBox
				.click(function() {
					// 记录总金额，当length为0时，进行总金额的处理
					var totalDeduct = $("#deduct").val();
					// 获得单个单子的金额
					var deductAmount = parseFloat($(this).attr("grantAmount"));
					if ($(this).is(':checked')) {
						var hiddenDeduct = parseFloat($("#hiddenDeduct").val())
								+ deductAmount;
						$("#hiddenDeduct").val(hiddenDeduct);
						var amount = fmoney(hiddenDeduct, 2);
						$("#amount").text(amount);
					} else {
						if ($(":input[name='checkBoxItem']:checked").length == 0) {
							
							$("#hiddenDeduct").val(0.00);
							$("#amount").text(fmoney(totalDeduct, 2));
						} else {
							var hiddenDeduct = parseFloat($("#hiddenDeduct").val())
									- deductAmount;
							var amount = fmoney(hiddenDeduct, 2);
							$("#amount").text(amount);
							$("#hiddenDeduct").val(hiddenDeduct);
						}
					}
					$("#checkAll")
							.attr(
									"checked",
									$subBox.length == $(":input[name='checkBoxItem']:checked").length ? true
											: false);
				});

		// 列表导出
		$("#dao")
				.click(
						function() {
							
								window.location.href = ctx
										+ "/borrow/trusteeship/ceiling/exportCeiling?"
										+ "cid=" + selectedList() + "&referralsDate="
										+ $("#referralsDate").val()
										+ "&customerName="
										+ $("#customerName").val()
										+ "&storeOrgId="
										+ $("#storeOrgId").val()
										+ "&loanStatus="
										+ $("#loanStatus").val()
										+ "&grantDate=" + $("#grantDate").val()
										+ "&gtantEndDate="
										+ $("#gtantEndDate").val()
										+ "&logo="
										+ $("#logo").val()
										+ "&dataStatus="
										+ $("#dataStatus").val()
										;
						});
		
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
	function checkLoanApply(applyId){
		window.location.href = ctx
		+ "/borrow/trusteeship/ceiling/checkInfo?applyId="+applyId;
	}
	function selectedList() {
		var cid = "";
		if ($(":input[name='checkBoxItem']:checked").length > 0) {
			$(":input[name='checkBoxItem']:checked").each(function() {
				if (cid != "") {
					cid += "," + $(this).attr("cid");
				} else {
					cid = $(this).attr("cid");
				}
			});
		}
		return cid;
	}
</script>
</head>
<body>

		<div class="control-group">
			<form:form id="disCardForm"
				action="${ctx }/borrow/trusteeship/ceiling/init"
				modelAttribute="params" method="post">
				<input type="hidden" id="userCode">
				<table class="table1" cellpadding="0" cellspacing="0" border="0"
					width="100%">
					<tr>
						<td><label class="lab">推介日期：</label> <input type="text"
							class="Wdate input_text178" name="referralsDate" id = "referralsDate"
							value="<fmt:formatDate value='${params.referralsDate}' pattern='yyyy-MM-dd'/>"
							onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" /></td>
						<td><label class="lab">客户姓名：</label> <form:input type="text"
								class="input_text178" path="customerName" id = "customerName"></form:input></td>
						<td><label class="lab">门店：</label><input type="text"
							class="input_text178" name="storesName" id="storesName"
							readonly="readonly" value="${params.storesName }" /> <i
							id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
							<input type="hidden" name="storeOrgId"
							value="${params.storeOrgId}" id="storeOrgId" /></td>
					</tr>
					<tr>
						<td><label class="lab">借款状态：</label> <form:select
								class="select180" path="loanStatus" id  = "loanStatus">
								<form:option value="">请选择</form:option>
								<c:forEach items="${fns:getDictList('jk_loan_apply_status')}"
									var="loan_type">
									<form:option value="${loan_type.value}">${loan_type.label}</form:option>
								</c:forEach>
							</form:select></td>
						<td><label class="lab">放款日期：</label> <input name=grantDate
							id="grantDate"
							value="<fmt:formatDate value='${params.grantDate}' pattern='yyyy-MM-dd'/>"
							type="text" class="Wdate input_text70" size="10"
							onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'gtantEndDate\')}'})" style="cursor: pointer" />-<input name="gtantEndDate" id="gtantEndDate"
							value="<fmt:formatDate value='${params.gtantEndDate}' pattern='yyyy-MM-dd'/>"
							type="text" class="Wdate input_text70" size="10"
							onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'grantDate\')}'})" style="cursor: pointer" />
						</td>
						<td><label class="lab">渠道：</label> <form:select
								class="select180" path="logo" id  = "logo">
								<form:option value="">请选择</form:option>
								<c:forEach items="${fns:getDictList('jk_channel_flag')}"
									var="card_type">
									<form:option value="${card_type.value}">${card_type.label}</form:option>
								</c:forEach>
							</form:select></td>
					</tr>
					<tr id="T1" style="display: none">
						<td><label class="lab">数据状态：</label> <form:select
								class="select180" path="dataStatus" id = "dataStatus">
								<form:option value="">请选择</form:option>
								<%-- <c:forEach items="${fns:getDictList('jk_loan_type')}"
									var="loan_type">
									<form:option value="${loan_type.value}">${loan_type.label}</form:option>
								</c:forEach>--%>
								<form:option value="0">在用</form:option>
								<form:option value="1">历史</form:option>
							</form:select></td>
					</tr>
				</table>
				<div class="tright pr30 pb5">
					<input class="btn btn-primary" type="submit" value="搜索"
						id="searchBtn"></input>
					<button class="btn btn-primary" id="clearBtn">清除</button>
					<div style="float: left; margin-left: 45%; padding-top: 10px">
						<img src="${ctxStatic }/images/up.png" id="showMore"></img>
					</div>
				</div>
			</form:form>
		</div>
		<p class="mb5">
			<button class="btn btn-small" id="dao">导出Excel</button>
			<!-- <button class="btn btn-small" id="ceiling">设置金账户额度上限</button>
			<button class="btn btn-small" id="clear">归档</button> -->

			&nbsp;&nbsp;<label class="lab1"><span class="red">金信总金额：</span></label><label id = "amount"
			class="red"> <fmt:formatNumber value='${totalDeducts}'
				pattern="#,##0.00" /></label><label class="red">元</label> <input
			type="hidden" id="hiddenDeduct" value="0.00" /><input type="hidden"
			id="deduct" value="${totalDeducts}">
		</p>
		<input type="hidden" id="hiddenGrant" value="0.00">
		 <div>
	   	 	<sys:columnCtrl pageToken="gCeillingList"></sys:columnCtrl>
	   	 </div>
		<div class="box5">
		<table id="tableList"
			class="table table-bordered table-condensed table-hover">
			<thead>
				<tr>
					<th><input type="checkbox" id="checkAll"/></th>
					<th>门店名称</th>
					<th>推介日期</th>
					<th>客户姓名</th>
					<th>身份证号</th>
					<th>开户行</th>
					<th>银行账号</th>
					<th>批借金额</th>
					<th>外访费</th>
					<th>加急费</th>
					<th>放款金额</th>
					<th>期限</th>
					<th>借款状态</th>
					<th>放款日期</th>
					<th>数据状态</th>
					<th>渠道</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${ceiling!=null && fn:length(ceiling.list)>0}">
					<c:forEach items="${ceiling.list}" var="item">
						<tr>
							<td><input type="checkbox" name="checkBoxItem" 
								grantAmount='${item.loanAuditAmount}' value='${item.applyId}' cid = "${item.applyId }"/></td>
							<td>${item.storesName}</td>
							<td><fmt:formatDate value="${item.referralsDate}" pattern='yyyy-MM-dd' /></td>
							<td>${item.customerName}</td>
							<td>${item.customerNum}</td>
							<td>${item.bankName}</td>
							<td>${item.bankAccount}</td>
							<td><fmt:formatNumber value='${item.loanAuditAmount}' pattern="#,#00.00" /></td>
							<td><fmt:formatNumber value='${item.feePetition}' pattern="#,#00.00" /></td>
							<td><fmt:formatNumber value='${item.feeExpedited}' pattern="#,#00.00" /></td>
							<td><fmt:formatNumber value='${item.grantAmount}' pattern="#,#00.00" /></td>
							<td>${item.loanMonths}</td>
							<td>${item.loanStatus }
							</td>
							<td><fmt:formatDate value="${item.grantDate}" pattern='yyyy-MM-dd' /></td>
							<td>
								${item.dataStatus}
							</td>
							<td>
							${ item.logo}
							</td>
							<td><button class="btn_edit" name="dealBtn"
									value='${item.applyId}' onclick="checkLoanApply('${item.applyId}')">查看</button>
								<button class="btn_edit" name="history"
									value='${item.applyId}' onclick="showLoanHis('${item.applyId}')">历史</button>
							</td>
						</tr>
					</c:forEach>

				</c:if>

				<c:if test="${ ceiling==null || fn:length(ceiling.list)==0}">
					<tr>
						<td colspan="17" style="text-align: center;">没有待办数据</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>
	</div>
	<div class="pagination">${ceiling}</div>
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