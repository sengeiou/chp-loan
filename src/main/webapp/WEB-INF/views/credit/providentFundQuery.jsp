<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<html>
<head>
<meta name="decorator" content="default"/>
<title>征信报告</title>
<script type="text/javascript" src="${context}/js/credit/providentFundQuery.js"></script>
<script type="text/javascript" src="${context}/js/credit/common.js"></script>
<script type="text/javascript" src='${context}/js/validateCredit.js'></script>
</head>
<body>
<div class="box1" style="background:none;border:none;margin-top:27px;">
<div style="position:absolute;right:50%" id="msg" align="center"></div>

<table class="table2" width="100%" cellspacing="0" cellpadding="0" border="0"  >
	<tr>
		<td style="text-align:left;border-right:0px" colspan="14">
			<b class="pl10">个人住房公积金信息</b>
		</td>
		<td style="text-align:right;border-left:0px">
			<input class="btn btn-small" type="button" value="新增公积金" onClick="addData();" style="margin-bottom:0;">
			<input id="saveButton" class="btn btn-small" type="button" value="保存公积金" onClick="saveData();" style="margin-bottom:0;">
		</td>
	</tr>
</table>
<form id="infoForm" style="overflow-x:auto">
	<table id="info" class="table  table-bordered table-condensed" width="100%" cellspacing="0" cellpadding="0" border="0"  >
		<thead>
		<tr>
			<th>编号</th>
			<th>个人账号</th>
			<th>单位名称</th>
			<th>开户日期</th>
			<th>初交年月</th>
			<th>缴至年月</th>
			<th>最后一次缴交日期</th>
			<th>单位缴存比例</th>
			<th>个人缴存比例</th>
			<th>月缴存额</th>
			<th>信息获取时间</th>
			<th>操作</th>
		</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
</form>
	<table id="infoHide" class="hide">
		<tbody>
			<tr>
				
				<td>
					<input style="width:30px;" onfocus="clearMsg(this);" disabled=true name="num" type="text" class="input_text50"/>
					<input type="hidden" name="id" value=""/>
				</td>
				<td><input name="personAccount" maxlength="30" type="text" class="input_text70  "/></td>
				<td><input name="unitName" type="text" class="input_text70"/></td>
				<td><input name="accountDay" onblur="clearMsg(this);" type="text" class="select50 Wdate" onclick="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})"/></td>
				<td><input name="payDay" onblur="clearMsg(this);" type="text" class="select50 Wdate" onclick="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})"/></td>
				<td><input name="payToDay" onblur="clearMsg(this);" type="text" class="select50 Wdate" onclick="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})"/></td>
				<td><input name="payDayNear" onblur="clearMsg(this);" type="text" class="select50 Wdate" onclick="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})"/></td>
				<td><input name="unitRation" type="text" onfocus="clearMsg(this);" class="input_text50 number"/>%</td>
				<td><input name="personRation" type="text" onfocus="clearMsg(this);" class="input_text50 number"/>%</td>
				<td><input name="deposit" type="text" onfocus="clearMsg(this);" class="input_text50 number"/></td>
				<td><input name="getinfoTime" onblur="clearMsg(this);" type="text" class="select50 Wdate" onclick="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})"/></td>
				<td><input type="button" name="deleteName" class="btn_edit" value="删除" /></td>
			</tr>
		</tbody>
	</table>
<table class="table2" width="100%" cellspacing="0" cellpadding="0" border="0"  >
	<tr>
		<td style="text-align:left;border-right:0px" colspan="14">
			<b class="pl10">最近24个月缴交状态</b>
		</td>
		
	</tr>
</table>
<form id="periodsForm">
	<table id="periods" class="table  table-bordered table-condensed" width="100%" cellspacing="0" cellpadding="0" border="0"  >
		<thead>
		<tr>
			<th>编号</th>
			<th>24</th>
			<th>23</th>
			<th>22</th>
			<th>21</th>
			<th>20</th>
			<th>19</th>
			<th>18</th>
			<th>17</th>
			<th>16</th>
			<th>15</th>
			<th>14</th>
			<th>13</th>
			<th>12</th>
			<th>11</th>
			<th>10</th>
			<th>09</th>
			<th>08</th>
			<th>07</th>
			<th>06</th>
			<th>05</th>
			<th>04</th>
			<th>03</th>
			<th>02</th>
			<th>01</th>
		</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
</form>	
	
</div>
<div>
	<table id="periodsHide" class="hide">
		<tbody>
			<tr>
				<td>
					<input style="width:30px;" onfocus="clearMsg(this);" name="num" disabled=true type="text" class="input_text50"/>
					<input name="relationId" disabled=true type="hidden" class="input_text50"/>
				</td>
				<td><input  mark="checkQs" name="qs24" style="width:25px;" onfocus="clearMsg(this);" type="text" class="input_text50 paystatus "/></td>
				<td><input  mark="checkQs" name="qs23" style="width:25px;" onfocus="clearMsg(this);" type="text" class="input_text50 paystatus "/></td>
				<td><input  mark="checkQs" name="qs22" style="width:25px;" onfocus="clearMsg(this);" type="text" class="input_text50 paystatus "/></td>
				<td><input  mark="checkQs" name="qs21" style="width:25px;" onfocus="clearMsg(this);" type="text" class="input_text50 paystatus "/></td>
				<td><input  mark="checkQs" name="qs20" style="width:25px;" onfocus="clearMsg(this);" type="text" class="input_text50 paystatus "/></td>
				<td><input  mark="checkQs" name="qs19" style="width:25px;" onfocus="clearMsg(this);" type="text" class="input_text50 paystatus "/></td>
				<td><input  mark="checkQs" name="qs18" style="width:25px;" onfocus="clearMsg(this);" type="text" class="input_text50 paystatus "/></td>
				<td><input  mark="checkQs" name="qs17" style="width:25px;" onfocus="clearMsg(this);" type="text" class="input_text50 paystatus "/></td>
				<td><input  mark="checkQs" name="qs16" style="width:25px;" onfocus="clearMsg(this);" type="text" class="input_text50 paystatus "/></td>
				<td><input  mark="checkQs" name="qs15" style="width:25px;" onfocus="clearMsg(this);" type="text" class="input_text50 paystatus "/></td>
				<td><input  mark="checkQs" name="qs14" style="width:25px;" onfocus="clearMsg(this);" type="text" class="input_text50 paystatus "/></td>
				<td><input  mark="checkQs" name="qs13" style="width:25px;" onfocus="clearMsg(this);" type="text" class="input_text50 paystatus "/></td>
				<td><input  mark="checkQs" name="qs12" style="width:25px;" onfocus="clearMsg(this);" type="text" class="input_text50 paystatus "/></td>
				<td><input  mark="checkQs" name="qs11" style="width:25px;" onfocus="clearMsg(this);" type="text" class="input_text50 paystatus "/></td>
				<td><input  mark="checkQs" name="qs10" style="width:25px;" onfocus="clearMsg(this);" type="text" class="input_text50 paystatus "/></td>
				<td><input  mark="checkQs" name="qs9" style="width:25px;" onfocus="clearMsg(this);" type="text" class="input_text50 paystatus "/></td>
				<td><input  mark="checkQs" name="qs8" style="width:25px;" onfocus="clearMsg(this);" type="text" class="input_text50 paystatus "/></td>
				<td><input  mark="checkQs" name="qs7" style="width:25px;" onfocus="clearMsg(this);" type="text" class="input_text50 paystatus "/></td>
				<td><input  mark="checkQs" name="qs6" style="width:25px;" onfocus="clearMsg(this);" type="text" class="input_text50 paystatus "/></td>
				<td><input  mark="checkQs" name="qs5" style="width:25px;" onfocus="clearMsg(this);" type="text" class="input_text50 paystatus "/></td>
				<td><input  mark="checkQs" name="qs4" style="width:25px;" onfocus="clearMsg(this);" type="text" class="input_text50 paystatus "/></td>
				<td><input  mark="checkQs" name="qs3" style="width:25px;" onfocus="clearMsg(this);" type="text" class="input_text50 paystatus "/></td>
				<td><input  mark="checkQs" name="qs2" style="width:25px;" onfocus="clearMsg(this);" type="text" class="input_text50 paystatus "/></td>
				<td><input  mark="checkQs" name="qs1" style="width:25px;" onfocus="clearMsg(this);" type="text" class="input_text50 paystatus "/></td>
			</tr>
		</tbody>
	</table>
</div>

</body>
</html>