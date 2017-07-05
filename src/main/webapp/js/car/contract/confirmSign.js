var confirmSign={};

var InterValObj; //timer变量，控制时间
var count = 120; //间隔函数，1秒执行
var curCount;//当前剩余秒数
var crtBtn;
var timerArray = new Array();
function pinObj(){
	var pin = new Object();
	pin.index = 0;
	pin.timer=null;
	return pin;
};
var hasValid = false; 
//发送短信
function sendPin(obj,phones,customerCode,customerName)
{
	  var typevalue = $(obj).attr("stype");
	  $.ajax({
		   type: "POST",
		   url : ctx + "/carpaperless/confirminfo/sendPin",
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
				   if(typevalue=="0" )
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
	if(inputValue!=hiddenValue){
		$('#lab_co_pin_'+type).addClass('error');
		hasValid=true;
		return false;
	}else{
		$('#lab_co_pin_'+type).removeClass('error');
	}
	  $.ajax({
		   type: "POST",
		   url : ctx + "/carpaperless/confirminfo/confirmPin",
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
						if(type=="0"){
							$("#txt_pin").attr("disabled", "true");
							$('#btnPin').val("客户已验证");
						}else{
							$("#btn_co_pin_"+type).attr("disabled", "true");
							$('#btn_co_pin_'+type).val("客户已验证");
						}
					}else if(data=='FAILES'){
						hasValid = true;
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
             --maxtime;
             $(obj).val("重新发送" + maxtime );
        }     
       else
       {     
            clearInterval(timer ); 
            
            $(obj).removeAttr("disabled");//启用按钮
            $(obj).val("重发验证码");
        }     
    }, 1000);
   return timer;
}  	