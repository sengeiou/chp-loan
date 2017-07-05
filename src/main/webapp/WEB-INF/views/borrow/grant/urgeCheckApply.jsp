<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context }/js/grant/urgeGuarante.js"></script>
<script type="text/javascript" src="${context }/js/grant/applyPayback.js"></script>
<script type="text/javascript" src="${context }/js/payback/jquery-barcode-1.3.3.js"></script>
<script src="${ctxStatic}/bootstrap/3.3.5/js/moment.js" type="text/javascript"></script>
<script src="${ctxStatic}/bootstrap/3.3.5/js/daterangepicker.js" type="text/javascript"></script>
<link href="${ctxStatic}/bootstrap/3.3.5/awesome/daterangepicker-bs3.css" type="text/css" rel="stylesheet" />

<script src="${context}/static/ckfinder/plugins/gallery/colorbox/jquery.colorbox-min.js"></script>
<link media="screen" rel="stylesheet" href="${context}/static/ckfinder/plugins/gallery/colorbox/colorbox.css" />
</head>
<body>
	<form id="applyPayLaunchForm" enctype="multipart/form-data" action="${ctx}/borrow/payback/applyPayback/saveApplyPayLaunch" method="post">
		<div id="qishu_div1" class="box2" style="display:block ;border-top:0;border-bottom:0">
		<h2 class="f14 ml10">催收服务费查汇款账户&nbsp;&nbsp;&nbsp;</h2>
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">实际到账总额：</label> 
						<input type="text" class="input_text178" id="transferAmount" name="paybackApply.transferAmount" readonly/>
					</td>
					<td><label class="lab">存入账户：</label> 
						<select class="select78_24" id="storesInAccount" name="paybackTransferInfo.storesInAccount">
							<option>请选择</option>
							<c:forEach var="item" items="${middlePersonList }">
								<option value="${item.bankCardNo }">${item.midBankName}</option>
							</c:forEach>
						</select>
					</td>
					<td><label class="lab">还款申请日期：</label> 
						<input type="text" class="input_text178" id="applyPayDay" name="paybackApply.applyPayDay" readonly>
					</td>
				</tr>
			</table>
		</div>
		
		<div id="qishu_div0" style="display: block;">
			<table id='appendTab' class="table table-bordered">
				<tr>
					<th colspan="8">
						<h1 class="f14 ml10">汇款账号&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<a  onclick="appendRow()" style="background-size:100% ;height:14px;width:14px" ><img  src="${context }/static/images/jiahao.png"></a>&nbsp;&nbsp;&nbsp;&nbsp;
							<a  onclick="deleteRow()" style="background-size:100%;height:14px;width:14px"><img alt="" src="${context }/static/images/jianhao.png"></a>
						</h1>
					</th>
				</tr>
				<tr>
					<td>存款方式</td>
					<td>到账日期</td>
					<td>实际到账金额</td>
					<td>实际存款人</td>
					<td>存款凭条</td>
					<td>上传人</td>
					<td>上传时间</td>
				</tr>
				<tr id='appendId'>
					<td><input type="text" class="input_text178" id="dictDeposit" name="paybackTransferInfo.dictDeposit" /></td>
					<td><input class="input_text178" name="paybackTransferInfo.tranDepositTimeStr" type="text" /></td>
					<td><input type="text" class="input_text178" id="applyAmountCopy" name="paybackTransferInfo.reallyAmountStr" /></td>
					<td><input type="text" class="input_text178" id="depositName" name="paybackTransferInfo.depositName" /></td>
					<td><input name="files" type="file"></td>
					<td><input type="text" class="input_text178" id="uploadName" name="paybackTransferInfo.uploadName" / readonly />&nbsp;</td>
					<td><input type="text" class="input_text178" id="uploadDate" name="paybackTransferInfo.uploadDate" readOnly /></td>
				</tr>
			</table>
		</div>
		
		<div id="qishu_div4" class="tcenter mt10 pr30 mb10">
			<input type="button" class="btn btn-primary" id="applyPayLaunchBtn" value="确认" />
		</div>
	</form>
</body>
</html>
