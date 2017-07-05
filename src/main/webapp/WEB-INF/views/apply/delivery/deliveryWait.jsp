<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>交割待办列表</title>
<script type="text/javascript" src="${context}/js/delivery/delivery.js"></script>
<script type="text/javascript">
	function page(n, s) {
		if (n)
			$("#pageNo").val(n);
		if (s)
			$("#pageSize").val(s);
		$("#waitForm")
				.attr("action", "${ctx}/borrow/delivery/deliveryWaitInfo");
		$("#waitForm").submit();
		return false;
	}

	function show() {
		if (document.getElementById("T1").style.display == 'none') {
			document.getElementById("showMore").src = '${context}/static/images/down.png';
			document.getElementById("T1").style.display = '';
			document.getElementById("T2").style.display = '';
		} else {
			document.getElementById("showMore").src = '${context}/static/images/up.png';
			document.getElementById("T1").style.display = 'none';
			document.getElementById("T2").style.display = 'none';
		}
	}
	
	function checkUser(){  
		 $("input[name='openFormBtn']").each(function(){
			 $(this).bind('click',function(){
				 $('#loanCode').val($(this).attr('loanCode'));
				 $('#openFlowForm').submit();
			 });
		 });  
	}
</script>

</head>
<body>

	<div class="control-group">
		<form id="waitForm" method="post">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
			<input id="pageSize" name="pageSize" type="hidden"
				value="${page.pageSize}" />
			<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">客户姓名：</label><input type="text"
						class="input_text178" name="custName" value="${params.custName }" />
					</td>
					<td><label class="lab">合同编号：</label> <input type="text"
						class="input_text178" name="contractCode"
						value="${params.contractCode }" /></td>
					<td><label class="lab">申请时间：</label> <input id="startTime" onchange="checkDate(this)"
						class="input_text70 Wdate" name="startDate" type="text"
						readonly="readonly" value="${params.startTime }"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});" />
						- <input id="endTime" class="input_text70 Wdate" name="endDate"
						type="text" readonly="readonly" value="${params.endTime }" onchange="checkDate(this)"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});" />
					</td>
				</tr>
				<tr>
					<td> 
						<label class="lab" style="float:left;height:30px;line-height:30px">门店名称：</label>
                		<select name="strote" class="select180">
							<option value="" selected>请选择</option>
							<c:forEach var="org" items="${orgs }">
								<option value="${org.orgCode }" <c:if test="${params.strote==org.orgCode }">selected</c:if> >${org.orgName }</option>							
							</c:forEach>
						</select>
					</td>
					<td><label class="lab">客户经理：</label> <input type="text"
						class="input_text178" name="manager" value="${params.manager }" />
					</td>
					<td><label class="lab">团队经理：</label> <input type="text"
						class="input_text178" name="teamManager"
						value="${params.teamManager }" /></td>
				</tr>
				<tr id="T1" style="display: none">
					<td>
						<label class="lab" style="float:left;height:30px;line-height:30px">门店名称(新)：</label>
						<select name="newStrote" class="select180">
							<option value="" selected>请选择</option>
							<c:forEach var="org" items="${orgs }">
								<option value="${org.orgCode }" <c:if test="${params.newStrote==org.orgCode }">selected</c:if> >${org.orgName }</option>							
							</c:forEach>
						</select>
					</td>
					<td><label class="lab">客户经理(新)：</label> <input type="text"
						class="input_text178" name="newManager"
						value="${params.newManager }" /></td>
					<td><label class="lab">团队经理(新)：</label> <input type="text"
						class="input_text178" name="newTeamManager"
						value="${params.newTeamManager }" /></td>
				</tr>
			</table>
			<div class="tright pr30 pb5">
				<input type="submit" class="btn btn-primary" value="搜索"> <input
					type="button" class="btn btn-primary" value="清除" id="wRemoveBtn" />
		</form>
		<div class="xiala">
			<center>
				<img src="${context}/static/images/up.png" id="showMore"
					onclick="javascript:show();"></img>
			</center>
		</div>
	</div>
	</div>
	<p class="mb5">
		<input type="checkbox" id="checkAll" />全选
		<button id="passAll" class="btn btn-small">批量审核通过</button>
		<button id="regectedAll" class="btn btn-small">批量审核驳回</button>
	</p>
	<sys:columnCtrl pageToken="deliver"></sys:columnCtrl>
	<div class="box5">
	<form id="openFlowForm" action="${ctx}/borrow/delivery/delivaryInfo" method="post">
         <input type="hidden" name="loanCode" id="loanCode"/>
      </form>
		<table id="tableList" class="table  table-bordered table-condensed table-hover ">
			<thead>
				<tr>
					<th></th>
					<th>序号</th>
					<th>客户姓名</th>
					<th>借款状态</th>
					<th>门店名称</th>
					<th>团队经理</th>
					<th>客户经理</th>
					<th>客服</th>
					<th>外访</th>
					<th>门店名称(新)</th>
					<th>团队经理(新)</th>
					<th>客户经理(新)</th>
					<th>客服(新)</th>
					<th>外访(新)</th>
					<th>交割申请时间</th>
					<th>交割原因</th>
					<th>申请人</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="de" items="${waitPage.list }" varStatus="str">
				<!--  	<form
						action="${ctx}/borrow/delivery/delivaryInfo?loanCode=${de.loanCode}"
						method="post">
						-->
						<tr>
							<td><input type="checkbox" name="check"
								value="${de.loanCode }"></td>
							<td>${str.count }</td>
							<td>${de.loanCustomerName}</td>
							<td>${de.dictLoanStatusLabel}</td>
							<td>${de.orgName }</td>
							<td>${de.teamManagerName }</td>
							<td>${de.managerName }</td>
							<td>${de.customerServiceName }</td>
							<td>${de.outbountByName }</td>
							<td>${de.newOrgName }</td>
							<td>${de.newTeamManagerName }</td>
							<td>${de.newManagerName }</td>
							<td>${de.newCustomerServiceName }</td>
							<td>${de.newOutbountByName }</td>
							<td><fmt:formatDate value="${de.deliveryApplyTime }"
									pattern="yyyy-MM-dd" /></td>
							<td>
								<p
									style="width: 50px; overflow: hidden; white-space: nowrap; text-overflow: ellipsis;"
									title=${de.deliveryReason }>${de.deliveryReason }</p>
							</td>
							<td>${de.entrustManName }</td>
							<td><input type="submit" name="openFormBtn" class="btn_edit" loanCode="${de.loanCode}" onclick="checkUser()"; value="办理"  /></td>
						</tr>
						</div>
						<!--  
					</form>
						-->
				</c:forEach>
				<c:if test="${waitPage.list==null || fn:length(waitPage.list)==0}">
					<tr>
						<td colspan="18" style="text-align: center;">没有符合条件的数据</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>
	</div>
	<div class="pagination">${waitPage}</div>
	<div id="reason" style="width: 800px; margin-top: 15px; display: none">
		<textarea id="regectedReason" class="textarea_refuse"
			style="width: 99%"></textarea>

	</div>
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