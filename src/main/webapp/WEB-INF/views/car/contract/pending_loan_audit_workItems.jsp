<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html style="overflow-x:auto;overflow-y:auto;">
<head>
<title>车借待款项确认列表</title>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context}/js/enter_select.js"></script>
<script type="text/javascript" src="${context}/js/car/contract/carPendingAudit.js" ></script>
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
 if("${message}"!=""){
	 art.dialog.alert("${message}");
 }
 var contractNo='<c:forEach items="${fns:getDictList('jk_car_contract_version')}" var="dict" varStatus="status"><c:if test="${status.last}">${dict.label }</c:if></c:forEach>';
	$(".contractNo").html(contractNo);
});
function page(n, s) {
	if (n)
		$("#pageNo").val(n);
	if (s)
		$("#pageSize").val(s);
	$("#inputForm").attr("action", "${ctx}/car/carLoanWorkItems/fetchTaskItems/statisticsCommissioner");
	$("#inputForm").submit();
	return false;
} 

// 点击批量P2P标识处理
function jXP2Pflag(channelFlag,message) {
	var checkVal = "";
	var flagSet = "false";
	if ($(":input[name='checks']:checked").length == 0) {
		art.dialog.alert("请选择要进行操作的数据!");
		return;
	} else {
		art.dialog.confirm(message,function (){
			$(":input[name='checks']:checked").each(function(i) {
					if (0 == i) {
						checkVal = $(this).val();
					} else {
						checkVal += ("," + $(this).val());
					}
			});
			$.ajax({
				type : 'post',
				url : ctx + '/car/carContract/pendingAudit/updateCarP2PStatu',
				data : {
					'checkVal' : checkVal, //传递流程需要的参数
					'handleFlag' : channelFlag
				//单子标识
				},
				cache : false,
				dataType : 'json',
				async : false,
				success : function(result) {
						art.dialog.alert("操作成功",function (){
							windowLocationHref(ctx + "/car/carLoanWorkItems/fetchTaskItems/statisticsCommissioner");
						});
				},
				error : function() {
					art.dialog.alert('请求异常！');
				}
			});

		});
	}
}

</script>
<style type="text/css">
.trRed {
	color:red;
}
</style>

</head>
<body>
<sys:message content="${message}"/>
 <div class="control-group">
 <form:form id="inputForm" modelAttribute="carLoanFlowQueryParam" action="${ctx} /car/carLoanWorkItems/fetchTaskItems/statisticsCommissioner" method="post" class="form-horizontal">
     <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
     <input name="menuId" type="hidden" value="${param.menuId}" />
     <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
     <table id = "searchTable" class="table1 " cellpadding="0" cellspacing="0" border="0"width="100%">
         <tr>
             <td><label class="lab">客户姓名：</label>
             	<form:input id="customerName" path="customerName" class="input_text178"/>
             </td>
             <td>			
				<label class="lab">门店：</label> 
				<form:input id="txtStore"
						path="storeName" maxlength="20"
						class="txt date input_text178" value="${secret.store }" />
				<i id="selectStoresBtn"
				class="icon-search" style="cursor: pointer;"></i>
		     </td>
             <td><label class="lab">产品类型：</label>
             	<form:select id="productType" path="auditBorrowProductCode" class="select180">
                   <option value="">请选择</option>
                   <form:options items="${fncjk:getPrd('products_type_car_credit')}" itemLabel="productName" itemValue="productCode" htmlEscape="false"/>
		         </form:select>
             </td>
         </tr>
         <tr>
           	<td><label class="lab">身份证号：</label>
           	<form:input path="certNum" class="input_text178"/></td>
            <td><label class="lab">合同编号：</label>
            <form:input path="contractCode"  class="input_text178"/></td>
            <td><label class="lab">借款期限：</label>
	           		<form:select id="loanMonths" path="auditLoanMonths" class="select180">
	                	<option value="">请选择</option>
						<c:forEach items="${fncjk:getPrdMonthsByType('products_type_car_credit')}" var="product_type">
								<form:option value="${product_type.key}">${product_type.value}</form:option>
							</c:forEach>
					</form:select>
				</td>
         </tr>
		<tr id="T1" style="display:none">
				  <td><label class="lab">签约日期：</label>
            <input id="contractFactDayStart" name="contractFactDayStart"  class="input_text70 Wdate" 
            		onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" style="cursor: pointer" 
            		value="<fmt:formatDate value='${carLoanFlowQueryParam.contractFactDayStart}' type='date' pattern="yyyy-MM-dd" />" />-<input id="contractFactDayEnd" name="contractFactDayEnd"  class="input_text70 Wdate" 
            		onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" style="cursor: pointer" 
            		value="<fmt:formatDate value='${carLoanFlowQueryParam.contractFactDayEnd}' type='date' pattern="yyyy-MM-dd" />" /></td>
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
     <div  class="tright pr30 pb5">
     <span style="position: relative;left: 100px; top: 43px;color:red;font-size:14px;" >当前合同版本号：<span class="contractNo"></span></span>
     	<input type="hidden" id="hdStore">
        <input type="button" class="btn btn-primary" id="searchBtn" value="搜索"></input>
        <input type="button" class="btn btn-primary" id="clearBtn" value="清除"></input>
     <div class="xiala" style="text-align:center;">
	  <img src="${context}/static/images/up.png" id="showMore"></img>
    </div>
 </div>
 </div>
</form:form> 
 
	<p class="mb5">
		
		<button class="btn btn-small jkcj_pending_loan_audit_workItems_export" id="exportWatch" >导出打款表</button>
		<button class="btn btn-small jkcj_pending_loan_audit_workItems_p2p" onclick="jXP2Pflag('2','是否确定批量标识P2P？')" value="P2P">批量标识P2P</button>
		<button class="btn btn-small jkcj_pending_loan_audit_workItems_p2pCancel" onclick="jXP2Pflag('3','是否确定批量取消P2P？')" value="P2P">批量取消P2P</button>
		<button id="offLineShang" role="button" class="btn btn-small jkcj_pending_loan_audit_workItems_uploadBack" data-target="#uploadAuditedModal" data-toggle="modal">上传回执</button>
    </p>
    
    <sys:columnCtrl pageToken="carPendLoanAuList"></sys:columnCtrl>
    <div class="box5" style="position:relative;width:100%;overflow:hidden;height:60%;margin-bottom: 20px">
   <table id="tableList" class="table  table-bordered table-condensed table-hover " style="margin-bottom:100px;">
   <thead>
      <tr>
      	<th><input type="checkbox" id="checkAll"/></th>
        <th>序号</th>
		<th>合同编号</th>
		<th>客户姓名</th>
		<th>共借人姓名</th>
		<th>门店名称</th>
		<th>管辖省份</th>
		<th>合同金额</th>
		<th>实放金额</th>
		<th>借款期限</th>
		<th>产品类型</th>
		<th>银行信息</th>
		<th>银行账号</th>
		<th>车牌号码</th>
		<th>签约日期</th>
		<th>合同版本号</th>
		<th>借款状态</th>
		<th>退回原因</th>
		<th>是否电销</th>
		<th>渠道</th>
		<th>操作</th>
      </tr>
      </thead>
      	<c:if test="${ itemList!=null && fn:length(itemList)>0}">
       	<c:forEach items="${itemList}" var="item" varStatus="status"> 
		<tr <c:if test="${fn:contains(item.orderField,'0,') }">class='trRed'</c:if>>
          <td><input type="checkbox" name="checks" class="checks" value="${item.applyId}"  /></td>
          <td>${status.count}</td>
          <td>${item.contractCode}</td>
          <td>${item.customerName}</td>
          <td>${item.coborrowerName}</td>
          <td>${item.storeName}</td>
          <td>${item.addrProvince}</td>
          <td><fmt:formatNumber value="${item.contractAmount == null ? 0 : item.contractAmount }" pattern="#,##0.00" /></td>
          <td><fmt:formatNumber value="${item.grantAmount == null ? 0 : item.grantAmount }" pattern="#,##0.00" /></td>
          <td>${item.auditLoanMonths}</td>
          <td>${item.auditBorrowProductName}</td>
          <td>${item.cardBank}</td>
          <td>${item.bankCardNo}</td>
          <td>${item.plateNumbers}</td>
          <td><fmt:formatDate value='${item.contractFactDay}' pattern="yyyy-MM-dd"/></td>
          <td>${item.contractVersion}</td>
          <td>${item.dictStatus}</td>
          <td>${item.grantBackResultCode}</td>
          <td>${item.loanIsPhone}</td>
          <td>${item.loanFlag}</td>
          <td>
          	<c:if test="${item.canHandle eq '0' }">
              <input type="button" value="确认" name="manage" class="btn_edit jkcj_pending_loan_audit_workItems_deal" applyId="${item.applyId}" ></input>
            </c:if>
              <input type="button" value="查看" onclick="showCarLoanInfo('${item.loanCode}')" class="btn_edit jkcj_pending_loan_audit_workItems_view"></input>
              <input type="button" value="历史" onclick="showCarLoanHis('${item.loanCode}')" class="btn_edit jkcj_pending_loan_audit_workItems_history"></input>
              <input type="button" data-target="#back_div" data-toggle="modal" name="back" value="退回" applyId="${item.applyId}" class="btn_edit jkcj_pending_loan_audit_workItems_back" ></input>
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
	<!--退回弹框  -->
   <form method="post" id="loanApplyForm" >
		<input type="hidden" id="applyId"  name="applyId"></input>
		<input name="menuId" type="hidden" value="${param.menuId}" />
    <div  id="back_div" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabe1l" aria-hidden="true" data-url="data">
	<div class="modal-dialog" role="document">
	<div class="modal-content">
	<div class="modal-header">
   	<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	<div class="l">
         		 <h4 class="pop_title">退回</h4>
       	 	</div>
 	</div>
 	<div class="modal-body">
  	  <table class="table4" cellpadding="0" cellspacing="0" border="0" width="100%" id="tpTable">
  	  <tr>
    	<td><lable class="lab"><span style="color: red;">*</span>退回原因：</lable>
    		<select id="backNode" name="dictBackMestype" style="width:150px;" class="required">
    		  <option value="">请选择退回原因</option>
				<c:forEach items="${fns:getDictLists('6,7,9', 'jk_chk_back_reason')}" var="item">
					<option value="${item.value}">${item.label}</option>
				</c:forEach>
 		 	</select>
 		 </td>
   	 </tr>
	 <tr>
	<td style="display:none" id="ta" ><lable class="lab">其它退回原因：</lable>
	<textarea rows="" cols="" class="textarea_refuse" id="remark" name="remark"></textarea>
	</p>
	</td>
	</tr>
      	  </table>
 	</div>
 	<div class="modal-footer">
 	<button id="backSure" class="btn btn-primary" aria-hidden="true" >确定</button>
 	<button class="btn btn-primary" data-dismiss="modal" aria-hidden="true">取消</button>
 	</div>
	</div>
	</div>
	</div>
	</form>
	<!--上传回执 -->
	<div class="modal fade" id="uploadAuditedModal" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"> 
			<div class="modal-dialog" role="document">
				    <div class="modal-content">
				    <form id="uploadAuditForm" role="form" enctype= "multipart/form-data" method="post"  >
					<div class="modal-header">
					    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title" id="myModalLabel">上传回执结果</h4>
					</div>
					<div class="modal-body">
                      <input type='file' name="file" accept="application/msexcel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" id="fileid">
                    </div>
					</form>
					<div class="modal-footer">
       					 <button class="btn btn-primary"  class="close" data-dismiss="modal" id="sureBtn">确认</button>
      					 <button class="btn btn-primary"  class="close" data-dismiss="modal" >取消</button>
   					</div>
			</div>
			</div>
		
	</div>
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