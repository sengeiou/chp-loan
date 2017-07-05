<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>预览附件</title>
<meta name="decorator" content="default" />
<link href="${context}/js/customertransfer/slider.css" type="text/css"
	rel="stylesheet" />
<script src="${context}/js/customertransfer/bootstrap-slider.js"
	type="text/javascript"></script>
<style type="text/css">
-moz-transform，-moz-transform-origin -moz-transform:scale (0.5 );
       #ex1 .Slider .slider-selection {
	background: #BABABA;
}

#indicators li {
	text-align: center;
}
</style>
<script type="text/javascript">
	jQuery(document).ready(
			function($) {
				$('#btnMenu').remove();
				$('.slider').slider({
					range : true,
					min : 0,
					max : 10
				}).on(
						"slide",
						function(ui) {
							zoomEle(1 + ((ui.value - 5) / 10),
									1 + ((ui.value - 5) / 10));
						});
				//获取浏览器名称 

				function getbrowser() {
					var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
					var isOpera = userAgent.indexOf("Opera") > -1;

					if (isOpera) {
						return "Opera"
					}
					; //判断是否Opera浏览器
					if (userAgent.indexOf("Firefox") > -1) {
						return "FF";
					} //判断是否Firefox浏览器
					if (userAgent.indexOf("Safari") > -1) {
						return "Safari";
					} //判断是否Safari浏览器
					if (userAgent.indexOf("compatible") > -1
							&& userAgent.indexOf("MSIE") > -1 && !isOpera) {
						return "IE";
					}
					;
					//判断是否IE浏览器
				}

				/** 进行缩放 */
				/**xSacle x方向缩放的大小*/
				/**yScale y方向缩放的大小*/
				function zoomEle(xScale, yScale) {
					$.each($('[id="f1"]'), function() {
						var el = this;
						var name = getbrowser();
						style = el.getAttribute('style') || "";
						if (name == "IE") {
							if (document.compatMode == "CSS1Compat") {//模式匹配 解决ie8下兼容模式
								el.style.width = el.clientWidth * 2.0;
								el.style.height = el.clientHeight * 2.0;
							}
							el.style.zoom = xScale;
						} else if (name == "FF") {
							el.style.transform = 'scale(' + xScale + ', '
									+ yScale + ')';
							el.style.transformOrigin = '0px 0px';
						} else {
							el.setAttribute('style', style
									+ '-webkit-transform: scale(' + xScale
									+ ', ' + yScale
									+ '); -webkit-transform-origin: 0px 0px;');
						}
					});
				}
			});

	function st() {
		var dd = document.getElementById('d1');
		dd.innerText = "W:" + dd.clientWidth + ",H:" + dd.clientWidth;
	}

	// Without JQuery
</script>
</head>
<body style="overflow-x: hidden;">
	<input id="ex1" class="slider" style="width: 300px; zoom: 1;" />
	<div id="myCarousel" class="carousel slide">

		<!-- 轮播（Carousel）指标 -->
		<ol class="carousel-indicators" id="indicators"
			style="width: 80%; height: 1px;">
			<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
			<c:forEach items="${list}" var="item" varStatus="statu">
				<li data-target="#myCarousel" data-slide-to="${statu.index + 1}"></li>
			</c:forEach>
		</ol>
		<!-- 轮播（Carousel）项目 -->
		<div class="carousel-inner">
			<div class="item active"
				style="width: 1201px; height: 801px; border: 1px solid #ccc; margin-left: auto; margin-right: auto;">
				<iframe width="100%" id="f1" height="100%" scrolling="yes"
					src="${ctx}/common/file/download/${seletive.attaId}/preview"
					alt="First slide"></iframe>
			</div>
			<c:forEach items="${list}" var="itemv" varStatus="status">
				<div class="item"
					style="width: 1201px; height: 801px; border: 1px solid #ccc; margin-left: auto; margin-right: auto;">
					<iframe width="80%" id="f1" height="100%" scrolling="yes"
						src="${ctx}/common/file/download/${itemv.attaId}/preview"
						alt="Third slide"></iframe>
				</div>
			</c:forEach>
		</div>
		<!-- 轮播（Carousel）导航 -->
		<a class="left carousel-control" style="width: 5%;" href="#myCarousel"
			data-slide="prev">&lsaquo;</a> <a class="right carousel-control"
			style="width: 5%;" href="#myCarousel" data-slide="next">&rsaquo;</a>
		<!-- 控制按钮 -->
	</div>
</body>
</html>