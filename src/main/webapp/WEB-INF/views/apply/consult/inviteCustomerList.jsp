<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
<title>邀请客户管理</title>
<meta name="decorator" content="default" />
<link media="screen" rel="stylesheet" href="${context}/static/ckfinder/plugins/gallery/colorbox/colorbox.css" />
<script type="text/javascript" src="${context}/static/ckfinder/plugins/gallery/colorbox/jquery.colorbox-min.js" />
<script type="text/javascript" src="${context}/js/consult/popUpWindows.js" />
<script type="text/javascript" src="${context}/js/common.js"></script>
</head>
<body>
	<div class="control-group">
		<form id ="inputForm" action="${ctx}/consult/customerManagement/inviteCustomerList" method="post" class="form-horizontal">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td>
						<label class="lab">业务部：</label>
						<select name="businessOrgId" class="select180">
							<option value="">请选择</option>
							<c:forEach items="${businessOrgList}" var="item">
								<c:choose>
									<c:when test="${item.id eq inviteCustomerView.businessOrgId}">
										<option value="${item.id}" selected="selected">${item.name}</option>
									</c:when>
									<c:otherwise>
										<option value="${item.id}">${item.name}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</td>
					<td>
						<label class="lab">姓名：</label>
						<input name="customerName" value="${inviteCustomerView.customerName}" class="input_text178" />
					</td>
					<td>
						<label class="lab">状态：</label>
						<select name="status" class="select180">
							<option value="">请选择</option>
							<c:forEach items="${fns:getNewDictList('jk_invite_customer_status')}" var="item">
								<c:choose>
									<c:when test="${item.value eq inviteCustomerView.status}">
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
				<tr>
					<td>
						<label class="lab">来源：</label>
						<select name="sourceType" class="select180">
							<option value="">请选择</option>
							<c:forEach items="${fns:getNewDictList('jk_invite_customer_source')}" var="item">
								<c:choose>
									<c:when test="${item.value eq inviteCustomerView.sourceType}">
										<option value="${item.value}" selected="selected">${item.label}</option>
									</c:when>
									<c:otherwise>
										<option value="${item.value}">${item.label}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</td>
					<td>
						<label class="lab">标识：</label>
						<select name="systemFlag" class="select180">
							<option value="">请选择</option>
							<c:forEach items="${fns:getNewDictList('jk_invite_customer_flag')}" var="item">
								<c:choose>
									<c:when test="${item.value eq inviteCustomerView.systemFlag}">
										<option value="${item.value}" selected="selected">${item.label}</option>
									</c:when>
									<c:otherwise>
										<option value="${item.value}">${item.label}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</td>
					<td>
						<label class="lab">时间：</label>
						<input id="beginDate" name="beginDate" value="<fmt:formatDate value='${inviteCustomerView.beginDate}' pattern="yyyy-MM-dd HH:mm"/>" type="text" class="Wdate input_text140" size="10" onClick="WdatePicker({skin:'whyGreen', dateFmt:'yyyy-MM-dd HH:mm'})" style="cursor: pointer" />
						-
						<input id="endDate" name="endDate" value="<fmt:formatDate value='${inviteCustomerView.endDate}' pattern="yyyy-MM-dd HH:mm"/>" type="text" class="Wdate input_text140" size="10" onClick="WdatePicker({skin:'whyGreen', dateFmt:'yyyy-MM-dd HH:mm'})" style="cursor: pointer" />
					</td>
				</tr>
			</table>
			<div class="tright pr30 pb5">
				<input type="submit" id="searchBtn" class="btn btn-primary" value="搜索"></input>
				<input type="button" id="clearBtn" class="btn btn-primary" value="清除"></input>
				<input type="button" id="exportBtn" class="btn btn-primary" value="导出Excel"></input>
			</div>
		</form>
	</div>
	<sys:message content="${message}" />
	<table id="tableList">
		<thead>
			<tr height="37px">
				<th>客户姓名</th>
				<th>客户省市</th>
				<th>客户区域</th>
				<th>保存状态</th>
				<th>业务部</th>
				<th>业务区域</th>
				<th>所在门店</th>
				<th>备注内容</th>
				<th>客户经理</th>
				<th>客户经理ID</th>
				<th>业务团队</th>
				<th>提交时间</th>
				<th>来源</th>
				<th>标识</th>
				<th>操作</th>
			</tr>
		</thead>
		<c:forEach items="${page.list}" var="item">
			<tr>
				<td>${item.customerName}</td>
				<td>${item.provinceName}</td>
				<td>${item.cityName}</td>
				<td>${item.statusName}</td>
				<td>${item.businessOrgName}</td>
				<td>${item.areaOrgName}</td>
				<td>${item.storeOrgName}</td>
				<td>
					<a class="remark" href="javascript:void(0);">查看</a>
					<input type="hidden" name="id" value="${item.id}"/>
				</td>
				<td>${item.financingName}</td>
				<td>${item.financingId}</td>
				<td>${item.teamOrgName}</td>
				<td>
					<fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd" />
				</td>
				<td>${item.sourceTypeName}</td>
				<td>${item.systemFlagName}</td>
				<td>
					<c:if test="${item.financingId eq null || item.financingId == '' }">
						<a class="allot" href="javascript:void(0);">人工分配</a>
					</c:if>
					<c:if test="${item.financingId ne null && item.financingId != ''  && item.statusName ne null && item.statusName != '' }">
						<a class="updateStatus" href="javascript:void(0);">修改状态</a>
					</c:if>
					<input type="hidden" name="id" value="${item.id}"/>
					<input type="hidden" name="provinceCode" value="${item.provinceCode}"/>
					<input type="hidden" name="cityCode" value="${item.cityCode}"/>
					<input type="hidden" name="status" value="${item.status}"/>
				</td>
			</tr>
		</c:forEach>
	</table>
	<div class="pagination">${page}</div>
	<script type="text/javascript">
		$().ready(function() {

			$("#tableList").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
			$('#tableList').bootstrapTable({
				method : 'get',
				cache : false,
				height : $(window).height() - 230,

				pageSize : 20,
				pageNumber : 1
			});
			$(window).resize(function() {
				$('#tableList').bootstrapTable('resetView');
			});

			$(".allot").click(function() {
				var provinceId = $(this).siblings("input[name='provinceCode']").val();
				var cityId = $(this).siblings("input[name='cityCode']").val();
				var id = $(this).siblings("input[name='id']").val();
				var url = ctx + "/consult/customerManagement/inviteCustomerList/allot?provinceId=" + provinceId + "&cityId="+cityId+"&id="+id;
				art.dialog.open(url, {
					id : "allot",
					title : '人工分配',
					width : 500,
					lock : true,
					height : 300,
					close : function() {
					}
				});
			});

			$(".updateStatus").click(function() {
				var id = $(this).siblings("input[name='id']").val();
				var status = $(this).siblings("input[name='status']").val();
				var url = ctx + "/consult/customerManagement/inviteCustomerList/updateStatus?id=" + id + "&status="+status;
				art.dialog.open(url, {
					id : "updateStatus",
					title : '修改状态',
					width : 400,
					lock : true,
					height : 230,
					close : function() {
					}
				});
			});

			$(".remark").click(function() {
				var id = $(this).siblings("input[name='id']").val();
				var url = ctx + "/consult/customerManagement/inviteCustomerList/remark?customerId=" + id;
				art.dialog.open(url, {
					id : "remark",
					title : '备注',
					width : 800,
					lock : true,
					height : 250,
					close : function() {
						//$("form:first").submit();
					}
				});
			});
			
			$('#clearBtn').bind('click', function() {
				$(':input', '#inputForm').not(':button, :submit, :reset').val('').removeAttr('checked').removeAttr('selected');
				var url = ctx + "/consult/customerManagement/inviteCustomerList"
				$("#inputForm").attr("action", url);
				$('#inputForm')[0].submit();
			});
			
			//导出Excel
			$("#exportBtn").bind("click", function(){
				var url = ctx + "/consult/customerManagement/inviteCustomerList/export"
				$("#inputForm").attr("action", url);
				$("#inputForm").submit();
			});
			//查询
			$("#searchBtn").bind("click", function(){
				var url = ctx + "/consult/customerManagement/inviteCustomerList"
				$("#inputForm").attr("action", url);
				$("#inputForm").submit();
			});
		});
		function page(n, s) {
			if (n)
				$("#pageNo").val(n);
			if (s)
				$("#pageSize").val(s);
			$("#inputForm").attr("action", "${ctx}/consult/customerManagement/inviteCustomerList");
			$("#inputForm").submit();
			return false;
		}
	</script>
</body>
</html>
