$(document).ready(function(){

	// 初始化门店下拉框
	loan.getstorelsit("txtStore","hdStore");
	
	/**
	 * @function 初始化tab
	 */
	$("#paybackDoneTab").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
	$('#paybackDoneTab').bootstrapTable({
		method: 'get',
		cache: false,
		height: $(window).height() - 230,
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
		}else{
			$("#showSearchConfirmPaybackBtn").attr("src",'../../../../static/images/up.png');
			$("#searchConfirmPayback").hide();
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
	$("input[name='confirmInfoBtn']").each(function(){
		$(this).bind('click',function(){
			$('#confirmContractCode').val($(this).attr('confirmContractCode'));
			var url=ctx+"/borrow/payback/paybackDone/confirmed?contarctCode="+$('#confirmContractCode').val();
		    art.dialog.open(url, {  
		         id: 'his',
		         title: '结清已办',
		         lock:true,
		         width:1000,
		     	 height:300
		     },  
		     false); 
       });
	});
	
	   // 清除按钮
	$('#clearDeductBtn').click(function(){
		$(':input','#paybackDoneForm')
		  .not(':button, :reset')
		  .val('')
		  .removeAttr('checked')
		  .removeAttr('selected');
		$('select').val();
		$("select").trigger("change");
		$("#paybackDoneForm").submit();
		
	});
	
	//导出Excel数据表
	$("#exportExcel").click(function(){
		var idVal = "";
		$("input:checkbox[name='checkBox']:checked").each(function(){
			idVal+=","+$(this).val();
		});
		if(idVal.length!=0) {
			idVal = idVal.substr(1, idVal.length);
		}
		$("#ids").val(idVal);
		$("#paybackDoneForm").attr("action",ctx+"/borrow/payback/paybackDone/excelList");
		$("#paybackDoneForm").submit();
		$("#paybackDoneForm").attr("action",  ctx + "/borrow/payback/paybackDone/list");
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
	$("#paybackDoneForm").attr("action",  ctx + "/borrow/payback/paybackDone/list");
	$("#paybackDoneForm").submit();
	return false;
}

/**
 * @function 全选按钮事件
 */
function selectAll(obj){
	$("input[name='checkBox']").each(function(){
		this.checked = !this.checked;
	});
}