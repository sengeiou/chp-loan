<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>车借总费率增加列表</title>
<meta name="decorator" content="default"/>
<script type="text/javascript" language="javascript" src='${context}/js/common.js'></script>
<script type="text/javascript">
	function getCitys(){
	 $.ajax({
   		type : "POST",
   		url : ctx + "/common/cityinfo/asynLoadCity",
   		data: {provinceId: document.getElementById('dictInitiate').value},	
   		success : function(data) {
   			var resultObj = eval("("+data+")");
	   		 var citysHtml= "<table  width='100%' ><tr>";
	   		  $.each(resultObj,function(i,item){
		   			citysHtml += "<td width='30%'>  <input name='city' type='checkbox' value="+item.areaCode+">  "+item.areaName+"</td> ";
		   			if( (i+1)%3==0){
		   				citysHtml += "</tr><tr>";
		   			}
	          });
   			document.getElementById("citys").innerHTML=citysHtml + "</tr></table>";
   		}
   	});
	}
	
	
	function addCitys() {
		var citys=getCheckedCheckBoxValue("city");
		if(citys == ''){
			 art.dialog.alert("请至少选择一个城市!");
			 return ;
		}
		 $.ajax({
		   		type : "POST",
		   		url : ctx + "/car/carGrossSpread/carGrossSpread/grossSpreadAddCitys",
		   		data: {citys: citys , rateId:document.getElementById("rateId").value},	
		   		success : function(data) {
		   		 	art.dialog.alert("操作成功！成功添加" + data.count + "个城市，有" + data.recount + "个重复数据，无需添加！");
		   		}
		   	});
	}
</script>
</head>
<body>	
	<div class="control-group pb10">
		<form  id="traForm" >
        	<table cellpadding="0" cellspacing="0" border="0" width="100%">       	
            	<tr>
					<td align="left">
						<label class="lab">选择省份：</label>
                		<select name="dictInitiate" id="dictInitiate" class="select180" onchange="getCitys()">
							 <c:forEach items="${provinces }" var="province" >
								<option value="${province.areaCode}"> ${province.areaName}</option>
							</c:forEach>
						</select>
                	</td>
                </tr>
                  <tr>
                <td><input id='rateId' type="hidden" value="${carGrossSpread.rateId}"><br></td>
                </tr>
                <tr>
                <td><div id='citys'></div></td>
                </tr>
        	</table>    
        	
        </form>
	</div>  
	<div class="tright pr30 pb5" >   
                <input type="button" class="btn btn-primary" id="insertGrossSpread_btn" onclick="addCitys()" value="提交"/>
        	</div> 
	<script type="text/javascript">getCitys();</script>
</body>

</html>