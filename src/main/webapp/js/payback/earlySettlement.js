function show(){
	if(document.getElementById("T1").style.display=='none'){
		document.getElementById("showMore").src='../../../../static/images/down.png';
		document.getElementById("T1").style.display='';
		document.getElementById("T2").style.display='';
	}else{
		document.getElementById("showMore").src='../../../../static/images/up.png';
		document.getElementById("T1").style.display='none';
		document.getElementById("T2").style.display='none';
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
	$("#auditForm").attr("action",  ctx + "/borrow/payback/earlySement/earlySettlementHaveTodoList");
	$("#auditForm").submit();
	return false;
}



// 还款明细
function getqueryManualOperation(contractCode,monthId) {
	var url=ctx + "/borrow/payback/manualOperation/queryManualOperation?contractCode="+contractCode+"&id="+monthId;
	art.dialog.open(url, {  
         id: 'his',
         title: '还款明细',
         lock:true,
         width:1200,
     	 height:500
     },  
     false);
}

$(document).ready(function() {

	// 初始化门店下拉框
	loan.getstorelsit("txtStore","hdStore");
	
	// 查看页面弹出框
	$(":input[name='seeEarlySettlement']").each(function(){
		$(this).click(function(){
			var id=$(this).next().val();
			var rPaybackId=$(this).next().next().val();
			var url = ctx + '/borrow/payback/earlySement/seeEarlySettlementHaveTodo?id='+ id + "&rPaybackId=" + rPaybackId ;
			art.dialog.open(url, {  
		         id: 'his',
		         title: '提前结清已办查看页面',
		         lock:true,
		         width:1000,
		     	 height:400
		     },  
		     false); 
		});
	});
	
	// 清除按钮
	// 清除按钮
	$("#clearBtn").click(function(){	
		$(':input','#auditForm')
		  .not(':button, :reset')
		  .val('')
		  .removeAttr('checked')
		  .removeAttr('selected');
		$('select').val();
		$("select").trigger("change");
		$("#auditForm").attr("action",  ctx + "/borrow/payback/earlySement/earlySettlementHaveTodoList");
		$("#auditForm").submit();
	});
	
	// 清除按钮
	$("#exportExcel").click(function(){	
		var idVal = '';
		$("input:checkbox[name='checkBox']:checked").each(function(){
		 idVal+=","+$(this).val();
		});
		$("#idVals").val(idVal);
		$("#auditForm").attr("action",ctx+"/borrow/payback/earlySement/excelList");
		$("#auditForm").submit();
	});
	
	// 清除按钮
	$("#btn-submit").click(function(){	
		$("#idVals").val('');
		$("#auditForm").attr("action",ctx+"/borrow/payback/earlySement/earlySettlementHaveTodoList");
		$("#auditForm").submit();
	});
	
});

/**
 * @function 全选按钮事件
 */
function selectAll(obj){
	$("input[name='checkBox']").each(function(){
		this.checked = !this.checked;
	});
}