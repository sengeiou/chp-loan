<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>提前结清已办</title>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context}/js/payback/earlySettlement.js?time=111"></script>
<script src='${context}/js/common.js' type="text/javascript"></script>
<script src="${context}/js/payback/historicalRecords.js" type="text/javascript"></script>
<script src="${context}/static/ckfinder/plugins/gallery/colorbox/jquery.colorbox-min.js"></script>
<link media="screen" rel="stylesheet" href="${context}/static/ckfinder/plugins/gallery/colorbox/colorbox.css" />
</head>
<body>
<div class="control-group">
   	<form id="auditForm" action="${ctx}/borrow/payback/earlySement/earlySettlementHaveTodoList" method="post" >
   			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
			<input id="idVals" name="idVals" type="hidden" value="" />
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td><label class="lab">客户姓名：</label>
                	<input value="${EarlySettlementEx.customerName}" type="text" name="customerName" class="input_text178"></input></td>
                <td><label class="lab">合同编号：</label>
                	<input value="${EarlySettlementEx.contractCode}" type="text" name="contractCode" class="input_text178"></input></td>
                <td><label class="lab">门店：</label>
                    <input id="txtStore" name="storeName" type="text" maxlength="20" class="txt date input_text178" value="${EarlySettlementEx.storeName}" /> 
					<i id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
					<input type="hidden" id="hdStore" name = "store"  value= '${EarlySettlementEx.store}' }>
                </td>
            </tr>
            <tr>
                <td><label class="lab">还款状态：</label>
                	<select class="select180" name="dictPayStatus">
                		<option value="">请选择</option>
                              <c:forEach items="${fns:getDictList('jk_repay_status')}" var="dictPayStatus">
                                   <option value="${dictPayStatus.value }" <c:if test="${EarlySettlementEx.dictPayStatus == dictPayStatus.value }">selected</c:if>>${dictPayStatus.label }</option>
                              </c:forEach>
                	</select>
                </td>
                <td><label class="lab">来源系统：</label>
                	<select class="select180" name="dictSourceType">
                		<option value="">请选择</option>
                              <c:forEach items="${fns:getDictList('jk_new_old_sys_flag')}" var="dictSourceType">
                                   <option value="${dictSourceType.value }" <c:if test="${EarlySettlementEx.dictSourceType == dictSourceType.value }">selected</c:if>>${dictSourceType.label }</option>
                              </c:forEach>
                	</select>
                </td>
                <td><label class="lab">渠道：</label>
                	<select class="select180" name="loanFlag">
                		<option value="">请选择</option>
                              <c:forEach items="${fns:getDictList('jk_channel_flag')}" var="loanFlag">
                                   <option value="${loanFlag.value }" <c:if test="${EarlySettlementEx.loanFlag == loanFlag.value }">selected</c:if>>${loanFlag.label }</option>
                              </c:forEach>
                	</select>
                </td>
            </tr>
            <tr id="T1" style="display:none">
                <td><label class="lab">提前结清日期：</label>
                		<label><input id="beginDate" name="beginDate" type="text" readonly="readonly" maxlength="20" class="input_text70 Wdate"
								value="<fmt:formatDate value="${EarlySettlementEx.beginDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
						</label>-<input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input_text70 Wdate"
								value="<fmt:formatDate value="${EarlySettlementEx.endDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
				</td>
                <td><label class="lab">是否电销：</label>
                	<select class="select180" name="loanIsPhone">
                		<option value="">请选择</option>
                              <c:forEach items="${fns:getDictList('jk_telemarketing')}" var="loanIsPhone">
                                   <option value="${loanIsPhone.value }" <c:if test="${EarlySettlementEx.loanIsPhone == loanIsPhone.value }">selected</c:if>>${loanIsPhone.label }</option>
                              </c:forEach>
                	</select>
                </td>
                <td><label class="lab">模式：</label> 
						<select name="model" class="select180">
							<option value=''>请选择</option>
							<c:forEach items="${fns:getDictList('jk_loan_model')}" var="loanmodel">
	                                  <option value="${loanmodel.value }" <c:if test="${payback.model == loanmodel.value }">selected</c:if>>
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
        	<div class="tright pr30 pb5">
	        		<button class="btn btn-primary" id="btn-submit">搜索</button>
				 <input type="button" class="btn btn-primary" id="clearBtn" value="清除" />
			 	<div style="float:left;margin-left:45%;padding-top:10px">
				 <img src="${context}/static/images/up.png" id="showMore" onclick="javascript:show();"></img>
				</div>
            </div>
	  </form>
	  </div>
    <div>
    <p class="mb5">
    		<button class="btn btn-small" onclick="selectAll(this)">全选</button>
			<button class="btn btn-small" id="exportExcel">导出Excel</button>
	</p>
    <table id="tableList">
       <thead>
        <tr>
        	<th></th>
            <th>门店名称</th>
            <th>合同编号</th>
            <th>客户姓名</th>
            <th>合同金额</th>
			<th>已收催收服务费金额</th>
            <th>放款金额</th>
            <th>批借期数</th>
            <th>首期还款日</th>
			<th>最长逾期天数</th>
            <th>提前结清应还款总额</th>
            <th>申请还款金额</th>
            <th>实际提前结清金额</th>
            <th>还款状态</th>
            <th>提前结清日期</th>
            <th>借款状态</th>
            <th>减免人</th>
            <th>减免金额</th>
            <th>渠道</th>
            <th>模式</th>
            <th>是否电销</th>
            <th>操作</th>
        </tr>
        </thead>
        <c:forEach items="${waitPage.list}" var="item" varStatus="num">
					<tr>
						<td>
				            <input type="checkbox" name="checkBox"   value="${item.id}" />
				        </td>
						<td>${item.orgName}</td>
						<td>${item.contractCode}</td>
						<td>${item.customerName}</td>
						<td><fmt:formatNumber value='${item.contractMoney}' pattern="#,##0.00" /></td>
						<td><fmt:formatNumber value='${item.urgeDecuteMoeny}' pattern="#,##0.00" /></td>
						<td><fmt:formatNumber value='${item.grantAmount}' pattern="#,##0.00" /></td>
						<td>${item.contractMonths}</td>
						<td><fmt:formatDate value="${item.contractReplayDate}" type="date"/></td>
						<td>${item.paybackMaxOverduedays}</td>
						<td><fmt:formatNumber value='${item.modifyYingpaybackBackMoney}' pattern="#,##0.00" /></td>
						<td><fmt:formatNumber value='${item.applyAmountPaybac}' pattern="#,##0.00" /></td>
						<td><fmt:formatNumber value='${item.actualModifyMoney}' pattern="#,##0.00" /></td>
						<td>${item.chargeStatusLabel}</td>
						<td><fmt:formatDate value="${item.modifyDate}" type="date"/></td>
						<td>${item.dictLoanStatusLabel}</td>
						<td>${item.reductionBy}</td>
						<td><fmt:formatNumber value='${item.monthBeforeReductionAmount}' pattern="#,##0.00" /></td>
						<td>${item.loanFlagLabel}</td>
						<td>${item.modelLabel}</td>
						<td>${item.loanIsPhoneLabel}</td>
						<td><input type="button" name="seeEarlySettlement" class="btn_edit" value="查看" />
							<input type="hidden" value="${item.id}" />
							<input type="hidden" value="${item.rPaybackId}" />
							<input type="button" class="btn_edit" onclick="getqueryManualOperation('${item.contractCode}','${item.paybackMonthId}','');" value="还款明细" />
							<input type="button" class="btn_edit" onclick="showNoDeductPaybackHistory('','${item.id}','');" value="历史" />
						</td>
					</tr>
		</c:forEach>
     </table>
      </div>
    <div class="pagination">${waitPage}</div>
	  </div>
	  <script type="text/javascript">

	  $("#tableList").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
		$('#tableList').bootstrapTable({
			method: 'get',
			cache: false,
			height: $(window).height()- 200,
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
