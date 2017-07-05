<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>客户管理</title>
<meta name="decorator" content="default" />
<link media="screen" rel="stylesheet" href="${context}/static/ckfinder/plugins/gallery/colorbox/colorbox.css" />
<script type="text/javascript" src="${context}/static/ckfinder/plugins/gallery/colorbox/jquery.colorbox-min.js"></script>
<script type="text/javascript" src="${context}/js/consult/popUpWindows.js"></script>
<script type="text/javascript" src="${context}/js/common.js"></script>
</head>
<body>
	<div class="control-group">
		<form id="inputForm" action="${ctx}/consult/customerManagement/findConsultList" method="post" class="form-horizontal">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
			<input type="hidden" name="menuId" value="${menuId}">
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td>
						<label class="lab">客户姓名：</label>
						<input name="customerName" value="${consultSearchView.customerName}" class="input_text178" />
					</td>
					<td>
						<label class="lab">证件号码：</label>
						<input name="mateCertNum" value="${consultSearchView.mateCertNum}" id="mateCertNum" class="input_text178" />
					</td>
					<td>
						<label class="lab">门店：</label>
						<input type="text" id="storeOrgName" name="storeOrgName" value="${consultSearchView.storeOrgName}" readonly="readonly" class="input_text178" />
						<i id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
						<input type="hidden" name="storeOrgId" value="${consultSearchView.storeOrgId}" id="storeOrgId" />
					</td>
				</tr>
				<tr>
					<td>
						<label class="lab">咨询状态：</label>
						<select id="dictOperStatus" name="dictOperStatus" class="select180">
							<option value="">请选择</option>
							<c:forEach items="${fns:getNewDictList('jk_next_step')}" var="item">
								<c:if test="${item.value ne '5' }">
									<c:choose>
										<c:when test="${item.value eq consultSearchView.dictOperStatus}">
											<option value="${item.value}" selected="selected">${item.label}</option>
										</c:when>
										<c:otherwise>
											<option value="${item.value}">${item.label}</option>
										</c:otherwise>
									</c:choose>
								</c:if>
							</c:forEach>
						</select>
					</td>
					<td>
						<label class="lab">在职状态：</label>
						<select id="userStatus" name="userStatus" class="select180">
							<option value="">请选择</option>
							<c:forEach items="${fns:getNewDictList('com_user_status')}" var="item">
								<c:if test="${item.value ne '2' }">
									<c:choose>
										<c:when test="${item.value eq consultSearchView.userStatus}">
											<option value="${item.value}" selected="selected">${item.label}</option>
										</c:when>
										<c:otherwise>
											<option value="${item.value}">${item.label}</option>
										</c:otherwise>
									</c:choose>
								</c:if>
							</c:forEach>
						</select>
					</td>
					<td>
						<label class="lab">咨询来源：</label>
						<select name="consultDataSource" class="select180">
							<option value="">请选择</option>
							<c:forEach items="${fns:getNewDictList('jk_consult_data_source')}" var="item">
								<c:choose>
									<c:when test="${item.value eq consultSearchView.consultDataSource}">
										<option value="${item.value}" selected="selected">${item.label}</option>
									</c:when>
									<c:otherwise>
										<option value="${item.value}">${item.label}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</td>
				</tr>
			</table>
			<div class="tright pr30 pb5">
				<input type="button" id="searchBtn" class="btn btn-primary" value="搜索"></input>
				<input type="button" id="clearBtn" class="btn btn-primary" value="清除"></input>
			</div>
		</form>
	</div>
	<form id="giveUpForm">
		<input type="hidden" name="id" id="consultId" />
		<input type="hidden" name="consOperStatus" id="consOperStatus" value="1" />
		<input type="hidden" name="customerCode" id="customerCode" />
		<input type="hidden" name="customerMobilePhone" id="customerMobilePhone" />
		<input type="hidden" name="consCommunicateDate" value="<fmt:formatDate value='${today}' pattern="yyyy-MM-dd"/>" id="consCommunicateDate" />
	</form>
	<sys:message content="${message}" />
	<table id="tableList">
		<thead>
			<tr height="37px">
				<th>客户编号</th>
				<th>客户姓名</th>
				<th>客户证件号码</th>
				<th>手机号码</th>
				<th>上次沟通时间</th>
				<th>咨询创始时间</th>
				<th>最近沟通记录</th>
				<th>门店</th>
				<th>团队经理</th>
				<th>客户经理</th>
				<th>在职状态</th>
				<th>咨询状态</th>
				<th>咨询来源</th>
				<th>操作</th>
			</tr>
		</thead>
		<c:forEach items="${page.list}" var="item">
			<tr>
				<td>${item.customerCode}</td>
				<td>${item.customerName}</td>
				<td>
						<%--${item.mateCertNum}--%>
				</td>
				<td>
						<%--${item.customerMobilePhone}--%>
				</td>
				<td>
					<fmt:formatDate value="${item.lastTimeConsCommunicateDate}" pattern="yyyy-MM-dd" />
				</td>
				<td>
					<fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd" />
				</td>
				<td>
					<a href="javascript:void(0)" title="${item.consLoanRecord }">${fns:abbr(item.consLoanRecord, 30)}</a>
				</td>
				<td>${item.storeOrgName}</td>
				<td>${item.loanTeamEmpName}</td>
				<td>${item.custManagerName}</td>
				<td>${item.userStatusName}</td>
				<td>${item.dictOperStatusName}</td>
				<td>${item.consultDataSource}</td>
				<td>
					<c:if test="${item.dictOperStatus!=null && item.dictOperStatus=='0' && isManager==true}">
						<input type="button" class="btn_edit" value="添加沟通记录" onclick="doOpenss('${item.id}','${item.customerCode}','${item.customerName}','${item.mateCertNum}','${item.customerMobilePhone}');" />
					</c:if>
					<input type="button" class="btn_edit" onclick="doOpenhis('${item.id}')" value="历史沟通记录" />
					<c:if test="${item.dictOperStatus!=null && item.dictOperStatus=='0' && isManager==true}">
						<input type="button" class="btn_edit" value="客户放弃" onclick="giveUpConsult('giveUpForm','${item.id}','${item.customerCode}','${item.customerName}','${item.mateCertNum}','${item.customerMobilePhone}')" />
					</c:if>
					<c:if test="${item.dictOperStatus eq '1' && item.userStatus eq '0' && jkhj_customer_management_allot==null}">
						<input type="button" class="btn_edit allot" value="分配" />
						<input type="hidden" name="id" value="${item.id}" />
					</c:if>
				</td>
			</tr>
		</c:forEach>
	</table>
	<div class="pagination">${page}</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#tableList").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
			$('#tableList').bootstrapTable({
				method : 'get',
				cache : false,
				height : $(window).height() - 200,

				pageSize : 20,
				pageNumber : 1
			});
			$(window).resize(function() {
				$('#tableList').bootstrapTable('resetView');
			});
			$('#searchBtn').bind('click', function() {
				$('#inputForm')[0].submit();
			});
			$('#clearBtn').bind('click', function() {
				$(':input', '#inputForm').not(':button, :submit, :reset').val('').removeAttr('checked').removeAttr('selected');
				$('#inputForm')[0].submit();
			});
			$('#mateCertNum').bind('blur', function() {
				var certValue = $(this).val();
				if (certValue.indexOf("x") != -1) {
					certValue = certValue.replace(/x/g, "X");
					$(this).val(certValue);
				}
			});
			loan.getstorelsit("storeOrgName", "storeOrgId", "1");
			//分配
			CustomerManagementList.allot();
		});
		function page(n, s) {
			if (n)
				$("#pageNo").val(n);
			if (s)
				$("#pageSize").val(s);
			$("#inputForm").attr("action", "${ctx}/consult/customerManagement/findConsultList");
			$("#inputForm").submit();
			return false;
		}
	</script>
</body>
</html>
