<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script src="${context}/js/common.js" type="text/javascript"></script>
<script src="${context}/js/grant/wthkList.js" type="text/javascript"></script>
<script type="text/javascript" src="${context}/js/payback/ajaxfileupload.js"></script>
<script>
function page(n, s) {
	if (n)
		$("#pageNo").val(n);
	if (s)
		$("#pageSize").val(s);
	$("#grantForm").attr("action", "${ctx}/borrow/grant/grantDone/wthkList");
	$("#grantForm").submit();
	return false;
}
$(document).ready(
		function() {
			loan.getstorelsit("storesCode","storeOrgId");
			var msg = "${message}";
			if (msg != "" && msg != null) {
				art.dialog.alert(msg);
			};
		});
</script>
<meta name="decorator" content="default"/>
<title>委托划扣列表</title>	
</head>
<body>
	<div class="control-group">
	<form:form id="grantForm" action="${ctx}/borrow/grant/grantDone/wthkList"  modelAttribute="loanGrantEx" method="post">
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" /> 
			    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
			    <input type="hidden" name="menuId" value="${param.menuId }">
			    <input type="hidden" name="loanCode" id="loanCode">
                <td><label class="lab">客户姓名：</label>
                <input type="text" class="input_text178" id="customerName" name="customerName" value="${loanGrantEx.customerName }"></input></td>
                <td><label class="lab">合同编号：</label>
			         <input  type="text" class="input_text178" id="contractCode" name="contractCode" value="${loanGrantEx.contractCode }"></input>
			     </td>
                <td><label class="lab">门店：</label>
                <input type="text" id="storesCode" class="input_text178" name="storesCode" readonly="true" value="${loanGrantEx.storesCode }"></input>
                	  <i id="selectStoresBtn"
						class="icon-search" style="cursor: pointer;"></i>
				   <input type="hidden" name="storeOrgId" id="storeOrgId" /></td>
            </tr>
            <tr>
            	<td>
					   	 <label class="lab">借款产品：</label>
					   	 <select id="productCode" name="productCode"
				 class="select180 required">
					<option value="">请选择</option>
					<c:forEach var="item" items="${productList}" varStatus="status">
					  <option value="${item.productCode }"
					    <c:if test="${item.productCode==loanGrantEx.productCode}">
					      selected=true 
					    </c:if>
					  >${item.productName}</option>
					</c:forEach>
     			</select>
				   </td>
                <td><label class="lab">放款日期：</label>
               <input  name="startDate"  id="startDate"  
                 value="<fmt:formatDate value='${loanGrantEx.startDate}' pattern='yyyy-MM-dd'/>"
                     type="text" class="Wdate input_text70" size="10"  
                                        onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'endDate\')}'})" style="cursor: pointer" ></input>-<input id="endDate" name="endDate" 
                 value="<fmt:formatDate value='${loanGrantEx.endDate}' pattern='yyyy-MM-dd'/>"
                     type="text" class="Wdate input_text70" size="10" 
                                        onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'startDate\')}'})" style="cursor: pointer" ></input></td>
				 <td><label class="lab">合同签订日期：</label>
               <input  name="htStartDate"  id="htStartDate"  
                 value="<fmt:formatDate value='${loanGrantEx.htStartDate}' pattern='yyyy-MM-dd'/>"
                     type="text" class="Wdate input_text70" size="10"  
                                        onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'htEndDate\')}'})" style="cursor: pointer" ></input>-<input id="htEndDate" name="htEndDate" 
                 value="<fmt:formatDate value='${loanGrantEx.htEndDate}' pattern='yyyy-MM-dd'/>"
                     type="text" class="Wdate input_text70" size="10" 
                                        onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'htStartDate\')}'})" style="cursor: pointer" ></input></td> 
                
                
            </tr>
            <tr id="T1" style="display:none">
            	<td><label class="lab" >开户行名称：</label>
		                <sys:multipleBank bankClick="selectBankBtn" bankName="bankName" bankId="bankId"></sys:multipleBank>
		                <input id="bankName" type="text" class="input_text178" name="bankName" value='${loanGrantEx.bankName }' readonly="true">&nbsp;<i id="selectBankBtn"
								class="icon-search" style="cursor: pointer;"></i> 
								<input type="hidden" id="bankId" name='bankId' value="${loanGrantEx.bankId }">
				</td>
				<td><label class="lab">卡号：</label>
			         <input type="text" class="input_text178" id="bankCardNo" name="bankCardNo" value='${loanGrantEx.bankCardNo }'></input>
			     </td>
			   <td>
					   	 <label class="lab">渠道：</label>
					   	 <select class="select180" id="loanFlag" name="loanFlag"><option value="">请选择</option>
						   	 <c:forEach items="${fns:getDictList('jk_channel_flag')}" var="channel_flag">
						   	 <c:if test="${'5'!=channel_flag.value}">
								<option value="${channel_flag.value}" 
								<c:if test="${loanGrantEx.loanFlag==channel_flag.value}">
					      selected=true 
					    </c:if>>${channel_flag.label}</option>
					    </c:if>
				             </c:forEach>
					 	 </select>
				   </td>
            </tr>
			<tr id="T2" style="display:none">
			     
				 <td><label class="lab">模式：</label>
                  <select class="select180" id="model" name="model">
				 <option value="">请选择</option>
				 <c:forEach items="${fns:getDictList('jk_loan_model')}" var="loan_model">
								<option value="${loan_model.value}"<c:if test="${loanGrantEx.model==loan_model.value}">
					      selected=true 
					    </c:if>>${loan_model.label}</option>
				 </c:forEach>
				 </select>
				 </td> 
               
            </tr>

        </table>
        <div class="tright pr30 pb5">
        <input class="btn btn-primary" type="submit" value="搜索" />
         <input type="button" class="btn btn-primary" id="clearBtn" value="清除" />
          <div style="float: left; margin-left: 45%; padding-top: 10px">
	      <img src="../../../../static/images/up.png" id="showMore"></img>
	     </div>
		</div>
		</form:form>
	</div>

	<p class="mb5">
		<button class="btn btn-small" id="daoBtn" onclick="javaScript:downFiles();">批量下载</button>
	</p>
    	 <sys:columnCtrl pageToken="grantSureList"></sys:columnCtrl>
  <div class="box5">
	   <table id="tableList"
		class="table  table-bordered table-condensed table-hover ">
		<thead>
			<tr>
				<th><input type="checkbox" id="checkAll"/></th>
            <th>客户姓名</th>
            <th>卡号</th>
            <th>开户行</th>
            <th>合同编号</th>
            <th>合同签订日期</th>
            <th>门店</th>
            <th>放款日期</th>
            <th>合同金额</th>
            <th>借款状态</th>
            <th>渠道</th>
            <th>模式</th>
            <th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${ grantDoneList!=null && fn:length(grantDoneList.list)>0}">
         <c:set var="index" value="0"/>
          <c:forEach items="${grantDoneList.list}" var="item">
          <c:set var="index" value="${index+1}" />
             <tr>
             <td><input type="checkbox" name="checkBoxItem" value="${item.loanCode}"/></td>
             <td>${item.customerName}</td>
             <td>${item.bankCardNo}</td>
             <td>${item.bankName}</td>
             <td>${item.contractCode}</td>
             <td><fmt:formatDate value="${item.contractFactDay}"
							type="date" /></td>
			 <td>${item.storesCode}</td>
			 <td><fmt:formatDate value="${item.lendingTime}"
							type="date" /></td>
			 <td><fmt:formatNumber value='${item.contractAmount}' pattern="#,#00.00" /></td>				
             <td>${item.dictLoanStatus }</td>
             <td>${item.loanFlag}</td>
             <td>${item.model}</td>
             <td class="tcenter"><button class="btn_edit" onclick="downFile('${item.loanCode}')">下载</button></td>
             </tr> 
         </c:forEach>  
         </c:if>
         <c:if test="${ grantDoneList==null || fn:length(grantDoneList.list)==0}">
           <tr><td colspan="24" style="text-align:center;">没有待办数据</td></tr>
         </c:if>
		</tbody>
	</table>
	</div>
	<c:if test="${workItems!=null || fn:length(workItems)>0}">
		<div class="page">${page}</div>
	</c:if>
	
	<div class="pagination">${grantDoneList}</div>
	<div class='modal fade' id="subDIV" style="width: 90%; height: 90%" data-backdrop="static">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-body">下载中，请等待......</div>
			</div>
		</div>
	</div>

</body>
</html>