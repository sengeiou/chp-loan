<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="markClick" type="java.lang.String" required="true"
	description="页面token"%>
<%@ attribute name="markName" type="java.lang.String" required="true"
	description="页面token"%>
<%@ attribute name="markId" type="java.lang.String" required="true"
	description="页面token"%>
<div
	style="width:350px;height: 350px; overflow: scroll; display: none"
	id="markdiv">
	<table style=""
		class="table  table-bordered table-condensed table-hover data-list-table">
		<tr>
			<th>全选<input type="checkbox"
				onclick="checkedAll('markChk',this)"></th>
			<th>渠道名称</th>
		</tr>
		<c:forEach items="${fns:getDictList('jk_channel_flag')}" var="item">
			<tr style="line-height: 10px">
				<td><input type="checkbox" id="${item.value }"
					sname="${item.label }" name="markChk" value="${item.label }"></td>
				<td>${item.label }</td>
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
		var txtName = '${markName}';
		var selectBtn = '${markClick}';
		var bankId = '${markId}';
		$("#" + selectBtn).bind('click', function() {
			art.dialog({
				content : document.getElementById("markdiv"),
				title : '渠道选择',
				padding:0,
				fixed : true,
				lock:true,
				opacity: .1,
				id : 'confirm',
				okVal : '确认',
				ok : function() {
					var str = "";
					var strname = "";
					var win = art.dialog.open.origin;
					$("input[name='markChk']:checked").each(function() {
						if ($(this).attr("checked")) {
							str += $(this).attr("id") + ",";
							strname += $(this).attr("sname") + ",";
						}
					});

					str = str.replace(/,$/g, "");
					strname = strname.replace(/,$/g, "");
					
					$("#" + txtName, win.document).val(strname);
					$("#" + bankId, win.document).val(str);
					return true;
				},
				cancel : true
			});
		});
	});
</script>