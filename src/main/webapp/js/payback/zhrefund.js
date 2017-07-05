$(document).ready(function(){
	// 初始化门店下拉框
	loan.getstorelsit("txtStore","hdStore");
	/**
	 * @function 初始化tab
	 */
	$('#refundTab').bootstrapTable('destroy');
	$('#refundTab').bootstrapTable({
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
		top.$.jBox.tip("正在导入数据", 'loading'); 
		$("#uploadAuditForm").ajaxSubmit({
			type: "POST",
			url:ctx + "/borrow/payback/zhrefund/importData",
			dataType: "json",
		    success: function(data){
		     	if(data.code=='2'){
		     		top.$.jBox.closeTip();
		     		
		   		} else if(data.code=='0'){
		    		top.$.jBox.closeTip();
		    		top.$.jBox.tip(data.msg, 'success');
		    		window.setTimeout(function () { 
		    			window.location.href=ctx+"/borrow/payback/zhrefund/list"; 
		    		}, 2000);
		    		
		    	}else{
		    		top.$.jBox.closeTip();
 			    	window.setTimeout(function () { top.$.jBox.tip(data.msg, 'success'); }, 2000);
		    	}
			}
		});
	})
	
	/**
	 * @function 清除按钮绑定
	 */
	$('#clearBtn').bind('click', function() {
		$(':input','#refundForm')
		  .not(':button, :reset,:hidden')
		  .val('')
		  .removeAttr('checked')
		  .removeAttr('selected');
		$('select').val();
		$("select").trigger("change");
		$("#hdStore").val('');
		document.forms[0].submit();
	});
	
	/**
	 * @function 失效按钮
	 */
	$('#invalidBtn').click(function() {
		var data = $('#refundTab').bootstrapTable('getSelections');
		var contractCodes;
		if(data.length > 0){
			for(var i=0;i<data.length;i++){
				if(typeof(contractCodes)!='undefined'){
					contractCodes +="," + data[i].contractCode;
				}else{
					contractCodes = data[i].contractCode; 
				}
			}
			$.ajax({  
				   type : "POST",
				   async:true,
				   data:{
					   contractCodes:contractCodes,
					   zhrefundStatus:'2'
				   },
					url : ctx+"/borrow/payback/zhrefund/updateStatus",
					datatype : "json",
					success: function(data){
						if(data=='1'){
							window.setTimeout(function () {
								top.$.jBox.tip('修改成功', 'success');
								window.location.href=ctx+"/borrow/payback/zhrefund/list";
							}, 2000);
						}else{
							top.$.jBox.tip('修改失败', 'error');
						}
						
					},
					error: function(){
							top.$.jBox.closeTip();
							top.$.jBox.tip('服务器没有返回数据，可能服务器忙，请重试!','warning');
					}
				});
		}else{
			top.$.jBox.tip('请选择要失效的数据','warning');
		}
	})
	
	// 搜索提交
	$('#auditedSubmitBtn').bind('click', function() {
		
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
	$("#refundForm").attr("action",  ctx + "/borrow/payback/zhrefund/list");
	$("#refundForm").submit();
	return false;
}
/**
 * 点击结清申请按钮时
 * @param contractCode
 * @param dictLoanStatus
 */
function settleApply(contractCode,index){
	if($("#loanStatus_"+index).text()!='逾期'){
		top.$.jBox.tip("只有逾期数据才能进行结清申请");
	}else{
		$.ajax({  
			   type : "POST",
			   async:true,
			   data:{
				   contractCode:contractCode
			   },
				url : ctx+"/borrow/payback/zhrefund/settleApply",
				datatype : "json",
				success: function(data){
					if(data=='1'){
						top.$.jBox.tip("申请成功");
						$("#loanStatus_"+index).text("结清待确认");
						$("#settleApplyBtn_"+index).attr("disabled","true");
					}else{
						top.$.jBox.tip("申请失败");
					}
				}
		})
	}
}