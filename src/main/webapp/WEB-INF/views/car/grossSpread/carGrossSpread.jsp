<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>车借总费率列表</title>
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
<script type="text/javascript" src="${context}/js/enter_select.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
//	进入新增页面		
		$("#insert_btn").click(function(){
			window.location.href = ctx + "/car/carGrossSpread/carGrossSpread/goAddPage";
		});
		
//	全选/反选
		$("#checkAll").click(function(){
			$("input[name='rateId']").prop("checked",$("#checkAll").prop("checked"));
		});
		
		$("#searchForm").validate({
			onclick: true, 
			rules:{
				grossRate:{
					number:true,
					maxlength:18
				},
			},
			messages : {
				grossRate:{
					number:"请输入合法的数字",
					maxlength:$.validator.format("请输入一个长度最多是 {0} 的数字字符串")
				},
			}
		});
	});
</script>
<script type="text/javascript">

//  根据rateId进入查看页面，查看数据 
	function showCarGrossSpread(rateId){
		window.location.href = ctx + "/car/carGrossSpread/carGrossSpread/findByRateId?rateId=" + rateId;
	}
	
//  根据rateId进入修改页面，修改数据 	
	function updateCarGrossSpread(rateId){
		window.location.href = ctx + "/car/carGrossSpread/carGrossSpread/updateByRateId?rateId=" + rateId;
	}
	
//	根据rateId进入分配城市页面
	function spreadProvinceCity(rateId){
		window.location.href = ctx + "/car/carGrossSpread/carGrossSpread/showSpreadProvinceCity?rateId=" +rateId;
	}

//	启用/停用一条或者多条数据  mark:区分停用和启用
	function updateDictInitiate1(mark){
		var arr = "";
		$("input[name='rateId']:checked").each(function(){
			arr += this.value+",";
		});
		if(arr.length > 0){
			arr = arr.substring(0,arr.length-1);
		}else{
			alert("请选择要启用/停用的选项");
			return false;
		}
		$.ajax({
			type:"POST",
			url:ctx +"/car/carGrossSpread/carGrossSpread/updateDictInitiate",
			data:{"rateId":arr, "mark":mark},
		 	success:function(data){
				if(data == "success"){
					alert("修改成功！");
					window.location.href = ctx + "/car/carGrossSpread/carGrossSpread/findCarGrossSpreadList";
				 }else{
					alert("修改失败！");
				 }
			}
		});
	}
	
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
 
	function page(n, s) {
		if (n)
			$("#pageNo").val(n);
		if (s)
			$("#pageSize").val(s);
		
// 		$("#searchForm").attr("action", "${ctx}/car/carGrossSpread/carGrossSpread/findCarGrossSpreadList");
		$("#searchForm").submit();
	}
	 
</script>
</head>
<body>	
	<div class="control-group">
		<form  id="searchForm" action="${ctx}/car/carGrossSpread/carGrossSpread/findCarGrossSpreadList" >
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" /> 
			<input name="menuId" type="hidden" value="${param.menuId}" />
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />					
        	<table cellpadding="0" cellspacing="0" border="0" width="100%">       	
            	<tr>
					<td>
						<label class="lab">产品类型：</label>
						<select name="dictProductType" id="dictProductType" class="select180" onchange="loadproductMonths()">
						    <option value="">请选择</option>
							 <c:forEach items="${fncjk:getPrd('products_type_car_credit')}" var="product_type">
							    <option value="${product_type.productCode}"   <c:if test="${info.dictProductType == product_type.productCode  }">selected</c:if>  >${product_type.productName}</option>
							 </c:forEach>
						</select>
					</td>
                	<td>
                		<label class="lab">期限：</label>
                		<select id="monthId"  name="dictDeadline" class="select180">
                		     <option value="">请选择</option>
                			 <c:forEach items="${fncjk:getPrdMonthsByType('products_type_car_credit')}" var="product_type">
								<option value="${product_type.key}" <c:if test="${info.dictDeadline == product_type.key  }">selected</c:if>>${product_type.value}</option>
							</c:forEach>
						</select>
                	</td>
                	<td>
                		<label class="lab">总费率：</label>
                		<input type="text" name="grossRate" class="input_text178"  value="${info.grossRate }">
                	</td>
                	<td>	
                		<label class="lab">费率类型：</label>
                		<select name="rateType" id="rateType" class="select180">
                			<option value="">请选择</option>
                			<c:forEach items="${fns:getDictList('jk_car_contract_version')}" var="item">
								<option value="${item.value}">${item.label}</option>
							</c:forEach>
						</select>
                	</td>
                	</tr>
        	</table>       
        	 
        	<div class="tright pr30 pb5">
        		<input type="submit" class="btn btn-primary" value="搜索" >
        		<input type="button" class="btn btn-primary" value="清除" id="tRemoveBtn" />
        	</div> 
        	</div>   
        </form>
        <p class="mb5">
        <input type="checkbox" id="checkAll">全选
    	<input type="button" class="btn btn-small"id="insert_btn" value="新增">
    	<input type="button" class="btn btn-small"value="启用" onclick="updateDictInitiate1(0)">
    	<input type="button" class="btn btn-small"value="停用" onclick="updateDictInitiate1(1)">
    	</p>
        <div class="box5" style="height:500px;">
           <table class="table table-hover table-bordered table-condensed" >
             <thead>
                <tr>
                    <th>序号</th>
					<th>产品类型</th>
					<th>期限</th>
                    <th>总费率</th>
                    <th>状态</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${page.list}" var="carGrossSpread">
            	<tr>
            		<td><input type="checkbox" name="rateId" value="${carGrossSpread.rateId}"></td>
            		<td>
            		 <c:forEach items="${products }" var="pro">
						    <c:if test="${carGrossSpread.dictProductType == pro.productCode  }"> ${pro.productName }</c:if> 
					</c:forEach>
            		</td>
            		<td>${carGrossSpread.dictDeadline}
            		</td>
            		<td>${carGrossSpread.grossRate}%</td>
            		<td>
            		  ${carGrossSpread.dictInitiate} 
            		</td>
            		<td>
            			<input type="button" class="btn_edit" value="查看" onclick="showCarGrossSpread('${carGrossSpread.rateId}')" />
            			<input type="button" class="btn_edit" value="修改" onclick="updateCarGrossSpread('${carGrossSpread.rateId }')" />
            			<input type="button" class="btn_edit" value="分配城市" onclick="spreadProvinceCity('${carGrossSpread.rateId}')" />
            		</td>
            	</tr>
            </c:forEach>	
            </tbody>
          </table>
	</div>
<div class="pagination">
	${page}
</div>
</body>
</html>