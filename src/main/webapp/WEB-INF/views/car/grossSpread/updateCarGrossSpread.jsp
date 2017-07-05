<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>车借总费率修改列表</title>
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
		
	$("#updateCarGrossSpread_btn").click(function(){
		var v_rateId = $("#rateId").val();
		var v_dictProductType = $("#dictProductType").val();
		var v_dictDeadline = $("#monthId").val();
		var v_grossRate = $("#grossRate").val();
		var v_dictInitiate = $("#dictInitiate").val();
		
		$.ajax({
			type:"POST",
			url:ctx +"/car/carGrossSpread/carGrossSpread/checkCarGrossSpread",
		    data:{"dictProductType":v_dictProductType,"dictDeadline":v_dictDeadline,"grossRate":v_grossRate,"rateId":v_rateId},
		 	success:function(data){
		 		if(data == 0){
		 			$.ajax({
		 				type:"POST",
		 				url:ctx +"/car/carGrossSpread/carGrossSpread/updateCarGrossSpread",
		 				data:{"rateId":v_rateId,"dictProductType":v_dictProductType,"dictDeadline":v_dictDeadline,"grossRate":v_grossRate,"dictInitiate":v_dictInitiate},
		 				success:function(){
		 					window.location.href = ctx +"/car/carGrossSpread/carGrossSpread/findCarGrossSpreadList";
		 				}
		 			});
		 		}else{
		 			 art.dialog.alert("该费率已存在,请检查。。!");
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
					$("#monthId").append("<option value=''>请选择</option>");
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
	<div class="control-group " style="padding-bottom:10px">
		<form  id="inputForm" >
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />		
			<input type="hidden" name="rateId" id="rateId" value="${carGrossSpread.rateId}">			
        	<table cellpadding="0" cellspacing="0" border="0" width="100%">       	
            	<tr>
					<td align="right">
						<label class="lab">产品类型：</label>
					</td>
					<td>	
						<select name="dictProductType" id="dictProductType" class="select180" onchange="loadproductMonths()">
							 <c:forEach items="${products }" var="pro">
							    <option value="${pro.productCode}"   <c:if test="${carGrossSpread.dictProductType == pro.productCode  }">selected</c:if>  >${pro.productName }</option>
							 </c:forEach>
						</select>
					</td>
                	<td align="right">
                		<label class="lab">期限：</label>
                	</td>
                	<td align="left">	
                	<select id="monthId"  name="dictDeadline" class="select180">
                		     <option value="">请选择</option>
                			 <c:forEach items="${productMonths }" var="month">
							    <option value="${month}"  <c:if test="${carGrossSpread.dictDeadline == month  }">selected</c:if> >${month }</option>
							 </c:forEach>
						</select>
                	</td>
				</tr>
				<tr>
                	<td align="right">
                		<label class="lab">总费率：</label>
                	</td>
                	<td>	
                		<input type="text" name="grossRate" id="grossRate" value="${carGrossSpread.grossRate }" class="input_text178" >%
                	</td>
                	<td align="right">
                		<label class="lab">状态：</label>
                	</td>
                	<td align="left">	
                		<select name="dictInitiate" id="dictInitiate" class="select180">
                			<c:forEach items="${fns:getDictList('com_use_flag')}" var="item">
								<option value="${item.value}" <c:if test="${carGrossSpread.dictInitiate==item.value}">  selected = true  </c:if>>
									${item.label}
								</option>
							</c:forEach>
						</select>
                	</td>
                </tr>
        	</table>
        	</div>    
        	<div class="tright pr30 pt10" style="text-align:right">   
        		<input type="button" class="btn btn-primary" id="updateCarGrossSpread_btn" value="提交"/>
                <input type="button" class="btn btn-primary" id="goBack_btn" value="取消"/>
        	</div>
        </form>
	   
<div class="pagination">
	
</div>
</body>
</html>