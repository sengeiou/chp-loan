//多选 全选
$(document).ready(function() {
	
	
	
	// 还款明细
	/**
	 * @function 绑定还款明细页面弹出框事件
	 */
	function showMx(contractCode) {
		var contractCode = $("#contractCode").val();
		
		var url=ctx + "/borrow/payback/manualOperation/queryManualOperation?contractCode="+contractCode;
		art.dialog.open(url, {  
	         id: 'his',
	         title: '还款明细',
	         lock:true,
	         width:1200,
	     	 height:500
	     },  
	     false);
	}
	
	
		// 初始化门店下拉框
		loan.getstorelsit("txtStore","hdStore");
		
		// 导出Excel数据表
		$("button[name='exportExcel']").click(function() {
			var idVal = "";
			var checkList=$("input:checkbox[name='checkBox']:checked").val();
			$("input:checkbox[name='checkBox']:checked").each(function(){
				 if(checkList != null){
					 var value=$(this).val();
					 idVal +=";"+ value;
				 }
				});
			 window.location.href=ctx+"/borrow/payback/paybackAudit/exportExcel?"+$("#auditForm").serialize()+"&idVal="+idVal;
		});
		
		// 搜索提交
		$('#paybackAuditSubmit').bind('click', function() {
			var beginOutReallyAmount = $("[name='beginOutReallyAmount']").val();
			var endOutReallyAmount = $("[name='endOutReallyAmount']").val();
			if(beginOutReallyAmount != ''){
				if(!(/^[0-9]+([.]\d{1,2})?$/).test(beginOutReallyAmount)){
		    		artDialog.alert("金额只能输入数字并且保留二位小数！"); 
		    	 	return false;
				}
			}

			if(endOutReallyAmount != ''){
				if(!(/^[0-9]+([.]\d{1,2})?$/).test(endOutReallyAmount)){
		    		artDialog.alert("金额只能输入数字并且保留二位小数！"); 
		    	 	return false;
				}
			}
			$('#auditForm').submit();
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
		
		// 清除按钮
		$("#clearBtn").click(function(){	
		/*	// 清除text	
			$(":text").val('');
			// 清除number
			$("#repaymentDate").val('');
			$("#alsoAmountOne").val('');
			$("#alsoAmountTwo").val('');
			// 清除checkbox	
			$("#auditForm:checkbox").attr("checked", false);
			// 清除radio			
			$("#auditForm:radio").attr("checked", false);
			// 清除select			
			$("select").val("");
			$("select").trigger("change");	*/
			
			$(':input','#auditForm')
			  .not(':button, :reset,:hidden')
			  .val('')
			  .removeAttr('checked')
			  .removeAttr('selected');
			$('select').val();
			$("select").trigger("change");
			$('#auditForm').submit();
		});
		
		/**
		 * @function 搜索条件显示隐藏事件
		 */
		$('#showMore').click(function() {
			if($("#T1").css('display')=='none'){
				$("#showMore").attr('src','../../../../static/images/down.png');
				$("#T1").show();
				$("#T2").show();
			}else{
				$("#showMore").attr("src",'../../../../static/images/up.png');
				$("#T1").hide();
				$("#T2").hide();
			}
		});
		
		// 查看页面弹出框
		$(":input[name='seePaybackAudit']").each(function(){
			$(this).click(function(){
				var payBackId=$(this).next().val()
				var url = ctx + '/borrow/payback/paybackAudit/seePaybackAuditHavaTodo?payBackId=' + payBackId ;
				art.dialog.open(url, {  
			         id: 'his',
			         title: '还款查账已办查看页面',
			         lock:true,
			         width:1000,
			     	 height:500
			     },  
			     false); 
			});
		});
		
		 //全选和反选
		$("#checkAll").click(function() {
			$(".checkAll1").attr("checked", this.checked);
			var totalMoney = 0;
			var totalNum = 0;
			if (this.checked) {
				$("[name='checkBox']").each(function() {
					totalNum = totalNum + 1;
					totalMoney = totalMoney + parseFloat($(this).attr("applyAmount")?$(this).attr("applyAmount"):0);
				});
				$("#total_money").text(totalMoney.toFixed(2));
				$("#total_num").text(totalNum);
			} else {
				$("#total_money").text("0.00");
				$("#total_num").text(0);
			}
		}); 
		
		//为每一条记录的复选框绑定事件
		$("[name='checkBox']").click(function() {
			var totalMoney = 0.0;
			var totalNum = 0;
			$("[name='checkBox']").each(function() {
				if (this.checked) {
					totalMoney = totalMoney + parseFloat($(this).attr("applyAmount")?$(this).attr("applyAmount"):0);
					totalNum = totalNum + 1;
				}
				$("#total_money").text(totalMoney.toFixed(2));
				$("#total_num").text(totalNum);
			});
			var checkBox = $("input:checkbox[name='checkBox']").length;
			var checkBoxchecked = $("input:checkbox[name='checkBox']:checked").length
			/*if(checkBoxchecked==0){
				$("#checkAll").attr("checked", false);
			}*/
			if(checkBox==checkBoxchecked){
				$("#checkAll").attr("checked", true);
			}else{
				$("#checkAll").attr("checked", false);
			}
		});
});
/**
 * 页面搜索框中的实际到账金额
 * @param val
 */
function checkVaiolate(val){
	if(!(/^[0-9]+([.]\d{1,2})?$/).test(val)){
		artDialog.alert("金额只能输入数字且保留两位小数");
	}
}
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
	$("#auditForm").submit();
	return false;
}