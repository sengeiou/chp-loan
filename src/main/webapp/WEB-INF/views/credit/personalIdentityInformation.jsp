<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<html>
<head>
<meta name="decorator" content="default"/>
<title>征信报告</title>
<script type="text/javascript" src="${context}/js/credit/personalIdentityInformation.js"></script>
<script type="text/javascript" src="${context}/js/credit/common.js"></script>
<script type="text/javascript" src='${context}/js/validateCredit.js'></script>
</head>
<body>

<div class="box1" style="background:none;border:none;margin-top:27px;">
<div style="position:absolute;right:50%" id="msg" align="center"></div>
<table class="table2" width="100%" cellspacing="0" cellpadding="0" border="0" style="background:#fff">
		<tr>
			<td style="text-align:left;border-right:0px;padding-left:20px;color:#004b75;color:#004b75" colspan="14">
				<h3>报告编号（数据来源：手工输入）</h3>
			</td>
			<td style="border-left:0px">
				<input id="backBtn" type="button" class="btn btn-small" value="返回" />
				<input type="hidden" id="saveLock" value="0">
			</td>
		</tr>
</table>
<form id="detailedInfo">
	<table class="table  table-bordered table-condensed" width="100%" cellspacing="0" cellpadding="0" border="0" style="background:#fff">
		<thead>
		<tr>
			<th>报告编号</th>
			<th>查询时间<font color="red">*</font></th>
			<th>报告时间</th>
		</tr>
		</thead>
		<tr>
			<td><input name="creditCode" maxlength="30" type="text" class="input_text178"/></td>
			<td><input name="queryTime" type="text" class="select50 Wdate required" onclick="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})"/></td>
			<td><input name="reportTime" type="text" class="select50 Wdate" onclick="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})"/></td>
		</tr>
	</table>
	<table class="table2" width="100%" cellspacing="0" cellpadding="0" border="0" style="background:#fff">
			<tr>
				<td style="text-align:left;padding-left:20px;color:#004b75;color:#004b75" colspan="14">
					<h3>个人身份信息</h3>
				</td>
			</tr>
	</table></br>
	<table class="table table-bordered table-condensed" width="20%" style="float:left;width:20%" cellspacing="0" cellpadding="0" border="0" style="background:#fff">
			
			<tr>
				<td>查询项</td>
				<td>返回值</td>
			</tr>
			<tr>
				<td><p class="tright pr5"><font color="red">*</font>姓名</p></td>
				<td>
					<input name="name" maxlength="20" value="" type="text" class="input_text178 required"/>
					<input name="id" type="hidden" value=""/>
				</td>
			</tr>
			<tr>
				<td><p class="tright pr5">性别</p></td>
				<td>
					<select name="sex" class="select180">
						<option value="">请选择</option>
						<c:forEach items="${fns:getDictList('jk_sex')}" var="item" >
							<option value="${item.value}">${item.label}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td><p class="tright pr5"><font color="red">*</font>证件类型</p></td>
				<td>
					<select name="certType" class="select180 required">
						<option value="">请选择</option>
						<c:forEach items="${fns:getDictList('jk_certificate_type')}" var="item" >
							<option value="${item.value}">${item.label}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td><p class="tright pr5"><font color="red">*</font>证件号码</p></td>
				<td><input name="certNo" maxlength="18" value="" type="text" class="input_text178 required card"/></td>
			</tr>
			<tr>
				<td><p class="tright pr5">出生日期</p></td>
				<td><input name="birthday" value="" type="text" class="input_text178 Wdate" onclick="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})"/></td>
			</tr>
			<tr>
				<td><p class="tright pr5">最高学历</p></td>
				<td>
					<select name="highestEducation" class="select180">
						<option value="">请选择</option>
						<c:forEach items="${fns:getDictList('jk_degree')}" var="item" >
							<option value="${item.value}">${item.label}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td><p class="tright pr5">通讯-省</p></td>
				<td>
					<select str="province" name="contactAddProvince" class="select180">
						<option value="" >请选择</option>
						<c:forEach var="item" items="${provinceList}" varStatus="status">
							<option value="${item.areaCode }">${item.areaName}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td><p class="tright pr5">通讯-市</p></td>
				<td>
					<select str="city" name="contactAddCity" class="select180">
						<option value="" >请选择</option>
					</select>
				</td>
			</tr>
			<tr>
				<td><p class="tright pr5">通讯-区</p></td>
				<td>
					<select name="contactAddArea" class="select180">
						<option value="" >请选择</option>
					</select>
				</td>
			</tr>
			<tr>
				<td><p class="tright pr5">通讯详细地址</p></td>
				<td><input value="" maxlength="100" name="contactAddress" type="text" class="input_text178"/></td>
			</tr>
			<tr>
				<td><p class="tright pr5">邮政编码</p></td>
				<td><input value="" name="zipCode" type="text" class="input_text178 zipcode"/></td>
			</tr>
			<tr>
				<td><p class="tright pr5">户籍-省</p></td>
				<td>
					<select str="province" name="nativeAddProvince" class="select180">
						<option value="" >请选择</option>
						<c:forEach var="item" items="${provinceList}" varStatus="status">
							<option value="${item.areaCode }">${item.areaName}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td><p class="tright pr5">户籍-市</p></td>
				<td>
					<select str="city" name="nativeAddCity" class="select180">
						<option value="" >请选择</option>
					</select>
				</td>
			</tr>
			<tr>
				<td><p class="tright pr5">户籍-区</p></td>
				<td>
					<select name="nativeAddArea" class="select180">
						<option value="" >请选择</option>
					</select>
				</td>
			</tr>
			<tr>
				<td><p class="tright pr5">户籍详细地址</p></td>
				<td><input maxlength="100" value="" name="nativeAddress" type="text" class="input_text178"/></td>
			</tr>
			<tr>
				<td><p class="tright pr5">住宅电话</p></td>
				<td><input value="" name="homePhone" type="text" class="input_text178  phone"/></td>
			</tr>
			<tr>
				<td><p class="tright pr5">单位电话</p></td>
				<td><input value="" name="unitPhone" type="text" class="input_text178  phone"/></td>
			</tr>
			<tr>
				<td><p class="tright pr5">手机号码</p></td>
				<td><input value="" name="mobilePhone" type="text" class="input_text178 mobile"/></td>
			</tr>
			<tr>
				<td><p class="tright pr5"><font color="red">*</font>婚姻状况</p></td>
				<td>
					<select name="marryStatus" class="select180 required">
						<option value="">请选择</option>
						<c:forEach items="${fns:getDictList('jk_marriage')}" var="item" >
							<option value="${item.value}">${item.label}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td><p class="tright pr5">配偶姓名</p></td>
				<td><input value="" maxlength="20" name="mateName" type="text" class="input_text178"/></td>
			</tr>
			<tr>
				<td><p class="tright pr5">配偶手机号</p></td>
				<td><input value="" name="matePhone" type="text" class="input_text178 mobile"/></td>
			</tr>
			<tr>
				<td><p class="tright pr5">配偶证件类型</p></td>
				<td>
					<select name="mateCertType" class="select180">
						<option value="">请选择</option>
						<c:forEach items="${fns:getDictList('jk_certificate_type')}" var="item" >
							<option value="${item.value}">${item.label}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td><p class="tright pr5">配偶证件号码</p></td>
				<td><input value="" maxlength="18" name="mateCertNo" type="text" class="input_text178 card"/></td>
			</tr>
			
	</table>
</form>	
<form id="formHouseWork">
	<table id="houseInfo" class="table table-bordered table-condensed" style="float:right;width:78%" width="78%" cellspacing="0" cellpadding="0" border="0" style="background:#fff">
		<thead>
			<tr>
				<th colspan="8">居住信息&nbsp;&nbsp;&nbsp;<a title="点击添加居住信息" href="javascript:void(0);" onclick='addHouse()'>添加</a></th>
			</tr>
			<tr>
				<td>居住省</td>
				<td>居住市</td>
				<td>居住区</td>
				<td>居住详细地址</td>
				<td>居住状况</td>
				<td>信息获取时间</td>
				<td>操作</td>
			</tr>
		</thead>
		<tbody>
			
		</tbody>
	</table>
	
	<table id="workOne" class="table table-bordered table-condensed" style="float:right;width:78%" width="78%" cellspacing="0" cellpadding="0" border="0" style="background:#fff">
		<thead>	
			<tr>
				<th colspan="8">职业信息&nbsp;&nbsp;&nbsp;<a title="点击添加职业信息" href="javascript:void(0);" onclick='addWork()'>添加</a></th>
			</tr>
			<tr>
				<td>编号</td>
				<td>工作单位名称</td>
				<td>工作单位省</td>
				<td>工作单位市</td>
				<td>工作单位区</td>
				<td>单位详细地址</td>
				<td>单位所属行业</td>
				<td>操作</td>
			</tr>
		</thead>
		<tbody>
			
		</tbody>
	</table>
	
	<table id="workTwo" class="table table-bordered table-condensed" style="float:right;width:78%" width="78%" cellspacing="0" cellpadding="0" border="0" style="background:#fff">
		<thead>
			<tr>
				<th colspan="7">职业信息&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
			</tr>
			<tr>
				<td>编号</td>
				<td>职业</td>
				<td>职务</td>
				<td>职称</td>
				<td>年收入</td>
				<td>本单位工作起年份</td>
				<td>信息获取时间</td>
			</tr>
		</thead>
		<tbody>
			
		</tbody>
	</table>
</form>
</div>
<div class="tright pr30" style="margin:5px 5px 5px;">
	<input class="btn btn-small" type="button" value="保存数据" onClick="saveData();" style="margin-bottom:0;">
</div>
<table id="houseHid" class="hide">
	<tbody>
		<tr>
			<td>
			<input type="hidden" name="id" value="" />
				<select str="provinceTD" name="liveProvince" style="width:120px;padding-left:0px">
					<option value="" >请选择</option>
					<c:forEach var="item" items="${provinceList}" varStatus="status">
							<option value="${item.areaCode }">${item.areaName}</option>
					</c:forEach>
				</select>
			</td>
			<td>
				<select str="cityTD" name="liveCity" style="width:120px;padding-left:0px">
					<option value="" >请选择</option>
				</select>
			</td>
			<td>
				<select name="liveArea" style="width:120px;padding-left:0px">
					<option value="" >请选择</option>
				</select>
			</td>
			<td><input name="liveAddress" maxlength="100" type="text" class="input_text80"/></td>
			<td><input name="liveSituation" maxlength="50" type="text" class="input_text80"/></td>
			<td><input name="getinfoTime" type="text" class="select50 Wdate" onclick="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})"/></td>
			<td><a href="javascript:void(0);">删除</a></td>
		</tr>
	</tbody>
</table>

<table id="workOneHid" class="hide">
	<tbody>
		<tr>
			<td>
				<input name="num" type="text" disabled=true class="input_text50" style="width:30px"/>
				<input name="id" value="" type="hidden" />
			</td>
			<td><input name="unitName" type="text" class="input_text80"/></td>
			<td>
				<select str="provinceTD" name="unitProvince" style="width:120px;padding-left:0px">
					<option value="" >请选择</option>
					<c:forEach var="item" items="${provinceList}" varStatus="status">
							<option value="${item.areaCode }">${item.areaName}</option>
					</c:forEach>
				</select>
			</td>
			<td>
				<select str="cityTD" name="unitCity" style="width:120px;padding-left:0px">
					<option value="" >请选择</option>
				</select>
			</td>
			<td>
				<select name="unitArea" style="width:120px;padding-left:0px">
					<option value="" >请选择</option>
				</select>
			</td>
			<td><input name="unitAddress" maxlength="30" type="text" class="input_text80"/></td>
			<td><input name="unitIndustry" type="text" class="input_text80" style="width:60px"/></td>
			<td><a href="javascript:void(0);">删除</a></td>
		</tr>
	</tbody>
</table>


<table id="workTwoHid" class="hide">
	<tbody>
		<tr>
			<td>
				<input name="num" disabled=true type="text" class="input_text50" style="width:30px"/>
				<input name="id" value="" type="hidden" />
			</td>
			<td><input name="occupation" type="text" class="input_text80"/></td>
			<td><input name="duties" type="text" class="input_text80"/></td>
			<td><input name="title" type="text" class="input_text80"/></td>
			<td><input name="annualIncome" digits="1" type="text" class="input_text80 number" style="width:70px"/></td>
			<td><input name="startingYear" type="text" class="input_text80 number" style="width:70px"/></td>
			<td><input name="getinfoTime" type="text" class="select50 Wdate" onclick="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})" style="width:100px"/></td>
		</tr>
	</tbody>
</table>
</body>
</html>