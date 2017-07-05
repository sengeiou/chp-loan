$(document).ready(function(){
	/**
	 * @function 初始化tab
	 */
	$('#auditedTab').bootstrapTable('destroy');
	$('#auditedTab').bootstrapTable({
		method: 'get',
		cache: true,
		height: $(window).height() - 230,
		striped: true,
		pageSize: 20,
		pageNumber:1
	});
	
	/**
	 * @function 加载弹出框
	 */
	var msg = $("#msg").val();
	if (msg != "" && msg != null) {
		top.$.jBox.tip(msg);
	}
	
	/**
	 * @function 查账办理按钮弹出框
	 */
	$(":input[name='auditedMalBtn']").each(function(){
		$(this).bind('click',function(){
			$('#transferAccountsId').val($(this).attr('transferAccountsId'));
			$('#applyId').val($(this).attr('rPaybackApplyId'));
			$('#outAuditId').val($(this).attr('auditedId'));
			$('#relationType').val($(this).attr('relationType'));
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
				"outTimeCheckAccount": '',
				"outAuditStatusTemp":'1'
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
		var auditeDate = $("#auditForm").serialize();
		var data = $('#auditedTab').bootstrapTable('getSelections');
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
		window.location.href=ctx+"/borrow/payback/zhaudited/derivedAuditedExl?audited=" + audited + "&"+auditeDate;
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
		var files = $('#file').val();
		if(files == "" || file == null ){
			top.$.jBox.tip("请选择文件！");
			return false;
		}
		top.$.jBox.tip("正在导入数据。。", 'loading');
		$("#uploadAuditForm").ajaxSubmit({
			type: "POST",
			url:ctx + "/borrow/payback/zhaudited/importAuditedExl",
			dataType: "json",
		    success: function(data){
		     	if(data.code=='1'){
		     		art.dialog.confirm(data.msg, function(){
		     			$('#confrimFlag').val('1');
		     			$("#uploadAuditForm").ajaxSubmit({
		     				type: "POST",
		     				url:ctx + "/borrow/payback/zhaudited/importAuditedExl",
		     				dataType: "json",
		     			    success: function(data){
		     			    	window.setTimeout(function () { 
		     			    		top.$.jBox.tip(data.msg, 'success'); 
		     			    		window.location.href=ctx+"/borrow/payback/zhaudited/list";
		     			    		}, 2000);
		     				}
		     			});
		     		});
		     		top.$.jBox.closeTip();
		   		 } else if(data.code=='0'){
  			    	window.setTimeout(function () { 
  			    		top.$.jBox.tip(data.msg, 'success'); 
  			    		window.location.href=ctx+"/borrow/payback/zhaudited/list";
  			    	}, 2000);
		    	}else{
 			    	window.setTimeout(function () { top.$.jBox.tip(data.msg, 'success'); }, 2000);
		    	}
			}
		});
	})
	
	/**
	 * @function 清除按钮绑定
	 */
	$('#clearAuditedBtn').bind('click', function() {
		$(':input','#auditForm')
		  .not(':button, :reset,:hidden')
		  .val('')
		  .removeAttr('checked')
		  .removeAttr('selected');
		$("#startDepositdDate").val('');
		$("#endDepositDate").val('');
		$("#startAuditedDate").val('');
		$("#endAuditedDate").val('');
		$('select').val();
		$("select").trigger("change");
		document.forms[0].submit();
	});
	
	// 搜索提交
	$('#auditedSubmitBtn').bind('click', function() {
		var beginOutReallyAmount = $("[name='beginOutReallyAmount']").val();
		var endOutReallyAmount = $("[name='endOutReallyAmount']").val();
		if(beginOutReallyAmount != ''){
			if(!(/^[0-9]+([.]\d{1,2})?$/).test(beginOutReallyAmount)){
	    		top.$.jBox.tip("金额只能输入数字且保留二位小数！"); 
	    	 	return false;
			}
		}

		if(endOutReallyAmount != ''){
			if(!(/^[0-9]+([.]\d{1,2})?$/).test(endOutReallyAmount)){
	    		top.$.jBox.tip("金额只能输入数字且保留二位小数！"); 
	    	 	return false;
			}
		}
		$('#auditForm').submit();
	});
});

function checkField(val){
	if(val != ''){
		if(!(/^[0-9]+([.]\d{1,2})?$/).test(val)){
			top.$.jBox.tip("金额只能输入数字且保留二位小数！"); 
		}
	}
}

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
	$("#auditForm").attr("action",  ctx + "/borrow/payback/zhaudited/list");
	$("#auditForm").submit();
	return false;
}