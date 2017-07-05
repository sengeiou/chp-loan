$(document).ready(function(){

	// 初始化门店下拉框
	loan.getstorelsit("txtStore","hdStore");
	
	/**
	 * @function 初始化tab
	 */
	$("#confirmTab").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
	$('#confirmTab').bootstrapTable({
		method: 'get',
		cache: false,
    	height:$(window).height() - 200,
		striped: true,
		pageSize: 20,
		pageNumber:1
	});
	
	// 搜索条件显示隐藏事件
	$("#showMore").click(function(){
		if(document.getElementById("T1").style.display=='none'){
			document.getElementById("showMore").src='../../../../static/images/down.png';
			document.getElementById("T1").style.display='';
			//document.getElementById("T2").style.display='';
		}else{
			document.getElementById("showMore").src='../../../../static/images/up.png';
			document.getElementById("T1").style.display='none';
			//document.getElementById("T2").style.display='none';
		}
	});
	
	/**
	 * 页面办理按钮事件
	 */
	
	$("input[name='confirmInfoBtn']").each(function(){
		$(this).bind('click',function(){
			$("#isTopCashBackNot").attr('checked',true);
			$('#isSettleMoney').hide();
			$('#confirmContractCode').val($(this).attr('confirmContractCode'));
			var url=ctx+"/borrow/payback/confirmPayback/form?contarctCode="+$('#confirmContractCode').val();
		    art.dialog.open(url, {  
		         id: 'his',
		         title: '结清确认详情页面',
		         lock:true,
		         width:1200,
		     	 height:300
		     },  
		     false); 
       });
	});
	
	// 清除按钮
	$("#clearBtn").click(function(){
      $(":input").not(':button,:submit,:reset,:hidden').val('');
      $("#hdStore").val('');
      $("#confirmPaybackForm").submit();
	});
	
	//导出Excel数据表
	$("#exportExcel").click(function() {
		var idVal = "";
		$("input:checkbox[name='checkBox']:checked").each(function(){
			idVal+=","+$(this).val();
		});
		if(idVal.length!=0) {
			idVal = idVal.substr(1, idVal.length);
		}
		$("#ids").val(idVal);
		$("#confirmPaybackForm").attr("action",ctx+"/borrow/payback/confirmPayback/excelList");
		$("#confirmPaybackForm").submit();
		$("#confirmPaybackForm").attr("action",  ctx + "/borrow/payback/confirmPayback/list?dictPayStatus=5");
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
	$("#confirmPaybackForm").attr("action",  ctx + "/borrow/payback/confirmPayback/list?dictPayStatus=5");
	$("#confirmPaybackForm").submit();
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