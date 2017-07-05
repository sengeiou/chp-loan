<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>门店已办页面</title>
<script type="text/javascript" src="${context}/js/payback/stresAlready.js"></script>
<script src='${context}/js/common.js' type="text/javascript"></script>
</head>
<body>
    <div class="control-group">
    <form id="auditForm"  method="post" action="${ctx}/borrow/payback/loanServices/allStoresAlreadyDoList">
    			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" /> 
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
				<input  name="menuId"  id ="menuId" type="hidden" value="${menuId}" /> 
				
           <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
             <tr>
                <td><label class="lab">客户姓名：</label>
                <input value="${LoanServiceBureauEx.customerName}" type="text" name="customerName" class="input_text178"></input></td>
                <td><label class="lab">合同编号：</label>
                <input value="${LoanServiceBureauEx.contractCode}" type="text" name="contractCode" class="input_text178"></input></td>
				<td><label class="lab">申请日期：</label>
				<input id="contractReplayDay" name="createTime" type="text" readonly="readonly" maxlength="20" class="input_text178 Wdate"
								value="<fmt:formatDate value="${LoanServiceBureauEx.createTime}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
				</td>
            </tr>
            <tr> 
			   	<td><label class="lab">回盘结果：</label>
			   	<select class="select180" name="splitBackResult">
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
                              <c:if test="${not (dictPayUse.value eq 0 || dictPayUse.value eq 1 || dictPayUse.value eq 2)}">
                                   <option value="${dictPayUse.value }" <c:if test="${LoanServiceBureauEx.dictPayUse == dictPayUse.value }">selected</c:if>>${dictPayUse.label }</option>
                              </c:if>
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
               <td><label class="lab">来源系统：</label>
               <select class="select180" name="dictSourceType">
                		<option value="">请选择</option>
                              <c:forEach items="${fns:getDictList('jk_new_old_sys_flag')}" var="dictSourceType">
                                   <option value="${dictSourceType.value }" <c:if test="${LoanServiceBureauEx.dictSourceType == dictSourceType.value }">selected</c:if>>${dictSourceType.label }</option>
                              </c:forEach>
                	</select>
               </td>
               <td><label class="lab">渠道：</label>
               <select class="select180" name="loanMark">
                		<option value="">请选择</option>
                              <c:forEach items="${fns:getDictList('jk_channel_flag')}" var="loanMark">
                                   <option value="${loanMark.value }" <c:if test="${LoanServiceBureauEx.loanMark == loanMark.value }">selected</c:if>>${loanMark.label }</option>
                              </c:forEach>
                	</select>
               </td>
               <td><label class="lab">是否电销：</label>
               <select class="select180" name="customerTelesalesFlag">
                		<option value="">请选择</option>
                              <c:forEach items="${fns:getDictList('jk_telemarketing')}" var="customerTelesalesFlag">
                                   <option value="${customerTelesalesFlag.value }" <c:if test="${LoanServiceBureauEx.customerTelesalesFlag == customerTelesalesFlag.value }">selected</c:if>>${customerTelesalesFlag.label }</option>
                              </c:forEach>
                	</select>
               </td>
            </tr>
            <tr id="T2" style="display: none;">
                <td><label class="lab">还款方式：</label> 
							<select class="select180" name="dictRepayMethod">
		                			  <option value="">请选择</option>
		                              <c:forEach items="${fns:getDictList('jk_repay_way')}" var="dictRepayMethod">
		                                   <option value="${dictRepayMethod.value }" <c:if test="${LoanServiceBureauEx.dictRepayMethod== dictRepayMethod.value }">selected</c:if>>${dictRepayMethod.label }</option>
		                              </c:forEach>
		                	</select>
			   </td>
			   
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
                <td>
                  <label class="lab">还款状态：</label>
                  <select class="select180" name="dictPayStatus">
                    <option value="">请选择</option>
                    <c:forEach var="applyStatus" items="${fns:getDictList('jk_repay_apply_status') }">
                     <c:if test="${not (applyStatus.value eq 0 || applyStatus.value eq 1 || applyStatus.value eq 4 || applyStatus.value eq 5
                     || applyStatus.value eq 6 || applyStatus.value eq 11) }">
                       <option value="${applyStatus.value }">
                        <!--   <c:if test="${loanServiceBureauEx.dictPayStatus == applyStatus.value}">selected</c:if>>-->
                          ${applyStatus.label }
                       </option>
                       </c:if>
                    </c:forEach>
                    
                   <c:forEach var="paybackStatus" items="${fns:getDictList('jk_charge_against_status') }">
                       <option value="${paybackStatus.value }">
                       <!-- <c:if test="${loanServiceBureauEx.dictPayStatus == paybackStatus.value }">selected</c:if>> --> 
                         ${paybackStatus.label }
                        </option>
                    </c:forEach>
                     
                  </select>
                </td>
            </tr>
            <tr id="T3" style="display: none;">
                 <td><label class="lab">回盘日期：</label>
                 
				 		<%-- <label><input id="beginDate" name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
								value="<fmt:formatDate value="${LoanServiceBureauEx.beginDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
						</label>-<label>
							<input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
								value="<fmt:formatDate value="${LoanServiceBureauEx.endDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
						</label> --%>
				   <input id="beginDate" name="beginDate" type="text" readonly="readonly" maxlength="20" class="input_text178 Wdate"
					value="<fmt:formatDate value="${LoanServiceBureauEx.beginDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
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
	<div>
		<table id="tableList">
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
					<th>申请日期</th>
					<th>回盘结果</th>
					<th>回盘日期</th>
					<th>渠道</th>
					<th>模式</th>
					<th>是否电销</th>
					<th>失败原因</th>
					<th>操作</th>
				</tr>
			</thead>
			<c:forEach items="${waitPage.list}" var="item" varStatus="num">
				<tr align="center">
					<td>${num.index + 1 }</td>
					<td>${item.contractCode}</td>
					<td>${item.customerName}</td>
					<td>${item.orgName}</td>
					<td><fmt:formatDate value="${item.contractEndDay}" type="date" /></td>
					<td>${item.dictLoanStatusLabel}</td>
					<td>${item.repaymentDate}</td>
					<td><fmt:formatNumber value='${item.applyMoneyPayback}'
							pattern="#,##0.00" /></td>
					<td><fmt:formatNumber value='${item.applyReallyAmount}'
							pattern="#,##0.00" /></td>
					<td>${item.dictPayUseLabel}</td>
					<td>${item.dictRepayMethodLabel}</td>
					<td>${item.dictPaybackStatusLabel}</td>
					<td><fmt:formatDate value="${item.createTime}" type="date" /></td>
					<td>${item.splitBackResultLabel}</td>
					<td><fmt:formatDate value="${item.modifyTime}" type="date" /></td>
					<td>${item.loanMarkLabel}</td>
					<td>${item.modelLabel}</td>
					<td>${item.customerTelesalesFlagLabel}</td>
					<td>${item.failReason}</td>
					<td>
					<c:if test="${jkhj_mdmdyb_ckbtn==null }">
					<input type="button" name="seeStresAlready" class="btn_edit" value="查看" /> 
					</c:if>
						<input type="hidden"
						value="${item.ids}" /> <input type="hidden"
						value="${item.dictPayUse}" />
				    <c:if test="${jkhj_mdmdyb_lsbtn==null }"> 
						 <input type="button"
						class="btn_edit"
						onclick="showNoDeductPaybackHistory('${item.rPaybackId}','${item.ids}','');"
						value="历史" />
				 	</c:if> 
						</td>
				</tr>
			</c:forEach>
		</table>
	</div>

	<div class="pagination">${waitPage}</div>
</body>
</html>