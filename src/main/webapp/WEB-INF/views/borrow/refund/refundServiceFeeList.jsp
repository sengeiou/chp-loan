<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>放款失败催收服务费退回列表</title>
<script src="${context}/js/refund/refundServiceFeeList.js" type="text/javascript"></script>
<script type="text/javascript" language="javascript"
	src='${context}/js/common.js'></script>
<script>
function page(n, s) {
	if (n)
		$("#pageNo").val(n);
	if (s)
		$("#pageSize").val(s);
	$("#urgeForm").attr("action", "${ctx}/borrow/refund/longRefund/refundServiceFeeList");
	$("#urgeForm").submit();
	return false;
}
//省市级联
$(document).ready(
		function() {
			loan.initCity("addrProvice", "addrCity",null);
			areaselect.initCity("addrProvice", "addrCity",null, $("#addrProvice")
							.attr("value"), $("#addrCity")
							.attr("value"));
		});
</script>
<meta name="decorator" content="default"/>
</head>
<body>
	<div class="control-group">
       <form:form  method="post"  id="urgeForm" modelAttribute="GrantUrgeBackEx" >
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
         <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" /> 
		 <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
		 <input id="urgeId" type="hidden">
            <tr>
               <td><label class="lab">客户姓名：</label><input type="text" class="input_text178" id="customerName" name="customerName" value="${refundServiceFee.customerName}"></input></td>
               <td><label class="lab">合同编号：</label><input type="text" class="input_text178" id="contractCode" name="contractCode" value="${refundServiceFee.contractCode}"></input></td>
               <td><label class="lab">证件号码：</label><input type="text" class="input_text178" id="customerCertNum" name="customerCertNum" value="${refundServiceFee.customerCertNum}"></input></td>
            </tr>
            <tr>
                <td>
                <label class="lab">退款状态：</label><select class="select180" id="appStatus" name="appStatus">
                <option value="">请选择</option>
                <option value="-1" <c:if test="${'-1' eq refundServiceFee.appStatus}">selected</c:if>>待退款</option>
			    <c:forEach items="${fns:getDictList('jk_app_status')}" var="status">
								<option value="${status.value}" <c:if test="${status.value eq refundServiceFee.appStatus}">selected</c:if>>${status.label}</option>
				 </c:forEach>
			    </select>
                </td>
				<td></td>
                <td>
				</td>
            </tr>
        </table>
        	
        <div class="tright pr30 pb5">
              <input class="btn btn-primary" type="submit" value="搜索"></input>
              <button class="btn btn-primary" id="clearBtn">清除</button>
        <div style="float: left; margin-left: 45%; padding-top: 10px">
	      <img src="../../../../static/images/up.png" id="showMore"></img>
	     </div>
		</div>
	</form:form>
	   </div>
		<p class="mb5">
		</p>
		<div class="box5" style="overflow: auto; width: 100%;">
        <table id="tableList" class="table  table-bordered table-condensed table-hover ">
        <thead>
         <tr>
            <th><input type="checkbox" id="checkAll"/></th>
            <th>合同编号</th>
            <th>门店名称</th>
            <th>客户姓名</th>
            <th>证件号码</th>
            <th>借款类型</th>
            <th>借款产品</th>
            <th>合同金额</th>
			<th>放款金额</th>
			<th>催收服务费金额</th>
			<th>已催收服务费金额</th>
			<th>催收渠道</th>
			<th>催收成功日期</th>
			<th>批复期限</th>
            <th>退款状态</th>
			<th>放款日期</th>
			<th>放款失败日期</th>
			<th>退款日期</th>
            <th>渠道</th>
            
            <th>操作</th>
        </tr>
		</thead>
        <tbody>
         <c:if test="${grantUrgeList!=null && fn:length(grantUrgeList.list)>0}">
         <c:set var="index" value="0"/>
          <c:forEach items="${grantUrgeList.list}" var="item">
          <c:set var="index" value="${index+1}" />
             <tr>
             <td>
             <input type="checkbox" name="checkBoxItem" urgeId='${item.urgeId}'/>
             </td>
             <td>${item.contractCode}</td>
             <td>${item.name}</td>
             <td>${item.customerName}</td>
             <td>${item.customerCertNum}</td>
             <td>${item.classType}</td>
             <td>${item.productName}</td>
             <td><fmt:formatNumber value='${item.contractAmount}' pattern="#,#00.00"/></td>
             <td><fmt:formatNumber value='${item.grantAmount}' pattern="#,#00.00"/></td>
             <td><fmt:formatNumber value='${item.urgeMoeny}' pattern="#,#00.00"/></td>
             <td><fmt:formatNumber value='${item.returnAmount}' pattern="#,#00.00"/></td>
             <td>${item.dictDealType}</td>
             <td>${item.urgeDecuteDate}</td>
             <td>${item.contractMonths}</td>
			 <td>${item.appStatusTmp}<c:if test="${item.appStatus==null }">待退款</c:if></td>
             <td><fmt:formatDate value="${item.lendingTime}"
							type="date" /></td>
             <td><fmt:formatDate value="${item.checkTime}"
							type="date" /></td>
			 <td>${item.backTime}</td>     
             <td>${item.loanFlag}</td>  
             <td>
             <c:if test="${item.appStatus==null or item.appStatus=='6'}">
             <button class="btn_edit" onclick="doOpenRefund('${item.contractCode}','${item.refundId}','${item.urgeId}','${item.id}');" id="backBtn">退款申请</button>
             </c:if>
             </td>
         </tr> 
         </c:forEach>  
         </c:if>
         <c:if test="${grantUrgeList==null || fn:length(grantUrgeList.list)==0}">
           <tr><td colspan="18" style="text-align:center;">没有待办数据</td></tr>
         </c:if>
     </tbody>
    </table>
	</div>
     <div class="pagination">${grantUrgeList}</div> 
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