<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>退款审核</title>
<script src="${context}/js/car/refund/refundAudit.js" type="text/javascript"></script>
<meta name="decorator" content="default" />
</head>
<body>
	<div >
		<h2 class="f14 pl10">退款审核
					
	</div>
	<input type="hidden" id="id" value="${id}">
				<div class="control-group">
					<table class="table1" cellpadding="0" cellspacing="0"
						border="0" width="100%">
						<tr>
							<td style="text-align: left"><label class="lab">客户卡号：</label>
							${cardNo}
							</td>
						</tr>
						<tr>
							<td style="text-align: left"><label class="lab">审核结果：</label>
							<input type="radio" class="mr10" name="sort" checked="checked" value="审核通过" onClick="isReasonShow();">审核通过 
							<input type="radio" class="mr10" name="sort"  value="审核拒绝" onClick="isReasonShow();">审核拒绝
							</td>
						</tr>
						<tr>
						<td style="display:none;text-align: left" id="ta" ><label class="lab">拒绝原因：</label>
							<textarea rows="" cols="" class="textarea_refuse" id="refuseReason" name="refuseReason"></textarea>
							
							</td>
						</tr>
					</table>
			</div>
				<div class="tright mt10 pr10">
					<button class="btn btn-primary" id="auditSure">提交</button>
					<button class="btn btn-primary" id="closeBtn">取消</button>
				</div>
					
</body>
</html>