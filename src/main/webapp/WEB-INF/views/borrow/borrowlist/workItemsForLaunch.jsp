<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script src='${context}/js/bootstrap.autocomplete.js' type="text/javascript"></script>

<script src="${context}/js/poscard/strock.js" type="text/javascript"></script>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript">
var msg = "${message}";
if (msg != "" && msg != null) {
	top.$.jBox.tip(msg);
};
function page(n, s) {
	if (n)
		$("#pageNo").val(n);
	if (s)
		$("#pageSize").val(s);
	$("#inputForm").attr("action", "${ctx}/borrow/borrowlist/fetchItemsForLaunch");
	$("#inputForm").submit();
	return false;
}
</script>
<title>信借发起</title>
<meta name="decorator" content="default" />
</head>
<body>
  <div>
    <div class="control-group">
    <form:form id="inputForm" modelAttribute="loanFlowQueryParam" action="${ctx}/borrow/borrowlist/fetchItemsForLaunch" method="post" class="form-horizontal">
        <input id="pageNo" name="pageNo" type="hidden"
				value="${page.pageNo}" />
			<input id="pageSize" name="pageSize" type="hidden"
				value="${page.pageSize}" />
        <table class=" table1 " cellpadding="0" cellspacing="0" border="0"width="100%">
            <tr>
                <td><label class="lab">客户姓名：</label>
                    <form:input id="customerName" path="customerName" class="input_text178"/>
                    <input type="hidden" name="flowFlag" value=""/>
                 </td>
                <td><label class="lab">团队经理：</label>
                    <form:input path="teamManagerName" id="teamManagerName" class="input_text178"/>
                    <form:hidden  path="teamManagerCode" id="teamManagerCode"/>  
                </td>
                <td><label class="lab">客户经理：</label>
                   <form:input path="customerManagerName" id="customerManagerName"  class="input_text178"/>
                   <form:hidden path="customerManagerCode" id="customerManagerCode"/>  
                </td>
            </tr>
            <tr>
               	<td><label class="lab">身份证号：</label>
               	<form:input path="identityCode" class="input_text178"/></td>
               	<td><label class="lab">是否电销：</label>
               		<select id="telesalesFlag" name="telesalesFlag" class="select180" path="customerTelesalesFlag">
               			<option value="">请选择</option>
            		<c:forEach items="${fns:getDictList('jk_telemarketing')}" var="card_type">
								<option value="${card_type.value}" <c:if test="${card_type.value eq lfqp.telesalesFlag}">selected</c:if>>${card_type.label}</option>
				 	</c:forEach>
               		</select>
					<%-- <form:radiobuttons path="telesalesFlag" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/> --%>			
		        </td>
                <td></td>
            </tr>
		 </table>
        <div  class="tright pr30 pb5">
           <input type="button" class="btn btn-primary" id="takeSingle" value="取单"></input>
           <input type="button" class="btn btn-primary" id="searchBtn" value="搜索"></input>
           <input type="button" class="btn btn-primary" id="clearBtn" value="清除"></input>
        </div>
    </div>
   </form:form> 
   <div class="box5"> 
      <form id="openLaunchForm" action="${ctx}/bpm/flowController/openLaunchForm" method="post">
        <input type="hidden" name="flowCode" id="flowCode"/>
        <input type="hidden" name="customerCode" id="customerCode"/>
        <input type="hidden" name="consultId" id="consultId"/>
        <input type="hidden" name="dealType" id="dealType"/>
      </form>
      <table id="tableList" class="table  table-bordered table-condensed table-hover " style="margin-bottom:100px;">
      <thead>
         <tr>
          <th>序号</th>
          <th>客户姓名</th>
          <th>状态</th>
          <th>团队经理</th>
          <th>客户经理</th>
          <th>是否电销</th>
          <th>咨询时间</th>
          <th>操作</th>
         </tr>
         </thead>
         <c:if test="${ itemList!=null && fn:length(itemList)>0}">
         <c:set var="index" value="0"/>
          <c:forEach items="${itemList}" var="item"> 
           <tr>
             <td><c:set var="index" value="${index+1}"/>${index}</td>
             <td>${item.customerName}</td>
             <td>
                ${item.loanStatusCodeName}
             </td>
             <td>${item.teamManagerName}</td>
             <td>${item.customerManagerName}</td>
             <td>${item.telesalesFlagName}
             </td>
             <td><fmt:formatDate value="${item.intoLoanTime}" pattern="yyyy-MM-dd"/></td>
             <td>
                 <input type="button" value="办理" class="btn_edit" name="openFormBtn" flowCode="loanflow" customerCode="${item.customerCode}" consultId="${item.consultId}" dealType="${item.loanInfoOldOrNewFlag}"></input>
             </td>           
         </tr>  
         </c:forEach>       
         </c:if>
         <c:if test="${ itemList==null || fn:length(itemList)==0}">
           <tr><td colspan="18" style="text-align:center;">没有待办数据</td></tr>
         </c:if>
      </table>
    </div>
    <div class="pagination">${page}</div>
 </div>
</body>
</html>