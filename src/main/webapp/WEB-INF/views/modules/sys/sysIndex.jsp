<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>${fns:getConfig('productName')}</title>
	<meta name="decorator" content="blank"/>
	<link rel="Stylesheet" href="${ctxStatic}/jerichotab/css/jquery.jerichotab.css" />
    <script type="text/javascript" src="${ctxStatic}/jerichotab/js/jquery.jerichotab.js"></script>
	<c:set var="tabmode" value="${empty cookie.tabmode.value ? '1' : cookie.tabmode.value}"/>
	<%@ include file="/WEB-INF/views/include/pushInfo.jsp"%>
	<script type="text/javascript">
		$(document).ready(function() {
			$.fn.initJerichoTab({
                renderTo: '#right', uniqueId: 'jerichotab',
                contentCss: { 'height': $('#right').height() - tabTitleHeight },
                tabs: [], loadOnce: true, tabWidth: 110, titleHeight: tabTitleHeight
            });
			$("#sidebar-collapse").click(function(){
				wSize();
			});
			// 绑定菜单单击事件
			$("#menu a.menu").click(function(){
				// 一级菜单焦点
				$("#menu li.menu").removeClass("active");
				$(this).parent().addClass("active");
				// 左侧区域隐藏
				if ($(this).attr("target") == "mainFrame"){
					$("#left,#openClose").hide();
					wSizeWidth();
					// <c:if test="${tabmode eq '1'}"> 隐藏页签
					$(".jericho_tab").hide();
					$("#mainFrame").show();//</c:if>
					return true;
				}
				// 显示二级菜单
				var menuId = ".menu-" + $(this).attr("data-id");
				if ($(menuId).length > 0){
					$("#leftmenu .accordion").hide();
					$("#leftmenu "+menuId).show();
					// 初始化点击第一个二级菜单
					//$(menuId + " ul:first li:first a").click();
					wSize();
				}else{
					// 获取二级菜单数据
					$.get($(this).attr("data-href"), function(data){
						if (data.indexOf("id=\"loginForm\"") != -1){
							alert('未登录或登录超时。请重新登录，谢谢！');
							top.location = "${ctx}";
							return false;
						}
						$("#leftmenu .accordion").hide();
						$("#leftmenu").append(data);
						// 链接去掉虚框
						$(menuId + " a").bind("focus",function() {
							if(this.blur) {this.blur()};
						});
						// 二级标题
						$(menuId + " .accordion-heading a").click(function(){
							$(menuId + " .accordion-toggle i").removeClass('icon-chevron-down').addClass('icon-chevron-right');
							if(!$($(this).attr('data-href')).hasClass('in')){
								$(this).children("i").removeClass('icon-chevron-right').addClass('icon-chevron-down');
							}
						});
						// 二级内容
						$(menuId + " .accordion-body a").click(function(){
							$(menuId + " li").removeClass("active");
							$(menuId + " li i").removeClass("icon-white");
							$(this).parent().addClass("active");
							$(this).children("i").addClass("icon-white");
						});
						// 展现三级
						$(menuId + " .menu-inner a").click(function(){
							var href = $(this).attr("data-href");
							if($(href).length > 0){
								$(href).toggle().parent().toggle();
								return false;
							}
							// <c:if test="${tabmode eq '1'}"> 打开显示页签
							return addTab($(this)); // </c:if>
						});
						// 默认选中第一个菜单
						//$(menuId + " ul:first li:first a").click();
						wSize();
					});
				}
				// 大小宽度调整
				//wSizeWidth();
				return false;
			});
			setTimeout(function(){$("#systemsetA").mouseup();}, 600);
			$("#systemsetA").mouseup(function(){
				return addTab($(this), true);
			});
			setTimeout(function(){
				var $a = $("<a href='${ctx}${menuUrl}' menu-id='${menuId}'>${menuName}</a>");
				if('${menuUrl}' != ''){
					addTab($a,true);
				}
			}, 600);
		});
		function addTab($this, refresh){
			$(".jericho_tab").show();
			$("#mainFrame").hide();
			var url = $this.attr('href');
			var menuId = $this.attr('menu-id');
			if(menuId!=""&&menuId!="undefined"){
				if(url.indexOf("?")>-1){
					url += "&menuId="+menuId;
				}else{
					url += "?menuId="+menuId;
				}
			}
			
			
			$.fn.jerichoTab.addTab({
                tabFirer: $this,
                title: $this.text(),
                closeable: true,
                data: {
                    dataType: 'iframe',
                    dataLink: url
                }
            }).loadData(refresh);
			return false;
		}
		
		function switchOrg(){
			location.href="${ctx}/?orgId="+$("#switchOrgSel").val();
		}
		// 金账户修改待审核
		$(function() {
			setTimeout("getGoldAccountCount()",1000); //金账户修改待审核
			setTimeout("queryDeductLimit()",1000); //金账户修改待审核
		})
		
		function getGoldAccountCount(){
			$.ajax({
				type : "POST",
				url :ctx+"/borrow/account/repayaccount/getGoldAccountCount",
				dataType : "json",
				success : function(data) {
					if(data>0){
						if (!("Notification" in window)) {
							alert("目前您浏览器的版本不支持消息提醒，请您更新浏览器版本！");
						} else if (Notification.permission === "granted") {
							var notification = new Notification('通知', {
								icon: "http://www.creditharmony.cn/misc/favicon.ico",
								body : "金账户变更审核列表有"+data+"条数据未处理！"
							});
							notification.onclick = function() {
							};
							notification.onshow = function() {
							};
							notification.onclose = function() {
								setTimeout("getGoldAccountCount()", 30*60*1000);
							};
						} else if (Notification.permission !== 'denied') {
							Notification.requestPermission(function (permission) {
								// If the user accepts, let's create a notification
						        if (permission === "granted") {
						        	var notification = new Notification('通知', {
						        		icon: "http://www.creditharmony.cn/misc/favicon.ico",
										body : "金账户变更审核列表有"+data+"条数据未处理！"
									});
									notification.onclick = function() {
									};
									notification.onshow = function() {
									};
									notification.onclose = function() {
										setTimeout("getGoldAccountCount()", 30*60*1000);
									};
						        }
							});
						}
					}else{
						setTimeout("getGoldAccountCount()", 30*60*1000);
					}
				}
			});
		}
		

		function queryDeductLimit(){
			$.ajax({
				type : "POST",
				url :ctx+"/borrow/payback/deduct/queryDeductLimit",
				dataType : "json",
				success : function(data) {
					if(data>0){
						if (!("Notification" in window)) {
							alert("目前您浏览器的版本不支持消息提醒，请您更新浏览器版本！");
						} else if (Notification.permission === "granted") {
							var notification = new Notification('通知', {
								icon: "http://www.creditharmony.cn/misc/favicon.ico",
								body : "余额不足比例/失败率/失败笔数超过所设置比例，有数据未成功发送！"
							});
							notification.onclick = function() {
							};
							notification.onshow = function() {
							};
							notification.onclose = function() {
								setTimeout("queryDeductLimit()", 5*60*1000);
							};
						} else if (Notification.permission !== 'denied') {
							Notification.requestPermission(function (permission) {
								// If the user accepts, let's create a notification
						        if (permission === "granted") {
						        	var notification = new Notification('通知', {
						        		icon: "http://www.creditharmony.cn/misc/favicon.ico",
										body : "余额不足比例/失败率/失败笔数超过所设置比例，有数据未成功发送！"
									});
									notification.onclick = function() {
									};
									notification.onshow = function() {
									};
									notification.onclose = function() {
										setTimeout("queryDeductLimit()", 5*60*1000);
									};
						        }
							});
						}
					}else{
						setTimeout("queryDeductLimit()", 5*60*1000);
					}
				}
			});
		}
	</script>
</head>
<body>
<div id="main">
<div class="navbar navbar-inverse" id="navbar">
			<div class="navbar-container" id="navbar-container">
				<div class="navbar-header pull-left">
					<a href="#" class="navbar-brand">
						<small>
							<i class="icon-leaf"></i>
							${fns:getConfig('productName')}
						</small>
					</a><!-- /.brand -->
				</div><!-- /.navbar-header -->
				<div class="navbar-header" role="navigation">
					<ul id="menu" class="nav ace-nav">
					<c:set var="firstMenu" value="true"/>
						<c:forEach items="${fns:getMenuList('MODULE_LOAN')}" var="menu" varStatus="idxStatus">
							<c:if test="${menu.parent.id eq rootId && menu.isShow eq '1'}">
								<li class="menu ${not empty firstMenu && firstMenu ? ' active' : ''}">
									<c:if test="${empty menu.href}">
										<a class="menu" href="javascript:" data-href="${ctx}/sys/menu/tree?parentId=${menu.id}" data-id="${menu.id}"><span>${menu.name}</span></a>
									</c:if>
									<c:if test="${not empty menu.href}">
										<a class="menu" href="${fn:indexOf(menu.href, '://') eq -1 ? ctx : ''}${menu.href}" data-id="${menu.id}" target="mainFrame"><span>${menu.name}</span></a>
									</c:if>
								</li>
								<c:if test="${firstMenu}">
									<c:set var="firstMenuId" value="${menu.id}"/>
								</c:if>
								<c:set var="firstMenu" value="false"/>
							</c:if>
						</c:forEach>
				</ul>
				</div>
				<div class="navbar-header pull-right" role="navigation">
					<ul class="nav ace-nav">
						<li><a href="#" target="_blank" title="访问网站主页"><i class="icon-home"></i></a></li>
						<li style="padding-right: 10px;">
 						  <select id="switchOrgSel" class="" onChange="switchOrg()">
 						  	 <c:forEach items="${userOrgList}" var="userOrg">
 						  	 	<option value="${userOrg.orgId }" <c:if test="${orgId eq userOrg.orgId}"> selected </c:if> >${userOrg.orgName }</option>
 						  	 </c:forEach>
							</select>
			            </li>
						<li id="userInfo" class="dropdown">
						<a class="dropdown-toggle" data-toggle="dropdown" href="#" title="个人信息">您好, ${fns:getUser().name}&nbsp;<span id="notifyNum" class="label label-info hide"></span></a>
						     <ul class="dropdown-menu">
						         <li id="systemset"><a id="systemsetA" href="${ctx}/sys/user/info"  target="mainFrame" style="cursor:pointer;"><i class="icon-user"></i> 个人信息</a></li>
						         <li id="productCode"><a  id="passwordA"  href="${ctx}/sys/user/modifyPwd" target="mainFrame" style="cursor:pointer;"><i class="icon-cogs"></i> 修改密码</a></li>
						    </ul>
						</li>
						<%
								if(request.getRequestURL().toString().contains("localhost:")){
						%>
								<li class="dropdown"><a href="${ctx}/logout" title="退出登录">退出</a></li>
						<%
								}
						%>
						<li>&nbsp;</li>
							</ul>
						</li>
					</ul><!-- /.ace-nav -->
				</div><!-- /.navbar-header -->
			</div><!-- /.container -->
		</div>
		
			<div id="content" class="rows">
				<div class="sidebar menu-min" id="sidebar">
					<ul class="nav nav-list" id="leftmenu"></ul>
					<div class="sidebar-collapse" id="sidebar-collapse">
						<i class="icon-double-angle-right" data-icon1="icon-double-angle-left" data-icon2="icon-double-angle-right"></i>
					</div>
				</div>
				<div id="right">
					<iframe id="mainFrame" name="mainFrame" scrolling="yes" frameborder="no" style="margin:0 auto;width:100%;height:100%;overflow:visible;"></iframe>
				</div>
			</div>
			</div>
	<script type="text/javascript"> 
		var leftWidth = 160; // 左侧窗口大小
		var tabTitleHeight = 44; // 页签的高度
		var htmlObj = $("html"), mainObj = $("#main");
		var headerObj = $("#header"), footerObj = $("#footer");
		var frameObj = $("#sidebar, #right, #right iframe");
		function wSize(){
			var minHeight = 200, minWidth = 980;
			var strs = getWindowSize().toString().split(",");
			var sidebarHeight = $("#leftmenu").height();
			htmlObj.css({"overflow-x":strs[1] < minWidth ? "auto" : "hidden", "overflow-y":sidebarHeight > minHeight ? "hidden" : "hidden"});
			mainObj.css("width",strs[1] < minWidth ? minWidth - 10 : "auto");
			frameObj.height((strs[0] < minHeight ? minHeight : strs[0]) - (strs[1] < minWidth ? 42 : 28)-10);
			//$(".jericho_tab iframe").height($("#right").height() - tabTitleHeight);
			wSizeWidth();
		}
		function wSizeWidth(){
			if (!$("#openClose").is(":hidden")){
				var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
				$("#right").width($("#content").width()- leftWidth - $("#sidebar").width() -5);
			}else{
				$("#right").width($("#content").width()- leftWidth - $("#sidebar").width());
			}
		}// <c:if test="${tabmode eq '1'}"> 
		function openCloseClickCallBack(b){
			$.fn.jerichoTab.resize();
		} // </c:if>
	</script>
	<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>