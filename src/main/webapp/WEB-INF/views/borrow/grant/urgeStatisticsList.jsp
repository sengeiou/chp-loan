<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default"/>
<script type="text/javascript">
$(document).ready(function () {
	// 点击清除，清除搜索页面中的数据
	$("#clearAuditedBtn").click(function() {
		$(':input','#urgeAuditForm')
		.not(':button,:submit,:reset,:hidden')
		.val('').removeAttr('checked').removeAttr('selected');
		$('#urgeAuditForm').submit();
		/* $('select').val('');
		$('select').trigger("change");
		$('#urgeAuditForm').submit(); */
		// $('#colResult').val('');
		/*$('#dictPayStatus').val('');
		$('#backMoneyDesc').val('');
		$('#payStatus').val(''); */
	});
	
	// 点击全选
	$("#checkAll").click(function(){
		if($('#checkAll').attr('checked')=='checked'|| $('#checkAll').attr('checked'))
		{
			$(":input[name='checkBoxItem']").each(function() {
				$(this).attr("checked",true);
				});
		}else{
			$(":input[name='checkBoxItem']").each(function() {
				$(this).removeAttr("checked");
			});
		}
	});
	    
	//导出excel
	$("#daoExcel").click(function(){
		var idVal = "";
		if($(":input[name='checkBoxItem']:checked").length>0){
			$(":input[name='checkBoxItem']:checked").each(function(){
        		if(idVal!="")
        		{
        			idVal+=","+$(this).attr("contractCode");
        		}else{
        			idVal=$(this).attr("contractCode");
        		}
        	});
		}else{
			$(":input[name='checkBoxItem']").each(function(){
        		if(idVal!="")
        		{
        			idVal+=","+$(this).attr("contractCode");
        		}else{
        			idVal=$(this).attr("contractCode");
        		}
        	});
		}
		window.location.href=ctx+"/borrow/grant/urgeCheckAudited/excelStatisticsList?cid="+idVal;
	});

	
 });
 
function page(n, s) {
	if (n)
		$("#pageNo").val(n);
	if (s)
		$("#pageSize").val(s);
	$("#urgeAuditForm").attr("action",  ctx + "/borrow/grant/urgeCheckAudited/urgeStatisticsList");
	$("#urgeAuditForm").submit();
	return false;
}

</script>
</head>
<body>
	<div class="control-group">
		<form:form id="urgeAuditForm" action="${ctx}/borrow/grant/urgeCheckAudited/urgeStatisticsList" modelAttribute="param" >
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
			<input type="hidden" id="msg" value="${message}">
			<input type="hidden" name="id" value="${paybackTransferOut.id }"/>
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">合同编号：</label> <form:input type="text" path="contractCode" class="input-medium"></form:input></td>
					<td><label class="lab">客户姓名：</label> <form:input type="text" path="customerName" class="input-medium"></form:input></td>
					<td>
                       <label class="lab">最后划扣时间：</label>
               		 <span>  <input id="deductBeginTime" name="deductBeginTime"
							type="text" class="Wdate input_text70" size="10" value="${param.deductBeginTime }"
							onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'deductEndTime\')}'})"
							style="cursor: pointer" />-<input id="deductEndTime"
							name="deductEndTime" 
							type="text" class="Wdate input_text70" size="10"  value="${param.deductEndTime }"
						     onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'deductBeginTime\')}'})"
						     style="cursor: pointer" /> 
					</span>
               </td>
				</tr>
			<tr>
					<td>
                 <label class="lab">放款日期：</label>
               		 <span>  <input id="lendingTimeBegin" name="fanKuanBegin"
							type="text" class="Wdate input_text70" size="10" value="${param.lendingTimeBegin }"
							onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'fanKuanEnd\')}'})"
							style="cursor: pointer" />-<input id="fanKuanEnd"
							name="lendingTimeEnd"
							type="text" class="Wdate input_text70" size="10" value="${param.lendingTimeEnd }"
						     onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'fanKuanBegin\')}'})"
						     style="cursor: pointer" /> 
					</span>
               </td>
               <td><label class="lab" style="text-align: right">收取结果：</label>
						<form:select path="colResult" class="input-medium"  >
							<form:option value='4'>请选择</form:option>
						    <form:option value="3">成功</form:option>
						    <form:option value="1">失败</form:option>
						    <form:option value="2">部分成功</form:option>
						</form:select>
					</td>
					<td><label class="lab" style="text-align: right">是否返还：</label>
						<form:select path="dictPayStatus" class="input-medium" >
							<form:option value=''>请选择</form:option>
						    <form:option value="2">是</form:option>
						    <form:option value="1">否</form:option>
						</form:select>
					</td>
				</tr>
				<tr>
				   <td><label class="lab" style="text-align: right">是否符合退款条件：</label>
						<form:select path="backMoneyDesc" class="input-medium" >
							<form:option value=''>请选择</form:option>
						    <form:option value="1">是</form:option>
						    <form:option value="2">否</form:option>
						</form:select>
					</td>
					<td><label class="lab" style="text-align: right">还款状态：</label>
						<form:select path="payStatus" class="input-medium" >
							<form:option value=''>请选择</form:option>
							<c:forEach items="${fns:getDictList('jk_repay_status')}" var="item">
								<c:if test="${item.value ==0 || item.value ==1 || item.value ==2 || item.value ==3 || item.value ==5}">
								<form:option value="${item.value}">${item.label}
								</form:option>
								</c:if>
							</c:forEach>
						</form:select>
					</td>
					<td>
					    <label class="lab">借款产品：</label><form:select class="select180" path="productName"><option value="">请选择</option>
					    <c:forEach items="${productList}" var="card_type">
										<form:option value="${card_type.productCode}">${card_type.productName}</form:option>
						 </c:forEach>
					    </form:select>
			   	 	</td>
				</tr>
			</table>
		</form:form>
		<div class="tright pr30 pb5">
			<input type="button" class="btn btn-primary" onclick="document.forms[0].submit();" value="搜索">
			<input type="button" class="btn btn-primary" id="clearAuditedBtn" value="清除">
			<%-- <div style="float:left;margin-left:45%;padding-top:10px">
				  <img src="${context }/static/images/up.png" id="showMore" onclick="show();"></img>
				</div> --%>
		</div>
	</div>
	<div id="auditList">
	<!-- 当门店客服登陆时候， 导出按钮不可见 -->
		<c:if test="${ isCanSe==false    }">
		<p class="mb5">  
			<button class="btn btn-small" id="daoExcel" >导出excel</button>
		</p>
		</c:if>
		<div class="box5">
		<table id="tableList" class="table  table-bordered table-condensed table-hover">
			<thead>
				<tr>
				    <th><input type="checkbox" id="checkAll" />全选</th>
					<th>序号</th>
					<th>合同编号</th>
					<th>客户姓名</th>
					<th>借款产品</th>
					<th>合同金额</th>
					<th>应收催收服务费</th>
					<th>已收催收服务费</th>
					<th>收取结果</th>
					<th>放款时间</th>
					<th>最后划扣日期</th>
					<th>是否返还</th>
					<th>是否符合退款条件</th>
					<th>还款状态</th>
					<th>渠道</th>
				</tr>
			</thead>
			<tbody id="prepareListBody">
				<c:forEach items="${page.list}" var="ps" varStatus="xh">
					<tr>
					  <td><input type="checkbox" name="checkBoxItem"
								contractCode="${ps.contractCode}"  />
							</td>
						<td>${xh.count+(page.pageSize*(page.pageNo-1))}</td>
						<td>${ps.contractCode}</td>
						<td>${ps.customerName}</td>
						<td>${ps.productName}</td>
						<td><fmt:formatNumber value='${ps.contractAmount}' pattern="#,##0.00"/></td>
						<td><fmt:formatNumber value='${ps.receivableFeeUrgedService}' pattern="#,##0.00"/></td>
						<td><fmt:formatNumber value='${ps.receivedfeeUrgedService}' pattern="#,##0.00"/></td>
						<td>${ps.colResult}</td>
						<td><fmt:formatDate value="${ps.lendingTime}" type="date"/></td>
						<td><fmt:formatDate value="${ps.lastDeductDate}" type="date"/></td>
						<td>${ps.dictPayStatusLabel}</td>
						<td>${ps.backMoneyDesc}</td>
						<td>${ps.payStatusLabel}</td>
						<td>${ps.loanFlag}</td>
					</tr>
				</c:forEach>
				<c:if test="${ page.list==null || fn:length(page.list)==0}">
					<tr>
						<td colspan="18" style="text-align: center;">没有待办数据</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>

	<div class="pagination">${page}</div>
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