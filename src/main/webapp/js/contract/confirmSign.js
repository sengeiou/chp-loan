var confirmSign={};

var InterValObj; //timer变量，控制时间
var count = 120; //间隔函数，1秒执行
var curCount;//当前剩余秒数
var crtBtn;
var hasValid = false; 
var timerArray = new Array();
function pinObj(){
	var pin = new Object();
	pin.index = 0;
	pin.timer=null;
	return pin;
};
confirmSign.load  = function(){
	updateEmailIfConfirm();
	
	if($('#custGiveUp').length>0){
		$('#custGiveUp').bind('click',function(){
			art.dialog.confirm('是否确定执行放弃操作', function (){
				$('#dictOperateResult').val("3");
				var url = ctx + "/apply/contractAudit/dispatchFlow";
				$('#backForm').attr('action',url); 
				$('#backForm').submit();
				$('#confirmSignBtn').attr('disabled',true);
				$('#custGiveUp').attr('disabled',true);
				$('#storeGiveUp').attr('disabled',true);
			});
		});
	}
	if($("#storeGiveUp").length>0){
		$("#storeGiveUp").click(function(){
			art.dialog({
				content: document.getElementById("refuseMod"),
				title:'门店拒绝',
				fixed: true,
				lock:true,
				okVal: '确认拒绝',
				ok: function () {
					$("#remark").val($("#rejectReason").val());
					if ($("#remark").val() == null || $("#remark").val()=='') {
						art.dialog.alert("请输入拒绝原因!");
					}else{					
						art.dialog.confirm('是否确定执行门店拒绝操作', function (){
							$('#dictOperateResult').val("1");
							var url = ctx + "/apply/contractAudit/dispatchFlow";
							$('#backForm').attr('action',url); 
							$('#backForm').submit();
							$('#confirmSignBtn').attr('disabled',true);
							$('#custGiveUp').attr('disabled',true);
							$('#storeGiveUp').attr('disabled',true);
						});
					}
				return false;
				},
				cancel: true
			});
		});
	}
 	// 中英文校验
    jQuery.validator.addMethod("stringCheck", function(value, element) {
      	var isOK = true;
      	if(value!=''){
      		var regex= /^[a-zA-Z\u4e00-\u9fa5 ]{1,30}$/;
      		isOK = regex.test(value);
          }
      	return this.optional(element) || (isOK);
     }, "只能输入长度为30位中文、英文、空格");
	 $('#confirmSignBtn').bind('click',function(){
		if(hasValid){
			art.dialog.alert("手机号还在验证");
			return false;
		}
		var issplit=$('#issplit').val();
		if(issplit=='1'){
			obj = document.getElementsByName("isreceive");
		    check_val = [];
		    for(k in obj){
		        if(obj[k].checked)
		            check_val.push(obj[k].value);
		    }
		    if(check_val.length==0){
		    	art.dialog.alert("请至少选择一笔借款");
		    	return false;
		    }
		}
		
	 });
	 $("#confirmSignForm").validate({
			onkeyup: true, 
	 		rules : {
				'loanBank.bankAccount' : {
				    digits:true,
					maxlength:19,
					minlength:16
				}
		  	},
			messages : {
				'loanBank.bankAccount':{
					digits:  "银行卡号必须为数字类型",
					maxlength:$.validator.format("请输入一个长度最多是 {0} 的数字字符串"),
					minlength:$.validator.format("请输入一个长度最少是 {0} 的数字字符串")
				}
			},
	 		submitHandler : function(form) {
	 			var sendEmailFlag = $("#sendEmailFlag").val();
				var email = $("input[name='email']").val();
				var oldEmailFlag = $("#oldEmailFlag").val();
				var productType= $('#productType').val();
				if(productType == 'A021' && email =='' ){
					
					var loanCode = $("#loanCode").val();
		 			var bankIsRareword= $("input[id='bankIsRareword']:checked").val();
		 			var bankAccountName= $("#bankAccountName").val();
		 			var url = ctx + "/apply/contractAudit/dispatchFlow?response=TO_CONTRACT_MAKE&redirectUrl="+REDIRECT_URL;
		 		   	form.action = url;
		  		  	art.dialog.confirm('是否确定执行提交操作', function (){
		  		  		$.ajax({
			  		  		url:ctx+"/borrow/borrowlist/confirmSignCheckAccountName",
			  				type:'post',
			  				data:{"loanCode":loanCode,"bankIsRareword":bankIsRareword,"bankAccountName":bankAccountName},
			  				dataType:'json',
			  				success:function(data){
			  					if(!data.success){
			  						art.dialog.alert(data.message);
			  					}else{
			  						$('#applyBankName').val($('#bankName option:selected').text());
			  						$('#signingPlatformName').val($("#bankSigningPlatform option:selected").text()); 
			  						$('#applyBankBranch').val($('#bankBranch').val());
			  						$('#applyBankAccount').val($('#bankAccount').val());
			  						if($('#modelName').val()=='TG'){
			  							$('#kingBankProvinceName').val($('#bankJzhKhhss option:selected').text()); 
			  							$('#kingBankProvinceCode').val($('#bankJzhKhhss option:selected').val()); 
			  							$('#kingBankCityCode').val($('#bankJzhKhhqx option:selected').val()); 
			  							$('#kingBankCityName').val($('#bankJzhKhhqx option:selected').text()); 
			  						}
			  						$('#confirmSignBtn').attr('disabled',true);
			  						$('#custGiveUp').attr('disabled',true);
			  						form.submit(); 
			  					}
			  				},
			  				async:false, 
			  				error:function(){
			  					art.dialog.alert('服务器异常！');
			  					return false;
			  				}
		  		  		});
		 	     	 });
				}else{
					//如果填写邮箱不是之前保存过的邮箱，并且不是验证过的邮箱则进行验证提示
					if(sendEmailFlag!=email && email!=oldEmailFlag){
						top.$.jBox.tip('请进行邮箱验证处理！');
						return;
					}
					$.ajax({
						  url:ctx+"/borrow/borrowlist/checkIfEmailConfirm",
						  type:"post",
						  data:{'customerCode':$("#custCode").val(),'loanCode':$("#loanCode").val(),'customerEmail':email},
						  async:false,
						  dataType:'json',
						  success:function(data){
							  if(data=='1'){
								    var loanCode = $("#loanCode").val();
						 			var bankIsRareword= $("input[id='bankIsRareword']:checked").val();
						 			var bankAccountName= $("#bankAccountName").val();
						 			var url = ctx + "/apply/contractAudit/dispatchFlow?response=TO_CONTRACT_MAKE&redirectUrl="+REDIRECT_URL;
						 		   	form.action = url;
						  		  	art.dialog.confirm('是否确定执行提交操作', function (){
						  		  		$.ajax({
							  		  		url:ctx+"/borrow/borrowlist/confirmSignCheckAccountName",
							  				type:'post',
							  				data:{"loanCode":loanCode,"bankIsRareword":bankIsRareword,"bankAccountName":bankAccountName},
							  				dataType:'json',
							  				success:function(data){
							  					if(!data.success){
							  						art.dialog.alert(data.message);
							  					}else{
							  						$('#applyBankName').val($('#bankName option:selected').text());
							  						$('#signingPlatformName').val($("#bankSigningPlatform option:selected").text()); 
							  						$('#applyBankBranch').val($('#bankBranch').val());
							  						$('#applyBankAccount').val($('#bankAccount').val());
							  						if($('#modelName').val()=='TG'){
							  							$('#kingBankProvinceName').val($('#bankJzhKhhss option:selected').text()); 
							  							$('#kingBankProvinceCode').val($('#bankJzhKhhss option:selected').val()); 
							  							$('#kingBankCityCode').val($('#bankJzhKhhqx option:selected').val()); 
							  							$('#kingBankCityName').val($('#bankJzhKhhqx option:selected').text()); 
							  						}
							  						$('#confirmSignBtn').attr('disabled',true);
							  						$('#custGiveUp').attr('disabled',true);
							  						form.submit(); 
							  					}
							  				},
							  				async:false, 
							  				error:function(){
							  					art.dialog.alert('服务器异常！');
							  					return false;
							  				}
						  		  		});
						 	     	 });
							  }else{
								  top.$.jBox.tip('请进行邮箱验证处理！');
							  }
						  },error:function(data){
							  top.$.jBox.tip('业务处理错误');
						  }
				    });
				}
				
				
	 		 },
	 	  });
     
	  $('#historyBtn').bind('click',function(){
		  showHisByLoanCode($(this).attr('loanCode'));
	  }); 
	  
	  $('#retBtn').bind('click',function(){
		window.location=ctx+REDIRECT_URL;
	  }); 
	  loan.initCity("bankProvince", "bankCity",null);
	  
	  $('#txt_pin').rules('add', {required:true,equalTo:'#pin', messages: { equalTo: ""}});
/*	  if($("#pin").val()!=null && $("#pin").val()!='' && $("#pin").val()!=undefined)
	  {
		 $('#btnPin').attr('disabled',true);
		 $('#btnPin').val("客户已验证");
		 $('#txt_pin').attr('readonly',true);
	  }*/
		
	  //var co_count = ${fn:length(workItem.bv.coborrowers)};
	  for(i=1;i<=co_count;i++)
	  {
		$('#txt_co_pin_'+ i).rules('add', {required:true,equalTo:'#co_pin_'+i, messages: { equalTo: ""}});
		
		/*if($('#co_pin_'+i).val()!=null && $('#co_pin_'+i).val()!="" && $('#co_pin_'+i).val()!=undefined)
		 {
			 $('#btn_co_pin_'+i).attr('disabled',true);
			 $('#btn_co_pin_'+i).val("客户已验证");
			 $('#txt_co_pin_'+i).attr('readonly',true);
		 }*/
	  }
  }
confirm.removeRules = function(){
	/*$('#txt_pin').rules("remove");
	for(i=1;i<=co_count;i++){
		$('#txt_co_pin_'+ i).rules('remove');
	}
	$('#contractDueDay').removeClass('required');
	$('#confirmSignForm').validate({
		beforeSubmit:true
	});*/
	$('#confirmSignForm').validate({
		ignore:"#txt_pin,#contractDueDay"
	});
};

//发送短信
function sendPin(obj,phones,customerCode,customerName)
{     if(hasValid){
		art.dialog.alert("等验证中的手机号完成，才可继续");
		return false;
	  }
	  $('#confirmSignBtn').attr('disabled',true);
      hasValid = true;
	  var typevalue = $(obj).attr("stype");
	  $.ajax({
		   type: "POST",
		   url : ctx + "/paperless/confirminfo/sendPin",
	 	   data: {
	 		   	  loanCode: $("#loanCode").val(),
	 		   	  customerCode:customerCode,
	 		   	  phone:phones,
	 		   	  customerType:typevalue,
	 		   	  customerName:customerName
	 		   	 },	
		   success: function(data){
			   if(data!=""){
					crtBtn = obj;
					
					//设置button效果，开始计时
					$(obj).attr("disabled", "true");
					
					var timer = countDown(120,obj);
					var has = false;
					var currPin = null;
					if(timerArray.length==0){
						currPin = pinObj();
						currPin.index=typevalue;
						currPin.timer=timer;
						timerArray.push(currPin);
					}else{
						for(var m in timerArray){
							if(timerArray[m].index==typevalue){
								has = true;
							}
						}
						if(!has){
							currPin = pinObj();
							currPin.index=typevalue;
							currPin.timer=timer;
							timerArray.push(currPin);
						}
					}
				   if(typevalue=="0")
					{
					   $("#pin").val(data);
					}
				   else
					{
					   $("#co_pin_"+ typevalue).val(data);
					}
				   art.dialog.alert("验证码发送成功，请及时确认！");
			   }else{
				   art.dialog.alert("验证码发送失败！");
			   }
		  }
	});
}
// 保存校验结果
function confirmPin(obj,customerCode,type){
	var typevalue = $(obj).attr("stype");
	var inputValue = $(obj).val();
	var hiddenValue = "";
	if(type=='0'){
		hiddenValue = $('#pin').val();
	}else{
		hiddenValue = $('#co_pin_'+type).val();
	}
	if($.trim(inputValue)=="" || $.trim(inputValue)==null){
		return false;
	}
	if(inputValue!=hiddenValue){
		$("<label class='error' for='"+$(obj).attr('id')+"'></label>").appendTo(obj);
		$(obj).addClass('error');
		return false;
	}else{
		$("label[for='"+$(obj).attr('id')+"']").remove();
	}
	  $.ajax({
		   type: "POST",
		   url : ctx + "/paperless/confirminfo/confirmPin",
	 	   data: {
	 		   	  loanCode: $("#loanCode").val(),
	 		   	  customerCode:customerCode,
	 		   	  customerType:type 
	 		   	 },	
		   success: function(data){
			   if(data!=""){
					crtBtn = obj;
					
					//设置button效果，开始计时
					$(obj).attr("disabled", "true");
					/*if(data=='SUCCESS'){
						$("<label for='"+$(obj).attr('id')+"'>成功</label>").appendTo(obj);
					}*/
					for(var m in timerArray){
					  if(timerArray[m].index==type){
					    clearInterval(timerArray[m].timer); 
					  }
					}
					if(data=='SUCCESS'){
						hasValid = false;
						$('#confirmSignBtn').removeAttr('disabled');
					 	if(type=="0"){
							$("#txt_pin").attr("disabled", "true");
							$('#btnPin').val("客户已验证");
						}else{
							$("#btn_co_pin_"+type).attr("disabled", "true");
							$('#btn_co_pin_'+type).val("客户已验证");
						}
					}else if(data=='FAILES'){
						if(type=="0"){
							$("#txt_pin").removeAttr("disabled");
						 }else{
							$("#btn_co_pin_"+type).removeAttr("disabled");
						}
					}
			   }else{
				   art.dialog.alert("验证码发送失败！");
			   }
		  }
	});
}
/**
 * 倒计时
 * @param maxtime
 * @param fn
 */
function countDown( maxtime,obj )  
{      
   var timer = setInterval(function()  
   {  
       if(maxtime>=0){     
             msg = "距离结束还有"+maxtime+"秒";     
             $(obj).val("重新发送" + maxtime );
             --maxtime;
        }     
       else
       {     
            clearInterval(timer); 
            
            $(obj).removeAttr("disabled");//启用按钮
            $(obj).val("重发验证码");
            hasValid = false;
        }     
    }, 1000);
   return timer;
} 




/**
 * 发送邮件
 */
var countFlag=150;
var countdown=countFlag;
var successFlag="0";
var timeFlag=30;
var sendTime=timeFlag;
function sendEmail(val){
	if (countdown == 0) { 
		val.removeAttribute("disabled"); 
		val.value="发送邮箱验证"; 
		if(successFlag=='0'){
			$("#checkImg").remove();
			$("#emailBtn").after("<img id='checkImg' src="+context+"/static/images/chahao.png/>");
		}
		countdown = countFlag; 
		
	} else { 
		val.setAttribute("disabled", true); 
		val.value="重新发送(" + countdown + ")"; 
		countdown--;
		setTimeout(function() { 
			sendEmail(val) 
		},1000);
	} 
}
function sendtoemail(val){
	var email=$("input[name='email']").val();
	var reg = /\w+[@]{1}\w+[.]\w+/;
	if(reg.test(email)){
	    $("#emailvalidator").val("");
	    var url=ctx+"/borrow/borrowlist/sendEmail";
	    var customerName = $("#mainLoaner").val();
	    $.ajax({
			  url:url,
			  type:"post",
			  data:{'customerName':customerName,'customerEmail':email,
				  'customerCode':$("#custCode").val(),'loanCode':$("#loanCode").val()},
			  async:false,
			  dataType:'json',
			  success:function(data){
				  if(data.code=='1'){
					  sendEmail(val);
					  top.$.jBox.tip('发送成功');
					  $("#sendEmailFlag").val(email);
					  sendTime=timeFlag;
					  checkifemailConfirm();
				  }else if(data.code=='2'){
					  top.$.jBox.tip('发送失败');
				  }else{
					  top.$.jBox.tip(data.msg);
				  }
				  
			  },error:function(data){
				  top.$.jBox.tip('业务处理错误');
			  }
	    });
	}else{
		$("#checkImg").remove();
	    $("#emailvalidator").val("请输入正确的email地址");
	}
}
/**
 * 查询邮箱是否已验证
 */
function checkifemailConfirm(){
	var sendEmailFlag = $("#sendEmailFlag").val();
	var email = $("input[name='email']").val();
	if(sendEmailFlag!=email){
		return;
	}
	if(sendTime<31 && sendTime>0){
		$.ajax({
			  url:ctx+"/borrow/borrowlist/checkIfEmailConfirm",
			  type:"post",
			  data:{'customerCode':$("#custCode").val(),'loanCode':$("#loanCode").val(),'customerEmail':email},
			  async:false,
			  dataType:'json',
			  success:function(data){
				  if(data=='1'){
					  $("#checkImg").remove();
					  $("#emailBtn").after("<img id='checkImg' src="+context+"/static/images/duigou.png/>");
					  successFlag="1";
					  countdown=0; 
				  }else if(data=='2'){
					  $("#checkImg").remove();
					  $("#emailBtn").after("<img id='checkImg' src="+context+"/static/images/chahao.png/>");
					  successFlag="2";
				  }else{
					  $("#checkImg").remove();
					  sendTime--;
					  setTimeout(function() { 
						  checkifemailConfirm() 
					  },5000);
				  }
				  
			  },error:function(data){
				  top.$.jBox.tip('业务处理错误');
			  }
	    });
		
	}
}

$(function(){
	$("#custGiveUp1").click(function(){
		art.dialog({
			content: document.getElementById("refuseMod"),
			title:'建议放弃',
			fixed: true,
			lock:true,
			okVal: '确认放弃',
			ok: function () {
				$("#remark1").val($("#rejectReason").val());
				if ($("#remark1").val() == null || $("#remark1").val()=='') {
					art.dialog.alert("请输入放弃原因!");
					return false;
				}else{					
					    $('#response1').val('TO_PROPOSE_OUT1');
					    $('#dictOperateResult1').val("13");
						var url = ctx + "/apply/contractAudit/dispatchFlow";
						$('#backForm').attr('action',url); 
						$('#backForm').submit();
						$('#confirmSignBtn').attr('disabled',true);
						$('#custGiveUp1').attr('disabled',true);
				}
				return false;
			},
			cancel: true
		});
	});
})

function updateEmailIfConfirm(){
	//if($("#loanCode").val()==null || $("#loanCode").val()==''){
		$.ajax({
			  url:ctx+"/borrow/borrowlist/updateEmailIfConfirm",
			  type:"post",
			  data:{'customerCode':$("#custCode").val()},
			  async:false,
			  dataType:'json',
			  success:function(data){
				  
			  },error:function(data){
			  }
	  });
	//}
	
}
