<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<html>
<head>
<meta name="decorator" content="default"/>
<title>征信报告</title>
<script type="text/javascript" src="${context}/js/credit/common.js"></script>
<script type="text/javascript" src="${context}/js/credit/creditReport.js"></script>
<script type="text/javascript" src="${context}/js/credit/cardReport.js"></script>
<script type="text/javascript" src="${context}/js/credit/creditGuarantee.js"></script>
<script type="text/javascript" src='${context}/js/validateCredit.js'></script>

</head>
<body>
<div class="box1" style="background:none;border:none;margin-top:27px;">

<table class="table2" width="100%" cellspacing="0" cellpadding="0" border="0"  >
		<tr>
			<td style="text-align:left;border-right:0px" colspan="14">
				<h3>贷款明细信息（一）</h3>
			</td>
			<td style="text-align:right;border-left:0px">
				<input class="btn btn-small" id="addrowloanoneBtn" type="button" value="新增贷款"  onclick="addData();" >
				<!-- <input class="btn btn-small" id="saverowloanoneBtn" type="button" value="保存贷款" onClick="saveData();" > -->
			</td>
		</tr>
</table>
<form id="loanoneForm" style="overflow-x:auto">
	<table id="loanoneTable" class="table  table-bordered table-condensed" width="100%" cellspacing="0" cellpadding="0" border="0"  >
		<thead>
		<tr>
			<th>编号</th>
			<th>贷款类型</th>
			<th>担保方式</th>
			<th>币种</th>
			<th>账户状态</th>
			<th>还款频率</th>
			<th>五级分类</th>
			<th>还款月数</th>
			<th>发放日期</th>
			<th>到期日期</th>
			<th>合同金额</th>
			<th>贷款余额</th>
			<th>信息获取时间</th>
			<th>删除</th>
			<th>保存</th>
		</tr>
		</thead>
        <tbody>
		</tbody>
	</table>
	</form>
		<table id="loanoneHide" class="hide">
	<tr id="loanoneTable_trRow_1" class="CaseRow">
			<td>
	    	<input style="width:30px;!important" disabled=true name="num" type="text" class="input_text50"/>
			<input type="hidden" name="id" value=""/>
		
			</td>
			<td>
			<select name="loanType" class="required">
	    		   <option value="" >请选择</option>		
	    		<c:forEach var="item" items="${fns:getDictList('jk_credit_loan_type_flag')}" >
	    		   <option value="${item.value}" >${item.label} </option>								
				</c:forEach>
	         </select>
			</td>
			<td>
			<select name="guaranteeType" id="guaranteeType-new" class="required">
	    		   <option value="" >请选择</option>		
	    		<c:forEach var="item" items="${fns:getDictList('jk_guarantee_type')}" >
	    		   <option value="${item.value}" >${item.label} </option>								
				</c:forEach>
	         </select>
			</td>
			<td>
			<select name="currency" id="currency-new" class="required">
	    		   <option value="" >请选择</option>		
	    		<c:forEach var="item" items="${fns:getDictList('jk_credit_currency')}" >
	    		   <option value="${item.value}" >${item.label} </option>								
				</c:forEach>
	         </select>
			</td>
			<td>
			<select name="accountStatu" id="accountStatus-new" class="required">
	    		   <option value="" >请选择</option>		
	    		<c:forEach var="item" items="${fns:getDictList('jk_credit_loaninfo_accountstatus')}" >
	    		   <option value="${item.value}" >${item.label} </option>								
				</c:forEach>
	         </select>
			</td>
			<td>
			<select name="repayFrequency" id="repayFrequency-new" class="required">
	    		   <option value="" >请选择</option>		
	    		<c:forEach var="item" items="${fns:getDictList('jk_repay_rate')}" >
	    		   <option value="${item.value}" >${item.label} </option>								
				</c:forEach>
	         </select>
			</td>
			<td>
			<select name="levelClass" id="levelClass-new" class="required">
	    		   <option value="" >请选择</option>		
	    		<c:forEach var="item"  items="${fns:getDictList('jk_enterprise_level')}" >
	    		   <option value="${item.value}" >${item.label} </option>								
				</c:forEach>
	         </select>
			</td>
			
			<td><input type="text" name="repayMonths" money="1" maxlength="20"  class="input_text50 required" onkeyup="this.value=this.value.replace(/^[0]/g,'');"/></td>
			<td><input type="text" name="releaseDay" id="releaseDay-new" style="width:100px;" class="input_text178 required" onclick="WdatePicker()"  value=""></td>
			<td><input type="text" name="actualDay" id="actualDay-new" style="width:100px;" class="input_text178 required" onclick="WdatePicker()"  value=""></td>
			<td><input type="text" name="conteactAmount" money="1" maxlength="20" class="input_text50 required"/></td>
			<td><input type="text" name="loanBalance" money="1" maxlength="20" class="input_text50 required"/></td>
			<td><input type="text" name="getingTime" id="getingTime-new" style="width:100px;" class="input_text178 required" onclick="WdatePicker()"  value=""></td>
		
			<td><input type="button" class="btn_edit" name="deleteName" value="删除" /></td>
			<td><input type="button" class="btn_edit" name="saveName" value="保存" /></td>
		</tr>
		</table>
	
	
	
	<table class="table2" width="100%" cellspacing="0" cellpadding="0" border="0"  >
		<tr>
			<td style="text-align:left;" colspan="14">
				<h3>贷款明细信息（二）</h3>
			</td>
		</tr>
	</table>
	<form id="loantwoForm" style="overflow-x:auto">
	<table id="loantwoTable" class="table  table-bordered table-condensed" width="100%" cellspacing="0" cellpadding="0" border="0"  >
		<thead>
		<tr>
			<th>编号</th>
			<th>剩余还款月数</th>
			<th>最近一次实际还款日</th>
			<th>本月应还款金额</th>
			<th>本月实际还款金额</th>
			<th>当前逾期期数</th>
			<th>当前逾期总额</th>
			<th>累计逾期次数</th>
			<th>最高逾期数</th>
			<th>逾期31-60天未还本金</th>
			<th>逾期61-90天未还本金</th>
			<th>逾期91-180天未还本金</th>
			
			<th>逾期180天以上未还本金</th>
				</tr>
		</thead>
		 <tbody>
		</tbody>
	</table>
	</form>
	
	<table  id="loantwoHide" class="hide">

	<tr id="loantwoTable_trRow_1" class="CaseRow">
			<td>
			<input style="width:30px;!important" disabled=true name="num" type="text" class="input_text50 required"/>
			<input type="hidden" name="id" />
			<input type="hidden" name="relationId" />
			</td>
			<td>
			<input type="text" name="repayMonths" money="1" maxlength="20" class="input_text50 required"/>
			</td>
			<td><input type="text" name="realRepayDay"  style="width:80px;" class="input_text178 required" onclick="WdatePicker()"   /></td>
			<td>
		    <input type="text" name="shouldRepayAmount" money="1" maxlength="20" class="input_text50 required"/>
		
			</td>
			<td><input type="text" name="realRepayAmount"  money="1" maxlength="20"    class="input_text50 required"/></td>
			<td><input type="text" name="currentOverdue"  money="1" maxlength="20"    class="input_text50 required integer"/></td>
			<td><input type="text" name="currentOverdueTotal"  money="1" maxlength="20"    class="input_text50 required"/></td>
			<td><input type="text" name="overdueNoTotal"  money="1" maxlength="20"     class="input_text50 required integer"/></td>
			<td><input type="text" name="overdueNoHighest"   money="1" maxlength="20"   class="input_text50 required integer"/></td>
			<td><input type="text" name="overduePrincipalLevel1"  money="1" maxlength="20"    class="input_text50 required"/></td>
			<td><input type="text" name="overduePrincipalLevel2"  money="1" maxlength="20"    class="input_text50 required"/></td>
			<td><input type="text" name="overduePrincipalLevel3" money="1" maxlength="20"    class="input_text50 required"/></td>
			<td><input type="text" name="overduePrincipalLevel4" money="1" maxlength="20"    class="input_text50 required"/></td>
		
		</tr>
	
	</table>
	
	</br>
	<table class="table2" width="100%" cellspacing="0" cellpadding="0" border="0"  >
		<tr>
			<td style="text-align:left;">
				<h3>贷款最近24个月每个月的还款状态记录</h3>
			</td>
		</tr>
	</table>
	<form id="periodsForm" style="overflow-x:auto">
	<table id="loan24Table" class="table  table-bordered table-condensed" width="100%" cellspacing="0" cellpadding="0" border="0"  >
		<thead>
		<tr>
			<th>编号<font color="red">*</font></th>
			<th>结算年月<font color="red">*</font></th>
			<th>24<font color="red">*</font></th>
			<th>23<font color="red">*</font></th>
			<th>22<font color="red">*</font></th>
			<th>21<font color="red">*</font></th>
			<th>20<font color="red">*</font></th>
			<th>19<font color="red">*</font></th>
			<th>18<font color="red">*</font></th>
			<th>17<font color="red">*</font></th>
			<th>16<font color="red">*</font></th>
			<th>15<font color="red">*</font></th>
			<th>14<font color="red">*</font></th>
			<th>13<font color="red">*</font></th>
			<th>12<font color="red">*</font></th>
			<th>11<font color="red">*</font></th>
			<th>10<font color="red">*</font></th>
			<th>09<font color="red">*</font></th>
			<th>08<font color="red">*</font></th>
			<th>07<font color="red">*</font></th>
			<th>06<font color="red">*</font></th>
			<th>05<font color="red">*</font></th>
			<th>04<font color="red">*</font></th>
			<th>03<font color="red">*</font></th>
			<th>02<font color="red">*</font></th>
			<th>01<font color="red">*</font></th>
		</tr>
		</thead>
		
		<tbody>
		</tbody>
	</table>
	</form>
	<div>
	<table id="periodsHide" class="hide">
		<tbody>
			<tr>
				<td>
					<input style="width:30px;!important" name="num" disabled=true type="text" class="input_text50"/>
					<input name="relationId" disabled=true type="hidden" class="input_text50"/>
				</td>
				<td><input type="text" name="balanceTime"  style="width:100px;" maxlength="20" class="input_text178 required" onclick="WdatePicker()" value="" /></td>
				<td><input name="qs24" style="width:25px;!important" type="text" maxlength="20"  class="input_text50 validate24 required"/></td>
				<td><input name="qs23" style="width:25px;!important" type="text" maxlength="20" class="input_text50 validate24 required"/></td>
				<td><input name="qs22" style="width:25px;!important" type="text" maxlength="20" class="input_text50 validate24 required"/></td>
				<td><input name="qs21" style="width:25px;!important" type="text" maxlength="20" class="input_text50 validate24 required"/></td>
				<td><input name="qs20" style="width:25px;!important" type="text" maxlength="20" class="input_text50 validate24 required"/></td>
				<td><input name="qs19" style="width:25px;!important" type="text" maxlength="20" class="input_text50 validate24 required"/></td>
				<td><input name="qs18" style="width:25px;!important" type="text" maxlength="20" class="input_text50 validate24 required"/></td>
				<td><input name="qs17" style="width:25px;!important" type="text" maxlength="20" class="input_text50 validate24 required"/></td>
				<td><input name="qs16" style="width:25px;!important" type="text" maxlength="20" class="input_text50 validate24 required"/></td>
				<td><input name="qs15" style="width:25px;!important" type="text" maxlength="20" class="input_text50 validate24 required"/></td>
				<td><input name="qs14" style="width:25px;!important" type="text" maxlength="20" class="input_text50 validate24 required"/></td>
				<td><input name="qs13" style="width:25px;!important" type="text" maxlength="20" class="input_text50 validate24 required"/></td>
				<td><input name="qs12" style="width:25px;!important" type="text" maxlength="20" class="input_text50 validate24 required"/></td>
				<td><input name="qs11" style="width:25px;!important" type="text" maxlength="20" class="input_text50 validate24 required"/></td>
				<td><input name="qs10" style="width:25px;!important" type="text" maxlength="20" class="input_text50 validate24 required"/></td>
				<td><input name="qs9" style="width:25px;!important" type="text" maxlength="20" class="input_text50 validate24 required"/></td>
				<td><input name="qs8" style="width:25px;!important" type="text" maxlength="20" class="input_text50 validate24 required"/></td>
				<td><input name="qs7" style="width:25px;!important" type="text" maxlength="20" class="input_text50 validate24 required"/></td>
				<td><input name="qs6" style="width:25px;!important" type="text" maxlength="20" class="input_text50 validate24 required"/></td>
				<td><input name="qs5" style="width:25px;!important" type="text" maxlength="20" class="input_text50 validate24 required"/></td>
				<td><input name="qs4" style="width:25px;!important" type="text" maxlength="20" class="input_text50 validate24 required"/></td>
				<td><input name="qs3" style="width:25px;!important" type="text" maxlength="20" class="input_text50 validate24 required"/></td>
				<td><input name="qs2" style="width:25px;!important" type="text" maxlength="20" class="input_text50 validate24 required"/></td>
				<td><input name="qs1" style="width:25px;!important" type="text" maxlength="20" class="input_text50 validate24 required"/></td>
			</tr>
		</tbody>
	</table>
</div>



   
	<table class="  table-bordered table-condensed" width="100%" cellspacing="0" cellpadding="0" border="0"  >
		<tr>
			<td style="text-align:left;border-right:0px" >
				<h3>信用卡明细信息（一）</h3>
			</td>
			<td style="text-align:right;border-left:0px">
				<input class="btn btn-small" id="addrowcardoneBtn" type="button" value="新增信用卡"  onclick="addCardData();" >
				<!-- <input class="btn btn-small" id="saverowcardoneBtn" type="button" value="保存信用卡" onClick="saveCardData();" > -->
			</td>
		</tr>
	</table>
	<form id="cardoneForm" style="overflow-x:auto">
	<table id="cardoneTable" class="table  table-bordered table-condensed" width="100%" cellspacing="0" cellpadding="0" border="0"  >
		<thead>
		<tr>
			<th>编号</th>
			<th>卡类型</th>
			<th>币种</th>
			<th>开户日期</th>
			<th>信用额度</th>
			<th>共享信用额度</th>
			<th>最大负债额</th>
			<th>透支余额/已使用额度</th>
			<th>删除</th>
			<th>保存</th>
		</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
	</form>
	<table id="cardoneHide" class="hide">
	<tr id="cardoneTable_trRow_1" class="CaseRow">
			<td>
			<input style="width:30px;!important" disabled=true name="num" type="text" class="input_text50"/>
			<input type="hidden" name="id" value=""/>
			</td>
			<td>
				<input type="hidden" name="cardType" value="1"/>
				贷记卡
<%-- 			<select name="cardType" class="required">
	    		   <option value="" >请选择</option>		
	    		<c:forEach var="item" items="${fns:getDictList('jk_card_type')}" >
	    		   <option value="${item.value}" >${item.label} </option>								
				</c:forEach>
	         </select> --%>
			</td>
			<td>
			<select name="currency" id="currency-new" class="required">
	    		   <option value="" >请选择</option>		
	    		<c:forEach var="item" items="${fns:getDictList('jk_credit_currency')}" >
	    		   <option value="${item.value}" >${item.label} </option>								
				</c:forEach>
	         </select>
			</td>
			<td><input type="text" name="accountDay" readonly="readonly" money="1" maxlength="20"  style="width:100px;" class="input_text178 required" onclick="WdatePicker()"   /></td>
			<td><input type="text" name="cerditLine" money="1" maxlength="20"  class="input_text50 required"/></td>
			<td><input type="text" name="shareCreditLine" money="1" maxlength="20"  class="input_text50 required"/></td>
			<td><input type="text" name="liabilitiesLine" money="1" maxlength="20"  class="input_text50 required"/></td>
			<td><input type="text" name="usedAmount" money="1" maxlength="20"  class="input_text50 required"/></td>
			<td><input type="button" class="btn_edit" name="deleteName" value="删除" /></td>
			<td><input type="button" class="btn_edit" name="saveName" value="保存" /></td>
		</tr>
		</table>
	
	
	<table class=" table-bordered table-condensed" width="100%" cellspacing="0" cellpadding="0" border="0"  >
		<tr>
			<td style="text-align:left;" colspan="12">
				<h3>信用卡明细信息（二）</h3>
			</td>
		</tr>
	</table>
	<form id="cardtwoForm" style="overflow-x:auto">
	<table id="cardtwoTable" class="table  table-bordered table-condensed" width="100%" cellspacing="0" cellpadding="0" border="0"  >
		<thead>
		<tr>
			<th>编号</th>
			<th>账户状态</th>
			<th>本月应还款金额</th>
			<th>本月实际还款金额</th>
			<th>最近一次实际还款日期</th>
			<th>当前逾期期数</th>
			<th>当前逾期总额</th>
			<th>贷记卡透支180天以上未付余额</th>
			<th>贷记卡12个月内未还最低还款次数</th>
			<th>信息获取时间</th>
		</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
	</form>
	<table id="cardtwoHide" class="hide">
	<tr id="cardtwoTable_trRow_1" class="CaseRow">
			<td>
			<input style="width:30px;!important" disabled=true name="num" type="text" class="input_text50"/>
			<input type="hidden" name="id" value=""/>
			<input type="hidden" name="relationId" />
			</td>
			<td>
			<select name="accountStatus" class="required">
				<option value="" >请选择</option>		
				<c:forEach var="item" items="${fns:getDictList('jk_credit_cardinfo_accountstatus')}" >
					<c:if test="${item.value != '7'}">
						<option value="${item.value}" >${item.label} </option>								
					</c:if>
				</c:forEach>
	         </select>
			</td>
			<td><input type="text" name="shouldRepayAmount" money="1" maxlength="20"  class="input_text50 required"/></td>
			<td><input type="text" name="realRepayAmount" money="1" maxlength="20"  class="input_text50 required"/></td>
			<td><input type="text" name="realRepayDay"  style="width:100px;" class="input_text178 required" onclick="WdatePicker()" value=""  /></td>
			<td><input type="text" name="currentOverdue" money="1" maxlength="20"  class="input_text50 required integer"/></td>
			<td><input type="text" name="currentOverdueTotal" money="1" maxlength="20"  class="input_text50 required"/></td>
			<td><input type="text" name="overdraftBalance" money="1" maxlength="20"  class="input_text50 required"/></td>
			<td><input type="text" name="repaymentNo" money="1" maxlength="20"  class="input_text50 integer required"/></td>
			<td><input type="text" name="getinfoTime"  style="width:100px;" class="input_text178 required" onclick="WdatePicker()" value=""  /></td>
		</tr>
	</table>
	
	<table class="  table-bordered table-condensed" width="100%" cellspacing="0" cellpadding="0" border="0"  >
		<tr>
			<td style="text-align:left;" colspan="26">
				<h3>信用卡最近24个月每个月的还款状态记录</h3>
			</td>
		</tr>
	</table>
	<form id="periodscardForm" style="overflow-x:auto">
	<table id="card24Table" class="table  table-bordered table-condensed" width="100%" cellspacing="0" cellpadding="0" border="0"  >
		<thead>
		<tr>
			<th>编号<font color="red">*</font></th>
			<th>结算年月<font color="red">*</font></th>
			<th>24<font color="red">*</font></th>
			<th>23<font color="red">*</font></th>
			<th>22<font color="red">*</font></th>
			<th>21<font color="red">*</font></th>
			<th>20<font color="red">*</font></th>
			<th>19<font color="red">*</font></th>
			<th>18<font color="red">*</font></th>
			<th>17<font color="red">*</font></th>
			<th>16<font color="red">*</font></th>
			<th>15<font color="red">*</font></th>
			<th>14<font color="red">*</font></th>
			<th>13<font color="red">*</font></th>
			<th>12<font color="red">*</font></th>
			<th>11<font color="red">*</font></th>
			<th>10<font color="red">*</font></th>
			<th>09<font color="red">*</font></th>
			<th>08<font color="red">*</font></th>
			<th>07<font color="red">*</font></th>
			<th>06<font color="red">*</font></th>
			<th>05<font color="red">*</font></th>
			<th>04<font color="red">*</font></th>
			<th>03<font color="red">*</font></th>
			<th>02<font color="red">*</font></th>
			<th>01<font color="red">*</font></th>
		</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
	</form>
	<table id="periodscardHide" class="hide">
		<tbody>
			<tr>
				<td>
					<input style="width:30px;!important" name="num" disabled=true type="text" class="input_text50"/>
					<input name="relationId" disabled=true type="hidden" class="input_text50"/>
				</td>
				<td><input type="text" name="balanceTime"  style="width:100px;" class="input_text178 required" onclick="WdatePicker()"  /></td>
				<td><input name="qs24" style="width:25px;!important" type="text" maxlength="20" class="input_text50 validate24 required"/></td>
				<td><input name="qs23" style="width:25px;!important" type="text" maxlength="20" class="input_text50 validate24 required"/></td>
				<td><input name="qs22" style="width:25px;!important" type="text" maxlength="20" class="input_text50 validate24 required"/></td>
				<td><input name="qs21" style="width:25px;!important" type="text" maxlength="20" class="input_text50 validate24 required"/></td>
				<td><input name="qs20" style="width:25px;!important" type="text" maxlength="20" class="input_text50 validate24 required"/></td>
				<td><input name="qs19" style="width:25px;!important" type="text" maxlength="20" class="input_text50 validate24 required"/></td>
				<td><input name="qs18" style="width:25px;!important" type="text" maxlength="20" class="input_text50 validate24 required"/></td>
				<td><input name="qs17" style="width:25px;!important" type="text" maxlength="20" class="input_text50 validate24 required"/></td>
				<td><input name="qs16" style="width:25px;!important" type="text" maxlength="20" class="input_text50 validate24 required"/></td>
				<td><input name="qs15" style="width:25px;!important" type="text" maxlength="20" class="input_text50 validate24 required"/></td>
				<td><input name="qs14" style="width:25px;!important" type="text" maxlength="20" class="input_text50 validate24 required"/></td>
				<td><input name="qs13" style="width:25px;!important" type="text" maxlength="20" class="input_text50 validate24 required"/></td>
				<td><input name="qs12" style="width:25px;!important" type="text" maxlength="20" class="input_text50 validate24 required"/></td>
				<td><input name="qs11" style="width:25px;!important" type="text" maxlength="20" class="input_text50 validate24 required"/></td>
				<td><input name="qs10" style="width:25px;!important" type="text" maxlength="20" class="input_text50 validate24 required"/></td>
				<td><input name="qs9" style="width:25px;!important" type="text" maxlength="20" class="input_text50 validate24 required"/></td>
				<td><input name="qs8" style="width:25px;!important" type="text" maxlength="20" class="input_text50 validate24 required"/></td>
				<td><input name="qs7" style="width:25px;!important" type="text" maxlength="20" class="input_text50 validate24 required"/></td>
				<td><input name="qs6" style="width:25px;!important" type="text" maxlength="20" class="input_text50 validate24 required"/></td>
				<td><input name="qs5" style="width:25px;!important" type="text" maxlength="20" class="input_text50 validate24 required"/></td>
				<td><input name="qs4" style="width:25px;!important" type="text" maxlength="20" class="input_text50 validate24 required"/></td>
				<td><input name="qs3" style="width:25px;!important" type="text" maxlength="20" class="input_text50 validate24 required"/></td>
				<td><input name="qs2" style="width:25px;!important" type="text" maxlength="20" class="input_text50 validate24 required"/></td>
				<td><input name="qs1" style="width:25px;!important" type="text" maxlength="20" class="input_text50 validate24 required"/></td>
			</tr>
		</tbody>
	</table>
	</br>
	<table class="table2" width="100%" cellspacing="0" cellpadding="0" border="0" >
		<tr>
			<td style="text-align:left;border-right:0px" >
				<h3>为他人担保贷款明细信息</h3>
			</td>
			<td style="text-align:right;border-left:0px">
				<input class="btn btn-small" id="addrowcardoneBtn" type="button" value="新增担保明细"  onclick="addGuaranteeData();" >
				<input class="btn btn-small" id="saverowcardoneBtn" type="button" value="保存担保明细" onClick="saveGuaranteeData();" >
			</td>
		</tr>
	</table>
	<form id="guaranteeoneForm" style="overflow-x:auto">
	<table id="guaranteeoneTable" class="table  table-bordered table-condensed" width="100%" cellspacing="0" cellpadding="0" border="0" >
		<thead>
		<tr>
			<th>编号</th>
			<th>为他人贷款合同担保金额<font color="red">*</font></th>
			<th>被担保贷款实际本金金额<font color="red">*</font></th>
		
			<th>删除</th>
		</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
	</form>
	<table id="guaranteeoneHide" class="hide">
	<tr id="guaranteeoneTable_trRow_1" class="CaseRow">
	<td>
	<input style="width:30px;!important" disabled=true name="num" type="text" class="input_text50"/>
			<input type="hidden" name="id" value=""/>
			<input type="hidden" name="relationId" />
	</td>
			<td><input type="text" name="otherGuaranteeAmount" money="1" maxlength="20"  class="input_text50 required"/></td>
			<td><input type="text" name="realPrincipal" money="1" maxlength="20"  class="input_text50 required"/></td>
			
			<td><input type="button" class="edit" name="deleteName" value="删除" /></td>
	</tr>
	</table>
	
</div>

</body>
</html>