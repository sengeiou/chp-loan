<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>评估师录入已办列表</title>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context}/js/enter_select.js"></script>
<script type="text/javascript">
  $(document).ready(function(){
		
	 $('#searchBtn').bind('click',function(){
		 page(1, ${page.pageSize});
	 });
	 $('#clearBtn').bind('click',function(){
		 $(':input','#searchTable')
		  .not(':button, :reset')
		  .val('')
		  .removeAttr('checked')
		  .removeAttr('selected');
		 $("#hdStore").val('');
		 $("select").trigger("change");
	 });
 });
  function page(n,s){
      if(n) $("#pageNo").val(n);
      if(s) $("#pageSize").val(s);
      $("#inputForm").attr("action", "${ctx}/common/carHistory/appraiserEntryDoneList");
      $("#inputForm").submit();
      return false;
  }
</script>
</head>
<body style="overflow-y: hidden">
	<div class="control-group">
		<form:form id="inputForm" modelAttribute="carLoanStatusHisEx"
			method="post" class="form-horizontal">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<table id = "searchTable" class="table1 " cellpadding="0" cellspacing="0" border="0"
				width="100%">
				<tr>
	                <td><label class="lab">客户姓名：</label>
	                <form:input id="loanCustomerName" path="loanCustomerName" class="input_text178" /></td>
	                <td><label class="lab">客户经理：</label>
	                <form:input id="costumerManagerName" path="costumerManagerName" class="input_text178" /></td>
	                <td><label class="lab">咨询状态：</label>
	                <form:select id="dictOperStatus" path="dictOperStatus" class="select180">
	                <option value="">请选择</option>
	                	<c:forEach items="${fns:getDictLists('0,1,2,3,5,','jk_next_step')}" var="item">
			      				<form:option value="${item.value}">${item.label}</form:option>
			  			</c:forEach>
					</form:select></td>
	            </tr>
	            <tr>
	                <td><label class="lab">预计到店时间：</label>
	                	<input id="planArrivalTimeStart" name="planArrivalTimeStart"  class="input_text70 Wdate" 
	            		onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" style="cursor: pointer" 
	            		value="<fmt:formatDate value='${carLoanStatusHisEx.planArrivalTimeStart}' type='date' pattern="yyyy-MM-dd" />" />-
	            		<input id="planArrivalTimeend" name="planArrivalTimeend"  class="input_text70 Wdate" 
	            		onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" style="cursor: pointer" 
	            		value="<fmt:formatDate value='${carLoanStatusHisEx.planArrivalTimeend}' type='date' pattern="yyyy-MM-dd" />" />
	                </td>
					 <td><label class="lab">是否电销：</label>
					 	<form:radiobuttons path="telesalesFlag" items="${fns:getDictList('jk_telemarketing')}" itemLabel="label" itemValue="value" htmlEscape="false"/>			
			         </td>
	            </tr>
			</table>
			<div class="tright pr30 pb5">
				<input type="button" class="btn btn-primary" id="searchBtn"
					value="搜索"></input> <input type="button" class="btn btn-primary"
					id="clearBtn" value="清除"></input>
			</div>
	</form:form>
	</div>
	<sys:columnCtrl pageToken="carContractProDoneList"></sys:columnCtrl>
	<div class="box5" style="position:relative;width:100%;overflow:hidden;height:60%;margin-bottom: 20px">
	 
		<table id="tableList" class="table table-bordered table-condensed table-hover"
			style="margin-bottom: 200px;">
			<thead>
				<tr>
					<th>序号</th>
					<th>客户姓名</th>
					<th>门店名称</th>
					<th>预计到店时间</th>
					<th>车辆型号</th>
					<th>客户经理</th>
					<th>团队经理</th>
					<th>咨询状态</th>
					<th>是否电销</th>
					<th>是否展期</th>
					<th>展期次数</th>
					<th>操作</th>
				</tr>
			</thead>
			<c:if test="${ page.list != null && fn:length(page.list)>0}">
				<c:forEach items="${page.list}" var="item" varStatus="status">
					<tr>
						<td>${status.count}</td>
						<td>${item.loanCustomerName}</td>
						<td>${item.storeName}</td>
						<td><fmt:formatDate value="${item.planArrivalTime}" pattern="yyyy-MM-dd" /></td>
						<td>${item.vehicleBrandModel}</td>
						<td>${item.costumerManagerName}</td>
						<td>${item.teamManagerName}</td>
						<td>${item.dictOperStatus}</td>
						<td>${item.telesalesFlag}</td>
						<td>${item.isExtendsion}</td>
						<td>${item.extendNum}</td>
						<td><input class="btn_edit jkcj_lendCarApply_check" type="button" onclick="appraiserEntryDone('${item.loanCode}')"  value="查看" /></td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${ page.list==null || fn:length(page.list)==0}">
				<tr>
					<td colspan="12" style="text-align: center;">没有已办数据</td>
				</tr>
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