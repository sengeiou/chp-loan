<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<html>
<head>
<meta name="decorator" content="default"/>
<title>征信报告</title>
<script type="text/javascript" src="${context}/js/credit/queryInformation.js"></script>
<script type="text/javascript" src="${context}/js/credit/common.js"></script>
<script type="text/javascript" src='${context}/js/validateCredit.js'></script>
</head>
<body>
<div class="box1" style="background:none;border:none;margin-top:27px;">
<div style="position:absolute;right:50%" id="msg" align="center"></div>
<table class="table2" width="100%" cellspacing="0" cellpadding="0" border="0" style="background:#fff">
		<tr>
			<td style="text-align:left;border-right:0px" colspan="14">
				<h3>查询记录</h3>
			</td>
			<td style="text-align:right;border-left:0px">
				<input class="btn btn-small" type="button" value="新增" onClick="addData();" style="margin-bottom:0;">
				<input id="saveButton" class="btn btn-small" type="button" value="保存" onClick="saveData();" style="margin-bottom:0;">
			</td>
		</tr>
</table>
	<table id="queryInfoId" class="table  table-bordered table-condensed" width="100%" cellspacing="0" cellpadding="0" border="0" style="background:#fff">
		<thead>
		<tr>
			<th>编号</th>
			<th>查询日期</th>
			<th>查询原因</th>
			<th>操作</th>
		</tr>
		</thead>
		<tbody>
		
		</tbody>
	</table>
	
</div>

<table id="model" class="hide table table-bordered table-condensed" width="100%" cellspacing="0" cellpadding="0" border="0" style="background:#fff">
	<tbody>
		<tr>
			<td>
				<input name="num" readonly="readonly" type="text" class="input_text50"/>
				<input name="id" type="hidden" value=""/>
			</td>
			<td><input name="queryDay" type="text" class="select50 Wdate" onclick="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})"/></td>
			<td>
				<select name="queryType" style="width:180px;padding-left:0px" class="required">
					<option value="">请选择</option>
					<c:forEach items="${fns:getDictList('jk_credit_queryrecord_querytype')}" var="item" >
						<option value="${item.value}">${item.label}</option>
					</c:forEach>
				</select>
			</td>
			<td><a href="javascript:void(0);" >删除</a></td>
		</tr>
	</tbody>
</table>
</body>
</html>