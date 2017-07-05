<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context }/js/payback/zhaudited.js"></script>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context }/js/payback/dateUtils.js"></script>
<script type="text/javascript" src="${ctxStatic}/jquery-validation/1.11.0/lib/jquery.form.js" ></script>
</head>
<body>
	<div class="control-group">
		<form id="auditForm" action="${ctx}/borrow/payback/zhaudited/list" >
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
			<input type="hidden" id="msg" value="${message}">
			<input type="hidden" name="id" value="${paybackTransferOut.id }"/>
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">序号：</label> 
						<input type="text" name=orderNumber class="input-medium" value="${paybackTransferOut.orderNumber }"/>
					</td>
					<td><label class="lab" style="text-align: right">查账状态：</label>
						<select name="outAuditStatus" class="input-medium" value="${paybackTransferOut.outAuditStatus }"> 
							<option value="">请选择</option>
							<c:forEach items="${fns:getDictList('jk_bankserial_status')}" var="item">
								<option value="${item.value }" <c:if test="${item.value == paybackTransferOut.outAuditStatus }">selected</c:if>>${item.label }</option>
							</c:forEach>
						</select>
					</td>
					<td><label class="lab">存款人：</label> 
						<input type="text" name="outDepositName" class="input-medium" value="${paybackTransferOut.outDepositName }"/></td>
				</tr>
				<tr>
					<td><label class="lab">合同编号：</label> <input type="text" name="contractCode" class="input-medium" value="${paybackTransferOut.contractCode }"/></td>
					<td><label class="lab" style="text-align: right">入账银行：</label>
						<select name="outEnterBankAccount" class="input-medium"> 
							<option value="">请选择</option>
							<c:forEach var="item" items="${middlePersonList }">
								<option value="${item.bankCardNo }" <c:if test="${paybackTransferOut.outEnterBankAccount==item.bankCardNo }">selected</c:if>>${item.midBankName }</option>
							</c:forEach>
						</select>
					</td>
					<td><label class="lab">金额：</label> 
						<input type="text" name="beginOutReallyAmount" class="input_text70" onchange="checkField(this.value)" value="${paybackTransferOut.beginOutReallyAmount }"/>-
						<input type="text" name="endOutReallyAmount" class="input_text70"  onchange="checkField(this.value)" value="${paybackTransferOut.endOutReallyAmount }"/>
					</td>
				</tr>
				<tr id="T1" style="display:none">
					<td><label class="lab">存款日期：</label> 
						 <input id="startDepositdDate" name="startDepositdDate"  type="text" readonly="readonly" maxlength="40" class="input-mini Wdate"
					     value="<fmt:formatDate value="${paybackTransferOut.startDepositdDate}" pattern="yyyy-MM-dd"/>"
					     onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>-
					     <input id="endDepositDate" name="endDepositDate"  type="text" readonly="readonly" maxlength="40" class="input-mini Wdate"
					     value="<fmt:formatDate value="${paybackTransferOut.endDepositDate}" pattern="yyyy-MM-dd"/>"
					     onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
						 </td>
					<td><label class="lab">查账日期：</label>
						 <input id="startAuditedDate" name="startAuditedDate"  type="text" readonly="readonly" maxlength="40" class="input-mini Wdate"
					     value="<fmt:formatDate value="${paybackTransferOut.startAuditedDate}" pattern="yyyy-MM-dd"/>"
					     onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>-
					     <input id="endAuditedDate" name="endAuditedDate"  type="text" readonly="readonly" maxlength="40" class="input-mini Wdate"
					     value="<fmt:formatDate value="${paybackTransferOut.endAuditedDate}" pattern="yyyy-MM-dd"/>"
					     onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
						</td>
				</tr>
			</table>
		</form>
		<div class="tright pr30 pb5">
			<input type="button" class="btn btn-primary" id="auditedSubmitBtn" value="搜索">
			<input type="button" class="btn btn-primary" id="clearAuditedBtn" value="清除">
			<div style="float:left;margin-left:45%;padding-top:10px">
				  <img src="${context }/static/images/up.png" id="showMore" onclick="show();"></img>
				</div>
		</div>
	</div>
	<div id="auditList">
		<p class="mb5">
			<button class="btn btn-small" id="derivedAuditedBtn"   >导出银行账款数据</button>   
			<button class="btn btn-small" id="auditedMdl" data-target="#uploadAuditedModal" data-toggle="modal">导入银行账款数据</button>
		</p>
		<table id="auditedTab">
			<thead>
				<tr>
					<th data-field="state" data-checkbox="true"></th>
					<th data-field="id" data-visible="false" data-switchable="false" class="hidden">ID</th>
					<th data-field="outEnterBankAccount" data-visible="false" data-switchable="false" class="hidden">入账账号</th>
					<th>序号</th>
					<th>到账日期</th>
					<th>入账银行</th>
					<th>金额</th>
					<th>存款人</th>
					<th>查账状态</th>
					<th>合同编号</th>
					<th>查账日期</th>
					<th>备注</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody id="prepareListBody">
				<c:forEach items="${paybackTransferOutList.list}" var="item">
					<tr>
						<td></td>
						<td>${item.id}</td>
						<td>${item.outEnterBankAccount }</td>
						<td>${item.orderNumber}</td>
						<td><fmt:formatDate value="${item.outDepositTime}" type="date"/></td>
						<td>${item.middlePerson.midBankName }</td>
						<td><fmt:formatNumber value='${item.outReallyAmount}' pattern="#,##0.00"/></td>
						<td>${item.outDepositName}</td>
						<td>${item.outAuditStatusLabel}</td>
						<td>${item.contractCode}</td>
						<td><fmt:formatDate value="${item.outTimeCheckAccount}" type="date"/></td>
						<td>${item.outRemark}</td>
						<td><input type="button" class="btn_edit" id ="auditedMal" name="auditedMalBtn" auditedId="${item.id}" orderNumber="${item.orderNumber }"
						outAuditStatus="${item.outAuditStatus}"  contractCode="${item.contractCode}" outReallyAmount ="${item.outReallyAmount}" relationType = "${item.relationType}"
						outTimeCheckAccount="<fmt:formatDate value="${item.outTimeCheckAccount}" pattern="yyyy-MM-dd"/>" transferAccountsId = "${item.transferAccountsId }" rPaybackApplyId = "${item.rPaybackApplyId }" name="dealRateAudit" 
						value="办理" data-toggle="modal" data-target="#auditedModal"/>
						<input type="button" class="btn_edit" onclick="showNoDeductPaybackHistory('','${item.id}','')" value="历史"/>
						</td>
					</tr>
				</c:forEach>
				<c:if test="${ paybackTransferOutList.list==null || fn:length(paybackTransferOutList.list)==0}">
					<tr>
						<td colspan="18" style="text-align: center;">没有待办数据</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>
	<div class="pagination">${paybackTransferOutList}</div>
	<div class="modal fade" id="auditedModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title">修改查账状态</h4>
				</div>
				<form id="updateAuditForm" action="${ctx}/borrow/payback/zhaudited/updateAudited" method="post">
				<input type="hidden" id="outAuditId" name="id" />
				<input type="hidden" id="outReallyAmount" name="outReallyAmount"/>
				<input type="hidden" id="transferAccountsId" name="transferAccountsId"/>
				<input type="hidden" id="relationType" name="relationType"/>
				<input type="hidden" id="applyId" name="rPaybackApplyId"/>
				<input type="hidden" id="orderNumber" name="orderNumber"/>
				<input type="hidden" id="contractCodeTemp" name="contractCodeTemp"/>
					<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
						<tr>
							<td><label class="lab" style="text-align: right">查账状态：</label>
								<select id="outAuditStatus" name="outAuditStatus" class="input-medium">
									<c:forEach items="${fns:getDictList('jk_bankserial_check')}" var="item">
										<c:choose>
											<c:when test="${item.value == 0 || item.value == 2}">
												<option value="${item.value}">${item.label}
											</c:when>
										</c:choose>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td><label class="lab">合同编号：</label> <input type="text"
								class="input-medium" id="updateContractCode" name="contractCode" /></td>
						</tr>
						<tr>
							<td><label class="lab">查账日期：</label>
								<input type="text" id="outTimeCheckAccount" name="outTimeCheckAccount" maxlength="20" class="input-medium Wdate" style="cursor: pointer"
						 value="<fmt:formatDate value="${paybackTransferOut.outDepositTime}" pattern="yyyy-MM-dd"/>" 
						 onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
							</td>
						</tr>
					</table>
			</form>
			</div>
			<div class="modal-footer">
					<a type="button" class="btn btn-primary" data-dismiss="modal">关闭</a>
					<a type="button" class="btn btn-primary" data-dismiss="modal" id="updateAudited">提交更改</a>
			</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="uploadAuditedModal" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title">导入银行账款数据</h4>
				</div>
				<div class="modal-body">
				<form id="uploadAuditForm" role="form" enctype="multipart/form-data" action="${ctx}/borrow/payback/zhaudited/importAuditedExl" method="POST">
					<input id="confrimFlag" name="confrimFlag"  value="0" type="hidden">
					<input id="file" name="file" type="file" style="display: none">
					<input id="photoCover" class="input_text178" type="text" /> 
					<a class="btn btn-small" onclick="$('input[id=file]').click();">选择文件</a>
					</div>
					<div class="modal-footer">
						<a type="button" class="btn btn-primary" data-dismiss="modal">关闭</a>
						<a type="button" class="btn btn-primary" data-dismiss="modal" id="uploadAuditedFile">确认</a>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>