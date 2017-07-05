<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<script src="${context}/js/contract/contractAudit.js" type="text/javascript"></script>
<title>信息平台占比分配</title>
</head>

<body>
<script language="javascript">
	$(document).ready(function() {
		$('#closeBtn').bind('click', function() {
			art.dialog.close();
		});
		$('#splitFormClrBtn').bind('click', function() {
			$("#zcj").val('');
			$("#jinxin ").val('');
		});
		$('#saveBtn').bind('click', function() {
			saveSplit($("#param").val(),$("#zcj").val(),$("#jinxin ").val());
		});

	});
</script>
		<div>
			<div class="control-group">
				<div class="tright pr30 pb5">
					<input type="button" id="splitFormClrBtn" class="btn btn-primary"
						value="清除"></input> <input type="button" class="btn btn-primary"
						id="saveBtn" value="确定"></input> <input type="button"
						class="btn btn-primary" id="closeBtn" value="取消"></input>
				</div>
			</div>
			<div id="auditList">
				<div class="box5">
					<form id="splitForm" action="">
					<input type="hidden" id="param" name="param" value="${params}"
									class="input_text178" /></td>
					<table id="tableList"
						class="table  table-bordered table-condensed table-hover ">
						<thead>
							<tr>
								<th>渠道</th>
								<th>占比（%）</th>
							</tr>
						</thead>
						<tbody id="prepareListBody">
							<tr>
								<td>大金融</td>
								<td><input type="text" id="zcj" name="zcj" value="${zcj}"
									class="input_text178" /></td>
								</td>
							</tr>
							<tr>
								<td>金信</td>
								<td><input type="text" id="jinxin" name="jinxin" value="${jinxin}"
									class="input_text178" /></td>
								</td>
							</tr>
						</tbody>
					</table>
					</form>
				</div>
			</div>
		</div>
	
</body>
</html>