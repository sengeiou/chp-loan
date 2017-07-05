<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>风控数据修复</title>
<meta name="decorator" content="default"/>
<style type="text/css">
.textareCss {
	margin: 5px 0px; 
	width: 1100px; 
	height: 120px;
}
.lab {
	margin: 5px 0px; 
	height: 120px;
	line-height: 120px;
	overflow: hidden;
}
.labOne {
	width: 125px;
	margin: 5px 0px; 
	text-align: right;
}
</style>
<script type="text/javascript">
$(function(){  
	var errMsg = $("#errMsg").val();
	if(errMsg != '') {
		art.dialog.alert(errMsg);
	}
});

</script>
</head>
<body>
	<div class="control-group">
		<form id="fkOperateForm" action="${ctx}/fk/opt/operate" method="post">
			<input type="hidden" id="errMsg" value="${errMsg}" />
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td>
						<label class="labOne">还款日：</label>
						<input id="repaymentDate" name="repaymentDate" class="form-control"></input>
					</td>
				</tr>
				<tr>
					<td>
						<label class="lab">合同编号：</label>
						<textarea id="contractCodes" name="contractCodes" class="form-control textareCss">
						</textarea>
					</td>
				</tr>
			</table>
		</form>
		<div class="tright pr30 pb5">
			<input type="button" class="btn btn-primary" onclick="document.forms[0].submit();" value="查询">
		</div>
	</div>
</body>
</html>