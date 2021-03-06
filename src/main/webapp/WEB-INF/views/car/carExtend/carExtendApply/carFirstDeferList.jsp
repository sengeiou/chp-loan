<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<!-- 车借展期列表 -->
<head>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context}/js/car/carExtend/carExtendCommon.js"></script>
<script type="text/javascript">
  $(document).ready(function(){
	 $('#searchBtn').bind('click',function(){
		 page(1,${page.pageSize});
	 });
	 $('#clearBtn').bind('click',function(){
		 $(':input','#searchTable')
		  .not(':button, :reset')
		  .val('')
		  .removeAttr('checked')
		  .removeAttr('selected');
		 $("select").trigger("change");
		 
	 });
	 $('#showMore').bind('click',function(){
		show(); 
		 
	 });
	 
	$("#testCantractBtn").click(function(){
		var contractValue = $("#contractCodes").val();
		if (contractValue.replace(/(^\s*)|(\s*$)/g, "")=="") {
			alert("请输入合同编号！");
		} else if (!validateContractCode(contractValue)) {
			alert("请输入正确格式的合同编号！");
		} else {
			$.ajax({
	    		url : ctx + "/car/carExtendHistory/checkExtendHistoryEntry",
	    		type :'post',
	    		data : {contractCode: contractValue},
	    		success : function(data) {
	    			if (data == "true") {
	    				window.location = ctx + "/car/carExtendHistory/queryHistoryExtend?contractCode=" + contractValue;
	    			} else {
	    				art.dialog.alert("此合同编号下已存在正在进行的数据！");
	    			}
	    		},
	    		error:function(){
	    			art.dialog.alert('服务器异常！');
	    			return false;
	    		}
	    	});
		}
	});
	 
	//选择门店
	 loan.getstorelsit("txtStore","hdStore");
 });
  
  //分页查询js
  function page(n, s) {
		if(n){
			$("#pageNo").val(n);
		}
		if(s){
			$("#pageSize").val(s);
		}
		$("#inputForm").attr("action", "${ctx}/car/carContract/firstDefer/selectDeferList");
		$("#inputForm").submit();
		return false;
	}
</script>
<script type="text/javascript">
function queryShow(){
	$('#modal_Query').modal('show');
}
</script>
<title>车借展期列表</title>
<meta name="decorator" content="default" />
</head>
<body>
	<ul class="nav nav-tabs">
		<li ><a href="${ctx}/car/carApplyTask/fetchTaskItems">申请待办列表</a></li>
		<li class="active"><a href="${ctx}/car/carContract/firstDefer/selectDeferList">展期待办列表</a></li>
	</ul>
    <div class="control-group">
    <form:form id="inputForm" modelAttribute="carFirstDeferEx" method="post" class="form-horizontal">
    	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    	<input name="menuId" type="hidden" value="${param.menuId}" />
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
        <table id="searchTable" class="table1 " cellpadding="0" cellspacing="0" border="0"width="100%">
            <tr>
                <td><label class="lab">客户姓名：</label><form:input path="customerName"   class="input_text178" />
                 </td>
                <td><label class="lab">产品类型：</label><form:select id="productType" path="productType" class="select180">
                   <option value="">请选择</option>
					<c:forEach items="${fncjk:getPrd('products_type_car_credit')}" var="pro">
		                 <form:option value="${pro.productCode}">${pro.productName}</form:option>
		            </c:forEach>  
		         </form:select>
                </td>
                <td>
				    <label class="lab">身份证号：</label><form:input  path="customerCertNum"  class="input_text178"/>
				 </td>
            </tr>
            <tr>
                <td>
                  <label class="lab">团队经理：</label><form:input type="text" id="loanTeamEmpName" path="consTeamManagerName" value="${param.loanTeamEmpName}" class="input_text178"/>
                </td>
                <td><label class="lab">客户经理：</label><form:input type="text" id="loanApplySell" path="managerName" value="${param.loanApplySell}" class="input_text178"/>
                </td>
                <td><label class="lab">门店名称：</label> 
					<input id="txtStore" name="storeName"
						type="text" maxlength="20" class="txt date input_text178"
						value="${secret.store }" readonly="true"/> 
					<i id="selectStoresBtn"
					class="icon-search" style="cursor: pointer;"></i>
               </td>
            </tr>
			<tr id="T1" style="display:none">
					<td>
                  <label class="lab">是否电销：</label>
                  	<c:forEach items="${fns:getDictList('jk_yes_or_no')}" var="item">
                  		<form:radiobutton path="telesalesFlag" value="${item.value}"></form:radiobutton>${item.label}
               	  </c:forEach>
                </td>
                    <td><label class="lab">借款状态：</label><form:select id="cardType" path="dictLoanStatus" class="select180">
                    <option value="">请选择</option>
					<form:options items="${fns:getDictList('jk_car_loan_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
					</td>
					<td><label class="lab">渠道：</label><form:select id="cardType" path="loanFlag" class="select180">
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
        <div  class="tright pr30 pb5">
					<input type="hidden" id="hdStore">
                    <input type="button" class="btn btn-primary" id="searchBtn" value="搜索"></input>
                    <input type="button" class="btn btn-primary" id="clearBtn" value="清除"></input>
        <div class="xiala" style="text-align:center;">
				  <img src="${context}/static/images/up.png" id="showMore"></img>
       </div>
    </div>
    </div>
   </form:form> 
   <p class="mb5" >
	 <input type="button" class="btn btn-small" onclick="queryShow()" value="历史展期添加"></input>
   </p>
   <div class="box5" style="position:relative;width:100%;overflow:hidden;height:60%;margin-bottom: 20px"> 
      <table id="tableList" class="table  table-bordered table-condensed table-hover " style="margin-bottom:100px;">
      <thead>
         <tr>
          <th>序号</th>
          <th>合同编号</th>
          <th>客户姓名</th>
          <th>申请金额（元）</th>
          <th>借款限期（天）</th>
          <th>产品类型</th>
          <th>评估金额</th>
         <!--  <th>批借金额</th> -->
          <th>申请日期</th>
          <th>团队经理</th>
          <th>客户经理</th>
          <th>面审人员</th>
          <th>借款状态</th>
          <th>合同到期提醒</th>
          <th>车牌号码</th>
          <th>是否电销</th>
          <th>已展期次数</th>
          <th>渠道</th>
          <th>操作</th>
         </tr>
         </thead>
         	<c:if test="${ page.list!=null && fn:length(page.list)>0}">
         	<c:set var="index" value="0"/>
          	<c:forEach items="${page.list}" var="item"> 
           <tr>
             <td><c:set var="index" value="${index+1}"/>${index}</td>
             <td>${item.contractCode}</td>
             <td>${item.customerName}</td>
             <td><fmt:formatNumber value="${item.loanApplyAmount == null ? 0 : item.loanApplyAmount }" pattern="#,##0.00" /></td>
             <td>${item.contractMonths}</td>
             <td>${item.productType}</td>
             <td><fmt:formatNumber value="${item.storeAssessAmount == null ? 0 : item.storeAssessAmount }" pattern="#,##0.00" /></td>
             <%-- <td><fmt:formatNumber value="${item.auditAmount == null ? 0 : item.auditAmount }" pattern="#,##0.00" /></td> --%>
             <td><fmt:formatDate value='${item.loanApplyTime}' pattern="yyyy-MM-dd"/></td>
             <td>${item.consTeamManagerName}</td>
             <td>${item.managerName}</td>
             <td>${item.reviewMeet}</td>
             <td>${item.dictLoanStatus}</td>
             <td><fmt:formatDate value="${item.contractEndDay}" pattern="yyyy-MM-dd"/></td>
             <td>${item.plateNumbers}</td>
             <td>${item.telesalesFlag}</td>
             <td>${item.extendNum}</td>
             <td>${item.loanFlag}</td>
             <td>
            <button class="btn_edit" onclick="window.location='${ctx}/bpm/flowController/openLaunchForm?flowCode=loanExtendFlow&applyId=${item.applyId}'">办理展期</button>
             </td>           
         </tr>  
         </c:forEach>       
         </c:if>
         <c:if test="${ page.list==null || fn:length(page.list)==0}">
           <tr><td colspan="18" style="text-align:center;">没有待办数据</td></tr>
         </c:if>
      </table>
    </div>
 <div class="pagination">${page}</div>
 <!-- 查询 -->
<div id="modal_Query" class="modal fade" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabe1l" aria-hidden="true" data-url="data">
		<div class="modal-dialog role" document="">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
						<h4>查询合同</h4>
				</div>
				<form id="contractForm"  method="post" class="form-horizontal">
				<br/>
				<table>
					<tr>
						<td><lable class="lab"><span style="color: #ff0000">*</span>请输入合同编号：</lable><input type="text" style="width:300px;" name="contractCode" id="contractCodes" class="required input_text178"/></td>
					</tr>
				</table>
				<br/>
				<div class="modal-footer">
						
				<input type="button" class="btn btn-primary" id="testCantractBtn" value="确定">
				 <button type="button" class="btn btn-primary" data-dismiss="modal">取消</button>
				</div>
				</form>
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