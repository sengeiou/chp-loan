<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<html>
<head>
<meta name="decorator" content="default"/>
<title>保证人代偿信息</title>
<script type="text/javascript" src="${context}/js/credit/creditPayBackDetail.js"></script>
<script type="text/javascript" src="${context}/js/credit/common.js"></script>
<script type="text/javascript" src="${context}/js/validateCredit.js"></script>
</head>
<body>
<div class="box1" style="background:none;border:none;margin-top:27px;">
<div style="position:absolute;right:50%" id="msg" align="center"></div>
<table class="table2" width="100%" cellspacing="0" cellpadding="0" border="0"  >
		<tr>
			<td style="text-align:left;border-right:0px" colspan="14">
				<b class="pl10">查询记录</b>
			</td>
			<td style="text-align:right;border-left:0px">
				<input class="btn btn-small" type="button" value="新增" onClick="addData();" >
				<input id="saveButton" class="btn btn-small" type="button" value="保存" onClick="saveData();" >
			</td>
		</tr>
		
</table>
<form id="bzrdcxxID">
	<table id="queryInfoId" class="table  table-bordered table-condensed" width="100%" cellspacing="0" cellpadding="0" border="0"  >
		<thead>
		<tr>
			<th><font color="red">*</font>最近一次代偿时间</th>
			<th><font color="red">*</font>代偿机构</th>
			<th><font color="red">*</font>累计代偿金额</th>
			<th><font color="red">*</font>最后一次还款日期</th>
			<th><font color="red">*</font>余额</th>
			<th>操作</th>
		</tr>
		</thead>
		<tbody>
		
		</tbody>
	</table>
</form>
	
</div>

<table id="model" class="hide table table-bordered table-condensed" width="100%" cellspacing="0" cellpadding="0" border="0"  >
	<tbody>
		<tr>
			<td>
				<input name="recentlyPaybackTime" type="text" class="select50 Wdate required" onclick="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})"/>
				<input name="id" type="hidden" value=""/>
			</td>
			<td>
				<input maxlength="10" name="paybackOrg" maxlength="30" class="input_text180 required" type="text" />
			</td>
			<td>
				<input maxlength="10" name="totalPaybackAmount" maxlength="12" class="input_text180 required number" type="text" />
			</td>
			<td>
				<input name="lastPaybackDate" type="text" class="select50 Wdate required" onclick="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})"/>
			</td>
			<td>
				<input maxlength="10" name="residualAmount" maxlength="12" class="input_text180 required number" type="text" />
			</td>
			<td><a href="javascript:void(0);" >删除</a></td>
		</tr>
	</tbody>
</table>
</body>
</html>