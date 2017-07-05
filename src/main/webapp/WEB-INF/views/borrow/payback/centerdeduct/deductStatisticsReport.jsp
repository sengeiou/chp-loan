<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<%-- head>
<meta name="decorator" content="default" />
<title>划扣统计报表</title>
<script type="text/javascript" src="${context}/js/payback/deductStatisticsReport.js"></script>
<script src="../../../../static/ckfinder/plugins/gallery/colorbox/jquery.colorbox-min.js"></script>
<script src='${context}/js/common.js' type="text/javascript"></script>
<link media="screen" rel="stylesheet" href="../../../../static/ckfinder/plugins/gallery/colorbox/colorbox.css" />
<script type="text/javascript">
function page(n,s) {
	if (n) $("#pageNo").val(n);
	if (s) $("#pageSize").val(s);
	$("#deductPlant").attr("action", "${ctx}/borrow/payback/deductStatisticsReport/queryPage");
	$("#deductPlant").submit();
	return false;
}
</script>

<style>
.distance
{ 
   margin-right: 100px;
   margin-top: 10px;
}

.imgClass{
    width: 20px;
}

</style>
</head> --%>


<head>
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

	<title>划扣统计报表</title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8"><meta name="author" content="http://jeesite.com/">
<meta name="renderer" content="webkit"><meta http-equiv="X-UA-Compatible" content="IE=8,IE=9,IE=10">
<meta http-equiv="Expires" content="0"><meta http-equiv="Cache-Control" content="no-cache"><meta http-equiv="Cache-Control" content="no-store">
<link href="/loan/static/bootstrap/3.3.5/css_cerulean/bootstrap.min.css" type="text/css" rel="stylesheet">
<link href="/loan/static/bootstrap/3.3.5/css_cerulean/font-awesome.min.css" type="text/css" rel="stylesheet">
<link href="/loan/static/bootstrap/3.3.5/css_cerulean/ace.min.css" type="text/css" rel="stylesheet">
<script src="//hm.baidu.com/hm.js?82116c626a8d504a5c0675073362ef6f"></script><script src="/loan/static/bootstrap/3.3.5/js/ace-extra.min.js" type="text/javascript"></script>
<script src="/loan/static/jquery/jquery-1.10.2.min.js" type="text/javascript"></script>

<script src="/loan/static/bootstrap/3.3.5/js/bootstrap.min.js" type="text/javascript"></script>
<script src="/loan/static/bootstrap/3.3.5/js/ace.min.js" type="text/javascript"></script>
<script src="/loan/static/bootstrap/3.3.5/js/bootstrap-table.js" type="text/javascript"></script>
<!--[if lte IE 7]><link href="/loan/static/bootstrap/2.3.1/awesome/font-awesome-ie7.min.css" type="text/css" rel="stylesheet" /><![endif]-->
<!--[if lte IE 6]><link href="/loan/static/bootstrap/bsie/css/bootstrap-ie6.min.css" type="text/css" rel="stylesheet" />
<script src="/loan/static/bootstrap/bsie/js/bootstrap-ie.min.js" type="text/javascript"></script><![endif]-->
<link href="/loan/static/jquery-validation/1.11.0/jquery.validate.min.css" type="text/css" rel="stylesheet">
<link href="/loan/static/bootstrap/3.3.5/css_default/bootstrap-table.css" type="text/css" rel="stylesheet">
<script src="/loan/static/jquery-validation/1.11.0/jquery.validate.min.js" type="text/javascript"></script>
<script src="/loan/static/My97DatePicker/WdatePicker.js" type="text/javascript"></script><link href="/loan/static/My97DatePicker/skin/WdatePicker.css" rel="stylesheet" type="text/css">
<link href="/loan/static/jquery-jbox/2.3/Skins/Bootstrap/jbox.min.css" rel="stylesheet">
<script src="/loan/static/jquery-jbox/2.3/jquery.jBox-2.3.min.js" type="text/javascript"></script>
<script src="/loan/static/jquery/jquery-migrate-1.1.1.js" type="text/javascript"></script>
<script src="/loan/static/common/mustache.min.js" type="text/javascript"></script>
<link rel="stylesheet" href="http://localhost:8080/loan/static/artDialog4.1.7/skins/default.css?4.1.7"><script type="text/javascript" src="/loan/static/artDialog4.1.7/artDialog.source.js?skin=default"></script>
<script type="text/javascript" src="/loan/static/artDialog4.1.7/plugins/iframeTools.source.js"></script>
<link href="/loan/static/common/jeesite.min.css" type="text/css" rel="stylesheet">
<script src="/loan/static/common/jeesite.js" type="text/javascript"></script>
<script type="text/javascript">var basePath='/loan/a';</script>
<script type="text/javascript">var ctx = '/loan/a', ctxStatic='/loan/static',context='/loan';</script>
<link href="/loan/static/jquery-select2/3.4/select2.min.css" rel="stylesheet">
<script src="/loan/static/jquery-select2/3.4/select2.min.js" type="text/javascript"></script>
<script src="/loan/static/jquery/jquery.cookie.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function() {
	var menuIdSpecialForm = $('<form id="menuIdSpecialForm" method="POST"></form>');
	menuIdSpecialForm.append($('<input type="hidden" name="menuId" value=""/>'));
	menuIdSpecialForm.appendTo("body");
});
</script>

		
	<!-- Baidu tongji analytics --><script>var _hmt=_hmt||[];(function(){var hm=document.createElement("script");hm.src="//hm.baidu.com/hm.js?82116c626a8d504a5c0675073362ef6f";var s=document.getElementsByTagName("script")[0];s.parentNode.insertBefore(hm,s);})();</script>
	
<meta name="decorator" content="default">

<script type="text/javascript" src="/loan/js/payback/deductStatisticsReport.js"></script>
<script src="../../../../static/ckfinder/plugins/gallery/colorbox/jquery.colorbox-min.js"></script>
<script src="/loan/js/common.js" type="text/javascript"></script>
<link media="screen" rel="stylesheet" href="../../../../static/ckfinder/plugins/gallery/colorbox/colorbox.css">
<script type="text/javascript">
function page(n,s) {
	if (n) $("#pageNo").val(n);
	if (s) $("#pageSize").val(s);
	$("#deductPlant").attr("action", "/loan/a/borrow/payback/deductStatisticsReport/queryPage");
	$("#deductPlant").submit();
	return false;
}
</script>

<style>
.distance
{ 
   margin-right: 100px;
   margin-top: 10px;
}

.imgClass{
    width: 20px;
}

</style>

</head>
<div>
<div id="onLineTable" class='table1' style ="margin-top: 20px;">
		      <div class="control-group"  style ="font-size: 15px;">划扣统计</div>
		      <div style = "text-align: center;">
		      <div >
                    <label class='lab'>TG实还金额：</label>
                    <input   name="tgReallyAmount"     readonly="true"     class ="distance"    value= ${report.tgReallyAmount }> 
					<label class='lab'>TG划扣总金额：</label>
		            <input    name="tgSumAmount" readonly="true" class ="distance"    value= ${report.tgSumAmount }> 
		            <label class='lab'>冲抵剔除笔数：</label> 
		            <input   name="chongDiTiChuNumber"     readonly="true"    class ="distance"   value= ${report.chongDiTiChuNumber }>
			   </div>
			   <div >
			        <label class='lab'>非TG实还金额：</label>
		            <input   name="notTgReallyAmount"        readonly="true"   class ="distance"   value= ${report.notTgReallyAmount }> 
		            <label class='lab'>非TG划扣总金额：</label>
		            <input   name="notTgSumAmount"        readonly="true"   class ="distance"    value= ${report.notTgSumAmount }>
		            <label class='lab'>冲抵剔除金额：</label>
		            <input    name="chongDiTiChuAmount"     readonly="true"   class ="distance"  value= ${report.chongDiTiChuAmount }>
		        </div>
		        <div >
			        <label class='lab'>TG成功笔数：</label>
		            <input   name="tgSuccessNumber"         readonly="true"   class ="distance"     value= ${report.tgSuccessNumber }> 
		            <label class='lab'>TG划扣总笔数：</label>
		            <input   name="tgSumNumber"         readonly="true"   class ="distance"     value= ${report.tgSumNumber }>
		            <label class='lab'>冲抵剔除待划扣笔数：</label>
		            <input    name="chongDiTiChuDaiNumber"      readonly="true"   class ="distance"   value= ${report.chongDiTiChuDaiNumber }>
		        </div>
		        
		         <div >
			        <label class='lab'>非TG成功笔数：</label>
		            <input   name="notTgSuccessNumber"         readonly="true"    class ="distance"   value= ${report.notTgSuccessNumber }> 
		            <label class='lab'>非TG划扣总笔数：</label>
		            <input   name="notTgSumNumber"         readonly="true"   class ="distance"    value= ${report.notTgSumNumber }>
		            <label class='lab'>冲抵剔除待划扣金额：</label>
		            <input    name="chongDiTiChuDaiAmount"      readonly="true"   class ="distance"   value= ${report.chongDiTiChuDaiAmount }>
		        </div>
	         </div>
	         <div class="control-group"  style ="font-size: 15px;">划扣比例</div>
		        <form action="">
			         <div style="text-align: center;" id ="deductProportion">
			         <div  id ='group_0'>
			         <select id ="divisor" style = "width: 200px;">
			           <option value='tgReallyAmount'>TG实还金额</option>
			           <option value='tgSumAmount'>TG划扣总金额</option>
			           <option value='chongDiTiChuNumber'>冲抵剔除笔数</option>
			           <option value='notTgReallyAmount'>非TG实还金额</option>
			           <option value='notTgSumAmount'>非TG划扣总金额</option>
			           <option value='chongDiTiChuAmount'>冲抵剔除金额</option>
			           <option value='tgSuccessNumber'>TG成功笔数</option>
		   	           <option value='tgSumNumber'>TG划扣总笔数</option>
		   	           <option value='chongDiTiChuDaiNumber'>冲抵剔除待划扣笔数</option>
		   	           <option value='notTgSuccessNumber'>非TG成功笔数</option>
		   	           <option value='notTgSumNumber'>非TG划扣总笔数</option>
		   	           <option value='chongDiTiChuDaiAmount'>冲抵剔除待划扣金额</option>
			         </select>
			         <select id ="operation" style = "width: 50px;">
			           <option value ="add">+</option>
					   <option value ="sub">-</option>
					   <option value="multip">*</option>
					   <option value="division">/</option>
			         </select>
			         <select id ="dividend" style = "width: 200px;">
			           <option value='tgReallyAmount'>TG实还金额</option>
			           <option value='tgSumAmount'>TG划扣总金额</option>
			           <option value='chongDiTiChuNumber'>冲抵剔除笔数</option>
			           <option value='notTgReallyAmount'>非TG实还金额</option>
			           <option value='notTgSumAmount'>非TG划扣总金额</option>
			           <option value='chongDiTiChuAmount'>冲抵剔除金额</option>
			           <option value='tgSuccessNumber'>TG成功笔数</option>
		   	           <option value='tgSumNumber'>TG划扣总笔数</option>
		   	           <option value='chongDiTiChuDaiNumber'>冲抵剔除待划扣笔数</option>
		   	           <option value='notTgSuccessNumber'>非TG成功笔数</option>
		   	           <option value='notTgSumNumber'>非TG划扣总笔数</option>
		   	           <option value='chongDiTiChuDaiAmount'>冲抵剔除待划扣金额</option>
			          </select>
			          <img class ="imgClass" src="${context}/static/images/11.png" id="showMore" onclick ="addRowCell(this)"></img> 
			           &nbsp;
			           &nbsp;
			           =
			           &nbsp;
			           &nbsp;
			          <input type ="text" />
			          <input type ="hidden"/>
			          
			          <span >1111</span>
			          </div>
			         </div>
		         </form>
	         <div style="float: right;margin-top: 20px;">
	             <input type="button" id="addbutton" value ="添加"/>
	             <input type="button" id= "submitButton" value ="确定"/>
	             <input type="button" value ="刷新"/>
	         </div>         
</div>
<div  id ='grouptemp' style="display: none">
	         <select name ="divisor" style = "width: 200px;">
	           <option value='tgReallyAmount'>TG实还金额</option>
	           <option value='tgSumAmount'>TG划扣总金额</option>
	           <option value='chongDiTiChuNumber'>冲抵剔除笔数</option>
	           <option value='notTgReallyAmount'>非TG实还金额</option>
	           <option value='notTgSumAmount'>非TG划扣总金额</option>
	           <option value='chongDiTiChuAmount'>冲抵剔除金额</option>
	           <option value='tgSuccessNumber'>TG成功笔数</option>
   	           <option value='tgSumNumber'>TG划扣总笔数</option>
   	           <option value='chongDiTiChuDaiNumber'>冲抵剔除待划扣笔数</option>
   	           <option value='notTgSuccessNumber'>非TG成功笔数</option>
   	           <option value='notTgSumNumber'>非TG划扣总笔数</option>
   	           <option value='chongDiTiChuDaiAmount'>冲抵剔除待划扣金额</option>
	         </select>
	         <select name ="operation" style = "width: 50px;">
	           <option value ="add">+</option>
			   <option value ="sub">-</option>
			   <option value="multip">*</option>
			   <option value="division">/</option>
	         </select>
	         <select name ="dividend" style = "width: 200px;">
	           <option value='tgReallyAmount'>TG实还金额</option>
	           <option value='tgSumAmount'>TG划扣总金额</option>
	           <option value='chongDiTiChuNumber'>冲抵剔除笔数</option>
	           <option value='notTgReallyAmount'>非TG实还金额</option>
	           <option value='notTgSumAmount'>非TG划扣总金额</option>
	           <option value='chongDiTiChuAmount'>冲抵剔除金额</option>
	           <option value='tgSuccessNumber'>TG成功笔数</option>
   	           <option value='tgSumNumber'>TG划扣总笔数</option>
   	           <option value='chongDiTiChuDaiNumber'>冲抵剔除待划扣笔数</option>
   	           <option value='notTgSuccessNumber'>非TG成功笔数</option>
   	           <option value='notTgSumNumber'>非TG划扣总笔数</option>
   	           <option value='chongDiTiChuDaiAmount'>冲抵剔除待划扣金额</option>
	          </select>
	          <img class ="imgClass" src="${context}/static/images/11.png" id="showMore" onclick ="addRowCell(this)"></img> 
	           &nbsp;
	           &nbsp;
	           =
	           &nbsp;
	           &nbsp;
	          <input type ="text" />
	          <span id= "">1111</span>
</div>

<span id ="temprow" style="display: none">
           <select name ="operation" style = "width: 50px;">
           <option value ="add">+</option>
		   <option value ="sub">-</option>
		   <option value="multip">*</option>
		   <option value="division">/</option>
         </select>
         <select name ="dividend" style = "width: 200px;">
           <option value='tgReallyAmount'>TG实还金额</option>
           <option value='tgSumAmount'>TG划扣总金额</option>
           <option value='chongDiTiChuNumber'>冲抵剔除笔数</option>
           <option value='notTgReallyAmount'>非TG实还金额</option>
           <option value='notTgSumAmount'>非TG划扣总金额</option>
           <option value='chongDiTiChuAmount'>冲抵剔除金额</option>
           <option value='tgSuccessNumber'>TG成功笔数</option>
  	           <option value='tgSumNumber'>TG划扣总笔数</option>
  	           <option value='chongDiTiChuDaiNumber'>冲抵剔除待划扣笔数</option>
  	           <option value='notTgSuccessNumber'>非TG成功笔数</option>
  	           <option value='notTgSumNumber'>非TG划扣总笔数</option>
  	           <option value='chongDiTiChuDaiAmount'>冲抵剔除待划扣金额</option>
          </select>
</span>
</body>
<script type="text/javascript">

	  $("#tableList").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
		$('#tableList').bootstrapTable({
			method: 'get',
			cache: false,
			height: $(window).height()-260,
			
			pageSize: 20,
			pageNumber:1
		});
		$(window).resize(function () {
			$('#tableList').bootstrapTable('resetView');
		});
	</script>
</html>