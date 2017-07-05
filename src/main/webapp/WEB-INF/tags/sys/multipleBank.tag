<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="bankClick" type="java.lang.String" required="true"
	description="页面token"%>
<%@ attribute name="bankName" type="java.lang.String" required="true"
	description="页面token"%>
<%@ attribute name="bankId" type="java.lang.String" required="true"
	description="页面token"%>
<div
	style="width:350px;height: 350px; overflow: scroll; display: none"
	id="bankdiv">
	<table style=""
		class="table  table-bordered table-condensed table-hover data-list-table">
		<tr>
			<th>全选<input type="checkbox"
				onclick="checkedAll('bankChk',this)"></th>
			<th>银行编码</th>
			<th>银行名称</th>
		</tr>
		<c:forEach items="${fns:getDictList('jk_open_bank')}" var="item">
			<tr style="line-height: 10px">
				<td><input type="checkbox" id="${item.value }"
					sname="${item.label }" name="bankChk" value="${item.label }"></td>
				<td>${item.value }</td>
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
		var txtName = '${bankName}';
		var selectBtn = '${bankClick}';
		var bankId = '${bankId}';
		$("#" + selectBtn).bind('click', function() {
			art.dialog({
				content : document.getElementById("bankdiv"),
				title : '银行选择',
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
					$("input[name='bankChk']:checked").each(function() {
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