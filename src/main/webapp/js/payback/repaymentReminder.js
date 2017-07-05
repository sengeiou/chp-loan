

//多选 全选
$(document).ready(
		function() {
			var count=0;
			var cmoney=0;
			/*$("[name='checkBox']").each(
					function(){
						$(this).bind('click', function() {
							if($(this).attr('checked') == 'checked'
								|| $(this).attr('checked')){
								count+=1;
								cmoney=cmoney+parseFloat($(this).attr('cmoney'),10);
							}else{
								count-=1;
								cmoney-=parseFloat($(this).attr('cmoney'),10);
								$("#checkAll").removeAttr("checked");
							}
							$('#count').text(count);
							$('#cmoney').text(cmoney);	
						});
						
			});*/
			$(":input[name='checkBox']").click(function (){
				var $checkBox = $(":input[name='checkBox']:checked"),$box = $(":input[name='checkBox']");
				$("#checkAll").prop("checked", $checkBox.length == $box.length ? true : false);
				if ($(this).is(":checked")) {
					count += 1;
					cmoney += parseFloat($.isBlank($(this).attr("cmoney")),10);
				} else {
					if ($checkBox.length == 0) {
						count = 0,
						cmoney = 0.0;
					} else {
						count -= 1;
						cmoney -= parseFloat($.isBlank($(this).attr("cmoney")),10);
					}
				}
				$('#count').text(count);
				$('#cmoney').text(fmoney(cmoney,2));
			});
		
		$('#checkAll').bind(
				'click',
				function() {
					
					var $checkBox = $(":input[name='checkBox']");
					$checkBox.prop("checked",this.checked);
					if ($(this).is(":checked")) {
						$checkBox.each(function(){
							count += 1;
							cmoney += parseFloat($.isBlank($(this).attr("cmoney")),10);
						});
					}
					else{
						count = 0,
						cmoney = 0.0;
					}
					$('#count').text(count);
					$('#cmoney').text(fmoney(cmoney,2));
					/*if ($('#checkAll').attr('checked') == 'checked'
							|| $('#checkAll').attr('checked')) {
						count+=1;
						cmoney=cmoney+parseFloat($(":input[name='checkBox']").attr('cmoney'),10);
						$(":input[name='checkBox']").attr("checked",'true');
					}else{
						count=0;
						cmoney='0.0';
						$(":input[name='checkBox']").removeAttr("checked");
					}
					$('#count').text(count);
					$('#cmoney').text(cmoney);
				});*/
				});
});
$(document).ready(function(){
	//短信提醒推送
	$('#informationAlertPush').click(function() {
		var checkBoxValue="";
		var checkList=$("input:checkbox[name='checkBox']:checked").val();
		var num = $("input:checkbox[name='checkBox']:checked").length;
		var confirmMsg ="";
		if($("input:checkbox[name='checkBox']:checked").length==0)
		{
			confirmMsg = "是否确认发送全部数据";
		}
		else{
			confirmMsg = "请确认是否发送数据至借款短信发送列表？发送总笔数："+num+"笔";
		}
		 $("input:checkbox[name='checkBox']:checked").each(function(){
			 if(checkList != null){
				 checkBoxValue +=$(this).val()+ ",";
			 }
		  });
		 art.dialog.confirm(confirmMsg, function(){
			                   $("#idStr").val(checkBoxValue);
			                   $("#auditForm").attr("action",ctx + '/borrow/payback/overdueManager/informationAlert');
			                   $("#auditForm").submit();
		
				});
		  
	});
	
	//还款日验证
	$("#repaymentDate").blur(function(){
		var da = $("#repaymentDate").val();
		if (da != null && "" != da) {
			var dar = eval(da);
			if (dar>31 || dar<1 ) {
				artDialog.alert('请输入1~31之间的数字!');
				$("#repaymentDate").focus();
				return;
			}
		}
	});
	
	
	// 导出
	$("#exportExcel").click(function(){
		var idStr = "";
		if($("input:checkbox[name='checkBox']:checked").length>0){
		   $("input:checkbox[name='checkBox']:checked").each(function(){
				idStr += $(this).val() + ","; 
			});
		}
		$("#idStr").val(idStr);
		$("#auditForm").attr('action',ctx+"/borrow/payback/overdueManager/exportExcel");
		$("#auditForm").submit();
		$("#auditForm").attr('action',ctx+"/borrow/payback/overduemanager/overdueManagerList");
		
	});
	
	// 搜索按钮绑定事件
	/*$("#searchBtn").click(function(){
		$("#auditForm").attr('action',ctx+"/borrow/payback/overduemanager/overdueManagerList");
		$("#auditForm").submit();
	});
	*/

	// 清除按钮
	$("#clearBtn").click(function(){	
		// 清除text	
		$(":input").val('');
		$("#auditForm").submit();
	});	
});

$(function(){
	// 全选按钮显示划扣金额，划扣笔数
	$("#checkAll").click(function(){
		$("input[name='checkBox']").attr("checked",this.checked);
		var totalNum = 0;
		var totalMoney = 0;
		if(this.checked){
			$("input[name='checkBox']").each(function(){
				totalNum = totalNum + 1;
				totalMoney = totalMoney + parseFloat($(this).next().val()?$(this).next().val():0); 
			});
			$("#totalNum").text(totalNum);
			$("#totalMoney").text(totalMoney.toFixed(2));
		}else{
			$("#totalNum").text($("#numbk").val());
			$("#totalMoney").text(fmoney($("#totalbk").val(),2));
		}
	});
	
	// 为每一个复选框绑定事件
	$("input[name='checkBox']").click(function(){
		var totalNum = 0;
		var totalMoney = 0;
		$("[name='checkBox']").each(function(){
			if (this.checked) {
				totalMoney = totalMoney + parseFloat($(this).next().val());
				totalNum = totalNum + 1;
			}
			$("#totalMoney").text(totalMoney.toFixed(2));
			$("#totalNum").text(totalNum);
		});
		var checkBox = $("input:checkbox[name='checkBox']").length;
		var checkBoxLength = $("input:checkbox[name='checkBox']:checked").length;
		if(checkBox == checkBoxLength){
			$("#checkAll").attr("checked",true);
		}else{
			$("#checkAll").attr("checked",false);
		}
		if (checkBoxLength == 0 || checkBoxLength == '0' ) {
			$("#totalNum").text($("#numbk").val());
			$("#totalMoney").text(fmoney($("#totalbk").val(),2));
		}
	});
})
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
	$("#auditForm").attr("action",  ctx + "/borrow/payback/overdueManager/overdueManagerList");
	$("#auditForm").submit();
	return false;
}
//格式化，保留两个小数点
function fmoney(s, n) {  
    n = n > 0 && n <= 20 ? n : 2;  
    s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";  
    var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];  
    t = "";  
    for (i = 0; i < l.length; i++) {  
        t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");  
    }  
    return t.split("").reverse().join("") + "." + r;  
}
;(function($){
	  $.isBlank = function(obj){
	    return(!obj || $.trim(obj) === "") ? 0 : obj;
	  };
})(jQuery);	