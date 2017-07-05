<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="loanStatusClick" type="java.lang.String" required="true"
	description="页面token"%>
<%@ attribute name="loanStatusName" type="java.lang.String" required="true"
	description="页面token"%>
<%@ attribute name="loanStatusId" type="java.lang.String" required="true"
	description="页面token"%>
<div
	style="width:170px;height: 220px; overflow: scroll; display: none"
	id="loanStatusdiv">
	<table style=""
		class="table  table-bordered table-condensed table-hover data-list-table">
		<tr>
			<th>全选<input type="checkbox"
				onclick="checkedAll('loanStatusChk',this)"></th>
		
			<th>借款状态</th>
		</tr>
		<c:forEach items="${loanMap}" var="item">
			<tr style="line-height: 10px">
				<td><input type="checkbox" id="${item.key }"
					sname="${item.value }" name="loanStatusChk" value="${item.value }"></td>
				<td>${item.value }</td>
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
		var txtName = '${loanStatusName}';
		var selectBtn = '${loanStatusClick}';
		var bankId = '${loanStatusId}';
		$("#" + selectBtn).bind('click', function() {
			art.dialog({
				content : document.getElementById("loanStatusdiv"),
				title : '借款状态',
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
					$("input[name='loanStatusChk']:checked").each(function() {
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