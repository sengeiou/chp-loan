var contractSign={};
contractSign.load = function(){
//	if($('#CancelJXFlag').length!=0){
//		$('#CancelJXFlag').bind('click',function(){
//			cancelFlag();
//		});
//	}
	$('#upLoadBtn').bind('click',function(){
		/* var result = contractSign.judgeSignTime();
		if(!result){
			art.dialog.alert("预约签署日期早于当前日期，不允许操作",'提示');
			return false;
		} */
		contractSign.upload($(this).attr("loanCode"));
	}); 
	$('#confirmBtn').bind('click',function(){
		/* var result = contractSign.judgeSignTime();
		if(!result){
			art.dialog.alert("预约签署日期早于当前日期，不允许操作",'提示');
			return false;
		} */
		var bankSigningPlatform = $('#bankSigningPlatform option:selected').val();
		if(bankSigningPlatform=='' || bankSigningPlatform==null){
		  $('#bankSigningPlatformMsg').html("请选择签约平台");
		  return true;
		}else{
		  $('#bankSigningPlatformHid').val(bankSigningPlatform);
		  var bankSigningPlatformName = $('#bankSigningPlatform option:selected').text();
		  $('#signingPlatformName').val(bankSigningPlatformName);
		  $('#bankSigningPlatformMsg').html("");	
		}
		 var updated = contractSign.updatedValid();
		 if(!updated){
			art.dialog.alert("身份证或照片没有上传完成，请继续上传！"); 
			return true;
		 }
		 var  result = contractSign.validateID();
		 if(!result){
			 art.dialog.alert("身份未验证通过，禁止提交！"); 
			 return result;
		 }
		
		var sunyardUploaded = contractSign.uploadSunyardValid($(this).attr("oldLoanCode"));
		if(!sunyardUploaded){
			art.dialog.alert("合同文件没有上传完成，请继续上传！"); 
			return true;
		}
		
	    art.dialog.confirm('是否确定执行合同上传确认操作',function (){
		    $('#response').val('TO_CONTRACT_CHECK');
		    contractSign.uploadConfirm();
		    $('#confirmBtn').attr('disabled',true);
		    $('#giveUpBtn').attr('disabled',true);
		    $('#backBtn').attr('disabled',true);
		    $('#storeGiveUp').attr('disabled',true);
//		    if($('#CancelJXFlag').length>0){
//		    	$('#CancelJXFlag').attr('disabled',true);
//		    }
	    });
	}); 
	if($('#giveUpBtn').length>0){
		$('#giveUpBtn').bind('click',function(){
		art.dialog.confirm("是否确认执行客户放弃",
				function(){
		        	$('#response').val('TO_GIVEUP');
				    $('#dictOperateResult').val('3');
				    contractSign.uploadConfirm();
				    $('#giveUpBtn').attr('disabled',true);
				    $('#confirmBtn').attr('disabled',true);
				    $('#backBtn').attr('disabled',true);
				    $('#storeGiveUp').attr('disabled',true);
//				    if($('#CancelJXFlag').length>0){
//				    	$('#CancelJXFlag').attr('disabled',true);
//				    }
				});
     		}); 
	 }
	if($('#storeGiveUp').length>0){
		$('#storeGiveUp').bind('click',function(){
			art.dialog.confirm("是否确认执行门店拒绝",
				function(){
		        	$('#response').val('TO_GIVEUP');
				    $('#dictOperateResult').val('1');
				    contractSign.uploadConfirm();
				    $('#giveUpBtn').attr('disabled',true);
				    $('#confirmBtn').attr('disabled',true);
				    $('#backBtn').attr('disabled',true);
				    $('#storeGiveUp').attr('disabled',true);
//				    if($('#CancelJXFlag').length>0){
//				    	$('#CancelJXFlag').attr('disabled',true);
//				    }
				});
     		}); 
	}
	$('#retBtn').bind('click',function(){
		contractSign.returnBack();
	}); 
	$('#backBtn').bind('click',function(){
	  contractSign.backFlow('_grantBackDialog',null,'loanapplyForm');  //传递退回窗口的视图名称
	});
	$('#hisBtn').bind('click',function(){
		showHisByLoanCode($(this).attr('loanCode'));
	});
	$(".lightbox").lightbox({
	    fitToScreen: true,
	    imageClickClose: false
    });
	$(".lightbox-2").lightbox({
	    fitToScreen: true,
	    scaleImages: true,
	    xScale: 1.2,
	    yScale: 1.2,
	    displayDownloadLink: false,
		imageClickClose: false
    });
	$('#plotPhoto').bind('click',function(){
		
	});
	$('#custAppSignCheck').bind('click',function() {
		if ($(this).attr('checked') == true
				|| $(this).attr('checked') == 'checked') {
			
			$('#signBtn').removeAttr('disabled');
	     	
		} else {
			$('#signBtn').attr('disabled', true);
		}
	});
	$('#legalAppSignCheck').bind('click',function() {
		if ($(this).attr('checked') == true
				|| $(this).attr('checked') == 'checked') {
			
			$('#legalSignBtn').removeAttr('disabled');
	     	
		} else {
			$('#legalSignBtn').attr('disabled', true);
		}
	});
};


    contractSign.validateID = function(){
    	var result = true;
    	if($('#mainBorrower').attr('validResult')!='1'){
    		result = false;
    	}
    	if($("input[id^='coboIDValidBtn']").length>0){
    		if(result){
    		   $("input[id^='coboIDValidBtn']").each(function(){
    			   if($(this).attr('validResult')!='1'){
    		    		result = false;
    		    	}
    		   }); 
    		}
    	}
    	return result;
    }
    /**
     *合同下载 
     * 
     */
    contractSign.download=function(applyId,token,wobNum){
	   
	    window.location.href=ctx+"/apply/contractAudit/downLoadContract?applyId="+applyId+"&token="+token+"&wobNum="+wobNum;
	};
    /**
     *合同上传 
     * 
     */	
    contractSign.upload=function(loanCode){
    	var url = imageUrl;
    	art.dialog.open(url, {  
			   id: 'largeAmount',
			   title: '影像资料',
			   lock:false,
			   width:700,
			   height:350
			},false); 
	};	
	/**
	 * 合同上传确认
	 * 
	 */
    contractSign.uploadConfirm=function(){
    	contractSign.dispatchFlow();
    };
    /**
	 * 流程提交、退回
	 * @param queue 队列集合的名称；viewName 返回的视图；response 节点路由；redirectUrl 重定向路径 
	 */
    contractSign.dispatchFlow=function(){
		$('#loanapplyForm').attr('action',ctx+"/apply/contractAudit/dispatchFlow");
		$('#loanapplyForm').submit();
		//window.location = ctx+"/apply/contractAudit/dispatchFlow?"+finalForm;
	};
    contractSign.backFlow = function(viewName,redirectUrl,targetForm){
    	var param = $('#'+targetForm).serialize();
    	 var url = ctx + '/apply/contractUtil/openGrantDialog?viewName='+viewName+"&"+param;
    	 if(redirectUrl!='' && redirectUrl!= null){
    		 url = url +"&redirectUrl="+redirectUrl
    	 }
    	 art.dialog.open(url, {  
             id: 'his',
             title: '退回!',
             lock:true,
             width:700,
         	 height:350
         },  
         false);
    	};	
    
    /**
     * 
     * 返回
     * 
     */
    contractSign.returnBack=function(){
    	window.location = ctx+"/borrow/borrowlist/fetchTaskItems";
    	
    };
    contractSign.judgeSignTime=function(){
    	var result = true;
    	var dueDay = $('#dueDay').val();
    	var curDay = $('#curDay').val();
    	dueDay = dueDay.replace(new RegExp(/(-)/g),'');
    	curDay = curDay.replace(new RegExp(/(-)/g),'');
    	if(parseInt(dueDay)<parseInt(curDay)){
    		result = false;
    	}
    	return result;
    };
    /**
     *打开上传照片的对话框 
     * 
     * 
     */
    contractSign.openFileDialog = function(sourceId,zoomId,validBtnId,relationId,photoType,customerType,loanCode,contractCode){
       var data = "relationId="+relationId+"&photoType="+photoType+"&customerType="+customerType+"&loanCode="+loanCode+"&contractCode="+contractCode;
       art.dialog.open(ctx+"/paperless/confirminfo/openPhotoUpload?"+data,{
    	    title: '照片上传',
    	    width:400, 
			height:80, 
			lock:true,
			opacity: .1,
            okVal:'确定',
    	    ok: function(){
    	    	var iframe = this.iframe.contentWindow;
    	   		//$('#fileForm',iframe.document).submit();
    	    	var fileSize = $('#fileSize',iframe.document).val();
    	    	if(parseInt(fileSize)>100*102400){
    	    		art.dialog.alert("图片大小不能超过10M");
    	    		return false;
    	    	}
    	   	 var formData = new FormData($('#fileForm',iframe.document)[0]);
    	     var win = art.dialog.open.origin;//来源页面
			 contents_getJsonForFileUpload(
					 this,
					 ctx+'/paperless/confirminfo/savePhoto',
					 formData,
					 function(result){
					     $('#'+sourceId,win.document).attr("src",ctx+'/paperless/confirminfo/getPreparePhoto?docId='+result.docId);
						 $('#'+zoomId,win.document).attr('href',ctx+'/paperless/confirminfo/getPreparePhoto?docId='+result.docId);
						 $('#'+sourceId,win.document).attr('updated','1');
						 if(validBtnId!=''){// 重新上传完身份证号之后，使按钮可以点击
							 $('#'+validBtnId).removeAttr('disabled');
							 $('#'+validBtnId).attr('validResult','');
							 $('#'+validBtnId).attr('docId',result.docId);
							 $('#'+validBtnId).attr('idNumOCR',result.idNumOCR);
							 $('#'+validBtnId).attr('customerNameOCR',result.customerNameOCR);
						 }
					  },null,false);	
			 return true;
    	    }
    	},false);
     };
     /**
      * 校验图片是否上传 
      */
     contractSign.updatedValid = function(){
    	 // 主借人现场照
    	 if($('#mainPlotPhoto').attr('updated')!='1'){
    		 return false;
    	 }
    	 // 主借人身份证照
    	 if($('#mainIdPhoto').attr('updated')!='1'){
    		 return false;
    	 }
    	 var plotPhotos = $("img[id^='plotPhoto']");
    	 var i = 0;
    	 if(plotPhotos.length>0){
    		 for(i=0;i<plotPhotos.length;i++){
    			 if($(plotPhotos[i]).attr('updated')!="1"){
    				 return false;
    			 }
    		 }
    	 }
    	 var idPhotos = $("img[id^='IdPhoto']");
    	 var i = 0;
    	 if(idPhotos.length>0){
    		 for(i=0;i<idPhotos.length;i++){
    			 if($(idPhotos[i]).attr('updated')!="1"){
    				 return false;
    			 }
    		 }
    	 }
    	 return true;
     };
     /**
      * 校验划扣协议与CA签章是否上传到信雅达信用合同文件夹 
      */
     contractSign.uploadSunyardValid = function(str){
    	 var valid = false;
    	 // 合同资料
    	 $.ajax({
      		type:'post',
  			url :ctx + '/apply/contractAudit/uploadSunyardValid',
  			data:{
  				'loanCode':str
  			},
  			cache:false,
  			dataType:'json',
  			async:false,
  			success:function(result) {
  				if(result){
  					valid = true;
  				}
  			},
  			error : function() {
  				art.dialog.alert('请求异常！', '提示信息');
  			}
        });
    	 return valid;
     };
     /**
      *身份证检测
      *@param customerId  
      *@param customerType  
      *@param docId 
      *@param idNumOCR 
      *@param customerNameOCR 
      */
     contractSign.validIDCard = function(customerId,customerType,obj){
    	 if($(obj).attr("docId")=="" || $(obj).attr("docId")==undefined || $(obj).attr("docId")==null){
    		 art.dialog.alert('请先获取身份证信息');
    		 return false;
    	 }
    	/* if($(obj).attr("idNumOCR")==''|| $(obj).attr("idNumOCR")==undefined 
    			 || $(obj).attr("customerNameOCR")==''|| $(obj).attr("customerNameOCR")==undefined){
    		 art.dialog.alert('OCR信息为空');
    		 return false; 
    	 }*/
    	 
    	 $.ajax({
      		type:'post',
  			url :ctx + '/paperless/confirminfo/findVerNum',
  			data:{
  				'docId':$(obj).attr("docId"),
  				'customerId':customerId,
  				'customerType':customerType
  			},
  			cache:false,
  			dataType:'json',
  			async:false,
  			success:function(result) {
  				var flag=result.flag;
  				var num=result.verNum;
  				//存在验证错误信息  需要判断验证次数
  				if(flag=='0'){
  					if(num=='0'){
  						contractSign.verification(result.message,customerId,customerType,obj,result.code);
  						return;
  					}
  				}
  				//身份证号或姓名未修改
  				if(flag=='2'){
  					art.dialog.alert("此客户还有"+result.verNum+"次验证机会，请修改客户姓名或客户身份证号！");
  					return;
  				}
  				if(flag=='3'){
  					art.dialog.alert(result.message);
  					$(obj).attr('disabled',true);
  					return;
  				}
  				//正常数据或错误数据但验证次数>0 可以进行身份验证
  				if(flag=='1'||(flag=='0'&&num!='0')){
  					$.ajax({
  			     		type:'post',
  			 			url :ctx + '/paperless/confirminfo/validIDCard',
  			 			data:{
  			 				'docId':$(obj).attr("docId"),
  			 				'customerId':customerId,
  			 				'customerType':customerType
  			 			},
  			 			cache:false,
  			 			dataType:'json',
  			 			async:false,
  			 			success:function(result) {
  			 				//正常返回
  			 				$(obj).attr('validResult',result.flag);
  			 				if("1"==result.flag){
  			 					$(obj).attr('disabled',true);
  			 					art.dialog.alert(result.message);
  			 				}else{
  			 					//特征提取失败 （2030）
  			 					if(result.code=='2030'){
		 							contractSign.verification(result.message,customerId,customerType,obj,result.code);
		 							$(obj).attr('disabled',true);
  			 						
  			 					}
  			 					//当失败原因为图片质量不合格 （9998）
  			 					if(result.code=='9998'){
  			 						var num=null;
  			 						if(result.verifyNumber!=null){
  			 							num=parseInt(result.verifyNumber);
  			 						}
  			 						if(num>0){
  			 							art.dialog.alert("失败原因：</br>"+result.message+"</br>"+"此客户还有"+num+"次验证机会，请核实后再进行验证！");
  			 						}else{
  			 							contractSign.verification(result.message,customerId,customerType,obj,result.code);
  			 							$(obj).attr('disabled',true);
  			 						}
  			 					}
  			 					
  			 					//当失败原因为身份证号一致但姓名不一致（2011）
  			 					if(result.code=='2011'){
  			 						var num=null;
  			 						if(result.verifyNumber!=null){
  			 							num=parseInt(result.verifyNumber);
  			 						}
  			 						if(num>0){
  			 							art.dialog.alert("失败原因：</br>"+result.message+"</br>"+"此客户还有"+num+"次验证机会，请修改客户姓名或身份证号！");
  			 						}else{
  			 							art.dialog.alert(result.message);
  			 							$(obj).attr('disabled',true);
  			 						}
  			 					}
  			 					
  			 					//验证身份信息不在查询范围内(2014)
  			 					if(result.code=='2014'||result.code=='2012'||result.code==result.SYS_CERT_VERIFY_FAILCODE_PER){
  			 						contractSign.verification(result.message,customerId,customerType,obj,result.code);
  			 					}
  			 					if(result.code!='2030' && result.code!='9998'&&result.code!='2011'&&result.code!='2014'&&result.code!='2012'&&result.code!=result.SYS_CERT_VERIFY_FAILCODE_PER){
  			 						art.dialog.alert(result.message);
  			 					}
  			 				}
  			 				
  			 			},
  			 			error : function() {
  			 				art.dialog.alert('请求异常！', '提示信息');
  			 			}
  			       	});
  				}
  				
  			},
  			error : function() {
  				art.dialog.alert('请求异常！', '提示信息');
  			}
        });
    	 
    	 
    	
    	 
     };
     
     //手动验证
     contractSign.verification = function(message,customerId,customerType,obj,code){
    	 message = decodeURIComponent(message,true); 
    	 message = encodeURI(encodeURI(message));
    	 art.dialog.open(ctx+"/paperless/confirminfo/openVerification?message="+message+"&customerId="+customerId+"&customerType="+customerType+"&code="+code,{
     	    title: '验证失败',
     	    width:500, 
 			height:200, 
 			lock:true,
 			opacity: .1,
            okVal:'确定',
            cancelVal:'关闭',
     	    ok: function(){
     	    	var iframe = this.iframe.contentWindow;
     	    	var temp = iframe.document.getElementsByName("verificationRadio");
     	    	var verificateReason = iframe.document.getElementById('verificateReason').value;
     	    	var intHot=null;
 	    	    for(var i=0;i<temp.length;i++){
 	    	     if(temp[i].checked)
 	    	            intHot = temp[i].value;
 	    	    }
 	    	    if(intHot==null||intHot==''){
 	    	    	art.dialog.alert('请进行手动验证！', '提示信息');
 	    	    	return false;
 	    	    }
 	    	   if(intHot=='0'&&(verificateReason==null||verificateReason=='')){
	    	    	art.dialog.alert('请填写手动验证原因！', '提示信息');
	    	    	return false;
	    	    }
 	    	  if(intHot=='0'&&(verificateReason!=null)){
 	    		  var len=verificateReason.length;
 	    		  if(len<5){
 	    			art.dialog.alert('输入的手动验证原因不能少于5个字！', '提示信息');
 	    	    	return false;
 	    		  }
	    	    	
	    	    }
 	    	    if(intHot=='0'){
 	    	    	$.ajax({
 	     	     		type:'post',
 	     	 			url :ctx + '/paperless/confirminfo/saveHistory',
 	     	 			data:{
 	     	 				'verificateReason':verificateReason,
 	     	 				'customerId':customerId,
 	     	 				'customerType':customerType
 	     	 			},
 	     	 			cache:false,
 	     	 			dataType:'json',
 	     	 			async:false,
 	     	 			success:function(result) {
 	     	 				
 	     	 				$(obj).attr('validResult',result.flag);
 	     	 				art.dialog.alert(result.message);
 	     	 				$(obj).attr('disabled',true);
 	     	 			},
 	     	 			error : function() {
 	     	 				art.dialog.alert('请求异常！', '提示信息');
 	     	 			}
 	     	       	});
 	    	    }
     	    	
     	    	
     	    },
     	    cancel:function(){
     	    	art.dialog.close();
     	    }
     	    
     	},false);
    	 
    	 
     }
    /**
     *保存照片信息 
     *@author zhanghao
     *@param relationId
     *@param photoType
     *@param customerType
     *@param loanCode
     *
     * 
     */
    contractSign.savePhoto = function(relationId,photoType,customerType,loanCode){
    	$.ajax({
    		type:'post',
			url :ctx + '/paperless/confirminfo/savePhoto',
			data:{
				'relationId':relationId,
				'photoType':photoType,
				'customerType':customerType,
				"loanCode":loanCode
			},
			cache:false,
			dataType:'json',
			async:false,
			success:function(result) {
				
			},
			error : function() {
				art.dialog.error('请求异常！', '提示信息');
			}
      	});
    };
    contractSign.zoomUp = function(href){
    	art.dialog({
    	    title: '照片上传',
    	    content:"<img src='"+href+"'>",
    	    width:400, 
			height:80, 
			lock:true,
			opacity: .1
     	},false);
    };
    
    $(function(){
    	$("#storeGiveUp1").click(function(){
    		art.dialog({
    			content: document.getElementById("refuseMod"),
    			title:'建议拒绝',
    			fixed: true,
    			lock:true,
    			okVal: '确认拒绝',
    			ok: function () {
    				$("#remark").val($("#rejectReason").val());
    				if ($("#remark").val() == null || $("#remark").val()=='') {
    					art.dialog.alert("请输入拒绝原因!");
    					return false;
    				}else{					
    						$('#response').val('TO_PROPOSE_OUT2');
    					    $('#dictOperateResult').val('11');
    					    contractSign.uploadConfirm();
    					    $('#giveUpBtn1').attr('disabled',true);
    					    $('#confirmBtn').attr('disabled',true);
    					    $('#backBtn').attr('disabled',true);
    					    $('#storeGiveUp1').attr('disabled',true);
    				}
    				
    			},
    			cancel: true
    		});
    	});
    	$("#giveUpBtn1").click(function(){
    		art.dialog({
    			content: document.getElementById("refuseMod"),
    			title:'建议放弃',
    			fixed: true,
    			lock:true,
    			okVal: '确认放弃',
    			ok: function () {
    				$("#remark").val($("#rejectReason").val());
    				if ($("#remark").val() == null || $("#remark").val()=='') {
    					art.dialog.alert("请输入放弃原因!");
    					return false;
    				}else{					
    						$('#response').val('TO_PROPOSE_OUT2');
    					    $('#dictOperateResult').val('13');
    					    contractSign.uploadConfirm();
    					    $('#giveUpBtn1').attr('disabled',true);
    					    $('#confirmBtn').attr('disabled',true);
    					    $('#backBtn').attr('disabled',true);
    					    $('#storeGiveUp1').attr('disabled',true);
    				}
    				return false;
    			},
    			cancel: true
    		});
    	});
    })