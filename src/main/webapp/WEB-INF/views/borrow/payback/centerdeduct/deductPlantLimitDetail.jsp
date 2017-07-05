<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="renderer" content="webkit"><meta http-equiv="X-UA-Compatible" content="IE=8,IE=9,IE=10" />
<meta http-equiv="Expires" content="0"><meta http-equiv="Cache-Control" content="no-cache"><meta http-equiv="Cache-Control" content="no-store">
<link href="${ctxStatic}/bootstrap/3.3.5/css_${not empty cookie.theme.value ? cookie.theme.value : 'cerulean'}/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/bootstrap/3.3.5/css_${not empty cookie.theme.value ? cookie.theme.value : 'cerulean'}/font-awesome.min.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/bootstrap/3.3.5/css_${not empty cookie.theme.value ? cookie.theme.value : 'cerulean'}/ace.min.css" type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/bootstrap/3.3.5/js/ace-extra.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery/jquery-1.10.2.min.js" type="text/javascript"></script>

<script src="${ctxStatic}/bootstrap/3.3.5/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/bootstrap/3.3.5/js/ace.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/bootstrap/3.3.5/js/bootstrap-table.js" type="text/javascript"></script>
<!--[if lte IE 7]><link href="${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome-ie7.min.css" type="text/css" rel="stylesheet" /><![endif]-->
<!--[if lte IE 6]><link href="${ctxStatic}/bootstrap/bsie/css/bootstrap-ie6.min.css" type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/bootstrap/bsie/js/bootstrap-ie.min.js" type="text/javascript"></script><![endif]-->
<link href="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/bootstrap/3.3.5/css_default/bootstrap-table.css" type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<link href="${ctxStatic}/jquery-jbox/2.3/Skins/Bootstrap/jbox.min.css" rel="stylesheet" />
<script src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery/jquery-migrate-1.1.1.js" type="text/javascript"></script>
<script src="${ctxStatic}/common/mustache.min.js" type="text/javascript"></script>
<script type=text/javascript src="${ctxStatic}/artDialog4.1.7/artDialog.source.js?skin=default"></script>
<script type=text/javascript src="${ctxStatic}/artDialog4.1.7/plugins/iframeTools.source.js"></script>
<link href="${ctxStatic}/common/jeesite.min.css" type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/common/jeesite.js" type="text/javascript"></script>
<script type="text/javascript">var basePath='${ctx}';</script>
<script type="text/javascript">var ctx = '${ctx}', ctxStatic='${ctxStatic}',context='${context}';</script>
<script src="${ctxStatic}/jquery/jquery.cookie.js" type="text/javascript"></script>
<link href="${context}/static/bootstrap/3.3.5/css_cerulean/bootstrap-select.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${context}/js/payback/deductPlantLimit.js"></script>

<title></title>
</head>
<body>
<div>
<div>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div>
	<form id="addOrEdit"  action="${ctx}/borrow/payback/deductPlantLimit/save" method="post" class="form-horizontal">
		     <div id="onLineTable" class='table1'>
			    <input type="hidden" class="cf_input_text178" name="id" value="${limit.id}">
			    <input type="hidden"  id="plantId" value="${limit.plantCode}">
			    
            	<div class="control-group">
					<label class='lab'>平台：</label>
					<select name="plantCode" id ="plantCodeId"    class="select78"  style ="width:135px;" onchange = "plantChage(this)">
						<option value="">请选择</option>
                		<c:forEach items="${fns:getDictList('jk_deduct_plat')}" var="d">
                	      <c:if test="${d.value != 4 }">
                			<option value="${d.value}" 
                				<c:if test="${d.value eq limit.plantCode}">
                					selected
                				</c:if> 
                			>${d.label}</option>
                			</c:if>
                		</c:forEach>
                	</select>
					<label class='lab'>余额不足比例：</label>
		            <input    name="notEnoughProportion"  id="notEnoughProportionId"   type = "number"     value= "${limit.notEnoughProportion}"> %
		            <label class='lab'>余额不足基数：</label> 
		            <input   name="notEnoughBase"    id ="notEnoughBaseId"      type ="number"   value= "${limit.notEnoughBase}">
			   </div>
			   <div class="control-group">
			        <label class='lab'>失败率：</label>
		            <input   name="failureRate"   id ="failureRateId"           type ="number"    value= "${limit.failureRate }"> %
		            <label class='lab'>失败基数：</label>
		            <input   name="failureBase"    id ="failureBaseId"         type ="number"  value= "${limit.failureBase }">
		            <label class='lab'>失败笔数：</label>
		            <input    name="failureNumber"     id ="failureNumberId"     type ="number"    value= "${limit.failureNumber }">
		        </div>
		        <div class="control-group"   id="template" style="display:none">
		            <label class='lab'>金额条件1：</label>
		            <select class="select180" name="moneySymbol1"  id ='moneySymbol1Id'>
                    <option value="">请选择</option>
					<option <c:if test="${limit.moneySymbol1=='0'}">selected</c:if> value="0" > &lt;=X </option>
					<option <c:if test="${limit.moneySymbol1=='1'}">selected</c:if> value="1" > &gt;X </option>
                     </select>
                    <input type ="number"  name ="deductMoney1" id ="deductMoney1Id" value ="${limit.deductMoney1}"/>
                    <select    class="select78"  name ="deductType1" id ="deductType1Id"  style ="width:135px;">
							 <option value="">请选择</option>
	                		 <c:forEach items="${fns:getDictList('com_deduct_type')}" var="d">
	                			<option value="${d.value}" 
	                				<c:if test="${d.value eq limit.deductType1}">selected</c:if> >${d.label}
	                			</option>
	                		 </c:forEach>
			         </select>    
			        <label class='lab'>金额条件2：</label>
		            <select class="select180" name="moneySymbol2" id ="moneySymbol2Id">
                    <option value="">请选择</option>
					<option <c:if test="${limit.moneySymbol2=='0'}">selected</c:if> value="0" > &lt;=X </option>
					<option <c:if test="${limit.moneySymbol2=='1'}">selected</c:if> value="1" > &gt;X </option>
                     </select>
                    <input type ="number"  name ="deductMoney2"  id ="deductMoney2Id" value ="${limit.deductMoney2}"/>
                    <select    class="select78"  name ="deductType2" id ="deductType2Id"  style ="width:135px;">
							 <option value="">请选择</option>
	                		 <c:forEach items="${fns:getDictList('com_deduct_type')}" var="d">
	                			<option value="${d.value}" 
	                				<c:if test="${d.value eq limit.deductType2}">selected</c:if> >${d.label}
	                			</option>
	                		 </c:forEach>
			         </select>                            
			   </div>
	       </div>
        </div>
	 </form>
	  </div>
	 	<div class="tright pr30 pt10">
   		    <input type="button" class="btn btn-primary" id="submitButton" value="提交"/>
	    	<input type="reset" class="btn btn-primary"  value="返回" onclick="go();" />
		</div>
</div>
</body>
</html>