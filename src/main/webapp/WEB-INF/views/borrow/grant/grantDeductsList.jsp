<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>放款当日待划扣</title>
<script type="text/javascript" language="javascript"
	src='${context}/js/common.js'></script>
<script src="${context}/js/grant/grantDeducts.js?version=20161209" type="text/javascript"></script>
<script>
function page(n, s) {
	if (n)
		$("#pageNo").val(n);
	if (s)
		$("#pageSize").val(s);
	$("#deductsForm").attr("action", "${ctx}/borrow/grant/grantDeducts/deductsInfo?returnUrl=grantDeductsList&result=today");
	$("#deductsForm").submit();
	return false;
}
//省市级联
	$(document).ready(function() {
		loan.initCity("addrProvice", "addrCity", null);
		if($("#autoDeductsValue").val()=='0'){
			btnCol('button', 'start');
		}else{
			btnCol('hidden', 'stop');
		}
	});
	$(document).ready(
			function() {
				loan.getstorelsit("name","storeId");
				 var msg = "${message}";
					if (msg != "" && msg != null) {
						art.dialog.alert(msg);
					}
			});
</script>
<meta name="decorator" content="default"/>
</head>
<body>
	<div class="control-group">
	         <input type="hidden" id="autoDeductsID" value="${autoDeducts.id}"/>
             <input type="hidden" id="autoDeductsFlag" value="${autoDeducts.sysFlag}"/>
             <input type="hidden" id="autoDeductsName" value="${autoDeducts.sysName}"/>
             <input type="hidden" id="autoDeductsValue" value="${autoDeducts.sysValue}"/>
            <%--  <input type="hidden" id="platFormID" value="${deductsPlatform.id}"/>
             <input type="hidden" id="platFormFlag" value="${deductsPlatform.sysFlag}"/>
             <input type="hidden" id="platFormName" value="${deductsPlatform.sysName}"/>
             <input type="hidden" id="platFormValue" value="${deductsPlatform.sysValue}"/> --%>
       <form:form  method="post" modelAttribute="urgeMoneyEx" id="deductsForm">
         <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
         <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" /> 
		 <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
		 <input id="returnUrl" type="hidden" value="grantDeductsList">
		 <input id="checkVal" type="hidden" />
		 <input id="result" type="hidden" value="today">
		 <input id="rule" type="hidden" path="rule">
            <tr>
               <td><label class="lab">客户姓名：</label><form:input type="text" class="input_text178" path="customerName"></form:input></td>
               <td><label class="lab">合同编号：</label><form:input type="text" class="input_text178" path="contractCode"></form:input></td>
               <td><label class="lab">门店：</label><form:input type="text" id="name" class="input_text178" path="name" readonly="true"></form:input>
                	  <i id="selectStoresBtn"
						class="icon-search" style="cursor: pointer;"></i>
				    <form:hidden path="storeId" id="storeId"/>
				</td>
            </tr>
            <tr>
                <td><label class="lab">划扣回盘结果：</label><form:select class="select180" path="splitBackResult">
						<form:option value="">请选择</form:option>
						<c:forEach items="${fns:getDictList('jk_urge_counteroffer_result')}" var="card_type">
						<c:if test="${card_type.label=='划扣失败'||card_type.label=='处理中'||card_type.label=='处理中(导出)' || card_type.label=='待划扣'}">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
						</c:if>
				 		</c:forEach>
						</form:select></td>
                <td><label class="lab">签约平台：</label>
	            <form:select class="select180" path="bankSigningPlatform">
						<form:option value="">请选择</form:option>
						 <c:forEach items="${fns:getDictList('jk_deduct_plat')}"
								var="item">
								<c:if test="${item.value=='0'|| item.value=='1' || item.value=='3'}">
									<form:option value="${item.value}">${item.label}</form:option>
					      		</c:if>
							</c:forEach>					
				</form:select>
            </td>
				 <td><label class="lab">是否电销：</label><form:select class="select180" path="customerTelesalesFlag"><option value="">请选择</option>
			    <c:forEach items="${fns:getDictList('jk_telemarketing')}" var="card_type">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
				 </c:forEach>
			    </form:select></td>
            </tr>
			<tr id="T1" style="display:none">
                <td>
                <label class="lab">所属城市：</label><form:select class="select180" style="width:110px" path="provinceCode" id="addrProvice">
                <form:option value="">选择省份</form:option>
                <c:forEach var="item" items="${provinceList}" varStatus="status">
		             <form:option value="${item.areaCode }">${item.areaName}</form:option>
	            </c:forEach>
                </form:select>-<form:select class="select180" style="width:110px" path="cityCode" id="addrCity">
                <form:option value="">请选择</form:option>
                </form:select>
                </td>
				<td><label class="lab">放款日期：</label><input id="stratDay" name="startDate" 
                 value="<fmt:formatDate value='${urgeMoneyEx.startDate}' pattern='yyyy-MM-dd'/>"
                     type="text" class="Wdate input_text70" size="10"  
                                        onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'endDay\')}'})" style="cursor: pointer" ></input>-<input id="endDay" name="endDate" 
                 value="<fmt:formatDate value='${urgeMoneyEx.endDate}' pattern='yyyy-MM-dd'/>"
                     type="text" class="Wdate input_text70" size="10"  
                                        onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'stratDay\')}'})" style="cursor: pointer" ></td>
				<td><label class="lab">划扣平台：</label><form:select class="select180" path="dictDealType">
						<form:option value="">请选择</form:option>
						<c:forEach items="${fns:getDictList('jk_deduct_plat')}" var="card_type">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
				 		</c:forEach>
				</form:select></td>
			</tr>
            <tr id="T2" style="display:none">
            	<td><label class="lab">自动划扣状态：</label><form:select class="select180" path="autoDeductFlag">
						<form:option value="">请选择</form:option>
						<form:option value="1">自动</form:option>
               			<form:option value="0">手动</form:option>
				</form:select>
				</td>
				<td><label class="lab">开户行名称：</label> 
				   <sys:multipleBank bankClick="selectBankBtn"  bankName="search_applyBankName" bankId="bankId"></sys:multipleBank>
				    <input id="search_applyBankName" type="text" class="input_text178" name="applyBankName"  value='${urgeMoneyEx.bankNameLabel}'/>&nbsp;<i id="selectBankBtn"
				    class="icon-search" style="cursor: pointer;"></i> 
					<form:input type="hidden" id="bankId" name='bank' path="bankName"></form:input>
				</td>
				<td><label class="lab">批次：</label><form:select class="select180" path="grantBatch">
						<form:option value="">请选择</form:option>
						<c:forEach items="${grantBatchList}" var="card_type">
								<form:option value="${card_type.grantBatch}">${card_type.grantBatch}</form:option>
				 		</c:forEach>
				</form:select></td>
            </tr>
             <tr id="T3" style="display:none">
            	<td><label class="lab">畅捷是否签约：</label><form:select class="select180" path="cjSign">
						<form:option value="">请选择</form:option>
						<form:option value="0">未签约</form:option>
               			<form:option value="1">已签约</form:option>
               			<form:option value="2">签约失败</form:option>
				</form:select></td>
				<td>
			    <label class="lab">借款产品：</label><form:select class="select180" path="productName"><option value="">请选择</option>
			    <c:forEach items="${productList}" var="card_type">
								<form:option value="${card_type.productCode}">${card_type.productName}</form:option>
				 </c:forEach>
			    </form:select>
			    </td>
            </tr>
        </table>
        	
        <div class="tright pr30 pb5">
              <input class="btn btn-primary" type="submit" value="搜索"></input>
              <button class="btn btn-primary" id="clearBtn">清除</button>
        <div style="float: left; margin-left: 45%; padding-top: 10px">
	      <img src="../../../../static/images/up.png" id="showMore"></img>
	     </div>
		</div>
	</form:form>
	   </div>
		<p >
		<input id="startFlag" type="hidden">
    	<input class="btn btn-small" id="stopDeducts" type="button" value="停止自动划扣" />
    	<input class="btn btn-small" id="backBtn" type="button" value="追回" data-toggle="modal"/>
		<input class="btn btn-small" id="daoHBtn" type="button" value="导出(好易联)"/>
		<input class="btn btn-small" id="daoFBtn" type="button" value="导出(富友)"/>
		<input class="btn btn-small" id="daoTLBtn" type="button" value="导出(通联)"/>
		<input class="btn btn-small" id="daoZBtn" type="button" value="导出(中金)"/>
		<input role="button" class="btn btn-small" data-target="#uploadAuditedModal" data-toggle="modal" id="shangF" type="button" value="上传回盘结果(富友)"/>
		<input role="button" class="btn btn-small" data-target="#uploadAuditedModal" data-toggle="modal" id="shangH" type="button" value="上传回盘结果(好易联)"/>
		<input role="button" class="btn btn-small" data-target="#uploadAuditedModal" data-toggle="modal" id="shangT" type="button" value="上传回盘结果(通联)"/>
		<input role="button" class="btn btn-small" data-target="#uploadAuditedModal" data-toggle="modal" id="shangZ" type="button" value="上传回盘结果(中金)"/>
		<input class="btn btn-small" id="sendF" type="button" value="发送富友划扣"/>
		<input class="btn btn-small" id="sendZ" type="button" value="发送中金划扣"/>
		<input class="btn btn-small" id="sendH" type="button" value="发送好易联划扣"/>
		<input class="btn btn-small" id="sendT" type="button" value="发送通联划扣"/>
		<br/>
    	<label class="lab1"><span class="red">划扣累计金额：</span></label><label class="red" id="deductAmount"><fmt:formatNumber value='${deductsAmount}' pattern="#,##0.00"/>
    	</label>元
    	<input type="hidden"  id="hiddenDeduct" value="0.00"/>
    	<input type="hidden" id="deduct" value="${deductsAmount}">
		<label class="lab1"><span class="red">划扣总笔数：</span></label><label class="red" id="totalNum">${totalNum }</label>笔
		<input type="hidden" id="num" value="${totalNum }">
		<input type="hidden" id="hiddenNum" value="0"/>
		</p>
		<sys:columnCtrl pageToken="grantdeductlist"></sys:columnCtrl>
		
        <table id="tableList" class="table  table-bordered table-condensed table-hover ">
        <thead>
         <tr>
            <th><input type="checkbox" id="checkAll" onclick="allOrNo()" /></th>
            <th>合同编号</th>
            <th>门店名称</th>
            <th>客户姓名</th>
            <th>账户名字</th>
            <th>签约平台</th>
            <th>借款产品</th>
            <th>合同金额</th>
            <th>放款金额</th>
			<th>催收服务费</th>
			<th>征信费</th>
			<th>信访费</th>
			<th>费用总计</th>
			<th>未划金额</th>
			<th>划扣金额</th>
			<th>划扣平台</th>
			<th>批借期限</th>
            <th>开户行</th>
			<th>放款日期</th>
			<th>最新划扣日期</th>
			<th>划扣回盘结果</th>
			<th>失败原因</th>
			<th>是否电销</th>
            <th>渠道</th>
            <th>自动划扣状态</th>
            <th>畅捷是否签约</th>
            <th>放款批次</th>
            <th>操作</th>
        </tr>
		</thead>
        <tbody>
         <c:if test="${ urgeList!=null && fn:length(urgeList.list)>0}">
         <c:set var="index" value="0"/>
          <c:forEach items="${urgeList.list}" var="item">
          <c:set var="index" value="${index+1}" />
             <tr>
             <td><input type="checkbox" name="checkBoxItem" 
               deductAmount='${item.waitUrgeMoeny}' reason='${item.splitBackResult}'
               deductsType='${item.dictDealType}' cid='${item.urgeId }'/>
             </td>
             <td>${item.contractCode}</td>
             <td>${item.name }</td>
             <td>${item.customerName}</td>
             <td>${item.bankAccountName}</td>
             <td>${item.bankSigningPlatform}</td>
             <td>${item.productName}</td>
             <td><fmt:formatNumber value='${item.contractAmount}' pattern="#,#00.00"/></td>
             <td><fmt:formatNumber value='${item.grantAmount}' pattern="#,#00.00"/></td>
             <td><fmt:formatNumber value='${item.urgeMoeny}' pattern="#,#00.00"/></td>
             <td><c:if test="${item.productName=='农信借'}"><fmt:formatNumber value='${item.feeCredit}'
				pattern="#,##0.00"/></c:if></td>
			 <td><c:if test="${item.productName=='农信借'}"><fmt:formatNumber value='${item.feePetition}'
				pattern="#,##0.00"/></c:if></td>
			 <td><fmt:formatNumber value='${item.feeSum}' pattern="#,##0.00"/></td>
             <td><fmt:formatNumber value='${item.waitUrgeMoeny}' pattern="#,#00.00"/></td>
             <td><fmt:formatNumber value='${item.waitUrgeMoeny}' pattern="#,#00.00"/></td>
             <td>${item.dictDealType}</td>
             <td>${item.contractMonths}</td>
             <td>${item.bankName}</td>
             <td><fmt:formatDate value="${item.lendingTime}"
							type="date" /></td>
             <td><fmt:formatDate value="${item.decuctTime}"
							type="date" /></td>
             <td>${item.splitBackResult}</td>
             <td>${item.deductFailReason}</td>
             <td>${item.customerTelesalesFlag}</td>
             <td>${item.loanFlag}</td>
             <td>
             <c:if test="${item.autoDeductFlag =='1'}">
             	自动
             </c:if>
             <c:if test="${item.autoDeductFlag =='0'}">
             	手动
             </c:if>
             </td>
             <td>
             <c:choose>
	             <c:when test="${item.cjSign=='1'}">已签约</c:when>
	             <c:when test="${item.cjSign=='2'}">签约失败</c:when>
	             <c:otherwise>未签约</c:otherwise>
             </c:choose>
             </td>
             <td>${item.grantBatch}</td>            
             <td><input class="btn_edit" sid='${item.urgeId}' name="history" value="历史" type="button">
             <input class="btn_edit" onclick="showPaybackHis('${item.urgeId}','','lisi');" value="拆分历史" type="button"></td>
         </tr> 
         </c:forEach>  
         </c:if>
         <c:if test="${ urgeList==null || fn:length(urgeList.list)==0}">
           <tr><td colspan="18" style="text-align:center;">没有待办数据</td></tr>
         </c:if>
     </tbody>
    </table>

     <div class="pagination">${urgeList}</div> 
     
<div class="modal fade" id="uploadAuditedModal" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<form id="uploadAuditForm" role="form" enctype= "multipart/form-data" method="post">
			<div class="modal-dialog">
				<div class="modal-content">
				
					<div class="modal-header">
						<h4 class="modal-title" id="myModalLabel">上传回执结果</h4>
						
					</div>
					 <div class="modal-body" > 
					<input type='file' name="file" id="fileid">
					 </div>
					 <div class="modal-footer">
       					 <input class="btn btn-primary" type="submit" value="确认"/>
      					 <button class="btn btn-primary"  class="close" data-dismiss="modal" >取消</button>
   					 </div>
			</div>
			</div>
		</form>
	</div>
	<!-- 追回 ，选择划扣平台-->
	<div class="modal fade" id="backModal" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<form id="uploadAuditForm" role="form" enctype= "multipart/form-data" method="post">
			<div class="modal-dialog " role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<label class="lab">追回选择平台：</label>
						</div>
						<div class="modal-body">
							<select id="backPlat" class="select180">
							<c:forEach items="${fns:getDictList('jk_recove_way')}"
								var="card_type">
								<option value="${card_type.value}">${card_type.label}</option>
							</c:forEach>
							</select>
					   </div>
               <div class="modal-footer">
                         <button class="btn btn-primary" id="backSure">确认</button>
                         <button class="btn btn-primary" >取消</button>
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