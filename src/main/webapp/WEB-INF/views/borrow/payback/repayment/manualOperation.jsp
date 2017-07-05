<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>风控手动冲抵页面</title>
<script type="text/javascript" src="${context}/js/payback/manualOperation.js"></script>
<script src='${context}/js/common.js' type="text/javascript"></script>
</head>
<body>
<div > 
	<div class="control-group">
    <form id="auditForm" modelAttribute="ManualOperationEx" method="post">
        	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td><label class="lab">客户姓名：</label>
                <input value="${ManualOperationEx.customerName }" type="text" class="input_text178" name="customerName"></input></td>
                <td><label class="lab">合同编号：</label>
                <input value="${ManualOperationEx.contractCode }" type="text" class="input_text178" name="contractCode"></input></td>
                <td><label class="lab">门店：</label>
                 <input id="txtStore" name="store" type="text" maxlength="20" class="txt date input_text178" value="${secret.store }" /> 
					<i id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
					<input type="hidden" id="hdStore">
                </td>
            </tr>
            <tr>
				<td><label class="lab">银行：</label>
					<select class="select180" name="applyBankName">
                		<option value="">请选择</option>
                              <c:forEach items="${fns:getDictList('jk_open_bank')}" var="applyBankName">
                                   <option value="${applyBankName.value }" <c:if test="${ManualOperationEx.applyBankName == applyBankName.value }">selected</c:if>>${applyBankName.label }</option>
                              </c:forEach>
                	</select>
				</td>
				<td ><label class="lab">渠道：</label>
					<select class="select180" name="loanMark">
                		<option value="">请选择</option>
                              <c:forEach items="${fns:getDictList('jk_channel_flag')}" var="loanMark">
                                   <option value="${loanMark.value }" <c:if test="${ManualOperationEx.loanMark == loanMark.value }">selected</c:if>>${loanMark.label }</option>
                              </c:forEach>
                	</select>
				</td>
				<td><label class="lab">划扣平台：</label>
					<select class="select180" name="dictDealType">
                		<option value="">请选择</option>
                              <c:forEach items="${fns:getDictList('jk_deduct_plat')}" var="dictDealType">
                                   <option value="${dictDealType.value }" <c:if test="${ManualOperationEx.dictDealType == loanMark.value }">selected</c:if>>${dictDealType.label }</option>
                              </c:forEach>
                	</select>
				</td>
               
                <td></td>
            </tr>
        </table>
        <div class="tright pr30 pb5">
	        <button onclick="document.forms[0].submit();" class="btn btn-primary">搜索</button>
	        <input type="button" id="clearBtn" class="btn btn-primary" value="清除">
	    </div>
        </div>
      </form> 
		</div>
	<div>
		<p class="mb5">
		 <button class="btn btn-small" name="querys" role="button" data-toggle="modal">批量冲抵</button> 
		 <button class="btn btn-small" name="exportExcel">导出EXCEL</button><span style="color:red"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;冲抵总金额：<label class="red" id="total_money">0.00</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;冲抵总笔数：<label class="red" id="total_num">0</label></span></p>
    </div>
    <div class="box5">
    <table id="tableList" class="table  table-bordered table-condensed table-hover ">
       <thead>
        <tr>
            <th><input type="checkbox" class="userCardInfo" id="checkAll" /></th>
            <th>合同编号</th>
            <th>客户姓名</th>
            <th>门店名称</th>
            <th>期供金额</th>
            <th>当期已还金额</th>
            <th>当期未还金额</th>
            <th>银行</th>
            <th>借款状态</th>
            <th>蓝补金额</th>
			<th>渠道</th>
			<th>划扣平台</th>
            <th>操作</th>
        </tr>
        </thead>
         <c:forEach items="${waitPage.list}" var="item" varStatus="num">
	        <tr>
	        	<td><input type="checkbox" name="checkBox" class="userCardInfo" value="${item.id},${item.contractMonthRepayAmount },${item.paybackBuleAmount},${item.chargeId}"/></td>
	        	<td>${item.contractCode}</td>
	        	<td>${item.customerName}</td>
	        	<td>${item.orgName}</td>
	        	<td><fmt:formatNumber value='${item.contractMonthRepayAmount}' pattern="0.00"/></td>
	        	<td><fmt:formatNumber value='${item.hisOverpaybackMonthMoney}' pattern="0.00"/></td>
	        	<td><fmt:formatNumber value='${item.notOverpaybackMonthMoney}' pattern="0.00"/></td>
	        	<td>${item.applyBankNameLabel}</td>
	        	<td>${item.dictLoanStatusLabel}</td>
	        	<td><fmt:formatNumber value='${item.paybackBuleAmount}' pattern="0.00"/></td>
	        	<td>${item.loanMarkLabel}</td>
	        	<td>${item.dictDealTypeLabel}</td>
	        	<td>
	        		<button class="btn_edit" value="${item.contractCode},${item.id}" name="seeDate" >查看</button>
	        	</td>
	        </tr>
        </c:forEach>
        <c:if test="${waitPage.list==null || fn:length(waitPage.list)==0}">
			<tr>
				<td colspan="24" style="text-align: center;">没有符合条件的数据</td>
			</tr>
		</c:if>
     </table>
	 </div>
<div class="pagination">${waitPage}</div> 
</div>
<!-- 批量冲抵确认 -->
<div id="determine" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-url="data">
        <div class="modal-dialog" role="document">
        <div style="width: 300px;margin-left: 150px;" class="modal-content">
        <div class="modal-header">
         <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        	<h2 class="f14">批量冲抵信息</h2>
        	</div>
          <div class="modal-body">
            <table class="table4" cellpadding="0" cellspacing="0" border="0" width="100%">
                 <ul style="list-style:none">
	                 <li class="red">冲抵金额：<label id="money"></label>&nbsp;（元）</li><br/>
	                 <li class="red">冲抵笔数：<label id="count"></label></li><br/>
	                 <p>是否确认冲抵？</p>
                 </ul>
            </table>
            </div>
            <div class="modal-footer" style="padding-left: 100px;"><button class="btn btn-primary" id="confirm">确认</button>
            <button class="btn btn-primary" data-dismiss="modal" aria-hidden="true">取消</button></div>
    </div>
 </div>
 </div>
 </div>
 <script type="text/javascript">

	  $("#tableList").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
		$('#tableList').bootstrapTable({
			method: 'get',
			cache: false,
			height: $(window).height()-260,
			
			pageSize: 20,
			pageNumber:1
		});
		$(window).resize(function () {
			$('#tableList').bootstrapTable('resetView');
		});
	</script>
</body>
</html>