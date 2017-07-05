$(document).ready(function(){
	
	// 初始化门店下拉框
	loan.getstorelsit("txtStore","hdStore");

	/**
	 * @function 初始化tab
	 */
	$('#earlyFinishTab').bootstrapTable('destroy');
	$('#earlyFinishTab').bootstrapTable({
		method: 'get',
		cache: false,
    	height:$(window).height() - 200,
		striped: true,
		pageSize: 20,
		pageNumber:1
	});
	/**
	 * @function 搜索条件显示隐藏事件
	 */
	$('#showSearchConfirmPaybackBtn').click(function() {
		if($("#searchConfirmPayback").css('display')=='none'){
			$("#showSearchConfirmPaybackBtn").attr('src','../../../../static/images/down.png');
			$("#searchConfirmPayback").show();
			$("#searchHidden").show();
		}else{
			$("#showSearchConfirmPaybackBtn").attr("src",'../../../../static/images/up.png');
			$("#searchConfirmPayback").hide();
			$("#searchHidden").hide();
		}
	});
	/**
	 * @function 是否结清点击事件
	 */
	$('input:radio[name="isContfirm"]').click(function(){
		  var val=$('input:radio[name="isContfirm"]:checked').val();
            if(val=='0'){
			$('#isTopCashBack').show();
			$('#isSettleMoney').hide();
			 $("#isTopCashBackNot").attr('checked','checked');
	          }
            else{
				$('#isSettleMoney').hide();
            }
	})
	
	/**
	 * @function 是否还款点击事件
	 */
	$('input:radio[name="isTopCashBack"]').click(function(){
		  var val=$('input:radio[name="isTopCashBack"]:checked').val();
            if(val=='0'){
	                $('#isSettleMoney').show();
	          }
            else{
                $('#isSettleMoney').hide();
            }
	})
	
	/**
	 * 页面办理按钮事件
	 */
	$("input[name='earlyFinishBtn']").each(function(){
		$(this).bind('click',function(){
			var url=ctx+"/borrow/payback/earlyFinishConfirm/getPaybackInfo?id="+$(this).attr('confirmContractCode')+"&contractCode=" + $(this).attr('pmContractCode');
		    art.dialog.open(url, {  
		         id: 'his',
		         title: '提前结清确认详情页面',
		         lock:true,
		         width:1200,
		     	 height:400
		     },  
		     false); 
       });
	});
	
	// 绑定查看还款明细弹框
	$("input[name='seeDate']").each(function() {
		$(this).bind('click',function(){
			//var contractCode=$(this).next().next().val();
			//var id = $(this).next().val();
			var url=ctx + "/borrow/payback/manualOperation/queryManualOperation?contractCode="+$(this).attr("pmContractCode");
			art.dialog.open(url, {  
			         id: 'his',
			         title: '还款明细',
			         lock:true,
			         width:1200,
			     	 height:500
			     },  
			     false);
		});
	});
	
	//还款日验证
	$("#paybackDay").blur(function(){
		var da = $("#paybackDay").val();
		if (da != null && "" != da) {
			var dar = eval(da);
			if (dar>31 || dar<1 ) {
				artDialog.alert('请输入1~31之间的数字!');
				$("#paybackDay").focus();
				return;
			}
		}
	});
	
	// 清除按钮
	$("#clearBtn").click(function(){	
		$(':input','#confirmPaybackForm')
		  .not(':button, :reset')
		  .val('')
		  .removeAttr('checked')
		  .removeAttr('selected');
		$('select').val();
		$("select").trigger("change");
		$("#confirmPaybackForm").attr("action",ctx+"/borrow/payback/earlyFinishConfirm/list");
		$("#confirmPaybackForm").submit();
	});
	
	
	// 清除按钮
	$("#exportExcel").click(function(){	
		var idVal = '';
		$("input:checkbox[name='checkBox']:checked").each(function(){
		 idVal+=","+$(this).val();
		});
		//alert(idVal);
		$("#idVals").val(idVal);
		$("#confirmPaybackForm").attr("action",ctx+"/borrow/payback/earlyFinishConfirm/excelList");
		$("#confirmPaybackForm").submit();
	});
	
	// 清除按钮
	$("#btn-submit").click(function(){	
		$("#idVals").val('');
		$("#confirmPaybackForm").attr("action",ctx+"/borrow/payback/earlyFinishConfirm/list");
		$("#confirmPaybackForm").submit();
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
	$("#confirmPaybackForm").attr("action",  ctx + "/borrow/payback/earlyFinishConfirm/list");
	$("#confirmPaybackForm").submit();
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