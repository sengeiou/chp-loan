<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>放款划扣已办</title>
<script type="text/javascript" language="javascript"
	src='${context}/js/common.js'></script>
<script type="text/javascript"
	src="${context}/js/pageinit/areaselect.js"></script>	
<script src="${context}/js/grant/grantDeducts.js" type="text/javascript"></script>
<script>
function page(n, s) {
	if (n)
		$("#pageNo").val(n);
	if (s)
		$("#pageSize").val(s);
	$("#deductsForm").attr("action", "${ctx }/borrow/grant/grantDeductsDone/deductsDoneInfo");
	$("#deductsForm").submit();
	return false;
}
//省市级联
$(document).ready(
		function() {
			loan.initCity("addrProvice", "addrCity",null);
			areaselect.initCity("addrProvice", "addrCity",null, $("#addrProvice")
							.attr("value"), $("#addrCity")
							.attr("value"));
			
			loan.getstorelsit("name","storeId");
			loan.getChannelFlag("loanFlag","loanFlagId");
		});
		
</script>
<meta name="decorator" content="default"/>
</head>
<body>


    
	<div class="control-group">
   <form:form  method="post" modelAttribute="UrgeServicesMoneyEx" id="deductsForm">
          <input id="pageNo" name="pageNo" type="hidden"
				value="${page.pageNo}" />
			<input id="pageSize" name="pageSize" type="hidden"
				value="${page.pageSize}" />
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td><label class="lab">客户姓名：</label><form:input type="text" class="input_text178" path="customerName"></form:input></td>
                <td><label class="lab">合同编号：</label><form:input type="text" class="input_text178" path="contractCode"></form:input></td>
                <td><label class="lab">门店：</label>
                  <form:input type="text" id="name" class="input_text178" path="name" readonly="true"></form:input>
                	  <i id="selectStoresBtn"
						class="icon-search" style="cursor: pointer;"></i>
				    <form:hidden path="storeId" id="storeId"/>
				</td>
            </tr>
            <tr>
                
                <td><label class="lab">划扣平台：</label><form:select class="select180" path="dictDealType">
						<form:option value="">请选择</form:option>
						<c:forEach items="${fns:getDictList('jk_recove_way')}" var="card_type">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
				 		</c:forEach>
				</form:select></td>
                <td><label class="lab">渠道：</label><form:input type="text" id="loanFlag" class="input_text178" path="loanFlag" readonly="true"></form:input>
                	  <i id="selectChannelBtn"
						class="icon-search" style="cursor: pointer;"></i>
				    <form:hidden path="loanFlagId" id="loanFlagId"/></td>
				<td><label class="lab">退款标识：</label><form:select class="select180" path="refundFlag">
				    	<option value=""> </option>
				    	<form:option value="1">退款</form:option>
				    </form:select>
			    </td>
            </tr>
			<tr id="T1" style="display:none">
                <td>
                <label class="lab">管辖城市：</label><form:select class="select180" style="width:110px" path="provinceCode" id="addrProvice">
                <form:option value="">选择省份</form:option>
                <c:forEach var="item" items="${provinceList}" varStatus="status">
		             <form:option value="${item.areaCode }">${item.areaName}</form:option>
	            </c:forEach>
                </form:select>-<form:select class="select180" style="width:110px" path="cityCode" id="addrCity">
                <form:option value="">请选择</form:option>
                </form:select>
                </td>
				<td><label class="lab">放款日期：</label><input  name="startDate"  id="startDay"  
                 value="<fmt:formatDate value='${UrgeServicesMoneyEx.startDate}' pattern='yyyy-MM-dd'/>"
                     type="text" class="Wdate input_text70" size="10"  
                                        onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'endDay\')}'})" style="cursor: pointer" ></input>-<input  name="endDate" 
                 value="<fmt:formatDate value='${UrgeServicesMoneyEx.endDate}' pattern='yyyy-MM-dd'/>"
                     type="text" class="Wdate input_text70" size="10" id="endDay"  
                                        onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'startDay\')}'})" style="cursor: pointer" ></input></td>
				<td><label class="lab">划扣日期：</label>
				<input  name="deductStartDate"  id="deductStartDay" value="<fmt:formatDate value='${UrgeServicesMoneyEx.deductStartDate}' pattern='yyyy-MM-dd'/>"
                     type="text" class="Wdate input_text70" size="10"  
                                        onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'deductEndDay\')}'})" style="cursor: pointer" ></input>
               -<input  name="deductEndDate" value="<fmt:formatDate value='${UrgeServicesMoneyEx.deductEndDate}' pattern='yyyy-MM-dd'/>"
                     type="text" class="Wdate input_text70" size="10" id="deductEndDay"  
                                        onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'deductStartDay\')}'})" style="cursor: pointer" ></input></td>
				
            </tr>
            
            <tr id="T2" style="display:none">  
			    <td><label class="lab">是否电销：</label><form:select class="select180" path="customerTelesalesFlag"><option value="">请选择</option>
			    <c:forEach items="${fns:getDictList('jk_telemarketing')}" var="card_type">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
				 </c:forEach>
			    </form:select></td>
			    <td>
			    <label class="lab">借款产品：</label><form:select class="select180" path="productName"><option value="">请选择</option>
			    <c:forEach items="${productList}" var="card_type">
								<form:option value="${card_type.productCode}">${card_type.productName}</form:option>
				 </c:forEach>
			    </form:select>
			    </td>
			    <td><label class="lab">模式：</label>
						 <form:select path="model" class="select180">
                          <form:option value="">全部</form:option>
                          <c:forEach items="${fns:getDictList('jk_loan_model')}" var="card_type">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
				 		  </c:forEach>
				        </form:select>	
			   </td>
            </tr>
			 
        </table>
         
        <div class="tright pr30 pb5"><input type="button" class="btn btn-primary" onclick="document.forms[0].submit();" value="搜索"> 
               <button class="btn btn-primary" id="clearBtn">清除</button>
      
        <div style="float: left; margin-left: 45%; padding-top: 10px">
	       <img src="../../../../static/images/up.png" id="showMore"></img>
	    </div>
	    </div>
	    </form:form>
		</div>
		<p class="mb5">
    	<button class="btn btn-small" id="dao">导出excel</button>
    	&nbsp;&nbsp;<label class="lab1"><span class="red">划扣累计金额：</span></label><label class="red" id="deductAmount">
    	<fmt:formatNumber value='${deductsAmount}' pattern="#,#0.00"/></label>元
    	<input type="hidden"  id="hiddenDeduct" value="0.00"/>
    	<input type="hidden" id="deduct" value="${deductsAmount}">
		&nbsp;&nbsp;<label class="lab1"><span class="red">划扣总笔数：</span></label>
		<label class="red" id="totalNum">${totalNum }</label>笔
		<input type="hidden" id="num" value="${totalNum }">
		<input type="hidden" id="hiddenNum" value="0"/>
		</p>
		<sys:columnCtrl pageToken="grantdeduct"></sys:columnCtrl>
		<div class="box5">
        <table id="tableList" class="table  table-bordered table-condensed table-hover ">
        <thead>
         <tr>
            <th><input type="checkbox" id="checkAll"/></th>
            <th>合同编号</th>
            <th>门店名称</th>
            <th>客户姓名</th>
            <th>账户名字</th>
            <th>借款产品</th>
            <th>合同金额</th>
            <th>放款金额</th>
			<th>催收服务费</th>
			<th>征信费</th>
			<th>信访费</th>
			<th>费用总计</th>
			<th>未划金额</th>
			<th>划扣金额</th>
			<th>划扣平台</th>
			<th>批借期限</th>
            <th>开户行</th>
			<th>放款日期</th>
			<th>最新划扣日期</th>
			<th>划扣回盘结果</th>
			<th>回盘原因</th>
			<th>是否电销</th>
			<th>模式</th>
            <th>渠道</th>
            <th>退款标识</th>
            <th>操作</th>
        </tr>
		</thead>
        <tbody>
         <c:if test="${ urgeList!=null && fn:length(urgeList.list)>0}">
         <c:set var="index" value="0"/>
          <c:forEach items="${urgeList.list}" var="item">
          <c:set var="index" value="${index+1}" />
             <tr>
             <td><input type="checkbox" name="checkBoxItem" 
               deductAmount='${item.splitAmount}' reason='${item.splitBackResult}'
               deductsType='${item.dictDealType}' cid='${item.urgeId }'/>
             </td>
             <td>${item.contractCode}</td>
             <td>${item.name }</td>
             <td>${item.customerName}</td>
             <td>${item.bankAccountName}</td>
             <td>${item.productName}</td>
             <td><fmt:formatNumber value='${item.contractAmount}' pattern="#,#00.00"/></td>
             <td><fmt:formatNumber value='${item.grantAmount}' pattern="#,#00.00"/></td>
             <td><fmt:formatNumber value='${item.urgeMoeny}' pattern="#,#00.00"/></td>
             <td><c:if test="${item.productName=='农信借'}"><fmt:formatNumber value='${item.feeCredit}'
				pattern="#,##0.00"/></c:if></td>
			 <td><c:if test="${item.productName=='农信借'}"><fmt:formatNumber value='${item.feePetition}'
				pattern="#,##0.00"/></c:if></td>
			 <td><fmt:formatNumber value='${item.feeSum}' pattern="#,##0.00"/></td>            
             <td><fmt:formatNumber value='${item.waitUrgeMoeny}' pattern="#,#00.00"/></td>
             <td><fmt:formatNumber value='${item.splitAmount}' pattern="#,#00.00"/></td>
             <td>${item.dictDealType}</td>
             <td>${item.contractMonths}</td>
             <td>${item.bankName}</td>
             <td><fmt:formatDate value="${item.lendingTime}"
							type="date" /></td>
             <td><fmt:formatDate value="${item.decuctTime}"
							pattern="yyyy-MM-dd" /></td>
             <td>${item.splitBackResult}</td>
             <td>${item.splitFailResult}</td>
             <td>${item.customerTelesalesFlag}</td>
             <td>${item.modelName}</td>
             <td>${item.loanFlag}</td> 
             <td><c:if test="${item.refundFlag=='1'}">退款</c:if></td> 
             <td><input class="btn_edit" sid='${item.urgeId}' name="history" value="历史" type="button"></td>
         </tr> 
         </c:forEach>  
         </c:if>
         <c:if test="${ urgeList==null || fn:length(urgeList.list)==0}">
           <tr><td colspan="24" style="text-align:center;">没有待办数据</td></tr>
         </c:if>
     </tbody>
      
    </table>
	</div>

<div class="pagination">${urgeList}</div> 
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