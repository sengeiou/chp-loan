<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>车借展期申请已办列表</title>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context}/js/enter_select.js"></script>
<script type="text/javascript" src="${context}/js/car/carExtend/carExtendDetailsCheck.js"></script>
<script type="text/javascript">
  $(document).ready(function(){
	 $('#searchBtn').bind('click',function(){
			// 申请日期验证触发事件,点击搜索进行验证
			var startDate = $("#planArrivalTimeStart").val();
			var endDate = $("#planArrivalTimeend").val();
			if(endDate!=""&& endDate!=null && startDate!="" &&startDate!=null){
				var arrStart = startDate.split("-");
				var arrEnd = endDate.split("-");
				var sd=new Date(arrStart[0],arrStart[1],arrStart[2]);  
				var ed=new Date(arrEnd[0],arrEnd[1],arrEnd[2]);  
				if(sd.getTime()>ed.getTime()){   
					art.dialog.alert("申请开始日期不能小于申请结束日期!",function(){
						$("#planArrivalTimeStart").val("");
						$("#planArrivalTimeend").val("");
					});
					return false;     
				} else {
					 page(1,${page.pageSize});
				}
		 }else{
			 page(1,${page.pageSize});
			}
	 });
	 $('#clearBtn').bind('click',function(){
		 $(':input','#inputForm')
		  .not(':button, :reset,:hidden')
		  .val('')
		  .removeAttr('checked')
		  .removeAttr('selected');
		 $("#hdStore").val('');
	 });
	 $('#showMore').bind('click',function(){
		show();
	 });
	 //选择门店
	 loan.getstorelsit("txtStore","hdStore");
	//$(".jkcj_extensionApply_check").remove();
 });
  function page(n,s){
      if(n) $("#pageNo").val(n);
      if(s) $("#pageSize").val(s);
      $("#inputForm").attr("action", "${ctx}/common/carHistory/carLoanApplyDoneListExtend");
      $("#inputForm").submit();
      return false;
      
  }

  function applyAgreement(contractCode){
		var url = "${ctx}/common/carHistory/showCutomerMsg?contractCode=" + contractCode;
		$.ajax({
			type : "POST",
			url : url,
			success : function(data){
				$("#actId").val(data.id);
    			$("#applyLoanCustomerName").val(data.loanCustomerName);
    			$("#applyContractCode").val(data.contractCode);
    			$("#applyCustomerCertNum").val(data.customerCertNum);
    			$("#applyCustomerEmail").val(data.customerEmail);
    			art.dialog({
    			    content: document.getElementById("applyAgreement"),
    			    title:'申请电子协议',
    			    fixed: true,
    			    lock:true,
    			    width:450,
    			    height:200,
    			    id: 'confirm',
    			    okVal: '确认',
    			    ok: function () {
    			    	var srcFormParam = $('#applyAgreementMessage');
    					srcFormParam.submit();
    					return false;
    			    },
    			    cancel: true
    			});
			}
		});
  }
  
  	function exportExcelBtn(){
		// 申请日期验证触发事件,点击搜索进行验证
		var startDate = $("#planArrivalTimeStart").val();
		var endDate = $("#planArrivalTimeend").val();
		if(endDate!=""&& endDate!=null && startDate!="" &&startDate!=null){
			var arrStart = startDate.split("-");
			var arrEnd = endDate.split("-");
			var sd=new Date(arrStart[0],arrStart[1],arrStart[2]);  
			var ed=new Date(arrEnd[0],arrEnd[1],arrEnd[2]);  
			if(sd.getTime()>ed.getTime()){   
				art.dialog.alert("申请开始日期不能小于申请结束日期!",function(){
					$("#planArrivalTimeStart").val("");
					$("#planArrivalTimeend").val("");
				});
				return false;     
			} else {
			      $("#pageNo").val(1);
			      $("#pageSize").val(9999);
			      $("#inputForm").attr("action", "${ctx}/common/carHistory/exportzqExcel");
			      $("#inputForm").submit();
			}
	 }else{
		   $("#pageNo").val(1);
	       $("#pageSize").val(9999);
	       $("#inputForm").attr("action", "${ctx}/common/carHistory/exportzqExcel");
	       $("#inputForm").submit();
		}
  	}
</script>
</head>
<body>
	<div class="control-group">
		<form:form id="inputForm" modelAttribute="carLoanStatusHisEx"
			method="post" class="form-horizontal">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input name="menuId" type="hidden" value="${param.menuId}" />
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<table class="table1 " cellpadding="0" cellspacing="0" border="0"
				width="100%">
				<tr>
					<td><label class="lab">客户姓名：</label> <form:input
							id="customerName" path="loanCustomerName" class="input_text178" />
					</td>
					<td><label class="lab">合同编号：</label> <form:input
							id="contractCode" path="contractCode" class="input_text178" />
					</td>
					<td><label class="lab">申请日期：</label>
                	<input id="planArrivalTimeStart" name="applyTimeStrat"  class="input_text70 Wdate" 
            		onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" style="cursor: pointer" 
            		value="<fmt:formatDate value='${carLoanStatusHisEx.applyTimeStrat}' type='date' pattern="yyyy-MM-dd" />" />-
            		<input id="planArrivalTimeend" name="applyTimeEnd"  class="input_text70 Wdate" 
            		onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" style="cursor: pointer" 
            		value="<fmt:formatDate value='${carLoanStatusHisEx.applyTimeEnd}' type='date' pattern="yyyy-MM-dd" />" />
                	</td>
             	</tr>
             	<tr>
					<td><label class="lab">借款类型：</label> <form:select
							id="productType" path="productType" class="select180">
							<option value="">全部</option>
							<c:forEach items="${fncjk:getPrd('products_type_car_credit')}" 
								var="product_type">
								<form:option value="${product_type.productCode}">${product_type.productName}</form:option>
							</c:forEach>
						</form:select></td>
					<td ><label class="lab">是否电销：</label> <form:radiobuttons
							path="telesalesFlag"
							items="${fns:getDictList('jk_telemarketing')}" itemLabel="label"
							itemValue="value" htmlEscape="false" /></td>
					<td ><label class="lab">是否展期：</label> <form:radiobuttons
							path="telesalesFlag"
							items="${fns:getDictList('jk_extend_loan_flag')}" itemLabel="label"
							itemValue="value" htmlEscape="false" /></td>
				</tr>
				<tr id="T1" style="display: none">
		            <td>
		              <label class="lab">系统来源：</label>
	                   <form:select id="dictSourceType" path="dictSourceType" class="select180">
							<option value="">请选择</option>
			                <option value="2">2.0</option> 
			                <option value="3">3.0</option>
						</form:select>
		            </td>
				</tr>
			</table>
			<div class="tright pr30 pb5">
				<input type="button" class="btn btn-primary" id="searchBtn"
					value="搜索"></input> <input type="button" class="btn btn-primary"
					id="clearBtn" value="清除"></input>
				<div class="xiala" style="text-align: center;">
					<img src="${context}/static/images/up.png" id="showMore"></img>
				</div> 
			</div>
	</div>
	</form:form>
	<p class="mb5">
   	<button class="btn btn-small" id="exportExcelBtn" onclick="exportExcelBtn();">导出excel</button>
	 <sys:columnCtrl pageToken="carExtendLoanApplyList"></sys:columnCtrl>
	<div class="box5" style="position:relative;width:100%;overflow:hidden;height:60%;margin-bottom: 20px">
		<table id="tableList" class="table table-bordered table-condensed table-hover"
			style="margin-bottom: 200px;">
			<thead>
				<tr>
					<th>序号</th>
					<th>展期合同编号</th>
					<th>客户姓名</th>
					<th>共借人</th>
					<th>门店名称</th>
					<th>申请金额（元）</th>
					<th>借款期限(天)</th>
					<th>产品类型</th>
					<th>申请日期</th>
					<th>借款状态</th>
					<th>展期申请</th>
					<th>已展期次数</th>
					<th>车牌号码</th>
					<th>批借金额</th>
					<th>降额</th>
					<th>原合同编号</th>
					<th>合同到期提醒</th>
					<th>系统来源</th>
					<th>渠道</th>
					<th>操作</th>
				</tr>
			</thead>
			<c:if test="${ page.list != null && fn:length(page.list)>0}">
				<c:forEach items="${page.list}" var="item" varStatus="status">
					<tr>
						<td>${status.count}</td>
						<td>${item.contractCode}</td>
						<td>${item.loanCustomerName}</td>
						<td>${item.coborrowerName}</td>
						<td>${item.storeName}</td>
						<td>
						<fmt:formatNumber value="${item.loanApplyAmount}"  pattern="#,##0.00"/>
						</td>
						<td>${item.loanMonths}</td>
						<td>${fncjk:getPrdLabelByTypeAndCode('products_type_car_credit',item.productType)}</td>
						<td><fmt:formatDate value='${item.applyTime }' pattern="yyyy-MM-dd"/></td>
						<td>${item.loanStatusCode}</td>
						<td>${item.isExtendsion}</td>
						<td>${item.extendNum }</td>
						<td>${item.plateNumbers }</td>
						<td>${item.contractAmount }</td>
						<td><fmt:formatNumber value="${item.derate }"  pattern="#,##0.00"/></td>
						<td>${item.originalContractCode }</td>
						<td><fmt:formatDate value='${item.contractTipDay }' pattern="yyyy-MM-dd"/></td>
						<td><fmt:formatNumber value="${item.dictSourceType}"  pattern="0.0"/></td>
         			    <td>${item.loanFlag}</td>
						<td>
							<c:if test="${item.signFlag eq '1' and item.agrDictLoanStatus eq '40' and  (item.agreementType eq null or item.agreementType eq '0')}">
								<input class="btn_edit jkcj_extensionApply_apply" type="button" onclick="applyAgreement('${item.contractCode}')" value="申请电子协议"  />
							</c:if>
							<input type="button" value="查看" class="btn_edit jkcj_extensionApply_check" id="butss"  onclick="showExtendLoanInfo('${item.applyId}')"></input>&nbsp;&nbsp;
							<button class="btn_edit jkcj_extensionApply_history" value="${item.applyId}"  onclick="javascript:showCarLoanHisByApplyId(this.value)">历史</button>
						</td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${ page.list==null || fn:length(page.list)==0}">
				<tr>
					<td colspan="18" style="text-align: center;">没有已办数据</td>
				</tr>
			</c:if>
		</table>
	</div>
	<div class="pagination">${page}</div>
	<!-- 申请电子协议 -->
	<div id="applyAgreement" class=" pb5 pt10 pr30 title ohide" style="display:none">
		<form id="applyAgreementMessage" class="validate" action="${ctx}/common/carHistory/updateContractArgType" method="post" enctype="multipart/form-data">
			<input type="hidden" id="actId" name="id"/>
			<input type="hidden" name="dictOperStatus" value="1"/>
			<table class="table table-bordered table-condensed table-hover ">
				<tbody>
					<tr>
						<td><label class="lab">客户姓名：</label></td>
						<td><input type="text" class="input-medium" id="applyLoanCustomerName" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label class="lab">合同编号：</label></td>
						<td><input type="text" class="input-medium" id="applyContractCode" name="contractCode" style="width: 270px;" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label class="lab">身份证号码：</label></td>
						<td>
							<input type="text" class="input-medium isEmailModify" id="applyCustomerCertNum" readonly="readonly"/></td>
						</td>
					</tr>
					<tr>
						<td><label class="lab">邮箱地址：</label></td>
						<td>
							<input type="text" class="input-medium isEmailModify" id="applyCustomerEmail" readonly="readonly"/></td>
						</td>
					</tr>
					<tr>
						<td><label class="lab">紧急程度：</label></td>
						<td>
							<select id="urgentDegree" name="urgentDegree" style="width:100px">
								<option value="">请选择</option>
								<option value="1">紧急</option>
								<option value="2">正常</option>
							</select>
						</td>
					</tr>
					<tr>
						<td><label class="lab">申请理由：</label></td>
						<td>
							<textarea rows="20" cols="10"  id="applyReason" name="applyReason" style="margin: 3px 0px; width: 305px; height: 68px;"></textarea>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
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