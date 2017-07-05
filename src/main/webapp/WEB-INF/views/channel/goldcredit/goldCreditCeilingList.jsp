<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="decorator" content="default" />
<title>上限列表</title>
<script src="${context}/js/common.js" type="text/javascript"></script>
<script src="${context}/js/channel/goldcredit/onlyNumber.js" type="text/javascript"></script>
<script type="text/javascript" src="${context}/js/channel/goldcredit/popuplayer.js"></script>
<script type="text/javascript">
 $(function() {
 	/* $('#dis input').each(function(){
 		$(this).attr("readonly","readonly");
 	}) */
 	$('#dis2').hide();
}); 
$(function() {
 	/* $('#dis input').each(function(){
 		$(this).attr("readonly","readonly");
 	}) */
 	$('#dis2').hide();
}); 
function page(n, s) {
	if (n)
		$("#pageNo").val(n);
	if (s)
		$("#pageSize").val(s);
	$("#disCardForm").attr("action","${ctx }/channel/goldcredit/ceiling/init");
	$("#disCardForm").submit();
	return false;
}
	$(function() {
		//获取table数据集
		var tlen = $("#tableList tbody>tr");
		//如果只有一条记录,则需要判断
		if(tlen.length==1){
			//获取tr的第二列
			var $td = $("#tableList tbody>tr").find("td:eq(1)");
			//第二列的文本内容
			var tdtext = $td.text();
			//如果=='-',则表示没有数据,则需要做以下操作,此行的第一个列增加colspan属性,删除之后的数据
			if(tdtext=='-'){
				//获取th的列数
				var theadtr = $("#tableList thead>tr").find("th");
				//
				var len = theadtr.length;
				$("#tableList tbody>tr").find("td:eq(0)").attr("colspan",len).css("text-align","center");
				tlen.children("td:gt(0)").remove();
			}
		}
		$.popuplayer(".edit");
		$("#clearBtn").click(
				function() {
					$(':input', '#disCardForm').not(':submit,:button, :reset').val('')
						.removeAttr('checked').removeAttr('selected');
				});
		loan.getstorelsit("storesName", "storeOrgId");
		// 点击全选
		$("#checkAll").click(function() {
			//总笔数
			var amount = $("#hiddenTotalAccount").val();
			//批借总金额
			var hiddenTotalCeilingsum = $("#hiddenTotalCeilingsum").val();
			//合同总金额
			var hiddenTotalContractAmount = $("#hiddenTotalContract").val();
			//实放总金额
			var hiddenTotalGrant = $("#hiddenTotalGrant").val();
			$('input[name="checkBoxItem"]').attr("checked", this.checked);
			if ($(this).is(":checked")){
				amount = hiddenTotalCeilingsum = hiddenTotalContractAmount = hiddenTotalGrant = 0;
				$('input[name="checkBoxItem"]').each(function (){
					var deductAmount = parseFloat($.isBlank($(this).attr("auditAmount")));
					var contractAmount = parseFloat($.isBlank($(this).attr("contractAmount")));
					var grantAmount = parseFloat($.isBlank($(this).attr("grantAmount")));
					amount = amount + 1;
					hiddenTotalCeilingsum = hiddenTotalCeilingsum + deductAmount;
					hiddenTotalContractAmount = hiddenTotalContractAmount + contractAmount;
					hiddenTotalGrant = hiddenTotalGrant + grantAmount;
				});
				$("#hiddenAccount").val(amount);
				$("#hiddenCeilingsum").val(hiddenTotalCeilingsum);
				$("#hiddenContract").val(hiddenTotalContractAmount);
				$("#hiddenGrant").val(hiddenTotalGrant);
			}else {
				$("#hiddenAccount").val(0);
				$("#hiddenCeilingsum").val(0.00);
				$("#hiddenContract").val(0.00);
				$("#hiddenGrant").val(0.00);
			}
			$("#acount").text(amount);
			$("#amount").text(fmoney(hiddenTotalCeilingsum,2));
			$("#contract").text(fmoney(hiddenTotalContractAmount,2));
			$("#grant").text(fmoney(hiddenTotalGrant,2));
			 
		});
		// 计算金额,
		var $subBox = $(":input[name='checkBoxItem']");
		$subBox
				.click(function() {
					// 记录总金额，当length为0时，进行总金额的处理
					var totalDeduct = $("#hiddenTotalAccount").val();
					var hiddenCeilingsum = $("#hiddenTotalCeilingsum").val();
					var hiddenContractAmount = $("#hiddenTotalContract").val();
					var hiddenGrant = $("#hiddenTotalGrant").val();
					// 获得单个单子的金额
					var deductAmount = parseFloat($.isBlank($(this).attr("auditAmount")));
					var contractAmount = parseFloat($.isBlank($(this).attr("contractAmount")));
					var grantAmount = parseFloat($.isBlank($(this).attr("grantAmount")));
					
					var account = 0,amount = 0.00,contract = 0.00,grant = 0.00;
					if ($(this).is(':checked')) {
						account = parseInt($("#hiddenAccount").val()) + 1;
						amount = parseFloat($("#hiddenCeilingsum").val()) + deductAmount;
						contract = parseFloat($("#hiddenContract").val()) + contractAmount;
						grant = parseFloat($("#hiddenGrant").val()) + grantAmount;
						$("#hiddenAccount").val(account);
						$("#hiddenCeilingsum").val(amount);
						$("#hiddenContract").val(contract);
						$("#hiddenGrant").val(grant);
					} else {
						if ($(":input[name='checkBoxItem']:checked").length == 0) {
							account = totalDeduct;
							amount = hiddenCeilingsum;
							contract = hiddenContractAmount;
							grant = hiddenGrant;
							$("#hiddenAccount").val(0);
							$("#hiddenCeilingsum").val(0.00);
							$("#hiddenContract").val(0.00);
							$("#hiddenGrant").val(0.00);
						} else {
							account = parseInt($("#hiddenAccount").val()) - 1;
							amount = parseFloat($("#hiddenCeilingsum").val()) - deductAmount;
							contract = parseFloat($("#hiddenContract").val()) - contractAmount;
							grant = parseFloat($("#hiddenGrant").val()) - grantAmount;
							$("#hiddenAccount").val(account);
							$("#hiddenCeilingsum").val(amount);
							$("#hiddenContract").val(contract);
							$("#hiddenGrant").val(grant);
						}
					}
					$("#acount").text(account);
					$("#amount").text(fmoney(amount,2));
					$("#contract").text(fmoney(contract,2));
					$("#grant").text(fmoney(grant,2));
					$("#checkAll")
							.attr(
									"checked",
									$subBox.length == $(":input[name='checkBoxItem']:checked").length ? true
											: false);
				});

		// 列表导出
		$("#dao")
				.click(
						function() {
							
								window.location.href = ctx
										+ "/channel/goldcredit/ceiling/exportCeiling?"+$("#disCardForm").serialize()
										+ "&cid=" + selectedList();
						});
		
	});
	//格式化，保留两个小数点
	function fmoney(s, n) {  
	    n = n > 0 && n <= 20 ? n : 2;  
	    s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";  
	    var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];  
	    t = "";  
	    for (i = 0; i < l.length; i++) {  
	        t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");  
	    }  
	    return t.split("").reverse().join("") + "." + r;  
	}
	function checkLoanApply(loanCode){
		window.location.href = ctx
		+ "/channel/goldcredit/ceiling/checkInfo?loanCode="+loanCode;
	}
	function selectedList() {
		var cid = "";
		if ($(":input[name='checkBoxItem']:checked").length > 0) {
			$(":input[name='checkBoxItem']:checked").each(function() {
				if (cid != "") {
					cid += "," + $(this).attr("loanCode");
				} else {
					cid = $(this).attr("loanCode");
				}
			});
		}
		return cid;
	}
 $(function(){
	 $(".onlyNum","#cellingSettingFrom").onlyNum();
	 $('.onlyFloat','#TGSettingFrom').onlyFloat();
	 $(".onlyNum","#cellingSettingFrom").click(function (){
		 if (!/^[1-9]\d+/g.test($(this).val()))
			 $(this).val($(this).val().replace(/^0*/,''));
	 }).trigger("click");
	 //如果是有数据的话,禁用提交按钮
	 var usingFlag = '${cellNum.usingFlag}';
	 banButton(usingFlag);
	 function banButton (usingFlag) {
		 if (usingFlag == '0' || $.trim(usingFlag).length != 0) {
			 $(".onlyNum").attr("disabled",true);
			 $("#confirmBtn").attr("disabled",true);
			 $("#archiveBtn").attr("disabled",false);
		 } else {
			 $(".onlyNum").attr("disabled",false);
			 $("#confirmBtn").attr("disabled",false);
			 $("#archiveBtn").attr("disabled",true);
		 }
	 }
	 //(CHP AND JINXIN)提交上限数量信息
	 $("#confirmBtn").click(function (){
		 var flag = false,chp = 0,jinxin= 0,zcj= 0,p2p=0;
		 $(".onlyNum","#cellingSettingFrom").each(function (){
			 //判断数字是不是以零开始的，如果是以零开始的，替换掉零
			 if (!/^[1-9]\d+/g.test($(this).val()))
				 $(this).val($(this).val().replace(/^0*/,''));
			 if (!$(this).val()){
				 return true;
			 } else {
				 if (/^CHP/.test($(this).attr('name'))) {
					 chp += parseInt($(this).val());
				 } else if(/^goldCredit/.test($(this).attr('name'))) {
					 jinxin += parseInt($(this).val());
				 } else if(/^zcj/.test($(this).attr('name'))) {
					 zcj += parseInt($(this).val());
				 }else if(/^p2p/.test($(this).attr('name'))) {
					 p2p += parseInt($(this).val());
				 }
				 flag = true;
			 }
			
		 });
		 if (flag) {
			 var $flag = $(this);
			 var flag1 = parseInt($flag.attr("flag"));
			 var flag11 = flag1
			 $flag.attr("flag",flag11 ++);
			 if (flag1 == 0){
				 art.dialog.confirm("确认提交上限信息？",function (){
					 $.post(ctx+"/channel/goldcredit/ceiling/settingCeilingNum", 
						$("#cellingSettingFrom").serialize(), 
						function(data){
							if (data == 'true' || data == true){
								$(".chp").val(chp);
								$(".jinxin").val(jinxin);
								$(".zcj").val(zcj);
								$(".p2p").val(p2p);
								art.dialog.alert('上限信息设定成功！');
								banButton('0');
							}else{
								art.dialog.alert('上限信息设定失败！');
							}
						});
				 });
			 }
		 } else {
			 art.dialog.alert("上限数量不能够为空,请输入！",function (){
				 $(".onlyNum","#cellingSettingFrom").first().focus();
			 });
		 }
	 });
	 //(CHP AND JINXIN)上限数量归档
	 $("#archiveBtn").click(function() {
		var message;
		 $.ajax({
			 url:ctx+"/channel/goldcredit/ceiling/ceilingSumChannel?model=0",
			 data:$("#disCardForm").serialize(),
			 success:function(data){
				 message=data;
			 },
			 async:false
		 });
		art.dialog.confirm(message,function (){
			$.post(ctx+"/channel/goldcredit/ceiling/archive", 
			function(data){
				if (data == 'true' || data == true){
					banButton();
					$(":input","#cellingSettingFrom").not(':submit,:button, :reset').val('');
					art.dialog.alert('上限数量已失效!',function (){
						$('#disCardForm').submit();
					});
				}else {
					art.dialog.alert('上限数量未失效!');
				}
			});
		});
	 });
	 //禁用TG的按钮
	 if(!'${goldCelling}') {
		 $(".onlyFloat","#TGSettingFrom").not(':submit,:button, :reset').attr("disabled",false);
		 $("#TGConfirmBtn","#TGSettingFrom").attr("disabled",false);
		 $("#TGArchiveBtn","#TGSettingFrom").attr("disabled",true);
	 } else {
		 $(".onlyFloat","#TGSettingFrom").not(':submit,:button, :reset').attr("disabled",true);
		 $("#TGConfirmBtn","#TGSettingFrom").attr("disabled",true);
		 $("#TGArchiveBtn","#TGSettingFrom").attr("disabled",false);
	 }
	 //TG提交上限额度信息
	 $("#TGConfirmBtn").click(function (){
		 var TGValue = $(".onlyFloat").val();
		 if (!TGValue) {
			 art.dialog.alert("TG上限额度不能够为空,请输入!",function (){
				 $(".onlyFloat").focus();
			 });
			 return false;
		 } else {
			 if (!/^[1-9]\d+/g.test($(this).val())) {
				 $(".onlyFloat").val(TGValue.replace(/^0*/,''));
			 }
		 }
		 var $flag = $(this);
		 var sflag = $flag.attr("flag");
		 var flag = parseInt(sflag);
		 $flag.attr("flag",++flag );
		 if (sflag == 0) {
			 art.dialog.confirm("确认提交TG上限额度?",function () {
				 $.post(ctx+"/borrow/trusteeship/ceiling/gaCeiling", 
				 {"ceilingMoney":TGValue}, 
				 function(data){
					if (data == 'true' || data == true){
						art.dialog.alert('TG上限信息设定成功!');
						$(".TG").val(TGValue);
						$(".onlyFloat","#TGSettingFrom").not(':submit,:button, :reset').attr("disabled",true);
						$("#TGConfirmBtn","#TGSettingFrom").attr("disabled",true);
						$("#TGArchiveBtn","#TGSettingFrom").attr("disabled",false);
					}else {
						art.dialog.alert('TG上限信息设定失败!');
					}
				});
			 });
		 }
	 });
	//(TG)上限额度归档
	 $("#TGArchiveBtn").click(function() {
		 var account = 0;
		 var ceilingsum = 0;
		 $.ajax({
			 url:ctx+"/channel/goldcredit/ceiling/ceilingSum?model=1",
			 data:$("#disCardForm").serialize(),
			 success:function(data){
				 if(data!=null){
					 account = data[0].account;
					 ceilingsum = data[0].ceilingsum;
				 }
			 },
			 async:false
		 });
		art.dialog.confirm("批借金额="+ceilingsum+",总笔数="+account+",是否确认TG额度失效?",function (){
			$.post(ctx+"/channel/goldcredit/ceiling/goldAccount", 
			function(data){
				if (data == 'true' || data == true){
					$("#TGConfirmBtn","#TGSettingFrom").attr("disabled",false);
					$("#TGArchiveBtn","#TGSettingFrom").attr("disabled",true);
					$(".onlyFloat","#TGSettingFrom").not(':submit,:button, :reset').attr("disabled",false);
					$(":input","#TGSettingFrom").not(':submit,:button, :reset').val('');
					art.dialog.alert('TG额度已失效!',function (){
						$('#disCardForm').submit();
					});
				}else {
					art.dialog.alert('TG额度未失效!');
				}
			});
		});
	 });
 });
;(function($){
  $.isBlank = function(obj){
    return(!obj || $.trim(obj) === "") ? 0 : obj;
  };
})(jQuery);
</script>
<style>
.input_text70{width:80px}
.table1 tr{height:49px}
.pagination{
  position:fixed;
  bottom:20px!important;
}
</style>
</head>
<body>
<div class="row control-group" style="margin:0">
	<div class="  l" style="width:55.5%">
		<form:form id="cellingSettingFrom" modelAttribute="cellNum" method="post">
			<table class="table" cellpadding="0" cellspacing="0" border="0" width="100%" style="background: rgb(251, 251, 251)">
				<thead>
					<tr>
						<th>渠道标识</th>
						<th>数量上限1</th>
						<th>数量上限2</th>
						<th>数量上限3</th>
						<th>数量上限4</th>
						<th>数量上限5</th>
						<th>剩余数量</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>财富</td>
						<td><input type = 'text' maxlength="10" class = 'input_text70 onlyNum' name = "CHP1" value = "${cellNum.CHP1 }" style="ime-mode:disabled" /></td>
						<td><input type = 'text' maxlength="10" class = 'input_text70 onlyNum' name = "CHP2" value = "${cellNum.CHP2 }" style="ime-mode:disabled"  /></td>
						<td><input type = 'text' maxlength="10" class = 'input_text70 onlyNum' name = "CHP3" value = "${cellNum.CHP3 }" style="ime-mode:disabled" /></td>
						<td><input type = 'text' maxlength="10" class = 'input_text70 onlyNum' name = "CHP4" value = "${cellNum.CHP4 }" style="ime-mode:disabled"/></td>
						<td><input type = 'text' maxlength="10" class = 'input_text70 onlyNum' name = "CHP5" value = "${cellNum.CHP5 }" style="ime-mode:disabled" /></td>
						<td><input type = 'text' class = 'input_text70 chp' value = "${cellNum.CHPResidual }" disabled/></td>
					</tr>
					<tr>
						<td>金信</td>
						<td><input type = 'text' maxlength="10" class = 'input_text70 onlyNum' name = "goldCredit1" value = "${cellNum.goldCredit1}" style="ime-mode:disabled "/></td>
						<td><input type = 'text' maxlength="10" class = 'input_text70 onlyNum' name = "goldCredit2" value = "${cellNum.goldCredit2}" style="ime-mode:disabled "/></td>
						<td><input type = 'text' maxlength="10" class = 'input_text70 onlyNum' name = "goldCredit3" value = "${cellNum.goldCredit3}" style="ime-mode:disabled "/></td>
						<td><input type = 'text' maxlength="10" class = 'input_text70 onlyNum' name = "goldCredit4" value = "${cellNum.goldCredit4}" style="ime-mode:disabled "/></td>
						<td><input type = 'text' maxlength="10" class = 'input_text70 onlyNum' name = "goldCredit5" value = "${cellNum.goldCredit5}" style="ime-mode:disabled "/></td>
						<td><input type = 'text' class = 'input_text70 jinxin' value = "${cellNum.goldCreditResidual}" disabled/></td>
					</tr>
					<tr id="dis2">
						<td>P2P</td>
						<td><input type = 'text' maxlength="10" class = 'input_text70 onlyNum' name = "p2p1" value = "${cellNum.p2p1}" style="ime-mode:disabled "/></td>
						<td><input type = 'text' maxlength="10" class = 'input_text70 onlyNum' name = "p2p2" value = "${cellNum.p2p2}" style="ime-mode:disabled "/></td>
						<td><input type = 'text' maxlength="10" class = 'input_text70 onlyNum' name = "p2p3" value = "${cellNum.p2p3}" style="ime-mode:disabled "/></td>
						<td><input type = 'text' maxlength="10" class = 'input_text70 onlyNum' name = "p2p4" value = "${cellNum.p2p4}" style="ime-mode:disabled "/></td>
						<td><input type = 'text' maxlength="10" class = 'input_text70 onlyNum' name = "p2p5" value = "${cellNum.p2p5}" style="ime-mode:disabled "/></td>
						<td><input type = 'text' class = 'input_text70 p2p' value = "${cellNum.p2pResidual}" disabled/></td>
					</tr>
					<tr id="dis">
						<td>ZCJ</td>
						<td><input type = 'text' maxlength="10" class = 'input_text70 onlyNum' name = "zcj1" value = "${cellNum.zcj1}" style="ime-mode:disabled "/></td>
						<td><input type = 'text' maxlength="10" class = 'input_text70 onlyNum' name = "zcj2" value = "${cellNum.zcj2}" style="ime-mode:disabled "/></td>
						<td><input type = 'text' maxlength="10" class = 'input_text70 onlyNum' name = "zcj3" value = "${cellNum.zcj3}" style="ime-mode:disabled "/></td>
						<td><input type = 'text' maxlength="10" class = 'input_text70 onlyNum' name = "zcj4" value = "${cellNum.zcj4}" style="ime-mode:disabled "/></td>
						<td><input type = 'text' maxlength="10" class = 'input_text70 onlyNum' name = "zcj5" value = "${cellNum.zcj5}" style="ime-mode:disabled "/></td>
						<td><input type = 'text' class = 'input_text70 zcj' value = "${cellNum.zcjResidual}" disabled/></td>
					</tr>
					<tr>
						<td colspan = "7">
							<div class="tright pr30 pb5">
									<input class="btn btn-primary" type="button" value="确认" id="confirmBtn" flag = '0'/>
									<input class="btn btn-primary" type="button" value="归档" id="archiveBtn"/>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
			
		</form:form>
		<form:form id="TGSettingFrom" modelAttribute="params" method="post">
			<table class="table" cellpadding="0" cellspacing="0" border="0" width="100%" style="background: rgb(251, 251, 251)">
				<thead>
					<tr>
						<th>模式</th>
						<th>上限额度</th>
						<th>剩余额度</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>TG</td>
						<td><input type = 'text' maxlength="10" class = 'input_text70 onlyFloat' name = "GOLD_CREDIT1" value = "${goldCelling.trusteeshipquotallimit}" style="ime-mode:disabled"/></td>
						<td><input type = 'text' maxlength="10" class = 'input_text70 TG' name = "TG" value = "${goldCelling.surplusmoney}" disabled/></td>
						<td>
							<div class="tright pr30 pb5">
								<input class="btn btn-primary" type="button" value="确认" id="TGConfirmBtn" flag = '0'/>
								<input class="btn btn-primary" type="button" value="归档" id="TGArchiveBtn"/>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</form:form>
	</div>
		<div class="r" style="width:41%;border-left:none">
			<form:form id="disCardForm" action="${ctx }/channel/goldcredit/ceiling/init"
				modelAttribute="params" method="post">
				<input type="hidden" id="userCode">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" /> 
			    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
				<table class = "table1" cellpadding="0" cellspacing="0" border="0"
					width="100%">
					<tr>
						<td><label class="lab" style="width:63px">推介日期：</label> <input type="text"
							class="Wdate input_text178"  name="referralsDate" id = "referralsDate" style="width:120px"
							value="${params.referralsDate}"
							onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" /></td>
						<td><label class="lab" style="width:63px">客户姓名：</label> <form:input type="text"
								class="input_text178"  path="customerName" id = "customerName" style="width:120px"></form:input></td>
					</tr>
					<tr>
					<td><label class="lab" style="width:63px">门店：</label> <input type="text"
							class="input_text178" name="storesName" id="storesName" style="width:120px"
							readonly="readonly" value="${params.storesName }" /> <i
							id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
							<input type="hidden" name="storeOrgId"
							value="${params.storeOrgId}" id="storeOrgId" /></td>
						<td><label class="lab" style="width:63px">借款状态：</label> <form:select
								class="select180" path="loanStatus" id  = "loanStatus" style="width:120px">
								<form:option value="">全部</form:option>
								<c:forEach items="${fns:getDictList('jk_loan_apply_status')}"
									var="loan_type">
									<form:option value="${loan_type.value}">${loan_type.label}</form:option>
								</c:forEach>
							</form:select></td>
						</tr>
						<tr>
						<td><label class="lab" style="width:63px">数据状态：</label> <form:select
								class="select180" path="dataStatus" id = "dataStatus" style="width:120px">
								<form:option value="">全部</form:option>
								<%-- <c:forEach items="${fns:getDictList('jk_loan_type')}"
									var="loan_type">
									<form:option value="${loan_type.value}">${loan_type.label}</form:option>
								</c:forEach>--%>
								<form:option value="0">在用</form:option>
								<form:option value="1">历史</form:option>
							</form:select></td>
						
						<td><label class="lab" style="width:63px">渠道：</label> <form:select
								class="select180" path="logo" id  = "logo" style="width:120px">
								<form:option value="">全部</form:option>
								<c:forEach items="${fns:getDictList('jk_channel_flag')}"
									var="card_type">
									<c:if test="${card_type.value!='4'&&card_type.value!='3' }">
										<form:option value="${card_type.value}">${card_type.label}</form:option>
									</c:if>
								</c:forEach>
							</form:select></td>
					</tr>
					<tr>
					<td><label class="lab" style="width:63px">模式：</label> <form:select
								class="select180" path="model" id = "model" style="width:120px">
								<form:option value="">全部</form:option>
								<form:option value="0">空</form:option>
								<form:option value="1">TG</form:option>
							</form:select></td>
					<td><label class="lab" style="width:63px">放款日期：</label> <input name=grantDate
							id="grantDate"
							value="<fmt:formatDate value='${params.grantDate}' pattern='yyyy-MM-dd'/>"
							type="text" class="Wdate input_text70" style="width:97px"size="10"
							onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'gtantEndDate\')}'})" style="cursor: pointer" />-<input name="gtantEndDate" id="gtantEndDate"
							value="<fmt:formatDate value='${params.gtantEndDate}' pattern='yyyy-MM-dd'/>"
							type="text" class="Wdate input_text70" style="width:97px" size="10"
							onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'grantDate\')}'})" style="cursor: pointer" />
						</td>
						
					</tr>
				</table>
				<div class="tright pr30 pb5">
					<input class="btn btn-primary" type="submit" value="搜索"
						id="searchBtn"></input>
					<button class="btn btn-primary" id="clearBtn">清除</button>
					
				</div>
			</form:form>
		</div>
	</div>
		<p class="mb5">
			<%-- <button class="btn btn-small" id="dao">导出Excel</button> --%>
			<%-- <button class="btn btn-small" id="ceiling">设置金信额度上限</button>
			<button class="btn btn-small" id="clear">归档</button> --%>

			&nbsp;&nbsp;<label class="lab1"><span class="red">总笔数：</span></label><label id = "acount"
			class="red"> ${totalDeducts.account}</label><label class="red">笔</label> <input
			type="hidden" id="hiddenAccount" value="0" /><input type="hidden" id="hiddenTotalAccount" value="${totalDeducts.account}">
			&nbsp;&nbsp;<label class="lab1"><span class="red">批借总金额：</span></label><label id = "amount"
			class="red"> <fmt:formatNumber value='${totalDeducts.ceilingsum}' pattern="#,##0.00" /></label><label class="red">元</label> <input
			type="hidden" id="hiddenCeilingsum" value="0.00" /><input type="hidden" id="hiddenTotalCeilingsum" value="${totalDeducts.ceilingsum}">
			&nbsp;&nbsp;<label class="lab1"><span class="red">合同总金额：</span></label><label id = "contract"
			class="red"> <fmt:formatNumber value='${totalDeducts.contractamount}' pattern="#,##0.00" /></label><label class="red">元</label> <input
			type="hidden" id="hiddenContract" value="0.00" /><input type="hidden" id="hiddenTotalContract" value="${totalDeducts.contractamount}">
			&nbsp;&nbsp;<label class="lab1"><span class="red">实放总金额：</span></label><label id = "grant"
			class="red"> <fmt:formatNumber value='${totalDeducts.grantamount}' pattern="#,##0.00" /></label><label class="red">元</label> <input
			type="hidden" id="hiddenGrant" value="0.00" /><input type="hidden" id="hiddenTotalGrant" value="${totalDeducts.grantamount}">
		</p>
		<input type="hidden" id="hiddenGrant" value="0.00">
		 <p><label>全选</label>
			<input type = "checkbox" id="checkAll"/>
		 </p>
		 <div>
	   	 	<sys:columnCtrl pageToken="gCeillingList"></sys:columnCtrl>
	   	 </div>
		<div class="box5" style="position:relative;width:100%;overflow:hidden;height:60%;">
		<table id="tableList"
			class="table table-bordered table-condensed table-hover">
			<thead>
				<tr >
					<th></th>
					<th>门店名称</th>
					<th>推介日期</th>
					<th>客户姓名</th>
					<th>身份证号</th>
					<th>开户行</th>
					<th>银行账号</th>
					<th>批借金额</th>
					<th>合同金额</th>
					<th>实放金额</th>
					<th>外访费</th>
					<th>加急费</th>
					<th>期限</th>
					<th>借款状态</th>
					<th>放款日期</th>
					<th>数据状态</th>
					<th>渠道</th>
					<th>模式</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty ceiling && fn:length(ceiling.list)>0}">
					<c:forEach items="${ceiling.list}" var="item" varStatus="status">
						<tr>
							<td><input type="checkbox" name="checkBoxItem" 
								auditAmount='${item.loanAuditAmount}' value='${item.applyId}' loanCode = "${item.loanCode}"
								contractAmount = '${item.contractAmount}' grantAmount = '${item.grantAmount}'/> ${status.count}</td>
							<td>${item.storesName}</td>
							<td>${item.referralsDate}</td>
							<td>${item.customerName}</td>
							<td>${item.customerNum}</td>
							<td>${item.bankName}</td>
							<td>${item.bankAccount}</td>
							<td><fmt:formatNumber value='${item.loanAuditAmount}' pattern="#,##0.00" /></td>
							<td><fmt:formatNumber value='${item.contractAmount}' pattern="#,##0.00" /></td>
							<td><fmt:formatNumber value='${item.grantAmount}' pattern="#,##0.00" /></td>
							<td><fmt:formatNumber value='${item.feePetition}' pattern="#,##0.00" /></td>
							<td><fmt:formatNumber value='${item.feeExpedited}' pattern="#,##0.00" /></td>
							<td>${item.loanMonths}</td>
							<td>${item.loanStatus }</td>
							<td><fmt:formatDate value="${item.grantDate}" pattern='yyyy-MM-dd' /></td>
							<td>${item.dataStatus}</td>
							<td>
								<c:if test="${item.model ne '1'}">${item.logo}</c:if>
							</td>
							<td><c:if test = "${item.model eq '1'}">TG</c:if></td>
							<td style="position:relative">
								<input type="button" class="btn btn_edit edit" value="操作"/>
								<div class="alertset" style="display:none">
									<%-- <button class="btn_edit" name="dealBtn"
										value='${item.loanCode}' onclick="checkLoanApply('${item.loanCode}')">查看</button> --%>
									<button class="btn_edit" name="history"
										value='${item.applyId}' onclick="showAllHisByLoanCode('${item.loanCode}')">历史</button>
								</div>
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty ceiling || fn:length(ceiling.list)==0}">
					<tr>
						<td colspan="19" style="text-align: center;">没有待办数据</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>
	</div>
	<div class="pagination">${ceiling}</div>
	<script type="text/javascript">

	  $("#tableList").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
		$('#tableList').bootstrapTable({
			method: 'get',
			cache: false,
			height: $(window).height()-390,
			
			pageSize: 20,
			pageNumber:1
		});
		$(window).resize(function () {
			$('#tableList').bootstrapTable('resetView');
		});
	</script>
</body>
</html>