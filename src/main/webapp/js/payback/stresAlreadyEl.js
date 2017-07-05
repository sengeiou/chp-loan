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
		$("#auditForm").attr("action",  ctx + "/borrow/payback/loanServices/allStoresElertDoList");
		$("#auditForm").submit();
		return false;
	}

$(document).ready(function(){
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
		
		// 清除按钮
		$("#clearBtn").click(function(){	
			// 清除text	
			$(":text").val('');
			// 清除number
			$("#repaymentDate").val('');
			// 清除checkbox	
			$("#auditForm:checkbox").attr("checked", false);
			// 清除radio			
			$(":radio").attr("checked", false);
			// 清除select			
			$("select").val("");
			$("select").trigger("change");	
		});
		
		// 查看页面弹出框
		$(":input[name='seeStresAlready']").each(function(){
			$(this).click(function(){
				var ids=$(this).next().val()
				var dictPayUse=$(this).next().next().val()
				var url = ctx + '/borrow/payback/loanServices/seeStoresElectDo?ids='+ ids + "&dictPayUse=" + dictPayUse ;
				art.dialog.open(url, {  
			         id: 'his',
			         title: '电催_电催已办查看页面',
			         lock:true,
			         width:1000,
			     	 height:500
			     },  
			     false); 
			});
		});

		/**
		 * @function 搜索条件显示隐藏事件
		 */
		$('#showMore').click(function() {
			if($("#T1").css('display')=='none'){
				$("#showMore").attr('src','../../../../static/images/down.png');
				$("#T1").show();
			}else{
				$("#showMore").attr("src",'../../../../static/images/up.png');
				$("#T1").hide();
			}
		});
});