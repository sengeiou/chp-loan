$(document).ready(function(){
	
	  $("#tableList").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
		$('#tableList').bootstrapTable({
			method: 'get',
			cache: false,
			height: $(window).height()-230,
			striped: true,
			pageSize: 20,
			pageNumber:1
		});
		$(window).resize(function () {
			$('#tableList').bootstrapTable('resetView');
		});
	
	loan.getstorelsit("mendianName","mendianId");
	$('#showMore').bind('click',function(){
		 show();
	});
	
	$("#daoExcel").click(function() {
		var ids = "";
		var data = $('#tableList').bootstrapTable('getSelections');
		if(data.length > 0){
			for(var i=0;i<data.length;i++){
				if(typeof(ids)!='undefined'){
					ids +="," + data[i].id;
				}else{
					ids = data[i].id;
				}
	    	}
		}
		document.getElementById("ids").value=ids;
		var form = document.getElementById("refundForm");
		form.action = ctx + "/borrow/refund/longRefund/firstExl";
		form.submit();
		form.action = ctx + "/borrow/refund/longRefund/firstList";
		//window.location.href = ctx + "/borrow/refund/longRefund/firstExl?"+$('#refundForm').serialize()+"&ids=" + ids;
	});
});

function clear1(){
	$("#customerName").val('');
	$("#contractCode").val('');
	$("#mendianName").val('');
	$("#mendianId").val('');
	$("#loanStatus").val('');
	$("#loanStatus").trigger("change");
	$("#appStatus").val('');
	$("#appStatus").trigger("change");
	$("#startDate").val('');
 	$("#endDate").val('');
 	$("#appType").val('');
 	$("#appType").trigger("change");
 	$("#fkReason").val('');
 	$("#fkReason").trigger("change");
 	$("#loanFlag").val('');
 	$("#loanFlag").trigger("change");
 	$("#model").val('');
 	$("#model").trigger("change");
}

function openView(id,contractCode,appType){
	var url = ctx + '/borrow/refund/longRefund/view?id='+id+'&contractCode='+contractCode+'&appType='+appType;
	
	art.dialog.open(url, {
		id:"view",
		title : '退款查看',
		width : 800,
		lock:true,
		height : 560,
		close:function()
		{
			
		}
	});
}

function refundExamine(id,contractCode,appType,time,loanStatus){
	if(loanStatus=='88'){
		art.dialog.alert("逾期客户，禁止退款操作！");
		return;
	}
	var url = ctx + '/borrow/refund/longRefund/refundExaminePage?id='+id+'&contractCode='+contractCode+'&appType='+appType+'&mt='+time;
	art.dialog.open(url, {
		id:"view",
		title : '退款审核',
		width : 800,
		lock:true,
		height : 600,
		close:function()
		{
			$("form:first").submit();
		}
	});
}

function operate(type){
	var strIds=new Array();
	var strMts=new Array();
	var loanStatus = "";
	var data = $('#tableList').bootstrapTable('getSelections');
	if(data.length > 0){
		for(var i=0;i<data.length;i++){
			if(typeof(data[i].id)!='undefined' && data[i].refundBank == "待初审"){
				strIds.push(data[i].id.split("_")[0]);
				strMts.push(data[i].id.split("_")[1]);
				//如果是逾期数据进行标识
				if(data[i].realLoanStatus=='88'){
					loanStatus="1";
				}
			}
    	}
	}else{
		art.dialog.alert("请选择操作数据！");
		return;
	}
	//对于批量通过的如果有逾期数据则不允许进行批量操作
	if(type=='1' && loanStatus=="1"){
		art.dialog.alert("逾期客户，禁止退款操作！");
		return;
	}
	if(strIds.length>0){
		var url = ctx + '/borrow/refund/longRefund/operatePage?fkResult='+type+'&id='+strIds.toString()+'&mt='+strMts.toString();
		art.dialog.open(url, {
			id:"view",
			title : '审核',
			width : 400,
			lock:true,
			height : 200,
			close:function()
			{
				$("form:first").submit();
			}
		});
	}else{
		art.dialog.alert("批量退回只能选择申请状态为待初审的数据！");
		return;
	}
	/*art.dialog.confirm("请确认是否取消预约？",function(r) {
		if(r){
			$('#id').val(strIds.toString());
			document.getElementById("refundForm").action=ctx+"/borrow/refund/longRefund/operate";
			$('#refundForm').submit();
	  } 
	},function(){
		
	});*/
}