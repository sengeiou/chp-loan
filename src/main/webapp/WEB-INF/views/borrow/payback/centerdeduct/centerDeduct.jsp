<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>集中划扣申请</title>
<script type="text/javascript" src="${context}/js/payback/centerHkApply.js"></script>
<script src='${context}/js/common.js' type="text/javascript"></script>
<script type="text/javascript">
function page(n,s) {
	if (n) $("#pageNo").val(n);
	if (s) $("#pageSize").val(s);
	$("#CenterapplyPayForm").attr("action", "${ctx}/borrow/payback/jzapply/list");
	$("#CenterapplyPayForm").submit();
	return false;
	
}
$(document).ready(function() {
	loan.getstorelsit("txtStore","hdStore");
});

$(document).ready(function() {
	centerApply.centerReduct();
	var msg = "${message}"
	if (msg != "" && msg != null) {
		art.dialog.alert(msg);
	}
});

function stateFormatter(value, row, index) {
	var flag = row.bankTopFlag 
	    if (flag === 1) {
	        return {
	            checked: true
	        }
	    }
	    return value;
 }

</script>
</head>
<body>

   <div class="control-group">
		<form method="post" action="${ctx}/borrow/payback/jzapply/list" id="CenterapplyPayForm">
			<input id="pageNo" name="pageNo" type="hidden"	value="${page.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" />
			<input id="ids" name="search_id" type="hidden"	value="${paramMap.id}" /> 
			<input id="platFormId" name="search_platFormId" type="hidden" /> 
		
				<table class=" table1" cellpadding="0" cellspacing="0" border="0"
					width="100%">
					<tr>
						<td><label class="lab">客户姓名：</label><input type="text"
							class="input_text178" name="search_customerName" value="${paramMap.customerName}"></td>
						<td><label class="lab">合同编号：</label><input type="text"
							class="input_text178" name="search_contractCode" value="${paramMap.contractCode}"></td>
						<td><label class="lab">还款日：</label>
						<select name="search_monthPayDay" class="select180">
						        <option value="">请选择</option>
						<c:forEach var="day" items="${dayList}">
								<option value="${day.billDay}"
								<c:if test="${paramMap.monthPayDay==day.billDay }">selected</c:if>>${day.billDay}</option>
								</c:forEach>
						</select>
						
						</td>
					</tr>
					<tr>
					<td><label class="lab">合同到期日：</label><input name="search_contractReplayDay" id="search_contractReplayDay"  type="text" readonly="readonly" maxlength="40" class="input_text70 Wdate"
					    value="<fmt:formatDate value="${paramMap.contractReplayDay}" pattern="yyyy-MM-dd"/>"
					     onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,maxDate:'#F{$dp.$D(\'search_contractEndDay\')}'});" style="cursor: pointer"/>-<input name="search_contractEndDay" id="search_contractEndDay"  type="text" readonly="readonly" maxlength="40" class="input_text70 Wdate"
					    value="<fmt:formatDate value="${paramMap.contractEndDay}" pattern="yyyy-MM-dd"/>"
					     onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'search_contractReplayDay\')}'});" style="cursor: pointer"/>
					</td>
					<td><label class="lab">门店：</label><input id="txtStore" name="search_storesName"
						type="text" maxlength="20" class="txt date input_text178"
						value="${paramMap.storesName}" /> <i id="selectStoresBtn"
						class="icon-search" style="cursor: pointer;"></i>
						<input type="hidden" id="hdStore" name='search_stores' value="${paramMap.stores}">
						</td>
				    <td ><label class="lab">来源系统：</label><select name="search_dictSourceType" class="select180">
								<option value="">请选择</option>
								<c:forEach var="sys" items="${fns:getDictList('jk_new_old_sys_flag')}">
								<option value="${sys.value }"
								<c:if test="${paramMap.dictSourceType==sys.value }">selected</c:if>>${sys.label }</option>
								</c:forEach>
								</select>
						</td>
					</tr>
					<tr id="T1" style="display: none">
						<td><label class="lab">渠道：</label>
						<sys:multipleMark markClick="selectMarkBtn" markName="search_applyMarkName" markId="mark"></sys:multipleMark>
						<input id="search_applyMarkName" type="text" class="input_text178" name="search_applyMarkName"  value='${paramMap.applyMarkName}'>&nbsp;
						<i id="selectMarkBtn" class="icon-search" style="cursor: pointer;"></i> 
						<input type="hidden" id="mark" name='search_mark' value='${paramMap.mark}'>
						</td>
						<td >
						<sys:multipleBank bankClick="selectBankBtn" bankName="search_applyBankName" bankId="bankId"></sys:multipleBank>
						<label class="lab">开户行名称：</label><input
							id="search_applyBankName" type="text" class="input_text178" name="search_applyBankName"  value='${paramMap.applyBankName}'>&nbsp;<i id="selectBankBtn"
						class="icon-search" style="cursor: pointer;"></i> 
						<input type="hidden" id="bankId" name='search_bank' value='${paramMap.bank}'>
						</td>
						
						<td><label class="lab" >还款状态：</label><select class="select180" name="search_dictPayStatus">
									<option value="">全部</option>
			                        <c:forEach var="repay" items="${codeName}">
			                             <option value="${repay.code }"
										 <c:if test="${paramMap.dictPayStatus==repay.code }">selected</c:if>>${repay.name }</option>
									</c:forEach>
							</select>
						</td>
					    </tr>
						<tr id="T2" style="display: none">
							<td><label class="lab">划扣平台：</label><select class="select180"  name="search_dictDealType">
								<option value="">请选择</option>
			                        <c:forEach var="plat" items="${fns:getDictList('jk_deduct_plat')}">
										<option value="${plat.value }"
										<c:if test="${plat.label!='ZCJ金账户' }">
											<c:if test="${paramMap.dictDealType==plat.value }">selected</c:if>>${plat.label }</option>
										</c:if>
									</c:forEach>
								</select>
							</td>
							
							<td><label class="lab">模式：</label><select class="select180" name="search_model">
				                <option value="">请选择</option>
						        <c:forEach var="flag" items="${fns:getDictList('jk_loan_model')}">
									<option value="${flag.value }"
									<c:if test="${paramMap.model==flag.value }">selected</c:if>>
									<c:if test="${flag.value=='1' }">${flag.label}</c:if>
									<c:if test="${flag.value!='1' }">非TG</c:if>
									</option>
								</c:forEach>
				                </select>
				             </td>
						</tr>
				</table>
				</form>
				<div class="tright pr30 pb5">
					<button class="btn btn-primary" onclick="document.forms[0].submit();">搜索</button>
				   <button class="btn btn-primary"  onclick = "clearBtns()">清除</button>
				 <div style="float: left; margin-left: 45%; padding-top: 10px">
	          <img src="${context}/static/images/up.png" id="showMore"></img>
	 </div>
	</div>
  </div>
	<p class="mb5">
		<input type="button" class="btn btn-small" id="checkAll1" onclick="selectAll()" value="全选"></input>
	
		<button class="btn btn-small" id="centerApply">集中划扣申请</button>
	
		<button class="btn btn-small" name = "exportExcel">导出Excel</button>
		
		<input type="hidden" id="totalbk" value="${numTotal.total}">
		<input type="hidden" id="numbk" value="${numTotal.num}">
		
	<span class="red">划扣总金额:</span>
		<!--  <input class="red" id="total_money" value="">-->
		<span class="red" id="total_money">${numTotal.total}</span> </input>&nbsp;<span class="red">划扣总笔数:</span>
		<!--  <input class="red" id="total_num" value=""></input>-->
		<span class="red" id="total_num">${numTotal.num}</span>
	</p>
	<div class="box5">
	<%-- <sys:columnCtrl pageToken="centerdeductApply"></sys:columnCtrl> --%>
		<table class="table  table-bordered table-condensed table-hover " id="customerTab">
			<thead>
			<tr>
				<th><input type="checkbox" class="checkbox" id="checkAll" /></th>
				<th>序号</th>
				<th>合同编号</th>
				<th>客户姓名</th>
				<th>门店名称</th>
				<th>开户行名称</th>
				<th>合同到期日</th>
				<th>期数</th>
				<th>还款日</th>
				<th>月还期供金额</th>
				<th>已还金额</th>
				<th>当期应还金额</th>
				<th>蓝补金额</th>
				<th>借款状态</th>
				<th>期供状态</th>
				<th>还款状态</th>
				<th>渠道</th>
				<th>模式</th>
				<th>划扣平台</th>
				<th>操作</th>
			</tr>
			</thead>
			<c:forEach var="paybacklist" items="${page.list }" varStatus="sta">
				<tr>
					<td><input type="checkbox" name="checkBox" class="userCardInfo"
						value="${paybacklist.repayAmount }"/>
						<!-- 期供表中的ID -->
						 <input type="hidden" class="qiGongId" name="monthId" value="${paybacklist.monthId }"/>
						 <!-- 待还款列表的ID -->
						 <input type="hidden" name="id" value="${paybacklist.id}"/>
						 <!-- 还款主表的ID --> 
					     <input type="hidden" name="mainId" value="${paybacklist.payback.id }"/>
					     <input type="hidden" name="contractCode" value="${paybacklist.contractCode}"/>
					   </td>
					<td>${sta.count}</td> 
					<td>${paybacklist.contractCode }</td>
					<td>${paybacklist.customerName }</td>
					<td>${paybacklist.storesName }</td>
					<td>
					 ${paybacklist.applyBankNameLabel} 
					</td>
					<td><fmt:formatDate
							value="${paybacklist.contract.contractEndDay }" type="date" />
					</td>
					<td>${paybacklist.months }</td>
					<td>${paybacklist.monthDay }</td>
					<td class="payMoney"><fmt:formatNumber value="${paybacklist.monthlySupplyAmount }" pattern='0.00'/></td>
					<!--  <td>${CenterHkApply.completeMoney }</td>-->
					 <td><fmt:formatNumber value="${paybacklist.completeAmount }" pattern='0.00'/></td>
					<td><fmt:formatNumber value="${paybacklist.repayAmount}" pattern='0.00'/></td>
					<td><fmt:formatNumber value="${paybacklist.buleAmount }" pattern='0.00'/></td>
					<td>
					 ${paybacklist.loanInfo.dictLoanStatusLabel} 
					</td>
					<td>
					 ${paybacklist.dictMonthStatusLabel}
					</td>
					<td>
				     <c:if test="${paybacklist.status=='0'}">
					    未申请
					 </c:if>
					 <c:if test="${paybacklist.status=='7'}">
					  还款退回
					 </c:if>
					</td>
				    <td>${paybacklist.markLabel}</td>
				    <td>${paybacklist.modelLabel}</td>
					<td>
						${paybacklist.dictDealTypeLabel}
					</td>
					<td >
				    <button class="btn_edit" value="${paybacklist.contractCode }" role="button" data-toggle="modal" >还款账号</button>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div class="pagination">${page}</div>

	<!-- 选择还款账号弹出框 -->
<div  id="myModal" style="display:none">
    <div class="modal-body table-bordered table-condensed table-hover" >
       <table id="centerHKTab" cellspacing="0" cellpadding="0" border="0"  class="table table-striped"  width="100%" >
       <thead>
       <tr>
           <th data-radio="true" data-formatter="stateFormatter">选择</th>
           <th data-field="bankAccountName">账号姓名</th>
	       <th data-field="bankAccount">划扣账号</th>
		   <th data-field="bankName">开户行</th>
		   <th data-field="bankBranch">具体支行</th>
		   <th data-field="bankSigningPlatform">划扣平台</th>
           <th data-field="fileName">附件</th>
           <th data-field="id"  class="hidden" ></th>
       </tr>
       </thead>
       </table>
   </div>
</div>

<!-- 集中划扣申请选择划扣平台对话框 -->	
<div  id="myModalPlat" style="display: none">
        <table class=" table4" cellpadding="0" cellspacing="0" border="0" width="100%" id="tpTable">
            <tr>
                <td><label class="lab">划扣平台：</label>
                <select class="select78" id="platForm">
                    <option value="">请选择</option>
                	<c:forEach var="plat" items="${fns:getDictList('jk_deduct_plat')}">
                		<c:if test="${plat.label!='ZCJ金账户' }">
							<option value="${plat.value }"
							<c:if test="${paramMap.dictDealType==plat.value }">selected</c:if>>${plat.label }</option>
						</c:if>
					</c:forEach>
                </select>
                </td>
            </tr>
        </table>
       </div>
       <script type="text/javascript">

	  $("#customerTab").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
		$('#customerTab').bootstrapTable({
			method: 'get',
			cache: false,
			height: $(window).height()-260,
			
			pageSize: 20,
			pageNumber:1
		});
		$(window).resize(function () {
			$('#customerTab').bootstrapTable('resetView');
		});
	</script>
</body>
</html>