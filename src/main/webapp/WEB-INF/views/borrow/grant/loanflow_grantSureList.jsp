<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript" src="${context}/js/payback/ajaxfileupload.js"></script>
<script src="${context}/js/grant/grantSure.js?version=14" type="text/javascript"></script>
<script type="text/javascript" language="javascript"
	src='${context}/js/grant/amountCaculate.js'></script>
<script src="${context}/js/grant/grantSureDeal.js" type="text/javascript"></script>
<script src="${context}/js/common.js" type="text/javascript"></script>
<script src="${context}/js/contract/rateList.js" type="text/javascript"></script>
<script type="text/javascript" src="${context}/js/channel/goldcredit/popuplayer.js"></script>
<script src="${context}/js/grant/revisitStatusDeal.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctxStatic}/jquery-validation/1.11.0/lib/jquery.form.js" ></script>
<meta name="decorator" content="default"/>
<title>放款确认</title>
<style type="text/css">
  .trRed {
color:red;
}
</style>
<script type="text/javascript">
//点击全选
function checkAll(){
	var totalNum=0;
	var totalContractMoney=0.00;
	var totalGrantMoney=0.00;
	if($('#checkAll').is(':checked')){
		$(":input[name='checkBoxItem']").each(function() {
			$(this).attr("checked",'true');
			totalNum++;
			totalContractMoney+=parseFloat($(this).attr("contractMoney"));
			totalGrantMoney+=parseFloat($(this).attr("grantAmount"));
			});
	}else{
		$(":input[name='checkBoxItem']").each(function() {
			$(this).removeAttr("checked");
		});
	}
	$('#totalNum').text(totalNum);
	$('#hiddenContract').val(totalContractMoney);
	$('#hiddenGrant').val(totalGrantMoney);
	$("#totalContractMoney").text(fmoney(totalContractMoney,2));
	$("#totalGrantMoney").text(fmoney(totalGrantMoney,2));
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
$(document).ready(function(){
	$(":input[name='backApply']").each(function(){
		var listFlag = $("#listFlag").val();
		var loanCode = $(this).attr('loanCode');
		var applyId = $(this).attr('applyId');
		var contractCode = $(this).attr('contractCode');
		var menuId = '${param.menuId}';
		$(this).bind('click',function(){
			art.dialog({
				content:document.getElementById("rejectFrozen"),
				title:'驳回申请',
				lock:true,
				ok:function(){
					var rejectReason=$('#rejectReason').val();
					if(rejectReason ==''|| rejectReason == null){
						art.dialog.alert("请输入驳回理由!");
						return false;
					}
					$.ajax({
						type : 'post',
						url : ctx+'/borrow/grant/grantSureDeal/backApply',
						data :{
							'loanCode':loanCode,
		    				'applyId':applyId,
		    				'rejectReason':rejectReason,
		    				'contractCode':contractCode
		    				},
						cache : false,
						dataType : 'json',
						async : false,
						success : function(result) {
							if(result){
								art.dialog.alert("驳回成功");
								window.location.href=ctx+'/borrow/grant/grantSure/getGrantSureInfo?listFlag='+listFlag+'&menuId='+menuId;
							}else{
								art.dialog.alert(result);
							}
						},
						error : function() {
							art.dialog.alert('请求异常');
						}
					});
				},
				cancel:true
				
			});
		});
		
	});
  loan.getstorelsit("storeName","storeOrgId");
  revisitStatusObj.getRevisitStatusList("revisitStatusId","revisitStatus","revisitStatusName","");
  $.popuplayer(".edit");
  $('#findBtn').click(function(){
	  $('#grantSureForm').submit();
  });
  
  var msg = "${message}";
	if (msg != "" && msg != null) {
		art.dialog.alert(msg);
	}
	rateObj.getRateQueryList('monthRateBtn','monthRate');
});
	function page(n, s) {
		var listFlag = $("#listFlag").val();
		if (n)
			$("#pageNo").val(n);
		if (s)
			$("#pageSize").val(s);
		$("#grantSureForm").attr("action", "${ctx}/borrow/grant/grantSure/getGrantSureInfo?listFlag="+listFlag);
		$("#grantSureForm").submit();
		return false;
	}

</script>

</head>
<body>

   <div class="control-group">
      <div id="rejectFrozen" style="display:none">
			<form method="post" action="">
				<table class="table1" cellpadding="0" cellspacing="0" border="0"
					width="100%">
					<tr valign="top">
						<td><label ><span style="color:red;">*</span>驳回理由：</label>
							<textarea id="rejectReason" maxlength="450" cols="55" class="textarea_refuse" ></textarea>
						</td>
					</tr>
				</table>		
			</form>
		</div>
	  <form:form id="grantSureForm" action="${ctx }/borrow/grant/grantSure/getGrantSureInfo?listFlag=${listFlag }&menuId=${param.menuId}" modelAttribute="LoanFlowQueryParam" method="post">
         <input type="hidden" name="menuId" id="menuId" value="${param.menuId }">
          <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td><label class="lab">客户名称：</label>
                <input type="hidden" id="pageNo" name="pageNo" value="${workItems.pageNo}" />
	   			<input type="hidden" id="pageSize" name="pageSize" value="${workItems.pageSize}" />
                <input type="hidden" id="listFlag" value='${listFlag }'/>
                <form:input type="text" class="input_text178" path="customerName"></form:input></td>
                <td><label class="lab">合同编号：</label>
                <form:input type="text" class="input_text178" path="contractCode"></form:input></td>
                <td><label class="lab">门店：</label>
                    <form:input type="text" id="storeName" class="input_text178" path="storeName" readonly="true"></form:input>
                	  <i id="selectStoresBtn"
						class="icon-search" style="cursor: pointer;"></i>
				   <form:hidden path="storeOrgIds" id="storeOrgId"/>
                </td>
            </tr>
            <tr>
				 <td><label class="lab">是否加急：</label>
				 <form:select class="select180" path="urgentFlag">
				 <form:option value="">请选择</form:option>
				 <c:forEach items="${fns:getDictList('jk_urgent_flag')}" var="card_type">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
				 </c:forEach>
				 </form:select></td>	
				 
					 <td>
					 
					 <c:if test="${listFlag!='TG' }">
					 	<label class="lab">渠道：</label>
					 	<form:select class="select180" path="channelCode">
							<form:option value="">全部</form:option>
						 	<c:forEach items="${fns:getDictList('jk_channel_flag')}" var="card_type">
								<c:if test="${card_type.label=='P2P'|| card_type.label=='财富'}">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
								</c:if>
				             </c:forEach>
						</form:select>
						</c:if>
						<c:if test="${listFlag =='TG' }">
					 	<label class="lab">渠道：</label>
					 	<form:select class="select180" path="channelCode">
							<form:option value="">全部</form:option>
						 	<c:forEach items="${fns:getDictList('jk_channel_flag')}" var="card_type">
								<c:if test="${card_type.label=='P2P'|| card_type.label=='财富' }">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
								</c:if>
				             </c:forEach>
						</form:select>
						</c:if>
						
					</td>
				
                <td><label class="lab">产品：</label>
                <form:select class="select180" path="replyProductCode">
					<form:option value="">请选择</form:option>
					 <c:forEach items="${productList}" var="card_type">
								<form:option value="${card_type.productCode}">${card_type.productName}</form:option>
				    </c:forEach>					
					</form:select>
				</td>
            </tr>
            <tr id="T1" style="display:none;">
                 <td><label class="lab">证件号码：</label>
                 <form:input type="text" class="input_text178" path="identityCode"></form:input></td>
                <td><label class="lab">是否追加借：</label>
                <form:select class="select180" path="additionalFlag"><form:option value="">请选择</form:option>
                <c:forEach items="${fns:getDictList('jk_add_flag')}" var="card_type">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
				</c:forEach>
                </form:select></td>
                <td><label class="lab">借款状态：</label>
                <form:select class="select180" path="loanStatusCode">
					<form:option value="">全部</form:option>
					<c:forEach items="${fns:getDictList('jk_loan_apply_status')}" var="card_type">
					  <c:if test="${listFlag!='TG' }">
						<c:if test="${ card_type.label=='待款项确认'|| card_type.label=='金信拒绝'}">
							<form:option value="${card_type.value}">${card_type.label}</form:option>
						</c:if>
					  </c:if>
					  <c:if test="${listFlag =='TG' }">
					     <c:if test="${ card_type.label=='待款项确认'}">
							<form:option value="${card_type.value}">${card_type.label}</form:option>
						</c:if>
					  </c:if>
				     </c:forEach>
					</form:select>
					
				</td>
            </tr>
            <tr id="T2" style="display:none;">
				 <td><label class="lab">是否电销：</label>
				 <form:select class="select180" path="telesalesFlag"><form:option value="">请选择</form:option>
				 <c:forEach items="${fns:getDictList('jk_telemarketing')}" var="card_type">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
				 </c:forEach></form:select></td>
				 <td><label class="lab">合同版本号：</label>
                   <form:select class="select180" path="contractVersion">
					<form:option value="">请选择</form:option>
					 <c:forEach items="${fns:getDictList('jk_contract_ver')}" var="card_type">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
				    </c:forEach>					
					</form:select>
                </td>
				 <td><label class="lab">是否冻结：</label>
             <form:select class="select180" path="frozenFlag">
               <option value="">请选择</option>
           	   <form:option value="1">是</form:option>
               <form:option value="0">否</form:option>
               </form:select>
            </td>
            </tr>
            <tr id="T3" style="display:none;">
                <td><label class="lab">借款利率：</label>
                   <form:input path="monthRate" id="monthRate" class="input_text178" readonly="true"/> 
					  <i id="monthRateBtn" class="icon-search" style="cursor: pointer;"></i>
                </td>
                <td><label class="lab">是否有保证人：</label>
                	<select class="select180" name="ensureManFlag">
                	    <option value="">请选择</option>
            			<option 
            			    <c:if test="${LoanFlowQueryParam.ensureManFlag=='1'}">
            					selected= true 
            				</c:if> 
            			 value="1">有</option>
           			    <option 
           			        <c:if test="${LoanFlowQueryParam.ensureManFlag=='0'}">
            					selected= true 
            				</c:if> 
           			     value="0">无</option>
           			</select>
                </td>
                <td><label class="lab">是否登记失败：</label>
                	<select class="select180" name="registFlag">
                	    <option value="">请选择</option>
            			<option 
            			    <c:if test="${LoanFlowQueryParam.registFlag=='1'}">
            					selected= true 
            				</c:if> 
            			 value="1">成功</option>
           			    <option 
           			       <c:if test="${LoanFlowQueryParam.registFlag=='0'}">
            					selected= true 
            				</c:if> 
           			     value="0">失败</option>
           			</select>
                </td>
            </tr>
            <tr id="T4" style="display:none;">
                <td><label class="lab">是否无纸化：</label>
                	<select class="select180" name="paperLessFlag">
                	    <option value="">请选择</option>
            			<option 
            			    <c:if test="${LoanFlowQueryParam.paperLessFlag=='1'}">
            					selected= true 
            				</c:if> 
            			 value="1">是</option>
           			    <option 
           			        <c:if test="${LoanFlowQueryParam.paperLessFlag=='0'}">
            					selected= true 
            				</c:if> 
           			     value="0">否</option>
           			</select>
                </td>
                <td><label class="lab">是否加盖失败：</label>
                	<select class="select180" name="signUpFlag">
                	    <option value="">请选择</option>
            			<option 
            			 <c:if test="${LoanFlowQueryParam.signUpFlag=='1'}">
            			    selected= true 
            			 </c:if>
            			   value="1">成功</option>
           			    <option 
           			      <c:if test="${LoanFlowQueryParam.signUpFlag=='0'}">
            			    selected= true 
            			  </c:if>
           			     value="0">失败</option>
           			</select>
                </td>
                 <c:if test="${listFlag!='TG' }">
                   <td>
                      <div class="jkhj_grantsure_limitMax"> <label class="lab">合同金额上限：</label>
				        <input type="text" class="input_text178" id="maxMoney" path="contractMoneyStart"></input>
				        <span class="all">&nbsp;<a href="#" id="contractSure">确定</a></span>
				      </div>
				  </td>
				 </c:if>
            </tr>
             <tr id="T5">
            <td><label class="lab">风险等级：</label>
					      <select name="riskLevel" class="select180">
					          <option value=''>全部</option>
			    	        <c:forEach items="${fns:getDictList('jk_loan_risk_level')}" var="item">
					           <option value="${item.value}"
									 <c:if test="${LoanFlowQueryParam.riskLevel==item.value}">
									    selected=true
									  </c:if>
									>
									${item.label}
								  </option>
					         </c:forEach>
					     </select>
					     </td>
						     <td><label class="lab">回访状态：</label> 
						     <form:input path="revisitStatusName" id="revisitStatusName" class="input_text178" readonly="true"/>
						     <form:hidden path="revisitStatus" id="revisitStatus"/>
							 <i id="revisitStatusId"  class="icon-search" style="cursor: pointer;"></i>
						</td>
					     <td><label class="lab">审核日期：</label>
               			<input  name="checkTime"  id="checkTime"  
                 		value="<fmt:formatDate value='${LoanFlowQueryParam.checkTime}' pattern='yyyy-MM-dd'/>"
                     	type="text" class="Wdate input_text178" size="10"  
                                        onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" ></input>
						</td>
			 </tr>
          </table>
		</form:form>
			<div class="tright pr30 pb5">
				<input class="btn btn-primary" type="submit" id="findBtn" value="搜索"></input>
				<button class="btn btn-primary" id="clearBtn">清除</button>
				<div style="float: left; margin-left: 45%; padding-top: 10px">
					<img src="../../../../static/images/up.png" id="showMore"></img>
				</div>
			</div>
	 </div>
        
          <p class="mb5"> 
          <c:if test="${jkhj_grantsure_csexport==null}">
		  <button class="btn btn-small jkhj_grantsure_csexport" id="customerDao">客户信息表导出</button>
		  </c:if>
		  <c:if test="${jkhj_grantsure_dkexport==null}">
		  <button class="btn btn-small jkhj_grantsure_dkexport" id="moneyBtn">打款表导出</button>
		  </c:if>
		  <c:if test="${jkhj_grantsure_hzexport==null}">
		  <button class="btn btn-small jkhj_grantsure_hzexport" id="sumBtn">汇总表导出</button>
		  </c:if>
		  <c:if test="${listFlag!='TG' }">
		  	<c:if test="${jkhj_grantgc_ppexport==null}">
			  <button class="btn btn-small jkhj_grantgc_ppexport" id="addP2P" value="P2P">批量添加P2P标识</button>
			  </c:if>
			  <c:if test="${jkhj_grantgc_ppcancel==null}">
			  <button class="btn btn-small jkhj_grantgc_ppcancel" id="cancleP2P" value="P2P">批量P2P取消</button>
			  </c:if>
			  <c:if test="${jkhj_grantgc_cancel==null}">
		       <button class="btn btn-small jkhj_grantgc_cancel" id="flagUp" role="button" data-target="#uploadFlag" data-toggle="modal">标识上传</button>
		       </c:if>
		  </c:if>
		  <c:if test="${jkhj_grantsure_upload==null}">
	      <button id="offLineShang" role="button" class="btn btn-small jkhj_grantsure_upload" data-target="#uploadAuditedModal" data-toggle="modal">上传回执结果</button>
		  </c:if> 
		   放款笔数：<label class="red" id="totalNum">0 </label>笔&nbsp;
		  放款金额：<label class="red" id="totalGrantMoney">0.00 </label>元&nbsp;
		  <input type="hidden" id="hiddenGrant" value="0.00"/>
		  合同金额：<label class="red" id="totalContractMoney">0.00 </label>元
		  <input type="hidden" id="hiddenContract" value="0.00"/>
		  </p>
    <sys:columnCtrl pageToken="grantSureList"></sys:columnCtrl>
   <div class="box5">
    <!-- id="tableList" -->
    <table  id="tableList" class="table  table-bordered table-condensed table-hover ">
	  <thead>
        <tr>
            <th><input  id="checkAll" onclick="checkAll()"  type="checkbox"/></th>
            <th>序号</th>
            <th>合同编号</th>
            <th>门店</th>                    
            <th>客户姓名</th>
            <th>产品</th>
            <th>共借人</th>
            <th>自然人保证人</th>
            <th>证件号码</th>
            <th>期数</th>
            <th>借款利率</th>
            <th>合同金额</th>
            <th>放款金额</th>
            <th>催收服务费</th>
            <th>开户行</th>
            <th>支行名称</th>
            <th>账号</th>
            <th>借款状态</th>
            <th>退回原因</th>
            <th>加急标识</th>
            <th>模式</th>
            <th>渠道</th>
            <th>合同版本号</th>
            <th>是否无纸化</th>
            <th>是否加盖失败</th>
            <th>是否有保证人</th>
            <th>是否登记失败</th>
			<th>是否电销</th>
			<th>风险等级</th>
			<th>回访状态</th>
			<th>审核日期</th>
		    <th>操作</th>
        </tr>  
        </thead>
     <tbody class="bs">
         <c:if test="${workItems!=null && fn:length(workItems.list)>0}">
         <c:set var="index" value="0"/>
          <c:forEach items="${workItems.list}" var="item">
          <c:set var="index" value="${index+1}" />
             <tr <c:if test="${item.backFlag=='1'||item.loanStatusName=='金信拒绝'}">
                    class='trRed'
               </c:if>
             >
             <td><input type="checkbox" name="checkBoxItem" borrowTrusteeFlag='${item.channelName}' applyId='${item.applyId }' replyProductName='${item.replyProductName}'
              contractMoney='${item.contractMoney}' grantAmount='${item.lendingMoney}' frozenFlag="${item.frozenFlag}"  
              value='${item.applyId},${item.contractCode},${item.loanCode}'/>
              </td>
            
             <td>${index}</td>
             <td>${item.contractCode}</td>
             <td>${item.storeName}</td>
             <td>${item.customerName}</td>             
             <td>${item.replyProductName}</td>
             <td>   
             	<c:if test="${item.loanInfoOldOrNewFlag eq '' || item.loanInfoOldOrNewFlag eq '0'}">
             		<c:if test="${item.coborrowerName!=null && fn:contains(item.coborrowerName,'null')==false}" >
                 		${item.coborrowerName}
             		</c:if>
             	</c:if> 
             </td>
             <td>
             	<c:if test="${item.loanInfoOldOrNewFlag eq '1'}">
             		<c:if test="${item.coborrowerName!=null && fn:contains(item.coborrowerName,'null')==false}" >
                 		${item.coborrowerName}
             		</c:if>
             	</c:if> 
             </td>
             <td>${item.identityCode}</td>
             <td>${item.replyMonth}</td>
             <td>${item.monthRate}</td>
             <td><fmt:formatNumber value='${item.contractMoney}' pattern="#,#00.00"/></td>
             <td><fmt:formatNumber value='${item.lendingMoney}' pattern="#,#00.00"/></td>
             <td><fmt:formatNumber value='${item.urgeServiceFee}' pattern="#,#00.00"/></td>
             <td>${item.depositBank}</td>
             <td>${item.bankBranchName}</td>
             <td>${item.bankAccountNumber}</td>
             <td>
                ${item.loanStatusName}
                <c:if test="${item.frozenFlag=='1'}">
                    (门店申请冻结)
                </c:if>
             </td>
             <td>${item.backReason}</td>
             <td>
             <c:if test="${item.urgentFlag=='1'}">
                    <span style="color:red">加急</span>
             </c:if>
             </td>
             <td>${item.modelLabel}</td>
             <td>${item.channelName}</td>
             <td>${item.contractVersion}</td>
             <td>
              <c:choose>
                 <c:when test="${item.paperLessFlag=='1'}">
                   	是
                 </c:when>
                 <c:otherwise>
                                                    否
                 </c:otherwise>
               </c:choose>
             </td>
             <td>
              <c:choose>
                 <c:when test="${item.signUpFlag=='1'}">
                   	成功
                 </c:when>
                 <c:when test="${item.signUpFlag=='0'}">
                   	失败
                 </c:when>
               </c:choose>
             </td>
             <td>
              <c:choose>
                 <c:when test="${item.ensureManFlag=='1'}">
                                                    有
                 </c:when>
                 <c:otherwise>
                                                    无
                 </c:otherwise>
               </c:choose>
             </td>
             <td>
              	<c:choose>
                  <c:when test="${item.registFlag=='1'}">
                                                      成功
                  </c:when>
                  <c:when test="${item.registFlag=='0'}">
                      <span style="color:red">失败</span>
                  </c:when>
                </c:choose>
             </td>
             <td>
             <c:if test="${item.telesalesFlag=='1'}">
             <span>是</span>
             </c:if>
             </td>
             <td>${item.riskLevel}</td>
              <td>
             	<c:choose>
             	  <c:when test="${item.revisitStatus == '' || item.revisitStatus == null}">
                  </c:when>
                  <c:when test="${item.revisitStatus == -1 }">
                                                     失败
                  </c:when>
                 <c:when test="${item.revisitStatus == 0 }">
                                                     待回访	
                  </c:when>
                  <c:when test="${item.revisitStatus == 1 }">
                                                      成功
                  </c:when>
                </c:choose>
             </td>
             <td><fmt:formatDate value="${item.checkTime }"
							pattern="yyyy-MM-dd" /></td>
             <td style="position:relative">
             <input type="button" class="btn btn_edit edit" value="操作"/>
             
             <div class="alertset" style="display:none">
             <c:if test="${notContractAudit=='1'}">
             <input type="button" class="btn_edit jkhj_grantsure_surebtn" name="grantSureBtn"　
									contractCode='${item.contractCode}' applyId='${item.applyId}'
									urgentFlag='${item.urgentFlag}' frozenFlag='${item.frozenFlag}'　
									revisitStatus='${item.revisitStatus }' value="确认" />
			<c:if test="${item.issplit=='0'}"> 						
			<button class="btn_edit jkhj_grantsure_backbtn" data-target="#sureBack"
									data-toggle="modal" name="grantBackBtn" applyId='${item.applyId}'
									contractCode='${item.contractCode}' loanCode = '${item.loanCode }'>退回</button>
			</c:if>						
			<c:if test="${listFlag!='TG' }">
			<input type="button" class="btn_edit jkhj_grantsure_p2pbtn" name="addP2PBtn" value="P2P" applyId='${item.applyId}'
									contractCode='${item.contractCode}' loanCode='${item.loanCode}'/>
			</c:if>
			</c:if>
			<input type="button" class="btn_edit jkhj_grantsure_hisbtn" onclick="showAllHisByLoanCode('${item.loanCode}')"  value="历史"></input> 
			
			<!-- 门店申请冻结的单子显示驳回申请的按钮 -->
			 <c:if test="${item.frozenFlag=='1'}">
			 	<c:if test="${notContractAudit=='1'}">
								<input type="button" class="btn_edit jkhj_grantsure_backFrozenbtn" name="backApply"
									applyId='${item.applyId}' contractCode='${item.contractCode}'
									loanCode='${item.loanCode }'
									value="驳回申请" />
			    </c:if>
			</c:if>
			</div>
			
             </td>
         </tr> 
         </c:forEach>  
         </c:if>
         
         <c:if test="${ workItems==null || fn:length(workItems.list)==0}">
           <tr><td colspan="25" style="text-align:center;">没有待办数据</td></tr>
         </c:if>
     </tbody> 
    </table>
	</div>

   <!-- 打款表上传回执结果Start -->
   <div class="modal fade" id="uploadAuditedModal" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" >
		<div class="modal-dialog role="document"">
				<div class="modal-content">
		<form id="uploadAuditForm" role="form" enctype= "multipart/form-data" method="post" action="${ctx}/borrow/grant/grantSure/importResult?listFlag=${listFlag }">
					<div class="modal-header">
					   <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title" id="myModalLabel">上传回执结果</h4>
					 </div>
					 <div class="modal-body">
					 <input type="hidden" name="menuId" value="${param.menuId }">
					 <input type='file' name="file" id="fileid">
					 </div>
                     <div class="modal-footer">
   					 <input class="btn btn-primary" type="submit" id="sureBtn" value="确认">
      					 <button class="btn btn-primary"  class="close" data-dismiss="modal" >取消</button>
                    </div>
		</form>
			</div>
			</div>
	</div>
	<!-- 打款表上传回执结果End -->
	
	<!-- 标识上传回执结果Start -->
   <div class="modal fade" id="uploadFlag" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" >
		<div class="modal-dialog role="document"">
				<div class="modal-content">
		<form id="uploadFlagForm" role="form" enctype= "multipart/form-data" method="post" action="${ctx}/borrow/grant/grantSure/uploanFlag">
					<div class="modal-header">
					   <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title" id="uploadFlagLabel">标识上传</h4>
					 </div>
					 <div class="modal-body">
					 <input type='file' name="file" id="fileid2">
					 </div>
                     <div class="modal-footer">
   					 <input class="btn btn-primary" type="submit" id="sureUploadFlagBtn" value="确认">
      					 <button class="btn btn-primary"  class="close" data-dismiss="modal" >取消</button>
                    </div>
		</form>
			</div>
			</div>
	</div>
	<!-- 标识上传回执结果End -->
	
	<!-- 放款确认退回Start -->
	<div class='modal fade'  id="sureBack" style="width:90%;height:90%">
       <div class="modal-dialog" role="document">
	       <div class="modal-content">
           <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h4 class="modal-title" id="grantSureBack">待放款确认退回</h4>
		    </div>
       <div class="modal-body">
			<table class="table table-striped table-bordered table-condensed">
                <tr>
                    <td align="left"><label>退回原因</label></td>
                    <td align="left">
                      <select id="sel" class="select180">
                        <c:forEach items="${fns:getDictList('jk_chk_back_reason')}" var="card_type">
                        <c:if test="${card_type.label=='客户主动放弃'||card_type.label=='风险客户'||card_type.label=='其他'}">
							<option value="${card_type.value}">${card_type.label}</option>
						</c:if>
				       </c:forEach>
                      </select>
                    </td>
                </tr>
                <tr id="other" style="display:none">
                    <td align="left"><label>其他退回原因</label></td>
                    <td align="left"><span class="red">*</span><textarea  rows="30" cols="16" id="remark"></textarea></td>
                </tr>
                
         <form id="param">
			<input type="hidden" name="applyId" id="applyId" />
			<input type="hidden" name="loanCode" id="loanCode"/>
			<input type="hidden" name="contractCode" id="contractCode1"/>
 		 </form>
            </table>
		</div>
            <div class="modal-footer"><button class="btn btn-primary" id="grantSureBackBtn">确认</button>
            <button class="btn btn-primary" class="close" data-dismiss="modal" onclick="closeModal('sureBack')">取消</button></div>
		</div>
		</div>
		</div>
	<!-- 放款确认退回End -->
	 <div class="pagination">${workItems}</div>
	  <script type="text/javascript">
	  $("#tableList").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
		$('#tableList').bootstrapTable({
			method: 'get',
			cache: false,
			height: $(window).height()-410,
			
			pageSize: 20,
			pageNumber:1
		});
		$(window).resize(function () {
			$('#tableList').bootstrapTable('resetView');
		}); 
	</script> 
</body>
</html>