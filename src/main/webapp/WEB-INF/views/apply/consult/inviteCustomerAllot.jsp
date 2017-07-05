<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>人工分配</title>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context}/js/consult/popUpWindows.js"></script>
</head>
<body>
	<div style="margin-top: 20px;">
		<div>
			<label class="lab"> 区域： </label>
			<select id="areaOrgId" class="select180">
				<option value="">请选择</option>
				<c:forEach var="item" items="${areaOrgList}">
					<c:choose>
						<c:when test="${defaultAreaOrg.id eq item.id }">
							<option value="${item.id }" selected="selected">${item.name}</option>
						</c:when>
						<c:otherwise>
							<option value="${item.id }">${item.name}</option>	
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
			<input type="hidden" name="id" value="${id}"/>
		</div>
		<div style="margin-top: 15px">
			<label class="lab"> 门店： </label>
			<select id="storeOrgId" class="select180">
				<option value="">请选择</option>
				<c:if test="${storeOrgList ne null && not empty storeOrgList}">
					<c:forEach items="${storeOrgList}" var="item">
						<option value="${item.id }">${item.name}</option>
					</c:forEach>
				</c:if>
			</select>
		</div>
		<div  style="margin-top: 15px">
			<label class="lab"> 客户经理： </label>
			<select name="customerManagerId" class="select180">
				<option value="">请选择</option>
			</select>
		</div>
		<div class="tright" style="margin: 30px 20px;">
			<input id="save" type="button" value="确认" class="btn btn-primary" />
			<input id="cancle" type="button" value="取消" class="btn btn-primary" />
		</div>
	</div>
	<script type="text/javascript">
		$().ready(function() {
			$("#areaOrgId").change(function() {
				var parentOrgId = $(this).val();
				var params = {
					"parentId" : parentOrgId,
					"type" : '6200000003'
				};
				$.get(ctx + "/borrow/statistics/queryChildOrg", params, function(data) {
					$("#storeOrgId").html('');
					$("#storeOrgId").append("<option value=''>请选择</option>");
					for (var i = 0; i < data.length; i++) {
						$("#storeOrgId").append("<option value='"+data[i].id+"'>" + data[i].name + "</option>");
					}
				}, "json");
			});

			$("#storeOrgId").change(function() {
				var parentOrgId = $(this).val();
				var params = {
					"parentOrgId" : parentOrgId,
				};
				$.get(ctx + "/common/userinfo/findCustomerManagerUser", params, function(data) {
					$("select[name='customerManagerId']").html('');
					$("select[name='customerManagerId']").append("<option value=''>请选择</option>");
					for (var i = 0; i < data.length; i++) {
						$("select[name='customerManagerId']").append("<option value='"+data[i].id+"'>" + data[i].name + "</option>");
					}
				}, "json");
			});

			$("#save").click(function() {
				var id = $("input[name='id']").val();
				var financingId = $("select[name='customerManagerId']").val();
				var storeOrgId = $("#storeOrgId").val();
				var areaOrgId = $("#areaOrgId").val();
				var params = {
					"id" : id,
					"financingId" : financingId,
					"storeOrgId" : storeOrgId,
					"areaOrgId" : areaOrgId
				}
				$.post(ctx + "/consult/customerManagement/inviteCustomerList/allot", params, function(data) {
					if (data.code != '200') {
						art.dialog.alert(data.msg);
					} else {
						art.dialog.close();
						var win = art.dialog.open.origin;
						win.location.href = win.location.href;
					}
				}, "json");
			});

			$("#cancle").click(function() {
				art.dialog.close();
			});
		});
	</script>
</body>
</html>