<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<script src='${context}/js/bootstrap.autocomplete.js'
	type="text/javascript"></script>
<script src="${context}/js/contract/contractAudit.js?version=3"
	type="text/javascript"></script>
<script type="text/javascript" src='${context}/js/common.js'></script>
<script src="${context}/js/contract/confirmSign.js?vension=243"
	type="text/javascript"></script>
<script src="${context}/js/pageinit/areaselect.js"
	type="text/javascript"></script>

<script type="text/javascript">
   REDIRECT_URL="/borrow/borrowlist/fetchTaskItems";
   var co_count = ${fn:length(workItem.bv.coborrowers)};
  $(document).ready(function() {
  	confirmSign.load();
  	loanCard.initCity("bankProvince", "bankCity",null);
  	 areaselectCard.initCity("bankProvince", "bankCity",
  			  null, $("#bankCityHidden")
  					.val(), null);
  	if($('#modelName').val()=='TG'){
  		loan.initKingBankCity("bankJzhKhhss", "bankJzhKhhqx","2");
  		areaselect.initKingBankCity("bankJzhKhhss", "bankJzhKhhqx","2", $("#bankJzhKhhqxHidden").val());
  		}
  	$('#bankIsRareword').bind('click',function(){
  		 if($(this).attr('checked') || $(this).attr('checked')=='checked'){
  			 $('#bankAccountName').removeAttr("readOnly");
  			 $('#rarewordHidden').val('1');
  		 }else{
  			 $('#bankAccountName').attr('readOnly', true); 
  			 $('#rarewordHidden').val('0');
  		 }
  	 });
   	$('#backBtn').bind('click',function(){
    	backFlow('_grantBackDialog','',REDIRECT_URL,'backForm1');  //传递退回窗口的视图名称
    }); 
   });
  function aaa(){
	  obj = document.getElementsByName("a11");
	    check_val = [];
	    for(k in obj){
	        if(obj[k].checked)
	            check_val.push(obj[k].value);
	    }
	    if(check_val==0){
	    	art.dialog.alert("请至少选择一笔");
	    }
  }
/*   */
</script>
</head>
<body>
	<div class="tright " style="height: 35px; line-height: 33px">
	   <%--  <c:if test="${workItem.bv.isStoreAssistant=='1'}"> --%>
	   <c:if test="${workItem.bv.proposeFlag=='1'}">
		   <button class="btn btn-small" id="custGiveUp" class="cancel">客户放弃</button>
		   <button class="btn btn-small" id="storeGiveUp" class="cancel">门店拒绝</button>
	   </c:if>
	   
	   <c:if test="${workItem.bv.reconsiderFlag=='0' && workItem.bv.proposeFlag=='0'}">
				<c:if test="${workItem.bv.loanInfo.outFlag!='1'}">
					<button class="btn btn-small" id="custGiveUp1">建议放弃</button>
				</c:if>
			</c:if>
		<%-- </c:if> --%>
		<button class="btn btn-small"
			loanCode="${workItem.bv.contract.loanCode}" id="historyBtn">历史</button>
		<button class="btn btn-small" id="retBtn">返回</button>
	</div>
	<!-- 门店拒绝弹出框 -->
	<div id="refuseMod" style="display:none">
	  <form method="post" action="">
	   	<table class="table1" cellpadding="0" cellspacing="0" border="0"
				width="100%">
		 <tr valign="top">
			<td><label >备注原因：</label>
		    	<textarea id="rejectReason" maxlength="450" cols="55" class="textarea_refuse" ></textarea>
			</td>
		 </tr>
		</table>		
		</form>
	</div>
	<form id="backForm1" medthod="post">
	   <input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
		<input type="hidden" value="${workItem.flowName}" name="flowName"></input>
		<input type="hidden" value="${workItem.stepName}" name="stepName"></input>
		<input type="hidden" value="${workItem.token}" name="token"></input>
		<input type="hidden" value="${workItem.flowId}" name="flowId"></input> 
		<input type="hidden" value="${workItem.wobNum}" name="wobNum"></input> 
		<input type="hidden" value="${workItem.bv.applyId}" name="applyId"></input>
		<input type="hidden" value="${bv.contract.applyId}"name="contract.applyId" />
		<input type="hidden" value="${workItem.bv.contract.loanCode}" name="contract.loanCode" /> 
		<input type="hidden" value="${bv.contract.contractCode}" name="contract.contractCode" />
		<input type="hidden" value="${bv.contract.contractMonths}" name="contract.contractMonths" />
		<input type="hidden" value="2" id="dictOperateResult" name="dictOperateResult" />
		<input type="hidden" value="TO_GIVEUP" name="response"/>
		<input type="hidden" name="remarks" id="remark"/>
	</form>
	<form id="backForm" medthod="post">
	   <input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
		<input type="hidden" value="${workItem.flowName}" name="flowName"></input>
		<input type="hidden" value="${workItem.stepName}" name="stepName"></input>
		<input type="hidden" value="${workItem.token}" name="token"></input>
		<input type="hidden" value="${workItem.flowId}" name="flowId"></input> 
		<input type="hidden" value="${workItem.wobNum}" name="wobNum"></input> 
		<input type="hidden" value="${workItem.bv.applyId}" name="applyId"></input>
		<input type="hidden" value="${bv.contract.applyId}"name="contract.applyId" />
		<input type="hidden" value="${bv.contract.loanCode}" name="contract.loanCode" /> 
		<input type="hidden" value="${bv.contract.contractCode}" name="contract.contractCode" />
		<input type="hidden" value="${bv.contract.contractMonths}" name="contract.contractMonths" />
		<input type="hidden" value="2" id="dictOperateResult1" name="dictOperateResult" />
		<input type="hidden" value="TO_GIVEUP" name="response" id="response1"/>
		<input type="hidden" value="/borrow/borrowlist/fetchTaskItems" name="redirectUrl"/>
		<input type="hidden" name="remarks" id="remark1"/>
	</form>
	<c:set var="bv" value="${workItem.bv}" />
	<form id="confirmSignForm" method="post">
		<input type="hidden" value="${bv.productType}" id="productType"/>
		<input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
		<input type="hidden" value="${workItem.flowName}" name="flowName"></input>
		<input type="hidden" value="${workItem.stepName}" name="stepName"></input>
		<input type="hidden" value="${workItem.token}" name="token"></input>
		<input type="hidden" value="${workItem.flowId}" name="flowId"></input>
		<input type="hidden" value="${workItem.wobNum}" name="wobNum"></input> 
		<input type="hidden" value="${workItem.bv.applyId}" name="applyId"></input>
		<input type="hidden" value="${bv.contract.applyId}"name="contract.applyId" />
		<input type="hidden" value="${bv.loanInfo.issplit}"name="loanInfo.issplit" id="issplit" />
		<input type="hidden" id="loanCode" value="${bv.contract.loanCode}" name="contract.loanCode" /> 
		<input type="hidden" value="${bv.contract.contractCode}" name="contract.contractCode" />
		<input type="hidden" value="${bv.contract.contractMonths}" name="contract.contractMonths" />
		<input type="hidden" value="2" name="dictOperateResult" />
		<input type="hidden" value="${bv.loanFlag}" id="channelName" />
		<input type="hidden" value="${bv.mainLoaner}" id="mainLoaner" />
		<input type="hidden" value="${bv.customerId}" id="custId" />
		<input type="hidden" value="${bv.customerCode}" id="custCode" />
		<input type="hidden" id="modelName" value="${bv.modelName}"/>
		<input type="hidden" id="sendEmailFlag" value="${bv.email}"/>
		<input type="hidden" id="oldEmailFlag" value="${bv.email}"/>
		<div class="control-group">
			<table class=" table1" cellpadding="0" cellspacing="0" border="0"
				width="100%">
				<tr>
					<td width="200px"><label class="lab ">客户姓名：</label>${bv.mainLoaner}</td>
					<td><label class="lab ">手机号码：</label>${bv.customerPhoneFirst}</td>
					<td width="500px"><input class="btn btn-small" type="button"
						 stype="0" id="btnPin" 
						 <c:if test="${bv.captchaIfConfirm=='1'}">
								   disabled=true value="客户已验证"
						 </c:if>
						 <c:if test="${bv.captchaIfConfirm!='1'}">
						     value="发送验证码"
						 </c:if>
						onclick="sendPin(this,'${bv.customerPhoneFirst}','${bv.customerCode}','${bv.mainLoaner}');" />&nbsp;
						<span><input type="text" class="input-medium" name="txt_pin" 
						<c:if test="${bv.captchaIfConfirm=='1'}">
								   disabled=true 
								   value="${bv.customerPin}" 
						 </c:if>
						id="txt_pin" onchange="confirmPin(this,'${bv.contract.loanCode}','0');"/> <span>
						<input type="hidden" name="pin" id="pin" value="${bv.customerPin}"> 
					</td>
					<td><label class="lab ">证件类型：</label>${bv.mainCertTypeName}</td>
					<td><label class="lab ">证件号码：</label>${bv.mainCertNum}</td>
				</tr>
				<c:if
					test="${bv.coborrowers !=null && fn:length(bv.coborrowers)>0 }">
					<c:forEach items="${bv.coborrowers}" var="item" varStatus="status">
						<tr>
							<td width="200px"><label class="lab ">
							 <c:if test="${bv.loanInfo.loanInfoOldOrNewFlag=='1'}"> 
                				自然人保证人：
			                 </c:if>
			                  <c:if test="${bv.loanInfo.loanInfoOldOrNewFlag!='1'}">
			                 	共借人： 
			                 </c:if>
							</label>${item.coboName}
							</td>
							<td><label class="lab ">手机号码：</label>${item.coboMobile}</td>
							<td><input class="btn btn-small" type="button"
								stype="${status.index + 1}"  id="btn_co_pin_${status.index + 1}" 
								<c:if test="${item.captchaIfConfirm=='1'}">
								   disabled=true value="客户已验证"
								</c:if> 
								<c:if test="${item.captchaIfConfirm!='1'}">
								  value="发送验证码" 
								</c:if>
								onclick="sendPin(this,'${item.coboMobile}','${item.id}','${item.coboName}');"
								/>&nbsp;<span><input  type="text" class="input-medium"
								name="txt_co_pin_${status.index + 1}" onchange="confirmPin(this,'${item.id}','${status.index + 1}');"
								id="txt_co_pin_${status.index + 1}"
								<c:if test="${item.captchaIfConfirm=='1'}">
								   disabled=true value="${item.customerPin}" 
								</c:if>
								 ></span>
							</td>
							<td> 
								<input type="hidden" name="co_pin_${status.index + 1}" id="co_pin_${status.index + 1}" value="${item.customerPin}">
								<label class="lab ">证件类型：</label>${item.dictCertTypeName}
							</td>
							<td>
								<label class="lab ">证件号码：</label>${item.coboCertNum}
								<input type="hidden" id="coboName_${status.index + 1}"
								value="${item.coboName}">&nbsp;
							</td>
						</tr>
					</c:forEach>
				</c:if>
			</table>
		</div>
		<h2></h2>
		<c:if test="${bv.loanInfo.issplit=='0'}">
		<div class="box2 mb10">
			<table class=" table1" cellpadding="0" cellspacing="0" border="0"
				width="100%">
				<tr>
					<td width="33%"><label class="lab ">批借金额：</label> <fmt:formatNumber
							value='${bv.contract.auditAmount}' pattern="#,##0.00" /></td>
					<td><label class="lab ">批复期数：</label>${bv.contract.contractMonths}</td>
					<td><label class="lab ">实放金额：</label> <fmt:formatNumber
							value='${bv.ctrFee.feePaymentAmount}' pattern="#,##0.00" /></td>
				</tr>
				<tr>
					<td width="33%"><label class="lab">产品总费率：</label> <fmt:formatNumber
							value='${bv.ctrFee.feeAllRaio}' pattern="#,##0.000" />%</td>
					<td><label class="lab">合同金额：</label> <fmt:formatNumber
							value='${bv.contract.contractAmount}' pattern="#,##0.00" /></td>
					<td><label class="lab">月还款：</label> <fmt:formatNumber
							value='${bv.contract.monthPayTotalAmount}' pattern="#,##0.00" /></td>
				</tr>
				<td><label class="lab ">合同编号：</label>${bv.contract.contractCode}</td>
				<td></td>
				</tr>
			</table>
		</div>
		</c:if>
		<c:if test="${bv.loanInfo.issplit=='1'}">
		<div class="box2 mb10">
			<table class=" table1" cellpadding="0" cellspacing="0" border="0"
				width="100%">
				<tr>
					<td width="33%"><label class="lab ">批复期数：</label>${bv.contract.contractMonths}</td>
						<td><label class="lab ">合同编号：</label>${bv.contractTemps[0].contractCode}</td>	
						<td><label class="lab ">合同编号：</label>${bv.contractTemps[1].contractCode}</td>	
				
				</tr>
				<tr>
					<td width="33%"><label class="lab">产品总费率：</label> <fmt:formatNumber
							value='${bv.contractTemps[0].feeAllRaio}' pattern="#,##0.000" />%</td>
							
							<td><label class="lab ">批借金额：</label><fmt:formatNumber value='${bv.contractTemps[0].auditAmount}' pattern="#,##0.00" /></td>	
							<td><label class="lab ">批借金额：</label><fmt:formatNumber value='${bv.contractTemps[1].auditAmount}' pattern="#,##0.00" /></td>	
				
				</tr>
				<tr>
						<td width="33%"></td>
						<td><label class="lab ">实放金额：</label><fmt:formatNumber value='${bv.contractTemps[0].feePaymentAmount}' pattern="#,##0.00" /></td>
						<td><label class="lab ">实放金额：</label><fmt:formatNumber value='${bv.contractTemps[1].feePaymentAmount}' pattern="#,##0.00" /></td>
				</tr>
				<tr>
						<td width="33%"></td>
						<td><label class="lab ">合同金额：</label><fmt:formatNumber value='${bv.contractTemps[0].contractAmount}' pattern="#,##0.00" /></td>
						<td><label class="lab ">合同金额：</label><fmt:formatNumber value='${bv.contractTemps[1].contractAmount}' pattern="#,##0.00" /></td>
				</tr>
				<tr>
						<td width="33%"></td>
						<td><label class="lab ">月还款额：</label><fmt:formatNumber value='${bv.contractTemps[0].contractMonthRepayTotal}' pattern="#,##0.00" /></td>
						<td><label class="lab ">月还款额：</label><fmt:formatNumber value='${bv.contractTemps[1].contractMonthRepayTotal}' pattern="#,##0.00" /></td>
				</tr>
				<tr>
						<td width="33%"></td>
						<td><label class="lab ">接受此笔借款:</label><input type="checkbox" name="isreceive" value="${bv.contractTemps[0].id}" <c:if test="${bv.contractTemps[0].isreceive=='1'}"> checked="checked"</c:if> /></td>
						<td><label class="lab ">接受此笔借款:</label><input type="checkbox" name="isreceive" value="${bv.contractTemps[1].id}" <c:if test="${bv.contractTemps[1].isreceive=='1'}"> checked="checked"</c:if> /></td>
				</tr>
			</table>
		</div>
		</c:if>
		<h2></h2>
		<div class="box2 mb10">
			<table class=" table1" cellpadding="0" cellspacing="0" border="0"
				width="100%">
				<tr>
					<td width="33%"><label class="lab "><span
							style="color: red">*</span>开户行名称：</label> 
							<c:if test="${bv.productType!='A020'}">
									<select id="bankName"
								name="loanBank.bankName" value="${bv.loanBank.bankName}"
								class="select180 required">
									<option value="">请选择</option>
									<c:forEach items="${fns:getDictList('jk_open_bank')}"
										var="curItem">
										<option value="${curItem.value}"
											<c:if test="${bv.loanBank.bankName==curItem.value}">
							      selected=true 
							    </c:if>>${curItem.label}</option>
									</c:forEach> </select> 
							</c:if>
							<c:if test="${bv.productType=='A020'}">
								<select id="bankName"
								name="loanBank.bankName" value="${bv.loanBank.bankName}"
								class="select180 required">
									<option value="">请选择</option>
									<c:forEach items="${bv.jyjBank}" var="curItem">
										<option value="${curItem.bankCode}"
											<c:if test="${bv.loanBank.bankName==curItem.bankCode}">
							      selected=true 
							    </c:if>>${curItem.bankName}</option>
									</c:forEach> </select> 
							</c:if>
						<input type="hidden" name="applyBankName" id="applyBankName" /></td>
					<td><label class="lab "><span style="color: red">*</span>支行名称：</label>
						<input type="text" id="bankBranch" name="loanBank.bankBranch"
						value="${bv.loanBank.bankBranch}"
						class="input-medium required stringCheck2"> <input
						type="hidden" name="applyBankBranch" id="applyBankBranch" /></td>
					<td width="33%"><label class="lab "><span
							style="color: red">*</span>开户省市：</label> <select
						class="select78 mr34 required" name="loanBank.bankProvince"
						value="${bv.loanBank.bankProvince}" id="bankProvince">
							<option value="">请选择</option>
							<c:forEach items="${bv.provinceList}" var="item">
								<option value="${item.areaCode}"
									<c:if test="${bv.loanBank.bankProvince==item.areaCode}">
		                    selected=true 
		                  </c:if>>${item.areaName}
							</c:forEach>
					</select> <input type="hidden" id="bankProvinceHidden"
						value="${bv.loanBank.bankCity}" /> <select
						class="select78 required" name="loanBank.bankCity"
						value="${bv.loanBank.bankCity}" id="bankCity">
							<option value="">请选择</option>
					</select> <input type="hidden" id="bankCityHidden"
						value="${bv.loanBank.bankCity}" /></td>

				</tr>
				<tr>
					<td width="33%"><label class="lab "><span
							style="color: red">*</span>账户姓名：</label> <input type="text"
						name="loanBank.bankAccountName"
						value="${bv.loanBank.bankAccountName}" readonly="readonly" id="bankAccountName" 
						class="input-medium required"> 
						 <input type="checkbox" id="bankIsRareword" value="1"
				   			 <c:if test="${bv.loanBank.bankIsRareword==1}">
				     			  checked = true
				    		</c:if>
				 		   />是否生僻字
						<input type="hidden"
						name="loanBank.id" value="${bv.loanBank.id}"></input>
						 <input type="hidden" name="loanBank.bankIsRareword" id="rarewordHidden"
						  value="${bv.loanBank.bankIsRareword}"></input>
					</td>
					<td><label class="lab "><span style="color: red">*</span>银行账号：</label>
						<input type="text" id="bankAccount" name="loanBank.bankAccount"
						value="${bv.loanBank.bankAccount}" class="input-medium required">
						<input type="hidden" name="applyBankAccount" id="applyBankAccount" />
					</td>
					<td><label class="lab "><span style="color: red">*</span>签约平台：</label>
						<select id="bankSigningPlatform"
						name="loanBank.bankSigningPlatform"
						value="${bv.loanBank.bankSigningPlatform}"
						class="select180 required">
							<option value="">请选择</option>
							<c:forEach items="${fns:getDictList('jk_deduct_plat')}"
								var="item">
								<c:if test="${item.label!='中金' && item.value!='6' && item.value != '7' && item.value != '4'}">
									<option value="${item.value}"
										<c:if test="${bv.loanBank.bankSigningPlatform==item.value}">
					        				 selected=true 
					      				</c:if>>${item.label}
					      			</option>
					      		</c:if>
							</c:forEach>
					</select> <input type="hidden" name="signingPlatformName"
						id="signingPlatformName" /></td>
				</tr>
				<tr
					<c:if test="${bv.modelName!='TG'}">
                 style="display:none" 
              </c:if>>
					<td width="33%"><label class="lab "><span
							style="color: red">*</span>金账户开户省市:</label> <select
						class="select78 mr34 required" name="loanBank.bankJzhKhhss"
						value="${bv.loanBank.bankJzhKhhss}" id="bankJzhKhhss">
							<option value="">请选择</option>
							<c:forEach items="${bv.fyProvinceList}" var="item">
								<option value="${item.areaCode}"
									<c:if test="${bv.loanBank.bankJzhKhhss==item.areaCode}">
		                    selected=true 
		                  </c:if>>${item.areaName}
							</c:forEach>
					</select> <input type="hidden" id="kingBankProvinceName"
						name="kingBankProvinceName" /> <input type="hidden"
						id="kingBankProvinceCode" name="kingBankProvinceCode" /> <select
						class="select78 required" name="loanBank.bankJzhKhhqx"
						value="${bv.loanBank.bankJzhKhhqx}" id="bankJzhKhhqx">
							<option value="">请选择</option>
					</select> <input type="hidden" id="bankJzhKhhqxHidden"
						value="${bv.loanBank.bankJzhKhhqx}" /> <input type="hidden"
						id="kingBankCityCode" name="kingBankCityCode" /> <input
						type="hidden" id="kingBankCityName" name="kingBankCityName" /></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td width="33%"><label class="lab "><span
							style="color: red">*</span>合同签订日期：</label> <input id="contractDueDay"
						name="contract.contractDueDay"
						value="<fmt:formatDate value='${bv.contract.contractDueDay}' pattern="yyyy-MM-dd"/>"
						type="text" class="Wdate input_text178 required"
						size="10"
						onClick="WdatePicker({skin:'whyGreen'})"
						style="cursor: pointer" id="contractDueDay" /> <!--effectiveDay 有效期十天校验--> <%-- 	<input type="hidden" value="2030-01-01" id="signEndDay"/>--%>
						<input type="hidden"
						value="<fmt:formatDate value="${bv.signEndDay}" pattern="yyyy-MM-dd"/>"
						id="signEndDay" />
						<input type="hidden" value="<fmt:formatDate value="${bv.signStartDay}" pattern="yyyy-MM-dd"/>" 
						id="signStartDay"/>
						</td>
					<td>
						<label class="lab">
						<c:if test="${bv.productType!='A021'}">
						<span style="color: red">*</span>
						</c:if>
						邮箱：</label> 
						<input name="email" type="text" value="${bv.email}" class="input_text178 <c:if test="${bv.productType!='A021'}"> required </c:if>" />
						<input type="button" value="发送邮箱验证" onclick="sendtoemail(this)" id="emailBtn"/>
						<input name = "emailvalidator" id="emailvalidator" width="30px" style= "background-color:transparent;border:none;"></input>
					</td>
				</tr>
			</table>
		</div>
		<div class="tright  pr30"">
			<input type="submit" class="btn btn-primary" value="提交"
				id="confirmSignBtn"></input>
			<c:if test="${bv.backTimeFlag=='0'}"> 
				<button class="btn btn-primary"  type="button" id="backBtn">退回</button>
			</c:if>
		</div>
	</form>

</body>
</html>