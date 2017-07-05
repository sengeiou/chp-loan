<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="blank" />
<c:set var="tabmode"
	value="${empty cookie.tabmode.value ? '1' : cookie.tabmode.value}" />
<title>Insert title here</title>
<link rel="Stylesheet"
	href="${ctxStatic}/jerichotab/css/jquery.jerichotab.css" />
<%--<script type="text/javascript" src="${context}/js/consult/huijing.js"/> --%>
<script type="text/javascript" src="${context}/js/launch/clevertabs.js"></script>

<style type="text/css">
.ui-tabs-hide {
	display: none !important;
}

#main {
	padding: 0;
	margin: 0;
}

#main .container-fluid {
	padding: 0 4px 0 6px;
}

#header {
	margin: 0 0 8px;
	position: static;
}

#header li {
	font-size: 14px;
	_font-size: 12px;
}

#header .brand {
	font-family: Helvetica, Georgia, Arial, sans-serif, 黑体;
	font-size: 26px;
	padding-left: 33px;
}

#footer {
	margin: 8px 0 0 0;
	padding: 3px 0 0 0;
	font-size: 11px;
	text-align: center;
	border-top: 2px solid #0663A2;
}

#footer, #footer a {
	color: #999;
}

#left {
	overflow-x: hidden;
	overflow-y: auto;
}

#left .collapse {
	position: static;
}
</style>
<script type="text/javascript">
var tabs=null;
	$(document).ready(
			function() {
				tabs = $('#tabs').cleverTabs({
					panelContainer : $('#jerichotab_contentholder')
				});
				tabs.add({
					url : "${ctx}/apply/dataEntry/getApplyInfo?customerCode="
							+ $('#customerCode').val() + "&loanCode="
							+ $("#loanCode").val()
							+ "&flag=1&viewName=_loanFlowCustomer",
					label : "个人资料",
					selected : true
				});
				tabs.add({
					url : "${ctx}/apply/dataEntry/getApplyInfo?customerCode="
							+ $('#customerCode').val() + "&loanCode="
							+ $("#loanCode").val()
							+ "&flag=2&viewName=_loanFlowMate",
					label : '配偶资料'
				});
				tabs.add({
					url : "${ctx}/apply/dataEntry/getApplyInfo?customerCode="
							+ $('#customerCode').val() + "&loanCode="
							+ $("#loanCode").val()
							+ "&flag=3&viewName=_loanFlowApplyInfo&consultId="+$('#consultId').val(),
					label : "申请信息"
				});
				tabs.add({
					url : "${ctx}/apply/dataEntry/getApplyInfo?customerCode="
							+ $('#customerCode').val() + "&loanCode="
							+ $("#loanCode").val()
							+ "&flag=4&viewName=_loanFlowCoborrower",
					label : "共同借款人"
				});
				tabs.add({
					url : "${ctx}/apply/dataEntry/getApplyInfo?customerCode="
							+ $('#customerCode').val() + "&loanCode="
							+ $("#loanCode").val()
							+ "&flag=5&viewName=_loanFlowCreditInfo",
					label : "信用资料"
				});
				tabs.add({
					url : "${ctx}/apply/dataEntry/getApplyInfo?customerCode="
							+ $('#customerCode').val() + "&loanCode="
							+ $("#loanCode").val()
							+ "&flag=6&viewName=_loanFlowCompany",
					label : "职业信息/公司资料"
				});
				tabs.add({
					url : "${ctx}/apply/dataEntry/getApplyInfo?customerCode="
							+ $('#customerCode').val() + "&loanCode="
							+ $('#loanCode').val()
							+ "&flag=7&viewName=_loanFlowHouse",
					label : "房产资料"
				});
				tabs.add({
					url : "${ctx}/apply/dataEntry/getApplyInfo?customerCode="
							+ $('#customerCode').val() + "&loanCode="
							+ $('#loanCode').val()
							+ "&flag=8&viewName=_loanFlowContact",
					label : "联系人资料"
				});
				tabs.add({
					url : "${ctx}/apply/dataEntry/getApplyInfo?customerCode="
							+ $('#customerCode').val() + "&loanCode="
							+ $('#loanCode').val()
							+ "&flag=9&viewName=_loanFlowBank",
					label : "银行卡资料"
				});
			});

	/* 	
	 function reload()
	 {		
	 document.title = "【　　　】";
	 }
	
	 $(window).bind('load', function(e){
	 close_window = true;
	 $("a").bind('click', function(e){
	 close_window = false;
	 });
	 $("form").bind('submit', function(e){
	 close_window = false;
	 });
	 if (window.addEventListener) {
	 window.addEventListener('beforeunload', message, false);
	 } else {
	 window.attachEvent('onbeforeunload', message);
	 }
	 function message(ev) {
	 //alert(close_window);
	 }
	 });
	 */
</script>
</head>
<body>
	<div id="main">
		<form id="custInfoForm">
			<input type="hidden" value="${workItem.bv.loanCustomer.loanCode}"
				name="loanInfo.loanCode" id="loanCode" /> <input type="hidden"
				value="${workItem.bv.loanCustomer.id}" name="loanCustomer.id"
				id="custId" /> <input type="hidden"
				value="${workItem.bv.loanCustomer.customerCode}"
				name="loanCustomer.customerCode" id="customerCode" /> <input
				type="hidden" value="${workItem.bv.customerName}"
				name="loanInfo.loanCustomerName" id="loanCustomerName" /> <input
				type="hidden" value="${workItem.bv.customerName}"
				name="loanCustomer.customerName" />
				<input type="hidden" value="${workItem.bv.consultId}" id="consultId"/>
		</form>
		<form id="redirectForm">
			<input type="hidden" name="queue" value="HJ_CUSTOMER_AGENT" /> <input
				type="hidden" name="viewName" value="loanflow_borrowlist_workItems" />
		</form>
		<form id="flowParam">
			<div>
				<input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
				<input type="hidden" value="${workItem.flowName}" name="flowName"></input>
				<input type="hidden" value="${workItem.stepName}" name="stepName"></input>
				<input type="hidden" value="${workItem.flowType}" name="flowType"></input>
			</div>
		</form>
		<div class="container-fluid">
			<div id="content" class="row-fluid">
				<div id="main_content">
					<div class="jericho_tab" id="jerichotab">
						<div class="tab_pages">
							<div class="tabs" id="tabs">
								<ul></ul>
							</div>
						</div>
						<div class="tab_content">
							<div class="content" id="jerichotab_contentholder">
								<div class="righttag" style="display: none;">正在加载...</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var tabTitleHeight = 26; // 页签的高度
		var htmlObj = $("html"), mainObj = $("#main");
		var headerObj = $("#header"), footerObj = $("#footer");
		var frameObj = $("#main_content, #main_content iframe");
		function wSize() {
			var minHeight = 500, minWidth = 980;
			var strs = getWindowSize().toString().split(",");
			htmlObj.css({
				"overflow-x" : strs[1] < minWidth ? "auto" : "hidden",
				"overflow-y" : strs[0] < minHeight ? "auto" : "hidden"
			});
			mainObj.css("width", strs[1] < minWidth ? minWidth - 10 : "auto");
			frameObj.height((strs[0] < minHeight ? minHeight : strs[0])
					- headerObj.height() - footerObj.height()
					- (strs[1] < minWidth ? 42 : 28));
			$("#jerichotab_contentholder").height(
					$("#main_content").height() - tabTitleHeight);
			$(".jericho_tab iframe").height(
					$("#main_content").height() - tabTitleHeight);
		}
		if (!Array.prototype.map)
			Array.prototype.map = function(fn, scope) {
				var result = [], ri = 0;
				for (var i = 0, n = this.length; i < n; i++) {
					if (i in this) {
						result[ri++] = fn.call(scope, this[i], i, this);
					}
				}
				return result;
			};
		var getWindowSize = function() {
			return [ "Height", "Width" ].map(function(name) {
				return window["inner" + name]
						|| document.compatMode === "CSS1Compat"
						&& document.documentElement["client" + name]
						|| document.body["client" + name];
			});
		};
		$(window).resize(function() {
			wSize();
		});
		wSize();
	</script>
</body>
</html>