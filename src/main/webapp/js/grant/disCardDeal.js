var wid = screen.width/2 + 150;
var hei = screen.height/2 + 100;
window.middleId;
window.userCode;
$(document).ready(function(){
	// 点击选择账户,重新获得窗体，
	$("#selectMiddleBtn").click(function(){
		
		var flag = $("#flag").val(),
		url = "";
		if(flag == "disCard" || flag == "JX_disCard"){
			url= ctx+"/borrow/grant/disCard/showChoose";
			if(flag == "JX_disCard")
			{
				url = ctx+"/borrow/grant/disCard/showChoose?wayFlag=9";
			}
		}else{
				url = ctx+"/borrow/grant/grantAudit/selectMiddle";
		}
		jump(url);
	});
	
	// 样式控制
	function jump(url){
		art.dialog.open(url, {  
			   title: '分配卡号',
			   lock:true,
			   width:800,
			   height:550,
			   opacity: .1
			},false);
	}
	
	// 修改放款行的时候进行修改，
	$("#middleSure").click(function(){
		if($(":input[name='SelectBank']:checked").length == 0){
			art.dialog.alert("请选择卡号信息");
			return;
		}else{
			middleId=$(":input[name='SelectBank']:checked").attr("middleId");
			callback_setCardNum(middleId);
			closeAtr();
		}
		
	});
	
	
	// 总的选择中间人和放款人员信息
	$("#middleSureBtn").click(function(){
		var middleId=""; // 中间人id
		var userCode=""; // 选择放款人员
		if($(":input[name='SelectBank']:checked",window.frames['midPerson'].contentDocument).length == 0){
			art.dialog.alert("请选择卡号信息");
			return;
		}else if($(":input[name='SelectEl']:checked",window.frames['disCardPerson'].contentDocument).length==0){
			art.dialog.alert("请选择放款人员信息");
			return;
		}else{
			 middleId=$(":input[name='SelectBank']:checked",window.frames['midPerson'].contentDocument).attr("middleId");
			 userCode=$(":input[name='SelectEl']:checked",window.frames['disCardPerson'].contentDocument).attr("userCode");
			 callback_setCardNum(middleId,userCode);
			 closeAtr();
		}
	});
	
	function callback_setCardNum(middleId,userCode){
		$.ajax({
			type : 'post',
			url : ctx+'/borrow/grant/disCard/disPersonSure',
			data:{
				'userCode':userCode,
				'middleId':middleId
			},
			dataType:'json',
			async:false,
			success : function(data) {
				if(data!=null){
					var win = art.dialog.open.origin;//来源页面
					$("#middleName",win.document).val(data.middleName); 
					$("#midBankName",win.document).val(data.midBankName);
					$("#bankCardNo",win.document).val(data.bankCardNo);
					$("#userCode",win.document).val(userCode);
					$("#middleId",win.document).val(middleId);
					this.userCode  = userCode;
					this.middleId = middleId;
				}
			},
			error : function() {
				art.dialog.alert('请求异常！');
			}
		});
		
	}
	// 点击提交，将中间人id和放款人员编号更新到放款记录表中，同时更新放款状态，走流程
	 $("#disCommitBtn").click(function(){
		 var flag= $("#flag").val();
		 $(this).attr('disabled',true);
		 if($("#middleName").val()==""||$("#middleName").val()==null){
			 art.dialog.alert("请选择卡号再进行提交");
			 $('#disCommitBtn').removeAttr('disabled');
			 return;
		 }else{
			 if(flag == "disCard"){
				 commit(); 
			 }else{
				 updBankCommit();
			 }
			 
		 }
	 });
	
	 // 点击返回
	 $("#bankBtn").click(function(){
		 var flag = $("#flag").val();
		 if(flag =="disCard"){
			 window.location.href=ctx+"/borrow/grant/disCard/getDisCardList";
		 }else{
			 window.location.href=ctx+"/borrow/grant/grantAudit/grantAuditItem";
		 }
		
	 })
})

// 点击提交，获得applyId等处理的功能
function commit(){
	 var ParamEx=$("#param").serialize();
	 var userCode=$("#userCode").val();
	 var middleId=$("#middleId").val();
	 var deftokenId=$("#deftokenId").val();
	 var deftoken=$("#deftoken").val();
	 ParamEx+="&userCode="+userCode+"&middleId="+middleId+"&deftokenId="+deftokenId+"&deftoken="+deftoken;
	 var dialog = art.dialog({
			      content: '正在提交...',
			      cancel:false,
			      lock:true
		 		});
	 $.ajax({
			type : 'post',
			url : ctx+'/borrow/grant/disCard/disCardCommit',
			data :ParamEx,
			cache : false,
			success : function(result) {
				var obj = eval("("+result+")");
				/*dialog.content(obj.message);
				dialog.config.cancel = true;*/
				dialog.close();
				art.dialog.alert(obj.message,function(){
					window.location.href=ctx+"/borrow/grant/disCard/getDisCardList";
			  	});
				$('#disCommitBtn').removeAttr('disabled');
			},
			error : function() {
				dialog.close();
				art.dialog.alert('请求异常！');
				$('#disCommitBtn').removeAttr('disabled');
			}
		});
}

// 修改放款行的提交
function updBankCommit(){
	 var ParamEx=$("#param").serialize();
	 var middleId=$("#middleId").val();
	 ParamEx+="&middleId="+middleId;
	 $.ajax({
			type : 'post',
			url : ctx+'/borrow/grant/grantAudit/updBankCommit',
			data :ParamEx,
			cache : false,
			success : function(result) {
				art.dialog.alert(result);
				window.location.href=ctx+"/borrow/grant/grantAudit/grantAuditItem";
				$('#disCommitBtn').removeAttr('disabled');
			},
			error : function() {
				art.dialog.alert('请求异常！');
				$('#disCommitBtn').removeAttr('disabled');
			}
		});
}

function closeAtr(){
	art.dialog.close();
}