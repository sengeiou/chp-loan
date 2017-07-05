<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="overDateClick" type="java.lang.String" required="true"
	description="页面token"%>
<%@ attribute name="overDateId" type="java.lang.String" required="true"
	description="页面token"%>
<div
	style="width:150px;height: 280px; overflow: scroll; display: none"
	id="overDayDiv">
	<table style=""
		class="table  table-bordered table-condensed table-hover data-list-table">
		<tr>
			<th><input type="checkbox"
				onclick="checkedAll('overDayChk',this)"></th>
			<th>期数</th>
		</tr>
		<c:forEach items="${overCountList}" var="item">
			<tr style="line-height: 10px">
				<td width="35px"><input type="checkbox" id="${item}" name="overDayChk" value="${item}"></td>
				<td>${item}</td>
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
		var selectBtn = '${overDateClick}';
		var dateId = '${overDateId}';
		$("#" + selectBtn).bind('click', function() {
			art.dialog({
				content : document.getElementById("overDayDiv"),
				title : '累计逾期期数',
				padding:0,
				fixed : true,
				lock:true,
				opacity: .1,
				id : 'confirm',
				okVal : '确认',
				ok : function() {
					var str = "";
					var win = art.dialog.open.origin;
					$("input[name='overDayChk']:checked").each(function() {
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