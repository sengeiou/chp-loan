<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript">
 $(document).ready(function(){
	$('#contractSummaryForm').validate({
		 onkeyup:true,
		 submitHandler : function(form) {
		   form.submit();
		 }
	}); 
 });
</script>
<meta name="decorator" content="default" />
<title>占比查看</title>
</head>
<body>
  <div class="control-group">
    <form id="contractSummaryForm" action="${ctx}/apply/contractAudit/getSummary" method="post">
       <table class=" table1" cellpadding="0" cellspacing="0" border="0"width="100%">
        
         <tr>
             <td><label class="lab"><span style="color:red">*</span>审核节点：</label>
               <select name="status" class="select180 required">
								<option value=''>请选择</option>
								<c:forEach items="${statusList}" var="item">
									<option value="${item.code}"
									  <c:if test="${item.code==status}">
									    selected=true
									  </c:if>
									>${item.name}
									</option>
								</c:forEach>
						</select>
			 </td>
            <td>
              <input type="submit" class="btn btn-primary" value="查询"/>
            </td>
         </tr>
       </table>
    </form>
  </div>
  <div class="box5">
  	<table id="tableList" class="table  table-bordered table-condensed table-hover ">
  	  <thead>
		 <tr>
	     	  <th>合同金额</th>
			  <th>占比（%）</th>
			  <th>利率（%）</th>
	     </tr>
	  </thead>
	  <tbody id="prepareListBody">
	    <c:forEach items="${summaryList}" var="item">
	       <tr>
	    	  <td>${item.contractAmountSum}</td>
			  <td>${item.percent}</td>
			  <td>${item.rate}</td>
		   </tr>
	    </c:forEach>
	  </tbody>
  	</table>
  </div>
</body>
</html>