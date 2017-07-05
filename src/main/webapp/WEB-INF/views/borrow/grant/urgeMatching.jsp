<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript" src="${context }/js/grant/urgeMatchingPayback.js"></script>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src='${context}/js/bootstrap.autocomplete.js' ></script>
<script type="text/javascript" src="${context }/js/payback/dateUtils.js"></script>
<meta name="decorator" content="default" />
<script type="text/javascript">
$(document).ready(function(){
	var msg = "${message}";
	if (msg != "" && msg != null) {
		art.dialog.alert(msg);
	}
});
</script>
</head>
<body>
	<div class="control-group">
		<form id="urgeMatchingForm" action="${ctx}/borrow/grant/urgeCheckMatch/urgeCheckMatchInfo" method ="post" >
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
			<div>
				<tr>
					<td><label class="lab">客户姓名：</label> <input type="text" value="${urgeServicesMoneyEx.customerName}" name="customerName" class="input-medium"/></td>
					<td><label class="lab">合同编号：</label> <input type="text" value="${urgeServicesMoneyEx.contractCode}" name="contractCode" class="input-medium"/></td>
					<td><label class="lab">申请金额：</label><input type="text" id = "urgeApplyAmount" value="${urgeServicesMoneyEx.urgeServicesCheckApply.urgeApplyAmount}" name="urgeServicesCheckApply.urgeApplyAmount" class="input-medium"/>
				</tr>
				<tr>
					<td><label class="lab">门店：</label> 
						<input id="txtStore" readonly="readonly" name="loanInfo.storeName" type="text" maxlength="20" class="txt date input_text178" value="${urgeServicesMoneyEx.storeName}"/> 
							<i id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
						<input type="hidden" id="hdStore" name="storeId" value="${urgeServicesMoneyEx.storeId}">
					</td>
					<td><label class="lab">渠道：</label> 
						<select class="select180" name="loanFlag">
                			<option value="">请选择</option>
                              <c:forEach items="${fns:getDictList('jk_channel_flag')}" var="loanMark">
                                   <option value="${loanMark.value }" <c:if test="${urgeServicesMoneyEx.loanFlag == loanMark.value }">selected</c:if>>${loanMark.label}</option>
                              </c:forEach>
                		</select>
					</td>
				</tr>
			</div>
			<div >
				<tr id="T1" Style="display:none">
					<td><label class="lab">存入账户：</label>
						<select class="select180" name="middlePerson.bankCardNo">
							<option value="">请选择</option>
							<c:forEach var="item" items="${middlePersonList}">
								<option value="${item.bankCardNo }" <c:if test="${urgeServicesMoneyEx.middlePerson.bankCardNo==item.bankCardNo }">selected</c:if>>${item.midBankName }</option>
							</c:forEach>
						</select>
					</td>
					<td><label class="lab">借款产品：</label><select class="select180" name="productCode"><option value="">请选择</option>
				    <c:forEach items="${productList}" var="card_type">
					<option value="${card_type.productCode}" <c:if test="${urgeServicesMoneyEx.productCode==card_type.productCode}">selected</c:if>>${card_type.productName}</option>
					 </c:forEach>
				    </select></td>
				</tr>
			</div>
			</table>
		</form>
		<div style="float:left;margin-left:45%;padding-top:10px">
			<img src="${context }/static/images/up.png" id="showMore" onclick="show();"></img>
		</div>
		<div class="tright pr30 pb5">
			<input type="button" class="btn btn-primary" id="urgeMatchingSerachBtn" value="搜索">
			<input type="button" class="btn btn-primary" id="urgeMatchingClearBtn" value="清除">
		</div>
	</div>
	<div>
		<p class="mb5">
			<button class="btn btn-small" id="urgeBatchMatchingBtn">批量匹配</button>
			<button id="batchBackMathcingBtn" role="button" class="btn btn-small">批量匹配退回</button>
		</p>
	</div>
	<div id="matchingList" class="box5">
		<form id="matchingInfoForm" action="${ctx}/borrow/grant/urgeCheckMatch/form" method="post">
		<input type="hidden" id = "matchingContractCode" name="contractCode">
		<input type="hidden" id = "applyId" name="id">
		<table id="urgeMatchingTab">
			<thead>
				<tr height="37px">
					<th data-field="state" data-checkbox="true"></th>
					<th data-field="urgeId" data-visible="false" data-switchable="false" class="hidden">urgeId</th>
					<th data-field="id"data-visible="false"  data-switchable="false" class="hidden" >ID</th>
					<th data-field="applyIds" data-visible="false" data-switchable="false" class="hidden">申请表ID</th>
					<th data-field="paybackBuleAmount" data-visible="false" data-switchable="false" class="hidden">蓝补金额</th>
					<th data-field="contractCode" >合同编号</th>
		            <th data-field="customerName" >客户姓名</th>
					<th data-field="storeName" >门店名称</th>
		            <th data-field="midBankName" >存入银行</th>
		            <th data-field="productName">借款产品</th>
		            <th data-field="contractMonths" >批借期数</th>
		            <th data-field="urgeApplyDate" >查账申请日期</th>
		            <th data-field="dictLoanStatusLabel" >借款状态</th>
		            <th data-field="contractAmount" >合同金额</th>
		            <th data-field="grantAmount" >放款金额</th>
		            <th data-field="feeCredit">征信费</th>
		            <th data-field="feePetition">信访费</th>
		            <th data-field="feeSum">费用总计</th>
		            <th data-field="urgeMoeny" >催收服务费总金额</th>
		            <th data-field="waitUrgeMoeny" >待催收金额</th>
		            <th data-field="urgeApplyAmount" >申请查账金额</th>
		            <th data-field="loanFlagLabel" >渠道</th>
		            <th>操作</th>
				</tr>
				</thead>
				<tbody id="applyPayMatchingListBody">
					<c:forEach items="${paybackApplyList.list}" var="item">
						<tr>
							<td></td>
							<td>${item.urgeId}</td>
							<td>${item.id}</td>
							<td>${item.urgeServicesCheckApply.id}</td>
							<td>${item.payback.paybackBuleAmount}</td>
							<td>${item.contractCode}</td>
							<td>${item.customerName}</td>
							<td>${item.storeName}</td>
							<td>${item.middlePerson.midBankName }</td>
							<td>${item.productName}</td>
							<td>${item.contractMonths}</td>
							<td><fmt:formatDate value="${item.urgeServicesCheckApply.urgeApplyDate}" type="date"/></td>
							<td>${item.dictLoanStatusLabel}</td>
							<td><fmt:formatNumber value='${item.contractAmount}' pattern="#,##0.00"/></td>
							<td><fmt:formatNumber value='${item.grantAmount}' pattern="#,##0.00"/></td>
							<td><c:if test="${item.productName=='农信借'}">
									<fmt:formatNumber value='${item.feeCredit}' pattern="#,##0.00" />
								</c:if>
							</td>
							<td><c:if test="${item.productName=='农信借'}">
									<fmt:formatNumber value='${item.feePetition}'
										pattern="#,##0.00" />
								</c:if>
							</td>
							<td><fmt:formatNumber value='${item.feeSum}' pattern="#,##0.00" /></td>
							<td><fmt:formatNumber value='${item.urgeMoeny}' pattern="#,##0.00"/></td>
							<td><fmt:formatNumber value='${item.waitUrgeMoeny}' pattern="#,##0.00"/></td>
							<td><fmt:formatNumber value='${item.urgeServicesCheckApply.urgeApplyAmount}' pattern="#,##0.00"/></td>
							<td width="41">${item.loanFlagLabel}</td>
							<td><input type="button" class="btn_edit" value="办理"  name="matchingInfoBtn" matchingId="${item.urgeServicesCheckApply.id}" contractCode = "${item.contractCode }""></td>
						</tr>
					</c:forEach>
					<c:if
						test="${ paybackApplyList.list==null || fn:length(paybackApplyList.list)==0}">
						<tr>
							<td colspan="18" style="text-align: center;">没有待办数据</td>
						</tr>
					</c:if>
				</tbody>
			</table>
			<br>
			<br>
		</form>
	</div>
	<div class="pagination">${paybackApplyList}</div>
</body>
</html>