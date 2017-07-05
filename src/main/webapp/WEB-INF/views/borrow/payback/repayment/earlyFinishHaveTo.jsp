<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>待提前结清确认已办</title>
<script type="text/javascript" src="${context}/js/payback/loanServiceBureau.js"> </script>
<script src="${context}/js/grant/disCard.js" type="text/javascript"></script>
<script src='${context}/js/common.js' type="text/javascript"></script>
<script src="${context}/js/payback/historicalRecords.js" type="text/javascript"></script>
<script src="${context}/static/ckfinder/plugins/gallery/colorbox/jquery.colorbox-min.js"></script>
<link media="screen" rel="stylesheet" href="${context}/static/ckfinder/plugins/gallery/colorbox/colorbox.css" />
</head>
<body>
<div class="control-group">
    <form id="earlyFinishHaveToForm" method="post">
    		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
          <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
             <tr>
                <td><label class="lab">客户姓名：</label>
                	<input value="${LoanServiceBureauEx.customerName }" type="text" name="customerName" class="input_text178"></input></td>
                <td><label class="lab">合同编号：</label>
                	<input value="${LoanServiceBureauEx.contractCode }" type="text" name="contractCode" class="input_text178"></input></td>
				<td>
                	<label class="lab">还款日：</label>
				    	<select name="repaymentDate" class="select180">
							        <option value="">请选择</option>
							<c:forEach var="day" items="${dayList}">
									<option value="${day.billDay}"
									<c:if test="${search.paybackDayNum ==day.billDay }">selected</c:if>>${day.billDay}</option>
							</c:forEach>
					   </select>
                </td>
            </tr>
            <tr > 
			   	<td ><label class="lab">渠道：</label>
			   		<select class="select180" name="loanMark">
                		<option value="">请选择</option>
                              <c:forEach items="${fns:getDictList('jk_channel_flag')}" var="loanMark">
                                   <option value="${loanMark.value }" <c:if test="${LoanServiceBureauEx.loanMark == loanMark.value }">selected</c:if>>${loanMark.label }</option>
                              </c:forEach>
                	</select>
			   	</td>
				
			    <td ><label class="lab">来源系统：</label>
			    	<select class="select180" name="dictSourceType">
                		<option value="">请选择</option>
                              <c:forEach items="${fns:getDictList('jk_new_old_sys_flag')}" var="dictSourceType">
                                   <option value="${dictSourceType.value }" <c:if test="${LoanServiceBureauEx.dictSourceType == dictSourceType.value }">selected</c:if>>${dictSourceType.label }</option>
                              </c:forEach>
                	</select> 
			    </td>
			    <td ><label class="lab">门店：</label>
			    	<input id="txtStore" name="storeName" type="text" maxlength="20" class="txt date input_text178" value="${LoanServiceBureauEx.storeName }" /> 
					     <i id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
					     <input type="hidden" id="hdStore" name="store" value="${LoanServiceBureauEx.store}">
			   </td>
            </tr>
			<tr id="T1" style="display:none">
				     <td>
				      <label class="lab">模式：</label>
				      	<select class="select180" name="model">
	                     <option value="">请选择</option>
			              <c:forEach var="flag" items="${fns:getDictList('jk_loan_model')}">
						    <option value="${flag.value }"
							<c:if test="${LoanServiceBureauEx.model==flag.value }">selected</c:if>>
							<c:if test="${flag.value=='1' }">${flag.label}</c:if>
							<c:if test="${flag.value!='1' }">非TG</c:if>
						</option>
					   </c:forEach>
	                    </select>
		             </td>
			</tr>
        </table>
        <div class="tright pr30 pb5">
			<button class="btn btn-primary" id="searchBtn">搜索</button>
			<button class="btn btn-primary" id="clearBtnEarlyExam"/>清除</button>
			<div style="float:left;margin-left:45%;padding-top:10px">
				  <img src="${context}/static/images/up.png" id="showMore"></img>
				</div>
		</div>
    </form>
	</div>	
	<div>  
    <table id="tableList">
       <thead>
        <tr>
            <th>序号</th>
            <th>合同编号</th>
            <th>客户姓名</th>
            <th>合同到期日</th>
            <th>门店名称</th>
            <th>借款状态</th>
            <th>应还款金额</th>
            <th>申请还款金额</th>
            <th>还款类型</th>
            <th>还款状态</th>
            <th>还款日</th>
            <th>渠道</th>
            <th>模式</th>
			<th>划扣平台</th>
            <th>操作</th>
        </tr>
        </thead>
        <c:forEach items="${waitPage.list}" var="item" varStatus="num">
			<tr>
				<td>${num.index + 1 }</td>
				<td>${item.contractCode}</td>
				<td>${item.customerName}</td>
				<td><fmt:formatDate value="${item.contractEndDay}" type="date"/></td>
				<td>${item.orgName}</td>
				<td>${item.dictLoanStatusLabel}</td>
				<td><fmt:formatNumber value='${item.monthPayAmount}' pattern="0.00"/></td>
				<td><fmt:formatNumber value='${item.applyMoneyPayback}' pattern="0.00"/></td>
				<td>${item.dictPayUse}</td>
				<td>${item.dictPayStatusLabel}</td>
				<td>${item.repaymentDate }</td>
				<td>${item.loanMarkLabel}</td>
				<td>${item.modelLabel}</td>
				<td>${item.dictDealTypeLabel}</td>
				<td>
				<input type="button" class="btn_edit" value="${items.ids }"/>
				<input type="button" class="btn_edit" onclick="showNoDeductPaybackHistory('','${item.ids}','');" value="历史" /></td>
			</tr>
		</c:forEach>
		<c:if test="${waitPage.list==null || fn:length(waitPage.list)==0}">
				<tr>
					<td colspan="18" style="text-align: center;">没有符合条件的数据</td>
				</tr>
		</c:if>
    </table>
</div>
 
</div>
  <div class="pagination">${waitPage}</div>
  <script type="text/javascript">
		function page(n, s) {
			if (n)
				$("#pageNo").val(n);
			if (s)
				$("#pageSize").val(s);
			$("#earlyFinishHaveToForm").attr("action",  ctx + "/borrow/payback/loanServices/earlyExamHavetoList");
			$("#earlyFinishHaveToForm").submit();
			return false;
		}
		 
	  $("#tableList").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
		$('#tableList').bootstrapTable({
			method: 'get',
			cache: false,
			height: $(window).height()-200,
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