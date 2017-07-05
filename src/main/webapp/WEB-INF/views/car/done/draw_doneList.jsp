<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>车借已划扣列表</title>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript">
  $(document).ready(function(){
	 $('#searchBtn').bind('click',function(){
		$('#inputForm').submit(); 
	 });
	 $('#clearBtn').bind('click',function(){
		 $(':input','#inputForm')
		  .not(':button, :reset,:hidden')
		  .val('')
		  .removeAttr('checked')
		  .removeAttr('selected');  
		 $("select").trigger("change");
	 });
	 $('#showMore').bind('click',function(){
		 $("#moreContent").show(); 
		 
	 });
 });
  function page(n,s){
      if(n) $("#pageNo").val(n);
      if(s) $("#pageSize").val(s);
      $("#inputForm").attr("action","${ctx}/common/carHistory/doneList");
      $("#inputForm").submit();
      return false;
  }  
</script>
</head>
<body>
 <div class="control-group">
 <form:form id="inputForm" modelAttribute="carLoanStatusHisEx" action="${ctx}/car/grant/grantDeducts/selectDrawDone" method="post" class="form-horizontal">
     <table class="table1 " cellpadding="0" cellspacing="0" border="0"width="100%">
         <tr>
             <td><label class="lab">客户姓名：</label>
             	<form:input id="customerName" path="loanCustomerName" class="input_text178"/>
             </td>
             <td><label class="lab">合同编号：</label>
             	<form:input id="contractCode" path="contractCode" class="input_text178"/>
             </td>
             <td><label class="lab">开户行：</label>
             	<form:input id="cardBank" path="cardBank" class="input_text178"/>
             	<input type="button" class="btn_tj mr10" value="选择银行" />
             </td>
         </tr>
         <tr>
             <td><label class="lab">产品类型：</label>
             	<form:select id="productType" path="productType" class="select180">
                   <option value="">请选择</option>
					<c:forEach items="${fns:getDictList('jk_car_loan_product_type')}" var="product_type">
		                             <form:option value="${product_type.value}">${product_type.label}</form:option>
		            </c:forEach>  
		         </form:select>
             </td>
             <td>
             	<label class="lab">划扣日期：</label>
             	<input type="text" name="stratUrgeDecuteDate" class="input_text178" />至
             	<input type="text" name="endUrgeDecuteDate" class="input_text178" />
             </td>
         </tr>
		 <tr id="moreContent" style="display:none">
		 	<td>
		 		<label class="lab">划扣平台：</label>
		 		<input id="dictDealType" type="text" name="dictDealType" class="input_text178" />
		 		<input type="button"   class="btn_tj mr10" value="选择平台"/>
		 	</td>
		 	<td>
		 		<label class="lab">回盘结果：</label>
		 		<form:select id="dictDealStatus" path="dictDealStatus" class="select180">
                   <option value="">请选择</option>
					<c:forEach items="${fns:getDictList('jk_counteroffer_result')}" var="dictDealStatus">
		                             <form:option value="${dictDealStatus.value}">${dictDealStatus.label}</form:option>
		            </c:forEach>  
		         </form:select>
		 	</td>
		 	<td>
		 		<label class="lab">银行卡号：</label>
		 		<input id="bankCardNo" type="text" name="bankCardNo" class="input_text178" />
		 	</td>
		 </tr>
     </table>
     <div  class="tright pr30 pb5">
                                    <input type="button" class="btn btn-primary" id="searchBtn" value="搜索"></input>
                                    <input type="button" class="btn btn-primary" id="clearBtn" value="清除"></input>
     <div class="xiala" style="text-align:center;">
	  <img src="${context}/static/images/up.png" id="showMore"></img>
    </div>
 </div>
 </div>
</form:form> 
<div class="box5"> 
   <table class="table table-bordered table-condensed table-hover " style="margin-bottom:100px;">
   <thead>
      <tr>
        <th></th>
		<th>合同编号</th>
		<th>客户姓名</th>
		<th>开户行</th>
		<th>银行卡号	</th>
		<th>借款产品</th>
		<th>应划扣金额</th>
		<th>划扣日期</th>
		<th>回盘结果</th>
		<th>回盘原因</th>
		<th>划扣平台</th>
		<th>渠道</th>
		<th>操作	</th>
      </tr>
      </thead>
      	<c:if test="${ page.list != null && fn:length(page.list)>0}">
       	<c:forEach items="${page.list}" var="item" varStatus="status"> 
        <tr>
          <td><input type="checkbox" name="selectCheckBox" /><</td>
          <td>${item.contractCode}</td>
          <td>${item.loanCustomerName}</td>
          <td>${item.cardBank}</td>
          <td>${item.bankCardNo}</td>
          <td>${item.productType}</td>
          <td>${item.urgeDecuteMoney}</td>
          <td> <fmt:formatDate value="${item.urgeDecuteDate}" pattern="yyyy-MM-dd"/></td>
          <td>${item.dictDealStatus}</td>
          <td>${item.dictDealReason}</td>
          <td>${item.dictDealType}</td>
          <td>${item.flog}</td>
          <td>
              <input type="button" value="历史" class="btn_edit"  onclick="showCarLoanHis('${item.loanCode}')"></input>
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
</body>
</html>