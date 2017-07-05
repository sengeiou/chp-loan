<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>放款以往待划扣</title>
<script type="text/javascript" language="javascript"
	src='${context}/js/common.js'></script>
<script src="${context}/js/grant/grantDeducts.js?version=20161209" type="text/javascript"></script>
<script>
function page(n, s) {
	if (n)
		$("#pageNo").val(n);
	if (s)
		$("#pageSize").val(s);
	$("#deductsForm").attr("action", "${ctx }/borrow/grant/grantDeducts/deductsInfo?returnUrl=grantDeductsBefList&result=bef");
	$("#deductsForm").submit();
	return false;
}
//省市级联
$(document).ready(
		function() {
			loan.initCity("addrProvice", "addrCity",null);
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
     <form:form  method="post" modelAttribute="urgeMoneyEx" id="deductsForm">
        <table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" /> 
		 <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
		 <input id="returnUrl" type="hidden" value="grantDeductsBefList">
		 <input id="result" type="hidden" value="bef">
		 <input id="checkVal" type="hidden">
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
                
                <td><label class="lab">划扣回盘结果：</label>
                <form:select class="select180" path="splitBackResult">
						<form:option value="">请选择</form:option>
						<c:forEach items="${fns:getDictList('jk_urge_counteroffer_result')}" var="card_type">
						<c:if test="${card_type.value=='1'||card_type.value=='3'||card_type.value=='4'||card_type.value=='5'||card_type.value=='7'}">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
						</c:if>
				 		</c:forEach>
				</form:select></td>
                <td><label class="lab">划扣平台：</label><form:select class="select180" path="dictDealType">
						<form:option value="">请选择</form:option>
						<c:forEach items="${fns:getDictList('jk_deduct_plat')}" var="card_type">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
				 		</c:forEach>
				</form:select></td>
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
                 value="<fmt:formatDate value='${UrgeServicesMoneyEx.startDate}' pattern='yyyy-MM-dd'/>"
                     type="text" class="Wdate input_text70" size="10"  
                                        onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'endDay\')}'})" style="cursor: pointer" ></input>-<input id="endDay" name="endDate" 
                 value="<fmt:formatDate value='${UrgeServicesMoneyEx.endDate}' pattern='yyyy-MM-dd'/>"
                     type="text" class="Wdate input_text70" size="10"  
                                        onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'stratDay\')}'})" style="cursor: pointer" ></td>
                                        
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
		<button id="backBtn" role="button" class="btn btn-small"  data-toggle="modal">追回</button>
		<button id="daoBef" class="btn btn-small">导出excel</button>
		<button class="btn btn-small" id="daoHBtn">导出(好易联)</button>
		<button class="btn btn-small" id="daoFBtn">导出(富友)</button>
		<button class="btn btn-small" id="daoTLBtn">导出(通联)</button>
		<button class="btn btn-small" id="daoZBtn">导出(中金)</button>
		<button role="button" class="btn btn-small" data-target="#uploadAuditedModal" data-toggle="modal" id="shangF">上传回盘结果(富友)</button>
		<button role="button" class="btn btn-small" data-target="#uploadAuditedModal" data-toggle="modal" id="shangH">上传回盘结果(好易联)</button>
		<button role="button" class="btn btn-small" data-target="#uploadAuditedModal" data-toggle="modal" id="shangT">上传回盘结果(通联)</button>
		<button role="button" class="btn btn-small" data-target="#uploadAuditedModal" data-toggle="modal" id="shangZ">上传回盘结果(中金)</button>
		<button class="btn btn-small" id="sendF">发送富友划扣</button>
		<button class="btn btn-small" id="sendZ">发送中金划扣</button>
		<button class="btn btn-small" id="sendH">发送好易联划扣</button>
		<button class="btn btn-small" id="sendT">发送通联划扣</button>
		</br><label class="lab1"><span class="red">划扣累计金额：</span></label><label class="red" id="deductAmount"><fmt:formatNumber value='${deductsAmount}' pattern="#,##0.00"/>
		</label>
		<input type="hidden"  id="hiddenDeduct" value="0.00"/>
		<input type="hidden" id="deduct" value="${deductsAmount}">元
		&nbsp;&nbsp;<label class="lab1"><span class="red">划扣总笔数：</span></label><label class="red" id="totalNum">${totalNum}</label>笔
		<input type="hidden" id="num" value="${totalNum }">
		<input type="hidden" id="hiddenNum" value="0"/>
		</p>
		<sys:columnCtrl pageToken="grantdeductbeflist"></sys:columnCtrl>
		<div class="box5" style="height：300px">
        <table id="tableList"  class="table  table-bordered table-condensed table-hover ">
        <thead>
         <tr>
            <th><input type="checkbox" id="checkAll" onclick="allOrNo()"/></th>
            <th>合同编号</th>
            <th>门店名称</th>
            <th>客户姓名</th>
            <th>账户名字</th>
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
              <td><input class="btn_edit" sid='${item.urgeId}' name="history" value="历史" type="button">
             <input class="btn_edit" onclick="showPaybackHis('${item.urgeId}','','lisi');"  name="splitHistory" value="拆分历史" type="button"></td>
         </tr> 
         </c:forEach>  
         </c:if>
         <c:if test="${ urgeList==null || fn:length(urgeList.list)==0}">
           <tr><td colspan="21" style="text-align:center;">没有待办数据</td></tr>
         </c:if>
     </tbody>
        
    </table>
	</div>
</div>
	<div class="pagination">${urgeList}</div> 
	
<div class="modal fade" id="uploadAuditedModal" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<form id="uploadAuditForm" role="form" enctype= "multipart/form-data" method="post">
			<div class="modal-dialog">
				<div class="modal-content">
				
					<div class="modal-header">
						<h4 class="modal-title" id="myModalLabel">上传回执结果4</h4>
							</div>
						<div class="modal-body">
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
			</div>
		</form>
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