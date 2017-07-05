<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>展期合同制作待办列表</title>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript">
  $(document).ready(function(){


		 $('#searchBtn').bind('click',function(){
				// 终审日期验证触发事件,点击搜索进行验证
				var startDate = $("#contractFactDayStart").val();
				var endDate = $("#contractFactDayEnd").val();
				if(endDate!=""&& endDate!=null && startDate!="" &&startDate!=null){
					var arrStart = startDate.split("-");
					var arrEnd = endDate.split("-");
					var sd=new Date(arrStart[0],arrStart[1],arrStart[2]);  
					var ed=new Date(arrEnd[0],arrEnd[1],arrEnd[2]);  
					if(sd.getTime()>ed.getTime()){   
						art.dialog.alert("签约开始日期不能大于签约结束日期!",function(){
							$("#contractFactDayStart").val("");
							$("#contractFactDayEnd").val("");
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
		 $(':input','#searchTable')
		  .not(':button, :reset')
		  .val('')
		  .removeAttr('checked')
		  .removeAttr('selected');  
		 $("select").trigger("change");
	 });
	 
	 //选择门店
	 loan.getstorelsit("txtStore","hdStore");
	 var contractNo='<c:forEach items="${fns:getDictList('jk_car_contract_version')}" var="dict" varStatus="status"><c:if test="${status.last}">${dict.label }</c:if></c:forEach>';
	 $(".contractNo").html(contractNo);
	 
	//敲回车执行input条件查询
	 $(function(){
	 document.onkeydown = function(e){ 
	 var ev = document.all ? window.event : e;
	 		if(ev.keyCode== 13) {
	 			$('body form:first').submit();
	 		}
	 	}
	 });

 });
  function page(n, s) {
		if (n)
			$("#pageNo").val(n);
		if (s)
			$("#pageSize").val(s);
		$("#inputForm").attr("action", "${ctx}/car/carExtendWorkItems/fetchTaskItems/extendContractCommissioner");
		$("#inputForm").submit();
		return false;
} 
</script>
</head>
<body>
 <div class="control-group">
 <form:form id="inputForm" modelAttribute="carExtendFlowQueryView" method="post" class="form-horizontal">
    	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
     <input name="menuId" type="hidden" value="${param.menuId}" />
     <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
     <table id="searchTable" class="table1 " cellpadding="0" cellspacing="0" border="0"width="100%">
         <tr>
             <td><label class="lab">客户姓名：</label>
             	<form:input id="customerName" path="customerName" class="input_text178"/>
             </td>
            <td><label class="lab">门店名称：</label> 
					<input id="txtStore" name="storeName"
						type="text" maxlength="20" class="txt date input_text178"
						value="${secret.store }" readonly="true"/> 
					<i id="selectStoresBtn"
					class="icon-search" style="cursor: pointer;"></i>
               </td>
             <td><label class="lab">产品类型：</label>
             	<form:select
							id="productType" path="auditBorrowProductCode" class="select180">
							<option value="">请选择</option>
							<c:forEach items="${fncjk:getPrd('products_type_car_credit')}"
								var="product_type">
								<form:option value="${product_type.productCode}">${product_type.productName}</form:option>
							</c:forEach>
						</form:select>
             </td>
         </tr>
         <tr>
       <%--   <td>
         	<label class="lab">签约日期：</label>
            <form:input path="contractFactDay"  class="input_text178 Wdate" onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" style="cursor: pointer"/>
         </td>
          --%>
 		 <td><label class="lab">签约日期：</label>
            <input id="contractFactDayStart" name="contractFactDayStart"  class="input_text70 Wdate" 
            		onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" style="cursor: pointer" 
            		value="<fmt:formatDate value='${carLoanFlowQueryParam.contractFactDayStart}' type='date' pattern="yyyy-MM-dd" />" />-<input id="contractFactDayEnd" name="contractFactDayEnd"  class="input_text70 Wdate" 
            		onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" style="cursor: pointer" 
            		value="<fmt:formatDate value='${carLoanFlowQueryParam.contractFactDayEnd}' type='date' pattern="yyyy-MM-dd" />" />
         	</td>
      <td><label class="lab">合同编号：</label>
         <form:input type="text" class="input_text178" path="contractCode"></form:input></td>
         <td><label class="lab">借款期限：</label>
	           		 <form:select
							id="loanMonths" path="auditLoanMonths" class="select180">
							<option value="">请选择</option>
							<c:forEach items="${fncjk:getPrdMonthsByType('products_type_car_credit')}" var="product_type">
								<form:option value="${product_type.key}">${product_type.value}</form:option>
							</c:forEach>
						</form:select>
				</td>
		 <%-- <td><label class="lab">合同版本号：</label><form:input type="text" class="input_text178" path="contractVersion"></form:input>
         </td>	 --%>
         </tr>
		<tr id="T1" Style="display:none">
			<td><label class="lab">是否电销：</label>
				<form:radiobuttons path="loanIsPhone" items="${fns:getDictList('jk_telemarketing')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
		    </td>
		    <td><label class="lab">渠道：</label>
		             	<form:select id="loanFlag" path="loanFlag" class="select180">
		                   <option value="">请选择</option>
			                    <c:forEach items="${fns:getDictList('jk_car_throuth_flag')}" var="loan_Flag">
			                   		<form:option value="${loan_Flag.value}">${loan_Flag.label}</form:option>
			              </c:forEach>
				         </form:select>
		        </td>
		 </tr>
     </table>
     <div style="float:left;margin-left:45%;padding-top:10px">
		<img src="${context }/static/images/up.png" id="showMore" onclick="show();"></img>
	</div>	
     <div  class="tright pr30 pb5">
          <span style="position: relative;left: 100px;  top: 43px;color:red;font-size:14px;" >当前合同版本号：<span class="contractNo"></span></span>
			<input type="hidden" id="hdStore">
           <input type="button" class="btn btn-primary" id="searchBtn" value="搜索"></input>
           <input type="button" class="btn btn-primary" id="clearBtn" value="清除"></input>
 </div>
 </div>
</form:form> 
<sys:columnCtrl pageToken="carContractProductWorList"></sys:columnCtrl>
<div class="box5" style="position:relative;width:100%;overflow:hidden;height:60%;margin-bottom: 20px"> 
	
   <table id="tableList" class="table  table-bordered table-condensed table-hover " style="margin-bottom:100px;">
   <thead>
      <tr>
        <th>序号</th>
		<th>合同编号</th>
		<th>客户姓名</th>
		<th>共借人</th>
		<th>门店名称</th>
		<th>所属省份</th>
		<th>合同金额</th>
		<th>降额</th>
		<th>借款期限</th>
		<th>产品类型</th>
		<th>车牌号码</th>
		<th>已展期次数</th>
		<th>申请状态</th>
		<th>渠道</th>
		<th>是否电销</th>
		<th>合同版本号</th>
		<th>操作</th>
      </tr>
      </thead>
      	<c:if test="${ itemList!=null && fn:length(itemList)>0}">
       	<c:forEach items="${itemList}" var="item" varStatus="status"> 
        <tr <c:if test="${fn:contains(item.orderField,'0,') }">class='trRed'</c:if>>
          <td>${status.count}</td>
          <td>${item.contractCode}</td>
          <td>${item.customerName}</td>
          <td>${item.coborrowerName}</td>
          <td>${item.storeName}</td>
          <td>${item.addrProvince}</td>
          <td><fmt:formatNumber value="${item.contractAmount}" pattern="##0.00"/></td>
          <td><fmt:formatNumber value="${item.derate}" pattern="##0.00"/></td>
          <td>${item.auditLoanMonths}</td>
          <td>${item.auditBorrowProductName}</td>
          <td>${item.plateNumbers}</td>
          <td>${item.extendNumber}</td>
          <td>${item.applyStatusCode}</td>
          <td>${item.loanFlag}</td>
          <td>${item.loanIsPhone}</td>
          <td>${item.contractVersion}</td>
          <td>
              <input type="button" value="办理" class="btn_edit jkcj_contractProduct_workItems_deal"  onclick="window.location='${ctx}/bpm/flowController/openForm?applyId=${item.applyId}&wobNum=${item.wobNum}&dealType=0&token=${item.token}'"></input>&nbsp;
              <button class="btn_edit jkcj_contractProduct_workItems_history" value="${item.applyId}"  onclick="javascript:showCarLoanHisByApplyId(this.value)">历史</button>
          </td>           
      </tr>  
      </c:forEach>       
      </c:if>
      <c:if test="${ itemList==null || fn:length(itemList)==0}">
        <tr><td colspan="17" style="text-align:center;">没有待办数据</td></tr>
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