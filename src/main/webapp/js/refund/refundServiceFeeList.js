$(document).ready(function(){
	
	// 点击清除，清除搜索页面中的数据
	$("#clearBtn").click(function(){		
		/*$(':input','#deductsForm')
		  .not(':button, :reset,:hidden')
		  .val('')
		  .removeAttr('checked')
		  .removeAttr('selected'); */
		$('#customerName').val("");
		$('#contractCode').val("");
		$('#customerCertNum').val("");
		$('#appStatus').val("");
		$("#appStatus").trigger("change");
	});
	
	// 伸缩
	$("#showMore").click(function(){
		if(document.getElementById("T1").style.display=='none'){
			document.getElementById("showMore").src='../../../../static/images/down.png';
			document.getElementById("T1").style.display='';
			document.getElementById("T2").style.display='';
		}else{
			document.getElementById("showMore").src='../../../../static/images/up.png';
			document.getElementById("T1").style.display='none';
			document.getElementById("T2").style.display='none';
		}
	});
	
	// 点击全选
	$("#checkAll").click(function(){
		if($('#checkAll').attr('checked')=='checked'|| $('#checkAll').attr('checked'))
		{
			$(":input[name='checkBoxItem']").each(function() {
				$(this).attr("checked",'true');
			});
		}else{
			$(":input[name='checkBoxItem']").each(function() {
				$(this).removeAttr("checked");
			});
		}
	});
	
	// 放款划扣退回导出excel，根据催收退回表中的关联id进行导出
	$("#daoBtn").click(function(){
		 var urgeId = "";
		 var backForm = $("#urgeForm").serialize(); 
			if($(":input[name='checkBoxItem']:checked").length>0){
				$(":input[name='checkBoxItem']:checked").each(function(){
	        		if(urgeId!="")
	        		{
	        			urgeId+=","+$(this).attr("urgeId");
	        		}else{
	        			urgeId=$(this).attr("urgeId");
	        		}
	        	});
			}
			window.location.href=ctx+"/borrow/refund/longRefund/importBack?urgeId="+urgeId+"&"+backForm;
	});


	
	
});	

// 样式控制
function jump(url,title){
	art.dialog.open(url, {  
		   title: title,
		   lock:true,
		   width:800,
		   height:450
		},false);
}

function doOpenRefund(paybackId,refundId,urgeId,chargeId) {
	var url = ctx + '/borrow/refund/longRefund/openRefundServiceFee?paybackId='+paybackId+'&refundId='+refundId+'&urgeId='+urgeId+'&chargeId='+chargeId;
		
	art.dialog.open(url, {
		id:"openRefundServiceFee",
		title : '催收服务费退款',
		width : 800,
		lock:true,
		height : 500,
		close:function()
		{
			$("form:first").submit();
		}
	});
}