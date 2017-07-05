<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>车借总费率分配城市列表</title>
<meta name="decorator" content="default"/>
<script type="text/javascript" language="javascript" src='${context}/js/common.js'></script>
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
		var v_dictDeadline = $("#dictDeadline").val();
		var v_grossRate = $("#grossRate").val();
		var v_dictInitiate = $("#dictInitiate").val();
		$.ajax({
			type:"POST",
			url:ctx +"/car/carGrossSpread/carGrossSpread/updateCarGrossSpread",
			data:{"rateId":v_rateId,"dictProductType":v_dictProductType,"dictDeadline":v_dictDeadline,"grossRate":v_grossRate,"dictInitiate":v_dictInitiate},
			success:function(){
				window.location.href = ctx +"/car/carGrossSpread/carGrossSpread/findCarGrossSpreadList";
			}
		});
	});	
		
	});
</script>
<script type="text/javascript">
	function openWindow(rateId){
		var url=  ctx +"/car/carGrossSpread/carGrossSpread/toGrossSpreadAddCitys?rateId=" + rateId;
	    art.dialog.open(url, {  
		   id: 'addCitys',
		   title: '添加城市!',
		   lock:true,
		   width:700,
		   height:350,
		   close: function() {  
				 window.location.href = ctx + "/car/carGrossSpread/carGrossSpread/showSpreadProvinceCity?rateId=" +$("#rateId").val();;
           }
		},false);  
	}
	
	
	function batchDelete(){
			var citys=getCheckedCheckBoxValue("city");
			if(citys == ''){
				 art.dialog.alert("请至少选择一个城市!");
				 return ;
			}
			
			  art.dialog.confirm('是否确定删除操作', function () {
				  $.ajax({
				   		type : "POST",
				   		url : ctx + "/car/carGrossSpread/carGrossSpread/batchDeleteCitys",
				   		data: {linkIds: citys },	
				   		success : function(data) {
				   			 window.location.href = ctx + "/car/carGrossSpread/carGrossSpread/showSpreadProvinceCity?rateId=" +$("#rateId").val();;
				   		}
				   	});
			  });
	}
</script>
</head>
<body>	
	<div class="control-group pb10">
		<form  id="traForm" >
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />		
			<input type="hidden" name="rateId" id="rateId" value="${carGrossSpread.rateId}">			
        	<table cellpadding="0" cellspacing="0" border="0" width="100%">       	
            	<tr>
					<td align="right" width="25%">
						<label class="lab">产品类型：</label>
					</td>
					<td width="25%">	
						 <c:forEach items="${products }" var="pro">
						    <c:if test="${carGrossSpread.dictProductType == pro.productCode  }"> ${pro.productName }</c:if> 
					</c:forEach>
					</td>
                	<td align="right" width="25%">	
                		<label class="lab">期限：</label>
                	</td>
                	<td align="left">	
                		${carGrossSpread.dictDeadline}
                	</td>
				</tr>
				<tr>
                	<td align="right">
                		<label class="lab">总费率：</label>
                	</td>
                	<td>	 ${carGrossSpread.grossRate } %
                	</td>
                	<td align="right">
                		<label class="lab">状态：</label>
                	</td>
                	<td align="left">	
                	 ${carGrossSpread.dictInitiate}
                	</td>
                </tr>
        	</table>    
        </form>
	</div>   
 
<h4 class="f14 pl10">城市信息</h4>	
   <div class="mb5">
		<input type="checkbox" onclick="checkedAll('city',this)">全选
		<input type="button" class="btn btn-small" value="添加" onclick="openWindow('${carGrossSpread.rateId}')">
		<input type="button"class="btn btn-small" value="批量删除" onclick="batchDelete()">
	</div> 
             <table class="table table-hover table-bordered table-condensed" style="margin-bottom:200px">
              <tr>
             <c:forEach items="${ctiys }" var="city" varStatus="index"  >
             	<td align="center"> <input type="checkbox" name="city" value="${ city.linkId}"></td>
             	<td align="left">${ city.provinceCityId}</td>
           		<c:if test="${ (index.index+1) % 5 == 0}"></tr><tr></c:if>
             </c:forEach>
             </tr>
          </table>
                 <div class="tright pr10 pt5 pb5" style="margin-top: 10px;margin-right: 10px">
                   <input type="button" class="btn btn-primary" id="backToGrossSpreadList" value="返回"
			onclick="JavaScript:window.location='${ctx}/car/carGrossSpread/carGrossSpread/findCarGrossSpreadList?tabPageId=jerichotabiframe_4'"></input>
                       </div>
</body>
</html>