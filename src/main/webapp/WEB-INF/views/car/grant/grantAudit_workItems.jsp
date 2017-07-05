<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>放款确认待办列表</title>	
<script type="text/javascript" language="javascript" src='${context}/js/common.js'></script>
<script type="text/javascript" src="${context}/js/enter_select.js"></script>
<script src="${context}/js/car/grant/carGrantAudit.js" type="text/javascript"></script>
<meta name="decorator" content="default"/>
<script type="text/javascript">
$(document).ready(
		function() {
			

			 $('#clearBtn').bind('click',function(){
				 $(':input','#grantAuditForm')
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
			 
			 // 当退回原因为其他时，备注前增加  红星
			  $('#reason').bind('change',function(){
				 var dictBackMestype=$("#reason").attr("selected","selected").val();
				 if(dictBackMestype == "9")
				 {
					 $('#remarkSpan').show();
				 }else{
					 $('#remarkSpan').hide();
				 }
			 }); 
			 
		});
function page(n, s) {
	if (n)
		$("#pageNo").val(n);
	if (s)
		$("#pageSize").val(s);
	$("#grantAuditForm").attr("action", "${ctx}/car/carLoanWorkItems/fetchTaskItems/balanceManager");
	$("#grantAuditForm").submit();
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
	<div class="control-group">
    <form:form id="grantAuditForm" action="${ctx}/car/carLoanWorkItems/fetchTaskItems/balanceManager" modelAttribute="carLoanFlowQueryParam" >
           <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
           <input name="menuId" type="hidden" value="${param.menuId}" />
     	   <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
          <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td><label class="lab">客户姓名：</label><form:input type="text" class="input_text178" path="customerName"></form:input></td>
                <td><label class="lab">合同编号：</label><form:input type="text" class="input_text178" path="contractCode"></form:input></td>
                   <td><sys:multipleBank bankClick="selectBankBtn"
							bankName="search_applyBankName" bankId="bankId"></sys:multipleBank>
						<label class="lab">放款银行：</label> <form:input type="text"
							id="search_applyBankName" path="midBankName"
							class="input_text178"></form:input> <i
						id="selectBankBtn" class="icon-search" style="cursor: pointer;"></i>
						<input type="hidden" id="bankId" ></input>
			      </td>
            </tr>
            <tr>
   
            	<td><label class="lab">放款日期：</label><input id="lendingTimeStart"  name="lendingTimeStart"  type="text" class="Wdate input_text70" size="10"  
                                        onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" style="cursor: pointer" value="<fmt:formatDate value='${carLoanFlowQueryParam.lendingTimeStart}' type='date' pattern="yyyy-MM-dd" />" />-
                                        <input id="lendingTimeEnd" name="lendingTimeEnd"  type="text" class="Wdate input_text70" size="10"  
                                        onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" style="cursor: pointer" value="<fmt:formatDate value='${carLoanFlowQueryParam.lendingTimeEnd}' type='date' pattern="yyyy-MM-dd" />" />
                </td>
  				<td ><label class="lab">是否电销：</label><form:select class="select180" path="loanIsPhone"><form:option value="">请选择</form:option>
				 <c:forEach items="${fns:getDictList('jk_telemarketing')}" var="card_type">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
				 </c:forEach></form:select></td>
				 <td><label class="lab">渠道：</label>
		             <form:select id="loanFlag" path="loanFlag" class="select180">
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
       <div class="tright pr30 pb5"><input type="button" class="btn btn-primary" id="searchBtn" value="搜索"></input>
        	<input type="button" class="btn btn-primary" id="clearBtn" value="清除"></input>
         
	 </div>
	 </form:form>
	 </div>
    <p class="mb5">
    		&nbsp;&nbsp;
		<input type="checkbox" id="checkAll"/>  全选    
    	<button class="btn btn-small" id="daoBtn">导出excel</button>
    	<button class="btn btn-small" id="auditBtn">批量审核</button>
    	&nbsp;&nbsp;<label class="lab1">放款累计金额：</label><label class="red" id="totalGrantMoney"><fmt:formatNumber value='${totalGrantMoney}' pattern="#,##0.00"/></label>元</p>
    	<input type="hidden" id="hiddenTotalGrant" value='${totalGrantMoney}'></input>
    </p>
    
     <sys:columnCtrl pageToken="carGrantAuditList" ></sys:columnCtrl>
     <div class="box5"  style="position:relative;width:100%;overflow:hidden;height:60%;margin-bottom: 20px">
    <table id="tableList" class="table  table-bordered table-condensed table-hover " >
       <thead>
        <tr>
            <th></th>
            <th>合同编号</th>
            <th>客户姓名</th>
            <th>共借人</th>
            <th>借款产品</th>
            <th>合同金额</th>
            <th>放款失败金额</th>
            <th>批借期限(天)</th>
			<th>放款日期</th>
            <th>放款账号</th>
            <th>开户行</th>
            <th>账号姓名</th>
            <th>渠道</th>
            <th>是否电销</th>
            <th>回盘结果</th>
            <th>回盘原因</th>
	        <th>操作</th>
        </tr>
        </thead>
        <tbody>
        	<c:if test="${ itemList!=null && fn:length(itemList)>0}">
       		<c:forEach items="${itemList}" var="item" varStatus="status"> 
        	<tr <c:if test="${fn:contains(item.orderField,'0,') }">class='trRed'</c:if>>
             <td>
             <input type="checkbox" name="checkBoxItem" grantAmount='${item.contractAmount}'
              value='${item.applyId}'/>
              </td>
             <td>${item.contractCode}</td>
             <td>${item.customerName}</td>
             <td>${item.coborrowerName}</td>
			 <td>${item.auditBorrowProductName}</td>
             <td><fmt:formatNumber value='${item.contractAmount}' pattern="#,##0.00"/></td>
             <td><fmt:formatNumber value='${item.grantFailAmount}' pattern="#,##0.00"/></td>
             <td>${item.auditLoanMonths}</td>
             <td><fmt:formatDate value="${item.lendingTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
             <td>${item.midBankCardNo}</td>
             <td>${item.midBankName}</td>
             <td>${item.midBankCardName}</td>
             <td>${item.loanFlag}</td>
             <td>${item.loanIsPhone}</td>
             <td>${item.grantRecepicResult}</td>
             <td>${item.grantFailResult}</td>
             <td>
	          <button  class="btn_edit"  data-target="#back_div" data-toggle="modal" name="back" applyId='${item.applyId}' 
	             	wobNum='${item.wobNum}' dealType='0'token='${item.token}' contractCode='${item.contractCode}' stepName='${item.stepName }' loanCode='${item.loanCode}'>退回</button>
        	  <button class="btn_edit" value='${item.applyId}' name="jumpTo">办理</button>
              <button class="btn_edit" name="historyBtn" value='${item.applyId}' onclick="showCarLoanHis('${item.loanCode}')">历史</button>
             </td>
         </tr> 
      	</c:forEach>       
	    </c:if>
	    <c:if test="${ itemList==null || fn:length(itemList)==0}">
	       <tr><td colspan="18" style="text-align:center;">没有待办数据</td></tr>
	    </c:if>
        </tbody> 
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
       <!--点击退回时用  -->
  	<form id="param">
		<input type="hidden" name="applyId" id="applyId"></input>
	    <input type="hidden" name="contractCode" id="contractCode"></input>	
	    <input type="hidden" name="loanCode" id="loanCode"></input>
 	 </form>	
    
    
    <!--退回弹框  -->
    <div  id="back_div" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabe1l" aria-hidden="true" data-url="data">
		<div class="modal-dialog role="document"">
				<div class="modal-content">
				<div class="modal-header">
					   <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						
						<div class="l">
         					<h4 class="pop_title">退回</h4>
       					</div>
					 </div>
				 <div class="modal-body">
				<form id="reasonValidate">
										    <table class="table4" cellpadding="0" cellspacing="0" border="0" width="100%" id="tpTable">
				    <tr>
				    	<td><lable class="lab"><span style="color: red;">*</span>退回原因：</lable>

				    		 <select id="reason" class="select180 required " >
				    				 <option value="">请选择</option>
									<c:forEach items="${fns:getDictLists('6,7,9', 'jk_chk_back_reason')}" var="item">
										<option value="${item.value}">${item.label}</option>
									</c:forEach>
				 			</select>
				 			</p>
				 		</td>
				    </tr>
          			 <tr>
						<td><lable class="lab" id="redChange" ><span id="remarkSpan" style="color: red;display:none;" >*</span>备注：</lable>

								<textarea rows="" cols="" class="textarea_refuse"
									id="remark" name="consLoanRemarks" ></textarea>

						</td>
					</tr>
        </table>
				</form>

				 </div>
				 <div class="modal-footer">
				 <button id="backSure" class="btn btn-primary"  aria-hidden="true" >确定</button>
				 <button class="btn btn-primary" data-dismiss="modal" aria-hidden="true">取消</button>
				 </div>
				</div>
				</div>
	</div>
	<!--退回弹框  -->
</body>
</html>