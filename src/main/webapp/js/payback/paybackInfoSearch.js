$(document).ready(function(){
	/**
	 * @function 初始化tab
	 */
	$("#applypaybackUseTab").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
	$('#applypaybackUseTab').bootstrapTable({
		method: 'get',
		cache: false,
		height: 350,
		striped: true,
		pageSize: 20,
		pageNumber:1
	});
	
	/**
	 * 点击紧急诉讼按钮事件,进行赋值
	 */
	$(":input[name='emergencyBtn']").each(function(){
		$(this).bind('click',function(){
			$("#paybackLoanCode").val($(this).attr("loanCode"));
			$("#applyPayBackContractCode").val($(this).attr("payBackUseContractCode"));
			// 让选中的单选框置于没有选中的状态
			$("input:radio[name='loanInfo.emergencyStatus']:checked").attr('checked',false);
			$("#emergencyTd").css('display','none');
		});
	});
	
	/**
	 * @function 紧急诉讼变化事件
	 */
	$("#emergencySure").click(function(){
		var checkType = $("input:radio[name='loanInfo.emergencyStatus']:checked").val();
		var checkVal = $("#emergencyRemark").val();  
		if(checkType == null){
			top.$.jBox.tip("请选择紧急诉讼状态！");
			return false;
		}else if(checkVal=="" && checkType=='1'|| checkVal== "" && checkType =='3'){
			top.$.jBox.tip("备注框不能为空！");
			return false;
		}else{
			var payback = $("#appplyPayBackUseForm").serialize()+"&emergencyRemark="+checkVal;
			$.ajax({
				type: 'post',
				url: ctx +'/borrow/payback/paybackInfoSearch/updateEmergency',
				data: payback,
				success:function(data){
					top.$.jBox.tip(data);
					window.location.href = ctx +'/borrow/payback/paybackInfoSearch/list';
				},
				error:function(data){
					top.$.jBox.tip(data);
				}
			});
		}
	});
	
	/**
	 * @function 控制紧急诉讼备注框的显示
	 */
	$("input:radio[name='loanInfo.emergencyStatus']").each(function(){
		$(this).bind('click',function(){
			if($(this).attr("checked")){
				$("#emergencyTd").css('display','');
			}else{
				$("#emergencyTd").css('display','none');
			}
		});
	});
	
	/**
	 * @function 黑灰名单按钮标记点击事件
	 */
	$(":input[name='blackBtn']").each(function(){
		$(this).bind('click',function(){
			$("#blackLoanCode").val($(this).attr("loanCode"));
		});
	});
	
	/**
	 * @function 黑灰名单标记按钮点击事件
	 */
	$("#blackSure").click(function(){
		var blackLabel = $("input[name='loanInfo.blackType']:checked").attr("label");
		var blackVal = $("input[name='loanInfo.blackType']:checked").val();
		if(blackVal != ''){
			art.dialog.confirm('是否标记为'+blackLabel+'?',function(){
				var payback = $("#blackTypeForm").serialize();
				$.ajax({
					url:  ctx + '/borrow/payback/paybackInfoSearch/updateBlackType',
					type:'post',
					data: payback,
					success:function(data){
						top.$.jBox.tip(data);
						window.location.href = ctx +'/borrow/payback/paybackInfoSearch/list';
					},
					error:function(data){
						top.$.jBox.tip(data);
					}
				});
			},
			function(){
				art.dialog.tips('执行取消操作');
			});
		}else{
			top.$.jBox.tip('请选择操作');
		}
	});
	
	/**
	 * @function 查看还款信息维护历史
	 */
	
	$("input[name='paybackChangeHis']").each(function(){
		$(this).bind('click',function(){
			var contractCode = $(this).attr("contractCode");
			var url = ctx + '/borrow/payback/paybackInfoSearch/getPayBackMainHis?contractCode='+contractCode;
			art.dialog.open(url,{
				id:'his',
				title:'还款维护历史',
				lock:true,
			    width:850,
			    height:450
			},false);
		});
	});
	
	/**
	 * @function 加载弹出框
	 */
	var msg = $("#msg").val();
	if (msg != "" && msg != null) {
		top.$.jBox.tip(msg);
	}
	
	/**
	 * @function 搜索条件显示隐藏事件
	 */
	$('#showSearchApplyUseBtn').click(function() {
		if($("#searchApplyUse").css('display')=='none'){
			$("#showSearchApplyUseBtn").attr('src','../../../../static/images/down.png');
			$("#searchApplyUse").show();
		}else{
			$("#showSearchApplyUseBtn").attr("src",'../../../../static/images/up.png');
			$("#searchApplyUse").hide();
		}
	});
	/**
	 * @function 日期
	 */
	$('input[name="contractEndDay"]').daterangepicker({ 
		 startDate: moment().subtract('days')
	})
	
	/**
	 * @function 清除搜索表单数据
	 */
	$('#applyPayBackUseClearBtn').click(function() {
		$(':input','#applyPayBackUseForm') 
		.not(':submit, :reset, :hidden') 
		.val('') 
		.removeAttr('selected');
		$("#applyPayBackUseForm").submit();
	});
	
});

//日期比较
function comptime(beginTime,endTime) {
    
    var beginTimes = new Date(beginTime);
    var endTimes =  new Date(endTime);
    var a =  (new Date()- beginTimes) / 3600 / 1000;
	var b =  (new Date()- endTimes) / 3600 / 1000;
    
	if(a>0 && b <0){
	   return true;
	}else {
	   return false
	}
}



function offsetMsg(){
	var today = new Date();
    var i1 = today.getHours()*60*60+ today.getMinutes()*60;

	var startTime1 = new Date("January 12,2006 11:50:00");
	var i2 = startTime1.getHours()*60*60+ startTime1.getMinutes()*60;
	var endTime1 = new Date("January 12,2006 13:00:00");
	var i3 = endTime1.getHours()*60*60+ endTime1.getMinutes()*60;
	
	var startTime2 = new Date("January 12,2006 17:50:00");
	var i4 = startTime2.getHours()*60*60+ startTime2.getMinutes()*60;
	var endTime2 = new Date("January 12,2006 19:00:00");
	var i5 = endTime2.getHours()*60*60+ endTime2.getMinutes()*60;
	
	if (i2 <= i1 && i1 <= i3) {
		return true;
	}
	if (i4 <= i1 && i1 <= i5) {
		return true;
	}
	return false;
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
	$("#applyPayBackUseForm").attr("action",  ctx + "/borrow/payback/paybackInfoSearch/list?dictPaybackStatus=2");
	$("#applyPayBackUseForm").submit();
	return false;
}
