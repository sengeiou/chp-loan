<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>车借待审核费率列表</title>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context}/js/enter_select.js"></script>
<script type="text/javascript">
  $(document).ready(function(){
	  
	//手机号码验证
	jQuery.validator.addMethod("isStoreCode", function(value, element) {
	    var length = value.length;
	    var storeCode = /^\d{2}$/;
	    return this.optional(element) || (length == 2 && storeCode.test(value));
	}, "门店编号有误");
	 $('#searchBtn').bind('click',function(){
		// 终审日期验证触发事件,点击搜索进行验证
		var startDate = $("#finalCheckTimeStart").val();
		var endDate = $("#finalCheckTimeEnd").val();
		if(endDate!=""&& endDate!=null && startDate!="" &&startDate!=null){
			var arrStart = startDate.split("-");
			var arrEnd = endDate.split("-");
			var sd=new Date(arrStart[0],arrStart[1],arrStart[2]);  
			var ed=new Date(arrEnd[0],arrEnd[1],arrEnd[2]);  
			if(sd.getTime()>ed.getTime()){   
				art.dialog.alert("终审开始日期不能大于终审结束日期!",function(){
					$("#finalCheckTimeStart").val("");
					$("#finalCheckTimeEnd").val("");
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
		 $("#hdStore").val('');
		 $("select").trigger("change");
		 
	 });
	 $('#showMore').bind('click',function(){
		show(); 
	 });
	 
	 //选择门店
	 loan.getstorelsit("txtStore","hdStore");
	 
	$("#inputSure").click(function() {
		var flag = $("#storeFormId").validate({
			onclick: true,
			rules : {
				storeCode : {
					isStoreCode : true
				}
			},
			messages : {
				storeCode : {
					isStoreCode : "请输入2位数字的门店编号"
				}
			}
		}).form();
		if (flag) {
			var carLoanCode = $("#storeCode").val();
			var model = eval("(" + $("#workParam").val() + ")");
			$.ajax({
				type : "POST",
				data : {
					applyId : model.applyId,
					carLoanCode : carLoanCode
				},
				url : ctx + "/car/carContract/checkRate/updateOrgCarStoreCode",
				success : function(data) {
					if (data == "false") {
						alert("更新车借门店编号失败，请联系管理员");
						return false;
					} else if (data == "true") {
						$("#input_div").modal("hide");
						windowLocationHref(ctx + '/bpm/flowController/openForm?applyId=' + model.applyId + '&wobNum=' + model.wobNum + '&dealType=0&token=' + model.token);
					} else if (data == "geneFalse") {
						alert("生成合同编号失败，请联系管理员！");
						return false;
					} else if (data == "carExist") {
						alert("此城市下已存在所填车借门店编号，请录入其他车借门店编号！");
						return false;
					}
				}
			});
		}
	});
	var contractNo='<c:forEach items="${fns:getDictList('jk_car_contract_version')}" var="dict" varStatus="status"><c:if test="${status.last}">${dict.label }</c:if></c:forEach>';
	$(".contractNo").html(contractNo);
	
});
function checkExistStoreCode(applyId, wobNum, token) {
	$.ajax({
		type : "POST",
		data : {
			applyId : applyId
		},
		url : ctx + "/car/carContract/checkRate/checkStoreCodeIsEmpty",
		success : function(data) {
			if (data == "false") {
				windowLocationHref(ctx + '/bpm/flowController/openForm?applyId=' + applyId + '&wobNum=' + wobNum + '&dealType=0&token=' + token);
			} else if (data == "true") {
				$("#input_div").modal("show");
				var param = {
					applyId : applyId,
					wobNum : wobNum,
					token : token
				};
				$("#workParam").val(JSON.stringify(param));
			} else if (data == "geneFalse") {
				alert("生成合同编号失败，请联系管理员！");
				return false;
			} else if (data == "pCEmpty") {
				alert("门店机构所属省市有误，请联系管理员！");
				return false;
			} else if (data == "cCodeEmpty") {
				alert("门店机构所属城市不存在或车借城市编号为空，请联系管理员！");
				return false;
			}
		}
	});
}
function page(n, s) {
	if (n)
		$("#pageNo").val(n);
	if (s)
		$("#pageSize").val(s);
	$("#inputForm").attr("action", "${ctx}/car/carLoanWorkItems/fetchTaskItems/rateCheck");
	$("#inputForm").submit();
	return false;
} 
</script>
</head>
<body>
 <div class="control-group">
 <form:form id="inputForm" modelAttribute="carLoanFlowQueryParam" action="${ctx}/car/carLoanWorkItems/fetchTaskItems/rateCheck" method="post" class="form-horizontal">
     <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
     <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
     <table id = "searchTable" class="table1 " cellpadding="0" cellspacing="0" border="0"width="100%">
         <tr>
             <td><label class="lab">客户姓名：</label>
             	<form:input id="customerName" path="customerName" class="input_text178"/>
             </td>
             <td>			
				<label class="lab">门店：</label> 
				<form:input id="txtStore" path="storeName" class="txt date input_text178" readonly="true"/> 
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
            <td><label class="lab">合同编号：</label>
            <form:input path="contractCode"  class="input_text178"/></td>
            <td><label class="lab">终审日期：</label>
            <input id="finalCheckTimeStart" name="finalCheckTimeStart"  class="input_text70 Wdate" 
            		onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" style="cursor: pointer" 
            		value="<fmt:formatDate value='${carLoanFlowQueryParam.finalCheckTimeStart}' type='date' pattern="yyyy-MM-dd" />" />-<input id="finalCheckTimeEnd" name="finalCheckTimeEnd"  class="input_text70 Wdate" 
            		onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" style="cursor: pointer" 
            		value="<fmt:formatDate value='${carLoanFlowQueryParam.finalCheckTimeEnd}' type='date' pattern="yyyy-MM-dd" />" />
            </td>
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
				<input type="hidden" id="hdStore">
     			<span style="position: relative;right: 0px; top: 43px;color:red;font-size:14px;" >当前合同版本号：<span class="contractNo"></span></span>
                <input type="button" class="btn btn-primary" id="searchBtn" value="搜索"></input>
                <input type="button" class="btn btn-primary" id="clearBtn" value="清除"></input>
     <div class="xiala" style="text-align:center;">
	  <img src="${context}/static/images/up.png" id="showMore"></img>
    </div>
 </div>
</form:form>
 </div>
 <sys:columnCtrl pageToken="carCheckRateList"></sys:columnCtrl>
<div class="box5" style="position:relative;width:100%;overflow:hidden;height:60%;margin-bottom: 20px"> 
 	
   <table id="tableList" class="table  table-bordered table-condensed table-hover " style="margin-bottom:100px;">
   <thead>
      <tr>
        <th>序号</th>
		<th>合同编号</th>
		<th>客户姓名</th>
		<th>共借人姓名</th>
		<th>门店名称</th>
		<th>管辖省份</th>
		<th>审批金额</th>
		<th>借款期限</th>
		<th>产品类型</th>
		<th>终审日期</th>
		<th>借款状态</th>
		<th>是否电销</th>
		<th>合同版本号</th>
		<th>渠道</th>
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
          <td><fmt:formatNumber value="${item.auditAmount}" type="number" pattern="0.00"/></td>
          <td>${item.auditLoanMonths}</td>
          <td>${item.auditBorrowProductName}</td>
          <td><fmt:formatDate value='${item.auditTime}' pattern="yyyy-MM-dd"/></td>
          <td>${item.dictStatus}</td>
          <td>${item.loanIsPhone}</td>
          <td >${item.contractVersion}</td>
          <td>${item.loanFlag}</td>
          <td>
              <input type="button" value="办理" class="btn_edit jkcj_lendCarPendingReview_Audit_rates" onclick="checkExistStoreCode('${item.applyId}','${item.wobNum}','${item.token}')"></input>&nbsp;
              <button class="btn_edit jkcj_lendCarPendingReview_history" value="${item.applyId}"  onclick="javascript:showCarLoanHisByApplyId(this.value)">历史</button>
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
	<!--门店编号 输入 弹框  -->
	<div id="input_div" class="modal fade" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabe1l" aria-hidden="true" data-url="data">
		<div class="modal-dialog role="document"">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
						<h4 class="modal-title">输入门店编号</h4>
				</div>
				<div class="modal-body">
					<form id="storeFormId">
					<table class="table4" cellpadding="0" cellspacing="0"
						border="0" width="100%" id="tpTable">
						<tr>
							<td><span style="color: red;">*</span>门店编号：
								<input type="text" name="storeCode" id="storeCode" maxlength="2" class="required">
								<input type="hidden" name="workParam" id="workParam"></td>
						</tr>
					</table>
					</form>
				</div>
				<div class="modal-footer">
					<button id="inputSure" class="btn btn-primary">确定</button>
					<button class="btn btn-primary" data-dismiss="modal" aria-hidden="true">取消</button>
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