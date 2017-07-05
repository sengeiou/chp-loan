<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>放款确认</title>
<script src="${context}/js/grant/grantAudit.js?version=2"
	type="text/javascript"></script>
<meta name="decorator" content="default" />
</head>
<body>
		<h4></h4>
				<table class="table  table-bordered table-condensed table-hover ">
				<thead>
					<tr>
						<th>借款人</th>
						<th>合同编号</th>
						<th>放款金额</th>
						<th>放款账户</th>
						<th>放款日期</th>
						<th>渠道</th>
					</tr>
				</thead>
				<tbody>
          			<form id="param">
          			<input type="hidden" id="listFlag" value="${listFlag }" />
          			<input type="hidden" id="messageBuffer" value="${messageBuffer }" />
          			<input type="hidden" id="isExistSplit" value="${isExistSplit }" />
	            		<c:forEach items="${list}" var="item" varStatus="stat">
	            		<tr>
	                		<td>${item.customerName}</td>
	                		<td>${item.contractCode}</td>
	                		<td><fmt:formatNumber value='${item.lendingMoney}' pattern="#,#00.00"/></td>
	                		<td>${item.bankAccountName}</td>
	                		<td><fmt:formatDate value="${item.lendingTime}"
							pattern="yyyy-MM-dd"/></td>
	                		<td>${item.channelName}
	                		<input type="hidden" name="list[${stat.index}].applyId" value='${item.applyId}'/>
	                		<input type="hidden" name="list[${stat.index}].contractCode" value='${item.contractCode}'/>
	                		<input type="hidden" name="list[${stat.index}].loanCode" value='${item.loanCode}'/>
	                		<input type="hidden" name="list[${stat.index}].channelCode" value='${item.channelName}'/>
	                		<input type="text" value="<fmt:formatDate value="${item.submitDeductTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"  name='submitDeductTime' />
	                		</td>
	                   	 </tr>
	            		</c:forEach>
         		 	</form> 
         		 </tbody>					
				</table>
				<div class="control-group pb10 ">
					<table class="table1" cellpadding="0" cellspacing="0"
						border="0" width="100%">
						<tr>
							<td style="text-align: left"><label class="lab">筛选结果：</label><input
								type="radio" class="mr10" name="sort" checked="checked"
								 value="通过">通过 <input type="radio" 
								class="mr10" name="sort"  value="退回">退回</td>
						</tr>
						<tr>
							<td style="text-align: left">
								<div style="display: inline" id="sort_div">
									<label class="lab">确认放款日期：</label><input id="grantDate" name="bv.lendingTime" value="<fmt:formatDate value='${bv.lendingTime}' pattern="yyyy-MM-dd"/>" type="text" class="Wdate input_text178" size="10"  
                                        onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" />
								</div>
								<div style="display: none" id="back_div">
									<label class="lab">退回原因：</label><select class="select180" id="backResult"><option value="">请选择</option>
										<c:forEach items="${fns:getDictList('jk_chk_back_reason')}" var="card_type">
											<option value="${card_type.value}">${card_type.label}</option>
									</c:forEach>
								</div>
							</td>
						</tr>
					</table>
			
					</div> 
					<div class="tright mt10 pr10">
					<button class="btn btn-primary" id="auditSure">确认</button>
					<button class="btn btn-primary" id="closeBtn">取消</button>
				   </div>
</body>
</html>