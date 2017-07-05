<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%><%--
<html>
<head>
	<title>菜单导航</title>
	<meta name="decorator" content="blank"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".accordion-heading a").click(function(){
				$('.accordion-toggle i').removeClass('icon-chevron-down');
				$('.accordion-toggle i').addClass('icon-chevron-right');
				if(!$($(this).attr('href')).hasClass('in')){
					$(this).children('i').removeClass('icon-chevron-right');
					$(this).children('i').addClass('icon-chevron-down');
				}
			});
			$(".accordion-body a").click(function(){
				$("#menu-${param.parentId} li").removeClass("active");
				$("#menu-${param.parentId} li i").removeClass("icon-white");
				$(this).parent().addClass("active");
				$(this).children("i").addClass("icon-white");
				//loading('正在执行，请稍等...');
			});
			//$(".accordion-body a:first i").click();
			//$(".accordion-body li:first li:first a:first i").click();
		});
	</script>
</head>
<body> --%>
	<c:set var="menuList" value="${fns:getMenuList('MODULE_LOAN')}"/><c:set var="firstMenu" value="true"/>
	<c:forEach items="${menuList}" var="menu" varStatus="idxStatus">
	<c:if test="${menu.parent.id eq (not empty param.parentId ? param.parentId:1)&&menu.isShow eq '1'}">
		<li class="menu-${param.parentId} accordion">
			<a href="#" class="dropdown-toggle">
				<i class="${not empty menu.icon ? menu.icon : 'icon-desktop'}"></i>
				<span class="menu-text"> ${menu.name}</span>
				<b class="arrow icon-angle-down"></b>
			</a>
			<ul class="submenu" style="display: none;" id="menu-${param.parentId}">
				<c:forEach items="${menuList}" var="menu2"><c:if test="${menu2.parent.id eq menu.id&&menu2.isShow eq '1'}">
				<li class="menu-inner">
					<a data-href=".menu3-${menu2.id}" href="${fn:indexOf(menu2.href, '://') eq -1 ? ctx : ''}${not empty menu2.href ? menu2.href : '/404'}" menu-id="${menu2.id }" target="${not empty menu2.target ? menu2.target : 'mainFrame'}"><i class="icon-${not empty menu2.icon ? menu2.icon : 'circle-arrow-right'}"></i>&nbsp;${menu2.name}</a>
					<ul class="submenu" style="display: block;">
					<c:forEach items="${menuList}" var="menu3"><c:if test="${menu3.parent.id eq menu2.id&&menu3.isShow eq '1'}">
						<li>
							<a href="${fn:indexOf(menu3.href, '://') eq -1 ? ctx : ''}${not empty menu3.href ? menu3.href : '/404'}" menu-id="${menu3.id }" target="${not empty menu3.target ? menu3.target : 'mainFrame'}" ><i class="icon-${not empty menu3.icon ? menu3.icon : 'circle-arrow-right'}"></i>&nbsp;${menu3.name}</a>
						</li>
					 </c:if>
					 </c:forEach>
					</ul>
				</li>
				</c:if></c:forEach>
			</ul>
		</li>
		</c:if>
		</c:forEach>
<%--
</body>
</html> --%>