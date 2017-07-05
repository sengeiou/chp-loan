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
<script type="text/javascript" src="${context}/js/payback/platform.js"></script>

<title></title>
</head>
<body>
<div>
<div>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<input  type="button"  value="添加划扣平台" id="addhkpt"/>
</div>
	<form id="addOrEdit"  action="${ctx}/borrow/payback/plantskiporder/save" method="post" class="form-horizontal">
		     <div id="onLineTable" class='table1'>
				<input type="hidden" class="cf_input_text178" name="id" value="${platformGotoRule.id}">
				<input type="hidden" required class="cf_input_text178" name="platformRuleName"  value="${platformGotoRule.platformRuleName}">
			    <input type="hidden" class="cf_input_text178" readonly="readonly" value="${platformGotoRule.platformRuleTitle}" name="platformRuleText">
				<input type="hidden" class="cf_input_text178" value="${platformGotoRule.platformRule}" name="platformRule">
               <c:choose>
            	<c:when test="${get eq 'get' }">
            		     <c:forEach items="${platformGotoRule.platformIdList}" var="platformIdList" varStatus="status">
            				<c:if test="${status.index == 0 }">
            				       <div class="control-group">
										<label class='lab'>第一平台：</label>
										<select name="platformId" required  class="select78"  style ="width:135px;" disble>
											<option value="">请选择</option>
					                		<c:forEach items="${fns:getDictList('jk_deduct_plat')}" var="d">
					                			<option value="${d.value}" 
					                				<c:if test="${d.value eq platformIdList}">
					                					selected
					                				</c:if> 
					                			>${d.label}</option>
					                		</c:forEach>
					                	</select>
								</div>
            				</c:if>
            				<c:if test="${status.index != 0 }">
            					<div class="control-group">
										<label class='lab'>自动跳转平台：</label>
										<select name="platformIdList" class="select78">
											<option value="">请选择</option>
					                		<c:forEach items="${fns:getDictList('jk_deduct_plat')}" var="d">
					                			<option value="${d.value}" 
					                				<c:if test="${d.value eq platformIdList}">
					                					selected
					                				</c:if> 
					                			>${d.label}</option>
					                		</c:forEach>
					                	</select>
					                <input class="btn btn_sc ml10" type="button" value="删除" id="removeTr">
								</div>
            				</c:if>
            			</c:forEach>
            	</c:when>
            	<c:otherwise>
            		<div class="control-group" class="control-group">
							<label class='lab'>第一平台：</label>
							<select name="platformId" required  class="select78"  style ="width:135px;" onchange='checkPlatform()'>
								<option value="">请选择</option>
		                		<c:forEach items="${fns:getDictList('jk_deduct_plat')}" var="d">
		                			<option value="${d.value}" 
		                				<c:if test="${d.value eq platformIdList}">
		                					selected
		                				</c:if> 
		                			>${d.label}</option>
		                		</c:forEach>
		                	</select>
					</div>
            	 </c:otherwise>
            </c:choose>
            </div>
                 <div class="control-group">
					<label class='lab'>规则状态：</label>
					<label>启用</label>
					<input type="radio" required  name="status" <c:if test="${platformGotoRule.status eq '1'}">checked</c:if> value="1">
					<label>停用</label>
					<input type="radio" required  name="status" <c:if test="${platformGotoRule.status eq '0'}">checked</c:if>  value="0">
				</div>
                <div class="control-group">
					<label class='lab'>是否集中：</label>
					<label>是&nbsp;&nbsp;&nbsp;</label>
					<input type="radio" required  name="isConcentrate" onclick = 'checkPlatform()' <c:if test="${platformGotoRule.isConcentrate eq '1'}">checked</c:if>  value="1">
					<label>否&nbsp;&nbsp;&nbsp;&nbsp;</label>
					<input type="radio" required  name="isConcentrate" onclick = 'checkPlatform()'  <c:if test="${platformGotoRule.isConcentrate eq '0'}">checked</c:if> value="0">
				</div>
            <!--隐藏的tr,功能实现的需要  -->
            <div id="template" style="display:none" class="control-group">
					<label class='lab'>自动跳转平台：</label>
					<select class="select78" id="hiddenSelect"  style ="width:135px;">
						<option value="">请选择</option>
                		<c:forEach items="${fns:getDictList('jk_deduct_plat')}" var="item">
                			<option value="${item.value}" 
                				<c:if test="${item.value eq dictApplyDeductType}">
                					selected
                				</c:if> 
                			>${item.label}</option>
                		</c:forEach>
                	</select>
                	<input class="btn btn_sc ml10" type="button" value="删除" id="removeTr">
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