<%@ page contentType="text/html;charset=UTF-8" %><meta http-equiv="Content-Type" content="text/html;charset=utf-8" /><meta name="author" content="http://jeesite.com/"/>
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
<link href="${ctxStatic}/jquery-select2/3.4/select2.min.css" rel="stylesheet" />
<script src="${ctxStatic}/jquery-select2/3.4/select2.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery/jquery.cookie.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function() {
	var menuIdSpecialForm = $('<form id="menuIdSpecialForm" method="POST"></form>');
	menuIdSpecialForm.append($('<input type="hidden" name="menuId" value="${param.menuId}"/>'));
	menuIdSpecialForm.appendTo("body");
});
</script>

<auth:hasPermission/>