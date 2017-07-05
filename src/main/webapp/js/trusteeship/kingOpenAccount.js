/**
 * 金账户开户
 */
var id;
$(document).ready(function(){
	// 点击清除，清除搜索页面中的数据，DOM
	$("#clearBtn").click(function(){		
	 $(':input','#accountForm')
	  .not(':button,:submit, :reset,:hidden')
	  .val('')
	  .removeAttr('checked')
	  .removeAttr('selected');
	 $("#storeOrgId").val('');
	});
	
	// 伸缩
	$("#showMore").click(function(){
		if(document.getElementById("T1").style.display=='none'){
			document.getElementById("showMore").src=ctxStatic + '/images/down.png';
			document.getElementById("T1").style.display='';
			document.getElementById("T2").style.display='';
		}else{
			document.getElementById("showMore").src=ctxStatic + '/images/up.png';
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
		
	// 自动开户
	$("#tryOpenAccount").click(function(){
		var checkVal = null;
		if($(":input[name='checkBoxItem']:checked").length==0){
			art.dialog.alert('请选择要操作的数据!');
	           return;
			}else{				
				// 对选中的单子进行批量开户,paramStr:loanCode,applyId
				$(":input[name='checkBoxItem']:checked").each(function(){	                		
            		if(checkVal!=null)
            		{
            			checkVal+=";"+$(this).attr("paramStr");
            		}else{
            			checkVal=$(this).attr("paramStr");
            		}
            	});				
			}
		
		$.ajax({
				type : 'post',
				url : ctx + '/borrow/king/openAccount/updateKingAccount',
				data : {
						'paramStr':checkVal,
						'operateType':'ACCOUNT_TRY_OPEN'
					},
				dataType:'json',
				success : function(data) {
					if(data){
						art.dialog.list['Alert'].close();
						art.dialog.alert(data,function(){
							window.location=ctx+'/borrow/king/openAccount/getTaskItems';
						});						
					}
				}
		});				
		art.dialog.alert("已开始自动开户，请稍候");
	});	
	
	// 批量更新协议库
	$("#refreshProtocol").click(function(){
		var loanCode = null;
		if($(":input[name='checkBoxItem']:checked").length==0){
			art.dialog.alert('请选择要操作的数据!');
	           return;
			}else{
				// 对选中的单子批量更新协议库
				$(":input[name='checkBoxItem']:checked").each(function(){	                		
            		if(loanCode!=null) {
            			loanCode += ","+$(this).attr("loanCode");
            		} else {
            			loanCode = $(this).attr("loanCode");
            		}
            	});	
			}		
		
		$.ajax({
				type : 'post',
				url : ctx + '/borrow/king/openAccount/updateProtocol',
				data : 'params='+loanCode,
				success : function(data) {
					art.dialog.list['Alert'].close();
					art.dialog.alert(eval(data),function(){
						window.location=ctx+'/borrow/king/openAccount/getTaskItems';
					});
				}
		});
		art.dialog.alert("已开始更新协议库，请稍候");
	});	
	
	// 批量退回弹出框
	$("#batchBackBtn").click(function(){
		var checkVal = null;
		if($(":input[name='checkBoxItem']:checked").length==0){
			   art.dialog.alert('请选择要操作的数据!');
	           return false;
			}else{
				// 对选中的单子批量退回
				$(":input[name='checkBoxItem']:checked").each(function(){	                		
            		if(checkVal!=null)
            		{
            			checkVal+=";"+$(this).attr("paramStr");
            		}else{
            			checkVal=$(this).attr("paramStr");
            		}
            	});
				$(this).attr("data-target","#backBatch_div");
				$("#check").val(checkVal);
			}
	});
	
	
	// 批量退回
	$('#backBatchSure').click(function(){
	  var batchBackReason = $('#batchBackReason').val();
	  if(batchBackReason == null||batchBackReason==''){
		  art.dialog.alert("请输入退回原因！");
		  return false;
	  }else{
		  var checkVal=$("#check").val();
		  $(":input[name='checkBoxItem']:checked").each(function(){	                		
      		if($(this).attr("issplit")!='0'){
      			 art.dialog.alert('选择的数据中含有拆分数据，不能批量退回，合同号：'+$(this).attr("contractCode"));
			     return false;
      		}
      	 });
		  $.ajax({
				type : 'post',
				url : ctx + '/borrow/king/openAccount/updateKingAccount',
				data : {
					'paramStr':checkVal,
					'operateType':'ACCOUNT_UNDO',
					'kingBackReason':batchBackReason
					},
				dataType:'json',
				success : function(data) {
					if(data==""){
						art.dialog.alert('退回成功!');
						$('#batchBackReason').val("");
						window.location=ctx+'/borrow/king/openAccount/getTaskItems';
					}else{
						art.dialog.alert('退回失败:'+data);
						$('#batchBackReason').val("");
						window.location=ctx+'/borrow/king/openAccount/getTaskItems';
					}
				}
			});
	  }
	});
	
	// 导出excel
	$("#exportAccount").click(function(){
		var idVal = "";
		if($(":input[name='checkBoxItem']:checked").length>0){
			$(":input[name='checkBoxItem']:checked").each(function(){
        		if(idVal!="")
        		{
        			idVal+=","+$(this).attr("loanCode");
        		}else{
        			idVal=$(this).attr("loanCode");
        		}
        	});
		}else{
			if($(":input[name='checkBoxItem']").length>0){
				alert("导出列表中所有记录!");
				$(":input[name='checkBoxItem']").each(function(){
	        		if(idVal!="")
	        		{
	        			idVal+=","+$(this).attr("loanCode");
	        		}else{
	        			idVal=$(this).attr("loanCode");
	        		}
	        	});
			}else{
				art.dialog.alert("列表中没有数据！");
				return false;
			}	
			
		}
		window.location.href=ctx+"/borrow/king/openAccount/exportAccountExl?idVal="+idVal
		//+"&"+loanGrant;
	});
	
	
	// 导出协议库
	$("#exportProtocol").click(function(){
		var idVal = "";
		if($(":input[name='checkBoxItem']:checked").length>0){
			$(":input[name='checkBoxItem']:checked").each(function(){
        		if(idVal!="")
        		{
        			idVal+=","+$(this).attr("loanCode");
        		}else{
        			idVal=$(this).attr("loanCode");
        		}
        	});
		}else{
			if($(":input[name='checkBoxItem']").length>0){
				alert("导出列表中所有记录!");
				$(":input[name='checkBoxItem']").each(function(){
	        		if(idVal!="")
	        		{
	        			idVal+=","+$(this).attr("loanCode");
	        		}else{
	        			idVal=$(this).attr("loanCode");
	        		}
	        	});
			}else{
				art.dialog.alert("列表中没有数据！");
				return false;
			}	
			
		}
		window.location.href=ctx+"/borrow/king/openAccount/exportProtocolExl?idVal="+idVal
		//+"&"+loanGrant;
	});
			
	// 每次点击退回，将流程信息放到批量退回的div form表单中
	$(":input[name='back']").each(function(){
		$(this).bind('click',function(){			
			var checkVal = $(this).parents('tr:first').find('input[name="checkBoxItem"]').attr("paramStr");				
			$(this).attr("data-target","#backBatch_div");
			$("#check").val(checkVal);			
		});
	});
	
	// 每次点击开户成功，该条记录流转到流程下一个节点
	$(":input[name='openSuccess']").each(function(){
		$(this).bind('click',function(){
			var link = $(this);
			art.dialog.confirm("确认开户成功",
				function(){				
				var checkVal = link.parents('tr:first').find('input[name="checkBoxItem"]').attr("paramStr");
				 var dialog = art.dialog({
				      content: '正在处理...',
				      cancel:false,
				      lock:true
			 		});
				  $.ajax({
						type : 'post',
						url : ctx + '/borrow/king/openAccount/updateKingAccount',
						data : {
							'paramStr':checkVal,
							'operateType':'ACCOUNT_SUCCESS'
							},
						dataType:'json',
						success : function(data) {
							dialog.close();
							if(data){
								art.dialog.alert(data,function(){
									window.location=ctx+'/borrow/king/openAccount/getTaskItems';
								});
							}
						}
					});	
			});
					
		});
	});
	
	// 每次点击开户失败，弹出失败原因输入窗口
	$(":input[name='openFail']").each(function(){
		$(this).bind('click',function(){					
			var checkVal = $(this).parents('tr:first').find('input[name="checkBoxItem"]').attr("paramStr");	            		
			$(this).attr("data-target","#fail_div");
			$("#check").val(checkVal);			
		});
	});
	
	// 开户失败，更新开户状态
	$("#openFailSure").click(function(){					
			var checkVal = $("#check").val();		
			if($('#failReason').val().trim()==""){
				art.dialog.alert("请输入失败原因");
				return false;
			}
			  $.ajax({
					type : 'post',
					url : ctx + '/borrow/king/openAccount/updateKingAccount',
					data : {
						'paramStr':checkVal,
						'operateType':'ACCOUNT_ERROR',
						'kingBackReason':$('#failReason').val()
						},
					dataType:'json',
					success : function(data) {
						if(data){
							// 更新失败
							art.dialog.alert(data,function(){
								window.location=ctx+'/borrow/king/openAccount/getTaskItems';
							});
						}else{
							// 更新成功
							$('#failReason').val('');
							window.location=ctx+'/borrow/king/openAccount/getTaskItems';
						}
					}
				});			
	});
	
	// 上传回执结果,在上传之前要进行验证，所以上传回执结果要使用Ajax进行控制
	$("#sureBtn").click(function(){
//		$("#uploadAuditForm").submit();
		ajaxFileUpload();
	});
	
});
			
	// 关闭窗口
	function closeModal(id){
		$("#"+id).modal('hide');
	}
	
	// 上传文件处理
	function ajaxFileUpload() {
		  var file = $("#fileid");
	      if($.trim(file.val())==''){
	             alert("请选择文件");
	             return false;
	     }
	    $.ajaxFileUpload({
	   	    url : ctx+'/borrow/king/openAccount/importExl',
	        type: 'post',
	        secureuri: false, //一般设置为false
	        fileElementId: "fileid", // 上传文件的id、name属性名
	        dataType: 'application/json', 
	        success: function(data,status){
	        	art.dialog.alert(data,function(){
	        		window.location=ctx+'/borrow/king/openAccount/getTaskItems';	   
	        	});
	        },
	        error: function(data, status, e){
	        	art.dialog.alert("请求异常");
	        }
	    });
	}	
