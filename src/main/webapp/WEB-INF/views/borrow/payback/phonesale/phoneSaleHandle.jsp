<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>电销还款提醒待办</title>
<script type="text/javascript" src="${context}/js/payback/phoneSaleHandle.js"></script>
<script src='${context}/js/common.js' type="text/javascript"></script>
<script type="text/javascript">
function page(n,s) {
	if (n) $("#pageNo").val(n);
	if (s) $("#pageSize").val(s);
	$("#CenterapplyPayForm").attr("action", "${ctx}/borrow/payback/phonesale/phoneSaleHandle");
	$("#CenterapplyPayForm").submit();
	return false;
	
}
</script>
</head>
<body>
   <div class="control-group">
	 <form method="post" action="${ctx}/borrow/payback/phonesale/phoneSaleHandle" id="CenterapplyPayForm">
			<input id="pageNo" name="pageNo" type="hidden"	value="${page.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" />
			<input id="ids" name="ids" type="hidden" />
			<input id="remindStatusFlag" name="remindStatusFlag" type="hidden" />
			
		<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
						<td><label class="lab">客户姓名：</label><input type="text"
							class="input_text178" name="customerName" value="${bean.customerName}"></td>
						<td><label class="lab">合同编号：</label><input type="text"
							class="input_text178" name="contractCode" value="${bean.contractCode}"></td>
						<td><label class="lab">提醒状态：</label>
						<select name="status" class="select180">
						  <option value="">全部</option>
						  <option value="0" <c:if test="${bean.status=='0' }">selected</c:if>>待提醒</option>
						  <option value="1" <c:if test="${bean.status=='1' }">selected</c:if>>已提醒</option>
						  <option value="2" <c:if test="${bean.status=='2' }">selected</c:if>>提醒失败</option>	
						</select>
						</td>
			   </tr>
			   <tr>
					<td><label class="lab">划扣平台：</label><select class="select180"  name="dictDealType">
								<option value="">请选择</option>
			                        <c:forEach var="plat" items="${fns:getDictList('jk_deduct_plat')}">
										<option value="${plat.value }"
										<c:if test="${plat.label!='ZCJ金账户' }">
											<c:if test="${bean.dictDealType==plat.value }">selected</c:if>>${plat.label }</option>
										</c:if>
									</c:forEach>
								</select>
					</td>
					<td><label class="lab">还款日：</label>
						<select name="monthPayDay" class="select180">
						        <option value="">请选择</option>
						<c:forEach var="day" items="${dayList}">
								<option value="${day.billDay}"
								<c:if test="${bean.monthPayDay==day.billDay }">selected</c:if>>${day.billDay}</option>
								</c:forEach>
						</select>
					</td>
					<td><label class="lab">电销专员：</label><input type="text"
							class="input_text178" name="commissioner" value="${bean.commissioner}"></td>
			   </tr>
		 </table>
	</form>
				<div class="tright pr30 pb5">
					<button class="btn btn-primary" onclick="document.forms[0].submit();">搜索</button>
				   <button class="btn btn-primary"  onclick = "resets()">清除</button>
				 <div style="float: left; margin-left: 45%; padding-top: 10px">
	          <img src="${context}/static/images/up.png" id="showMore"></img>
	 </div>
	</div>
  </div>
		<p class="mb5">
			<button class="btn btn-small" id="remindBtn">标记提醒</button>
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
				<!-- <th>手机号</th> -->
				<th>期数</th>
				<th>还款日</th>
				<th>月还期供金额</th>
				<th>已还金额</th>
				<th>借款状态</th>
				<th>渠道</th>
				<th>划扣平台</th>
				<th>提醒状态</th>
				<th>电销专员</th>
				<th>操作</th>
			</tr>
			</thead>
			<c:forEach var="paybacklist" items="${page.list }" varStatus="sta">
				<tr>
					<td>
					<input type="checkbox" name="checkBox"  value="${paybacklist.id}"/>
					</td>
					<td>${sta.count}</td> 
					<td>${paybacklist.contractCode }</td>
					<td>${paybacklist.customerName }</td>
					<td>${paybacklist.storesName }</td>
					<td>
					 ${paybacklist.applyBankNameLabel} 
					</td>
					<%-- <td>${paybacklist.tel}</td> --%>
					<td>${paybacklist.months }</td>
					<td>${paybacklist.monthDay }</td>
					<td class="payMoney"><fmt:formatNumber value="${paybacklist.repayAmount }" pattern='0.00'/></td>
					<!--  <td>${CenterHkApply.completeMoney }</td>-->
					 <td><fmt:formatNumber value="${paybacklist.completeAmount }" pattern='0.00'/></td>
					  
					  <td> ${paybacklist.dictLoanStatusLabel} 
					</td>
					
				    <td>${paybacklist.markLabel}</td>
					<td>
						${paybacklist.dictDealTypeLabel}
					</td>
					<td>
					  <c:if test="${empty  paybacklist.status}">
	                                                          全部
	                  </c:if>
	                   <c:if test="${paybacklist.status=='1'}">
	                                                           已提醒
	                   </c:if>
	                   <c:if test="${paybacklist.status=='2'}">
	                                                         提醒失败
	                   </c:if>
	                   <c:if test="${paybacklist.status=='0'}">
	                                                        待提醒
	                   </c:if>
					</td>
					<td>
						${paybacklist.commissioner}
					</td>
					<td>
				      <input type="button" class="btn_edit" onclick="showHis('${paybacklist.id}');" value="历史" />
	                  <input type="button" class="btn_edit" onclick ="remindBtn('${paybacklist.id}')" value="标记提醒" />
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div class="pagination">${page}</div>
	
	
	<!--标记提醒 -->
	<div id="remindDiv" style="display: none">
				 <p>
				 <label class="lab">提醒状态：</label>
						<select name="remindStatus" class="select180" id="remindStatusId">
						  <option value="">请选择</option>
						  <option value="1" <c:if test="${bean.status=='1' }">selected</c:if>>已提醒</option>
						  <option value="2" <c:if test="${bean.status=='1' }">selected</c:if>>提醒失败</option>	
						</select>
				 </p>
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