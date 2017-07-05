
	function clear1(){
		$("#serialnum").val('');
		$("#contractCode").val('');
		$("#accountName").val('');
		$("#bankName").val('');
		$("#banknum").val('');
		$("#banknum").trigger("change");
		$("#backResult").val('');
		$("#backResult").trigger("change");
		$("#opearTime").val('');
	 	$("#status").val('');
	 	$("#status").trigger("change");
	 	$("#deductBeginTime").val('');
	 	$("#deductEndTime").val('');
	 	$("#beginBackTime").val('');
	 	$("#endBackTime").val('');
	}
	
	function checkAll(t){
		if(t.checked == true){
			$("input[name='checkBoxItem']").each(function(){
				   $(this).attr("checked",true);
				  }); 
			
		  }else {
			  $("input[name='checkBoxItem']").each(function(){
				   $(this).attr("checked",false);
				  }); 
		  }
		chekbox_click();
	}
	
	function chekbox_click(){
		var count = 0; 
		var money = 0.0;
		var applyReallyAmount = 0.0;
		$("input[name='checkBoxItem']").each(function(){
			   if($(this).is(':checked')){
				   count += 1;
				   money = money + parseFloat($('#'+$(this).val()+'money').val());
				   applyReallyAmount = applyReallyAmount + parseFloat($('#'+$(this).val()+'applyReallyAmount').val());
			   }
			  }); 
		if(count==0){
			
			$('#totalNum').empty();
			$('#totalNum').html($('#num').val());
			$('#deductAmount').empty();
			$('#deductAmount').html($('#deduct').val());
			$('#applyReallyAmount').empty();
			$('#applyReallyAmount').html($('#applyReallyAmountdeduct').val());
			
			
		}else{
			$('#totalNum').empty();
			$('#totalNum').html(count);
			$('#deductAmount').empty();
			$('#deductAmount').html(money);
			$('#applyReallyAmount').html(applyReallyAmount?applyReallyAmount:0.00 );
		}
		
	}
	
	function showHistory(id){
		var url = ctx + '/borrow/zhongjin/getHistory?cpcnId='
			+ id;
		art.dialog.open(url, {
			id : 'his',
			title : '历史',
			lock : true,
			width : 700,
			height : 350
		}, false); 
	} 
	
	function cancel(){
		var strIds=new Array();//声明一个存放id的数组
		$("input[name='checkBoxItem']:checkbox").each(function(i,d){
			if (d.checked) {
				strIds.push(d.value);
			}
		});
		art.dialog.confirm("请确认是否取消预约？",function(r) {
			if(r){
				$('#checkIds').val(strIds.toString());
				document.getElementById("deductsForm").action=ctx+"/borrow/zhongjin/cancelOrderDeduct";
				$('#deductsForm').submit();
		  } 
		},function(){
			
		});
	}
	
	function exports(){
		var strIds=new Array();//声明一个存放id的数组
		$("input[name='checkBoxItem']:checkbox").each(function(i,d){
			if (d.checked) {
				strIds.push(d.value);
			}
		});
		art.dialog.confirm("请确认是否导出？",function(r) {
			if(r){
				$('#checkIds').val(strIds.toString());
				document.getElementById("deductsForm").action=ctx+"/borrow/zhongjin/export";
				$('#deductsForm').submit();
		  } 
		},function(){
			
		});
	}
	
	function search(){
		document.getElementById("deductsForm").action=ctx+"/borrow/zhongjin/donelist";
		$('#deductsForm').submit();
	}