<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<html>
<head>
<!-- <meta name="decorator" content="default"/> -->
<title>征信报告</title>
<script type="text/javascript">

// 切换页签
function changeTab(t,id){// cxxx=查询信息；gjjxx=公积金信息；mxxx=明细信息；
	var isSave = $("input[name='isSave']").val();
	if(id == "cxxx" || id == "gjjxx" || id == "mxxx" || id == "bzrdcxx"){
		if(isSave == "1"){
			$("div[name='iframeDIV']").each(function(){
				$(this).attr("class","hide")// 隐藏所有页面内容
			});
			$("#"+id).attr("class","");// 显示当前所选页面内容
			
			$("span[name='pageTable']").each(function(){
				$(this).attr("class","")// 取消所有页签选中状态
			});
			$(t).parent("span").attr("class","click")// 当前页签状态为选中状态
			
		}else{
			art.dialog.tips("请先保存基本信息!");
		}
	}else{// 当点击页签为grsfxx=个人身份信息的时候
		$("div[name='iframeDIV']").each(function(){
			$(this).attr("class","hide")
		});
		$("#"+id).attr("class","");
		$("span[name='pageTable']").each(function(){
			$(this).attr("class","")
		});
		$(t).parent("span").attr("class","click")
	}
}

</script>
</head>
<body >
<form id="param" class="hide">
	<input type="hidden" name="loanCode" value="${param.loanCode}"/>
	<input type="hidden" name="rCustomerCoborrowerId" value="${param.rId}"/>
	<input type="hidden" name="dictCustomerType" value="${param.customerType}"/>
	<input type="hidden" name="applyCertNum" value="${applyCertNum}"/>
</form>
<div class="table-top" style="position:fixed;background:#fff;border-bottom:solid 1px #ccc;height:27px;line-height:29px;width:100%">
	<input type="hidden" name="isSave" value="0"/>
	<span name="pageTable" class="click"><a href="javascript:void(0);" onClick="changeTab( this ,'grsfxx')">个人身份信息</a></span>
	<span name="pageTable" ><a href="javascript:void(0);" onClick="changeTab(this ,'cxxx')" >查询信息</a></span>
	<span name="pageTable" ><a href="javascript:void(0);" onClick="changeTab(this ,'gjjxx')" >公积金信息</a></span>
	<span name="pageTable" ><a href="javascript:void(0);" onClick="changeTab(this ,'mxxx')" >明细信息</a></span>
	<span name="pageTable" ><a href="javascript:void(0);" onClick="changeTab(this ,'bzrdcxx')" >保证人代偿信息</a></span>
</div>
	<div id="grsfxx" name="iframeDIV" >
		<iframe id="ckfinder_iframe" scrolling="yes" frameborder="0" width="100%" height="100%" src="${ctx}/creditdetailed/info/initPage;"> </iframe>
	</div>
	<div id="cxxx" name="iframeDIV" class="hide" >
		<iframe id="ckfinder_iframe" scrolling="yes" frameborder="0" width="100%" height="100%" src="${ctx}/creditdetailed/queryrecord/initPage;"> </iframe>
	</div>
	<div id="gjjxx" name="iframeDIV" class="hide" style="width:100%;height:100%;">
		<iframe id="ckfinder_iframe" scrolling="yes" frameborder="0" width="100%" height="100%" src="${ctx}/creditdetailed/accumulation/initPage;"> </iframe>
	</div>
	<div id="mxxx" name="iframeDIV" class="hide" >
		<iframe id="ckfinder_iframe" scrolling="yes" frameborder="0" width="100%" height="100%" src="${ctx}/credit/creditReport;"> </iframe>
	</div>
	<div id="bzrdcxx" name="iframeDIV" class="hide" >
		<iframe id="ckfinder_iframe" scrolling="yes" frameborder="0" width="100%" height="100%" src="${ctx}/credit/creditpaybackdetail/initPage;"> </iframe>
	</div>
</body>
</html>