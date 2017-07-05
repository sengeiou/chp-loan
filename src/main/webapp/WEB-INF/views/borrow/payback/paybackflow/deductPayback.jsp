<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<script type="text/javascript"
	src="${context }/js/payback/deductPayback.js"></script>
<script type="text/javascript"
	src="${context}/js/payback/ajaxfileupload.js"></script>
<script src='${context}/js/common.js' type="text/javascript"></script>

<script type="text/javascript">
	$(document).ready(function() {
		deductpayback.sendAllPlat();
		deductpayback.exportAndImport();
		deductpayback.onlinededuct();
		deductpayback.deductBack();
		deductpayback.trustRecharge();
		deductpayback.deductTrust();		
		var msg = "${message}"
		if (msg != "" && msg != null) {
			art.dialog.alert(msg);
		}
	});
	
	function cle(){
		$(':input','#applyPayDeductForm').val('')
		  .removeAttr('checked')
		  .removeAttr('selected');
		$('select').trigger("change");
		
		$('#applyPayDeductForm').submit();
	};
</script>
</head>
<body>
	<div class="control-group">
		<form id="applyPayDeductForm"
			action="${ctx}/borrow/payback/deduct/list" method="post">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
			<input id="pageSize" name="pageSize" type="hidden"
				value="${page.pageSize}" />
			<table class=" table1" cellpadding="0" cellspacing="0" border="0"
				width="100%">
				<input id="ids" name="id" type="hidden" />
				<input id="plat" name="plat" type="hidden" />
				<input id="rule" name="rule" type="hidden" />
				<input id="deductType" name="deductType" type="hidden" />
				<input id="backmsg" name="backmsg" type="hidden" />
					<tr>
						<td><label class="lab">客户姓名：</label> <input type="text"
							name="customerName" class="input_text178"
							value="${paramBean.customerName }" /></td>
						<td><label class="lab">合同编号：</label> <input type="text"
							name="contractCode" class="input_text178"
							value="${paramBean.contractCode}" /></td>
						<td><label class="lab">签约平台：</label> 
						   <sys:multirePlatform platClick="platClick"  platId="platId" platName ='platName'></sys:multirePlatform>
			                <input id="platName" type="text" class="input_text178" name="dictDealTypeName"  value='${paramBean.dictDealTypeName}'>&nbsp;
			                <i id="platClick" class="icon-search" style="cursor: pointer;"></i> 
							<input type="hidden" id="platId" name='dictDealTypeId' value='${paramBean.dictDealTypeId}'>
						</td>
					</tr>
					<tr>
						<td><label class="lab">门店：</label> <input id="txtStore" name="storeName" type="text" maxlength="20"
							class="txt date input_text178" value="${paramBean.storeName}" />
							<i id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
							<input type="hidden" id="hdStore" name="stores" value = "${paramBean.stores}"></td>
						<td><label class="lab">划扣金额：</label> 
							<input value="${paramBean.smallApplyAmount }" onblur = "isDigit(this)" min="0" type="number" name="smallApplyAmount" class="input_text70">-
							<input  type="number" min="0" onblur = "isDigit(this)" name="bigApplyAmount" class="input_text70" value="${paramBean.bigApplyAmount}">
							</td>
						<td><label class="lab">还款状态 ：</label> <select
							class="select180" name="dictPayStatus" id="hkzt">
								<option value="">请选择</option>
								<c:forEach var="repay"
									items="${fns:getDictList('jk_repay_status')}">
									<option value="${repay.value }"
										<c:if test="${paramBean.dictPayStatus==repay.value }">selected</c:if>>${repay.label }</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr id="searchDeduct" style="display:none">
						<td><label class="lab">开户行名称：</label> 
						   <sys:multipleBank bankClick="selectBankBtn" bankName="search_applyBankName" bankId="bankId"></sys:multipleBank>
						    <input id="search_applyBankName" type="text" class="input_text178" name="applyBankName"  value='${paramBean.applyBankName}'/>&nbsp;<i id="selectBankBtn"
						    class="icon-search" style="cursor: pointer;"></i> 
						<input type="hidden" id="bankId" name='bank' value='${paramBean.bank}' />
						</td>
						<td><label class="lab">渠道：</label> <select class="select180"
							name="mark" id="loanFlag">
								<option value="">请选择</option>
								<c:forEach var="flag"
									items="${fns:getDictList('jk_channel_flag')}">
									<option value="${flag.value }"
										<c:if test="${paramBean.mark==flag.value }">selected</c:if>>${flag.label}</option>
								</c:forEach>
						</select>
						</td>
				<td><label class="lab">还款日：</label>
				  <sys:multirepaymentDate dateClick="dateClick" dateId="repayDate"></sys:multirepaymentDate>
				  <input id="repayDate"  name="paybackDay" class="input_text178" readonly ="readonly"   value="${paramBean.paybackDay}">
				  <i id="dateClick" class="icon-search" style="cursor: pointer;"></i> 
				</td>
			</tr>
					<tr id="T2" style="display:none">
						<td><label class="lab">委托充值：</label><select class="select180" id="trustRechargeFlag" name="trustRecharge">
                				<option value="">请选择</option>
		       	 				<c:forEach var="trustRecharge" items="${fns:getDictList('jk_trust_status')}">
									<option value="${trustRecharge.value }"
									<c:if test="${paramBean.trustRecharge==trustRecharge.value }">selected</c:if>>${trustRecharge.label}</option>
								</c:forEach>
               					 </select>
						</td>
						
				      <td>
						<label class="lab">回盘结果：</label><select class="select180 " name="splitbackResult">
		                    <option value="">请选择</option>
		                    <c:forEach var="result" items="${fns:getDictList('jk_counteroffer_result')}">
		                        <c:if test="${result.value ==0 ||  result.value == 4}">
										<option value="${result.value }"
										<c:if test="${paramBean.splitbackResult==result.value }">selected</c:if>>${result.label }</option>
								</c:if>
							</c:forEach>    
		                    </select>
						</td>
						
					  <td><label class="lab">模式：</label><select class="select180" name="model">
		                <option value="">请选择</option>
				        <c:forEach var="flag" items="${fns:getDictList('jk_loan_model')}">
							<option value="${flag.value }"
								<c:if test="${paramBean.model==flag.value }">selected</c:if>>
								<c:if test="${flag.value=='1' }">${flag.label}</c:if>
								<c:if test="${flag.value!='1' }">非TG</c:if>
							</option>
						</c:forEach>
		                </select>
		                </td>
		                </tr>
	                <tr id="T3" style="display:none">
		                <td><label class="lab">通联批量签约：</label> 
		                  <select class="select180" name="tlSign">
		                        <option value="">请选择</option>
			                    <c:forEach var="plat" items="${fns:getDictList('yes_no')}">
										<option value="${plat.value }"
										<c:if test="${paramBean.tlSign==plat.value }">selected</c:if>>${plat.label }</option>
							    </c:forEach>
						    </select>
						</td>
						
						<%-- <td><label class="lab">卡联实名认证：</label><select class="select180" name="realAuthen">
			                <option value="">请选择</option>
								<option value="0" <c:if test="${paramBean.realAuthen=='0'}">selected</c:if>>否</option>
								<option value="1" <c:if test="${paramBean.realAuthen=='1'}">selected</c:if>>是</option>
								<option value="2" <c:if test="${paramBean.realAuthen=='2'}">selected</c:if>>认证中</option>
			                </select>
			                </td> --%>
			                
			                <td><label class="lab">卡联是否签约：</label><select class="select180" name="klSign">
				                 <option value="">请选择</option>
				                 <option value="0" <c:if test="${paramBean.klSign=='0'}">selected</c:if>>未签约</option>
								 <option value="1" <c:if test="${paramBean.klSign=='1'}">selected</c:if>>已签约</option>
								 <option value="2" <c:if test="${paramBean.klSign=='2'}">selected</c:if>>签约失败</option>
			                  </select>
			               </td>
			               
			                 <td><label class="lab">畅捷是否签约：</label><select class="select180" name="cjSign">
				                 <option value="">请选择</option>
				                 <option value="0" <c:if test="${paramBean.cjSign=='0'}">selected</c:if>>未签约</option>
								 <option value="1" <c:if test="${paramBean.cjSign=='1'}">selected</c:if>>已签约</option>
								 <option value="2" <c:if test="${paramBean.cjSign=='2'}">selected</c:if>>签约失败</option>
			                  </select>
			               </td>
		                
						<%-- <td><label class="lab">失败原因：</label> <input type="text"
							name="failReason" class="input_text178"
							value="${paramBean.failReason }" />
						</td> --%>
					</tr>
					
					<tr id="T4" style="display:none">
					     <td><label class="lab">是否超过设置比例：</label> 
		                 <select class="select180" name="limitFlag">
		                        <option value="">请选择</option>
			                    <c:forEach var="plat" items="${fns:getDictList('yes_no')}">
										<option value="${plat.value }"
										<c:if test="${paramBean.limitFlag==plat.value }">selected</c:if>>${plat.label }</option>
							    </c:forEach>
						    </select>
					</tr>
			</table>
		</form>
	
		<div class="tright pr30 pb5">
			<input type="button" class="btn btn-primary"
				onclick="document.forms[0].submit();" value="搜索"> <input
				type="button" class="btn btn-primary" onclick="cle()" value="清除">
			<div style="float: left; margin-left: 45%; padding-top: 10px">
				<img src="${context }/static/images/up.png" id="showSearchDeductBtn"></img>
			</div>
		</div>
</div>
		<p class="mb5">
			<button class="btn btn-small"  id="deductBackbutton">批量退回</button>
			<button class="btn btn-small" id="onlinedeductForm">线上划扣</button>
			<button class="btn btn-small" id="realdeductForm">线下划扣</button>
			<button class="btn btn-small" id="modifyPlatForm">一键发送</button>
			<button class="btn btn-small" id="trustRecharge">委托充值</button>
			<button class="btn btn-small" id="trustDeduct">划拨</button>
			<button class="btn btn-small" id="exportExcel">导出</button>
			<button class="btn btn-small"  id="startRoll" onclick="startOrstop('1')">开始自动发送</button>
	        <button class="btn btn-small"  id= "stopRoll" onclick="startOrstop('0')">停止自动发送</button>
			
		<span class="red">划扣总金额:</span>
		<span class="red" id="total_money">${numTotal.total}</span>&nbsp;
		<span class="red">划扣总笔数:</span>
		<span class="red" id="total_num">${numTotal.num}</span></p>
		<form id="deductInfoForm"
			action="${ctx}/borrow/payback/dealPayback/form" method="post">
			<input type="hidden" id="deductContractCode" name="contractCode">
			<input type="hidden" id="matchingId" name="id">
		<div class="box5">
		<sys:columnCtrl pageToken="item"></sys:columnCtrl>
			<table id="tableList" class="table  table-bordered table-condensed table-hover" width="100%">
				<thead>
					<tr height="37px">
						<th><input type="checkbox" class="checkbox" id="checkAll" /></th>
						<th>合同编号</th>
						<th>客户姓名</th>
						<th>门店名称</th>
						<th>开户行名称</th>
						<th>批借期数</th>
						<th>首期还款日</th>
						<th>申请还款日期</th>
						<th>还款状态</th>
						<th>合同金额</th>
						<th>还款日</th>
						<th>期供金额</th>
						<th>申请还款金额</th>
						<th>借款状态</th>
						<th>签约平台</th>
						<th>蓝补金额</th>
						<th>回盘结果</th>
					<!-- 	<th>失败原因</th> -->
						<th>渠道</th>
						<th>模式</th>
						<th>通联批量签约</th>
						<th>委托充值结果</th>
            			<th>委托充值失败原因</th>
            		<!-- 	<th>卡联实名认证</th> -->
                        <th>卡联是否签约</th>
                        <th>畅捷是否签约</th>
                        
                       <th>是否超过设置比例</th>
					   <th>操作</th>
					</tr>
				</thead>
				<tbody id="applyPayMatchingListBody">
					<c:forEach items="${page.list}" var="item">
						<tr>
							<td><input type="checkbox" name="checkBox"
								applyDeductId="${item.id}" contractCode="${item.contractCode}"
								splitBackResult="${item.splitBackResult}"
								applyAmount="${item.applyAmount }" 
								trustRecharge="${item.loanInfo.trustRecharge}" 
								loanFlag="${item.loanInfo.loanFlag }" model="${item.model }" /> 
								<input type="hidden" value="${item.id}" /> 
								<input type="hidden" value="${item.applyAmount}" /> 
								<input type="hidden" value="${item.contractCode}" /> 
								<input type="hidden" value="${item.loanCustomer.loanCode}" />
								</td>
							<td>${item.contractCode}</td>
							<td>${item.loanCustomer.customerName}</td>
							<td>${item.orgName}</td>
							<td>${item.loanBank.bankNameLabel}</td>
							<td>${item.contract.contractMonths }</td>
							<td><fmt:formatDate
									value="${item.contract.contractReplayDay}" type="date" /></td>
							<td><fmt:formatDate value="${item.applyPayDay}" type="date" /></td>
							<td>${item.payback.dictPayStatusLabel}</td>
							<td><fmt:formatNumber
									value="${item.contract.contractAmount }" pattern='0.00' /></td>
							<td>${item.payback.paybackDay}</td>
							<td><fmt:formatNumber
									value="${item.payback.paybackMonthAmount }" pattern='0.00' /></td>
							<td><fmt:formatNumber value="${item.applyAmount }"
									pattern='0.00' /></td>
							<td>${item.loanInfo.dictLoanStatusLabel}</td>
							<td>${item.dictDealTypeLabel}</td>
							<td><fmt:formatNumber
									value="${item.payback.paybackBuleAmount }" pattern='0.00' /></td>
							<td width="41">${item.splitBackResultLabel}</td>
							<%-- <td width="41">${item.failReason}</td> --%>
							<td width="41">${item.loanInfo.loanFlagLabel}</td>
							<td width="41">${item.modelLabel}</td>
							<td width="41">${item.tlSignLabel}</td>
            				<td>${item.trustRechargeResultLabel}</td>            
            				<td>${item.trustRechargeFailReason}</td>
            			<%-- 	 <td>${item.realAuthen}</td> --%>
                             <td>${item.klSign}</td>
                             <td>${item.cjSign}</td>
                               <td>${item.limitFlag}</td>
							<td><input type="button" class="btn_edit" value="查看"
								applyPaybackId="${item.id }" name="deductInfoBtn"
								deductContractCode="${item.contractCode}">
						 <input type="button" class="btn_edit" onclick="showNoDeductPaybackHistory('','${item.id}','')" value="历史" />
	        		   <%--   <input type="button" class="btn_edit" onclick="showPaybackHis('','${item.id}','lisi');" value="已拆分历史" /> --%>
								</td>
						</tr>
					</c:forEach>
					<c:if
						test="${ page.list==null || fn:length(page.list)==0}">
						<tr>
							<td colspan="19" style="text-align: center;">没有待办数据</td>
						</tr>
					</c:if>
				</tbody>
			</table>
			</div>
		</form>
	
	<div class="pagination">${page}</div>
	
	<!--一键发送START-->
	<div id="platForm" style="display: none">
		<table class="table1 " cellpadding="0" cellspacing="0" border="0" id="platFormTb"
			width="100%">
			 <tr>
                <td><label class="lab">划扣平台：</label>
                <select class="select78" id="platValue0" onchange = "plantChage(this,0)">
                       <option value="">请选择</option>
                       <c:forEach var="plat" items="${fns:getDictList('jk_deduct_plat')}">
						<option value="${plat.value }">${plat.label }</option>
						</c:forEach>
                </select>
               </td>
                <td id="deductTime0">
	                <c:forEach var="deductTime" items="${fns:getDictList('jk_deduct_time')}">
							    <input type="radio" name="back0" value="${deductTime.value }">${deductTime.label }&nbsp;&nbsp;&nbsp;
					</c:forEach>
               </td>
                <td id="deductTimeFy0" style="display: none">
					<c:forEach var="deductTime" items="${fns:getDictList('jk_deduct_time')}">
						   <c:if test="${deductTime.value eq '0'}" >
						     <input type="radio" name="back0" value="${deductTime.value }">${deductTime.label }&nbsp;&nbsp;&nbsp;
				           </c:if>
				    </c:forEach>
			   </td>
            </tr>
            <tr>
                <td><label class="lab">划扣平台：</label>
                <select class="select78" id="platValue1" onchange = "plantChage(this,1)">
                         <option value="">请选择</option>
                       <c:forEach var="plat" items="${fns:getDictList('jk_deduct_plat')}">
						<option value="${plat.value }">${plat.label }</option>
						</c:forEach>
                </select>
               </td>
               <td id="deductTime1">
	                <c:forEach var="deductTime" items="${fns:getDictList('jk_deduct_time')}">
							    <input type="radio" name="back1" value="${deductTime.value }">${deductTime.label }&nbsp;&nbsp;&nbsp;
					</c:forEach>
               </td>
                <td id="deductTimeFy1" style="display: none">
					<c:forEach var="deductTime" items="${fns:getDictList('jk_deduct_time')}">
						   <c:if test="${deductTime.value eq '0'}" >
						     <input type="radio" name="back1" value="${deductTime.value }">${deductTime.label }&nbsp;&nbsp;&nbsp;
				           </c:if>
				    </c:forEach>
			   </td>
            </tr>
            <tr>
                <td><label class="lab">划扣平台：</label>
                <select class="select78" id="platValue2" onchange = "plantChage(this,2)">
                        <option value="">请选择</option>
                       <c:forEach var="plat" items="${fns:getDictList('jk_deduct_plat')}">
						<option value="${plat.value }">${plat.label }</option>
						</c:forEach>
                </select>
               </td>
               <td id="deductTime2">
	                <c:forEach var="deductTime" items="${fns:getDictList('jk_deduct_time')}">
							    <input type="radio" name="back2" value="${deductTime.value }">${deductTime.label }&nbsp;&nbsp;&nbsp;
					</c:forEach>
               </td>
                <td id="deductTimeFy2" style="display: none">
					<c:forEach var="deductTime" items="${fns:getDictList('jk_deduct_time')}">
						   <c:if test="${deductTime.value eq '0'}" >
						     <input type="radio" name="back2" value="${deductTime.value }">${deductTime.label }&nbsp;&nbsp;&nbsp;
				           </c:if>
				    </c:forEach>
			   </td>
            </tr>
            <tr>
                <td><label class="lab">划扣平台：</label>
                <select class="select78" id="platValue3" onchange = "plantChage(this,3)">
                        <option value="">请选择</option>
                       <c:forEach var="plat" items="${fns:getDictList('jk_deduct_plat')}">
						<option value="${plat.value }">${plat.label }</option>
						</c:forEach>
                </select>
               </td>
               <td  id="deductTime3">
	                <c:forEach var="deductTime" items="${fns:getDictList('jk_deduct_time')}">
							    <input type="radio" name="back3" value="${deductTime.value }">${deductTime.label }&nbsp;&nbsp;&nbsp;
					</c:forEach>
               </td>
               <td  id="deductTimeFy3" style="display: none">
					<c:forEach var="deductTime" items="${fns:getDictList('jk_deduct_time')}">
						   <c:if test="${deductTime.value eq '0'}" >
						     <input type="radio" name="back3" value="${deductTime.value }">${deductTime.label }&nbsp;&nbsp;&nbsp;
				           </c:if>
				    </c:forEach>
			   </td>
            </tr>
		</table>
	</div>
	<!--一键发送END-->
	<!-- 批量退回 -->
	<div  id="deductBackDiv" style="display: none">
					<table id="backTB" class="table4" cellpadding="0"
						cellspacing="0" border="0" width="100%">
						<tr>
							<td><label class="lab">退回原因：</label>
							<textarea rows="" cols="" class="textarea_refuse" id="applyBackMes" style="margin-left:60px"></textarea>
							<span class="red">最多输入1500字符</span></p>
							</td>
						</tr>
					</table>
	</div>
	<!-- 线上划扣 -->
	<div  id="onlineDeductDiv" style="display: none">
    	<table id="onlineDeductTB" class="table1 " cellpadding="0" cellspacing="0" border="0" width="100%">
		</table>
		<div id="qishu_div2">
			<table class="table1" cellpadding="0" cellspacing="0" border="0"
				width="100%">
				<tr>
			    <td><label class="lab" id="realTimePlat">划扣平台：</label><select id="realTimePlatSelect" class="select78">
				    <option value=''>请选择</option>
					<c:forEach var="plat" items="${fns:getDictList('jk_deduct_plat')}">
					<option value="${plat.value }">${plat.label }</option>
					</c:forEach>
				</tr>
			</table>
              </div>
	</div>
	<!-- 线下划扣START  -->
	<div id="realDeductDiv" style="display: none">
		<table class="table4" cellpadding="0" cellspacing="0" border="0" width="380px" id="tpTable">
			<tr>
				<td width="380px"><label class="lab"></label> 
				     <input type="radio" name="tp" value="1" onclick="javascript:hideUpload();">导出&nbsp;&nbsp;&nbsp;
					<input type="radio" name="tp" value="2" checked="checked"
					onclick="javascript:showUpload();">上传&nbsp;&nbsp;&nbsp;</td>
			</tr>
			<tr height="50px">
				<td width="380px"><label class="lab">划扣平台：</label><select class="select180"
					id="platId">
						<option value=''>请选择</option>
						<c:forEach var="plat" items="${fns:getDictList('jk_deduct_plat')}">
						  <option value="${plat.value }">${plat.label }</option>
						</c:forEach>
			</tr>
			<tr>
				<td width="380px"><label class="lab">文件格式：</label><select class="select180" id="fileFormat">
						<option>Excel</option>
						<option>txt</option>
						</td>
			</tr>
			
             <form id="fileForm" enctype="multipart/form-data">
                  <tr id="T">
					       <td width="380px"><label class="lab"></label>
					               <input type='file'  name="file" id="fileid">
					       </td>
					</tr>
			</form>
			
		</table>
	</div>
	<!-- 线下划扣END  -->
	
	
	    <!-- 委托充值 -->
		<div  id="trustRechargeDiv" style="display: none">
			<table class="table4" cellpadding="0" cellspacing="0" border="0"  id="tpTable" width="380px">
				<tr> 
					<td width="380px"><label class="lab"></label>
					<input type="radio" name="trustRd" value = "1" checked="checked" onclick="javascript:diahide();">导出&nbsp;&nbsp;&nbsp;
					<input type="radio" name="trustRd" value = "2" onclick="javascript:diashow();">上传&nbsp;&nbsp;&nbsp;
					<input type="radio" name="trustRd" value = "3" onclick="javascript:diahideAll();">线上委托充值&nbsp;&nbsp;&nbsp;</td>
				</tr>
				<tr class="DT"> 
					<td width="380px"><label class="lab">文件格式：</label><select class="select78"><option>Excel</option>
					</td>
				</tr>
				<form id ="fileForm" enctype="multipart/form-data">
				<tr id="DT" class="DT" style="display:none;">
					<td width="380px"><label class="lab"></label><input type='file' name="file" id="fileid" ></td>
				</tr>
				</form>
			</table>
		</div>
		
		     <!-- 线下划拨 -->
		<div  id="trustDeductDiv" style="display: none">
			<table class="table4" cellpadding="0" cellspacing="0" border="0"  id="tpTable" width="380px">
				<tr> 
					<td width="380px"><label class="lab"></label>
					<input type="radio" name="opeType" value = "1" checked="checked" onclick="javascript:trustDeductHide();">导出&nbsp;&nbsp;&nbsp;
					<input type="radio" name="opeType" value = "2" onclick="javascript:trustDeductshow();">上传&nbsp;&nbsp;&nbsp;
					<input type="radio" name="opeType" value = "3" onclick="javascript:trustDeductHideAll();">线上划拨&nbsp;&nbsp;&nbsp;</td>
				</tr>
				<tr class="ODT"> 
					<td width="380px"><label class="lab">文件格式：</label><select class="select78"><option>Excel</option>
					</td>
				</tr>
				<form id ="fileForm" enctype="multipart/form-data">
				<tr id="ODT" class="ODT" style="display:none">
					<td width="380px"><label class="lab"></label><input type='file' name="file" id="fileid" ></td>
				</tr>
				</form>
			</table>
		</div>
	
		</div>
			</div>
<form method="post"  id="onekeySned">
<input id='deductId' name='deductType' type ='hidden'>
</form>
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