<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>电销已办页面</title>
<script type="text/javascript" src="${context}/js/payback/stresAlready.js?version=1"></script>
<script src='${context}/js/common.js' type="text/javascript"></script>
</head>
<body>
    <div class="control-group">
    <form id="auditForm"  method="post" action="${ctx}/telesales/customerManagement/allStoresAlreadyDoList">
    			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" /> 
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
          <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
             <tr>
                <td><label class="lab">客户姓名：</label>
                <input value="${LoanServiceBureauEx.customerName}" type="text" name="customerName" class="input_text178"></input></td>
                <td><label class="lab">合同编号：</label>
                <input value="${LoanServiceBureauEx.contractCode}" type="text" name="contractCode" class="input_text178"></input></td>
				<td><label class="lab">起始还款日期：</label>
				<input id="contractReplayDay" name="contractReplayDay" type="text" readonly="readonly" maxlength="20" class="input_text178 Wdate"
								value="<fmt:formatDate value="${LoanServiceBureauEx.contractReplayDay}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				</td>
            </tr>
            <tr> 
			   	<td><label class="lab">回盘结果：</label>
			   	<select class="select180" name="dictPayResult">
                		<option value="">请选择</option>
                              <c:forEach items="${fns:getDictList('jk_counteroffer_result')}" var="dictPayResult">
                                   <option value="${dictPayResult.value }" <c:if test="${LoanServiceBureauEx.dictPayResult == dictPayResult.value }">selected</c:if>>${dictPayResult.label }</option>
                              </c:forEach>
                	</select>
			   	</td>
				<td><label class="lab">还款类型：</label>
				<select class="select180" name="dictPayUse">
                		<option value="">请选择</option>
                              <c:forEach items="${fns:getDictList('jk_repay_type')}" var="dictPayUse">
                                   <option value="${dictPayUse.value }" <c:if test="${LoanServiceBureauEx.dictPayUse == dictPayUse.value }">selected</c:if>>${dictPayUse.label }</option>
                              </c:forEach>
                	</select>
				</td>
			     <td>
                	<label class="lab">还款日：</label>
                	<select name="repaymentDate" class="select180">
						        <option value="">请选择</option>
						<c:forEach var="day" items="${dayList}">
								<option value="${day.billDay}"
								<c:if test="${LoanServiceBureauEx.repaymentDate==day.billDay }">selected</c:if>>${day.billDay}</option>
						</c:forEach>
				</select>
                </td>
            </tr>
            <tr id="T1" style="display: none;">
            
               <td><label class="lab">标识：</label>
               <select class="select180" name="loanMark">
                		<option value="">请选择</option>
                              <c:forEach items="${fns:getDictList('jk_channel_flag')}" var="loanMark">
                                   <option value="${loanMark.value }" <c:if test="${LoanServiceBureauEx.loanMark == loanMark.value }">selected</c:if>>${loanMark.label }</option>
                              </c:forEach>
                	</select>
               </td>
               
               <td><label class="lab">还款方式：</label> 
							<select class="select180" name="dictRepayMethod">
		                			  <option value="">请选择</option>
		                              <c:forEach items="${fns:getDictList('jk_repay_way')}" var="dictRepayMethod">
		                                   <option value="${dictRepayMethod.value }" <c:if test="${LoanServiceBureauEx.dictRepayMethod== dictRepayMethod.value }">selected</c:if>>${dictRepayMethod.label }</option>
		                              </c:forEach>
		                	</select>
			   </td>
            </tr>
        </table>
	        <div class="tright pr30 pb5">
            <!--  <button class="btn btn-primary"  onclick="document.forms[0].submit();">搜索</button> -->
             <input type="button" class="btn btn-primary" value="搜索" id="stresBtn"/>
			 <input type="button" class="btn btn-primary" id="clearBtn" value="清除" />
	             <div style="float: left; margin-left: 45%; padding-top: 10px">
					<img src="${context}/static/images/up.png" id="showMore"></img>
				</div>
        	</div>
        </form>
      </div> 
      	<form id="deductInfoForm" action="${ctx}/borrow/payback/doStore/form" method="post">
      <sys:columnCtrl pageToken="stresttt"></sys:columnCtrl>
      <div class="box5">       
    <table id="tableList" class="table  table-bordered table-condensed table-hover ">
      <thead>
        <tr>
        　 　	<th>序号</th>
            <th>合同编号</th>
            <th>客户姓名</th>
            <th>门店名称</th>
            <th>合同到期日</th>
            <th>借款状态</th>
            <th>还款日</th>
            <th>申请还款金额</th>
            <th>实际还款金额</th>
            <th>还款类型</th>
            <th>还款方式</th>
            <th>还款状态</th>
            <th>回盘结果</th>
            <th>失败原因</th>
            <th>标识</th>
            <th>是否电销</th>
            <th>操作</th>
        </tr>
        </thead>
        <c:forEach items="${waitPage.list}" var="item" varStatus="num">
			<tr align="center">
				<td>${num.index + 1 }</td>
				<td>${item.contractCode}</td>
				<td>${item.customerName}</td>
				<td>${item.orgName}</td>
				<td><fmt:formatDate value="${item.contractEndDay}" type="date"/></td>
				<td>${item.dictLoanStatusLabel}</td>
				<td><fmt:formatDate value="${item.monthPayDay}" pattern="dd"/></td>
				<td><fmt:formatNumber value='${item.applyMoneyPayback}' pattern="#,##0.00"/></td>
				<td><fmt:formatNumber value='${item.applyReallyAmount}' pattern="#,##0.00"/></td>
				<td>${item.dictPayUseLabel}</td>
				<td>${item.dictRepayMethodLabel}</td>
				<td>${item.dictPayStatusLabel}</td>
				<td>${item.splitBackResultLabel}</td>
				<td>${item.splitFailResult}</td>
				<td>${item.loanMarkLabel}</td>
				<td>${item.customerTelesalesFlagLabel}</td>
				<td><input type="button" name="seeStresAlready" class="btn_edit" value="查看" />
					<input type="hidden" value="${item.ids}" />
					<input type="hidden" value="${item.dictPayUse}" />
					 <input type="button"
								class="btn_edit"
								onclick="showNoDeductPaybackHistory('${item.rPaybackId}','${item.ids}','');"
								value="历史" />
				    	<input type="button" class="btn_edit" onclick="showPaybackHis('','${item.rPaybackId}','lisi');" value="已拆分历史" /></td>
				</td>
			</tr>
		</c:forEach>
		<c:if test="${waitPage.list==null || fn:length(waitPage.list)==0}">
				<tr>
					<td colspan="18" style="text-align: center;">没有符合条件的数据</td>
				</tr>
		</c:if>
    </table>
    </form>
</div>
 
  <div class="pagination">${waitPage}</div>
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