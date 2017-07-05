<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script src="${context}/js/grant/urgeBack.js" type="text/javascript"></script>
<script src="${context}/js/common.js" type="text/javascript"></script>
<meta name="decorator" content="default"/>
<script language="javascript"> 
	function page(n, s) {
		if (n)
			$("#pageNo").val(n);
		if (s)
			$("#pageSize").val(s);
		$("#backForm").attr("action", "${ctx}/borrow/grant/urgeServicesBack/urgeBackInfo");
		$("#backForm").submit();
		return false;
	}
	$(document).ready(function(){
		  loan.getstorelsit("storesCode","storesId");
		});
</script>
</head>
<body>  
  <div class="control-group">
   <form:form  action="${ctx }/borrow/grant/urgeServicesBack/urgeBackInfo" method="post" id="backForm" modelAttribute="UrgeServicesBackMoneyEx">
   			<input id="pageNo" name="pageNo" type="hidden" value="${urgePage.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" value="${urgePage.pageSize}" />	
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
            <td><label class="lab">客户姓名：</label>
			   		<form:input type="text" class="input_text178" path="customerName"></form:input>
            </td>
            <td><label class="lab">合同编号：</label>
                	<form:input type="text" class="input_text178" path="contractCode"></form:input></td>
		   <td><label class="lab">门店：</label>
		   		<form:input type="text" id="storesCode" class="input_text178" path="storesCode" readonly="true"></form:input>
               	  <i id="selectStoresBtn"
					class="icon-search" style="cursor: pointer;"></i>
			    <form:hidden path="storesId" id="storesId"/>
               </td>
            </tr>
            <tr>
            <td><label class="lab">开户行：</label>
				 <select name="bankName" value="${applyInfoEx.loanBank.bankName}" class="select180 required">
                    <option value="">请选择</option>
                    <c:forEach items="${fns:getDictList('jk_open_bank')}" var="curItem">
					  <option value="${curItem.value}"
					    <c:if test="${UrgeServicesBackMoneyEx.bankName==curItem.value}">
					      selected=true 
					    </c:if>
					  >${curItem.label}</option>
				    </c:forEach>
				</select>
                </td>
                 <td><label class="lab" >申请日期：</label>
                 <input id="grantDate" name="backApplyPayTime" value="<fmt:formatDate value='${UrgeServicesBackMoneyEx.backApplyPayTime }' pattern='yyyy-MM-dd'/>"
                     type="text" class="Wdate input_text178" size="10"  
                                        onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer"></input>
                                        
                 </td>
                      
                <td ><label class="lab">返款状态：</label>
                	<form:select class="select180" path="dictPayStatus">
						<form:option value="">请选择</form:option>
						<c:forEach items="${fns:getDictList('jk_urge_repay_status')}" var="card_type">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
				    </c:forEach>
				
						</form:select></td>
            </tr>
           	<tr>
           	 <td><label class="lab">返款结果：</label>
				 <form:select class="select180" path="dictPayResult">
						<form:option value="">请选择</form:option>
							<c:forEach items="${fns:getDictList('jk_payback_fee_result')}" var="card_type">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
				   			 </c:forEach>
					
				</form:select></td>
					<td><label class="lab">渠道：</label>
					<select class="select180" name="loanFlag">
							<option value="">请选择</option>
							<c:forEach items="${fns:getDictList('jk_channel_flag')}"
								var="loanMark">
								<option value="${loanMark.value }"
									<c:if test="${UrgeServicesBackMoneyEx.loanFlag == loanMark.value }">selected</c:if>>${loanMark.label }</option>
							</c:forEach>
					</select></td>
					<td><label class="lab">模式：</label>
						<select name="model" class="select180">
								<option value=''>请选择</option>
								<c:forEach items="${fns:getDictList('jk_loan_model')}" var="loanmodel">
	                                   <option value="${loanmodel.value }" <c:if test="${paybackApply.loanInfo.model == loanmodel.value }">selected</c:if>>
	                                   <c:if test="${loanmodel.value=='0'}">
	                                   	非TG
	                                   </c:if>
	                                   <c:if test="${loanmodel.value!='0'}">${loanmodel.label}</c:if>
	                                   </option>
	                            </c:forEach>
	                       </select>
					</td>
           	</tr>
        </table>
       
        <div class="tright pr30 pb5"><button type="submit" class="btn btn-primary" >搜索</button>
                     <button class="btn btn-primary" id="clearBtn">清除</button></div>
      </form:form>
		</div>
		<p class="mb5">
    	<button class="btn btn-small" id="sendApply">发送返款申请</button>
    	<button class="btn btn-small" id="djrApply">发送大金融</button>
		<button class="btn btn-small" id="daoBtn">导出excel</button>
		</p>
		<div >
        <table id="tableList">
        <thead>
         <tr>
            <th><input type="checkbox" id="checkAll"/></th>
            <th>门店</th>
            <th>客户姓名</th>
            <th>合同编号</th>
            <th>合同金额</th>
            <th>放款金额</th>
            <th>收款人所在省</th>
            <th>收款人所在市</th>
            <th>开户行</th>
            <th>开户行支行</th>
			<th>银行账号</th>
			<th>结清日期</th>
			<th>借款状态</th>
			<th>催收服务费</th>
			<th>最长逾期天数</th>
			<th>申请返款金额</th>
			<th>返款状态</th>
			<th>返款结果</th>
			<th>原因</th>
			<th>申请日期</th>
            <th>返款日期</th>
            <th>渠道</th>
            <th>模式</th>
            <th>操作</th>
        </tr>
		</thead>
        <tbody>
        <c:if test="${ urgePage.list!=null && fn:length(urgePage.list)>0}">
         <c:set var="index" value="0"/>
          <c:forEach items="${urgePage.list}" var="item">
          <c:set var="index" value="${index+1}" />
             <tr>
             <td><input type="checkbox" name="checkBoxItem" val="${item.id}" channel="${item.channelFlag }" /></td>
             <td>${item.storesCode}</td>
             <td>${item.customerName}</td>
             <td>${item.contractCode}</td>
             <td><fmt:formatNumber value='${item.contractAmount}' pattern="#,#00.00"/></td>
             <td><fmt:formatNumber value='${item.grantAmount}' pattern="#,#00.00"/></td>
             <td>${item.bankProvince}</td>
             <td>${item.bankCity}</td>
             <td>${item.bankNameLabel}</td>
             <td>${item.bankBranch}</td>
             <td>${item.bankAccount}</td>
             <td><fmt:formatDate value="${item.settlementTime}"
							type="date" /></td>
             <td>${item.dictLoanStatusLabel} </td>
             <td><fmt:formatNumber value='${item.feeUrgedService}' pattern="#,#00.00"/></td>
             <td>${item.paybackMaxOverduedays}</td>
             <td><fmt:formatNumber value='${item.paybackBackAmount}' pattern="#,#00.00"/></td>
             <td>${item.dictPayStatusLabel} </td>
             <td>${item.dictPayResultLabel} </td>
             <td>${item.remark}</td>
             <td><fmt:formatDate value="${item.backApplyPayTime}"
							type="date" /></td>
             <td><fmt:formatDate value="${item.backTime}"
							type="date" /></td>
			<td>${item.loanFlagLabel}</td>
            <td>${item.modelLabel}</td>			
            <td><button class="btn_edit"  id="history" onclick="backHistory('${item.id }');" sid="${item.id}">历史</button></td>
         </tr> 
         </c:forEach>  
         </c:if>
     </tbody>
    </table>
	</div>
      

</div>
<div class="pagination">
	${urgePage }
</div>
<script type="text/javascript">

	  $("#tableList").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
		$('#tableList').bootstrapTable({
			method: 'get',
			cache: false,
			height: $(window).height()-265,
			striped: true,
			pageSize: 20,
			pageNumber:1
		});
		$(window).resize(function () {
			$('#tableList').bootstrapTable('resetView');
		});
	</script>
</body>
</html>