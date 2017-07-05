<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>外访任务信息</title>
<meta name="decorator" content="default" />
<script src="${context}/js/common.js" type="text/javascript"></script>
<script language="javascript">
	
</script>
</head>
<body>
	<div>
		<c:if test="${fn:contains(taskInfo.visitType,'0')}">
			<div>
				<h2 class=" pl10 f14 ">
					<b>外访家庭</b>
				</h2>
				<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td>
							<label class="lab">居住地址：</label>
							${taskInfo.liveProvince}&nbsp;${taskInfo.liveCity}&nbsp;${taskInfo.liveArea}&nbsp;${taskInfo.liveAddress}
						</td>
					</tr>
					<tr>
						<td>
							<label class="lab">外访家庭备注：</label>
							<textarea class="textarea_refuse" readonly="readonly">${taskInfo.remark}</textarea>
						</td>
					</tr>
				</table>
			</div>
		</c:if>
		<c:if test="${fn:contains(taskInfo.visitType,'1')}">
			<div>
				<h2 class=" pl10 f14 ">
					<b>外访单位</b>
				</h2>
				<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td>
							<label class="lab">工作单位地址：</label>
							${taskInfo.workUnitProvince}&nbsp;${taskInfo.workUnitCity}&nbsp;${taskInfo.workUnitArea}&nbsp;${taskInfo.workUnitAddress}
						</td>
					</tr>
					<tr>
						<td>
							<label class="lab">外访单位备注：</label>
							<textarea class="textarea_refuse" readonly="readonly">${taskInfo.visitWorkRemark}</textarea>
						</td>
					</tr>
				</table>
			</div>
		</c:if>
		<c:if test="${fn:contains(taskInfo.visitType,'2')}">
			<div>
				<h2 class=" pl10 f14 ">
					<b>外访企业</b>
				</h2>
				<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td>
							<label class="lab">实际经营地址：</label>
							${taskInfo.operationProvince}&nbsp;${taskInfo.operationCity}&nbsp;${taskInfo.operationArea}&nbsp;${taskInfo.operationAddress}
						</td>
					</tr>
					<tr>
						<td>
							<label class="lab">外访企业备注：</label>
							<textarea class="textarea_refuse" readonly="readonly">${taskInfo.visitWorkRemark}</textarea>
						</td>
					</tr>
				</table>
			</div>
		</c:if>
	</div>
</body>
</html>