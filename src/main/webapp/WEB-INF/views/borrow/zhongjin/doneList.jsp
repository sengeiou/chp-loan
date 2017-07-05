<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>中金划扣已办列表</title>
<script type="text/javascript" language="javascript"
	src='${context}/js/common.js'></script>
<script src="${context}/js/zhongjin/donelist.js" type="text/javascript"></script>
<script>
function page(n, s) {
	if (n)
		$("#pageNo").val(n);
	if (s)
		$("#pageSize").val(s);
	$("#deductsForm").attr("action", "${ctx }/borrow/zhongjin/donelist");
	$("#deductsForm").submit();
	return false;
}
var ctx = '${ctx}';
	$(document).ready(function() {
		var msg = '${message}';
		if (msg != "" && msg != null) {
			art.dialog.alert(msg);
		}
	});
</script>
<meta name="decorator" content="default"/>
</head>
<body>


    
	<div class="control-group">
   <form:form  method="post" modelAttribute="UrgeServicesMoneyEx" id="deductsForm" name="deductsForm">
   <input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo }">
   <input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize }">
		<input type="hidden" id="checkIds" name="checkIds"/>
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td><label class="lab">序号：</label><input type="text" class="input_text178" id="serialnum" name="serialnum" maxlength="12" value="${cpcn.serialnum }"></input></td>
                <td><label class="lab">合同编号：</label><input type="text" class="input_text178" id="contractCode" name="contractCode" maxlength="15" value="${cpcn.contractCode }"></input></td>
                <td><label class="lab">户名：</label><input type="text" class="input_text178" id="accountName" name="accountName" maxlength="18" value="${cpcn.accountName }"></input></td>
            </tr>
            <tr>
				<td><label class="lab">银行名称：</label>
					<sys:multipleBank bankClick="selectBankBtn" bankName="bankName" bankId="banknum"></sys:multipleBank>
					<input id="bankName" type="text" class="input_text178" name="bankName"  value='${cpcn.bankName}' readonly>&nbsp;<i id="selectBankBtn"
						class="icon-search" style="cursor: pointer;"></i> 
					<input type="hidden" id="banknum" name='banknum' value='${cpcn.banknum}'>
				<%-- <select class="select180" id="banknum" name="banknum" >
            		<option value="">请选择</option>
            		<c:forEach items="${fns:getDictList('jk_open_bank')}" var="item">
						<option value="${item.value}" <c:if test="${item.value eq cpcn.banknum}">selected</c:if>>${item.label}</option>
					</c:forEach>
            	</select> --%></td>
				<td><label class="lab">回盘结果：</label><select class="select180" id="backResult" name="backResult">
					<option value="">请选择</option>
					<c:forEach items="${fns:getDictList('jk_counteroffer_result')}"
								var="item">
								<option value="${item.value}"
									<c:if test="${item.value eq cpcn.backResult}">selected</c:if>>${item.label}</option>
							</c:forEach>
					</select>
				</td>
                <td><label class="lab">操作日期：</label><input id="opearTime" name="opearTime" value="${cpcn.opearTime}" type="text" class="Wdate input_text178" size="10"  
                                        onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" /></td>
            </tr>
           <tr>
            	<td><label class="lab">操作类型：</label><select class="select180" id="status" name="status">
						<option value="">请选择</option>
						<!-- <option value="0">待处理</option>
						<option value="1">实时</option>
						<option value="2">批量</option>
						<option value="3">放弃</option>
						<option value="4">预约</option>
						<option value="5">取消预约</option> -->
						<c:forEach items="${fns:getDictList('jk_cpcn_status')}"
								var="item">
								<c:if test="${item.value eq 1 or item.value eq 2 or item.value eq 4}">
								<option value="${item.value}"
									<c:if test="${item.value eq cpcn.status}">selected</c:if>>${item.label}</option>
								</c:if>
							</c:forEach>
				</select></td>
				<td><label class="lab">预约时间：</label><input id="deductBeginTime" name="deductBeginTime" value="${cpcn.deductBeginTime}" type="text" class="Wdate input_text70" size="10"  
                                        onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" />-<input id="deductEndTime" name="deductEndTime" value="${cpcn.deductEndTime}" type="text" class="Wdate input_text70" size="10"  
                                        onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" /></td>
				<td><label class="lab">回盘时间：</label><input id="beginBackTime" name="beginBackTime" value="${cpcn.beginBackTime}" type="text" class="Wdate input_text70" size="10"  
                                        onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" />-<input id="endBackTime" name="endBackTime" value="${cpcn.endBackTime}" type="text" class="Wdate input_text70" size="10"  
                                        onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" /></td>
            </tr>
		
        </table>
         
        <div class="tright pr30 pb5"><input type="button" class="btn btn-primary" onclick="search();" value="搜索"> 
               <input type="button" class="btn btn-primary" onclick="clear1();" value="清除">
      
        <div style="float: left; margin-left: 45%; padding-top: 10px">
	       <img src="../../../static/images/up.png" id="showMore"></img>
	    </div>
	    </div>
	    </form:form>
		</div>
		<p class="mb5">
    	&nbsp;&nbsp;
    	<button class="btn btn-small" onclick="exports();">导出</button>
    	&nbsp;&nbsp;
    	<button class="btn btn-small" onclick="cancel();">取消预约</button>
    	&nbsp;&nbsp;
    	&nbsp;&nbsp;<label class="lab1"><span class="red">划扣总金额：</span></label><label id="deductAmount" class="red" >
    	<fmt:formatNumber value='${deductsAmount}'  pattern="#,#0.00"/></label>元
    	<input type="hidden"  id="hiddenDeduct" value="0.00"/>
    	<input type="hidden" id="deduct" value="${deductsAmount}">
    	<input type="hidden" id="applyReallyAmountdeduct" value="${applyReallyAmount}">
    	
		&nbsp;&nbsp;<label class="lab1"><span class="red">划扣总笔数：</span></label>
		<label class="red" id="totalNum">${totalNum }</label>笔
		<input type="hidden" id="num" value="${totalNum }">
		<input type="hidden" id="hiddenNum" value="0"/>
		&nbsp;&nbsp;<label class="lab2"><span class="red">实还总金额：</span></label>
		<label class="red" id ="applyReallyAmount"><fmt:formatNumber value='${applyReallyAmount}'  pattern="#,#0.00"/></label> 元
		</p>
	<div class="box5">   
        <table class="table  table-bordered table-condensed table-hover " id ="tableList">
        <thead>
         <tr>
            <th><input type="checkbox" onclick="checkAll(this);" /></th>
            <th>序列号</th>
            <th>银行账号</th>
            <th>户名</th>
            <th>金额</th>
             <th>实还金额</th>
            <th>银行名称</th>
            <th>账户类型</th>
            <th>合同编号</th>
			<th>操作类型</th>
			<th>操作时间</th>
			<th>回盘时间</th>
			<th>回盘结果</th>
			<th>回盘原因</th>
			<th>备注</th>
            <th>操作</th>
        </tr>
		</thead>
        <tbody>
         <c:if test="${ items!=null && fn:length(items.list)>0}">
         <c:set var="index" value="0"/>
          <c:forEach items="${items.list}" var="item">
          <c:set var="index" value="${index+1}" />
             <tr>
             <td><input type="checkbox" name="checkBoxItem" value='${item.cpcnId }' onclick="chekbox_click();"
               <%-- deductAmount='${item.splitAmount}' reason='${item.splitBackResult}'
               deductsType='${item.dictDealType}' --%> cid='${item.cpcnId }'/>
             </td>
             <td>${item.serialNum}</td>
             <td>${item.accountNum}</td>
             <td>${item.accountName}</td>
             <td>${item.dealMoney}<c:if test="${item.dealMoney eq null}">0.00</c:if><input type="hidden" id="${item.cpcnId }money" name="${item.cpcnId }money" value="${item.dealMoney}<c:if test="${item.dealMoney eq null}">0.00</c:if>"></td>
             <td><fmt:formatNumber value="${item.applyReallyAmount}" pattern='0.00'/><c:if test="${item.applyReallyAmount eq null}">0.00</c:if>
             <input type="hidden" id="${item.cpcnId }applyReallyAmount" name="${item.cpcnId }applyReallyAmount" value="${item.applyReallyAmount}<c:if test="${item.applyReallyAmount eq null}">0.00</c:if>">
             </td>
             <td>${item.bankName}</td>
             <td>${item.accountTypeName}</td>
             <td>${item.contractCode}</td>
			 <td align="right" >${item.status}<c:if
					test="${item.deductType == '1' and item.status=='预约'}">-批量</c:if> <c:if
					test="${item.deductType != '1' and item.status=='预约'}">-实时</c:if></td>
			 <td>${item.operateTime}</td>
             <td>${item.backTimes}</td>
             <td>${item.backResult}</td>
             <td>${item.backReason}</td>
             <td>${item.note}</td>          
             <td>
             	<button class="btn_edit" id="history" onclick="showHistory('${item.cpcnId}');">历史</button>
             	<input type="button" class="btn_edit" onclick="showPaybackHis('','${item.cpcnId}','lisi');" value="已拆分历史" /></td>
             </td>
         </tr> 
         </c:forEach>  
         </c:if> 
         <c:if test="${ items==null || fn:length(items.list)==0}">
           <tr><td colspan="24" style="text-align:center;">没有待办数据</td></tr>
         </c:if>
     </tbody>
    </table>
</div>
 <div class="pagination">${items}</div>
</body>

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
</html>