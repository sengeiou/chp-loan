<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>

<script type="text/javascript" language="javascript"
	src='${context}/js/common.js'></script>
<script type="text/javascript" language="javascript"
	src='${context}/js/grant/amountCaculate.js'></script>
<script src="${context}/js/grant/disCard.js" type="text/javascript"></script>
<script type="text/javascript"
	src="${context}/js/pageinit/areaselect.js"></script>
<meta name="decorator" content="default"/>
	<title>分配卡号</title>	
<script type="text/javascript">
$(document).ready(
		function() {
			loan.initCity("addrProvice", "addrCity",null);
			areaselect.initCity("addrProvice", "addrCity",null, $("#addrProvice")
							.attr("value"), $("#addrCity")
							.attr("value"));
			loan.getstorelsit("storeName","storeOrgId");
		});
function page(n, s) {
	if (n)
		$("#pageNo").val(n);
	if (s)
		$("#pageSize").val(s);
	$("#disCardForm").attr("action", "${ctx}/borrow/grant/disCard/getDisCardList");
	$("#disCardForm").submit();
	return false;
}
</script>
<style type="text/css">
  .trRed {
color:red;
}
</style>
</head>
<body>
  <div class="control-group" >
      <form:form id="disCardForm" action="${ctx}/borrow/grant/disCard/getDisCardList" modelAttribute="LoanFlowQueryParam" method="post">
       <input type="hidden" id="pageNo" name="pageNo" value="${workItems.pageNo}" />
	   <input type="hidden" id="pageSize" name="pageSize" value="${workItems.pageSize}" />
	   <input type="hidden" id="userCode"/>
       <input type="hidden" id="checkVal"/>
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
        
            <tr>
                <td><label class="lab">客户姓名：</label>
                <form:input type="text" class="input_text178" path="customerName"></form:input></td>
                <td><label class="lab">合同编号：</label>
                <form:input type="text" class="input_text178" path="contractCode"></form:input></td>
                <td><label class="lab">门店：</label>
                   <form:input type="text" id="storeName" class="input_text178" path="storeName" readonly="true"></form:input>
                	  <i id="selectStoresBtn"
						class="icon-search" style="cursor: pointer;"></i>
				   <form:hidden path="storeOrgIds" id="storeOrgId"/>
                </td>
            </tr>
            <tr>
                
                <td ><label class="lab">渠道：</label>
                <form:select class="select180" path="channelCode">
						<form:option value="">请选择</form:option>
							<c:forEach items="${fns:getDictList('jk_channel_flag')}" var="card_type">
								<c:if test="${card_type.label=='P2P'|| card_type.label=='财富'|| card_type.label=='XT01' || card_type.label=='XT02'|| card_type.label==' '}">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
								</c:if>
				             </c:forEach>
						</form:select></td>
                <td><label class="lab">证件号码：</label>
                <form:input type="text" class="input_text178" path="identityCode"></form:input>
				</td>
				 <td>
				 <sys:multipleBank bankClick="selectBankBtn"
							bankName="search_applyBankName" bankId="bankId"></sys:multipleBank>
				 <label class="lab">开户行名称：</label>
						<form:input type="text"
							id="search_applyBankName" name="search_applyBankName"
							class="input_text178" path="depositBanks"></form:input> 
						<i id="selectBankBtn" class="icon-search" style="cursor: pointer;"></i>
				</td>
            </tr>
			<tr id="T1" style="display:none">
                <td><label class="lab">所属城市：</label>
                <form:select class="select180" style="width:110px" path="provinceCode" id="addrProvice">
                <form:option value="">选择省份</form:option>
                <c:forEach var="item" items="${provinceList}" varStatus="status">
		             <form:option value="${item.areaCode }">${item.areaName}</form:option>
	            </c:forEach>
                </form:select>-<form:select class="select180" style="width:110px" path="cityCode" id="addrCity">
                <form:option value="">请选择</form:option>
                </form:select>
                </td>
				<td><label class="lab">放款金额：</label>
				<form:input type="text" class="input_text70" path="lendingMoneyStart" id="lendingMoneyStart"></form:input>-<form:input type="text" class="input_text70" path="lendingMoneyEnd" id="lendingMoneyEnd"></form:input></td>
				<td>
				<label class="lab">是否电销：</label>
				<form:select class="select180" path="telesalesFlag"><form:option value="">请选择</form:option>
			    <c:forEach items="${fns:getDictList('jk_telemarketing')}" var="card_type">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
				 </c:forEach></form:select>
				</td>
				
            </tr>
            <tr id="T2" style="display:none">
                <td><label class="lab">是否追加借：</label>
                <form:select class="select180" path="additionalFlag">
                <form:option value="">请选择</form:option>
                <c:forEach items="${fns:getDictList('jk_add_flag')}" var="card_type">
						<form:option value="${card_type.value}">${card_type.label}</form:option>
				</c:forEach>
                </form:select></td>
				<td><label class="lab">放款金额上限：</label>
				<input type="text" id="maxMoney" class="input_text70" /><span class="all">&nbsp;<a href="#" id="lendingMoneySure">确定</a></span></td>
				<td><label class="lab">是否加急：</label>
				 <form:select class="select180" path="urgentFlag">
				 <form:option value="">请选择</form:option>
				 <c:forEach items="${fns:getDictList('jk_urgent_flag')}" var="card_type">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
				 </c:forEach>
				 </form:select>
				</td>
            </tr>
            <tr id="T3" style="display:none">
              <td><label class="lab">提交批次：</label>
                    <form:input type="text" class="input_text178" path="submissionBatch"></form:input>
			  </td>
			 <td><label class="lab">提交时间：</label>
				   <input  name="submissionDateStart"  id="submissionDateStart"  
                  value="<fmt:formatDate value='${LoanFlowQueryParam.submissionDateStart}' pattern='yyyy-MM-dd'/>"
                     type="text" class="Wdate input_text70" size="10"  
                                        onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'submissionDateEnd\')}'})" style="cursor: pointer" ></input>-<input  name="submissionDateEnd" 
                  value="<fmt:formatDate value='${LoanFlowQueryParam.submissionDateEnd}' pattern='yyyy-MM-dd'/>"
                     type="text" class="Wdate input_text70" size="10" id="submissionDateEnd"  
                                        onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'submissionDateStart\')}'})" style="cursor: pointer" ></input></td>
			  <td>
			    <label class="lab">是否加盖失败：</label>
                	<form:select class="select180" path="signUpFlag">
                	    <form:option value="">请选择</form:option>
            			<form:option value="1">成功</form:option>
           			    <form:option value="0">失败</form:option>
           			</form:select>
			  </td>
            </tr>
        </table>
        <div class="tright pr30 pb5"><input class="btn btn-primary" type="submit" value="搜索" id="searchBtn"></input>
        <button class="btn btn-primary" id="clearBtn">清除</button>
        <div style="float: left; margin-left: 45%; padding-top: 10px">
	      <img src="../../../../static/images/up.png" id="showMore"></img>
	   </div>
		</div>
		</form:form>
		</div>
		<p class="mb5">
    	<button class="btn btn-small" id="disCardBtn">批量分配</button>
    	<button class="btn btn-small" id="disCardBackBtn" role="button" data-toggle="modal">批量退回</button>
 		<button class="btn btn-small" id="daoBtn">导出excel</button>
 		 <button class="btn btn-small" id="rejectApplyBtn">驳回申请</button>
    	&nbsp;&nbsp;<label class="lab1">放款累计金额：</label><label class="red" id="totalGrantMoney">0.00</label>元
    	&nbsp;&nbsp;<label class="lab1">放款累计笔数：</label><label class="red" id="totalGrantCount">0</label>
    	</p>
    	<input type="hidden" id="hiddenGrant" value="0.00"/>
    	<div>
    	 <sys:columnCtrl pageToken="disCardList"></sys:columnCtrl>
    	</div>
    	<div class="box5" style="overflow: auto; width: 100%;">
        <table id="tableList" class="table  table-bordered table-condensed table-hover ">
        <thead>
          <tr>
            <th><input type="checkbox" id="checkAll"/></th>
            <th>合同编号</th>
            <th>客户姓名</th>
            <th>证件号码</th>
            <th>门店名称</th>
            <th>借款产品</th>
            <th>合同金额</th>
            <th>放款金额</th>
            <th>批借期限</th>
            <th>开户行</th>
			<th>是否电销</th>
            <th>状态</th>
            <th>渠道</th>
            <th>加急标识</th>
            <th>提交批次</th>
            <th>提交日期</th>
            <th>是否加盖失败</th>
            <th>操作</th>
         </tr>
         </thead>
		<tbody>
         <c:if test="${ workItems!=null && fn:length(workItems.list)>0}">
         <c:set var="index" value="0"/>
          <c:forEach items="${workItems.list}" var="item">
          <c:set var="index" value="${index+1}" />
              <tr <c:if test="${item.frozenFlag=='1'}">
	          class = 'trRed'
	          </c:if>
	          >
           	 <td><input type="checkbox" name="checkBoxItem" loanMarking='${item.channelName}'
            	  grantAmount='${item.lendingMoney}'  frozenFlag="${item.frozenFlag}" 
            	 value='${item.applyId}' flowParam='${item.applyId},${item.contractCode},${item.loanCode}'/>
           	  </td>
             <td>${item.contractCode}</td>
             <td>${item.customerName}</td>
             <td>${item.identityCode}</td>
             <td>${item.storeName}</td>
             <td>${item.replyProductName}</td>
             <td><fmt:formatNumber value='${item.contractMoney}' pattern="#,#00.00"/></td>
             <td><fmt:formatNumber value='${item.lendingMoney}' pattern="#,#00.00"/></td>
             <td>${item.replyMonth}</td>
             <td>${item.depositBank}</td>
             <td>
             <c:if test="${item.telesalesFlag=='1'}">
             <span>是</span>
             </c:if>
             </td>
             <td>${item.loanStatusName}
             <c:if test="${item.frozenFlag=='1'}">(门店申请冻结)</c:if>
             </td>
             <td>${item.channelName}</td>
             <td>
             <c:if test="${item.urgentFlag=='1'}">
                    <span style="color:red">加急</span>
             </c:if>
             </td>   
             <td>${item.submissionBatch}</td> 
             <td>${item.submissionDate}</td>  
             <td>
               <c:choose>
                 <c:when test="${item.signUpFlag=='1'}">
                                                    成功
                 </c:when>
                 <c:when test="${item.signUpFlag=='0'}">
                         <span style="color:red">失败</span>
                 </c:when>
               </c:choose>
             </td>       
             <td><button class="btn_edit" name="dealBtn" value='${item.applyId}'>办理</button>
             <button class="btn_edit"  onclick="showLoanHis('${item.applyId}')" >历史</button>
             <button class="btn_edit" name="backBtn" value='${item.applyId}' role="button" data-toggle="modal">退回</button></td>
             </tr> 
         </c:forEach>  
            
         </c:if>
         
         <c:if test="${ workItems==null || fn:length(workItems.list)==0}">
           <tr><td colspan="18" style="text-align:center;">没有待办数据</td></tr>
         </c:if>
       </tbody>
    </table>
	</div>
<!-- 退回原因显示 -->
<div class="modal fade" id="backDiv" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"> 
			<div class="modal-dialog" role="document">
				    <div class="modal-content">
					<div class="modal-header">
					    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title" id="myModalLabel">退回原因</h4>
					</div>
					<div class="modal-body">
                      <table >
                	<tr>
                   	 	<td ><label class="lab">退回原因：</label>
                    	
                      	<select id="reason" class="select180" >
                        <c:forEach items="${fns:getDictList('jk_chk_back_reason')}" var="card_type">
                        <c:if test="${card_type.label=='客户主动放弃'||card_type.label=='风险客户'||card_type.label=='其他'}">
							<option value="${card_type.value}">${card_type.label}</option>
						</c:if>
				       </c:forEach>
                      	</select>
                    </td>
                	</tr>
                	<tr id="other" style="display:none">
                    	<td><label  class="lab">备注</label>
                    	<span class="red">*</span><textarea id="remark" rows="" cols="" style='font-family:"Microsoft YaHei";' ></textarea></td>
                	</tr>
            	</table>
                    </div>
					<div class="modal-footer">
       					 <button class="btn btn-primary"  class="close" data-dismiss="modal" id="sureBtn">确认</button>
      					 <button class="btn btn-primary"  class="close" data-dismiss="modal">取消</button>
   					</div>
			</div>
			</div>
	  </div>
	   <!-- 冻结驳回弹出框 -->
		<div id="rejectFrozen" style="display:none">
			<form method="post" action="">
				<table class="table1" cellpadding="0" cellspacing="0" border="0"
					width="100%">
					<tr valign="top">
						<td><label ><span style="color:red;">*</span>驳回理由：</label>
							<textarea id="rejectReason" maxlength="450" cols="55" class="textarea_refuse" ></textarea>
						</td>
					</tr>
				</table>		
			</form>
		</div>
	<div class="pagination">${workItems}</div>
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