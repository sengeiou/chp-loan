<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>分配</title>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context}/js/consult/popUpWindows.js"></script>
</head>
<body>
	<div style="margin-top: 20px;">
		<div>
			<label class="lab"> 门店： </label>
			<input type="text" name="storeOrgName" value="${storeOrg.name }" class="input_text178" readonly="readonly"/>
			<input type="hidden" name="storeOrgId" value="${storeOrg.id }"/>
			<input type="hidden" name="consultId" value="${consult.id }"/>
		</div>
		<div style="margin-top: 15px;">
			<label class="lab"> 团队经理： </label>
			<select name="teamManagerId" class="select180">
				<option value="">请选择</option>
				<c:forEach var="item" items="${teamManagerList}">
					<c:if test="${currentTeamManager.id ne item.id}">
						<option value="${item.id }">${item.name}</option>
					</c:if>
				</c:forEach>
				<option value="${currentTeamManager.id }" selected="selected">${currentTeamManager.name}</option>				
			</select>
		</div>
		<div style="margin-top: 15px;">
			<label class="lab"> 客户经理： </label>
			<select name="customerManagerId" class="select180">
				<option value="">请选择</option>
				<c:if test="${customerManagerList ne null && not empty customerManagerList}">
					<c:forEach var="item" items="${customerManagerList}">
						<option value="${item.id }">${item.name}</option>
					</c:forEach>
				</c:if>
			</select>
		</div>
		<div class="tright" style="margin: 30px 20px;">
			<input id="save" type="button" value="确认" class="btn btn-primary" />
			<input id="cancle" type="button" value="取消" class="btn btn-primary" />
		</div>
	</div>	
	<script type="text/javascript">
		$().ready(function() {
			$("select[name='teamManagerId']").change(function() {
				var parentOrgId = $("input[name='storeOrgId']").val();
				var teamManagerId = $("select[name='teamManagerId']").val();
				var params = {
					"parentOrgId" : parentOrgId,
					"teamManagerId" : teamManagerId
				};
				$.get(ctx + "/common/userinfo/getCustomerManagerUser", params, function(data) {
					$("select[name='customerManagerId']").html('');
					$("select[name='customerManagerId']").append("<option value=''>请选择</option>");
					for (var i = 0; i < data.length; i++) {
						$("select[name='customerManagerId']").append("<option value='"+data[i].id+"'>" + data[i].name + "</option>");
					}
				}, "json");
			});

			$("#save").click(function() {
				var consultId = $("input[name='consultId']").val();
				var teamManagerId = $("select[name='teamManagerId']").val();
				var customerManagerId = $("select[name='customerManagerId']").val();
				var params = {"id":consultId, "loanTeamEmpcode":teamManagerId, "managerCode":customerManagerId}
				$.post(ctx+"/consult/customerManagement/findConsultList/allot", params, function(data){
					if(data.code != '200'){
						art.dialog.alert(data.msg);
					}else{
						art.dialog.close();
					    var win = art.dialog.open.origin;  
					    win.location.href = win.location.href;  
					}
				}, "json");
			});
			
			$("#cancle").click(function(){
				art.dialog.close();
			});
		});
	</script>
</body>
</html>