<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html style="overflow-x:auto;overflow-y:auto;">
<head>
<title>车借面审初审待办列表</title>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context}/js/enter_select.js"></script>
<script type="text/javascript">
  $(document).ready(function(){
	 $('#searchBtn').bind('click',function(){
		// 申请日期验证触发事件,点击搜索进行验证
		var startDate = $("#loanApplyTimeStart").val();
		var endDate = $("#loanApplyTimeEnd").val();
		if(endDate!=""&& endDate!=null && startDate!="" &&startDate!=null){
			var arrStart = startDate.split("-");
			var arrEnd = endDate.split("-");
			var sd=new Date(arrStart[0],arrStart[1],arrStart[2]);  
			var ed=new Date(arrEnd[0],arrEnd[1],arrEnd[2]);  
			if(sd.getTime()>ed.getTime()){   
				art.dialog.alert("申请开始日期不能大于申请结束日期!",function(){
					$("#loanApplyTimeStart").val("");
					$("#loanApplyTimeEnd").val("");
				});
				return false;     
			}else{
				$('#inputForm').submit(); 
			}  
		}else{
			$('#inputForm').submit(); 
		}
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
		show(); 
		 
	 });
	 
	 //选择门店
	 loan.getstorelsit("txtStore","hdStore");
 });
 function page(n, s) {
		if (n)
			$("#pageNo").val(n);
		if (s)
			$("#pageSize").val(s);
		$("#inputForm").submit();
		return false;
} 
</script>
</head>
<body>
 <div class="control-group">
 <form:form id="inputForm" modelAttribute="carLoanFlowQueryParam" method="post" class="form-horizontal">
     <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
     <input name="menuId" type="hidden" value="${param.menuId}" />
     <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
     <table class="table1 " cellpadding="0" cellspacing="0" border="0"width="100%">
         <tr>
             <td><label class="lab">客户姓名：</label>
             	<form:input id="customerName" path="customerName" class="input_text178"/>
             </td>
            <td><label class="lab">申请日期：</label>
            	<input id="loanApplyTimeStart" name="loanApplyTimeStart"  class="input_text70 Wdate" 
            		onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" style="cursor: pointer" value="<fmt:formatDate value='${carLoanFlowQueryParam.loanApplyTimeStart}' type='date' pattern="yyyy-MM-dd" />"/>
            		-<input id="loanApplyTimeEnd" name="loanApplyTimeEnd"  class="input_text70 Wdate" 
            		onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" style="cursor: pointer" value="<fmt:formatDate value='${carLoanFlowQueryParam.loanApplyTimeEnd}' type='date' pattern="yyyy-MM-dd" />"/>
            </td>
             <td><label class="lab">产品类型：</label>
             	<form:select id="productType" path="borrowProductCode" class="select180">
                   <option value="">请选择</option>
					 <c:forEach items="${fncjk:getPrd('products_type_car_credit')}" var="product_type">
                           <form:option value="${product_type.productCode}">${product_type.productName}</form:option>
                     </c:forEach>  
		         </form:select>
             </td>
         </tr>
		<tr >
				<td><label class="lab">是否电销：</label>
				 	<form:radiobuttons path="loanIsPhone" items="${fns:getDictList('jk_telemarketing')}" itemLabel="label" itemValue="value" htmlEscape="false"/>			
		         </td>
		         <td><label class="lab">是否展期：</label>
				 	<form:radiobuttons path="extensionFlag" items="${fns:getDictList('jk_extend_loan_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>			
		         </td>
		         <td><label class="lab">合同编号：</label>
             	<form:input id="contractCode" path="contractCode" class="input_text178"/>
             	</td>
		 </tr>
		 <tr>
		 	<td><label class="lab">渠道：</label><form:select id="cardType" path="loanFlag" class="select180">
                    <option value="">请选择</option>
                    <c:forEach items="${fns:getDictList('jk_car_throuth_flag')}" var="loan_Flag">
			                   		<c:if test = "${loan_Flag.value!=1}">
			                   			<form:option value="${loan_Flag.value}">${loan_Flag.label}</form:option>
			                   		</c:if>	
			                   </c:forEach>
				</form:select>
					</td>
		 </tr>
     </table>
     <div  class="tright pr30 pb5">
            <input type="button" class="btn btn-primary" id="searchBtn" value="搜索"></input>
            <input type="button" class="btn btn-primary" id="clearBtn" value="清除"></input>
 </div>
 </div>
</form:form> 
 
	<sys:columnCtrl pageToken="carReviewMeetList"></sys:columnCtrl>
	<div class="box5" style="position:relative;width:100%;overflow:hidden;height:60%;margin-bottom: 20px">
   <table id="tableList" class="table  table-bordered table-condensed table-hover " style="margin-bottom:100px;">
   <thead>
      <tr>
        <th>序号</th>
		<th>客户姓名</th>
		<th>借款申请额度</th>
		<th>产品类型</th>
		<th>借款期限</th>
		<th>批借金额</th>
		<th>申请日期</th>
		<th>管辖省份</th>
		<th>借款状态</th>
		<th>是否电销</th>
		<th>渠道</th>
		<th>是否展期</th>
		<th>展期次数</th>
		<th>操作</th>
      </tr>
      </thead>
      	<c:if test="${ itemList!=null && fn:length(itemList)>0}">
       	<c:forEach items="${itemList}" var="item" varStatus="status"> 
<%--         <tr <c:if test="${item.backTop eq '0'}">style='color:red'</c:if>> --%>
		<tr <c:if test="${fn:contains(item.orderField,'0,') }">class='trRed'</c:if>>
          <td>${status.count}</td>
          <td>${item.customerName}</td>
          <td><fmt:formatNumber value="${item.loanApplyAmount == null ? 0 : item.loanApplyAmount }" pattern="#,##0.00" /></td>
          <td><c:choose>
          		<c:when test="${item.auditBorrowProductCode != ''}">
          			${fncjk:getPrdLabelByTypeAndCode('products_type_car_credit',item.auditBorrowProductCode)}
          		</c:when>
          		<c:otherwise>
          			${fncjk:getPrdLabelByTypeAndCode('products_type_car_credit',item.borrowProductCode)}
          		</c:otherwise>
          	</c:choose>
          </td>
          <td>${item.loanMonths}</td>
          <td><fmt:formatNumber value="${item.auditAmount }" pattern="#,##0.00" />
          </td>
          <td><fmt:formatDate value='${item.loanApplyTime}' pattern="yyyy-MM-dd"/></td>
          <td>${item.addrProvince}</td>
          <td>
          	<c:choose>
          		<c:when test="${item.extensionFlag == '是'}">
          			${item.applyStatusCode}
          		</c:when>
          		<c:otherwise>
          			${item.dictStatus}
          		</c:otherwise>
          	</c:choose>
          </td>
          <td>${item.loanIsPhone}</td>
          <td>${item.loanFlag}</td>
          <td>${item.extensionFlag}</td>
          <td>${item.extendNumber}</td>
          <td>
              <input type="button" value="办理" class="btn_edit"  onclick="window.location='${ctx}/bpm/flowController/openForm?applyId=${item.applyId}&wobNum=${item.wobNum}&dealType=0&token=${item.token}'"></input>
          </td>           
      </tr>  
      </c:forEach>       
      </c:if>
      <c:if test="${ itemList==null || fn:length(itemList)==0}">
        <tr><td colspan="12" style="text-align:center;">没有待办数据</td></tr>
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