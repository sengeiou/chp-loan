<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>门店划扣申请限制</title>
<script type="text/javascript" src="${context}/js/payback/deductOrgLimit.js"></script>
<script type="text/javascript" src="${context}/js/payback/ajaxfileupload.js"></script>
<script src="${context}/js/payback/historicalRecords.js" type="text/javascript"></script>
<script src="../../../../static/ckfinder/plugins/gallery/colorbox/jquery.colorbox-min.js"></script>
<link media="screen" rel="stylesheet" href="../../../../static/ckfinder/plugins/gallery/colorbox/colorbox.css" />
<script type="text/javascript">
function page(n,s) {
	if (n) $("#pageNo").val(n);
	if (s) $("#pageSize").val(s);
	$("#fengyeId").attr("action", "${ctx}/borrow/payback/deductPlantLimit/queryOrgPage");
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
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div>
    <form id="fengyeId">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" /> 
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
    </form>
	<form id="addOrEdit"  action="${ctx}/borrow/payback/deductPlantLimit/saveOrg" method="post" class="form-horizontal">
		  <div id="onLineTable"  style="margin: 10px 50px;">
					
					<label class='lab' style ="font-size: 16px; width: 200px;">门店还款申请提交比例设置</label>
					<input type ="button" id = "submitButton"   style="float: right;font-size: 16px;" value ="保存"/>
					<hr></hr>
            	<div>
					<label class='lab'>余额不足比例：</label>
		            <input    name="notEnoughProportion"  id="notEnoughProportionId"    type = "number"  > %
			   </div>
			   <div>
			        <label class='lab'>失败比例：</label>
		            <input   name="failureRate"   id ="failureRateId"           type ="number"    > %
		        </div>
		       <div>
		            <label class='lab'>基数：</label>
		            <input   name="baseNumber"   id ="baseNumberId"           type ="number"   >                        
			   </div>
			   <div>
		       <label class='lab'>适用门店：</label>
		             <input id="txtStore" name="search_storesName" 
						type="text" maxlength="20" class="txt date input_text178"
						 style="width: 140px;"/> <i id="selectStoresBtn"
						class="icon-search" style="cursor: pointer"></i>
						<input type="hidden" id="hdStore" name="orgId">
			   </div>
			 </form>
	        <sys:message content="${message}"/>
	       <label class='lab' style ="font-size: 16px; width: 250px;margin-top: 20px;">门店还款申请提交比例设置展示</label>
	       <hr></hr>
	       <form action="${ctx}/borrow/payback/deductPlantLimit/queryOrgPage">
	        <div style ="margin: 10px 0 10px;">
		       <label class='lab'>适用门店：</label>
		             <input id="queryTxtStore" name="orgName" 
						type="text" maxlength="20" class="txt date input_text178"
						value="${record.orgName}" style="width: 140px;"/> 
						<i id="querySelectStoresBtn" class="icon-search" style="cursor: pointer"></i>
						<input type="hidden" id="queryHdStore" name="orgId" value="${record.orgId}">
					   <button class="btn btn-primary" style="float: right;" onclick="document.forms[1].submit();">搜索</button>
		   </div>
		   
		   </form>
	       <div class="box5"> 
		        <table  class="table  table-bordered table-condensed" >
		        <thead>
		            <tr>
		               	<th>序号</th>
		                <th>门店名称</th>
		                <th>余额不足比例(%)</th>
		                <th>失败比例(%)</th>
		                <th>基数</th>
		                <th>操作</th>
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
		                    ${bankPlantPort.failureRate}
		                 </td>
		                  <td>
		                    ${bankPlantPort.baseNumber}
		                 </td>
	                      <td>
						   <input id="delete" type="button" url="${ctx}/borrow/payback/deductPlantLimit/deleteOrg?id=${bankPlantPort.id }" value="删除" class="cf_btn_edit" style="cursor:pointer;">
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