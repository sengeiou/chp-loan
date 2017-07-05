<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript" src="${context}/js/payback/ajaxfileupload.js"></script>
<script src="${context}/js/car/grant/carGrant.js" type="text/javascript"></script>
<script src="${context}/js/common.js" type="text/javascript"></script>
<script type="text/javascript" src="${context}/js/enter_select.js"></script>
<script type="text/javascript" src="${context}/js/pageinit/areaselect.js"></script>
<meta name="decorator" content="default"/>
	<title>待放款列表</title>	
<script type="text/javascript">
$(document).ready(function(){
	 
		
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
	$("#grantForm").attr("action", "${ctx}/car/carLoanWorkItems/fetchTaskItems/deductionCommissioner");
	$("#grantForm").submit();
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
          <form:form id="grantForm" action="${ctx}/car/carLoanWorkItems/fetchTaskItems/deductionCommissioner" modelAttribute="carLoanFlowQueryParam" method="post">
          <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
          <input name="menuId" type="hidden" value="${param.menuId}" />
     	  <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
          <table id = "searchTable" class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td><label class="lab">客户姓名：</label>
                   <form:input type="text" class="input_text178" path="customerName"></form:input></td>
                <td>			
					<label class="lab">门店：</label> 
					<form:input id="txtStore" path="storeName"
						type="text" maxlength="20" class="txt date input_text178"
						value="${secret.store }"  readonly="true"/> 
					<i id="selectStoresBtn"
					class="icon-search" style="cursor: pointer;"></i>
		     	</td>
                <td><label class="lab">身份证号：</label>
                <form:input type="text" class="input_text178" path="certNum"></form:input></td>
            </tr>
            <tr>
             	<td><label class="lab">合同编号：</label>
             	<form:input type="text" class="input_text178" path="contractCode"></form:input></td>
			    <td><sys:multipleBank bankClick="selectBankBtn"
							bankName="search_applyBankName" bankId="bankId"></sys:multipleBank>
						<label class="lab">开户行：</label> <form:input type="text"
							id="search_applyBankName" path="midBankName"
							class="input_text178"></form:input> <i
						id="selectBankBtn" class="icon-search" style="cursor: pointer;"></i>
			    </td>
				 
				<td ><label class="lab">是否电销：</label>
				<form:select class="select180" path="loanIsPhone"><form:option value="">请选择</form:option>
				 <c:forEach items="${fns:getDictList('jk_telemarketing')}" var="card_type">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
				 </c:forEach></form:select></td>
              
            </tr>
            <tr id="T1" style="display:none">
            	<td ><label class="lab">借款状态：</label>
            	<form:select class="select180" path="dictStatus"><form:option value="">请选择</form:option>
				 <c:forEach items="${fns:getDictList('jk_loan_apply_status')}" var="card_type">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
				 </c:forEach>				 
				</form:select></td>
				
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
		             <td></td>
		             
           
            </tr>
        </table>
       <div  class="tright pr30 pb5">
       		 <input type="hidden" id="hdStore">
       		 <input type="hidden" id="bankId" ></input>
	         <input type="button" class="btn btn-primary" id="searchBtn" value="搜索"></input>
	         <input type="button" class="btn btn-primary" id="clearBtn" value="清除"></input>
		     <div class="xiala" style="text-align:center;">
			  <img src="${context}/static/images/up.png" id="showMore"></img>
		    </div>
	 	</div>
	    </div>
	</form:form> 
	 </div>
    <p class="mb5">
    			&nbsp;&nbsp;
			<input type="checkbox" id="checkAll"/>  全选
			<button id="onLineGrantBtn" role="button" class="btn btn-small"  data-toggle="modal">线上放款</button>
			<button class="btn btn-small" id="offLineDao">导出excel</button>
			<button id="offLineShang" role="button" class="btn btn-small" data-target="#uploadAuditedModal" data-toggle="modal">上传回执结果</button>
			<button class="btn btn-small" id="manualBtn">手动确认放款</button>
    	&nbsp;&nbsp;<label class="lab1">放款累计金额：</label><label class="red" id="totalGrantMoney"><fmt:formatNumber value='${totalGrantMoney}' pattern="#,##0.00"/></label>元</p>
    	<input type="hidden" id="hiddenTotalGrant" value='${totalGrantMoney}'></input>
    </p>
		<sys:columnCtrl pageToken="carGrantList"></sys:columnCtrl>
		<div class="box5" style="position:relative;width:100%;overflow:hidden;height:60%;margin-bottom: 20px">
    <table id="tableList" class="table  table-bordered table-condensed table-hover ">
    <thead>
        <tr>
            <th></th>
            <th>合同编号</th>
            <th>门店名称</th>
            <th>客户姓名</th>
            <th>证件号码</th>
            <th>借款产品</th>
            <th>合同金额</th>
            <th>放款失败金额</th>
            <th>应划扣金额</th>
            <th>批借期限(天)</th>
            <th>开户行</th>
            <th>状态</th>           
            <th>放款途径</th>
			<th>回盘结果</th>
			<th>回盘原因</th>
			<th>是否电销</th>
			<th>渠道</th>
            <th>操作</th>
        </tr>
      </thead>
      <tbody>
      		  	<c:if test="${ itemList!=null && fn:length(itemList)>0}">
       	<c:forEach items="${itemList}" var="item" varStatus="status"> 
      		<tr <c:if test="${fn:contains(item.orderField,'0,') }">class='trRed'</c:if>>
              <td><input type="checkbox" name="checkBoxItem" grantAmount='${item.contractAmount}'
	              contractCode="${item.contractCode }" dictStatusFlag='${item.dictStatus}' dictLoanWayFlag='${item.dictLoanWay}' dictLoanWay='${item.dictLoanWay}'
	              value='${item.applyId}' index='${index}'/>
              </td>
             <td>${item.contractCode}</td>
             <td>${item.storeName}</td>
             <td>${item.customerName}</td>
             <td>${item.certNum}</td>
             <td>${item.auditBorrowProductName}</td>
             <td><fmt:formatNumber value='${item.contractAmount}' pattern="#,##0.00"/></td>
             <td><fmt:formatNumber value='${item.grantFailAmount}' pattern="#,##0.00"/></td>
             <td><fmt:formatNumber value='${item.deductsAmount}' pattern="#,##0.00"/></td>
             <td>${item.auditLoanMonths}</td>
             <td>${item.midBankName}</td>
             <td id="statusId${item.applyId}" >${item.dictStatus} </td>
             <td>${item.dictLoanWay} </td>
             <td>${item.grantRecepicResult}</td>
             <td>${item.grantFailResult}</td>
             <td>${item.loanIsPhone}</td>
             <td>${item.loanFlag}</td>
             <td>
               <c:if test="${item.dictStatus ne '处理中'}">
	          <button  class="btn_edit"  data-target="#back_div" data-toggle="modal" name="back" applyId='${item.applyId}' 
	             	wobNum='${item.wobNum}' dealType='0'token='${item.token}' contractCode='${item.contractCode}'
	             	 stepName='${item.stepName }' loanCode='${item.loanCode}'>退回</button></c:if>
             <button class="btn_edit" id="dealBtn" name="dealBtn" value='${item.applyId}' onclick="showCarLoanDownload()">下载</button>
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
</div>
<div class="modal fade" id="uploadAuditedModal" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"> 
			<div class="modal-dialog" role="document">
				    <div class="modal-content">
				    <form id="uploadAuditForm" role="form" enctype= "multipart/form-data" method="post" action="${ctx}/car/grant/grantDeal/importResult">
					<div class="modal-header">
					    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title" id="myModalLabel">上传回执结果</h4>
					</div>
					<div class="modal-body">
                      <input type='file' name="file" id="fileid">
                    </div>
					</form>
					<div class="modal-footer">
						 <span style="float:left;color:red;">仅限于导入xls，xlsx格式的数据,且大小不超过10M</span>
       					 <button class="btn btn-primary"  class="close" data-dismiss="modal" id="sureBtn">确认</button>
      					 <button class="btn btn-primary"  class="close" data-dismiss="modal" >取消</button>
   					</div>
			</div>
			</div>
		
	</div>
	
	
	
	<div class="modal fade" id="online" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
       <div class="modal-content">
       <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h4 class="modal-title" id="online">线上放款</h4>
	   </div>
       <div class="modal-body">
       <table class="table4" cellpadding="0" cellspacing="0" border="0" width="100%">
             <tr>
                <td><label class="lab">发送平台：</label>
                <select class="select180" id="plat"> 				    				
				<c:forEach items="${fns:getDictLists('1,2', 'jk_payment_way')}" var="item">
					<option value="${item.value}">${item.label}</option>
				</c:forEach>
				</select>
				</td>
				<input type="hidden" id="check">
            </tr>
        </table>
     </div>
    <div class="modal-footer">
    <button class="btn btn-primary"  class="close" data-dismiss="modal" id="onlineBtn">确认</button>
    <button class="btn btn-primary"  class="close" data-dismiss="modal" onclick="closeModal('online')">取消</button>
    </div>
    </div>
	</div>
</div>

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
				<form id="reasonValidate" >
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