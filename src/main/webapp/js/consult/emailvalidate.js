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
	var sendEmailFlag = $("#sendEmailFlag").val();
	var email = $("input[name='loanCustomer.customerEmail']").val();
	if(sendEmailFlag==email && successFlag=='1'){
		top.$.jBox.tip('邮箱并未变更，无需再次验证！');
		return;
	}
	
	var reg = /\w+[@]{1}\w+[.]\w+/;
	if(reg.test(email)){
	    $("#emailvalidator").val("");
	    var url=ctx+"/borrow/borrowlist/sendEmail";
	    var customerName = $("#cuCustomerName1").val();
	    $.ajax({
			  url:url,
			  type:"post",
			  data:{'customerName':customerName,'customerEmail':email,
				  'customerCode':$("#bvCustomerCode").val(),'loanCode':$("#loanCode").val()},
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
	var email = $("input[name='loanCustomer.customerEmail']").val();
	if(sendEmailFlag!=email){
		return;
	}
	if(sendTime<31 && sendTime>0){
		$.ajax({
			  url:ctx+"/borrow/borrowlist/checkIfEmailConfirm",
			  type:"post",
			  data:{'customerCode':$("#bvCustomerCode").val(),'loanCode':$("#loanCode").val(),'customerEmail':email},
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
function updateEmailIfConfirm(){
	$.ajax({
		  url:ctx+"/borrow/borrowlist/updateEmailIfConfirm",
		  type:"post",
		  data:{'customerCode':$("#bvCustomerCode").val()},
		  async:false,
		  dataType:'json',
		  success:function(data){
			  
		  },error:function(data){
		  }
  });	
}
