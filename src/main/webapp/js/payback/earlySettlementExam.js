
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
	$("#auditForm").attr("action",  ctx + "/borrow/payback/earlySettlement/list");
	$("#auditForm").submit();
	return false;
}

//查询条件中图标的收缩和显示
function show() {
		if (document.getElementById("T1").style.display == 'none') {
			$("#showMore").attr('src','../../../../static/images/down.png');
			$("#T1").removeAttr("style");
			$("#T2").removeAttr("style");
			$("#T3").removeAttr("style");
		} else {
			$("#showMore").attr("src",'../../../../static/images/up.png');
			$("#T1").attr("style", "display:none;");
			$("#T2").attr("style", "display:none;");
			$("#T3").attr("style", "display:none;");
		}
	}

//显示历史弹框事件
/*function showHirstory(applyId){
	    var wid = screen.width/2 + 150;
		var hei = screen.height/2 + 100;
		var popwin = window.open(ctx+"/borrow/payback/earlySettlement/getHirstory?applyId="+applyId,"window","width="+wid+",height="+hei+",status=no,scrollbars=yes");		
		popwin.moveTo(screen.width/4,screen.height/4);
		popwin.focus();
}
*/

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

/**
 * 弹出还款历史界面
 * rPaybackId ：还款主表申请表主键
 * dictLoanStatus：还款流水记录的操作步骤
 */
function showLoanHis(applyId){
	        
 			var url=ctx+"/borrow/payback/earlySettlement/getHirstory?applyId="+applyId;
 			art.dialog.open(url, {  
 		         id: 'his',
 		         title: '历史记录',
 		         lock:true,
 		         width:900,
 		     	 height:400
 		     },  
 		     false);
}

$(document).ready(function(){
	
	// 初始化门店下拉框
	loan.getstorelsit("txtStore","hdStore");

	// 为办理按钮绑定事件
	$("#examHandlerBtn").each(function(){
		$(this).bind('click',function(){
			var applyId=$(this).next().val();
			$.ajax({  
				   type : "POST",
				   data:{
					   "id":applyId
				   },
					url : ctx+"/borrow/payback/earlySettlement/getEarlyBackApply",
					datatype : "json",
					success : function(data){
					},
					error: function(){
							alert("服务器没有返回数据，可能服务器忙，请重试");
					}
				});
		})
	});
	
	$("#backConfirmBtn").click(function(){
		var id=$("#id").val();
		var pId=$("#pId").val();
		var returnReason=$("#returnReason").val();
		if(returnReason == null || returnReason == ""){
		    artDialog.alert('请输入审核意见!');
			return;
		}
		$.ajax({  
			   type : "POST",
			   data:{
				   "id":id,
				   "returnReason":returnReason,
				   "pId":pId,
				   "flag" : '0'
			   },
				url : ctx+"/borrow/payback/earlySettlement/updatePaybackChargeStatus",
				datatype : "json",
				success : function(data){
					window.location.href=ctx+"/borrow/payback/earlySettlement/list";
				},
				error: function(){
						alert("服务器没有返回数据，可能服务器忙，请重试");
				}
			});
	});
	

	// 为审核页面的确定按钮绑定事件
	$("#submitBtn").click(function(){
		var chargeId=$("input[name=chargeId]").val();
		var returnReason=$("#returnReason").val();
		var id=$("input[name=id]").val();
		var flag1=$("input[name='one']:checked").val();
		var flag = '0';
		if(returnReason == null || returnReason == ""){
		    artDialog.alert('请输入审核意见!');
			return;
		}
		if(flag1 == null || flag1 == ""){
			artDialog.alert("请选择是否审核通过！");
			return;
		}
		 // 审核结果为通过
		if(flag1==0){
			$.ajax({  
				   type : "POST",
				   data:{
					   "chargeId":chargeId,
					   "returnReason":returnReason,
					    "id":id
				   },
					url : ctx+"/borrow/payback/earlySettlement/updatePayBackStatus",
					datatype : "text",
					success : function(data){
						window.location.href=ctx+"/borrow/payback/earlySettlement/list";
					},
					error: function(){
							alert("服务器没有返回数据，可能服务器忙，请重试");
						}
				});
		}else{
			$.ajax({  
				   type : "POST",
				   data:{
					   "id":chargeId,
					   "returnReason":returnReason,
					   "pId":id,
					   "flag":"0"
				   },
					url : ctx+"/borrow/payback/earlySettlement/updatePaybackChargeStatus",
					datatype : "json",
					success : function(data){
						if(data=="true"){
							window.location.href=ctx+"/borrow/payback/earlySettlement/list";
						}else{
							artDialog.alert("退回失败！");
						}
						
					},
					error: function(){
							alert("服务器没有返回数据，可能服务器忙，请重试");
						}
				});
		 }
	});
	
	// 为下载提前结清申请资料按钮绑定事件
	$("#downZip").click(function(){
		window.location.href = ctx
				+ "/borrow/payback/earlySettlement/downZip?docId="
				+ $(this).attr('docId')
				+ "&fileName="
				+ $(this).attr('fileName')
				+ "&id=" + $("#detailId").val();
	});
	
	// 清除按钮
	$("#clearBtn").click(function(){	
		$(':input','#auditForm')
		  .not(':button, :reset,:hidden')
		  .val('')
		  .removeAttr('checked')
		  .removeAttr('selected');
		$('select').val();
		$("select").trigger("change");
		$("#auditForm").submit();
	});
	
});


	

	
	
	
	
	
