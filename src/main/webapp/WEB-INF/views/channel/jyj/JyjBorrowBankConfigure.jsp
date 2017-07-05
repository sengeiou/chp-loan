<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>银行卡配置和放款比例配置</title>
<script src="../../../../static/ckfinder/plugins/gallery/colorbox/jquery.colorbox-min.js"></script>
<link media="screen" rel="stylesheet" href="../../../../static/ckfinder/plugins/gallery/colorbox/colorbox.css" />


<style>
.lab {
	width: 180px;
	text-align: left;
	display: inline-block;
	height: 26px;
	line-height: 30px;
}

hr {
	margin-top: 10px;
	margin-bottom: 10px;
	border: 0;
	border-top: 1px solid #000;
}
</style>

<script>
$(function(){
	
	$("#firstLoanProportionId").blur(function(){
		if($("#firstLoanProportionId").val()){
			  var firstLoanProportion = $("#firstLoanProportionId").val();
			  if(firstLoanProportion > 100 ){
					art.dialog.alert("首次放款比例不能大于100%！");
					$("#firstLoanProportionId").val("")
					return false;
				}
				if(firstLoanProportion <= 0 ){
					art.dialog.alert("首次放款比例不能小于等于0！");
					$("#firstLoanProportionId").val("");
					return false;
				}
			  var endLoanProportion = (10000 - firstLoanProportion*100)/100;
			  $("#endLoanProportionId").val(endLoanProportion);
		  }
	})
	
	$("#endLoanProportionId").blur(function(){
		  if($("#endLoanProportionId").val()){
			  var endLoanProportion = $("#endLoanProportionId").val();
			  if(endLoanProportion >= 100 ){
					art.dialog.alert("尾款放款比例不能大于100%！");
					$("#endLoanProportionId").val("")
					return false;
				}
				if(endLoanProportion < 0 ){
					art.dialog.alert("尾款放款比例不能小于等于0！");
					$("#endLoanProportionId").val("");
					return false;
				}
			  var firstLoanProportion = (10000- endLoanProportion*100)/100;
			  $("#firstLoanProportionId").val(firstLoanProportion);
		  }
	})
	
	
	$("#saveConfigure").click(function(){
		
		var productCode = $("select[name='productCode']").val();
		if(productCode == ''){
			art.dialog.alert("请选择产品");
			  return false;
		}
		
		var bankId = "";
		$('input[name="bankCode"]:checked').each(function(){
			bankId+=$(this).val()+",";
		});
		 if(!bankId){
			  art.dialog.alert("请开户选择银行！");
			  return false;
		} 
		var firstLoanProportion  = $("#firstLoanProportionId").val();
		if(!firstLoanProportion){
			  art.dialog.alert("首次放款比例不能为空！");
			  return false;
			
		}
		if(firstLoanProportion > 100 ){
			art.dialog.alert("首次放款比例不能大于100%！");
			return false;
		}
		if(firstLoanProportion <= 0 ){
			art.dialog.alert("首次放款比例不能小于等于0！");
			return false;
		}
		
		var firstLoanProportionIds  = firstLoanProportion.split(".");
		if(firstLoanProportionIds[1]){
			if(firstLoanProportionIds[1].length>2){
				art.dialog.alert("首次放款比例只能输入俩位小数！");
				return false;
			}
		}
		var endLoanProportion  = $("#endLoanProportionId").val();
		if(!endLoanProportion){
			  art.dialog.alert("尾款放款比例不能为空！");
			  return false;
		}
		if(endLoanProportion >= 100 ){
			art.dialog.alert("尾款放款比例不能大于100%！");
			return false;
		}
		if(endLoanProportion < 0 ){
			art.dialog.alert("尾款放款比例不能小于等于0%！");
			return false;
		}
	
		var endLoanProportionids  = endLoanProportion.split(".");
		if(endLoanProportionids[1]){
			if(endLoanProportionids[1].length>2){
				art.dialog.alert("尾款放款比例只能输入俩位小数！");
				return false;
			}
		}
		
		$.ajax({  
			    type : "POST",
				data : {
					'bankId' : bankId,
					'firstLoanProportion' : firstLoanProportion,
					'endLoanProportion' : endLoanProportion,
					'productCode' : productCode
				},
				url : ctx + "/channel/jyj/JyjBorrowBankConfigure/save",
				datatype : "json",
				async : false,
				success : function(data) {
					if (data.success == true) {
						art.dialog.alert("配置成功", function() {
							window.location.reload(true);
						});
					} else {
						art.dialog.alert(data.msg);
					}
				},
				error : function() {
					art.dialog.alert("服务器没有返回数据，可能服务器忙，请重试");
				}
			});

		});
	
		$("select[name='productCode']").change(function(){
			var productCode = $(this).val();
			location.href = ctx + "/channel/jyj/JyjBorrowBankConfigure/queryList?productCode="+productCode;
		});
	});
</script>
</head>
<body>
	<div>
		<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
		<form id="addOrEdit" method="post" class="form-horizontal">
			<div id="onLineTable" style="margin: 10px 50px;">
				<label class='lab' style="font-size: 16px;">银行卡配置</label>
				<hr />
				<div>
					<label class='lab'>产品名称</label>
					<select name="productCode" class="select180">
						<option value="" <c:if test="${record.productCode eq ''}">selected = selected</c:if>>请选择</option>
						<option value="A020" <c:if test="${record.productCode eq 'A020'}">selected = selected</c:if>>简易借</option>
						<option value="A021" <c:if test="${record.productCode eq 'A021'}">selected = selected</c:if>>农信借</option>
					</select>
				</div>
				<div>
					<label class='lab'>请选择简易借产品借款申请开户行</label>
				</div>
				<c:forEach items="${list}" var="bean" varStatus="status">
					<c:if test="${ status.index % 4 ==0}">
						<div>
					</c:if>
					<input type="checkbox" name="bankCode" id="bankId" <c:if test="${bean.flag =='1' }">checked="checked"</c:if> value=${bean.bankCode }>
					<label class='lab'>${bean.bankName }</label>
					<c:if test="${status.index != 0 && (status.index+1 % 4 ==0 || status.index == fn:length(list))}">
						</div>
					</c:if>
				</c:forEach>
			</div>
			<label class='lab' style="font-size: 16px; margin-top: 20px;">简易借-放款比例配置</label>
			<hr/>
			<div>
				<label class='lab'>请进行简易借产品放款比例配置</label>
			</div>
			<div>
				<label class='lab'>首次放款比例：</label>
				<input name="firstLoanProportion" id="firstLoanProportionId" type="number" value=${limit.firstLoanProportion }>
				%
			</div>
			<div>
				<label class='lab'>尾款放款比例：</label>
				<input name="endLoanProportion" id="endLoanProportionId" type="number" value=${limit.endLoanProportion }>
				%
			</div>
			<input type="button" id="saveConfigure" value="保存">
		</form>
	</div>
</body>
</html>