<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>交割-发起交割申请列表</title>
<script type="text/javascript" src="${context}/js/delivery/delivery.js"></script>
<script type="text/javascript">
	function page(n, s) {
		if (n) $("#pageNo").val(n);
		if (s) $("#pageSize").val(s);
		$("#applyForm").attr("action", "${ctx}/borrow/apply/deliveryApply");
		$("#applyForm").submit();
		return false;
	};
</script>
</head>
<body>
	<div class="control-group">
		<form id="applyForm" method="post">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
			<input id="errorMesStr" name="errorMesStr" type="hidden" value="${errorMesStr}" />
			<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td>
						<label class="lab">客户姓名：</label> 
						<input type="text" class="input_text178" name="custName" value="${params.custName }" />
					</td>
					<td>
						<label class="lab">合同编号：</label> 
						<input type="text" class="input_text178" name="contractCode" value="${params.contractCode }" />
					</td>
					<td>
						<label class="lab">申请状态：</label> 
						<select class="select180" name="loanStatus">
							<option value="">请选择</option>
							<c:forEach var="sta" items="${fns:getDictList('jk_loan_apply_status') }">
								<option value="${sta.value }"
									<c:if test="${params.loanStatus==sta.value }">selected</c:if>>${sta.label }
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<label class="lab">客户经理：</label> 
						<input type="text" class="input_text178" name="manager" value="${params.manager }" />
					</td>
					<td>
						<label class="lab">团队经理：</label> 
						<input type="text" class="input_text178" name="teamManager" value="${params.teamManager }" />
					</td>
				</tr>
			</table>
			<div class="tright pr30 mb5">
				<input type="submit" value="搜索" class="btn btn-primary" /> 
				<input type="button" value="清除" class="btn btn-primary" id="appRemoveBtn" />
			</div>
		</form>
	</div>
	<p class="mb5">
		<input type="checkbox" id="checkAll" />全选
		<button id="transate" class="btn btn-small">批量办理</button>
	</p>
	
		<table id="tableList" class="table table-hover table-bordered table-condensed">
			<thead>
				<tr>
					<th></th>
					<th>序号</th>
					<th>客户姓名</th>
					<th>合同编号</th>
					<th>门店名称</th>
					<th>团队经理</th>
					<th>客户经理</th>
					<th>客服</th>
					<th>外访员</th>
					<th>申请状态</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="dl" items="${delPage.list }" varStatus="st">
					<tr>
						<td>
							<input type="checkbox" name="applyLoanCodes" value="${dl.loanCode }">
						</td>
						<td><c:out value="${st.count }" /></td>
						<td>${dl.loanCustomerName }</td>
						<td>${dl.contractCode }</td>
						<td>${dl.orgName }</td>
						<td>${dl.teamManagerName }</td>
						<td>${dl.managerName }</td>
						<td>${dl.customerServiceName }</td>
						<td>${dl.outbountByName }</td>
						<td>${dl.dictLoanStatusLabel}</td>
						<td>
							<button class="btn_edit" id="${dl.loanCode }" onclick="traBtn(this)">办理</button>
						</td>
					</tr>
				</c:forEach>
				<c:if test="${delPage.list==null || fn:length(delPage.list)==0}">
					<tr>
						<td colspan="11" style="text-align: center;">没有符合条件的数据</td>
					</tr>
				</c:if>
			</tbody>
		</table>
		<div class="pagination">${delPage}</div>

	<!-- 单条交割办理弹出层 -->
	<div id="single" style="width:100%;height:100%;display: none">
		<form method="post" id="singleForm">
			<input type="hidden" id="loanCodeHid" name="loanCode"> 
			<input type="hidden" id="deliveryResult" name="deliveryResult" value="1">
			<input type="hidden" id="orgCode" name="orgCode"> 
			<input type="hidden" id="teamManagerCode" name="teamManagerCode">
			<input type="hidden" id="managerCode" name="managerCode"> 
			<input type="hidden" id="serviceCode" name="customerServiceCode">
			<input type="hidden" id="outBoundCode" name="outbountByCode">
			<input type="hidden" id="dictLoanStatus" name="dictLoanStatus">
			<input type="hidden" id="contractCode" name="contractCode">
			<table class="table  table-bordered table-condensed table-hover">
				<tr>
					<th></th>
					<th>旧数据</th>
					<th>新数据</th>
				</tr>
				<tr>
					<td>门店名称</td>
					<td>
						<span id="orgName"></span>
					</td>
					<td>
						<select id="org" name="newOrgName" class="select180">
							<option value="" selected>请选择</option>
							<c:forEach var="org" items="${orgs }">
								<option value="${org.orgCode }">${org.orgName }</option>
							</c:forEach>
						</select> 
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
				</tr>
				<tr>
					<td>团队经理</td>
					<td>
						<span id="teamManagerName"></span>
					</td>
					<td>
						<select id="team" name="newTeamManagerName" class="select180">
							<option value="" selected>请选择</option>
						</select> 
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
				</tr>
				<tr>
					<td>客户经理</td>
					<td>
						<span id="managerName"></span>
					</td>
					<td>
						<select id="newManager" name="newManagerName" class="select180">
							<option value="" selected>请选择</option>
						</select> 
						<span class="help-inline">
							<font color="red">*</font> 
						</span>
					</td>
				</tr>
				<tr>
					<td>客服</td>
					<td>
						<span id="customerServiceName"></span>
					</td>
					<td>
						<select id="service" name="newCustomerServiceName" class="select180">
							<option value="" selected>请选择</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>外访员</td>
					<td>
						<span id="outbountByName"></span>
					</td>
					<td>
						<select id="outBound" name="newOutbountByName" class="select180">
							<option value="" selected>请选择</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>交割原因</td>
					<td colspan="2">
						<textarea name="deliveryReason" style="height:28px;width:90%;"></textarea>
					</td>
				</tr>
				<tr>
					<td>上传附件</td>
					<td colspan="2">
						<iframe style="width:1000px;height:500px"
 							src="" id="imageUrl"> 
 						</iframe>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<!-- 批量交割弹出层  -->
	<div id="boath" style="width: 800px; margin-top: 15px; display: none">
		<form method="post" id="confirmApplyForm">
			<table class="table  table-bordered table-condensed table-hover">
				<tr>
					<td>门店名称(新)</td>
					<td>
						<select id="totalOrg" name="newOrgName" class="select180">
							<option value="" selected>请选择</option>
							<c:forEach var="org" items="${orgs }">
								<option value="${org.orgCode }">${org.orgName }</option>
							</c:forEach>
						</select> 
						<span class="help-inline">
							<font id="totalOrgFont" color="red">*</font> 
						</span>
					</td>
				</tr>
				<tr>
					<td>团队经理(新)</td>
					<td>
						<select id="totalTeam" name="newTeamManagerName" class="select180">
							<option value="" selected>请选择</option>
						</select> 
						<span class="help-inline">
							<font id="totalTeamFont" color="red">*</font> 
						</span>
					</td>
				</tr>
				<tr>
					<td>客户经理(新)</td>
					<td>
						<select id="totalManager" name="newManagerName" class="select180">
							<option value="" selected>请选择</option>
						</select> 
						<span class="help-inline">
							<font id="totalManagerFont" color="red">*</font> 
						</span>
					</td>
				</tr>
				<tr>
					<td>客服(新)</td>
					<td>
						<select id="totalService" name="newCustomerServiceName" class="select180">
							<option value="" selected>请选择</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>外访员(新)</td>
					<td>
						<select id="totalOutBound" name="newOutbountByName" class="select180">
							<option value="" selected>请选择</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>交割原因</td>
					<td>
						<textarea name="deliveryReason" style="width: 98%; height: 30%"></textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<script type="text/javascript">
	var str = $("#errorMesStr").val();
	if(str != ""){
		art.dialog.alert(str);
	}
</script>
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