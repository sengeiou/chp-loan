$(document).ready(function(){
	/**
	 * @function 初始化tab
	 */
	$('#urgeAuditedTab').bootstrapTable('destroy');
	$('#urgeAuditedTab').bootstrapTable({
		method: 'get',
		cache: true,
		height: 330,
		striped: true,
		pageSize: 20,
		checkboxHeader: true,
		clickToSelect: true,
		pageNumber:1
	});
	
	/**
	 * @function 加载弹出框
	 */
	var msg = $("#msg").val();
	if (msg != "" && msg != null) {
		art.dialog.alert(msg);
	}
	
	/**
	 * @function 查账办理按钮弹出框
	 */
	$(":input[name='auditedMalBtn']").each(function(){
		$(this).bind('click',function(){
			$('#transferAccountsId').val($(this).attr('transferAccountsId'));
			$('#applyId').val($(this).attr('rPaybackApplyId'));
			$('#outAuditId').val($(this).attr('auditedId'));
			$('#outReallyAmount').val($(this).attr('outReallyAmount'));
			$('#orderNumber').val($(this).attr('orderNumber'));
			if($(this).attr('outAuditStatus')=='2'){
				$('#outAuditStatus').val("2").trigger("change");
				$("#updateAuditForm").setForm({
					"updateContractCode": $(this).attr('contractCode'),
					"contractCodeTemp": $(this).attr('contractCode'),
					"outTimeCheckAccount": $(this).attr('outTimeCheckAccount'),
					"transferAccountsId":$(this).attr('transferAccountsId')
				});
			}else{
				$('#outAuditStatus').val("0").trigger("change");
			}
			$('#auditedModal').css({
				width: 'auto',
				height: 'auto',
				'margin-left': 0,
				display: 'block',
				'padding-right': 0
			})
		});
	});
	
	/**
	 * @function 查账下拉框事件
	 */
	$('#outAuditStatus').change(function(){ 
		var d = new Date($('#outTimeCheckAccount').val());
		if($(this).children('option:selected').val()=='0'){
			$("#updateAuditForm").setForm({ 
				"updateContractCode": '',
				"outTimeCheckAccount": ''
			});
		}
	}) 
	
	/**
	 * @function 修改查账状态
	 */
	$('#updateAudited').click(function(){
		$('#updateAuditForm').submit();
	});
	
	/**
	 * @function 导出查账账款数据
	 */
	$('#derivedAuditedBtn').click(function(){
		var data = $('#urgeAuditedTab').bootstrapTable('getSelections');
		var audited = "";
		if(data.length > 0){
			for(var i=0;i<data.length;i++){
				if(typeof(audited)!='undefined' && audited.length > 0){
					audited +="," + data[i].id;
				}else{
					audited = data[i].id;
				}
	    	}
		}
		window.location.href=ctx+"/borrow/grant/urgeCheckAudited/derivedAuditedExl?audited="+audited;
	})
	
	/**
	 * @function 导入银行流水
	 */
	$('input[type=file]').change(function() {
			$('#photoCover').val($(this).val());
	})
	
	/**
	 * @function 导入银行流水确认
	 */
	$('#uploadAuditedFile').click(function() {
			$('#uploadAuditForm').submit();  
	})
	
	/**
	 * @function 清除按钮绑定
	 */
	$('#clearAuditedBtn').bind('click', function() {
		$(':input','#urgeAuditForm') 
		.not(':button, :submit, :reset, :hidden') 
		.val('') 
		.removeAttr('selected');
		// 清除select			
		$("select").val("");
		$("select").trigger("change");	
	});
});

/**
 * @function 表单set方法
 * @param $
 */
(function ($) {
    $.fn.setForm = function (jsonValue) {
         var obj=this;
        $.each(jsonValue, function (id, ival) {obj.find("#" + id).val(ival); })
        }
})(jQuery)

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
	$("#urgeAuditForm").attr("action",  ctx + "/borrow/grant/urgeCheckAudited/list");
	$("#urgeAuditForm").submit();
	return false;
}