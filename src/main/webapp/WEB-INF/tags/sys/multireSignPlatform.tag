<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="platClick" type="java.lang.String" required="true"
	description="页面token"%>
<%@ attribute name="platId" type="java.lang.String" required="true"
	description="页面token"%>
<%@ attribute name="platName" type="java.lang.String" required="true"
	description="页面token"%>
<div
	style="width:150px;height: 150px; overflow: scroll; display: none"
	id="platDiv">
	<table style=""
		class="table  table-bordered table-condensed table-hover data-list-table">
		<tr>
			<th><input type="checkbox"
				onclick="checkedAll('platChk',this)"></th>
			<th>签约平台</th>
		</tr>
		<c:forEach items="${fns:getDictList('jk_deduct_plat')}" var="item">
		    <c:if test="${item.value !='6' && item.value !='7' && item.value !='2' && item.value !='4'}">
				<tr style="line-height: 10px">
					<td><input type="checkbox" id="${item.value }"
						sname="${item.label }" name="platChk" value="${item.label }"></td>
					<td>${item.label }</td>
				</tr>
			</c:if>
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
		var selectBtn = '${platClick}';
		var platId = '${platId}';
		var platName = '${platName}'
		$("#" + selectBtn).bind('click', function() {
			
			art.dialog({
				content : document.getElementById("platDiv"),
				title : '平台选择',
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
					$("input[name='platChk']:checked").each(function() {
						if ($(this).attr("checked")) {
							str += $(this).attr("id") + ",";
							strname += $(this).attr("sname") + ",";
						}
					});
					str = str.replace(/,$/g, "");
					strname = strname.replace(/,$/g, "");
					$("#" + platName, win.document).val(strname);
					$("#" + platId, win.document).val(str);
					return true;
				},
				cancel : true
			});
		});
	});
</script>