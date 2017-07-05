
$(document).ready(function(){
	
	// 点击清除，清除搜索页面中的数据,催收服务费返款申请页面
	$("#clearBtn").click(function(){	
		$(':input','#backForm')
		  .not(':button, :reset, :submit')
		  .val('')
		  .removeAttr('checked')
		  .removeAttr('selected'); 
	});
	
	//点击清除，清除搜索页面中的数据,催收服务费返款页面
	$("#clearBtnList").click(function(){	
		$(':input','#backListForm')
		  .not(':button, :reset')
		  .val('')
		  .removeAttr('checked')
		  .removeAttr('selected'); 
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
	
	// 弹出退回弹框,如果有选择，将选中的退回，如果没有退回，将查询条件下的所有状态为"待返款"的单子进行退回操作
	  $("#exitBt").click(function(){
		   var returnId = "";
		   if($(":input[name='checkBoxItem']:checked").length == 0){
				var flag = confirm("确定将所有的待返款的单子进行退回？");
				if(flag){
					$(this).attr("data-target","#backDiv");
				}else{
					return false;
				}
			}else{
				$(":input[name='checkBoxItem']:checked").each(function(){
	        		if(returnId!="")
	        		{
	        			returnId+=","+$(this).attr("returnId");
	        		}else{
	        			returnId=$(this).attr("returnId");
	        		}
	        	});
				$("#returnIds").val(returnId);
				$(this).attr("data-target","#backDiv");
			}
	   });
	
	 // 导入返款结果处理
	   $("#sureBtn").click(function(){
		   $("#uploadAuditForm").submit(); 
	   });
	
	// 导出催收服务费待申请列表,需要将查询条件传送过去
	$("#daoBtn").click(function(){
			var checkVal = "";
			var backForm = $("#backForm").serialize(); 
			if($(":input[name='checkBoxItem']:checked").length>0){
				$(":input[name='checkBoxItem']:checked").each(function(){
	        		if(checkVal!="")
	        		{
	        			checkVal+=","+$(this).attr("val");
	        		}else{
	        			checkVal=$(this).attr("val");
	        		}
	        	});
			}
			window.location.href=ctx+"/borrow/grant/urgeServicesBack/exportBackApply?checkVal="+checkVal+"&"+backForm;
	});
	
	// 导出催收服务费返款列表
	$("#daoBtnList").click(function(){
			var checkVal = "";
			var backForm = $("#backForm").serialize(); 
			if($(":input[name='checkBoxItem']:checked").length>0){
				$(":input[name='checkBoxItem']:checked").each(function(){
	        		if(checkVal!="")
	        		{
	        			checkVal+=","+$(this).attr("returnId");
	        		}else{
	        			checkVal=$(this).attr("returnId");
	        		}
	        	});
			}
			window.location.href=ctx+"/borrow/grant/urgeBackList/exportBackList?checkVal="+checkVal+"&"+backForm;
		
	});
	
	// 发送返款申请
	$("#sendApply").click(function(){
		// 如果没有选中的单子，默认为列表中的所有状态为返款退回，待申请返款的单子
		var checkVal="";
		if($(":input[name='checkBoxItem']:checked").length==0){
	           var flag=confirm("确认发送全部单子的返款申请？");
	           if(flag){
	        	   sendAll(checkVal,"");
	           }else{
	        	   return;
	           }
			}else{
				// 获得选中的单子
				var r=confirm("确定将选中的单子发送返款申请么？");
				var num=0;
	            if(r){
	            	$(":input[name='checkBoxItem']:checked").each(function(){
	            		if($(this).attr("channel")=="5"){
	            			return;
	            		}
	            		if(checkVal!=""){
	            			checkVal+=","+$(this).attr("val");
	            		}else{
	            			checkVal=$(this).attr("val");
	            		}
	            		num++;
	            	});
	            	if(num<$(":input[name='checkBoxItem']:checked").length){
	            		top.$.jBox.tip("不能发送渠道为ZCJ的数据");
	            		return;
	            	}
	            	sendAll(checkVal,"");
		      }else{
	            	return;
	          }
		}
	});
	//大金融发送
	$("#djrApply").click(function(){
		// 如果没有选中的单子，默认为列表中的所有状态为返款退回，待申请返款的单子
		var checkVal="";
		if($(":input[name='checkBoxItem']:checked").length==0){
	           var flag=confirm("确认发送全部单子的返款申请？");
	           if(flag){
	        	   sendAll(checkVal,"5");
	           }else{
	        	   return;
	           }
			}else{
				// 获得选中的单子
				var r=confirm("确定将选中的单子发送返款申请么？");
	            if(r){
	            	var num=0;
	            	$(":input[name='checkBoxItem']:checked").each(function(){
	            		if($(this).attr("channel")!="5"){
	            			return;
	            		}
	            			
	            		if(checkVal!=""){
	            			checkVal+=","+$(this).attr("val");
	            		}else{
	            			checkVal=$(this).attr("val");
	            		}
	            		num++;
	            	});
	            	if(num<$(":input[name='checkBoxItem']:checked").length){
	            		top.$.jBox.tip("只能发送渠道为ZCJ的数据");
	            		return;
	            	}
	            	
	            	sendAll(checkVal,"5");
		      }else{
	            	return;
	          }
		}
	});
	
	//退回确认
	$("#backSure").click(function(){
		var backReason=$("#backReason").val();
		var checkVal = $("#returnIds").val();
		if(backReason==""){
			top.$.jBox.tip("退回原因不能为空！");
			return;
		}else{
			backDeal(backReason,checkVal);
		}
	});

});

// 查询条件下的单子的发送返款申请
function sendAll(checkVal,channelFlag){
	var backForm = $("#backForm").serialize();
	backForm +="&checkVal="+checkVal+"&channelFlag="+channelFlag; 
	$.ajax({
		type : 'post',
		url : ctx + '/borrow/grant/urgeServicesBack/sendBackApply',
		data : backForm,
		cache : false,
		success : function(result) {
			    var obj = eval("("+result+")");
				if(obj.success=="true"){
					top.$.jBox.tip(obj.message);
					window.location.reload();
				}else{
					top.$.jBox.tip(obj.message);
					window.location.reload();
				}
		},
		error : function() {
			top.$.jBox.tip('请求异常！');
		}
	});
}


// 退回处理
function backDeal(backReason,checkVal){
	var backForm = $("#backForm").serialize();
	backForm +="&checkVal="+checkVal+"&backReason="+backReason;
	$.ajax({
		type : 'post',
		url : ctx + '/borrow/grant/urgeBackList/urgeMoneyBack',
		data : backForm,
		async : false,
		success : function(result) {
			if(result){
				top.$.jBox.tip("退回成功");
				window.location.href = ctx+"/borrow/grant/urgeBackList/urgeBackListInfo";
			}else{
				top.$.jBox.tip(result);
				window.location.href = ctx+"/borrow/grant/urgeBackList/urgeBackListInfo";
			}
		},
		error : function() {
			top.$.jBox.tip("退回异常!");
			window.location.reload();
		}
	});

}

// 查询催收服务费返款操作历史列表（催收返款历史）
function backHistory(rUrgeId){
	// 获得催收返款表id
	var url=ctx + "/borrow/grant/urgeServicesBack/grantJump?rUrgeId="+rUrgeId;
    jump(url);
  }

//关闭窗口
function closeModal(id){
	$("#"+id).modal('hide');
}

//样式控制
function jump(url){
	art.dialog.open(url, {  
		   title: '历史操作',
		   lock:true,
		   width:700,
		   height:350
		},false);
}

