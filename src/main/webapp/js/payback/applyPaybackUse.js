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
	
	/**
	 * @function 页面办理按钮事件
	 */
	$(":input[name='applyPayBackUse']").each(function(){
		$(this).bind('click',function(){
			// 冲抵提示信息
			if(offsetMsg()){
				$('#dictPayUseMsg').show();
			}else{
				$('#dictPayUseMsg').hide();
			}
			$("#applyPayBackContractCode").val($(this).attr('payBackUseContractCode'));
			$("#id").val($(this).attr('applyPaybackId'));
			// 还款状态为 ‘逾期’,'结清','提前结清','提前结清待审核','提前结清待确认','结清待确认','还款退回'的不允许提交提前结清申请
			if($(this).attr('dictPayStatus')=='1' || $(this).attr('dictPayStatus')== '2' 
				|| $(this).attr('dictPayStatus')=='3' || $(this).attr('dictPayStatus')== '4'
					|| $(this).attr('dictPayStatus')=='5' || $(this).attr('dictPayStatus')== '6' || $(this).attr('dictPayStatus')== '11'){
				$('#monthBeforefinish').hide();
			}else{
				if($(this).attr('paybackDay') == new Date().getDate()){
					$('#monthBeforefinish').hide();
				}else{
					if($(this).attr('isuse')=='1')
					{
						$('#monthBeforefinish').show();
						if($(this).attr('paybackDay') == '3'){
							var flag = comptime('2016/09/30 16:00:00','2016/10/04 00:00:00')
							if(flag){
								$('#monthBeforefinish').hide();
							}else{
								$('#monthBeforefinish').show();
							}
						}
						if($(this).attr('paybackDay') == '7'){
							var flag7 = comptime('2016/09/30 16:00:00','2016/10/08 00:00:00')
							if(flag7){
								$('#monthBeforefinish').hide();
							}else{
								$('#monthBeforefinish').show();
							}
						}
					}
					else
					{
						$('#monthBeforefinish').hide();
					}
				}
				
			}
			//如果当前日期是2017-1-25 四点以后并且还款日是30日或者3日则不允许进行提前结清
			var nowDate=new Date();
			var lineDate=new Date("2017-01-25 16:00:00"); 
			var firstDate = new Date("2017-01-31 00:00:00");
			var secondDate = new Date("2017-02-04 00:00:00");
			var d1=(nowDate- lineDate) / 3600 / 1000;
			var d2=(nowDate- firstDate) / 3600 / 1000;
			var d3=(nowDate- secondDate) / 3600 / 1000
			if(d1 >0){ 
				//如果还款日是30号，则在31日之前不允许进行提前结清
				if($(this).attr('paybackDay') == '30' && d2<0){
					$('#monthBeforefinish').hide();
				}
				//如果还款日是3号，则在4日之前不允许进行提前结清
				if($(this).attr('paybackDay') == '3' && d3<0){
					$('#monthBeforefinish').hide();
				}
			}
			
		});
	});
	
	
	
	/**
	 * @function 页面弹出框事件
	 */
	$('#applyPayBackUsebtn').click(function() {
		// 冲抵提示信息
		if(offsetMsg()){
			top.$.jBox.tip("系统冲抵中请勿操作！");
			return false;
		}
		$("#appplyPayBackUseForm").submit();
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
	$("#applyPayBackUseForm").attr("action",  ctx + "/borrow/payback/applyPaybackUse/list?dictPaybackStatus=2");
	$("#applyPayBackUseForm").submit();
	return false;
}

function doOpenRefund(paybackId) {
	$.ajax({
		url:ctx+'/borrow/payback/zhrefund/blueRefundBefore',
		data:{contractCode:paybackId},
		type : "POST",
		success:function(data){
			if(data=='1'){
				top.$.jBox.tip("此客户为中和东方催收，请勿退款！");
			}else if(data=='0'){
				var url = ctx + '/borrow/refund/longRefund/openRefundBlue?paybackId='+paybackId+'&refundId=';
				art.dialog.open(url, {
					id:"openRefundBlue",
					title : '蓝补退款',
					width : 800,
					lock:true,
					height : 500,
					close:function()
					{
						//document.forms[0].submit();
					}
				});
			}
		},
		error:function(){
			
		}
	});
	
}