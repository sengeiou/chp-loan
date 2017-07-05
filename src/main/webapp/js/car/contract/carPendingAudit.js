$(document).ready(function(){
	 
	 $('#clearBtn').bind('click',function(){
		 $(':input','#searchTable')
		  .not(':button, :reset')
		  .val('')
		  .removeAttr('checked')
		  .removeAttr('selected');
		 $("select").trigger("change");
		 
	 });
	 $('#showMore').bind('click',function(){
		show(); 
		 
	 });
	 
	 //选择门店
	 loan.getstorelsit("txtStore","hdStore");
	 //退回原因其他的隐藏和显现
	 $("#backNode").bind('change',function(){
		 var selectText = $("#backNode").find("option:selected").val();
		 if (selectText != "9") {
             $("#ta").hide();
          } else {
  		   $("#ta").show(); 
          }
	 });
	//确认提交js
	$(":input[name='manage']").each(function(){
		$(this).bind('click',function(){
			$('#applyId').val($(this).attr('applyId'));
			var url = ctx + "/car/carContract/pendingAudit/loanConfirm";		
			art.dialog.confirm("是否确认办理?",function(){
				$("#loanApplyForm").attr('action',url);
				$("#loanApplyForm").submit();
			});
		})
	});
  //传递applyId到form表单中
  //每次点击退回，将流程信息放到form表单中
	$(":input[name='back']").each(function(){
		$(this).bind('click',function(){
			$('#applyId').val($(this).attr('applyId'));
			//退回是的必填原因的验证
			$("#loanApplyForm").validate({
				onclick: true,
				rules:{
					dictBackMestype: {required:true}
				},
				messages: {
					dictBackMestype: {required:"请选择退回原因"}
				}
			});
		});
	});
  
  //提交退回表单的js方法
  $("#backSure").bind('click',function(){
	var url = ctx + "/car/carContract/pendingAudit/sendBack";
	$("#loanApplyForm").attr('action',url);
	$("#loanApplyForm").submit();
  });
  
  //全选js
  $("#checkAll").bind('click',function(){
	  if(this.checked == true){
		  $(".checks").attr('checked',true);
	  }else {
		  $(".checks").attr('checked',false);
	  }
  });
  //导出打款表excel
  $("#exportWatch").bind('click',function(){
	  var idVal = "";
	  var CarLoanFlowQueryView = $("#inputForm").serialize();
	  if($(":input[name='checks']:checked").length == 0){
		  art.dialog.confirm("确认对所有数据进行导出？",function(){
       	   CarLoanFlowQueryView+="&idVal="+idVal;
       	   window.location.href=ctx+"/car/carContract/pendingAudit/exportWatch?"+CarLoanFlowQueryView;			  
		  });
	  } else{
		  $(":input[name='checks']:checked").each(function(){
	        	 if(idVal!="")
	        	 {
	        	 idVal+=","+$(this).val();
	        	 }else{
	        	 idVal=$(this).val();
	        	 }
	        	});
	        	 CarLoanFlowQueryView+="&idVal="+idVal;
	        	 art.dialog.confirm("确认对选中的数据进行导出？",function(){
	        	 window.location.href=ctx+"/car/carContract/pendingAudit/exportWatch?"+CarLoanFlowQueryView;
	        	 })
	  }
  });
  //上传回执确认
  $("#sureBtn").bind('click',function(){
	  var formData = new FormData($( "#uploadAuditForm" )[0]);
	  $.ajax({
		   type: "POST",
		   url: ctx + "/car/carContract/pendingAudit/uploadReceipt",
		  /* data: $("#uploadAuditForm").serialize(),*/
		   data:formData,
		   cache: false,  
           processData: false,  
           contentType: false ,
		   dataType : 'text',
		   success: function(data){
			   if(data == 'false'){
				   $.jBox.error('传入文件类型不正确', '提示信息');
			   }else if(data == 'date'){
				   $.jBox.error('传入的数据不正确', '提示信息');
			   }else if(data == 'true'){
				   $.jBox.info('上传操作成功', '提示信息');
				   windowLocationHref(ctx + "/car/carLoanWorkItems/fetchTaskItems/statisticsCommissioner");
			   }
		   },
		   error:function(date){
			   $.jBox.error('传输错误', '提示信息');
		   }
		});
  })
  
  
 });