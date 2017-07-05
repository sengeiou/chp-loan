<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>放款审核</title>
<script src="${context}/js/grant/GCGrantAudit.js" type="text/javascript"></script>
<meta name="decorator" content="default" />
<script type="text/javascript">
Date.prototype.format = function(format){ 
	var args = { "M+" : this.getMonth() + 1, "d+" : this.getDate(), "h+" : this.getHours(), "m+" : this.getMinutes(), "s+" : this.getSeconds(), "q+" : Math.floor((this.getMonth() + 3) / 3),  //quarter
		"S" : this.getMilliseconds() }; 
		if(/(y+)/.test(format)) format = format.replace(RegExp.$1,(this.getFullYear() + "").substr(4 - RegExp.$1.length)); 
		for(var i in args) { 
			var n = args[i]; 
			if(new RegExp("("+ i +")").test(format)) 
				format = format.replace(RegExp.$1,RegExp.$1.length == 1 ? n : ("00" + n).substr(("" + n).length)); 
		} 
		return format;
	};
</script>
</head>
<body>
	<table class="table  table-bordered table-condensed table-hover ">
		<thead>
			<tr>
				<th>借款人</th>
				<th>合同编号</th>
				<th>放款金额</th>
				<th>放款账户</th>
				<th>放款日期</th>
				<th>渠道</th>
			</tr>
		</thead>
		<tbody>
			<form id="param">
				<input type="hidden" value="${messageBuffer}" id="messageBuffer"/>
				<input type="hidden" value="${isExistSplit}" id="isExistSplit"/>
				<c:forEach items="${list}" var="item" varStatus="stat">
					<tr>
						<td>${item.customerName}</td>
						<td>${item.contractCode}</td>
						<td><fmt:formatNumber value='${item.lendingMoney}'
								pattern="#,#00.00" /></td>
						<td>${item.lendingAccount}</td>
						<td class = "lengingDate"><fmt:formatDate value="${item.lendingTime}"
								pattern="yyyy-MM-dd" /></td>
						<td>${item.channelName}</td>
						<input type="hidden" name="list[${stat.index}].applyId" value='${item.applyId}'></input>
						<%-- <input type="hidden" name="list[${stat.index}].workItemView.wobNum" value='${item.wobNum}'></input>
						<input type="hidden" name="list[${stat.index}].workItemView.flowName" value='${item.flowName}'></input>
						<input type="hidden" name="list[${stat.index}].workItemView.token" value='${item.token}'></input>
						<input type="hidden" name="list[${stat.index}].workItemView.stepName" value='${item.stepName}'></input> --%>
						<input type="hidden" name="list[${stat.index}].contractCode" value='${item.contractCode}'></input>
						<input type="hidden" name="list[${stat.index}].loanCode" value='${item.loanCode}'></input>
	                	<%-- <input type="hidden" name="list[${stat.index}].channelCode" value='${item.channelCode}'></input> --%>
					</tr>
				</c:forEach>
			</form>
		</tbody>
	</table>
	<div class="control-group pb10">
		<table class="table1" cellpadding="0" cellspacing="0" border="0"
			width="100%">
			<tr>
				<td style="text-align: left"><label class="lab">筛选结果：</label><input
					type="radio" class="mr10" name="sort" checked="checked" value="通过">通过
					<input type="radio" class="mr10" name="sort" value="退回">退回</td>
			</tr>
			<tr>
				<td style="text-align: left">
					<div style="display: inline" id="sort_div">
						<label class="lab">确认放款日期：</label><input id="grantDate"
							name="lendingTime"
							value="<fmt:formatDate value='${lendingTime}' pattern="yyyy-MM-dd"/>"
							type="text" class="Wdate input_text178" size="10"
							onClick="WdatePicker({skin:'whyGreen',onpicked:function(dp){$('.lengingDate').each(function (){if($.trim($(this).text())!=dp.cal.getDateStr()){art.dialog.alert('确认放款日期和放款日期不同');return false;}});}})" style="cursor: pointer" />
					</div>
					<div style="display: none" id="back_div">
						<label class="lab">退回原因：</label><select class="select180"
							id="backResult"><option value="">请选择</option>
							<c:forEach items="${fns:getDictList('jk_chk_back_reason')}"
								var="card_type">
								<option value="${card_type.value}">${card_type.label}</option>
							</c:forEach>
					</div>
				</td>
			</tr>
		</table>
	</div>
	<div class="tright pr10 pr30">
			<button class="btn btn-primary" id="auditSure">确认</button>
			&nbsp;&nbsp;
			<button class="btn btn-primary" id="closeBtn">取消</button>
		</div>
	</div>
</body>
</html>