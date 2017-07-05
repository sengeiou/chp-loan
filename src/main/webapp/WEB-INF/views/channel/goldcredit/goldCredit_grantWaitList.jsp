<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript" language="javascript" src='${context}/js/common.js'></script>
<script type="text/javascript" src="${context}/js/channel/goldcredit/popuplayer.js"></script>
<script type="text/javascript"
	src="${context}/js/pageinit/areaselect.js"></script>
<meta name="decorator" content="default"/>
<title>放款审核</title>
<script type="text/javascript">
$(function() {
	loan.initCity("addrProvice", "addrCity",null);
	loan.getstorelsit("storesName", "storeOrgId");
	$.popuplayer(".edit");
	// 点击清除，清除搜索页面中的数据，DOM
	$("#clearBtn").click(
			function() {
				$(':input', '#searchCondtionForm')
						.not(':button,:submit, :reset').val('').removeAttr(
								'checked').removeAttr('selected');
			});
	// 导出excel
	$("#btnExportExcel").click(function(){
		var queryParam = $("#searchCondtionForm").serialize();
		var selected = "";
		if($(":input[name='checkBoxItem']:checked").length>0){
			$(":input[name='checkBoxItem']:checked").each(function(){
        		if(selected!="")
        		{
        			selected+=","+$(this).val();
        		}else{
        			selected=$(this).val();
        		}
        	});
		}	
		window.location.href=ctx+"/channel/goldcredit/grantWait/exportExcel?loanCodes="+selected+"&"+queryParam;
	});
	//批量审核
	$("#btnBatchAudit").click(function(){
		var checkVal=null;
		if($(":input[name='checkBoxItem']:checked").length==0){
			   art.dialog.alert("请选择要进行操作的数据!");
	           return;					
		}else{
			$(":input[name='checkBoxItem']:checked").each(function(){	                		
         		if(checkVal!=null)
         		{
         			checkVal+=","+$(this).attr("applyId");
         		}else{
         			checkVal=$(this).attr("applyId");
         		}
     		});	
			var url=ctx +"/channel/goldcredit/grantWait/showAuditPage?checkVal="+checkVal;
			art.dialog.open(url, {title:'放款审核',lock:true, width:700,height:350},false);  
		}
	});
	/* var num = 0,money = 0.00;
	$(":input[name='checkBoxItem']").each(function(){
		var totalGrantMoney = parseFloat($(this).attr("lendingMoney"));
			money += totalGrantMoney;
			num ++;
		$("#totalGrantMoney").text(fmoney(money,2));
		$("#totalNum").text(num);
	});
	//checkAll
	var $checkBoxItem = $("input[name='checkBoxItem']") ;
	$("#checkAll").click(function () {
		var cumulativeLoan = 0.00,
		totalNum = 0;
		$checkBoxItem.prop("checked",this.checked);
		//全选按钮选中
		if ($(this).is(":checked")) {
			cumulativeLoan = money;
			totalNum = num;
		} 
		$("#hiddenGrant").val(cumulativeLoan);
		$("#hiddenNum").val(totalNum);
		$("#totalGrantMoney").text(fmoney(money,2));
		$("#totalNum").text(num);
	});
	$checkBoxItem.click(function(){
		var cumulativeLoan  = parseFloat($("#hiddenGrant").val());
		var totalNum = parseFloat($("#hiddenNum").val(),10);
		var hiddeNum = 0,hiddenMoney = 0.00;
		if ($(this).is(":checked")) {
			hiddenMoney = cumulativeLoan += parseFloat($(this).attr("lendingMoney"));
			hiddeNum = totalNum += 1;
		} else {
			if ($("input[name='checkBoxItem']:checked").length == 0){
				cumulativeLoan = money;
				totalNum = num;
			} else {
				hiddenMoney = cumulativeLoan -= parseFloat($(this).attr("lendingMoney"));
				hiddeNum = totalNum -= 1 ;
			}
		}
		$("#checkAll").prop("checked",($("input[name='checkBoxItem']").length == $("input[name='checkBoxItem']:checked").length ? true : false));
		
		$("#totalGrantMoney").text(fmoney(cumulativeLoan,2));
		$("#hiddenGrant").val(hiddenMoney);
		$("#totalNum").text(totalNum);
		$("#hiddenNum").val(hiddeNum);
	}); */
	
	// 点击全选
	$("#checkAll").click(function(){
		var curMoney = 0.0;
		var curNum = 0;
		if($('#checkAll').attr('checked')=='checked'|| $('#checkAll').attr('checked'))
		{
			$(":input[name='checkBoxItem']").each(function() {
			  $(this).attr("checked",'true');
			  curMoney = parseFloat($(this).attr("lendingMoney"))+curMoney;
			  curNum=curNum+1;
			});
			$('#totalItem').text(curNum);
			$('#numHid').val(curNum);
			$('#grantMoneyText').text(fmoney(curMoney, 2));
			$('#grantMoneyHid').val(curMoney);
		}else{
			$(":input[name='checkBoxItem']").each(function() {
			  $(this).removeAttr("checked");
			});
			$('#grantMoneyHid').val(0.00);
			$('#numHid').val(0);
			$('#totalItem').text($("#hiddenNum").val());
			$('#grantMoneyText').text(fmoney($("#hiddenTotalGrant").val(), 2));
		}
		
   });
	
	
	$(":input[name='checkBoxItem']").click(function() {
		// 记录总金额，当length为0时，进行总金额的处理
		var totalGrantMoney = $("#hiddenTotalGrant").val();
		var totalNum = $("#hiddenNum").val();
		// 获得单个单子的金额
		var deductAmount = parseFloat($(this).attr("lendingMoney"));
		var num = 1;
		if ($(this).attr('checked') == 'checked' || $(this).attr('checked')) {
			var hiddenNum = parseFloat($("#numHid").val()) + num;
			var hiddenDeduct = parseFloat($("#grantMoneyHid").val()) + deductAmount;
			$('#totalItem').text(hiddenNum);
			$("#numHid").val(hiddenNum);
			$("#grantMoneyHid").val(hiddenDeduct);
			$('#grantMoneyText').text(fmoney(hiddenDeduct, 2));
		} else {
			$('#checkAll').removeAttr("checked");
			if ($(":input[name='checkBoxItem']:checked").length == 0) {
				$('#totalItem').text(totalNum);
				$("#grantMoneyText").text(fmoney(totalGrantMoney, 2));
				$('#grantMoneyHid').val(0.00);
				$('#numHid').val(0);
			} else {
				var hiddenDeduct = parseFloat($("#grantMoneyHid").val()) - deductAmount;
				$('#totalItem').text(parseFloat($("#numHid").val()) - num);
				$("#numHid").val(parseFloat($("#numHid").val()) - num);
				$("#grantMoneyText").text(fmoney(hiddenDeduct, 2));
				$("#grantMoneyHid").val(hiddenDeduct);
			}
		}
	});
	
	// 伸缩
	$("#showMore").click(function(){
		if(document.getElementById("T1").style.display=='none'){
			document.getElementById("showMore").src='../../../../static/images/down.png';
			document.getElementById("T1").style.display='';
			document.getElementById("T2").style.display='';
		}else{
			document.getElementById("showMore").src='../../../../static/images/up.png';
			document.getElementById("T1").style.display='none';
			document.getElementById("T2").style.display='none';
		}
	});
	
});
	function openHtml(checkVal){
		var url=ctx +"/channel/goldcredit/grantWait/showAuditPage?checkVal="+checkVal;
		art.dialog.open(url, {title:'放款审核',lock:true, width:700,height:350},false); 
	}
	// 格式化，保留两个小数点
    function fmoney(s, n) {  
        n = n > 0 && n <= 20 ? n : 2;  
        s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";  
        var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];  
        t = "";  
        for (i = 0; i < l.length; i++) {  
            t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");  
        }  
        return t.split("").reverse().join("") + "." + r;  
    }  
    function page(n, s) {
    	if (n)
    		$("#pageNo").val(n);
    	if (s)
    		$("#pageSize").val(s);
    	$("#searchCondtionForm").attr("action","${ctx }/channel/goldcredit/grantWait/getAuditGrantInfo");
    	$("#searchCondtionForm").submit();
    	return false;
    }
</script>
	
</head>
<body>
	<form id="openForm" class="hide" action="${ctx }/channel/goldcredit/grantWait/showAuditPage" method="post">
		<input name="checkVal" />
	</form>
	<div class="control-group">
         <form:form id="searchCondtionForm" action="${ctx }/channel/goldcredit/grantWait/getAuditGrantInfo" modelAttribute="LoanFlowQueryParam">
         	<input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo}" />
	   		<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}" />
          <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td><label class="lab">客户姓名：</label><form:input type="text" class="input_text178" path="customerName"></form:input></td>
                <td><label class="lab">合同编号：</label><form:input type="text" class="input_text178" path="contractCode"></form:input></td>
                <td><%-- <label class="lab">借款类型：</label><form:select class="select180" path="loanType">
				<form:option value="">请选择</form:option>
				<c:forEach items="${fns:getDictList('jk_loan_type')}" var="loan_type">
								<form:option value="${loan_type.value}">${loan_type.label}</form:option>
				             </c:forEach>
				</form:select> --%>
				<label class="lab">提交批次：</label><form:select class="select180" path="submissionBatch"><form:option value="">请选择</form:option>
				 <c:forEach items="${submitBatchLists}" var="submitBatch">
								<form:option value="${submitBatch}">${submitBatch}</form:option>
				 </c:forEach></form:select>
				</td>
            </tr>
            <tr>
                <td><label class="lab">管辖城市：</label><form:select class="select180" style="width:110px" path="provinceCode" id="addrProvice">
                <form:option value="">选择省份</form:option>
                <c:forEach var="item" items="${provinceList}" varStatus="status">
		             <form:option value="${item.areaCode }">${item.areaName}</form:option>
	            </c:forEach>
                </form:select>-<form:select class="select180" style="width:110px" path="cityCode" id="addrCity">
                <form:option value="">请选择</form:option>
                <c:forEach var = "item" items = "${cityList }">
                	<form:option value="${item.areaCode }">${item.areaName}</form:option>
                </c:forEach>
                </form:select>
                </td>
                <td><label class="lab">门店：</label><input type="text"
							class="input_text178" name="storeName" id="storesName"
							readonly="readonly" value="${LoanFlowQueryParam.storeName }" /> <i
							id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
							<input type="hidden" name="storeOrgIds"
							value="${LoanFlowQueryParam.storeOrgId}" id="storeOrgId" /></td>
                <td><label class="lab">放款批次：</label><form:select class="select180" path="grantBatchCode">
				 <form:option value="">请选择</form:option>
				 <c:forEach items="${grantBatchLists}" var="grantBatchList">
					<form:option value="${grantBatchList}">${grantBatchList}</form:option>
				 </c:forEach>
				 </form:select></td>
            </tr>
            <tr id="T1" style="display:none">
                 <td><label class="lab">放款时间：</label><input id="lendingTimeStart"  name="lendingTimeStart"  type="text" class="Wdate input_text70" size="10"  value = "<fmt:formatDate value='${LoanFlowQueryParam.lendingTimeStart}' pattern='yyyy-MM-dd'/>"
                    onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'lendingTimeEnd\')}'})" style="cursor: pointer">-<input id="lendingTimeEnd" name="lendingTimeEnd"  type="text" class="Wdate input_text70" size="10"  
                    onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'lendingTimeStart\')}'})" style="cursor: pointer" value = "<fmt:formatDate value='${LoanFlowQueryParam.lendingTimeEnd}' pattern='yyyy-MM-dd'/>">
                </td>
                <td ><label class="lab">提交日期：</label>
                <input id="submissionDateStart"  name="submissionDateStart"  type="text" class="Wdate input_text70" size="10"  value = "<fmt:formatDate value='${LoanFlowQueryParam.submissionDateStart}' pattern='yyyy-MM-dd'/>"
                    onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'submissionDateEnd\')}'})" style="cursor: pointer">-<input id="submissionDateEnd" name="submissionDateEnd"  type="text" class="Wdate input_text70" size="10"  
                    onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'submissionDateStart\')}'})" style="cursor: pointer" value = "<fmt:formatDate value='${LoanFlowQueryParam.submissionDateEnd}' pattern='yyyy-MM-dd'/>">
                </td>
                <td><label class="lab">是否加急：</label><form:select class="select180" path="urgentFlag">
                <option value="">请选择</option>
                <c:forEach items="${fns:getDictList('jk_urgent_flag')}" var="card_type">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
				 </c:forEach>
                </form:select></td>
            </tr>
            <tr id="T2" style="display:none">
			  <%-- <td><label class="lab">提交批次：</label><form:select class="select180" path="submissionBatch"><form:option value="">请选择</form:option>
				 <c:forEach items="${submitBatchLists}" var="submitBatch">
								<form:option value="${submitBatch}">${submitBatch}</form:option>
				 </c:forEach></form:select>
				</td> --%>
            </tr>
        </table>
       <div class="tright pr30 pb5"><input class="btn btn-primary" type="submit" value="搜索" id="searchBtn"></input>
                                    <button class="btn btn-primary" id="clearBtn">清除</button>
           <div style="float: left; margin-left: 45%; padding-top: 10px">
	          <img src="../../../../static/images/up.png" id="showMore"></img>
	       </div>
	 </div>
	 </form:form>
	 </div>
    <p class="mb5">
    	<button class="btn btn-small" id="btnBatchAudit">批量审核</button>
    	<button class="btn btn-small" id="btnExportExcel">导出excel</button>
    	<input type="hidden" id="hiddenTotalGrant" value="${totalGrantMoney}">
		<input type="hidden" id="hiddenNum" value="${totalItem}">
    	&nbsp;&nbsp;<label class="lab1">放款总金额：</label><label class="red" id="totalGrantMoney">
    		 <span id="grantMoneyText">
		  	 <fmt:formatNumber value='${totalGrantMoney}' pattern="#,##0.00" />
		  	</span>
    	</label>
    	&nbsp;&nbsp;<label class="lab1">放款总笔数：</label><label class="red" id="totalNum">
    		<span id="totalItem">
		     ${totalItem}
		     </span>
    	</label>笔
    	<!-- <input type="hidden" id="hiddenGrant" value="0.00">
    	<input type="hidden" id="hiddenNum" value="0"> -->
    	<input type="hidden" id="numHid" value="0"/>
		<input type="hidden" id="grantMoneyHid" value="0.00"/>
    </p>
    <div>
   	 <sys:columnCtrl pageToken="gWaiteList"></sys:columnCtrl>
   	</div>
   	<div class="box5">
    <table  id = "tableList"  class="table  table-bordered table-condensed table-hover" >
       <thead>
        <tr>
            <th><input type="checkbox" id="checkAll"/></th>
            <th>合同编号</th>
            <th>门店名称</th>
            <th>客户姓名</th>
            <th>共借人</th>
            <th>借款类型</th>
            <th>借款产品</th>
            <th>合同金额</th>
            <th>放款金额</th>
			<th>催收服务费</th>
			<th>未划金额</th>
            <th>批复期限(月)</th>
            <th>放款账号</th>
            <th>开户行</th>
            <th>账户姓名</th>
<!-- 		<th>放款途径</th>-->   
            <th>渠道</th>
            <th>模式</th>
            <th>放款日期</th>
            <th>放款批次</th>
            <th>加急标识</th>
            <th>提交批次</th>
            <th>提交日期</th>
			<%-- <th>是否电销</th> --%>
	        <th>操作</th>
        </tr>
        </thead>
        <tbody>
       <c:if test="${ workItems!=null && fn:length(workItems)>0}">
         <c:set var="index" value="0"/>
          <c:forEach items="${workItems}" var="item" varStatus="status">
          <c:set var="index" value="${index+1}" />
             <tr>
             	<td>
             		<input type="checkbox" name="checkBoxItem" applyId='${item.applyId}' value='${item.loanCode}'
             		 lendingMoney= "${item.lendingMoney}"/> ${status.count}
              	</td>
             <td>${item.contractCode}</td>
             <td>${item.storeName}</td>
             <td>${item.customerName}</td>
             <td>${empty item.coborrowerName || item.coborrowerName == 'null' ? "" : item.coborrowerName}</td>
             <td>信借</td>
             <td>${item.replyProductName}</td>
             <td><fmt:formatNumber value='${item.contractMoney}' pattern="#,#00.00"/></td>
             <td><fmt:formatNumber value='${item.lendingMoney}' pattern="#,#00.00"/></td>
             <td><fmt:formatNumber value='${item.urgeServiceFee}' pattern="#,#00.00"/></td>
             <td>
             <fmt:formatNumber value='${item.unDeductMoney}' pattern="#,#00.00"/>
             </td>
             <td>${item.replyMonth}</td>
             <td>${item.lendingAccount}</td>
             <td>${item.cautionerDepositBank}</td>
             <td>${item.bankAccountName}</td>
             <%-- <td>${item.lendingWayName}</td> --%>
             <td>${item.channelName}</td> 
             <td></td>
            <td><fmt:formatDate value='${item.lendingTime}' pattern='yyyy-MM-dd'/></td>
             <td>${item.grantBatchCode }</td> 
             <%-- <td>${item.telesalesFlag}</td> --%>
             
             <td>${item.urgentFlag}</td>
             <td>${item.submissionBatch}</td>
             <td>${fn:substring(item.submissionDate,0,10)}</td>   
             <td style="position:relative">
				<input type="button" class="btn btn_edit edit" value="操作"/>
				 <div class="alertset" style="display:none">
				 	<button class="btn_edit"  onclick="openHtml('${item.applyId}')">办理</button>
             		<button class="btn_edit"  onclick="showHisByLoanCode('${item.loanCode}')">历史</button>
				 </div>
			 </td>        
            </tr> 
         </c:forEach>  
         </c:if>
         <c:if test="${workItems==null || fn:length(workItems)==0}">
           <tr><td colspan="24" style="text-align:center;">没有待办数据</td></tr>
         </c:if>
        </tbody> 
    </table>
    <c:if test="${workItems!=null || fn:length(workItems)>0}">
		<div class="page">${page}</div>
	</c:if>
    </div>
   <%--  <div class="pagination">${workItems}</div> --%>
</body>
</html>