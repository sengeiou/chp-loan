<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="dateClick" type="java.lang.String" required="true"
	description="页面token"%>
<%@ attribute name="dateId" type="java.lang.String" required="true"
	description="页面token"%>
<div
	style="width:150px;height: 280px; overflow: scroll; display: none"
	id="dayDiv">
	<table style=""
		class="table  table-bordered table-condensed table-hover data-list-table">
		<tr>
			<th><input type="checkbox"
				onclick="checkedAll('dayChk',this)"></th>
			<th>还款日</th>
		</tr>
		<c:forEach items="${dayList}" var="item">
			<tr style="line-height: 10px">
				<td width="35px"><input type="checkbox" id="${item.billDay}" name="dayChk" value="${item.billDay}"></td>
				<td>${item.billDay}</td>
			</tr>
		</c:forEach>
	</table>
</div>
<script type="text/javascript">
	function checkedAll(checkBoxName, obj) {
		var ls = document.getElementsByName(checkBoxName);
		for (var i = 0; i < ls.length; i++) {
			ls[i].checked = obj.checked;
		}
	}
	jQuery(document).ready(function($) {
		var selectBtn = '${dateClick}';
		var dateId = '${dateId}';
		$("#" + selectBtn).bind('click', function() {
			art.dialog({
				content : document.getElementById("dayDiv"),
				title : '还款日选择',
				padding:0,
				fixed : true,
				lock:true,
				opacity: .1,
				id : 'confirm',
				okVal : '确认',
				ok : function() {
					var str = "";
					var win = art.dialog.open.origin;
					$("input[name='dayChk']:checked").each(function() {
						if ($(this).attr("checked")) {
							str += $(this).attr("id") + ",";
						}
					});
					str = str.replace(/,$/g, "");
					$("#" + dateId, win.document).val(str);
					return true;
				},
				cancel : true
			});
		});
	});
</script>