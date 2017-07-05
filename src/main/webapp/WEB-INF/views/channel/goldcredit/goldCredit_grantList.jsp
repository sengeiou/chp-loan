<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html> 
<head>
<script src="${context}/js/common.js" type="text/javascript"></script>
<script type="text/javascript" src="${context}/js/payback/ajaxfileupload.js"></script>
<script type="text/javascript" src="${context}/js/channel/goldcredit/popuplayer.js"></script>
<meta name="decorator" content="default"/>
<title>待放款列表</title>
<style type="text/css">
  .trRed {
color:red;
}
</style>	
<script type="text/javascript">
$(function(){
	loan.initCity("addrProvice", "addrCity",null);
	loan.getstorelsit("storesName", "storeOrgId");
	$.popuplayer(".edit");
	var message = '${message}';
	if (message) {
		art.dialog.alert(message);
	}
	/* $(":input[name='checkBoxItem']").each(function(){
		var totalMoney = $("#hiddenGrant").val(),
		totalNum = parseInt($("#totalNum").text(),10);
		var totalGrantMoney = parseFloat($(this).attr("grantAmount"));
			totalMoney = totalGrantMoney+parseFloat(totalMoney);
			totalNum += 1;
		$("#totalGrantMoney").text(fmoney(totalMoney,2));
		$("#totalNum").text(totalNum);
		$("#hiddenGrant").val(totalMoney);
	}); */
	// 导出excel
	$("#exportExcel").click(function(){
		var queryParam = $("#grantForm").serialize();
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
		var url = ctx+"/channel/goldcredit/grant/exportExcel?loanCodes="+selected+"&"+queryParam;
		window.location.href=url;
	});	
	// 手动确认放款
	$("#handSureGrant").click(function(){
		var checkVal = null;
		if($(":input[name='checkBoxItem']:checked").length>0){
			$(":input[name='checkBoxItem']:checked").each(function(){
				if(checkVal!=null)
	        	{
	        		checkVal+=";"+$(this).attr("contractCode");
	        	}else{
	        		checkVal=$(this).attr("contractCode");
	        	}
			});
		}
		$("#contractCode").val(checkVal);
	});
	// 手动确认放款点击确定
	$("#sureGrant").click(function(){
		var grantPch = $("#handSureCon").val();
		var contractCode = $("#contractCode").val();
		var param = $("#grantForm").serialize();
		if(grantPch == null || grantPch == ""){
			art.dialog.alert("放款批次不能为空");
			return false;
		}
		var dialog = art.dialog({
			 content: '正在提交...',
		      cancel:false,
		      lock:true
		});
		$.ajax({
			type : "post",
			url : ctx + '/channel/goldcredit/grant/updateGrant',
			data : {'contractCodeItem':contractCode,'grantPch':grantPch,param},
			dataType : 'text',
			success : function (data){
				dialog.close();
				art.dialog.alert(data);
				window.location=ctx+'/channel/goldcredit/grant/getGCGrantInfo';
			},
			error : function(){
				dialog.close();
				art.dialog.alert("手动确认放款发生异常");
			}
			
		});
	});
	// 批量退回
	$("#GCbatchBackBtn").click(function(){
		var checkVal = null;
		if($(":input[name='checkBoxItem']:checked").length==0){
			art.dialog.alert('请选择要操作的数据!');
	    	return false;
		}else{
			$(":input[name='checkBoxItem']:checked").each(function(){	                		
				if($(this).attr("issplit")!='0'){
	       			 art.dialog.alert('选择的数据中含有拆分数据，不能退回，合同号：'+$(this).attr("contractCode"));
				     return false;
	       		}
				if(checkVal!=null)
            	{
            		checkVal+=";"+$(this).attr("backParam");
            	}else{
            		checkVal=$(this).attr("backParam");
            	}
            });
			$(this).attr("data-target","#backBatch_div");
			$("#GCbackBatchSure").data("checkList",checkVal);
		}
	});	
	$("#backBatchReason").change(function (){
		if ($(this).val() == 9)
			$("#other").show();
		else 
			$("#other").hide();
	});
	// 批量退回确认
	$("#GCbackBatchSure").click(function(){
		  var backBatchReason = $('#backBatchReason option:selected').text();
		  if(backBatchReason==''){
			  art.dialog.alert("请选择退回原因！");
			  return false;
		  }else{
			  if (backBatchReason == '其他'){
				  backBatchReason = $.trim($("textArea").val());
				  if (backBatchReason.length == 0){
					  art.dialog.alert("退回原因不能够为空!");
					  return false;
				  }
			  }
			  var dialog = art.dialog({
			      content: '正在提交...',
			      cancel:false,
			      lock:true
				});
			  
			  $.ajax({
					type : "post",
					url : ctx + '/channel/goldcredit/grant/batchBack',
					data : {'batchParam':$(this).data("checkList"),'backBatchReason':backBatchReason,'grantSureBackReasonCode':$('#backBatchReason').val()},
					dataType:'json',
					success : function(data) {
						dialog.close();
						var isSuccess = data.res;
						if(isSuccess == true || isSuccess == "true"){
							art.dialog.alert('退回成功!',function (){
								window.location=ctx+'/channel/goldcredit/grant/getGCGrantInfo';
							});
						}else{
							var failCancel =data.failCancelContract;
							var failReturn =data.failReturnContract;
							var msg = "";
							
							if(failCancel!="" && failCancel!=undefined && failCancel.length>0)
							{
								msg = "合同：" + failCancel + " 作废章加盖失败<br>";
							}
							if(failReturn!="" && failReturn!=undefined  && failReturn.length>0)
							{
								msg = msg + "合同：" + failReturn + " 退回异常";
							}
							art.dialog.alert(msg,function (){
								window.location=ctx+'/channel/goldcredit/grant/getGCGrantInfo';
							});
						}
					}
			});
		  }
	});
	$("#submitBtn").click(function (){
		$("#uploadAuditForm").submit();
	});
	//全选
	//点击全选反选选择框
	var totalMoney = 0.00,totalNum = 0,monty = 0.00,num = 0;
	$("#checkAll").click(function(){
		var totalGrantMoney=0.00;
		var checkedLength = 0;
		/* var grntMoney = $("#totalGrantMoney").text(),
		totlNum = $("#totalNum").text(); */
		$("input[name=checkBoxItem]").prop("checked",this.checked);
		if ($(this).is(":checked")) {
			$("input[name=checkBoxItem]").each(function() {
				totalGrantMoney += parseFloat($(this).attr("grantAmount"));
				checkedLength += 1;
				/* monty = totalGrantMoney;
				num = checkedLength;
				totalMoney = totalGrantMoney;
				totalNum = checkedLength; */
			});
			$("#totalGrantMoney").text(fmoney(totalGrantMoney,2));
			$("#totalNum").text(checkedLength);
			$("#hiddenGrant").val(totalGrantMoney);
		} else {
			totalGrantMoney = 0.00;
			checkedLength = 0;
			$("#totalGrantMoney").text(totalGrantMoney);
			$("#totalNum").text(checkedLength);
			$("#hiddenGrant").val(totalGrantMoney);
		}
	});
	//计算金额
	$("input[name=checkBoxItem]").click(function(){
		var totalGrantMoney = parseFloat($(this).attr("grantAmount"));
		if($(this).is(":checked")){
			totalMoney += totalGrantMoney;
			totalNum += 1;
			monty = totalMoney;
			num = totalNum;
			$("#totalGrantMoney").text(fmoney(totalMoney,2));
			$("#totalNum").text(totalNum);
			$("#hiddenGrant").val(totalMoney);
	 	}else{
			if ($(":input[name='checkBoxItem']:checked").length == 0) {
				totalMoney = 0.00;
				totalNum = 0 ;
				
				$("#totalGrantMoney").text(fmoney(totalMoney,2));
				$("#totalNum").text(totalNum);
				$("#hiddenGrant").val(totalMoney);
				monty = totalMoney = 0.00;
				num = totalNum = 0 ;
			}else {
				totalMoney = totalMoney - totalGrantMoney;
				totalNum --;
				$("#totalGrantMoney").text(fmoney(totalMoney,2));
				$("#totalNum").text(totalNum);
				$("#hiddenGrant").val(totalMoney);
			}
	 	}
		$("#checkAll").prop("checked",
				($(":input[name='checkBoxItem']").length == $(":input[name='checkBoxItem']:checked").length) ? true : false);
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
	$("#clearBtn").click(function () {
		$(":input","#grantForm").not(":button,:submit,:reset").val("").removeAttr('checked')
		  .removeAttr('selected');
	});
});
//单条退回
function showSingleBack(target,checkindex){
	$(target).attr("data-target","#backBatch_div");
	$("#GCbackBatchSure").data("checkList",$("#"+checkindex).attr("backParam"));
}						

function showGrantHis(applyId){
	var url=ctx + "/channel/goldcredit/grant/showHistory?applyId="+applyId;
    art.dialog.open(url, {  
	   id: 'his',
	   title: '待放款列表历史',
	   lock:true,
	   width:700,
	   height:350
	},false);  
}
//金额精确到小数点后2位
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
	$("#grantForm").attr("action","${ctx }/channel/goldcredit/grant/getGCGrantInfo");
	$("#grantForm").submit();
	return false;
}
</script>
</head>
<body>
	<div class="control-group">
        <form:form id="grantForm" action="${ctx }/channel/goldcredit/grant/getGCGrantInfo" modelAttribute="LoanFlowQueryParam" method="post">
         	<input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo}" />
	   		<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}" />
          <table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
               <td><label class="lab">客户姓名：</label><form:input type="text" class="input_text178" path="customerName"></form:input></td>
                <td><label class="lab">合同编号：</label><form:input type="text" class="input_text178" path="contractCode"></form:input></td>
                <td><label class="lab">提交日期：</label><input id="grantDate" name="submissionDate" 
                 value="${LoanFlowQueryParam.submissionDate}"
                     type="text" class="Wdate input_text178" size="10"  
                                        onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" ></input>
                </td>
            </tr>
            <tr>
             <td><label class="lab">回执结果：</label><form:select class="select180" path="loanStatusCode">
                <form:option value="">全部</form:option>
                <form:option value="67">待放款</form:option>
                <form:option value="71">放款失败</form:option>
                </form:select></td>
				 <td ><label class="lab">管辖城市：</label><form:select class="select180" style="width:110px" path="provinceCode" id="addrProvice">
                <form:option value="">选择省份</form:option>
                <c:forEach var="item" items="${provinceList}" varStatus="status">
		             <form:option value="${item.areaCode }">${item.areaName}</form:option>
	            </c:forEach>
                </form:select>-<form:select class="select180" style="width:110px" path="cityCode" id="addrCity">
                <form:option value="">请选择</form:option>
                 <c:forEach var="item" items="${cityList}" varStatus="status">
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
				</td>
            </tr>
            <tr id="T1" style="display:none">
                <td><label class="lab">是否加急：</label><form:select class="select180" path="urgentFlag">
				 <form:option value="">请选择</form:option>
				 <c:forEach items="${fns:getDictList('jk_urgent_flag')}" var="card_type">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
				 </c:forEach>
				 </form:select></td>
				 <td><label class="lab">提交批次：</label><form:select class="select180" path="submissionBatch">
				 <form:option value="">请选择</form:option>
				 <c:forEach items="${submitBatchList}" var="submitBatch">
								<form:option value="${submitBatch}">${submitBatch}</form:option>
				 </c:forEach>
				 </form:select></td>
            </tr>
        </table>
       <div class="tright pr30 pb5"><input type="button" class="btn btn-primary" onclick="document.forms[0].submit();" value="搜索"> 
       <button class="btn btn-primary" id="clearBtn">清除</button>
        <div style="float: left; margin-left: 45%; padding-top: 10px">
	      <img src="${ctxStatic }/images/up.png" id="showMore"></img>
	    </div>
	    </div>
	    </div>
	</form:form> 
	 </div>
    <p class="mb5">
		<button id="exportExcel" class="btn btn-small" >导出excel</button>
		<button id="uploadResult" role="button" class="btn btn-small" data-target="#uploadAuditedModal" data-toggle="modal">上传回执结果</button>
		<button id="handSureGrant" class="btn btn-small" data-target="#hand_sure_grant" data-toggle="modal">手动确认放款</button>
    	<button id="GCbatchBackBtn" role="button" class="btn btn-small" data-toggle="modal">批量退回</button>
    &nbsp;&nbsp;<label class="lab1">放款累计金额：</label><label class="red" id="totalGrantMoney">0.00</label>元     	
    	&nbsp;&nbsp;<label class="lab1">放款总笔数：</label><label class="red" id="totalNum">0</label>笔</p>
    	<input type="hidden" id="hiddenGrant" value="0.00">
    </p>
    <div class="box5">
    <table class="table  table-bordered table-condensed table-hover ">
    <thead>
        <tr>
            <th><input type="checkbox" id="checkAll"/></th>
            <th>合同编号</th>
            <th>门店名称</th>
            <th>客户姓名</th>
            <th>借款类型</th>
            <th>借款产品</th>
            <th>合同金额</th>
            <th>放款金额</th>
            <th>批借期限(月)</th>
            <th>开户行</th>
            <th>状态</th> 
            <th>渠道</th>
            <th>模式</th>
            <th>提交批次</th>
            <th>提交日期</th>
            <th>操作</th>
        </tr>
      </thead>
      <tbody>
       <c:if test="${ workItems!=null && fn:length(workItems)>0}">
          <c:set var="index" value="0"/>
          <c:forEach items="${workItems}" var="item" varStatus="status">
          <c:set var="index" value="${index+1}" />
             <tr <c:if test="${item.frozenFlag=='1'}">
                    class='trRed'
               </c:if>>
             <td><input id="checkIndex_${index}" type="checkbox" name="checkBoxItem"
             contractCode="${item.contractCode }" grantAmount='${item.lendingMoney}' issplit="${item.issplit}" 
              value='${item.loanCode}' backParam="${item.loanCode},${item.wobNum},${item.contractCode},${item.token},${item.applyId}" index='${index}'/>
              ${status.count} </td>
             <td>${item.contractCode}</td>
             <td>${item.storeName}</td>
             <td>${item.customerName}</td>
             <td>信借</td>
             <td>${item.replyProductName}</td>
             <td><fmt:formatNumber value='${item.contractMoney}' pattern="#,#00.00"/></td>
             <td><fmt:formatNumber value='${item.lendingMoney}' pattern="#,#00.00"/></td>
             <td>${item.replyMonth}</td>
             <td>${item.cautionerDepositBank}</td>
             <td>${item.loanStatusName}</td>
             <td>${item.channelName}</td>
             <td></td>
             <td>${item.submissionBatch }</td> 
             <td>${fn:substring(item.submissionDate,0,10)}</td>    
             <td style="position:relative">
				<input type="button" class="btn btn_edit edit" value="操作"/>
				 <div class="alertset" style="display:none">
				 	<button class="btn_edit" onclick="showHisByLoanCode('${item.loanCode}')">历史</button>
				 	<c:if test="${item.issplit=='0'}">
						<button class="btn_edit" data-toggle="modal" onclick="showSingleBack(this,'checkIndex_${index}')">退回</button>
				 	</c:if>
				 </div>
			 </td>        
            </tr> 
         
         </c:forEach>  
            
         </c:if>
         
         <c:if test="${ workItems==null || fn:length(workItems)==0}">
           <tr><td colspan="17" style="text-align:center;">没有待办数据</td></tr>
         </c:if>
        </tbody>      
    </table>
    <c:if test="${workItems!=null || fn:length(workItems)>0}">
		<div class="page">${gcGrantListPage}</div>
	</c:if>
<form id="param">
	<input type="hidden" name="applyId" id="applyId"></input>
	<input type="hidden" name="wobNum" id="wobNum"></input>
	<input type="hidden" name="flowName" id="flowName"></input>
	<input type="hidden" name="token" id="token"></input>
	<input type="hidden" name="stepName" id="stepName"></input>
</form>	
<input type="hidden" name="contractCode" id="contractCode"></input>									
</div>

<div  id="back_reason" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabe1l" aria-hidden="true" data-url="data">
	<label class="lab">退回原因：</label><label id="reason">${backReason }</label>
	<div style="text-align:right">
		<button id="backSure" class="btn" data-dismiss="modal" aria-hidden="true" >确定</button>
	</div>
</div>
<!-- 手动确认放款Start -->
<div  id="hand_sure_grant" class="modal fade" tabindex="-1" role="dialog" 
  aria-labelledby="myModalLabe1l" aria-hidden="true">
  <div class="modal-dialog" role="document">
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			<h4 class="modal-title">放款批次</h4>
		</div>
		<div class="modal-body">
		<label class="lab">放款批次：</label>
		<input type="text" class="input_text178" id="handSureCon"></input>
		</div>
		<div class="modal-footer" style="text-align:right">
			<button id="sureGrant" class="btn btn-primary" data-dismiss="modal" aria-hidden="true">确定</button>
			<button class="btn btn-primary" data-dismiss="modal" aria-hidden="true">取消</button>
		</div>
	</div>
	</div>
</div>
<!-- 手动确认放款End -->
<!-- 上传回执结果 -->
<div class="modal fade" id="uploadAuditedModal" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"> 
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<form id="uploadAuditForm" role="form" enctype= "multipart/form-data" method="post" action="${ctx}/channel/goldcredit/grant/importResult">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title" id="myModalLabel">上传回执结果</h4>
				</div>
				<div class="modal-body">
                	<input type='file' name="file" id="fileid">
                </div>
			</form>
			<div class="modal-footer">
       			<input type = "button" class="btn btn-primary" id = 'submitBtn' value = "确认"/>
      			<button class="btn btn-primary"  class="close" data-dismiss="modal" >取消</button>
   			</div>
		</div>
	</div>	
</div>
<!-- 退回原因 -->
<div id="backBatch_div" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabe1l" aria-hidden="true" data-url="data">
	<div class="modal-dialog role="document"">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">退回原因</h4>
			</div>
			<div class="modal-body">
				<input type = "hidden" id = "hiddenValue"/>
				<table class="table table-striped table-bordered table-condensed">
					<tr>
                    	<td align="left"><label>退回原因</label></td>
                    	<td align="left">
							<select id="backBatchReason" class="select180">
								<c:forEach items="${fns:getDictList('jk_chk_back_reason')}" var="card_type">
									<option value="${card_type.value}">${card_type.label}</option>
								</c:forEach>
							</select>
						</td>
					 </tr>
                <tr id="other" style="display:none">
                    <td><label class="lab">其他退回原因：</label></td>
                    <td><span class="red">*</span><textarea id = "textArea" class="muted"   rows="" cols="" style='font-family:"Microsoft YaHei";' ></textarea></td>
                </tr>
            </table>
			</div>
			<div class="modal-footer" style="text-align:right">
				<button id="GCbackBatchSure" class="btn btn-primary" data-dismiss="modal" aria-hidden="true" >确定</button>
				<button class="btn btn-primary" data-dismiss="modal" aria-hidden="true">取消</button>
			</div>
		</div>
	</div>
</div>
</body>
</html>