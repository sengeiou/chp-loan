<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script src="${context}/js/grant/grantUrgeBack.js" type="text/javascript"></script>
<script type="text/javascript" language="javascript"
	src='${context}/js/common.js'></script>
<script type="text/javascript"
	src="${context}/js/pageinit/areaselect.js"></script>
<script>
function page(n, s) {
	if (n)
		$("#pageNo").val(n);
	if (s)
		$("#pageSize").val(s);
	$("#urgeForm").attr("action", "${ctx}/borrow/grant/grantDeductsBack/grantDeductsBackInfo");
	$("#urgeForm").submit();
	return false;
}
//省市级联
$(document).ready(
		function() {
			loan.initCity("addrProvice", "addrCity",null);
			areaselect.initCity("addrProvice", "addrCity",null,
					 $("#cityHid").val(),null);
			loan.getstorelsit("storeOrgName","storeOrgIds");
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
               <td><label class="lab">客户姓名：</label><form:input type="text" class="input_text178" path="customerName"></form:input></td>
               <td><label class="lab">合同编号：</label><form:input type="text" class="input_text178" path="contractCode"></form:input></td>
               <td><label class="lab">证件号码：</label><form:input type="text" class="input_text178" path="customerCertNum"></form:input></td>
            </tr>
            <tr>
                <td>
                <label class="lab">退款状态：</label><form:select class="select180" path="returnStatus"><form:option value="">请选择</form:option>
			    <c:forEach items="${fns:getDictList('jk_fee_return')}" var="card_type">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
				 </c:forEach>
			    </form:select>
                </td>
				<td><label class="lab">管辖城市：</label><form:select class="select180" style="width:110px" path="province" id="addrProvice">
                <form:option value="">选择省份</form:option>
                <c:forEach var="item" items="${provinceList}" varStatus="status">
		             <form:option value="${item.areaCode}">${item.areaName}</form:option>
	            </c:forEach>
                </form:select>-<select class="select180" style="width:110px" name="city" id="addrCity">
                 <option value="">请选择</option>
                </select>
                <input type="hidden" value="${GrantUrgeBackEx.city}" id="cityHid"/>
                </td>
                <td>
                <label class="lab">选择门店：</label><form:input type="text" path="name" readonly="true" id="storeOrgName" class="input_text178"/>
                 <form:hidden path="storeOrgIds"  id="storeOrgIds"/>
                 <i id="selectStoresBtn"
						class="icon-search" style="cursor: pointer;"></i>
				</td>
            </tr>
			<tr id="T1" style="display:none">
				<td><label class="lab">放款日期：</label><input  name="lendingTimeBegin" id="lendingTimeBegin"
                 value="<fmt:formatDate value='${GrantUrgeBackEx.lendingTimeBegin}' pattern='yyyy-MM-dd'/>"
                     type="text" class="Wdate input_text70" size="10"  
                                        onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'lendingTimeEnd\')}'})" style="cursor: pointer" ></input>-<input id="lendingTimeEnd" name="lendingTimeEnd" 
                 value="<fmt:formatDate value='${GrantUrgeBackEx.lendingTimeEnd}' pattern='yyyy-MM-dd'/>"
                     type="text" class="Wdate input_text70" size="10"  
                                        onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'lendingTimeBegin\')}'})" style="cursor: pointer" ></input></td>
				<td><label class="lab">渠道：</label><form:select class="select180" path="loanFlag">
						<form:option value="">请选择</form:option>
						<c:forEach items="${fns:getDictList('jk_channel_flag')}" var="card_type">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
				 		</c:forEach>
				</form:select></td>
				<td><label class="lab">放款退回日期：</label><input  name="checkTimeBegin" id="checkTimeBegin"
                 value="<fmt:formatDate value='${GrantUrgeBackEx.checkTimeBegin}' pattern='yyyy-MM-dd'/>"
                     type="text" class="Wdate input_text70" size="10"  
                                        onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'checkTimeEnd\')}'})" style="cursor: pointer" ></input>-<input id="checkTimeEnd" name="checkTimeEnd" 
                 value="<fmt:formatDate value='${GrantUrgeBackEx.checkTimeEnd}' pattern='yyyy-MM-dd'/>"
                     type="text" class="Wdate input_text70" size="10"  
                                        onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'checkTimeBegin\')}'})" style="cursor: pointer" ></input></td>
            </tr>
        </table>
        	
        <div class="tright pr30 pb5">
              <button class="btn btn-primary">搜索</button>
              <button class="btn btn-primary" id="clearBtn">清除</button>
        <div style="float: left; margin-left: 45%; padding-top: 10px">
	      <img src="../../../../static/images/up.png" id="showMore"></img>
	     </div>
		</div>
	</form:form>
	   </div>
		<p class="mb5">
		<button class="btn btn-small" id="daoBtn">导出excel</button>
		<button class="btn btn-small" id="backSureBtn">确认退款</button>
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
            <th>借款产品</th>
            <th>合同金额</th>
			<th>放款金额</th>
			<th>催收服务费金额</th>
			<th>已催收服务费金额</th>
			<th>批复期限</th>
            <th>退款状态</th>
			<th>放款日期</th>
			<th>放款失败日期</th>
            <th>渠道</th>
            <th>退款标识</th>
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
             <td>${item.productName}</td>
             <td>${item.contractAmount}</td>
             <td>${item.grantAmount}</td>
             <td>${item.urgeMoeny}</td>
             <td>${item.returnAmount}</td>
             <td>${item.contractMonths}</td>
             <td>${item.returnStatus}</td>
             <td><fmt:formatDate value="${item.lendingTime}"
							pattern="yyyy-MM-dd" /></td>
             <td><fmt:formatDate value="${item.checkTime}"
							pattern="yyyy-MM-dd" /></td>
             <td>${item.loanFlag}</td>  
             <td><c:if test="${item.refundFlag=='1'}">退款</c:if></td>          
             <td>
             <c:if test="${item.auditAmount>0}">
             <input type="button" class="btn_edit" sid='${item.urgeId}' name="seeBtn" value="查看" />
             </c:if>
             <input type="button" class="btn_edit" detailId='${item.urgeId}' name="detailBtn" value="已收记录" />
             <input type="button" class="btn_edit" contractCode='${item.contractCode}' name="historyBtn" value="历史" />
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