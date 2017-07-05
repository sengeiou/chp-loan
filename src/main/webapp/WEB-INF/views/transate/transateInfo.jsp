<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>信借已办列表</title>
<meta name="decorator" content="default"/>
<style type="text/css">
.input-append {
    height: 30px;
    float:left;
    margin-left:3px;
    line-height:30px;   
    }
    .input-small{
    	float:left;
    	width:138px;
    	border-radius:3px;
    	margin:1px 0;
    }   
</style>
<script type="text/javascript" language="javascript"
	src='${context}/js/common.js'></script>
<script src="${context}/js/transate/transate.js"></script>
<script type="text/javascript" src="${context}/js/transate/effectiveDateReckon.js"></script>
<script language="javascript"> 
	function page(n, s) {
		if (n) $("#pageNo").val(n);
		if (s) $("#pageSize").val(s);
		$("#traForm").attr("action", "${ctx}/borrow/transate/transateInfo");
		$("#traForm").submit();
		return false;
	}	
	 function show() {
		if (document.getElementById("T1").style.display == 'none') {
			document.getElementById("showMore").src = '${context}/static/images/down.png';
			document.getElementById("T1").style.display = '';
			document.getElementById("T2").style.display = '';
			document.getElementById("T3").style.display = '';
			document.getElementById("T4").style.display = '';
		} else {
			document.getElementById("showMore").src = '${context}/static/images/up.png';
			document.getElementById("T1").style.display = 'none';
			document.getElementById("T2").style.display = 'none';
			document.getElementById("T3").style.display = 'none';
			document.getElementById("T4").style.display = 'none';
		} 
	}
$(document).ready(function(){
	var msg = "${message}";
	if(msg!='' && msg!=null){
		top.$.jBox.tip(msg);
	}
	loan.getstorelsit("orgName", "orgCode", null);
	$(":input[name='applyFrozen']").each(function(){
	  $(this).bind('click',function(){
		$('#frozenConfirmBtn').removeAttr('disabled');
		$('#loanCode').val($(this).attr("loanCode"));
		$('#applyId').val($(this).attr("applyId"));
		$('#dictLoanStatus').val($(this).attr('dictLoanStatus'));
		$('#frozenApplyTimes').val($(this).attr('frozenApplyTimes'));
		$('#frozenLastApplyTime').val($(this).attr('frozenLastApplyTime'));
		$(this).attr("data-target","#applyFreezenDialog");
	  });
   });
/* $('#frozenConfirmBtn').bind('click',function(){ //ReasonRquired
	
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
		 	 art.dialog.alert("此次申请冻结时间距离上次申请冻结的时间不超过1个小时，不允许再次申请");  
			 return false;
		 }else{
			 art.dialog.confirm('每个客户只能申请两次冻结，请务必核实好再提交',function(){
			        frozenApplyTimes = parseInt(frozenApplyTimes)+1;
	    	    	$('#frozenApplyTimes').val(frozenApplyTimes);
	    	    	window.location = ctx + "/borrow/borrowlist/asynApplyFrozen?"+$('#applyFreezenForm').serialize();
					$.ajax({
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
					}); 
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
	  $.ajax({
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
		}); 
  }
	 
  }); */
/* $('#frozenCode').bind('change',function(){
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
}); */
		// action="${ctx}/borrow/grant/grantSure/importResult"
});
</script>
</head>
<body>	
	<div class="control-group">
		<form id="traForm" method="post">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />					
        	<table cellpadding="0" cellspacing="0" border="0" width="100%">       	
            	<tr>
                	<td>
                		<label class="lab">客户姓名：</label><input type="text" name="loanCustomerName"class="input_text178" value="${t.loanCustomerName }" maxlength="20"/>
                	</td>
					<td>
						<label class="lab">身份证号：</label>
						<input type="text"  name="customerCertNum" class="input_text178" value="${t.customerCertNum }" maxlength="20"/>
					</td>
					<td>
						<label class="lab">标识：</label>
						<select name="markings" class="select180">
							<option value="">请选择</option>
							<c:forEach var="mark" items="${fns:getDictList('jk_channel_flag')}">
								<option value="${mark.value }" <c:if test="${t.markings==mark.value }">selected</c:if> >${mark.label }</option>
							</c:forEach>
						</select>
					</td>
            	</tr>
            	<tr>
            <!-- 	如果门店人员登录本页面则不能够选择门店选择框体 -->
            		<c:if test="${isManager == false}">	          
	                	<td>
	                		<label class="lab" style="float:left;height:30px;line-height:30px">门店：</label><input type="text" id="orgName" name="orgName" class="input_text178" path="name" value="${t.orgName }" readonly></input>
					    	<input type="hidden" path="storeId" id="orgCode" name="orgCode" value="${t.orgCode }"/>
	                		<%-- <select name="orgCode" class="select180">
								<option value="" selected>请选择</option>
								<c:forEach var="org" items="${orgs }">
									<option value="${org.orgCode }" <c:if test="${t.orgCode==org.orgCode }">selected</c:if> >${org.orgName }</option>							
								</c:forEach>
							</select> --%>
	                	</td>
                	 </c:if>
                	 
                	<c:if test="${isManager == true}">          
	                	<td>
	                		<label class="lab" style="float:left;height:30px;line-height:30px">门店：</label><input type="text" id="orgName" name="orgName" class="input_text178" path="name" value="${t.orgName }" readonly></input>
	                	  		<i id="selectStoresBtn"
									class="icon-search" style="cursor: pointer;"></i>
					    	<input type="hidden" path="storeId" id="orgCode" name="orgCode" value="${t.orgCode }"/>
	                		<%-- <select name="orgCode" class="select180">
								<option value="" selected>请选择</option>
								<c:forEach var="org" items="${orgs }">
									<option value="${org.orgCode }" <c:if test="${t.orgCode==org.orgCode }">selected</c:if> >${org.orgName }</option>							
								</c:forEach>
							</select> --%>
	                	</td>
                	 </c:if>
                	<td>
                		<label class="lab">申请产品：</label>
						<select name="applicationProduct" class="select180">
							<option value="">请选择</option>
							<c:forEach var="pro" items="${products }">
								<option value="${pro }" <c:if test="${t.applicationProduct==pro }">selected</c:if> >${pro }</option>
							</c:forEach>
						</select>
                	</td>
                	<td>
                		<label class="lab">批借产品：</label>
						<select name="products" class="select180">
							<option value="">请选择</option>
							<c:forEach var="pro" items="${products }">
								<option value="${pro }" <c:if test="${t.products==pro }">selected</c:if> >${pro }</option>
							</c:forEach>
						</select>
                	</td>
            	</tr>
            	<tr id="T1" style="display:none">             
                	<td>
                		<label class="lab">申请状态：</label>
                		<select name="dictLoanStatus" class="select180">
							<option value="">请选择</option>
							<c:forEach var="status" items="${fns:getDictList('jk_loan_apply_status')}">
								<option value="${status.value }" <c:if test="${t.dictLoanStatus==status.value }">selected</c:if> >${status.label }</option>
							</c:forEach>
						</select>
                	</td>					
                	<td>
                		<label class="lab">客户经理：</label><input type="text" name="userName" class="input_text178" value="${t.userName }">
                	</td>
					 <td>
						<label class="lab">借款状态：</label>
						<select name="dictPayStatus" class="select180">
							<option value="">请选择</option>
							<c:forEach var="sour" items="${fns:getDictList('jk_loan_status')}">
								<option value="${sour.value }" <c:if test="${t.dictPayStatus==sour.value }">selected</c:if> >${sour.label }</option>
							</c:forEach>
						</select>
					</td>   
                </tr>
                <tr id="T2" style="display:none">
					<td>
                		<label class="lab">是否加急：</label>
                		<select id="isUrgent" name="isUrgent" class="select180">
                			<option value="">请选择</option>
            				<c:forEach items="${fns:getDictList('jk_urgent_flag')}" var="item">
								<option value="${item.value}" <c:if test="${item.value eq t.isUrgent}">selected</c:if>>${item.label}</option>
				 			</c:forEach>
                		</select>
						<%-- <input type="radio" name="isUrgent" value="1" 
                			<c:if test="${t.isUrgent==1 }">checked</c:if>
                		>是
                		<input type="radio"  name="isUrgent" value="0" 
                			<c:if test="${t.isUrgent==0 }">checked</c:if>
                		>否 --%>
					</td>            	
                	<td >
                		<label class="lab">是否追加借：</label><select id="dictIsAdditional" name="dictIsAdditional" class="select180">
                			<option value="">请选择</option>
            				<c:forEach items="${fns:getDictList('yes_no')}" var="item">
								<option value="${item.value}" <c:if test="${item.value eq t.dictIsAdditional}">selected</c:if>>${item.label}</option>
				 			</c:forEach>
                		</select>
						<%-- <input type="radio" name="dictIsAdditional" value="1" 
                			<c:if test="${t.dictIsAdditional==1 }">checked</c:if>
                		>是
                		<input type="radio"  name="dictIsAdditional" value="0"
                			<c:if test="${t.dictIsAdditional==0 }">checked</c:if>
                		>否 --%>
                	</td>
                	<td>
                		<label class="lab">是否电销：</label>
                		<select id="loanIsPhone" name="loanIsPhone" class="select180">
                			<option value="">请选择</option>
            				<c:forEach items="${fns:getDictList('jk_telemarketing')}" var="item">
								<option value="${item.value}" <c:if test="${item.value eq t.loanIsPhone}">selected</c:if>>${item.label}</option>
				 			</c:forEach>
                		</select>
						<%-- <input type="radio" name="loanIsPhone" value="1"  
                			<c:if test="${t.loanIsPhone==1 }">checked</c:if>
                		>是
                		<input type="radio"  name="loanIsPhone" value="0"
                			<c:if test="${t.loanIsPhone==0 }">checked</c:if>
                		>否 --%>
                	</td>
            	</tr>
            	
                 <tr id="T3" style="display:none">
                    <td>
                		<label class="lab">客服：</label><input type="text" name="servicename" class="input_text178" value="${t.servicename }">
                	</td>
                	
                	<td>
                		<label class="lab">外访：</label><input type="text" name="empname" class="input_text178" value="${t.empname }">
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
            	<tr id="T4" style="display:none">
                	<td><label class="lab">回访状态：</label> 
							<select class="select180" name="revisitStatus" value="${t.revisitStatus}">
								<option value="">请选择</option>
								<option value="-1" ${t.revisitStatus=='-1'?'selected':''}>失败</option>
								<option value="0"  ${t.revisitStatus=='0'?'selected':''}>待回访</option>
								<option value="1"  ${t.revisitStatus=='1'?'selected':''}>成功</option>
								<option value="null"  ${t.revisitStatus=='null'?'selected':''}>空</option>
							</select>
					</td>
            	</tr>
        	</table>        
        	<div class="tright pr30 pb5">
        		<input type="submit" class="btn btn-primary" value="搜索" >
        		<input type="button" class="btn btn-primary" value="清除" id="tRemoveBtn" />
				<div class="xiala">
					<center>
						<img src="${context}/static/images/up.png" id="showMore"  onclick="javascript:show();"></img>
					</center>
        	    </div>      
        	</div>
		</form>
    </div>
    <sys:columnCtrl pageToken="transate"></sys:columnCtrl>
    <div class="box5" >
        <table id="tableList" class="table table-hover table-bordered table-condensed" style="margin-bottom:100px">
            <thead>
                <tr>
                    <th>序号</th>
					<th>合同版本</th>
					<th>合同编号</th>
                    <th>客户姓名</th>
                    <th>共借人</th>
                    <th>自然人保证人</th>
                     <c:if test="${ isManager!=false}">	 
                    <th>省份</th>
                    <th>城市</th>
                    <th>门店</th>
                    </c:if>
                    <th>申请产品</th>
                    <th>批借产品</th>
                    <th>申请状态</th>
                    <th>批复金额</th>
                    <th>批复分期</th>
                    <th>团队经理</th>              
                    <th>销售人员</th>
                    <th>进件时间</th>
                    <th>上调标识</th>
                    <th>是否电销</th>
                    <th>加急标识</th>
                    <th>渠道</th>
                    
                    <th>客服</th>
                    <th>外访</th>
                    <th>回访状态</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
				<c:forEach var="li" items="${traPage.list }" varStatus="st">
					<tr <c:if test="${li.backFlag=='1'}">
					       style='color:red'
					    </c:if>
					  >
						<td>
							<c:out value="${st.count }" />
						</td>
						<td>
						    <c:forEach var="dict" items="${dictlist }">
								            <c:if test="${li.contractVersion==dict.value}">
								               ${dict.label }
								            </c:if>
								         </c:forEach>
						</td>
				    <!--<c:if test="${li.contractVersion=='4'}">
					    <td>1.4</td>
					 </c:if>
					 <c:if test="${li.contractVersion!='4' && li.contractVersion!=null}">
						<td>1.${li.contractVersion}</td>
					 </c:if>
					 <c:if test="${li.contractVersion==null or li.contractVersion==''}">
						<td>${li.contractVersion}</td>
					 </c:if>-->
						<td>${li.contractCode }</td>
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
					    <c:if test="${ isManager!=false}">	 
						<td>${li.provinceId }</td>
						<td>${li.cityId }</td>
						<td>${li.storeId }</td>
						</c:if>
						<td>${li.applicationProductName }</td>
						<td>${li.productName }</td>
						<td>
    						<c:choose>  
    							<c:when test="${li.dictLoanStatus=='7' || li.dictLoanStatus=='8'}">  
        							拒借  
        						</c:when>
        						<c:when test="${fn:length(li.frozenCode)>0}">
        							${li.dictLoanStatusLabel}(门店申请冻结)
        						</c:when>  
    							<c:otherwise>
        						${li.dictLoanStatusLabel}
    							</c:otherwise>
							</c:choose>
						</td>
						<td>
							<fmt:formatNumber value="${li.money == null ? 0 : li.money }" pattern='#,##0.00#'/>
						</td>
						<td>${li.contractMonths == null ? 0 : li.contractMonths}</td>
						<td>${li.teamManagerName }</td>
						<td>${li.userName }</td>
						<td>
							<fmt:formatDate value="${li.customerIntoTime }" pattern="yyyy-MM-dd"/>
						</td>
						<td>${li.loanIsRaiseLable}</td>
						<td>${li.loanIsPhoneLabel}</td>
						<td>                    	                  
							<c:if test="${li.loanIsUrgent == '0'}"></c:if>	                	
							<c:if test="${li.loanIsUrgent == '1'}">
								<span style="color:red">加急</span>
							</c:if>
						</td>
						<td>${li.loanMarkingLable == 'CHP' ? '' : li.loanMarkingLable}</td>
						
						<td>${li.servicename }</td>
						<td>${li.empname }</td>
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
						<td class="tcenter">
							<input type="hidden" id="loanCodeHid${st.count}" value="${li.loanCode }" />
							<input type="hidden" id="applyIdHid${st.count}" value="${li.applyId }" />
							<input type="hidden" id="status${st.count}" value="${li.dictPayStatus }" />
							<input type="hidden" id="coboNames${st.count}" value="${li.coroName }" />
							<input type="hidden" id="query${st.count}" value="${query }" />
							<input type="hidden" id="loanInfoOldOrNewFlag${st.count}" value="${li.loanInfoOldOrNewFlag }" />
							<input type="button" class="btn_edit" id="${st.count }" value="查看" onClick="checkBtn(this)" />
							
							<%-- <input type="button" class="btn_edit" value="查看" 
								<c:if test="${li.contractCode == null || li.contractCode == ''}">
									onclick="window.location='${ctx}/borrow/transate/transateDetails?loanCode=${li.loanCode }&applyId=${li.applyId }&status=${li.dictPayStatus }&coboNames=${li.coroName }&query=${query }'"
								</c:if>
								<c:if test="${li.contractCode != null && li.contractCode != ''}">
									onclick="window.location='${ctx}/borrow/transate/loanMinute?loanCode=${li.loanCode }&status=${li.dictPayStatus}&query=${query }'"
								</c:if>
								 > --%>
							<%-- <c:if test="${(li.frozenApplyTimes==null || li.frozenApplyTimes<2)
						   		 &&(li.dictLoanStatus=='64' || li.dictLoanStatus=='75'
						      		 || li.dictLoanStatus=='72' || li.dictLoanStatus=='65' ||
						         	 li.dictLoanStatus=='66' || li.dictLoanStatus=='95' 
						   		 )}">	
						  		<!-- 如果登录是客服角色， 则申请冻结按钮不可见 -->		
					    	<c:if test="${ isCanSe!=true}">	 
								<input type="button" name="applyFrozen" 
								 value="申请冻结" role="button" class="btn_edit" data-toggle="modal"
							 	 dictLoanStatus="${li.dictLoanStatus}" 
							 	 applyId="${li.applyId}" frozenApplyTimes="${li.frozenApplyTimes}" 
							 	 frozenLastApplyTime="<fmt:formatDate value='${li.frozenLastApplyTime}' pattern="yyyy-MM-dd HH:mm:ss"/>" loanCode="${li.loanCode}"/>
							 </c:if>
							 	 
							 	 
						   </c:if> --%>
						</td>
					</tr>
				</c:forEach>
				<c:if test="${traPage.list==null || fn:length(traPage.list)==0}">
					<tr>
						<td colspan="19" style="text-align: center;">没有符合条件的数据</td>
					</tr>
				</c:if>
            </tbody>
        </table>
		
	</div>
	<div class="pagination">${traPage }</div>
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
			 				 <input type="hidden" id="frozenApplyTimes" name="frozenApplyTimes"/>
			 				 <input type="hidden" id="frozenLastApplyTime"/>
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