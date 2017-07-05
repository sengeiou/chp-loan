<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>门店划扣统计数据</title>
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
</head>
<body>
	<div class="control-group" >
            <form method="post" action="${ctx}/borrow/payback/deductPlantLimit/queryOrgStatisticsPage" id="CenterapplyPayForm">
      <table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td><label class="lab">适用门店：</label>
                <input id="queryTxtStore" name="orgName" 
						type="text" maxlength="20" class="txt date input_text178"
						value="${record.orgName}" style="width: 140px;"/> 
						<i id="querySelectStoresBtn" class="icon-search" style="cursor: pointer"></i>
						<input type="hidden" id="queryHdStore" name="orgId" value="${record.orgId}">
				</td>
				<td>
				<label class="lab">统计日期：</label>
				<input name="statisticsDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
								value="<fmt:formatDate value="${record.statisticsDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
				
				</td>
            </tr>
        </table>
        </form>
        <div class="tright pr30 pb5 ">
					<button class="btn btn-primary" onclick="document.forms[0].submit();">搜索</button>
					<button class="btn btn-primary"  id="cleBtn">清除</button>
	 </div>
	 </div>
	<div class="box5">
    <table id="tableList"  class="table  table-bordered table-condensed table-hover" width="100%">
       <thead>
        <tr>
             <tr>
               	<th>序号</th>
                <th>门店名称</th>
                <th>余额不足比例(%)</th>
                <th>余额不足条数</th>
                <th>失败比例(%)</th>
                <th>失败条数</th>
                <th>划扣条数</th>
                <th>统计日期</th>
            </tr>
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
		                 <td>
		                    ${bankPlantPort.createDate}
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
<script type="text/javascript">

	  $("#tableList").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
		$('#tableList').bootstrapTable({
			method: 'get',
			cache: false,
			height: $(window).height()-260,
			
			pageSize: 20,
			pageNumber:1
		});
		$(window).resize(function () {
			$('#tableList').bootstrapTable('resetView');
		});
	</script>
</body>
</html>