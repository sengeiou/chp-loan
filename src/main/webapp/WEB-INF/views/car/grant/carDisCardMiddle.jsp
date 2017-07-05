<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>放款确认</title>

<script src="${context}/static/ckfinder/plugins/gallery/colorbox/jquery.min.js"></script>
<script src="${context}/static/ckfinder/plugins/gallery/colorbox/jquery.colorbox-min.js"></script>
<link media="screen" rel="stylesheet" href="${context}/static/ckfinder/plugins/gallery/colorbox/colorbox.css" />
<script src="${context}/js/car/grant/carDisCardDeal.js" type="text/javascript"></script>
<meta name="decorator" content="default" />

</head>



<body>
	
		<iframe id="midPerson"  src="${ctx}/car/grant/disCard/showMidPerson" scrolling="yes" frameborder="no" style="margin:0 auto;width:100%;height:100%;overflow:visible;"></iframe>
		<iframe id="disCardPerson" name="mainFrame" src="${ctx}/car/grant/disCard/showDisPerson" scrolling="yes" frameborder="no" style="margin:0 auto;width:100%;height:100%;overflow:visible;"></iframe>
		<div class="tright mt20 pr30">
					<button class="btn btn-primary" id="middleSureBtn">确认</button>
		</div>
</body>

</html>