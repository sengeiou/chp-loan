<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript" src="${context }/js/payback/dateUtils.js"></script>
<script>
$(document).ready(function(){
	checkMoney();
	/**
	 * @function 图片预览下载
	 */
	$("[name='previewPngBtn']").each(function(){
		$(this).bind('click',function(){
			var url=ctx + "//borrow/payback/dealPayback/previewPng?docId="+$(this).attr('docId');
			art.dialog.open(url, {  
		         id: 'previewPngBtn',
		         title: '图片预览',
		         lock:true,
		         width:800,
		     	 height:500
		     },  
		     false);
		});
	});
	
	$("[name='downPng']").each(function(){
		$(this).bind('click',function(){
			window.location.href=ctx+"/borrow/payback/dealPayback/downPng?docId="+$(this).attr('docId');
		});
	});
	
	/**
	 * @function 手动匹配弹出框
	 */
	var idx;
	$("[name='matchingPayback']").each(function(){
		$(this).bind('click',function(){
			$('#infoId').val($(this).attr('infoId'));
			idx =  $(this).attr('id');
			var outDepositTime = $(this).attr("tranDepositTime");
			var outDepositTimeD = new Date(outDepositTime)
			var data = {outDepositTime: typeof(DateUtils.format(outDepositTimeD, 'yyyy-MM-dd'))=="undefined"?'':DateUtils.format(outDepositTimeD, 'yyyy-MM-dd'),
						outReallyAmount:typeof($(this).attr("reallyAmount"))=="undefined"?'':$(this).attr("reallyAmount"),
						outEnterBankAccount:$('#storesInAccount').val()};
			$.ajax({  
				   type : "POST",
				   async:false,
				   url : ctx+"/car/refund/carPendingRepayMatch/getTransferOutMatchList",
				   data:data,
				   datatype : "json",
					success : function(data){
						var jsonData = eval("("+data+")");
						$('#matchingTable').bootstrapTable('destroy');
						$('#matchingTable').bootstrapTable({
							data:jsonData,
							dataType: "json",    
							pageSize: 10,
							pageNumber:1
						});
					},
					error: function(){
							top.$.jBox.tip('服务器没有返回数据，可能服务器忙，请重试!','warning');
						}
				});
		});
	});
	
	/**
	 * @function 手动匹配
	 */
	$('#matchingSemiautomatic').click(function(){	
		if($('#matchingTable').bootstrapTable('getSelections').length<=0)
		{
			art.dialog.alert("请选择要匹配的银行流水记录再进行匹配!");
			return false;
		}
		var checkData = $('#matchingTable').bootstrapTable('getSelections')[0],
			id = $('[name="id"]').val();
		$.ajax({  
			   type : "POST",
			   async:false,
			   data:{
				   infoId:$('#infoId').val(),
				   outId:checkData.id,
				   applyId:id
			   },
				url : ctx+"/car/refund/carPendingRepayMatch/handMatching",
				datatype : "json",
				success : function(success){
					if(success){
						art.dialog.alert("匹配完成!",function(){
							$("#matchingPayback_"+$('#infoId').val()).val("已匹配");
							$("#matchingPayback_"+$('#infoId').val()).attr("disabled","disabled");
						});
					}
				},
				error: function(){
						top.$.jBox.tip('服务器没有返回数据，可能服务器忙，请重试!','warning');
					}
			});
	})

	$('#applyPaySaveBtn').click(function(){
		if($('[name="matchingResult"]:checked').val()==1){
			if(parseFloat($("#aleadyMatchMoney").val())<parseFloat($("#applyRepayAmount").val()))
			{
				art.dialog.alert("无法提交匹配成功，已匹配转账信息的实际到账金额之和少于此单的申请还款金额!");
				$('[name="matchingResult"]').attr("checked",false);//取消匹配成功的选中
				return false;
			}else{
				$("#paybackInfoForm").validator();
			}
		}else{
			$("#paybackInfoForm").validator();
		}
	})
});	

/*
 * @function 日期格式化
 */
function dataFormatter(value, row) {
	var d = new Date(value);
    return DateUtils.format(d, 'yyyy-MM-dd');
}

function checkMoney()
{
	var aleadyMatchMoney = $("#aleadyMatchMoney").val();
	$("[name='matchingPayback']").each(function(){
		if($(this).attr("disabled")=="disabled")
		{
			var money = typeof($(this).attr("reallyAmount"))=="undefined"?'':$(this).attr("reallyAmount");		
			if(money!='')
			{
				aleadyMatchMoney = accAdd(parseFloat(aleadyMatchMoney),parseFloat(money));
			}
		}
	})
	$("#aleadyMatchMoney").val(aleadyMatchMoney);
}
//精确加法运算
function accAdd(arg1,arg2){
	var r1,r2,m;
	try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
	try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
	
	m=Math.pow(10,Math.max(r1,r2)) 
	return (arg1*m+arg2*m)/m
}
</script>
<meta name="decorator" content="default" />
<style type="text/css">
.pagination {
	background: none;
	position: static;
	width: auto
}

#file {
	border-radius: 8px;
	width: 70%;
	height: 70%;
	margin：auto;
}
</style>
</head>	
<body>
	<div id="messageBox" class="alert alert-success ${empty message ? 'hide' : ''}"><button data-dismiss="alert" class="close">×</button>
		<label id="loginSuccess" class="success">${message}</label>
	</div>
	<form id="paybackInfoForm" action="${ctx}/car/refund/carPendingRepayMatch/repayMatchSubmit" method="post" class="form-horizontal" modelAttribute="carPendingRepayMatchInfo">
		<input type="hidden" name="id" value="${paybackMatch.id}" />
		<input type="hidden" id="infoId" value="ss" />
		<input type="hidden" name="urgeId" value="${paybackMatch.urgeId}" />
		<input type="hidden" id="applyRepayAmount" value="${paybackMatch.applyRepayAmount}" />
		<input type="hidden" id="aleadyMatchMoney" value="0" />
		<h2 class="f14 pl10">基本信息&nbsp;&nbsp;&nbsp;</h2>
		<div class="box2 ">
			<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">客户姓名：</label> 
						<input type="text" class="input_text178" value="${paybackMatch.loanCustomerName}" readonly>
					</td>
					<td><label class="lab">证件号码：</label> 
						<input type="text" class="input_text178" value="${paybackMatch.customerCertNum}" readonly>
					</td>
					<td><label class="lab">合同编号：</label> 
						<input type="text" class="input_text178" value="${paybackMatch.contractCode}" readonly/>
					</td>
				</tr>
				<tr>
					<td><label class="lab">产品类型：</label> 
						<input type="text" class="input_text178" value="${paybackMatch.productType}" readonly/>
					</td>
					<td><label class="lab">合同金额(元)：</label> 
						<input type="text" class="input_text178" value="<fmt:formatNumber value='${paybackMatch.contractAmount}' pattern="#,##0.00"/>" readonly>
					</td>
					<td><label class="lab">期供金额(元)：</label> 
						<input type="text"class="input_text178" value="-" readonly>
					</td>
				</tr>
				<tr>
					<td><label class="lab">借款期限：</label> 
						<input type="text" class="input_text178" value="${paybackMatch.contractMonths}" readonly>
					</td>
					<td><label class="lab">借款状态：</label> 
						<input type="text" class="input_text178" value="${paybackMatch.dictLoanStatus}" readonly>
					</td>
				</tr>
			</table>
		</div>
		<h2 class="f14 pl10 mt20">还款信息</h2>
		<div class="box2 ">
		 <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
			<tr>
				<td><label class="lab">蓝补金额：</label>
					<input type="text" class="input_text178" value="<fmt:formatNumber value='${paybackMatch.blueAmount }' pattern="#,##0.00"/>" readonly />
				</td>
			</tr>
			<tr>	
				<td><label class="lab"> 还款方式:</label> 
					<input type="radio" id="paymentsManual" name="dictRepayMethod" value="1" disabled="disabled"
					 checked/>汇款/转账
				</td>
			</tr>
			</table>
		</div>
		 <h2 class="f14 pl10 mt20">汇款账户&nbsp;&nbsp;&nbsp;</h2>
		<div class="box2 " id="qishu_div1">
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">实际到账总额：</label> 
					<input type="text" id="reallyAmount" class="input_text178" value="<fmt:formatNumber value='${paybackMatch.reallyAmount}' pattern="#,##0.00"/>" readonly/>
					</td>
					<td><label class="lab">存入银行：</label> 
						<input type="hidden" id="storesInAccount" name="storesInAccount" value="${paybackMatch.storesInAccount}"/>
						<select class="select178_24" disabled>
							<option>请选择</option>
							<c:forEach var="item" items="${middlePersonList}">
								<option value="${item.bankCardNo}" <c:if test="${paybackMatch.storesInAccount==item.bankCardNo}">selected</c:if>>${item.midBankName}</option>
							</c:forEach>
						</select>
					</td>
					<td><label class="lab">还款申请日期：</label> 
						<input type="text" name="applyPayDay" class="input_text178"
						value="<fmt:formatDate value='${paybackMatch.applyPayDay}' type='date' pattern="yyyy-MM-dd" />" readonly/>
					</td>
				</tr>
			</table>
		</div>
		<div id="paybackTransferInfo">
			<table id='appendTab' class="table table-bordered table-condensed" cellpadding=" 0" cellspacing="0" border="0" width="100%">
				<tr>
					<td>存款方式</td>
					<td>存款时间</td>
					<td>实际到账金额</td>
					<td>实际存入人</td>
					<td>存款凭条</td>
					<td>上传人</td>
					<td>上传时间</td>
					<td>操作</td>
				</tr>
				<c:forEach items="${pendingDetailList}" var="item" varStatus="status">
					<tr>
						<td>
							${item.dictDeposit}
						</td>
						<td>
							<fmt:formatDate value='${item.tranDepositTime}' type='date' pattern="yyyy-MM-dd" />
						</td>
						<td>
							<fmt:formatNumber value='${item.reallyAmount}' pattern="#,##0.00"/>
						</td>
						<td>
							${item.depositName}
						</td>
						<td>
							<input type="button" class="btn_edit" name = "previewPngBtn" value="${item.uploadFilename}" docId = ${item.uploadPath }>
						<td>
							&nbsp;${item.uploadName}
						</td>
						<td>
							<fmt:formatDate value='${item.uploadDate}' type='date' pattern="yyyy-MM-dd" />
						</td>
						<td>
							<c:choose>
								<c:when test="${fn:trim(item.matchingResult)=='2'}">
								<input type="button" class="btn_edit" data-toggle="modal" data-target="#matchingPayMal" id="matchingPayback_${item.id}" name="matchingPayback" value="已匹配"
								infoId = "${item.id }" tranDepositTime="${item.tranDepositTime}" reallyAmount="${item.reallyAmount }" disabled="disabled">
								</c:when>
								<c:otherwise>
								<input type="button" class="btn_edit" data-toggle="modal" data-target="#matchingPayMal" id="matchingPayback_${item.id}" name="matchingPayback" value="匹配"
								infoId = "${item.id }" tranDepositTime="${item.tranDepositTime}" reallyAmount="${item.reallyAmount }">
								</c:otherwise>
							</c:choose>
							<a type="button" class="btn_edit" id ="downPng" name="downPng" docId = ${item.uploadPath } fileName="${item.uploadFilename}">下载存款凭条</a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<div class="box2 mb10" id="qishu_div1">
			<tr>
				<td><label class="lab">匹配结果：</label>&nbsp; 
					<input type="radio" name="matchingResult" value="1" required onclick="matchValid();"> 匹配成功 
					<input type="radio" name="matchingResult" value="2" required onclick="matchValid();"> 匹配退回
				</td>
			</tr>
		</div>
		<div class="box2 mt20">
			<label class="lab">审核意见：</label>
			<textarea class="textarea_refuse" id="auditCheckExamine" name="auditCheckExamine" required></textarea>
		</div>
		<div class="pull-right mt10 pr10" id="qishu_div4">
			<input class="btn btn-primary" type="submit" id="applyPaySaveBtn" value="确认"/>
			<input  class="btn btn-primary" type="button" id="btnCancel" value="返 回" 
			onclick="JavaScript:window.location='${ctx}/car/refund/carPendingRepayMatch/pendingMatchJump'" />
		</div>
	</form>
	<div class="modal fade" id="matchingPayMal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 900px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title">手动匹配</h4>
				</div>
				<table class="table" id="matchingTable" data-toggle="table"  data-pagination="true">
					<thead>
						<tr>
							<th data-field="orderNumber">序号</th>
							<th data-field="outDepositTime" data-formatter="dataFormatter">存款日期</th>
							<th data-field="outReallyAmount">实际到账金额</th>
							<th data-field="outEnterBankAccount">存入账号</th>
							<th data-field="outDepositName">存款人</th>
							<th data-radio="true">操作</th>
						</tr>
					</thead>
				</table>
				<div class="modal-footer">
					<a type="button" class="btn btn-primary" data-dismiss="modal">关闭</a>
					<a type="button" class="btn btn-primary" data-dismiss="modal" id="matchingSemiautomatic">匹配</a>
				</div>
			</div>
		</div>
	</div>		
</body>
</html>
