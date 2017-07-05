<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>Insert title here</title>
	<meta name="decorator" content="default" />	
	<script type="text/javascript" src="${context}/js/launch/contactFormat.js"></script>
	<script type="text/javascript" src="${context}/js/launch/addContact.js"></script>
	<script type="text/javascript" src="${context}/js/launch/creditFormat.js"></script>
	<script type="text/javascript" src="${context}/js/launch/addContact.js"></script>
	<script type="text/javascript" src="${context}/js/launch/house.js"></script>
	<script type="text/javascript" src="${context}/js/consult/loanLaunch.js"></script>
	<script type="text/javascript" src="${context}/js/launch/coborrowerFormat.js"></script>
	<script type="text/javascript" src="${context}/js/certificate.js"></script>
	<script type="text/javascript" src="${context}/js/launch/addContact.js"></script>
	<script type="text/javascript" language="javascript" src='${context}/js/common.js'></script>
	<script type="text/javascript" src="${context}/js/storereview/loanFlowStoreReview.js?version=1"></script>	
	<script type="text/javascript" src="${context}/js/pageinit/areaselect.js"></script>
	<script src="${context}/static/ckfinder/plugins/gallery/colorbox/jquery.colorbox-min.js"></script> 
	<link media="screen" rel="stylesheet" href="${context}/static/ckfinder/plugins/gallery/colorbox/colorbox.css" />
	<script type="text/javascript">
		$(document).ready(
				function() {
					var teleFlag='${param.teleFlag}';
					$("#backBtn").click(function(){
						if(teleFlag=='0'){
							window.location=ctx+"/borrow/borrowlist/fetchTaskItems";
						}else if(teleFlag=='1'){
							window.location=ctx+"/borrow/borrowlist/fetchTaskTelesales";
						}
					});	
					// 删除共借人
	    			$("input[name='delCoborrower']").each(function(){
	    				$(this).bind('click',function(){
	    					var index = $(this).attr("index");
	    					var coboId = $(this).attr("coboId");
	    					coborrower.deleteCobo("contactPanel_"+index,$('#response').val(),coboId);
	    				});
	    				
	    			});	
	    			 $('#bankIsRareword').bind('click',function(){
	    				 if($(this).attr('checked') || $(this).attr('checked')=='checked'){
	    					 $('#bankAccountName').removeAttr("readOnly");
	    				 }else{
	    					 $('#bankAccountName').attr('readOnly', true); 
	    				 }
	    			 });
	    			var createTime = '<fmt:formatDate value="${workItem.bv.consultTime}" pattern="yyyy-MM-dd HH:mm:ss"/>';
	    			loan.initProductWithLimit("productId", "monthId","limitLower","limitUpper",createTime);
	    			loan.selectedProductWithLimit("productId", "monthId",$('#loanMonths').val(),"limitLower","limitUpper",createTime);
					// 主借人现住址,户籍地址
					loan.initCity("liveProvinceId", "liveCityId","liveDistrictId");
					loan.initCity("registerProvince","customerRegisterCity", "customerRegisterArea");
					
					
					areaselect.initCity("liveProvinceId", "liveCityId",
							"liveDistrictId", $("#liveCityIdHidden").val(), $("#liveDistrictIdHidden").val());
					areaselect.initCity("registerProvince",
							"customerRegisterCity", "customerRegisterArea",
							$("#customerRegisterCityHidden").val(), $("#customerRegisterAreaHidden").val());
					
					
					
					
					// 配偶工作单位

					loan.initCity('compProvince', 'compCity','compArer');
     				areaselect.initCity("compProvince", "compCity",
					 	"compArer", $("#compCityHidden").val(),$("#compArerHidden").val());
					// 信用资料格式化
					$('#creditNextBtn').bind('click',function(){
						 credit.format();
						 credit.required();
					 });
					// 增加信用资料
					$('#creditAddBtn').bind('click',function(){
						var tabLength=$('#loanCreditArea tr').length;
						launch.additionItem('loanCreditArea','_loanFlowCreditInfoItem',tabLength,'0');
					});
					// 公司地址
					loan.initCity("BcompProvince", "BcompCity","BcompArer");
					areaselect.initCity("BcompProvince", "BcompCity",
							 "BcompArer", $("#BcompCityHidden").val(),$("#BcompArerHidden").val());				
					// 开户省市
					loanCard.initCity("bankProvince", "bankCity",null);
					 $('#bankSigningPlatform').bind('change',function(){
							$('#signingPlatformName').val($("#bankSigningPlatform option:selected").text()); 
 					});
 					areaselectCard.initCity("bankProvince", "bankCity",null, $("#bankCityHidden").val(), null);
					// 借款产品期限	
					loan.initCity("storeProvice", "storeCity",null);
					loan.initProductRisk("productId", "monthId",createTime);
					loan.selectedProductRisk("productId", "monthId",$('#loanMonths').val(),createTime);
					// 申请信息管辖城市
					areaselect.initCity("storeProvice", "storeCity",
							  null, $("#storeCityHidden").val(), null);
					// 联系人格式化
					$('#contactNextBtn').bind('click',function(){
						 contactFormat.format();
						 var tabLength=$('#contactArea tr').length;
						 if(tabLength<4){
							 art.dialog.alert("联系人至少要3个");
							 return false;
						 }
					 });
					// 增加联系人
					$('#contactAdd').bind('click',function(){
						var tabLength=parseInt($('#contactIndex').val())+1;
						launch.additionItem('contactArea','_loanFlowContactItem',tabLength,'');
						$('#contactIndex').val(tabLength);
					});
					// 联系人关系类型联动				
					$("select[name$='relationType']").each(function(){
						var id = $(this).attr("id");
						var index = $(this).attr("index");
						var targetId = "contactRelation_"+index;
						var targetValueId = "contactRelationHidden_"+index;
						$(this).bind('change',function(){
							launch.getRelationDict($('#'+id+" option:selected").val(),targetId);
						});
						launch.initRelationDict($('#'+id+" option:selected").val(),targetId,$("#"+targetValueId).val());
						
					});
					// 房产信息格式化
	    			$('#houseNextBtn').bind('click', function() {
						house.format();						
					});
	    			// 增加房产信息
					$('#addHouseBtn').bind(
							'click',
							function() {
								var tabLength = parseInt($('#houseIndex').val())+1;
								launch.additionItem('loanHouseArea',
										'_loanFlowHouseItem', tabLength, '');
								$('#houseIndex').val(tabLength);
							});
					// 删除房产信息
	                $("input[name='delHouseItem']").each(function(){
	                	$(this).bind('click',function(){	                		
	                		house.delItem($(this).attr("currId"),"loanHouseArea",$(this).attr("dbId"),'HOUSE');
	                   	});	                	
	                });					
					// 房产地址联动
	                loan.batchInitCity('houseProvince', 'houseCity', 'houseArea');
	                areaselect.batchInitCity("houseProvince","houseCity","houseArea");
	    			// 增加共借人联系人
	    			$(":input[name='addCobContactBtn']").each(function(){
						$(this).bind('click',function(){
							var taObj = $(this).attr('parentIndex');
							var tabLengthStr=$('#table_'+taObj).attr("currIndex");
							var tabLength = parseInt(tabLengthStr)+1;
						  launch.additionItem($(this).attr('tableId'),'_loanFlowCoborrowerContactItem',taObj,tabLength,null);
						  $('#table_'+taObj).attr("currIndex",tabLength);
						});
					});
	    			// 共借人格式化
	    			$('#coborrowNextBtn').bind('click',function(){
	    				 coborrower.format();
	    			 }); 
	    			// 增加共借人
	    			$('#addCoborrowBtn').bind('click',function(){
	    				 var indexStr = $('#parentPanelIndex').val();
	    				 var index = parseInt(indexStr)+1;
	    				launch.additionItem('contactPanel','_loanFlowCoborrowerItem',index,'0');
	    				$('#parentPanelIndex').val(index);
	    			});
	    			// 共借人省市区联动
	    			loan.batchInitCity("coboHouseholdProvince", "coboHouseholdCity", "coboHouseholdArea");
	    			areaselect.batchInitCity("coboHouseholdProvince", "coboHouseholdCity", "coboHouseholdArea");
	    			
	    			
	    			loan.batchInitCity("coboLiveingProvince", "coboLiveingCity", "coboLiveingArea");
	    			areaselect.batchInitCity("coboLiveingProvince", "coboLiveingCity", "coboLiveingArea");
	    			
	    			
	    			loan.batchInitCity("coboCompProvince", "coboCompCity", "coboCompArer");
	    			areaselect.batchInitCity("coboCompProvince", "coboCompCity", "coboCompArer");
	    		
	    			// 联系人关系联动
	    		    $("select[name$='relationType']").each(function(){
	    		    	var id = $(this).attr("id");
	    		    	var index = $(this).attr("index");
    					var targetId = "contactRelation_"+index;
    					var targetValueId = "contactRelationHidden_"+index;
	    				$(this).bind('change',function(){
	    					launch.getRelationDict($('#'+id+" option:selected").val(),targetId);
	    				});
	    				launch.initRelationDict($('#'+id+" option:selected").val(),targetId,$("#"+targetValueId).val());
	    			});
	    			   								
	    			// 身份证信息点击长期后截止日期置灰
	    			$('#longTerm').bind('click',function() {
							if ($(this).attr('checked') == true || $(this).attr('checked') == 'checked') {
								$('#idEndDay').val('');
								$('#idEndDay').attr('disabled',true);
							} else {
								$('#idEndDay').attr('disabled',false);
							}							
					});
					// 银行卡信息格式化
					$('#launchFlow').bind('click',function(){
						launch.launchFlow('9','bankForm','flowParam');
					});
					// 银行卡信息开户行区域联动
					loan.initCity("bankProvince", "bankCity",null);
					$('#bankSigningPlatform').bind('change',function(){
						$('#signingPlatformName').val($("#bankSigningPlatform option:selected").text()); 
					});
					areaselect.initCity("bankProvince", "bankCity",null, $("#bankCityHidden").val(), null);					
				});
		function words_deal() { 
			var curLength=$("#remarks").val().length; 
			if(curLength>450) { 
				var num=$("#remarks").val().substr(0,450); 
				$("#remarks").val(num); 
			} 
		} 
	</script>
</head>
<body>
<!-- .housePledgeFlag -->
	<input type="hidden" value="${msg }" id="msg" />
	<input type="hidden" value="" id="createTime" />
	<c:set var="bview" value="${workItem.bv}" />
	 <c:if test="${bview.customerLoanHouseList!=null}">	
		 <input type="hidden" id="houseIndex" value="${fn:length(bview.customerLoanHouseList)}"/>
     </c:if>
     <c:if test="${bview.customerLoanHouseList==null}">	
         <input type="hidden" id="houseIndex" value="0"/>
     </c:if>
	<div class=" pt5 pr30 mb5">
	<!-- 共借人 /自然人保证人页签 -->
	   <input type="hidden" id="oneedition" value="${bview.oneedition}"/>
	
		<div class="tright">
			<c:if test="${bview.coborrowerNames == null || bview.coborrowerNames == ''}">
		       <c:if test="${bview.oneedition==-1}"> 
				<input type="button" id="coboEditBtn" class="btn btn-small" value="添加共借人" />
               </c:if>
            <c:if test="${bview.oneedition==1}"> 
				<input type="button" id="coboEditBtn" class="btn btn-small" value="添加自然人保证人" />
            </c:if>
				
				
			</c:if>
			<c:if test="${bview.loanCustomer.dictMarry == '1' && bview.loanMate.id==null}">
				<input type="button" id="mateAddBtn" class="btn btn-small" value="添加配偶" />
			</c:if>
			<c:if test="${fn:length(bview.loanCreditInfoList) == 1 }">
				<c:if test="${bview.loanCreditInfoList[0].creditMortgageGoods == '' || bview.loanCreditInfoList[0].creditMortgageGoods == null}">
					<input type="button" id="creditEditBtn" class="btn btn-small" value="添加信用资料" />
				</c:if>
			</c:if>
			<c:if test="${bview.customerLoanCompany.compName == null || bview.customerLoanCompany.compName == ''}">	
			<%-- 	<c:if test="${bview.preResponse == 'TO_RULEENGIN' }">	 --%>					
					<input type="button" id="companyEditBtn" class="btn btn-small" value="添加职业信息/公司资料" />															
			<%-- 	</c:if> --%>
			</c:if>
			<c:if test="${bview.customerLoanHouseList[0].id == null || bview.customerLoanHouseList[0].id == ''}">
				<input type="button" class="btn btn-small" id="houseEditBtn" value="添加房产资料" />
			</c:if>
			<input type="hidden" id="imageUrl" value="${bview.imageUrl }">		
			<input type="button" class="btn btn-small" id="storereviews" value="客户影像资料">
			<button class="btn btn-small" id="${bview.applyId}"  onclick="showLoanHis(this.id)">历史</button>
			<%-- <c:if test="${bview.isStoreAssistant=='1'}"> --%>
			 	 <button class="btn btn-small" id="refuse">门店拒绝</button>
				 <button class="btn btn-small" id="giveUp">客户放弃</button>
		   <%--  </c:if> --%>
		    <c:if test="${bview.preResponse == 'STORE_TO_CHECK' || bview.preResponse == 'STORE_BACK_CHECK' }">
		        <button class="btn btn-small" loanCode="${workItem.bv.loanCustomer.loanCode}" id="backDetailBtn">汇诚退回明细</button>
		    </c:if>
			<button class="btn btn-small" id="backBtn">返回</button> 
		</div>
		<!-- 门店拒绝弹出框 -->
		<div id="refuseMod" style="display:none">
			<form method="post" action="">
				<table class="table1" cellpadding="0" cellspacing="0" border="0"
					width="100%">
					<tr valign="top">
						<td><label >拒绝理由：</label>
							<textarea id="rejectReason" maxlength="450" cols="55" class="textarea_refuse" ></textarea>
						</td>
					</tr>
				</table>		
			</form>
		</div>
	</div>
	            <h2 class="f14 pl10">客户信息					
						<input type="button" class="btn btn-small r mr10" id="customerEditBtn" value="编辑"/>				
				</h2>
				<div class="box2">
				<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">客户姓名：</label>${bview.loanCustomer.customerName}
					
				
					
					</td>	
					<td></td>
					<td></td>					
				</tr>
				<tr>
					<td><label class="lab">客户编号：</label>${bview.loanCustomer.customerCode}</td>				
					<td><label class="lab">性别：</label>${bview.loanCustomer.customerSexLabel}</td>
					<td>
						<label class="lab">婚姻状况：</label>
						<span id="dictMarrySpan">${bview.loanCustomer.dictMarryLabel}</span>
					</td>
				</tr>
				<tr>					
					<td><label class="lab">证件类型：</label>${bview.loanCustomer.dictCertTypeLabel}</td>
					<td><label class="lab">证件号码：</label>${bview.loanCustomer.customerCertNum}</td>
					<td>
						<label class="lab">证件有效期：</label>
						<c:if test="${bview.loanCustomer.idEndDay == null}">
							长期
						</c:if>
						<c:if test="${bview.loanCustomer.idStartDay !=null && bview.loanCustomer.idEndDay != null}">
							<fmt:formatDate value="${bview.loanCustomer.idStartDay}" pattern="yyyy-MM-dd"/>至
							<fmt:formatDate value="${bview.loanCustomer.idEndDay}" pattern="yyyy-MM-dd"/>					
						</c:if>
					</td>
				</tr>								
				<tr>
					<td><label class="lab">学历：</label>${bview.loanCustomer.dictEducationLabel}</td>
					<td><label class="lab">电话：</label>${bview.loanCustomer.customerTel}</td>
					<td><label class="lab">手机：</label>${bview.loanCustomer.customerPhoneFirst}&nbsp;&nbsp;${bview.loanCustomer.customerPhoneSecond}</td>
				</tr>
			</table>
		</div>	
	
	<sys:message content="${message}"/>	
		<c:if test="${bview.loanCustomer.dictMarry == '1' && bview.loanMate!=null && bview.loanMate.id!=null}">
			<h2 class="f14 pl10 mt20">配偶资料										
				<input type="button" class="btn btn-small r mr10" id="mateEditBtn" value="编辑"/>				
			</h2>
			<div class="box2">
				<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td><label class="lab">姓名：</label>${bview.loanMate.mateName}</td>					
						<td><label class="lab">手机：</label>${bview.loanMate.mateTel}</td>	
						<td><label class="lab">证件号码：</label>${bview.loanMate.mateCertNum}</td>
					</tr>
					<tr>
						<td><label class="lab">单位名称：</label>${bview.loanMate.mateLoanCompany.compName}</td>
						<td><label class="lab">单位电话：</label>${bview.loanMate.mateLoanCompany.compTel}</td>
						<td><label class="lab">单位地址：</label>
						${bview.loanMate.mateLoanCompany.comProvinceName}
						${bview.loanMate.mateLoanCompany.comCityName}
						${bview.loanMate.mateLoanCompany.comArerName}
						${bview.loanMate.mateLoanCompany.compAddress}</td>
					</tr>
				</table>			
			</div>						
		</c:if>	
		<h2 class="f14 pl10 mt20">信借申请  
		    	<input type="button" class="btn btn-small r mr10" id="applyEditBtn" value="编辑" />					
		</h2>
		<div class="box2">
			<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">产品类型：</label>${bview.productName}</td>
					<td><label class="lab">申请额度(元)：</label><fmt:formatNumber value="${bview.loanInfo.loanApplyAmount == null ? 0 : bview.loanInfo.loanApplyAmount }" pattern="#,##0.00" /></td>
					<td><label class="lab">借款期限(月)：</label>${bview.loanInfo.loanMonths}</td>
				</tr>
				<tr>
					<td><label class="lab">申请日期：</label><fmt:formatDate value="${bview.loanInfo.loanApplyTime}" pattern="yyyy-MM-dd"/></td>
					<td><label class="lab">借款用途：</label>${bview.loanInfo.dictLoanUserLabel}</td>
					<td><label class="lab">具体用途：</label>${bview.loanInfo.realyUse}</td>
				</tr>
				<tr>					
					<td><label class="lab">是否加急：</label>${bview.loanInfo.loanUrgentFlagLabel}</td>
					<td><label class="lab">录入人：</label>${bview.loanInfo.loanCustomerServiceName}</td>
					<td><label class="lab">客户经理：</label>${bview.loanInfo.loanManagerName}</td>
				</tr>				
				<tr>					
					<td><label class="lab">管辖城市：</label>${bview.loanInfo.storeProviceName}${bview.loanInfo.storeCityName}</td>
				</tr>
				<tr>
					<td colspan="3">
						<label class="lab">备注：</label>													
						<textarea readonly="readonly" rows="" cols="" name="loanRemark.remark" class="textarea_refuse">${bview.loanRemark.remark}</textarea>				 													
					</td>
				</tr>
			</table>
		</div>			
		<c:if test="${bview.coborrowerNames != null && bview.coborrowerNames != ''}">
		
		<c:if test="${workItem.bv.oneedition==-1}"> 
				<h4 class="f14 pl10 mt10">共同借款人 <input type="button" id="coboEditBtn" class="btn btn-small r mr10" value="编辑" /></h4>
        </c:if>
        <c:if test="${workItem.bv.oneedition==1}"> 
				<h4 class="f14 pl10 mt10">自然人保证人<input type="button" id="coboEditBtn" class="btn btn-small r mr10" value="编辑" /></h4>
        </c:if>
																							
			<table class="table table-hover table-bordered table-condensed">
				<thead>
					<tr>
						<th>序号</th>
						<th>姓名</th>
						<th>身份证号</th>
						<th>手机号</th>													
					</tr>
				</thead>
				<tbody>
					<c:forEach var="coboList" items="${bview.loanCoborrower }" varStatus="stu">
						<tr>
							<td>${stu.count }</td>
							<td>${coboList.coboName }</td>
							<td>${coboList.coboCertNum }</td>
							<td>${coboList.coboMobile }</td>												
						</tr>								
					</c:forEach>
					<c:if test="${bview.loanCoborrower==null || fn:length(bview.loanCoborrower)==0}">
						<tr>
							<td colspan="4" style="text-align: center;">没有符合条件的数据</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</c:if>	
	<c:if test="${fn:length(bview.loanCreditInfoList) != 1 || (bview.loanCreditInfoList[0].creditMortgageGoods != '' && bview.loanCreditInfoList[0].creditMortgageGoods != null)}">				
   		<h4 class="f14 pl10 mt10">信用资料 <input type="button" id="creditEditBtn" class="btn btn-small r mr10" value="编辑" /></h4>
			<table class="table table-hover table-bordered table-condensed">
				<thead>
					<tr>
						<th>序号</th>
						<th>抵押类型</th>
						<th>抵押物品</th>
						<th>机构名称</th>					
						<th>贷款额度(¥)</th>
						<th>每月供额度(¥)</th>
						<th>贷款余额(¥)</th>						
					</tr>
				</thead>
				<tbody>
					<c:forEach var="credit" items="${bview.loanCreditInfoList }" varStatus="str">
						<tr>
							<td>${str.count }</td>
							<td>${credit.dictMortgageTypeLabel}</td>
							<td>${credit.creditMortgageGoods }</td>
							<td>${credit.orgCode }</td>
							<td><fmt:formatNumber value="${credit.creditLoanLimit == null ? 0 : credit.creditLoanLimit }" pattern="#,##0.00" /></td>
							<td><fmt:formatNumber value="${credit.creditMonthsAmount == null ? 0 : credit.creditMonthsAmount }" pattern="#,##0.00" /></td>
							<td><fmt:formatNumber value="${credit.creditLoanBlance == null ? 0 : credit.creditLoanBlance }" pattern="#,##0.00" /></td>							
						</tr>								
					</c:forEach>
					<c:if test="${bview.loanCreditInfoList==null || fn:length(bview.loanCreditInfoList)==0}">
						<tr>
							<td colspan="7" style="text-align: center;">没有符合条件的数据</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</c:if>	
	<c:if test="${bview.customerLoanCompany.compName != null && bview.customerLoanCompany.compName != ''}">	
		<h2 class="f14 pl10 mt20">职业信息/公司资料 
	       	<input type="button" id="companyEditBtn" class="btn btn-small r mr10" value="编辑" />															
		</h2>
		<div class="box2">
			<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">名称：</label>${bview.customerLoanCompany.compName }</td>
					<td>
						<label class="lab">地址：</label>
						${bview.customerLoanCompany.comProvinceName }
						${bview.customerLoanCompany.comCityName }
						${bview.customerLoanCompany.comArerName }
						${bview.customerLoanCompany.compAddress }
					</td>					
					<td><label class="lab">职务：</label>${bview.customerLoanCompany.compPostLabel}</td>		
				</tr>
				<tr>
					<td><label class="lab">入职时间：</label><fmt:formatDate value="${bview.customerLoanCompany.compEntryDay }" pattern="yyyy-MM-dd"/></td>
					<td><label class="lab">单位类型：</label>${bview.customerLoanCompany.dictCompTypeLabel}</td>
					<td><label class="lab">单位电话：</label>${bview.customerLoanCompany.compTel }</td>				
				</tr>
				<tr>
					<td><label class="lab">月收入(元)：</label><fmt:formatNumber value="${bview.customerLoanCompany.compSalary == null ? 0 : bview.customerLoanCompany.compSalary}" pattern="#,##0.00" /></td>
					<td><label class="lab">每月支薪日：</label>${bview.customerLoanCompany.compSalaryDay }</td>
					<td><label class="lab">累积工作年限：</label>${bview.customerLoanCompany.compWorkExperience }</td>															
				</tr>				
				
				<%-- <tr>
					<td><label class="lab">法人姓名：</label>${bview.customerLoanCompany.comLegalMan}</td>
					<td><label class="lab">法人身份证号：</label>${bview.customerLoanCompany.comLegalManNum }</td>
					<td><label class="lab">法人手机号：</label>${bview.customerLoanCompany.comLegalManMoblie }</td>															
				</tr>		
				<tr>
					<td><label class="lab">企业邮箱：</label>${bview.customerLoanCompany.comEmail }</td>
				</tr> --%>
				
			</table>
		</div>
	</c:if>	
	<c:if test="${bview.customerLoanHouseList[0].id != null && bview.customerLoanHouseList[0].id != ''}">	
		<h4 class="f14 pl10 mt10">房产资料 <input type="button" class="btn btn-small r mr10" id="houseEditBtn" value="编辑" /></h4>
			<table class="table table-hover table-bordered table-condensed">
				<thead >
					<tr>
						<th>序号</th>
						<th>房产具体信息</th>					
						<th>建筑年份</th>								
						<th>购买方式</th>
						<th>购买日期</th>
						<th>房屋面积</th>	
						<th>是否抵押</th>								
					</tr>
				</thead>
				<tbody>
					<c:forEach var="hose" items="${bview.customerLoanHouseList }" varStatus="stu">
						<tr>
							<td>${stu.count }</td>
							<td>${hose.houseProvinceName}${hose.houseCityName}${hose.houseAreaName}${hose.houseAddress}</td>
							<td><fmt:formatDate value="${hose.houseCreateDay }" pattern="yyyy-MM-dd"/></td>
							<td>${hose.houseBuywayLabel}</td>
							<td><fmt:formatDate value="${hose.houseBuyday }" pattern="yyyy-MM-dd"/></td>
							<td><fmt:formatNumber value="${hose.houseBuilingArea}" pattern="#,##0.00" ></fmt:formatNumber></td>
							<td>${hose.housePledgeFlagLabel}</td>
						</tr>
					</c:forEach>
				</tbody>							
			</table>
		</c:if>	
		<h4 class="f14 pl10 mt10">联系人资料 <input type="button" class="btn btn-small r mr10" id="contactEditBtn" value="编辑" /></h4>
			<table class="table table-hover table-bordered table-condensed">
				<thead>
					<tr>
						<th>序号</th>
						<th><span class="red">*</span>姓名</th>
						<th><span class="red">*</span>关系类型</th>
						<th><span class="red">*</span>和本人关系</th>
						<th>工作单位</th>
						<th>家庭或工作单位详细地址</th>
						<th>单位电话</th>
						<th><span class="red">*</span>手机号</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="cust" items="${bview.customerContactList }" varStatus="str">
						<tr>
							<td>${str.count}</td>
							<td>${cust.contactName }</td>
							<td>${cust.relationTypeLabel}</td>
							<td>
								${cust.contactRelationLabel}
							</td>
							<td>${cust.contactSex }</td>
							<td>${cust.contactNowAddress }</td>
							<td>${cust.contactUnitTel }</td>
							<td>${cust.contactMobile }</td>
						</tr>								
					</c:forEach>
					<c:if test="${bview.customerContactList==null || fn:length(bview.customerContactList)==0}">
						<tr>
							<td colspan="7" style="text-align: center;">没有符合条件的数据</td>
						</tr>
					</c:if>
				</tbody>
			</table>
	<h2 class="f14 pl10 mt20">银行卡资料					
		<input type="button" class="btn btn-small r mr10" id="bankEditBtn" value="编辑" />											
	</h2>
	<div class="box2">
			<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">银行卡/折 户名：</label>${bview.loanBank.bankAccountName}
					    <c:if test="${applyInfoEx.loanBank.bankIsRareword==1}">
					     <input type="checkbox" name="loanBank.bankIsRareword" id="bankIsRareword" value="1"
				   	  			  checked = true disabled="disabled"
				  		 />是否生僻字
				  		 </c:if>
					</td>
					<td><label class="lab">授权人：</label>${bview.loanBank.bankAuthorizer}</td>
					<td><label class="lab">签约平台：</label>${bview.loanBank.bankSigningPlatformName}</td>					
				</tr>
				<tr>
					<td><label class="lab">开户行名称：</label>${bview.loanBank.bankNameLabel}</td>
					<td><label class="lab">开户支行：</label>${bview.loanBank.bankBranch}</td>
					<td><label class="lab">开户省市：</label>${bview.loanBank.bankProvinceName}${bview.loanBank.bankCityName}</td>					
				</tr>
				<tr>
					<td><label class="lab">银行卡号：</label>${bview.loanBank.bankAccount}</td>
					<td></td>				
				</tr>
			</table>
		</div>
	<!-- 流程节点提交表单 -->
	<div class="  pt10 pr30 mb30">	
		<div class="r">
			<form id="subForm" method="post" >
		    	<input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
		      	<input type="hidden" value="${workItem.flowName}" name="flowName"></input>
		      	<input type="hidden" value="${workItem.stepName}" name="stepName"></input>
		      	<input type="hidden" value="${workItem.token}" id="token" name="token"></input>
		      	<input type="hidden" value="${workItem.flowId}" name="flowId"></input>
		      	<input type="hidden" value="${workItem.wobNum}" id="wobNum" name="wobNum"></input>
		      	<input type="hidden" value="${bview.applyId}" id="applyId" name="applyId"></input>
		      	<input type="hidden"  name="rejectReason" id="remark"></input>
		      	<input type="hidden" value="${bview.loanCustomer.loanCode }" name="loanCustomer.loanCode"></input>
		      	<input type="hidden" value="${bview.loanInfo.loanApplyAmount }" name="loanInfo.loanApplyAmount">
		      	<input type="hidden" value="${bview.coborrowerNames }" name="coborrowerNames"></input>
		      	<input type="hidden" value="${bview.loanCustomer.customerName}" name="customerName"></input>
		      	<input type="hidden" name="queue" value="HJ_CUSTOMER_AGENT"/>
      	 		<input type="hidden" name="viewName" value="loanflow_borrowlist_workItems"/>
      	 		<input type="hidden" value="${bview.preResponse}" name="response" id="response"/>
      	 		<input type="hidden" value="${bview.lastLoanStatus}" name="lastLoanStatus"/>
				<input type="button" class="btn btn-primary" id="subBtn" value="提交" />
    		 </form>
		</div>	
	</div>
	<!-- 编辑信息提交数据 -->
  	<form id="custInfoForm" target="fraBox">
		<input type="hidden" value="${workItem.bv.loanCustomer.loanCode}" name="loanCode" id="loanCode" /> 
		<input type="hidden" value="${workItem.bv.loanCustomer.customerCode}" name="customerCode" id="customerCode" /> 
		<input type="hidden" value="${workItem.bv.consultId}" name="consultId" id="consultId"/>
		<input type="hidden" name="flag" id="flag" /> 
		<input type="hidden" name="viewName" id="viewName" />
	</form>
	
	<!-- 客户资料编辑 -->
	<div id="customerMod"  style="width:100%;height:100%;overflow:auto;display:none">		
	  			<form id="customerForm" action="${ctx }/apply/storeReviewController/storeReviewUpdate?preResponse=${bview.preResponse}" method="post">
	  			<input type="hidden" name="loanCustomer.id" value="${bview.loanCustomer.id }">
	  			<input type="hidden" name="customerLivings.id" value="${bview.customerLivings.id }">
	  			<input type="hidden" name="customerLivings.loanCode" value="${bview.loanCustomer.loanCode }">
	  			<input type="hidden" name="applyId" value="${bview.applyId }">
	  			<input type="hidden" name="wobNum" value="${workItem.wobNum }">
	  			<input type="hidden" name="token" value="${workItem.token }">
	  			<input type="hidden" name="stepName" value="${workItem.stepName }">
	  			<input type="hidden" name="flowName" value="${workItem.flowName }">
	  			<input type="hidden" name="method" value="customer">
	  			<input type="hidden" name="loanCustomer.loanCode" value="${bview.loanCustomer.loanCode}"/>
	  			<input type="hidden" id="sendEmailFlag" value="${bview.loanCustomer.customerEmail}"/>
	  			<input type="hidden" id="oldEmailFlag" value="${bview.loanCustomer.customerEmail}"/>
	  			<input type="hidden" id="bvCustomerCode" value="${bview.loanCustomer.customerCode}"/>
	  			<input type="hidden" id="cuCustomerName1" value="${bview.loanCustomer.customerName}"/>
	  			<table class="table1" cellpadding="0" cellspacing="0" border="0"
					width="100%">
					<tr>	
						<td><label class="lab"><span style="color: red">*</span>客户姓名：</label>
							<input type="text" name="loanCustomer.customerName" 
							value="${bview.loanCustomer.customerName}" class="input_text178 required stringCheck"
							<c:if test="${bview.preResponse != 'STORE_TO_CHECK' && bview.preResponse != 'STORE_BACK_CHECK' }">
							   readonly ="readonly" 
							</c:if>  
							  /></td>
						<td><label class="lab"><span style="color: red">*</span>性别：</label>
							<c:choose>
								<c:when test="${bview.loanCustomer.customerSex!=null}">
								  <span>
									<c:forEach items="${fns:getDictList('jk_sex')}" var="item">
										<input type="radio" class="required"
											name="loanCustomer.customerSex" value="${item.value}"
											<c:if test="${bview.loanCustomer.customerSex==item.value}">
	                                  checked
	                                </c:if>>
	                           ${item.label}
	                         </input>
									</c:forEach>
									</span>
								</c:when>
								<c:otherwise>
								  <span>
									<c:forEach items="${fns:getDictList('jk_sex')}" var="item">
										<input type="radio" class="required"
											name="loanCustomer.customerSex" value="${item.value}"
											<c:if test="${bview.customerSex==item.value}">
	                                  checked
	                                </c:if>>
	                             ${item.label}
	                           </input>
									</c:forEach>
									</span>
								</c:otherwise>
							</c:choose></td> <!--  -->
						<td><label class="lab"><span style="color: red">*</span>婚姻状况：</label>
							<select id="dictMarry" name="loanCustomer.dictMarry"
							value="${bview.loanCustomer.dictMarry}" class="select180 required">
								<option value="">请选择</option>
								<c:forEach items="${fns:getDictList('jk_marriage')}" var="item">
								  <c:if test="${item.label!='空'}">
									<option value="${item.value}"
										<c:if test="${bview.loanCustomer.dictMarry==item.value}">
						    		 	 selected = true
						   				</c:if>>${item.label}
						   			</option>
						   		  </c:if>
								</c:forEach>
						   </select>
						  <input type="hidden" name="loanCustomer.oldDictMarry" value="${bview.loanCustomer.dictMarry}"/>
						</td>
					</tr>
					<tr>
					  <td><label class="lab"><span style="color: red">*</span>手机号码：</label>
							<c:choose>
								<c:when test="${bview.loanCustomer.customerPhoneFirst!=null}">
									<input type="text" name="loanCustomer.customerPhoneFirst"
										value="${bview.loanCustomer.customerPhoneFirst}"
										<c:if test="${bview.preResponse != 'STORE_TO_CHECK' && bview.preResponse != 'STORE_BACK_CHECK' }">
										readonly="readonly"
										</c:if>
										 class="input_text178 required" />
								</c:when>
								<c:otherwise>
									<input type="text" name="loanCustomer.customerPhoneFirst"
										value="${bview.customerMobilePhone}"
										<c:if test="${bview.preResponse != 'STORE_TO_CHECK' && bview.preResponse != 'STORE_BACK_CHECK' }">
										  readonly="readonly"
										 </c:if>
										class="input_text178 required" />
					    		</c:otherwise>
							</c:choose></td>
						<td><label class="lab">手机号码2：</label> <input
							name="loanCustomer.customerPhoneSecond" type="text"
							value="${bview.loanCustomer.customerPhoneSecond}"
							class="input_text178" readonly ="readonly"/></td>
	                   <td>
				    	<label class="lab">固定电话：</label> <c:choose>
								<c:when test="${bview.loanCustomer.customerTel!=null}">
									<input name="loanCustomer.customerTel" type="text"
										value='${bview.loanCustomer.customerTel}' class="input_text178 isTel" />
								</c:when>
								<c:when test="${bview.areaNo!=null && bview.areaNo!='' && bview.telephoneNo!=null && bview.telephoneNo!=''}">
									<input name="loanCustomer.customerTel" type="text"
										value='${bview.areaNo}-${bview.telephoneNo}' class="input_text178 isTel" />
								</c:when>
								<c:otherwise>
								    <input name="loanCustomer.customerTel" type="text" class="input_text178 isTel"/>
								</c:otherwise>
							</c:choose>
					
					    </td>
					</tr>
	
					<tr>
						<td><label class="lab"><span style="color: red">*</span>证件类型：</label>
							<select id="cardType" name="loanCustomer.dictCertType"
							value="${bview.loanCustomer.dictCertType}" class="select180 required"
							disabled="disabled">
								<option value="">请选择</option>
								<c:forEach items="${fns:getDictList('jk_certificate_type')}"
									var="item">
									<option value="${item.value}"
										<c:if test="${bview.loanCustomer.dictCertType==item.value}">
						      selected = true
						     </c:if>>${item.label}</option>
								</c:forEach>
						</select> <input type="hidden" name="loanCustomer.dictCertType"
							value="${bview.loanCustomer.dictCertType}" /></td>
						<td><label class="lab"><span style="color: red">*</span>证件号码：</label>
							<c:choose>
								<c:when test="${bview.loanCustomer.customerPhoneFirst!=null}">
									<input name="loanCustomer.customerCertNum" disabled="disabled"
										id="customerCertNum" type="text"
										value="${bview.loanCustomer.customerCertNum}"
										class="input_text178 required" />
									<input name="loanCustomer.customerCertNum" type="hidden"
										value="${bview.loanCustomer.customerCertNum}" />
								</c:when>
								<c:otherwise>
									<input name="loanCustomer.customerCertNum" id="customerCertNum"
										disabled="disabled" type="text" value="${bview.mateCertNum}"
										class="input_text178 required" />
									<input name="loanCustomer.customerCertNum" type="hidden"
										value="${bview.mateCertNum}" />
								</c:otherwise>
							</c:choose> <span id="blackTip" style="color: red;"></span></td>
						<td><label class="lab"><span style="color: red">*</span>证件有效期：</label>
							<input id="idStartDay" name="loanCustomer.idStartDay"
							value="<fmt:formatDate value='${bview.loanCustomer.idStartDay}' pattern="yyyy-MM-dd"/>"
							type="text" class="Wdate input_text70 required" size="10"
							onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'idEndDay\')}'})"
							style="cursor: pointer" />-<input id="idEndDay"
							name="loanCustomer.idEndDay"
							value="<fmt:formatDate value='${bview.loanCustomer.idEndDay}' pattern="yyyy-MM-dd"/>"
							type="text" class="Wdate input_text70" size="10"
							<c:if test="${bview.loanCustomer.idEndDay==null}"> 
							  disabled=true
							</c:if>
							onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'idStartDay\')}'})"
							style="cursor: pointer" /> <input type="checkbox" id="longTerm"
							name="longTerm" value="1"
							<c:if test="${bview.loanCustomer.idEndDay==null}"> 
							     checked=true 
							</c:if>  />
							长期</td>
					</tr>
			
					<tr>
						<td><label class="lab"><span style="color: red">*</span>学历：</label>
							<select id="dictEducation" name="loanCustomer.dictEducation"
							value="${bview.loanCustomer.dictEducation}"
							class="select2-container select180 required">
								<option value="">请选择</option>
								<c:forEach items="${fns:getDictList('jk_degree')}" var="curEdu">
									<option value="${curEdu.value}"
										<c:if test="${bview.loanCustomer.dictEducation==curEdu.value}">
						      selected = true
						     </c:if>>${curEdu.label}</option>
								</c:forEach>
						</select></td>
						<td><label class="lab"><span style="color: red">*</span>住房性质：</label>
							<select name="customerLivings.customerHouseHoldProperty"
							value="${bview.customerLivings.customerHouseHoldProperty}"
							class="select180 required">
								<option value="">请选择</option>
								<c:forEach items="${fns:getDictList('jk_house_nature')}"
									var="item">
									<option value="${item.value}"
										<c:if test="${item.value==bview.customerLivings.customerHouseHoldProperty}">
						      selected=true 
						    </c:if>>${item.label}</option>
								</c:forEach>
						</select></td>
						<td><label class="lab">家人是否知悉：</label> <c:forEach
								items="${fns:getDictList('jk_yes_or_no')}" var="item">
								<input type="radio" name="loanCustomer.contactIsKnow"
									value="${item.value}"
									<c:if test="${item.value==bview.loanCustomer.contactIsKnow}">
	                     checked='true'
	                  	</c:if> />${item.label}
	               	 </c:forEach></td>
					</tr>
					<tr>
	
						<td><label class="lab">有无子女：</label> <c:forEach
								items="${fns:getDictList('jk_have_or_nohave')}" var="item">
								<input type="radio" name="loanCustomer.customerHaveChildren"
									value="${item.value}"
									<c:if test="${item.value==bview.loanCustomer.customerHaveChildren}">
	                     checked='true'
	                  </c:if> />${item.label}
	                </c:forEach></td>
	                <td><label class="lab">毕业日期：</label> <input
							id="customerGraduationDay"
							name="loanCustomer.customerGraduationDay"
							value="<fmt:formatDate value='${bview.loanCustomer.customerGraduationDay}' pattern="yyyy-MM-dd"/>"
							type="text" class="Wdate input_text178" size="10"
							onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" />
						</td>
						<td><label class="lab">初到本市时间：</label>
							<input id="firstArriveYear"
							name="customerLivings.customerFirtArriveYear"
							value="${bview.customerLivings.customerFirtArriveYear}" type="text"
							class="Wdate input_text178" size="10"
							onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" />
						</td>
	
					</tr>
					<tr>
	
						<td><label class="lab">起始居住时间：</label> <input
							id="customerFirtLivingDay"
							name="customerLivings.customerFirtLivingDay"
							value="<fmt:formatDate value='${bview.customerLivings.customerFirtLivingDay}' pattern="yyyy-MM-dd"/>"
							type="text" class="Wdate input_text178" size="10"
							onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" />
						</td>
						<td><label class="lab"><span style="color: red">*</span>客户类型：</label>
							<select id="cardType" name="loanCustomer.dictCustomerDiff"
							class="select180 required" 
							<c:if test="${bview.preResponse == 'STORE_TO_CHECK' || bview.preResponse == 'STORE_BACK_CHECK' }">
							  disabled = "disabled" 
							</c:if> 
							value="${bview.loanCustomer.dictCustomerDiff}">
								<option value="">请选择</option>
								<c:forEach items="${fns:getDictList('jk_customer_diff')}"
									var="item">
									<option value="${item.value}"
										<c:if test="${bview.loanCustomer.dictCustomerDiff==item.value}">
						       selected=true 
						    </c:if>>${item.label}</option>
								</c:forEach>
						</select></td>
						<td>
						<label class="lab" >由何处得知我公司：</label> <select
							id="cardType" class="select180"
							name="loanCustomer.dictCustomerSource"
							value="${bview.loanCustomer.dictCustomerSource}">
								<option value="">请选择</option>
								<c:forEach items="${fns:getDictList('jk_cm_src')}" var="item">
									<option value="${item.value}"
										<c:if test="${item.value==bview.loanCustomer.dictCustomerSource}">
	                        selected=true 
	                         </c:if>>${item.label}</option>
								</c:forEach>
						</select>						
						</td>
					</tr>
					<tr>
						<td><label class="lab"><span style="color: red">*</span>电子邮箱：</label> <input
							name="loanCustomer.customerEmail" type="text"
							value="${bview.loanCustomer.customerEmail}" class="input_text178 required" />
							<input type="button" value="发送邮箱验证" onclick="sendtoemail(this)" id="emailBtn"/>
						<input name = "emailvalidator" id="emailvalidator" width="30px" style= "background-color:transparent;border:none;"></input>
						</td>
						<td><%-- <label class="lab">暂住证:</label> <!--暂住证没有  --> <c:forEach
								items="${fns:getDictList('sex')}" var="item">
								<input type="radio"
									name="customerLivings.customertmpResidentialPermit"
									 <c:if test="${item.value==bview.customerLivings.customertmpResidentialPermit}"> checked='true' </c:if>
	                      value="${item.value}" />${item.label}
	                      </c:forEach> --%></td>
	                      <td>
					      </td>
					</tr>
					<tr>
						<td colspan="3"><label class="lab"><span style="color:red">*</span>现住址：</label>
							<select class="select78 required" id="liveProvinceId"
							name="loanCustomer.customerLiveProvince">
								<option value="">请选择</option>
								<c:forEach var="item" items="${bview.provinceList}"
									varStatus="status">
									<option value="${item.areaCode}"
										<c:if test="${bview.loanCustomer.customerLiveProvince==item.areaCode}">
			                    selected = true 
			                 </c:if>>${item.areaName}</option>
								</c:forEach>
						</select>-<select class="select78 required" id="liveCityId"
							name="loanCustomer.customerLiveCity"
							value="${bview.loanCustomer.customerLiveCity}">
								<option value="">请选择</option>
						</select>-<select id="liveDistrictId" class="select78 required"
							name="loanCustomer.customerLiveArea"
							value="${bview.loanCustomer.customerLiveArea}">
								<option value="">请选择</option>
						</select>
						<input type="hidden" id="liveCityIdHidden" value="${bview.loanCustomer.customerLiveCity}"/>
						<input type="hidden" id="liveDistrictIdHidden" value="${bview.loanCustomer.customerLiveArea}"/>
						
						<input type="text"
							name="loanCustomer.customerAddress"
							value="${bview.loanCustomer.customerAddress}" style="width:250px"
							class="required" /></td>
					</tr>
					<tr>
						<td colspan="3"><label class="lab"><span style="color: red">*</span>户籍地址：</label>
							<select id="registerProvince"
							name="loanCustomer.customerRegisterProvince" class="select78 required">
								<option value="">请选择</option>
								<c:forEach var="item" items="${bview.provinceList}"
									varStatus="status">
									<option value="${item.areaCode}"
										<c:if test="${bview.loanCustomer.customerRegisterProvince==item.areaCode}">
								selected = true 
			                 </c:if>>
										${item.areaName}</option>
								</c:forEach>
						</select>-<select id="customerRegisterCity"
							name="loanCustomer.customerRegisterCity" class="select78 required"
							value="${bview.loanCustomer.customerRegisterCity}">
								<option value="">请选择</option>
						</select>-<select id="customerRegisterArea"
							name="loanCustomer.customerRegisterArea" class="select78 required
							"
							value="${bview.loanCustomer.customerRegisterArea}">
								<option value="">请选择</option>
						</select>
						<input type="hidden" id="customerRegisterCityHidden" value="${bview.loanCustomer.customerRegisterCity}"/>
						<input type="hidden" id="customerRegisterAreaHidden" value="${bview.loanCustomer.customerRegisterArea}"/>
						<input type="text"
							name="loanCustomer.customerRegisterAddress" class="required"
							style="width:250px"
							value="${bview.loanCustomer.customerRegisterAddress}" /></td>
					</tr>
				</table>
				<div class="tright mr10 mb5">
					<input type="submit" class="btn btn-primary" id="customerNextBtn" value="保存">					
				</div>
	  		</form>	 
	</div>
	
	<!-- 配偶资料编辑模态层 -->
	<div id="mateMod"  style="overflow:auto;display:none">					
	  			<form id="mateForm" method="post" style="width:900px;" action="${ctx}/apply/storeReviewController/storeReviewUpdate?preResponse=${bview.preResponse}">
	  				<input type="hidden" name="loanMate.id" value="${bview.loanMate.id }">
	  				<input type="hidden" name="loanMate.loanCode" value="${bview.loanCustomer.loanCode }">	
	  				<input type="hidden" name="applyId" value="${bview.applyId }">
	  				<input type="hidden" name="wobNum" value="${workItem.wobNum }">
	  				<input type="hidden" name="token" value="${workItem.token }">
	  				<input type="hidden" name="stepName" value="${workItem.stepName }">
	  				<input type="hidden" name="flowName" value="${workItem.flowName }">
	  				<input type="hidden" name="method" value="mate">
	  				<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
			           <tbody id="loanMateTbody"> 
			            <tr>
			              <td  >
			                 <label class="lab"><span class="red">*</span>姓名：</label>
			                 <input name="loanMate.mateName" value="${bview.loanMate.mateName}" type="text" maxlength="50" class="input_text178 required stringCheck"/>
			              </td>
			              <td>
			              <label class="lab"><span class="red">*</span>证件类型：</label>
			                 <select id="mateCardType" name="loanMate.dictCertType" value="${bview.loanMate.dictCertType}" class="select180 required">
			                     <option value="">请选择</option>
			                     <c:forEach items="${fns:getDictList('jk_certificate_type')}" var="item">
			                      
								  <option value="${item.value}"
								   <c:if test="${bview.loanMate.dictCertType==item.value}"> 
							            selected=true 	   
								   </c:if>
								  >${item.label}</option>
							     </c:forEach>
							</select>
			              </td>
			              <td>
			                 <label class="lab" ><span class="red">*</span>证件号码：</label>
			                 <input type="text" value="${bview.loanMate.mateCertNum}" name="loanMate.mateCertNum" class="input_text178 required mateNumCheck" />
			              </td>
			            </tr>
			            <tr>
			              <td>
			                  <label class="lab"><span class="red">*</span>手机：</label>
			                  <input type="text" name="loanMate.mateTel" value="${bview.loanMate.mateTel}" class="input_text178 required isMobile mateMobileDiff"  />
			              </td>
			              <td>
			                 <label class="lab">单位名称：</label>
			                 <input name="loanMate.mateLoanCompany.compName" value="${bview.loanMate.mateLoanCompany.compName}" type="text" class="input_text178 stringCheck2"/>
			                 <input type="hidden" id="mateLoanCompId" name="loanMate.mateLoanCompany.id" value="${bview.loanMate.mateLoanCompany.id}"/>
			               </td>
			               <td>
			                 <label class="lab">单位电话：</label>
			                 <input name="loanMate.mateLoanCompany.compTel" value="${bview.loanMate.mateLoanCompany.compTel}" type="text" class="input_text178 isTel"/>
			               </td>
			            </tr>
			            <tr>
			              <td colspan="3"><label class="lab" >单位地址：</label>
			                <select class="select180" id="compProvince" name="loanMate.mateLoanCompany.compProvince" value="${bview.loanMate.mateLoanCompany.compProvince}">
			                   <option value="">请选择</option>
			                   <c:forEach var="item" items="${bview.provinceList}" varStatus="status">
					             <option value="${item.areaCode}" 
					              <c:if test="${bview.loanMate.mateLoanCompany.compProvince==item.areaCode}"> 
							            selected=true 	   
								   </c:if>
					             >${item.areaName}</option>
				               </c:forEach>
			                </select>
			                <select class="select180" id="compCity" name="loanMate.mateLoanCompany.compCity" value="${bview.loanMate.mateLoanCompany.compCity}">
			                   <option value="">请选择</option>
			                </select>
			                <select class="select180" id="compArer" name="loanMate.mateLoanCompany.compArer" value="${bview.loanMate.mateLoanCompany.compArer}">
			                   <option value="">请选择</option>
			                </select>
			                 <input type="hidden" id="compCityHidden" value="${bview.loanMate.mateLoanCompany.compCity}"/>
			                 <input type="hidden" id="compArerHidden" value="${bview.loanMate.mateLoanCompany.compArer}"/>
			                 <input name="loanMate.mateLoanCompany.compAddress" value="${bview.loanMate.mateLoanCompany.compAddress}" type="text" class="input_text178" style="width:250px"/> 
			              </td>
			            </tr>
			          </tbody>
			        </table>
			        </table>
	  				<div class="tright mr10 mb5">
			           <input type="submit" class="btn btn-primary" id="mateNextBtn" value="保存"/>
			        </div>
	  			</form>	  		
	</div>	
	<!-- 申请信息编辑模态层 -->	
	<div id="applyMod"  style="width:100%;height:100%;overflow:auto;display:none">				
	  			<form id="applyForm" method="post" action="${ctx}/apply/storeReviewController/storeReviewUpdate?preResponse=${bview.preResponse}">
	  				<input type="hidden" name="loanInfo.id" value="${bview.loanInfo.id }">
	  				<input type="hidden" name="loanInfo.loanCode" value="${bview.loanCustomer.loanCode }">	
	  				<input type="hidden" name="applyId" value="${bview.applyId }">
	  				<input type="hidden" name="wobNum" value="${workItem.wobNum }">
	  				<input type="hidden" name="token" value="${workItem.token }">
	  				<input type="hidden" name="stepName" value="${workItem.stepName }">
	  				<input type="hidden" name="flowName" value="${workItem.flowName }">
	  				<input type="hidden" name="method" value="apply">
	  				<input type="hidden" id="limitLower"/>
      				<input type="hidden" id="limitUpper"/>
					<table class="table1" cellpadding="0" cellspacing="0" border="0"width="100%">
						<tr>
						   <td ><label class="lab" ><span class="red">*</span>产品类型：</label>
								<select id="productId" name="loanInfo.productType"
								value="${bview.loanInfo.productType}" class="select180 required">
									<option value="">请选择</option>
									<c:forEach var="item" items="${bview.productList}" varStatus="status">
									  <option value="${item.productCode }"
									    <c:if test="${item.productCode==bview.loanInfo.productType}">
									      selected=true 
									    </c:if>
									  >${item.productName}</option>
									</c:forEach>
				     			</select>
				     		</td>
							<td ><label class="lab" ><span class="red">*</span>申请额度（元）:</label>
								<input type="text" name="loanInfo.loanApplyAmount"
								value="<fmt:formatNumber value='${bview.loanInfo.loanApplyAmount}' pattern="##0.00"/>" class="input_text178 required numCheck positiveNumCheck limitCheck" /></td>
							<td><label class="lab" ><span class="red">*</span>借款期限：</label> <select
								id="monthId" name="loanInfo.loanMonths"
								value="${bview.loanInfo.loanMonths}" class="select180 required">
									<option value="">请选择</option>
								</select>
								<input type="hidden" id="loanMonths" value="${bview.loanInfo.loanMonths}"/>
							</td>
						</tr>
						<tr>
							<td><label class="lab" ><span class="red">*</span>申请日期：</label> <input class="input_text178 required"
								id="d4311" name="loanInfo.loanApplyTime" value="<fmt:formatDate value='${bview.loanInfo.loanApplyTime}' pattern="yyyy-MM-dd HH:mm:ss"/>" type="text" class="Wdate"
								size="10" onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
								style="cursor: pointer" /></td>
							<td><label class="lab" ><span class="red">*</span>借款用途：</label> <select id="cardType"
								name="loanInfo.dictLoanUse" value="${bview.loanInfo.dictLoanUse}"
								class="select180 required">
									<option value="">请选择</option>
									<c:forEach items="${fns:getDictList('jk_loan_use')}" var="item">
										<option value="${item.value}" 
										<c:if test="${bview.loanInfo.dictLoanUse==item.value}"> 
										    selected=true 
										  </c:if> 
										>${item.label}</option>
									</c:forEach>
							</select></td>
							<td><label class="lab" >具体用途：</label>
							<input type="text"
								value="${bview.loanInfo.realyUse}" name="loanInfo.realyUse"
								class="input_text178" /></td>
						</tr>
						<tr>
							<td><label class="lab"><span class="red">*</span>是否加急:</label> 
							      <span>
							      <c:forEach
									items="${fns:getDictList('jk_urgent_flag')}" var="item">
									<input type="radio" class="required" name="loanInfo.loanUrgentFlag" value="${item.value}" 
										<c:if test="${item.value==bview.loanInfo.loanUrgentFlag}">
				                        checked=true 
				                      </c:if> >${item.label}</input>
				                   </c:forEach>
				                   </span>
				            </td>
				            <td><label class="lab">录入人：</label>
				               <input type="text" name="loanInfo.loanCustomerServiceName" disabled="disabled" value="${bview.loanInfo.loanCustomerServiceName}" class="input_text178"/>
				               <input type="hidden" name="loanInfo.loanCustomerService" value="${bview.loanInfo.loanCustomerService}"/>
				            </td>
				            <td><label class="lab">客户经理：</label>
				               <input type="text" name="loanInfo.loanManagerName" disabled="disabled" value="${bview.loanInfo.loanManagerName}" class="input_text178"/>
				               <input type="hidden" name="loanInfo.loanManagerCode" value="${bview.loanInfo.loanManagerCode}"/>
				            </td>
				 		</tr>
						<tr>
							<td colspan="2"><label class="lab" >管辖城市：</label> 							 
							 <input type="text" value="${bview.loanInfo.storeProviceName}" disabled>
							 <input type="text" value="${bview.loanInfo.storeCityName}" disabled>
						   </td>
						   <td>
				               <input type="hidden" name="loanInfo.loanStoreOrgId" value="${bview.loanInfo.loanStoreOrgId}"/>
				         
				            </td>
						</tr>
						<tr>
					     <td colspan="3"> 
					       	<label class="lab">备注：</label>
					       	<input type="hidden" name="loanRemark.id" value="${bview.loanRemark.id}"/>
					       	<textarea rows="" cols="" id="remarks" name="loanRemark.remark" class="textarea_refuse" onkeyup="words_deal();">${bview.loanRemark.remark}</textarea>				             	
						 </td>
					  </tr>
					</table>	
					<div class="tright mb5 mr10" >
						<input type="submit" id="applyInfoNextBtn" class="btn btn-primary" value="保存" />
					</div>  			
	  			</form>
	  		
	</div>
	
	<!-- 共同借款人编辑模态层 -->	
	<div id="coboMod"  style="width:100%;height:100%;overflow:auto;display:none">
		<!-- 待门店复核 -->
			<form id="coboForm" method="post" action="${ctx}/apply/storeReviewController/storeReviewUpdate?preResponse=${bview.preResponse}">
	  				<input type="hidden" name="applyId" id="coboApplyId" value="${bview.applyId}">
	  				<input type="hidden" name="wobNum" id="coboWobNum" value="${workItem.wobNum }">
	  				<input type="hidden" name="token" id="coboToken" value="${workItem.token }">
	  				<input type="hidden" name="stepName" value="${workItem.stepName }">
	  				<input type="hidden" name="flowName" value="${workItem.flowName }">
	  				<input type="hidden" value="${bview.loanInfo.loanCode}" name="loanInfo.loanCode"/>
	  				<input type="hidden" name="method" value="cobo">	  			  			
     				<c:if test="${bview.loanCoborrower!=null &&fn:length(bview.loanCoborrower)>0}">
					   <input type="hidden" value="${fn:length(bview.loanCoborrower)}" id="parentPanelIndex"></input>
					  </c:if>
					  <c:if test="${bview.loanCoborrower==null || fn:length(bview.loanCoborrower)==0}">
					    <input type="hidden" value="0" id="parentPanelIndex"></input>
					  </c:if>
				     <c:if test="${bview.loanCoborrower!=null &&fn:length(bview.loanCoborrower)>0}">
				      <div id="contactPanel">
				     <c:forEach items="${bview.loanCoborrower}" var="currCoborro" varStatus="coboStatus">
					  <div class="contactPanel" id="contactPanel_${coboStatus.index}" index='${coboStatus.index}'> 
					     <div  style="text-align:left;">
					     
				         <c:if test="${bview.oneedition==1}">   
                                 <input type="button" name="delCoborrower" index="${coboStatus.index}" coboId="${currCoborro.id}" value="删除自然人保证人" class="btn btn-small"></input>
                            </c:if>       
					      <c:if test="${bview.oneedition==-1}">  
					      <input type="button" name="delCoborrower" index="${coboStatus.index}" coboId="${currCoborro.id}" value="删除共借人" class="btn btn-small"></input>
					   </c:if> 
					   
					     </div>
					   <input type="hidden" class="coboId" name="loanCoborrower[${coboStatus.index}].id" value="${currCoborro.id}"/>
					    <table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				            <tr>
				                <td  width="31%" ><label class="lab"><span class="red">*</span>共借人姓名：</label>
				                <input type="text" value="${currCoborro.coboName}" name="loanCoborrower[${coboStatus.index}].coboName" class="input_text178 required stringCheck"/></td>
				                <td  width="31%" ><label class="lab"><span class="red">*</span>性别：</label>
				                 <span>
				                  <c:forEach items="${fns:getDictList('jk_sex')}" var="item">
				                     <input type="radio"name="loanCoborrower[${coboStatus.index}].coboSex" class="required" 
				                        <c:if test="${currCoborro.coboSex==item.value}"> 
				                         checked=true 
				                       </c:if>
				                      value="${item.value}">${item.label}</input>
				                   </c:forEach>
				                   </span>
				               </td> 
				               <td><label class="lab"><span class="red">*</span>婚姻状况：</label>
				                 <select name="loanCoborrower[${coboStatus.index}].dictMarry" value="${currCoborro.dictMarry}" class="select180 required">
				                   <option value="">请选择</option>
				                     <c:forEach items="${fns:getDictList('jk_marriage')}" var="item">
				                      <c:if test="${item.label!='空'}">
									   <option value="${item.value}"
									    <c:if test="${currCoborro.dictMarry==item.value}"> 
									     selected=true 
									    </c:if>
									   >${item.label}
									   </option>
									   </c:if>
								     </c:forEach>
								</select>
				                </td>
				            </tr>
				            <tr>
				              <td><label class="lab"><span style="color:red">*</span>证件类型：</label>
										<select name="loanCoborrower[${coboStatus.index}].dictCertType"
										value="${currCoborro.dictCertType}" class="select180 required">
											<option value="">请选择</option>
											<c:forEach items="${fns:getDictList('jk_certificate_type')}"
												var="item">
											 <option value="${item.value}"
													<c:if test="${currCoborro.dictCertType==item.value}">
									      selected = true 
									     </c:if>>${item.label}</option>
											</c:forEach>
									</select>
								</td>
								<td><label class="lab"><span class="red">*</span>证件号码：</label>
				                   <input name="loanCoborrower[${coboStatus.index}].coboCertNum" value="${currCoborro.coboCertNum}" type="text" onblur="javascript:launch.certifacteFormatByCertNum(this.value,this);" class="input_text178 required coboCertCheck"/>
				                </td>
				               <td>
				                 <label class="lab"><span class="red">*</span>证件有效期：</label>
				                 <input id="idStartDay_${coboStatus.index}" name="loanCoborrower[${coboStatus.index}].idStartDay"
											value="<fmt:formatDate value='${currCoborro.idStartDay}' pattern="yyyy-MM-dd"/>"
											type="text" class="Wdate input_text70 required" size="10"
											onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'idEndDay_${coboStatus.index}\')}'})"
											style="cursor: pointer" />-<input id="idEndDay_${coboStatus.index}"
											name="loanCoborrower[${coboStatus.index}].idEndDay"
											value="<fmt:formatDate value='${currCoborro.idEndDay}' pattern="yyyy-MM-dd"/>"
											type="text" class="Wdate input_text70" size="10"
											<c:if test="${currCoborro.idStartDay!=null && currCoborro.idEndDay==null}"> 
												  disabled=true
										    </c:if>
										     onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'idStartDay_${coboStatus.index}\')}'})"
										     style="cursor: pointer" /> <input type="checkbox" id="longTerm_${coboStatus.index}"
										     name="longTerm" value="1" onclick="launch.idEffectiveDay(this,'idEndDay_${coboStatus.index}');"
										     <c:if test="${currCoborro.idStartDay!=null && currCoborro.idEndDay==null}"> 
										         checked=true 
										     </c:if>  />
										          长期
				               </td>
				                
				            </tr>
				            <tr>
				            <td><label class="lab"><span class="red">*</span>手机号码1：</label>
				                   <input name="loanCoborrower[${coboStatus.index}].coboMobile" value="${currCoborro.coboMobile}"  type="text" class="input_text178 required isMobile coboMobileDiff1 coboMobileDiff2"/>
				             </td>
				             <td><label class="lab">手机号码2：</label>
				                 <input type="text" name="loanCoborrower[${coboStatus.index}].coboMobile2" value="${currCoborro.coboMobile2}" class="input_text178 isMobile coboMobileDiff1 coboMobileDiff2"/>
				             </td>
				             <td><label class="lab">固定电话：</label>
				                   <input name="loanCoborrower[${coboStatus.index}].coboNowTel" value="${currCoborro.coboNowTel}" type="text" class="input_text178 isTel"/>
				             </td>
				            </tr>
				            <tr>
				              <td><label class="lab">学历：</label>
										<select id="dictEducation_${coboStatus.index}" name="loanCoborrower[${coboStatus.index}].dictEducation"
										value="${currCoborro.dictEducation}"
										class="select2-container select180">
											<option value="">请选择</option>
											<c:forEach items="${fns:getDictList('jk_degree')}" var="curEdu">
												<option value="${curEdu.value}"
													<c:if test="${currCoborro.dictEducation==curEdu.value}">
									      selected = true
									     </c:if>>${curEdu.label}</option>
											</c:forEach>
									</select>
							   </td>
							   <td><label class="lab"><span style="color: red">*</span>住房性质：</label>
										<select name="loanCoborrower[${coboStatus.index}].customerHouseHoldProperty"
										value="${currCoborro.customerHouseHoldProperty}"
										class="select180 required">
											<option value="">请选择</option>
											<c:forEach items="${fns:getDictList('jk_house_nature')}"
												var="item">
												<option value="${item.value}"
													<c:if test="${item.value==currCoborro.customerHouseHoldProperty}">
									      selected=true 
									    </c:if>>${item.label}</option>
											</c:forEach>
									</select>
							    </td>
							    <td><label class="lab">家人是否知悉：</label> <c:forEach
											items="${fns:getDictList('jk_yes_or_no')}" var="item">
											<input type="radio" name="loanCoborrower[${coboStatus.index}].coboContractIsKnow"
												value="${item.value}"
												<c:if test="${item.value==currCoborro.coboContractIsKnow}">
				                     checked='true'
				                  	</c:if> />${item.label}
				               	 </c:forEach></td>
				            </tr>
				            <tr>
				                <td><label class="lab">有无子女：</label>
				                  <c:forEach items="${fns:getDictList('jk_have_or_nohave')}" var="item">
				                     <input type="radio" name="loanCoborrower[${coboStatus.index}].coboHaveChild"
				                       <c:if test="${currCoborro.coboHaveChild==item.value}"> 
				                         checked=true 
				                       </c:if>
				                      value="${item.value}"/>${item.label}
				                   </c:forEach>
				               	</td>
				               	<td><label class="lab">毕业日期：</label> <input
										id="coboGraduationDay_${coboStatus.index}"
										name="loanCoborrower[${coboStatus.index}].coboGraduationDay"
										value="<fmt:formatDate value='${currCoborro.coboGraduationDay}' pattern="yyyy-MM-dd"/>"
										type="text" class="Wdate input_text178" size="10"
										onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" />
								</td>
								<td><label class="lab">初到本市时间：</label>
										<input id="firstArriveYear_${coboStatus.index}"
										name="loanCoborrower[${coboStatus.index}].customerFirtArriveYear"
										value="${currCoborro.customerFirtArriveYear}" type="text"
										class="Wdate input_text178" size="10"
										onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" />
								</td>
				            </tr>
				            <tr>
				              <td><label class="lab">起始居住时间：</label> <input
										id="customerFirtLivingDay_${coboStatus.index}"
										name="loanCoborrower[${coboStatus.index}].customerFirstLivingDay"
										value="<fmt:formatDate value='${currCoborro.customerFirstLivingDay}' pattern="yyyy-MM-dd"/>"
										type="text" class="Wdate input_text178" size="10"
										onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" />
									</td>
									<td><label class="lab"><span style="color: red">*</span>客户类型：</label>
										<select id="dictCustomerDiff_${coboStatus.index}" name="loanCoborrower[${coboStatus.index}].dictCustomerDiff"
										class="select180 required" 
										<c:if test="${bview.preResponse == 'STORE_TO_CHECK' || bview.preResponse == 'STORE_BACK_CHECK' }">
										  <c:if test="${currCoborro.dictCustomerDiff!=null && currCoborro.dictCustomerDiff!=''}">
											disabled = "disabled"
											</c:if> 
										</c:if> 
										value="${currCoborro.dictCustomerDiff}">
											<option value="">请选择</option>
											<c:forEach items="${fns:getDictList('jk_customer_diff')}"
												var="item">
												<option value="${item.value}"
													<c:if test="${currCoborro.dictCustomerDiff==item.value}">
									       selected=true 
									    </c:if>>${item.label}</option>
											</c:forEach>
									</select></td>
									<td>
									  <label class="lab" ><span style="color: red">*</span>电子邮箱：</label>
									   <input type="text" class="input_text178 email required"
										name="loanCoborrower[${coboStatus.index}].coboEmail"
										value="${currCoborro.coboEmail}"/>
								    </td>
				            </tr>
				            <tr>
				                <td>
              				       	 <label class="lab">月收入（元/每月）：</label>
                    				 <input type="text" name="loanCoborrower[${coboStatus.index}].coboCompany.compSalary" value="<fmt:formatNumber value='${currCoborro.coboCompany.compSalary}' pattern="##0.00"/>" class="input_text178  number amountCheck numCheck" />
                				</td>
				                <td><label class="lab">其他所得：</label>
				                     <input type="hidden" name="loanCoborrower[${coboStatus.index}].coboCompany.id" class="coboCompId" value="${currCoborro.coboCompany.id}"/>
				                     <input name="loanCoborrower[${coboStatus.index}].coboCompany.compOtherAmount" value="${currCoborro.coboCompany.compOtherAmount}" type="text" class="input_text178"/>元/每月
				                </td>
				                <td><label class="lab">房屋出租：</label>
				                     <input type="hidden" name="loanCoborrower[${coboStatus.index}].coboLivings.id" class="coboLivingsId" value="${currCoborro.coboLivings.id}"/>
				                     <input name="loanCoborrower[${coboStatus.index}].coboLivings.customerRentMonth" value="<fmt:formatNumber value='${currCoborro.coboLivings.customerRentMonth}' pattern="##0.00"/>" type="text" class="input_text178 number numCheck amountCheck"/>元/每月
				                </td>
				            </tr>
			<c:if test="${bview.oneedition==1}">      
			<!-- 单位名称   QQ  微博  开始  -->  
			             <tr>
			                <td>
			                  <label class="lab"><span style="color: red">*</span>单位名称：</label>
			                     <input name="loanCoborrower[${coboStatus.index}].coboCompName" value="${currCoborro.coboCompName}" type="text" class="input_text178 required"/>
			                </td>
			                <td><label class="lab">QQ：</label>
			                    <input name="loanCoborrower[${coboStatus.index}].coboQq" value="${currCoborro.coboQq}" type="text" class="input_text178"/>
			                </td>
			                <td><label class="lab">微博：</label>
			                    <input name="loanCoborrower[${coboStatus.index}].coboWeibo" value="${currCoborro.coboWeibo}" type="text" class="input_text178"/>
			                </td>
			            </tr>
         <!--     单位名称   QQ  微博 结束 --> 
            </c:if>  
				                  <tr>
				                <td colspan="3"><label class="lab"><span class="red">*</span>户籍地址：</label>
				                  <select  class="select78   required coboHouseholdProvince" id="coboHouseholdProvince_${coboStatus.index}" name="loanCoborrower[${coboStatus.index}].coboHouseholdProvince">
				                     <option value="">请选择</option>
				                     <c:forEach var="item" items="${bview.provinceList}" varStatus="status">
						             <option value="${item.areaCode }"
						               <c:if test="${item.areaCode==currCoborro.coboHouseholdProvince}">
						                 selected = true 
						               </c:if>
						             >${item.areaName}</option>
					               </c:forEach>
				                  </select>
				                  <select class="select78  required coboHouseholdCity" id="coboHouseholdCity_${coboStatus.index}" name="loanCoborrower[${coboStatus.index}].coboHouseholdCity">
				                     <option value="">请选择</option>
				                  </select>
				                  <select class="select78 required coboHouseholdArea" id="coboHouseholdArea_${coboStatus.index}" name="loanCoborrower[${coboStatus.index}].coboHouseholdArea">
				                     <option value="">请选择</option>
				                  </select>
				                  <input type="hidden" id="coboHouseholdCityHidden_${coboStatus.index}" value="${currCoborro.coboHouseholdCity}"/>
				                   <input type="hidden" id="coboHouseholdAreaHidden_${coboStatus.index}" value="${currCoborro.coboHouseholdArea}"/>
				                  <input name="loanCoborrower[${coboStatus.index}].coboHouseholdAddress" value="${currCoborro.coboHouseholdAddress}" type="text" class="input_text178 required"style="width:250px"/>
				                </td>
				            </tr>
				            <tr>
				             <td colspan="3"><label class="lab"><span class="red">*</span>现住址：</label>
				                  <select class="select78  required coboLiveingProvince" id="coboLiveingProvince_${coboStatus.index}" name="loanCoborrower[${coboStatus.index}].coboLiveingProvince">
				                     <option value="">请选择</option>
				                     <c:forEach var="item" items="${bview.provinceList}" varStatus="status">
						             <option value="${item.areaCode}"
						              <c:if test="${item.areaCode==currCoborro.coboLiveingProvince}"> 
						                 selected=true 
						              </c:if>
						             >${item.areaName}</option>
					               </c:forEach>
				                  </select>
				                  <select class="select78  required coboLiveingCity" id="coboLiveingCity_${coboStatus.index}" name="loanCoborrower[${coboStatus.index}].coboLiveingCity">
				                     <option value="">请选择</option>
				                  </select>
				                  <select class="select78  required coboLiveingArea" id="coboLiveingArea_${coboStatus.index}" name="loanCoborrower[${coboStatus.index}].coboLiveingArea">
				                     <option value="">请选择</option>
				                  </select>
				                  <input type="hidden" id="coboLiveingCityHidden_${coboStatus.index}" value="${currCoborro.coboLiveingCity}"/>
				                   <input type="hidden" id="coboLiveingAreaHidden_${coboStatus.index}" value="${currCoborro.coboLiveingArea}"/>
				                  <input name="loanCoborrower[${coboStatus.index}].coboNowAddress" value="${currCoborro.coboNowAddress}" type="text" class="input_text178 required" style="width:250px"/>
				                </td>
				            </tr>
				            <c:if test="${bview.oneedition==1}">   
<!--单位住址  开始 -->
            <tr>
             <td colspan="3"><label class="lab"><span class="red">*</span>单位地址：</label>
                  <select class="select78  required coboCompProvince" id="coboCompProvince_${coboStatus.index}" name="loanCoborrower[${coboStatus.index}].coboCompProvince">
                     <option value="">请选择</option>
                     <c:forEach var="item" items="${bview.provinceList}" varStatus="status">
		             <option value="${item.areaCode}"
		              <c:if test="${item.areaCode==currCoborro.coboCompProvince}"> 
		                 selected=true 
		              </c:if>
		             >${item.areaName}</option>
	               </c:forEach>
                  </select>
                  <select class="select78  required coboCompCity" id="coboCompCity_${coboStatus.index}" name="loanCoborrower[${coboStatus.index}].coboCompCity">
                     <option value="">请选择</option>
                  </select>
                  <select class="select78  required coboCompArer" id="coboCompArer_${coboStatus.index}" name="loanCoborrower[${coboStatus.index}].coboCompArer">
                     <option value="">请选择</option>
                  </select>
                  <input type="hidden" id="coboCompCityHidden_${coboStatus.index}" value="${currCoborro.coboCompCity}"/>
                   <input type="hidden" id="coboCompArerHidden_${coboStatus.index}" value="${currCoborro.coboCompArer}"/>
                  <input name="loanCoborrower[${coboStatus.index}].coboCompAddress" value="${currCoborro.coboCompAddress}" type="text" class="input_text178 required" style="width:250px"/>
                </td>
            </tr>
<!--单位住址  结束-->       
      </c:if>       
				        </table>
				      <div class="box1 mb5">
					    <input type="button" class="btn btn-small"   value="增加联系人" name="addCobContactBtn" tableId='table_${coboStatus.index}'  parentIndex='${coboStatus.index}'/>	
				      </div>
				        <table border="1" cellspacing="0" cellpadding="0" border="0" currIndex="${fn:length(currCoborro.coborrowerContactList)}" id="table_${coboStatus.index}"  class="table3" width="100%">
				              <tr>
				                <th><span class="red">*</span>姓名</th>
				                <th><span class="red">*</span>关系类型</th>
				                <th><span class="red">*</span>和本人关系</th>
				                <th>工作单位</th>
				                <th>家庭或工作单位详细地址</th>
				                <th>单位电话</th>
				                <th><span class="red">*</span>手机号</th>
				                <th>操作</th>
				            </tr>
				          <c:if test="${fn:length(currCoborro.coborrowerContactList)>0}">  
				         	 <c:forEach items="${currCoborro.coborrowerContactList}" var="curContact" varStatus="st">
				           	   <tr>
				               	<td>
				                 <input type="hidden" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.id" value="${curContact.id}"></input>
				                 <input type="text" type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.contactName" value="${curContact.contactName}" class="input_text70 required stringCheck contactCheck"/>
				                </td>
				                <td>
				                <select id="relationType_${coboStatus.index}_${st.index}" index="${coboStatus.index}${st.index}" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.relationType" class="required select180">
				                   <option value="">请选择</option>
				                     <c:forEach items="${fns:getDictList('jk_relation_type')}" var="item">
									  <option value="${item.value}" 
									    <c:if test="${curContact.relationType==item.value}"> 
				                         selected=true 
				                       </c:if>
									   class="${item.id}">${item.label}</option>
								     </c:forEach>
								</select>
				                </td>
				                <td>
				                <select id="contactRelation_${coboStatus.index}${st.index}" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.contactRelation" class="required select180">
				                   <option value="">请选择</option>
				             	</select>
				             	 <input type="hidden" id="contactRelationHidden_${coboStatus.index}${st.index}" value="${curContact.contactRelation}"/>
				                </td>
				                <td><input type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.contactSex" value="${curContact.contactSex}" class="input_text178"/></td>
				                <td><input type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.contactNowAddress" value="${curContact.contactNowAddress}" class="input_text178"/></td>
				                <td><input type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.contactUnitTel" value="${curContact.contactUnitTel}" class="input_text178 isTel"/></td>
				                <td><input type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.contactMobile" value="${curContact.contactMobile}" class="input_text178 required isMobile coboMobileDiff1 coboMobileDiff2"/></td>
				                <td class="tcenter">
				                  <c:if test="${st.index>2}">
				                    <input type="button" class="btn_edit"  value="删除"  onclick="contact.delConCoboByReturn(this,'table_${coboStatus.index}','CONTACT')" style="width:50px;"/>
				                  </c:if>
				                 </td>
				            </tr>
				            </c:forEach>
				          </c:if>
				          <c:if test="${fn:length(currCoborro.coborrowerContactList)<3}">
				            <c:forEach  var="x" begin="${fn:length(currCoborro.coborrowerContactList)}" end="2">
				           	   <tr>
				               	<td>
				                 <input type="hidden" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.id"></input>
				                 <input type="text" type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.contactName" class="input_text70 required stringCheck contactCheck"/>
				                </td>
				                <td>
				                <select id="relationType_${coboStatus.index}_${x}" index="${coboStatus.index}${x}" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.relationType" class="required select180">
				                   <option value="">请选择</option>
				                     <c:forEach items="${fns:getDictList('jk_relation_type')}" var="item">
									  <option value="${item.value}" 
									   class="${item.id}">${item.label}</option>
								     </c:forEach>
								</select>
				                </td>
				                <td>
				                <select id="contactRelation_${coboStatus.index}${x}" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.contactRelation" class="required select180">
				                   <option value="">请选择</option>
				             	</select>
				                </td>
				                <td><input type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.contactSex" class="input_text178"/></td>
				                <td><input type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.contactNowAddress" class="input_text178"/></td>
				                <td><input type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.contactUnitTel" class="input_text178 isTel"/></td>
				                <td><input type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.contactMobile" class="input_text178 required isMobile coboMobileDiff1 coboMobileDiff2"/></td>
				                <td class="tcenter">
				                </td>
				            </tr>
				            </c:forEach>
				          </c:if>
				        </table>
				        </div>
				       </c:forEach>
				       </div>
				       </c:if>	
				       <div class="tright mr10 mb5">
				           <c:if test="${bview.oneedition==-1}">   
	                           <input type="button" id="addCoborrowBtn" class="btn btn-primary" value="增加共借人"/>  
                           </c:if>       
				       
				         <c:if test="${bview.oneedition==1}">   
	                           <input type="button" id="addCoborrowBtn" class="btn btn-primary" value="增加自然人保证人"/>  
                           </c:if>   
					       					  			
				           <input type="submit" id="coborrowNextBtn" onsubmit="disabled=1;v=this;setTimeout('v.disabled=0',3000)" class="btn btn-primary" value="保存"/>
				       </div>
	  			</form>
	</div>
	
	<!-- 信用资料编辑模态层 -->	
	<div id="creditMod"  style="width:100%;height:100%;overflow:auto;display:none">
	  			<form id="creditForm" method="post" action="${ctx}/apply/storeReviewController/storeReviewUpdate?preResponse=${bview.preResponse}">
	  				<input type="hidden" name="applyId" value="${bview.applyId }">
	  				<input type="hidden" name="wobNum" value="${workItem.wobNum }">
	  				<input type="hidden" name="token" value="${workItem.token }">
	  				<input type="hidden" name="stepName" value="${workItem.stepName }">
	  				<input type="hidden" name="flowName" value="${workItem.flowName }">
	  				<input type="hidden" value="${bview.loanInfo.loanCode}" name="loanInfo.loanCode"/>
	  				<input type="hidden" name="method" value="credit">
	  				 <table cellspacing="0" id="loanCreditArea" cellpadding="0" border="1"  class="table3" width="100%">
			            <tr>
			                <th><span class="red">*</span>抵押类型</th>
			                <th>抵押物品</th>
			                <th>机构名称</th>
			                <th>贷款额度(元)</th>
			                <th>每月供额度(元)</th>
			                <th>贷款余额(元)</th>
			                <th>信用卡总数</th>
			                <th>操作</th>
			            </tr>
			            <c:forEach items="${bview.loanCreditInfoList}" var="curLoanCredit" varStatus="creditStatus">
			             <tr>
			                <td>
			                <span>
			                <c:forEach items="${fns:getDictList('jk_pledge_flag')}" var="curItem">
			                <input type="radio" class="required" name="loanCreditInfoList[${creditStatus.index}].dictMortgageType"
			                     value="${curItem.value}"
			                     <c:if test="${curItem.value==curLoanCredit.dictMortgageType}">
			                        checked=true
			                      </c:if> 
			                     />${curItem.label}
			                </c:forEach>
			                </span>
			                 <input type="hidden" name="loanCreditInfoList.id" class="creditId" value="${curLoanCredit.id}"/>
			                </td>
			                <td><input name="loanCreditInfoList.creditMortgageGoods" value="${curLoanCredit.creditMortgageGoods}" type="text" class="input_text178 required"/></td>
			                <td><input name="loanCreditInfoList.orgCode" value="${curLoanCredit.orgCode}" type="text" class="input_text178"/></td>
			                <td><input name="loanCreditInfoList.creditLoanLimit" value="<fmt:formatNumber value='${curLoanCredit.creditLoanLimit}' pattern="##0.00"/>" type="text"class="input_text178 number numCheck positiveNumCheck"/></td>
			                <td><input name="loanCreditInfoList.creditMonthsAmount" value="<fmt:formatNumber value='${curLoanCredit.creditMonthsAmount}' pattern="##0.00"/>"  type="text"class="input_text178 number numCheck positiveNumCheck"/></td>
			                <td><input name="loanCreditInfoList.creditLoanBlance" value="<fmt:formatNumber value='${curLoanCredit.creditLoanBlance}' pattern="##0.00"/>"  type="text" class="input_text178 number numCheck positiveNumCheck"/></td>
			                <td><input name="loanCreditInfoList.creditCardNum" value="<fmt:formatNumber value='${curLoanCredit.creditCardNum}' pattern="##0"/>"  type="text" class="input_text178 digits positiveNumCheck"/></td>
			               <td class="tcenter"><input type="button" onclick="contact.delRow(this,'loanCreditArea','CREDIT');" class="btn_edit" value="删除"></input></td>
			             </tr>
			            </c:forEach>
			        </table>	
			        <div  class="tright mr10 mb5">
			        	<input type="button" class="btn btn-primary" id="creditAddBtn" value="增加"></input>
			        	<input type="submit" id="creditNextBtn" class="btn btn-primary" value="保存"/>
			        </div>
	  			</form>
 	</div>
	
	<!-- 职业信息/公司资料编辑模态层 -->	
	<div id="companyMod"  style="width:100%;height:100%;overflow:auto;display:none">  			  			
	  			<form id="companyForm" method="post" action="${ctx}/apply/storeReviewController/storeReviewUpdate?preResponse=${bview.preResponse}">
	  				<input type="hidden" name="applyId" value="${bview.applyId }">
	  				<input type="hidden" name="customerLoanCompany.rid" value="${bview.loanCustomer.id}">
	  				<input type="hidden" name="wobNum" value="${workItem.wobNum }">
	  				<input type="hidden" name="token" value="${workItem.token }">
	  				<input type="hidden" name="stepName" value="${workItem.stepName }">
	  				<input type="hidden" name="flowName" value="${workItem.flowName }">
	  				<input type="hidden" value="${bview.loanCustomer.customerCode}" name="loanCustomer.customerCode" id="customerCode" /> 
	  				<input type="hidden" name="customerLoanCompany.id" value="${bview.customerLoanCompany.id }">
	  				<input type="hidden" value="${bview.loanInfo.loanCode}" name="loanInfo.loanCode"/>
	  				<input type="hidden" name="method" value="company">
			        <table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
			            <tr>
			                <td  width="31%" >
			                  <label class="lab"><span class="red">*</span>公司名称：</label>
			                  <input type="text" name="customerLoanCompany.compName" value="${bview.customerLoanCompany.compName}" class="input_text178 required compNameCheck"/></td>
			                <td  width="31%" ><label class="lab"><span class="red">*</span>单位类型:</label>
			                <select name="customerLoanCompany.dictCompType" value="${bview.customerLoanCompany.dictCompType}" class="select180 required">
			                    <option value="">请选择</option>
			                     <c:forEach items="${fns:getDictList('jk_unit_type')}" var="item">
								  <option value="${item.value}"
								    <c:if test="${bview.customerLoanCompany.dictCompType==item.value}">
								       selected=true
								    </c:if>
								  >${item.label}</option>
							     </c:forEach>
							</select>
			                </td>
							<td><label class="lab"><span class="red">*</span>单位电话：</label>
							   <input type="text" name="customerLoanCompany.compTel" value="${bview.customerLoanCompany.compTel}" class="input_text178 isTel required"/>
							 </td>
			            </tr>
			            
			            <tr>
			                <td><label class="lab">月收入(元)：</label>
			                     <input type="text" name="customerLoanCompany.compSalary" value="<fmt:formatNumber value='${bview.customerLoanCompany.compSalary}' pattern="##0.00"/>"  class="input_text178 numCheck positiveNumCheck"/>
			                </td>
			                <td><label class="lab">每月支薪日：</label>
			                     <input type="text" name="customerLoanCompany.compSalaryDay" value="${bview.customerLoanCompany.compSalaryDay}" class="input_text178"/>
			                </td>
			                <td><label class="lab"><span style="color:red">*</span>行业类型：</label>
			                <select class="select180 required" name="customerLoanCompany.dictCompIndustry" value="${bview.customerLoanCompany.dictCompIndustry}">
			                     <option value="">请选择</option>
			                     <c:forEach items="${fns:getDictList('jk_industry_type')}" var="item">
								  <option value="${item.value}"
								    <c:if test="${bview.customerLoanCompany.dictCompIndustry==item.value}">
								       selected=true 
								    </c:if>
								  >${item.label}</option>
							     </c:forEach>
							</select>
			                </td>
			            </tr>
			            <tr>
			                <td><label class="lab">入职时间：</label>
			                <input id="d4311" name="customerLoanCompany.compEntryDay" value="<fmt:formatDate value='${bview.customerLoanCompany.compEntryDay}' pattern="yyyy-MM-dd"/>" type="text" class="Wdate input_text178" size="10"  
			                   onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" />
			                </td> 
							<td><label class="lab">职务：</label>
							  <select id="compPost" name="customerLoanCompany.compPost" value="${bview.customerLoanCompany.compPost}" class="select180">
			                    <option value="">请选择</option>
			                     <c:forEach items="${fns:getDictList('jk_job_type')}" var="item">
								  <option value="${item.value}"
								    <c:if test="${bview.customerLoanCompany.compPost==item.value}">
								       selected=true 
								    </c:if>
								  >${item.label}</option>
							     </c:forEach>
							</select>
				    		</td> 
							<td><label class="lab"><span style="color:red">*</span>累积工作年限：</label>
							    <input type="text" name="customerLoanCompany.compWorkExperience"  value="${bview.customerLoanCompany.compWorkExperience}" class="input_text178" />
							</td> 
			            </tr>
			            
			                <c:if test="${bview.oneedition==1}">   
			              <!-- 法人信息  开始 -->          
			           <tr>
			                <td><label class="lab">法人姓名：</label>
			                     <input type="text" id="comLegalMan" name="customerLoanCompany.comLegalMan" value="${bview.customerLoanCompany.comLegalMan}" class="input_text178 comLegalMan"/>
			                </td>
			                
			                <td><label class="lab">法人身份证号：</label>
			                     <input type="text" id="comLegalManNum" name="customerLoanCompany.comLegalManNum"  onblur="javascript:launch.certifacte(this.value,this);"  value="${bview.customerLoanCompany.comLegalManNum}" class="input_text178 comLegalManNum coboCertCheckCopmany"/>
			                </td>
			                
			                <td><label class="lab">法人手机号：</label>
			                     <input type="text"  id="comLegalManMoblie" name="customerLoanCompany.comLegalManMoblie" value="${bview.customerLoanCompany.comLegalManMoblie}" class="input_text178 comLegalManMoblie isMobile"/>
			                </td>
			            </tr>
			            <tr>
			                 <td><label class="lab">企业邮箱：</label>
			                     <input type="text" id="comEmail" name="customerLoanCompany.comEmail" value="${bview.customerLoanCompany.comEmail}" class="input_text178 comLegalManComEmail email"/>
			                </td>
			                
			           </tr>
                      <!-- 法人信息结束  -->   
                       </c:if>       
         
         
			            <tr>
			                <td colspan="3" style="text-align:left;"><label class="lab"><span class="red">*</span>地址：</label>
			                   <select class="select78 mr10 required" id="BcompProvince" name="customerLoanCompany.compProvince" value="${bview.customerLoanCompany.compProvince}">
			                      <option value="">请选择</option>
			                      <c:forEach var="item" items="${bview.provinceList}" varStatus="status">
					               <option value="${item.areaCode}" 
					                <c:if test="${bview.customerLoanCompany.compProvince==item.areaCode}">
					                   selected = true
					                </c:if>
					               >${item.areaName}</option>
				                  </c:forEach>
			                   </select>
			                   <select class="select78 mr10 required" id="BcompCity" name="customerLoanCompany.compCity" value="${bview.customerLoanCompany.compCity}">
			                      <option value="">请选择</option>
			                   </select>
			                   <select class="select78 mr10 required" id="BcompArer" name="customerLoanCompany.compArer" value="${bview.customerLoanCompany.compArer}">
			                      <option value="">请选择</option>
			                   </select>
			                   <input type="hidden" id="BcompCityHidden" value="${bview.customerLoanCompany.compCity}"/>
			                   <input type="hidden" id="BcompArerHidden" value="${bview.customerLoanCompany.compArer}"/>
			                   <input type="text" name="customerLoanCompany.compAddress" value="${bview.customerLoanCompany.compAddress}" class="input_text178 required" style="width:250px">
			                </td>
			            </tr>
			        </table>	 
			        <div class=" tright mr10 mb5" >
			        	<input type="submit" id="companyNextBtn" class="btn btn-primary" value="保存"/>
			        </div> 			
	  			</form>
	  		
	</div>
	
	<!-- 房产资料编辑模态层 -->	
	<div id="houseMod"  style="width:100%;height:100%;overflow:auto;display:none">
	  			<form id="houseForm" method="post" action="${ctx}/apply/storeReviewController/storeReviewUpdate?preResponse=${bview.preResponse}">	  				
	  				<input type="hidden" name="applyId" value="${bview.applyId }">
	  				<input type="hidden" name="wobNum" value="${workItem.wobNum }">
	  				<input type="hidden" name="token" value="${workItem.token }">
	  				<input type="hidden" name="stepName" value="${workItem.stepName }">
	  				<input type="hidden" name="flowName" value="${workItem.flowName }">
	  				<input type="hidden" value="${bview.loanInfo.loanCode}" name="loanInfo.loanCode"/>
	  				<input type="hidden" value="${bview.loanCustomer.id}" name="loanCustomer.id"/>
	  				<input type="hidden" name="method" value="house">
		  			<div id="loanHouseArea">
		  			<c:forEach items="${bview.customerLoanHouseList}" var="curLoanHouse" varStatus="loanHouseStatus">
						
							<table class=" table1"
								id="loanHouseArea_${loanHouseStatus.index}" cellpadding="0"
								cellspacing="0" border="0" width="100%">
								<tbody>	
									<c:if test="${loanHouseStatus.index!=0}">
										<tr>
							  				<td colspan="3">
							   					<input type="button" name="delHouseItem" class="btn btn-small" value="删除" dbId="${curLoanHouse.id}" currId="loanHouseArea_${loanHouseStatus.index}"/>
							   				</td>
										</tr>
									</c:if>							
									<tr>
										<td>
											<label class="lab">购买方式：</label> 
											<select id="cardType"
												name="customerLoanHouseList.houseBuyway" class="select180">
												<option value="">请选择</option>
												<c:forEach items="${fns:getDictList('jk_house_buywayg')}"
													var="item">
													<option value="${item.value}"
														<c:if test="${curLoanHouse.houseBuyway == item.value}">
							      							selected
							     						</c:if>>
							     						${item.label}
							     					</option>
												</c:forEach>
											</select>
										</td>
										<td>
											<label class="lab">购买日期：</label> <input id="d4311"
												class="input_text178" name="customerLoanHouseList.houseBuyday"
												value="<fmt:formatDate value='${curLoanHouse.houseBuyday}' pattern="yyyy-MM-dd"/>"
												type="text" class="Wdate" size="10"
												onClick="WdatePicker({skin:'whyGreen'})"
												style="cursor: pointer" />
										</td>
										<td>
											<label class="lab">房屋面积：</label> 
												<input type="text" name="customerLoanHouseList.houseBuilingArea"
													value="<fmt:formatNumber value='${curLoanHouse.houseBuilingArea}' pattern="###0.00"/>"
													class="input_text178 number houseAreaCheck" />
										</td>
									</tr>
									<tr>
										<td>
											<label class="lab">房屋共有情况：</label> 
											<select class="select180" name="customerLoanHouseList.dictHouseCommon"
												value="${curLoanHouse.dictHouseCommon}">
												<option value="">请选择</option>
													<c:forEach items="${fns:getDictList('jk_house_common_type')}" var="item">
														<option value="${item.value}"
													   		<c:if test="${curLoanHouse.dictHouseCommon==item.value}">
							                            		selected 
							                           		</c:if>>
							                           		${item.label}
							                        	</option>
													</c:forEach>
											</select>
										</td>
										<td>
											<label class="lab">规划用途/设计用途：</label> 
											<select id="cardType" name="customerLoanHouseList.dictHouseType"
												class="select180">
												<option value="">请选择</option>
													<c:forEach items="${fns:getDictList('jk_design_use')}" var="item">
														<option value="${item.value}"
															<c:if test="${curLoanHouse.dictHouseType==item.value}">
							                         			selected 
							                         		</c:if>>
							                         		${item.label}
							                        	</option>
													</c:forEach>
										  	</select>
										</td>
										<td >
											<label class="lab">是否抵押：</label> 
											<c:forEach items="${fns:getDictList('jk_pledge_flag')}" var="item">
												<input type="radio" class="ml10 mr6"
													name="customerLoanHouseList[${loanHouseStatus.index}].housePledgeFlag"
													value="${item.value}"
													<c:if test="${curLoanHouse.housePledgeFlag==item.value}">checked</c:if>> 
													${item.label}
												</input>
											</c:forEach>
										</td>
								    </tr>
								    <tr>
								    	<td colspan="2">
								    		<label class="lab"><span class="red">*</span>房产具体信息：</label>
											<select class="select78 mr10 houseProvince required"
												id="houseProvince_${loanHouseStatus.index}"
												name="customerLoanHouseList.houseProvince"
												value="${curLoanHouse.houseProvince}">
												<option value="">请选择</option>
												<c:forEach var="item" items="${bview.provinceList}" varStatus="status">
													<option value="${item.areaCode}"
														<c:if test="${curLoanHouse.houseProvince==item.areaCode}"> 
					                  						selected=true
					                					</c:if>>${item.areaName}
					                				</option>
												</c:forEach>
											</select> 
											<select class="select78 mr10 houseCity required"
												id="houseCity_${loanHouseStatus.index}"
												name="customerLoanHouseList.houseCity"
												value="${curLoanHouse.houseCity}">
												<option value="">请选择</option>
											</select> 
											<select class="select78 houseArea required"
												id="houseArea_${loanHouseStatus.index}"
												name="customerLoanHouseList.houseArea"
												value="${curLoanHouse.houseArea}">
												<!-- <option value="">请选择</option> -->
											</select> 
											<input name="customerLoanHouseList.houseAddress"
												type="text" value="${curLoanHouse.houseAddress}"
												class="input_text178 required" style="width:250px">
												<input type="hidden" id="houseCityHidden_${loanHouseStatus.index}" value="${curLoanHouse.houseCity}"/>
												<input type="hidden" id="houseAreaHidden_${loanHouseStatus.index}" value="${curLoanHouse.houseArea}"/>
												
												<input type="hidden" name="customerLoanHouseList.id"
												class="houseListId" value="${curLoanHouse.id}" />
										    </td>
								    <td>
											<label class="lab">建筑年份：</label> 
												<input id="d4311"
												class="input_text178"
												name="customerLoanHouseList.houseCreateDay"
												value="<fmt:formatDate value='${curLoanHouse.houseCreateDay}' pattern="yyyy-MM-dd"/>"
												type="text" class="Wdate" size="10"
												onClick="WdatePicker({skin:'whyGreen'})"
												style="cursor: pointer" />
										</td></tr>								
								</tbody>
							</table>
					</c:forEach>	
					</div>
					<div class="tright mr10 mb5" >
						<input type="button" class="btn btn-primary" value="增加" id="addHouseBtn" /> 
						<input type="submit" id="houseNextBtn" class="btn btn-primary" value="保存" />
					</div>
	  			</form>
	  	</div>	
	</div>
	
	<!-- 联系人资料编辑模态层 -->	
	<div id="contactMod"  style="width:100%;height:100%;overflow:auto;display:none">
			    <form id="contactForm" method="post" action="${ctx}/apply/storeReviewController/storeReviewUpdate?preResponse=${bview.preResponse}">
			    	<input type="hidden" name="applyId" value="${bview.applyId }">
	  				<input type="hidden" name="wobNum" value="${workItem.wobNum }">
	  				<input type="hidden" name="token" value="${workItem.token }">
	  				<input type="hidden" name="stepName" value="${workItem.stepName }">
	  				<input type="hidden" name="flowName" value="${workItem.flowName }">
	  				<input type="hidden" value="${bview.loanInfo.loanCode}" name="loanInfo.loanCode"/>
	  				<input type="hidden" value="${bview.loanCustomer.id}" name="loanCustomer.id"/>
	  				<input type="hidden" name="method" value="contact">
	  				<c:if test="${fn:length(bview.customerContactList)>0}">
	  	  				<input type="hidden" id="contactIndex" value="${fn:length(bview.customerContactList)}" />
	  	 			</c:if>
	  	 			<c:if test="${fn:length(bview.customerContactList)==0}">
	  	  				<input type="hidden" id="contactIndex" value="3" />
	  	 			</c:if>
			        <table  border="1" id="contactArea" cellspacing="0" cellpadding="0" border="0"  class="table3" width="100%">
			            <tr>
			                <th>姓名</th>
			                <th>关系类型</th>
			                <th>和本人关系</th>
			                <th>工作单位</th>
			                <th>家庭或工作单位详细地址</th>
			                <th>单位电话</th>
			                <th>手机号</th>
			                <th>操作</th>
			            </tr>
			            <c:if test="${bview.customerContactList!=null && fn:length(bview.customerContactList)>0}">
			            <c:forEach items="${bview.customerContactList}" var="ccItem" varStatus="ccStatus">
			             <tr>
			                <td>
			                 <input type="hidden" name="customerContactList.id" class="contactId" value="${ccItem.id}" />
			                 <input type="text" name="customerContactList.contactName" value="${ccItem.contactName}" class="input_text70 required stringCheck"/>
			                </td>
			                <td>
			     			  <select name="customerContactList.relationType" id="relationType_${ccStatus.index}" index="${ccStatus.index}" class="select60">
			       				<option value="">请选择</option>
			         			<c:forEach items="${fns:getDictList('jk_relation_type')}" var="item">
					  			  <option value="${item.value}" 
					  			    <c:if test="${ccItem.relationType==item.value}">
					  			       selected=true 
					  			    </c:if>
					  			   >${item.label}
						 	      </option>
					  			</c:forEach>
				            </select>
			      			</td>
			                <td>
			                <select name="customerContactList.contactRelation" id="contactRelation_${ccStatus.index}" value="${ccItem.contactRelation}" class="select60">
			                  <option value="">请选择</option>
			    			</select>
			    			 <input type="hidden" id="contactRelationHidden_${ccStatus.index}" value="${ccItem.contactRelation}"/>
			                </td>
			                <td><input type="text" name="customerContactList.contactSex" value="${ccItem.contactSex}" class="input_text70"/></td>
			                <td><input type="text" name="customerContactList.contactNowAddress"  value="${ccItem.contactNowAddress}" class="input_text178"/></td>
			                <td><input type="text" name="customerContactList.contactUnitTel" value="${ccItem.contactUnitTel}" class="input_text178 isTel"/></td>
			                <td><input type="text" name="customerContactList.contactMobile" value="${ccItem.contactMobile}" class="input_text178 isMobile contactMobileDiff1 contactMobileDiff2"/></td>
			                <td class="tcenter"><input type="button" onclick="contact.delConMainByReturn(this,'contactArea','CONTACT');" class="btn_edit" value="删除"></input></td>
			              </tr>
			            </c:forEach>
			            </c:if>
			            <c:if test="${bview.customerContactList==null || fn:length(bview.customerContactList)==0}">
			               <c:forEach var="x" begin="0" end="2">
			                 <tr>
			                <td>
			                 <input type="hidden" name="customerContactList.id" class="contactId"/>
			                 <input type="text" name="customerContactList.contactName" class="input_text70 required stringCheck"/>
			                </td>
			                <td>
			     			  <select name="customerContactList.relationType"  id="relationType_${x}" index="${x}" class="select180">
			       				<option value="">请选择</option>
			         			<c:forEach items="${fns:getDictList('jk_relation_type')}" var="item">
					  			  <option value="${item.value}">${item.label}
						 	      </option>
					  			</c:forEach>
				            </select>
			      			</td>
			                <td>
			                <select name="customerContactList.contactRelation" id="contactRelation_${x}" class="select180">
			                  <option value="">请选择</option>
			                     <c:forEach items="${fns:getDictList('card_type')}" var="item">
								  <option value="${item.value}">${item.label}</option>
							     </c:forEach>
							</select>
			                </td>
			                <td><input type="text" name="customerContactList.contactSex" class="input_text178"  maxlength="180" /></td>
			                <td><input type="text" name="customerContactList.contactNowAddress" class="input_text178"  maxlength="80"/></td>
			                <td><input type="text" name="customerContactList.contactUnitTel" class="input_text178 isTel"/></td>
			                <td><input type="text" name="customerContactList.contactMobile" class="input_text178 isMobile contactMobileDiff1 contactMobileDiff2"/></td>
			                <td class="tcenter"><input type="button" onclick="contact.delConMainByReturn(this,'contactArea','CONTACT');" class="btn_edit" value="删除"></input></td>
			              </tr>
			               </c:forEach>
			            </c:if>
			        </table>
			        <div class="tright mb5 mr10" >
				        <input type="button" class="btn btn-primary" id="contactAdd" value="增加联系人"></input>
			        	<input type="submit" id="contactNextBtn" class="btn btn-primary" value="保存"/>
			        </div>
			    </form>
	</div>
	<!-- 银行卡资料编辑模态层 -->	
	<div id="bankMod"  style="width:100%;height:100%;overflow:auto;display:none">
	  		
	  			<form id="bankForm" method="post" action="${ctx}/apply/storeReviewController/storeReviewUpdate?preResponse=${bview.preResponse}">
				  	<input type="hidden" value="${bview.loanBank.id}" name="loanBank.id" id="loanBankId"/>
				  	<input type="hidden" name="loanBank.loanCode" value="${bview.loanBank.loanCode }">	
	  				<input type="hidden" name="applyId" value="${bview.applyId }">
	  				<input type="hidden" name="wobNum" value="${workItem.wobNum }">
	  				<input type="hidden" name="token" value="${workItem.token }">
	  				<input type="hidden" name="stepName" value="${workItem.stepName }">
	  				<input type="hidden" name="flowName" value="${workItem.flowName }">
	  				<input type="hidden" name="method" value="bank">
					<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
			            <tr>
			               <td>
			 				<label class="lab"><span class="red">*</span>银行卡/折 户名：</label>
							<c:choose><c:when test="${bview.loanBank.bankAccountName!=null && bview.loanBank.bankAccountName!=''}">
							          <input type="text" name="loanBank.bankAccountName"  id="bankAccountName" 
							             <c:if test="${applyInfoEx.loanBank.bankIsRareword!=1}">
				        					   readonly="readonly"
				       					 </c:if> 
							           value="${bview.loanBank.bankAccountName}" class="input_text178 required stringCheck"/>
							      </c:when>
							      <c:otherwise>
							          <input type="text" name="loanBank.bankAccountName"  id="bankAccountName" 
							               readonly="readonly"
							           value="${workItem.bv.loanInfo.loanCustomerName}" class="input_text178 required stringCheck"/>
							   </c:otherwise>
							 </c:choose> 
							 <input type="checkbox" name="loanBank.bankIsRareword" id="bankIsRareword" value="1"
				   			 <c:if test="${applyInfoEx.loanBank.bankIsRareword==1}">
				     			  checked = true
				   			 </c:if>
							 />是否生僻字
							 </td>
							 <td><label class="lab"><span class="red">*</span>授权人：</label><c:choose>
							   <c:when test="${bview.loanBank.bankAuthorizer!=null && bview.loanBank.bankAuthorizer!=''}">
								  <input type="text" name="loanBank.bankAuthorizer" readonly="readonly" value="${bview.loanBank.bankAuthorizer}" class="input_text178 required stringCheck"/>
							   </c:when>
							   <c:otherwise>
							      <input type="text" name="loanBank.bankAuthorizer" readonly="readonly" value="${workItem.bv.loanInfo.loanCustomerName}" class="input_text178 required stringCheck"/>
							   </c:otherwise>
							 </c:choose> 
							</td>
							<td><label class="lab"><span class="red">*</span>签约平台：</label>
							<select id="bankSigningPlatform" name="loanBank.bankSigningPlatform" value="${bview.loanBank.bankSigningPlatform}" class="select180 required">
			                  <option value="">签约平台</option>
			                     <c:forEach items="${fns:getDictList('jk_deduct_plat')}" var="item">
								   <c:if test="${item.value!='2' }">
								     <option value="${item.value}"
								   <c:if test="${bview.loanBank.bankSigningPlatform==item.value}">
								      selected=true 
								   </c:if> >${item.label}</option>
								   	</c:if>
							     </c:forEach>
							</select>
						 </td>
			            </tr>
			            <tr>
						 <td><label class="lab"><span class="red">*</span>开户行名称：</label>
						 <select name="loanBank.bankName" value="${bview.loanBank.bankName}" class="select180 required">
			                    <option value="">开户行</option>
			                    <c:forEach items="${fns:getDictList('jk_open_bank')}" var="curItem">
								  <option value="${curItem.value}"
								    <c:if test="${bview.loanBank.bankName==curItem.value}">
								      selected=true 
								    </c:if>
								  >${curItem.label}</option>
							    </c:forEach>
							</select>
			                </td>
			                <td>
			                	<label class="lab"><span class="red">*</span>开户支行：</label>
				                <input type="text" name="loanBank.bankBranch" value="${bview.loanBank.bankBranch}" class="input_text178 required stringCheck"/></td>
				                <td><label class="lab"><span class="red">*</span>开户省市：</label>
				                <select class="select78" id="bankProvince" name="loanBank.bankProvince" value="${bview.loanBank.bankProvince}">
				                      <option value="">请选择</option>
				                      <c:forEach var="item" items="${bview.provinceList}" varStatus="status">
						               <option value="${item.areaCode}" 
						                <c:if test="${bview.loanBank.bankProvince==item.areaCode}">
						                   selected=true 
						                </c:if>
						               >${item.areaName}</option>
					                  </c:forEach>
				                    </select>-<select class="select78 required" id="bankCity" name="loanBank.bankCity" value="${bview.loanBank.bankCity}">
				                       <option value="">请选择</option>
				                    </select>
				                    <input type="hidden" id="bankCityHidden" value="${bview.loanBank.bankCity}"/>
			                </td>
			            </tr>
			            <tr>
			               <td><label class="lab"><span class="red">*</span>银行卡号：</label>
			                 <input type="text" id="firstInputBankAccount" value="${bview.loanBank.bankAccount}" class="input_text178 required" name="firstInputBankAccount"/></td>
			                 <td><label class="lab"><span class="red">*</span>确认银行卡号：</label>
			                <input type="text" id="confirmBankAccount" value="${bview.loanBank.bankAccount}" name="loanBank.bankAccount" class="input_text178 required" onpaste="return false" oncontextmenu="return false"></td>
			               <td></td>
			            </tr>
			         </table>	
			         <div class="tright mr10 mb30" >			           
			             <input type="submit" class="btn btn-primary" id="tempSave" value="保存"></input>
			        </div>  			
	  			</form>	  		
	</div>
</body>
</html>
