<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>操作历史</title>
<meta name="decorator" content="default"/> 
<script src="${context}/js/grant/grant.js" type="text/javascript"></script>
<script language=javascript>
$(function(){
	$('#maxAmountBtn').bind('click',function(){
		window.open('http://123.124.20.50:9099/SunIAS/SunIASRequestServlet.do?UID=admin&PWD=111&AppID=AB&UserID=zhangsan&UserName=zhangsan&OrgID=1&OrgName=fenbu&info1=BUSI_SERIAL_NO:400000317152215;OBJECT_NAME:HUIJINSY1;QUERY_TIME:20150611;FILELEVEL:1,3;RIGHT:1111111', "window",
			"width=1000,height=500,status=no,scrollbars=yes"); 
	});
		
	// 退回确定
	$("#backSure1").click(function(){
		// 获得选中的退回原因，退回到合同审核，获得的为name
		var grantSureBackReason=$("#reason").attr("selected","selected").val();
		var WorkItemView = $("#param").serialize();
		var contractCode = $("#contractCode").val();
		WorkItemView+="&grantSureBackReason="+grantSureBackReason+"&contractCode="+contractCode;
		$.ajax({
			type : 'post',
			url : ctx + '/borrow/grant/grantDeal/backTo',
			data : WorkItemView,
			success : function(data) {
				if(data!=null){
					alert('退回成功!');
					window.location=ctx+'/channel/goldcredit/grant/fetchTaskItems';
				}else{
					alert('退回失败!');
					window.location=ctx+'/channel/goldcredit/grant/fetchTaskItems';
				}
			}		
		});
	});
});
</script>
</head>
<body>
	<form id="searchForm"> 
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		 <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
		 <input id="applyId" name="applyId" type="hidden" value="${info.applyId}" />
	</form>
<div class="history">
    <h3 class="pt10 pb10">历史状态</h3>
      <p class="mb5">
			<c:if test="${workItems.loanStatusName=='放款失败'}">
				<button class="btn_edit" name="seeBack" contractCode="${workItems.contractCode}">查看退回原因</button>
				</c:if> 
				<c:if test="${workItems.lendingMoney > '300000' }">
				 	<button class="btn btn-small" id="maxAmountBtn">大额审批查看</button>
				 </c:if>
				<%-- <button  class="btn_edit"  data-target="#back_div" data-toggle="modal" name="back" applyId='${workItems.applyId}' wobNum='${workItems.wobNum}' dealType='0'
						token='${workItems.token}' contractCode='${workItems.contractCode}' stepName='${workItems.stepName }'>退回</button>--%>
    </p>
    <table class="table  table-bordered table-condensed table-hover " >
    <thead>
	    <tr>
	        <th>操作时间</th>
	        <th>操作人</th>
	        <th>操作步骤</th>
	        <th>操作结果</th>
	        <th>备注</th>
	    </tr>
	  </thead>
        <tbody>
         <c:if test="${ page!=null && fn:length(page.list)>0}">
          <c:forEach items="${page.list}" var="item">
             <tr>
             <td> <fmt:formatDate value="${item.operateTime}" pattern="yyyy/MM/dd HH:mm:ss"/>   </td>
             <td>${item.operator}</td>
             <td>${item.operateStep}</td>
             <td>${item.operateResult} </td>
             <td>${item.remark} </td>
         </tr> 
         </c:forEach>            
         </c:if>
         <c:if test="${ page==null || fn:length(page.list)==0}">
           <tr><td colspan="18" style="text-align:center;">没有待办数据</td></tr>
         </c:if>
       </tbody> 
</table>
	<div class="pagination">${page}</div>
</div>
<div  id="back_div" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabe1l" aria-hidden="true" data-url="data">
		<div class="modal-dialog role="document"">
				<div class="modal-content">
				<div class="modal-header">
					   <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<label class="lab">退回原因：</label>
					 </div>
				 <div class="modal-body">
				 <select id="reason">
				 <c:forEach items="${fns:getDictList('jk_chk_back_reason')}" var="card_type">
							<option value="${card_type.value}">${card_type.label}</option>
				 </c:forEach>
				 </select>
				 </div>
				 <div class="modal-footer" style="text-align:right">
				 <button id="backSure1" class="btn btn-primary" data-dismiss="modal" aria-hidden="true" >确定</button>
				 <button class="btn btn-primary" data-dismiss="modal" aria-hidden="true">取消</button>
				 </div>
				</div>
				</div>

</div>
<script type="text/javascript">
function page(n, s) {
	if (n)
		$("#pageNo").val(n);
	if (s)
		$("#pageSize").val(s);
	
	$("#searchForm").attr("action", "${ctx}/common/history/showLoanHisByApplyId");
	$("#searchForm").submit();
	return false;
}
</script>
</body>
</html>