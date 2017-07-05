<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>提醒还款发起列表</title>
<script type="text/javascript" src="${context}/js/payback/repaymentReminder.js">
</script>
<script type="text/javascript">
	$(function (){
		$("#searchBtn").click(function (){
			$("#auditForm").attr("action",ctx+"/borrow/payback/overdueManager/overdueManagerList").submit();
		});
		
		$('#clearBtn1').click(function(){
			$(':input','#auditForm')
			  .not(':button, :reset,:hidden')
			  .val('')
			  .removeAttr('checked')
			  .removeAttr('selected');
			$("input[name='flag']").val('');
			$("#auditForm").attr("action",ctx+"/borrow/payback/overdueManager/overdueManagerList").submit();
		});
		var msg = "${message}";
		if (msg != "" && msg != null) {
			art.dialog.alert(msg);
		};
	});
</script>
</head>
<body>
	<div class="control-group ">
		<form id="auditForm" method="post">
				<input type = "hidden" name  = "flag" value = "12"/>
				<input id="pageNo" name="pageNo" type="hidden" value="${waitPage.pageNo}" /> 
				<input id="pageSize" name="pageSize" type="hidden" value="${waitPage.pageSize}" />
				<input  id="idStr" type="hidden" name="id"/>
	        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
	            <tr>
	                <td><label class="lab">客户姓名：</label><input value="${RepaymentReminderEx.customerName }" name="customerName" type="text" class="input_text178"></input></td>
	                <td><label class="lab">合同编号：</label><input value="${RepaymentReminderEx.contractCode }" name="contractCode" type="text" class="input_text178"></input></td>
	                <td><label class="lab">期供还款日期：</label><input id="paybackMonthMoneyDate" name="paybackMonthMoneyDate" type="text" readonly="readonly" maxlength="20" class="input_text178 Wdate" style="cursor:pointer;"
								value="<fmt:formatDate value="${RepaymentReminderEx.paybackMonthMoneyDate }" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" ></input>
					</td>
	            </tr>
	            
	            <tr>
	                <td><label class="lab">划扣平台：</label><select class="select180" name="dictDealType">
	                		<option value="">请选择</option>
	                              <c:forEach items="${fns:getDictList('jk_deduct_plat')}" var="dictDeType">
	                                   <option value="${dictDeType.value }" <c:if test="${RepaymentReminderEx.dictDealType == dictDeType.value }">selected</c:if>>
	                                   		${dictDeType.label }
	                                   </option>
	                              </c:forEach>
	                	</select>
	                </td>
	                <td><label class="lab">模式：</label><select class="select180" name="model">
	                <option value="">请选择</option>
			        <c:forEach var="flag" items="${fns:getDictList('jk_loan_model')}">
						<option value="${flag.value }"
							<c:if test="${RepaymentReminderEx.model==flag.value }">selected</c:if>>
							<c:if test="${flag.value=='1' }">${flag.label}</c:if>
							<c:if test="${flag.value!='1' }">非TG</c:if>
						</option>
					</c:forEach>
	                </select>
	                </td>
	                
	                 <td><label class="lab">渠道：</label><select class="select180" name="logo">
	                <option value="">请选择</option>
			        <c:forEach var="flag" items="${fns:getDictList('jk_channel_flag')}">
			            <c:if test="${flag.label != 'XT02'}">
						<option value="${flag.value }"
						<c:if test="${RepaymentReminderEx.logo==flag.value }">selected</c:if>>${flag.label}</option>
						</c:if>
					</c:forEach>
	                </select>
	                </td>
	            </tr>
	        </table>
	        </form>
			 <div class="tright pr30 pb5" >
			    <button class="btn btn-primary" id="searchBtn">搜索</button>
				<input type="button" class="btn btn-primary" id="clearBtn1" value="清除" />
			 </div>
	</div>
	  <p class="mb5">
    	    <input class="btn btn-small" value="短信提醒推送" id="informationAlertPush" type="button">
    	    <input class="btn btn-small" value="导出Excel" id="exportExcel" type="button"/>
    	    <input value="${numTotal.total}" id="totalbk" type="hidden"/>
    	    <input value="${waitPage.count}" id="numbk" type="hidden"/>
    	    <span class="red">划扣总金额:</span>
			<span class="red" id="totalMoney"><fmt:formatNumber value='${numTotal.total}' pattern="#,##0.00"/></span> </input>&nbsp;
			<span class="red">划扣总笔数:</span>
			<span class="red" id="totalNum">${waitPage.count}</span>
    	</p>
<div class="box5">
    <table id="tableList"  class="table  table-bordered table-condensed table-hover" width="100%">
          <thead>
						<tr>
							<th ><input type="checkbox" id="checkAll" />全选</th>
							<th >序号 </th>
							<th >合同编号</th>
							<th >客户姓名</th>
							<th >门店名称</th>
							<th >开户行名称</th>
							<th >合同到期日</th>
							<th >期数</th>
							<th >还款日</th>
							<th >月还期供金额</th>
							<th >当期应还金额</th>
							<th >已还期供金额</th>
							<th >蓝补金额</th>
							<th >借款状态</th>
							<th >期供状态</th>
							<th >划扣平台</th>
							<th >渠道</th>
							<th>模式</th>
						</tr>
						</thead>
					<tbody id="repaymentReminder">
						<c:forEach items="${waitPage.list}" var="item" varStatus="status">
							<tr style="text-align: center;">
								<td><input type="checkbox" name="checkBox" value="${item.id}"/>
								<input type="hidden" value="${item.payMoney }"/></td>
								<td>${status.count }</td>
								<td>${item.contractCode}</td>
								<td>${item.customerName}</td>
								<td>${item.stroeName}</td>
								<td>${item.applyBankNameLabel}</td>
								<td><fmt:formatDate value="${item.contractEndTimestamp}" /></td> 
								<td>${item.months}</td>
								<td>${item.monthPayDay}</td>
								 <td><fmt:formatNumber value='${item.completeMoney}' pattern="#,##0.00"/></td>
								<td><fmt:formatNumber value='${item.payMoney}' pattern="#,##0.00"/></td>
								<td><fmt:formatNumber value='${item.completeAmount}' pattern="#,##0.00"/></td>
								<td><fmt:formatNumber value='${item.buleAmont}' pattern="#,##0.00"/></td> 
								<td>${item.dictLoanStatusLabel}</td>
								<td>${item.dictMonthStatusLabel}</td>
								<td>${item.dictDealTypeLabel}</td>
								<td>${item.logoLabel}</td>
								<td>${item.modelLabel}</td>
							</tr>
						</c:forEach>
						<c:if test="${waitPage.list==null || fn:length(waitPage.list)==0}">
							<tr>
								<td colspan="18" style="text-align: center;">没有符合条件的数据</td>
							</tr>
						</c:if>
					</tbody>
    </table>
</div>
<div class="pagination">${waitPage}</div>
</div>
<script type="text/javascript">
$("#tableList").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
	$('#tableList').bootstrapTable({
		method: 'get',
		cache: false,
		height: $(window).height()-230,
		pageSize: 20,
		pageNumber:1
	});
	$(window).resize(function () {
		$('#tableList').bootstrapTable('resetView');
	});
</script>
</body>
</html>