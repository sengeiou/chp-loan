$(document).ready(function(){
	
	// 初始化门店下拉框
	loan.getstorelsit("txtStore","hdStore");
	
	// 初始化申请人下拉框
	loan.getuserlist("txtUser","hdUser");

	/**
	 * @function 初始化tab
	 */
	$('#applyTab').bootstrapTable('destroy');
	$('#applyTab').bootstrapTable({
		method: 'get',
		cache: false,
    	height:$(window).height() - 250,
		striped: true,
		pageSize: 20,
		pageNumber:1
	});
	
	// 搜索按钮
	$("#btn-submit").click(function(){	
		$("#applyPaybackForm").attr("action",ctx+"/borrow/payback/applyList/applyList");
		$("#applyPaybackForm").submit();
	});
	// 清除按钮
	$("#clearBtn").click(function(){	
		$(':input','#applyPaybackForm')
		  .not(':button, :reset')
		  .val('')
		  .removeAttr('checked')
		  .removeAttr('selected');
		$('select').val('');
		$("select").trigger("change");
		$("#hdUser").val('');
		$("#hdStore").val('');
	});
	
	
	// 导出按钮
	$("#exportExcel").click(function(){	
		if($("#dataList").html().indexOf('没有待办数据')>0){
			art.dialog.alert("请选择要导出的数据！");
			return;
		}
		var idVal = '';
		$("input:checkbox[name='checkBox']:checked").each(function(){
		 idVal+=","+$(this).val();
		});
		//alert(idVal);
		$("#ids").val(idVal);
		$("#applyPaybackForm").attr("action",ctx+"/borrow/payback/applyList/exportExl");
		$("#applyPaybackForm").submit();
	});
	
	
});
/**
 * @function 页面分页
 * @param n
 * @param s
 * @returns {Boolean}
 */
function page(n, s) {
	if (n)
		$("#pageNo").val(n);
	if (s)
		$("#pageSize").val(s);
	$("#applyPaybackForm").attr("action",  ctx + "/borrow/payback/applyList/applyList");
	$("#applyPaybackForm").submit();
	return false;
}
function callback_refresh()
{
	window.loacation.reload();
	}

/**
 * @function 全选按钮事件
 */
function selectAll(obj){
	$("input[name='checkBox']").each(function(){
		this.checked = !this.checked;
	});
}