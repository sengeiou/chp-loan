<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<title>信借数据列表</title>
<head>
<meta name="decorator" content="default" />
<script type="text/javascript" language="javascript"
	src='${context}/js/common.js'></script>
<script type="text/javascript" src="${context}/js/transate/effectiveDateReckon.js"></script>
<script src="${context}/js/transate/transate.js?ver=1"></script>
<script language="javascript">
	var emailTimer = new Object();
	var emailFlag = "";
	function page(n, s) {
		if (n) $("#pageNo").val(n);
		if (s) $("#pageSize").val(s);
		$("#loanForm").attr("action", "${ctx}/borrow/transate/loanInfo");
		$("#loanForm").submit();
		return false;
	}	
	 function show() {
		if (document.getElementById("T1").style.display == 'none') {
			document.getElementById("showMore").src = '${context}/static/images/down.png';
			document.getElementById("T1").style.display = '';
			document.getElementById("T2").style.display = '';
			document.getElementById("T3").style.display = '';
			document.getElementById("T4").style.display = '';
			document.getElementById("T5").style.display = '';
		} else {
			document.getElementById("showMore").src = '${context}/static/images/up.png';
			document.getElementById("T1").style.display = 'none';
			document.getElementById("T2").style.display = 'none';
			document.getElementById("T3").style.display = 'none';
			document.getElementById("T4").style.display = 'none';
			document.getElementById("T5").style.display = 'none';
		}
		$('#tableList').bootstrapTable('resetView');
	} 
$(document).ready(function(){
	var msg = "${message}";
	if(msg!='' && msg!=null){
		top.$.jBox.tip(msg);
	}
	$(":input[name='applyFrozen']").each(function(){
		  $(this).bind('click',function(){
			$('#frozenConfirmBtn').removeAttr('disabled');
			$('#loanCode').val($(this).attr("loanCode"));
			$('#applyId').val($(this).attr("applyId"));
			$('#loanFlagLabel').val($(this).attr("loanFlagLabel"));
			$('#dictLoanStatus').val($(this).attr('dictLoanStatus'));
			$('#frozenApplyTimes').val($(this).attr('frozenApplyTimes'));
			$('#frozenLastApplyTime').val($(this).attr('frozenLastApplyTime'));
			$('#contractCode').val($(this).attr('contractCode'));
			$('#issplit').val($(this).attr('issplit'));
			$('#zcjRejectFlag').val($(this).attr('zcjRejectFlag'));
			$("#djmenuId").val('${param.menuId }');
			$(this).attr("data-target","#applyFreezenDialog");
		  });
	   });
	$(":input[name='backDetailBtn']").each(function(){
		$(this).click(function(){
			var url = ctx + "/apply/storeReviewController/getBackDetail?loanCode="+$(this).attr("loanCode");
			art.dialog.open(url, {
				title: "汇诚回退原因",	
				top: 80,
				lock: true,
		    	width: 1000,
		   	 	height: 450,
			},false);
	 	});
	});
	$('#frozenConfirmBtn').bind('click',function(){ //ReasonRquired
		
		 var frozenCode = $('#frozenCode option:selected').val();
		 var frozenText = $('#frozenCode option:selected').text();
		 if(frozenText!='' && frozenText!=null){
			 frozenText = frozenText.trim();
		 }
		 if(frozenCode=='' ||frozenCode==null){
			 $('#frozenCodeMsg').html("请选择冻结原因");
			 return false;
		 }else{
			 $('#frozenName').val(frozenText);
			 $('#frozenCodeMsg').html("");
		 }
		 if(frozenText=='其它'|| frozenText=='其他'){
		   var frozenReason = $('#frozenReason').val();
		   if(frozenReason==null || frozenReason=='' ||frozenReason.trim().length==0){
			  $('#frozenReasonMsg').html("请输入冻结原因");
		      return false;
		   }else if(frozenReason.trim().length<10){
			  $('#frozenReasonMsg').html("填写原因不得少于10个字");
			  return false;
		   }else{
			  $('#frozenReasonMsg').html("");
		   }
		 }else{
			$('#frozenReasonMsg').html("");
		 }
			   var frozenApplyTimes = $('#frozenApplyTimes').val();
			   var frozenLastApplyTime = $('#frozenLastApplyTime').val();
			   var submitFlag = true;
			   if(frozenApplyTimes=="1"){
				 var interval = effectiveDate.interval(frozenLastApplyTime);
				 if(interval<1){
				 	 art.dialog.alert("此次申请冻结时间距离上次驳回冻结的时间不超过1个小时，不允许再次申请");  
					 return false;
				 }else{
					 art.dialog.confirm('每个客户只能申请两次冻结，请务必核实好再提交',function(){
					        frozenApplyTimes = parseInt(frozenApplyTimes)+1;
			    	    	$('#frozenApplyTimes').val(frozenApplyTimes);
			    	    	window.location = ctx + "/borrow/borrowlist/asynApplyFrozen?"+$('#applyFreezenForm').serialize();
						/* 	$.ajax({
								type : "POST",
								url : ctx + "/borrow/borrowlist/asynApplyFrozen",
								data :$('#applyFreezenForm').serialize(),
								dataType:'json',
								success : function(data) {
									$('#frozenConfirmBtn').attr('disabled',true);	
									art.dialog.confirm(data.msg,function(){
									   $("#traForm").submit();
									});
			   						 
								},
								error:function(){
									art.dialog.alert('服务器异常');
								},
								async:false
							}); */
				 },function(){
					 submitFlag = false;
					 return true;
			}); 
		   }
		  }else{
			  frozenApplyTimes = 1;
			  $('#frozenApplyTimes').val(frozenApplyTimes);
			  $('#frozenConfirmBtn').attr('disabled',true);
			  window.location = ctx + "/borrow/borrowlist/asynApplyFrozen?"+$('#applyFreezenForm').serialize();
			/*   $.ajax({
					type : "POST",
					url : ctx + "/borrow/borrowlist/asynApplyFrozen",
					data :$('#applyFreezenForm').serialize(),
					dataType:'json',
					success : function(data) {
						$('#frozenConfirmBtn').attr('disabled',true);
						art.dialog.confirm(data.msg,function(){
							   $("#traForm").submit();
							});
						
					},
					error:function(){
						art.dialog.alert('服务器异常');
					},
					async:false
				}); */
		  }
			 
		  });
	loan.getstorelsit("orgName", "orgCode", null);
	$('#frozenCode').bind('change',function(){
		  var text = $("#frozenCode option:selected").text().trim();
		  if(text=='其它' || text=='其他'){
			  $('#ReasonRquired').html("*");
			  $('#frozenReason').removeAttr("disabled");
		   }else{
			  $('#ReasonRquired').html("");
			  $('#frozenReason').attr("disabled",true);
		   }
		  $('#frozenCodeMsg').html("");
		  $('#frozenReasonMsg').html("");
	});	
// action="${ctx}/borrow/grant/grantSure/importResult"

	$('#loanInfoExcelExport').bind('click',function() {
		var idVal = "";
		var delayEntity = $("#loanForm").serialize();
		window.location.href = ctx
		+ "/borrow/transate/loanInfoExcelExport?delayEntity="+delayEntity;
	
	});
});

function editCustomerEmail(loanCode){
	var url = "${ctx}/borrow/transate/customerEmailInfo?loanCode=" + loanCode;
	$.ajax({
		type : "POST",
		url : url,
		success : function(data){
			$("#customerId").val(data.id);
  			$("#emailCustomerName").val(data.customerName);
  			$("#emailContractCode").val(data.contractCode);
  			$("#emailCustomerCard").val(data.customerCertNum);
  			$("#emailLoanCode").val(data.loanCode);
  			$("#menuId").val('${param.menuId }');
  			art.dialog({
  			    content: document.getElementById("editEmail"),
  			    title:'修改邮箱',
  			    fixed: true,
  			    lock:true,
  			    width:450,
  			    height:200,
  			    id: 'confirm',
  			    okVal: '确认',
  			    ok: function () {
  			    	if(emailFlag == "through"){
  			    		var srcFormParam = $('#editEmailForm');
  	  					srcFormParam.submit();
  	  					return false;
  			    	}else{
  			    		alert("邮箱未验证成功，请验证后确认提交！");
  			    		return false;
  			    	}
  			    	
  			    },
  			    cancel: function(){
  			    	clearInterval(emailTimer); 
  			    	emailFlag = "";
  		            $("#newEmail").removeAttr("disabled");
  		            $("#emailBtn").removeAttr("disabled");
  		            $("#emailBtn").val("邮箱验证");
  		            $("#confirmEmailResult").text("")
  			    	 return true;
  			    }
  			});
		}
	});
}

function sendEmail(obj){
	 if(!(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/.test($("#newEmail").val()))){
		 art.dialog.alert("请正确填写邮箱地址");
		 return;
	 }
	 $("#newEmail").attr("disabled", "true");
	 $("#emailBtn").attr("disabled", "true");
	 $("#emailTemp").val($("#newEmail").val());
     $.ajax({
		   type: "POST",
		   url : ctx + "/borrow/transate/sendEmail",
	 	   data: {
	 		  	  contractCode: $("#emailContractCode").val(),
	 		   	  customerName:$("#emailCustomerName").val(),
	 		   	  email:$("#newEmail").val(),
	 		   	  id:$("#customerId").val()
	 		   	 },	
		   success: function(data){
			   if(data!=""){
					countDown(300,obj);
				    $("#confirmEmailResult").text("验证中");
					$("#confirmEmailResult").css("color","#FF9933")

			   }else{
				   $("#confirmEmailResult").text("验证失败");
				   $("#confirmEmailResult").css("color","red")
				   $("#newEmail").removeAttr("disabled");
				   $("#emailBtn").removeAttr("disabled");
			   }
		  }
	}); 
}

//倒计时
function countDown(maxtime,obj)  
{   
   var countNum = 0;
   emailTimer = setInterval(function()  
   {  
       if(maxtime>0){     
             --maxtime;
             ++countNum;
             $(obj).val("邮箱验证(" + maxtime+")" );
             if(countNum == 40){
            	 countNum = 0;
            	confirmEmail(obj);
             }
        }     
       else
       {     
            clearInterval(emailTimer); 
            emailFlag = "";
            $("#newEmail").removeAttr("disabled");
            $("#emailBtn").removeAttr("disabled");
            $(obj).val("邮箱验证");
            $("#confirmEmailResult").text("验证失败")
            $("#confirmEmailResult").css("color","red")
        }     
    }, 1000);
}

function confirmEmail(obj){
	 $.ajax({
		   type: "POST",
		   url : ctx + "/borrow/transate/confirmEmail",
	 	   data: {
	 		  	id:$("#customerId").val()
	 		   	 },	
		   success: function(data){
			   if(data!=""){
				   $("#confirmEmailResult").text("验证成功")
				    $("#confirmEmailResult").css("color","#00FF00")
				   clearInterval(emailTimer); 
				   emailFlag = "through";
				   $(obj).val("邮箱验证");
				   $("#newEmail").attr("disabled", "true");
			   }else{
				  // art.dialog.alert("验证邮件发送失败！");
			   }
		  }
	});
}

function xyck(contractCode){
	 var url='${ctx}/apply/contractAudit/xieYiList?contractCode='+contractCode+'&type=1';
	  art.dialog.open(url, {  
		   id: 'protcl',
		   title: '协议查看',
		   lock:false,
		   width:1000,
		   height:450
		},false); 
}
</script>
</head>
<body>

		<div class="control-group">
			<form id="loanForm" method="post">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" /> 
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
				<input id="isManager" name="isManager" type="hidden" value="${isManager}" />
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td><label class="lab">客户姓名：</label> <input type="text" class="input_text178"
							name="loanCustomerName" value="${params.loanCustomerName }"></td>
							
					    <!-- 	如果门店人员登录本页面则不能够选择门店选择框体 -->		
						<c:if test="${isManager == false}">	        
						 <td>
	                		<label class="lab" style="float:left;height:30px;line-height:30px">门店：</label><input type="text" id="orgName" name="orgName" class="input_text178" path="name" value="${params.orgName }" readonly></input>
					    	<input type="hidden" path="storeId" id="orgCode" name="orgCode" value="${params.orgCode }"/>
	                	</td>
						 	 </c:if>
						
						<c:if test="${isManager == true}">     
						 <td>
	                		<label class="lab" style="float:left;height:30px;line-height:30px">门店：</label><input type="text" id="orgName" name="orgName" class="input_text178" path="name" value="${params.orgName }" readonly></input>
	                	  		<i id="selectStoresBtn"
									class="icon-search" style="cursor: pointer;"></i>
					    	<input type="hidden" path="storeId" id="orgCode" name="orgCode" value="${params.orgCode }"/>
	                	</td>
						 </c:if>
						
						<td><label  class="lab">申请产品：</label> <select name="products" class="select180">
								<option value="">全部</option>
								<c:forEach var="pro" items="${products }">
									<option value="${pro }"
										<c:if test="${params.products==pro }">selected</c:if>>${pro }</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr>
						<td><label class="lab">团队经理：</label> <input type="text" class="input_text178"
							name="teamManageName" value="${params.teamManageName }">
						</td>
						<td><label class="lab">客户经理：</label> <input type="text" class="input_text178"
							name="loanManageName" value="${params.loanManageName }">
						<td><label class="lab">是否加急：</label> <input type="radio"
							name="loanIsUrgent" value="1"
							<c:if test="${params.loanIsUrgent==1 }">checked</c:if>>是
							<input type="radio" name="loanIsUrgent" value="0"
							<c:if test="${params.loanIsUrgent==0 }">checked</c:if>>否
						</td>
					</tr>
					<tr id="T1" style="display: none">
						<td >
							<label class="lab">渠道：</label> 
							<select name="loanMarking"   class="select180"> 
                                    <option value="">请选择</option>
                                    <c:forEach items="${fns:getDictList('jk_channel_flag')}" var="str">
                                    	<option value="${str.value }" 
                                    	 <c:if test="${params.loanMarking==str.value }">selected</c:if>>${str.label }</option>
                                    </c:forEach>									
							</select>
						</td>
						<td><label class="lab">模式：</label> 
							<select name="model" class="select180">
								<option value=''>请选择</option>
								<c:forEach items="${fns:getDictList('jk_loan_model')}" var="loanmodel">
	                                   <option value="${loanmodel.value }" <c:if test="${payback.model == loanmodel.value }">selected</c:if>>
	                                   <c:if test="${loanmodel.value=='0'}">
	                                   	非TG
	                                   </c:if>
	                                   <c:if test="${loanmodel.value!='0'}">${loanmodel.label}</c:if>
	                                   </option>
	                            </c:forEach>
	                       </select></td>
						<td ><label class="lab">身份证号：</label> <input type="text" name="certNum" class="input_text178"
							value="${params.certNum }"></td>
					</tr>
					<tr id="T2" style="display: none">
						<td>
							<label class="lab">借款状态：</label>
							<select name="dictLoanStatus" class="select180">
								<option value="">请选择</option>
								<c:forEach var="sour" items="${queryList}">
									<option value="${sour.code }" <c:if test="${params.dictLoanStatus==sour.code }">selected</c:if> >${sour.name }</option>
								</c:forEach>
							</select>
						</td>
						<td><label class="lab">进件时间：</label> <input type="text" class="input_text178 Wdate"
						name="customerIntoTime"		value="<fmt:formatDate value="${params.customerIntoTime}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					   </td>
						<td ><label class="lab">是否电销：</label> <input type="radio"
							name="loanIsPhone" value="1"
							<c:if test="${params.loanIsPhone==1 }">checked</c:if>>是
							<input type="radio" name="loanIsPhone" value="0"
							<c:if test="${params.loanIsPhone==0 }">checked</c:if>>否
						</td>
					</tr>
					 <tr id="T3" style="display: none">
						<td><label class="lab">外访人员：</label> <input type="text" class="input_text178"
							name="loanSurveyEmpName" value="${params.loanSurveyEmpName }">
					   </td>
					    <td><label class="lab">客服：</label> <input type="text" class="input_text178"
							name="loanCustomerServiceName" value="${params.loanCustomerServiceName }">
					   </td>
					   <td><label  class="lab">是否冻结：</label> <select name="frozenCode" value="${params.frozenCode}" class="select180">
							<option value="2" ${params.frozenCode==2?'selected':''}
								>全部</option>
							<option value="0" ${params.frozenCode==0?'selected':''}
								>未冻结</option>
							<option value="1" ${params.frozenCode==1?'selected':''}
								>已冻结</option>
						</select></td>
					</tr>
					 <tr id="T4" style="display: none">
						<td><label  class="lab">批借产品：</label> <select name="auditProducts" class="select180">
								<option value="">全部</option>
								<c:forEach var="pro" items="${products }">
									<option value="${pro }"
										<c:if test="${params.auditProducts==pro }">selected</c:if>>${pro }</option>
								</c:forEach>
						</select></td>
						<td><label class="lab">回访状态：</label> 
							<select class="select180" name="revisitStatus" value="${params.revisitStatus}">
								<option value="">请选择</option>
								<option value="-1" ${params.revisitStatus=='-1'?'selected':''}>失败</option>
								<option value="0"  ${params.revisitStatus=='0'?'selected':''}>待回访</option>
								<option value="1"  ${params.revisitStatus=='1'?'selected':''}>成功</option>
								<option value="null"  ${params.revisitStatus=='null'?'selected':''}>空</option>
							</select>
						</td>
						<td><label class="lab">合同寄送状态：</label> 
							<select class="select180" name="sendStatus" value="${params.revisitStatus}">
								<option value="">请选择</option>
								<option value="-1" ${params.sendStatus=='-1'?'selected':''}>未申请</option>
								<option value="1"  ${params.sendStatus=='1'?'selected':''}>已申请</option>
								<option value="2"  ${params.sendStatus=='2'?'selected':''}>待邮寄</option>
								<option value="3"  ${params.sendStatus=='3'?'selected':''}>已邮寄</option>
								<option value="8"  ${params.sendStatus=='8'?'selected':''}>申请退回</option>
								<option value="0"  ${params.sendStatus=='0'?'selected':''}>电子协议未申请</option>
								<option value="4"  ${params.sendStatus=='4'?'selected':''}>电子协议待审核</option>
								<option value="5"  ${params.sendStatus=='5'?'selected':''}>电子协议发送失败</option>
								<option value="6"  ${params.sendStatus=='6'?'selected':''}>电子协议已发送</option>
								<option value="7"  ${params.sendStatus=='7'?'selected':''}>电子协议申请退回</option>
							</select>
						</td>
					</tr>
					<tr id="T5" style="display: none">
					     <td ><label class="lab">是否无纸化：</label> <input type="radio"
							name="paperless" value="1"
							<c:if test="${params.paperless==1 }">checked</c:if>>是
							<input type="radio" name="paperless" value="0"
							<c:if test="${params.paperless==0 }">checked</c:if>>否
						</td>
						<td><label class="lab">有无邮箱：</label> 
							<input type="radio" name="emailFlag" value="1"
							<c:if test="${params.emailFlag==1 }">checked</c:if>>有
							<input type="radio" name="emailFlag" value="0"
							<c:if test="${params.emailFlag==0 }">checked</c:if>>无
						</td>    
 					</tr>
				</table>			
				<div class="tright pr30 pb5">
					<input type="submit" value="搜索" class="btn btn-primary" /> 
					<input type="button" value="清除" class="btn btn-primary" id="lRemoveBtn" />
					<div style="float:left;margin-left:45%;padding-top:10px">
				 		<img src="${context }/static/images/up.png" id = 'showMore' onclick="show();"></img>
					</div>
				</div>
			</form>
		</div>
				
	       <p>
	       	<c:if test="${jkhj_loaninfo_export==null }">
		       <button class="btn btn-small jkhj_loaninfo_export" id="loanInfoExcelExport">导出Excel</button>
		    </c:if>   
		       <span><label>当前版本号：</label> ${curVersion}</span>
	       </p>
        <div>
		<table id="tableList">
			<thead>
				<tr>
					<th>序号</th>
					<th>合同编号</th>
					<th>版本号</th>
					<th>客户姓名</th>
					<th>共借人</th>
					<th>自然人保证人</th>
					<th>省份</th>
					<th>城市</th>
					<th>门店</th>
					<th>申请产品</th>
					<th>批借产品</th>
					<th>状态</th>
					<th>合同寄送状态</th>
					<th>批复金额</th>
					<th>批复分期</th>
					<th>加急标识</th>
					<th>是否电销</th>
					<th>团队经理</th>
					<th>销售人员</th>
					<th>申请时间</th>
					<th>进件时间</th>
					<th>外访人员</th> 
					<th>客服</th>
					<th>上调标识</th>
					<th>渠道</th>
					<th>是否无纸化</th>
					<th>回访状态</th>
					<th>有无邮箱</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="li" items="${loanPage.list }" varStatus="st">
					<tr>
						<td><c:out value="${st.count }" /></td>
						<td>${li.contractCode }</td>
						<td>${li.contractVersion }</td>
						<td>${li.loanCustomerName }</td>
						<td>						
							<c:if test="${li.loanInfoOldOrNewFlag eq '0' || li.loanInfoOldOrNewFlag eq ''}">	
								${li.coroName }
							</c:if>
						</td>
						<td>
							<c:if test="${li.loanInfoOldOrNewFlag eq '1'}">	
								${li.bestCoborrower }
							</c:if> 
						</td>
						<td>${li.provinceName }</td>
						<td>${li.cityName }</td>
						<td>${li.loanStoreOrgName }</td>
						<td>${li.productName }</td>	
						<td>${li.auditProductName }</td>
						<td>
						${li.dictLoanStatusLabel}
						<c:if test="${fn:length(li.frozenCode)>0}">
						(门店申请冻结)
						</c:if>
						</td>
						<td>
							<c:if test="${li.paperless=='0'}">
								<c:if test="${li.sendStatus==null }">未申请</c:if>
								<c:if test="${li.sendStatus=='1' }">已申请</c:if>
								<c:if test="${li.sendStatus=='2' }">待邮寄</c:if>
								<c:if test="${li.sendStatus=='3' }">已邮寄</c:if>
								<c:if test="${li.sendStatus=='6' }">已邮寄退回</c:if>
								<c:if test="${li.sendStatus=='7' }">待邮寄退回</c:if>
								<c:if test="${li.sendStatus=='8' }">申请退回</c:if>
							</c:if>
							<c:if test="${li.paperless=='1'||li.paperless==null}">
								<c:if test="${li.sendEmailStatus==null }">电子协议未申请</c:if>
								<c:if test="${li.sendEmailStatus=='4' }">电子协议待审核</c:if>
								<c:if test="${li.sendEmailStatus=='5' }">电子协议发送失败</c:if>
								<c:if test="${li.sendEmailStatus=='6' }">电子协议已发送</c:if>
								<c:if test="${li.sendEmailStatus=='7' }">电子协议申请退回</c:if>
							</c:if>
						</td>
						<td><fmt:formatNumber value="${li.money == null ? 0 : li.money}" pattern="#,##0.00#"></fmt:formatNumber>
						</td>
						<td>${li.contractMonths }</td>
						<td>							
							<c:if test="${li.loanIsUrgent == '0'}"></c:if>	                	
                    		<c:if test="${li.loanIsUrgent == '1'}">
                    			<span style="color:red">加急</span>
                    		</c:if>
						</td>
						<td>${li.loanIsPhoneLabel}</td>
						<td>${li.teamManagerName }</td>
						<td>${li.userName }</td>
						<td><fmt:formatDate value="${li.loanApplyTime }"
								pattern="yyyy-MM-dd" /></td>
						<td><fmt:formatDate value="${li.customerIntoTime }"
								pattern="yyyy-MM-dd" /></td>
						<td>${li.loanSurveyEmpName }</td>
						<td>${li.loanCustomerServiceName }</td>
						<td>${li.loanIsRaiseLable}</td>
						<td>${li.loanMarkingLable == 'CHP' ? '' : li.loanMarkingLable}</td>
						<td>${li.paperless=='1'||li.paperless==null ? "是":"否"}</td>
						<td>
							<c:choose>
				             	  <c:when test="${li.revisitStatus == '' || li.revisitStatus == null}">
				                  </c:when>
				                  <c:when test="${li.revisitStatus == -1 }">
				                                                     失败
				                  </c:when>
				                 <c:when test="${li.revisitStatus == 0 }">
				                                                     待回访	
				                  </c:when>
				                  <c:when test="${li.revisitStatus == 1 }">
				                                                      成功
				                  </c:when>
			                </c:choose>
						</td>
						<td>${li.customerEmail != null ? "有":"无"}</td>
						<td class="tcenter">
							<input type="hidden" id="loanCodeHid${st.count}" value="${li.loanCode }" />
							<input type="hidden" id="applyIdHid${st.count}" value="${li.applyId }" />
							<input type="hidden" id="status${st.count}" value="${li.dictPayStatus }" />
							<input type="hidden" id="coboNames${st.count}" value="${li.coroName }" />
							<input type="hidden" id="query${st.count}" value="${query }" />
							<input type="hidden" id="loanInfoOldOrNewFlag${st.count}" value="${li.loanInfoOldOrNewFlag}" />
							<c:if test="${isCanSe!=true}">
								<input type="button" class="btn_edit" id="${st.count }" frozenCode="${li.frozenCode }" value="查看" onClick="checkBtn(this)" />
								<c:if test="${li.dictLoanStatus=='21' || li.dictLoanStatus=='41' }">
									<button class="btn btn_edit" loanCode="${li.loanCode}" name="backDetailBtn">汇诚退回明细</button>
								</c:if>
							</c:if>
							<c:if test="${(li.frozenApplyTimes==null || li.frozenApplyTimes<2)
						   		 &&(li.dictLoanStatus=='64' || li.dictLoanStatus=='75'
						      		 || li.dictLoanStatus=='72' || li.dictLoanStatus=='65' ||
						         	 li.dictLoanStatus=='66' || li.dictLoanStatus=='95' || 
						         	 li.dictLoanStatus=='107' || li.dictLoanStatus=='104'
						   		 )&& (fn:length(li.frozenCode)==0) && seeApplyFrozen}">
								<input type="button" name="applyFrozen" value="申请冻结" role="button" class="btn_edit" data-toggle="modal" dictLoanStatus="${li.dictLoanStatus}" contractCode="${li.contractCode}" issplit="${li.issplit}" zcjRejectFlag="${li.zcjRejectFlag}" applyId="${li.applyId}" frozenApplyTimes="${li.frozenApplyTimes}" loanFlagLabel="${li.loanMarkingLable}" frozenLastApplyTime="<fmt:formatDate value='${li.frozenLastApplyTime}' pattern="yyyy-MM-dd HH:mm:ss" />" loanCode="${li.loanCode}" />
							</c:if>
							<c:if test="${role=='1' && (li.dictLoanStatus=='87' || li.dictLoanStatus=='88' || li.dictLoanStatus=='89' || li.dictLoanStatus=='90' || li.dictLoanStatus=='91' || li.dictLoanStatus=='92')}">					
								<button class="btn_edit" id="${li.contractCode}" onclick="xyck(this.id)">还款计划表</button>	
						   </c:if>	
							<button class="btn_edit" id="${li.loanCode }"  onclick="showHisByLoanCode(this.id)">历史</button>
					  	<c:if test="${li.customerEmail == null || li.emailIfConfirm != '1' }">
						  	<c:if test="${jkhj_loaninfo_emailAdd==null }">
						  		<button class="btn_edit jkhj_loaninfo_emailAdd" id="${li.loanCode }"  onclick="editCustomerEmail(this.id)">补录邮箱</button>
						  	</c:if>
					  	</c:if>
													
							<%-- <input type="button" value="查看" class="btn_edit"
							<c:if test="${li.contractCode == null || li.contractCode == ''}">
								onclick="window.location='${ctx}/borrow/transate/transateDetails?loanCode=${li.loanCode }&applyId=${li.applyId }&status=${li.dictPayStatus }&coboNames=${li.coroName }&query=${query }'"
							</c:if>
						    <c:if test="${li.contractCode != null && li.contractCode != ''}">
								onclick="window.location='${ctx}/borrow/transate/loanMinute?loanCode=${li.loanCode }&status=${li.dictPayStatus}&query=${query }'"
                            </c:if>
							/> --%>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		</div>
	<div class="pagination">${loanPage}</div>
<div class="modal fade" id="applyFreezenDialog" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" >
		<div class="modal-dialog role="document"">
				<div class="modal-content">
		<form id="applyFreezenForm" role="form" enctype= "multipart/form-data" method="post">
					<div class="modal-header">
					   <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title" id="myModalLabel">门店申请冻结确认</h4>
					 </div>
					 <div class="modal-body">
					  <table class=" table1" cellpadding="0" cellspacing="0" border="0"
		                width="100%">
					     <tr><td><label class="lab"><span style="color: red" id="frozenCodeFlag">*</span>冻结原因：</label>
					     	 <select class="select78 mr34" name="frozenCode" id="frozenCode">
				 				 <option value="">请选择</option>
				 				 <c:forEach var="item" items="${fns:getDictList('jk_frozen_reason')}" varStatus="status">
		             				<option value="${item.value}"
		             			      <%--  <c:if test="${applyInfoEx.loanInfo.storeProviceCode==item.areaCode}">
		              					   selected=true 
		             			       </c:if> --%>
		            				  >${item.label}
		            				</option>
	              				 </c:forEach>
			 				 </select>
			 				 <span id="frozenCodeMsg" style="color:red"></span>
			 				 <input type="hidden" id="loanCode" name="loanCode"/>
			 				 <input type="hidden" id="frozenName" name="frozenName"/>
			 				 <input type="hidden" id="dictLoanStatus" name="dictLoanStatus"/>
			 				 <input type="hidden" id="applyId" name="applyId"/>
			 				 <input type="hidden" id="loanFlagLabel" name="loanFlagLabel"/>
			 				 <input type="hidden" id="frozenApplyTimes" name="frozenApplyTimes"/>
			 				 <input type="hidden" id="frozenLastApplyTime"/>
			 				 <input type="hidden" id="contractCode" name="contractCode"/>
			 				 <input type="hidden" id="issplit" name="issplit"/>
			 				 <input type="hidden" id="zcjRejectFlag" name="zcjRejectFlag"/>
			 				 <input type="hidden" name="menuId" id="djmenuId">
			 	          </td>
					     </tr>
					     <tr><td><label class="lab"><span id="ReasonRquired" style="color: red"></span>原因描述：</label>
					           <textarea rows="" cols="" id="frozenReason" name="frozenReason" maxlength="460" class="textarea_refuse"></textarea>
					         </td>
					     </tr>
					     <tr><td>
					          <span id="frozenReasonMsg" style="color:red"></span>
					         </td>
					     </tr>
					  </table>
					 </div>
                     <div class="modal-footer">
   					     <input class="btn btn-primary" id="frozenConfirmBtn" type="button" value="确认">
      					 <button class="btn btn-primary"  class="close" data-dismiss="modal" >取消</button>
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
			height: $(window).height()-230,
			striped: true,
			pageSize: 20,
			pageNumber:1
		});
	</script>
		<div id="editEmail" class=" pb5 pt10 pr30 title ohide" style="display:none">
			<form id="editEmailForm" class="validate" action="${ctx}/borrow/transate/customerEmailEdit" method="post" enctype="multipart/form-data">
				<input name="loanCode" type="hidden" id="emailLoanCode"/>
				<input name="id" type="hidden" id="customerId"/>
				<input type="hidden" name="menuId" id="menuId">
				<table class="table table-bordered table-condensed table-hover ">
					<tbody>
						<tr>
							<td><label class="lab">客户姓名：</label></td>
							<td><input type="text" class="input-medium" id="emailCustomerName" name="customerName" readonly="readonly"/></td>
							<td><label class="lab">合同编号：</label></td>
							<td><input type="text" class="input-medium" id="emailContractCode" name="contractCode" readonly="readonly"/></td>
						</tr>
						<tr>
							<td><label class="lab">客户身份证号：</label></td>
							<td><input type="text" class="input-medium" id="emailCustomerCard" name="customerCard" readonly="readonly"/></td>
						</tr>
						<tr>
							<td><label class="lab">邮箱地址：</label></td>
							<td><input type="text" class="input-medium" name="customerEmail" id="newEmail" /></td>
							<input name="email" type="hidden" id="emailTemp"/>
							<td><input class="btn btn-small" type="button" id="emailBtn" value="邮箱验证" name="emailBtn" onclick="sendEmail(this)"/>
							</td>
							<td><label class="lab" style="text-align:left;" id="confirmEmailResult"></label></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
</body>
</html>
