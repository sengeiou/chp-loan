var id;
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
	// 点击清除，清除搜索页面中的数据，DOM
	$("#clearBtn").click(function(){		
	 $(':input','#searchTable')
	  .not(':button,:submit, :reset')
	  .val('')
	  .removeAttr('checked')
	  .removeAttr('selected');
	 $("select").trigger("change");
	});
	$('#searchBtn').bind('click',function(){
		$('#grantForm').submit(); 
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
		var totalGrantMoney=0.00;
		if($('#checkAll').attr('checked')=='checked'|| $('#checkAll').attr('checked'))
		{
			$(":input[name='checkBoxItem']").each(function() {
				$(this).attr("checked",'true');
				totalGrantMoney+=parseFloat($(this).attr("grantAmount"));
				});
		}else{
			$(":input[name='checkBoxItem']").each(function() {
				$(this).removeAttr("checked");
			});
			totalGrantMoney+=parseFloat($("#hiddenTotalGrant").val());
		}
		$("#totalGrantMoney").text(fmoney(totalGrantMoney,2));
	});
	
	//计算金额
	$(":input[name='checkBoxItem']").click(function(){
		var totalGrantMoney=0.00;
			$(":input[name='checkBoxItem']:checked").each(function(){
				totalGrantMoney+=parseFloat($(this).attr("grantAmount"));
        	});
			if ($(":input[name='checkBoxItem']:checked").length==0) {
				totalGrantMoney+=parseFloat($("#hiddenTotalGrant").val());
			}
			$("#totalGrantMoney").text(fmoney(totalGrantMoney,2));

	});
	
	// 线上放款弹出框
	$("#onLineGrantBtn").click(function(){
		var checkVal = "";
		
		
		
		if($(":input[name='checkBoxItem']:checked").length==0){
			
			art.dialog.alert("请选择线上放款数据！");
	          				
		 }else{
				// 对选中的单子进行线上放款，根据合同编号 &&$(this).attr("contractCode")!="网银"
				$(":input[name='checkBoxItem']:checked").each(function(){	                		
	         		if(checkVal!="")
	         		{
	         			checkVal+=";"+$(this).attr("contractCode");
	         		}else{
	         			checkVal=$(this).attr("contractCode");
	         		}
	         	});
				$(this).attr("data-target","#online");
				$("#check").val(checkVal);
			 
		 }
		

				
	});
	
	// 导出excel
	$("#offLineDao").click(function(){
		
		
		var idVal = "";
		var CarLoanFlowQueryView = $("#grantForm").serialize();
		if($(":input[name='checkBoxItem']:checked").length==0){
			
			 var flag=confirm("确认对所有待放款(网银)数据导出？");
	           if(flag){
	        	   CarLoanFlowQueryView+="&idVal="+idVal;

	        	   window.location=ctx+"/car/grant/grantSure/grantExl?"+CarLoanFlowQueryView;
	        	 // 通过js将状态改为处理中
		           $("input[name='checkBoxItem']").not("input:checked").each(function(){
						if($(this).attr("dictStatusFlag") == "待放款" && $(this).attr("dictLoanWayFlag") == "网银" )
						{
							$(this).attr("dictStatusFlag","处理中");
							$("#statusId"+$(this).val()).html("处理中");
						}
			    	});	
	        	
	           }else{
	        	   return;
	           }
	          				
			}else{
		    var dictLoanWayCount=0;
			$(":input[name='checkBoxItem']:checked").each(function(){
        		if(idVal!="")
        		{
        			idVal+=","+$(this).val();
        		}else{
        			idVal=$(this).val();
        		}
        		if($(this).attr("dictLoanWayFlag")!="网银"){
        			dictLoanWayCount++;
        		}
        	});
			if(dictLoanWayCount>0){
				var flag=confirm("勾选中包含有线上的数据，系统将自动去除导出只针对待放款(网银)的数据？");
		         if(!flag){
		        	   return ;
		         }
			 }
			 CarLoanFlowQueryView+="&idVal="+idVal;
			 window.location=ctx+"/car/grant/grantSure/grantExl?"+CarLoanFlowQueryView;
			 	// 通过js将状态改为处理中
				$(":input[name='checkBoxItem']:checked").each(function(){
					
					if($(this).attr("dictStatusFlag") == "待放款" && $(this).attr("dictLoanWayFlag") == "网银" )
					{
						$(this).attr("dictStatusFlag","处理中");
						$("#statusId"+$(this).val()).html("处理中");
					}
	        	});

		
		}

	});
		
	// 线上放款确认,获得选择的平台，
	$("#onlineBtn").click(function(){
		var grantWay=$("#plat").attr("selected","selected").val();
		var checkVal=$("#check").val();
		if(grantWay=="其他"){
			art.dialog.alert("请选择合适的放款划扣平台！");
			return;
		}else{
			online(checkVal,grantWay);
		}
	});
	
    // 退回确定
	$("#backSure").click(function(){
		// 获得选中的退回原因，退回到合同审核，获得的为name
		var dictBackMestype=$("#reason").attr("selected","selected").val();
		//备注
		var remark=$("#remark").val();
		var applyId = $("#applyId").val();
		var loanCode = $("#loanCode").val();
		var contractCode = $("#contractCode").val();
		var carLoanWorkQueues = 'HJ_CAR_DEDUCTION_COMMISSIONER';

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
			$("#back_div").hide();
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
						windowLocationHref(ctx+'/car/carLoanWorkItems/fetchTaskItems/deductionCommissioner');
					}else{
						alert('退回失败!');
						windowLocationHref(ctx+'/car/carLoanWorkItems/fetchTaskItems/deductionCommissioner');
					}
				}
			});
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
	
	
	
	// 上传回执结果,在上传之前要进行验证，所以上传回执结果要使用Ajax进行控制
	$("#sureBtn").click(function(){
		var formData = new FormData($("#uploadAuditForm")[0]);
		if(validateXlsFileImport('fileid','file')){
			$.ajax({
				type : 'post',
				url : ctx + '/car/grant/grantDeal/importResult',
				data : formData,
				cache: false,
				processData: false,
				contentType: false,
				dataType : 'text',
				success : function(data) {
					if (data == 'true') {
						windowLocationHref(ctx + "/car/carLoanWorkItems/fetchTaskItems/deductionCommissioner");
					} else if (data == 'false') {
						art.dialog.alert("导入excel格式有误，请导入正确格式的数据");
					} else {
						
					}
				}
			});
		}
		return false;
	});
	//手动确认
	$("#manualBtn").click(function(){
	
		var contractCode = ""; 
		if($(":input[name='checkBoxItem']:checked").length==0){
			
			art.dialog.alert("请选择数据！");
	          				
			}else{
				var flag=confirm("确认对所勾选的数据手动确认放款？");
		           if(flag){
				$(":input[name='checkBoxItem']:checked").each(function(){
	        		if(contractCode !="")
	        		{
	        			
	        			contractCode+=","+$(this).attr('contractCode');
	        		}else{
	        		
	        			contractCode = $(this).attr('contractCode');
	        		}
	        	});
				window.location.href=ctx+"/car/grant/grantDeal/manualSure?contractCode="+contractCode;
		           }
			}
	});
	
		
	
	//查看退回原因
	$(":input[name='seeBack']").each(function(){
		$(this).click(function(){
			var contractCode=$(this).attr("contractCode");
			$.ajax({
				type : 'post',
				url : ctx + '/borrow/grant/grantDeal/seeBackReason',
				data : {
					'contractCode':contractCode
					},
				success : function(data) {
					if(data!=null){
						art.dialog.alert("退回原因:"+data);
					}
				}
			});
	    });
	});
});
	function showCarLoanDownload(){
		art.dialog.open('http://10.168.230.116:8080/SunIAS/SunIASRequestServlet.do?UID=admin&PWD=111&AppID=AB&UserID=zhangsan&UserName=zhangsan&OrgID=1&OrgName=fenbu&info1=BUSI_SERIAL_NO:400000317152215;OBJECT_NAME:HUIJINSY1;QUERY_TIME:20150611;FILELEVEL:1,3;RIGHT:1111111', {
			title: "客户影像资料",	
			top: 80,
			lock: true,
			    width: 1000,
			    height: 450,
			},false);	
	}
    // 线上放款处理，调用中金接口
	function online(checkVal,grantWay){
			$.ajax({
				type : 'post',
				url : ctx + '/car/grant/grantSure/onlineGrantDeal',
				data : {
					'checkVal':checkVal,
					'grantWay':grantWay
					},
				dataType:'json',
				success : function(data ) {
					if(data == true){
					  	art.dialog.alert('发送成功!',function(){
					  		windowLocationHref(ctx+'/car/carLoanWorkItems/fetchTaskItems/deductionCommissioner');
					  	});
					}else if(data == "2"){
						art.dialog.alert('没有符合条件的数据!',function(){
							windowLocationHref(ctx+'/car/carLoanWorkItems/fetchTaskItems/deductionCommissioner');
						});
						
					}else{
						art.dialog.alert('发送失败!',function(){
							windowLocationHref(ctx+'/car/carLoanWorkItems/fetchTaskItems/deductionCommissioner');
						});
						
					}
				}
			});
	}
	
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
	   	    url : ctx+'/borrow/grant/grantDeal/importResultTest',
	        type: 'post',
	        secureuri: false, //一般设置为false
	        fileElementId: "fileid", // 上传文件的id、name属性名
	        dataType: 'json', //返回值类型，一般设置为json、application/json
	        success: function(data,status){
	        	var isReturn = data.isReturn;
	        	var lst = data.lst;
	        	alert("he"+isReturn);
	        	if(isReturn){
	        		// 如果有不符合条件的，将list传送过来，confirm一下，如果确定，将list传送到后台再次进行处理
	        		var sure = confirm("之前放款划扣尚未退款，请确认是否继续放款？");
	        		if(sure){
	        			// 重定向到处理事件
	        		}
	        	}
	        },
	        error: function(data, status, e){ 
	               alert(e);
	        }
	    });
	}
	
	// 导入表格进行处理
	function sendMoney(lst){
		$.ajax({
			type : 'post',
			url : ctx+'/borrow/grant/grantDeal/importResultTestDeal',
			data : {
				'lst':lst
				},
			dataType:'json',
			success : function(data) {
				if(data){
					alert('上传成功!');
					windowLocationHref(ctx+'/borrow/grant/grantSure/grantSureFetchTaskItems?flowFlag=GRANT');
				}
			},
			error: function(){ 
	               alert("请求异常");
	        }
		});
	}
	
	// 金额精确到小数点后2位
	  function fmoney(s, n) {  
	        n = n > 0 && n <= 20 ? n : 2;  
	        s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";  
	        var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];  
	        t = "";  
	        for (i = 0; i < l.length; i++) {  
	            t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");  
	        }  
	        return t.split("").reverse().join("") + "." + r;  
	    }  

