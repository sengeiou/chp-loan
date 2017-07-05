<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<html>
<head>
<title>征信报告</title>
<script type="text/javascript" src="${context}/js/credit/common.js"></script>
 <script type="text/javascript" src="${context}/js/credit/creditReport.js"></script>


</head>
<body>
<h3 style="font-size:16px;color:#337ab7;padding-left:10px;line-height:26px;">基础信息</h3>
<div class="box1 form-search ">
	<table class="table1" width="100%" cellspacing="0" cellpadding="0" border="0">
		<tbody>
			<tr>
				<td>
					<lable class="lab" style="font-size:13px">名称：</lable><input type="text" class="input_text178"/>
				</td>
				<td>
					<lable class="lab" style="font-size:13px">登记注册号：</lable><input type="text" class="input_text178"/>
				</td>
				<td>
					<lable class="lab" style="font-size:13px">登记注册类型：</lable><input type="text" class="input_text178"/>
				</td>
			</tr>
			<tr>
				<td>
					<lable class="lab" style="font-size:13px">国税登记号：</lable><input type="text" class="input_text178"/>
				</td>
				<td>
					<lable class="lab" style="font-size:13px">贷款卡状态：</lable><input type="text" class="input_text178"/>
				</td>
				<td>
					<lable class="lab" style="font-size:13px">地税登记号：</lable><input type="text" class="input_text178"/>
				</td>
			</tr>
				<td>
					<lable class="lab" style="font-size:13px">登记注册日期：</lable><input type="text" class="input_text178 Wdate"/>
				</td>
				<td>
					<lable class="lab" style="font-size:13px">有效截至日期：</lable><input type="text" class="input_text178 Wdate"/>
				</td>
			</tr>
		</tbody>
	</table>
</div>
<h3 style="font-size:15px;color:#337ab7;padding-left:10px;line-height:26px;">出资人信息</h3>
<table class="table2" width="100%" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<th>出资金额</th>
		<th>出资方名称</th>
		<th>出资占比</th>
		<th>证件类型</th>
		<th>证件号码</th>
		<th>币种</th>
		<th>操作</th>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td>删除</td>
	</tr>
</table>
<h3 style="font-size:15px;color:#337ab7;padding-left:10px;line-height:26px;">高管人员信息</h3>
<table class="table2" width="100%" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<th>职务</th>
		<th>姓名</th>
		<th>证件类型</th>
		<th>证件号码</th>
		<th>操作</th>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td>删除</td>
	</tr>
</table>
<h3 style="font-size:15px;color:#337ab7;padding-left:10px;line-height:26px;">有直接关联的其他企业</h3>
<table class="table2" width="100%" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<th>名称</th>
		<th>贷款卡编号</th>
		<th>关系</th>
		<th>操作</th>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<td>删除</td>
	</tr>
</table>
<h3 style="font-size:16px;color:#337ab7;padding-left:10px;line-height:26px;">未结清信贷信息概要</h3>
<h3 style="font-size:15px;color:#333;padding-left:10px;line-height:26px;">当前负债信息概要</h3>
<table class="table2" width="100%" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<td rowspan="2" class="listbg01">由资产管理公司处置的债务</td>
		<td class="listbg01">笔数</td>
		<td class="listbg01">余额</td>
		<td class="listbg01">最近一次处置完成日期</td>
	</tr>
	<tr>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
	</tr>
	<tr>
		<td rowspan="2" class="listbg01">欠息汇总</td>
		<td class="listbg01">笔数</td>
		<td class="listbg01">余额</td>
		<td class="listbg01"></td>
	</tr>
	<tr>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
	</tr>
	<tr>
		<td rowspan="2" class="listbg01">垫款汇总</td>
		<td class="listbg01">笔数</td>
		<td class="listbg01">余额</td>
		<td class="listbg01"></td>
	</tr>
	<tr>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
	</tr>
</table><br>
<table class="table2" width="100%" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<td class="listbg01"></td>
		<td class="listbg01" colspan="2">正常类汇总</td>
		<td class="listbg01" colspan="2">关注类汇总</td>
		<td class="listbg01" colspan="2" width="20%">不良类汇总</td>
	</tr>
	<tr>
		<td class="listbg01"></td>
		<td class="listbg01">笔数</td>
		<td class="listbg01">余额</td>
		<td class="listbg01">笔数</td>
		<td class="listbg01">余额</td>
		<td class="listbg01">笔数</td>
		<td class="listbg01">余额</td>
	</tr>
	<tr>
		<td class="listbg01">贷款</td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
	</tr>
	<tr>
		<td class="listbg01">贸易融资</td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
	</tr>
	<tr>
		<td class="listbg01">保理</td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
	</tr>
	<tr>
		<td class="listbg01">票据贴现</td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
	</tr>
	<tr>
		<td class="listbg01">银行承兑汇票</td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
	</tr>
	<tr>
		<td class="listbg01">信用证</td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
	</tr>
	<tr>
		<td class="listbg01">保函</td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
	</tr>
</table>
<h3 style="font-size:15px;color:#333;padding-left:10px;line-height:26px;">对外担保信息概要</h3>
<table class="table2" width="100%" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<td rowspan="2" class="listbg01"></td>
		<td rowspan="2"  class="listbg01">笔数</td>
		<td rowspan="2"  class="listbg01">担保金额</td>
		<td colspan="3" class="listbg01">被担保业务余额</td>
	</tr>
	<tr>
		<td class="listbg01">正常</td>
		<td class="listbg01">关注</td>
		<td class="listbg01">不良</td>
	</tr>
	<tr>
		<td class="listbg01">保证汇总</td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
	</tr>
	<tr>
		<td class="listbg01">抵押汇总</td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
	</tr>
	<tr>
		<td class="listbg01">质押汇总</td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
	</tr>
</table>
<h3 style="font-size:16px;color:#337ab7;padding-left:10px;line-height:26px;">已结清信贷信息概要</h3>
<table class="table2" width="100%" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<td class="listbg01"></td>
		<td class="listbg01">笔数</td>
		<td class="listbg01">余额</td>
		<td class="listbg01">最近一次处置完成日期</td>
	</tr>
	<tr>
		<td class="listbg01">由资产管理公司处置的债务</td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
	</tr>
	<tr>
		<td class="listbg01">被剥离负债汇总</td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
	</tr>
	<tr>
		<td class="listbg01">垫款汇总</td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
	</tr>
</table><br>
<table class="table2" width="100%" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<td class="listbg01"></td>
		<td class="listbg01">贷款</td>
		<td class="listbg01">贸易融资</td>
		<td class="listbg01">保理</td>
		<td class="listbg01">票据贴现</td>
		<td class="listbg01">银行承兑汇票</td>
		<td class="listbg01">信用证</td>
		<td class="listbg01">保函</td>
	</tr>
	<tr>
		<td class="listbg01">正常类汇总</td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
	</tr>
	<tr>
		<td class="listbg01">关注类汇总</td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
	</tr>
	<tr>
		<td class="listbg01">不良类汇总</td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
		<td><input type="text" class="input_text17801"/></td>
	</tr>
</table>
<h3 style="font-size:16px;color:#337ab7;padding-left:10px;line-height:26px;">负债历史变化</h3>
<table class="table2" width="100%" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<th>负债历史变化时间</th>
		<th>全部负债余额</th>
		<th>不良负债余额</th>
		<th>操作</th>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<td>删除</td>
	</tr>
</table>
<h3 style="font-size:16px;color:#337ab7;padding-left:10px;line-height:26px;">信贷记录明细</h3>
<h3 style="font-size:15px;color:#333;padding-left:10px;line-height:26px;">未结清业务:不良、关注类</h3>
<h3 style="font-size:14px;color:#666;padding-left:10px;line-height:26px;">贷款</h3>
<table class="table2" width="100%" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<th>授信机构</th>
		<th>币种</th>
		<th>借据金额</th>
		<th>借据余额</th>
		<th>放款日期</th>
		<th>到期日期</th>
		<th>五级分类</th>
		<th>贷款形式</th>
		<th>担保</th>
		<th>展期</th>
		<th>操作</th>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td>删除</td>
	</tr>
</table>
<h3 style="font-size:14px;color:#666;padding-left:10px;line-height:26px;">贸易融资</h3>
<table class="table2" width="100%" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<th>授信机构</th>
		<th>币种</th>
		<th>融资金额</th>
		<th>融资余额</th>
		<th>放款日期</th>
		<th>到期日期</th>
		<th>五级分类</th>
		<th>担保</th>
		<th>展期</th>
		<th>操作</th>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td>删除</td>
	</tr>
</table>
<h3 style="font-size:14px;color:#666;padding-left:10px;line-height:26px;">保理</h3>
<table class="table2" width="100%" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<th>授信机构</th>
		<th>币种</th>
		<th>叙做金额</th>
		<th>叙做余额</th>
		<th>叙做日期</th>
		<th>到期日期</th>
		<th>五级分类</th>
		<th>担保</th>
		<th>垫款</th>
		<th>操作</th>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td>删除</td>
	</tr>
</table>
<h3 style="font-size:14px;color:#666;padding-left:10px;line-height:26px;">票据贴现</h3>
<table class="table2" width="100%" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<th>授信机构</th>
		<th>币种</th>
		<th>贴现金额</th>
		<th>贴现日期</th>
		<th>到期日期</th>
		<th>五级分类</th>
		<th>操作</th>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td>删除</td>
	</tr>
</table>
<h3 style="font-size:14px;color:#666;padding-left:10px;line-height:26px;">银行承兑汇票</h3>
<table class="table2" width="100%" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<th>授信机构</th>
		<th>币种</th>
		<th>出票金额</th>
		<th>保证金比例</th>
		<th>承兑日期</th>
		<th>到期日期</th>
		<th>五级分类</th>
		<th>担保</th>
		<th>垫款</th>
		<th>操作</th>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td>删除</td>
	</tr>
</table>
<h3 style="font-size:14px;color:#666;padding-left:10px;line-height:26px;">信用证</h3>
<table class="table2" width="100%" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<th>授信机构</th>
		<th>币种</th>
		<th>开证金额</th>
		<th>保证金比例</th>
		<th>可用余额</th>
		<th>到期日期</th>
		<th>五级分类</th>
		<th>担保</th>
		<th>垫款</th>
		<th>操作</th>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td>删除</td>
	</tr>
</table>
<h3 style="font-size:14px;color:#666;padding-left:10px;line-height:26px;">保函</h3>
<table class="table2" width="100%" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<th>授信机构</th>
		<th>币种</th>
		<th>开证金额</th>
		<th>保证金比例</th>
		<th>可用余额</th>
		<th>到期日期</th>
		<th>五级分类</th>
		<th>担保</th>
		<th>垫款</th>
		<th>操作</th>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td>删除</td>
	</tr>
</table>
<h3 style="font-size:15px;color:#666;padding-left:10px;line-height:26px;">未结清业务:正常</h3>
<h3 style="font-size:14px;color:#666;padding-left:10px;line-height:26px;">贷款</h3>
<table class="table2" width="100%" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<th>授信机构</th>
		<th>币种</th>
		<th>借据金额</th>
		<th>借据余额</th>
		<th>放款日期</th>
		<th>到期日期</th>
		<th>贷款形式</th>
		<th>担保</th>
		<th>展期</th>
		<th>操作</th>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td>删除</td>
	</tr>
</table>
<h3 style="font-size:14px;color:#666;padding-left:10px;line-height:26px;">贸易融资</h3>
<table class="table2" width="100%" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<th>授信机构</th>
		<th>币种</th>
		<th>融资金额</th>
		<th>融资余额</th>
		<th>放款日期</th>
		<th>到期日期</th>
		<th>担保</th>
		<th>展期</th>
		<th>操作</th>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td>删除</td>
	</tr>
</table>
<h3 style="font-size:14px;color:#666;padding-left:10px;line-height:26px;">保理</h3>
<table class="table2" width="100%" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<th>授信机构</th>
		<th>币种</th>
		<th>叙做金额</th>
		<th>叙做余额</th>
		<th>叙做日期</th>
		<th>担保</th>
		<th>垫款</th>
		<th>操作</th>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td>删除</td>
	</tr>
</table>
<h3 style="font-size:14px;color:#666;padding-left:10px;line-height:26px;">票据贴现</h3>
<table class="table2" width="100%" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<th>授信机构</th>
		<th>笔数</th>
		<th>余额</th>
		<th>操作</th>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<td>删除</td>
	</tr>
</table>
<h3 style="font-size:14px;color:#666;padding-left:10px;line-height:26px;">银行承兑汇票</h3>
<table class="table2" width="100%" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<td rowspan="2">授信机构</td>
		<td rowspan="2">笔数</td>
		<td colspan="6">余额</td>
	</tr>
	<tr>
		<th>到期日 <30</th>
		<th>到期日 <60</th>
		<th>到期日 ≤90</th>
		<th>到期日 >90</th>
		<th>合计</th>
		<th>操作</th>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td>删除</td>
	</tr>
</table>
<h3 style="font-size:14px;color:#666;padding-left:10px;line-height:26px;">信用证</h3>
<table class="table2" width="100%" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<th>授信机构</th>
		<th>笔数</th>
		<th>金额</th>
		<th>余额</th>
		<th>操作</th>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td>删除</td>
	</tr>
</table>
<h3 style="font-size:14px;color:#666;padding-left:10px;line-height:26px;">保函</h3>
<table class="table2" width="100%" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<th>授信机构</th>
		<th>笔数</th>
		<th>金额</th>
		<th>余额</th>
		<th>操作</th>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td>删除</td>
	</tr>
</table>
<h3 style="font-size:15px;color:#666;padding-left:10px;line-height:26px;">已还清债务</h3>
<h3 style="font-size:14px;color:#666;padding-left:10px;line-height:26px;">贷款</h3>
<table class="table2" width="100%" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<th>授信机构</th>
		<th>币种</th>
		<th>借据金额</th>
		<th>放款日期</th>
		<th>到期日期</th>
		<th>结清日期</th>
		<th>还款方式</th>
		<th>五级分类</th>
		<th>贷款形式</th>
		<th>担保</th>
		<th>展期</th>
		<th>操作</th>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td>删除</td>
	</tr>
</table>
<h3 style="font-size:14px;color:#666;padding-left:10px;line-height:26px;">贸易融资</h3>
<table class="table2" width="100%" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<th>授信机构</th>
		<th>币种</th>
		<th>融资金额</th>
		<th>放款日期</th>
		<th>到期日期</th>
		<th>结清日期</th>
		<th>还款方式</th>
		<th>五级分类</th>
		<th>贷款形式</th>
		<th>展期</th>
		<th>操作</th>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td>删除</td>
	</tr>
</table>
<h3 style="font-size:14px;color:#666;padding-left:10px;line-height:26px;">保理</h3>
<table class="table2" width="100%" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<th>授信机构</th>
		<th>币种</th>
		<th>叙做金额</th>
		<th>叙做日期</th>
		<th>到期日期</th>
		<th>结清日期</th>
		<th>还款方式</th>
		<th>五级分类</th>
		<th>贷款形式</th>
		<th>展期</th>
		<th>操作</th>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td>删除</td>
	</tr>
</table>
<h3 style="font-size:14px;color:#666;padding-left:10px;line-height:26px;">票据贴现</h3>
<table class="table2" width="100%" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<th>授信机构</th>
		<th>币种</th>
		<th>贴现金额</th>
		<th>贴现日期</th>
		<th>承兑到期日期</th>
		<th>结清日期</th>
		<th>五级分类</th>
		<th>操作</th>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td>删除</td>
	</tr>
</table>
<h3 style="font-size:14px;color:#666;padding-left:10px;line-height:26px;">银行承兑汇票</h3>
<table class="table2" width="100%" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<th>授信机构</th>
		<th>币种</th>
		<th>出票金额</th>
		<th>保证金比例</th>
		<th>承兑日期</th>
		<th>到期日期</th>
		<th>结清日期</th>
		<th>五级分类</th>
		<th>担保</th>
		<th>垫款</th>
		<th>操作</th>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td>删除</td>
	</tr>
</table>
<h3 style="font-size:14px;color:#666;padding-left:10px;line-height:26px;">信用证</h3>
<table class="table2" width="100%" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<th>授信机构</th>
		<th>币种</th>
		<th>开证/开立金额</th>
		<th>保证金比例</th>
		<th>到期日期</th>
		<th>注销日期/结清日期</th>
		<th>五级分类</th>
		<th>担保</th>
		<th>垫款</th>
		<th>操作</th>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td>删除</td>
	</tr>
</table>
<h3 style="font-size:14px;color:#666;padding-left:10px;line-height:26px;">保函</h3>
<table class="table2" width="100%" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<th>授信机构</th>
		<th>币种</th>
		<th>开证/开立金额</th>
		<th>保证金比例</th>
		<th>到期日期</th>
		<th>注销日期/结清日期</th>
		<th>五级分类</th>
		<th>担保</th>
		<th>垫款</th>
		<th>操作</th>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td>删除</td>
	</tr>
</table>
<h3 style="font-size:15px;color:#666;padding-left:10px;line-height:26px;">对外担保记录</h3>
<table class="table2" width="100%" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<th>类型</th>
		<th>被担保人</th>
		<th>证件类型</th>
		<th>证件号码</th>
		<th>担保币种</th>
		<th>担保金额</th>
		<th>担保形式</th>
		<th>操作</th>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td>删除</td>
	</tr>
</table>
<h3 style="font-size:16px;color:#337ab7;padding-left:10px;line-height:26px;">公共记录明细</h3>
<h3 style="font-size:15px;color:#666;padding-left:10px;line-height:26px;">民事判决记录</h3>
<table class="table2" width="100%" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<th>立案法院</th>
		<th>案由</th>
		<th>案号</th>
		<th>诉讼标的</th>
		<th>结案方式</th>
		<th>立案日期</th>
		<th>诉讼地位</th>
		<th>审判程序</th>
		<th>诉讼标的金额（元）</th>
		<th>判决/调解生效日期</th>
		<th>操作</th>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td>删除</td>
	</tr>
</table>
<h3 style="font-size:16px;color:#337ab7;padding-left:10px;line-height:26px;">声明信息明细</h3>
<h3 style="font-size:15px;color:#666;padding-left:10px;line-height:26px;">征信中心标注</h3>
<h3 style="font-size:14px;color:#666;padding-left:10px;line-height:26px;">贷款卡</h3>
<table class="table2" width="100%" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<th>通过年度</th>
		<th>机构名称</th>
		<th>添加日期</th>
		<th>操作</th>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<td>删除</td>
	</tr>
</table>
<h3 style="font-size:14px;color:#666;padding-left:10px;line-height:26px;">评级</h3>
<table class="table2" width="100%" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<th>时间</th>
		<th>机构</th>
		<th>等级</th>
		<th>添加日期</th>
		<th>操作</th>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td>删除</td>
	</tr>
</table>
<h3 style="font-size:15px;color:#666;padding-left:10px;line-height:26px;">处罚</h3>
<table class="table2" width="100%" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<th>时间</th>
		<th>机构</th>
		<th>项目</th>
		<th>金额</th>
		<th>添加日期</th>
		<th>操作</th>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td>删除</td>
	</tr>
</table>
</body>
</html>