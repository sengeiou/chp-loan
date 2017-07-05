$(document).ready(function(){
	loan.getstorelsit("mendianName","mendianId");
	$('#showMore').bind('click',function(){
		 show();

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
	var url = ctx + '/borrow/refund/longRefund/refundExaminePage?id='+id+'&contractCode='+contractCode+'&appType='+appType+'&mt='+time+'&type=end';
	
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
	var strIds=new Array();//声明一个存放id的数组
	var strMts=new Array();
	var loanStatus = "";
	$("input[name='checkBoxItem']:checkbox").each(function(i,d){
		if (d.checked) {
			strIds.push(d.value.split("_")[0]);
			strMts.push(d.value.split("_")[1]);
			//如果是逾期数据进行标识
			if(d.value.split("_")[2]=='88'){
				loanStatus="1";
			}
		}
	});
	if(strIds.length<1){
		art.dialog.alert("请选择操作数据！");
		return;
	}
	//对于批量通过的如果有逾期数据则不允许进行批量操作
	if(type=='1' && loanStatus=="1"){
		art.dialog.alert("逾期客户，禁止退款操作！");
		return;
	}
	var url = ctx + '/borrow/refund/longRefund/endOperatePage?type=end&fkResult='+type+'&id='+strIds.toString()+'&mt='+strMts.toString();
	
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
	/*art.dialog.confirm("请确认是否取消预约？",function(r) {
		if(r){
			$('#id').val(strIds.toString());
			document.getElementById("refundForm").action=ctx+"/borrow/refund/longRefund/operate";
			$('#refundForm').submit();
	  } 
	},function(){
		
	});*/
}