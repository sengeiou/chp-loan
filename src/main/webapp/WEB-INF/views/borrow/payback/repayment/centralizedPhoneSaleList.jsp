<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>集中划扣已办页面</title>
<script type="text/javascript" src="${context}/js/payback/centralizedPhoneSaleList.js"></script>
<script src="${context}/js/grant/disCard.js" type="text/javascript"></script>
<script src='${context}/js/common.js' type="text/javascript"></script>
</head>
<body>
<div class="control-group">
   <form id="auditForm" method="post">
   		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" /> 
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
		<input id="ids" name="id" type="hidden" />
		<input  name="menuId"  id ="menuId" type="hidden" value="${menuId}" /> 
		
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td><label class="lab">客户姓名：</label>
                <input value="${PaybackApply.customerName }" type="text" name="customerName" class="input_text178"></input></td>
                <td><label class="lab">合同编号：</label>
                <input value="${PaybackApply.contractCode }" type="text" name="contractCode" class="input_text178"></input></td>
                 <td><label class="lab">开户行名称：</label>
                	<sys:multipleBank bankClick="selectBankBtn" bankName="search_applyBankName" bankId="bankId"></sys:multipleBank>
                        <input id="search_applyBankName" type="text" class="input_text178" name="applyBankName"  value='${PaybackApply.applyBankName}'>&nbsp;<i id="selectBankBtn"
						class="icon-search" style="cursor: pointer;"></i> 
						<input type="hidden" id="bankId" name='bank' value='${PaybackApply.bank}'>
                </td>
            </tr>
            <tr>
                <td><label class="lab">期供还款日：</label>
                <input id="paybackMonthMoneyDate" name="monthPayDay" type="text" readonly="readonly" maxlength="20" class="input_text178 Wdate"
								value="<fmt:formatDate value="${PaybackApply.monthPayDay}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
                </td>
                <td><label class="lab">回盘结果：</label>
                <select class="select180" name="splitBackResult">
                		<option value="">请选择</option>
                              <c:forEach items="${fns:getDictList('jk_counteroffer_result')}" var="splitBackResult">
                                  <c:if test="${splitBackResult.value =='0' || splitBackResult.value =='1' ||  splitBackResult.value =='2'|| splitBackResult.value =='3'}">
                                   	<option value="${splitBackResult.value }" <c:if test="${PaybackApply.splitBackResult == splitBackResult.value }">selected</c:if>>${splitBackResult.label }</option>
                                  </c:if>
                              </c:forEach>
                	</select>
                </td>
                <td><label class="lab">划扣日期：</label>
				 		<label><input id="beginDate" name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
								value="<fmt:formatDate value="${PaybackApply.beginDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
						</label>-<label>
							<input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
								value="<fmt:formatDate value="${PaybackApply.endDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
						</label>
				 </td>
            </tr>
            <tr>
            	<td>
                	<label class="lab">账单日：</label>
				    <sys:multirepaymentDate dateClick="dateClick" dateId="repayDate"></sys:multirepaymentDate>
				    <input id="repayDate"  name="paybackDay" class="input_text178" readonly ="readonly"   value="${PaybackApply.paybackDay}">
				    <i id="dateClick" class="icon-search" style="cursor: pointer;"></i> 
               	</td> 
                <td><label class="lab">渠道：</label>
                <select class="select180" name="loanMark">
                		<option value="">请选择</option>
                              <c:forEach items="${fns:getDictList('jk_channel_flag')}" var="loanMark">
                              	<c:if test="${loanMark.label!='TG' && loanMark.label!='附条件' }">
                                   <option value="${loanMark.value }" <c:if test="${PaybackApply.loanMark == loanMark.value }">selected</c:if>>${loanMark.label }</option>
                              	</c:if>
                              </c:forEach>
                	</select>
                </td>
                <td><label class="lab">模式：</label><select class="select180" name="model">
                        <option value="">请选择</option>
		                  <c:forEach var="flag" items="${fns:getDictList('jk_loan_model')}">
					        <option value="${flag.value }"
						    <c:if test="${PaybackApply.model==flag.value }">selected</c:if>>
						    <c:if test="${flag.value=='1' }">${flag.label}</c:if>
						    <c:if test="${flag.value!='1' }">非TG</c:if>
					      </option>
				       </c:forEach>
                   </select>
                </td>
            </tr>
			<%-- <c:if test="${isManager == true}">
			<tr>
				<td><label class="lab">门店：</label> <input id="txtStore" name="storesName" type="text" readonly="readonly"
							class="txt date input_text178"
							value="${PaybackApply.storesName }" /> <i id="selectStoresBtn"
							class="icon-search" style="cursor: pointer;"></i> <input
							type="hidden" id="hdStore" name="stores"
							value="${PaybackApply.stores}">
					</td>
				</tr>
			</c:if> --%>
		</table>
      </form>
      <div class="tright pr30 pb5">
     		<button class="btn btn-primary" id="searchBtn">搜索</button>
             <input type="button" class="btn btn-primary" id="clearBtn" value="清除" />
      </div>
	   </div>
     <p class="mb5">
    <%--   <c:if test="${jkhj_jzhukyb_dcebtn==null }"> --%>
       <button class="btn btn-small" name="exportExcel">导出EXCEL</button>
   <%--     </c:if> --%>
	      <%--  <span class="red">划扣总金额:</span>
			<span class="red" id="totalMoney">${numTotal.total}</span> </input>&nbsp;
			<span class="red">划扣总笔数:</span>
			<span class="red" id="totalNum">${numTotal.num}</span> --%>
       </p>  
    <div class="box5">
    <sys:columnCtrl pageToken="centralizedList"></sys:columnCtrl>
    <table id="tableList" class="table  table-bordered table-condensed table-hover " width="100%">
    	<thead>
        <tr>
            <th><input type="checkbox" id="checkAll"/></th>
            <th>序号</th>
            <th>合同编号</th>
            <th>客户姓名</th>
            <th>门店名称</th>
            <th>开户行名称</th>
            <th>批借期数</th>
            <th>首期还款期</th>
            <th>实还金额</th>
			<th>期供</th>
			<th>当期未还期供</th>
			<th>当期已还期供</th>
			<th>划扣金额</th>
            <th>还款类型</th>
            <th>划扣日期</th>
            <th>账单日</th>
            <th>还款日</th>
            <th>借款状态</th>
            <th>回盘结果</th>
            <th>团队经理</th>
            <th>客户经理</th>
            <th>外访</th>
            <th>客服</th>
            <th>失败原因</th>
			<th>蓝补金额</th>
            <th>划扣平台</th>
            <th>渠道</th>
            <th>模式</th>
            <th>操作</th>
        </tr>
        </thead>
        <c:forEach items="${waitPage.list}" var="item" varStatus="num">
	        <tr>
	        	<td><input type="checkbox" name="checkBox" value="${item.id}" />
	        	<input type="hidden" value="${item.applyReallyAmount }"/>
	        	</td>
	            <td>${(waitPage.pageNo-1)*(waitPage.pageSize)+num.count}</td>
	        	<td>${item.contractCode}</td>
	        	<td>${item.customerName}</td>
	        	<td>${item.orgName}</td>
	        	<td>${item.applyBankName}</td>
	        	<td>${item.contractMonths}</td>
	        	<td><fmt:formatDate value="${item.contractReplayDay}" type="date"/></td>
	        	<td><fmt:formatNumber value='${item.applyReallyAmount}' pattern="0.00"/></td>
	        	<td><fmt:formatNumber value='${item.paybackMonthAmount}' pattern="0.00"/></td>
	        	<td><fmt:formatNumber value='${item.notPaybackMonthAmount}' pattern="0.00"/></td>
	        	<td><fmt:formatNumber value='${item.alsoPaybackMonthAmount}' pattern="0.00"/></td>
	        	<td><fmt:formatNumber value='${item.applyAmount}' pattern="0.00"/></td>
	        	<td>集中划扣</td>
	        	<td><fmt:formatDate value="${item.modifyTime}" type="date"/></td>
	        	<td>${item.billDay}</td>
	        	<td>
	        	<fmt:formatDate value="${item.monthPayDay}" pattern="yyyy-MM-dd"/>
	        	</td>
	        	<td>${item.dictLoanStatus}</td>
	        	<td>
		        	<c:if test="${item.splitBackResult=='3'||item.splitBackResult=='4' || item.splitBackResult=='7'}">
		        		处理中
		        	</c:if>
		        	<c:if test="${item.splitBackResult=='2'}">
		        		成功
		        	</c:if>
		        	<c:if test="${item.splitBackResult=='1'||item.splitBackResult=='5'||item.splitBackResult=='6'}">
		        		失败
		        	</c:if>
		        	<c:if test="${item.splitBackResult=='0'}">
		        		待划扣
		        	</c:if>
	        	</td>
	        	<td>${item.loanTeamManagerName}</td>
	        	<td>${item.loanManagerName}</td>
	        	<td>${item.loanSurveyEmpName}</td>
	        	<td>${item.loanCustomerService}</td>
	        	<td>
		        	<c:if test="${item.failReason=='冲抵剔除'}">
		        		划扣失败
		        	</c:if>
		        	<c:if test="${item.failReason!='冲抵剔除'}">
		        		${item.failReason}
		        	</c:if>
	        	</td>
	        	<td><fmt:formatNumber value='${item.paybackBuleAmount}' pattern="0.00"/></td>
	        	<td>${item.dictDealTypeLabel}</td>
	        	<td>${item.loanMark}</td>
	        	<td>
	        	<c:if test="${item.model=='1'}">
	        		TG
	        	</c:if>
	        	</td>
	        	<td>
	        	<c:if test="${jkhj_jzhukyb_lsbtn==null }">
	        	<input type="button" class="btn_edit" onclick="showPaybackHis('','${item.id}','');" value="历史" />
	        	</c:if>
	        	<c:if test="${jkhj_jzhukyb_ycflsbtn==null }">
	        		<input type="button" class="btn_edit" onclick="showPaybackHis('','${item.id}','lisi');" value="已拆分历史" />
	            </c:if>
	            </td>
	        </tr>
        </c:forEach>
        <c:if test="${waitPage.list==null || fn:length(waitPage.list)==0}">
			<tr>
				<td colspan="26" style="text-align: center;">没有符合条件的数据</td>
			</tr>
		</c:if>
		</table>
	</div>
    <div class="pagination">${waitPage}</div> 
    <script type="text/javascript">
	  $("#tableList").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
		$('#tableList').bootstrapTable({
			method: 'get',
			cache: false,
			height: $(window).height()-300,
			pageSize: 20,
			pageNumber:1
		});
		$(window).resize(function () {
			$('#tableList').bootstrapTable('resetView');
		});
	</script>
</body>
</html>