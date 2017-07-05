<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>车借总费率增加列表</title>
<meta name="decorator" content="default"/>
<style type="text/css">
.input-append {
    height: 30px;
    float:left;
    margin-left:3px;
    line-height:30px;
    
   
    }
    .input-small{
    	float:left;
    	width:138px;
    	border-radius:3px;
    	margin:1px 0;
    }
   
</style>
<script src="${context}/js/transate/transate.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$("#goBack_btn").click(function(){
			window.location.href = ctx + "/car/carGrossSpread/carGrossSpread/findCarGrossSpreadList";
		});
		
		$("#insertGrossSpread_btn").click(function(){
			var v_dictProductType = $("#dictProductType").val();
			var v_dictDeadline = $("#monthId").val();
			var v_grossRate = $("#grossRate").val();
			var v_dictInitiate = $("#dictInitiate").val();
			var rateType = $("#rateType").val();
			if(v_dictProductType == ''){
				 art.dialog.alert("请选择产品类型!");
				 return;
			}
			if(v_grossRate == ''){
				 art.dialog.alert("请输入产品总费率!");
				 return;
			}
			if(rateType == ''){
				art.dialog.alert("请选择费率种类!"); 
				 return;
			}
			//检查是否存在
			$.ajax({
				type:"POST",
				url:ctx +"/car/carGrossSpread/carGrossSpread/checkCarGrossSpread",
			    data:{"dictProductType":v_dictProductType,"dictDeadline":v_dictDeadline,"grossRate":v_grossRate,"rateType":rateType},
			 	success:function(data){
			 		if(data == 0){
			 			$.ajax({
							type:"POST",
							url:ctx +"/car/carGrossSpread/carGrossSpread/insertGrossSpread",
						    data:{"dictProductType":v_dictProductType,"dictDeadline":v_dictDeadline,"grossRate":v_grossRate,"dictInitiate":v_dictInitiate,"rateType":rateType},
						 	success:function(data){
								if(data == "success"){
									 art.dialog.alert("添加成功！");
									window.location.href = ctx +"/car/carGrossSpread/carGrossSpread/findCarGrossSpreadList";
								}else{
									 art.dialog.alert("添加失败！");
									window.location.href = ctx +"/car/carGrossSpread/carGrossSpread/findCarGrossSpreadList";
								}
						 	}
						});
			 		}else{
			 			 art.dialog.alert("该费率已存在,请检查!");
			 		}
			 	}
			});
		});
	});
	
	function loadproductMonths(){
		$.ajax({
			type:"POST",
			url:ctx +"/common/pro/getProproductMonthsByProductCode",
			data : {
				productCode : document.getElementById('dictProductType').value,
				productType:"products_type_car_credit"
			},
		 	success:function(data){
	                var resultObj = eval("(" + data + ")");
					$("#monthId").empty();
					//$("#monthId").append("<option value=''>请选择</option>");
					$.each(resultObj, function(i, item) {
						$("#monthId").append("<option value=" +item+ ">" +item+ "</option>");
					});
					$("#monthId").trigger("change");
					$("#monthId").attr("disabled", false);
			}
		});
	}
	
	
	var carLaunch = {};
	carLaunch.launchFlow = function() {
			$("#inputForm").validate({
			onkeyup: true, 
			rules : {
			    grossRate:{
					number:true,
		 			min:0
				} 
			},
			messages : {
				    grossRate:{
					number:"请输入大于0的数字",
					min:"请输入大于0的数字"
				}
			}
			})
	}
	$(document).ready(function(){
		carLaunch.launchFlow();
	});
</script>
</head>
<body>	
	<div class="control-group pb10">
		<form  id="inputForm" >
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />					
        	<table cellpadding="0" cellspacing="0" border="0" width="100%">       	
            	<tr>
					<td align="right">
						<label class="lab">产品类型：</label>
					</td>
					<td>	
						<select name="dictProductType" id="dictProductType" class="select180" onchange="loadproductMonths()">
						    <option value="">请选择</option>
							 <c:forEach items="${products }" var="pro">
							    <option value="${pro.productCode}"   <c:if test="${info.dictProductType == pro.productCode  }">selected</c:if>  >${pro.productName }</option>
							 </c:forEach>
						</select>
					</td>
                	<td align="right">
                		<label class="lab">期限：</label>
                	</td>
                	<td align="left">	
                		<select id="monthId"  name="dictDeadline" class="select180">
                		     <option value="">请选择</option>
						</select>
                	</td>
				</tr>
				<tr>
                	<td align="right">
                		<label class="lab">总费率：</label>
                	</td>
                	<td>	
                		<input type="text" name="grossRate" id="grossRate" class="input_text178" >%
                	</td>
                	<td align="right">
                		<label class="lab">状态：</label>
                	</td>
                	<td align="left">	
                		<select name="dictInitiate" id="dictInitiate" class="select180">
                			<c:forEach items="${fns:getDictList('com_use_flag')}" var="item">
								<option value="${item.value}">${item.label}</option>
							</c:forEach>
						</select>
                	</td>
                </tr>
                <tr>
                	<td align="right">
                		<label class="lab">费率类型：</label>
                	</td>
                	<td>	
                		<select name="rateType" id="rateType" class="select180">
                			<option value="">请选择</option>
                			<c:forEach items="${fns:getDictList('jk_car_contract_version')}" var="item">
								<option value="${item.value}">${item.label}</option>
							</c:forEach>
						</select>
                	</td>
                </tr>
        	</table>
        	</div>    
        	<div class="tright pr30 pb5" >   
                <input type="button" class="btn btn-primary" id="insertGrossSpread_btn" value="提交"/>
                <input type="button" class="btn btn-primary" id="goBack_btn" value="取消"/>
        	</div>
        </form>
	   
<div class="pagination">
	
</div>
</body>
</html>