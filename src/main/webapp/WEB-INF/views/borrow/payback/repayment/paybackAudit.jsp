<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>还款查账已办页面</title>
<script type="text/javascript" src="${context}/js/payback/paybackAudit.js"></script> 
<script src='${context}/js/common.js' type="text/javascript"></script>
<script src="${context}/js/payback/historicalRecords.js" type="text/javascript"></script>
<script src="${context}/static/ckfinder/plugins/gallery/colorbox/jquery.colorbox-min.js"></script>
<link media="screen" rel="stylesheet" href="${context}/static/ckfinder/plugins/gallery/colorbox/colorbox.css" />
</head>
<body>
<div class="control-group">
	<div>
	<form id="auditForm" method="post" action="${ctx }/borrow/payback/paybackAudit/allpaybackAuditHavaTodoList">
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
        	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
			<input name="operateRole" type="hidden" value="${zhcz}" />
			<input name="zhcz" type="hidden" value="${zhcz}" />
            <tr>
                <td><label class="lab">客户姓名：</label>
                	<input value="${PaybackAuditEx.customerName }" type="text" name="customerName" class="input_text170"></input></td>
                <td><label class="lab">合同编号：</label>
                	<input value="${PaybackAuditEx.contractCode }" type="text" name="contractCode" class="input_text170"></input></td>
                <td><label class="lab">门店：</label>
                	<input id="txtStore" name="storeName" type="text" maxlength="20" class="txt date input_text170" value="${PaybackAuditEx.storeName }" /> 
					<i id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
					<input type="hidden" id="hdStore" name = "storeId"  value= '${PaybackAuditEx.storeId}' }>
                </td>
            </tr>
            <tr>
                <td>
                	<label class="lab">还款日：</label>
                	<select name="repaymentDate" class="select180">
						        <option value="">请选择</option>
						<c:forEach var="day" items="${dayList}">
								<option value="${day.billDay}"
								<c:if test="${PaybackAuditEx.repaymentDate ==day.billDay }">selected</c:if>>${day.billDay}</option>
						</c:forEach>
				   </select>
                </td>
                <td><label class="lab">存入银行：</label>
                	<select class="select180" name="storesInAccount">
							<option value="">请选择</option>
							<c:forEach var="item" items="${MiddlePerson }">
								<c:if test="${item.midBankName !='中和-中国工商银行' && item.midBankName !='中和-招商银行' && (zhcz==null || zhcz=='')}">
									<option value="${item.bankCardNo }" <c:if test="${PaybackAuditEx.storesInAccount == item.bankCardNo }">selected</c:if>>${item.midBankName}</option>
								</c:if>
								<c:if test="${zhcz!=null && zhcz!='' && (item.midBankName =='中和-中国工商银行' || item.midBankName =='中和-招商银行')}">
									<option value="${item.bankCardNo }" <c:if test="${PaybackAuditEx.storesInAccount == item.bankCardNo }">selected</c:if>>${item.midBankName}</option>
								</c:if>
							</c:forEach>
					</select>
                </td>
                <td><label class="lab">回盘结果：</label>
                	<select class="select180" name="dictPayResult">
                		<option value="">请选择</option>
                              <c:forEach items="${fns:getDictList('jk_counteroffer_result')}" var="dictPayResult">
                              	<c:if test="${dictPayResult.value=='2' || dictPayResult.value=='6'}">
                              		<option value="${dictPayResult.value }" <c:if test="${PaybackAuditEx.dictPayResult == dictPayResult.value }">selected</c:if>>${dictPayResult.label }</option>
                              	</c:if>
                              </c:forEach>
                	</select>
                </td>
            </tr>
            <tr id="T1" style="display:none">
	            <td><label class="lab">还款状态：</label>
                	<select class="select180" name="dictPayStatus" id="dictPayStatus">
                		<option value="">请选择</option>
                              <c:forEach items="${fns:getDictList('jk_repay_apply_status')}" var="dictPayStatus">
                              <c:if test="${dictPayStatus.value == '7'  || dictPayStatus.value=='8'  || dictStatus.value=='10' || dictPayStatus.value=='12'}">
                                   <option value="${dictPayStatus.value }" <c:if test="${PaybackAuditEx.dictPayStatus == dictPayStatus.value }">selected</c:if>>${dictPayStatus.label }</option>
                             </c:if>
                              </c:forEach>
                	</select>
                </td>
                <td><label class="lab">还款方式：</label>
                	<select class="select180" name="dictRepayMethod" id="dictRepayMethod">
                		<option value="">请选择</option>
                              <c:forEach items="${fns:getDictList('jk_repay_way')}" var="dictPayStatus">
                              <c:if test="${not (dictPayStatus.value eq 4)}">
                             	 <option value="${dictPayStatus.value }" <c:if test="${PaybackAuditEx.dictRepayMethod == dictPayStatus.value }">selected</c:if>>${dictPayStatus.label }</option>
                              </c:if>
                              </c:forEach>
                	</select>
                </td>
            	<td><label class="lab">查账日期：</label>
                 	<input id="beginDate" name="beginDate" type="text" readonly="readonly" maxlength="20" class="input_text70 Wdate"
									value="<fmt:formatDate value="${PaybackAuditEx.beginDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
									-
									<input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input_text70 Wdate"
									value="<fmt:formatDate value="${PaybackAuditEx.endDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
                 </td>
            </tr>
            <tr id="T2" style="display:none">
                <td><label class="lab">来源系统：</label>
                	<select class="select180" name="dictSourceType" id="dictSourceType">
                		<option value="">请选择</option>
                              <c:forEach items="${fns:getDictList('jk_new_old_sys_flag')}" var="dictSourceType">
                                   <option value="${dictSourceType.value }" <c:if test="${PaybackAuditEx.dictSourceType == dictSourceType.value }">selected</c:if>>${dictSourceType.label }</option>
                              </c:forEach>
                	</select>
                </td>
                <td><label class="lab">渠道：</label>
                	<select class="select180" name="loanMark" id="loanMark">
                		<option value="">请选择</option>
                              <c:forEach items="${fns:getDictList('jk_channel_flag')}" var="loanMark">
                                   <option value="${loanMark.value }" <c:if test="${PaybackAuditEx.loanMark == loanMark.value }">selected</c:if>>${loanMark.label }</option>
                              </c:forEach>
                	</select>
                </td>
                <td><label class="lab">实还金额：</label>
                	<input type="text" value="${PaybackAuditEx.beginOutReallyAmount }" id="beginOutReallyAmount" name="beginOutReallyAmount"  class="input_text170" onchange="checkVaiolate(this.value)">
                         -
                  		<input type="text" value="${PaybackAuditEx.endOutReallyAmount }" id="endOutReallyAmount" name="endOutReallyAmount"  class="input_text170"  onchange="checkVaiolate(this.value)">
                  </td>
            </tr>
        </table>
		</div>
        <div class="tright pr30 pb5">
        	<button class="btn btn-primary" id="paybackAuditSubmit">搜索</button>
			<input type="button" class="btn btn-primary" id="clearBtn" value="清除" />
			<div style="float:left;margin-left:45%;padding-top:10px">
				  <img src="${context }/static/images/up.png" id="showMore"></img>
				</div>
       </div>
      </form>
      </div>
    <p class="mb5">
    	<button class="btn btn-small" name="exportExcel">导出EXCEL</button>
    	<label class="red">查账总金额:</label>
		<label class="red" id="total_money"></label>&nbsp;
		<label class="red">查账总笔数:</label>
		<label class="red" id="total_num"></label></p>
    <div class="box5">
    <table id="tableList">
      <thead>
        <tr>
            <th><input type="checkbox" id="checkAll"/></th>
            <th>合同编号</th>
            <th>客户姓名</th>
            <th>门店名称</th>
            <th>批借期数</th>
            <th>首期还款日</th>
            <th>存入银行</th>
            <th>还款方式</th>
            <th>申请还款金额</th>
            <th>实还金额</th>
            <th>还款类型</th>
            <th>查账日期</th>
            <th>还款日</th>
            <th>借款状态</th>
            <th>还款状态</th>
            <th>回盘结果</th>
            <th>蓝补金额</th>
            <th>操作</th>
        </tr>
        </thead>
        <c:forEach items="${waitPage.list}" var="item" varStatus="num">
	        <tr>
	        	<td><input type="checkbox" name="checkBox" class="checkAll1" applyAmount="${item.applyMoneyPayback}" value="${item.payBackId}" /></td>
	        	<td>${item.contractCode}</td>
	        	<td>${item.customerName}</td>
	        	<td>${item.orgName}</td>
	        	<td>${item.contractMonths}</td>
	        	<td><fmt:formatDate value="${item.contractReplayDay}" type="date"/></td>
	        	<td>${item.storesInAccountname}</td>
	        	<td>${item.dictRepayMethodLabel}</td>
	        	<td><fmt:formatNumber value='${item.applyMoneyPayback}' pattern="0.00"/></td>
	        	<td><fmt:formatNumber value='${item.reallyAmount}' pattern="0.00"/></td>
	        	<td>${item.dictPayUseLabel}</td>
	        	<td><fmt:formatDate value="${item.modifyTime }" type="date"/></td>
	        	<td><fmt:formatDate value="${item.contractReplayDay}" pattern="dd"/></td>
	        	<td>${item.dictLoanStatusLabel}</td>
	        	<td>${item.dictPayStatusLabel}</td>
	        	<td>${item.dictPayResultLabel }</td>
	        	<td><fmt:formatNumber value='${item.paybackBuleAmount}' pattern="0.00"/></td>
	        	<td><input type="button" name="seePaybackAudit" class="btn_edit" value="查看" />
					<input type="hidden" value="${item.payBackId}" />
	        		<input type="button" class="btn_edit" onclick="showNoDeductPaybackHistory('','${item.payBackId }','');" value="历史" />
	        	</td>
	        </tr>
        </c:forEach>
    </table>
    </div>
<div class="pagination">${waitPage}</div>
</div>
<script type="text/javascript">
		$('#tableList').bootstrapTable({
			method: 'get',
			cache: false,
			height: $(window).height()-250,
			pageSize: 20,
			pageNumber:1
		});
		$(window).resize(function () {
			$('#tableList').bootstrapTable('resetView');
		});
	</script>
</body>
</html>
