<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default"/>
<title>大金融已放款列表</title>	
<script src="${context}/js/common.js" type="text/javascript"></script>
<script type="text/javascript" src="${context}/js/channel/goldcredit/popuplayer.js"></script>
<script src="${context}/js/grant/bigFinanceGrantDone.js" type="text/javascript"></script>
<script>
function page(n, s) {
	if (n)
		$("#pageNo").val(n);
	if (s)
		$("#pageSize").val(s);
	$("#grantForm").attr("action", "${ctx}/channel/bigfinance/grantDone/grantDone");
	$("#grantForm").submit();
	return false;
}
$(function (){
	loan.getstorelsit("storeName","storeOrgId");
	$.popuplayer(".edit");
});
</script>

</head>
<body>	
	<div class="control-group">
	<form:form id="grantForm"  modelAttribute="loanGrantEx" method="post" action="${ctx}/channel/bigfinance/grantDone/grantDone">
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" /> 
			    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
                <td><label class="lab">客户姓名：</label><form:input type="text" class="input_text178" path="customerName"></form:input></td>
				 <td ><label class="lab">提交日期：</label><input id="submissionDate"  name="submissionDate"  type="text" class="Wdate input_text178" size="10"  value = "<fmt:formatDate value='${loanGrantEx.submissionDate}' pattern='yyyy-MM-dd'/>"
                    onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer"></td>
                <td><label class="lab">门店：</label><form:input path="storeName" id = "storeName" readonly = "true" class="input_text178" />
					 <i id="selectStoresBtn"
						class="icon-search" style="cursor: pointer;"></i>
					<form:hidden path="storeCode" id = 'storeOrgId'/>
            </tr>
            <tr>
                <td><label class="lab">放款日期：</label><input id="grantDate" name="lendingTimeStrat"  type="text" class="Wdate input_text70" size="10" 
                	value = "<fmt:formatDate value='${loanGrantEx.lendingTimeStrat}' pattern='yyyy-MM-dd'/>" 
                    onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" />-<input id="grantDate" name="lendingTimeEnd"  type="text" class="Wdate input_text70" size="10"  
                    value = "<fmt:formatDate value='${loanGrantEx.lendingTimeEnd}' pattern='yyyy-MM-dd'/>"
                    onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" ></td>
                <td><label class="lab">合同编号：</label><form:input type="text" class="input_text178" path="contractCode"></form:input></td>
                <td><label class="lab">提交批次：</label><form:select class="select180" path="grantPch"><form:option value="">请选择</form:option>
				 <c:forEach items="${submitBatchList}" var="submitBatch">
								<form:option value="${submitBatch}">${submitBatch}</form:option>
				 </c:forEach></form:select>
				</td>
            </tr>
            <tr id="T1" style="display:none">
			  <td><label class="lab">放款批次：</label><form:select class="select180" path="submissionBatch"><form:option value="">请选择</form:option>
				<c:forEach items="${grantBatchList}" var="grantBatch">
								<form:option value="${grantBatch}">${grantBatch}</form:option>
				 </c:forEach></form:select>
				</td>
			    <td><label class="lab">是否加急：</label><form:select class="select180" path="urgentFlag">
                <option value="">请选择</option>
                <c:forEach items="${fns:getDictList('jk_urgent_flag')}" var="card_type">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
				 </c:forEach>
                </form:select></td>
            </tr>
        </table>
        <div class="tright pr30 pb5"><input class="btn btn-primary" type="submit" value="搜索"></input>
                  <button class="btn btn-primary" id="clearBtn">清除</button>
               <div style="float: left; margin-left: 45%; padding-top: 10px">
	      <img src="../../../../static/images/up.png" id="showMore"></img>
	     </div>
		</div>
		</form:form>
		</div>
    <p class="mb5">
    	<button class="btn btn-small zcj_grantdone_dexcl" id="daoBtn">导出excel</button>
    	&nbsp;&nbsp;<label class="lab1">放款累计金额：</label><label class="red" id="totalGrantMoney">0.00</label>元     	&nbsp;&nbsp;<label class="lab1">放款总笔数：</label><label class="red" id="totalNum">${grantDoneList.count}</label>笔
    	<input type="hidden" id="hiddenGrant" value="0.00">
    	<input type="hidden" id="hiddenNum" value="0">
    </p>
    <sys:columnCtrl pageToken="gDoneList"></sys:columnCtrl>
    <div class="box5">
    <table id="tableList" class="table  table-bordered table-condensed table-hover ">
    <thead>
        <tr>
            <th><input type="checkbox" id="checkAll"/></th>
            <th>客户姓名</th>
            <th>共借人</th>
            <th>自然人保证人</th>
            <th>客户经理</th>
            <th>客户经理编号</th>
            <th>团队经理</th>
            <th>团队经理编号</th>
            <th>借款类型</th>
            <th>合同编号</th>
            <th>合同金额</th>
            <th>放款金额</th>
            <th>催收服务费</th>
            <th>未划金额</th>
            <th>期数</th>
            <th>放款账户</th>
            <th>开户行</th>
            <th>账号</th>
            <th>机构</th>
            <th>放款时间</th>
            <th>放款批次</th>
            <th>操作人员</th>
             <th>渠道</th>
             <th>模式</th>
            <th>加急标识</th>
            <th>提交批次</th>
            <th>提交时间</th>
           <!--  <th>是否追加</th> -->
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:if test="${ grantDoneList!=null && fn:length(grantDoneList.list)>0}">
         <c:set var="index" value="0"/>
          <c:forEach items="${grantDoneList.list}" var="item" varStatus="status">
          <c:set var="index" value="${index+1}" />
             <tr>
             <td><input type="checkbox" name="checkBoxItem" fId="${item.contractCode}"
              contractMoney= "${item.grantAmount}" applyId = "${item.applyId }" loanCode = "${item.loanCode }"/> ${status.count}</td>
             <td>${item.customerName}</td>
             <td>
	             <c:if test="${item.loanInfoOldOrNewFlag =='0'|| empty item.loanInfoOldOrNewFlag}">
	             ${item.coBorrowing}
	             </c:if>
             </td>
             <td>
	             <c:if test="${item.loanInfoOldOrNewFlag =='1'}">
	             ${item.sercurityNames}
	             </c:if>
             </td>
             <td>${item.loanManagerName}</td>
             <td>${item.loanManagerCode}</td>
             <td>${item.loanTeamManagerName}</td>
             <td>${item.loanTeamManagerCode}</td>
             <td>信借</td>
             <%-- <td>${fns:getDictLabel(item.dictIsAdditional,'jk_add_flag','-')}</td> --%>
             <td>${item.contractCode}</td>
             <td><fmt:formatNumber value='${item.contractAmount}' pattern="#,#00.00"/></td>
             <td><fmt:formatNumber value='${item.grantAmount}' pattern="#,#00.00"/></td>
             <td><fmt:formatNumber value='${item.urgeMoney}' pattern="#,#00.00"/></td>
             <td><fmt:formatNumber value='${item.urgeDecuteMoeny}' pattern="#,#00.00"/></td>
             <td>${item.contractMonths}</td>
             <td></td>
             <td></td>
             <td></td>
             <td>${item.storesCode}</td>
            <%--  <td>${fns:getDictLabel(item.dictLoanWay,'jk_payment_way','-')}</td> --%>
             <td><fmt:formatDate value='${item.lendingTime}' pattern="yyyy-MM-dd" /></td>
			 <td>${item.submissionBatch }</td>
             <td>${item.checkEmpName }</td>
             
             <td>${item.loanFlag}</td>
             <td></td>
             <td>${item.urgentFlag}</td>
              <td>${item.grantPch}</td>
             <td><fmt:formatDate value="${item.submissionDate}"
				pattern="yyyy-MM-dd" />
			 </td>
			 <td style="position:relative">
			 	<input type="button" class="btn btn_edit edit" value="操作"/>
				<div class="alertset tcenter" style="display:none">
					<button class="btn_edit zcj_grantdone_his" onclick="showHisByLoanCode('${item.loanCode}')">历史</button>
				</div>
			 </td>
             </tr> 
         </c:forEach>  
         </c:if>
         <c:if test="${ grantDoneList==null || fn:length(grantDoneList.list)==0}">
           <tr><td colspan="26" style="text-align:center;">没有待办数据</td></tr>
         </c:if>
       </tbody>   
    </table>
 
</div>
<div class="pagination">${grantDoneList}</div>
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