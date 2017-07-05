$(function(){
	// 初始化门店下拉框
	loan.getstorelsit("txtStore","hdStore");
	
	// 搜索按钮
	$("#btn-submit").click(function(){	
		$("#waitSendForm").attr("action",ctx+"/borrow/serve/emailServe/sendList");
		$("#waitSendForm").submit();
	});
	
	// 清除按钮
	$("#clearBtn").click(function(){	
		$(':input','#waitSendForm')
		  .not(':button, :reset')
		  .val('')
		  .removeAttr('checked')
		  .removeAttr('selected');
		/*$('select').val('');
		$("select").trigger("change");*/
		$("#hdUser").val('');
		$("#hdStore").val('');
		$("#waitSendForm").attr("action",ctx+"/borrow/serve/emailServe/sendList");
		$("#waitSendForm").submit();
	});
	
})
function selectAll(){
	if($('#checkAllItem').attr('checked')=='checked'|| $('#checkAll').attr('checked'))
	{
		$("input:checkbox[name='checkItem']").each(function() {
			$(this).attr("checked",'true');
		});
	}else{
		$("input:checkbox[name='checkItem']").each(function() {
			$(this).removeAttr("checked");
		});
	}
}
/**
 * 导出
 */
function exportExcel(){
	var idVal = '';
	$("input:checkbox[name='checkItem']:checked").each(function(){
		if(idVal==''){
			idVal = $(this).val();
		}else{
			idVal+=","+$(this).val();
		}
	});
	//alert(idVal);
	$("#ids").val(idVal);
	$("#waitSendForm").attr("action",ctx+"/borrow/serve/emailServe/exportExl");
	$("#waitSendForm").submit();
}


/**
 * 查看历史操作
 * @param applyId
 */
function doOpenhis(loanCode,fileType) {
	var url = ctx + "/borrow/serve/customerServe/historyList?loanCode=" + loanCode + "&fileType=" + fileType;
	art.dialog.open(url, {  
        id: 'his',
        title: '历史列表',
        lock:true,
        width:700,
     	height:350
    }, false);  
}
/**
 * @function 
 * @param n
 * @param s
 * @returns {Boolean}
 */
function page(n, s) {
	if (n)
		$("#pageNo").val(n);
	if (s)
		$("#pageSize").val(s);
	$("#waitSendForm").attr("action",ctx+"/borrow/serve/emailServe/sendList");
	$("#waitSendForm").submit();
	return false;
}
