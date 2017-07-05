<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>修改状态</title>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context}/js/consult/popUpWindows.js"></script>
</head>
<body>
	<div style="margin-top: 30px;">
		<div>
			<label class="lab"> 状态： </label>
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
			<input type="hidden" name="id" value="${inviteCustomerView.id}"/>
		</div>
		<div class="tright" style="margin: 30px 20px;">
			<input id="save" type="button" value="确认" class="btn btn-primary" />
			<input id="cancle" type="button" value="取消" class="btn btn-primary" />
		</div>
	</div>
	<script type="text/javascript">
		$().ready(function() {
			$("#save").click(function() {
				var id = $("input[name='id']").val();
				var status = $("select[name='status']").val();
				var params = {
					"id" : id,
					"status":status
				}
				$.post(ctx + "/consult/customerManagement/inviteCustomerList/updateStatus", params, function(data) {
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