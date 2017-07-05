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
		
	});
</script>
</head>
<body>	
	<div class="control-group pb10">
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
        	</div>    
  			 <br>
             <table class="table table-hover table-bordered table-condensed" >
             <tr >  <td>城市信息</td> </tr>
              <tr>
             <c:forEach items="${ctiys }" var="city" varStatus="index"  >
             	<td align="center">${ city.provinceCityId}</td>
           		<c:if test="${ (index.index+1) % 5 == 0}"></tr><tr></c:if>
             </c:forEach>
             </tr>
          </table>
          <div class="tright pr30 mt10" >   
                <input type="button" class="btn btn-primary" id="goBack_btn" value="返回"/>
          </div>

</body>
</html>