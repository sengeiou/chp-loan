<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>放款确认</title>
<script src="${context}/js/grant/disCardDeal.js" type="text/javascript"></script>
<meta name="decorator" content="default" />
</head>
<body>
	<div>
		<div class="tcenter mt20" style="height: 220px">
			<iframe id="midPerson"
				src="${ctx}/borrow/grant/disCard/showMiddlePerson?wayFlag=${wayFlag}" scrolling="yes"
				frameborder="no"
				style="margin: 0 auto; width: 100%; height: 100%; overflow: visible;"></iframe>
		</div>
		<br>
		<div class="tcenter mt20" style="height: 220px">
			<iframe id="disCardPerson" name="mainFrame"
				src="${ctx}/borrow/grant/disCard/showDisPerson" scrolling="yes"
				frameborder="no"
				style="margin: 0 auto; width: 100%; height: 100%; overflow: visible;"></iframe>
		</div>
		<div class="tcenter mt20">
			<button class="btn btn-primary" id="middleSureBtn">确认</button>
		</div>
	</div>
</body>

</html>