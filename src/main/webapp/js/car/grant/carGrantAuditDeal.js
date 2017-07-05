//备注校验
jQuery.validator.addMethod("remarkValidate", function() {
	var isOK = true;
	// 获得选中的退回原因
	var dictBackMestype=$("#reason").attr("selected","selected").val();
	//备注
	var remark=$("#remark").val();
	if(dictBackMestype == '9' && remark == ''){
		isOK = false;
	}
    return isOK;
}, "退回原因为 其他 时请填写备注！");

$(document).ready(function(){
	// 点击页面跳转事件
	$(":input[name='jumpTo']").each(function(){
		$(this).click(function(){
			var applyId=$(this).val();
			var url=ctx +"/car/grant/grantAudit/grantAuditJump?checkVal="+applyId;
			jump(url);
		
	});
	});
	
	
	// 同步到财富的数据测试
	$("#sycBtn").click(function(){
		window.location.href=ctx +"/borrow/grant/grantAudit/sycTest";
	})
	
	function jump(url){
	    art.dialog.open(url, {  
			   title: '放款审核!',
			   lock:true,
			   width:700,
			   height:350
			},false);  
	}
	
	// 点击清除，清除搜索页面中的数据，DOM
	$("#clearBtn").click(function(){		
	 $(':input','#grantAuditForm')
	  .not(':button,:submit, :reset,:hidden')
	  .val('')
	  .removeAttr('checked')
	  .removeAttr('selected');
	});
	
	// 导出excel,
	$("#daoBtn").click(function(){
		var idVal = "";
		var CarLoanFlowQueryView = $("#grantAuditForm").serialize();
		
		if($(":input[name='checkBoxItem']:checked").length==0){
			
			 var flag=confirm("确认对所有待审核数据导出？");
	           if(flag){
	        	   CarLoanFlowQueryView+="&idVal="+idVal;
	        	   window.location.href=ctx+"/car/grant/grantSure/grantAuditExl?"+CarLoanFlowQueryView;
	           }else{
	        	   return;
	           }
	          				
			}else{
			$(":input[name='checkBoxItem']:checked").each(function(){
        		if(idVal!="")
        		{
        			idVal+=","+$(this).val();
        		}else{
        			idVal=$(this).val();
        		}
        	});
			
			 CarLoanFlowQueryView+="&idVal="+idVal;
			window.location.href=ctx+"/car/grant/grantSure/grantAuditExl?"+CarLoanFlowQueryView;
		}
		
	});
	
	// 点击取消，关闭模式对话框
	$("#closeBtn").click(function(){
		closeBtn();
	});
	
	function closeBtn(){
		art.dialog.close();
	}
	
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
	
	// 审核或者退回
	$(":input[name='sort']").click(function(){
		showDiv1();
	});
	
	// 点击确认
	$("#auditSure").click(function(){
		// 暂时获得放款时间，放款时间需要进行日期判定
		var ParamEx=$("#param").serialize();
		var checkEle=$(":input[name='sort']:checked").val();
		var result=null;
		// 点击退回
		if(checkEle=="退回"){
			// 获得选中的退回原因，退回到合同审核，获得的为name
			var dictBackMestype=$("#reason").attr("selected","selected").val();
			//备注
			var remark=$("#remark").val();
			var Tflag =$("#reasonValidate").validate({
				onclick: true, 
				rules:{
				
					consLoanRemarks:{
						maxlength:200,
						remarkValidate:true
					},
		
			},
				messages : {

					consLoanRemarks:{
						maxlength:"备注200字以内"
					},
			
				}
	}).form();
			if (Tflag) {
				// 退回原因确定
				back(ParamEx,dictBackMestype,remark);
			}
		
		
			}else{
			// 审核通过
			result=$("#grantDate").val();
			if(result==""){
				art.dialog.alert("请选择放款日期！");
				return;
			}else{
				grantOver(ParamEx,result);
			}
		}
	})
	
	//退回处理
	function back(ParamEx,dictBackMestype,remark){
		ParamEx+="&dictBackMestype="+dictBackMestype+"&remark="+remark;
		$.ajax({
			type : 'post',
			url : ctx+'/car/grant/grantAudit/grantAuditBack',
			data :ParamEx,
			cache : false,
			dataType : 'text',
			success : function(result) {
				if(result=="true"){
					alert("退回成功");
					art.dialog.open.origin.location.href = ctx+"/car/carLoanWorkItems/fetchTaskItems/balanceManager";
				}else if(result=="deal"){
					alert("该数据正在划扣中，不能进行放款退回！");
					closeBtn();
				}else{
					art.dialog.alert('退回失败');
					art.dialog.open.origin.location.href = ctx+"/car/carLoanWorkItems/fetchTaskItems/balanceManager";
				}
			},
			error : function() {
				art.dialog.alert('请求失败');
			}
		});
	}
	
	// 审核通过,更新放款日期
	function grantOver(ParamEx,result){
		
		ParamEx+="&result="+result;
		$.ajax({
			type : 'post',
			url : ctx+'/car/grant/grantAudit/grantAuditOver',
			data : ParamEx,
			dataType : 'text',
			success : function(result) {
				if(result){
					alert("审核成功！");
					art.dialog.open.origin.location.href=ctx+"/car/carLoanWorkItems/fetchTaskItems/balanceManager";
				}else{
					art.dialog.alert("审核失败！");
					art.dialog.open.origin.location.href=ctx+"/car/carLoanWorkItems/fetchTaskItems/balanceManager";
				}
			},
			error : function() {
				art.dialog.alert('请求失败！');
			}
		});
	}
	
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
	
	// 批量处理,根据id查找办理页面
	$("#auditBtn").click(function(){
		var checkVal=null;
		if($(":input[name='checkBoxItem']:checked").length==0){
			   art.dialog.alert("请选择要进行操作的数据!");
	           return;					
			}else{
				$(":input[name='checkBoxItem']:checked").each(function(){	                		
         		if(checkVal!=null)
         		{
         			checkVal+=","+$(this).val();
         		}else{
         			checkVal=$(this).val();
         		}
         	});
				var url=ctx +"/car/grant/grantAudit/grantAuditJump?checkVal="+checkVal;
				jump(url);
			}
	})
	
	// 日期验证触发事件,点击搜索进行验证
	$("#searchBtn").live("click",function(){//金额输入框失去焦点时，进行格式的验证
		var startDate = $("#lendingTimeStart").val();
		var endDate = $("#lendingTimeEnd").val();
		// 验证
		if((startDate==null || startDate=="")&&(endDate!=null|| endDate!="")){
			art.dialog.alert("开始日期为空");
			return false;
		}
		if((endDate==null|| endDate=="")&&(startDate!=null || startDate!="")){
			art.dialog.alert("结束日期为空");
			return false;
		}
		var arrStart = startDate.split("-");
		var arrEnd = endDate.split("-");
		var sd=new Date(arrStart[0],arrStart[1],arrStart[2]);  
        var ed=new Date(arrEnd[0],arrEnd[1],arrEnd[2]);  
        if(sd.getTime()>ed.getTime()){   
        	art.dialog.alert("开始日期不能大于结束日期");
        	$("#lendingTimeStart").val("");
        	$("#lendingTimeEnd").val("");
            return false;     
        }  
	});
	
	//切换
	 function showDiv1(){
			var elements =  document.getElementsByName("sort");
			for(var i = 0 ; i < elements.length ;i++){
				var clickEle= elements[i].value;
				if(elements[i].checked){
					if("通过"==clickEle){
					document.getElementById("sort_div").style.display = "inline";
					document.getElementById("back_div").style.display = "none";
				    }
				   else{
					 document.getElementById("back_div").style.display = "inline";
					 document.getElementById("sort_div").style.display = "none";
				   } 
				}
			}
	 }
	 
	// 退回确定
		$("#backSure").click(function(){
			// 获得选中的退回原因，退回到合同审核，获得的为name
			var dictBackMestype=$("#reason").attr("selected","selected").val();
			//备注
			var remark=$("#remark").val();
			var applyId = $("#applyId").val();
			var loanCode = $("#loanCode").val();
			var contractCode = $("#contractCode").val();
			var carLoanWorkQueues = 'HJ_CAR_LOAN_BALANCE_MANAGER';
			if(dictBackMestype ==""){
				alert("退回原因不能为空！");
			}else{
				if(dictBackMestype == "其他" && remark == ""){
					alert("退回原因为其他时  备注不能为空！");
					
				}else{
					
					$.ajax({
						type : 'post',
						url : ctx + '/car/grant/grantSure/backTo',
						data : {
							'applyId':applyId,
							'loanCode':loanCode,
							'contractCode':contractCode,
							'dictBackMestype':dictBackMestype,
							'remark':remark,
							'carLoanWorkQueues':carLoanWorkQueues
							
						},
						success : function(data) {
							if(data!=null){
								alert('退回成功!');
								window.location=ctx+'/car/carLoanWorkItems/fetchTaskItems/balanceManager';
							}else{
								alert('退回失败!');
								window.location=ctx+'/car/carLoanWorkItems/fetchTaskItems/balanceManager';
							}
						}
					});
				}
				
			
			}
		
		});
	 
		// 每次点击退回，将流程信息放到form表单中
		$(":input[name='back']").each(function(){
			$(this).bind('click',function(){
				$('#applyId').val($(this).attr('applyId'));
				$('#contractCode').val($(this).attr('contractCode'));
				$('#loanCode').val($(this).attr('loanCode'));

			});
		});
		
});