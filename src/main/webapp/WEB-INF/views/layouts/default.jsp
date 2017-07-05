<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<!DOCTYPE html>
<html style="overflow-x:auto;overflow-y:auto;">
<style type="text/css">  
.myvalidatecss {  
    border: 1px solid #FF4500;  
    border-radius:4px;
}  
.ketchup-error-container {
    position: absolute;
    width: auto;
    height: auto;
    top: 48px;
    left: 143px;
}
.ketchup-error-container ol {
    font-size: 12px;
    padding: 10px;
    border-radius: 5px;
    list-style: outside none none;
    line-height: 16px;
    background: #FFF none repeat scroll 0% 0%;
    color: rgb(102, 102, 102);
    border: 1px solid rgb(0, 0, 0);
}
.ketchup-error-container span {
    display: block;
    width: 0px;
    height: 0px;
    border-width: 10px 15px 0px 0px;
    border-style: solid solid none;
    margin-left: 10px;
    border-color: rgba(0, 0, 0, 0.6) transparent -moz-use-text-color;
}
.trRed {
	color:red;
}
</style> 
<head>
	<title><sitemesh:title/></title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>		
	<!-- Baidu tongji analytics --><script>var _hmt=_hmt||[];(function(){var hm=document.createElement("script");hm.src="//hm.baidu.com/hm.js?82116c626a8d504a5c0675073362ef6f";var s=document.getElementsByTagName("script")[0];s.parentNode.insertBefore(hm,s);})();</script>
	<sitemesh:head/>
</head>
<body>
	<sitemesh:body/>
	  <script type="text/javascript">//<!-- 无框架时，左上角显示菜单图标按钮。
		if(!(self.frameElement && self.frameElement.tagName=="IFRAME")){
			//$("body").prepend("<i id=\"btnMenu\" class=\"icon-th-list\" style=\"cursor:pointer;float:right;margin:10px;\"></i><div id=\"menuContent\"></div>");
			//$("#btnMenu").click(function(){
				//top.$.jBox('get:${ctx}/sys/menu/treeselect;JSESSIONID=<shiro:principal property="sessionid"/>', {title:'选择菜单', buttons:{'关闭':true}, width:300, height: 350, top:10});
				//if ($("#menuContent").html()==""){$.get("${ctx}/sys/menu/treeselect", function(data){$("#menuContent").html(data);});}else{$("#menuContent").toggle(100);}
			//});
		}//-->
	</script>  
</body>
</html>