<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>集中划扣已拆分列表页面</title>
<script type="text/javascript" src="${context}/js/payback/paybacksplit.js"></script>
<script type="text/javascript" src="${context}/js/payback/ajaxfileupload.js"></script>
<script src="${context}/js/payback/historicalRecords.js" type="text/javascript"></script>
<script src="../../../../static/ckfinder/plugins/gallery/colorbox/jquery.colorbox-min.js"></script>
<script src='${context}/js/common.js' type="text/javascript"></script>
<link media="screen" rel="stylesheet" href="../../../../static/ckfinder/plugins/gallery/colorbox/colorbox.css" />
<script type="text/javascript">
function page(n,s) {
	if (n) $("#pageNo").val(n);
	if (s) $("#pageSize").val(s);
	$("#CenterapplyPayForm").attr("action", "${ctx}/borrow/payback/payBackSplitapply/paybackSplitListApply");
	$("#CenterapplyPayForm").submit();
	return false;
}

$(document).ready(function() {
	loan.getstorelsit("txtStore","hdStore");
	
	$('#cleBtn').bind('click',function(){
		 $(':input','#CenterapplyPayForm')
		  .not(':button, :reset,:hidden')
		  .val('')
		  .removeAttr('checked')
		  .removeAttr('selected');
	});
});
$(document).ready(function() {
	deductpayback.sendAllPlat();
	deductpayback.exportAndImport();
	deductpayback.onlinededuct();
	deductpayback.deductBack();
	deductpayback.trustRecharge();
	deductpayback.deductTrust();
	var msg = "${message}";
	if (msg != "" && msg != null) {
		art.dialog.alert(msg);
	};
	var errormsg = "${errormessage}";
	if (msg != "" && msg != null) {
		art.dialog.alert(errormsg);
	};
	
	
	$('#en').bind('blur',function(){
		var start=$('#st').val();
		var end=$('#en').val();
		
		
		if(start > end){
			art.dialog.alert('输入数据有误，确保下限值小于上限值！');
		}
	});
});

</script>
</head>
<body>
	<div class="control-group" >
            <form method="post" action="${ctx}/borrow/payback/payBackSplitapply/paybackSplitListApply" id="CenterapplyPayForm">
            <input id="pageNo" name="pageNo" type="hidden"	value="${page.pageNo}" /> 
		    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
		    <input id="ids" name="search_id" type="hidden"  />
		    <input id="plat" name="search_plat" type="hidden"  />
		    <input id="rule" name="search_rule" type="hidden"  />
		    <input id="deductType" name="search_deductType" type="hidden" />
		    <input id="backmsg" name="search_backmsg" type="hidden" />
      <table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td><label class="lab">客户姓名：</label><input type="text" class="input_text178" name="search_customerName" value = "${paramMap.customerName }"></td>
                <td><label class="lab">合同编号：</label><input type="text" class="input_text178" name="search_contractCode" value = "${paramMap.contractCode }"></td>
                <td><label class="lab">签约平台：</label>
                <sys:multireSignPlatform platClick="platClick"  platId="platId" platName ='platName'></sys:multireSignPlatform>
                <input id="platName" type="text" class="input_text178" name="search_dictDealTypeName"  value='${paramMap.dictDealTypeName}'>&nbsp;
                <i id="platClick" class="icon-search" style="cursor: pointer;"></i> 
				<input type="hidden" id="platId" name='search_dictDealTypeId' value='${paramMap.dictDealTypeId}'>
                </td>
            </tr>
            <tr>
                <td><label class="lab">门店：</label>
                <input id="txtStore" name="search_storesName"
						type="text" maxlength="20" class="txt date input_text178"
						value="${paramMap.storesName}" /> <i id="selectStoresBtn"
						class="icon-search" style="cursor: pointer;"></i>
						<input type="hidden" id="hdStore" name='search_stores' value="${paramMap.stores}">
                </td>
                <td><label class="lab">回盘结果：</label><select class="select180 " name="search_splitBackResult">
	                    <option value="">请选择</option>
	                    <c:forEach var="result" items="${fns:getDictList('jk_counteroffer_result')}">
	                         <c:if test="${result.value ==0 || result.value ==3 || result.value == 4 || result.value == 5 }">
							    <option value="${result.value }"
							   <c:if test="${paramMap.splitBackResult==result.value }">selected</c:if>>${result.label }</option>
							</c:if>
						</c:forEach>    
	                </select>
                </td>
<%--                 <td><label class="lab">失败原因：</label><input type="text" class="input_text178" name="search_splitFailResult" value = "${paramMap.splitFailResult }">
				</td>
 --%>    
               <td><label class="lab">划扣金额：</label>
                <input type="text" id="st" class="input_text70" type='number' name="search_minsplitAmount" value ="${paramMap.minsplitAmount}">-
                <input type="text" id="en" class="input_text70"  type='number' name="search_maxsplitAmount" value ="${paramMap.maxsplitAmount}"></td>
         </tr>
            <tr id="T1" style="display:none">
                
                <td><label class="lab" >开户行名称：</label>
                <sys:multipleBank bankClick="selectBankBtn" bankName="search_applyBankName" bankId="bankId"></sys:multipleBank>
                <input id="search_applyBankName" type="text" class="input_text178" name="search_applyBankName"  value='${paramMap.applyBankName}'>&nbsp;<i id="selectBankBtn"
						class="icon-search" style="cursor: pointer;"></i> 
						<input type="hidden" id="bankId" name='search_bank' value='${paramMap.bank}'>
                </td>
                <td><label class="lab" >来源系统：</label><select class="select180" name="search_dictSourceType">
                <option value="">请选择</option>
					<c:forEach var="sys" items="${fns:getDictList('jk_new_old_sys_flag')}">
					<option value="${sys.value }"
					<c:if test="${paramMap.dictSourceType==sys.value }">selected</c:if>>${sys.label }</option>
					</c:forEach>
                </select></td>
                <td><label class="lab">渠道：</label><select class="select180" name="search_mark">
                <option value="">请选择</option>
		        <c:forEach var="flag" items="${fns:getDictList('jk_channel_flag')}">
		            <c:if test="${flag.label != 'XT02'}">
					<option value="${flag.value }"
					<c:if test="${paramMap.mark==flag.value }">selected</c:if>>${flag.label}</option>
					</c:if>
				</c:forEach>
                </select>
                </td>
            </tr>
            <tr id="T2" style="display:none">
               
				 <td><label class="lab">还款日：</label>
				  <sys:multirepaymentDate dateClick="dateClick" dateId="repayDate"></sys:multirepaymentDate>
				  <input id="repayDate"  name="search_dayName" class="input_text178" readonly ="readonly"   value="${paramMap.dayName}">
				  <i id="dateClick" class="icon-search" style="cursor: pointer;"></i> 
				</td>
				<td><label class="lab">委托充值：</label><select class="select180" id="trustRechargeFlag" name="search_trustRecharge">
                <option value="">请选择</option>
		        <c:forEach var="trustRecharge" items="${fns:getDictList('jk_trust_status')}">
					<option value="${trustRecharge.value }"
					<c:if test="${paramMap.trustRecharge==trustRecharge.value }">selected</c:if>>${trustRecharge.label}</option>
				</c:forEach>
                </select>
                </td>
                <td><label class="lab">失败原因：</label> 
				    <select class="select180" name="search_qfailReason" id ="search_qfailReasonId">
	                  <option value="">请选择</option>
						<option value="0" <c:if test="${paramMap.qfailReason=='0'}">selected</c:if>>不足</option>
						<option value="1" <c:if test="${paramMap.qfailReason=='1'}">selected</c:if>>其他</option>
	                </select>
	                 <input type ="text"  name ="search_failReasonText"   value ="${paramMap.failReasonText}">
				</td>
            </tr>
            <tr id="T3" style="display:none">
                <td><label class="lab">模式：</label><select class="select180" name="search_model">
                <option value="">请选择</option>
		        <c:forEach var="flag" items="${fns:getDictList('jk_loan_model')}">
					<option value="${flag.value }"
						<c:if test="${paramMap.model==flag.value }">selected</c:if>>
						<c:if test="${flag.value=='1' }">${flag.label}</c:if>
						<c:if test="${flag.value!='1' }">非TG</c:if>
					</option>
				</c:forEach>
                </select>
                </td>
                
               <td><label class="lab">逾期天数：</label>
                <select class="select180" name="search_queryRule">
                    <option value="">请选择</option>
					<option <c:if test="${paramMap.queryRule=='0'}">selected</c:if> value="0" > &lt;X </option>
					<option <c:if test="${paramMap.queryRule=='1'}">selected</c:if> value="1" > &gt;=X </option>
                </select>
                <input type ="number"  name ="search_overdueDays"  min="1" max="1000" value ="${paramMap.overdueDays}">
                </td>
                <td><label class="lab">通联批量签约：</label> 
                  <select class="select180" name="search_tlSign">
                        <option value="">请选择</option>
	                    <c:forEach var="plat" items="${fns:getDictList('yes_no')}">
								<option value="${plat.value }"
								<c:if test="${paramMap.tlSign==plat.value }">selected</c:if>>${plat.label }</option>
					    </c:forEach>
				    </select>
				</td>
            </tr>
            
            <tr id="T4" style="display:none">
                <td><label class="lab">中金余额不足次数：</label><select class="select180" name="search_cpcnCount">
                <option value="">请选择</option>
					<option value="0" <c:if test="${paramMap.cpcnCount=='0'}">selected</c:if>>0</option>
					<option value="1" <c:if test="${paramMap.cpcnCount=='1'}">selected</c:if>>1</option>
					<option value="2" <c:if test="${paramMap.cpcnCount=='2'}">selected</c:if>>2</option>
					<option value="3" <c:if test="${paramMap.cpcnCount=='3'}">selected</c:if>>3</option>
                </select>
                </td>
                
                <td><label class="lab">借款状态：</label><select class="select180" name="search_loanStatus">
	                 <option value="">请选择</option>
	                 <option value="87" <c:if test="${paramMap.loanStatus=='87'}">selected</c:if>>还款中</option>
					 <option value="88" <c:if test="${paramMap.loanStatus=='88'}">selected</c:if>>逾期</option>
					 <option value="89" <c:if test="${paramMap.loanStatus=='89'}">selected</c:if>>结清</option>
					 <option value="90" <c:if test="${paramMap.loanStatus=='90'}">selected</c:if>>提前结清</option>
					 <option value="91" <c:if test="${paramMap.loanStatus=='91'}">selected</c:if>>提前结清待审核</option>
					 <option value="92" <c:if test="${paramMap.loanStatus=='92'}">selected</c:if>>结清待确认</option>
                 </select>
                
                 <td><label class="lab">累计逾期期数：</label>
                    <input type ="number"  name ="search_overCount"  min="1" max="1000" value ="${paramMap.overCount}">
                 </td>
            </tr>
            
            <tr id="T5" style="display:none">
               <%--  <td><label class="lab">卡联实名认证：</label><select class="select180" name="search_realAuthen">
                <option value="">请选择</option>
					<option value="0" <c:if test="${paramMap.realAuthen=='0'}">selected</c:if>>否</option>
					<option value="1" <c:if test="${paramMap.realAuthen=='1'}">selected</c:if>>是</option>
					<option value="2" <c:if test="${paramMap.realAuthen=='2'}">selected</c:if>>认证中</option>
                </select>
                </td> --%>
                
                <td><label class="lab">卡联是否签约：</label><select class="select180" name="search_klSign">
	                 <option value="">请选择</option>
	                 <option value="0" <c:if test="${paramMap.klSign=='0'}">selected</c:if>>未签约</option>
					 <option value="1" <c:if test="${paramMap.klSign=='1'}">selected</c:if>>已签约</option>
					 <option value="2" <c:if test="${paramMap.klSign=='2'}">selected</c:if>>签约失败</option>
                  </select>
                </td>
                
                 <td><label class="lab">通联余额不足次数：</label><select class="select180" name="search_tlCount">
                   <option value="">请选择</option>
					<option value="0" <c:if test="${paramMap.tlCount=='0'}">selected</c:if>>0</option>
					<option value="1" <c:if test="${paramMap.tlCount=='1'}">selected</c:if>>1</option>
					<option value="2" <c:if test="${paramMap.tlCount=='2'}">selected</c:if>>2</option>
					<option value="3" <c:if test="${paramMap.tlCount=='3'}">selected</c:if>>3</option>
                   </select>
                </td>

                
                 <td><label class="lab">畅捷是否签约：</label><select class="select180" name="search_cjSign">
	                 <option value="">请选择</option>
	                 <option value="0" <c:if test="${paramMap.cjSign=='0'}">selected</c:if>>未签约</option>
					 <option value="1" <c:if test="${paramMap.cjSign=='1'}">selected</c:if>>已签约</option>
					 <option value="2" <c:if test="${paramMap.cjSign=='2'}">selected</c:if>>签约失败</option>
                  </select>
                </td>
            </tr>
        </table>
        </form>
        <div class="tright pr30 pb5 ">
					<button class="btn btn-primary" onclick="document.forms[0].submit();">搜索</button>
					<button class="btn btn-primary"  id="cleBtn">清除</button>
    <div class="xiala">
	   <img src="${context}/static/images/up.png" id="showMore"></img> 
	 </div>
	 </div>
	 </div>
	 <p class="mb5">
	 <button class="btn btn-small" id="backFlagBtn">批量退回 </button>
     <button class="btn btn-small" id="onlinedeductForm">线上划扣</button>
	 <button class="btn btn-small" id="realdeductForm" >线下划扣</button>
	 <button class="btn btn-small" id="modifyPlatForm">一键发送</button>
	 <button class="btn btn-small"  id="stopRoll" onclick="startOrstop('0')">停止滚动</button>
	 <button class="btn btn-small"  id= "startRoll" onclick="startOrstop('1')">开始滚动</button>
	 <button class="btn btn-small" id="trustRecharge">委托充值</button>
	 <button class="btn btn-small" id="trustDeduct">划拨</button>
	  <button class="btn btn-small" id="exportExcel">导出</button>
		 <span class="red">划扣总金额:</span>
		<span class="red" id="total_money">${numTotal.total}</span>&nbsp;
		<span class="red">划扣总笔数:</span>
		<span class="red" id="total_num">${numTotal.num}</span>
		<span class="red">实还总金额:</span>
		<span class="red" id="total_reallyMoney">${numTotal.reallyTotal}</span>&nbsp;
		</p>
	<div class="box5">
	<sys:columnCtrl pageToken="paybacksplit"></sys:columnCtrl>
    <table id="tableList"  class="table  table-bordered table-condensed table-hover" width="100%">
       <thead>
        <tr>
            <th><input type="checkbox" class="checkbox" id="checkAll"/></th>
            <th>序号</th>
            <th>合同编号</th>
            <th>客户姓名</th>
            <th>门店名称</th>
            <th>开户行名称</th>
            <th>批借期数</th>
            <th>首期还款期</th>
            <th>实还金额</th>
			<th>期供</th>
			<th>当期未还期供</th>
			<th>当期已还期供</th>
			<th>划扣金额</th>
            <th>还款类型</th>
            <th>还款状态</th>          
            <th>还款日</th>
            <th>借款状态</th>
            <th>逾期天数</th>
            <th>累计逾期期数</th>
            <th>签约平台</th>
            <th>回盘结果</th>
            <th>失败原因</th>
            <th>渠道</th>
            <th>模式</th>
            <th>委托充值结果</th>
            <th>委托充值失败原因</th>
            <th>中金余额不足次数</th>
            <th>通联余额不足次数</th>
            <th>通联批量签约</th>
         <!--    <th>卡联实名认证</th> -->
            <th>卡联是否签约</th>
            <th>畅捷是否签约</th>
            <th>操作</th>
        </tr>
        </thead>
        <c:forEach var="payBackSplit" items="${page.list }" varStatus="sta">
		    <tr>
            <td>
            <input type="checkbox" name="checkBox" value="${payBackSplit.splitAmount}" reallyAmount="${payBackSplit.applyReallyAmount}"    splitBackResult ="${payBackSplit.splitBackResult}"
            	 trustRecharge="${payBackSplit.loanInfo.trustRecharge}" loanFlag="${payBackSplit.loanInfo.loanFlag }" model="${payBackSplit.model}"/>
            <input type="hidden" name="id" value="${payBackSplit.id }"/>
            </td>
            <td>${sta.count }</td> 
            <td>${payBackSplit.payback.contractCode}</td>
            <td>${payBackSplit.loanCustomer.customerName}</td>
            <td>${payBackSplit.org.name}</td>
            <td>${payBackSplit.loanBank.bankNameLabel}
          </td>
            <td>${payBackSplit.contract.contractMonths}</td>
            
            <td>
            <fmt:formatDate value="${payBackSplit.contract.contractReplayDay}" type="date" />
           </td>
            
            <td><fmt:formatNumber value="${payBackSplit.applyReallyAmount}" pattern='0.00'/></td>
           
            <td><fmt:formatNumber value="${payBackSplit.payback.paybackMonthAmount}" pattern='0.00'/></td>
            
            <td>
            <fmt:formatNumber value="${payBackSplit.payback.paybackMonthAmount-
            payBackSplit.paybackMonth.monthCapitalPayactual-
            payBackSplit.paybackMonth.monthInterestPayactual-payBackSplit.paybackMonth.actualMonthFeeService}" pattern='0.00'/>
           </td>
            
            <td>
            <fmt:formatNumber value="${payBackSplit.paybackMonth.monthCapitalPayactual+
            payBackSplit.paybackMonth.monthInterestPayactual+payBackSplit.paybackMonth.actualMonthFeeService}" pattern='0.00'/>
            </td>
            <td>
            <fmt:formatNumber value="${payBackSplit.splitAmount}" pattern='0.00'/>
            </td>
            <td>集中划扣</td>
			<td>
			${payBackSplit.payback.dictPayStatusLabel}
			</td>
            <td>
            <fmt:formatDate 
            value="${payBackSplit.paybackMonth.monthPayDay}" type="date"  pattern="dd"/>
            </td>
            <td>
			${payBackSplit.loanInfo.dictLoanStatusLabel}
			</td>
			 <td>
			 ${payBackSplit.overdueDays}
			</td>
			 <td>
			 ${payBackSplit.overCount}
			</td>
			<td>${payBackSplit.dictDealTypeLabel }</td>
            <td>${payBackSplit.splitBackResultLabel}</td>
            <td>${payBackSplit.failReason}</td>
            <td>${payBackSplit.loanInfo.loanFlagLabel}</td> 
             <td>${payBackSplit.modelLabel}</td>            
            <td>${payBackSplit.trustRechargeResultLabel}</td>
            <td>${payBackSplit.trustRechargeFailReason}</td>
            <td>${payBackSplit.cpcnCount}</td>
            <td>${payBackSplit.tlCount}</td>
            <td>${payBackSplit.tlSignLabel}</td>
         <%--    <td>${payBackSplit.realAuthen}</td> --%>
            <td>${payBackSplit.klSign}</td>
            <td>${payBackSplit.cjSign}</td>
            <td><input type="button" class="btn_edit" onclick="showPaybackHis('','${payBackSplit.id}','');" value="历史" />
	        <input type="button" class="btn_edit" onclick="showPaybackHis('','${payBackSplit.id}','lisi');" value="已拆分历史" /></td>
        </tr>
       </c:forEach>
       <c:if test="${page.list==null || fn:length(page.list)==0}">
						<tr>
							<td colspan="25">没有待办数据</td>
						</tr>
	  </c:if>
	  </table>
	</div>
 <div class="pagination">${page}</div>
<!-- 修改划扣平台弹框 -->   
<div  id="platForm"  style="display: none" >
        <table id="platFormTb" class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td><label class="lab">划扣平台：</label>
                <select class="select78" id="platValue0" onchange = "plantChage(this,0)">
                       <option value="">请选择</option>
                       <c:forEach var="plat" items="${fns:getDictList('jk_deduct_plat')}">
						<c:if test="${plat.label!='ZCJ金账户' }">
							<option value="${plat.value }">${plat.label }</option>
						</c:if>
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
                        <c:if test="${plat.label!='ZCJ金账户' }">
							<option value="${plat.value }">${plat.label }</option>
						</c:if>
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

<!-- 批量退回 -->
<div id="backDiv" style="display: none">
			 <p><span class="red">*</span>退回原因：
				 <textarea rows="" cols="" class="textarea_refuse" id="textarea" style="margin-left:60px"></textarea>
				<span class="red">最多输入1500字符</span></p>
</div>
	<!-- 线上划扣弹框 -->
	<div  id="onlineDeductDiv" style="display: none">
         <table id="onlineDeductTB" cellpadding="0" cellspacing="0" border="0" width="100%"> 
		</table>
		
	   <div    id="qishu_div2">
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr> 
			   	<td><label class="lab" id="realTimePlat">划扣平台：</label><select id="realTimePlatSelect" class="select78">
					  <option value=''>请选择</option>
					  <c:forEach var="plat" items="${fns:getDictList('jk_deduct_plat')}">
					  <c:if test="${plat.label!='ZCJ金账户' }">
						<option value="${plat.value }">${plat.label }</option>
					  </c:if>
				      </c:forEach>
				</td>
            </tr>
        </table>
	  </div>
 </div>
<!-- 线下划扣弹框 -->
<div  id="realDeductDiv" style="display: none">
        <table class="table4" cellpadding="0" cellspacing="0" border="0"  id="tpTable" width="380px">
			<tr> 
			   	<td width="380px"><label class="lab"></label>
					<input type="radio" name="tp" value = "1" onclick="javascript:diahide();">导出&nbsp;&nbsp;&nbsp;
					<input type="radio" name="tp" value = "2" checked="checked" onclick="javascript:diashow();">上传&nbsp;&nbsp;&nbsp;</td>
            </tr>
             <tr  height="50px">
                <td width="380px"><label class="lab">划扣平台：</label><select class="select78" id="platId">
                    <option value=''>请选择</option>
					<c:forEach var="plat" items="${fns:getDictList('jk_deduct_plat')}">
					<c:if test="${plat.label!='ZCJ金账户' }">
						<option value="${plat.value }">${plat.label }</option>
					</c:if>
				    </c:forEach>
				</td>
            </tr>
            <tr> 
			   	<td width="380px"><label class="lab">文件格式：</label><select id="fileFormat">
			   	                      <option>Excel</option>
			   	                      <option>txt</option>
			   	                    </select>
							</td>
            </tr>
            <form id ="fileForm" enctype="multipart/form-data">
            <tr id="DT">
                <td width="380px"><label class="lab"></label><input type='file' name="file" id="fileid" ></td>
            </tr>
            </form>
         </table>
 </div>
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

<!-- 线上划拨 -->
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
<form method="post"  id="onekeySned">
<input id='deductId' name='search_deductType' type ='hidden'>
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