<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>车借合同审核列表</title>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context}/js/enter_select.js"></script>
<script type="text/javascript">
  $(document).ready(function(){
	  var contractNo='<c:forEach items="${fns:getDictList('jk_car_contract_version')}" var="dict" varStatus="status"><c:if test="${status.last}">${dict.label }</c:if></c:forEach>';
		 $(".contractNo").html(contractNo);
	//退回
	//传递applyId到form表单中
  	//每次点击退回，将流程信息放到form表单中
	$(":input[name='back']").each(function(){
	$(this).bind('click',function(){
	$('#applyId').val($(this).attr('applyId'));
	//退回是的必填原因的验证
	$("#loanApplyForm").validate({
		onclick: true, 
		rules:{
			backNode: {required:true}
		},
		messages: {
			backNode: {required:"请选择退回节点"}
		}
	});
	});
	});
	//提交退回表单的js方法
  	$("#backSure").bind('click',function(){
		var url = ctx + "/car/carContract/checkRate/contractSendBack";
		$("#loanApplyForm").attr('action',url);
		$("#loanApplyForm").submit();
  	})
	  //搜索，清除js
	 $('#searchBtn').bind('click',function(){
		$('#inputForm').submit(); 
	 });
	 $('#clearBtn').bind('click',function(){
		 $(':input','#searchTable')
		  .not(':button, :reset')
		  .val('')
		  .removeAttr('checked')
		  .removeAttr('selected');
		 $("#hdStore").val('');
		 $("select").trigger("change");
		 
	 });
	 $('#showMore').bind('click',function(){
		show(); 
		 
	 });
	 
	 //选择门店
	 loan.getstorelsit("txtStore","hdStore");
	 
	//点击全选反选选择框
	$("#checkAll").click(function(self){
		if($(this).attr('checked') == 'checked' || $(this).attr('checked')) {
			$(":input[name='checkBoxItem']").each(function(i, e) {
				$(e).attr("checked",'true');
			});
		} else {
			$(":input[name='checkBoxItem']").each(function(i, e) {
				$(e).removeAttr("checked");
			});
		}
	});
	//导出客户信息
	$("#offLineDao").bind('click', function() {
		var idVal = "";// 指的是applyId
		var CarLoanFlowQueryView = $("#inputForm").serialize();
		if ($(":input[name='checkBoxItem']:checked").length == 0) {
			art.dialog.confirm("确认将客户信息全部导出？", function() {
				CarLoanFlowQueryView += "&idVal=" + idVal;
				window.location.href = ctx + "/car/carContract/checkRate/exportData?" + CarLoanFlowQueryView;			  
			});
		} else {
			$(":input[name='checkBoxItem']:checked").each(function() {
				if (idVal != "") {
					idVal += "," + $(this).val();
				} else {
					idVal = $(this).val();
				}
			});
			CarLoanFlowQueryView += "&idVal=" + idVal;
			art.dialog.confirm("确认对选中客户信息导出？", function() {
				window.location.href = ctx + "/car/carContract/checkRate/exportData?" + CarLoanFlowQueryView;
			})
		}
	});
});
  function page(n, s) {
		if (n)
			$("#pageNo").val(n);
		if (s)
			$("#pageSize").val(s);
		$("#inputForm").attr("action", "${ctx}/car/carLoanWorkItems/fetchTaskItems/contractCheck");
		$("#inputForm").submit();
		return false;
}   
  //协议查看
 function  showImage(applyId, contractCode){
		  var url = '${ctx}/car/carContract/checkRate/xieyiLookList?applyId=' + applyId + '&contractCode=' + contractCode;
		  art.dialog.open(url, {
			   id: 'protcl',
			   title: '协议查看',
			   lock:false,
			   width:1500,
			   height:600
			},false); 
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
 <form:form id="inputForm" modelAttribute="carLoanFlowQueryParam" action="${ctx}/car/carLoanWorkItems/fetchTaskItems/contractCheck" method="post" class="form-horizontal">
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
				<input id="txtStore" name="storeName"
					type="text" maxlength="20" class="txt date input_text178"
					value="${secret.store }" readonly="true" /> 
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
	            <td><label class="lab">借款状态：</label>
	           		<form:select id="dictStatus" path="dictStatus" class="select180">
	                	<option value="">请选择</option>
	                	<form:options items="${fns:getDictLists('24,25','jk_car_loan_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</td>
				<td><label class="lab">是否电销：</label>
				 	<form:radiobuttons path="loanIsPhone" items="${fns:getDictList('jk_telemarketing')}" itemLabel="label" itemValue="value" htmlEscape="false"/>			
		         </td>
				<td>
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
		<input type="checkbox" id="checkAll" class="jkcj_contract_check_workItems_All"/> 全选
		<button class="btn btn-small jkcj_lendCarsigningVerify_Export_Excel" id="offLineDao">导出客户信息</button>
    </p>
   <div class="box5" style="position:relative;width:100%;overflow:hidden;height:60%;margin-bottom: 20px"> 
   
   <sys:columnCtrl pageToken="carContractCheckList"></sys:columnCtrl>
   <table id="tableList" class="table  table-bordered table-condensed table-hover " style="margin-bottom:100px;">
   <thead>
      <tr>
        <th></th>
		<th>合同编号</th>
		<th>客户姓名</th>
		<th>共借人姓名</th>
		<th>门店名称</th>
		<th>管辖省份</th>
		<th>合同金额</th>
		<th>实放金额</th>
		<th>借款期限</th>
		<th>产品类型</th>
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
       	<c:forEach items="${itemList}" var="item"> 
		<tr <c:if test="${fn:contains(item.orderField,'0,') }">class='trRed'</c:if>>
          <td><input type="checkbox" name="checkBoxItem" value='${item.applyId}'/></td>
          <td>${item.contractCode}</td>
          <td>${item.customerName}</td>
          <td>${item.coborrowerName}</td>
          <td>${item.storeName}</td>
          <td>${item.addrProvince}</td>
          <td><fmt:formatNumber value='${item.contractAmount}' pattern="0.00"/></td>
          <td><fmt:formatNumber value='${item.grantAmount}' pattern="0.00"/></td>
          <td>${item.auditLoanMonths}</td>
          <td>${item.auditBorrowProductName}</td>
          <td>${item.plateNumbers}</td>
          <td><fmt:formatDate value='${item.contractFactDay}' pattern="yyyy-MM-dd"/></td>
          <td>${item.contractVersion}</td>
          <td>${item.dictStatus}</td>
          <td>${item.contractBackResultCode}</td>
          <td>${item.loanIsPhone}</td>
          <td>${item.loanFlag}</td>
          <td>
          	<c:choose>
          		<c:when test="${item.dictStatus == '待放款确认退回' && item.contractBackResultCode != '其他'}">
          			<input type="button" data-target="#back_div" data-toggle="modal" name="back" value="退回" applyId="${item.applyId}" class="btn_edit" ></input>
          		</c:when>
          		<c:otherwise>
              		<input type="button" value="办理" class="btn_edit jkcj_contract_check_workItems_deal"  onclick="window.location='${ctx}/bpm/flowController/openForm?applyId=${item.applyId}&wobNum=${item.wobNum}&dealType=0&token=${item.token}'"></input>&nbsp;
          		</c:otherwise>
          	</c:choose>
          	<button class="btn_edit jkcj_contract_check_workItems_protocolCheck"  value="${item.applyId}"  onclick="javascript:showImage(this.value, '${item.contractCode}')">协议查看</button>&nbsp;
          	    <button class="btn_edit jkcj_contract_check_workItems_history" value="${item.applyId}"  onclick="javascript:showCarLoanHisByApplyId(this.value)">历史</button>
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
    		<td><lable class="lab"><span style="color: red;">*</span>退回至流程节点：</lable>
    		     		  <select name="backNode" class="required">
					<option value="">请选择退回节点</option>
					<c:forEach
						items="${fns:getDictLists('16,20', 'jk_car_loan_status')}"
						var="item">
						<option value="${item.value}">${item.label}</option>
					</c:forEach>
			</select>
 		 </td>
   	 </tr>
      	  </table>
 	</div>
 	<div class="modal-footer">
 	<button id="backSure" class="btn btn-primary" data-dismiss="modal" aria-hidden="true" >确定</button>
 	<button class="btn btn-primary" data-dismiss="modal" aria-hidden="true">取消</button>
 	</div>
	</div>
	</div>
	</div>
	</form>
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