<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>门店划扣统计列表</title>
<script type="text/javascript" src="${context}/js/payback/deductOrgLimit.js"></script>
<script type="text/javascript" src="${context}/js/payback/ajaxfileupload.js"></script>
<script src="${context}/js/payback/historicalRecords.js" type="text/javascript"></script>
<script src="../../../../static/ckfinder/plugins/gallery/colorbox/jquery.colorbox-min.js"></script>
<link media="screen" rel="stylesheet" href="../../../../static/ckfinder/plugins/gallery/colorbox/colorbox.css" />
<script type="text/javascript">
function page(n,s) {
	if (n) $("#pageNo").val(n);
	if (s) $("#pageSize").val(s);
	$("#fengyeId").attr("action", "${ctx}/borrow/payback/deductPlantLimit/queryOrgStatisticsPage");
	$("#fengyeId").submit();

	return false;
}

</script>

<style>
.lab {
    width: 180px;
    text-align: left;
    display: inline-block;
    height: 26px;
    line-height: 30px;
    
   
}
hr{
margin-top: 10px;
margin-bottom: 10px;
border: 0;
border-top: 1px solid #000;
}

</style>

<title>
</title>
</head>
<body>
<div>
<div>
	       <form action="${ctx}/borrow/payback/deductPlantLimit/queryOrgStatisticsPage">
	        <div style ="margin: 10px 0 10px;">
		       <label class='lab'>适用门店：</label>
		             <input id="queryTxtStore" name="orgName" 
						type="text" maxlength="20" class="txt date input_text178"
						value="${record.orgName}" style="width: 140px;"/> 
						<i id="querySelectStoresBtn" class="icon-search" style="cursor: pointer"></i>
						<input type="hidden" id="queryHdStore" name="orgId" value="${record.orgId}">
					   <button class="btn btn-primary" style="float: right;" onclick="document.forms[0].submit();">搜索</button>
		   </div>
		   </form>
	       <div class="box5"> 
		        <table  class="table  table-bordered table-condensed" >
		        <thead>
		            <tr>
		               	<th>序号</th>
		                <th>门店名称</th>
		                <th>余额不足比例(%)</th>
		                <th>余额不足条数</th>
		                <th>失败比例(%)</th>
		                <th>失败条数</th>
		                <th>划扣条数</th>
		            </tr>
		         </thead>   
		        <c:if test="${page.list!=null && fn:length(page.list)>0}">
		         <c:forEach items="${page.list}" var="bankPlantPort" varStatus="status">
		            <tr>
		                 </td>
		                 <td>${(page.pageNo-1)*page.pageSize+status.count}</td>
		                 <td>${bankPlantPort.orgName}</td>
		                 <td>
		                    ${bankPlantPort.notEnoughProportion}
		                 </td>
		                 <td>
		                    ${bankPlantPort.notEnoughNumber}
		                 </td>
		                  <td>
		                    ${bankPlantPort.failureRate}
		                 </td>
		                 <td>
		                    ${bankPlantPort.failureNumber}
		                 </td>
		                  <td>
		                    ${bankPlantPort.deductNumber}
		                 </td>
			            </tr>
						</c:forEach>
					</c:if>
			        <c:if test="${ page.list==null || fn:length(page.list)==0}">
			           <tr><td colspan="18" style="text-align:center;">没有待办数据</td></tr>
			        </c:if>
			      </table>
			       </div>
			     <div class="pagination">${page}</div>
			     </div>
      </div>

</div>
</div>
</body>
</html>