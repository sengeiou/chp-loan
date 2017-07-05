<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script src="${context}/js/common.js" type="text/javascript"></script>
<script src="${context}/js/grant/grantJYJDone.js" type="text/javascript"></script>
<script type="text/javascript" src="${context}/js/payback/ajaxfileupload.js"></script>
<script>
function page(n, s) {
	if (n)
		$("#pageNo").val(n);
	if (s)
		$("#pageSize").val(s);
	$("#grantForm").attr("action", "${ctx}/grantDoneJYJ/grantDone?listFlag=${listFlag}");
	$("#grantForm").submit();
	return false;
}
$(document).ready(function() {
			loan.getstorelsit("storesCode","storeOrgId");
			var msg = "${message}";
			if (msg != "" && msg != null) {
				art.dialog.alert(msg);
			};
		

		// 点击清除，清除搜索页面中的数据，DOM
		$("#clearBtn").click(function(){
		 $(':input','#grantForm')
		 .not(':button, :submit,:reset,:hidden')
		  .val('')
		  .removeAttr('checked')
		  .removeAttr('selected');
		 $('select').val();
		 $('select').trigger("change");
		 $('#grantForm').submit();
		});
		
		// 伸缩
		$("#showMore").click(function(){
			if(document.getElementById("T1").style.display=='none'){
				document.getElementById("showMore").src=context+'/static/images/down.png';
				document.getElementById("T1").style.display='';
				document.getElementById("T2").style.display='';
				document.getElementById("T3").style.display='';
			}else{
				document.getElementById("showMore").src=context+'/static/images/up.png';
				document.getElementById("T1").style.display='none';
				document.getElementById("T2").style.display='none';
				document.getElementById("T3").style.display='none';
			}
		});
		
		// 导出excel,默认导出全部
		$("#daoBtn").click(function(){
			var idVal = "";
			var listFlag = $("#listFlag").val();
			var grantForm = $("#grantForm").serialize();
			if(listFlag=='TG'){
				grantForm+='&loanFlag=3'+'&tgFlag=1';
			}
			if($(":input[name='checkBoxItem']:checked").length>0){
				$(":input[name='checkBoxItem']:checked").each(function(){
	        		if(idVal!="")
	        		{
	        			idVal+=","+$(this).attr("fId");
	        		}else{
	        			idVal=$(this).attr("fId");
	        		}
	        	});
			}
			window.location.href=ctx+"/grantDoneJYJ/grantDoneExl?idVal="+idVal+"&"+grantForm+"&listFlag="+listFlag;
		});
		
		// 单选计算金额
		$(":input[name='checkBoxItem']").click(function(){
			var cumulativeLoan  = parseFloat($("#hiddenGrant").val());
			var totalNum = parseFloat($("#hiddenNum").val(),10);
			var hiddenTotalMoney = $("#hiddenTotalMoney").val();
			var hiddenTotalNum = $("#hiddenTotalNum").val();
			var hiddeNum = 0,hiddenMoney = 0.00;
			if ($(this).is(":checked")) {
				hiddenMoney = cumulativeLoan += parseFloat($(this).attr("contractMoney"));
				hiddeNum = totalNum += 1;
			} else {
				if ($("input[name='checkBoxItem']:checked").length == 0){
					cumulativeLoan = hiddenTotalMoney;
					totalNum = hiddenTotalNum;
				} else {
					hiddenMoney = cumulativeLoan -= parseFloat($(this).attr("contractMoney"));
					hiddeNum = totalNum -= 1 ;
				}
			}
			$("#checkAll").prop("checked",($("input[name='checkBoxItem']").length == $("input[name='checkBoxItem']:checked").length ? true : false));
			
			$("#totalGrantMoney").text(fmoney(cumulativeLoan,2));
			$("#hiddenGrant").val(hiddenMoney);
			$("#totalNum").text(totalNum);
			$("#hiddenNum").val(hiddeNum);
		});
		
});
// 全选
function selectAll(){
	var curMoney = 0.0;
	var curNum = 0;
	if($('#checkAll').attr('checked')=='checked'|| $('#checkAll').attr('checked')){
		$(":input[name='checkBoxItem']").each(function() {
			$(this).attr("checked",'true');
			  curMoney = parseFloat($(this).attr("contractMoney"))+curMoney;
			  curNum=curNum+1;
			});
		$('#hiddenNum').val(curNum);
		$('#hiddenGrant').val(curMoney);
		$('#totalNum').text(curNum);
		$('#totalGrantMoney').text(fmoney(curMoney, 2));
	}else{
		$(":input[name='checkBoxItem']").each(function() {
			$(this).removeAttr("checked");
		});
		$('#hiddenGrant').val(0.00);
		$('#hiddenNum').val(0);
		$('#totalNum').text($("#hiddenTotalNum").val());
		$('#totalGrantMoney').text(fmoney($("#hiddenTotalMoney").val(), 2));
	}
}
//格式化，保留两个小数点
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
</script>
<meta name="decorator" content="default"/>
<title>放款确认</title>	
</head>
<body>


		
	<div class="control-group">
	<form:form id="grantForm" action="${ctx}/grantDoneJYJ/grantDone?listFlag=${listFlag}"  modelAttribute="loanGrantEx" method="post">
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
        <input type="hidden" id="listFlag" value="${listFlag }" />
            <tr>
                <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" /> 
			    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
			    <input type="hidden" name="menuId" value="${param.menuId }">
                <td><label class="lab">客户姓名：</label>
                <form:input type="text" class="input_text178" path="customerName"></form:input></td>
                <td><label class="lab">门店：</label>
                <form:input type="text" id="storesCode" class="input_text178" path="storesCode" readonly="true"></form:input>
                	  <i id="selectStoresBtn"
						class="icon-search" style="cursor: pointer;"></i>
				   <form:hidden path="storeOrgId" id="storeOrgId"/></td>
				   <td><label class="lab">提交日期：</label>
               <input  name="subStartDate"  id="subStartDay"  
                 value="<fmt:formatDate value='${loanGrantEx.subStartDate}' pattern='yyyy-MM-dd'/>"
                     type="text" class="Wdate input_text70" size="10"  
                                        onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'subEndDay\')}'})" style="cursor: pointer" ></input>-<input  name="subEndDate" 
                 value="<fmt:formatDate value='${loanGrantEx.subEndDate}' pattern='yyyy-MM-dd'/>"
                     type="text" class="Wdate input_text70" size="10" id="subEndDay"  
                                        onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'subStartDay\')}'})" style="cursor: pointer" ></input></td>
            </tr>
            <tr>
                <td><label class="lab">放款日期：</label>
               <input  name="startDate"  id="startDay"  
                 value="<fmt:formatDate value='${loanGrantEx.startDate}' pattern='yyyy-MM-dd'/>"
                     type="text" class="Wdate input_text70" size="10"  
                                        onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'endDay\')}'})" style="cursor: pointer" ></input>-<input  name="endDate" 
                 value="<fmt:formatDate value='${loanGrantEx.endDate}' pattern='yyyy-MM-dd'/>"
                     type="text" class="Wdate input_text70" size="10" id="endDay"  
                                        onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'startDay\')}'})" style="cursor: pointer" ></input></td>
				 <c:if test="${listFlag!='TG' }">
				   <td>
					   	 <label class="lab">渠道：</label>
					   	 <form:select class="select180" path="loanFlag"><option value="">请选择</option>
						   	 <c:forEach items="${fns:getDictList('jk_channel_flag')}" var="card_type">
								<c:if test="${card_type.label=='P2P'|| card_type.label=='财富'|| card_type.label=='XT01' || card_type.label==' '}">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
								</c:if>
				             </c:forEach>
					 	 </form:select>
				   </td>
			    </c:if>
                <td><label class="lab">放款银行：</label>
                <form:input type="text" class="input_text178" path="midBankName"></form:input></td>
                
            </tr>
            <tr id="T1" style="display:none">
				<td><label class="lab">放款操作人员：</label>
				<form:select class="select180" path="lendingUserId"><form:option value="">请选择</form:option>
				<c:forEach items="${user}" var="user">
								<form:option value="${user.userCode}">${user.name}</form:option>
				</c:forEach>
				</form:select></td>
			   <td><label class="lab">是否追加借：</label>
			   <form:select class="select180" path="dictIsAdditional"><form:option value="">请选择</form:option>
			   <c:forEach items="${fns:getDictList('jk_add_flag')}" var="card_type">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
				 </c:forEach></form:select></td>
                <td><label class="lab">放款批次：</label> <form:select class="select180" path="grantPch">
							<form:option value="">请选择</form:option>
							<c:forEach items="${grantPchList}" var="card_type">
								<form:option value="${card_type.grantBatch}">${card_type.grantBatch}</form:option>
							</c:forEach>
						</form:select></td>
            </tr>
			<tr id="T2" style="display:none">
			     <td><label class="lab">合同编号：</label>
			         <form:input  type="text" class="input_text178" path="contractCode"></form:input>
			     </td>
				 <td><label class="lab">是否加急：</label>
                  <form:select class="select180" path="urgentFlag">
				 <form:option value="">请选择</form:option>
				 <c:forEach items="${fns:getDictList('jk_urgent_flag')}" var="card_type">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
				 </c:forEach>
				 </form:select>
				 <td><label class="lab">提交批次：</label> <form:input type="text"
							class="input_text178" path="submissionBatch"></form:input></td> 
               </td>
            </tr>
            <tr id="T3" style="display:none">
            <td><label class="lab">提交日期：</label>
               <input  name="subStartDate"  id="subStartDay"  
                 value="<fmt:formatDate value='${loanGrantEx.subStartDate}' pattern='yyyy-MM-dd'/>"
                     type="text" class="Wdate input_text70" size="10"  
                                        onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'subEndDay\')}'})" style="cursor: pointer" ></input>-<input  name="subEndDate" 
                 value="<fmt:formatDate value='${loanGrantEx.subEndDate}' pattern='yyyy-MM-dd'/>"
                     type="text" class="Wdate input_text70" size="10" id="subEndDay"  
                                        onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'subStartDay\')}'})" style="cursor: pointer" ></input></td>
		<c:if test="${listFlag=='TG' }">
			<td>
				<label class="lab">委托提现：</label>
                <form:select class="select180" path="trustCash">
                	<option value="">请选择</option>
                	<c:forEach items="${fns:getDictList('jk_trust_status')}" var="card_type">
    					<option value="${card_type.value}" <c:if test="${loanGrantEx.trustCash == card_type.value}">selected=true</c:if>>${card_type.label}</option>
                </c:forEach>
          		</form:select>
        	</td>
        </c:if>
        </tr>
        </table>
        <div class="tright pr30 pb5">
        <input class="btn btn-primary" type="submit" value="搜索" />
         <button class="btn btn-primary" id="clearBtn">清除</button>
          <div style="float: left; margin-left: 45%; padding-top: 10px">
	      <img src="${context}/static/images/up.png" id="showMore"></img>
	     </div>
		</div>
		</form:form>
		</div>
    <p class="mb5">
    	<button class="btn btn-small" id="daoBtn">导出excel</button>
    	&nbsp;&nbsp;<label class="lab1">放款累计金额：</label><label class="red" id="totalGrantMoney"><fmt:formatNumber value='${totalGrantMoney}' pattern="#,##0.00"/></label>元     	&nbsp;&nbsp;<label class="lab1">放款总笔数：</label><label class="red" id="totalNum">${totalNum}</label>笔
    	<input type="hidden" id="hiddenGrant" value="0.00">
    	<input type="hidden" id="hiddenNum" value="0">
    	<input type="hidden" id="hiddenTotalMoney" value="${totalGrantMoney}">
    	<input type="hidden" id="hiddenTotalNum" value="${totalNum}">
    </p>
    <sys:columnCtrl pageToken="grantDonejyjList"></sys:columnCtrl>
    <div class="box5" style="overflow: auto; width: 100%;">
    <table  id="tableList"  class="table  table-bordered table-condensed table-hover ">
    <thead>
        <tr>
            <th><input type="checkbox" id="checkAll" onclick="selectAll();"/></th>
            <th>客户姓名</th>
            <th>共借人</th>
            <th>团队经理</th>
            <th>团队经理编号</th>
            <th>客户经理</th>
            <th>客户经理编号</th>
            <th>账户名字</th>
            <th>是否追加</th>
            <th>合同编号</th>
            <th>合同金额</th>
            <th>首次放款金额</th>
            <th>费用总金额</th>
            <th>前期综合服务费</th>
            <th>外访费</th>
            <th>加急费</th>
			<th>催收服务费</th>
			<th>尾款放款金额</th>
            <th>期数</th>
            <th>放款账户</th>
            <th>开户行</th>
            <th>账号</th>
			<th>放款途径</th>
            <th>门店</th>
            <th>放款时间</th>
            <th>放款批次</th>
            <th>提交批次</th>
            <th>提交时间</th>
            <th>审核操作人员</th>
            <th>放款操作人员</th>
            <th>渠道</th>
            <th>加急标识</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:if test="${ grantDoneList!=null && fn:length(grantDoneList.list)>0}">
         <c:set var="index" value="0"/>
          <c:forEach items="${grantDoneList.list}" var="item">
          <c:set var="index" value="${index+1}" />
             <tr>
             <td><input type="checkbox" name="checkBoxItem" fId="${item.contractCode}" contractMoney="${item.contractAmount }"/></td>
             <td>${item.customerName}</td>
             <td>${item.coboName}</td>
             <td>${item.loanTeamManagerName}</td>
             <td>${item.loanTeamManagercode}</td>
             <td>${item.loanManagerName}</td>
             <td>${item.loanManagercode}</td>
             <td>${item.bankAccountName}</td>
             <td>${item.dictIsAdditional}</td>
             <td>${item.contractCode}</td>
             <td><fmt:formatNumber value='${item.contractAmount}' pattern="#,#00.00" /></td>
             
            <td><fmt:formatNumber value='${item.firstGrantAmount}' pattern="#,#00.00" /></td> 
            <td><fmt:formatNumber value='${item.allFee}' pattern="#,#00.00" /></td> <!--  费用总金额  -->
            <td><fmt:formatNumber value='${item.feeCount}' pattern="#,#00.00" /></td> <!--  前期综合服务费  -->
            <td><fmt:formatNumber value='${item.feePetition}' pattern="#,#00.00" /></td> <!--  外访费  -->
            <td><fmt:formatNumber value='${item.feeExpedited}' pattern="#,#00.00" /></td> <!--  加急费  -->
             <td><fmt:formatNumber value='${item.urgeMoney}' pattern="#,#00.00" /></td>
          	 <td><fmt:formatNumber value='${item.lastGrantAmount}' pattern="#,##0.00" /> <!--  尾款放款金额  -->
             <td>${item.contractMonths}</td>
             <td>${item.middleName}</td>
             <td>${item.bankName}</td>
             <td>${item.bankAccountNumber}</td>
             <td>金信</td>
             <td>${item.storesCode}</td>
             <td><fmt:formatDate value="${item.lendingTime}"
							type="date" /></td>
			 <td>${item.grantPch}</td>     
			 <td>${item.submissionBatch}</td>
			 <td><fmt:formatDate value="${item.submissionsDate}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
             <td>${item.checkEmpId}</td>
             <td>${item.lendingUserId}</td>
             <td>${item.loanFlag}</td>
             <td>
              <c:if test="${item.urgentFlag=='是'}">
                    <span style="color:red">加急</span>
             </c:if> 
             </td>
             <td class="tcenter"><button class="btn_edit" onclick="showAllHisByLoanCode('${item.loanCode}')">历史</button></td>
             </tr> 
         </c:forEach>  
         </c:if>
         <c:if test="${ grantDoneList==null || fn:length(grantDoneList.list)==0}">
           <tr><td colspan="24" style="text-align:center;">没有待办数据</td></tr>
         </c:if>
       </tbody>   
    </table>
 
</div>
<div  id="trustRechargeDiv" style="display: none">
	<table class=" table4" cellpadding="0" cellspacing="0" border="0"  id="tpTable" width="380px">
		<tr style="display: none;"> 
			<td width="380px"><label class="lab"></label>
			<input type="radio" name="trustRd" value = "1" onclick="javascript:diahide();">导出&nbsp;&nbsp;&nbsp;
			<input type="radio" name="trustRd" value = "2" checked="checked" onclick="javascript:diashow();">上传&nbsp;&nbsp;&nbsp;</td>
		</tr>
		<tr> 
			<td width="380px"><label class="lab">文件格式：</label><select class="select78"><option>Excel</option>
			</td>
		</tr>
		<form id ="fileForm" enctype="multipart/form-data">
		<tr id="DT">
			<td width="380px"><label class="lab"></label><input type='file' name="file" id="fileid" ></td>
		</tr>
		</form>
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