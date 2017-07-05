<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="overflow-x:auto;overflow-y:auto;">
<head>
<title>车借咨询待办</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" /><meta name="author" content="http://jeesite.com/"/>
<meta name="renderer" content="webkit"><meta http-equiv="X-UA-Compatible" content="IE=8,IE=9,IE=10" />
<meta name="decorator" content="default"/>
<meta http-equiv="Expires" content="0"><meta http-equiv="Cache-Control" content="no-cache"><meta http-equiv="Cache-Control" content="no-store">
<link href="/loan/static/bootstrap/3.3.5/css_cerulean/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="/loan/static/bootstrap/3.3.5/css_cerulean/font-awesome.min.css" type="text/css" rel="stylesheet" />
<link href="/loan/static/bootstrap/3.3.5/css_cerulean/ace.min.css" type="text/css" rel="stylesheet" />
<script src="/loan/static/bootstrap/3.3.5/js/ace-extra.min.js" type="text/javascript"></script>
<script src="/loan/static/jquery/jquery-1.10.2.min.js" type="text/javascript"></script>

<script src="/loan/static/bootstrap/3.3.5/js/bootstrap.min.js" type="text/javascript"></script>
<script src="/loan/static/bootstrap/3.3.5/js/ace.min.js" type="text/javascript"></script>
<script src="/loan/static/bootstrap/3.3.5/js/bootstrap-table.js" type="text/javascript"></script>
<!--[if lte IE 7]><link href="/loan/static/bootstrap/2.3.1/awesome/font-awesome-ie7.min.css" type="text/css" rel="stylesheet" /><![endif]-->
<!--[if lte IE 6]><link href="/loan/static/bootstrap/bsie/css/bootstrap-ie6.min.css" type="text/css" rel="stylesheet" />
<script src="/loan/static/bootstrap/bsie/js/bootstrap-ie.min.js" type="text/javascript"></script><![endif]-->
<link href="/loan/static/jquery-validation/1.11.0/jquery.validate.min.css" type="text/css" rel="stylesheet" />
<link href="/loan/static/bootstrap/3.3.5/css_default/bootstrap-table.css" type="text/css" rel="stylesheet" />
<script src="/loan/static/jquery-validation/1.11.0/jquery.validate.min.js" type="text/javascript"></script>
<link href="/loan/static/jquery-jbox/2.3/Skins/Bootstrap/jbox.min.css" rel="stylesheet" />
<script src="/loan/static/jquery-jbox/2.3/jquery.jBox-2.3.min.js" type="text/javascript"></script>
<script src="/loan/static/jquery/jquery-migrate-1.1.1.js" type="text/javascript"></script>
<script src="/loan/static/common/mustache.min.js" type="text/javascript"></script>
<script type=text/javascript src="/loan/static/artDialog4.1.7/artDialog.source.js?skin=blue"></script>
<script type=text/javascript src="/loan/static/artDialog4.1.7/plugins/iframeTools.source.js"></script>
<link href="/loan/static/common/jeesite.min.css" type="text/css" rel="stylesheet" />
<script src="/loan/static/common/jeesite.js" type="text/javascript"></script>
<script type="text/javascript">var basePath='/loan/a';</script>
<script type="text/javascript">var ctx = '/loan/a', ctxStatic='/loan/static',context='/loan';</script>
<link href="/loan/static/jquery-select2/3.4/select2.min.css" rel="stylesheet" />
<script src="/loan/static/jquery-select2/3.4/select2.min.js" type="text/javascript"></script>
		<!-- Baidu tongji analytics -->
<script>
	var _hmt=_hmt||[];
	(function(){var hm=document.createElement("script");
	hm.src="//hm.baidu.com/hm.js?82116c626a8d504a5c0675073362ef6f";
	var s=document.getElementsByTagName("script")[0];
	s.parentNode.insertBefore(hm,s);})();
</script>
<script src="/loan/js/common.js" type="text/javascript"></script>
<script src="/loan/static/ckfinder/plugins/gallery/colorbox/jquery.min.js"></script>
<script src="/loan/static/ckfinder/plugins/gallery/colorbox/jquery.colorbox-min.js"></script>
<link media="screen" rel="stylesheet" href="/loan/static/ckfinder/plugins/gallery/colorbox/colorbox.css" />
<script src="/loan/js/grant/grantDone.js" type="text/javascript"></script>
<script>
function page(n, s) {
	if (n)
		$("#pageNo").val(n);
	if (s)
		$("#pageSize").val(s);
	$("#grantForm").attr("action", "/loan/a/car/CarLoanAdvisoryBacklog");
	$("#grantForm").submit();
	return false;
}
</script>
<script type="text/javascript">
	<!--咨询待办通过ajax搜索-->
	
	$(document).ready(function(){
		
	})
</script>
</head>
<body>
 <ul id="myTabs" class="nav nav-tabs" >
    <li class="active"><a href="#appraiser" aria-controls="#appraiser" data-toggle="tab">评估师录入</a></li>
    <li><a href="#profile" aria-controls="profile" data-toggle="tab">退回后评估师录入</a></li>
  </ul>
  
  <div class="tab-content">
	<div class="control-group">
	<form id="grantForm"  action="/loan/a/car/CarLoanAdvisoryBacklog/CarLoanAdvisoryBacklog" method="post">
        <table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <input id="pageNo" name="pageNo" type="hidden" value="" /> 
			    <input id="pageSize" name="pageSize" type="hidden" value="" />
                <td><label class="lab">客户姓名1：</label><input id="customerName" name="customerName" class="input_text178" type="text" value=""/></td>
                <td><label class="lab">客户经理：</label><input id="customerManager" name="customerManager" class="input_text178" type="text" value=""/></td>
                <td><label class="lab">咨询状态：</label><select id="dictOperStatus" name="dictOperStatus" class="select180">
                <option value="">请选择</option>
                	<c:forEach items="${fns:getDictList('jk_next_step')}" var="item">
		      				<option value="${item.value}">${item.label}</option>
		  			</c:forEach>
				</select></td>
            </tr>
            <tr>
                <td><label class="lab">预计到店时间：</label>
                	<input id="planArrivalTime" name="planArrivalTime" onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" class="Wdate input_text70" type="text" value="" size="10"/>-
                	<input id="planArrivalTimeend" name="planArrivalTimeend" onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" class="Wdate input_text70" type="text" value="" size="10"/></td>
				 <td><label class="lab">是否电销：</label>
					  <input type="radio" name="consTelesalesFlag" value="1">是</input>
					  <input type="radio" name="consTelesalesFlag" value="0">否</input>		
                    </td>
            </tr>
        </table>
        <div class="tright pr30 pb5"><input class="btn btn-primary" id="search" type="submit" value="搜索"></input>
                  <button class="btn btn-primary" id="clearBtn">清除</button>
               
		</div>
		</form>
		</div>
    <table class="table  table-bordered table-condensed table-hover ">
    <thead>
        <tr>
            <th><input type="checkbox" id="checkAll"/></th>
            <th>序号</th>
            <th>客户姓名</th>
            <th>门店名称</th>
            <th>预计到店时间</th>
            <th>车辆型号</th>
            <th>客户经理</th>
            <th>团队经理</th>
            <th>咨询状态</th>
            <th>是否电销</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        	 <c:forEach items="${resultList}" var="b" varStatus="be">
             <tr>
             <td><input type="checkbox" name="checkBoxItem" fId="HJSC000100005"/></td>
             <td>${be.index+1}</td>
             <td>${b.customerName}</td>
             <td>${b.mingDiang}</td>
             <td><fmt:formatDate value='${b.planArrivalTime}' pattern="yyyy-MM-dd"/></td>
             <td>${b.vehicleBrandModel}</td>
             <td>${b.customerManager}</td>
             <td>${b.teamManager}</td>
             <td><c:choose>
             			<c:when test="${b.dictOperStatus==0}">继续跟踪</c:when>
             			<c:otherwise>客户放弃</c:otherwise>
             	</c:choose>
             </td>
             <td><c:choose><c:when test="${b.consTelesalesFlag==0}">否</c:when>
             				<c:otherwise>是</c:otherwise>
             </c:choose>
             </td>
				 <td class="tcenter"><button class="btn_edit" onclick="window.location='${ctx}/bpm/flowController/openLaunchForm?flowCode=loanCarFlow&loanCode=${b.loanCode}&customerCode=${b.customerCode}'">办理</button></td>
             </tr>
             </c:forEach>
       </tbody>   
    </table>
   </div>
</body>
</html>